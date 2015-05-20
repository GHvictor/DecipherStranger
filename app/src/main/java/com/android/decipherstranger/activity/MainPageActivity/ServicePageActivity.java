package com.android.decipherstranger.activity.MainPageActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.android.decipherstranger.R;
import com.android.decipherstranger.activity.Base.BaseActivity;
import com.android.decipherstranger.activity.SubpageActivity.GameListActivity;
import com.android.decipherstranger.activity.SubpageActivity.NearbyListViewActivity;
import com.android.decipherstranger.activity.SubpageActivity.ShakeActivity;
import com.android.decipherstranger.activity.SubpageActivity.ShowMapActivity;

public class ServicePageActivity extends BaseActivity {
    
    private Intent intent = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main_service);
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
                this.intent = new Intent(this, ShowMapActivity.class);
                this.startActivity(intent);
                break;
            case R.id.friends_game:
                this.intent = new Intent(this, GameListActivity.class);
                this.startActivity(intent);
                break;
        }
    }

}
