package com.ff7damage;

import java.awt.Color;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;

import com.ff7damage.characters.CharacterInterface;
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
	
	public static final Map<Byte, Elements> elements = new HashMap<Byte, Elements>() {
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
			put((byte) 0x10, Elements.NONELEMENTAL);
		}
	};
	
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
	
	public static byte elementToCode(String element) {
		switch(element) {
		case "Fire":
		{
			return 0x00;
		}
		case "Ice":
		{
			return 0x01;
		}
		case "Lightning":
		{
			return 0x02;
		}
		case "Earth":
		{
			return 0x03;
		}
		case "Wind":
		{
			return 0x04;
		}
		case "Water":
		{
			return 0x05;
		}
		case "Poison":
		{
			return 0x06;
		}
		case "Holy":
		{
			return 0x07;
		}
		case "Gravity":
		{
			return 0x08;
		}
		case "Restorative":
		{
			return 0x09;
		}
		case "Cut":
		{
			return 0x0A;
		}
		case "Hit":
		{
			return 0x0B;
		}
		case "Punch":
		{
			return 0x0C;
		}
		case "Shoot":
		{
			return 0x0D;
		}
		case "Shout":
		{
			return 0x0E;
		}
		case "Hidden":
		{
			return 0x0F;
		}
		case "Non-elemental":
		{
			return 0x10;
		}
		default:
		{
			return 0x10;
		}
		}
	}
	
	public static byte getWeaponCode(String weapon) {
		switch(weapon){
		case "Ultima Weapon":
		case "Missing Score":
		case "Premium Heart":
		case "Princess Guard":
		case "Limited Moon":
		case "Conformer":
		case "HP Shout":
		case "Death Penalty":
		case "Venus Gospel":
		{
			return 0x00;
		}
		default:
		{
			return 0x01;
		}
		}
	}
	
	public static byte intToHex(int value) {
		return Integer.valueOf(String.valueOf(value), 16).byteValue();
	}
	
	public CharacterInterface constructCharacterFromCode(byte code, Object... params) {
		return null; //TODO
	}
}