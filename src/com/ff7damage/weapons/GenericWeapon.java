package com.ff7damage.weapons;

import java.util.List;

import com.ff7damage.Utils;

public class GenericWeapon extends Weapon implements WeaponInterface {
	private String name;
	
	public GenericWeapon(int atkBonus, String name, boolean longRange) {
		super(atkBonus, 0, longRange);
		this.name = name;
	}

	@Override
	public int getPowerModifier(int techniquePower, int... otherParameters) {
		return techniquePower;
	}

	@Override
	public int getAtkBonus() {
		return super.getAtkBonus();
	}
	
	@Override
	public int getAp() {
		return Utils.USELESS;
	}
	
	@Override
	public List<Double> getAdditionalMultipliers() {
		return super.getAdditionalMultipliers();
	}
	
	@Override
	public boolean isLongRange() {
		return super.isLongRange();
	}
	
	@Override
	public String toString() {
		return this.name;
	}
}