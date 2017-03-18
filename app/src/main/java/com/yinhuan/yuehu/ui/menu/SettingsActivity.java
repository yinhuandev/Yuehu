package com.yinhuan.yuehu.ui.menu;



import com.yinhuan.yuehu.R;
import com.yinhuan.yuehu.ui.activity.ToolbarActivity;




/**
 * Created by yinhuan on 2017/2/4.
 */
public class SettingsActivity extends ToolbarActivity {

    @Override
    protected int setContentViewId() {
        return R.layout.activity_settings;
    }

    @Override
    protected boolean canBack() {
        return true;
    }
}
