import bagel.*;
import bagel.util.Rectangle;


import java.util.ArrayList;
import java.util.Random;

/**
 * Part of Project1 Solution implemented.
 * The type ShadowFlap.
 * @author Zhuoqing Zheng
 */
public class ShadowFlap extends AbstractGame {
    private final Image BACKGROUND_IMAGE = new Image("res/level-0/background.png");
    private final Image LEVEL_1_BACKGROUND_IMG = new Image("res/level-1/background.png");
    private final String INSTRUCTION_MSG = "PRESS SPACE TO START";
    private final String GAME_OVER_MSG = "GAME OVER!";
    private final String CONGRATS_MSG = "CONGRATULATIONS!";
    private final String SCORE_MSG = "SCORE: ";
    private final String FINAL_SCORE_MSG = "FINAL SCORE: ";
    private final String LEVEL_UP_MSG = "LEVEL-UP!";
    private final String SHOOT_MSG = "PRESS 'S' TO SHOOT";
    private final int SHOOT_MSG_OFFSET = 68;
    private static final int INITIAL_ADD_PIPE_FREQ=100;
    private final double SCORE_MSG_COORDINATE=100;
    private final int MIN_TIME_SCALE= 1;
    private final int MAX_TIME_SCALE = 5;
    private final int LEVEL_0_WIN=10;
    private final int LEVEL_1_WIN=30;
    private final int FONT_SIZE = 48;
    private final int LEVEL_UP_FRAME_LAST = 150;
    private final Font FONT = new Font("res/font/slkscr.ttf", FONT_SIZE);
    private final int SELECT_TYPE_1 = 1;
    private final int SCORE_MSG_OFFSET = 75;
    private final int TYPE_NUMBER = 2;
    private final Random RAND = new Random();
    private static int addPipeFreq= INITIAL_ADD_PIPE_FREQ;
    private int timeScale=MIN_TIME_SCALE;
    private Bird bird;
    private LifeBar lifeBar;
    private int frameCounter;
    private int score;
    private boolean gameOn;
    private boolean collision;
    private boolean win;
    private SteelPipe tempPipe;
    private static boolean levelUp = false;
    private boolean endOfLevel0 = false;
    private boolean startLevel1 = false;
    private ArrayList<PipeSet> pipeSetArrayList= new ArrayList<>();
    private ArrayList<PipeSet> pipeToRemove = new ArrayList<>();
    private ArrayList<Weapon> weaponArrayList = new ArrayList<>();
    private ArrayList<Weapon> weaponToRemove = new ArrayList<>();

    /**
     * Instantiates a new ShadowFlap.
     */
    public ShadowFlap() {
        super(1024, 768, "ShadowFlap");
        bird = new Bird();
        score = 0;
        gameOn = false;
        collision = false;
        win = false;
        levelUp = false;
        lifeBar = new LifeBar();
        frameCounter = INITIAL_ADD_PIPE_FREQ;

    }

    /**
     * The entry point for the program.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        ShadowFlap game = new ShadowFlap();
        game.run();
    }

    /**
     * Performs a state update.
     * allows the game to exit when the escape key is pressed.
     */
    @Override
    public void update(Input input) {

        if (!levelUp || endOfLevel0) {
            BACKGROUND_IMAGE.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);
        }
        // if the game is leveled up, change background
        if (levelUp) {
            LEVEL_1_BACKGROUND_IMG.draw(Window.getWidth()/2.0,Window.getHeight()/2.0);
        }
        if (input.wasPressed(Keys.ESCAPE)) {
            Window.close();
        }

        // game has not started
        if (!gameOn) {
            renderInstructionScreen(input);
        }
        if (birdOutOfBound()){
            lifeBar.reduceLife();
            bird.respawn();
        }


        // game over
        if (lifeBar.gameOverTest()) {
            renderGameOverScreen();
        }



        // game won
        if (win) {
            renderWinScreen();
        }

