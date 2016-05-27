package com.ff7damage.characters;

import com.ff7damage.Row;
import com.ff7damage.Utils;
import com.ff7damage.weapons.WeaponInterface;

public abstract class Character {
	private int level;
	private int strength;
	private WeaponInterface equippedWeapon;
	private int attack;
	private int boostedAttack;
	private boolean berserked;
	private boolean inFrog;
	private boolean inMini;
	private Row row;
	
	public Character(int level, WeaponInterface weapon, int strength, boolean berserked, boolean inFrog, boolean inMini, Row row) {
		this.level = Utils.sanitize(level, 1, 99, 99);
		this.strength = Utils.sanitize(strength, 1, 255, 255);
		this.equippedWeapon = weapon;
		this.attack = Utils.sanitize(this.strength + this.equippedWeapon.getAtkBonus(), 1, 255, 255);
		this.boostedAttack = this.attack;
		this.berserked = berserked;
		this.inFrog = inFrog;
		this.inMini = inMini;
		this.row = row;
	}
	
	protected int getLevel() {
		return this.level;
	}
	
	protected int getStrength() {
		return this.strength;
	}
	
	protected WeaponInterface getWeapon() {
		return this.equippedWeapon;
	}
	
	protected int getAttack() {
		return this.attack;
	}
	
	protected int getBoostedAttack() {
		return this.boostedAttack;
	}
	
	protected void setBoostedAttack(int boostedAttack) {
		this.boostedAttack = boostedAttack;
	}
	
	protected boolean isBerserked() {
		return this.berserked;
	}
	
	protected boolean isInFrog() {
		return this.inFrog;
	}
	
	protected boolean isInMini() {
		return this.inMini;
	}
	
	protected Row getRow() {
		return this.row;
	}
}
