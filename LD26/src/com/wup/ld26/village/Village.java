package com.wup.ld26.village;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.wup.ld26.render.Art;
import com.wup.ld26.render.Screen;

public class Village {

	public int width, height;
	private int population;
	public Random rand;
	private List<VillageObj> objects;
	
	public Village(int w, int h){
		width = w;
		height = h;
		rand = new Random();
		objects = new ArrayList<VillageObj>();
		population = 0;
	}
	
	public void tick(){
		if(objects.size() < population / 4){
			VillageObj obj = new VillageObj();
			obj.type = rand.nextInt(2);
			obj.x = rand.nextInt(width);
			if(obj.x == width - 1) obj.x = width - 6;
			obj.y = rand.nextInt(height);
			if(obj.y == height - 1) obj.y = height - 6;
			if(obj.type == 0) obj.moves = true;
			objects.add(obj);
		}
		else if(objects.size() > population / 4){
			objects.remove(rand.nextInt(objects.size()));
		}
		for(int o = 0; o < objects.size(); o++){
			VillageObj obj = objects.get(o);
			if(obj.moves && rand.nextInt(20) == 0){
				int rx = rand.nextBoolean() ? -1 : 1;
				int ry = rand.nextBoolean() ? -1 : 1;
				if(obj.x + rx <= width - 6 && obj.x + rx >= 0) obj.x += rx;
				if(obj.y + ry <= height - 6 && obj.y + ry >= 0) obj.y += ry;
			}
		}
	}
	
	public int getPopulation(){
		return population;
	}
	
	public void render(Screen s){
		s.fill(0, 0, width, height, 0x758918);
		for(int o = 0; o < objects.size(); o++){
			VillageObj obj = objects.get(o);
			s.draw(Art.cut("/res/village.png", 4, 4)[obj.type], obj.x, obj.y);
		}
	}
	
	public void feed(int amt){
		population += amt;
	}
	
	private class VillageObj{
		public int type;
		public int x, y;
		public boolean moves;
	}
	
}
