package com.haoche51.checker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import hugo.weaving.DebugLog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        testDebugLog(1);
    }
    @DebugLog
    private void testDebugLog(int i){
        System.out.println("test");
    }
}

