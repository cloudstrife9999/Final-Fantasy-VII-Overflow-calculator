package com.ff7damage.characters;

import com.ff7damage.Row;
import com.ff7damage.Utils;
import com.ff7damage.weapons.WeaponInterface;

public class Aerith extends Character implements CharacterInterface {

	public Aerith(int level, WeaponInterface weapon, int strength, boolean berserked, boolean inFrog, boolean inMini, Row row) {
		super(level, weapon, strength, berserked, inFrog, inMini, row);
	}

	@Override
	public int getLevel() {
		return super.getLevel();
	}
	
	@Override
	public int getStrength() {
		return super.getStrength();
	}
	
	@Override
	public WeaponInterface getWeapon() {
		return super.getWeapon();
	}
	
	@Override
	public int getAttack() {
		return super.getAttack();
	}
	
	@Override
	public int getBoostedAttack() {
		return super.getBoostedAttack();
	}
	
	@Override
	public void setBoostedAttack(int boostedAttack) {
		super.setBoostedAttack(boostedAttack);
	}

	@Override
	public int getKills() {
		return Utils.USELESS;
	}
	
	@Override
	public int getPowerModifier(int techniquePower, int... otherParameters) {
		return super.getWeapon().getPowerModifier(techniquePower, otherParameters);
	}

	@Override
	public int getCurrentHp() {
		return Utils.USELESS;
	}

	@Override
	public int getMaxHp() {
		return Utils.USELESS;
	}
	
	@Override
	public int getCurrentMp() {
		return Utils.USELESS;
	}
	
	@Override
	public int getMaxMp() {
		return Utils.USELESS;
	}

	@Override
	public int getLimitLevel() {
		return Utils.USELESS;
	}

	@Override
	public int getLimitGauge() {
		return Utils.USELESS;
	}
	
	@Override
	public int getWeaponAP() {
		return Utils.USELESS;
	}
	
	@Override
	public String toString() {
		return "Aerith Gainsborough";
	}
	
	@Override
	public boolean isBerserked() {
		return super.isBerserked();
	}
	
	@Override
	public boolean isInMini() {
		return super.isInMini();
	}
	
	@Override
	public boolean isInFrog() {
		return super.isInFrog();
	}
	
	@Override
	public Row getRow() {
		return super.getRow();
	}
}