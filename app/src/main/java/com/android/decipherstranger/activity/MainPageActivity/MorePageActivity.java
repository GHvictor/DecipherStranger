package com.android.decipherstranger.activity.MainPageActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.decipherstranger.R;
import com.android.decipherstranger.activity.SubpageActivity.NearbyListViewActivity;
import com.android.decipherstranger.activity.SubpageActivity.ShakeActivity;

public class MorePageActivity extends Activity {
    
    private Intent intent = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main_more);
	}
    
    public void morePageOnClick(View view) {
        switch (view.getId()) {
            case R.id.friends_trends:
                break;
            case R.id.friends_invitation:
                break;
            case R.id.friends_shake:
                this.intent = new Intent(this, ShakeActivity.class);
                this.startActivity(intent);
                break;
            case R.id.friends_near:
                this.intent = new Intent(this, NearbyListViewActivity.class);
                this.startActivity(intent);
                break;
            case R.id.friends_map:
                break;
            case R.id.friends_game:
                break;
        }
    }

}
