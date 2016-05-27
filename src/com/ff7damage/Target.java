package com.ff7damage;

import java.util.Map;

public class Target {
	private int averageLevel;
	private int defense;
	private Row row;
	private boolean inDefenseMode;
	private boolean inSadness;
	private boolean inBarrier;
	private boolean backAttacked;
	private int theoreticalBackAttackMultiplier;
	private Map<Elements, Double> elementalAffinities; //1 = no affinities, 2 = weak, 0.5 = half, 0 = immune
	private Map<Elements, Boolean> elementalAbsorptions; //does target absorb elements?
	
	public Target(int averageLevel, int defense, Row row, boolean inDefenseMode, boolean inSadness, boolean inBarrier, boolean backAttacked,
			int backAttackMultiplier, Map<Elements, Double> elementalAffinities, Map<Elements, Boolean> elementalAbsorptions) {
		
		this.averageLevel = Utils.sanitize(averageLevel, 1, 99, 99);
		this.defense = defense;
		this.row = row;
		this.inDefenseMode = inDefenseMode;
		this.inSadness = inSadness;
		this.inBarrier = inBarrier;
		this.backAttacked = backAttacked;
		this.theoreticalBackAttackMultiplier = Utils.sanitize(backAttackMultiplier, 2, 8, 2);
		this.elementalAffinities = elementalAffinities;
		this.elementalAbsorptions = elementalAbsorptions;
	}
	
	public int getAverageLevel(String attacker) {
		return attacker.equals("Yuffie Kisaragi") ? this.averageLevel : Utils.USELESS;
	}
	
	public int getDefense() {
		return this.defense;
	}
	
	public Row getRow() {
		return this.row;
	}
	
	public boolean isInDefenseMode() {
		return this.inDefenseMode;
	}
	
	public boolean isInSadness() {
		return this.inSadness;
	}
	
	public boolean isInBarrier() {
		return this.inBarrier;
	}
	
	public boolean isBackAttacked() {
		return this.backAttacked;
	}
	
	public int getTheoreticalBackAttackMultiplier() {
		return this.theoreticalBackAttackMultiplier;
	}
	
	public Map<Elements, Double> getElementalAffinities() {
		return this.elementalAffinities;
	}
	
	public Map<Elements, Boolean> getElementalAbsorptions() {
		return this.elementalAbsorptions;
	}
}