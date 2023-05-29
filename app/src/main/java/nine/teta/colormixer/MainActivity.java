package nine.teta.colormixer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import nine.teta.colormixer.adapter.MainActivityViewPagerAdapter;
import nine.teta.colormixer.fragment.ColorCanvasFragment;
import nine.teta.colormixer.fragment.ColorMixerFragment;
import nine.teta.colormixer.fragment.FavouriteColorsFragment;

public class MainActivity extends AppCompatActivity {

    private MainActivityViewPagerAdapter viewPagerAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager)findViewById(R.id.viewpager);
        // setting up the adapter
        viewPagerAdapter = new MainActivityViewPagerAdapter(getSupportFragmentManager());

        // add the fragments
        viewPagerAdapter.add(new ColorMixerFragment(viewPagerAdapter), "Mixer");
        viewPagerAdapter.add(new FavouriteColorsFragment(), "Colors");
        viewPagerAdapter.add(new ColorCanvasFragment(), "Canvas");

        // Set the adapter
        viewPager.setAdapter(viewPagerAdapter);

        // The Page (fragment) titles will be displayed in the
        // tabLayout hence we need to  set the page viewer
        // we use the setupWithViewPager().
        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }


}