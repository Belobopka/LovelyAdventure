package lovely.Adventure;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;

import lovely.Adventure.GameLevels.CityLevel;
import lovely.Adventure.GameLevels.MountainLevel;
import lovely.Adventure.Background.Stalactite;
import lovely.Adventure.Background.Stalagmite;
import lovely.Adventure.Objects.Hat;
import lovely.Adventure.Tiles.Brick;
import lovely.Adventure.Tiles.Grass;
import lovely.Adventure.Background.Background;
import lovely.Adventure.Background.BackgroundData;
import lovely.Adventure.GameLevels.DataLevel;
import lovely.Adventure.Background.Boulders;
import lovely.Adventure.Background.Cart;
import lovely.Adventure.Background.Lampost;
import lovely.Adventure.Background.Tree;
import lovely.Adventure.Background.Tree2;
import lovely.Adventure.Objects.Fire;
import lovely.Adventure.Teleport.Teleport;
import lovely.Adventure.Tiles.Coal;
import lovely.Adventure.Tiles.Concrete;
import lovely.Adventure.PickableItems.Coin;
import lovely.Adventure.PickableItems.ExtraLife;
import lovely.Adventure.PickableItems.GunUpgrade;
import lovely.Adventure.Tiles.Scorched;
import lovely.Adventure.Tiles.Snow;
import lovely.Adventure.Tiles.Stone;

import static lovely.Adventure.GameLevels.UndergroundLevel.newLevelCave;
import static lovely.Adventure.GameLevels.ForestLevel.newLevelForest;

public class LevelManager {
    private String level;
    int mapWidth;
    int mapHeight;

   public Player player;
    int playerIndex;

    private boolean isplaying;
    float gravity;

    Pointer3D pointer3D;

    DataLevel dataLevel;
    ArrayList<GameObject> gameObjects;
    ArrayList<Background> backgrounds;

    Bitmap[] bitmapsArray;

    public LevelManager(Context context, int ypixelsPerMetre, int screenWidth, String level) {
        this.level = level;
        switch (this.level) {
            case "UndergroundLevel":
                dataLevel = newLevelCave();
                break;
            case "CityLevel":
                dataLevel = CityLevel.newLevelCity();
                break;
            case "ForestLevel":
                dataLevel = newLevelForest();
                break;
            case "MountainLevel":
                dataLevel = MountainLevel.newLevelMountain();
                break;
        }

        gameObjects = new ArrayList<>();


        bitmapsArray = new Bitmap[25];

        loadMapData(context, ypixelsPerMetre);

        loadBackgrounds(context, ypixelsPerMetre, screenWidth);

        setWaypoints();

        isplaying = false;
    }

    private void loadBackgrounds(Context context, int ypixelsPerMetre, int screenWidth) {
        backgrounds = new ArrayList<Background>();
        for (BackgroundData bgData : dataLevel.backgroundDataList) {
            backgrounds.add(new Background(context, bgData,ypixelsPerMetre,screenWidth));
        }
    }

    public void setWaypoints() {
        for (GameObject guard : this.gameObjects) {
            if (guard.getType() == 'g') {
                int startTileIndex = -1;
                float waypointX1 = -1;
                float waypointX2 = -1;
                for (GameObject tile : this.gameObjects) {
                    startTileIndex++;
                    if (tile.getPointer3D().y == guard.getPointer3D().y + 2) {
                        if (tile.getPointer3D().x == guard.getPointer3D().x) {
                            for (int i = 0; i < 5; i++) {
                                if (!gameObjects.get(startTileIndex - i).isTraversable()) {
                                    waypointX1 = gameObjects.get(startTileIndex - (i + 1)).getPointer3D().x;
                                    Log.d("set x1 = ", "" + waypointX1);
                                    break;
                                } else {
                                    waypointX1 = gameObjects.get(startTileIndex - 5).getPointer3D().x;
                                }
                            }

                            for (int i = 0; i < 5; i++) {
                                if (!gameObjects.get(startTileIndex + i).isTraversable()) {
                                    waypointX2 = gameObjects.get(startTileIndex + (i - 1)).getPointer3D().x;
                                    break;
                                } else {
                                    waypointX2 = gameObjects.get(startTileIndex + 5).getPointer3D().x;
                                }
                            }
                            Guard g = (Guard) guard;
                            g.setWaypoints(waypointX1, waypointX2);
                        }
                    }
                }
            }
        }
    }


    public boolean isPlaying() {
        return isplaying;
    }

    public Bitmap getBitmap(char mapType) {

        int index;
        switch (mapType) {
            case '.':
                index = 0;
                break;
            case '1':
                index = 1;
                break;
            case '2':
                index = 9;
                break;
            case '3':
                index = 10;
                break;
            case '4':
                index = 11;
                break;
            case '5':
                index = 12;
                break;
            case '6':
                index = 13;
                break;
            case '7':
                index = 14;
                break;
            case 'w':
                index = 15;
                break;
            case 'x':
                index = 16;
                break;
            case 'l':
                index = 17;
                break;
            case 'r':
                index = 18;
                break;
            case 's':
                index = 19;
                break;
            case 'm':
                index = 20;
                break;
            case 'z':
                index = 21;
                break;
            case 't':
                index = 22;
                break;
            case 'p':
                index = 2;
                break;
            case 'c':
                index = 3;
                break;
            case 'u':
                index = 4;
                break;
            case 'e':
                index = 5;
                break;
            case 'd':
                index = 6;
                break;
            case 'g':
                index = 7;
                break;
            case 'f':
                index = 8;
                break;

            default:
                index = 0;
                break;
        }

        return bitmapsArray[index];
    }

