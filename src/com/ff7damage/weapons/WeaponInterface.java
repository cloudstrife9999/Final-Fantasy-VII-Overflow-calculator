package com.ff7damage.weapons;

import java.util.List;

public interface WeaponInterface {
	public int getAtkBonus();
	public int getPowerModifier(int techniquePower, int... otherParameters);
	public int getAp();
	public List<Double> getAdditionalMultipliers();
	public boolean isLongRange();
}
