package com.jiangpingwei.cowpea;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.jiangpingwei.cowpea.zhijin.ZhijinFragment;
import com.jiangpingwei.cowpea.movie.MovieFragment;
import com.jiangpingwei.cowpea.chongdian.ChongdianFragment;
import com.jiangpingwei.cowpea.zhijin.ZhijinPresenter;
import com.jiangpingwei.cowpea.zhijin.data.ZhijinRepository;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.bb_main)
    BottomBar bbMain;
    @BindView(R.id.activity_main)
    RelativeLayout activityMain;
    @BindView(R.id.content_container_main)
    FrameLayout contentContainerMain;

    private ZhijinFragment zhijinFragment;
    private MovieFragment movieFragment;
    private ChongdianFragment chongdianFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initFragment();

        //tab选中事件
        bbMain.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {

                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction transation = fm.beginTransaction();

                switch (tabId) {
                    case R.id.tab_book:
                        if (zhijinFragment == null) {
                            zhijinFragment = new ZhijinFragment();
                        }

                        transation.replace(R.id.content_container_main, zhijinFragment);
                        transation.commit();
                        break;
                    case R.id.tab_movie:
                        if (movieFragment == null) {
                            movieFragment = new MovieFragment();
                        }

                        transation.replace(R.id.content_container_main, movieFragment);
                        transation.commit();
                        break;
                    case R.id.tab_music:
                        if (chongdianFragment == null) {
                            chongdianFragment = new ChongdianFragment();
                        }

                        transation.replace(R.id.content_container_main, chongdianFragment);
                        transation.commit();
                        break;
                }
            }
        });

    }

    /**
     * 初始化Fragment
     */
    private void initFragment() {
        zhijinFragment = new ZhijinFragment();
        movieFragment = new MovieFragment();
        chongdianFragment = new ChongdianFragment();
    }
}
