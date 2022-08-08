import bagel.Image;

/**
 * The type Rock.
 * @author Zhuoqing Zheng
 */
public class Rock extends Weapon{
    private final double RANGE = 25.0;
    private final static Image ROCK_IMG = new Image("res/level-1/rock.png");

    /**
     * Instantiates a new Rock.
     */
    public Rock() {
        super(ROCK_IMG);
        super.destroySteel = false;
        super.range = RANGE;

    }
}
