package cat.xtec.ioc.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;

import cat.xtec.ioc.helpers.AssetManager;
import cat.xtec.ioc.utils.Settings;

import static cat.xtec.ioc.objects.Spacecraft.blinking;

/**
 * Created by ronny on 3/12/17.
 */

public class Bullet extends Actor {
    private Vector2 position;
    private int width;
    private int height;
    private Rectangle collisionRect;
    public static boolean BULLET_PAUSED = false;

    //Parpadejar quan es pausa
    public static Action blinking;


    public Bullet(float x, float y, int width, int height) {
        // Creem el rectangle de col·lisions
        collisionRect = new Rectangle();
        this.position = new Vector2(x + 25, y + 5);
        this.width = width;
        this.height = height;
        setName(Settings.FIRE_NAME);
        //Accio de parpadeix
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

        // Creem el rectangle de col·lisions
        collisionRect = new Rectangle();

        this.setTouchable(Touchable.disabled);
        // Per a la gestio de hit
        setBounds(position.x, position.y, width, height);
        addAction(Actions.moveTo(Gdx.graphics.getWidth(), getY(), 5));
        act(Gdx.graphics.getDeltaTime());
    }


    @Override
    public void act(float delta) {
        if (BULLET_PAUSED) {
            if (this.getActions().size == 1) {
                this.addAction(blinking);
            }
        } else {
            position.x += 1;
            collisionRect.set(position.x, position.y, width, height);
            setBounds(position.x, position.y, width, height);
            setVisible(true);
        }
        BULLET_PAUSED = false;
        super.act(delta);

    }

    public void setPause(boolean pause) {
        BULLET_PAUSED = pause;
        act(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(AssetManager.fire, this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    public float getX() {
        return this.position.x;
    }

    public float getY() {
        return this.position.y;
    }

    public float getWidth() {
        return (float) this.width;
    }

    public float getHeight() {
        return (float) this.height;
    }

    public Rectangle getCollisionRect() {
        return collisionRect;
    }


}
