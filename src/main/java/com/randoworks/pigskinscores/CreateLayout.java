package com.randoworks.pigskinscores;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

// Takes information scraped into Game objects and creates Layouts
public class CreateLayout {

    // Attaches team logo to team name
    private static @DrawableRes
    int getTeamImage(String team) {
        switch (team) {
            case "49ers":
                return R.drawable.sanfran;
            case "Bears":
                return R.drawable.bears;
            case "Bengals":
                return R.drawable.bengals;
            case "Bills":
                return R.drawable.bills;
            case "Broncos":
                return R.drawable.broncos;
            case "Browns":
                return R.drawable.browns;
            case "Buccaneers":
                return R.drawable.buccs;
            case "Cardinals":
                return R.drawable.cardinals;
            case "Chargers":
                return R.drawable.chargers;
            case "Chiefs":
                return R.drawable.chiefs;
            case "Colts":
                return R.drawable.colts;
            case "Cowboys":
                return R.drawable.cowboys;
            case "Dolphins":
                return R.drawable.dolphins;
            case "Eagles":
                return R.drawable.eagles;
            case "Falcons":
                return R.drawable.falcons;
            case "Giants":
                return R.drawable.giants;
            case "Jaguars":
                return R.drawable.jags;
            case "Jets":
                return R.drawable.jets;
            case "Lions":
                return R.drawable.lions;
            case "Packers":
                return R.drawable.packers;
            case "Panthers":
                return R.drawable.panthers;
            case "Patriots":
                return R.drawable.pats;
            case "Raiders":
                return R.drawable.raiders;
            case "Rams":
                return R.drawable.rams;
            case "Ravens":
                return R.drawable.ravens;
            case "Washington":
                return R.drawable.washington;
            case "Saints":
                return R.drawable.saints;
            case "Seahawks":
                return R.drawable.seahawks;
            case "Steelers":
                return R.drawable.steelers;
            case "Texans":
                return R.drawable.texans;
            case "Titans":
                return R.drawable.titans;
            case "Vikings":
                return R.drawable.vikings;
            default:
                return R.drawable.missing_team;
        }
    }

