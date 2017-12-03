package cat.xtec.ioc.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

import cat.xtec.ioc.helpers.AssetManager;
import cat.xtec.ioc.helpers.InputHandler;
import cat.xtec.ioc.objects.Asteroid;
import cat.xtec.ioc.objects.Bullet;
import cat.xtec.ioc.objects.FireButon;
import cat.xtec.ioc.objects.Pause;
import cat.xtec.ioc.objects.ScrollHandler;
import cat.xtec.ioc.objects.Spacecraft;
import cat.xtec.ioc.utils.Settings;


public class GameScreen implements Screen {

    // Els estats del joc
    public enum GameState {

        READY, RUNNING, GAMEOVER, PAUSE, RESUME

    }

    private GameState currentState;

    // Objectes necessaris
    private Stage stage;
    private Spacecraft spacecraft;
    private Pause pause;
    private FireButon fireButon;
    private ScrollHandler scrollHandler;

    // Encarregats de dibuixar elements per pantalla
    private ShapeRenderer shapeRenderer;
    private Batch batch;

    // Per controlar l'animació de l'explosió
    private float explosionTime = 0;

    // Preparem el textLayout per escriure text
    private GlyphLayout textLayout;

    // Text Pause
    private Label.LabelStyle textStyle;
    private Label textPause;
    Container containerPause;
    private boolean paused = false;

    long endPauseTime = 0;
    long secondsAsteroids = 1000;
    int lastAsteroid = 0;


    public GameScreen(Batch prevBatch, Viewport prevViewport) {

        // Iniciem la música
        AssetManager.music.play();

        // Creem el ShapeRenderer
        shapeRenderer = new ShapeRenderer();

        // Creem l'stage i assginem el viewport
        stage = new Stage(prevViewport, prevBatch);

        batch = stage.getBatch();

        // Creem la nau i la resta d'objectes
        spacecraft = new Spacecraft(Settings.SPACECRAFT_STARTX, Settings.SPACECRAFT_STARTY, Settings.SPACECRAFT_WIDTH, Settings.SPACECRAFT_HEIGHT);


        //Butó pause
        pause = new Pause(Settings.PAUSE_X, Settings.PAUSE_Y, Settings.PAUSE_WIDTH, Settings.PAUSE_HEIGHT);
        scrollHandler = new ScrollHandler();
        //Text Pause
        textStyle = new Label.LabelStyle(AssetManager.font, null);
        textPause = new Label("Pause", textStyle);
        containerPause = new Container(textPause);
        containerPause.setTransform(true);
        containerPause.center();
        containerPause.setPosition(Settings.GAME_WIDTH / 2, Settings.GAME_HEIGHT / 2);
        containerPause.setVisible(false);
        stage.addActor(containerPause);

        //Butó fire
        fireButon = new FireButon(Settings.BTN_FIRE_X, Settings.BTN_FIRE_Y, Settings.BTN_FIRE_WIDTH, Settings.BTN_FIRE_HEIGHT);


        // Afegim els actors a l'stage
        stage.addActor(scrollHandler);
        stage.addActor(spacecraft);
        stage.addActor(fireButon);
        stage.addActor(pause);
        // Donem nom a l'Actor
        spacecraft.setName("spacecraft");
        pause.setName("Pause");
        fireButon.setName("btnFire");

        // Iniciem el GlyphLayout
        textLayout = new GlyphLayout();
        textLayout.setText(AssetManager.font, "Are you\nready?");


        currentState = GameState.READY;

        // Assignem com a gestor d'entrada la classe InputHandler
        Gdx.input.setInputProcessor(new InputHandler(this));

    }

    private void drawElements() {

        // Recollim les propietats del Batch de l'Stage
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());

        // Pintem el fons de negre per evitar el "flickering"
        //Gdx.gl20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        //Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Inicialitzem el shaperenderer
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        // Definim el color (verd)
        shapeRenderer.setColor(new Color(0, 1, 0, 1));

        // Pintem la nau
        shapeRenderer.rect(spacecraft.getX(), spacecraft.getY(), spacecraft.getWidth(), spacecraft.getHeight());

        // Recollim tots els Asteroid
        ArrayList<Asteroid> asteroids = scrollHandler.getAsteroids();
        Asteroid asteroid;

        for (int i = 0; i < asteroids.size(); i++) {

            asteroid = asteroids.get(i);
            switch (i) {
                case 0:
                    shapeRenderer.setColor(1, 0, 0, 1);
                    break;
                case 1:
                    shapeRenderer.setColor(0, 0, 1, 1);
                    break;
                case 2:
                    shapeRenderer.setColor(1, 1, 0, 1);
                    break;
                default:
                    shapeRenderer.setColor(1, 1, 1, 1);
                    break;
            }
            shapeRenderer.circle(asteroid.getX() + asteroid.getWidth() / 2, asteroid.getY() + asteroid.getWidth() / 2, asteroid.getWidth() / 2);
        }
        shapeRenderer.end();
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        // Dibuixem tots els actors de l'stage
        stage.draw();

