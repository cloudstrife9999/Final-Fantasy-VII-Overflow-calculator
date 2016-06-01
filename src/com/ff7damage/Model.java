package com.ff7damage;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import com.ff7damage.characters.CharacterFactory;
import com.ff7damage.characters.CharacterInterface;
import com.ff7damage.weapons.WeaponInterface;
import com.ff7damage.weapons.WeaponsFactory;

public class Model extends Observable implements Observer {
	private int heroDrinksNumber;
	private boolean criticalHit;
	private CharacterInterface attacker;
	private Target target;
	private boolean splitAttack;
	private Elements attackElement;
	private int techniquePower;
	private boolean longRangeMateria;
	private boolean limit;
	private byte limitCode;
	private Random random;
	private Overflow overflow;
	
	private List<String> resultsForController;
	
	public Model() {
		this.random = new SecureRandom();
	}
	
	private int criticalMultiplier() {
		return this.criticalHit ? 2 : 1;
	}
	
	private double berserkMultiplier() {
		return this.attacker.isBerserked() ? 1.5 : 1;
	}
	
	private double rowMultiplier() {
		if(this.longRangeMateria || this.attacker.getWeapon().isLongRange()) {
			return 1;
		}
		
		return this.attacker.getRow().equals(Row.BACK) || this.target.getRow().equals(Row.BACK) ? 0.5 : 1;
	}
	
	private double defenseStatusMultiplier() {
		return this.target.isInDefenseMode() ? 0.5 : 1;
	}
	
	private double sadnessMultiplier() {
		return this.target.isInSadness() ? 7/10 : 1;
	}
	
	private double frogMultiplier() {
		return this.attacker.isInFrog() ? 0.25 : 1;
	}
	
	private int miniMultiplier() {
		return this.attacker.isInMini() ? 0 : 1;
	}
	
	private int backMultiplier() {
		return this.target.isBackAttacked() ? this.target.getTheoreticalBackAttackMultiplier() : 1;
	}
	
	private double barrierMultiplier() {
		return this.target.isInBarrier() ? 0.5 : 1;
	}
	
	private double splitMultiplier() {
		return this.splitAttack ? 2/3 : 1;
	}
	
	private int randomNumerator() {
		return this.random.nextInt(256) + 3841;
	}
	
	private double log2(int value) {
		return Math.log(value)/Math.log(2);
	}
	
	private int divide(int numerator, int denominator) {
		if(numerator >= 0 && numerator < denominator) {
			return 1;
		}
		
		double tmp = log2(denominator);
		
		if(Math.floor(tmp) == tmp) {
			return numerator >> (int)tmp;
		}
		else {
			return Math.floorDiv(numerator, denominator);
		}
	}
	
	private int attackMultiplier() {
		int level = this.attacker.getLevel();
		int attack = this.attacker.getBoostedAttack();
		
		int tmp1 = divide(level + attack, 32);
		int tmp2 = divide(level*attack, 32);
		
		return attack + tmp1*tmp2;
	}
	
	private int defenseMultiplier() {
		return Math.max(0, 512 - this.target.getDefense());
	}
	
