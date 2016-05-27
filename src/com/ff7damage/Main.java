package com.ff7damage;

import java.util.Map;

import com.ff7damage.characters.Aerith;
import com.ff7damage.characters.Barret;
import com.ff7damage.characters.CaitSith;
import com.ff7damage.characters.Character;
import com.ff7damage.characters.CharacterInterface;
import com.ff7damage.characters.Cid;
import com.ff7damage.characters.Cloud;
import com.ff7damage.characters.RedXIII;
import com.ff7damage.characters.Tifa;
import com.ff7damage.characters.Vincent;
import com.ff7damage.characters.Yuffie;
import com.ff7damage.view.View;
import com.ff7damage.weapons.Conformer;
import com.ff7damage.weapons.DeathPenalty;
import com.ff7damage.weapons.GenericWeapon;
import com.ff7damage.weapons.HPShout;
import com.ff7damage.weapons.LimitedMoon;
import com.ff7damage.weapons.MissingScore;
import com.ff7damage.weapons.PremiumHeart;
import com.ff7damage.weapons.PrincessGuard;
import com.ff7damage.weapons.UltimaWeapon;
import com.ff7damage.weapons.VenusGospel;
import com.ff7damage.weapons.Weapon;
import com.ff7damage.weapons.WeaponInterface;

public class Main {

	public static void main(String[] args) {
		int heroDrinksNumber = 4;
		boolean criticalHit = true;
		boolean attackerBerserked = true;
		Row attackerRow = Row.FRONT;
		Row targetRow = Row.FRONT;
		boolean targetInDefenseMode = false;
		boolean targetInSadness = false;
		boolean attackerInFrog = false;
		boolean attackerInMini = false;
		boolean targetInBarrier = false;
		boolean targetBackAttacked = false;
		int targetBackAttackMultiplier = 2;
		boolean splitAttack = false;
		Elements attackElement = Elements.SHOOT;
		int targetAverageLevel = 99;
		int currentHp = 9999;
		int maxHp = 9999;
		int currentMp = 999;
		int maxMp = 999;
		int limitLevel = 4;
		int limitGauge = 255;
		int characterLevel = 99;
		int characterStrength = 255;
		int techniquePower = 16;
		int targetDefense = 0;
		int characterKills = 65535;
		int weaponAp = 4000000;
		int deadCharacters = 2;
		boolean longRange = false;
		
		GenericWeapon genericWeapon = new GenericWeapon(18, "Buster Sword", longRange);
		
		Weapon ultimaWeapon = new UltimaWeapon();
		Character cloud = new Cloud(characterLevel, (WeaponInterface) ultimaWeapon, characterStrength, currentHp, maxHp,
				attackerBerserked, attackerInFrog, attackerInFrog, attackerRow);
		
		Weapon missingScore = new MissingScore(weaponAp);
		Character barret = new Barret(characterLevel, (WeaponInterface) missingScore, characterStrength, attackerBerserked,
				attackerInFrog, attackerInMini, attackerRow);
		
		Weapon limitedMoon = new LimitedMoon();
		Character redXIII = new RedXIII(characterLevel, (WeaponInterface) limitedMoon, characterStrength, currentMp, maxMp,
				attackerBerserked, attackerInFrog, attackerInFrog, attackerRow);
		
		Weapon venusGospel = new VenusGospel();
		Character cid = new Cid(characterLevel, (WeaponInterface) venusGospel, characterStrength, currentMp, maxMp,
				attackerBerserked, attackerInFrog, attackerInFrog, attackerRow);
		
		Weapon deathPenalty = new DeathPenalty();
		Character vincent = new Vincent(characterLevel, (WeaponInterface) deathPenalty, characterStrength, characterKills,
				attackerBerserked, attackerInFrog, attackerInMini, attackerRow);
		
		Weapon premiumHeart = new PremiumHeart();
		Character tifa = new Tifa(characterLevel, (WeaponInterface) premiumHeart, characterStrength, limitLevel, limitGauge,
				attackerBerserked, attackerInFrog, attackerInFrog, attackerRow);
		
		Weapon conformer = new Conformer();
		Character yuffie = new Yuffie(characterLevel, (WeaponInterface) conformer, characterStrength, attackerBerserked,
				attackerInFrog, attackerInFrog, attackerRow);
		
		Weapon hpShout = new HPShout();
		Character caitSith = new CaitSith(characterLevel, (WeaponInterface) hpShout, characterStrength, currentHp, maxHp,
				attackerBerserked, attackerInFrog, attackerInFrog, attackerRow);
		
		PrincessGuard princessGuard = new PrincessGuard();
		princessGuard.addAdditionalMultipliers(deadCharacters);
		Character aerith = new Aerith(characterLevel, (WeaponInterface) princessGuard, characterStrength, attackerBerserked,
				attackerInFrog, attackerInFrog, attackerRow);
		
		Map<Elements, Integer> elementalAffinities = Utils.createNoElementalAffinitiesMap();
		Map<Elements, Boolean> elementalAbsorptions = Utils.createNoElementalAbsorptionsMap();
		
		Target target = new Target(targetAverageLevel, targetDefense, targetRow, targetInDefenseMode, targetInSadness,
				targetInBarrier, targetBackAttacked, targetBackAttackMultiplier, elementalAffinities, elementalAbsorptions);
		
		Model calculator = new Model((CharacterInterface) vincent, heroDrinksNumber, splitAttack, target,
				criticalHit, attackElement);
		
		calculator.calculateDamage(techniquePower);
		
		Controller controller = new Controller();
		View view = new View(controller);
		
		calculator.addObserver(controller);
		controller.addObserver(calculator);
		controller.addObserver(view);
		view.addObserver(controller);
		
		view.createAndShowInitialView();
	}
}