package com.taco.tutorial1;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.omg.CORBA_2_3.portable.OutputStream;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player implements java.io.Serializable {

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	Vector2 position;
	String textureLoc;
	private static final int col = 4;
	private static final int row = 6;
	
	Animation animation;
	Texture playerTexture;
	TextureRegion[] frames;
	float stateTime;
	TextureRegion currentFrame;
	Rectangle bounds;
	public Vector2 getPreviousPosition() {
		return previousPosition;
	}

	public void setPreviousPosition(Vector2 previousPosition) {
		this.previousPosition = previousPosition;
	}

	String direction;
	Vector2 previousPosition;
	

	public Player(Vector2 position, String textureLoc){
		this.position = position;
		previousPosition = new Vector2(0,0);
		playerTexture = new Texture(Gdx.files.internal("data/boo.png"));
		TextureRegion[][] tmp = TextureRegion.split(playerTexture, playerTexture.getWidth() / col, playerTexture.getHeight() / row);
		frames = new TextureRegion[col * row];
		int index = 0;
		for (int i=0; i< row; i++){
			for (int j=0; j<col; j++){
				frames[index++] = tmp[i][j];
			}
		}
		
		
		
		animation = new Animation(1, frames);
		stateTime = 0f;
		currentFrame = animation.getKeyFrame(0);
		bounds = new Rectangle(position.x, position.y, currentFrame.getRegionWidth(), currentFrame.getRegionHeight());
		
	}
	
	public void update(){
		stateTime += Gdx.graphics.getDeltaTime();
		//BEGIN MOVEMENT LOGIC//
		if(Gdx.input.isKeyPressed(Keys.W)){
			position.y+=1f;
			currentFrame = animation.getKeyFrame(16 + stateTime%4);
		}
		
		if(Gdx.input.isKeyPressed(Keys.A)){
			position.x-=1f;
			currentFrame = animation.getKeyFrame(8 + stateTime%4);
		}
		
		if(Gdx.input.isKeyPressed(Keys.S)){
			position.y-=1f;
			currentFrame = animation.getKeyFrame(20 + stateTime % 4);
		}
		
		if(Gdx.input.isKeyPressed(Keys.D)){
			position.x+=1f;
			currentFrame = animation.getKeyFrame(0 + stateTime % 4);
		}
		
		float accelX = Gdx.input.getAccelerometerX();
		float accelY = Gdx.input.getAccelerometerY();
		float accelZ = Gdx.input.getAccelerometerZ();
			
		position.x+=accelY ;
		position.y-=accelX;	
		
		if(accelY>1){//D
			stateTime += Gdx.graphics.getDeltaTime();
			currentFrame = animation.getKeyFrame(0);
		}
		if(accelY<-1){//A
			stateTime += Gdx.graphics.getDeltaTime();
			currentFrame = animation.getKeyFrame(8);
		}
		if(accelX>1){//S
			stateTime += Gdx.graphics.getDeltaTime();
			currentFrame = animation.getKeyFrame(20);
		}
		if(accelX<-1){//W
			stateTime += Gdx.graphics.getDeltaTime();
			currentFrame = animation.getKeyFrame(16);
		}
		bounds = new Rectangle(position.x, position.y, currentFrame.getRegionWidth(), currentFrame.getRegionHeight());
		//END MOVEMENT LOGIC//	
		
	}
	
	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public static void savePlayer(Vector2 position) throws IOException{
		FileHandle file = Gdx.files.local("player.dat");
		OutputStream out = null;
		try{
			file.writeBytes(serialize(position), false);
		} catch(Exception ex){
			System.out.println(ex.toString());
		}finally{
			if(out!= null) try{
					out.close();
			}catch(Exception ex){}
		}
		System.out.println("Saving Player");
	}
	
	public static Vector2 readPlayer() throws IOException, ClassNotFoundException{
		Vector2 position = null;
		FileHandle file = Gdx.files.local("player.dat");
		position = (Vector2) deserialize(file.readBytes());
		return position;
	}
	
	private static byte[] serialize(Object obj) throws IOException{
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		ObjectOutputStream o = new ObjectOutputStream(b);
		o.writeObject(obj);
		return b.toByteArray();
	}
	
	public static Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
		ByteArrayInputStream b = new ByteArrayInputStream(bytes);
		ObjectInputStream o = new ObjectInputStream(b);
		return o.readObject();
	}
	

	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}

	public float getStateTime() {
		return stateTime;
	}

	public void setStateTime(float stateTime) {
		this.stateTime = stateTime;
	}

	public Animation getAnimation() {
		return animation;
	}

	public void setAnimation(Animation animation) {
		this.animation = animation;
	}

	public TextureRegion[] getFrames() {
		return frames;
	}

	public void setFrames(TextureRegion[] frames) {
		this.frames = frames;
	}

	public TextureRegion getCurrentFrame() {
		return currentFrame;
	}

	public void setCurrentFrame(TextureRegion currentFrame) {
		this.currentFrame = currentFrame;
	}

	public String getTextureLoc(){
		return textureLoc;
	}
	public Vector2 getPosition(){
		return position;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}
	

}
