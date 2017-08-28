package lovely.Adventure.PickableItems;

import lovely.Adventure.GameObject;


public class Coin extends GameObject {
    public Coin(float worldStartX, float worldStartY, char type) {

        final float HEIGHT = 0.75f;
        final float WIDTH = 0.75f;

        setHeight(HEIGHT);
        setWidth(WIDTH);

        setType(type);



        setBitmapName("coin");




        setWorldLocation(worldStartX, worldStartY, 0);
        setRectHitbox();
    }
    @Override
    public void update(long framesPerSecond, float gravity) {

    }
}
