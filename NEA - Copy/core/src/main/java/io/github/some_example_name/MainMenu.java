package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainMenu extends ScreenAdapter {
   private final MainGame game;
   private ShapeRenderer sr;
   private SpriteBatch batch;
   private BitmapFont font;
   private int highestScore = 0;

   public MainMenu(MainGame game) {
       this.game = game;
   }

   @Override
   public void show() {
       sr = new ShapeRenderer();
       batch = new SpriteBatch();
       font = new BitmapFont();
       font.getData().setScale(2f);
       highestScore = readHighestScore();
   }

   @Override
   public void render(float delta) {
       highestScore = readHighestScore();
       ScreenUtils.clear(0.08f, 0.12f, 0.2f, 1f);

       float width = Gdx.graphics.getWidth();
       float height = Gdx.graphics.getHeight();
       float buttonWidth = 260f;
       float buttonHeight = 80f;
       float startX = width / 2f - buttonWidth / 2f;
       float startY = height / 2f - 20f;
       float quitX = width / 2f - buttonWidth / 2f;
       float quitY = height / 2f - buttonHeight - 50f;

       if (Gdx.input.justTouched()) {
           int touchX = Gdx.input.getX();
           int touchY = Gdx.graphics.getHeight() - Gdx.input.getY();

           if (touchX >= startX && touchX <= startX + buttonWidth && touchY >= startY && touchY <= startY + buttonHeight) {
               game.setScreen(new GameScreen(game));
               return;
           }

           if (touchX >= quitX && touchX <= quitX + buttonWidth && touchY >= quitY && touchY <= quitY + buttonHeight) {
               Gdx.app.exit();
           }
       }

       if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
           game.setScreen(new GameScreen(game));
       }

       if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
           Gdx.app.exit();
       }

       sr.begin(ShapeRenderer.ShapeType.Filled);
       sr.setColor(new Color(0.12f, 0.18f, 0.30f, 1f));
       sr.rect(startX, startY, buttonWidth, buttonHeight);
       sr.setColor(new Color(0.32f, 0.10f, 0.14f, 1f));
       sr.rect(quitX, quitY, buttonWidth, buttonHeight);
       sr.end();

       batch.begin();
       font.setColor(Color.WHITE);
       font.draw(batch, "Goal Rush", width / 2f - 130f, height - 120f);
       font.draw(batch, "High Score: " + highestScore, width - 310f, height - 80f);
       font.draw(batch, "Play Game", startX + 55f, startY + 52f);
       font.draw(batch, "Quit", quitX + 95f, quitY + 52f);
       batch.end();
   }

   private int readHighestScore() {
       try {
           FileHandle file = Gdx.files.local("score.json");
           if (!file.exists()) {
               return 0;
           }

           String content = file.readString();
           if (content == null || content.trim().isEmpty() || content.trim().equals("[]")) {
               return 0;
           }

           Pattern scorePattern = Pattern.compile("\\\"score\\\"\\s*:\\s*(\\d+)");
           Matcher matcher = scorePattern.matcher(content);
           int bestScore = 0;

           while (matcher.find()) {
               int score = Integer.parseInt(matcher.group(1));
               if (score > bestScore) {
                   bestScore = score;
               }
           }

           return bestScore;
       } catch (Exception e) {
           Gdx.app.log("MainMenu", "Failed to read highest score: " + e.getMessage());
           return 0;
       }
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
       sr.dispose();
       batch.dispose();
       font.dispose();
   }
}

