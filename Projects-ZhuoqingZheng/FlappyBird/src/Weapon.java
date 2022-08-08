import bagel.Image;
import bagel.Window;
import bagel.util.Point;
import bagel.util.Rectangle;




/**
 * The type Weapon.
 * @author Zhuoqing Zheng
 */
public abstract class Weapon {
    private final double SHOOT_SPEED = 5;
    private final Image WEAPON_IMG;
    private final double LOWER_LIMIT = 500;
    private final double UPPER_LIMIT = 100;
    private double stepSize = PipeSet.getPipeSpeed();
    private double x = Window.getWidth();
    private double y;
    protected double range;
    private int frameCount=0;
    protected boolean destroySteel;
    private boolean activeStatus =false;
    private boolean pickedUpStatus = false;
    private boolean toRemove = false;

    /**
     * Instantiates a new Weapon.
     *
     * @param img the img of the weapon.
     */
    Weapon(Image img) {
        this.WEAPON_IMG = img;
        randomGenerateY();

    }

    /**
     * Draw the weapon on screen.
     *
     * @param birdRight the bird's right-most x coordinate
     * @param birdY     the bird y coordinate
     */
    public void draw(double birdRight, double birdY) {
        WEAPON_IMG.draw(x,y);
        if (!pickedUpStatus && !activeStatus) {
            x -= PipeSet.getPipeSpeed();
        }
        // if weapon is picked up, fly with bird
        else if (pickedUpStatus) {
            x = birdRight;
            y = birdY;
        }
        // if weapon is shot, move forward
        else if (activeStatus) {

            x += SHOOT_SPEED;
            frameCount ++;
            // if weapon reaches limit of range, remove it
            if (frameCount == range) {
                toRemove = true;
                activeStatus = false;

            }


        }

    }

    /**
     * Random generate y.
     */
    public void randomGenerateY() {
        y = (double)Math.floor(Math.random()*(LOWER_LIMIT - UPPER_LIMIT +1)+UPPER_LIMIT);
    }

    /**
     * Gets boundingBox of Weapon.
     *
     * @return the boundingBox
     */
    public Rectangle getBox() {
        return WEAPON_IMG.getBoundingBoxAt(new Point(x,y));
    }

    /**
     * Sets picked up status.
     */
    public void setPickedUpStatus() {
        if (pickedUpStatus) {
            pickedUpStatus = false;
        }
        else {
            pickedUpStatus = true;
        }
    }

    /**
     * Gets picked status.
     *
     * @return the picked status
     */
    public boolean getPickedStatus() {
        return pickedUpStatus;
    }

    /**
     * Gets active status.
     *
     * @return the boolean active status
     */
    public boolean getActiveStatus() {
        return activeStatus;
    }

    /**
     * Shoot.
     */
    public void shoot (){
        setPickedUpStatus();
        activeStatus = true;
    }

    /**
     * gets isToRemove boolean.
     *
     * @return the boolean toRemove to indicate if the weapon is needed to be removed in the Arraylist
     */
    public boolean isToRemove() {
        return toRemove;
    }

    /**
     *
     *
     * @return the boolean
     */
    public boolean outOfBound() {return x<0 ;}
}
