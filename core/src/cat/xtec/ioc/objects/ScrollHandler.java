package cat.xtec.ioc.objects;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

import java.util.ArrayList;
import java.util.Random;

import cat.xtec.ioc.utils.Methods;
import cat.xtec.ioc.utils.Settings;

public class ScrollHandler extends Group {

    // Fons de pantalla
    Background bg, bg_back;

    // Asteroides
    int numAsteroids;
    private ArrayList<Asteroid> asteroids;

    // Objecte Random
    Random r;

    public ScrollHandler() {

        // Creem els dos fons
        bg = new Background(0, 0, Settings.GAME_WIDTH * 2, Settings.GAME_HEIGHT, Settings.BG_SPEED);
        bg_back = new Background(bg.getTailX(), 0, Settings.GAME_WIDTH * 2, Settings.GAME_HEIGHT, Settings.BG_SPEED);

        // Afegim els fons al grup
        addActor(bg);
        addActor(bg_back);

        // Creem l'objecte random
        r = new Random();

        // Comencem amb 3 asteroids
        numAsteroids = 3;

        // Creem l'ArrayList
        asteroids = new ArrayList<Asteroid>();
        createAsteroids();
    }


    @Override
    public void act(float delta) {
        super.act(delta);
        // Si algun element està fora de la pantalla, fem un reset de l'element.
        if (bg.isLeftOfScreen()) {
            bg.reset(bg_back.getTailX());

        } else if (bg_back.isLeftOfScreen()) {
            bg_back.reset(bg.getTailX());

        }

        for (int i = 0; i < asteroids.size(); i++) {

            Asteroid asteroid = asteroids.get(i);
            if (asteroid.isLeftOfScreen()) {
                if (i == 0) {
                    asteroid.reset(asteroids.get(asteroids.size() - 1).getTailX() + Settings.ASTEROID_GAP);
                } else {
                    asteroid.reset(asteroids.get(i - 1).getTailX() + Settings.ASTEROID_GAP);
                }
            }
        }
    }

    public boolean collides(Spacecraft nau) {

        // Comprovem les col·lisions entre cada asteroid i la nau
        for (Asteroid asteroid : asteroids) {
            if (asteroid.collides(nau)) {
                return true;
            }
        }
        return false;
    }

    public void reset() {
        createAsteroids();
    }

    public ArrayList<Asteroid> getAsteroids() {
        return asteroids;
    }


    public ArrayList<Asteroid> createAsteroids() {
        asteroids.clear();
        Asteroid asteroid;
        for (int i = 0; i < numAsteroids; i++) {
            // Afegim l'asteroid.
            asteroid = createAsteroid();
            asteroids.add(asteroid);
            // Afegim l'asteroide al grup d'actors
            addActor(asteroid);
        }
        return asteroids;
    }

    /**
     * Ex 3. a)Els asteroides es comportaran de manera independent entre ells
     * (seran objectes que anirem instanciant amb “new”)
     *
     * @return
     */
    private Asteroid createAsteroid() {
        // Creem la mida al·leatòria
        float newSize = Methods.randomFloat(Settings.MIN_ASTEROID, Settings.MAX_ASTEROID) * 34;
        return new Asteroid(Settings.GAME_WIDTH, r.nextInt(Settings.GAME_HEIGHT - (int) newSize), newSize, newSize, Settings.ASTEROID_SPEED);
    }

    public int collides(ArrayList<Bullet> bullets) {
        // Comprovem les col·lisions entre cada asteroid i el tret
        for (int i = 0; i < asteroids.size(); i++) {
            for (Bullet b : bullets) {
                if (asteroids.get(i).collides(b)) {
                    return i;
                }
            }
        }
        return -1;
    }

    public void removeAsteroid(int i) {
        this.asteroids.get(i).reset(Settings.GAME_WIDTH);
    }
}