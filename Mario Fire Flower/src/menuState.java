import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.FadeInTransition;

/**
 * 
 */

/**
 * @author stephenwright
 *
 */
public class menuState extends BasicGameState{

	private int stateID;
	private Image splash, menu;
	private Music background;
	private Sound click;
	private int delay;
	private boolean played;
	
	public menuState(int stateID)
	{
		this.stateID = stateID;
	}
	
	public void leave(GameContainer gc, StateBasedGame sbg)
	{
		background.stop();
	}
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		// TODO Auto-generated method stub
		//splash = new Image("res/Splash.png");
		menu = new Image("res/menuBack.png");
		background = new Music("res/menu.wav");
		delay = 3500;
		played = false;
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		// TODO Auto-generated method stub
		if(delay <= 0)
			menu.drawCentered(400, 300);
		//else
			//splash.draw(0, 0);
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		// TODO Auto-generated method stub
		if(delay > 0)
			delay -= delta;
		else
		{
			if(!played)
			{
				background.play();
				played = true;
			}
		}
		Input input = gc.getInput();
		if(delay <= 0 && input.isKeyPressed(Input.KEY_ENTER)){
			background.stop();
			sbg.enterState(1, new EmptyTransition(), new FadeInTransition());
		}
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return stateID;
	}
}
