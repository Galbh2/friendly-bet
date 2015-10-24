package com.bet.gal.friendlybet;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import static com.bet.gal.friendlybet.MyJson.mapper;

/**
 * Created by Gal on 13/10/2015.
 */
public class FutureBetsFragment extends Fragment {

    private RecyclerView recycler;

    private GamesList gamesList = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        recycler = (RecyclerView) inflater.inflate(R.layout.future_bets_fragment, container, false);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));

        /*
        FutureTask<GamesList> f = new FutureTask<GamesList>(new NetOps.DownloadGameList(getContext()));
        new Thread(f).start();
        */

        new GetDataTask().execute();

        recycler.addItemDecoration(new SpacesItemDecoration(50, getContext()));
        recycler.setHasFixedSize(true);
        return recycler;
    }

    private Game[] init() {

        Game[] games = {new Game("ISR", "ITA"),
                new Game("ENG", "GER"),
                new Game("A  ", "B  "),
                new Game("A  ", "B  "),
                new Game("A  ", "B  "),
                new Game("A  ", "B  ")
                , new Game("A  ", "B  ")
                , new Game("A  ", "B  ")
                , new Game("A  ", "B  ")
                , new Game("A  ", "B  ")
                , new Game("A  ", "B  ")
                , new Game("A  ", "B  ")};



        return games;

    }


    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {

        private int space;
        private Drawable mDivider;

        public SpacesItemDecoration(int space, Context context) {
            this.space = space;
            mDivider = ContextCompat.getDrawable(context, R.drawable.line_divider);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.left = space / 2;
            outRect.right = space / 2;
            outRect.bottom = space;

            // Add top margin only for the first item to avoid double space between items
            if (parent.getChildLayoutPosition(view) == 0)
                outRect.top = space;


        }


        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            int offset = 100;
            int left = parent.getPaddingLeft() + offset;
            int right = parent.getWidth() - parent.getPaddingRight() - offset ;

            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount-1; i++) {
                View child = parent.getChildAt(i);

                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

                int top = child.getBottom() + params.bottomMargin;
                int bottom = top + mDivider.getIntrinsicHeight();

                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);


            }
        }
    }

    private class GetDataTask extends AsyncTask <String, Integer, String> {

        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {

            //dialog = ProgressDialog.show(getActivity(), "FriendlyBet", "Loading...");

        }

        @Override
        protected String doInBackground(String... params) {

            Log.d("doing in back","backkk");
            NetOps.loadSettings(getContext());
            try {
                String s = NetOps.httpRequest(NetOps.getMockAddress());
                gamesList = mapper.readValue(s, GamesList.class);

            } catch (Exception e) {
                Log.d("Connection error: ", e.getMessage());
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {



            if (gamesList != null && recycler.getAdapter() == null) {
                recycler.setAdapter(new BetsAdapter(getContext(), gamesList));
            }else {
                recycler.getAdapter().notifyDataSetChanged();
            }



           // dialog.dismiss();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.refreshButton :
                refresh();
                break;

        }


        return super.onOptionsItemSelected(item);
    }

    private void refresh() {

        new GetDataTask().execute();

    }
}
