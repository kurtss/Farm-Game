package com.wup.ld26.plant;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.wup.ld26.farm.Farmland;
import com.wup.ld26.farm.Seed;
import com.wup.ld26.genetics.Gene;
import com.wup.ld26.genetics.Genetics;
import com.wup.ld26.render.Art;
import com.wup.ld26.render.Screen;

public class Plant {

	public Random rand;
	public Farmland plantedOn;
	public int x, y;
	public int height;
	private List<Branch> branches;
	public int fruitColor;
	public int stemColor;
	private int maxHeight;
	public int fruitSize = 1;
	
	public Plant(){
		rand = new Random();
		branches = new ArrayList<Branch>();
		fruitColor = rand.nextInt(0xffffff);
		stemColor = 0x7DB32C;
		maxHeight = 8 + rand.nextInt(24);
		String g1 = "" + (rand.nextBoolean() ? 'r' : 'R') + (rand.nextBoolean() ? 'g' : 'G');
		String g2 = "" + (rand.nextBoolean() ? 'g' : 'G') + (rand.nextBoolean() ? 'b' : 'B');
		Gene col = Genetics.getGene(new Gene(g1, true), new Gene(g2, true));
		fruitColor = Art.additiveColor(Colors.get(col.type.charAt(0)).rgb, Colors.get(col.type.charAt(1)).rgb);

		g1 = "" + (rand.nextBoolean() ? '6' : '6') + (rand.nextBoolean() ? '2' : '2');
		g2 = "" + (rand.nextBoolean() ? '6' : '6') + (rand.nextBoolean() ? '2' : '2');
		//Gene size = Genetics.getGene(new Gene(g1, true), new Gene(g2, true));
		//fruitSize = (Integer.parseInt("" + size.type.charAt(0)) + Integer.parseInt("" + size.type.charAt(1))) / 2;
		fruitSize = rand.nextInt(4) + 2;
	}
	
	public Plant(Seed s){
		this();
		fruitColor = s.color;
		fruitSize = s.size;
	}
	
	public void tick(){
		if(rand.nextInt(100) == 0 && height + 1 <= maxHeight) height++;
		if(height == maxHeight){
			if(rand.nextInt(50) == 0 && branches.size() + 1 < maxHeight / 4){
				Branch branch = new Branch(this);
				branch.y = y - rand.nextInt(height);
				branches.add(branch);
			}
		}
		for(int b = 0; b < branches.size(); b++){
			Branch branch = branches.get(b);
			branch.tick();
		}
	}
	
	public void render(Screen s){
		s.line(x, y, x, y - height, stemColor);
		for(int b = 0; b < branches.size(); b++){
			Branch branch = branches.get(b);
			branch.render(s);
		}
	}
	
	public int getFood(){
		int feed = 0;
		for(int b = 0; b < branches.size(); b++){
			Branch branch = branches.get(b);
			if(branch.fruit != null){
				feed += branch.fruit.size / 3;
			}
		}
		return feed;
	}
	
	public boolean fullGrown(){
		return height == maxHeight && hasFruit();
	}
	
	public boolean hasFruit(){
		for(int b = 0; b < branches.size(); b++){
			Branch branch = branches.get(b);
			if(branch.fruit != null){
				return true;
			}
		}
		return false;
	}
	
}
