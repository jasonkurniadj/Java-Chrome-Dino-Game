package util;

import javax.imageio.*;
import java.awt.image.*;
import java.io.*;

/***
 * 
 * @author Jason.
 *
 */
public class Resource {
	public static BufferedImage getResouceImage(String path) {
		BufferedImage img = null;
		
		try {
		    img = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return img;
	}
}
