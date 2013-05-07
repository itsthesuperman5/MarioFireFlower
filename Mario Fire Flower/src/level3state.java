import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.tiled.TiledMap;

/**
 * 
 */

/**
 * @author stephenwright
 *
 */
public class level3state extends BasicGameState {
	private static final int DELAY1 = 2000;
	private static final int SHOTDELAY = 400;
	private int initialDelay = 5000;
	private boolean initialString;
	private int elapsedTime = 0;
	private int elapsedShotTime = 3001;
	int stateID, shotCount, enemyCount, shotDelay;
	TiledMap map;
	Image world;
	Image sheet;
	float playerX, playerY, Ground;
	float mapX, mapY;
	float marioVelocity, marioLift, marioAccel, marioFall;
	float spitVelocity;
	Rectangle marioBound;
	ArrayList<Rectangle> shotBound;
	ArrayList<Rectangle> enemyBound;
	ArrayList<capeGuy> capes;
	ArrayList<fireBall> fireballs;
	private fireBall fireball;
	boolean left, right, moving, crouched, jumping, shooting;
	boolean shotLeft, shotRight, shotUpRight, shotUpLeft;
	boolean falling;
	boolean drawRectangles;
	boolean[][] ground;
	Rectangle[][] rect;
	Shape[] shape;
	int shapeCount;
	Sound fireballFX, jumpFX, hitFX;
	mario player;
	Music overworld;
	private float enemyVelocity;
	private Random rand;
	private boolean[][] endGrid;
	private boolean[][] baddyGrid;
	private boolean playerDead;
	private boolean victorious;
	private boolean victoryPlayed;
	private Music victory;
	
	public level3state(int stateID)
	{
		this.stateID = stateID;
	}
	
	public void enter(GameContainer gc, StateBasedGame sbg)
	{
		overworld.loop();
		playerDead = false;
		victorious = false;
		initialString = true;
		victoryPlayed = false;
		mapY = -2560;
		playerY = 3000;
	}
	
