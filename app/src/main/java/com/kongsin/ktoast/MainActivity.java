package com.kongsin.ktoast;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.kongsin.customtoast.KToast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KToast kToast = new KToast(MainActivity.this);
                kToast.show(getString(R.string.app_name), "Test", null, null, KToast.DURATION_SHORT);
            }
        });
    }
}