	private int powerMultiplier() {
		if(this.limit) {
			return Utils.getLimitPowerMultiplier(this.attacker.toString(), this.limitCode);
		}
		
		if(this.attacker.getKills() != Utils.USELESS) { //Vincent with Death Penalty
			return this.attacker.getPowerModifier(this.techniquePower, new int[]{this.attacker.getKills()});
		}
		else if(this.attacker.getWeaponAP() != Utils.USELESS) { //Barret with Missing Score
			return this.attacker.getPowerModifier(this.techniquePower, new int[]{this.attacker.getWeaponAP()});
		}
		else if(this.attacker.getCurrentHp() != Utils.USELESS && this.attacker.getMaxHp() != Utils.USELESS) { //Cloud with Ultima Weapon or Cait Sith with HP Shout
			return this.attacker.getPowerModifier(this.techniquePower, new int[]{this.attacker.getCurrentHp(), this.attacker.getMaxHp()});
		}
		else if(this.attacker.getCurrentMp() != Utils.USELESS && this.attacker.getMaxMp() != Utils.USELESS) { // Red XIII with Limited Moon or Cid with Venus Gospel
			return this.attacker.getPowerModifier(this.techniquePower, new int[]{this.attacker.getCurrentMp(), this.attacker.getMaxMp()});
		}
		else if(this.target.getAverageLevel(this.attacker.toString()) != Utils.USELESS) { //Yuffie with Conformer
			return this.attacker.getPowerModifier(this.techniquePower, new int[]{this.target.getAverageLevel(this.attacker.toString())});
		}
		else if(this.attacker.getLimitLevel() != Utils.USELESS && this.attacker.getLimitGauge() != Utils.USELESS) { //Tifa with Premium Heart
			return this.attacker.getPowerModifier(this.techniquePower, new int[]{this.attacker.getLimitLevel(), this.attacker.getLimitGauge()});
		}
		else { //Aerith with Princess Guard and any character without his/her final weapon.
			return this.attacker.getPowerModifier(this.techniquePower, null);
		}
	}
	
	private int baseDamage(int pm, int dm, int am) {
		BigInteger bigTmp = BigInteger.valueOf(pm).multiply(BigInteger.valueOf(dm)).multiply(BigInteger.valueOf(am));
		this.overflow.setBdNumerator(bigTmp);
		
		int tmp = pm*dm*am;
		this.overflow.setOverflowableBDNumerator(tmp);
		this.overflow.calculateBDOverflows();
				
		this.resultsForController.add("Possible base damage overflow: numerator = " + tmp);
		this.resultsForController.add("Numerator without overflow would be: " + bigTmp.toString());
		this.resultsForController.add(this.overflow.getBdOverflows()[0] + " overflows and " + this.overflow.getBdOverflows()[1] + " anti-overflows happened during base damage calculation.");
		this.resultsForController.add("Hence base damage will be " + (this.overflow.isBdPositive() ? "positive" : "negative") + ".");
		
		return divide(tmp, 16*512);
	}
	
	private String formatNumber(double num) {
		if(Math.floor(num) == num) {
			return new Integer((int)num).toString();
		}
		else {
			return new Double(num).toString();
		}
	}
	
	private void printModifiers() {
		this.resultsForController.add(" ");
		this.resultsForController.add("##### Begin modifiers #####");
		this.resultsForController.add("The attack is " + (this.criticalHit ? "" : "not ") + "a critical hit: critical multiplier = " + criticalMultiplier());
		this.resultsForController.add("The attacker is " + (this.attacker.isBerserked() ? "" : "not ") + "in berserk: berserk status multiplier = " + formatNumber(berserkMultiplier()));
		this.resultsForController.add("Long range materia check: " + this.attacker.toString() + (this.longRangeMateria ? " has" : " doesn't have") + " the Long Range Materia equipped.");
		this.resultsForController.add("Long range weapon check: " + this.attacker.getWeapon().toString() + " is " + (this.attacker.getWeapon().isLongRange() ? "" : "not ") + "a long range weapon.");
		this.resultsForController.add("Row checks: " + this.attacker.toString() + " is in " + (this.attacker.getRow().equals(Row.BACK) ? "back" : "front") + " row and the target is in " + (this.target.getRow().equals(Row.BACK) ? "back" : "front") + " row.");
		this.resultsForController.add("For these reasons, row multiplier = " + formatNumber(rowMultiplier()));
		this.resultsForController.add("The target is " + (this.target.isInDefenseMode() ? "" : "not ") + "in defense position: defense position multiplier = " + formatNumber(defenseStatusMultiplier()));
		this.resultsForController.add("The target is " + (this.target.isBackAttacked() ? "" : "not ") + "being attacked on his back: back attack multiplier = " + backMultiplier());
		this.resultsForController.add("The attacker is " + (this.attacker.isInFrog() ? "" : "not ") + "in frog status: frog status multiplier = " + formatNumber(frogMultiplier()));
		this.resultsForController.add("The target is " + (this.target.isInSadness() ? "" : "not ") + "in sadness status: sadness status multiplier = " + formatNumber(sadnessMultiplier()));
		this.resultsForController.add("The attack is direct towards " + (this.splitAttack ? "a single target " : "multiple targets ") + ": split multiplier = " + formatNumber(splitMultiplier()));
		this.resultsForController.add("The target is " + (this.target.isInBarrier() ? "" : "not ") + "in barrier status: barrier multiplier = " + formatNumber(barrierMultiplier()));
		this.resultsForController.add("The attacker is " + (this.attacker.isInMini() ? "" : "not ") + "in mini status: mini multiplier = " + miniMultiplier());
		this.resultsForController.add("##### End modifiers #####");
		this.resultsForController.add(" ");
	}
	
