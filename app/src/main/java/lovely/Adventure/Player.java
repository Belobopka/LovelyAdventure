package lovely.Adventure;

import android.content.Context;

import lovely.Adventure.Objects.Hat;
import lovely.Adventure.Weapons.Gun;

public class Player extends GameObject {

    RectHitBox rectHitBoxFeet;
    RectHitBox rectHitBoxHead;
    RectHitBox rectHitBoxLeft;
    RectHitBox rectHitBoxRight;
    public Gun gun;
    public Hat hat;
    final float maxXspeed = 10;
    boolean isPressingRight = false;
    boolean isPressingLeft = false;
    public boolean isFalling;
    private boolean isJumping;
    private long jumpTime;
    private long jumpTimeMax = 700;
    float minXSpeed = 0;
    Player(Context context, float startX, float startY, int pixelsPerMetre) {
        final float height = 2;
        final float width = 1;
        gun = new Gun();
        setHeight(height);
        setWidth(width);
        setSpeedX(0);
        setSpeedY(0);
        setFacing(left);
        isFalling = false;
        hat = new Hat(context,startX,startY,pixelsPerMetre,"sombre");
        setMoves(true);
        setActive(true);
        setVisible(true);
        setType('p');


        setBitmapName("playersambuka");

        final int animFramesPerSecond = 16;
        final int animFrameCount = 5;


        setAnimFramesPerSecond(animFramesPerSecond);
        setAnimFrameCount(animFrameCount);
        setAnimated(context, pixelsPerMetre, true);


        setWorldLocation(startX, startY, 0);

        rectHitBoxFeet = new RectHitBox();
        rectHitBoxHead = new RectHitBox();
        rectHitBoxLeft = new RectHitBox();
        rectHitBoxRight = new RectHitBox();

    }

    public void update(long framesPerSecond, float gravity) {
        if (isPressingRight) {
            if(getSpeedX() <= 10){
                minXSpeed += (20.0/(float)framesPerSecond);
                this.setSpeedX(minXSpeed);
            }
        }
        else if (isPressingLeft) {
            if(getSpeedX() >= -10){
                minXSpeed -= (20.0/(float)framesPerSecond);
                this.setSpeedX(minXSpeed);
            }
        }
        else{
            minXSpeed = 0;
            this.setSpeedX(0);
        }



        if (this.getSpeedX() > 0) {

            setFacing(right);
        } else if (this.getSpeedX() < 0) {
            setFacing(left);
        }



        if (isJumping) {
            long timeJumping = System.currentTimeMillis() - jumpTime;
            if (timeJumping < jumpTimeMax) {
                if (timeJumping < jumpTimeMax / 2) {
                    this.setSpeedY(-gravity);
                }
                else if (timeJumping > jumpTimeMax / 2) {
                    this.setSpeedY(gravity);
                }
            }
            else {
                isJumping = false;
            }

        }
        else {
            this.setSpeedY(gravity);

            isFalling = true;
        }

        gun.update(framesPerSecond, gravity);
        hat.update(this.getPointer3D());
        this.move(framesPerSecond);
        Pointer3D location = getPointer3D();
        float lx = location.x;
        float ly = location.y;

        rectHitBoxFeet.top = ly + (getHeight() * .95f);
        rectHitBoxFeet.left = lx + getWidth() * .2f;
        rectHitBoxFeet.bottom = ly + getHeight() * .98f;
        rectHitBoxFeet.right = lx + getWidth() * .8f;

        rectHitBoxHead.top = ly;
        rectHitBoxHead.left = lx + (getWidth() * .4f);
        rectHitBoxHead.bottom = ly + getHeight() * .2f;
        rectHitBoxHead.right = lx + (getWidth() * .6f);

        rectHitBoxLeft.top = ly + getHeight() * .2f;
        rectHitBoxLeft.left = lx + getWidth() * .2f;
        rectHitBoxLeft.bottom = ly + getHeight() * .8f;
        rectHitBoxLeft.right = lx + (getWidth() * .3f);

        rectHitBoxRight.top = ly + getHeight() * .2f;
        rectHitBoxRight.left = lx + (getWidth() * .8f);
        rectHitBoxRight.bottom = ly + getHeight() * .8f;
        rectHitBoxRight.right = lx + getWidth() * .7f;
    }

    public int checkCollisions(RectHitBox rectHitBox) {
        int collided = 0;

        if (this.rectHitBoxLeft.intersection(rectHitBox)) {
            this.setPointer3DX(rectHitBox.right - getWidth() * .2f);

            collided = 1;
        }

        if (this.rectHitBoxRight.intersection(rectHitBox)) {

            this.setPointer3DX(rectHitBox.left - getWidth() * .8f);

            collided = 1;
        }


        if (this.rectHitBoxFeet.intersection(rectHitBox)) {

            this.setPointer3DY(rectHitBox.top - getHeight());
            collided = 2;

        }


        if (this.rectHitBoxHead.intersection(rectHitBox)) {

            this.setPointer3DY(rectHitBox.bottom);
            collided = 3;
        }

        return collided;

    }

    public void setPressingRight(boolean isPressingRight) {
        this.isPressingRight = isPressingRight;
    }

    public void setPressingLeft(boolean isPressingLeft) {
        this.isPressingLeft = isPressingLeft;
    }

    public void startJump(SoundManager soundManager) {
        if (!isFalling) {
            if (!isJumping) {
                isJumping = true;
                jumpTime = System.currentTimeMillis();
                soundManager.playingSound("jump");
            }


        }
    }
    public boolean shoot() {

        return gun.shoot(this.getPointer3D().x, this.getPointer3D().y,
                getFacing(), getHeight(), getWidth());
    }
    public void restorePreviousSpeed() {
        if (!isJumping && !isFalling) {
            if (getFacing() == left) {
                isPressingLeft = true;
                setSpeedX(-maxXspeed);
            } else {
                isPressingRight = true;
                setSpeedX(maxXspeed);
            }
        }
    }

    public boolean isFalling(){
        return isFalling;
    }

    public boolean isJumping(){
        return isJumping;
    }
}