package lovely.Adventure.Teleport;

public class LocationSave {
    String level;
    float x;
    float y;


    public LocationSave(String level, float x, float y){
        this.level = level;
        this.x = x;
        this.y = y;
    }

    public String getLevel(){
        return level;
    }
    public float getX(){
        return x;
    }
    public float getY(){
        return y;
    }
}