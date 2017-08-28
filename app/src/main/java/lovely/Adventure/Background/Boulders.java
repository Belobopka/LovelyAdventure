package lovely.Adventure.Background;

import java.util.Random;

import lovely.Adventure.GameObject;

public class Boulders extends GameObject {
    public Boulders(float worldStartX, float worldStartY, char type) {
        final float HEIGHT = 1;
        final float WIDTH = 3;
        setHeight(HEIGHT);
        setWidth(WIDTH);
        setType(type);

        setBitmapName("boulder");
        setActive(false);
        Random rand = new Random();
        if(rand.nextInt(2)==0) {
            setWorldLocation(worldStartX, worldStartY, -1);
        }else{
            setWorldLocation(worldStartX, worldStartY, 1);//
        }
    }
    public void update(long framesPerSecond, float gravity) {
    }
}
