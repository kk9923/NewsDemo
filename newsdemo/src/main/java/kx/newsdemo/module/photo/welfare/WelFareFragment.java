package kx.newsdemo.module.photo.welfare;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dl7.recycler.helper.RecyclerViewHelper;
import com.dl7.recycler.listener.OnRequestDataListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import kx.newsdemo.R;
import kx.newsdemo.adapter.SlideInBottomAdapter;
import kx.newsdemo.adapter.WelfarePhotoAdapter;
import kx.newsdemo.api.RetrofitService;
import kx.newsdemo.bean.WelfarePhotoInfo;
import kx.newsdemo.utils.ImageLoader;
import kx.newsdemo.widget.EmptyLayout;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by 31716 on 2017/5/24.
 */

public class WelFareFragment extends Fragment {
    @BindView(R.id.rv_photo_list)
    RecyclerView rvPhotoList;
    Unbinder unbinder;
    @BindView(R.id.empty_layout)
    EmptyLayout emptyLayout;
    // @BindView(R.id.refresh)
    // SwipeRefreshLayout refresh;
    //缓存Fragment view
    private View mRootView;
    private boolean mIsMulti = false;
    private WelfarePhotoAdapter mAdapter;
    private int pageNum = 1;
    private SmartRefreshLayout refreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_photo_welfare, container, false);
        }
        final ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }
        unbinder = ButterKnife.bind(this, mRootView);
        refreshLayout = (SmartRefreshLayout) mRootView.findViewById(R.id.refreshLayout);
        refreshLayout.setEnableLoadmore(false);
        mAdapter = new WelfarePhotoAdapter(getActivity());
        SlideInBottomAdapter slideAdapter = new SlideInBottomAdapter(mAdapter);
        RecyclerViewHelper.initRecyclerViewSV(getActivity(), rvPhotoList, slideAdapter, 2);
        mAdapter.setRequestDataListener(new OnRequestDataListener() {
            @Override
            public void onLoadMore() {
                    getMoreData();
            }
        });
//        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                pageNum=1;
//                getData();
//            }
//        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pageNum=1;
               getData();
            }
        });
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        if (getUserVisibleHint() && mRootView != null && !mIsMulti) {
//            mIsMulti = true;
//            RetrofitService.getWelfarePhoto(1).compose(mTransformer).subscribe(new Observer<List<WelfarePhotoInfo>>() {
//                @Override
//                public void onCompleted() {
//
//                }
//
//                @Override
//                public void onError(Throwable e) {
//
//                }
//
//                @Override
//                public void onNext(List<WelfarePhotoInfo> welfarePhotoInfos) {
//                }
//            });
//        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser && isVisible() && mRootView != null && !mIsMulti) {
            mIsMulti = true;
            getData();
        } else {
            super.setUserVisibleHint(isVisibleToUser);
        }
    }

    private void getData() {
        RetrofitService.getWelfarePhoto(pageNum).compose(mTransformer).subscribe(new Observer<List<WelfarePhotoInfo>>() {
            @Override
            public void onCompleted() {
                emptyLayout.hide();
                refreshLayout.finishRefresh();
            }

            @Override
            public void onError(Throwable e) {
                emptyLayout.setEmptyStatus(EmptyLayout.STATUS_NO_NET);
            }

            @Override
            public void onNext(List<WelfarePhotoInfo> welfarePhotoInfos) {
                mAdapter.updateItems(welfarePhotoInfos);
                mAdapter.loadComplete();
                pageNum++;
            }
        });
    }

    private void getMoreData() {
        RetrofitService.getWelfarePhoto(pageNum).compose(mTransformer).subscribe(new Observer<List<WelfarePhotoInfo>>() {
            @Override
            public void onCompleted() {
                //mAdapter.noMoreData();
                mAdapter.loadComplete();
            }

            @Override
            public void onError(Throwable e) {
                mAdapter.loadAbnormal();
            }

            @Override
            public void onNext(List<WelfarePhotoInfo> welfarePhotoInfos) {
                mAdapter.loadComplete();
                mAdapter.addItems(welfarePhotoInfos);
                pageNum++;
            }
        });
    }

    /**
     * 统一变换
     */
    private Observable.Transformer<WelfarePhotoInfo, List<WelfarePhotoInfo>> mTransformer = new Observable.Transformer<WelfarePhotoInfo, List<WelfarePhotoInfo>>() {

        @Override
        public Observable<List<WelfarePhotoInfo>> call(Observable<WelfarePhotoInfo> welfarePhotoInfoObservable) {
            return welfarePhotoInfoObservable
                    .observeOn(Schedulers.io())
                    // 接口返回的数据是没有宽高参数的，所以这里设置图片的宽度和高度，速度会慢一点
                    .filter(new Func1<WelfarePhotoInfo, Boolean>() {
                        @Override
                        public Boolean call(WelfarePhotoInfo photoBean) {
                            try {
                                photoBean.setPixel(ImageLoader.calePhotoSize(getActivity(), photoBean.getUrl()));
                                return true;
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                                return false;
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                return false;
                            }
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .toList();
            //  .compose(mView.<List<WelfarePhotoInfo>>bindToLife());
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
