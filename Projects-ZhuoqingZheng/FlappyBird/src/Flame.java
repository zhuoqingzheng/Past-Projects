import bagel.DrawOptions;
import bagel.Image;
import bagel.util.Rectangle;
import bagel.util.Point;

/**
 * The type Flame.
 * @author Zhuoqing Zheng
 */
public class Flame {
    private final DrawOptions ROTATOR = new DrawOptions().setRotation(Math.PI);
    private final Image FLAME_IMG = new Image("res/level-1/flame.png");
    private double x;
    private double topY;
    private double botY;

    /**
     * Instantiates a new Flame.
     */
    public Flame(){
    }

    /**
     * Draw the flame on the screen
     */
    public void draw() {
        FLAME_IMG.draw(x,topY);
        FLAME_IMG.draw(x,botY,ROTATOR);
    }

    /**
     * Update the coordinates of flame.
     *
     * @param x the x of its corresponding pipe.
     */
    public void update(double x) {
        this.x =x;
    }


    /**
     * Sets y.
     *
     * @param Gap the gap of pipe
     */
    public void setY(double Gap) {
       topY = Gap + FLAME_IMG.getHeight()/2;
       botY = Gap + PipeSet.getPIPE_GAP() - FLAME_IMG.getHeight()/2;

    }

    /**
     * Sets x.
     *
     * @param  x the x, the new x value to set to.
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Gets top box.
     *
     * @return the top box
     */
    public Rectangle getTopBox() {
        return FLAME_IMG.getBoundingBoxAt(new Point(x,topY));
    }

    /**
     * Gets bot box.
     *
     * @return the bot box
     */
    public Rectangle getBotBox() {
        return FLAME_IMG.getBoundingBoxAt(new Point(x, botY));
    }
}
