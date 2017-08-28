package lovely.Adventure;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import lovely.game2.R;

public class HelpMenuActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help1);
        final Button buttonmainmenu = (Button) findViewById(R.id.button4);
       // MusicManager.start(this,MusicManager.MUSIC_END_GAME);
        buttonmainmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),MainMenuActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}
