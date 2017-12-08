package cat.xtec.ioc.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;

import java.util.ArrayList;

import cat.xtec.ioc.helpers.AssetManager;
import cat.xtec.ioc.utils.Settings;

public class Spacecraft extends Actor {

    // Diverses posicions de la spacecraft, recta, pujant i baixant
    public static final int SPACECRAFT_STRAIGHT = 0;
    public static final int SPACECRAFT_UP = 1;
    public static final int SPACECRAFT_DOWN = 2;
    public static final int SPACECRAFT_PAUSED = 3;


    // Paràmetres de la spacecraft
    private Vector2 position;
    private int width, height;
    private int direction;
    public static ArrayList<Bullet> fire = new ArrayList<Bullet>();

    private Rectangle collisionRect;

    //Parpallejar quan es pausa
    public static Action blinking;

    public Spacecraft(float x, float y, int width, int height) {

        // Inicialitzem els arguments segons la crida del constructor
        this.width = width;
        this.height = height;
        position = new Vector2(x, y);

        // Inicialitzem la spacecraft a l'estat normal
        direction = SPACECRAFT_STRAIGHT;

        // Creem el rectangle de col·lisions
        collisionRect = new Rectangle();

        // Per a la gestio de hit
        setBounds(position.x, position.y, width, height);
        setTouchable(Touchable.enabled);
        //Accio de parpalleig
        blinking = Actions.repeat(RepeatAction.FOREVER, Actions.sequence(Actions.run(new Runnable() {
            @Override
            public void run() {
                Actions.delay(0.2f);
                setVisible(true);
                Actions.delay(0.2f);
                setVisible(false);
            }
        }), Actions.delay(0.2f), Actions.run(new Runnable() {
            @Override
            public void run() {
                Actions.delay(0.2f);
                setVisible(false);
                Actions.delay(0.2f);
                setVisible(true);
            }
        }), Actions.delay(0.2f)));
    }

    public void act(float delta) {
        // Movem la spacecraft depenent de la direcció controlant que no surti de la pantalla
        switch (direction) {
            case SPACECRAFT_UP:
                if (this.position.y - Settings.SPACECRAFT_VELOCITY * delta >= 0) {
                    this.position.y -= Settings.SPACECRAFT_VELOCITY * delta;
                }
                break;
            case SPACECRAFT_DOWN:
                if (this.position.y + height + Settings.SPACECRAFT_VELOCITY * delta <= Settings.GAME_HEIGHT) {
                    this.position.y += Settings.SPACECRAFT_VELOCITY * delta;
                }
                break;
            case SPACECRAFT_STRAIGHT:
                break;
            case SPACECRAFT_PAUSED:
                if (this.getActions().size < 1) {
                    this.addAction(blinking);
                }
                super.act(delta);
                direction = SPACECRAFT_STRAIGHT;
                act(delta);
                break;
        }

        collisionRect.set(position.x, position.y + 3, width, 10);
        setBounds(position.x, position.y, width, height);

    }

    // Getters dels atributs principals
    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    // Canviem la direcció de la spacecraft: Puja
    public void goUp() {
        direction = SPACECRAFT_UP;
    }

    // Canviem la direcció de la spacecraft: Baixa
    public void goDown() {
        direction = SPACECRAFT_DOWN;
    }

    // Posem la spacecraft al seu estat original
    public void goStraight() {
        direction = SPACECRAFT_STRAIGHT;
    }

    // Obtenim el TextureRegion depenent de la posició de la spacecraft
    public TextureRegion getSpacecraftTexture() {

        switch (direction) {

            case SPACECRAFT_STRAIGHT:
                return AssetManager.spacecraft;
            case SPACECRAFT_UP:
                return AssetManager.spacecraftUp;
            case SPACECRAFT_DOWN:
                return AssetManager.spacecraftDown;
            default:
                return AssetManager.spacecraft;
        }
    }

    public void reset() {
        // La posem a la posició inicial i a l'estat normal
        position.x = Settings.SPACECRAFT_STARTX;
        position.y = Settings.SPACECRAFT_STARTY;
        direction = SPACECRAFT_STRAIGHT;
        collisionRect = new Rectangle();
    }

    public void paused() {
        direction = SPACECRAFT_PAUSED;
        act(Gdx.graphics.getDeltaTime());
    }

    public void fire() {
        AssetManager.shootSound.play();
        fire.add(new Bullet(getX(), getY(), Settings.FIRE_WIDTH, Settings.FIRE_HEIGHT, fire.size()));
    }

    public void resume() {
        setVisible(true);
        direction = SPACECRAFT_STRAIGHT;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a);
        super.draw(batch, parentAlpha);
        batch.draw(getSpacecraftTexture(), position.x, position.y, width, height);
    }

    public Rectangle getCollisionRect() {
        return collisionRect;
    }
}
