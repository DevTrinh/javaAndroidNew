package com.example.readjson;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.readjson.ItemData.ItemSongMusic;
import com.example.readjson.Interface.DataVariable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AppMusic extends AppCompatActivity implements DataVariable {

    ImageButton ibListMusic;
    TextView tvNameMusic, tvNameMusician, tvTimeBegin, tvTimeEnd;
    CircleImageView ciCenterMusic;
    CheckBox cbPlayPause;
    ImageButton ibNextBegin, ibNextEnd;
    SeekBar sbSong;

    public ArrayList<ItemSongMusic> listSong;
    MediaPlayer playMusic;
    Animation animRotate;
    SharedPreferences sharedPreferences;
    private static int position;

    @RequiresApi(api = Build.VERSION_CODES.S)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_music);

        mapping();
        addSong();
        sharedPreferences = getSharedPreferences("dataMusic", MODE_PRIVATE);

        playMusic = MediaPlayer.create(AppMusic.this, listSong.get(position).getSource());
        animRotate = AnimationUtils.loadAnimation(this, R.anim.rotate_music);

        ciCenterMusic.startAnimation(animRotate);

//        LIST SONG MUSIC
        ibListMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppMusic.this, ListSongMusic.class));
                overridePendingTransition(R.anim.anim_begin, R.anim.anim_end);
            }
        });

//        NEXT -
        ibNextBegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position == 0){
                    position = listSong.size() - 1;
                }
                else{
                    position--;
                }
                setPlayMusic(position);
                setTimeMusic(playMusic);
            }
        });

//        NEXT +
        ibNextEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position == listSong.size() - 1){
                    position = 0;
                }
                else{
                    position++;
                }
                setPlayMusic(position);
                setTimeMusic(playMusic);
                updateTimeSong();
            }
        });

//        PAUSE PLAY
        cbPlayPause.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    ciCenterMusic.startAnimation(animRotate);
                    tvNameMusic.setText(listSong.get(position).getNameSong());
                    tvNameMusician.setText(listSong.get(position).getNameMusician());
                    ciCenterMusic.setImageResource(listSong.get(position).getAvtSong());
                    playMusic.start();
                    setTimeMusic(playMusic);
                    updateTimeSong();
                    Toast.makeText(AppMusic.this,
                            position+"", Toast.LENGTH_SHORT).show();
                } else {
                    ciCenterMusic.clearAnimation();
                    playMusic.pause();
                }
            }
        });

        sbSong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.d("VALUE: ", "VALUE i: "+progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                playMusic.seekTo(sbSong.getProgress());
                Log.d("GET PROGRESS: ", sbSong.getProgress()+"");
                cbPlayPause.setChecked(true);
                updateTimeSong();
                sbSong.setProgress(playMusic.getCurrentPosition());
            }
        });
    }

    private void updateTimeSong(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                tvTimeBegin.setText(simpleDateFormat.format(playMusic.getCurrentPosition()));
                handler.postDelayed(this, 200);
//                return position present

            }
        }, 100);
        playMusic.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                setPlayMusic(position+2);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void setTimeMusic(@NonNull MediaPlayer mediaPlayer){
        SimpleDateFormat formatHour = new SimpleDateFormat("mm:ss");
        tvTimeEnd.setText(formatHour.format(mediaPlayer.getDuration()));
//        ASSIGN VALUE OF SEEKBAR = mediaPlayer.getDuration
        sbSong.setMax(mediaPlayer.getDuration());

        Log.d("VALUE: ", mediaPlayer.getDuration()+"\n"+sbSong.getMax());
    }

    private void setPlayMusic(int i){
        playMusic.stop();
        playMusic = MediaPlayer.create(AppMusic.this, listSong.get(i).getSource());
        ciCenterMusic.startAnimation(animRotate);
        tvNameMusic.setText(listSong.get(i).getNameSong());
        tvNameMusician.setText(listSong.get(i).getNameMusician());
        ciCenterMusic.setImageResource(listSong.get(i).getAvtSong());
        cbPlayPause.setChecked(true);
        Toast.makeText(this, position+"", Toast.LENGTH_SHORT).show();
        playMusic.start();
    }

    //https://rejected-box.000webhostapp.com/music/DeVuong-DinhDungACV-7121634.mp3
    //https://rejected-box.000webhostapp.com/music/icons8-end-28%20(1).png

    private void addSong() {
        listSong = new ArrayList<>();
        listSong.add(new ItemSongMusic("Ánh sao và bầu trời", "T.R.I x Cá",
                R.raw.anh_sao_va_bau_troi, R.drawable.avt_anhsaovabautroi ));
        listSong.add(new ItemSongMusic("Chúng ta của sau này", "Amen Giời Ơi",
                R.raw.chungtasaunay, R.drawable.avt_chungtacuasaunay));
        listSong.add(new ItemSongMusic("Có hẹn với thanh xuân", "Monstar",
                R.raw.co_hen_voi_thanh_xuan, R.drawable.avt_cohenvoithanhxuan));
        listSong.add(new ItemSongMusic("3107 - 2", "Duong - G",
                R.raw.music_31072, R.drawable.avt_music3107));
        listSong.add(new ItemSongMusic("Nàng thơ", "Hoàng dũng",
                R.raw.nangtho, R.drawable.avt_nangtho));
        listSong.add(new ItemSongMusic("Phía sau một cô gái", "Soobin Hoàng Sơn",
                R.raw.phiasaumotcogai, R.drawable.avt_phiasaumotcogai));
        listSong.add(new ItemSongMusic("Răng khôn", "Phí Phương Anh",
                R.raw.rangkhon, R.drawable.avt_rangkhon));
        listSong.add(new ItemSongMusic("Sài gòn đau lòng quá", "Kim Tuyền",
                R.raw.saigondaulongqua, R.drawable.avt_saigondaulongqua));
    }

    public void mapping() {
        tvNameMusic = findViewById(R.id.tv_name_music);
        tvNameMusician = findViewById(R.id.tv_name_musician);
        tvTimeBegin = findViewById(R.id.tv_time_begin);
        tvTimeEnd = findViewById(R.id.tv_time_end);
        ibNextBegin = findViewById(R.id.ib_next_begin);
        ibNextEnd = findViewById(R.id.ib_next_end);
        sbSong = findViewById(R.id.sb_time_music);
        ciCenterMusic = findViewById(R.id.iv_main_music_app);
        cbPlayPause = findViewById(R.id.cb_play_pause);
        ibListMusic = findViewById(R.id.ib_menu);

    }

    public void dataSave(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(POSITION_MUSIC_PLAY, position);
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        position = sharedPreferences.getInt(POSITION_MUSIC_PLAY, 0);
        setPlayMusic(position);
        cbPlayPause.setChecked(false);
        Toast.makeText(this, "ON RESUME", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        dataSave();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "ON DESTROY", Toast.LENGTH_SHORT).show();
        playMusic.stop();
        playMusic.release();
    }
}