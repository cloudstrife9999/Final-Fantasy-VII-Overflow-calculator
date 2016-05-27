package com.ff7damage.weapons;

import java.util.List;

import com.ff7damage.Utils;

public class DeathPenalty extends Weapon implements WeaponInterface {
	public DeathPenalty() {
		super(99, 0, true);
	}

	@Override
	public int getPowerModifier(int techniquePower, int... otherParameters) {
		int kills = Utils.sanitize(otherParameters[0], 0, 65535, 0);
		return ((techniquePower * (kills >> 7)) >> 4) + 10;
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
		return "Death Penalty";
	}
}