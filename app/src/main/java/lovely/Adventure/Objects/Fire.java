package lovely.Adventure.Objects;

import android.content.Context;

import lovely.Adventure.GameObject;

public class Fire extends GameObject {

    public Fire(Context context, float worldStartX, float worldStartY, char type, int pixelsPerMetre) {

        final int animFramesPerSecond = 3;
        final int animFrameCount = 3;
        final String bitmapName = "fire";

        final float height = 1;
        final float width = 1;

        setHeight(height);
        setWidth(width);

        setType(type);

        setMoves(false);
        setActive(true);
        setVisible(true);


        setBitmapName(bitmapName);

        setAnimFramesPerSecond(animFramesPerSecond);
        setAnimFrameCount(animFrameCount);
        setBitmapName(bitmapName);
        setAnimated(context, pixelsPerMetre, true);


        setWorldLocation(worldStartX, worldStartY, 0);
        setRectHitbox();
    }

    public void update(long framesPerSecond, float gravity) {
    }
}