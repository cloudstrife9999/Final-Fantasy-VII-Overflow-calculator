package com.ff7damage;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
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
	private Random random;
	private Overflow overflow;
	
	private byte[] resultsForController;
	
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
		if(this.attacker.getWeapon().isLongRange()) {
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
				
		System.out.println("Possible base damage overflow: numerator = " + tmp);
		System.out.println("Numerator without overflow would be: " + bigTmp.toString());
		System.out.println(this.overflow.getBdOverflows()[0] + " overflows and " + this.overflow.getBdOverflows()[1] + " anti-overflows happened during base damage calculation.");
		System.out.println("Hence base damage will be " + (this.overflow.isBdPositive() ? "positive" : "negative") + ".");
		
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
		System.out.println("\n##### Begin modifiers #####");
		System.out.println("The attack is " + (this.criticalHit ? "" : "not ") + "a critical hit: critical multiplier = " + criticalMultiplier());
		System.out.println("The attacker is " + (this.attacker.isBerserked() ? "" : "not ") + "in berserk: berserk status multiplier = " + formatNumber(berserkMultiplier()));
		System.out.println("The attacker is in " + (this.attacker.getRow().equals(Row.BACK) ? "back" : "front") + " row and the target is in " + (this.target.getRow().equals(Row.BACK) ? "back" : "front") + " row: row multiplier = " + formatNumber(rowMultiplier()));
		System.out.println("The target is " + (this.target.isInDefenseMode() ? "" : "not ") + "in defense position: defense position multiplier = " + formatNumber(defenseStatusMultiplier()));
		System.out.println("The target is " + (this.target.isBackAttacked() ? "" : "not ") + "being attacked on his back: back attack multiplier = " + backMultiplier());
		System.out.println("The attacker is " + (this.attacker.isInFrog() ? "" : "not ") + "in frog status: frog status multiplier = " + formatNumber(frogMultiplier()));
		System.out.println("The target is " + (this.target.isInSadness() ? "" : "not ") + "in sadness status: sadness status multiplier = " + formatNumber(sadnessMultiplier()));
		System.out.println("The attack is direct towards " + (this.splitAttack ? "a single target " : "multiple targets ") + ": split multiplier = " + formatNumber(splitMultiplier()));
		System.out.println("The target is " + (this.target.isInBarrier() ? "" : "not ") + "in barrier status: barrier multiplier = " + formatNumber(barrierMultiplier()));
		System.out.println("The attacker is " + (this.attacker.isInMini() ? "" : "not ") + "in mini status: mini multiplier = " + miniMultiplier());
		System.out.println("##### End modifiers #####\n");
	}
	
	private void printVarianceModification(int adjustedBaseDamage, int randomNumerator, int minNumerator, int maxNumerator, int numerator, BigInteger fullNumerator) {
		System.out.println("Base damage after modifiers and before random variance: " + adjustedBaseDamage);
		System.out.println("Random variance = " + randomNumerator + "/4096 = " + (double)randomNumerator/(double)4096);
		System.out.println("Possible random variance overflow: minimum numerator (if random variance numerator = 3841) = " + minNumerator);
		System.out.println("Possible random variance overflow: maximum numerator (if random variance numerator = 4096) = " + maxNumerator);
		System.out.println("Possible random variance overflow: actual numerator = " + numerator);
		System.out.println("The non-overflowable numerator would be = " + fullNumerator.toString());
		System.out.println("Base damage was " + (this.overflow.isBdPositive() ? "positive" : "negative") + ", so the first overflow which may happen here is " + (this.overflow.isBdPositive() ? "a classic overflow" : "an anti-overflow") + ".");
		
		if(this.overflow.isBdPositive()) {
			System.out.println(this.overflow.getPositiveBDrvOverflows()[0] + " overflows and " + this.overflow.getPositiveBDrvOverflows()[1] + " anti-overflows happened while applying random variance.");
		}
		else {
			System.out.println(this.overflow.getNegativeBDrvOverflows()[0] + " anti-overflows and " + this.overflow.getNegativeBDrvOverflows()[1] + " overflows happened while applying random variance.");
		}
		
		System.out.println("After all the possible overflows the final damage will be " + (this.overflow.isAfterRVDamagePositive() ? "positive" : "negative") + ".");
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
				System.out.println("Found post-variance multiplier in the weapon with value = " + formatNumber(multiplier.doubleValue()));
				
				min *= multiplier;
				max *= multiplier;
				actual *= multiplier;
			}
			
			min = (int) Math.floor(min);
			max = (int) Math.floor(max);
			actual = (int) Math.floor(actual);
		}
		else {
			System.out.println("There are no post-variance multipliers in the weapon.");
		}
		
		return manageElementalAffinities(min, max, actual);
	}
	
	private int[] manageElementalAffinities(int min, int max, int actual) {
		double targetElementalAffinityMultiplier = this.target.getElementalAffinities().get(this.attackElement);
		
		System.out.println("The target has " + (targetElementalAffinityMultiplier != 1 ? "" : "no particular ") + "elemental affinities with " + this.attackElement.toString() + " element: elemental multiplier = " + formatNumber(targetElementalAffinityMultiplier));
		
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
		
		System.out.println("##### Begin attacker stats #####");
		System.out.println("Attacker: " + characterName);
		System.out.println("Weapon: " + weaponName);
		System.out.println("Level: " + formatStat(characterName, weaponName, this.attacker.getLevel()));
		System.out.println("Current HP: " + formatStat(characterName, weaponName, this.attacker.getCurrentHp()));
		System.out.println("Max HP: " + formatStat(characterName, weaponName, this.attacker.getMaxHp()));
		System.out.println("Current MP: " + formatStat(characterName, weaponName, this.attacker.getCurrentMp()));
		System.out.println("Max MP: " + formatStat(characterName, weaponName, this.attacker.getMaxMp()));
		System.out.println("Limit Level: " + formatStat(characterName, weaponName, this.attacker.getLimitLevel()));
		System.out.println("Limit Bar Slots: " + formatStat(characterName, weaponName, this.attacker.getLimitGauge()));
		System.out.println("Effective APs in the weapon: " + formatStat(characterName, weaponName, this.attacker.getWeaponAP()));
		System.out.println("Attack: " + formatStat(characterName, weaponName, this.attacker.getAttack()));
		System.out.println(this.heroDrinksNumber + " hero drinks used: attack boosted by " + (this.heroDrinksNumber != 4 ? 3*this.heroDrinksNumber*10 : 100) + "%");
		System.out.println("Boosted attack: " + formatStat(characterName, weaponName, this.attacker.getBoostedAttack()));
		System.out.println("Number of enemies killed: " + formatStat(characterName, weaponName, this.attacker.getKills()));
		System.out.println("Technique power: " + this.techniquePower);
		System.out.println("Attack element: " + this.attackElement.toString());
		System.out.println("##### End attacker stats #####\n");
		System.out.println("##### Begin target stats #####");
		System.out.println("Target average level: " + formatStat(characterName, weaponName, this.target.getAverageLevel(characterName)));
		System.out.println("Target defense stat: " + this.target.getDefense());
		System.out.println("##### End target stats #####\n");
	}
	
	private void printBaseMultipliers(int attackMultiplier, int defenseMultiplier, int powerMultiplier) {
		System.out.println("Attack multiplier = " + attackMultiplier);
		System.out.println("Defense multiplier = " + defenseMultiplier);
		System.out.println("Power multiplier = " + powerMultiplier);
	}
	
	private void printFinalDamage(int[] finalDamage) {
		System.out.println("\nMin final damage uncapped: " + finalDamage[0]);
		System.out.println("Min final damage capped (max 9999): " + Math.min(9999, finalDamage[0]));
		System.out.println("Max final damage uncapped: " + finalDamage[1]);
		System.out.println("Max final damage capped (max 9999): " + Math.min(9999, finalDamage[1]));
		System.out.println("Actual final damage uncapped: " + finalDamage[2]);
		System.out.println("Actual final damage capped (real damage, max 9999): " + Math.min(9999, finalDamage[2]));
		System.out.println("The final result is that the damage overflow glitch has " + (!this.overflow.isAfterRVDamagePositive() ? "" : "not ") + "been triggered.");
	}
	
	public void printHealingCheck() {
		boolean healingFlag = this.target.getElementalAbsorptions().get(this.attackElement);
		
		String result = !this.overflow.isAfterRVDamagePositive() ? healingFlag ? "fully healed" : "instantly killed" : healingFlag ? "healed by the capped final damage amount" : "damaged by the capped final damage amount";
		System.out.println("\nHealing flag: " + (healingFlag ? "" : "not ") + "active. The target will be " + result + ".");
	}
	
	public void calculateDamage() {
		this.overflow = new Overflow();
		
		int modifiedAttack = (int)(this.attacker.getAttack() * (this.heroDrinksNumber != 4 ? 1 + ((double)(3*this.heroDrinksNumber))/10 : 2));
		this.attacker.setBoostedAttack(modifiedAttack);
		
		printStats();
		
		int attackMultiplier = attackMultiplier();
		int defenseMultiplier = defenseMultiplier();
		int powerMultiplier = powerMultiplier();
		
		printBaseMultipliers(attackMultiplier, defenseMultiplier, powerMultiplier);
		
		int baseDamage = baseDamage(powerMultiplier, defenseMultiplier, attackMultiplier);
		System.out.println("Base damage: " + baseDamage);
		
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
		boolean ultimateWeapon = centerPanelData[3] == (byte) 0x00;
		this.techniquePower = Byte.valueOf(centerPanelData[4]).intValue();
		this.attackElement = Utils.elements.get(centerPanelData[5]);
		this.heroDrinksNumber = Byte.valueOf(centerPanelData[6]).intValue();
		this.criticalHit = centerPanelData[7] == (byte) 0x01;
		boolean berserk = centerPanelData[8] == (byte) 0x01;
		Row attackerRow = centerPanelData[9] == (byte) 0x00 ? Row.BACK : Row.FRONT;
		this.splitAttack = centerPanelData[10] == (byte) 0x01;
		boolean frog = centerPanelData[11] == (byte) 0x01;
		boolean mini = centerPanelData[12] == (byte) 0x01;
		
		int overhead = 0;
		byte[] first = null;
		byte[] second = null;
		byte[] third = null;
		
		if(centerPanelData[13] == (byte) 0x01) {
			int firstLength = ByteBuffer.allocate(4).put(Arrays.copyOfRange(centerPanelData, 14 + overhead, 18 + overhead)).getInt(0);
			overhead += 5;
			first = Arrays.copyOfRange(centerPanelData, 13 + overhead, 13 + overhead + firstLength);
			overhead += firstLength;
		}
		
		if(centerPanelData[13 + overhead] == (byte) 0x01) {
			int secondLength = ByteBuffer.allocate(4).put(Arrays.copyOfRange(centerPanelData, 14 + overhead, 18 + overhead)).getInt(0);
			overhead += 5;
			second = Arrays.copyOfRange(centerPanelData, 13 + overhead, 13 + overhead + secondLength);
			overhead += secondLength;
		}
		
		if(centerPanelData[13 + overhead] == (byte) 0x01) {
			int thirdLength = ByteBuffer.allocate(4).put(Arrays.copyOfRange(centerPanelData, 14 + overhead, 18 + overhead)).getInt(0);
			overhead += 5;
			third = Arrays.copyOfRange(centerPanelData, 13 + overhead, 13 + overhead + thirdLength);
		}
		
		WeaponInterface weapon = WeaponsFactory.createWeapon(ultimateWeapon, characterCode, first, third);
		this.attacker = CharacterFactory.createCharacter(characterCode, level, weapon, strength, first, second, berserk, frog, mini, attackerRow);
	
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
}