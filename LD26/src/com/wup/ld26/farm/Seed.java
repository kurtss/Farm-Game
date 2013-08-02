package com.wup.ld26.farm;

import java.util.Random;

import com.wup.ld26.genetics.ColorGene;
import com.wup.ld26.genetics.Gene;
import com.wup.ld26.genetics.Genetics;
import com.wup.ld26.genetics.SizeGene;
import com.wup.ld26.plant.Plant;

public class Seed {

	public int invX, invY, invIndex;
	public int color, size;
	private Gene sizeGene;
	private Gene colorGene;
	
	public Seed(){
		Random rand = new Random();
		String g1 = "" + (rand.nextBoolean() ? 'r' : 'R') + (rand.nextBoolean() ? 'g' : 'G');
		String g2 = "" + (rand.nextBoolean() ? 'g' : 'G') + (rand.nextBoolean() ? 'b' : 'B');
		colorGene = Genetics.getGene(new ColorGene(g1, true), new ColorGene(g2, true));
		colorGene.tag = "color";
		color = Gene.getGeneValue(colorGene);

		g1 = "" + (rand.nextBoolean() ? 'L' : 'l') + (rand.nextBoolean() ? 'S' : 's');
		g2 = "" + (rand.nextBoolean() ? 'L' : 'l') + (rand.nextBoolean() ? 'S' : 's');
		sizeGene = Genetics.getGene(new SizeGene(g1, true), new SizeGene(g2, false));
		sizeGene.tag = "size";
		size = Gene.getGeneValue(sizeGene);
	}
	
	public Seed(Gene sg, Gene cg){
		sizeGene = sg;
		colorGene = cg;
		size = Gene.getGeneValue(sizeGene);
		color = Gene.getGeneValue(colorGene);
	}
	
	public Seed(Plant p){
		size = p.fruitSize;
		color = p.fruitColor;
	}
	
	public Seed mixWith(Seed mix){
		Gene sg = Genetics.getGene(sizeGene, mix.sizeGene);
		Gene cg = Genetics.getGene(colorGene, mix.colorGene);
		sg.tag = "size";
		cg.tag = "color";
		Seed seed = new Seed(sg, cg);
		return seed;
	}
	
}
