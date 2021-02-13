package com.adamrubin.findsomefood.Room;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class PreviousOrderViewModel extends AndroidViewModel {
    private PreviousOrderRepository repository;
    private LiveData<List<PreviousOrder>> allOrders;

    //application is passed, not activity context - because this will outlive any activity's life
    public PreviousOrderViewModel(@NonNull Application application) {
        super(application);
        repository = new PreviousOrderRepository(application);
        allOrders = repository.getAllOrders();
    }

    //repository is private, so continue the grabber method chain so activity can get at the data
    public void insert(PreviousOrder order) {
        repository.insert(order);
    }

    public void update(PreviousOrder order) {
        repository.update(order);
    }

    public void delete(PreviousOrder order) {
        repository.delete(order);
    }

    public void deleteAllOrders() {
        repository.deleteAllOrders();
    }

    public LiveData<List<PreviousOrder>> getAllOrders() {
        return allOrders;
        }
}