	private void printVarianceModification(int adjustedBaseDamage, int randomNumerator, int minNumerator, int maxNumerator, int numerator, BigInteger fullNumerator) {
		this.resultsForController.add("Base damage after modifiers and before random variance: " + adjustedBaseDamage);
		this.resultsForController.add("Random variance = " + randomNumerator + "/4096 = " + (double)randomNumerator/(double)4096);
		this.resultsForController.add("Possible random variance overflow: minimum numerator (if random variance numerator = 3841) = " + minNumerator);
		this.resultsForController.add("Possible random variance overflow: maximum numerator (if random variance numerator = 4096) = " + maxNumerator);
		this.resultsForController.add("Possible random variance overflow: actual numerator = " + numerator);
		this.resultsForController.add("The non-overflowable numerator would be = " + fullNumerator.toString());
		this.resultsForController.add("Base damage was " + (this.overflow.isBdPositive() ? "positive" : "negative") + ", so the first overflow which may happen here is " + (this.overflow.isBdPositive() ? "a classic overflow" : "an anti-overflow") + ".");
		
		if(this.overflow.isBdPositive()) {
			this.resultsForController.add(this.overflow.getPositiveBDrvOverflows()[0] + " overflows and " + this.overflow.getPositiveBDrvOverflows()[1] + " anti-overflows happened while applying random variance.");
		}
		else {
			this.resultsForController.add(this.overflow.getNegativeBDrvOverflows()[0] + " anti-overflows and " + this.overflow.getNegativeBDrvOverflows()[1] + " overflows happened while applying random variance.");
		}
		
		this.resultsForController.add("After all the possible overflows the final damage will be " + (this.overflow.isAfterRVDamagePositive() ? "positive" : "negative") + ".");
	}
	
	private int[] finalDamage(int bd) {
		printModifiers();
		
		int tmp1 = (int) Math.floor(bd * criticalMultiplier() * berserkMultiplier() * rowMultiplier() * defenseStatusMultiplier() * backMultiplier() * frogMultiplier() * sadnessMultiplier() * splitMultiplier() * barrierMultiplier() * miniMultiplier());
		
		if(this.attacker.isInMini()) {
			tmp1++;
		}
		
		int randomNumerator = randomNumerator();
		
		int minTmp2 = tmp1 * 3841;
		int maxTmp2 = tmp1 * 4096;
		int tmp2 = tmp1 * randomNumerator;
		
		this.overflow.setOverflowablePostRandomVarianceNumerator(tmp2);
		
		BigInteger bigTmp2 = BigInteger.valueOf(tmp1).multiply(BigInteger.valueOf(randomNumerator));
		
		this.overflow.setPostRandomVarianceNumerator(bigTmp2);
		this.overflow.calculateRVOverflows();

		printVarianceModification(tmp1, randomNumerator, minTmp2, maxTmp2, tmp2, bigTmp2);
		
		int minTmp3 = divide(minTmp2, 4096);
		int maxTmp3 = divide(maxTmp2, 4096);
		int tmp3 = divide(tmp2, 4096);
		
		return managePostVarianceMultipliers(minTmp3, maxTmp3, tmp3);
	}
	
