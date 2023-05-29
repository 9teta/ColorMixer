package nine.teta.colormixer.setup;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

import nine.teta.colormixer.adapter.FavouriteColorItemModel;

public class FavouriteColorsSetup {

    public void fillWithDefaultColors(List<FavouriteColorItemModel> colors){
        colors.add(new FavouriteColorItemModel(Color.rgb(255,255,255), "FFFFFF", "white"));
        colors.add(new FavouriteColorItemModel(Color.rgb(255,0,0), "FF0000", "red"));
        colors.add(new FavouriteColorItemModel(Color.rgb(0,255,0), "00FF00", "computer green"));
        colors.add(new FavouriteColorItemModel(Color.rgb(0,0,255), "0000FF", "blue"));
        colors.add(new FavouriteColorItemModel(Color.rgb(255,255,0), "FFFF00", "yellow"));
        colors.add(new FavouriteColorItemModel(Color.rgb(255,0,255), "FF00FF", "magenta"));
        colors.add(new FavouriteColorItemModel(Color.rgb(0,255,255), "00FFFF", "cyan"));
        colors.add(new FavouriteColorItemModel(Color.rgb(0,0,0), "000000", "black"));
    }

    public List<FavouriteColorItemModel> getEmptyList(){
        return new ArrayList<FavouriteColorItemModel>();
    }

    public List<FavouriteColorItemModel> getDefaultList(){
        List<FavouriteColorItemModel> colors = new ArrayList<>();
        fillWithDefaultColors(colors);
        return colors;
    }






}
