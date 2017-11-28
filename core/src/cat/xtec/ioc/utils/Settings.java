package cat.xtec.ioc.utils;

public class Settings {

    // Mida del joc, s'escalarà segons la necessitat
    public static final int GAME_WIDTH = 240;
    public static final int GAME_HEIGHT = 135;

    // Propietats de la nau
    public static final float SPACECRAFT_VELOCITY = 50;
    public static final int SPACECRAFT_WIDTH = 36;
    public static final int SPACECRAFT_HEIGHT = 15;
    public static final float SPACECRAFT_STARTX = 20;
    public static final float SPACECRAFT_STARTY = GAME_HEIGHT / 2 - SPACECRAFT_HEIGHT / 2;

    //botones
    public static final int PAUSE_WIDTH = 30;
    public static final int PAUSE_HEIGHT = 30;
    public static final float PAUSE_Y = 0;
    public static final float PAUSE_X = GAME_WIDTH - PAUSE_WIDTH;

    public static final int BTN_FIRE_WIDTH = 30;
    public static final int BTN_FIRE_HEIGHT = 15;
    public static final float BTN_FIRE_Y = 0;
    public static final float BTN_FIRE_X = GAME_WIDTH - BTN_FIRE_WIDTH;

    // Rang de valors per canviar la mida de l'asteroide.
    public static final float MAX_ASTEROID = 1.5f;
    public static final float MIN_ASTEROID = 0.5f;

    // Configuració Scrollable
    public static final int ASTEROID_SPEED = -150;
    public static final int ASTEROID_GAP = 75;
    public static final int BG_SPEED = -100;


}
