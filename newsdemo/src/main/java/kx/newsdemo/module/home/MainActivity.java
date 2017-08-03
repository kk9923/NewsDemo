package kx.newsdemo.module.home;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import kx.newsdemo.R;
import kx.newsdemo.module.news.NewsFragment;
import kx.newsdemo.module.photo.PhotoFragment;
import kx.newsdemo.module.video.VideoFragment;

import static kx.newsdemo.R.id.fl_container;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";
    @BindView(fl_container)
    FrameLayout flContainer;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    private NewsFragment newsFragment;
    private PhotoFragment photoFragment;
    private VideoFragment videoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        _initDrawerLayout(drawerLayout,navView);
        navView.setCheckedItem(R.id.nav_news);
        if (savedInstanceState != null) {
            newsFragment = (NewsFragment) getSupportFragmentManager().getFragment(savedInstanceState, "News");
            photoFragment = (PhotoFragment) getSupportFragmentManager().getFragment(savedInstanceState, "Photos");
            videoFragment = (VideoFragment) getSupportFragmentManager().getFragment(savedInstanceState, "Videos");
        } else {
            newsFragment = new NewsFragment();
            photoFragment = new PhotoFragment();
            videoFragment = new VideoFragment();
        }
        if (!newsFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction()
                    .add(fl_container, newsFragment, "News")
                    .commit();
        }
    }
    /**
     * 初始化 DrawerLayout
     *
     * @param drawerLayout DrawerLayout
     * @param navView      NavigationView
     */
    private void _initDrawerLayout(DrawerLayout drawerLayout, NavigationView navView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
            //将侧边栏顶部延伸至status bar
            drawerLayout.setFitsSystemWindows(true);
            //将主页面顶部延伸至status bar
           drawerLayout.setClipToPadding(false);
        }
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerClosed(View drawerView) {
              //  mHandler.sendEmptyMessage(mItemId);
            }
        });
        navView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        switch (item.getItemId()){
            case R.id.nav_news :            //新闻
                showNewsFragment();
                navView.setCheckedItem(R.id.nav_news);
                break;
            case R.id.nav_photos :            //图片
                if (!photoFragment.isAdded()) {
                    getSupportFragmentManager().beginTransaction()
                            .add(fl_container, photoFragment, "Photos")
                            .commit();
                }
                showPhotosFragment();
                navView.setCheckedItem(R.id.nav_photos);
                break;
            case R.id.nav_videos :            //视频
                if (!videoFragment.isAdded()) {
                    getSupportFragmentManager().beginTransaction()
                            .add(fl_container, videoFragment, "Photos")
                            .commit();
                }
                showVideosFragment();
                navView.setCheckedItem(R.id.nav_videos);
                break;
            case R.id.nav_setting :            //设置
                navView.setCheckedItem(R.id.nav_setting);
                break;
        }
        return true;
    }
    private void showNewsFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.show(newsFragment);
        fragmentTransaction.hide(photoFragment);
        fragmentTransaction.hide(videoFragment);
        fragmentTransaction.commit();
    }

    private void showPhotosFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.show(photoFragment);
        fragmentTransaction.hide(newsFragment);
        fragmentTransaction.hide(videoFragment);
        fragmentTransaction.commit();
    }
    private void showVideosFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.show(videoFragment);
        fragmentTransaction.hide(newsFragment);
        fragmentTransaction.hide(photoFragment);
        fragmentTransaction.commit();
    }
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            exit();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    /**
     * 退出
     */
    private long mExitTime = 0;
    private void exit() {
        if (System.currentTimeMillis() - mExitTime > 2000) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }
    /**
     * 初始化 Toolbar
     *
     * @param toolbar
     * @param homeAsUpEnabled
     * @param title
     */
    public void initToolBar(Toolbar toolbar, boolean homeAsUpEnabled, String title) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(homeAsUpEnabled);
    }
}
