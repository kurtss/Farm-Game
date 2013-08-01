package com.wup.dd.level;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.wup.dd.entity.Entity;
import com.wup.dd.entity.Player;

public class Level {

	private static final int SKY_COLOR = 0xFF000097;
	
	public int width, height;
	private BufferedImage level;
	private List<Entity> entities, within;
	
	public Level(int w, int h) {
		width = w;
		height = h;
		level = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		entities = new ArrayList<Entity>();
		within = new ArrayList<Entity>();
	}
	
	public void tick() {
		for(int e = 0; e < entities.size(); e++) {
			Entity entity = entities.get(e);
			entity.tick();
		}
	}
	
	public boolean isValid(int x, int y) {
		return x >= 0 && y >= 0 && x < width && y < height;
	}
	
	public boolean isDirt(int x1, int y1, int x2, int y2) {
		for(int i = x1; i < x2; i++) {
			for(int j = y1; j < y2; j++) {
				if(getColor(i, j) == 0xFF000000) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean isPixelAtArea(int x, int y) {
		if(isValid(x, y)) {
			return level.getRGB(x, y) != 0;
		} else {
			return false;
		}
	}
	
	public void fillArea(int x1, int y1, int x2, int y2, int color) {
		for(int i = x1; i < x2; i++) {
			for(int j = y1; j < y2; j++) {
				setColor(i, j, color);
			}
		}
	}
	
	public void removeDirt(Player player, int x1, int y1, int x2, int y2) {
		if(y1 >= 32) {
			int dirtAmount = 0;
			for(int i = x1; i < x2; i++) {
				for(int j = y1; j < y2; j++) {
					if(getColor(i, j) != 0xFF000000) {
						setColor(i, j, 0xFF000000);
						dirtAmount++;
					}
					if(j % 2 == 0) {
						setColor(i - 1, j, 0xFF000000);
						setColor(i + 1, j, 0xFF000000);
					}
					if(i % 2 == 0) {
						setColor(i, j - 1, 0xFF000000);
						setColor(i, j + 1, 0xFF000000);
					}
				}
			}
			if(player != null) {
				player.addDugDirt(dirtAmount);
			}
		}
	}
	
	public void setColor(int x, int y, int color) {
		if(isValid(x, y)) {
			level.setRGB(x, y, color);
		}
	}
	
	public int getColor(int x, int y) {
		if(!isValid(x, y)) {
			return 0;
		} else {
			return level.getRGB(x, y);
		}
	}
	
	public void spawn(Entity e) {
		entities.add(e);
	}
	
	public void remove(Entity e) {
		entities.remove(e);
	}
	
	public List<Entity> getEntitiesWithin(int x1, int y1, int x2, int y2) {
		within.clear();
		for(int e = 0; e < entities.size(); e++) {
			Entity entity = entities.get(e);
			if(entity.intersects(x1, y1, x2, y2)) {
				within.add(entity);
			}
		}
		return within;
	}
	
	public BufferedImage getLevelImage() {
		return level;
	}
	
}
