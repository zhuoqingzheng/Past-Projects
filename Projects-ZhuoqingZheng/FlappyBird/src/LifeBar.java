import bagel.Image;
import org.lwjgl.system.CallbackI;

/**
 * The type Life bar.
 * @author Zhuoqing Zheng
 */
public class LifeBar {
    private final int TOTAL_LIFE_LEVEL_0 = 3;
    private final int TOTAL_LIFE_LEVEL_1 = 6;
    private final int GAP = 50;
    private final Image FULL_HEART_IMG = new Image("res/level/fullLife.png");
    private final Image EMPTY_HEART_IMG = new Image("res/level/noLife.png ");
    private final double INITIAL_X= 100;
    private final double INITIAL_Y=15;
    private int currentLife = TOTAL_LIFE_LEVEL_0;

    /**
     * Instantiates a new Life bar.
     */
    public LifeBar(){
    }

    /**
     * Render life-bar
     */
    public void render(){
        int i;
        for (i=0; i< currentLife;i++) {
            FULL_HEART_IMG.drawFromTopLeft(INITIAL_X+GAP*i,INITIAL_Y);
        }
        if (!ShadowFlap.getLevelUp()){
            for (int j=0; j<(TOTAL_LIFE_LEVEL_0-currentLife);j++){
                EMPTY_HEART_IMG.drawFromTopLeft(INITIAL_X+GAP*i+GAP*j, INITIAL_Y);
            }
        }
        else {
            for (int j = 0; j < (TOTAL_LIFE_LEVEL_1 - currentLife); j++) {
                EMPTY_HEART_IMG.drawFromTopLeft(INITIAL_X + GAP * i + GAP * j, INITIAL_Y);
            }
        }
    }

    /**
     * Sets current life to level 1.
     */
    public void setCurrentLifeToLevel1() {
        this.currentLife = TOTAL_LIFE_LEVEL_1;
    }

    /**
     * Reduce life.
     */
    public void reduceLife(){
        currentLife -= 1;
    }

    /**
     * Game over test boolean.
     *
     * @return the boolean. This returns whether the game ends.
     */
    public boolean gameOverTest(){
        return currentLife <= 0;
    }
}
