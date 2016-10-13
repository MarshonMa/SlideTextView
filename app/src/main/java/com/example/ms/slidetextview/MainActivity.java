package com.example.ms.slidetextview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private SlideTextView mSelectText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSelectText = (SlideTextView) findViewById(R.id.tv_select);
        mSelectText.setText("Hello World");
        findViewById(R.id.btn_last).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSelectText.previous();
                mSelectText.setText("Slide View");
            }
        });
        findViewById(R.id.btn_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSelectText.next();
                mSelectText.setText("I'm Second");
            }
        });
    }
}
