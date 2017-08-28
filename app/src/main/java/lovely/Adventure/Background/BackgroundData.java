package lovely.Adventure.Background;

public class BackgroundData {
    String bitmapName;
    int layer;
    float startY;
    float endY;
    float speed;
    int height;
    public BackgroundData(String bitmap, int layer, float startY, float endY,
                          float speed, int height){
        this.bitmapName = bitmap;
        this.layer = layer;
        this.startY = startY;
        this.endY = endY;
        this.speed = speed;
        this.height = height;

    }
}