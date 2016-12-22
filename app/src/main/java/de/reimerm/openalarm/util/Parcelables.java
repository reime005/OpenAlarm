package de.reimerm.openalarm.util;

import android.os.Parcel;
import android.os.Parcelable;

import de.reimerm.openalarm.alarm.Alarm;

/**
 * Created by mariu on 04.12.2016.
 */

public class Parcelables {
    public static byte[] toByteArray(Parcelable parcelable) {
        Parcel parcel = Parcel.obtain();

        parcelable.writeToParcel(parcel, 0);

        byte[] result = parcel.marshall();

        parcel.recycle();

        return (result);
    }

    public static Alarm toParcelableAlarm(byte[] bytes) {
        Alarm alarm = null;

        try {
            alarm = toParcelable(bytes, Alarm.CREATOR);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return alarm;
    }

    private static <T> T toParcelable(byte[] bytes,
                                      Parcelable.Creator<T> creator) throws Exception {
        final Parcel parcel = Parcel.obtain();

        parcel.unmarshall(bytes, 0, bytes.length);
        parcel.setDataPosition(0);

        final T result = creator.createFromParcel(parcel);

        parcel.recycle();

        return (result);
    }
}
