package lovely.Adventure.Weapons;

import java.util.concurrent.CopyOnWriteArrayList;

import lovely.Adventure.GameObject;

public class Gun extends GameObject {
    private int maxBullets = 10;
    private int numBullets;
    private int nextBullet;
    private int rateOfFire = 1;
    private long lastShotTime;

    private CopyOnWriteArrayList<Bullet> bullets;

    int bulletSpeed = 25;

    public Gun(){
        bullets = new CopyOnWriteArrayList<Bullet>();
        lastShotTime = -1;
        nextBullet = -1;
    }

    @Override
    public void update(long framesPerSecond, float gravity) {
        for(Bullet bullet: bullets){
            bullet.update(framesPerSecond);
        }
    }
    public void setFireRate(int fireRate){
        rateOfFire = fireRate;
    }

    public int getNumBullets(){
        return numBullets;
    }

    public float getBulletX(int bulletIndex){
        if(bullets != null && bulletIndex < numBullets) {
            return bullets.get(bulletIndex).getX();
        }
        return -1f;
    }
    public float getBulletY(int bulletIndex){
        if(bullets != null) {
            return bullets.get(bulletIndex).getY();
        }
        return -1f;
    }

    public void hideBullet(int bulletIndex){
        bullets.get(bulletIndex).hideBullet();
    }

    public boolean shoot(float ownerX, float ownerY, int ownerFacing, float ownerHeight,float ownerWidth){
        boolean shotFired = false;
        if(System.currentTimeMillis() - lastShotTime  > 1000/rateOfFire){
            nextBullet ++;
            if(numBullets >= maxBullets){
                numBullets = maxBullets;
            }
            if(nextBullet == maxBullets){
                nextBullet = 0;
            }
            lastShotTime = System.currentTimeMillis();
            if(ownerFacing == 1){
                bullets.add(nextBullet, new Bullet(ownerX + ownerWidth , (ownerY+ ownerHeight/3),
                        bulletSpeed, ownerFacing));
            }
            else{
                bullets.add(nextBullet, new Bullet(ownerX, (ownerY+ ownerHeight/3),
                        bulletSpeed, ownerFacing));
            }
            shotFired = true;
            numBullets++;
        }
        return shotFired;
    }

    public void upgradeRateOfFire(){
        rateOfFire +=2;
    }
}
