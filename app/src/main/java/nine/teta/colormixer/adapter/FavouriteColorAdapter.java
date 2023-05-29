package nine.teta.colormixer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Collection;
import java.util.List;

import nine.teta.colormixer.R;

public class FavouriteColorAdapter extends ArrayAdapter<FavouriteColorItemModel> {

    private List<FavouriteColorItemModel> modelList;

    /// con
    public FavouriteColorAdapter(
            @NonNull Context context) {

        super(context, 0);
    }
    public FavouriteColorAdapter(
            @NonNull Context context,
            @NonNull List<FavouriteColorItemModel> modelList) {

        super(context, 0, modelList);
        this.modelList = modelList;
    }

    // get set
    public List<FavouriteColorItemModel> getModelList() {
        return modelList;
    }

    public void setModelList(List<FavouriteColorItemModel> modelList) {
        this.modelList = modelList;
    }

    @Override
    public void addAll(@NonNull Collection<? extends FavouriteColorItemModel> collection) {
        modelList.addAll(collection);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(
            int position,
            @Nullable View convertView,
            @NonNull ViewGroup parent) {

        View gridItemView = convertView;
        if (gridItemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            gridItemView = LayoutInflater.from(getContext()).inflate(R.layout.item_grid_favourite_colors, parent, false);
        }

        FavouriteColorItemModel colorModel = getItem(position);
        LinearLayout color = (LinearLayout)gridItemView.findViewById(R.id.favouriteItemColor);
        TextView code = (TextView)gridItemView.findViewById(R.id.favouriteItemCode);
        TextView name = (TextView)gridItemView.findViewById(R.id.favouriteItemName);

        color.setBackgroundColor(colorModel.getColor());
        code.setText(colorModel.getHexCode());
        name.setText(colorModel.getName());
        return gridItemView;
    }
}
