package com.wup.ld26.genetics;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Genetics {

	public static Gene getGene(Gene g1, Gene g2){
		/**
		 * 		 G  g
		 * 	  g  Gg gg
		 *    G  GG Gg
		 */
		Random geneRand = new Random();
		String[] mix = new String[4];
		mix[0] = "" + g1.type.substring(0, g1.type.length() / 2) + g2.type.substring(0, g1.type.length() / 2);
		mix[1] = "" + g1.type.substring(g1.type.length() / 2, g1.type.length()) + g2.type.substring(0, g1.type.length() / 2);
		mix[2] = "" + g1.type.substring(0, g1.type.length() / 2) + g2.type.substring(g1.type.length() / 2, g1.type.length());
		mix[3] = "" + g1.type.substring(g1.type.length() / 2, g1.type.length()) + g2.type.substring(g1.type.length() / 2, g1.type.length());
		
		List<String> dom = new ArrayList<String>();
		List<String> rec = new ArrayList<String>();
		
		int percDominant = 0;
		for(int i = 0; i < mix.length; i++){
			String type = mix[i];
			boolean rect = false;
			boolean domt = false;
			for(int j = 0; j < type.length(); j++){
				if(Character.isUpperCase(type.charAt(j))){
					percDominant++;
					domt = true;
				}
				if(Character.isLowerCase(type.charAt(j))){
					rect = true;
				}
			}
			if(domt) dom.add(type);
			if(rect) rec.add(type);
		}
		int dominant = (int)((percDominant / 8.0) * 100);
		//int recessive = 100 - dominant;
		boolean isDominant = false;
		int chance = (int)(geneRand.nextDouble() * 100);
		if(chance <= dominant) isDominant = true;
		else isDominant = false;
		
		/*System.out.println(mix[0] + " | " + mix[1]);
		System.out.println(mix[2] + " | " + mix[3]);
		System.out.println("Gene 1: " + g1.type);
		System.out.println("Gene 2: " + g2.type);
		System.out.println("Perc dom: " + dominant);
		System.out.println("Perc rec: " + recessive);
		System.out.println("Chance: " + chance);
		System.out.println("Is dominant: " + isDominant);
		System.out.println("Amount dominant: " + dom.size());
		System.out.println("Amount recessive: " + rec.size());*/
		
		String type = "";
		if(isDominant){
			if(dom.size() <= 1) type = dom.get(0);
			else type = dom.get(geneRand.nextInt(dom.size()));
		}else{
			if(rec.size() <= 1) type = rec.get(0);
			else type = rec.get(geneRand.nextInt(rec.size()));
		}
		return new Gene(type, isDominant);
	}
	
}