    public int getBitmapIndex(char blockType) {

        int index;
        switch (blockType) {
            case '.':
                index = 0;
                break;
            case '1':
                index = 1;
                break;
            case '2':
                index = 9;
                break;
            case '3':
                index = 10;
                break;
            case '4':
                index = 11;
                break;
            case '5':
                index = 12;
                break;
            case '6':
                index = 13;
                break;
            case '7':
                index = 14;
                break;
            case 'w':
                index = 15;
                break;
            case 'x':
                index = 16;
                break;
            case 'l':
                index = 17;
                break;
            case 'r':
                index = 18;
                break;
            case 's':
                index = 19;
                break;
            case 'm':
                index = 20;
                break;
            case 'z':
                index = 21;
                break;
            case 't':
                index = 22;
                break;
            case 'p':
                index = 2;
                break;
            case 'c':
                index = 3;
                break;
            case 'u':
                index = 4;
                break;
            case 'e':
                index = 5;
                break;
            case 'd':
                index = 6;
                break;
            case 'g':
                index = 7;
                break;
            case 'f':
                index = 8;
                break;


            default:
                index = 0;
                break;
        }

        return index;
    }
    public void switchPlaying(){
        isplaying = !isplaying;
        if(isplaying){
            gravity = 6;
        }
        else {
            gravity = 0;
        }
    }

    void loadMapData(Context context, int pixelsPerMetre) {
        char c;
        int teleportIndex = -1;
        int currentIndex = -1;
        mapHeight = dataLevel.tiles.size();
        mapWidth = dataLevel.tiles.get(0).length();
        for (int i = 0; i < dataLevel.tiles.size(); i++) {
            for (int j = 0; j < dataLevel.tiles.get(i).length(); j++) {
                c = dataLevel.tiles.get(i).charAt(j);
                if (c != '.') {
                    currentIndex++;
                    switch (c) {
                        case '1':
                            gameObjects.add(new Grass(j, i, c));
                            break;

                        case '2':
                            gameObjects.add(new Snow(j, i, c));
                            break;

                        case '3':
                            gameObjects.add(new Brick(j, i, c));
                            break;

                        case '4':
                            gameObjects.add(new Coal(j, i, c));
                            break;

                        case '5':
                            gameObjects.add(new Concrete(j, i, c));
                            break;

                        case '6':
                            gameObjects.add(new Scorched(j, i, c));
                            break;

                        case '7':
                            gameObjects.add(new Stone(j, i, c));
                            break;

                        case 'w':
                            gameObjects.add(new Tree(j, i, c));
                            break;
                        case 'x':
                            gameObjects.add(new Tree2(j, i, c));
                            break;

                        case 'l':
                            gameObjects.add(new Lampost(j, i, c));
                            break;

                        case 'r':
                            gameObjects.add(new Stalactite(j, i, c));
                            break;

                        case 's':
                            gameObjects.add(new Stalagmite(j, i, c));
                            break;

                        case 'm':
                            gameObjects.add(new Cart(j, i, c));
                            break;

                        case 'z':
                            gameObjects.add(new Boulders(j, i, c));
                            break;

                        case 'p':
                            gameObjects.add(new Player(context, j, i, pixelsPerMetre));
                            playerIndex = currentIndex;
                            player = (Player) gameObjects.get(playerIndex);
                            pointer3D = new Pointer3D();
                            pointer3D.x = j;
                            pointer3D.y = i;
                            pointer3D.z = c;
                            break;

                        case 'c':
                            gameObjects.add(new Coin(j,i,c));
                            break;

                        case 'u':
                            gameObjects.add(new GunUpgrade(j, i, c));
                            break;

                        case 'e':
                            gameObjects.add(new ExtraLife(context,j, i, c,pixelsPerMetre));
                            break;

                        case 'd':
                            gameObjects.add(new Drone(j,i,c));
                            break;

                        case 'g':
                            gameObjects.add(new Guard(context,j,i,c,pixelsPerMetre));
                            break;

                        case 'f':
                            gameObjects.add(new Fire(context,j,i,c,pixelsPerMetre));
                            break;

                        case 't':
                            teleportIndex++;
                            gameObjects.add(new Teleport(j, i, c,
                                    dataLevel.locationSaves.get(teleportIndex)));
                            break;


                    }
                    if (bitmapsArray[getBitmapIndex(c)] == null) {
                        bitmapsArray[getBitmapIndex(c)] = gameObjects.get(currentIndex).
                                prepareBitmap(context,gameObjects.get(currentIndex).getBitmapName(),
                                        pixelsPerMetre);
                    }
                }
            }
        }
    }
}
