package com.example.readjson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.os.Bundle;
import android.widget.Toast;

import com.example.readjson.Adapter.AdapterItemSong;
import com.example.readjson.ItemData.ItemSongMusic;

import java.util.ArrayList;
import java.util.List;

public class ListSongMusic extends AppCompatActivity {

    List<ItemSongMusic> list;
    RecyclerView rvListSong;
    AdapterItemSong adapterItemSong;
    public AppMusic context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_song_music);

        mapping();

        Toast.makeText(this, context.listSong.size()+"", Toast.LENGTH_SHORT).show();

//        list = new ArrayList<>();
//        adapterItemSong = new AdapterItemSong(this);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
//        rvListSong.setLayoutManager(linearLayoutManager);
//        adapterItemSong.setData(getListSong());
//        rvListSong.setAdapter(adapterItemSong);
    }

    public void mapping(){
        rvListSong = findViewById(R.id.rv_list_song);
    }

    public List<ItemSongMusic> getListSong(){
        List<ItemSongMusic> list = new ArrayList<>();
        for(int i = 0; i < context.listSong.size(); i++) {
            list.add(new ItemSongMusic(context.listSong.get(i).getNameSong(),
                    context.listSong.get(i).getNameMusician(),
                    context.listSong.get(i).getAvtSong() ));
        }
        return list;
    }
}