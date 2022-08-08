import bagel.Image;


/**
 * The type Bomb.
 * @author Zhuoqing Zheng
 */
public class Bomb extends Weapon{
    private final double RANGE = 50;
    private static final Image BOMB_IMG = new Image("res/level-1/bomb.png");

    /**
     * Instantiates a new Bomb.
     */
    public Bomb() {
        super(BOMB_IMG);
        super.range = RANGE;
        super.destroySteel = true;

    }
}
