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

    //Botons
    public static final int PAUSE_WIDTH = 30;
    public static final int PAUSE_HEIGHT = 30;
    public static final float PAUSE_Y = 0;
    public static final float PAUSE_X = GAME_WIDTH - PAUSE_WIDTH;
    public static final String  BTN_PAUSE_NAME = "BTNPAUSE";
    public static final String  BTN_FIRE_NAME = "BTNFIRE";

    public static final int BTN_FIRE_WIDTH = 30;
    public static final int BTN_FIRE_HEIGHT = 15;
    public static final int BTN_FIRE_MARGIN = 10;
    public static final float BTN_FIRE_Y = (GAME_HEIGHT - BTN_FIRE_MARGIN) - BTN_FIRE_HEIGHT;
    public static final float BTN_FIRE_X = (GAME_WIDTH - BTN_FIRE_MARGIN) - BTN_FIRE_WIDTH;

    public static final int FIRE_WIDTH = 10;
    public static final int FIRE_HEIGHT = 2;



    // Rang de valors per canviar la mida de l'asteroide.
    public static final float MAX_ASTEROID = 1.5f;
    public static final float MIN_ASTEROID = 0.5f;

    // Configuració Scrollable
    public static final int ASTEROID_SPEED = -150;
    public static final int ASTEROID_GAP = 75;
    public static final int BG_SPEED = -100;


    public static final String FIRE_NAME = "fire";

}
