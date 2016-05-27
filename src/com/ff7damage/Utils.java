package com.ff7damage;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;

public class Utils {
	public static final int USELESS = -1;
	
	public static int sanitize(int value, int min, int max, int def) {
		if(value < min || value > max) {
			return def;
		}
		
		return value;
	}
	
	public static Map<Elements, Integer> createNoElementalAffinitiesMap() {
		Map<Elements, Integer> map = new HashMap<Elements, Integer>();
		
		for(Elements e : Elements.values()) {
			map.put(e, 1);
		}
		
		return map;
	}
	
	public static Map<Elements, Boolean> createNoElementalAbsorptionsMap() {
		Map<Elements, Boolean> map = new HashMap<Elements, Boolean>();
		
		for(Elements e : Elements.values()) {
			map.put(e, false);
		}
		
		return map;
	}
	
	public static JLabel drawHeader(String message) {
		JLabel header = new JLabel(message);
		header.setForeground(Color.BLUE);
		header.setHorizontalAlignment(JLabel.CENTER);
		
		return header;
	}
}