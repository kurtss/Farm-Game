package com.wup.ld26.farm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Farm {

	public int width, height;
	public Random rand;
	private List<Farmland> tilled;
	private boolean[] weeds;
	
	public Farm(int w, int h){
		width = w;
		height = h;
		rand = new Random();
		tilled = new ArrayList<Farmland>();
		weeds = new boolean[width * height];
		for(int i = 0; i < weeds.length; i++){
			if(rand.nextInt(30) == 0) weeds[i] = true;
		}
	}
	
	public void tick(){
		for(int f = 0; f < tilled.size(); f++){
			tilled.get(f).tick();
		}
	}
	
	public boolean valid(int x, int y){
		return x >= 0 && x < width && y >= 0 && y < height;
	}
	
	public boolean tillLand(int x, int y){
		if(farmlandIn(x, y, x + 16, y + 16).size() > 0) return false;
		Farmland till = new Farmland(x, y);
		tilled.add(till);
		for(int i = (int)(x / 16f); i <= (int)((x + 16) / 16f); i++){
			for(int j = (int)(y / 16f); j <= (int)((y + 16) / 16f); j++){
				if(weedsAt(i, j)){
					weeds[i + j * width] = false;
				}
			}
		}
		return true;
	}
	
	public List<Farmland> farmlandIn(int x1, int y1, int x2, int y2){
		List<Farmland> within = new ArrayList<Farmland>();
		for(int f = 0; f < tilled.size(); f++){
			Farmland at = tilled.get(f);
			if(at.intersects(x1, y1, x2, y2)) within.add(at);
		}
		Collections.sort(within, new Comparator<Farmland>(){

			@Override
			public int compare(Farmland f0, Farmland f1) {
				if(f0.y < f1.y) return -1;
				if(f0.y > f1.y) return 1;
				return 0;
			}
			
		});
		return within;
	}
	
	public Farmland farmlandAt(int x, int y){
		if(farmlandIn(x, y, x, y).isEmpty()) return null;
		return farmlandIn(x, y, x, y).get(0);
	}
	
	public boolean weedsAt(int x, int y){
		if(!valid(x, y)) return false;
		return weeds[x + y * width];
	}
	
}
