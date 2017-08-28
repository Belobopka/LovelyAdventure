package lovely.Adventure.Tiles;

import lovely.Adventure.GameObject;

public class Tile extends GameObject {

    public Tile(float worldStartX, float worldStartY, char type) {
        final float HEIGHT = 1;
        final float WIDTH = 1;

        setHeight(HEIGHT);
        setWidth(WIDTH);

        setType(type);

        setBitmapName("turf"); // Выбор любой поверхности.

        setWorldLocation(worldStartX, worldStartY, 0);

        setRectHitbox();

        setTraversable();
    }

    public void update(long framesPerSecond, float gravity) {
    }
}
