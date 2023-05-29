package nine.teta.colormixer.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.github.javafaker.Faker;

import java.time.LocalDateTime;
import java.time.Year;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.Locale;

import io.reactivex.rxjava3.core.Single;
import nine.teta.colormixer.R;
import nine.teta.colormixer.adapter.FavouriteColorAdapter;
import nine.teta.colormixer.adapter.FavouriteColorItemModel;
import nine.teta.colormixer.setup.FavouriteColorsSetup;

public class FavouriteColorsFragment extends Fragment {

    private FavouriteColorAdapter adapter;
    private FavouriteColorFragmentViewModel viewModel;
    private GridView gridView;


    public FavouriteColorsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(FavouriteColorFragmentViewModel.class);
        viewModel.getAllColors().observe(this, this::onColorsListChange);
    }

    private void onColorsListChange(List<FavouriteColorItemModel> allColors){
        System.out.println("ON COLOR LIST CHANGE");
        adapter = new FavouriteColorAdapter(getContext(), allColors);
        adapter.setModelList(allColors);
        gridView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
//        if (gridView != null) gridView.invalidateViews();
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_favourite_colors, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        gridView = (GridView) view.findViewById(R.id.favouritesGrid);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(this::onGridItemClicked);
        gridView.setOnItemLongClickListener(this::onGridItemLongClicked);
    }




    public void refresh(FavouriteColorAdapter adapter, GridView grid){
        adapter.notifyDataSetChanged();
        grid.invalidateViews();
    }

    /**
     Params:
     parent – The AdapterView where the click happened.
     view – The view within the AdapterView that was clicked (this will be a view provided by the adapter)
     position – The position of the view in the adapter.
     id – The row id of the item that was clicked.
     */
    private void onGridItemClicked(AdapterView<?> parent, View view, int position, long id){
        // TODO: Not a single point of truth
        LinearLayout colorView = (LinearLayout) view.findViewById(R.id.favouriteItemColor);
        TextView colorCodeView = (TextView) view.findViewById(R.id.favouriteItemCode);
        Drawable drawableColor = colorView.getBackground();
        int color = drawableColor instanceof ColorDrawable ? ( (ColorDrawable)drawableColor ).getColor() : 0;
        String colorCode = colorCodeView.getText().toString();

        LayoutInflater inflater = this.getLayoutInflater();
        View popupView = inflater.inflate(R.layout.popup_show_color, null);
        popupView.setBackgroundColor(color);
        String colorHexCode = "#" + colorCode;
        var topText = (TextView) popupView.findViewById(R.id.topText);
        var bottomText = (TextView) popupView.findViewById(R.id.bottomText);
        topText.setText(colorHexCode);
        bottomText.setText(colorHexCode);
        PopupWindow popupWindow = openPopupWindow(popupView);
        popupView.setOnTouchListener((v, event) -> {
            popupWindow.dismiss();
            return true;
        });
    }

    private boolean onGridItemLongClicked(AdapterView<?> adapterView, View view, int i, long l) {
        LinearLayout colorView = (LinearLayout) view.findViewById(R.id.favouriteItemColor);
        Drawable drawableColor = colorView.getBackground();
        int color = drawableColor instanceof ColorDrawable ? ( (ColorDrawable)drawableColor ).getColor() : 0;
        TextView colorCodeView = (TextView) view.findViewById(R.id.favouriteItemCode);
        String colorCode = colorCodeView.getText().toString();
        TextView colorNameView = (TextView) view.findViewById(R.id.favouriteItemName);
        String colorName = colorNameView.getText().toString().toUpperCase(Locale.ROOT);
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .setTitle("Delete this color?")
                .setMessage(colorName + " (#" + colorCode + ")")
                .setPositiveButton(
                        "Delete",
                        (dialog, id) -> {
                            viewModel.deleteColorByHex(colorCode);
                        })
                .setNegativeButton(
                        "Cancel",
                        (dialog, id) -> {
                            dialog.cancel();
                        })
                .setIcon(drawableColor)
                .create();
        alertDialog.show();
        return true;
    }

    private PopupWindow openPopupWindow(View popupView) {
        var wid = LinearLayout.LayoutParams.WRAP_CONTENT;
        var high = LinearLayout.LayoutParams.WRAP_CONTENT;
        var focus = true;
        PopupWindow popupWindow = new PopupWindow(popupView, wid, high, focus);
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
        return popupWindow;
    }
}