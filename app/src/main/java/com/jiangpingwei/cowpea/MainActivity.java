package com.jiangpingwei.cowpea;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.jiangpingwei.cowpea.book.BookFragment;
import com.jiangpingwei.cowpea.movie.MovieFragment;
import com.jiangpingwei.cowpea.music.MusicFragment;
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

    private BookFragment bookFragment;
    private MovieFragment movieFragment;
    private MusicFragment musicFragment;

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
                        if (bookFragment == null) {
                            bookFragment = new BookFragment();
                        }

                        transation.replace(R.id.content_container_main, bookFragment);
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
                        if (musicFragment == null) {
                            musicFragment = new MusicFragment();
                        }

                        transation.replace(R.id.content_container_main, musicFragment);
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
        bookFragment = new BookFragment();
        movieFragment = new MovieFragment();
        musicFragment = new MusicFragment();
    }
}
