package lovely.Adventure.GameLevels;

import java.util.ArrayList;

import lovely.Adventure.Background.BackgroundData;
import lovely.Adventure.Teleport.LocationSave;

public class MountainLevel extends DataLevel {
    public MountainLevel() {
        tiles = new ArrayList<String>();
        this.tiles.add("p................................................................................................2222222222222..........");
        this.tiles.add("........................................................................................................................");
        this.tiles.add("................................2...2....2...................................................t.2........................");
        this.tiles.add(".........................2..............................................................................................");
        this.tiles.add(".............................................2..........................................2....2..........................");
        this.tiles.add("............................22....................................................2......................................");
        this.tiles.add(".................................................2......................................................................");
        this.tiles.add(".......2...................2....................................................2.......................................");
        this.tiles.add("........................................................................................................................");
        this.tiles.add("..2.......................2..........................2.......................2..........................................");
        this.tiles.add("........................................................................................................................");
        this.tiles.add(".......2.....2...2..2...2...............................2.................2.............................................");
        this.tiles.add("........................................................................................................................");
        this.tiles.add(".....2.2....................................................2..2..222222................................................");
        this.tiles.add("........................................................................................................................");
        this.tiles.add(".2....x....2x.2....x.........x..........x.................x..............x..........x.........x........x................");
        this.tiles.add("........................................................................................................................");
        this.tiles.add("...2....2............ecu................................................................................................");
        this.tiles.add("........................................................................................................................");
        this.tiles.add("222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222");

        locationSaves = new ArrayList<LocationSave>();
        this.locationSaves.add(new LocationSave("UndergroundLevel", 1f, 16f));

        backgroundDataList = new ArrayList<BackgroundData>();
        this.backgroundDataList.add(new BackgroundData("mountain", -2, -7, 6, 4,11));
        this.backgroundDataList.add(new BackgroundData("mountainside", -1, 6, 20, 8,14));
        this.backgroundDataList.add(new BackgroundData("ice",1,18,24,25,4));
    }

    public static MountainLevel newLevelMountain(){
        return new MountainLevel();
    }
}