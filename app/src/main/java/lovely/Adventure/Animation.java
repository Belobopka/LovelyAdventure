package lovely.Adventure;

import android.content.Context;
import android.graphics.Rect;

public class Animation {
    String bitmapName;
    private Rect finalRect;
    private int frameCount;
    private int currentFrame;
    private long frameTicker;
    private int framePeriod;
    private int frameWidth;
    private int frameHeight;
    int pixelsPerMetre;

    Animation(Context context, String bitmapName, float frameHeight, float frameWidth,
              int animFramesPerSecond, int frameCount, int pixelsPerMetre){
        this.currentFrame = 0;
        this.frameCount = frameCount;
        this.frameWidth = (int)frameWidth * pixelsPerMetre ;
        this.frameHeight = (int)frameHeight * pixelsPerMetre;
        finalRect = new Rect(0, 0, this.frameWidth, this.frameHeight);
        framePeriod = 1000 / animFramesPerSecond;
        frameTicker = 0L;
        this.bitmapName = "" + bitmapName;
        this.pixelsPerMetre = pixelsPerMetre;
    }

    public Rect getCurrentFrame(long time, float speedX, boolean ismovable){

        if(speedX!=0 || ismovable == false) {
            if (time > frameTicker + framePeriod) {
                frameTicker = time;
                currentFrame++;
                if (currentFrame >= frameCount) {
                    currentFrame = 0;
                }
            }
        }

        this.finalRect.left = currentFrame * frameWidth ;
        this.finalRect.right = this.finalRect.left + frameWidth;

        return finalRect;
    }
}
