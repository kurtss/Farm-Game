package com.wup.ld26.render;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

public class Art {
		
	private static final Map<String, BufferedImage> loadedImages = new HashMap<String, BufferedImage>();
	private static final Map<String, BufferedImage[]> loadedImgArrays = new HashMap<String, BufferedImage[]>();
	
	public static void loadAll(){

	}
	
	public static BufferedImage load(String src){
		if(!loadedImages.containsKey(src)){
			BufferedImage img = null;
			try {
				img = ImageIO.read(Art.class.getResource(src));
				loadedImages.put(src, img);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return loadedImages.get(src);
	}
	
	public static BufferedImage[] cut(String s, int spw, int sph){
		String arr = "arr_" + s;
		if(!loadedImgArrays.containsKey(arr)){
			BufferedImage src = null;
			try {
				src = ImageIO.read(Art.class.getResource(s));
			} catch (IOException e) {
				e.printStackTrace();
			}
			int framesX = src.getWidth() / spw;
			int framesY = src.getHeight() / sph;
			
			BufferedImage[] imgs = new BufferedImage[framesX * framesY];
			
			for(int i = 0; i < framesX; i++){
				for(int j = 0; j < framesY; j++){
					imgs[i + j * framesX] = src.getSubimage(i * spw, j * sph, spw, sph);
				}
			}
			loadedImgArrays.put(arr, imgs);
		}
		return loadedImgArrays.get(arr);
	}
	
	public static BufferedImage[][] loadList(String folder, int spw, int sph){
		File[] files = (new File(Art.class.getResource(folder).getPath())).listFiles();
		BufferedImage[][] list = new BufferedImage[files.length][];
		for(int f = 0; f < files.length; f++){
			if(files[f].isFile()) list[f] = cut(folder + files[f].getName(), spw, sph);
		}
		return list;
	}
	
	public static BufferedImage[][] loadNumberedList(String folder, int spw, int sph){
		File[] files = (new File(Art.class.getResource(folder).getPath())).listFiles();
		BufferedImage[][] list = new BufferedImage[files.length][];
		
		List<File> all = new ArrayList<File>(Arrays.asList(files));
		Collections.sort(all, new Comparator<File>(){
			public int compare(File arg0, File arg1) {
				int fnum1 = Integer.parseInt(arg0.getName().substring(0, arg0.getName().indexOf('.')));
				int fnum2 = Integer.parseInt(arg1.getName().substring(0, arg1.getName().indexOf('.')));
				if(fnum1 < fnum2) return -1;
				if(fnum1 > fnum2) return 1;
				return 0;
			}			
		});
		
		for(int f = 0; f < all.size(); f++){
			if(all.get(f).isFile()) list[f] = cut(folder + all.get(f).getName(), spw, sph);
		}
		
		return list;
	}
	
	public static BufferedImage recolor(BufferedImage src, int col){
		String recol = "col:" + col + "img:" + src.hashCode();
		if(!loadedImages.containsKey(recol)){
			BufferedImage newimg = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_ARGB);
			for(int i = 0; i < newimg.getWidth(); i++){
				for(int j = 0; j < newimg.getHeight(); j++){
					if(src.getRGB(i, j) >> 24 != 0) newimg.setRGB(i, j, additiveColor(src.getRGB(i, j), col));
				}
			}
			loadedImages.put(recol, newimg);
		}
		return loadedImages.get(recol);
	}
	
	public static int additiveColor(int c1, int c2) {
		return (((c1&0xfefeff)+(c2&0xfefeff))>>1) + 0xff000000;
	}
	
}
