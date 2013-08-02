package com.wup.ld26.plant;

import com.wup.ld26.render.Screen;

public class Branch extends PlantPiece{

	public int length = 0, maxLength;
	public Fruit fruit;
	
	public Branch(Plant a) {
		super(a);
		maxLength = (4 + attached.rand.nextInt(6)) * (attached.rand.nextBoolean() ? -1 : 1);
	}
	
	public void tick(){
		if(attached.rand.nextInt(100) == 0 && length != maxLength){
			if(length < maxLength) length++;
			if(length > maxLength) length--;
		}
		if(fruit == null && length == maxLength && attached.rand.nextInt(75) == 0){
			fruit = new Fruit(attached);
			fruit.y = y;
			fruit.x = x + length;
			fruit.color = attached.fruitColor;
		}
		if(fruit != null){
			fruit.tick();
		}
	}
	
	public void render(Screen s){
		s.line(x + (length < 0 ? length : 0), y, x + (length > 0 ? length : 0), y, attached.stemColor);
		if(fruit != null) fruit.render(s);
	}
	
}
