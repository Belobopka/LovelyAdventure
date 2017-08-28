package lovely.Adventure;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import lovely.game2.R;

public class MainMenuActivity extends Activity  {
    private boolean continueMusic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList<TextView> textViewsArray = TopResults.createAndSetTextViewsArray(this);
        final Button buttonplay = (Button) findViewById(R.id.button);
        final Button buttonhelp = (Button) findViewById(R.id.button3);
        final Button buttonexit = (Button) findViewById(R.id.button2);

        MusicManager.setMusicMainMenu(MusicManager.setMusicArrayList("mm",this));
        MusicManager.start(this,MusicManager.MUSIC_MENU);



        buttonplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), PlatformActivity.class);
                startActivity(i);
                finish();
            }
        });
        buttonhelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),HelpMenuActivity.class);
                startActivity(i);
                finish();
            }
        });
        buttonexit.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view) {
                MusicManager.release();
                finish();
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!continueMusic) {
            MusicManager.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        continueMusic = false;
        MusicManager.start(this, MusicManager.MUSIC_MENU);
    }
    }

