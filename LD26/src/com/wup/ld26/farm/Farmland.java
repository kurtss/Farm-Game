package com.wup.ld26.farm;

import com.wup.ld26.plant.Plant;
import com.wup.ld26.render.Art;
import com.wup.ld26.render.Screen;

public class Farmland {

	public int x, y;
	public Plant plant;
	
	public Farmland(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public void tick(){
		if(plant != null){
			plant.tick();
		}
	}
	
	public void setPlant(Plant p){
		if(p != null){
			p.plantedOn = this;
			p.x = x + 8;
			p.y = y + 8;
		}
		plant = p;
	}
	
	public void render(Screen s){
		s.draw(Art.load("/res/farmland.png"), x, y);
		if(plant != null) plant.render(s);
	}
	
	public boolean intersects(int x1, int y1, int x2, int y2){
		return !(x + 16 < x1 || x > x2 || y + 16 < y1 || y - (plant == null ? 0 : plant.height) > y2);
	}
	
}
