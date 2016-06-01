package com.ff7damage;

import java.awt.Color;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;

import com.ff7damage.view.Aerith;
import com.ff7damage.view.Barret;
import com.ff7damage.view.CaitSith;
import com.ff7damage.view.Cid;
import com.ff7damage.view.Cloud;
import com.ff7damage.view.RedXIII;
import com.ff7damage.view.Tifa;
import com.ff7damage.view.Vincent;
import com.ff7damage.view.WrapperInterface;
import com.ff7damage.view.Yuffie;

public class Utils {
	public static final int USELESS = -1;
	
	private static final Map<Byte, Elements> elements = new HashMap<Byte, Elements>() {
		private static final long serialVersionUID = 2175508689893935234L;

		{
			put((byte) 0x00, Elements.FIRE);
			put((byte) 0x01, Elements.ICE);
			put((byte) 0x02, Elements.LIGHTNING);
			put((byte) 0x03, Elements.EARTH);
			put((byte) 0x04, Elements.WIND);
			put((byte) 0x05, Elements.WATER);
			put((byte) 0x06, Elements.POISON);
			put((byte) 0x07, Elements.HOLY);
			put((byte) 0x08, Elements.GRAVITY);
			put((byte) 0x09, Elements.RESTORATIVE);
			put((byte) 0x0A, Elements.CUT);
			put((byte) 0x0B, Elements.HIT);
			put((byte) 0x0C, Elements.PUNCH);
			put((byte) 0x0D, Elements.SHOOT);
			put((byte) 0x0E, Elements.SHOUT);
			put((byte) 0x0F, Elements.HIDDEN);
			put((byte) 0x10, Elements.NON_ELEMENTAL);
		}
	};
	
	public static Elements getElementFromCode(byte code) {
		return elements.get(code);
	}
	
	private static final Map<Elements, Byte> elementalCodes = new HashMap<Elements, Byte>() {
		private static final long serialVersionUID = -5952882091693666245L;
		
		{
			put(Elements.FIRE, (byte) 0x00);
			put(Elements.ICE, (byte) 0x01);
			put(Elements.LIGHTNING, (byte) 0x02);
			put(Elements.EARTH, (byte) 0x03);
			put(Elements.WIND, (byte) 0x04);
			put(Elements.WATER, (byte) 0x05);
			put(Elements.POISON, (byte) 0x06);
			put(Elements.HOLY, (byte) 0x07);
			put(Elements.GRAVITY, (byte) 0x08);
			put(Elements.RESTORATIVE, (byte) 0x09);
			put(Elements.CUT, (byte) 0x0A);
			put(Elements.HIT, (byte) 0x0B);
			put(Elements.PUNCH, (byte) 0x0C);
			put(Elements.SHOOT, (byte) 0x0D);
			put(Elements.SHOUT, (byte) 0x0E);
			put(Elements.HIDDEN, (byte) 0x0F);
			put(Elements.NON_ELEMENTAL, (byte) 0x10);
		}
	};
	
	public static byte getCodeFromElement(Elements element) {
		return elementalCodes.get(element);
	}
	
	private static final Map<String, String[]> limits = new HashMap<String, String[]>() {
		private static final long serialVersionUID = -4747479534463530374L;

		{
			put("Cloud Strife", new String[]{"Braver", "Cross-slash", "Blade Beam", "Climhazzard", "Meteorain", "Finishing Touch", "Omnislash"});
			put("Barret Wallace", new String[]{"Big Shot", "Grenade Bomb", "Satellite Beam", "Ungarmax", "Catastrophe"});
			put("Tifa Lockhart", new String[]{"Beat Rush", "Somersault", "Waterkick", "Meteodrive", "Dolphin Blow", "Meteor Strike", "Final Heaven"});
			put("Aerith Gainsborough", new String[]{});
			put("Red XIII", new String[]{"Sled Fang", "Blood Fang", "Stardust Ray", "Earth Rave", "Cosmo Memory"});
			put("Yuffie Kisaragi", new String[]{"Greased Lightning", "Landscaper", "Bloodfest", "Gauntlet", "Doom of the Living", "All Creation"});
			put("Cait Sith", new String[]{"Toy Box (rock)", "Toy Box (icicles)", "Toy Box (weight)", "Toy Box (hammer)", "Toy Box (fat chocobo)", "Toy Box (hell house)", "Toy Box (meteorites)", "Toy Soldier"});
			put("Vincent Valentine", new String[]{"Berserk Dance", "Beast Flare", "Gigadunk", "Live Wire", "Splattercombo", "Nightmare", "Chaos Saber", "Satan Slam"});
			put("Cid Highwind", new String[]{"Boost Jump", "Dynamite", "Hyper Jump", "Dragon", "Dragon Dive", "Big Brawl", "Highwind"});
		}
	};
	
	public static String getLimitName(String attacker, byte limitCode) {
		int index = Byte.valueOf(limitCode).intValue() - 1;
		return limits.get(attacker)[index];
	}
	
	public static String[] getCharacterLimits(String attacker) {
		return limits.get(attacker);
	}