	public void leave(GameContainer gc, StateBasedGame sbg)
	{
		overworld.stop();
		shotBound.clear();
		enemyBound.clear();
		capes.clear();
		fireballs.clear();
	}
	
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		// TODO Auto-generated method stub
		victory = new Music("res/marioVictory.wav");
		playerDead = false;
		victorious = false;
		victoryPlayed = false;
		rand = new Random();
		player = new mario();
		fireball = new fireBall("up");
		left = false;
		moving = false;
		right = true;
		crouched = false;
		shooting = false;
		shotLeft = false;
		shotUpRight = false;
		shotUpLeft = false;
		shotRight = false;
		falling = false;
		drawRectangles = false;
		initialString = true;
		marioVelocity = 0.3f;
		enemyVelocity = 0.25f;
		marioLift = 0;
		marioAccel = 0.3f;
		spitVelocity = 0.8f;
		marioFall = 0.5f;
		shotCount = 0;
		enemyCount = 0;
		gc.setMinimumLogicUpdateInterval(20);
		fireballFX = new Sound("res/fireball.wav");
		jumpFX = new Sound("res/jump.wav");
		hitFX = new Sound("res/hit.wav");
		map = new TiledMap("res/level3.tmx");
		overworld = new Music("res/drmario.wav");
		playerX = 30; 
		playerY = 3000;
		mapX = 0;
		mapY = -2560;
		shapeCount = 0;
		Ground = 501;
		enemyBound = new ArrayList<Rectangle>();
		capes = new ArrayList<capeGuy>();
		fireballs = new ArrayList<fireBall>();
		shotBound = new ArrayList<Rectangle>();
		marioBound = new Rectangle(playerX, playerY, player.standRight().getWidth()-32, player.standRight().getHeight());
		ground = new boolean[map.getWidth()][map.getHeight()];
		rect = new Rectangle[map.getWidth()][map.getHeight()];
		baddyGrid = new boolean[map.getWidth()][map.getHeight()];
		endGrid = new boolean[map.getWidth()][map.getHeight()];
		shape = new Shape[10000];
		for (int x=0;x<map.getWidth();x++) 
		{
			for (int y=0;y<map.getHeight();y++)
			{
				int tileID = map.getTileId(x, y, 2);
				String value = map.getTileProperty(tileID, "blocked", "false");
				String value1 = map.getTileProperty(tileID, "end", "false");
				String value2 = map.getTileProperty(tileID, "baddy", "false");
				if ("true".equals(value))
				{
					ground[x][y] = true;
					rect[x][y] = new Rectangle((float)(x*32), (float)(y*32), map.getTileWidth(), map.getTileHeight());
					shape[shapeCount] = rect[x][y];
					shapeCount++;
				}
				if("true".equals(value1))
				{
					endGrid[x][y] = true;
				}
				if("true".equals(value2))
				{
					baddyGrid[x][y] = true;
				}
			}
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		// TODO Auto-generated method stub
		map.render((int)mapX, (int)mapY, 0);
		map.render((int)mapX, (int)mapY, 1);
		if(initialString)
		{
			g.setColor(org.newdawn.slick.Color.black);
			g.fillRect(10, 10, 700, 100);
			g.setColor(org.newdawn.slick.Color.white);
			g.drawString("Time to climb Bowser's tower!", 50, 25);
			g.drawString("Beat the crawl!", 50, 50);
			g.drawString("Bowser awaits...", 50, 75);
		}
		if(drawRectangles)
		{
			g.draw(marioBound);
			if(!enemyBound.isEmpty())
			{
				for(int i = 0; i < capes.size(); i++)
				{
					g.draw(enemyBound.get(i));
				}
			}
			if(!shotBound.isEmpty())
			{
				for(int i = 0; i < shotBound.size(); i++)
				{
					g.draw(shotBound.get(i));
				}
			}
		}
		if(!capes.isEmpty())
		{
			for(int i = 0; i < capes.size(); i++)
			{
				if(enemyBound.get(i).getX() >= marioBound.getX())
					capes.get(i).getLeft().draw(enemyBound.get(i).getX()+mapX, enemyBound.get(i).getY()+mapY-16);
				else
					capes.get(i).getRight().draw(enemyBound.get(i).getX()+mapX, enemyBound.get(i).getY()+mapY-16);
			}
		}
		if(shooting)
		{
			for(int i = 0; i < fireballs.size(); i++)
			{
				fireballs.get(i).getFireball().draw(shotBound.get(i).getX()+mapX, shotBound.get(i).getY()+mapY-16);
			}
		}
		if(jumping && right)
		{
			player.rightJump().draw(marioBound.getX()+mapX-16, marioBound.getY()+mapY);
		}
		else if(jumping && left)
		{
			player.leftJump().draw(marioBound.getX()+mapX-16, marioBound.getY()+mapY);
		}
		else if(crouched && right)
		{
			player.rightCrouch().draw(marioBound.getX()+mapX-16, marioBound.getY()+mapY);
		}
		else if(crouched && left)
		{
			player.leftCrouch().draw(marioBound.getX()+mapX-16, marioBound.getY()+mapY);
		}
		else if(!moving && right)
		{
			player.standRight().draw(marioBound.getX()+mapX-16, marioBound.getY()+mapY);
		}
		else if(!moving && left)
		{
			player.standLeft().draw(marioBound.getX()+mapX-16, marioBound.getY()+mapY);
		}
		else if(moving && right)
		{
			player.rightWalk().draw(marioBound.getX()+mapX-16, marioBound.getY()+mapY);
		}
		else if(moving && left)
		{
			player.leftWalk().draw(marioBound.getX()+mapX-16, marioBound.getY()+mapY);
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		// TODO Auto-generated method stub
		if((marioBound.getY()*-1)-mapY < -700)
			playerDead = true;
		if (initialDelay > 0)
		{
			initialDelay -= delta;
		}
		else
		{
			initialString = false;
		}
			elapsedTime += delta;
		  if (elapsedTime >= DELAY1 && !victorious && !playerDead)
		  {
			  elapsedTime = 0;
			  //sendEnemy();
		  }
		  if(playerDead)
		  {
			  sbg.enterState(9, new FadeOutTransition(), new FadeInTransition());
		  }
		  if(!playerDead && endGrid[(int)marioBound.getCenterX()/32][(int)marioBound.getCenterY()/32])
			{
				victorious = true;
			}
			if(victorious)
			{
				System.out.println("Victorious");
				overworld.stop();
				if(!victoryPlayed)
				{
					victory.play();
					victoryPlayed = true;
				}
				else
				{
					if(!victory.playing())
					{
						MFFdriver.currentState++;
						sbg.enterState(MFFdriver.currentState, new FadeOutTransition(), new FadeInTransition());
					}
						
				}
			}
			else{
		Input input = gc.getInput();
		float hip = marioVelocity*delta;
		float ship = spitVelocity*delta;
		float fhip = marioFall*delta;
		float eip = enemyVelocity*delta;
		mapY += .04f * delta;
		//if(marioBound.getY()+mapY >= 530)
		{
			//marioBound.setX(30);
			//marioBound.setY(300);
			//mapX = 0;
			//mapY = 0;
		}
		if(fireballs.isEmpty() || shotBound.isEmpty())
			shooting = false;
		if(!enemyBound.isEmpty())
		{
			for(int i = 0; i < enemyBound.size(); i++)
			{
				if(hitEnemy(enemyBound.get(i), marioBound))
					playerDead = true;
				if(enemyBound.get(i).getX() >= marioBound.getX())
					enemyBound.get(i).setX(enemyBound.get(i).getX() - eip);
				else
					enemyBound.get(i).setX(enemyBound.get(i).getX() + eip);
				if(enemyBound.get(i).getY() >= marioBound.getY())
					enemyBound.get(i).setY(enemyBound.get(i).getY() - eip);
				else
					enemyBound.get(i).setY(enemyBound.get(i).getY() + eip);
				if(shooting)
				{
					for(int j = 0; j < shotBound.size(); j++)
					{
						if(hitEnemy(enemyBound.get(i), shotBound.get(j)))
						{
							hitFX.play();
							enemyBound.remove(i);
							capes.remove(i);
							shotBound.remove(j);
							fireballs.remove(j);
						}
					}
				}
			}
		}
		if(input.isKeyPressed(Input.KEY_R))
		{
			if(!drawRectangles)
				drawRectangles = true;
			else
				drawRectangles = false;
		}
		if(!jumping && !hitShape(marioBound, shape))
		{
			marioBound.setY(marioBound.getY()+fhip);
			falling = true;
		}

		if(shooting)
		{
			for(int i = 0; i < fireballs.size(); i++)
			{
				if(fireballs.get(i).getDir().equalsIgnoreCase("right"))
				{
					shotBound.get(i).setX(shotBound.get(i).getX()+ship);
				}
				if(fireballs.get(i).getDir().equalsIgnoreCase("left"))
				{
					shotBound.get(i).setX(shotBound.get(i).getX()-ship);
				}
				if(fireballs.get(i).getDir().equalsIgnoreCase("upRight"))
				{
					shotBound.get(i).setX(shotBound.get(i).getX()+ship);
					shotBound.get(i).setY(shotBound.get(i).getY()-ship);
				}
				if(fireballs.get(i).getDir().equalsIgnoreCase("upLeft"))
				{
					shotBound.get(i).setX(shotBound.get(i).getX()-ship);
					shotBound.get(i).setY(shotBound.get(i).getY()-ship);
				}
			}
			
		}
		
		elapsedShotTime += delta;
		if (elapsedShotTime >= SHOTDELAY)
		{
			if((input.isKeyDown(Input.KEY_SPACE) || input.isButton1Pressed(0)) &&(input.isKeyDown(Input.KEY_UP) || input.isControllerUp(0))&&(right))
			{
				fireballFX.play();
				fireballs.add(new fireBall("upRight"));
				shotBound.add(new Rectangle(marioBound.getCenterX(), marioBound.getCenterY(),
						fireball.getFireImage().getWidth(), fireball.getFireImage().getHeight()-32));
				elapsedShotTime = 0;
				shooting = true;
			}
			else if((input.isKeyDown(Input.KEY_SPACE) || input.isButton1Pressed(0)) &&(input.isKeyDown(Input.KEY_UP) || input.isControllerUp(0))&&(left))
			{
				fireballFX.play();
				fireballs.add(new fireBall("upLeft"));
				shotBound.add(new Rectangle(marioBound.getCenterX(), marioBound.getCenterY(),
						fireball.getFireImage().getWidth(), fireball.getFireImage().getHeight()-32));
				elapsedShotTime = 0;
				shooting = true;
			}
			else if((input.isKeyDown(Input.KEY_SPACE) || input.isButton1Pressed(0)) &&(right))
			{
				fireballFX.play();
				fireballs.add(new fireBall("right"));
				shotBound.add(new Rectangle(marioBound.getCenterX(), marioBound.getCenterY(),
						fireball.getFireImage().getWidth(), fireball.getFireImage().getHeight()-32));
				elapsedShotTime = 0;
				shooting = true;
			}
			else if((input.isKeyDown(Input.KEY_SPACE) || input.isButton1Pressed(0)) &&(left))
			{
				fireballFX.play();
				fireballs.add(new fireBall("left"));
				shotBound.add(new Rectangle(marioBound.getCenterX(), marioBound.getCenterY(),
						fireball.getFireImage().getWidth(), fireball.getFireImage().getHeight()-32));
				elapsedShotTime = 0;
				shooting = true;
			}
		}
		
		
		if(input.isKeyDown(Input.KEY_DOWN) || input.isControllerDown(0))
		{
			moving = false;
			crouched = true;
		}
		else
			crouched = false;
		if(jumping && marioLift >= 0)
		{
			marioBound.setY(marioBound.getY()-marioLift*delta);
			marioLift -= 0.04f;	
		}
		else if(jumping && marioLift < 0)
		{
			//if(((int)(((marioBound.getY()+player.standRight().getHeight())-(marioLift*delta))/32) < 19) && (int)marioBound.getX()/32 > -1){
			if (ground[(int)marioBound.getX()/32][((int)(((marioBound.getY()+player.standRight().getHeight())-(marioLift*delta))/32))] != true)
			{
				marioBound.setY(marioBound.getY()-marioLift*delta);
				marioLift -= 0.04f;		
			}
			else
				jumping = false;
			//}
		}
		if(input.isKeyPressed(Input.KEY_UP) || input.isButton2Pressed(0) && !jumping)
		{
			jumping = true;
			jumpFX.play();
			marioLift = 0.6f;
		}
		if(input.isKeyDown(Input.KEY_RIGHT) || input.isControllerRight(0) && !crouched)
		{
			left = false;
			right = true;
			moving = true;
			marioBound.setX(marioBound.getX()+hip);
			//mapX -= hip;
		}
		else if(input.isKeyDown(Input.KEY_LEFT) || input.isControllerLeft(0) && !crouched && marioBound.getX() > 0)
		{
			right = false;
			left = true;
			moving = true;
			marioBound.setX(marioBound.getX()-hip);
			//mapX += hip;
		}
		else
			moving = false;
			}
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return stateID;
	}
	
	public boolean hitShape(Rectangle a, Shape[] b)
	{
		boolean hit = false;
		for(int i=0; i < shapeCount; i++)
		{
			if(a.intersects(b[i]))
				hit = true;
		}
		return hit;
	}
	
	public boolean hitEnemy(Rectangle a, Rectangle b)
	{
		boolean hit = false;
		if(a.intersects(b))
			hit = true;
		return hit;
	}

	public void sendEnemy() throws SlickException{
		capes.add(new capeGuy());
		switch(rand.nextInt(2))
		{
		case 0 :
			enemyBound.add(new Rectangle(marioBound.getX()+500, rand.nextInt(600)+mapY, capes.get(0).getRight().getWidth(), capes.get(0).getRight().getHeight()-32));
			break;
		case 1 :
			enemyBound.add(new Rectangle(marioBound.getX()-350, rand.nextInt(600)+mapY, capes.get(0).getRight().getWidth(), capes.get(0).getRight().getHeight()-32));
			break;
		}
		
	}
}
