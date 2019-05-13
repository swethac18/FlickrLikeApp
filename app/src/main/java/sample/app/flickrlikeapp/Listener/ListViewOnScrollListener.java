package sample.app.flickrlikeapp.Listener;

import android.view.View;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by swetha on 5/12/19.
 */

public class ListViewOnScrollListener implements AbsListView.OnScrollListener {
    private int mLastFirstVisibleItem;
    private boolean mIsScrollingUp;
    private ListView mListView;
    private FrameLayout quickReturnBanner;
    private TextView recentViewTitle;

    public ListViewOnScrollListener(ListView lw, FrameLayout banner, TextView textView) {
        mListView  = lw;
        this.quickReturnBanner = banner;
        this.recentViewTitle = textView;
    }

    public void onScrollStateChanged(AbsListView view, int scrollState) {
        final ListView lw = mListView;

        if (view.getId() == lw.getId()) {
            final int currentFirstVisibleItem = lw.getFirstVisiblePosition();

            if (currentFirstVisibleItem > mLastFirstVisibleItem) {
                mIsScrollingUp = false;
            } else if (currentFirstVisibleItem < mLastFirstVisibleItem) {
                mIsScrollingUp = true;
            }

            mLastFirstVisibleItem = currentFirstVisibleItem;

            if (mIsScrollingUp) {
                quickReturnBanner.setVisibility(View.VISIBLE);
                recentViewTitle.setVisibility(View.VISIBLE);
            } else {
                quickReturnBanner.setVisibility(View.GONE);
                recentViewTitle.setVisibility(View.GONE);

            }
        }
    }

    private void removeOrRetainBannerAndTitle(int scrollState) {
        if (scrollState == SCROLL_STATE_FLING && !mIsScrollingUp ) {
            quickReturnBanner.setVisibility(View.GONE);
            recentViewTitle.setVisibility(View.GONE);
        } else if (scrollState == SCROLL_STATE_TOUCH_SCROLL) {
            if (mIsScrollingUp) {
                quickReturnBanner.setVisibility(View.VISIBLE);
                recentViewTitle.setVisibility(View.VISIBLE);
            } else {
                quickReturnBanner.setVisibility(View.GONE);
                recentViewTitle.setVisibility(View.GONE);
            }
        }
    }



    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {

    }
}

