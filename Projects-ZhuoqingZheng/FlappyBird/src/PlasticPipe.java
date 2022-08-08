import bagel.Image;


/**
 * The type Plastic pipe.
 * @author Zhuoqing Zheng
 */
public class PlasticPipe extends PipeSet{
    private static final Image PLASTIC_IMG=new Image("res/level/plasticPipe.png");

    /**
     * Instantiates a new Plastic pipe.
     */
    public PlasticPipe(){
           super(PLASTIC_IMG);
    }

    /**
     * Gets isSteel
     * @return boolean This returns if the pipe is steel.
     */
    @Override
    public boolean isSteel() {
        return false;
    }

}
