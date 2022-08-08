import bagel.DrawOptions;
import bagel.Image;
import bagel.Input;
import bagel.Keys;
import bagel.util.Point;
import bagel.util.Rectangle;
import java.lang.Math;

/**
 * Part of Project1 solution implemented
 * The type Bird.
 * @author Zhuoqing Zheng
 */
public class Bird {

    private final Image LEVEL_1_WING_UP = new Image("res/level-1/birdWingUp.png");
    private final Image LEVEL_1_WING_DOWN = new Image("res/level-1/birdWingDown.png");
    private final double X = 200;
    private final double FLY_SIZE = 6;
    private final double FALL_SIZE = 0.4;
    private final double INITIAL_Y = 350;
    private final double Y_TERMINAL_VELOCITY = 10;
    private final double SWITCH_FRAME = 10;
    private int frameCount = 0;
    private boolean hasWeapon = false;
    private  Image wingDownImg = new Image("res/level-0/birdWingDown.png");
    private  Image wingUpImg = new Image("res/level-0/birdWingUp.png");
    private double y;
    private double yVelocity;
    private Rectangle boundingBox;

    /**
     * Instantiates a new Bird.
     */
    public Bird() {
        y = INITIAL_Y;
        yVelocity = 0;
        boundingBox = wingDownImg.getBoundingBoxAt(new Point(X, y));
    }

    /**
     * Update rectangle.
     *
     * @param input the input
     * @return the modified rectangle of the bird
     */
    public Rectangle update(Input input) {
        frameCount += 1;
        if (input.wasPressed(Keys.SPACE)) {
            yVelocity = -FLY_SIZE;
            wingDownImg.draw(X, y);
        }
        else {
            yVelocity = Math.min(yVelocity + FALL_SIZE, Y_TERMINAL_VELOCITY);
            if (frameCount % SWITCH_FRAME == 0) {
                wingUpImg.draw(X, y);
                boundingBox = wingUpImg.getBoundingBoxAt(new Point(X, y));
            }
            else {
                wingDownImg.draw(X, y);
                boundingBox = wingDownImg.getBoundingBoxAt(new Point(X, y));
            }
        }
        y += yVelocity;

        return boundingBox;
    }

    /**
     * Gets y.
     *
     * @return the y
     */
    public double getY() {
        return y;
    }

    /**
     * Gets x.
     *
     * @return the x
     */
    public double getX() {
        return X;
    }

    /**
     * Gets right-most x coordinate of bird
     *
     * @return double, the rightX of bird
     */
    public double getRight() {return boundingBox.right(); }

    /**
     * Gets box.
     *
     * @return the box
     */
    public Rectangle getBox() {
        return boundingBox;
    }

    /**
     * Respawn.
     */
    public void respawn(){
        y = INITIAL_Y;
        yVelocity=0;
    }

    /**
     * Sets bird img to level 1.
     */
    public void setBirdImgToLevel1() {
        wingDownImg = LEVEL_1_WING_DOWN;
        wingUpImg = LEVEL_1_WING_UP;
    }

    /**
     * Test if the bird will pick a weapon
     *
     * @param weapon the weapon
     * @return boolean
     */
    public boolean pickWeaponTest(Rectangle weapon) {
        // if bird does not have weapon now and collides a weapon, the bird picks up the weapon.
        if (!hasWeapon) {
            if (boundingBox.intersects(weapon)) {

                hasWeapon = true;
                return true;
            }
        }

        return false;
    }

    /**
     * Shoot weapon.
     */
    public void shootWeapon() {

        hasWeapon = false;
    }
}