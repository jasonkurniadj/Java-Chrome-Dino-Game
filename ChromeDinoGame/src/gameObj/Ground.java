package gameObj;

import java.util.*;
import java.awt.Graphics;
import java.awt.image.*;

import util.*;

/***
 * 
 * @author Jason.
 *
 */
public class Ground {
	public static final int LAND_POSY = 128; //SCREEN_WIDTH - 97
	
	private List<ImageLand> listLand;
	private BufferedImage ground1, ground2, ground3;
	
	private MainCharacter mainCharacter;
	
	public Ground(int width, MainCharacter mainCharacter) {
		this.mainCharacter = mainCharacter;
		
		ground1 = Resource.getResouceImage("assets/image/ground1.png");
		ground2 = Resource.getResouceImage("assets/image/ground2.png");
		ground3 = Resource.getResouceImage("assets/image/ground3.png");
		
		int numberOfImageLand = width / ground2.getWidth() + 2;
		listLand = new ArrayList<ImageLand>();
		
		for(int i=0; i<numberOfImageLand; i++) {
			ImageLand imageLand = new ImageLand();
			imageLand.posX = i * ground2.getWidth();
			
			setImageLand(imageLand);
			listLand.add(imageLand);
		}
	}
	
	public void update(){
		Iterator<ImageLand> itr = listLand.iterator();
		ImageLand firstElement = itr.next();
		firstElement.posX -= mainCharacter.getSpeedX();
		float previousPosX = firstElement.posX;
		
		while(itr.hasNext()) {
			ImageLand element = itr.next();
			element.posX = previousPosX + ground2.getWidth();
			previousPosX = element.posX;
		}
		
		if(firstElement.posX < -ground2.getWidth()) {
			listLand.remove(firstElement);
			firstElement.posX = previousPosX + ground2.getWidth();
			setImageLand(firstElement);
			listLand.add(firstElement);
		}
	}
	
	private void setImageLand(ImageLand imgLand) {
		int typeLand = getTypeOfLand();
		
		if(typeLand == 1)
			imgLand.image = ground2;
		else if(typeLand == 3)
			imgLand.image = ground3;
		else
			imgLand.image = ground1;
	}
	
	public void draw(Graphics g) {
		for(ImageLand imgLand : listLand)
			g.drawImage(imgLand.image, (int)imgLand.posX, LAND_POSY, null);
	}
	
	private int getTypeOfLand() {
		Random rand = new Random();
		int type = rand.nextInt(15);
		
		if(type == 1)
			return 1;
		else if(type == 14)
			return 3;
		else
			return 2;
	}
	
	private class ImageLand {
		float posX;
		BufferedImage image;
	}
}
