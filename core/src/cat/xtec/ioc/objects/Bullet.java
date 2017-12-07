package cat.xtec.ioc.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import cat.xtec.ioc.helpers.AssetManager;
import cat.xtec.ioc.utils.Settings;

/**
 * Created by ronny on 3/12/17.
 */

public class Bullet extends Actor {
    private Vector2 position;
    private int width;
    private int height;
    private Rectangle collisionRect;


    public Bullet(float x, float y, int width, int height) {
        // Creem el rectangle de col·lisions
        collisionRect = new Rectangle();
        this.position = new Vector2(x+25, y+5);
        this.width = width;
        this.height = height;
        setName(Settings.FIRE_NAME);

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
        super.act(delta);
        position.x +=1;
        collisionRect.set(position.x, position.y, width, height);
        setBounds(position.x, position.y, width, height);
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
