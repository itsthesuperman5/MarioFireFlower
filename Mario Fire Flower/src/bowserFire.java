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
public class bowserFire {
	private SpriteSheet fireball;
	private Image fire1, fire2, fire3;
	private Image[] firing;
	private Animation spitting;
	
	public bowserFire() throws SlickException
	{
		fireball = new SpriteSheet("res/bowserFireSheet.png", 20, 20);
		fire1 = fireball.getSprite(0, 0);
		fire2 = fireball.getSprite(1, 0);
		fire3 = fireball.getSprite(2, 0);
		firing = new Image[3];
		firing[0] = fire1.getScaledCopy(2);
		firing[1] = fire2.getScaledCopy(2);
		firing[2] = fire3.getScaledCopy(2);
		spitting = new Animation(firing, 100);
	}
	
	public Image getFireImage()
	{
		return fire1;
	}
	public Animation getFireball()
	{
		return spitting;
	}
}
