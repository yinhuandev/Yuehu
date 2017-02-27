package com.yinhuan.yuehu.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.yinhuan.yuehu.R;
import com.yinhuan.yuehu.util.Toasts;


/**
 * Created by yinhuan on 2017/2/9.
 * 设置 页面
 */

public class SettingsFragment extends PreferenceFragment {

    private final String THEME = "theme";
    private final String HUE = "hue";
    private final String FEEDBACK = "feedback";
    private final String CHECK_VERSION = "check_version";

    //主题
    private Preference theme;
    //色调
    private Preference hue;
    //问题反馈
    private Preference feedback;
    //检查更新
    private Preference check_version;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
        initView();
    }

    private void initView() {
        theme = findPreference(THEME);
        hue = findPreference(HUE);
        feedback = findPreference(FEEDBACK);
        check_version = findPreference(CHECK_VERSION);

        theme.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Toasts.showShort(getString(R.string.developing));
                return true;
            }
        });

        hue.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Toasts.showShort(getString(R.string.developing));
                return true;
            }
        });

        feedback.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent email = new Intent(Intent.ACTION_SENDTO);
                email.setData(Uri.parse("mailto:ykzzldx269@163.com"));
                email.putExtra(Intent.EXTRA_SUBJECT, "Yuehu Feedback");
                email.putExtra(Intent.EXTRA_TEXT, "Hi，");
                startActivity(email);
                return true;
            }
        });

        check_version.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Toasts.showShort(getString(R.string.developing));
                return true;
            }
        });
    }
}
