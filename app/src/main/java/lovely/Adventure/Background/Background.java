package lovely.Adventure.Background;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

public class Background {
    public Bitmap bitmap;
    public Bitmap bitmapReversed;
    public int width;
    public int height;
    public boolean firstReversed;
    public int xClip;
    public float startY;
    public float endY;
    public float speed;
    public int z;

    public Background(Context context, BackgroundData data, int ypixelsPerMetre,int screenWidth){
        int resID = context.getResources().getIdentifier
                (data.bitmapName, "drawable",context.getPackageName());
        bitmap = BitmapFactory.decodeResource(context.getResources(), resID);
        firstReversed = false;
        xClip = 0;
        startY = data.startY;
        endY = data.endY;
        z = data.layer;
        speed = data.speed;
        bitmap = Bitmap.createScaledBitmap(bitmap, screenWidth,
                data.height * ypixelsPerMetre
                , true);
        width = bitmap.getWidth();
        height = bitmap.getHeight();

        Matrix matrix = new Matrix();
        matrix.setScale(-1, 1);
        bitmapReversed = Bitmap.createBitmap(
                bitmap, 0, 0, width, height, matrix, true);
    }
}