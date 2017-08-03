package kx.newsdemo.module.photo;

import android.animation.Animator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import kx.newsdemo.R;
import kx.newsdemo.adapter.ViewPagerAdapter;
import kx.newsdemo.module.home.MainActivity;
import kx.newsdemo.module.photo.beauty.BeautyFragment;
import kx.newsdemo.module.photo.news.PhotoNewsFragment;
import kx.newsdemo.module.photo.welfare.WelFareFragment;
import kx.newsdemo.utils.AnimateHelper;
import kx.newsdemo.utils.StatusUtils;

/**
 * Created by 31716 on 2017/5/24.
 */

public class PhotoFragment extends Fragment {

    @BindView(R.id.iv_count)
    TextView ivCount;
    @BindView(R.id.fl_layout)
    FrameLayout flLayout;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tab_layout)
    SlidingTabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    Unbinder unbinder;
    @BindView(R.id.top)
    TextView top;
    private Animator mLovedAnimator;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.fragment_photo_main, container, false);
        unbinder = ButterKnife.bind(this, mRootView);
        int statusHeight = StatusUtils.getStatusHeight(getActivity());
        //动态设置状态栏高度
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.height = statusHeight;
        top.setLayoutParams(params);
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new BeautyFragment());
        fragments.add(new WelFareFragment());
        fragments.add(new PhotoNewsFragment());
        ViewPagerAdapter mPagerAdapter = new ViewPagerAdapter(getFragmentManager());
        mPagerAdapter.setItems(fragments, new String[]{"美女", "福利", "生活"});
        viewPager.setAdapter(mPagerAdapter);
        tabLayout.setViewPager(viewPager);
        toolBar.setTitle("视频");  // 放在setSupportActionBar 方法后面无效
        ((MainActivity) getActivity()).setSupportActionBar(toolBar);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);
        return mRootView;
    }
    @Override
    public void onResume() {
        super.onResume();
        if (mLovedAnimator == null) {
            ivCount.post(new Runnable() {
                @Override
                public void run() {
                   mLovedAnimator = AnimateHelper.doHappyJump(ivCount, ivCount.getHeight() * 2 / 3, 3000);
                }
            });
        } else {
            AnimateHelper.startAnimator(mLovedAnimator);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        AnimateHelper.stopAnimator(mLovedAnimator);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
