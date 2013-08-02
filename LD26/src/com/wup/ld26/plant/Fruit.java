package com.wup.ld26.plant;

import com.wup.ld26.render.Screen;

public class Fruit extends PlantPiece{

	public int size = 1, maxSize, color;
	
	public Fruit(Plant a){
		super(a);
		//maxSize = attached.rand.nextInt(4) + 2;
		maxSize = a.fruitSize;
	}
	
	public void tick(){
		if(attached.rand.nextInt(25) == 0 && size + 1 != maxSize){
			size++;
		}
	}
	
	public void render(Screen s){
		//s.circle(x, y, size, color);
		s.fill(x, y, size, size, color);
	}
	
}
