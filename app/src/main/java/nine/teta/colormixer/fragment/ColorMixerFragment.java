package nine.teta.colormixer.fragment;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.github.javafaker.Faker;

import nine.teta.colormixer.R;
import nine.teta.colormixer.adapter.FavouriteColorItemModel;
import nine.teta.colormixer.adapter.MainActivityViewPagerAdapter;

public class ColorMixerFragment extends Fragment {

    private static final String RED_VALUE_STATE = "redValue";
    private static final String GREEN_VALUE_STATE = "greenValue";
    private static final String BLUE_VALUE_STATE = "blueValue";

    private final MainActivityViewPagerAdapter viewPagerAdapter;
    private TextView redValueView;
    private TextView greenValueView;
    private TextView blueValueView;
    private int whiteColor = Color.rgb(255,255,255);
    private LayoutInflater inflater;
    private ViewGroup colorGrid;

    private View cell2; // red
    private View cell4; // magenta
    private View cell5; // white
    private View cell6; // yellow
    private View cell7; // blue
    private View cell8; // cyan
    private View cell9; // green
    private FavouriteColorFragmentViewModel viewModel;


    public ColorMixerFragment(MainActivityViewPagerAdapter viewPagerAdapter) {
        // Required empty public constructor
        this.viewPagerAdapter = viewPagerAdapter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(FavouriteColorFragmentViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        this.inflater = inflater;
        return inflater.inflate(R.layout.fragment_color_mixer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        System.out.println("onViewCreated");
        redValueView = (TextView) view.findViewById(R.id.redValue);
        redValueView.setOnClickListener(this::onRedValueClick);
        greenValueView = (TextView) view.findViewById(R.id.greenValue);
        greenValueView.setOnClickListener(this::onGreenValueClick);
        blueValueView = (TextView) view.findViewById(R.id.blueValue);
        blueValueView.setOnClickListener(this::onBlueValueClick);
        colorGrid = view.findViewById(R.id.colorGrid);
        colorGrid.setOnClickListener(this::onAnyColorClick);
        if (savedInstanceState != null){
            String redHexString = savedInstanceState.getString(RED_VALUE_STATE);
            redValueView.setText(redHexString);
            String greenHexString = savedInstanceState.getString(GREEN_VALUE_STATE);
            greenValueView.setText(greenHexString);
            String blueHexString = savedInstanceState.getString(BLUE_VALUE_STATE);
            blueValueView.setText(blueHexString);
            viewPagerAdapter.notifyDataSetChanged();

        }

        cell2 = view.findViewById(R.id.cell2);
        cell4 = view.findViewById(R.id.cell4);
        cell5 = view.findViewById(R.id.cell5);
        cell6 = view.findViewById(R.id.cell6);
        cell7 = view.findViewById(R.id.cell7);
        cell8 = view.findViewById(R.id.cell8);
        cell9 = view.findViewById(R.id.cell9);

        var saveButton = (Button)view.findViewById(R.id.colorMixerSaveColorButton);
        saveButton.setOnClickListener(this::onSaveColorClicked);
        updateColors();
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(RED_VALUE_STATE, redValueView.getText().toString());
        outState.putString(GREEN_VALUE_STATE, greenValueView.getText().toString());
        outState.putString(BLUE_VALUE_STATE, blueValueView.getText().toString());
    }



    private void onRedValueClick(View v){
        View popupView = inflater.inflate(R.layout.popup_select_number, null);
        PopupWindow popupWindow = openPopupWindow(popupView);
        setupListeners(popupView, popupWindow, redValueView);
    }
    private void onGreenValueClick(View v){
        View popupView = inflater.inflate(R.layout.popup_select_number, null);
        PopupWindow popupWindow = openPopupWindow(popupView);
        setupListeners(popupView, popupWindow, greenValueView);
    }
    private void onBlueValueClick(View v){
        View popupView = inflater.inflate(R.layout.popup_select_number, null);
        PopupWindow popupWindow = openPopupWindow(popupView);
        setupListeners(popupView, popupWindow, blueValueView);
    }

    private void onAnyColorClick(View v){
        View popupView = inflater.inflate(R.layout.popup_show_color, null);
        popupView.setBackgroundColor(whiteColor);
        String colorHexCode = "#"
                + redValueView.getText()
                + greenValueView.getText()
                + blueValueView.getText();
        var topText = (TextView) popupView.findViewById(R.id.topText);
        var bottomText = (TextView) popupView.findViewById(R.id.bottomText);
        topText.setText(colorHexCode);
        bottomText.setText(colorHexCode);
        PopupWindow popupWindow = openPopupWindow(popupView);
        popupView.setOnTouchListener((view, event) -> {
            popupWindow.dismiss();
            return true;
        });
    }

    private void onSaveColorClicked(View view) {
        AlertDialog alertDialog = saveColorDialog();
        alertDialog.show();
    }


    private PopupWindow openPopupWindow(View popupView) {
        var wid = LinearLayout.LayoutParams.WRAP_CONTENT;
        var high = LinearLayout.LayoutParams.WRAP_CONTENT;
        var focus = true;
        PopupWindow popupWindow = new PopupWindow(popupView, wid, high, focus);
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
        return popupWindow;
    }

    private void setupListeners(View popupView, PopupWindow popupWindow, TextView colorValue) {
        popupView.setOnTouchListener((view, event) -> {
            popupWindow.dismiss();
            return true;
        });
        var textView = (TextView) popupView.findViewById(R.id.popup_text);

        StringBuilder number = new StringBuilder();
        View.OnClickListener onButton = v -> {
            Button button = (Button) v;
            var buttonText = button.getText();

            if (number.length() < 1){
                number.append(buttonText);
                textView.setText(number.toString());
            } else if (number.length() == 1){
                number.append(buttonText);
                colorValue.setText(number.toString());
                number.setLength(0);
                popupWindow.dismiss();
                updateColors();
            } else {
                number.setLength(0);
                popupWindow.dismiss();
            }

        };

        popupView.findViewById(R.id.popup_button_0).setOnClickListener(onButton);
        popupView.findViewById(R.id.popup_button_1).setOnClickListener(onButton);
        popupView.findViewById(R.id.popup_button_2).setOnClickListener(onButton);
        popupView.findViewById(R.id.popup_button_3).setOnClickListener(onButton);
        popupView.findViewById(R.id.popup_button_4).setOnClickListener(onButton);
        popupView.findViewById(R.id.popup_button_5).setOnClickListener(onButton);
        popupView.findViewById(R.id.popup_button_6).setOnClickListener(onButton);
        popupView.findViewById(R.id.popup_button_7).setOnClickListener(onButton);
        popupView.findViewById(R.id.popup_button_8).setOnClickListener(onButton);
        popupView.findViewById(R.id.popup_button_9).setOnClickListener(onButton);
        popupView.findViewById(R.id.popup_button_A).setOnClickListener(onButton);
        popupView.findViewById(R.id.popup_button_B).setOnClickListener(onButton);
        popupView.findViewById(R.id.popup_button_C).setOnClickListener(onButton);
        popupView.findViewById(R.id.popup_button_D).setOnClickListener(onButton);
        popupView.findViewById(R.id.popup_button_E).setOnClickListener(onButton);
        popupView.findViewById(R.id.popup_button_F).setOnClickListener(onButton);
    }


    private void updateColors() {
        CharSequence redNumber = redValueView.getText();
        CharSequence greenNumber = greenValueView.getText();
        CharSequence blueNumber = blueValueView.getText();
        int redHex = Integer.parseInt(redNumber.toString(), 16);
        int greenHex = Integer.parseInt(greenNumber.toString(), 16);
        int blueHex = Integer.parseInt(blueNumber.toString(), 16);
        var redColor = Color.rgb(redHex, 0, 0);
        var greenColor = Color.rgb(0, greenHex, 0);
        var blueColor = Color.rgb(0, 0, blueHex);
        cell2.setBackgroundColor(redColor);
        cell7.setBackgroundColor(blueColor);
        cell9.setBackgroundColor(greenColor);

        var magentaColor = Color.rgb(redHex, 0, blueHex);
        var yellowColor = Color.rgb(redHex, greenHex, 0);
        var cyanColor = Color.rgb(0, greenHex, blueHex);
        cell4.setBackgroundColor(magentaColor);
        cell6.setBackgroundColor(yellowColor);
        cell8.setBackgroundColor(cyanColor);

        whiteColor = Color.rgb(redHex, greenHex, blueHex);
        cell5.setBackgroundColor(whiteColor);
    }

    private AlertDialog saveColorDialog(){
        EditText editText = new EditText(getActivity());
        editText.setInputType(InputType.TYPE_CLASS_TEXT);
        editText.setText(getRandomName());
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .setTitle("Save the color")
                .setMessage("Choose name for the color")
                .setView(editText)
                .setPositiveButton(
                        "Ok",
                        (dialog, id) -> {
                            viewModel.insertColor(
                                    new FavouriteColorItemModel(
                                            whiteColor,
                                            "" + redValueView.getText() + greenValueView.getText() + blueValueView.getText(),
                                            editText.getText().toString()));
                        })
                .setNegativeButton(
                        "Cancel",
                        (dialog, id) -> {
                            dialog.cancel();
                        })


                .create();
        return alertDialog;
    }

    public String getRandomName(){
        Faker faker = Faker.instance();
        double r = Math.random();
        if (r < 0.25d) return faker.cat().name();
        else if (r < 0.5d) return faker.dog().name();
        else if (r < 0.75d) return faker.food().dish();
        else if (r < 1.0d) return faker.pokemon().name();
        else return "";

    }

}