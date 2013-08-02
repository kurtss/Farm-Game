package com.wup.ld26.genetics;

import com.wup.ld26.plant.Colors;
import com.wup.ld26.render.Art;

public class Gene {

	public String type;
	public boolean dominant;
	public String tag;
	
	public Gene(String s, boolean d){
		type = s;
		dominant = d;
	}
	
	public static int getGeneValue(Gene g){
		if(g.tag.equals("size")){
			char c1 = g.type.charAt(0);
			char c2 = g.type.charAt(1);
			if(c1 == 'L' || c2 == 'L') return 6;
			if(c1 == 'S' || c2 == 'S') return 2;
			if((c1 == 'l' && c2 == 's') || (c1 == 's' && c2 == 'l')) return 4;
			if((c1 == 'L' && c2 == 'S') || (c1 == 'S' && c2 == 'L')) return 4;
			if(c1 == 'l' && c2 == 'l') return 6;
			if(c1 == 's' && c2 == 's') return 4;
			else return 2;
		}
		if(g.tag.equals("color")){
			return Art.additiveColor(Colors.get(g.type.charAt(0)).rgb, Colors.get(g.type.charAt(1)).rgb);
		}
		return 0;
	}
	
}
