package lovely.Adventure.Weapons;


import lovely.Adventure.Destroyer;

public class Bullet {

    public float x;
    private float y;
    public float xSpeed;
    private int direction;

    Bullet(float x, float y, int speed, int direction){
        this.direction = direction;
        this.x = x;
        this.y = y;
        this.xSpeed = speed * this.direction;
    }
    public void update(long framesPerSecond){
        x += xSpeed / framesPerSecond;
    }

    public void hideBullet(){
        Destroyer.destroybullet(this);
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }
}
