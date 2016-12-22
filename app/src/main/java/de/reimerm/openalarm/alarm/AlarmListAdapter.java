package de.reimerm.openalarm.alarm;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.reimerm.openalarm.R;
import de.reimerm.openalarm.fragments.AlarmFragment;
import de.reimerm.openalarm.util.ItemViewHolder;

/**
 * Created by mariu on 06.12.2016.
 */

public class AlarmListAdapter extends RecyclerView.Adapter<ItemViewHolder> {

    private AlarmFragment alarmFragment;
    private List<Alarm> alarmList = new ArrayList<>();
    private AnimatedVectorDrawable checkedToUnchecked;
    private AnimatedVectorDrawable uncheckedToChecked;

    public AlarmListAdapter(AlarmFragment alarmFragment) {
        this.alarmFragment = alarmFragment;
        checkedToUnchecked = (AnimatedVectorDrawable) alarmFragment.getContext().getDrawable(R.drawable.avd_pathmorph_crosstick_tick_to_cross);
        uncheckedToChecked = (AnimatedVectorDrawable) alarmFragment.getContext().getDrawable(R.drawable.avd_pathmorph_crosstick_cross_to_tick);
    }

    public void setAlarmList(List<Alarm> alarmList) {
        this.alarmList = alarmList;
    }

    public Object getItem(int position) {
        return alarmList.get(position);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.alarm_list_element, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        final Alarm alarm = (Alarm) getItem(position);

        final CheckBox checkBox = holder.getCheckBox();
        checkBox.setChecked(alarm.isActive());
        checkBox.setTag(position);
        checkBox.setOnClickListener(alarmFragment);

        Log.d("adapter", "alarm is " + alarm.isActive() + ", " + alarm.getAlarmTimeStringParcelable());

        final TextView alarmTimeView = holder.getAlarmTimeView();
        alarmTimeView.setText(alarm.getAlarmTimeString());
        alarmTimeView.setOnClickListener(alarmFragment);
        alarmTimeView.setTag(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return alarmList.size();
    }
}
