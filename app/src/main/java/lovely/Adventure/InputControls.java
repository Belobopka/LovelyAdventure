package lovely.Adventure;

import android.graphics.Rect;
import android.view.MotionEvent;

import java.io.IOException;
import java.util.ArrayList;

public class InputControls {
    Rect left;
    Rect right;
    Rect jump;
    Rect shoot;
    Rect pause;
    public Rect menu;
    ButtonText buttonTextLeft;
    ButtonText buttonTextRight;
    ButtonText buttonTextJump;
    ButtonText buttonTextShoot;
    ButtonText buttonTextPause;

    public boolean mainButtonClick = false;

    InputControls(int screenWidth, int screenHeight) {
        int widthButton = screenWidth / 8;
        int heightButton = screenHeight / 7;
        int rangeButton = screenWidth / 80;
        left = new Rect(rangeButton,
                screenHeight - heightButton - rangeButton,
                widthButton,
                screenHeight - rangeButton);
        buttonTextLeft = new ButtonText("Вліво",widthButton/2 - rangeButton*3,
                screenHeight - heightButton/2);

        right = new Rect(widthButton + rangeButton,
                screenHeight - heightButton - rangeButton,
                widthButton + rangeButton + widthButton,
                screenHeight - rangeButton);
        buttonTextRight = new ButtonText("Вправо",widthButton + widthButton/2 - rangeButton*3,
                screenHeight - heightButton/2);

        jump = new Rect(screenWidth - widthButton - rangeButton,
                screenHeight - heightButton - rangeButton - heightButton - rangeButton,
                screenWidth - rangeButton,
                screenHeight - rangeButton - heightButton - rangeButton);
        buttonTextJump = new ButtonText("Стрибок",screenWidth - widthButton - rangeButton,
                screenHeight - heightButton/2 - rangeButton - heightButton - rangeButton);


        shoot = new Rect(screenWidth - widthButton - rangeButton,
                screenHeight - heightButton - rangeButton,
                screenWidth - rangeButton,
                screenHeight - rangeButton);
        buttonTextShoot = new ButtonText("Вогонь!",screenWidth - widthButton - rangeButton,
                screenHeight - heightButton/2 - rangeButton);


        pause = new Rect(screenWidth - rangeButton - widthButton,
                rangeButton,
                screenWidth - rangeButton,
                rangeButton + heightButton);
        buttonTextPause = new ButtonText("Пауза",screenWidth - widthButton,
                heightButton/2 +heightButton/4);

        menu = new Rect(screenWidth/2 - widthButton,rangeButton,
                screenWidth/2 + widthButton,heightButton+ rangeButton);

    }

    public ArrayList getButtons(){
        ArrayList<Rect> currentButtonList = new ArrayList<>();
        currentButtonList.add(left);
        currentButtonList.add(right);
        currentButtonList.add(jump);
        currentButtonList.add(shoot);
        currentButtonList.add(pause);
        return  currentButtonList;
    }
    public ArrayList getTextToDraw(){
        ArrayList <ButtonText> currentButtonTextList = new ArrayList<>();
        currentButtonTextList.add(buttonTextLeft);
        currentButtonTextList.add(buttonTextRight);
        currentButtonTextList.add(buttonTextJump);
        currentButtonTextList.add(buttonTextShoot);
        currentButtonTextList.add(buttonTextPause);
        return  currentButtonTextList;
    }
    public void handleInput(MotionEvent motionEvent, LevelManager levelManager,
                            SoundManager soundManager, RectView rectView){
        int pointerCount = motionEvent.getPointerCount();

        for (int i = 0; i < pointerCount; i++) {

            int x = (int) motionEvent.getX(i);
            int y = (int) motionEvent.getY(i);

            if(levelManager.isPlaying()) {

                switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

                    case MotionEvent.ACTION_DOWN:
                        if (right.contains(x, y)) {
                            levelManager.player.setPressingRight(true);
                            levelManager.player.setPressingLeft(false);
                        } else if (left.contains(x, y)) {
                            levelManager.player.setPressingLeft(true);
                            levelManager.player.setPressingRight(false);
                        } else if (jump.contains(x, y)) {
                            levelManager.player.startJump(soundManager);

                        } else if (shoot.contains(x, y)) {
                            if (levelManager.player.shoot()) {
                                soundManager.playingSound("shoot");

                            }
                        } else if (pause.contains(x, y)) {
                            levelManager.switchPlaying();

                        }

                        break;

                    case MotionEvent.ACTION_UP:
                        if (right.contains(x, y)) {
                            levelManager.player.setPressingRight(false);
                        } else if (left.contains(x, y)) {
                            levelManager.player.setPressingLeft(false);
                        }


                        break;


                    case MotionEvent.ACTION_POINTER_DOWN:
                        if (right.contains(x, y)) {
                            levelManager.player.setPressingRight(true);
                            levelManager.player.setPressingLeft(false);
                        }
                        else if (left.contains(x, y)) {
                            levelManager.player.setPressingLeft(true);
                            levelManager.player.setPressingRight(false);
                        }
                        else if (jump.contains(x, y)) {
                            levelManager.player.startJump(soundManager);

                        }
                        else if (shoot.contains(x, y)) {
                            if (levelManager.player.shoot()) {
                                soundManager.playingSound("shoot");
                            }
                        }
                        else if (pause.contains(x, y)) {
                            levelManager.switchPlaying();
                        }
                        break;


                    case MotionEvent.ACTION_POINTER_UP:
                        if (right.contains(x, y)) {
                            if(!levelManager.player.isJumping()){
                                levelManager.player.setPressingRight(false);
                            }


                        }
                        else if (left.contains(x, y)) {
                            if(!levelManager.player.isJumping()){
                                levelManager.player.setPressingLeft(false);
                            }

                        }

                        else if (shoot.contains(x, y)) {

                        }
                        else if (jump.contains(x, y)) {

                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (right.contains(x, y)) {
                            levelManager.player.setPressingRight(true);
                            levelManager.player.setPressingLeft(false);
                        }
                        else if (left.contains(x, y)) {
                            levelManager.player.setPressingLeft(true);
                            levelManager.player.setPressingRight(false);
                        }
                }
            }
            else {
                switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

                    case MotionEvent.ACTION_DOWN:
                        if (right.contains(x, y)) {
                            rectView.moveRectViewRight(levelManager.mapWidth);
                        }
                        else if (left.contains(x, y)) {
                            rectView.moveRectViewLeft();
                        }
                        else if (jump.contains(x, y)) {
                            rectView.moveRectViewUp();
                        }
                        else if (shoot.contains(x, y)) {
                            rectView.moveRectViewDown(levelManager.mapHeight);
                        }
                        if (pause.contains(x, y)) {
                            levelManager.switchPlaying();
                            //Log.w("pause:", "DOWN" );
                        }
                        if(menu.contains(x,y)){
                        mainButtonClick = true;
                    }

                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (right.contains(x, y)) {
                            rectView.moveRectViewRight(levelManager.mapWidth);
                        }
                        else if (left.contains(x, y)) {
                            rectView.moveRectViewLeft();
                        }
                        else if (jump.contains(x, y)) {
                            rectView.moveRectViewUp();
                        }
                        else if (shoot.contains(x, y)) {
                            rectView.moveRectViewDown(levelManager.mapHeight);
                        }
                }
            }
        }
    }
}