package lovely.Adventure;

import android.content.Context;

public class Guard extends GameObject {



    private float waypointX1;
    private float waypointX2;
    private int currentWaypoint;
    final float maxXSpeed = 3;


    Guard(Context context, float worldStartX, float worldStartY, char type, int pixelsPerMetre) {
        final int animFramesPerSecond = 8;
        final int animFrameCount = 5;
        final String bitmapName = "guard1";
        final float HEIGHT = 2;
        final float WIDTH = 1;

        setHeight(HEIGHT);
        setWidth(WIDTH);

        setType(type);

        setBitmapName(bitmapName);

        setMoves(true);
        setActive(true);
        setVisible(true);

        setAnimFramesPerSecond(animFramesPerSecond);
        setAnimFrameCount(animFrameCount);
        setAnimated(context, pixelsPerMetre, true);

        setWorldLocation(worldStartX, worldStartY, 0);
        setSpeedX(-maxXSpeed);
        currentWaypoint = 1;
        setRectHitbox();
        setFacing(left);


    }

    public void setWaypoints(float waypointX1, float waypointX2){
        this.waypointX1 = waypointX1;
        this.waypointX2 = waypointX2;
    }

    public void update(long framesPerSecond, float gravity) {

        if(currentWaypoint == 1) {
            if (getPointer3D().x <= waypointX1) {

                currentWaypoint = 2;
                setSpeedX(maxXSpeed);
                setFacing(right);
            }
        }

        if(currentWaypoint == 2){
            if (getPointer3D().x >= waypointX2) {

                currentWaypoint = 1;
                setSpeedX(-maxXSpeed);
                setFacing(left);
            }
        }

        setRectHitbox();

        move(framesPerSecond);
    }
}
