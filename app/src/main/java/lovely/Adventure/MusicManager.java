package lovely.Adventure;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

import lovely.game2.R;

public class MusicManager {
    private static ArrayList<MediaPlayer> musicMainMenuArray;
    private static final String TAG = "lovely.Adventure.MusicManager";
    public static final int MUSIC_PREVIOUS = -1;
    public static final int MUSIC_MENU = 0;
    public static final int MUSIC_GAME = 1;
    public static final int MUSIC_END_GAME = 2;
    private static HashMap players = new HashMap();
    static int check = 0;
    private static int currentMusic = -1;
    private static int previousMusic = -1;

    public static void setMusicMainMenu(ArrayList<MediaPlayer> musicMainMenu) {
        if ((musicMainMenuArray == null)) {
            musicMainMenuArray = musicMainMenu;
            for (int i = 0; i < musicMainMenuArray.size(); i++) {
                if (i == (musicMainMenuArray.size()-1)) {
                    System.out.println("Listener_ " + musicMainMenuArray.get(i));
                    musicMainMenuArray.get(i).setOnCompletionListener(
                            new Listener(musicMainMenuArray.get(0)));
                }
                else {
                    System.out.println("Listener_ " + musicMainMenuArray.get(i));
                    musicMainMenuArray.get(i).setOnCompletionListener(
                            new Listener(musicMainMenuArray.get(i + 1)));
                }
            }
        }
    }

    public static ArrayList<MediaPlayer> setMusicArrayList(String assetFolderName , Context context){
        ArrayList<MediaPlayer> mpArray = new ArrayList<>();
        try {
            String[] str = context.getAssets().list(assetFolderName);
            for(int i = 0; i< str.length;i++){
                AssetFileDescriptor afd = context.getAssets().openFd( assetFolderName + "/" + str[i]);
                MediaPlayer mp = new MediaPlayer();
                mp.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
                mp.prepare();
                mpArray.add(mp);
            }
        } catch (IOException e) {
            System.out.println("HELLO ");
            e.printStackTrace();
        }

        return mpArray;
    }
    public static void start(Context context, int music) {
        start(context, music, false);
    }

    public static void start(Context context, int music, boolean force) {
        if (!force && currentMusic > -1) {
            return;
        }
        if (music == MUSIC_PREVIOUS) {
            music = previousMusic;
        }
        if (currentMusic == music) {
            return;
        }
        if (currentMusic != -1) {
            previousMusic = currentMusic;
            pause();
        }
        currentMusic = music;
        MediaPlayer mp = (MediaPlayer) players.get(music);
        if (mp != null) {
            if (!mp.isPlaying()) {
                mp.start();
            }
        } else {
            if (music == MUSIC_MENU) {
                mp = musicMainMenuArray.get(0);
            } else if (music == MUSIC_GAME) {
                mp = MediaPlayer.create(context, R.raw.krk);
            } else if (music == MUSIC_END_GAME) {
                mp = MediaPlayer.create(context, R.raw.oraora);
            } else {
                return;
            }
            players.put(music, mp);
            if (mp == null) {}
            else {
                try {
                    mp.start();
                } catch (Exception e) {

                }
            }
        }
    }
    public static void pause() {
        Collection<MediaPlayer> mps = players.values();
        for (MediaPlayer p : mps) {
            if (p.isPlaying()) {
                p.pause();
            }
        }
        if (currentMusic != -1) {
            previousMusic = currentMusic;

        }
        currentMusic = -1;
    }

    public static void release() {
        Collection<MediaPlayer> mps = players.values();
        for (MediaPlayer mp : mps) {
            try {
                if (mp != null) {
                    if (mp.isPlaying()) {
                        mp.stop();
                    }
                    mp.release();
                }
            } catch (Exception e) {}
        }
        mps.clear();
        if (currentMusic != -1) {
            previousMusic = currentMusic;
        }
        currentMusic = -1;
    }


}
class Listener implements MediaPlayer.OnCompletionListener  {
    MediaPlayer nextMP;
    Listener(MediaPlayer nextMP){
        this.nextMP = nextMP;
    }
    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        System.out.println("Listener_OnCompletion_ " + nextMP);
        mediaPlayer.pause();
        nextMP.start();


    }
}