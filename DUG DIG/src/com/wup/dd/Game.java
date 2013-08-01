package com.wup.dd;

import java.util.List;
import java.util.Random;

import com.wup.dd.entity.Entity;
import com.wup.dd.entity.Player;
import com.wup.dd.entity.Pooka;
import com.wup.dd.level.Level;
import com.wup.dd.render.Screen;

public class Game {

	public static final String TITLE = "DUG DIG";
	public static final int WIDTH = 480 / 2;
	public static final int HEIGHT = 640 / 2;
	public static final int SCALE = 2;
	public static boolean DEBUG = !false;
	public static final int HALFWIDTH = WIDTH / 2;
	public static final int HALFHEIGHT = HEIGHT / 2;
	public Input input;
	
	public static Random RAND = new Random();
	public static Level LEVEL = new Level(WIDTH, HEIGHT);
	public static Player PLAYER = new Player();
	public static int SCORE = 0;
	
	private boolean moveKeyDown = false;
	
	public void init(){
		LEVEL.fillArea(0, 0, LEVEL.width, LEVEL.height, 0xffff0000);
		int layerHeight = LEVEL.height / 4;
		int randDot = 250;
		for(int j = 0; j < LEVEL.height / 4; j++) {
			if(j == 0) {
				LEVEL.fillArea(0, j * layerHeight, 
						LEVEL.width, j * layerHeight + layerHeight, 0xFFFFB800);
				for(int i1 = 0; i1 < LEVEL.width; i1++) {
					for(int j1 = j * layerHeight; j1 < j * layerHeight + layerHeight; j1++) {
						if(RAND.nextInt(randDot) == 0) {
							LEVEL.setColor(i1, j1, 0xFFDEDE00);
						}
					}
				}
			} else if(j == 1) {
				LEVEL.fillArea(0, j * layerHeight, 
						LEVEL.width, j * layerHeight + layerHeight, 0xFFDE6800);
				for(int i1 = 0; i1 < LEVEL.width; i1++) {
					for(int j1 = j * layerHeight; j1 < j * layerHeight + layerHeight; j1++) {
						if(RAND.nextInt(randDot) == 0) {
							LEVEL.setColor(i1, j1, 0xFFFFB800);
						}
					}
				}
			} else if(j == 2) {
				LEVEL.fillArea(0, j * layerHeight, 
						LEVEL.width, j * layerHeight + layerHeight, 0xFFB82100);
				for(int i1 = 0; i1 < LEVEL.width; i1++) {
					for(int j1 = j * layerHeight; j1 < j * layerHeight + layerHeight; j1++) {
						if(RAND.nextInt(randDot) == 0) {
							LEVEL.setColor(i1, j1, 0xFFDE6800);
						}
					}
				}
			} else if(j == 3) {
				LEVEL.fillArea(0, j * layerHeight, 
						LEVEL.width, j * layerHeight + layerHeight, 0xFF970000);
				for(int i1 = 0; i1 < LEVEL.width; i1++) {
					for(int j1 = j * layerHeight; j1 < j * layerHeight + layerHeight; j1++) {
						if(RAND.nextInt(randDot) == 0) {
							LEVEL.setColor(i1, j1, 0xFFB82100);
						}
					}
				}
			}
		}
		LEVEL.fillArea(0, 0, LEVEL.width, 32, 0xFF000097);
		
		LEVEL.removeDirt(null, 16, 64, 32, 64 + (16 * 5));
		Pooka pooka = new Pooka();
		pooka.setLocation(16, 64);
		LEVEL.spawn(pooka);
		
		PLAYER.setLocation(0, 16);
		LEVEL.spawn(PLAYER);
	}
	
	public void update(){
		LEVEL.tick();
	}
	
	public void draw(Screen s){
		s.draw(LEVEL.getLevelImage(), 0, 0);
		List<Entity> toRender = LEVEL.getEntitiesWithin(0, 0, WIDTH, HEIGHT);
		for(int e = 0; e < toRender.size(); e++) {
			toRender.get(e).render(s);
		}
		s.string("1UP", 24, 0, 0xffff0000);
		s.string("00", 40, 8, 0xffffffff);
		s.string("HIGH SCORE", 96, 0, 0xffff0000);
		s.string(String.valueOf(SCORE), 120, 8, 0xffffffff);
	}	
	
	public void stop(){

	}
	
	public void keyDown(int key){
		int speed = 2;
		if(key == Keys.UP && !moveKeyDown) {
			PLAYER.move(0, -speed);
			moveKeyDown = true;
		} else if(key == Keys.DOWN && !moveKeyDown) {
			PLAYER.move(0, speed);
			moveKeyDown = true;
		} else if(key == Keys.LEFT && !moveKeyDown) {
			PLAYER.move(-speed, 0);
			moveKeyDown = true;
		} else if(key == Keys.RIGHT && !moveKeyDown) {
			PLAYER.move(speed, 0);
			moveKeyDown = true;
		} else {
			moveKeyDown = false;
		}
	}
	
	public void keyTyped(int key, char keychar){
		
	}
	
	public void leftMouseClick(int x, int y){

	}
	
	public void leftMouseDown(int x, int y){
		LEVEL.fillArea(x - 8, y - 8, x + 8, y + 8, 0);
	}
	
	public void rightMouseClick(int x, int y){
		
	}
	
}
