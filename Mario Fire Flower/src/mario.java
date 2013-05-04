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
public class mario 
{
	private Image standingRight, standingLeft;
	private Image jumpRight, jumpLeft;
	private Image crouchRight, crouchLeft;
	private Image mario1, mario1a, mario1b;
	private Image mario2, mario2a, mario2b;
	private Image mario3, mario3a, mario3b;
	private Image mario4, mario4a, mario4b;
	private Image mario5, mario5a, mario5b;
	private Image mario6, mario6a, mario6b;
	private Image mario7, mario7a, mario7b;
	private Image[] wlkRight;
	private Image[] wlkLeft;
	private SpriteSheet mario;
	private Animation walkingRight;
	private Animation walkingLeft;
	
	public mario() throws SlickException
	{
		mario = new SpriteSheet("res/marioSprite.png", 32, 32);
		mario1 = mario.getSprite(0, 0);
		mario1a = mario1.getScaledCopy(2);
		mario1b = mario1a.getFlippedCopy(true, false);
		mario2 = mario.getSprite(1, 0);
		mario2a = mario2.getScaledCopy(2);
		mario2b = mario2a.getFlippedCopy(true, false);
		mario3 = mario.getSprite(2, 0);
		mario3a = mario3.getScaledCopy(2);
		mario3b = mario3a.getFlippedCopy(true, false);
		mario4 = mario.getSprite(3, 0);
		mario4a = mario4.getScaledCopy(2);
		mario4b = mario4a.getFlippedCopy(true, false);
		mario5 = mario.getSprite(4, 0);
		mario5a = mario5.getScaledCopy(2);
		mario5b = mario5a.getFlippedCopy(true, false);
		mario6 = mario.getSprite(5, 0);
		mario6a = mario6.getScaledCopy(2);
		mario6b = mario6a.getFlippedCopy(true, false);
		wlkRight = new Image[2];
		wlkRight[0] = mario1a;
		wlkRight[1] = mario2a;
		wlkLeft = new Image[2];
		wlkLeft[0] = mario1b;
		wlkLeft[1] = mario2b;
		walkingRight = new Animation(wlkRight, 200);
		walkingLeft = new Animation(wlkLeft, 200);
		jumpRight = mario4a;
		jumpLeft = mario4b;
		crouchRight = mario5a;
		crouchLeft = mario5b;
		standingRight = mario1a;
		standingLeft = mario1b;
	}
	
	public Image standRight()
	{
		return standingRight;
	}
	
	public Image standLeft()
	{
		return standingLeft;
	}
	
	public Image rightCrouch()
	{
		return crouchRight;
	}
	
	public Image leftCrouch()
	{
		return crouchLeft;
	}
	
	public Image rightJump()
	{
		return jumpRight;
	}
	
	public Image leftJump()
	{
		return jumpLeft;
	}
	
	public Animation rightWalk()
	{
		return walkingRight;
	}
	
	public Animation leftWalk()
	{
		return walkingLeft;
	}
}