	private int[] managePostVarianceMultipliers(int min, int max, int actual) {
		List<Double> postVarianceMultipliers = this.attacker.getWeapon().getAdditionalMultipliers();
		
		if(!postVarianceMultipliers.isEmpty()) {
			for(Double multiplier : postVarianceMultipliers) {
				this.resultsForController.add("Found post-variance multiplier in the weapon with value = " + formatNumber(multiplier.doubleValue()));
				
				min *= multiplier;
				max *= multiplier;
				actual *= multiplier;
			}
			
			min = (int) Math.floor(min);
			max = (int) Math.floor(max);
			actual = (int) Math.floor(actual);
		}
		else {
			this.resultsForController.add("There are no post-variance multipliers in the weapon.");
		}
		
		return manageElementalAffinities(min, max, actual);
	}
	
	private int[] manageElementalAffinities(int min, int max, int actual) {
		double targetElementalAffinityMultiplier = this.target.getElementalAffinities().get(this.attackElement);
		
		this.resultsForController.add("The target has " + (targetElementalAffinityMultiplier != 1 ? "" : "no particular ") + "elemental affinities with " + this.attackElement.toString() + " element: elemental multiplier = " + formatNumber(targetElementalAffinityMultiplier));
		
		return  new int[]{(int)(min * targetElementalAffinityMultiplier), (int)(max * targetElementalAffinityMultiplier), (int)(actual * targetElementalAffinityMultiplier)};
	}

	private String formatStat(String characterName, String weaponName, int stat) {
		if(stat == Utils.USELESS) {
			return "this value doesn't matter for " + characterName + " equipped with " + weaponName + ".";
		}
		
		return new Integer(stat).toString();
	}
	
	private void printStats() {
		String characterName = this.attacker.toString();
		String weaponName = this.attacker.getWeapon().toString();
		
		this.resultsForController.add("##### Begin attacker stats #####");
		this.resultsForController.add("Attacker: " + characterName);
		this.resultsForController.add("Weapon: " + weaponName);
		this.resultsForController.add("Level: " + formatStat(characterName, weaponName, this.attacker.getLevel()));
		this.resultsForController.add("Current HP: " + formatStat(characterName, weaponName, this.attacker.getCurrentHp()));
		this.resultsForController.add("Max HP: " + formatStat(characterName, weaponName, this.attacker.getMaxHp()));
		this.resultsForController.add("Current MP: " + formatStat(characterName, weaponName, this.attacker.getCurrentMp()));
		this.resultsForController.add("Max MP: " + formatStat(characterName, weaponName, this.attacker.getMaxMp()));
		this.resultsForController.add("Limit Level: " + formatStat(characterName, weaponName, this.attacker.getLimitLevel()));
		this.resultsForController.add("Limit Bar Slots: " + formatStat(characterName, weaponName, this.attacker.getLimitGauge()));
		this.resultsForController.add("Effective APs in the weapon: " + formatStat(characterName, weaponName, this.attacker.getWeaponAP()));
		this.resultsForController.add("Attack: " + formatStat(characterName, weaponName, this.attacker.getAttack()));
		this.resultsForController.add(this.heroDrinksNumber + " hero drinks used: attack boosted by " + (this.heroDrinksNumber != 4 ? 3*this.heroDrinksNumber*10 : 100) + "%");
		this.resultsForController.add("Boosted attack: " + formatStat(characterName, weaponName, this.attacker.getBoostedAttack()));
		this.resultsForController.add("Number of enemies killed: " + formatStat(characterName, weaponName, this.attacker.getKills()));
		this.resultsForController.add("Technique power: " + this.techniquePower);
		this.resultsForController.add("Attack element: " + this.attackElement.toString());
		this.resultsForController.add("##### End attacker stats #####");
		this.resultsForController.add(" ");
		this.resultsForController.add("##### Begin target stats #####");
		this.resultsForController.add("Target average level: " + formatStat(characterName, weaponName, this.target.getAverageLevel(characterName)));
		this.resultsForController.add("Target defense stat: " + this.target.getDefense());
		this.resultsForController.add("##### End target stats #####");
		this.resultsForController.add(" ");
	}
	