        // Depenent de l'estat del joc farem unes accions o unes altres
        switch (currentState) {

            case GAMEOVER:
                updateGameOver(delta);
                break;
            case RUNNING:
                updateRunning(delta);
                break;
            case READY:
                lastAsteroid = lastAsteroid();
                updateReady();
                break;
            case PAUSE:
                pause();
                break;
            case RESUME:
                resume();
                break;

        }

        //drawElements();

    }

    private void updateReady() {

        // Dibuixem el text al centre de la pantalla
        batch.begin();
        AssetManager.font.draw(batch, textLayout, (Settings.GAME_WIDTH / 2) - textLayout.width / 2, (Settings.GAME_HEIGHT / 2) - textLayout.height / 2);
        //stage.addActor(textLbl);
        batch.end();

    }

    private void updateRunning(float delta) {
        //Quan passi 1 segon
        float lastX = scrollHandler.getAsteroids().get(lastAsteroid).getTailX();
        if(lastX == 0){
            try {
                Gdx.app.wait(secondsAsteroids);
                scrollHandler.reset();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

       /* if (endPauseTime < System.currentTimeMillis()) {
            Gdx.app.log("Time", endPauseTime + "");
            endPauseTime = System.currentTimeMillis() + secondsAsteroids;
            // scrollHandler.reset();
        }*/
        stage.act(delta);

        if(scrollHandler.collides((Bullet) stage.getRoot().findActor(Settings.FIRE_NAME))){
            AssetManager.explosionSound.play();
        }

        if (scrollHandler.collides(spacecraft)) {
            // Si hi ha hagut col·lisió: Reproduïm l'explosió i posem l'estat a GameOver
            AssetManager.explosionSound.play();
            //eliminar els dispars quan sigui gameOver
            stage.getRoot().findActor(Settings.FIRE_NAME).remove();
            stage.getRoot().findActor("spacecraft").remove();
            textLayout.setText(AssetManager.font, "Game Over :'(");
            currentState = GameState.GAMEOVER;
        }
    }

    private void updateGameOver(float delta) {
        stage.act(delta);

        batch.begin();
        AssetManager.font.draw(batch, textLayout, (Settings.GAME_WIDTH - textLayout.width) / 2, (Settings.GAME_HEIGHT - textLayout.height) / 2);
        // Si hi ha hagut col·lisió: Reproduïm l'explosió i posem l'estat a GameOver
        batch.draw(AssetManager.explosionAnim.getKeyFrame(explosionTime, false), (spacecraft.getX() + spacecraft.getWidth() / 2) - 32, spacecraft.getY() + spacecraft.getHeight() / 2 - 32, 64, 64);
        batch.end();

        explosionTime += delta;

    }

    public void reset() {
        endPauseTime = System.currentTimeMillis() + secondsAsteroids;

        // Posem el text d'inici
        textLayout.setText(AssetManager.font, "Are you\nready?");
        // Cridem als restart dels elements.
        spacecraft.reset();
        scrollHandler.reset();

        // Posem l'estat a 'Ready'
        currentState = GameState.READY;

        // Afegim la nau a l'stage
        stage.addActor(spacecraft);

        // Posem a 0 les variables per controlar el temps jugat i l'animació de l'explosió
        explosionTime = 0.0f;

    }


    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

        //Pausem la música
        AssetManager.music.pause();
        //fem el buto pause invisible
        pause.setVisible(false);
        //la lletra de Pause la fem visible
        containerPause.setVisible(true);

        spacecraft.paused();
        stage.addActor(spacecraft);
        for (Asteroid a :
                scrollHandler.getAsteroids()) {
            a.setVisible(paused);
        }
        //afegim els canvis del actors
        stage.addActor(containerPause);
        stage.draw();


        paused = !paused;
    }

    @Override
    public void resume() {
        AssetManager.music.play();
        pause.setVisible(true);
        containerPause.setVisible(false);
        spacecraft.setVisible(true);
        spacecraft.resume();
        for (Asteroid a : scrollHandler.getAsteroids()) {
            a.setVisible(true);
        }
        currentState = GameState.RUNNING;
        stage.addActor(spacecraft);
        stage.addActor(pause);
        stage.addActor(containerPause);
        stage.draw();
        paused = false;
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public Spacecraft getSpacecraft() {
        return spacecraft;
    }

    public Pause getPauseButton() {
        return pause;
    }

    public Stage getStage() {
        return stage;
    }

    public ScrollHandler getScrollHandler() {
        return scrollHandler;
    }

    public GameState getCurrentState() {
        return currentState;
    }

    public FireButon getFireButon() {
        return fireButon;
    }

    public void setCurrentState(GameState currentState) {
        this.currentState = currentState;
    }

    public int lastAsteroid() {
        int contador = 0;
        for (Asteroid a :
                scrollHandler.getAsteroids()) {
            if (a.getName().equals("" + scrollHandler.getAsteroids().size())) {
                return contador;
            }
            contador++;
        }
        return 0;
    }
}
