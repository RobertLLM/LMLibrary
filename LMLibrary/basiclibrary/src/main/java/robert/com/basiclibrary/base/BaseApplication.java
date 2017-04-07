package robert.com.basiclibrary.base;

import android.app.Application;

import robert.com.basiclibrary.R;
import robert.com.basiclibrary.util.DataKeeper;
import robert.com.basiclibrary.util.ImageLoaderUtil;
import robert.com.basiclibrary.util.Log;
import robert.com.basiclibrary.util.SettingUtil;

/**基础Application
 * @author Lemon
 * @see #init
 * @use extends BaseApplication 或 在你的Application的onCreate方法中BaseApplication.init(this);
 */
public class BaseApplication extends Application {
	private static final String TAG = "BaseApplication";

	public BaseApplication() {
	}
	
	private static Application instance;
	public static Application getInstance() {
		return instance;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "项目启动 >>>>>>>>>>>>>>>>>>>> \n\n");
		
		init(this);
	}

	/**初始化方法
	 * @param application
	 * @must 调用init方法且只能调用一次，如果extends BaseApplication会自动调用
	 */
	public static void init(Application application) {
		instance = application;
		if (instance == null) {
			Log.e(TAG, "\n\n\n\n\n !!!!!! 调用BaseApplication中的init方法，instance不能为null !!!" +
					"\n <<<<<< init  instance == null ！！！ >>>>>>>> \n\n\n\n");
		}
		
		DataKeeper.init(instance);
		SettingUtil.init(instance);
		ImageLoaderUtil.init(instance);
	}

	/**获取应用名
	 * @return
	 */
	public String getAppName() {
		return getResources().getString(R.string.app_name);
	}
	/**获取应用版本名(显示给用户看的)
	 * @return
	 */
	public String getAppVersion() {
		return getResources().getString(R.string.app_version);
	}


}
