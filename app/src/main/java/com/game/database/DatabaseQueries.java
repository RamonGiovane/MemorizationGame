package com.game.database;
final class DatabaseQueries {
    static final String DATABASE_NAME = "memorization_game";

    static final int DATABASE_ACCESS = 0;

     static final String SQL_STRUCT =
            "CREATE TABLE IF NOT EXISTS ranking " +
                    "(id_ INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT NOT NULL, " +
                    "score INTEGER, " +
                    "errors INTEGER," +
                    "bot BOOLEAN);";

    static final String SQL_INSERT =
            "INSERT INTO ranking (name, score, errors, bot) " +
                    "values ('%s', '%d', '%d', '%d')";

    static final String SQL_QUERY =
            "SELECT * from ranking";

    static final String SQL_CHECK_TABLE =
            "SELECT count(*) FROM ranking";

}
