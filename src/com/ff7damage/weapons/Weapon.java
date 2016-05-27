package com.ff7damage.weapons;

import java.util.ArrayList;
import java.util.List;

import com.ff7damage.Utils;

public abstract class Weapon {
	private int atkBonus;
	private int ap;
	private List<Double> additionalMultipliers;
	private boolean longRange;
	
	public Weapon(int atkBonus, int ap, boolean longRange) {
		this.atkBonus = Utils.sanitize(atkBonus, 0, 255, 0);
		this.ap = Utils.sanitize(ap, 0, 4000000, 0);
		this.additionalMultipliers = new ArrayList<Double>();
		this.longRange = longRange;
	}
	
	protected int getAtkBonus() {
		return this.atkBonus;
	}
	
	protected int getAp() {
		return this.ap;
	}
	
	protected List<Double> getAdditionalMultipliers() {
		return this.additionalMultipliers;
	}
	
	protected void addAdditionalMultipliers(double multiplier) {
		this.additionalMultipliers.add(multiplier);
	}
	
	protected boolean isLongRange() {
		return this.longRange;
	}
	
	@Override
	public String toString() {
		return "A random weapon with no power modification properties";
	}
}