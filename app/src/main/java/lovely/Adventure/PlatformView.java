package lovely.Adventure;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.ArrayList;

import lovely.Adventure.Background.Background;
import lovely.Adventure.Objects.Hat;
import lovely.Adventure.Teleport.LocationSave;
import lovely.Adventure.Teleport.Teleport;

public class PlatformView extends SurfaceView implements Runnable {

    private boolean debugging = false;
    private volatile boolean activeGame;
    private Thread gameThread = null;
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;
    private Context context;
    private SoundManager soundManager;
    private LevelManager levelManager;
    private RectView rectView;
    InputControls inputControls;
    private int screenX;
    private int screenY;
    long startFrameTime;
    long thisFrameTime;
    long framesPerSecond;
    PlayerStatus playerStatus;
    private Player player;
    private Bitmap lifeHud;
    PlatformActivity platformActivity;
    Destroyer destroyer;
    PlatformView(Context context,int x, int y){
        super(context);
        screenX = x;
        screenY = y;
        this.context = context;
        surfaceHolder = getHolder();
        paint = new Paint();
        soundManager = new SoundManager();
        rectView = new RectView(screenX,screenY);
        soundManager.loadingSound(context);
        playerStatus = new PlayerStatus();
        int resID = context.getResources().getIdentifier
                ("lifed", "drawable",context.getPackageName());
        lifeHud = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),
                resID), ( rectView.getPixelsPerMetreX()),
                 (rectView.getPixelsPerMetreY()),false);
        loadinglevel("UndergroundLevel");
        player = (Player) levelManager.gameObjects.get(levelManager.playerIndex);
        platformActivity = (PlatformActivity) context;
        destroyer = new Destroyer();

    }

    public void loadinglevel(String levelName){
        inputControls = new InputControls(rectView.getScreenWidth(), rectView.getScreenHeight());

        levelManager = new LevelManager(context, rectView.getPixelsPerMetreX(),
                rectView.getScreenWidth(), levelName);

        PointF location = new PointF(levelManager.pointer3D.x, levelManager.pointer3D.y);
        playerStatus.saveLocation(location);
        levelManager.player.gun.setFireRate(playerStatus.getGunClips());

        rectView.setWorldCentre(levelManager.gameObjects.get(levelManager.playerIndex).getPointer3D().x,
                levelManager.gameObjects.get(levelManager.playerIndex).getPointer3D().y);
    }
    @Override
    public void run() {
        while (activeGame) {
            startFrameTime = System.currentTimeMillis();
            update();
            draw();
            thisFrameTime = System.currentTimeMillis() - startFrameTime;
                if (thisFrameTime >= 1) {
                framesPerSecond = 1000 / thisFrameTime;
                }
            }

    }
    public void update(){
        if(inputControls.mainButtonClick){
            try {
                TopResults.setNewResult(playerStatus.getCoins());
            } catch (IOException e) {
                e.printStackTrace();
            }
            platformActivity.returnToMain();
        }
        for (GameObject gameObject : levelManager.gameObjects) {
            if (gameObject.isActive()) {
                if (!rectView.clipObjects(gameObject.getPointer3D().x,
                        gameObject.getPointer3D().y,
                        gameObject.getWidth(), gameObject.getHeight())) {
                    gameObject.setVisible(true);
                    int hit = levelManager.player.checkCollisions(gameObject.getHitbox());
                    if (hit > 0) {
                        switch (gameObject.getType()) {
                            case 'c':
                                soundManager.playingSound("coin_pickup");
                                gameObject.setActive(false);
                                gameObject.setVisible(false);
                                playerStatus.gotCoin();
                                if (hit != 2) {
                                    levelManager.player.restorePreviousSpeed();
                                }
                                break;

                            case 'u':
                                soundManager.playingSound("gun_upgrade");
                                gameObject.setActive(false);
                                gameObject.setVisible(false);
                                levelManager.player.gun.upgradeRateOfFire();
                                playerStatus.increaseGunClips();
                                if (hit != 2) {
                                    levelManager.player.restorePreviousSpeed();
                                }
                                break;

                            case 'e':
                                gameObject.setActive(false);
                                gameObject.setVisible(false);
                                soundManager.playingSound("extra_life");
                                playerStatus.addLife();
                                if (hit != 2) {
                                    levelManager.player.restorePreviousSpeed();
                                }
                                break;

                            case 'd':
                                PointF location;
                                soundManager.playingSound("player_burn");
                                playerStatus.loseLife();
                                location = new PointF(playerStatus.loadLocation().x,
                                        playerStatus.loadLocation().y);
                                levelManager.player.setPointer3DX(location.x);
                                levelManager.player.setPointer3DY(location.y);
                                levelManager.player.setSpeedX(0);
                                break;

                            case 'g':
                                soundManager.playingSound("player_burn");
                                playerStatus.loseLife();
                                location = new PointF(playerStatus.loadLocation().x,
                                        playerStatus.loadLocation().y);
                                levelManager.player.setPointer3DX(location.x);
                                levelManager.player.setPointer3DY(location.y);
                                levelManager.player.setSpeedX(0);
                                break;

                            case 'f':
                                soundManager.playingSound("player_burn");
                                playerStatus.loseLife();
                                location = new PointF(playerStatus.loadLocation().x,
                                        playerStatus.loadLocation().y);
                                levelManager.player.setPointer3DX(location.x);
                                levelManager.player.setPointer3DY(location.y);
                                levelManager.player.setSpeedX(0);
                                break;

                            case 't':
                                if(playerStatus.getCoins()>= 5) {
                                    Teleport teleport = (Teleport) gameObject;
                                    LocationSave t = teleport.getTarget();
                                    loadinglevel(t.getLevel());
                                    soundManager.playingSound("teleport");
                                }
                                    break;


                            default:
                                if (hit == 1) {
                                    levelManager.player.setSpeedX(0);
                                    levelManager.player.setPressingRight(false);
                                    levelManager.player.setPressingLeft(false);
                                }
                                if (hit == 2) {
                                    levelManager.player.isFalling = false;
                                }
                                break;

                        }
                    }
                    for (int i = 0; i < levelManager.player.gun.getNumBullets(); i++) {
                        RectHitBox r = new RectHitBox();
                        r.setLeft(levelManager.player.gun.getBulletX(i));
                        r.setTop(levelManager.player.gun.getBulletY(i));
                        r.setRight(levelManager.player.gun.getBulletX(i) + .1f);
                        r.setBottom(levelManager.player.gun.getBulletY(i) + .1f);
                        if (gameObject.getHitbox().intersection(r)) {
                            levelManager.player.gun.hideBullet(i);
                            if (gameObject.getType() != 'g' && gameObject.getType() != 'd') {
                                soundManager.playingSound("ricochet");
                            } else if (gameObject.getType() == 'g') {
                                destroyer.destroyGameObject(gameObject);
                                soundManager.playingSound("hit_guard");
                            } else if (gameObject.getType() == 'd') {
                                soundManager.playingSound("explode");
                                destroyer.destroyGameObject(gameObject);
                            }


                        }

                    }

                    if (levelManager.isPlaying()) {
                        gameObject.update(framesPerSecond, levelManager.gravity);
                        if (gameObject.getType() == 'd') {
                            Drone d = (Drone) gameObject;
                            d.setWaypoint(levelManager.player.getPointer3D());
                        }
                    }
                }
                else {
                    gameObject.setVisible(false);
                }
            }

            if (levelManager.isPlaying()) {
                rectView.setWorldCentre(levelManager.gameObjects.get(levelManager.playerIndex)
                                .getPointer3D().x,
                        levelManager.gameObjects.get(levelManager.playerIndex)
                                .getPointer3D().y);
                if (levelManager.player.getPointer3D().x < 0 ||
                        levelManager.player.getPointer3D().x > levelManager.mapWidth ||
                        levelManager.player.getPointer3D().y > levelManager.mapHeight) {
                    soundManager.playingSound("player_burn");
                    playerStatus.loseLife();
                    PointF location = new PointF(playerStatus.loadLocation().x,
                            playerStatus.loadLocation().y);
                    levelManager.player.setPointer3DX(location.x);
                    levelManager.player.setPointer3DY(location.y);
                    levelManager.player.setSpeedX(0);
                }
                if (playerStatus.getLives() == 0) {
                    destroyer.destroyPS(this);
                    destroyer.destroyPlayer(this);
                    try {
                        TopResults.setNewResult(playerStatus.getCoins());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }
    @Override
    public  boolean onTouchEvent(MotionEvent motionEvent){
        if (levelManager != null) {
            inputControls.handleInput(motionEvent, levelManager, soundManager, rectView);
        }
        return true;
    }

    public void draw(){
        if(surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();
            paint.setColor(Color.argb(255, 0, 0, 255));
            canvas.drawColor(Color.argb(255, 0, 0, 255));

            drawBackground(0, -3);
            Rect toScreen2d = new Rect();
            for (int layer = -1; layer <= 1; layer++) {
                for (GameObject gameObject : levelManager.gameObjects) {
                    if (gameObject.isVisible() && gameObject.getPointer3D().z == layer) {
                        toScreen2d.set(rectView.rectToScreen
                                (gameObject.getPointer3D().x,
                                        gameObject.getPointer3D().y,
                                        gameObject.getWidth(),
                                        gameObject.getHeight()));
                        if (gameObject.isAnimated()) {
                            if (gameObject.getFacing() == 1) {
                                Matrix flipper = new Matrix();
                                flipper.preScale(-1, 1);
                                Rect r = gameObject.getRectToDraw(System.currentTimeMillis());
                                Bitmap b = Bitmap.createBitmap
                                        (levelManager.bitmapsArray[levelManager.getBitmapIndex(gameObject.getType())],
                                        r.left,
                                        r.top,
                                        r.width(),
                                        r.height(),
                                        flipper,
                                        true);
                                canvas.drawBitmap(b, toScreen2d.left,toScreen2d.top, paint);
                            }
                            else {
                                canvas.drawBitmap
                                        (levelManager.bitmapsArray[levelManager.getBitmapIndex(gameObject.getType())],
                                        gameObject.getRectToDraw(System.currentTimeMillis()),
                                        toScreen2d, paint);
                            }
                        }
                        else {
                            canvas.drawBitmap
                                    (levelManager.bitmapsArray[levelManager.getBitmapIndex(gameObject.getType())],
                                    toScreen2d.left,
                                    toScreen2d.top, paint);
                        }
                    }
                }
                paint.setColor(Color.argb(255, 255, 255, 255));
                for (int i = 0; i < levelManager.player.gun.getNumBullets(); i++) {
                    toScreen2d.set(rectView.rectToScreen
                            (levelManager.player.gun.getBulletX(i),
                                    levelManager.player.gun.getBulletY(i),
                                    .25f,
                                    .05f));
                    canvas.drawRect(toScreen2d, paint);
                }
                drawBackground(4, 0);

                int topSpace = rectView.getPixelsPerMetreY() / 4;
                int iconSize = rectView.getPixelsPerMetreX();
                int padding = rectView.getPixelsPerMetreX() / 5;
                int centring = rectView.getPixelsPerMetreY() / 6;
                paint.setTextSize(rectView.getPixelsPerMetreY()/2);
                paint.setTextAlign(Paint.Align.CENTER);

                paint.setColor(Color.argb(100, 0, 0, 0));

                paint.setColor(Color.argb(255, 255, 255, 0));

                canvas.drawBitmap(lifeHud, 0, topSpace, paint);

                canvas.drawText("" + playerStatus.getLives(), (iconSize * 1) + padding, (iconSize) - centring, paint);

                canvas.drawBitmap(levelManager.getBitmap('c'), (iconSize * 2.5f) + padding, topSpace, paint);

                canvas.drawText("" + playerStatus.getCoins(), (iconSize * 3.5f) + padding * 2, (iconSize) - centring, paint);

                canvas.drawBitmap(levelManager.getBitmap('u'), (iconSize * 5.0f) + padding, topSpace, paint);

                canvas.drawText("" + playerStatus.getGunClips(), (iconSize * 6.0f) + padding * 2, (iconSize) - centring, paint);
                paint.setTextSize(16);
                paint.setTextAlign(Paint.Align.LEFT);
                paint.setColor(Color.argb(255, 255, 255, 255));
                if (debugging) {
                    paint.setTextSize(16);
                    paint.setTextAlign(Paint.Align.LEFT);
                    paint.setColor(Color.argb(255, 255, 255, 255));
                    canvas.drawText("framesPerSecond:" + framesPerSecond, 10, 60, paint);
                    canvas.drawText("num objects:" + levelManager.gameObjects.size(), 10, 80, paint);
                    canvas.drawText("num clipped:" + rectView.getNumClipped(), 10, 100, paint);
                    canvas.drawText("playerX:" + levelManager.gameObjects.get(levelManager.playerIndex).
                            getPointer3D().x, 10, 120, paint);
                    canvas.drawText("playerY:" + levelManager.gameObjects.get(levelManager.playerIndex).
                            getPointer3D().y, 10, 140, paint);
                    canvas.drawText("IsFalling:" + player.isFalling(),10,280,paint);
                    canvas.drawText("IsJumping:" + player.isJumping(),10,260,paint);
                    canvas.drawText("Right" + player.isPressingRight,10,230,paint);
                    canvas.drawText("Left" + player.isPressingLeft,10,240,paint);
                    canvas.drawText("Gravity:" + levelManager.gravity, 10, 160, paint);
                    canvas.drawText("X velocity:" + levelManager.gameObjects.get(levelManager.playerIndex).
                            getSpeedX(), 10, 180, paint);
                    canvas.drawText("Y velocity:" + levelManager.gameObjects.get(levelManager.playerIndex).
                            getSpeedY(), 10, 200, paint);
                    rectView.resetNumClipped();

                }
            }
            paint.setColor(Color.argb(80, 255, 255, 255));

            ArrayList<Rect> buttonsToDraw;
            buttonsToDraw = inputControls.getButtons();

            ArrayList<ButtonText> buttonsTextToDraw;
            buttonsTextToDraw = inputControls.getTextToDraw();

            for (Rect rect : buttonsToDraw) {
                RectF rf = new RectF(rect.left, rect.top, rect.right, rect.bottom);
                canvas.drawRoundRect(rf, 15f, 15f, paint);
            }
            for(ButtonText buttonText:buttonsTextToDraw){
                paint.setColor(Color.argb(255, 255, 255, 255));
                paint.setTextSize(30);
                if(screenX == 1920 && screenY == 1080){
                    paint.setTextSize(40);
                }
                canvas.drawText(buttonText.getText(),buttonText.getX(),buttonText.getY(), paint);
            }
            if (!this.levelManager.isPlaying()) {
                paint.setColor(Color.argb(80, 255, 255, 255));
                RectF rf = new RectF(inputControls.menu.left, inputControls.menu.top, inputControls.menu.right, inputControls.menu.bottom);
                canvas.drawRoundRect(rf, 15f, 15f, paint);
                paint.setTextAlign(Paint.Align.CENTER);
                paint.setColor(Color.argb(255, 255, 255, 255));
                paint.setTextSize(120);
                canvas.drawText("Пауза", rectView.getScreenWidth() / 2,
                        rectView.getScreenHeight() / 2, paint);
                paint.setColor(Color.argb(255, 255, 255, 255));
                paint.setTextSize(50);
                canvas.drawText("ГоловнеМеню", rectView.getScreenWidth() / 2, (screenY/7)/1.5f , paint);


            }
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void drawBackground(int first, int last) {
        Rect startRect = new Rect();
        Rect endRect = new Rect();
        Rect startRect2 = new Rect();
        Rect endRect2 = new Rect();
        for (Background background : levelManager.backgrounds) {
            if (background.z < first && background.z > last) {
                if (!rectView.clipObjects(-1, background.startY, background.width, background.height)) {
                    float floatstartY = ((rectView.getScreenCentreY() -
                            ((rectView.getRectViewWorldCentreY() - background.startY) *
                                    rectView.getPixelsPerMetreY())));
                    int startY =   (int) floatstartY;
                    float floatendY = ((rectView.getScreenCentreY() -
                            ((rectView.getRectViewWorldCentreY() - background.endY) *
                                    rectView.getPixelsPerMetreY())));
                    int endY = (int) floatendY;

                    startRect = new Rect(0, 0, background.width - background.xClip,
                            background.height);
                    endRect = new Rect(background.xClip, startY, background.width, endY);
                    startRect2 = new Rect(background.width - background.xClip, 0,
                            background.width, background.height);
                    endRect2 = new Rect(0, startY, background.xClip, endY);
                }

                if (!background.firstReversed) {
                    canvas.drawBitmap(background.bitmap,
                            startRect, endRect, paint);
                    canvas.drawBitmap(background.bitmapReversed,
                            startRect2, endRect2, paint);
                } else {
                    canvas.drawBitmap(background.bitmap,
                            startRect2, endRect2, paint);
                    canvas.drawBitmap(background.bitmapReversed,
                            startRect, endRect, paint);
                }

                background.xClip -= levelManager.player.getSpeedX() / (18 / background.speed);
                if (background.xClip >= background.width) {
                    background.xClip = 0;
                    background.firstReversed = !background.firstReversed;
                }
                else if (background.xClip <= 0) {
                    background.xClip = background.width;
                    background.firstReversed = !background.firstReversed;
                }
            }
        }
    }

    public void resume(){
        activeGame = true;
        gameThread = new Thread(this);
        gameThread.start();
    }
    public void pause(){
        activeGame = false;
        try{
            gameThread.join();
        }
        catch (InterruptedException e){
            Log.e("ERROR","Failed boys");
        }
    }


}
