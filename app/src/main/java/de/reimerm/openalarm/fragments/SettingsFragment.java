package de.reimerm.openalarm.fragments;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.reimerm.openalarm.R;

/**
 * Created by mariu on 09.12.2016.
 */

public class SettingsFragment extends Fragment {

    private TabLayout tabLayout;
    private AnimatedVectorDrawable animation;
    private Drawable deactivated;
    private int position;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        if (getActivity() != null) {
            tabLayout = (TabLayout) getActivity().findViewById(R.id.tabs);
        }

        deactivated = getResources().getDrawable(R.drawable.vd_pathmorph_arrowoverflow_overflow_deactivated);
        animation = (AnimatedVectorDrawable) getResources().getDrawable(R.drawable.avd_pathmorph_arrowoverflow_overflow_to_arrow);

        tabLayout.getTabAt(position).setIcon(deactivated);

        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (tabLayout == null || deactivated == null || animation == null) {
            return;
        }

        if (tabLayout.getTabAt(position) == null) {
            return;
        }

        if (isVisibleToUser) {
            // start animation
            tabLayout.getTabAt(position).setIcon(animation);
            animation.start();
        } else {
            // deactivated icon
            animation.stop();
            tabLayout.getTabAt(position).setIcon(deactivated);
        }
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
