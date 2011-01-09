package com.assembla.gpsmonsters.battle;

import com.assembla.gpsmonsters.Creature;

public class Move{
		private String name;
		private int moveCategory;
		private int movePower;
		private int moveTime;  // something like 1 by default?
		private int alterredStat; // 1: attack, 2: defense, 3: speed, 
					  // 4: magic power, 5: magic defense, 
					  // 6: charm, 7: accuracy
		private boolean isSpecialAttack;
		private int particle;
		
		// constructor for categories 1, 4, and 5
		public Move(String n, int category, int pwr, int time, boolean isSpecial, int part){
			name = n;
			moveCategory = category;
			movePower = pwr;
			moveTime = time;
			isSpecialAttack = isSpecial;
			particle = part;;
		}
		
		// constructor for categories 2 and 3
		// Added flag parameter because Moves created for categories 1,4,5 were being mistakenly
		// created with this constructor.  Temporary fix for now.
		public Move(String n, int type, int pwr, int time, int stat, int part)
		{
		    name = n;
		    moveCategory = type;
		    movePower = pwr;
		    moveTime = time;
		    alterredStat = stat;  // tells which stat is being raised or lowered
		    particle = part;
		}
		
		public void setName(String name) {
			this.name = name;
		}
	
		public String getName() {
			return name;
		}
	
		public void setMoveType(int moveType) {
			this.moveCategory = moveType;
		}
	
		public int getMoveType() {
			return moveCategory;
		}
	
		public void setMovePower(int movePower) {
			this.movePower = movePower;
		}
	
		public int getMovePower() {
			return movePower;
		}
		
		public int getAlterredStat(){
		    return alterredStat;
		}
		
		public void setAlterredStat(int stat){
		    this.alterredStat = stat;
		}
		
		public void setMoveTime(int moveTime) {
			this.moveTime = moveTime;
		}

		public int getMoveTime() {
			return moveTime;
		}
		
		public void setParticle(int particle) {
			this.particle = particle;
		}

		public int getParticle() {
			return particle;
		}
		
		public static double randomVal()
		{
		    return (((Math.random() * 15) + 85) / 100.0);
		}
		
		// TODO: tweak damage values here... possibly base movePower as well. -mvd
		public int calculateDamage(int attackerLevel, int offenseStat, int defenseStat)
		{
		    float val;
		    val = (attackerLevel-1) * 0.1f + 1;
		    val = (val * movePower * offenseStat / defenseStat);
		    val = (val * (float)randomVal());
		    return Math.round(val);
		    //return((int)(((((((int)(attackerLevel * 0.4) + 2) * movePower * offenseStat / 10) / defenseStat) + 2) * randomVal());
		}
		
		public void execute(Creature target, Creature user){
			Creature [] players = {target, user};
			if(this.moveCategory == 1){ // Simple attack, calculate damage and resulting health accordingly
			    //players[0].addCurrentHealth(-(movePower*(1+(user.getAttackStat()-target.getDefenseStat()/2)/100)));  // old method
			    //players[0].addCurrentHealth(-(int)(((((int)(((int)(user.getLevel() * 0.4) + 2) * movePower * user.getAttackStat() / 10.0) / target.getDefenseStat())) + 2) * randomInt() / 100.0)); 
			    players[0].addCurrentHealth(-calculateDamage(user.getLevel(), user.getAttackStat(), target.getDefenseStat()));
			}
			else if(this.moveCategory == 2){ // alter opponent stats: lower attack, lower defense, etc.
			 // 1: attack, 2: defense, 3: speed, 
			 // 4: magic power, 5: magic defense, 6: charm, 7: accuracy
			    
			    if (alterredStat==1)
				players[0].addAttackStat(-movePower);
			    else if (alterredStat==2)
				players[0].addDefenseStat(-movePower);
			    else if (alterredStat==3)
				players[0].addSpeedStat(-movePower);
			    else if (alterredStat==4)
				players[0].addMagicPowerStat(-movePower);
			    else if (alterredStat==5)
				players[0].addMagicDefenseStat(-movePower);
			    else if (alterredStat==6)
				players[0].addCharmStat(-movePower);
			    else if (alterredStat==7)
				players[0].addAccuracyStat(-movePower*5);
			    else{
			    // log error: invalid stat ID
			    }
			}
			else if(this.moveCategory == 3){ // augment your own stats in some way
			    // 1: attack, 2: defense, 3: speed, 
			    // 4: magic power, 5: magic defense, 6: charm, 7: accuracy
			    
			    if (alterredStat==1)
				players[1].addAttackStat(movePower);
			    else if (alterredStat==2)
				players[1].addDefenseStat(movePower);
			    else if (alterredStat==3)
				players[1].addSpeedStat(movePower);
			    else if (alterredStat==4)
				players[1].addMagicPowerStat(movePower);
			    else if (alterredStat==5)
				players[1].addMagicDefenseStat(movePower);
			    else if (alterredStat==6)
				players[1].addCharmStat(movePower);
			    else if (alterredStat==7)
				players[1].addAccuracyStat(movePower*5);
			    else{
			    // log error: invalid stat ID
			    }   
			}
			else if(this.moveCategory == 4){ // type special move
			    players[0].addCurrentHealth(-calculateDamage(user.getLevel(), user.getMagicPowerStat(), target.getMagicDefenseStat()));
			}
			else if(this.moveCategory == 5){ // unique creature move
			    if (isSpecialAttack)
				players[0].addCurrentHealth(-calculateDamage(user.getLevel(), user.getAttackStat(), target.getDefenseStat()));
			    else
				players[0].addCurrentHealth(-calculateDamage(user.getLevel(), user.getMagicPowerStat(), target.getMagicDefenseStat()));
			}
		}

		
}