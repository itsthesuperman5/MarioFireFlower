import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * 
 */

/**
 * @author stephenwright
 *
 */
public class gameOver extends BasicGameState{
	
	private boolean beaten;
	private int stateId;
	
	public gameOver(int stateID)
	{
		this.stateId = stateID;
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		// TODO Auto-generated method stub
		beaten = false;
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g)
			throws SlickException {
		// TODO Auto-generated method stub
		if(beaten)
			g.drawString("Congratulations! You win!", 300, 300);
		else

			g.drawString("Game Over", 300, 300);
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		// TODO Auto-generated method stub
		if(MFFdriver.marioLives > 0)
			beaten = true;
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}

}
