package com.adamrubin.findsomefood.Room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

public abstract class PreviousOrderDatabase extends RoomDatabase {
    //singleton!
    private static PreviousOrderDatabase instance;

    //room creates sublass for this after builder below is called
    public abstract PreviousOrderDao orderDao();

    public static synchronized PreviousOrderDatabase getInstance(Context context) {
        if (instance == null) {
            //abstract class so use custom builder
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    PreviousOrderDatabase.class, "order_database")
                    //will recreate database on updates, crashes without this
                    .fallbackToDestructiveMigration()
                    //will call below callback to populate on creation
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    //can input data as it is created if you wish, below
    /**
     * Input data
     */
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        //called after database is created, but still need constructor thanks to static
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            //and use asynctask in this instance to create defaults
            //new PopulateDbAsyncTask(instance).execute();
        }
    };
}
