package de.reimerm.openalarm;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import de.reimerm.openalarm.fragments.AlarmFragment;
import de.reimerm.openalarm.fragments.SettingsFragment;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            final AlarmFragment alarmFragment = new AlarmFragment();
            alarmFragment.setPosition(position);
            return alarmFragment;
        } else {
            final SettingsFragment settingsFragment = new SettingsFragment();
            settingsFragment.setPosition(position);
            return settingsFragment;
        }
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // null == No title
        return null;
    }
}
