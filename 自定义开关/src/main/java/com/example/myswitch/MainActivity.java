package com.example.myswitch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private MySwitch mSwitch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSwitch = (MySwitch) findViewById(R.id.ms);
        mSwitch.setOnCheckChangeListener(new MySwitch.OnCheckChangeListener() {
            @Override
            public void OnCheckChange(View view, boolean isChecked) {
                Toast.makeText(getApplicationContext(),"当前状态"+isChecked,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
