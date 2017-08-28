package lovely.Adventure;

import android.graphics.Rect;

public class RectView {

    private Pointer3D pointer3DCameraCenterView;
    private Rect rectScreen;
    private int pixelsPerMetreX;
    private int pixelsPerMetreY;
    private int screenXResolution;
    private int screenYResolution;
    private int screenCentreX;
    private int screenCentreY;
    private int metresToShowX;
    private int metresToShowY;
    private int numClipped;

    RectView(int x, int y){

        screenXResolution = x;
        screenYResolution = y;

        screenCentreX = screenXResolution / 2;
        screenCentreY = screenYResolution / 2;

        pixelsPerMetreX = screenXResolution / 20;
        pixelsPerMetreY = screenYResolution / 11;
        metresToShowX = 27;
        metresToShowY = 15;

        rectScreen = new Rect();
        pointer3DCameraCenterView = new Pointer3D();
    }

    void setWorldCentre(float x, float y){
        pointer3DCameraCenterView.x  = x;
        pointer3DCameraCenterView.y  = y;
    }

    public Rect rectToScreen(float objX, float objY, float objWidth, float objHeight){
        int left = (int) (screenCentreX - ((pointer3DCameraCenterView.x - objX) * pixelsPerMetreX));
        int top =  (int) (screenCentreY - ((pointer3DCameraCenterView.y - objY) * pixelsPerMetreY));
        int right = (int) (left + (objWidth * pixelsPerMetreX));
        int bottom = (int) (top + (objHeight * pixelsPerMetreY));
        rectScreen.set(left, top, right, bottom);
        return rectScreen;
    }

    public boolean clipObjects(float objX, float objY, float objWidth, float objHeight) {
        boolean clipped = true;

        if (objX - objWidth < pointer3DCameraCenterView.x + (metresToShowX / 2)) {
            if (objX + objWidth> pointer3DCameraCenterView.x - (metresToShowX / 2)) {
                if (objY - objHeight< pointer3DCameraCenterView.y + (metresToShowY / 2)) {
                    if (objY + objHeight > pointer3DCameraCenterView.y - (metresToShowY / 2)){
                        clipped = false;
                    }

                }
            }
        }
        if(clipped){
            numClipped++;
        }
        return clipped;
    }

    public int getNumClipped(){

        return numClipped;
    }

    public void resetNumClipped(){

        numClipped = 0;
    }

    public int getPixelsPerMetreY(){
        return pixelsPerMetreY;
    }

    public int getScreenCentreY(){
        return screenCentreY;
    }

    public float getRectViewWorldCentreY(){
        return pointer3DCameraCenterView.y;
    }

    public int getScreenWidth(){

        return  screenXResolution;
    }

    public int getScreenHeight(){

        return  screenYResolution;
    }

    public int getPixelsPerMetreX(){

        return  pixelsPerMetreX;
    }

    public void moveRectViewRight(int maxWidth){
        if(pointer3DCameraCenterView.x < maxWidth -
                (metresToShowX/2)+3) {
            pointer3DCameraCenterView.x += 1;
        }
    }

    public void moveRectViewLeft(){
        if(pointer3DCameraCenterView.x > (metresToShowX/2)-3){
            pointer3DCameraCenterView.x -= 1;
        }
    }

    public void moveRectViewUp(){
        if(pointer3DCameraCenterView.y > (metresToShowY /2)-3) {
            pointer3DCameraCenterView.y -= 1;
        }
    }

    public void moveRectViewDown(int maxHeight){
        if(pointer3DCameraCenterView.y <
                maxHeight - (metresToShowY / 2)+3) {
            pointer3DCameraCenterView.y += 1;
        }
    }
}
