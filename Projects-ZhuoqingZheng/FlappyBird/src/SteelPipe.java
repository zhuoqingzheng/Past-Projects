import bagel.Image;

/**
 * The type Steel pipe.
 * @author Zhuoqing Zheng
 */
public class SteelPipe extends PipeSet{
    private final int FLAME_FREQ = 20;
    private final int FLAME_LAST = 30;
    private static final Image STEEL_IMG = new Image("res/level-1/steelPipe.png");
    private final Flame FLAME = new Flame();
    private boolean flameStatus = false;
    private int frameCount = 0;

    /**
     * Instantiates a new Steel pipe.
     */
    public SteelPipe() {
        super(STEEL_IMG);
        FLAME.setX(super.getPipeX());
        FLAME.setY(super.getRandGap());

    }

    /**
     *  update the movement of pipes and its flames
     */
    @Override
    public void  update() {
        super.update();
        FLAME.update(super.getPipeX());
        frameCount += 1;
        // if it is time to shoot flame, start flame
        if (frameCount == FLAME_FREQ && !flameStatus) {
            flameStatus = true;
            frameCount = 0;
        }
        // if flame has last long enough, cease flame
        if (frameCount == FLAME_LAST && flameStatus){
            flameStatus = false;
            frameCount = 0;
        }
        // when it should shoot flame, render flame on screen
        if (flameStatus) {
            FLAME.draw();
        }
    }

    /**
     * Gets flame.
     *
     * @return the flame
     */
    public Flame getFLAME() {
        return FLAME;
    }

    /**
     * Gets flame status.
     *
     * @return the flame status
     */
    public boolean getFlameStatus() {
        return flameStatus;
    }

    /**
     * Gets isSteel
     * @return boolean This returns if the pipe is steel.
     */
    @Override
    public boolean isSteel() {
        return true;

}
