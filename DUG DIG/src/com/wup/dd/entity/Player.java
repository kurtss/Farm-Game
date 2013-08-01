package com.wup.dd.entity;

import com.wup.dd.Game;
import com.wup.dd.render.Art;
import com.wup.dd.render.Screen;

public class Player extends Entity{

	private int dirtAmountDug = 0;
	
	public void tick() {
		super.tick();
		Game.LEVEL.removeDirt(this, x, y, x + 16, y + 16);
	}
	
	public void render(Screen s) {
		int sprInd = 0;
		if(moving) {
			sprInd = (age % 8) / 2 == 0 ? 1 : 2;
		}
		s.draw(Art.PLAYER[sprInd], x, y, facing == -1 ? true : false, false);
	}
	
	public void addDugDirt(int amt) {
		dirtAmountDug += amt;
		if(dirtAmountDug > 32) {
			while(dirtAmountDug > 32) {
				dirtAmountDug -= 32;
				Game.SCORE += 10;
			}
		}
	}
	
}
