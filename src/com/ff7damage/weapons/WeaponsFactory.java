package com.ff7damage.weapons;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import com.ff7damage.view.Aerith;
import com.ff7damage.view.Barret;
import com.ff7damage.view.CaitSith;
import com.ff7damage.view.Cid;
import com.ff7damage.view.Cloud;
import com.ff7damage.view.RedXIII;
import com.ff7damage.view.Tifa;
import com.ff7damage.view.Vincent;
import com.ff7damage.view.Yuffie;

public class WeaponsFactory {
	public static WeaponInterface createWeapon(byte weaponCode, byte characterCode, byte[] contextualParam, double[] multipliers) {
		boolean ultimate = weaponCode == (byte) 0x00;
		
		if(!ultimate) {
			return createNonUltimateWeapon(weaponCode, characterCode, multipliers);
		}
		
		switch(characterCode) {
		case 0x00:
		{
			return new UltimaWeapon();
		}
		case 0x01:
		{
			int apClassInt = ByteBuffer.allocate(4).put(contextualParam).getInt(0);
			int ap = apClassInt * 10000;
			return new MissingScore(ap);
		}
		case 0x02:
		{
			return new PremiumHeart();
		}
		case 0x03:
		{
			PrincessGuard princessGuard = new PrincessGuard();
			int deadCharacters = ByteBuffer.allocate(4).put(contextualParam).getInt(0);
			princessGuard.addAdditionalMultipliers(deadCharacters);
			
			return princessGuard;
		}
		case 0x04:
		{
			return new LimitedMoon();
		}
		case 0x05:
		{
			return new Conformer();
		}
		case 0x06:
		{
			return new HPShout();
		}
		case 0x07:
		{
			return new DeathPenalty();
		}
		case 0x08:
		{
			return new VenusGospel();
		}
		default:
		{
			return new UltimaWeapon();
		}
		}
	}
	
	private static WeaponInterface createNonUltimateWeapon(byte weaponCode, byte characterCode, double[] multipliers) {
		String weaponName = getWeaponName(characterCode, weaponCode);
		int atkBonus = getWeaponAttackBonus(weaponName);
		
		boolean longRangeWeapon = isLongRangeWeapon(weaponCode, characterCode);
		GenericWeapon weapon = new GenericWeapon(atkBonus, weaponName, longRangeWeapon);
		
		for(double multiplier : multipliers) {
			weapon.addAdditionalMultipliers(multiplier);
		}
		
		return weapon;
	}

	private static boolean isLongRangeWeapon(byte weaponCode, byte characterCode) {
		if(characterCode == (byte) 0x05 | characterCode == (byte) 0x07) {
			return true;
		}
		else if(characterCode == (byte) 0x01) {
			if(weaponCode == (byte) 0x03 || weaponCode == (byte) 0x04 || weaponCode == (byte) 0x06 || weaponCode == (byte) 0x0A ||
					weaponCode == (byte) 0x0C || weaponCode == (byte) 0x0E) {
				
				return false;
			}
			else {
				return true;
			}
		}
		else if(characterCode == (byte) 0x04 && weaponCode == (byte) 0x0A) {
			return true;
		}
		else {
			return false;
		}
	}

	private static String getWeaponName(byte characterCode, byte weaponCode) {
		switch(characterCode) {
		case 0x00:
		{
			return new Cloud().getWeapons()[Byte.valueOf(weaponCode).intValue()];
		}
		case 0x01:
		{
			return new Barret().getWeapons()[Byte.valueOf(weaponCode).intValue()];
		}
		case 0x02:
		{
			return new Tifa().getWeapons()[Byte.valueOf(weaponCode).intValue()];
		}
		case 0x03:
		{
			return new Aerith().getWeapons()[Byte.valueOf(weaponCode).intValue()];
		}
		case 0x04:
		{
			return new RedXIII().getWeapons()[Byte.valueOf(weaponCode).intValue()];
		}
		case 0x05:
		{
			return new Yuffie().getWeapons()[Byte.valueOf(weaponCode).intValue()];
		}
		case 0x06:
		{
			return new CaitSith().getWeapons()[Byte.valueOf(weaponCode).intValue()];
		}
		case 0x07:
		{
			return new Vincent().getWeapons()[Byte.valueOf(weaponCode).intValue()];
		}
		case 0x08:
		{
			return new Cid().getWeapons()[Byte.valueOf(weaponCode).intValue()];
		}
		default:
		{
			return "an unknown weapon";
		}
		}
	}

