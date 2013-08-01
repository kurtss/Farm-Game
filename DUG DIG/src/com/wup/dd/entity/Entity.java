package com.wup.dd.entity;

import com.wup.dd.Game;
import com.wup.dd.render.Screen;

public class Entity {

	public int lastX, lastY;
	public int x, y;
	public int width, height;
	protected int age;
	protected int facing;
	protected boolean moving;
	
	public Entity() {
		setSize(16, 16);
	}
	
	public void tick() {
		if(x != lastX || y != lastY) {
			moving = true;
		} else {
			moving = false;
		}
		lastX = x;
		lastY = y;
		age++;

	}
	
	public void render(Screen s) {
		
	}
	
	public void move(int mx, int my){
		if(!Game.LEVEL.isValid(x + mx, y)) {
			mx = 0;
		}
		x += mx;
		if(!Game.LEVEL.isValid(x, y + my) ||
				y + my < 16) {
			my = 0;
		}
		y += my;
		if(mx < 0) {
			facing = -1;
		} else if(mx > 0) {
			facing = 0;
		} else if(my < 0) {
			facing = -2;
		} else if(my > 0) {
			facing = 1;
		}
	}
	
	public boolean intersects(int x1, int y1, int x2, int y2) {
		return !(x + width < x1 || x > x2 || y + height < y1 || y > y2);
	}
	
	public void setLocation(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public void setSize(int w, int h) {
		width = w;
		height = h;
	}
	
}
