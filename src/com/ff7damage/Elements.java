package com.ff7damage;

public enum Elements {
	NON_ELEMENTAL {
		@Override
		public String toString() {
			return "NON-ELEMENTAL";
		}
	},
	FIRE,
	ICE,
	LIGHTNING,
	EARTH,
	WIND,
	WATER,
	POISON,
	HOLY,
	GRAVITY,
	RESTORATIVE,
	CUT,
	HIT,
	PUNCH,
	SHOOT,
	SHOUT,
	HIDDEN
	}