    public static ConstraintLayout createPregameLayout(Game game, int length, Context context) {

        // Get size constants based on device
        final int EIGHT = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                8, context.getResources().getDisplayMetrics());
        final int SIXTEEN = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                16, context.getResources().getDisplayMetrics());
        final int TWENTY_FIVE = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                25, context.getResources().getDisplayMetrics());
        final int THIRTY_TWO = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                32, context.getResources().getDisplayMetrics());
        final int FIFTY = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                50, context.getResources().getDisplayMetrics());

        ConstraintLayout pregameLayout = new ConstraintLayout(context);
        pregameLayout.setId(View.generateViewId());

        TextView broadcastView = new TextView(context);
        broadcastView.setId(View.generateViewId());
        broadcastView.setText(game.getBroadcastNetwork());
        broadcastView.setTextAppearance(R.style.BroadcastNetworkTextStyle);

        ImageView homeTeamImage = new ImageView(context);
        homeTeamImage.setId(View.generateViewId());
        homeTeamImage.setImageResource(getTeamImage(game.getHomeTeam()));

        TextView homeTeamView = new TextView(context);
        homeTeamView.setId(View.generateViewId());
        homeTeamView.setText(game.getHomeTeam());
        homeTeamView.setTextAppearance(R.style.TeamNameTextStyle);

        ImageView awayTeamImage = new ImageView(context);
        awayTeamImage.setId(View.generateViewId());
        awayTeamImage.setImageResource(getTeamImage(game.getAwayTeam()));

        TextView awayTeamView = new TextView(context);
        awayTeamView.setId(View.generateViewId());
        awayTeamView.setText(game.getAwayTeam());
        awayTeamView.setTextAppearance(R.style.TeamNameTextStyle);

        TextView gameDateView = new TextView(context);
        gameDateView.setId(View.generateViewId());
        gameDateView.setText(context.getString(R.string.scoreFragment_gameDate, game.getGameDay(), game.getGameMonth(), game.getGameDate()));
        gameDateView.setTextAppearance(R.style.GameTimeInfoTextStyle);

        TextView gameStartTimeView = new TextView(context);
        gameStartTimeView.setId(View.generateViewId());
        gameStartTimeView.setText(context.getString(R.string.scoreFragment_startTime, game.getStartTime()));
        gameStartTimeView.setTextAppearance(R.style.GameTimeInfoTextStyle);

        TextView marginMaker = new TextView(context);
        marginMaker.setId(View.generateViewId());
        marginMaker.setText("");
        if (length > 1) marginMaker.setBackgroundResource(R.drawable.bottom_border);

        TextView homeRecordView = new TextView(context);
        homeRecordView.setId(View.generateViewId());
        TextView awayRecordView = new TextView(context);
        awayRecordView.setId(View.generateViewId());

        if (!game.getHomeRecord().equals("null") && !game.getAwayRecord().equals("null")) {
            homeRecordView.setText(context.getString(R.string.scoreFragment_teamRecord, game.getHomeRecord()));
            awayRecordView.setText(context.getString(R.string.scoreFragment_teamRecord, game.getAwayRecord()));
            homeRecordView.setTextAppearance(R.style.RecordTextStyle);
            awayRecordView.setTextAppearance(R.style.RecordTextStyle);
            pregameLayout.addView(homeRecordView);
            pregameLayout.addView(awayRecordView);
        }

        pregameLayout.addView(broadcastView);
        pregameLayout.addView(homeTeamView);
        pregameLayout.addView(homeTeamImage);
        pregameLayout.addView(awayTeamView);
        pregameLayout.addView(awayTeamImage);
        pregameLayout.addView(gameDateView);
        pregameLayout.addView(gameStartTimeView);
        pregameLayout.addView(marginMaker);

        ConstraintSet constraintSet = new ConstraintSet();

        constraintSet.constrainWidth(broadcastView.getId(), ConstraintSet.WRAP_CONTENT);
        constraintSet.constrainHeight(broadcastView.getId(), ConstraintSet.WRAP_CONTENT);

        constraintSet.constrainWidth(homeTeamImage.getId(), FIFTY);
        constraintSet.constrainHeight(homeTeamImage.getId(), FIFTY);

        constraintSet.constrainWidth(homeTeamView.getId(), ConstraintSet.WRAP_CONTENT);
        constraintSet.constrainHeight(homeTeamView.getId(), ConstraintSet.WRAP_CONTENT);

        constraintSet.constrainWidth(awayTeamImage.getId(), FIFTY);
        constraintSet.constrainHeight(awayTeamImage.getId(), FIFTY);

        constraintSet.constrainWidth(awayTeamView.getId(), ConstraintSet.WRAP_CONTENT);
        constraintSet.constrainHeight(awayTeamView.getId(), ConstraintSet.WRAP_CONTENT);

        constraintSet.constrainWidth(gameDateView.getId(), ConstraintSet.WRAP_CONTENT);
        constraintSet.constrainHeight(gameDateView.getId(), ConstraintSet.WRAP_CONTENT);

        constraintSet.constrainWidth(gameStartTimeView.getId(), ConstraintSet.WRAP_CONTENT);
        constraintSet.constrainHeight(gameStartTimeView.getId(), ConstraintSet.WRAP_CONTENT);

        if (!game.getHomeRecord().equals("null") && !game.getAwayRecord().equals("null")) {

            constraintSet.constrainWidth(homeRecordView.getId(), ConstraintSet.WRAP_CONTENT);
            constraintSet.constrainHeight(homeRecordView.getId(), ConstraintSet.WRAP_CONTENT);

            constraintSet.constrainWidth(awayRecordView.getId(), ConstraintSet.WRAP_CONTENT);
            constraintSet.constrainHeight(awayRecordView.getId(), ConstraintSet.WRAP_CONTENT);
        }

        constraintSet.constrainWidth(marginMaker.getId(), 0);
        constraintSet.constrainHeight(marginMaker.getId(), ConstraintSet.WRAP_CONTENT);

        constraintSet.connect(broadcastView.getId(), ConstraintSet.TOP,
                ConstraintSet.PARENT_ID, ConstraintSet.TOP, SIXTEEN);
        constraintSet.connect(broadcastView.getId(), ConstraintSet.LEFT,
                ConstraintSet.PARENT_ID, ConstraintSet.LEFT, SIXTEEN);

        constraintSet.connect(homeTeamImage.getId(), ConstraintSet.TOP,
                broadcastView.getId(), ConstraintSet.BOTTOM, TWENTY_FIVE);
        constraintSet.connect(homeTeamImage.getId(), ConstraintSet.LEFT,
                ConstraintSet.PARENT_ID, ConstraintSet.LEFT, THIRTY_TWO);

        constraintSet.connect(homeTeamView.getId(), ConstraintSet.TOP,
                homeTeamImage.getId(), ConstraintSet.BOTTOM, EIGHT);
        constraintSet.connect(homeTeamView.getId(), ConstraintSet.LEFT,
                homeTeamImage.getId(), ConstraintSet.LEFT);
        constraintSet.connect(homeTeamView.getId(), ConstraintSet.RIGHT,
                homeTeamImage.getId(), ConstraintSet.RIGHT);

        constraintSet.connect(awayTeamImage.getId(), ConstraintSet.TOP,
                homeTeamImage.getId(), ConstraintSet.TOP);
        constraintSet.connect(awayTeamImage.getId(), ConstraintSet.BOTTOM,
                homeTeamImage.getId(), ConstraintSet.BOTTOM);
        constraintSet.connect(awayTeamImage.getId(), ConstraintSet.RIGHT,
                ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, THIRTY_TWO);

        constraintSet.connect(awayTeamView.getId(), ConstraintSet.TOP,
                awayTeamImage.getId(), ConstraintSet.BOTTOM, EIGHT);
        constraintSet.connect(awayTeamView.getId(), ConstraintSet.LEFT,
                awayTeamImage.getId(), ConstraintSet.LEFT);
        constraintSet.connect(awayTeamView.getId(), ConstraintSet.RIGHT,
                awayTeamImage.getId(), ConstraintSet.RIGHT);

        constraintSet.connect(gameDateView.getId(), ConstraintSet.LEFT,
                ConstraintSet.PARENT_ID, ConstraintSet.LEFT);
        constraintSet.connect(gameDateView.getId(), ConstraintSet.RIGHT,
                ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);
        constraintSet.connect(gameDateView.getId(), ConstraintSet.TOP,
                homeTeamImage.getId(), ConstraintSet.TOP);
        constraintSet.connect(gameDateView.getId(), ConstraintSet.BOTTOM,
                homeTeamImage.getId(), ConstraintSet.BOTTOM);

        constraintSet.connect(gameStartTimeView.getId(), ConstraintSet.LEFT,
                ConstraintSet.PARENT_ID, ConstraintSet.LEFT);
        constraintSet.connect(gameStartTimeView.getId(), ConstraintSet.RIGHT,
                ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);
        constraintSet.connect(gameStartTimeView.getId(), ConstraintSet.TOP,
                gameDateView.getId(), ConstraintSet.BOTTOM, EIGHT);

        if (!game.getHomeRecord().equals("null") && !game.getAwayRecord().equals("null")) {

            constraintSet.connect(homeRecordView.getId(), ConstraintSet.TOP,
                    homeTeamView.getId(), ConstraintSet.BOTTOM, EIGHT);
            constraintSet.connect(homeRecordView.getId(), ConstraintSet.LEFT,
                    homeTeamImage.getId(), ConstraintSet.LEFT);
            constraintSet.connect(homeRecordView.getId(), ConstraintSet.RIGHT,
                    homeTeamImage.getId(), ConstraintSet.RIGHT);

            constraintSet.connect(awayRecordView.getId(), ConstraintSet.TOP,
                    awayTeamView.getId(), ConstraintSet.BOTTOM, EIGHT);
            constraintSet.connect(awayRecordView.getId(), ConstraintSet.LEFT,
                    awayTeamImage.getId(), ConstraintSet.LEFT);
            constraintSet.connect(awayRecordView.getId(), ConstraintSet.RIGHT,
                    awayTeamImage.getId(), ConstraintSet.RIGHT);

            constraintSet.connect(marginMaker.getId(), ConstraintSet.TOP,
                    homeRecordView.getId(), ConstraintSet.BOTTOM, TWENTY_FIVE);
            constraintSet.connect(marginMaker.getId(), ConstraintSet.LEFT,
                    homeTeamImage.getId(), ConstraintSet.LEFT);
            constraintSet.connect(marginMaker.getId(), ConstraintSet.RIGHT,
                    awayTeamImage.getId(), ConstraintSet.RIGHT);

        } else {

            constraintSet.connect(marginMaker.getId(), ConstraintSet.TOP,
                    homeTeamView.getId(), ConstraintSet.BOTTOM, SIXTEEN);
            constraintSet.connect(marginMaker.getId(), ConstraintSet.LEFT,
                    homeTeamImage.getId(), ConstraintSet.LEFT);
            constraintSet.connect(marginMaker.getId(), ConstraintSet.RIGHT,
                    awayTeamImage.getId(), ConstraintSet.RIGHT);
        }

        constraintSet.applyTo(pregameLayout);

        return pregameLayout;
    }

    public static ConstraintLayout createInProgressLayout(Game game, int length, Context context) {

        // Determine size constants based on device
        final int EIGHT = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                8, context.getResources().getDisplayMetrics());
        final int SIXTEEN = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                16, context.getResources().getDisplayMetrics());
        final int THIRTY_TWO = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                32, context.getResources().getDisplayMetrics());
        final int FIFTY = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                50, context.getResources().getDisplayMetrics());

        ConstraintLayout inProgressLayout = new ConstraintLayout(context);
        inProgressLayout.setId(View.generateViewId());

        TextView broadcastView = new TextView(context);
        broadcastView.setId(View.generateViewId());
        broadcastView.setText(game.getBroadcastNetwork());
        broadcastView.setTextAppearance(R.style.BroadcastNetworkTextStyle);

        ImageView homeTeamImage = new ImageView(context);
        homeTeamImage.setId(View.generateViewId());
        homeTeamImage.setImageResource(getTeamImage(game.getHomeTeam()));

        TextView homeTeamView = new TextView(context);
        homeTeamView.setId(View.generateViewId());
        homeTeamView.setText(game.getHomeTeam());
        homeTeamView.setTextAppearance(R.style.TeamNameTextStyle);

        TextView homeScoreView = new TextView(context);
        homeScoreView.setId(View.generateViewId());
        homeScoreView.setText(game.getHomeScore());
        homeScoreView.setTextAppearance(R.style.ScoreTextStyle);

        ImageView awayTeamImage = new ImageView(context);
        awayTeamImage.setId(View.generateViewId());
        awayTeamImage.setImageResource(getTeamImage(game.getAwayTeam()));

        TextView awayTeamView = new TextView(context);
        awayTeamView.setId(View.generateViewId());
        awayTeamView.setText(game.getAwayTeam());
        awayTeamView.setTextAppearance(R.style.TeamNameTextStyle);

        TextView awayScoreView = new TextView(context);
        awayScoreView.setId(View.generateViewId());
        awayScoreView.setText(game.getAwayScore());
        awayScoreView.setTextAppearance(R.style.ScoreTextStyle);

        TextView quarterView = new TextView(context);
        quarterView.setId(View.generateViewId());
        if (game.getQuarter().equals("End") && game.getTime().equals("2nd")) {
            quarterView.setText(R.string.scoreFragment_halftime);
        } else {
            quarterView.setText(context.getString(R.string.scoreFragment_quarter, game.getQuarter()));
        }

        quarterView.setTextAppearance(R.style.GameTimeInfoTextStyle);

        TextView timeView = new TextView(context);
        timeView.setId(View.generateViewId());
        if (game.getQuarter().equals("End") && game.getTime().equals("2nd")) {
            timeView.setText("");  // Leave time blank if game at halftime
        } else {
            timeView.setText(game.getTime());
        }

        timeView.setTextAppearance(R.style.GameTimeInfoTextStyle);

        TextView marginMaker = new TextView(context);
        marginMaker.setId(View.generateViewId());
        marginMaker.setText("");
        if (length > 1) marginMaker.setBackgroundResource(R.drawable.bottom_border);

        inProgressLayout.addView(broadcastView);
        inProgressLayout.addView(homeTeamView);
        inProgressLayout.addView(homeTeamImage);
        inProgressLayout.addView(homeScoreView);
        inProgressLayout.addView(awayTeamView);
        inProgressLayout.addView(awayTeamImage);
        inProgressLayout.addView(awayScoreView);
        inProgressLayout.addView(quarterView);
        inProgressLayout.addView(timeView);
        inProgressLayout.addView(marginMaker);

        ConstraintSet constraintSet = new ConstraintSet();

        constraintSet.constrainHeight(broadcastView.getId(), ConstraintSet.WRAP_CONTENT);
        constraintSet.constrainWidth(broadcastView.getId(), ConstraintSet.WRAP_CONTENT);

        constraintSet.constrainHeight(homeTeamView.getId(), ConstraintSet.WRAP_CONTENT);
        constraintSet.constrainWidth(homeTeamView.getId(), ConstraintSet.WRAP_CONTENT);

        constraintSet.constrainHeight(homeTeamImage.getId(), FIFTY);
        constraintSet.constrainWidth(homeTeamImage.getId(), FIFTY);

        constraintSet.constrainHeight(homeScoreView.getId(), ConstraintSet.WRAP_CONTENT);
        constraintSet.constrainWidth(homeScoreView.getId(), ConstraintSet.WRAP_CONTENT);

        constraintSet.constrainHeight(awayTeamImage.getId(), FIFTY);
        constraintSet.constrainWidth(awayTeamImage.getId(), FIFTY);

        constraintSet.constrainHeight(awayTeamView.getId(), ConstraintSet.WRAP_CONTENT);
        constraintSet.constrainWidth(awayTeamView.getId(), ConstraintSet.WRAP_CONTENT);

        constraintSet.constrainHeight(awayScoreView.getId(), ConstraintSet.WRAP_CONTENT);
        constraintSet.constrainWidth(awayScoreView.getId(), ConstraintSet.WRAP_CONTENT);

        constraintSet.constrainHeight(quarterView.getId(), ConstraintSet.WRAP_CONTENT);
        constraintSet.constrainWidth(quarterView.getId(), ConstraintSet.WRAP_CONTENT);

        constraintSet.constrainHeight(timeView.getId(), ConstraintSet.WRAP_CONTENT);
        constraintSet.constrainWidth(timeView.getId(), ConstraintSet.WRAP_CONTENT);

        constraintSet.constrainHeight(marginMaker.getId(), ConstraintSet.WRAP_CONTENT);
        constraintSet.constrainWidth(marginMaker.getId(), 0);

        constraintSet.connect(broadcastView.getId(), ConstraintSet.TOP,
                ConstraintSet.PARENT_ID, ConstraintSet.TOP, SIXTEEN);
        constraintSet.connect(broadcastView.getId(), ConstraintSet.LEFT,
                ConstraintSet.PARENT_ID, ConstraintSet.LEFT, SIXTEEN);

        constraintSet.connect(homeTeamImage.getId(), ConstraintSet.TOP,
                broadcastView.getId(), ConstraintSet.BOTTOM, SIXTEEN);
        constraintSet.connect(homeTeamImage.getId(), ConstraintSet.LEFT,
                ConstraintSet.PARENT_ID, ConstraintSet.LEFT, THIRTY_TWO);

        constraintSet.connect(homeTeamView.getId(), ConstraintSet.TOP,
                homeTeamImage.getId(), ConstraintSet.BOTTOM, EIGHT);
        constraintSet.connect(homeTeamView.getId(), ConstraintSet.LEFT,
                homeTeamImage.getId(), ConstraintSet.LEFT);
        constraintSet.connect(homeTeamView.getId(), ConstraintSet.RIGHT,
                homeTeamImage.getId(), ConstraintSet.RIGHT);

        constraintSet.connect(homeScoreView.getId(), ConstraintSet.LEFT,
                homeTeamImage.getId(), ConstraintSet.RIGHT);
        constraintSet.connect(homeScoreView.getId(), ConstraintSet.RIGHT,
                quarterView.getId(), ConstraintSet.LEFT);
        constraintSet.connect(homeScoreView.getId(), ConstraintSet.TOP,
                homeTeamImage.getId(), ConstraintSet.TOP);
        constraintSet.connect(homeScoreView.getId(), ConstraintSet.BOTTOM,
                homeTeamImage.getId(), ConstraintSet.BOTTOM);

        constraintSet.connect(awayTeamImage.getId(), ConstraintSet.TOP,
                homeTeamImage.getId(), ConstraintSet.TOP);
        constraintSet.connect(awayTeamImage.getId(), ConstraintSet.BOTTOM,
                homeTeamImage.getId(), ConstraintSet.BOTTOM);
        constraintSet.connect(awayTeamImage.getId(), ConstraintSet.RIGHT,
                ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, THIRTY_TWO);

        constraintSet.connect(awayTeamView.getId(), ConstraintSet.TOP,
                awayTeamImage.getId(), ConstraintSet.BOTTOM, EIGHT);
        constraintSet.connect(awayTeamView.getId(), ConstraintSet.LEFT,
                awayTeamImage.getId(), ConstraintSet.LEFT);
        constraintSet.connect(awayTeamView.getId(), ConstraintSet.RIGHT,
                awayTeamImage.getId(), ConstraintSet.RIGHT);

        constraintSet.connect(awayScoreView.getId(), ConstraintSet.RIGHT,
                awayTeamImage.getId(), ConstraintSet.LEFT);
        constraintSet.connect(awayScoreView.getId(), ConstraintSet.LEFT,
                quarterView.getId(), ConstraintSet.RIGHT);
        constraintSet.connect(awayScoreView.getId(), ConstraintSet.TOP,
                awayTeamImage.getId(), ConstraintSet.TOP);
        constraintSet.connect(awayScoreView.getId(), ConstraintSet.BOTTOM,
                awayTeamImage.getId(), ConstraintSet.BOTTOM);

        constraintSet.connect(quarterView.getId(), ConstraintSet.TOP,
                homeTeamImage.getId(), ConstraintSet.TOP);
        constraintSet.connect(quarterView.getId(), ConstraintSet.BOTTOM,
                homeTeamImage.getId(), ConstraintSet.BOTTOM);
        constraintSet.connect(quarterView.getId(), ConstraintSet.LEFT,
                ConstraintSet.PARENT_ID, ConstraintSet.LEFT);
        constraintSet.connect(quarterView.getId(), ConstraintSet.RIGHT,
                ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);

        constraintSet.connect(timeView.getId(), ConstraintSet.TOP,
                quarterView.getId(), ConstraintSet.BOTTOM, EIGHT);
        constraintSet.connect(timeView.getId(), ConstraintSet.LEFT,
                ConstraintSet.PARENT_ID, ConstraintSet.LEFT);
        constraintSet.connect(timeView.getId(), ConstraintSet.RIGHT,
                ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);

        constraintSet.connect(marginMaker.getId(), ConstraintSet.TOP,
                homeTeamView.getId(), ConstraintSet.BOTTOM, SIXTEEN);
        constraintSet.connect(marginMaker.getId(), ConstraintSet.LEFT,
                homeTeamImage.getId(), ConstraintSet.LEFT);
        constraintSet.connect(marginMaker.getId(), ConstraintSet.RIGHT,
                awayTeamImage.getId(), ConstraintSet.RIGHT);

        constraintSet.applyTo(inProgressLayout);

        return inProgressLayout;
    }

    public static ConstraintLayout createPostgameLayout(Game game, int length, Context context) {

        //
        final int EIGHT = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                8, context.getResources().getDisplayMetrics());
        final int SIXTEEN = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                16, context.getResources().getDisplayMetrics());
        final int THIRTY_TWO = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                32, context.getResources().getDisplayMetrics());
        final int FIFTY = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                50, context.getResources().getDisplayMetrics());

        ConstraintLayout postgameLayout = new ConstraintLayout(context);
        postgameLayout.setId(View.generateViewId());

        TextView homeTeamView = new TextView(context);
        homeTeamView.setId(View.generateViewId());
        homeTeamView.setText(game.getHomeTeam());
        homeTeamView.setTextAppearance(R.style.TeamNameTextStyle);

        ImageView homeTeamImage = new ImageView(context);
        homeTeamImage.setId(View.generateViewId());
        homeTeamImage.setImageResource(getTeamImage(game.getHomeTeam()));

        TextView homeScoreView = new TextView(context);
        homeScoreView.setId(View.generateViewId());
        homeScoreView.setText(game.getHomeScore());
        homeScoreView.setTextAppearance(R.style.ScoreTextStyle);

        TextView awayTeamView = new TextView(context);
        awayTeamView.setId(View.generateViewId());
        awayTeamView.setText(game.getAwayTeam());
        awayTeamView.setTextAppearance(R.style.TeamNameTextStyle);

        ImageView awayTeamImage = new ImageView(context);
        awayTeamImage.setId(View.generateViewId());
        awayTeamImage.setImageResource(getTeamImage(game.getAwayTeam()));

        TextView awayScoreView = new TextView(context);
        awayScoreView.setId(View.generateViewId());
        awayScoreView.setText(game.getAwayScore());
        awayScoreView.setTextAppearance(R.style.ScoreTextStyle);

        TextView finalView = new TextView(context);
        finalView.setId(View.generateViewId());
        finalView.setText(context.getString(R.string.scoreFragment_final));
        finalView.setTextAppearance(R.style.GameFinalTextStyle);

        TextView marginMaker = new TextView(context);
        marginMaker.setId(View.generateViewId());
        marginMaker.setText("");
        if (length > 1) marginMaker.setBackgroundResource(R.drawable.bottom_border);

        TextView homeRecordView = new TextView(context);
        TextView awayRecordView = new TextView(context);
        homeRecordView.setId(View.generateViewId());
        awayRecordView.setId(View.generateViewId());

        if (!game.getHomeRecord().equals("null") && !game.getAwayRecord().equals("null")) {
            homeRecordView.setText(context.getString(R.string.scoreFragment_teamRecord, game.getHomeRecord()));
            awayRecordView.setText(context.getString(R.string.scoreFragment_teamRecord, game.getAwayRecord()));
            homeRecordView.setTextAppearance(R.style.RecordTextStyle);
            awayRecordView.setTextAppearance(R.style.RecordTextStyle);
            postgameLayout.addView(homeRecordView);
            postgameLayout.addView(awayRecordView);
        }

        postgameLayout.addView(homeTeamView);
        postgameLayout.addView(homeTeamImage);
        postgameLayout.addView(homeScoreView);
        postgameLayout.addView(awayTeamView);
        postgameLayout.addView(awayTeamImage);
        postgameLayout.addView(awayScoreView);
        postgameLayout.addView(finalView);
        postgameLayout.addView(marginMaker);

        ConstraintSet constraintSet = new ConstraintSet();

        constraintSet.constrainWidth(homeTeamImage.getId(), FIFTY);
        constraintSet.constrainHeight(homeTeamImage.getId(), FIFTY);

        constraintSet.constrainWidth(homeTeamView.getId(), ConstraintSet.WRAP_CONTENT);
        constraintSet.constrainHeight(homeTeamView.getId(), ConstraintSet.WRAP_CONTENT);

        constraintSet.constrainWidth(homeScoreView.getId(), ConstraintSet.WRAP_CONTENT);
        constraintSet.constrainHeight(homeScoreView.getId(), ConstraintSet.WRAP_CONTENT);

        constraintSet.constrainWidth(awayTeamImage.getId(), FIFTY);
        constraintSet.constrainHeight(awayTeamImage.getId(), FIFTY);

        constraintSet.constrainWidth(awayTeamView.getId(), ConstraintSet.WRAP_CONTENT);
        constraintSet.constrainHeight(awayTeamView.getId(), ConstraintSet.WRAP_CONTENT);

        constraintSet.constrainWidth(awayScoreView.getId(), ConstraintSet.WRAP_CONTENT);
        constraintSet.constrainHeight(awayScoreView.getId(), ConstraintSet.WRAP_CONTENT);

        constraintSet.constrainWidth(finalView.getId(), ConstraintSet.WRAP_CONTENT);
        constraintSet.constrainHeight(finalView.getId(), ConstraintSet.WRAP_CONTENT);

        if (!game.getHomeRecord().equals("null") && !game.getAwayRecord().equals("null")) {
            constraintSet.constrainWidth(homeRecordView.getId(), ConstraintSet.WRAP_CONTENT);
            constraintSet.constrainHeight(homeRecordView.getId(), ConstraintSet.WRAP_CONTENT);

            constraintSet.constrainWidth(awayRecordView.getId(), ConstraintSet.WRAP_CONTENT);
            constraintSet.constrainHeight(awayRecordView.getId(), ConstraintSet.WRAP_CONTENT);
        }

        constraintSet.constrainWidth(marginMaker.getId(), 0);
        constraintSet.constrainHeight(marginMaker.getId(), ConstraintSet.WRAP_CONTENT);

        constraintSet.connect(homeTeamImage.getId(), ConstraintSet.TOP,
                ConstraintSet.PARENT_ID, ConstraintSet.TOP, SIXTEEN);
        constraintSet.connect(homeTeamImage.getId(), ConstraintSet.LEFT,
                ConstraintSet.PARENT_ID, ConstraintSet.LEFT, THIRTY_TWO);

        constraintSet.connect(homeTeamView.getId(), ConstraintSet.TOP,
                homeTeamImage.getId(), ConstraintSet.BOTTOM, EIGHT);
        constraintSet.connect(homeTeamView.getId(), ConstraintSet.LEFT,
                homeTeamImage.getId(), ConstraintSet.LEFT);
        constraintSet.connect(homeTeamView.getId(), ConstraintSet.RIGHT,
                homeTeamImage.getId(), ConstraintSet.RIGHT);

        constraintSet.connect(homeScoreView.getId(), ConstraintSet.LEFT,
                homeTeamImage.getId(), ConstraintSet.RIGHT);
        constraintSet.connect(homeScoreView.getId(), ConstraintSet.RIGHT,
                finalView.getId(), ConstraintSet.LEFT);
        constraintSet.connect(homeScoreView.getId(), ConstraintSet.TOP,
                homeTeamImage.getId(), ConstraintSet.TOP);
        constraintSet.connect(homeScoreView.getId(), ConstraintSet.BOTTOM,
                homeTeamImage.getId(), ConstraintSet.BOTTOM);

        constraintSet.connect(awayTeamImage.getId(), ConstraintSet.TOP,
                homeTeamImage.getId(), ConstraintSet.TOP);
        constraintSet.connect(awayTeamImage.getId(), ConstraintSet.BOTTOM,
                homeTeamImage.getId(), ConstraintSet.BOTTOM);
        constraintSet.connect(awayTeamImage.getId(), ConstraintSet.RIGHT,
                ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, THIRTY_TWO);

        constraintSet.connect(awayTeamView.getId(), ConstraintSet.TOP,
                awayTeamImage.getId(), ConstraintSet.BOTTOM, EIGHT);
        constraintSet.connect(awayTeamView.getId(), ConstraintSet.LEFT,
                awayTeamImage.getId(), ConstraintSet.LEFT);
        constraintSet.connect(awayTeamView.getId(), ConstraintSet.RIGHT,
                awayTeamImage.getId(), ConstraintSet.RIGHT);

        constraintSet.connect(awayScoreView.getId(), ConstraintSet.RIGHT,
                awayTeamImage.getId(), ConstraintSet.LEFT);
        constraintSet.connect(awayScoreView.getId(), ConstraintSet.LEFT,
                finalView.getId(), ConstraintSet.RIGHT);
        constraintSet.connect(awayScoreView.getId(), ConstraintSet.TOP,
                awayTeamImage.getId(), ConstraintSet.TOP);
        constraintSet.connect(awayScoreView.getId(), ConstraintSet.BOTTOM,
                awayTeamImage.getId(), ConstraintSet.BOTTOM);

        constraintSet.connect(finalView.getId(), ConstraintSet.TOP,
                homeTeamImage.getId(), ConstraintSet.TOP);
        constraintSet.connect(finalView.getId(), ConstraintSet.BOTTOM,
                homeTeamImage.getId(), ConstraintSet.BOTTOM);
        constraintSet.connect(finalView.getId(), ConstraintSet.LEFT,
                ConstraintSet.PARENT_ID, ConstraintSet.LEFT);
        constraintSet.connect(finalView.getId(), ConstraintSet.RIGHT,
                ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);

        if (!game.getHomeRecord().equals("null") && !game.getAwayRecord().equals("null")) {
            constraintSet.connect(homeRecordView.getId(), ConstraintSet.TOP,
                    homeTeamView.getId(), ConstraintSet.BOTTOM, EIGHT);
            constraintSet.connect(homeRecordView.getId(), ConstraintSet.LEFT,
                    homeTeamImage.getId(), ConstraintSet.LEFT);
            constraintSet.connect(homeRecordView.getId(), ConstraintSet.RIGHT,
                    homeTeamImage.getId(), ConstraintSet.RIGHT);

            constraintSet.connect(awayRecordView.getId(), ConstraintSet.TOP,
                    awayTeamView.getId(), ConstraintSet.BOTTOM, EIGHT);
            constraintSet.connect(awayRecordView.getId(), ConstraintSet.LEFT,
                    awayTeamImage.getId(), ConstraintSet.LEFT);
            constraintSet.connect(awayRecordView.getId(), ConstraintSet.RIGHT,
                    awayTeamImage.getId(), ConstraintSet.RIGHT);

            constraintSet.connect(marginMaker.getId(), ConstraintSet.TOP,
                    homeRecordView.getId(), ConstraintSet.BOTTOM, SIXTEEN);
            constraintSet.connect(marginMaker.getId(), ConstraintSet.LEFT,
                    homeTeamImage.getId(), ConstraintSet.LEFT);
            constraintSet.connect(marginMaker.getId(), ConstraintSet.RIGHT,
                    awayTeamImage.getId(), ConstraintSet.RIGHT);
        } else {
            constraintSet.connect(marginMaker.getId(), ConstraintSet.TOP,
                    homeTeamView.getId(), ConstraintSet.BOTTOM, SIXTEEN);
            constraintSet.connect(marginMaker.getId(), ConstraintSet.LEFT,
                    homeTeamImage.getId(), ConstraintSet.LEFT);
            constraintSet.connect(marginMaker.getId(), ConstraintSet.RIGHT,
                    awayTeamImage.getId(), ConstraintSet.RIGHT);
        }

        constraintSet.applyTo(postgameLayout);

        return postgameLayout;
    }
}
