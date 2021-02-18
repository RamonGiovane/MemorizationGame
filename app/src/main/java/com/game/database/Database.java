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
            new UserScore(280000, 10, "MasterBot", true),
            new UserScore(250000, 9, "Recyclops", true),
            new UserScore(220000, 8, "Bender", true),
            new UserScore(200000, 7, "R2D2", true),
            new UserScore(170000, 6, "Murderbot", true),
            new UserScore(150000, 5, "Rusty", true),
            new UserScore(130000, 4, "Ava", true),
            new UserScore(120000, 3, "ThinMan", true),
            new UserScore(110000, 2, "Homer", true),
            new UserScore(100000, 1, "Atom", true),
        };

        for(UserScore u : userScores)
            insertRecord(u);

    }

    public void clear(){
        database.delete("ranking", "", null);

        insertDummyRecords();
    }
    public List<UserScore> getAll(){
        return getAll(DatabaseQueries.SQL_QUERY);
    }

    public List<UserScore> getAllByPoints(){
        return getAll(DatabaseQueries.SQL_QUERY_BY_POINTS);
    }

    public List<UserScore> getAllByErrors(){
        return getAll(DatabaseQueries.SQL_QUERY_BY_ERRORS);
    }

    private List<UserScore> getAll(String query){
        List<UserScore> scores = new ArrayList<>();

        cursor = database.rawQuery(query, null);

        if(cursor.moveToFirst()){
            int indexName = cursor.getColumnIndex("name");
            int indexScore = cursor.getColumnIndex("score");
            int indexErrors = cursor.getColumnIndex("errors");
            int indexIsBot = cursor.getColumnIndex("bot");

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

