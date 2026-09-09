package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;


public class GameScreen extends ScreenAdapter {
   private final MainGame game;
   private SpriteBatch batch;
   private Texture image;
   private ShapeRenderer sr;
   private Player p;
   private Completion c;
   private Enemy e;
   private Platform pl;
   private Wall w;
   private Wall w1;
   private OrthographicCamera cam;
   private BitmapFont font;
   private int score = 0;
   private float scoreAccumulator = 0f;
   private float prevPx = 0f;
   private float velocityY = 0;
   private float gravity = -0.5f;
   private float jumpStrength = 10f;
   private boolean onGround = true;
   private boolean onPlat = true;
   private float velocityX = 0;
   private final float worldWidth = 1000;
   private float worldHeight = 600;
   private float acceleration = 0.5f;
   private float maxSpeed = 5f;
   private float friction = 0.3f;
   private float deadZoneWidth = 200;
   private int enemyorigin = 450;
   private boolean enemyforward = true;
   private boolean enemybackward = false;
   private boolean gamefinished = false;
   private float enemyFireCooldown = 0f;
   private Sound moveSound;
   private float moveSoundCooldown = 0f;

   public GameScreen(MainGame game) {
       this.game = game;
   }

   @Override
   public void show() {
       batch = new SpriteBatch();
       image = new Texture("libgdx.png");
       p = new Player(80, 0, 40, 40, 0, 0, Color.MAROON, true);
       c = new Completion(800, 0, 40, 40, 0, 0, Color.GREEN);
       e = new Enemy(450, 0, 40, 40, 0, 0, Color.FIREBRICK);
       sr = new ShapeRenderer();
       w = new Wall(70, 0, 400, 10, 0, 0, Color.FIREBRICK);
       w1 = new Wall(700, 25, 400, 10, 0, 0, Color.FIREBRICK);
       pl = new Platform(450, 175, 200, 10, 0, 0, Color.GREEN);
       cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
       cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
       cam.update();
       font = new BitmapFont();
       moveSound = Gdx.audio.newSound(Gdx.files.internal("walk audio.ogg"));
       score = 0;
       scoreAccumulator = 0f;
       prevPx = p.Px;
   }

