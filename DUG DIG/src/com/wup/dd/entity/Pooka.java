package com.wup.dd.entity;

import com.wup.dd.render.Art;
import com.wup.dd.render.Screen;

public class Pooka extends Enemy {

	public void render(Screen s) {
		s.draw(Art.POOKA[0], x, y);
	}
	
}
