


import java.util.ArrayList;
import java.util.Random;

import javax.swing.Timer;

import org.lwjgl.input.Controller;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

/**
 * 
 */

/**
 * @author stephenwright
 *
 */
public class GameState extends BasicGameState{

	private static final int DELAY1 = 2000;
	private static final int SHOTDELAY = 500;
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
	
	public GameState(int stateID)
	{
		this.stateID = stateID;
	}
	
	public void enter(GameContainer gc, StateBasedGame sbg)
	{
		overworld.loop();
	}
	
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		// TODO Auto-generated method stub
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
		map = new TiledMap("res/test.tmx");
		overworld = new Music("res/overworld.wav");
		playerX = 300; 
		playerY = 300;
		mapX = 0;
		mapY = 0;
		shapeCount = 0;
		Ground = 501;
		enemyBound = new ArrayList<Rectangle>();
		capes = new ArrayList<capeGuy>();
		fireballs = new ArrayList<fireBall>();
		shotBound = new ArrayList<Rectangle>();
		marioBound = new Rectangle(300, 300, player.standRight().getWidth()-32, player.standRight().getHeight());
		ground = new boolean[map.getWidth()][map.getHeight()];
		rect = new Rectangle[map.getWidth()][map.getHeight()];
		shape = new Shape[10000];
		for (int x=0;x<map.getWidth();x++) 
		{
			for (int y=0;y<map.getHeight();y++)
			{
				int tileID = map.getTileId(x, y, 2);
				String value = map.getTileProperty(tileID, "blocked", "false");
				if ("true".equals(value))
				{
					ground[x][y] = true;
					rect[x][y] = new Rectangle((float)(x*32), (float)(y*32), map.getTileWidth(), map.getTileHeight());
					shape[shapeCount] = rect[x][y];
					shapeCount++;
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
	    	System.out.println(marioBound.getY());
			elapsedTime += delta;
		  if (elapsedTime >= DELAY1)
		  {
			  elapsedTime = 0;
			  sendEnemy();
		  }
		Input input = gc.getInput();
		float hip = marioVelocity*delta;
		float ship = spitVelocity*delta;
		float fhip = marioFall*delta;
		float eip = enemyVelocity*delta;
		if(marioBound.getY()+mapY >= 530)
		{
			marioBound.setX(300);
			marioBound.setY(300);
			mapX = 0;
			mapY = 0;
		}
		if(fireballs.isEmpty() || shotBound.isEmpty())
			shooting = false;
		if(!enemyBound.isEmpty())
		{
			for(int i = 0; i < enemyBound.size(); i++)
			{
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
			if(input.isKeyDown(Input.KEY_SPACE)&&input.isKeyDown(Input.KEY_UP)&&(right))
			{
				fireballFX.play();
				fireballs.add(new fireBall("upRight"));
				shotBound.add(new Rectangle(marioBound.getCenterX(), marioBound.getY(),
						fireball.getFireImage().getWidth(), fireball.getFireImage().getHeight()-32));
				elapsedShotTime = 0;
				shooting = true;
			}
			else if(input.isKeyDown(Input.KEY_SPACE)&&input.isKeyDown(Input.KEY_UP)&&(left))
			{
				fireballFX.play();
				fireballs.add(new fireBall("upLeft"));
				shotBound.add(new Rectangle(marioBound.getCenterX(), marioBound.getY(),
						fireball.getFireImage().getWidth(), fireball.getFireImage().getHeight()-32));
				elapsedShotTime = 0;
				shooting = true;
			}
			else if(input.isKeyDown(Input.KEY_SPACE)&&(right))
			{
				fireballFX.play();
				fireballs.add(new fireBall("right"));
				shotBound.add(new Rectangle(marioBound.getCenterX(), marioBound.getY(),
						fireball.getFireImage().getWidth(), fireball.getFireImage().getHeight()-32));
				elapsedShotTime = 0;
				shooting = true;
			}
			else if(input.isKeyDown(Input.KEY_SPACE)&&(left))
			{
				fireballFX.play();
				fireballs.add(new fireBall("left"));
				shotBound.add(new Rectangle(marioBound.getCenterX(), marioBound.getY(),
						fireball.getFireImage().getWidth(), fireball.getFireImage().getHeight()-32));
				elapsedShotTime = 0;
				shooting = true;
			}
		}
		
		
		if(input.isKeyDown(Input.KEY_DOWN))
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
		else if(jumping)
		{
			if (ground[(int)marioBound.getX()/32][((int)(((marioBound.getY()+player.standRight().getHeight())-(marioLift*delta))/32))] != true)
			{
				marioBound.setY(marioBound.getY()-marioLift*delta);
				marioLift -= 0.04f;		
			}
			else
				jumping = false;
		}
		if(input.isKeyPressed(Input.KEY_UP) && !jumping)
		{
			jumping = true;
			jumpFX.play();
			marioLift = 0.6f;
		}
		if(input.isKeyDown(Input.KEY_RIGHT) && !crouched)
		{
			left = false;
			right = true;
			moving = true;
			marioBound.setX(marioBound.getX()+hip);
			mapX -= hip;
		}
		else if(input.isKeyDown(Input.KEY_LEFT) || input.isControllerLeft(1) && !crouched && marioBound.getX() > 0)
		{
			right = false;
			left = true;
			moving = true;
			marioBound.setX(marioBound.getX()-hip);
			mapX += hip;
		}
		else
			moving = false;
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
