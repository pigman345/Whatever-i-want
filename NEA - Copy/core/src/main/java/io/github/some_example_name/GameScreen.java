package io.github.some_example_name;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.OrthographicCamera;


import javax.swing.text.StyledEditorKit;
import java.awt.*;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class GameScreen extends ScreenAdapter {
    MainGame game;
    private SpriteBatch batch;
    private Texture image;
    ShapeRenderer sr;
    private Player p;
    private Completion c;
    private Enemy e;
    private Platform pl;
    private Wall w;
    private Wall w1;
    int spd = 7;
    private OrthographicCamera cam;
    float velocityY = 0;
    float gravity = -0.5f;
    float jumpStrength = 10f;
    boolean onGround = true;
    boolean onPlat = true;
    float velocityX = 0;
   final float  worldWidth = 1000;
    float worldHeight = 600;
    float acceleration = 0.5f;
    float maxSpeed = 5f;
    float friction = 0.3f;
    float deadZoneWidth = 200;
    int enemyorigin = 450;
Boolean enemycoll = false;
Boolean enemyforward = true;
Boolean enemybackward = false;
    Boolean gamefinished = false;
    @Override
    public void show() {
        batch = new SpriteBatch();
        image = new Texture("libgdx.png");
        p = new Player(80, 0, 40, 40, 0, 0, Color.MAROON,true);
        c = new Completion(800,0,40,40,0,0,Color.GREEN);
        e = new Enemy(450,0,40,40,0,0,Color.FIREBRICK);
        sr = new ShapeRenderer();
        w = new Wall(70,0,400,10,0,0,Color.FIREBRICK);
        w1 = new Wall(700,25,400,10,0,0,Color.FIREBRICK);
        pl = new Platform(450,175,200,10,0,0,Color.GREEN);
        cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
        cam.update();

    }

    @Override
    public void render(float delta) {
        boolean touchingPlatform = false;
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        batch.begin();
        //   batch.draw(image, 140, 210);
        batch.end();
        float cameraOffsetY = 200;
        float lerp = 0.1f;

// Follow X only
        float targetX = p.Px + p.Pw / 2f;

// Smooth follow on X
        float leftBound = cam.position.x - deadZoneWidth / 2f;
        float rightBound = cam.position.x + deadZoneWidth / 2f;

// If player goes past right side → move camera right
        if (p.Px + p.Pw / 2f > rightBound) {
            cam.position.x = p.Px + p.Pw / 2f - deadZoneWidth / 2f;
        }

// If player goes past left side → move camera left
        if (p.Px + p.Pw / 2f < leftBound) {
            cam.position.x = p.Px + p.Pw / 2f + deadZoneWidth / 2f;
        }

// LOCK Y
        cam.position.y = cam.viewportHeight / 2f;

        cam.update();
        cam.update();



        //MOVEMENT LEFT AND RIGHT
        // Move right
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            velocityX += acceleration;}
// Move left
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            velocityX -= acceleration;}

        if (velocityX > maxSpeed) velocityX = maxSpeed;
        if (velocityX < -maxSpeed) velocityX = -maxSpeed;

        if (!Gdx.input.isKeyPressed(Input.Keys.A) && !Gdx.input.isKeyPressed(Input.Keys.D)) {
            if (velocityX > 0) {
                velocityX -= friction;
                if (velocityX < 0) velocityX = 0;
            } else if (velocityX < 0) {
                velocityX += friction;
                if (velocityX > 0) velocityX = 0;
            }
        }
        p.Px += velocityX;
        // enforcing size of worlds.

           if (p.Px < 0){
               p.Px = 0;
           }
          if (p.Px + p.Pw > worldWidth){
              p.Px = (int) (worldWidth - p.Pw);
          }
        if (p.Py <= 0) {
            p.Py = 0;

            velocityY = 0;
            onGround = true;
        }


        boolean touchingLeftWall = ((p.Px) >= w.Wlx&&(p.Px  <= w.Wlx+w.Wlw))&&(p.Py < w.Wly + w.Wlh)&&(p.Py+p.Ph > w.Wly);
        boolean touchingRightWall = (p.Px+p.Pw) >= w1.Wlx&&(p.Px+p.Pw <= w1.Wlx+w1.Wlw)&&(p.Py < w1.Wly + w1.Wlh)&&((p.Py+p.Ph > w1.Wly));
        boolean touchingLeftWallleft = (p.Px+p.Pw) >= w.Wlx&&(p.Px+p.Pw <= w.Wlx+w.Wlw)&&(p.Py < w.Wly + w.Wlh)&&((p.Py+p.Ph > w.Wly));
        boolean touchingRightWallright = ((p.Px) >= w1.Wlx&&(p.Px  <= w1.Wlx+w1.Wlw))&&(p.Py < w1.Wly + w1.Wlh)&&(p.Py+p.Ph > w1.Wly);



        //Collions? Width = Height Height = Width NEEDS FIXING for platform.
        if  ((p.Py <= (pl.Ply+pl.Plw)&&(p.Py >= pl.Ply)
            &&(p.Px < (pl.Plx + pl.Plh))&&(p.Px+p.Pw > pl.Plx))){
          touchingPlatform = true;}


        if (touchingPlatform == true){
            p.Py = pl.Ply + pl.Plw;
            velocityY = 0;
            onPlat = true;
        }
        if (touchingLeftWall){
            p.Px = w.Wlx + w.Wlw;
        }
        if (touchingRightWall){
            p.Px = w1.Wlx-p.Pw;
        }
        if (touchingLeftWallleft){
            p.Px = w.Wlx-p.Pw ;
        }
        if (touchingRightWallright){
            p.Px = w1.Wlx+w1.Wlw;
        }

        //  JUMPS
        if (Gdx.input.isKeyPressed(Input.Keys.W) || (Gdx.input.isKeyPressed(Input.Keys.SPACE))) {

            // normal jump
            if (onGround || onPlat) {
                velocityY = jumpStrength;
                onGround = false;
                onPlat = false;
            }

            // wall jump (LEFT wall → push right)
            else if (touchingLeftWall || touchingRightWallright) {
                velocityY = jumpStrength;
                velocityX = 5f; // push right
                onGround = false;

            }

            // wall jump (RIGHT wall → push left)
            else if (touchingRightWall || touchingLeftWallleft ) {
                velocityY = jumpStrength;
                velocityX = -5f;// push left
                onGround = false;
            }
        }
        velocityY += gravity;
        p.Py += velocityY;


        if (Gdx.input.isKeyPressed(Input.Keys.S)&&(p.Pw > 20)){
            p.Pw = p.Pw/2;
        }
        else if (!Gdx.input.isKeyPressed(Input.Keys.S)){
            p.Pw = 40;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)&&(p.Ph > 20)){
            p.Ph = p.Ph/2;
        }
        else if (!Gdx.input.isKeyPressed(Input.Keys.S)){
            p.Ph = 40;
        }


    // enemies movement and collisons

   if (e.Ex >= enemyorigin+150 ){
       enemyforward = false;
       enemybackward = true;
      }if (e.Ex <= enemyorigin-150 ){
       enemyforward = true;
       enemybackward = false;
    }
     if (enemyforward){
          e.Ex = e.Ex + 3;
      } if (enemybackward){
          e.Ex = e.Ex - 3;
    }

  if (((p.Px) >= e.Ex&&(p.Px  <= e.Ex+e.Ew))&&(p.Py < e.Ey + e.Eh)&&(p.Py+p.Ph > e.Ey)
       ||((p.Px+p.Pw) >= e.Ex&&(p.Px+p.Pw <= e.Ex+e.Ew)&&(p.Py < e.Ey + e.Eh)&&((p.Py+p.Ph > e.Ey)))){
      enemycoll = true;
      p.PlayerAlive = false;
  }
  else{
      enemycoll = false;
  }

        if (((p.Px) >= c.Cx&&(p.Px  <= c.Cx+c.Cw))&&(p.Py < c.Cy + c.Ch)&&(p.Py+p.Ph > c.Cy)
            ||((p.Px+p.Pw) >= c.Cx&&(p.Px+p.Pw <= c.Cx+c.Cw)&&(p.Py < c.Cy + c.Ch)&&((p.Py+p.Ph > c.Cx)))){
            gamefinished = true;

        }


        sr.setProjectionMatrix(cam.combined);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        if(gamefinished == false){
        if (p.PlayerAlive == true ){
        p.draw(sr);}
        c.draw(sr);
        w.draw(sr);
        e.draw(sr);
        w1.draw(sr);
        pl.draw(sr);}
        sr.end();


        if (((p.Px) >= c.Cx&&(p.Px  <= c.Cx+c.Cw))&&(p.Py < c.Cy + c.Ch)&&(p.Py+p.Ph > c.Cy)
            ||((p.Px+p.Pw) >= c.Cx&&(p.Px+p.Pw <= c.Cx+c.Cw)&&(p.Py < c.Cy + c.Ch)&&((p.Py+p.Ph > c.Cx)))){
            gamefinished = true;

        }

    }
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
        sr.dispose();
    }


}
