package com.wup.ld26.plant;

import java.util.HashMap;
import java.util.Map;

public class Colors {

	private static final Map<Character, Colors> colorMap = new HashMap<Character, Colors>();
	
	public char symbol;
	public int rgb;
	
	public static final Colors red = new Colors('r', 0xff0000);
	public static final Colors green = new Colors('g', 0x00ff00);
	public static final Colors blue = new Colors('b', 0x0000ff);
	
	public Colors(char s, int col){
		symbol = s;
		rgb = col;
		colorMap.put(symbol, this);
		colorMap.put(Character.toUpperCase(symbol), this);
	}
	
	public static Colors get(char c){
		return colorMap.get(c);
	}
	
}
