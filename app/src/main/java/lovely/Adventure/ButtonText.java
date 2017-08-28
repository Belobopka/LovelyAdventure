package lovely.Adventure;


public class ButtonText {
    int x;
    int y;
    String text;
    ButtonText(String text,int x,int y){
        this.text = text;
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getText() {
        return text;
    }
}
