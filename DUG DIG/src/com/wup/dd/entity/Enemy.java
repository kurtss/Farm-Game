package com.wup.dd.entity;

import com.wup.dd.Game;

public class Enemy extends Entity {

	private int walkingDir = 1;
	
	public void tick() {
		super.tick();
		if(walkingDir == 1) {
			move(0, 2);
		} else if(walkingDir == -2) {
			move(0, -2);
		}
		if(Game.LEVEL.isDirt(x + 1, y + 1, x + width - 1, y + height - 1)) {
			onHitWall();
		}
	}
	
	protected void onHitWall() {
		if(walkingDir == 1) {
			walkingDir = -2;
		} else if(walkingDir == -2) {
			walkingDir = 1;
		}
	}
	
}
