package lovely.Adventure;

import android.graphics.PointF;
public class Drone extends GameObject {

    long lastWaypointSetTime;
    PointF currentWaypoint;

    final float maxXSpeed = 3;
    final float maxYSpeed = 3;


    Drone(float worldStartX, float worldStartY, char type) {
        final float height = 1;
        final float width = 1;
        setHeight(height);
        setWidth(width);

        setType(type);

        setBitmapName("drone");

        setMoves(true);
        setActive(true);
        setVisible(true);

        currentWaypoint = new PointF();

        setWorldLocation(worldStartX, worldStartY, 0);
        setRectHitbox();
        setFacing(right);
    }


    public void update(long framesPerSecond, float gravity) {
        if (currentWaypoint.x > getPointer3D().x) {
            setSpeedX(maxXSpeed);
        } else if (currentWaypoint.x < getPointer3D().x) {
            setSpeedX(-maxXSpeed);
        } else {
            setSpeedX(0);
        }


        if (currentWaypoint.y >= getPointer3D().y) {
            setSpeedY(maxYSpeed);
        } else if (currentWaypoint.y < getPointer3D().y) {
            setSpeedY(-maxYSpeed);
        } else {
            setSpeedY(0);
        }


        setRectHitbox();


        move(framesPerSecond);


    }

    public void setWaypoint(Pointer3D playerLocation) {
        if (System.currentTimeMillis() > lastWaypointSetTime + 2000) {//Has 2 seconds passed
            lastWaypointSetTime = System.currentTimeMillis();
            currentWaypoint.x = playerLocation.x;
            currentWaypoint.y = playerLocation.y;
        }

    }
}
