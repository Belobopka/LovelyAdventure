package lovely.Adventure.Teleport;

import lovely.Adventure.GameObject;

public class Teleport extends GameObject {
    LocationSave target;
    public Teleport(float worldStartX, float worldStartY,
                    char type, LocationSave target) {
        final float HEIGHT = 2;
        final float WIDTH = 2;
        setHeight(HEIGHT);
        setWidth(WIDTH);
        setType(type);
        setBitmapName("teleport");
        this.target = new LocationSave(target.level,
                target.x, target.y);
        setWorldLocation(worldStartX, worldStartY, 0);
        setRectHitbox();
    }
    public LocationSave getTarget(){
        return target;
    }
    public void update(long framesPerSecond, float gravity){
    }
}
