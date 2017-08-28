package lovely.Adventure;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;

public class PlatformActivity extends Activity {
    private PlatformView gameView;
    private boolean continueMusic ;
    @Override
            protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        gameView = new PlatformView(this, size.x, size.y);
        setContentView(gameView);
        MusicManager.start(this, MusicManager.MUSIC_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
        if (!continueMusic) {
            MusicManager.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
        continueMusic = false;
        MusicManager.start(this, MusicManager.MUSIC_GAME);
    }
    public void returnToMain(){
        Intent i = new Intent(getApplicationContext(),MainMenuActivity.class);
        startActivity(i);
        this.finish();
    }
}
