package lovely.Adventure.Tiles;

import lovely.Adventure.GameObject;

public class Stone extends GameObject {
    public Stone(float worldStartX, float worldStartY, char type) {
        setTraversable();
        final float HEIGHT = 1;
        final float WIDTH = 1;
        setHeight(HEIGHT);
        setWidth(WIDTH);
        setType(type);
        setBitmapName("stone");
        setWorldLocation(worldStartX, worldStartY, 0);
        setRectHitbox();
    }
    public void update(long framesPerSecond, float gravity) {
    }
}
