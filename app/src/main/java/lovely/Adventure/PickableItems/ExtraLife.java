package lovely.Adventure.PickableItems;

import android.content.Context;

import lovely.Adventure.GameObject;


public class ExtraLife extends GameObject {
   public ExtraLife(Context context,float worldStartX, float worldStartY, char type, int pixelsPerMetre) {
       final int animFramesPerSecond = 8;
       final int animFrameCount = 4;

       final String bitmapName = "life";

       final float height = 1;
       final float width = 1;

       setHeight(height);
       setWidth(width);

       setType(type);

       setMoves(false);

       setAnimFramesPerSecond(animFramesPerSecond);
       setAnimFrameCount(animFrameCount);
       setBitmapName(bitmapName);
       setAnimated(context, pixelsPerMetre, true);

       setWorldLocation(worldStartX, worldStartY, 0);
       setRectHitbox();
    }
    @Override
    public void update(long framesPerSecond, float gravity) {

    }
}