	private static final Map<String, Integer> weaponsAttackBonuses = new HashMap<String, Integer>() {
		private static final long serialVersionUID = 4928418496175125736L;

		{
			put("Buster Sword", 18);
			put("Mythril Saber", 23);
			put("Hardedge", 32);
			put("Butterfly Edge", 39);
			put("Enhance Sword", 43);
			put("Organics", 62);
			put("Crystal Sword", 76);
			put("Force Stealer", 36);
			put("Rune Blade", 40);
			put("Murasame", 51);
			put("Nail Bat", 70);
			put("Yoshiyuki", 56);
			put("Apocalypse", 88);
			put("Heaven's Cloud", 93);
			put("Ragnarok", 97);
			put("Gatling Gun", 14);
			put("Assault Gun", 17);
			put("Cannon Ball", 23);
			put("Atomic Scissors", 32);
			put("Heavy Vulcan", 39);
			put("Chainsaw", 52);
			put("Microlaser", 63);
			put("A∙M Cannon", 77);
			put("W Machine Gun", 30);
			put("Drill Arm", 37);
			put("Solid Bazooka", 61);
			put("Rocket Punch", 62);
			put("Enemy Launcher", 35);
			put("Pile Banger", 90);
			put("Max Ray", 97);
			put("Leather Glove", 13);
			put("Metal Knuckle", 18);
			put("Mythril Claw", 24);
			put("Grand Glove", 31);
			put("Tiger Fang", 38);
			put("Diamond Knuckle", 51);
			put("Dragon Claw", 62);
			put("Crystal Glove", 75);
			put("Motor Drive", 27);
			put("Platinum Fist", 30);
			put("Kaiser Knuckle", 44);
			put("Work Glove", 68);
			put("Powersoul", 28);
			put("Master Fist", 38);
			put("God's Hand", 86);
			put("Guard Stick", 12);
			put("Mythril Rod", 16);
			put("Full Metal Staff", 22);
			put("Striking Staff", 32);
			put("Prism Staff", 40);
			put("Aurora Rod", 51);
			put("Wizard Staff", 28);
			put("Wizer Staff", 33);
			put("Fairy Tale", 37);
			put("Umbrella", 58);
			put("Mythril Clip", 24);
			put("Diamond Pin", 33);
			put("Silver Barrette", 40);
			put("Gold Barrette", 50);
			put("Adaman Clip", 60);
			put("Crystal Comb", 76);
			put("Magic Comb", 37);
			put("Plus Barrette", 39);
			put("Cent Clip", 58);
			put("Hairpin", 57);
			put("Seraph Comb", 68);
			put("Behemoth Horn", 91);
			put("Spring Gun Clip", 87);
			put("4-point Shuriken", 23);
			put("Boomerang", 30);
			put("Pinweel", 37);
			put("Razor Ring", 49);
			put("Hawkeye", 61);
			put("Crystal Cross", 74);
			put("Wind Slash", 30);
			put("Twin Viper", 36);
			put("Spiral Shuriken", 68);
			put("Superball", 68);
			put("Magic Shuriken", 64);
			put("Rising Sun", 68);
			put("Oritsuru", 90);
			put("Yellow M-phone", 36);
			put("Green M-phone", 41);
			put("Blue M-phone", 48);
			put("Red M-phone", 60);
			put("Crystal M-phone", 74);
			put("White M-phone", 35);
			put("Black M-phone", 31);
			put("Silver M-phone", 28);
			put("Trumpet Shell", 68);
			put("Gold M-phone", 58);
			put("Battle Trumpet", 95);
			put("Starlight Phone", 88);
			put("Quicksilver", 36);
			put("Shotgun", 48);
			put("Shortbarrel", 51);
			put("Lariat", 64);
			put("Winchester", 73);
			put("Peacemaker", 38);
			put("Buntline", 48);
			put("Long Barrel R", 66);
			put("Silver Rifle", 62);
			put("Sniper CR", 42);
			put("Supershot ST", 97);
			put("Outsider", 80);
			put("Spear", 44);
			put("Slash Lance", 56);
			put("Trident", 60);
			put("Mast Ax", 64);
			put("Partisan", 78);
			put("Viper Halberd", 58);
			put("Javelin", 62);
			put("Grow Lance", 78);
			put("Mop", 68);
			put("Dragoon Lance", 66);
			put("Scimitar", 86);
			put("Flayer", 100);
			put("Spirit Lance", 92);
		}
	};
	
	private static int getWeaponAttackBonus(String weaponName) {
		return weaponsAttackBonuses.get(weaponName);
	}

	public static double[] getPostVarianceMultipliers(byte weaponCode, byte characterCode, Object... additional) {
		String weaponName = getWeaponName(characterCode, weaponCode);
		
		switch(weaponName) {
		case "Yoshiyuki":
		{
			int deadCharacters = Byte.valueOf((byte) additional[0]).intValue();
			return new double[] {1 + deadCharacters};
		}
		case "Powersoul":
		{
			boolean nearDeath = (boolean) additional[7];
			boolean deathSentence = (boolean) additional[0];
			
			int length = 0;
			
			length += nearDeath ? 1 : 0;
			length += deathSentence ? 1 : 0;
			
			double[] toReturn = new double[length == 0 ? 1 : length];
			
			if(toReturn.length == 1 && nearDeath) {
				toReturn[0] = 2;
			}
			else if(toReturn.length == 1 && deathSentence) {
				toReturn[0] = 4;
			}
			else if(toReturn.length == 1) {
				toReturn[0] = 1;
			}
			else if(toReturn.length == 2) {
				toReturn[0] = 2;
				toReturn[1] = 4;
			}
			else {
				return new double[]{};
			}
			
			return toReturn;
		}
		case "Master Fist":
		{
			boolean deathSentence = (boolean) additional[0];
			boolean poison = (boolean) additional[1];
			boolean sadness = (boolean) additional[2];
			boolean silence = (boolean) additional[3];
			boolean slow = (boolean) additional[4];
			boolean darkness = (boolean) additional[5];
			boolean slowNumb = (boolean) additional[6];
			boolean nearDeath = (boolean) additional[7];
			
			double multiplier = 1 + (nearDeath ? 1 : 0) + (poison ? 1 : 0) + (sadness ? 1 : 0) + (silence ? 1 : 0) + (slow ? 1 : 0) +
					(darkness ? 1 : 0) + (slowNumb ? 2 : 0) + (deathSentence ? 2 : 0);
			
			return new double[]{multiplier}; 
		}
		default:
		{
			return new double[]{};
		}
		}
	}
}