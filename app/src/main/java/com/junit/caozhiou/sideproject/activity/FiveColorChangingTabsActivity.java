package com.junit.caozhiou.sideproject.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.junit.caozhiou.sideproject.R;
import com.junit.caozhiou.sideproject.view.bottom.tabbar.BottomBar;
import com.junit.caozhiou.sideproject.view.bottom.tabbar.OnMenuTabClickListener;

/**
 * Created by iiro on 7.6.2016.
 */
public class FiveColorChangingTabsActivity extends AppCompatActivity {
    private BottomBar mBottomBar;
    private TextView mMessageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_five_color_changing_tabs);

        mMessageView = (TextView) findViewById(R.id.messageView);

        mBottomBar = BottomBar.attach(this, savedInstanceState);
        mBottomBar.setItems(R.menu.bottombar_menu);
        mBottomBar.setOnMenuTabClickListener(new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                mMessageView.setText(TabMessage.get(menuItemId, false));
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {
                Toast.makeText(getApplicationContext(), TabMessage.get(menuItemId, true), Toast.LENGTH_SHORT).show();
            }
        });

        // Setting colors for different tabs when there's more than three of them.
        // You can set colors for tabs in three different ways as shown below.
//        mBottomBar.mapColorForTab(0, ContextCompat.getColor(this, R.color.colorAccent));

        mBottomBar.mapColorForTab(0, ContextCompat.getColor(this, R.color.colorAccent));
        mBottomBar.mapColorForTab(1, 0xFF5D4037);
        mBottomBar.mapColorForTab(2, "#7B1FA2");
        mBottomBar.mapColorForTab(3, "#FF5252");
        mBottomBar.mapColorForTab(4, "#FF9800");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Necessary to restore the BottomBar's state, otherwise we would
        // lose the current tab on orientation change.
        mBottomBar.onSaveInstanceState(outState);
    }
    static class TabMessage {
        public static String get(int menuItemId, boolean isReselection) {
            String message = "Content for ";

            switch (menuItemId) {
                case R.id.bb_menu_recents:
                    message += "recents";
                    break;
                case R.id.bb_menu_favorites:
                    message += "favorites";
                    break;
                case R.id.bb_menu_nearby:
                    message += "nearby";
                    break;
                case R.id.bb_menu_friends:
                    message += "friends";
                    break;
                case R.id.bb_menu_food:
                    message += "food";
                    break;
            }

            if (isReselection) {
                message += " WAS RESELECTED! YAY!";
            }

            return message;
        }
    }
}