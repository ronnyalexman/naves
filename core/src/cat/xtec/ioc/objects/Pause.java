package cat.xtec.ioc.objects;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import cat.xtec.ioc.helpers.AssetManager;

import static cat.xtec.ioc.utils.Settings.BTN_PAUSE_NAME;
import static cat.xtec.ioc.utils.Settings.GAME_HEIGHT;
import static cat.xtec.ioc.utils.Settings.GAME_WIDTH;

/**
 * Created by ronny on 20/11/17.
 */

public class Pause extends Actor {
    // Paràmetres del botó
    private Vector2 position;
    private int width, height;

    public Pause(float x, float y, int width, int height) {
        this.position = new Vector2(x, y);
        this.width = width;
        this.height = height;
        // Per a la gestio de hit
        setBounds(position.x, position.y, width, height);
        setName(BTN_PAUSE_NAME);
        setTouchable(Touchable.enabled);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(AssetManager.pause, getX(), getY(), getWidth(), getHeight());
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
}
