package nine.teta.colormixer.fragment;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import nine.teta.colormixer.adapter.FavouriteColorItemModel;
import nine.teta.colormixer.jpa.AppRepository;

public class FavouriteColorFragmentViewModel extends AndroidViewModel {

    private AppRepository repo;
    private final LiveData<List<FavouriteColorItemModel>> allColors;

    public FavouriteColorFragmentViewModel (Application application) {
        super(application);
        repo = new AppRepository(application);
        allColors = repo.getAllColors();
    }

    public LiveData<List<FavouriteColorItemModel>> getAllColors(){
        return allColors;
    }

    public void insertColor(FavouriteColorItemModel colorModel){
        repo.insertColor(colorModel);
    }

    public void deleteColor(FavouriteColorItemModel colorModel){
        repo.deleteColor(colorModel);
    }

    public void deleteColorByHex(String colorHex){
        repo.deleteColorByHex(colorHex);
    }

    public Single<List<FavouriteColorItemModel>> getFutureAllColors() {
        return repo.getFutureAllColors();
    }
}