	private static final Map<String, Integer> limitsPOWM = new HashMap<String, Integer>() {
		private static final long serialVersionUID = 8315125795249714950L;

		{
			put("Braver", 3 * 16);
			put("Cross-slash", 3 * 16 + (16 >> 2));
			put("Blade Beam", 3 * 16 + (16 >> 1));
			put("Climhazzard", 4 * 16 + 3 * (16 >> 3));
			put("Meteorain", 16 + 5 * (16 >> 3));
			put("Finishing Touch", 3 * 16 + (16 >> 3));
			put("Omnislash", 3 * (16 >> 2));
			put("Big Shot", 3 * 16 + (16 >> 2));
			put("Grenade Bomb", 3 * 16 + 3 * (16 >> 3));
			put("Satellite Beam", 2 * 16 + 3 * (16 >> 4));
			put("Ungarmax", 16 >> 1);
			put("Catastrophe", 16 + (16 >> 2));
			put("Beat Rush", 16 + (16 >> 4));
			put("Somersault", 16 + 3 * (16 >> 3));
			put("Waterkick", 16 + (16 >> 2));
			put("Meteodrive", 16 + 5 * (16 >> 3));
			put("Dolphin Blow", 16 + 3 * (16 >> 2));
			put("Meteor Strike", 16 + 7 * (16 >> 3));
			put("Final Heaven", 2 * 16 + 5 * (16 >> 3));
			put("Sled Fang", 3 * 16);
			put("Blood Fang", 16 + (16 >> 4));
			put("Stardust Ray", 5 * (16 >> 3) );
			put("Earth Rave", 16 + 7 * (16 >> 3));
			put("Cosmo Memory", 7 * 16 + 13 * (16 >> 4));
			put("Greased Lightning", 3 * 16 + (16 >> 3));
			put("Landscaper", 3 * 16);
			put("Bloodfest", 5 * (16 >> 3) );
			put("Gauntlet", 16 + 3 * (16 >> 2));
			put("Doom of the Living", 5 * (16 >> 3));
			put("All Creation", 8 * 16);
			put("Toy Box (rock)", 2 * 16);
			put("Toy Box (icicles)", 2 * 16 + (16 >> 1));
			put("Toy Box (weight)", 3 * 16);
			put("Toy Box (hammer)", 3 * 16 + (16 >> 1));
			put("Toy Box (fat chocobo)", 4 * 16);
			put("Toy Box (hell house)", 4 * 16 + (16 >> 1));
			put("Toy Box (meteorites)", 5 * 16);
			put("Toy Soldier", 5 * 16);
			put("Berserk Dance", 16 + (16 >> 1));
			put("Beast Flare", 3 * 16 + 3 * (16 >> 2));
			put("Gigadunk", 2 * 16 + 5 * (16 >> 3));
			put("Live Wire", 4 * 16 + 3 * (16 >> 3));
			put("Splattercombo", 16 >> 1);
			put("Chaos Saber", 2 * 16 + (16 >> 1));
			put("Satan Slam", 5 * 16 + 5 * (16 >> 3));
			put("Boost Jump", 3 * 16 + (16 >> 2));
			put("Dynamite", 2 * 16 + (16 >> 2));
			put("Hyper Jump", 3 * 16 + (16 >> 1));
			put("Dragon", 16 + (16 >> 2));
			put("Dragon Dive", 16 + 7 * (16 >> 4));
			put("Big Brawl", 16 + (16 >> 3));
			put("Highwind", 11 * (16 >> 4));
		}
	};
	
	public static int getLimitPowerMultiplier(String attacker, byte limitCode) {
		String limitName = getLimitName(attacker, limitCode);
		
		return limitsPOWM.get(limitName);
	}
	
	public static int sanitize(int value, int min, int max, int def) {
		if(value < min || value > max) {
			return def;
		}
		
		return value;
	}
	
	public static JLabel drawHeader(String message) {
		JLabel header = new JLabel(message);
		header.setForeground(Color.BLUE);
		header.setHorizontalAlignment(JLabel.CENTER);
		
		return header;
	}
	
	public static byte[] stringToIntToByteArray(String input) {
		int i = Integer.parseInt(input);
		
		return ByteBuffer.allocate(4).putInt(i).array();
	}
	
	public static byte stringToHexToByte(String input) {
		int i = Integer.parseInt(input);
		
		return (byte) i;
	}
	
	public static byte getElementalMultiplier(String value) {
		switch(value) {
		case "0":
		{
			return 0x00;
		}
		case "1":
		{
			return 0x01;
		}
		case "2":
		{
			return 0x02;
		}
		case "0.5":
		{
			return 0x03;
		}
		default:
		{
			return 0x01;
		}
		}
	}
	
	public static byte stringToByteCode(String input) {
		switch(input) {
		case "Yes":
		{
			return 0x01;
		}
		case "No":
		{
			return 0x00;
		}
		case "Back":
		{
			return 0x00;
		}
		case "Front":
		{
			return 0x01;
		}
		case "Absorbs":
		{
			return 0x01;
		}
		case "Doesn't absorb":
		{
			return 0x00;
		}
		default:
		{
			return 0x00;
		}
		}
	}
	
	public static byte characterWrapperToCode(WrapperInterface character) {
		if(character instanceof Cloud) {
			return 0x00;
		}
		else if(character instanceof Barret) {
			return 0x01;
		}
		else if(character instanceof Tifa) {
			return 0x02;
		}
		else if(character instanceof Aerith) {
			return 0x03;
		}
		else if(character instanceof RedXIII) {
			return 0x04;
		}
		else if(character instanceof Yuffie) {
			return 0x05;
		}
		else if(character instanceof CaitSith) {
			return 0x06;
		}
		else if(character instanceof Vincent) {
			return 0x07;
		}
		else if(character instanceof Cid) {
			return 0x08;
		}
		else {
			return 0x00;
		}
	}
}