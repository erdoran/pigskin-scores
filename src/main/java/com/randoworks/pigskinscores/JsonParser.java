package com.randoworks.pigskinscores;

import android.content.Context;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

// Parse the JSON data into a list of Game objects
public class JsonParser {

    private static final String sFILE_KEY = "game_data.json";

    public static ArrayList<Game> parseJson(Context context, LinearLayout ll) {

        try {
            // Open file and start reading lines
            FileInputStream fis = context.openFileInput(sFILE_KEY);
            InputStreamReader inputStreamReader = new InputStreamReader(fis, StandardCharsets.UTF_8);
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                stringBuilder.append(line).append('\n');
                line = reader.readLine();
            }
            // Convert file contents into JSON string
            String contents = stringBuilder.toString();
            try {
                // Convert JSON string into JSON Array
                JSONArray jsonArray = new JSONArray(contents);
                ArrayList<Game> games = new ArrayList<>();
                // Create Game object for each JSON Array object
                for (int i = 0; i < jsonArray.length(); i++) {
                    games.add(new Game(new JSONObject(jsonArray.get(i).toString())));
                }
                return games;
            } catch (JSONException e) {
                Util.showNoConnectionSnack(ll);
                return new ArrayList<Game>();
            }
        } catch (IOException e) {
            Util.showNoConnectionSnack(ll);
            return new ArrayList<Game>();
        }
    }
}
