package robert.com.baselibrary;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import robert.com.baselibrary.base.LSPageLoadingView;

import java.util.Timer;
import java.util.TimerTask;

public class LTProgressHUD extends Dialog {

    private Context context ;

    public Boolean canClose = true;



    public LTProgressHUD(Context context) {

        super(context, R.style.ProgressHUD);
        this.context = context  ;
    }
    @SuppressWarnings("unused")
    public LTProgressHUD(Context context, int theme) {
        super(context, theme);
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        LSPageLoadingView imageView = (LSPageLoadingView) findViewById(R.id.spinnerImageView);
        imageView.setVisibility( View.VISIBLE) ;
    }

    public void setMessage(CharSequence message) {
        if (message != null && message.length() > 0) {
            findViewById(R.id.message).setVisibility(View.VISIBLE);
            TextView txt = (TextView) findViewById(R.id.message);
            txt.setText(message);
            txt.invalidate();
        }
    }

//    public void setView(Context context, CharSequence message,
//                        @SuppressWarnings("UnusedParameters")boolean indeterminate, boolean cancelable,
//                        OnCancelListener cancelListener) {
//        setTitle("");
//        LayoutInflater factory = LayoutInflater.from(context);
//        @SuppressLint("InflateParams") View view = factory.inflate(R.layout.progress_hud, null);
//        setContentView(view);
//
//        if (message == null || message.length() == 0) {
//            view.findViewById(R.id.message).setVisibility(View.GONE);
//        } else {
//            TextView txt = (TextView) view.findViewById(R.id.message);
//            txt.setText(message);
//        }
//        setCancelable(cancelable);
//        setOnCancelListener(cancelListener);
//        getWindow().getAttributes().gravity = Gravity.CENTER;
//        WindowManager.LayoutParams lp = getWindow().getAttributes();
//        lp.dimAmount = 0.2f;
//        getWindow().setAttributes(lp);
//        setCancelable(cancelable);
//        if (!canClose)
//            startTimer();
//        show();
//    }


    public void setView(Context context, CharSequence message,
                        @SuppressWarnings("UnusedParameters")boolean indeterminate, boolean cancelable,
                        OnCancelListener cancelListener) {
        LayoutInflater factory = LayoutInflater.from(context);
        @SuppressLint("InflateParams") View view = factory.inflate(R.layout.progress_hud_new, null);
        setContentView(view);

        setCancelable(cancelable);
        setOnCancelListener(cancelListener);
        getWindow().getAttributes().gravity = Gravity.CENTER;
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.dimAmount = 0.2f;
        getWindow().setAttributes(lp);
        setCancelable(cancelable);
        if (!canClose)
            startTimer();
        show();
    }


    private void startTimer() {
        Timer mTimer = new Timer();
        TimerTask mTimerTask = new TimerTask() {
            @Override
            public void run() {
                closeProgress();
            }
        };
        mTimer.schedule(mTimerTask, 30000);
    }

    private void closeProgress() {

        this.dismiss();
    }
}
