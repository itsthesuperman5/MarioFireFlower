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
	public static final int LEVEL2STATE = 2;
	public static final int LEVEL3STATE = 3;
	public static final int LEVEL4STATE = 4;
	public static final int LEVEL5STATE = 5;
	//public static final int GAMEOVERSTATE = 6;
	public static final int BETWEENLEVELSTATE = 9;
	public static int currentState = 1;
	public static int marioLives = 5;

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
		this.addState(new menuState(MENUSTATE));
		this.addState(new GameState(GAMESTATE));
		this.addState(new level2state(LEVEL2STATE));
		this.addState(new level3state(LEVEL3STATE));
		this.addState(new level4state(LEVEL4STATE));
		this.addState(new level5state(LEVEL5STATE));
		//this.addState(new gameOver(GAMEOVERSTATE));
		this.addState(new betweenLevels(BETWEENLEVELSTATE));
	}

}