        // game is active and is at level0
        if (gameOn && !lifeBar.gameOverTest() && !endOfLevel0 && !levelUp)   {
            stage0(input);
        }
        // if level0 ends, render the level-up msg for a short time
        if (endOfLevel0) {
           endOfLevel0();
        }
        // render instruction msg before level1 starts
        if (levelUp && !startLevel1) {
            renderInstructionScreen(input);
            score = 0 ;
        }
        // game is running at level-1
        if (!lifeBar.gameOverTest() && startLevel1 && !win) {
              stage1(input);


        }
    }

    /**
     * Bird out of bound boolean.
     *
     * @return the boolean
     */
    public boolean birdOutOfBound() {
        return (bird.getY() > Window.getHeight()) || (bird.getY() < 0);
    }

    private void renderInstructionScreen(Input input) {
        // paint the instruction on screen for level-0
        if (!levelUp) {
            FONT.drawString(INSTRUCTION_MSG, (Window.getWidth() / 2.0 - FONT.getWidth(INSTRUCTION_MSG) / 2.0),
                    Window.getHeight() / 2.0 + FONT_SIZE / 2.0);
            if (input.wasPressed(Keys.SPACE)) {
                gameOn = true;
            }
        }
        // instruction for level-1
        else if (levelUp) {
            FONT.drawString(INSTRUCTION_MSG, (Window.getWidth() / 2.0 - FONT.getWidth(INSTRUCTION_MSG) / 2.0),
                    Window.getHeight() / 2.0 + FONT_SIZE / 2.0);
            FONT.drawString(SHOOT_MSG, Window.getWidth()/2.0-FONT.getWidth(SHOOT_MSG)/2.0,
                    Window.getHeight()/2.0 + FONT_SIZE/2.0 + SHOOT_MSG_OFFSET);
            if (input.wasPressed(Keys.SPACE)) {
                startLevel1 = true;
            }

        }
    }

    private void renderGameOverScreen() {
        FONT.drawString(GAME_OVER_MSG, (Window.getWidth()/2.0-(FONT.getWidth(GAME_OVER_MSG)/2.0)),
                (Window.getHeight()/2.0+(FONT_SIZE/2.0)));
        String finalScoreMsg = FINAL_SCORE_MSG + score;
        FONT.drawString(finalScoreMsg, (Window.getWidth()/2.0-(FONT.getWidth(finalScoreMsg)/2.0)),
                (Window.getHeight()/2.0+(FONT_SIZE/2.0))+SCORE_MSG_OFFSET);
    }

    private void renderWinScreen() {
        FONT.drawString(CONGRATS_MSG, (Window.getWidth()/2.0-(FONT.getWidth(CONGRATS_MSG)/2.0)),
                (Window.getHeight()/2.0+(FONT_SIZE/2.0)));

    }


    private  boolean detectCollision(Rectangle birdBox, Rectangle topPipeBox, Rectangle bottomPipeBox) {
        // check for collision

        return birdBox.intersects(topPipeBox) ||
                birdBox.intersects(bottomPipeBox);
    }

    private void detectDestroy(Weapon weapon, PipeSet pipeSet) {

        if (weapon.getActiveStatus() && detectCollision(weapon.getBox(),pipeSet.getTopBox(),
                pipeSet.getBottomBox())) {
            // if weapon can destroy pipe, score up
            if (weapon.destroySteel || (!weapon.destroySteel && !pipeSet.isSteel()) ) {
                pipeToRemove.add(pipeSet);
                score++;
            }
            // if the weapon collides pipe, regardless if it can destroy, remove weapon.
            weaponToRemove.add(weapon);
        }


    }



    private void updateScore(PipeSet pipe) {

        if (bird.getX() > pipe.getTopBox().right() && !pipe.getPassed()) {
            score += 1;
            pipe.setPassed();
        }
        // detect level up
        if (score == LEVEL_0_WIN && !levelUp) {

            endOfLevel0 = true;
            frameCounter = 0;

        }
        // detect win
        if (score == LEVEL_1_WIN) {
            win = true;
        }
    }



    private void stage0(Input input){
        modifyTimeScale(input);
        frameCounter += 1 ;
        // add pipe after at certain frequency
        if(frameCounter >= addPipeFreq){
            pipeSetArrayList.add(new PlasticPipe());
            frameCounter = 0;
        }
        bird.update(input);
        Rectangle birdBox = bird.getBox();
        // draw pipes
        for(PipeSet pipe: pipeSetArrayList) {
            pipe.update();
            Rectangle topPipeBox = pipe.getTopBox();
            Rectangle bottomPipeBox = pipe.getBottomBox();
            collision = detectCollision(birdBox, topPipeBox, bottomPipeBox);
            updateScore(pipe);
            if (collision) {
                lifeBar.reduceLife();
                pipeToRemove.add(pipe);
            }
            if (pipeOutOfBound(pipe)){
                pipeToRemove.add(pipe);
            }
        }
        // remove pipes which are out of bound or collided
        pipeSetArrayList.removeAll(pipeToRemove);
        pipeToRemove.clear();
        lifeBar.render();
        String scoreMsg = SCORE_MSG + score;
        FONT.drawString(scoreMsg, SCORE_MSG_COORDINATE, SCORE_MSG_COORDINATE);
    }


    private boolean pipeOutOfBound(PipeSet pipe){
        if (pipe.getTopBox().right() < 0 ){
            return true;
        }
        return false;
    }


    private void randomGeneratePipe() {
        int rand = RAND.nextInt(TYPE_NUMBER);
        if (rand==SELECT_TYPE_1) {
            pipeSetArrayList.add(new PlasticPipe());
        }
        else  {
            pipeSetArrayList.add(new SteelPipe());

        }
        frameCounter = 0;
    }


    private void randomGenerateWeapon() {
        int rand = RAND.nextInt(TYPE_NUMBER) ;
            if (rand == SELECT_TYPE_1) {
                weaponArrayList.add(new Rock());
            }
            else {
                weaponArrayList.add(new Bomb());
        }

    }

    /**
     * Get the level-up boolean.
     *
     * @return the boolean
     */
    public static boolean getLevelUp(){return levelUp;}


    private void detectCollideLevel1(PipeSet pipe){
        Rectangle birdBox = bird.getBox();
        Rectangle topPipeBox = pipe.getTopBox();
        Rectangle bottomPipeBox = pipe.getBottomBox();
        // if bird collides with pipe, collision is true.
        collision = detectCollision(birdBox, topPipeBox, bottomPipeBox);
        // if it is steelPipe, check if there is collision between bird and flame
        if (pipe.isSteel() && !collision) {
            tempPipe = (SteelPipe) pipe;
            if (tempPipe.getFlameStatus()) {
                collision = detectCollision(birdBox, tempPipe.getFLAME().getTopBox(),
                        tempPipe.getFLAME().getBotBox());
            }
        }

    }

    private void modifyTimeScale(Input input) {
        if (input.wasPressed(Keys.L)) {
            if (timeScale < MAX_TIME_SCALE) {
                timeScale += 1;
                addPipeFreq /= PipeSet.getScaleFactor();
                PipeSet.scaleSpeedUp();
            }
        }
        if (input.wasPressed(Keys.K)) {
            if (timeScale > MIN_TIME_SCALE) {
                timeScale -= 1;
                addPipeFreq *= PipeSet.getScaleFactor();
                PipeSet.scaleSpeedDown();
            }
        }
    }


    private void endOfLevel0() {
        frameCounter += 1;
        pipeSetArrayList.clear();
        FONT.drawString(LEVEL_UP_MSG, (Window.getWidth()/2.0-FONT.getWidth(LEVEL_UP_MSG)/2),
                Window.getHeight()/2.0 +FONT_SIZE/2.0 );
        if (frameCounter == LEVEL_UP_FRAME_LAST) {
            endOfLevel0 = false;
            frameCounter=INITIAL_ADD_PIPE_FREQ;
            levelUp = true;
        }
        bird.setBirdImgToLevel1();
        lifeBar.setCurrentLifeToLevel1();
        bird.respawn();
        resetTimeScale();
    }


    private void stage1(Input input){
        modifyTimeScale(input);
        bird.update(input);
        lifeBar.render();
        frameCounter +=1;
        if (frameCounter >= addPipeFreq) {
            randomGeneratePipe();

        }
        // generate new weapon in the centre between different pipes
        if (frameCounter == addPipeFreq/2) {
            randomGenerateWeapon();
        }
        // update and render pipes
        for(PipeSet pipe: pipeSetArrayList) {
            pipe.update();
            detectCollideLevel1(pipe);
            updateScore(pipe);
            if (collision) {
                lifeBar.reduceLife();
                pipeToRemove.add(pipe);
            }
            if (pipeOutOfBound(pipe)){
                pipeToRemove.add(pipe);
            }
        }
       // update and render weapons
        for (Weapon weapon: weaponArrayList) {
            // shoot weapon
            if (input.wasPressed(Keys.S)){
                if (weapon.getPickedStatus()){
                    bird.shootWeapon();
                    weapon.shoot();
                }
            }
            // check if bird picks up weapon
            if (!weapon.getActiveStatus()) {
                if (bird.pickWeaponTest(weapon.getBox())) {
                    weapon.setPickedUpStatus();
                }
            }
            if (weapon.isToRemove() || weapon.outOfBound()) {
                weaponToRemove.add(weapon);
            }
            // test if weapon destroy pipes
            for (PipeSet pipe: pipeSetArrayList) {
                detectDestroy(weapon,pipe);
            }


            weapon.draw(bird.getRight(), bird.getY());
        }
        // remove weapons which are to be removed
        weaponArrayList.removeAll(weaponToRemove);
        pipeSetArrayList.removeAll(pipeToRemove);
        pipeToRemove.clear();
        weaponToRemove.clear();
        String scoreMsg = SCORE_MSG + score;
        FONT.drawString(scoreMsg, SCORE_MSG_COORDINATE, SCORE_MSG_COORDINATE);

    }


    private void resetTimeScale() {
        // after level-0 end, reset the timescale to 1
        while (timeScale > MIN_TIME_SCALE) {
            timeScale -= 1;
            addPipeFreq *= PipeSet.getScaleFactor();
            PipeSet.scaleSpeedDown();
        }

    }



}

