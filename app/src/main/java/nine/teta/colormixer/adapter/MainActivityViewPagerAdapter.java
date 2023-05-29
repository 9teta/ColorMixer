package nine.teta.colormixer.adapter;

import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivityViewPagerAdapter extends FragmentStatePagerAdapter {

    private final List<Fragment> fragments = new ArrayList<>();
    private final List<String> fragmentTitle = new ArrayList<>();

    public MainActivityViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public void add(Fragment fragment, String title) {
        fragments.add(fragment);
        fragmentTitle.add(title);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitle.get(position);
    }

}
