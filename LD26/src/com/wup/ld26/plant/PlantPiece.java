package com.wup.ld26.plant;

import com.wup.ld26.render.Screen;

public class PlantPiece {

	public Plant attached;
	public int x, y;
	
	public PlantPiece(Plant a){
		attached = a;
		x = attached.x;
		y = attached.y;
	}
	
	public void tick(){
		
	}
	
	public void render(Screen s){
		
	}
	
}
