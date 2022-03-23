package com.randoworks.pigskinscores;

import org.json.JSONException;
import org.json.JSONObject;

// Game object to hold info parsed from JSON file
public class Game {

    // Class fields
    private String gameStatus;
    private String startTime;
    private String gameDay;
    private String gameMonth;
    private String gameDate;
    private String broadcastNetwork;
    private String homeTeam;
    private String awayTeam;
    private String homeRecord;
    private String awayRecord;
    private String homeScore;
    private String awayScore;
    private String quarter;
    private String time;

    // Constructor
    public Game(JSONObject gameJSON) throws JSONException {
        this.setGameStatus(gameJSON.get("game_status").toString());
        this.setStartTime(gameJSON.get("start_time").toString());
        this.setGameDay(gameJSON.get("game_day").toString());
        this.setGameMonth(gameJSON.get("game_month").toString());
        this.setGameDate(gameJSON.get("game_date").toString());
        this.setBroadcastNetwork(gameJSON.get("broadcast_network").toString());
        this.setHomeTeam(gameJSON.get("home_team").toString());
        this.setAwayTeam(gameJSON.get("away_team").toString());
        this.setHomeScore(gameJSON.get("home_score").toString());
        this.setAwayScore(gameJSON.get("away_score").toString());
        this.setQuarter(gameJSON.get("quarter").toString());
        this.setTime(gameJSON.get("time").toString());
        this.setHomeRecord(gameJSON.get("home_record").toString());
        this.setAwayRecord(gameJSON.get("away_record").toString());
    }


    // Getters and Setters
    public String getGameStatus() {
        return gameStatus;
    }

    private void setGameStatus(String gameStatus) {
        this.gameStatus = gameStatus;
    }

    public String getStartTime() {
        return startTime;
    }

    private void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getGameDay() {
        return gameDay;
    }

    private void setGameDay(String gameDay) {
        this.gameDay = gameDay;
    }

    public String getGameMonth() {
        return gameMonth;
    }

    private void setGameMonth(String gameMonth) {
        this.gameMonth = gameMonth;
    }

    public String getGameDate() {
        return gameDate;
    }

    private void setGameDate(String gameDate) {
        this.gameDate = gameDate;
    }

    public String getBroadcastNetwork() {
        return broadcastNetwork;
    }

    private void setBroadcastNetwork(String broadcastNetwork) {
        this.broadcastNetwork = broadcastNetwork;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    private void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    private void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public String getHomeRecord() {
        return homeRecord;
    }

    private void setHomeRecord(String homeRecord) {
        this.homeRecord = homeRecord;

    }

    public String getAwayRecord() {
        return awayRecord;
    }

    private void setAwayRecord(String awayRecord) {
        this.awayRecord = awayRecord;
    }

    public String getHomeScore() {
        return homeScore;
    }

    private void setHomeScore(String homeScore) {
        this.homeScore = homeScore;
    }

    public String getAwayScore() {
        return awayScore;
    }

    private void setAwayScore(String awayScore) {
        this.awayScore = awayScore;
    }

    public String getQuarter() {
        return quarter;
    }

    private void setQuarter(String quarter) {
        this.quarter = quarter;
    }

    public String getTime() {
        return time;
    }

    private void setTime(String time) {
        this.time = time;
    }
}
