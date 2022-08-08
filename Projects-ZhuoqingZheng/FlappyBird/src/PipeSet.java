import bagel.DrawOptions;
import bagel.Image;
import bagel.Window;
import bagel.util.Point;
import bagel.util.Rectangle;
import java.util.Random;


/**
 * part of project 1 solution implemented
 * the type PipeSet
 * @author Zhuoqing Zheng
 */
public abstract class PipeSet {
    private final Image PIPE_IMAGE;
    private static final double INITIAL_STEP_SIZE=5;
    private static double pipeSpeed = INITIAL_STEP_SIZE;
    private static final int PIPE_GAP = 168;
    private final double HIGH_GAP = 100;
    private final double MIDDLE_GAP = 300;
    private final double LOW_GAP = 500;
    private final int MODE_NUMBER = 3;
    private static final double SCALE_FACTOR = 1.5;
    private final int SELECT_MIDDLE_GAP = 1;
    private final int SELECT_HIGH_GAP = 2;
    private final DrawOptions ROTATOR = new DrawOptions().setRotation(Math.PI);
    private final Random RAND = new Random();
    private double randGap;
    private double topY;
    private double botY;
    private double pipeX = Window.getWidth();
    private boolean passed= false;

    /**
     * Instantiates a new Pipe set.
     *
     * @param img the img
     */
    public PipeSet(Image img){
         this.PIPE_IMAGE=img;

         if (!ShadowFlap.getLevelUp()) {
             randomGenerateLevel0();
         }
         else{
             randomGenerateLevel1();
         }
    }


    /**
     * Render pipe set.
     */
    public void renderPipeSet() {
        PIPE_IMAGE.draw(pipeX, topY);
        PIPE_IMAGE.draw(pipeX, botY, ROTATOR);

    }

    /**
     * Update.
     */
    public void update() {
        renderPipeSet();
        pipeX -= pipeSpeed;

    }

    /**
     * Gets top box.
     *
     * @return the top box
     */
    public Rectangle getTopBox() {
        return PIPE_IMAGE.getBoundingBoxAt(new Point(pipeX, topY));

    }

    /**
     * Gets bottom box.
     *
     * @return the bottom box
     */
    public Rectangle getBottomBox() {
        return PIPE_IMAGE.getBoundingBoxAt(new Point(pipeX, botY));
    }

    /**
     * Scale speed up.
     */
    public static void scaleSpeedUp(){
        pipeSpeed *= SCALE_FACTOR;
    }

    /**
     * Scale speed down.
     */
    public static void scaleSpeedDown(){
        pipeSpeed /= SCALE_FACTOR;
    }

    /**
     * Set passed.
     */
    public void setPassed(){
        passed = true;
    }

    /**
     * Get passed boolean.
     *
     * @return the boolean
     */
    public boolean getPassed(){
        return passed;
    }

    /**
     * Get scale factor double.
     *
     * @return the double
     */
    public static double getScaleFactor(){
        return SCALE_FACTOR;
    }

    /**
     * Random generate y coordinates for both top and bottom pipe at level 0.
     */
    public void randomGenerateLevel0() {

            int randNum = RAND.nextInt(MODE_NUMBER);
            if (randNum == SELECT_HIGH_GAP) {
                topY=HIGH_GAP -PIPE_IMAGE.getHeight()/2;
                botY=HIGH_GAP+ PIPE_GAP+PIPE_IMAGE.getHeight()/2;
            }
            else if (randNum == SELECT_MIDDLE_GAP) {
                topY=MIDDLE_GAP-PIPE_IMAGE.getHeight()/2;
                botY=MIDDLE_GAP+ PIPE_GAP+PIPE_IMAGE.getHeight()/2;
            }
            else {
                topY=LOW_GAP-PIPE_IMAGE.getHeight()/2;
                botY=LOW_GAP+ PIPE_GAP+PIPE_IMAGE.getHeight()/2;
            }

    }


    /**
     * Random generate level 1.
     */
    public void randomGenerateLevel1() {
          randGap = (double)Math.floor(Math.random()*(LOW_GAP - HIGH_GAP +1)+HIGH_GAP);
          topY = randGap - PIPE_IMAGE.getHeight()/2;
          botY = randGap + PIPE_GAP + PIPE_IMAGE.getHeight()/2;


    }

    /**
     * Gets IsSteel.
     *
     * @return the boolean
     */
    public abstract boolean isSteel();


    /**
     * Gets rand gap.
     *
     * @return double, the rand gap
     */
    public double getRandGap() {
        return randGap;
    }

    /**
     * Gets pipe gap.
     *
     * @return int, the pipe gap
     */
    public static int getPIPE_GAP() {
        return PIPE_GAP;
    }

    /**
     * Gets pipe x.
     *
     * @return double, the pipe x
     */
    public double getPipeX() {
        return pipeX;
    }

    /**
     * Gets pipe speed.
     *
     * @return double, the pipe speed
     */
    public static double getPipeSpeed() {
        return pipeSpeed;
    }
}
