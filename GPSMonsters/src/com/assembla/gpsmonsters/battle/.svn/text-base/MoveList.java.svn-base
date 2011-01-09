/*
 * Matt DelBrocco & Ken Watka
 * 10/2/10
 * 
 * This is simply a list of all the moves in the game. Simple enough, right?
 * Each creature can have at most 5 moves at a time because we have 5 possible
 * gestures. To keep them organized by move category, remember that the second 
 * parameter of the Move constructor is the move's category!
 */
package com.assembla.gpsmonsters.battle;

import java.util.ArrayList;

import com.assembla.gpsmonsters.particleengine.ParticleSystem;

// TODO: add moves to this! We want to have a good amount!
public class MoveList {
    public static ArrayList<Move> theList = new ArrayList<Move>();
    public static void populateMoveList()
    {
	// 1 basic attack: all creatures will have at least one of these.
	theList.add(new Move("Bite",1,9,1,false,ParticleSystem.NEUTRAL));
	theList.add(new Move("Slash",1,10,1,false,ParticleSystem.NEUTRAL));
	theList.add(new Move("Tongue Slap",1,8,1,false,ParticleSystem.NEUTRAL));
	theList.add(new Move("Smack",1,7,1,false,ParticleSystem.NEUTRAL));
	theList.add(new Move("Stomp",1,10,1,false,ParticleSystem.NEUTRAL));
	theList.add(new Move("Full Speed Take-Down",1,12,1,false,ParticleSystem.NEUTRAL));
	theList.add(new Move("Vicious Assault",1,14,1,false,ParticleSystem.NEUTRAL));
	theList.add(new Move("Atomic Fury!!!",1,25,1,false,ParticleSystem.CRAZY));
	theList.add(new Move("Pepperment Blast",1,15,1,false,ParticleSystem.NEUTRAL));
	// 2 alter opponent stats: lower attack, lower defense, etc.
	theList.add(new Move("Intimidate",2,2,1,1,ParticleSystem.VOID));  // lower opponent attack
	theList.add(new Move("Feint",2,2,1,7,ParticleSystem.VOID));  // lower opponent accuracy
	// 3 alter your own stats: raise attack, raise defense, etc.
	theList.add(new Move("Wind Up", 3, 2, 1,1,ParticleSystem.VOID)); // increase your attack
	theList.add(new Move("Focus", 3, 3, 1, 1,ParticleSystem.VOID));  // increase your attack
	theList.add(new Move("Defend", 3, 3, 1, 2,ParticleSystem.VOID)); // increase your defense
	theList.add(new Move("Protect", 3, 3, 1, 2,ParticleSystem.VOID));  // increase defense
	// 4 type special: unique moves, but creatures of same type may share.
	theList.add(new Move("FireBall", 4, 12, 1, true,ParticleSystem.FIRE));
	theList.add(new Move("Fire Rush", 4, 14, 1, true,ParticleSystem.FIRE));
	theList.add(new Move("Blaze", 4, 16, 1, true,ParticleSystem.FIRE));
	theList.add(new Move("Inferno", 4, 18, 1, true,ParticleSystem.FIRE));
	theList.add(new Move("Sea of Flame", 4, 20, 1, true,ParticleSystem.FIRE));
	theList.add(new Move("Freeze", 4, 12, 1, true,ParticleSystem.ICE));
	theList.add(new Move("Ice Spear", 4, 14, 1, true,ParticleSystem.ICE));
	theList.add(new Move("Hail Storm", 4, 16, 1, true,ParticleSystem.ICE));
	theList.add(new Move("Ice Hammer", 4, 18, 1, true,ParticleSystem.ICE));
	theList.add(new Move("Glacial Blast", 4, 20, 1, true,ParticleSystem.ICE));
	theList.add(new Move("Shock", 4, 12, 1, true,ParticleSystem.ELECTRIC));
	theList.add(new Move("Discharge", 4, 14, 1, true,ParticleSystem.ELECTRIC));
	theList.add(new Move("Bolt Rush", 4, 16, 1, true,ParticleSystem.ELECTRIC));
	theList.add(new Move("Electric Blast", 4, 18, 1, true,ParticleSystem.ELECTRIC));
	theList.add(new Move("Teslic Pierce", 4, 20, 1, true,ParticleSystem.ELECTRIC));
	theList.add(new Move("Douse", 4, 12, 1, true, ParticleSystem.WATER));
	theList.add(new Move("Drench", 4, 14, 1, true, ParticleSystem.WATER));
	theList.add(new Move("Aquatic Blast", 4, 16, 1, true,ParticleSystem.WATER));// creatures like aqua bear use this
	
	// 5 creature special: each creature has its own unique move for this category.
	theList.add(new Move("Undead Chomp", 5, 18, 1, false,ParticleSystem.NEUTRAL)); // zombie wolf owns you
    }
    
    public static ArrayList<Move> getMoveList()
    {
	return theList;
    }
    
    public static Move getMoveByIndex(int x)
    {
	return theList.get(x);
    }
    
    public static Move getMoveByName(String s)
    {
	Move move = new Move("TEST MOVE",1,20,1,false,ParticleSystem.NEUTRAL);
	for (Move m: theList)
	{
	    if (s.compareToIgnoreCase(m.getName())==0)
		move = m;
	}
	return move;
	
    }
    
}