	private void printBaseMultipliers(int attackMultiplier, int defenseMultiplier, int powerMultiplier) {
		this.resultsForController.add("Attack multiplier = " + attackMultiplier);
		this.resultsForController.add("Defense multiplier = " + defenseMultiplier);
		this.resultsForController.add("Power multiplier = " + powerMultiplier + (this.limit ? ", which is weapon-independent because " + Utils.getLimitName(this.attacker.toString(), this.limitCode) + " is being used." : ""));
	}
	
	private void printFinalDamage(int[] finalDamage) {
		this.resultsForController.add(" ");
		this.resultsForController.add("Min final damage uncapped: " + finalDamage[0]);
		this.resultsForController.add("Min final damage capped (max 9999): " + Math.min(9999, finalDamage[0]));
		this.resultsForController.add("Max final damage uncapped: " + finalDamage[1]);
		this.resultsForController.add("Max final damage capped (max 9999): " + Math.min(9999, finalDamage[1]));
		this.resultsForController.add("Actual final damage uncapped: " + finalDamage[2]);
		this.resultsForController.add("Actual final damage capped (real damage, max 9999): " + Math.min(9999, finalDamage[2]));
		this.resultsForController.add("The final result is that the damage overflow glitch has " + (!this.overflow.isAfterRVDamagePositive() ? "" : "not ") + "been triggered.");
	}
	
	public void printHealingCheck() {
		boolean healingFlag = this.target.getElementalAbsorptions().get(this.attackElement);
		
		String result = !this.overflow.isAfterRVDamagePositive() ? healingFlag ? "fully healed" : "instantly killed" : healingFlag ? "healed by the capped final damage amount" : "damaged by the capped final damage amount";
		this.resultsForController.add("");
		this.resultsForController.add("Healing flag: " + (healingFlag ? "" : "not ") + "active. The target will be " + result + ".");
	}
	
