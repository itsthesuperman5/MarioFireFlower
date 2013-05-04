import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * 
 */

/**
 * @author stephenwright
 *
 */
public class fireBall
{
	private SpriteSheet fireball;
	private Image fire1, fire2, fire3, fire4;
	private Image fire1a, fire2a, fire3a, fire4a;
	private Image[] firing;
	private Animation spitting;
	private String direction;
	
	public fireBall(String dir) throws SlickException
	{
		fireball = new SpriteSheet("res/fireball.png", 10, 20);
		fire1 = fireball.getSprite(0, 0);
		fire1a = fire1.getScaledCopy(2);
		fire2 = fireball.getSprite(1, 0);
		fire2a = fire2.getScaledCopy(2);
		fire3 = fireball.getSprite(2, 0);
		fire3a = fire3.getScaledCopy(2);
		fire4 = fireball.getSprite(3, 0);
		fire4a = fire4.getScaledCopy(2);
		firing = new Image[4];
		firing[0] = fire1a;
		firing[1] = fire2a;
		firing[2] = fire3a;
		firing[3] = fire4a;
		spitting = new Animation(firing, 100);
		direction = dir;
	}
	
	public Image getFireImage()
	{
		return fire1a;
	}
	public Animation getFireball()
	{
		return spitting;
	}
	
	public void setDir(String a)
	{
		direction = a;
	}
	
	public String getDir()
	{
		return direction;
	}
}
