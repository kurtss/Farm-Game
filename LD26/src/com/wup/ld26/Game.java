package com.wup.ld26;

import java.awt.event.KeyEvent;
import java.util.List;

import com.wup.ld26.farm.Farm;
import com.wup.ld26.farm.Farmland;
import com.wup.ld26.farm.Seed;
import com.wup.ld26.plant.Plant;
import com.wup.ld26.render.Art;
import com.wup.ld26.render.Screen;
import com.wup.ld26.village.Village;

public class Game {

	public static final String TITLE = "LD26";
	public static final int WIDTH = 640 / 2;
	public static final int HEIGHT = 480 / 2;
	public static final int SCALE = 2;
	public static boolean DEBUG = false;
	public static final int HALFWIDTH = WIDTH / 2;
	public static final int HALFHEIGHT = HEIGHT / 2;
	public Input input;
	
	public Village village;
	public Farm farm;
	private int camX, camY;
	
	private Seed[] seeds = new Seed[8];
	private int[] indX = new int[8];
	private int[] indY = new int[8];
	private Seed selected;
	
	public void init(){
		village = new Village(88, 88);
		farm = new Farm((WIDTH - 96) / 16 * 8, HEIGHT / 16 * 8);
		for(int s = 0; s < seeds.length; s++){
			seeds[s] = new Seed();
		}

		for(int i = 0; i < seeds.length; i++){
			if(seeds[i] != null){
				seeds[i].invIndex = i;
				indX[i] = i * 8 + WIDTH - 96;
				indY[i] = 64;
			}
			if(seeds[seeds.length - i - 1] != null){
				seeds[seeds.length - i - 1].invIndex = seeds.length - i - 1;
				indX[seeds.length - i - 1] = i * 8 + WIDTH - 96;
				indY[seeds.length - i - 1] = 72;
			}
		}
	}
	
	public void update(){
		farm.tick();
		village.tick();
	}
	
	public void draw(Screen s){
		s.fill(0, 0, WIDTH - 96, HEIGHT, 0x6B4A35);
		List<Farmland> inArea = farm.farmlandIn(camX, camY, camX + WIDTH - 96, camY + HEIGHT);
		s.xOffset = camX;
		s.yOffset = camY;
		for(int i = (int)(camX / 16f); i <= (int)((camX + WIDTH - 96) / 16f); i++){
			for(int j = (int)(camY / 16f); j <= (int)((camY + HEIGHT) / 16f); j++){
				if(farm.weedsAt(i, j)) s.draw(Art.load("/res/weeds.png"), i * 16, j * 16);
			}
		}
		for(int f = 0; f < inArea.size(); f++){
			inArea.get(f).render(s);
		}
		s.xOffset = s.yOffset = 0;
		s.fill(WIDTH - 96, 0, 96, HEIGHT, 0x916548);
		s.string("Fields: " + farm.farmlandIn(0, 0, farm.width * 16, farm.height * 16).size(), WIDTH - 88, 8, 0xffffff);
		s.string("Pop: " + village.getPopulation(), WIDTH - 88, 20, 0xffffff);
		
		s.string("Village", WIDTH - 96 + ("Village".length() * 6) / 2, HEIGHT - 120, 0xffffff);
		
		s.string("Seeds", WIDTH - (96 + ("Seeds".length() * 6)) / 2, 48, 0xffffff);
		
		for(int i = 0; i < seeds.length; i++){
			if(seeds[i] != null){
				if(seeds[i] == selected) s.fill(indX[i], indY[i], 8, 8, 0x634431);
				s.fill(indX[i], indY[i], seeds[i].size, seeds[i].size, seeds[i].color);
			}
		}
		
		s.xOffset = -WIDTH + 93;
		s.yOffset = -HEIGHT + 106;
		village.render(s);
		s.xOffset = s.yOffset = 0;
	}	
	
	public void stop(){

	}
	
	public void keyDown(int key){
		if(key == KeyEvent.VK_W && camY - 2 >= 0) {
			camY -= 2;
		}
		if(key == KeyEvent.VK_S && camY + 2 + HEIGHT <= farm.height * 16) {
			camY += 2;
		}
		if(key == KeyEvent.VK_A && camX - 2 >= 0) {
			camX -= 2;
		}
		if(key == KeyEvent.VK_D && camX + 2 + WIDTH - 96 <= farm.width * 16) {
			camX += 2;
		}
		/*if(camX < 0) camX = 0;
		if(camY < 0) camY = 0;
		if(camX + WIDTH - 96 > farm.width * 16) camX = (farm.width * 16) - WIDTH - 96;
		if(camY + HEIGHT > farm.height * 16) camY = (farm.height * 16) - HEIGHT;*/
	}
	
	public void keyTyped(int key, char keychar){
		
	}
	
	public void leftMouseClick(int x, int y){
		if(x < WIDTH - 96){
			if(!farm.tillLand(x + camX - 8, y + camY - 8)){
				if(farm.farmlandAt(x + camX, y + camY) != null && farm.farmlandAt(x + camX, y + camY).plant != null){
					village.feed(farm.farmlandAt(x + camX, y + camY).plant.getFood());
					seeds[getIndexFor(null)] = new Seed((farm.farmlandAt(x + camX, y + camY).plant));
					farm.farmlandAt(x + camX, y + camY).plant = null;
				}
			}
		}else{
			Seed at = null;
			for(int i = 0; i < seeds.length; i++){
				if(seeds[i] != null){
					if(x >= indX[i] && x <= indX[i] + 8 && y >= indY[i] && y <= indY[i] + 8){
						at = seeds[i];
						break;
					}
				}
			}
			if(at != null) selected = at;
			else if(selected != null && selected == at) selected = null;
		}
	}
	
	public void rightMouseClick(int x, int y){
		if(x < WIDTH - 96){
			if(selected != null){
				if(farm.farmlandAt(x + camX, y + camY) != null && farm.farmlandAt(x + camX, y + camY).plant == null){
					farm.farmlandAt(x + camY, y + camY).setPlant(new Plant(selected));
					seeds[getIndexFor(selected)] = null;
					selected = null;
				}
			}
		}else{
			Seed at = null;
			for(int i = 0; i < seeds.length; i++){
				if(seeds[i] != null){
					if(x >= indX[i] && x <= indX[i] + 8 && y >= indY[i] && y <= indY[i] + 8){
						at = seeds[i];
						break;
					}
				}
			}
			if(selected != null && at != null){
				seeds[getIndexFor(at)] = selected.mixWith(at);
				seeds[getIndexFor(selected)] = null;
				selected = null;
			}
		}
	}
	
	private int getIndexFor(Seed s){
		for(int i = 0; i < seeds.length; i++){
			if(seeds[i] == s) return i;
		}
		return 0;
	}
	
	private Seed getSeedAtMouse(int x, int y) {
		
	}
	
}
