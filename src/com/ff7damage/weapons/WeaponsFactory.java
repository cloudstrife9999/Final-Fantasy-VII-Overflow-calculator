package com.ff7damage.weapons;

import java.nio.ByteBuffer;

public class WeaponsFactory {
	public static WeaponInterface createWeapon(boolean ultimate, byte characterCode, byte[] contextualParam, byte[] atk) {
		if(!ultimate) {
			int atkBonus = ByteBuffer.allocate(4).put(atk).getInt(0);
			return new GenericWeapon(atkBonus, "a generic weapon", false); //TODO long range
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
}