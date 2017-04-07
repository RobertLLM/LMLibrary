package robert.com.baselibrary;

import android.app.Activity;

import com.lngtop.energymanager.base.LSBaseFragmentActivity;

import java.util.Stack;

/**
 * Created by LL on 15/10/9.
 */
public class LSActivtyManager {

    private static Stack<Activity> activityStack;

    private static LSActivtyManager instance;


    public LSActivtyManager() {

    }

    public static LSActivtyManager getActivityManager(){

        if(instance == null){

            instance = new LSActivtyManager();

        }
        return instance;
    }

    public void  popActivityStack(Activity activity){

        if(null != activity){

            activity.finish();
            activityStack.remove(activity);
            activity= null;
        }
    }

    public void pushActivity2Stack(Activity activity){

        if(activityStack== null){

            activityStack= new Stack<Activity>();

        }

        activityStack.add(activity);

    }

    public LSBaseFragmentActivity getCurrentActivity(){

        LSBaseFragmentActivity activity = null;

        if(!activityStack.isEmpty()){

            activity= (LSBaseFragmentActivity) activityStack.lastElement();

        }

        return activity;

    }

    public void popAllActivityFromStack(){

        while(true){
            Activity activity = getCurrentActivity();
            if(activity == null){
                break;
            }

            popActivityStack(activity);
        }

    }
}
