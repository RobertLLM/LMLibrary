package robert.com.baselibrary;

/**
 * Created by liminglu on 2017/3/29.
 */

public class C {
    public static final String LOG_TAG = "LTLog";
    public static final Boolean API_TEST = true;
    final public static String NETWORK_APPID = "Android";
    final public static String ACTION_DEVICE_CONNECTED = "com.device.connected";
    final public static String ACTION_DEVICE_DISCONNECTED = "com.device.disconnected";
    final public static String ACTION_DEVICE_UNREACH = "com.device.unreach";
    public static final int VERSION_RESULT_CODE_DIALOG = 0x00a;
    public static final int DOWNLOAD_OFFLINE_RESULT_CODE_DIALOG = 0x00a;
    public static final int DOWNLOAD_OFFLINE_WIFI_RESULT_CODE_DIALOG = 0x02a;
    public static final int COMPANY_RESULT_CODE_DIALOG = 0x00b;
    public static final int ORDER_RESULT_CODE_DIALOG = 0x00c;
    public static final int CALL_PHONE_RESULT_CODE_DIALOG = 0x00d;
    public static final int VERSION_MAIN_RESULT_CODE_DIALOG = 0x00e;
    public static final int MONTHPAY_RESULT_CODE_DIALOG = 0x00f;
    public static final int SAVE_SETTING_CODE_DIALOG = 0x01c;
    public static final int UPDATE_DATA_CODE_DIALOG = 0x01d;
    public static final int SURE_ORDER_CODE_DIALOG = 0x01a;
    public static final int ISSURE_ORDER_CODE_DIALOG = 0x01b;
    public static final int CONTRAL_RESULT = 0x01E;
    public static final int CONTRAL_REQUEST= 0x01F;
    public static final int CAMERA_REQUEST_CODE = 0x001;
    final public static String MAP_SIZE = "500" ;
    public static final int Duration=300;

    public static String OFFLINE_DATA_PATH ;
    public static String OFFLINE_TEMP_DATA_PATH ;
    final public static String OFFLINE_DATA_NATIVE_NAME="httpCache.zip" ;

    public static interface AnimtionType {
        int NONE = -1;

        int ZOOM_IN = 1;//由小到大 由浅到深  显现

        int ZOOM_OUT = 2;//由大到小 由浅到深 退出

        int OUT_FROM_RIGHT = 3;//向右侧由深到浅退出

        int OUT_FROM_LEFT = 4;//向左侧由深到浅退出

        int OUT_FROM_TOP = 5;//向下侧由深到浅退出

        int OUT_FROM_BUTTOM = 6;//从下侧由深到浅退出

        int IN_FROM_RIGHT = 7;//向右侧由浅到深显现

        int IN_FROM_LEFT = 8;//从左侧由浅到深显现

        int IN_FROM_TOP = 9;//从上侧由浅到深显现

        int IN_FROM_BUTTOM = 10;//从下侧由浅到深显现

        int IN_ALPHA = 11;//由浅到深

        int OUT_ALPHA = 12;//由深到浅
    }

    public static String INTENT_JSON = "intent_json";
    public static String INTENT_JSON_PAYDATA = "intent_json_paydata";
    public static String INTENT_ORDER_ID = "orderId";
    public static String INTENT_CURORDER = "curOrder";
    public static int INTENT_REQUEST_CODE = 0X001;
    public static int INTENT_RESULT_CODE = 0X002;
    public static String INTENT_ORDER_JOURNEY= "taskNum";
    public static String INTENT_ORDER_OFFLINE_NOTBEGAN= "offline_not_start_task";
    public static String INTENT_ORDER_DETAIL= "detaildata";
    public static String INTENT_ORDER_REMARK= "remark";
    public static String INTENT_CONTRAL= "control";
    public static String INTENT_CONTRALID= "controlid";
    public static final String CACHE_SERVER_HOST = "http://demoapi.lngtop.com";
    //    public static final String CACHE_SERVER_HOST = "http://58.210.161.178:8088/lngiot-api";
    public static final String CACHE_SERVER_TOKEN = "adfcba5e43526bfe67c20d7a2bad1407e21065d5";
//    public static final String CACHE_SERVER_TOKEN = "5a8df8d52c16a1d46de2e59d116eb9077143b620";

    public static final long OFFLINECURRTIME = 1442997580912L ;
    public static final String MESSAGE_KEY = "message_key" ;
}
