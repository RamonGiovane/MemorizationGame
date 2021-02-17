package com.game.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.game.model.UserScore;

import java.util.ArrayList;
import java.util.List;

public class Database {

    private final Context context;
    private SQLiteDatabase database;
    private Cursor cursor;
    private int indexId, indexName, indexScore, indexErrors, indexIsBot;

    public Database(Context context){
        this.context = context;
    }

    public void createOrOpen(){
        database =  context.openOrCreateDatabase(DatabaseQueries.DATABASE_NAME,
                DatabaseQueries.DATABASE_ACCESS, null);

        database.execSQL(DatabaseQueries.SQL_STRUCT);

        insertDummyRecords();
    }

    public void insertRecord(UserScore userScore){
        @SuppressLint("DefaultLocale")
        String query = String.format(DatabaseQueries.SQL_INSERT,
                userScore.getName(),
                userScore.getScore(),
                userScore.getErrors(),
                userScore.isBot() ? 1 : 0);

        database.execSQL(query);
    }


    public void insertDummyRecords(){

        if(checkIfTableExists()) return;

        UserScore[] userScores = new UserScore[]{
            new UserScore(2800, 10, "MasterBot", true),
            new UserScore(2500, 9, "Recyclops", true),
            new UserScore(2200, 8, "Bender", true),
            new UserScore(2000, 7, "R2D2", true),
            new UserScore(1700, 6, "Murderbot", true),
            new UserScore(1500, 5, "Rusty", true),
            new UserScore(1300, 4, "Ava", true),
            new UserScore(1200, 3, "ThinMan", true),
            new UserScore(1100, 2, "Homer", true),
            new UserScore(1000, 1, "Atom", true),
        };

        for(UserScore u : userScores)
            insertRecord(u);

    }

    public List<UserScore> getAll(){
        List<UserScore> scores = new ArrayList<>();

        cursor = database.rawQuery(DatabaseQueries.SQL_QUERY, null);

        if(cursor.moveToFirst()){
            indexId = cursor.getColumnIndex("_id");
            indexName = cursor.getColumnIndex("name");
            indexScore = cursor.getColumnIndex("score");
            indexErrors = cursor.getColumnIndex("errors");
            indexIsBot = cursor.getColumnIndex("bot");

            do{

                scores.add(new UserScore(
                        cursor.getInt(indexScore),
                        cursor.getInt(indexErrors),
                        cursor.getString(indexName),
                    cursor.getInt(indexIsBot) == 1));

            }while (cursor.moveToNext());
        }

        cursor.close();

        return scores;

    }

    public boolean checkIfTableExists(){
        cursor = database.rawQuery(DatabaseQueries.SQL_CHECK_TABLE, null);

        if(!cursor.moveToFirst()) return false;

        int response = cursor.getInt(0);
        cursor.close();

        return  response >= 1;
    }


}

