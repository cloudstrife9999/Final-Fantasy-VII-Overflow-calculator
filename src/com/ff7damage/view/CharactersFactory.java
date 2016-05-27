package com.ff7damage.view;

public class CharactersFactory {
	public static WrapperInterface getCharacterWrapper(String characterName) {
		switch(characterName) {
		case "cloud":
		{
			return new Cloud();
		}
		case "barret":
		{
			return new Barret();
		}
		case "tifa":
		{
			return new Tifa();
		}
		case "aerith":
		{
			return new Aerith();
		}
		case "red":
		{
			return new RedXIII();
		}
		case "yuffie":
		{
			return new Yuffie();
		}
		case "cait":
		{
			return new CaitSith();
		}
		case "vincent":
		{
			return new Vincent();
		}
		case "cid":
		{
			return new Cid();
		}
		default:
		{
			return new Cloud();
		}
		}
	}
}