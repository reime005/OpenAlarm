package de.reimerm.openalarm.alarm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import de.reimerm.openalarm.R;
import de.reimerm.openalarm.db.DatabaseManager;
import de.reimerm.openalarm.util.Parcelables;

/**
 * Created by mariu on 04.12.2016.
 */

public class AlarmAlertActivity extends Activity {

    private static final String TAG = "AlarmAlertActivity";
    private Alarm alarm;
    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;
    private boolean alarmActive;
    private ClickListener clickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        setContentView(R.layout.alarm_alert);

        DatabaseManager.init(getBaseContext());

        clickListener = new ClickListener();
        findViewById(R.id.button_deactivate).setOnClickListener(clickListener);

        final Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            alarm = Parcelables.toParcelableAlarm(bundle.getByteArray(Alarm.TAG));
        }

        if (alarm == null) {
            Log.d(TAG, "Alarm is null!");
            return;
        }

        this.setTitle(alarm.getName());

        final TelephonyManager telephonyManager = (TelephonyManager) this
                .getSystemService(Context.TELEPHONY_SERVICE);

        final CallStateListener callStateListener = new CallStateListener();

        telephonyManager.listen(callStateListener, CallStateListener.LISTEN_CALL_STATE);

        startAlarm();
    }

    private void startAlarm() {
        if (alarm != null && !alarm.getTonePath().isEmpty()) {
            Log.d(TAG, "startAlarm(): " + alarm.getAlarmTimeStringParcelable());
            mediaPlayer = new MediaPlayer();

            if (alarm.shouldVibrate()) {
                vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                long[] pattern = {1000, 200, 200, 200};
                vibrator.vibrate(pattern, 0);
            }

            try {
                mediaPlayer.setVolume(1.0f, 1.0f);
                mediaPlayer.setDataSource(this, Uri.parse(alarm.getTonePath()));
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
                mediaPlayer.setLooping(true);
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (Exception e) {
            } finally {
                mediaPlayer.release();
                alarmActive = false;
            }
        }
    }

    private void stopAlarm() {
        if (alarm != null) {
            Log.d(TAG, "stop alarm");
            alarm.setActive(false);
            DatabaseManager.update(alarm);

            try {
                vibrator.cancel();
            } catch (Exception e) {

            }
            try {
                mediaPlayer.stop();
            } catch (Exception e) {

            }
            try {
                mediaPlayer.release();
            } catch (Exception e) {

            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        processIntent(intent);
    }

    private void processIntent(Intent intent){
        Log.d(TAG, intent.toString());
    }

    @Override
    protected void onResume() {
        super.onResume();
        alarmActive = true;
    }

    @Override
    public void onBackPressed() {
        if (!alarmActive) {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        stopAlarm();
        super.onDestroy();
    }

    public class ClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (!alarmActive || alarm == null) {
                return;
            }

            v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
            stopAlarm();
            finish();
        }
    }

    private class CallStateListener extends PhoneStateListener {

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING:
                    Log.d(getClass().getSimpleName(), "Incoming call: "
                            + incomingNumber);
                    try {
                        mediaPlayer.pause();
                    } catch (Exception e) {

                    }
                    break;
                case TelephonyManager.CALL_STATE_IDLE:
                    Log.d(getClass().getSimpleName(), "Call State Idle");
                    try {
                        mediaPlayer.start();
                    } catch (Exception e) {

                    }
                    break;
            }
            super.onCallStateChanged(state, incomingNumber);
        }
    }
}
