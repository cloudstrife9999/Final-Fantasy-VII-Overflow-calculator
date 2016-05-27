package com.ff7damage.characters;

import java.nio.ByteBuffer;

import com.ff7damage.Row;
import com.ff7damage.weapons.WeaponInterface;

public class CharacterFactory {
	public static CharacterInterface createCharacter(byte code, int level, WeaponInterface weapon, int strength, byte[] first,
			byte[] second, boolean berserked, boolean inFrog, boolean inMini, Row row) {
		
		switch(code) {
		case 0x00:
		{
			int currentHp = ByteBuffer.allocate(4).put(first).getInt(0);
			int maxHp = ByteBuffer.allocate(4).put(second).getInt(0);
			
			return new Cloud(level, weapon, strength, currentHp, maxHp, berserked, inFrog, inMini, row);
		}
		case 0x01:
		{
			return new Barret(level, weapon, strength, berserked, inFrog, inMini, row);
		}
		case 0x02:
		{
			int limitLevel = ByteBuffer.allocate(4).put(first).getInt(0);
			int limitGauge = ByteBuffer.allocate(4).put(second).getInt(0);
			
			return new Tifa(level, weapon, strength, limitLevel, limitGauge, berserked, inFrog, inMini, row);
		}
		case 0x03:
		{
			return new Aerith(level, weapon, strength, berserked, inFrog, inMini, row);
		}
		case 0x04:
		{
			int currentMp = ByteBuffer.allocate(4).put(first).getInt(0);
			int maxMp = ByteBuffer.allocate(4).put(second).getInt(0);
			
			return new RedXIII(level, weapon, strength, currentMp, maxMp, berserked, inFrog, inMini, row);
		}
		case 0x05:
		{
			return new Yuffie(level, weapon, strength, berserked, inFrog, inMini, row);
		}
		case 0x06:
		{
			int currentHp = ByteBuffer.allocate(4).put(first).getInt(0);
			int maxHp = ByteBuffer.allocate(4).put(second).getInt(0);
			
			return new CaitSith(level, weapon, strength, currentHp, maxHp, berserked, inFrog, inMini, row);
		}
		case 0x07:
		{
			int kills = ByteBuffer.allocate(4).put(first).getInt(0);
			
			return new Vincent(level, weapon, strength, kills, berserked, inFrog, inMini, row);
		}
		case 0x08:
		{
			int currentMp = ByteBuffer.allocate(4).put(first).getInt(0);
			int maxMp = ByteBuffer.allocate(4).put(second).getInt(0);
			
			return new Cid(level, weapon, strength, currentMp, maxMp, berserked, inFrog, inMini, row);
		}
		default:
		{
			int currentHp = ByteBuffer.allocate(4).put(first).getInt(0);
			int maxHp = ByteBuffer.allocate(4).put(second).getInt(0);
			
			return new Cloud(level, weapon, strength, currentHp, maxHp, berserked, inFrog, inMini, row);
		}
		}
	}
}