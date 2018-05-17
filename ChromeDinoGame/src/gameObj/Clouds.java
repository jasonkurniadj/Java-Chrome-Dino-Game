package gameObj;

import java.awt.Graphics;
import java.awt.image.*;
import java.util.*;

import main.*;
import util.*;

/***
 * 
 * @author Jason.
 *
 */
public class Clouds {
	private List<ImageCloud> listCloud;
	private BufferedImage cloud;
	
	private MainCharacter mainCharacter;
	
	public Clouds(int width, MainCharacter mainCharacter) {
		this.mainCharacter = mainCharacter;
		cloud = Resource.getResouceImage("assets/image/cloud.png");
		listCloud = new ArrayList<ImageCloud>();
		
		ImageCloud imageCloud = new ImageCloud();
		imageCloud.posX = 100;
		imageCloud.posY = 50;
		listCloud.add(imageCloud);
		
		imageCloud = new ImageCloud();
		imageCloud.posX = 300;
		imageCloud.posY = 30;
		listCloud.add(imageCloud);
		
		imageCloud = new ImageCloud();
		imageCloud.posX = 600;
		imageCloud.posY = 60;
		listCloud.add(imageCloud);
	}
	
	public void update(){
		Iterator<ImageCloud> iter = listCloud.iterator();
		ImageCloud firstElement = iter.next();
		firstElement.posX -= mainCharacter.getSpeedX()/8;
		
		while(iter.hasNext()) {
			ImageCloud element = iter.next();
			element.posX -= mainCharacter.getSpeedX()/8;
		}
		
		if(firstElement.posX < -cloud.getWidth()) {
			listCloud.remove(firstElement);
			firstElement.posX = Main.SCREEN_WIDTH;
			listCloud.add(firstElement);
		}
	}
	
	public void draw(Graphics g) {
		for(ImageCloud imgLand : listCloud)
			g.drawImage(cloud, (int) imgLand.posX, imgLand.posY, null);
	}
	
	private class ImageCloud {
		float posX;
		int posY;
	}
}
