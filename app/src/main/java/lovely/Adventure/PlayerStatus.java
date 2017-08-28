package lovely.Adventure;


import android.graphics.PointF;

public class PlayerStatus {

    private int coins;
    private int gunClips;
    private int lives;
    private float restartX;
    private float restartY;

    PlayerStatus() {
        lives = 3;
        gunClips = 1;
        coins = 0;
    }

    public void saveLocation(PointF location) {

        restartX = location.x;
        restartY = location.y;
    }

    public PointF loadLocation() {
        return new PointF(restartX, restartY);
    }

    public int getLives(){
        return lives;
    }

    public int getGunClips(){
        return gunClips;
    }

    public void increaseGunClips(){
        gunClips += 2;
    }

    public void gotCoin(){
        coins++;
    }

    public int getCoins(){
        return coins;
    }

    public void loseLife(){
        lives--;
    }

    public void addLife(){
        lives++;
    }



}