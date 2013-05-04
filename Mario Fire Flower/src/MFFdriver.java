import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * 
 */

/**
 * @author Stephen Wright
 *
 */
public class MFFdriver extends StateBasedGame{
	
	public static final int MENUSTATE = 0;
	public static final int GAMESTATE = 1;

	public MFFdriver() {
		super("Mario Fire-Flower");
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 * @throws SlickException 
	 */
	public static void main(String[] args) throws SlickException {
		// TODO Auto-generated method stub
		AppGameContainer app = new AppGameContainer(new MFFdriver());
		app.setDisplayMode(800, 600, false);
		app.start();
		}
	

	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {
		// TODO Auto-generated method stub
		this.addState(new GameState(GAMESTATE));
	}

}
