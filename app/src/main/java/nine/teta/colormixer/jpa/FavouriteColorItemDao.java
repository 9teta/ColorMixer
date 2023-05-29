package nine.teta.colormixer.jpa;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Ignore;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import nine.teta.colormixer.adapter.FavouriteColorItemModel;

@Dao
public interface FavouriteColorItemDao {

    @Query("select * from favourite_colors")
    LiveData<List<FavouriteColorItemModel>> getAllColors();

    @Query("select * from favourite_colors where name like :name")
    FavouriteColorItemModel getColorFromName(String name);

    //  The selected on conflict strategy ignores a new word if it's exactly the same as one already in the list.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertColor(FavouriteColorItemModel color);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<FavouriteColorItemModel> colors);

    @Delete
    void deleteColor(FavouriteColorItemModel color);

    @Query("delete from favourite_colors where hexCode = :hexCode ")
    void deleteColorByHex(String hexCode);

    @Query("delete from favourite_colors")
    void deleteAll();

    @Query("select * from favourite_colors")
    Single<List<FavouriteColorItemModel>> getFutureAllColors();
}
