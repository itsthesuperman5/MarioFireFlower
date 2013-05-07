import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

/**
 * 
 */

/**
 * @author stephenwright
 *
 */
public class betweenLevels extends BasicGameState{
	
	private int delay1, delay2, stateID;
	private String string;
	private boolean nextDelay;
	
	public betweenLevels(int stateID)
	{
		this.stateID = stateID;
	}
	
	@Override
	public void enter(GameContainer arg0, StateBasedGame arg1)
	{
		nextDelay = false;
		delay1 = 1500;
		delay2 = 1500;
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		// TODO Auto-generated method stub
		nextDelay = false;
		delay1 = 1500;
		delay2 = 1500;
		string = "Lives Left: "+MFFdriver.marioLives;
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g)
			throws SlickException {
		// TODO Auto-generated method stub
		g.drawString("Lives Left: "+MFFdriver.marioLives, 300, 300);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		// TODO Auto-generated method stub
		if(nextDelay)
		{
			if(delay2 < 0){
				if(MFFdriver.marioLives <= 0)
				sbg.enterState(6, new FadeOutTransition(), new FadeInTransition());
				else
				sbg.enterState(MFFdriver.currentState);
			}else
				delay2 -= delta;
		}
		else if(delay1 < 0)
		{
			MFFdriver.marioLives--;
			nextDelay = true;
		}
		else
			delay1 -= delta;
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return stateID;
	}

}
