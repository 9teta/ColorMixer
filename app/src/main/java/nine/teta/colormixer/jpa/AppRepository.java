package nine.teta.colormixer.jpa;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import nine.teta.colormixer.adapter.FavouriteColorItemModel;

public class AppRepository {

    private FavouriteColorItemDao colorDao;
    private LiveData<List<FavouriteColorItemModel>> allColors;

    public AppRepository(Application application){
        ColorDatabase db = ColorDatabase.getDatabase(application);
        colorDao = db.favouriteColorItemDao();
        allColors = colorDao.getAllColors();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<FavouriteColorItemModel>> getAllColors() {
        return allColors;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insertColor(FavouriteColorItemModel color) {
        ColorDatabase.databaseWriteExecutor.execute(() -> {
            colorDao.insertColor(color);
        });
    }

    public void deleteColor(FavouriteColorItemModel color) {
        ColorDatabase.databaseWriteExecutor.execute(() -> {
            colorDao.deleteColor(color);
        });
    }

    public Single<List<FavouriteColorItemModel>> getFutureAllColors() {
        return colorDao.getFutureAllColors();
    }

    public void deleteColorByHex(String colorHex) {
        ColorDatabase.databaseWriteExecutor.execute(() -> {
            colorDao.deleteColorByHex(colorHex);
        });

    }
}
