package lovely.Adventure;


import lovely.Adventure.Weapons.Bullet;

public  class Destroyer {
    public  void destroyGameObject(GameObject gameObject) {
        switch (gameObject.getType()) {
            case 'g':
                gameObject.setWorldLocation(-100, -100, 0);
                break;
            case 'd':
                gameObject.setWorldLocation(-100, -100, 0);
                break;
        }
    }
        public static void destroybullet(Bullet bullet){
            bullet.xSpeed = 0;
            bullet.x = -100;
        }

    public void destroyPlayer(PlatformView platformView){
        platformView.loadinglevel("UndergroundLevel");
    }
    public  void destroyPS(PlatformView platformView){
        platformView.playerStatus = new PlayerStatus();
    }
}
