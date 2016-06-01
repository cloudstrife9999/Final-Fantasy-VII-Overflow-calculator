package com.ff7damage.view;

public class RedXIII extends Wrapper {

	@Override
	public void accept(VisitorInterface visitor) {
		visitor.visit(this);
	}
	
	@Override
	public String[] getWeapons() {
		return new String[]{"Limited Moon", "Mythril Clip", "Diamond Pin", "Silver Barrette", "Gold Barrette", "Adaman Clip", "Crystal Comb",
				"Magic Comb", "Plus Barrette", "Cent Clip", "Hairpin", "Seraph Comb", "Behemoth Horn", "Spring Gun Clip"};
	}
}