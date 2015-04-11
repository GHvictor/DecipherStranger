package com.android.decipherstranger.activity.ShakeActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.android.decipherstranger.R;

/**
 * Created by peng on 2015/3/26.
 */
public class ShakeSettingsActivity extends Activity implements View.OnClickListener{
    
    private Intent intent = null;
    private Button backButton = null;
    
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake_settings);
        intiView();
    }
    
    private void intiView() {
        this.backButton = (Button) super.findViewById(R.id.shake_back_button);
        this.backButton.setOnClickListener(this);
    }
    
    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.shake_back_button:
                onBackPressed();
                break;
        }
    }
    
}
