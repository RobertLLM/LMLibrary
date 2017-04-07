package robert.com.baselibrary;

import android.app.Activity;

import java.util.Stack;

/**
 * Created by LL on 15/10/9.
 */
public class LTActivtyManager {

    private static Stack<Activity> activityStack;

    private static LTActivtyManager instance;


    public LTActivtyManager() {

    }

    public static LTActivtyManager getActivityManager() {

        if (instance == null) {

            instance = new LTActivtyManager();

        }
        return instance;
    }

    public void popActivityStack(Activity activity) {

        if (null != activity) {

            activity.finish();
            activityStack.remove(activity);
            activity = null;
        }
    }

    public void pushActivity2Stack(Activity activity) {

        if (activityStack == null) {

            activityStack = new Stack<Activity>();

        }

        activityStack.add(activity);

    }

    public Activity getCurrentActivity() {

        Activity activity = null;

        if (activityStack != null && !activityStack.isEmpty()) {

            activity = activityStack.lastElement();

        }

        return activity;

    }

    public void popAllActivityFromStack() {

        while (true) {
            Activity activity = getCurrentActivity();
            if (activity == null) {
                break;
            }

            popActivityStack(activity);
        }

    }
}