	public void calculateDamage() {
		this.overflow = new Overflow();
		this.resultsForController = new ArrayList<String>();
		
		int modifiedAttack = (int)(this.attacker.getAttack() * (this.heroDrinksNumber != 4 ? 1 + ((double)(3*this.heroDrinksNumber))/10 : 2));
		this.attacker.setBoostedAttack(modifiedAttack);
		
		printStats();
		
		int attackMultiplier = attackMultiplier();
		int defenseMultiplier = defenseMultiplier();
		int powerMultiplier = powerMultiplier();
		
		printBaseMultipliers(attackMultiplier, defenseMultiplier, powerMultiplier);
		
		int baseDamage = baseDamage(powerMultiplier, defenseMultiplier, attackMultiplier);
		this.resultsForController.add("Base damage: " + baseDamage);
		
		int[] finalDamage = finalDamage(baseDamage);
		
		printFinalDamage(finalDamage);
		printHealingCheck();
		
		Event e = new Event(Events.RESULT_FOR_CONTROLLER, this.resultsForController);
		
		setChanged();
		notifyObservers(e);
	}

	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof Controller) {
			if(arg instanceof Event) {
				Event e = (Event) arg;
				
				if(e.getCode().equals(Events.DATA_FOR_MODEL)) {
					byte[] centerPanelData = (byte[]) (e.getParams()[0]);
					byte[] rightPanelData = (byte[]) (e.getParams()[1]);
					
					constructModel(centerPanelData, rightPanelData);
					calculateDamage();
				}
			}
		}
	}

	private void constructModel(byte[] centerPanelData, byte[] rightPanelData) {
		byte characterCode = centerPanelData[0];
		int level = Byte.valueOf(centerPanelData[1]).intValue();
		int strength = Byte.valueOf(centerPanelData[2]).intValue();
		byte weaponCode = centerPanelData[3];
		this.techniquePower = Byte.valueOf(centerPanelData[4]).intValue();
		this.attackElement = Utils.getElementFromCode(centerPanelData[5]);
		this.heroDrinksNumber = Byte.valueOf(centerPanelData[6]).intValue();
		this.criticalHit = centerPanelData[7] == (byte) 0x01;
		boolean berserk = centerPanelData[8] == (byte) 0x01;
		Row attackerRow = centerPanelData[9] == (byte) 0x00 ? Row.BACK : Row.FRONT;
		this.splitAttack = centerPanelData[10] == (byte) 0x01;
		boolean frog = centerPanelData[11] == (byte) 0x01;
		boolean mini = centerPanelData[12] == (byte) 0x01;
		
		this.limit = centerPanelData[13] != (byte) 0x00;
		this.limitCode = centerPanelData[13];
		
		List<byte[]> additional = manageAdditionalParameters(centerPanelData);
		
		this.longRangeMateria = additional.get(2)[0] == (byte) 0x01; 
		
		double[] postVarianceMultipliers = WeaponsFactory.getPostVarianceMultipliers(weaponCode, characterCode);
		WeaponInterface weapon = WeaponsFactory.createWeapon(weaponCode, characterCode, additional.get(0), postVarianceMultipliers);
		this.attacker = CharacterFactory.createCharacter(characterCode, level, weapon, strength, additional.get(0), additional.get(1), berserk, frog, mini, attackerRow);
	
		manageTarget(rightPanelData);
	}

	private void manageTarget(byte[] rightPanelData) {
		int targetAverageLevel = Byte.valueOf(rightPanelData[0]).intValue();
		int targetDefense = ByteBuffer.allocate(4).put(Arrays.copyOfRange(rightPanelData, 1, 5)).getInt(0);
		boolean targetDefend = rightPanelData[5] == (byte) 0x01;
		boolean targetSadness = rightPanelData[6] == (byte) 0x01;
		boolean targetBarrier = rightPanelData[7] == (byte) 0x01;
		boolean targetBack = rightPanelData[8] == (byte) 0x01;
		int targetBackMultiplier = Byte.valueOf(rightPanelData[9]).intValue();
		Row targetRow = rightPanelData[10] == (byte) 0x00 ? Row.BACK : Row.FRONT;
		boolean absorbs = rightPanelData[11] == (byte) 0x01;
		double elementMultiplier = Byte.valueOf(rightPanelData[12]).doubleValue();
		
		if(elementMultiplier == (double) 0x03) {
			elementMultiplier = 0.5;
		}
		
		Map<Elements, Double> affinities = new HashMap<Elements, Double>();
		affinities.put(this.attackElement, elementMultiplier);
		
		Map<Elements, Boolean> absorbsMap = new HashMap<Elements, Boolean>();
		absorbsMap.put(this.attackElement, absorbs);
		
		this.target = new Target(targetAverageLevel, targetDefense, targetRow, targetDefend, targetSadness, targetBarrier, targetBack, targetBackMultiplier, affinities, absorbsMap);
	}

	private List<byte[]> manageAdditionalParameters(byte[] centerPanelData) {
		int overhead = 0;
		
		List<byte[]> toReturn = new ArrayList<byte[]>();
		overhead = fetchParameter(centerPanelData, overhead, toReturn);
		overhead = fetchParameter(centerPanelData, overhead, toReturn);
		
		toReturn.add(new byte[]{centerPanelData[14 + overhead]});
		System.out.println(centerPanelData[14 + overhead]);
		
		return toReturn;
	}

	private int fetchParameter(byte[] centerPanelData, int overhead, List<byte[]> toReturn) {
		if(centerPanelData[14 + overhead] == (byte) 0x01) {
			int length = ByteBuffer.allocate(4).put(Arrays.copyOfRange(centerPanelData, 15 + overhead, 19 + overhead)).getInt(0);
			overhead += 5;
			toReturn.add(Arrays.copyOfRange(centerPanelData, 14 + overhead, 14 + overhead + length));
			overhead += length;
		}
		else {
			toReturn.add(null);
		}
		
		return overhead;
	}
}