package de.reimerm.openalarm.util;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import de.reimerm.openalarm.R;
import de.reimerm.openalarm.alarm.Alarm;

/**
 * Created by mariu on 10.12.2016.
 */

public class ItemViewHolder extends RecyclerView.ViewHolder {

    private final CheckBox checkBox;
    private final TextView alarmTimeView;
    private Alarm alarm;

    public ItemViewHolder(View itemView) {
        super(itemView);

        checkBox = (CheckBox) itemView.findViewById(R.id.checkBox_alarm_active);
        alarmTimeView = (TextView) itemView.findViewById(R.id.textView_alarm_time);
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public TextView getAlarmTimeView() {
        return alarmTimeView;
    }

    public Alarm getAlarm() {
        return alarm;
    }

    public void setAlarm(Alarm alarm) {
        this.alarm = alarm;
    }
}
