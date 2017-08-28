package lovely.Adventure.PickableItems;

import lovely.Adventure.GameObject;

public class GunUpgrade extends GameObject {
   public GunUpgrade(float worldStartX, float worldStartY, char type) {

        final float height = .5f;
        final float width = .5f;

        setHeight(height);
        setWidth(width);

        setType(type);

        setBitmapName("clip");

        setWorldLocation(worldStartX, worldStartY, 0);
        setRectHitbox();
    }
    @Override
    public void update(long framesPerSecond, float gravity) {

    }
}
