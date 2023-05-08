package entities;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class Entity {

	protected float x, y;
	protected int width, height;
	protected Rectangle hitbox;


	protected Rectangle2D.Float hitbox;

	public Entity(float x, float y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		initHitbox()
	}

}
	protected void drawHitbox(Graphics g) {
		g.drawRect((int) hitbox.x, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);

	}
	protected void initHitbox() {
		hitbox = new Rectangle((int)x,(int) y, width, height);
	}
	protected void updateHitbox() {
		hitbox.x = (int) x;
		hitbox.y = (int) y;
	}

	public Rectangle getHitbox() {
		return hitbox;
	}

	protected void drawHitbox(Graphics g){
		g.setColor(Color.BLUE);
		g.drawRect((int) hitbox.x, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
	}

	protected void iniHitbox(float x, float y, float width, float height){
		hitbox = new Rectangle2D.Float(x,y,width,height);
	}
//	public void updateHitbox(){
//		hitbox.x = (int)x;
//		hitbox.y = (int)y;
//	}
	public Rectangle2D.Float getHitbox(){
			return hitbox;
	}

}

