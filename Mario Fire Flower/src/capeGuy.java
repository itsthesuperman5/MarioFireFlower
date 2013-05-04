import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;


public class capeGuy {
	private Image flyRight1, flyRight2, flyLeft1, flyLeft2;
	private Image flyRight1a, flyRight2a, flyLeft1a, flyLeft2a;
	private Image die1, die2;
	private Image die1a, die2a;
	private Image[] flyingRight, flyingLeft, dying;
	private SpriteSheet capeDude;
	private Animation rightFly, leftFly, die;
	
	public capeGuy() throws SlickException
	{
		capeDude = new SpriteSheet("res/capeGuy.png", 32, 32);
		flyRight1 = capeDude.getSprite(0, 0);
		flyRight2 = capeDude.getSprite(1, 0);
		flyLeft1 = flyRight1.getFlippedCopy(true, false);
		flyLeft2 = flyRight2.getFlippedCopy(true, false);
		flyRight1a = flyRight1.getScaledCopy(2);
		flyRight2a = flyRight2.getScaledCopy(2);
		flyLeft1a = flyLeft1.getScaledCopy(2);
		flyLeft2a = flyLeft2.getScaledCopy(2);
		die1 = capeDude.getSprite(2, 0);
		die2 = capeDude.getSprite(3, 0);
		die1a = die1.getScaledCopy(2);
		die2a = die2.getScaledCopy(2);
		flyingRight = new Image[2];
		flyingRight[0] = flyRight1a;
		flyingRight[1] = flyRight2a;
		flyingLeft = new Image[2];
		flyingLeft[0] = flyLeft1a;
		flyingLeft[1] = flyLeft2a;
		dying = new Image[2];
		dying[0] = die1a;
		dying[1] = die2a;
		rightFly = new Animation(flyingRight, 200);
		leftFly = new Animation(flyingLeft, 200);
		die = new Animation(dying, 200);
	}
	
	public Animation getRight()
	{
		return rightFly;
	}
	
	public Animation getLeft()
	{
		return leftFly;
	}
	
	public Animation getDead()
	{
		return die;
	}
}
