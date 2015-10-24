package com.bet.gal.friendlybet;


import android.content.Context;
import android.media.Image;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Gal on 16/10/2015.
 */
public class BetsAdapter extends RecyclerView.Adapter <BetsAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private GamesList gamesList;
    private Context context;

    public BetsAdapter (Context context, GamesList list) {

        context = context;
        inflater = LayoutInflater.from(context);
        gamesList = list;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.game_layout_3, parent,false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Game game = gamesList.getGame(position);
        holder.teamAText.setText(game.getTeamA());
        holder.teamBText.setText((game.getTeamB()));
        holder.teamAImg.setImageResource(game.getTeamAIcon());
        holder.teamBImg.setImageResource(game.getTeamBIcon());
        holder.teamAScore.setText(String.valueOf(game.getTeamAScore()));
        holder.teamBScore.setText(String.valueOf(game.getTeamBScore()));
        holder.date.setText(game.getDate());

    }

    @Override
    public int getItemCount() {
        return gamesList.getSize();
    }

    public void updateItem (int index) {

       // notifyItemChanged(index);
        Log.d("teamAScore: ", String.valueOf(gamesList.getGame(index).getTeamAScore()));

    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView teamAImg, teamBImg;
        private TextView  teamAText, teamBText, teamAScore,teamBScore;
        private EditText date;

        public MyViewHolder(View itemView) {

            super(itemView);

            teamAImg = (ImageView) itemView.findViewById(R.id.team_a_pic);
            teamBImg = (ImageView) itemView.findViewById(R.id.team_b_pic);
            teamAText = (TextView) itemView.findViewById(R.id.team_a_text);
            teamBText = (TextView) itemView.findViewById(R.id.team_b_text);
            date = (EditText) itemView.findViewById(R.id.game_date);
            teamAScore = (TextView) itemView.findViewById(R.id.team_a_score);
            teamBScore = (TextView) itemView.findViewById(R.id.team_b_score);

            final GestureDetectorCompat mDetectorA = new GestureDetectorCompat(context, new MyGestureLisnter(teamAScore));

            final GestureDetectorCompat mDetectorB = new GestureDetectorCompat(context, new MyGestureLisnter(teamBScore));

            teamAScore.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    mDetectorA.onTouchEvent(event);

                    return true;
                }
            });

            teamBScore.setOnTouchListener(new View.OnTouchListener(){

                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    mDetectorB.onTouchEvent(event);

                    return true;
                }
            });


        }

        class MyGestureLisnter extends GestureDetector.SimpleOnGestureListener {

            TextView textView;

            MyGestureLisnter(TextView text){
                textView = text;
            }

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }
           /*

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

                float x1 = e1.getX();
                float x2 = e2.getX();

                int scoreA = Integer.parseInt(teamAScore.getText().toString());

                //left
                if ( (x1 - x2) > 0 ){
                    scoreA--;
                }
                //right
                else if (x1 - x2 < 0){
                    scoreA++;
                }

                teamAScore.setText(String.valueOf(scoreA));

                return true;

            }
            */

            @Override
            public boolean onDoubleTap(MotionEvent e) {

                int index = getAdapterPosition();
                Log.d("clicked at position D", String.valueOf(index));

                if (index != -1) {
                    Game currentGame = gamesList.getGame(index);
                    int score;
                    boolean isTeamA = false;

                    if (textView.getId() == R.id.team_a_score) {

                        score = currentGame.getTeamAScore();
                        isTeamA = true;

                    } else {

                        score = currentGame.getTeamBScore();

                    }

                    if (score > 0) {
                        score--;
                    }

                    if (isTeamA) {
                        currentGame.setTeamAScore(score);
                        teamAScore.setText(String.valueOf(score));
                    } else {
                        currentGame.setTeamBScore(score);
                        teamBScore.setText(String.valueOf(score));
                    }

                    updateItem(index);
                }

                return true;
            }

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {

                int index = getAdapterPosition();
                Log.d("clicked at position ", String.valueOf(index));

                if (index != -1) {
                    Game currentGame = gamesList.getGame(index);
                    int score;
                    boolean isTeamA = false;

                    if (textView.getId() == R.id.team_a_score) {

                        score = currentGame.getTeamAScore();
                        isTeamA = true;

                    } else {

                        score = currentGame.getTeamBScore();

                    }

                    if (score < 15) {
                        score++;
                    }

                    if (isTeamA) {
                        currentGame.setTeamAScore(score);
                        teamAScore.setText(String.valueOf(score));
                    } else {
                        currentGame.setTeamBScore(score);
                        teamBScore.setText(String.valueOf(score));
                    }

                    updateItem(index);
                }

                return true;
            }


        }
    }



}
