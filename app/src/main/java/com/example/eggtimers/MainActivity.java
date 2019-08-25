package com.example.eggtimers;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar timeSetter;
    Button button;
    TextView currentCount;
    boolean isStopped = false;

    public void buttonClicked(View view){
        String text = button.getText().toString();
        isStopped = false;
        if (text == "GO!"){
            button.setText("STOP!");
            timeSetter.setVisibility(View.GONE);
            countDownTimer(timeSetter.getProgress());
        } else{
            button.setText("GO!");
            timeSetter.setVisibility(View.VISIBLE);
            isStopped = true;
        }


    }

    public void playSound(){
        MediaPlayer mediaPlayer = MediaPlayer.create(this,R.raw.airhorn);
        mediaPlayer.start();
    }


    public void countDownTimer(int duration){
        new CountDownTimer(timeSetter.getProgress()*1000+100, 1000){
            @Override
            public void onTick(long millisUntilFinished) {
                int untilFinished = (int) (millisUntilFinished/1000);
                currentCount.setText(Integer.toString(untilFinished/60)+":"+Integer.toString(untilFinished%60));
                String text = currentCount.getText().toString();
                if (untilFinished%60 == 0){
                    currentCount.setText(text + "0");
                }else if (untilFinished%60 < 10){
                    currentCount.setText(Integer.toString(untilFinished/60)+":0"+Integer.toString(untilFinished%60));
                }
                if (isStopped){
                    cancel();
                    currentCount.setText("0:30");
                    timeSetter.setProgress(30);
                }
            }

            @Override
            public void onFinish() {
                playSound();
                button.setText("GO!");
                timeSetter.setVisibility(View.VISIBLE);
                timeSetter.setProgress(30);
                currentCount.setText("0:30");
            }
        }.start();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        button.setText("GO!");

        currentCount = (TextView) findViewById(R.id.time);

        timeSetter = (SeekBar) findViewById(R.id.timeSetter);
        timeSetter.setMax(600);
        timeSetter.setProgress(30);
        currentCount.setText("0:30");

        timeSetter.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                currentCount.setText(Integer.toString(progress/60)+":"+Integer.toString(progress%60));
                String text = currentCount.getText().toString();
                if (progress%60 == 0){
                    currentCount.setText(text + "0");
                }else if (progress%60 < 10){
                    currentCount.setText(Integer.toString(progress/60)+":0"+Integer.toString(progress%60));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }
}
