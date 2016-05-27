package com.ff7damage.weapons;

import java.util.List;

import com.ff7damage.Utils;

public class PrincessGuard extends Weapon implements WeaponInterface {

	public PrincessGuard() {
		super(52, 0, false);
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
	public void addAdditionalMultipliers(double deadCharacters) {
		double multiplier = 2 * deadCharacters;
		
		super.addAdditionalMultipliers(multiplier);
	}
	
	@Override
	public boolean isLongRange() {
		return super.isLongRange();
	}
	
	@Override
	public String toString() {
		return "Princess Guard";
	}
}