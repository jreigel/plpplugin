package plp_plugin.views;

import org.eclipse.swt.graphics.Image;
/** 
 * This class is the child of the Input class.
 * This class represents an LED light and has the ability to switch on and off with the flip() method.   
 * 
* @author Reigel, Justin
*/
public class LEDs extends Input{
	
	Image image = new Image(null, this.getClass().getResourceAsStream("/icons/LEDOff.png")); 
	boolean flipped = false;
	int number;

	public LEDs(int i) {
		super(i);
		number = i;
	}
	@Override
	public void flip()
	{
		flipped = !flipped;
		if(flipped){
			image = new Image(null, this.getClass().getResourceAsStream("/icons/LEDOn.png"));
		}
		else{
			image = new Image(null, this.getClass().getResourceAsStream("/icons/LEDOff.png"));
		}
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
