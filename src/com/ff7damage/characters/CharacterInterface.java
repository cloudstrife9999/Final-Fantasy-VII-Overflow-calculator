package com.ff7damage.characters;

import com.ff7damage.Row;
import com.ff7damage.weapons.WeaponInterface;

public interface CharacterInterface {
	public int getCurrentHp();
	public int getMaxHp();
	public int getCurrentMp();
	public int getMaxMp();
	public int getLimitLevel();
	public int getLimitGauge();
	public int getLevel();
	public int getStrength();
	public WeaponInterface getWeapon();
	public int getAttack();
	public int getBoostedAttack();
	public void setBoostedAttack(int boostedAttack);
	public int getPowerModifier(int techniquePower, int... otherParameters);
	public int getKills();
	public int getWeaponAP();
	public boolean isBerserked();
	public boolean isInFrog();
	public boolean isInMini();
	public Row getRow();
}