   @Override
   public void render(float delta) {
       boolean touchingPlatform = false;
       ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

       if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) || Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
           game.setScreen(new MainMenu(game));
           return;
       }

       if (gamefinished && Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
           game.setScreen(new GameScreen(game));
           return;
       }

       float leftBound = cam.position.x - deadZoneWidth / 2f;
       float rightBound = cam.position.x + deadZoneWidth / 2f;

       if (p.Px + p.Pw / 2f > rightBound) {
           cam.position.x = p.Px + p.Pw / 2f - deadZoneWidth / 2f;
       }

       if (p.Px + p.Pw / 2f < leftBound) {
           cam.position.x = p.Px + p.Pw / 2f + deadZoneWidth / 2f;
       }

       cam.position.y = cam.viewportHeight / 2f;
       cam.update();

       enemyFireCooldown -= delta;
       if (enemyFireCooldown <= 0f && p.PlayerAlive) {
           e.fireProjectile(p.Px + p.Pw / 2f, p.Py + p.Ph / 2f);
           enemyFireCooldown = 1.5f;
       }
       e.updateProjectile();

       if (e.projectileHitsPlayer(p.Px, p.Py, p.Pw, p.Ph)) {
           p.PlayerAlive = false;
       }

       if (Gdx.input.isKeyPressed(Input.Keys.D)) {
           velocityX += acceleration;
       }
       if (Gdx.input.isKeyPressed(Input.Keys.A)) {
           velocityX -= acceleration;
       }

       if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.A)) {
           moveSoundCooldown -= delta;

           if (Math.abs(velocityX) > 0.1f && moveSoundCooldown <= 0f) {
               moveSound.play(0.25f);
               moveSoundCooldown = 0.18f;
           }
       }

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


       float deltaX = p.Px - prevPx;
       if (deltaX > 0f) {
           scoreAccumulator += deltaX;
           if (scoreAccumulator >= 10f) {
               int gained = (int) (scoreAccumulator / 10f);
               score += gained;
               scoreAccumulator -= gained * 10f;
           }
       }

       if (p.Px < 0) {
           p.Px = 0;
       }
       if (p.Px + p.Pw > worldWidth) {
           p.Px = (int) (worldWidth - p.Pw);
       }
       if (p.Py <= 0) {
           p.Py = 0;
           velocityY = 0;
           onGround = true;
       }

       boolean touchingLeftWall = ((p.Px) >= w.Wlx && (p.Px <= w.Wlx + w.Wlw)) && (p.Py < w.Wly + w.Wlh) && (p.Py + p.Ph > w.Wly);
       boolean touchingRightWall = (p.Px + p.Pw) >= w1.Wlx && (p.Px + p.Pw <= w1.Wlx + w1.Wlw) && (p.Py < w1.Wly + w1.Wlh) && ((p.Py + p.Ph > w1.Wly));
       boolean touchingLeftWallleft = (p.Px + p.Pw) >= w.Wlx && (p.Px + p.Pw <= w.Wlx + w.Wlw) && (p.Py < w.Wly + w.Wlh) && ((p.Py + p.Ph > w.Wly));
       boolean touchingRightWallright = ((p.Px) >= w1.Wlx && (p.Px <= w1.Wlx + w1.Wlw)) && (p.Py < w1.Wly + w1.Wlh) && (p.Py + p.Ph > w1.Wly);

       if ((p.Py <= (pl.Ply + pl.Plw) && (p.Py >= pl.Ply) && (p.Px < (pl.Plx + pl.Plh)) && (p.Px + p.Pw > pl.Plx))) {
           touchingPlatform = true;
       }

       if (touchingPlatform) {
           p.Py = pl.Ply + pl.Plw;
           velocityY = 0;
           onPlat = true;
       }
       if (touchingLeftWall) {
           p.Px = w.Wlx + w.Wlw;
       }
       if (touchingRightWall) {
           p.Px = w1.Wlx - p.Pw;
       }
       if (touchingLeftWallleft) {
           p.Px = w.Wlx - p.Pw;
       }
       if (touchingRightWallright) {
           p.Px = w1.Wlx + w1.Wlw;
       }

       if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
           if (onGround || onPlat) {
               velocityY = jumpStrength;
               onGround = false;
               onPlat = false;
           } else if (touchingLeftWall || touchingRightWallright) {
               velocityY = jumpStrength;
               velocityX = 5f;
               onGround = false;
           } else if (touchingRightWall || touchingLeftWallleft) {
               velocityY = jumpStrength;
               velocityX = -5f;
               onGround = false;
           }
       }

       velocityY += gravity;
       p.Py += velocityY;

       if (Gdx.input.isKeyPressed(Input.Keys.S) && (p.Pw > 20)) {
           p.Pw = p.Pw / 2;
       } else if (!Gdx.input.isKeyPressed(Input.Keys.S)) {
           p.Pw = 40;
       }
       if (Gdx.input.isKeyPressed(Input.Keys.S) && (p.Ph > 20)) {
           p.Ph = p.Ph / 2;
       } else if (!Gdx.input.isKeyPressed(Input.Keys.S)) {
           p.Ph = 40;
       }

       if (e.Ex >= enemyorigin + 150) {
           enemyforward = false;
           enemybackward = true;
       }
       if (e.Ex <= enemyorigin - 150) {
           enemyforward = true;
           enemybackward = false;
       }
       if (enemyforward) {
           e.Ex = e.Ex + 3;
       }
       if (enemybackward) {
           e.Ex = e.Ex - 3;
       }

       if (((p.Px >= e.Ex && p.Px <= e.Ex + e.Ew) && (p.Py < e.Ey + e.Eh) && (p.Py + p.Ph > e.Ey))
               || ((p.Px + p.Pw >= e.Ex && p.Px + p.Pw <= e.Ex + e.Ew) && (p.Py < e.Ey + e.Eh) && ((p.Py + p.Ph > e.Ey)))) {
           p.PlayerAlive = false;
       }

       if (((p.Px >= c.Cx && p.Px <= c.Cx + c.Cw) && (p.Py < c.Cy + c.Ch) && (p.Py + p.Ph > c.Cy))
               || ((p.Px + p.Pw >= c.Cx && p.Px + p.Pw <= c.Cx + c.Cw) && (p.Py < c.Cy + c.Ch) && ((p.Py + p.Ph > c.Cx)))) {
           gamefinished = true;
       }

       if (gamefinished) {
           saveScoreToFile(score);
           game.setScreen(new MainMenu(game));
           return;
       }

       sr.setProjectionMatrix(cam.combined);
       sr.begin(ShapeRenderer.ShapeType.Filled);
       if (p.PlayerAlive) {
           p.draw(sr);
       }
       c.draw(sr);
       w.draw(sr);
       e.draw(sr);
       w1.draw(sr);
       pl.draw(sr);
       sr.end();

       // draw score in top-right of the camera view
       batch.setProjectionMatrix(cam.combined);
       batch.begin();
       String scoreText = "Score: " + score;
       float x = cam.position.x + cam.viewportWidth / 2f - 100f; // adjust margin from right edge
       float y = cam.position.y + cam.viewportHeight / 2f - 10f; // top edge minus margin
       font.draw(batch, scoreText, x, y);
       batch.end();

       // remember previous X for next frame
       prevPx = p.Px;
   }

   @Override
   public void resize(int width, int height) {
   }

   @Override
   public void pause() {
   }

   @Override
   public void resume() {
   }

   @Override
   public void hide() {
   }

   @Override
   public void dispose() {
       batch.dispose();
       image.dispose();
       sr.dispose();
       if (font != null) font.dispose();
       if (moveSound != null) moveSound.dispose();
   }

   // Saves the score to a local JSON file called score.json
   private void saveScoreToFile(int scoreValue) {
       try {
           String filename = "score.json";
           FileHandle fh = Gdx.files.local(filename);
           String entry = "{\"run\":%d,\"score\":%d}";
           int runNumber = 1;

           if (fh.exists()) {
               String content = fh.readString();
               content = content.trim();

               if (content.length() > 0) {
                   int existing = content.length() - content.replace("{", "").length();
                   runNumber = existing + 1;
               }

               String newEntry = String.format(entry, runNumber, scoreValue);
               if (content.startsWith("[") && content.endsWith("]")) {
                   if (content.equals("[]")) {
                       fh.writeString("[" + newEntry + "]", false);
                   } else {
                       // append before the final ]
                       String updated = content.substring(0, content.length() - 1) + "," + newEntry + "]";
                       fh.writeString(updated, false);
                   }
               } else {
                   // malformed or other content - overwrite with new array
                   fh.writeString("[" + newEntry + "]", false);
               }
           } else {
               String newEntry = String.format(entry, runNumber, scoreValue);
               fh.writeString("[" + newEntry + "]", false);
           }
       } catch (Exception ex) {
           // best-effort: log failure but don't crash the game
           Gdx.app.log("GameScreen", "Failed saving score: " + ex.getMessage());
       }
   }
}
