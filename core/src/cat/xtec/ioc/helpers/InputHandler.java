package cat.xtec.ioc.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;

import cat.xtec.ioc.objects.Bullet;
import cat.xtec.ioc.objects.FireButon;
import cat.xtec.ioc.objects.Pause;
import cat.xtec.ioc.objects.Spacecraft;
import cat.xtec.ioc.screens.GameScreen;
import cat.xtec.ioc.utils.Settings;
import sun.rmi.runtime.Log;

public class InputHandler implements InputProcessor {

    // Enter per a la gesitó del moviment d'arrastrar
    int previousY = 0;
    // Objectes necessaris
    private Spacecraft spacecraft;
    private Pause pause;
    private FireButon fireButon;
    private GameScreen screen;
    private Vector2 stageCoord;

    private Stage stage;

    public InputHandler(GameScreen screen) {

        // Obtenim tots els elements necessaris
        this.screen = screen;
        spacecraft = screen.getSpacecraft();
        pause = screen.getPauseButton();
        fireButon = screen.getFireButon();
        stage = screen.getStage();

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        switch (screen.getCurrentState()) {

            case READY:
                // Si fem clic comencem el joc
                screen.setCurrentState(GameScreen.GameState.RUNNING);
                break;
            case PAUSE:
                //Surtir de l'estat de pause
                screen.setCurrentState(GameScreen.GameState.RESUME);
                break;
            case RUNNING:
                previousY = screenY;

                if (pause.touchDown(screenX, screenY)) {
                    screen.setCurrentState(GameScreen.GameState.PAUSE);
                    break;
                }
                if(fireButon.touchDown(screenX, screenY)){
                    AssetManager.shootSound.play();
                    stage.addActor(new Bullet(spacecraft.getX(), spacecraft.getY(), Settings.FIRE_WIDTH, Settings.FIRE_HEIGHT));
                }

                stageCoord = stage.screenToStageCoordinates(new Vector2(screenX, screenY));
                Actor actorHit = stage.hit(stageCoord.x, stageCoord.y, true);
                if (actorHit != null)
                    Gdx.app.log("HIT", actorHit.getName());
                break;
            // Si l'estat és GameOver tornem a iniciar el joc
            case GAMEOVER:
                screen.reset();
                break;
        }

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        // Quan deixem anar el dit acabem un moviment
        // i posem la nau en l'estat normal
        spacecraft.goStraight();
        return true;
    }


    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        // Posem un umbral per evitar gestionar events quan el dit està quiet
        if (Math.abs(previousY - screenY) > 2)

            // Si la Y és major que la que tenim
            // guardada és que va cap avall
            if (previousY < screenY) {
                spacecraft.goDown();
            } else {
                // En cas contrari cap a dalt
                spacecraft.goUp();
            }
        // Guardem la posició de la Y
        previousY = screenY;
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
