package lovely.Adventure;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public abstract class GameObject {

    private Animation anim = null;
    private boolean isAnimated;
    private int animFramesPerSecond = 1;
    public boolean traversable = false;
    private RectHitBox rectHitBox = new RectHitBox();
    private Pointer3D pointer3D;
    private float width;
    private float height;
    private boolean active = true;
    private boolean visible = true;
    private int animFrameCount = 1;
    private char type;
    private String bitmapName;
    private float speedX;
    private float speedY;
    public final int left = -1;
    public final int right = 1;
    private int facing;
    private boolean moves = false;

    public void setAnimFramesPerSecond(int animFramesPerSecond) {
        this.animFramesPerSecond = animFramesPerSecond;
    }

    public void setAnimFrameCount(int animFrameCount) {

        this.animFrameCount = animFrameCount;
    }

    public boolean isAnimated() {

        return isAnimated;
    }

    public void setAnimated(Context context, int pixelsPerMetre, boolean animated){
        this.isAnimated = animated;
        this.anim = new Animation(context, bitmapName,
                height,
                width,
                animFramesPerSecond,
                animFrameCount,
                pixelsPerMetre );
    }

    public Rect getRectToDraw(long startTime){

        return anim.getCurrentFrame(startTime, speedX, isMoves());
    }

    public abstract void update(long framesPerSecond, float gravity);


    public Bitmap prepareBitmap(Context context, String bitmapName, int pixelsPerMetre) {
        int resID = context.getResources().getIdentifier(bitmapName,
                "drawable", context.getPackageName());

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
                resID);

        bitmap = Bitmap.createScaledBitmap(bitmap,
                (int) (width * animFrameCount * pixelsPerMetre),
                (int) (height * pixelsPerMetre),
                false);

        return bitmap;
    }


    public void setWorldLocation(float x, float y, int z) {
        this.pointer3D = new Pointer3D();
        this.pointer3D.x = x;
        this.pointer3D.y = y;
        this.pointer3D.z = z;
    }
    void move(long framesPerSecond){
        if(speedX != 0) {
            this.pointer3D.x += speedX / framesPerSecond;
        }

        if(speedY != 0) {
            this.pointer3D.y += speedY / framesPerSecond;
        }
    }

    public float getSpeedX() {
        return speedX;
    }

    public void setSpeedX(float speedX) {
        if(moves) {
            this.speedX = speedX;
        }
    }

    public float getSpeedY() {
        return speedY;
    }

    public void setSpeedY(float speedY) {
        if(moves) {
            this.speedY = speedY;
        }
    }
    public void setRectHitbox() {
        rectHitBox.setTop(pointer3D.y);
        rectHitBox.setLeft(pointer3D.x);
        rectHitBox.setBottom(pointer3D.y + height);
        rectHitBox.setRight(pointer3D.x + width);
    }

    public void setTraversable(){
        traversable = true;
    }

    public boolean isMoves() {
        return moves;
    }

    public Pointer3D getPointer3D() {
        return pointer3D;
    }

    RectHitBox getHitbox(){
        return rectHitBox;
    }

    public int getFacing() {
        return facing;
    }

    public void setFacing(int facing) {
        this.facing = facing;
    }

    public void setPointer3DY(float y) {
        this.pointer3D.y = y;
    }

    public void setPointer3DX(float x) {this.pointer3D.x = x;
    }
    public void setMoves(boolean moves) {
        this.moves = moves;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setBitmapName(String bitmapName){
        this.bitmapName = bitmapName;
    }

    public String getBitmapName() {
        return bitmapName;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public boolean isActive() { return active; }

    public boolean isVisible() {return visible;}

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }

    public boolean isTraversable(){
        return traversable;
    }
}
