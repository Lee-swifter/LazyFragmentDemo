package lic.swifter.demo.lazy.fragment;


import android.os.Bundle;
import androidx.annotation.CheckResult;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import lic.swifter.demo.lazy.R;

/**
 * 懒加载Fragment，其子类都具有懒加载的能力
 */
public abstract class LazyFragment extends Fragment {

    private ProgressBar progressBar;
    FrameLayout content;

    private boolean hasLoaded = false;  //标识是否已经加载过
    private boolean hasCreated = false; //标识onCreateView是否已调用
    private boolean needInit = false;   //标识是否需要在onCreateView中

    private int mShortAnimationDuration;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mShortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("lazy_load", "LazyFragment onCreateView. ");

        View rootView = inflater.inflate(R.layout.fragment_lazy, container, false);
        progressBar = $(rootView, R.id.fragment_lazy_progress_bar);
        content = $(rootView, R.id.fragment_lazy_content_wrapper);

        if(needInit) {
            initWrapper();
            needInit = false;
        }
        hasCreated = true;

        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.i("lazy_load", "LazyFragment setUserVisibleHint "+isVisibleToUser);

        if (isVisibleToUser && !hasLoaded) {    //如果当前Fragment向用户展示且没有加载过，进行下一步判断
            if (hasCreated) {   //如果onCreateView已经被创建，此时进行加载
                initWrapper();
            } else {        //如果Fragment没有创建，那么设置标记，在onCreateView中加载
                needInit = true;
            }
        }
    }

    /**
     * 此函数开始数据加载的操作，且仅调用一次
     */
    private void initWrapper() {
        LayoutInflater.from(getContext()).inflate(setContentView(), content);
        initialize();
        fadeOutView(progressBar, mShortAnimationDuration);
        fadeInView(content, mShortAnimationDuration);
        hasLoaded = true;
    }

    /**
     * 供子类使用，子类fragment初始化操作，此函数内部真正开始进行页面的一些列操作
     */
    abstract void initialize();

    /**
     * 子类fragment设置布局，返回fragment要设定的布局
     * @return 子类fragment要显示的布局
     */
    @LayoutRes
    abstract int setContentView();


    @SuppressWarnings("unchecked")
    @CheckResult
    public <T extends View> T $(@NonNull View view, @IdRes int id) {
        return (T) view.findViewById(id);
    }

    public static void fadeOutView(final View view, int duration) {
        view.animate()
                .alpha(0f)
                .setDuration(duration)
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        view.setVisibility(View.GONE);
                    }
                });
    }

    public static void fadeInView(View view, int duration) {
        view.setAlpha(0f);
        view.setVisibility(View.VISIBLE);
        view.animate()
                .alpha(1f)
                .setDuration(duration)
                .setListener(null);
    }
}
