package lovely.Adventure.Objects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import lovely.Adventure.GameObject;
import lovely.Adventure.Player;
import lovely.Adventure.Pointer3D;

public class Hat  {
    public float x;
    public float y;
    public  float height = 1;
    public  float width = 1;
    public Bitmap bitmap;

    public Hat(Context context, float worldStartX, float worldStartY, int pixelsPerMetre, String bitmapName){
        this.x = worldStartX;
        this.y = worldStartY;
        this.bitmap = prepareHatBitmap(context,bitmapName,pixelsPerMetre);


    }
    public void update(Pointer3D pointer3D){
        x = pointer3D.x;
        y = pointer3D.y;
    }
    private Bitmap prepareHatBitmap(Context context, String bitmapName, int pixelsPerMetre){
        int resID = context.getResources().getIdentifier(bitmapName,
                "drawable", context.getPackageName());

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
                resID);

        bitmap = Bitmap.createScaledBitmap(bitmap,
                (int) (width  * pixelsPerMetre),
                (int) (height * pixelsPerMetre),
                false);

        return bitmap;
    }
}
