package com.hfad.stopwatch;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int second=0;
    private boolean running;
    private boolean wasRunning;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState!=null){
            second=savedInstanceState.getInt("second");
            running=savedInstanceState.getBoolean("running");
        }
        runTimer();
    }

       public void onClickStart(View view){
        runTimer();
        running=true;

    }
    public void onClickStop(View view){
        running=false;

    }
    public void onClickReset(View view){
        running=false;
        second=0;
    }


    private void runTimer() {
        final Handler handler=new Handler();

        handler.post(new Runnable() {
            @Override
            public void run() {
                TextView textView=findViewById(R.id.timeView);
                int hours=second/3600;
                int minutes=(second%3600)/60;
                int secs=second%60;

                String time= String.format("%d : %02d : %02d ",hours,minutes,secs);
                textView.setText(time);
                if(running){
                    second++;
                }
                handler.postDelayed(this,1000);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("second",second);
        outState.putBoolean("running",running);
    }

    @Override
    protected void onStop() {
        super.onStop();

        wasRunning=running;
        running=false;

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(wasRunning){
            running=true;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        wasRunning=running;
        running=false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(wasRunning){
            running=true;
        }
    }
}
