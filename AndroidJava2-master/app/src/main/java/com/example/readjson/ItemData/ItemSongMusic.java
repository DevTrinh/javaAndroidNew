package com.example.readjson.ItemData;

import android.graphics.drawable.Drawable;

public class ItemSongMusic {
    private String nameSong;
    private String nameMusician;
    private Drawable drawable;
    private int source;
    private int avtSong;

    public ItemSongMusic(String nameSong, String nameMusician, int source, int avtSong) {
        this.nameSong = nameSong;
        this.nameMusician = nameMusician;
        this.source = source;
        this.avtSong = avtSong;
    }

    public ItemSongMusic(String nameSong, String nameMusician,  int avtSong) {
        this.nameSong = nameSong;
        this.nameMusician = nameMusician;
        this.avtSong = avtSong;
    }


    public String getNameSong() {
        return nameSong;
    }

    public void setNameSong(String nameSong) {
        this.nameSong = nameSong;
    }

    public String getNameMusician() {
        return nameMusician;
    }

    public void setNameMusician(String nameMusician) {
        this.nameMusician = nameMusician;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public int getAvtSong() {
        return avtSong;
    }

    public void setAvtSong(int avtSong) {
        this.avtSong = avtSong;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }
}
