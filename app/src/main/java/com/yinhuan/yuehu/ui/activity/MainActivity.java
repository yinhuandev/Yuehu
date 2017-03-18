package com.yinhuan.yuehu.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


import com.yinhuan.yuehu.R;
import com.yinhuan.yuehu.http.rx.RxBus;
import com.yinhuan.yuehu.http.rx.RxBusCode;
import com.yinhuan.yuehu.http.rx.ThemeEvent;
import com.yinhuan.yuehu.mvp.contract.MainContract;
import com.yinhuan.yuehu.mvp.presenter.MainPresenter;
import com.yinhuan.yuehu.ui.adapter.PagerAdapter;
import com.yinhuan.yuehu.ui.menu.DownloadActivity;
import com.yinhuan.yuehu.ui.menu.HomePageActivity;
import com.yinhuan.yuehu.ui.menu.SettingsActivity;
import com.yinhuan.yuehu.util.PreferencesUtility;
import com.yinhuan.yuehu.util.Toasts;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends ToolbarActivity implements MainContract.View{

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;


    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.nav_view)
    NavigationView navView;

    //夜间模式
    private PreferencesUtility mPreferences;

    MainContract.Presenter presenter;

    @Override
    protected int setContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initTheme();
        initView();
        initDrawerLayout();
        initFragment();

    }

    /**
     * 初始化主题
     */
    private void initTheme() {
        mPreferences = PreferencesUtility.getInstance(this);
        if (mPreferences.isNight()){
            setTheme(R.style.NightTheme);
        }else {
            setTheme(R.style.AppTheme);
        }
    }

    /**
     * 初始化 View
     */
    private void initView() {
        presenter = new MainPresenter();

        mPreferences = PreferencesUtility.getInstance(this);

        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initFragment() {
        PagerAdapter<Fragment> mAdapter = new PagerAdapter<>(getSupportFragmentManager());
        viewPager.setAdapter(mAdapter);
        viewPager.setOffscreenPageLimit(4);
        viewPager.setCurrentItem(0);
    }

    /**
     * 初始化 DrawerLayout
     */
    private void initDrawerLayout() {

        //HomeAsUp切换动画效果
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, mToolbar, R.string.open, R.string.close);
        drawerToggle.syncState();
        drawerLayout.addDrawerListener(drawerToggle);

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        drawerLayout.closeDrawers();
                        drawerLayout.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                HomePageActivity.startHome(MainActivity.this);
                            }
                        }, 360);
                        break;
                    case R.id.nav_download:
                        drawerLayout.closeDrawers();
                        drawerLayout.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                DownloadActivity.startAct(MainActivity.this);
                            }
                        }, 360);
                        break;
                    case R.id.nav_night:
                        drawerLayout.closeDrawers();
                        showAnim();
                        transformTheme();
                        setNightUI();
                        break;
                    case R.id.nav_settings:
                        drawerLayout.closeDrawers();
                        drawerLayout.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                                startActivity(intent);
                            }
                        }, 360);

                        break;
                    default:
                        break;
                }
                return false;//取消选中
            }
        });

    }

    /**
     * 转换主题
     */
    private void transformTheme() {
        if (mPreferences.isNight()) {
            setTheme(R.style.AppTheme);
            mPreferences.setNightMode(false);
        } else {
            setTheme(R.style.NightTheme);
            mPreferences.setNightMode(true);
        }
    }

    /**
     * 显示转化换主题过度动画
     */
    private void showAnim() {
        final View decorView = getWindow().getDecorView();
        Bitmap cacheBitmap = getCacheBitmapFromView(decorView);
        if (decorView instanceof ViewGroup && cacheBitmap != null) {
            final View view = new View(this);
            view.setBackgroundDrawable(new BitmapDrawable(getResources(), cacheBitmap));
            ViewGroup.LayoutParams layoutParam = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            ((ViewGroup) decorView).addView(view, layoutParam);
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f);
            objectAnimator.setDuration(300);
            objectAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    ((ViewGroup) decorView).removeView(view);
                }
            });
            objectAnimator.start();
        }
    }

    private Bitmap getCacheBitmapFromView(View decorView) {
        final boolean drawingCacheEnabled = true;
        decorView.setDrawingCacheEnabled(drawingCacheEnabled);
        decorView.buildDrawingCache(drawingCacheEnabled);
        final Bitmap drawingCache = decorView.getDrawingCache();
        Bitmap bitmap;
        if (drawingCache != null) {
            bitmap = Bitmap.createBitmap(drawingCache);
            decorView.setDrawingCacheEnabled(false);
        } else {
            bitmap = null;
        }
        return bitmap;
    }

    /**
     * 设置 MainActivity 夜间主题
     */
    private void setNightUI() {

        //通知各个 Fragment 设置 RecylerView 的子项
        RxBus.getInstance().send(new ThemeEvent(RxBusCode.SET_NIGHT_UI, null));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.search:
                Toasts.showShort(getString(R.string.developing));
                break;
            case R.id.settings:
                Toasts.showShort(getString(R.string.developing));
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }
}
