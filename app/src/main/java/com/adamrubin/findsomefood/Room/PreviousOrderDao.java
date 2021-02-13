package com.adamrubin.findsomefood.Room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PreviousOrderDao {
    @Insert
    void insert(PreviousOrder previousOrder);

    @Update
    void update(PreviousOrder previousOrder);

    @Delete
    void delete(PreviousOrder previousOrder);

    //why won't order appear here??
    //custom query allowed here! and many other defaults available
    @Query("DELETE FROM 'PreviousOrder'")
    void deleteAllOrders();

    //livedata allows room to autoupdate, and activities can watch for changes
    @Query("SELECT * FROM `PreviousOrder` ORDER BY id DESC")
    LiveData<List<PreviousOrder>> getAllOrders();
}