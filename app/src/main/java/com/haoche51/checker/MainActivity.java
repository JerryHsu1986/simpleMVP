package com.haoche51.checker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.haoche51.aoplib.annotation.DebugLog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        testDebugLog(1);
    }

    private void testDebugLog(int i){
        System.out.println("test");
    }
}

