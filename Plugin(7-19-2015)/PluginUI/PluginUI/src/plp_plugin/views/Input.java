package plp_plugin.views;

import org.eclipse.swt.graphics.Image;
/** 
 * This class is the parent of the LEDs and Switches classes.
 * Each of these classes will have a number, an image, and a way to switch states (flip).   
 * 
* @author Reigel, Justin
*/
public class Input {

	Image image; 
	boolean flipped;
	int number;

	public Input(int i) {
		// TODO Auto-generated constructor stub
		number = i;
	}
	public void flip()
	{
	}
	
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Image getImage(){
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
}
