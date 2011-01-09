package com.assembla.gpsmonsters.battle;

import java.util.ArrayList;

import com.assembla.gpsmonsters.Creature;

public class TrashTalker{
	public ArrayList<String> attacksP;
	public ArrayList<String> attacksE;
	public ArrayList<String> other;
	public ArrayList<String> encounters;
	String user;
	String target;
	String move;
	public TrashTalker(Creature a, Creature b){
		user = a.getName();
		target = b.getName();
		encounters = new ArrayList<String>();
		encounters.add("Encountered a wild "+target);
		encounters.add("A wild "+target+" appeared!!?!");
		encounters.add("A domesticated "+target+" appeared!");
		encounters.add("What's this? A "+target+"? Let's get 'em");
		encounters.add("It's brawlin' time!");
		encounters.add("This "+target+"'s no match for us, take him down!");
	}
	
	public String attacks(int i, boolean p){
		if(p)
			return attacksP.get(i);
		return attacksE.get(i);
	}
	public String other(int i){
		return other.get(i);
	}
	public String encounters(int i){
		return encounters.get(i);
	}
	public void setPMove(String m){
		move = m;
		populatePStrings();
	}
	public void setEMove(String m){
		move = m;
		populateEStrings();
	}
	public void populatePStrings(){
		
		attacksP = new ArrayList<String>();
		other = new ArrayList<String>();
		
		attacksP.add(user +" attacks with "+ move);
		attacksP.add(user + " lashes out with " + move);
		attacksP.add(target+" is no match for your "+move+" attack!");
		attacksP.add(user + " demands of the blood, and attacks with " + move );
		attacksP.add(user+" "+move+"s angers "+target);
		
	}
	public void populateEStrings(){
		
		attacksE = new ArrayList<String>();
		other = new ArrayList<String>();
		
		attacksE.add(target+" unleashes a furocious wave of "+move);
		attacksE.add(target +" attacks with "+ move);
		attacksE.add(target + " is getting angry " + user + ", and attacks with " + move);
		attacksE.add(target+" "+move+"s the crap out of "+user);
		attacksE.add(target+" uses "+move+". Watch out!");
	}
}