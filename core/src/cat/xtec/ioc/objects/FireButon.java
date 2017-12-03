//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cat.xtec.ioc.objects;

import cat.xtec.ioc.helpers.AssetManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class FireButon extends Actor {
    private Vector2 position;
    private int width;
    private int height;

    public FireButon(float x, float y, int width, int height) {
        this.position = new Vector2(x, y);
        this.width = width;
        this.height = height;
        this.setTouchable(Touchable.enabled);
    }

    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(AssetManager.fireBtn, this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    public float getX() {
        return this.position.x;
    }

    public float getY() {
        return this.position.y;
    }

    public float getWidth() {
        return (float)this.width;
    }

    public float getHeight() {
        return (float)this.height;
    }

    public boolean touchDown(int xx, int yy) {
        Gdx.app.log("Coordenadas", "X: " + xx + "\tY: " + yy);
        return xx >= 1615 && xx <= 1815 && yy >= 900 && yy <= 970;
    }
}
