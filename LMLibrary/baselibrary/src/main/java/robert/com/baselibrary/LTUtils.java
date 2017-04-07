package robert.com.baselibrary;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.lngtop.energymanager.LSApp;
import com.lngtop.energymanager.LSLoginAct;
import com.lngtop.energymanager.base.LSBaseFragmentActivity;
import com.lngtop.energymanager.monitor.act.LSChartAct;
import com.lngtop.network.data_model.LTErrorData;
import com.lngtop.network.data_model.LTFliterData;
import com.lngtop.network.data_model.LTOrderData;
import com.lngtop.network.data_model.LTOrdersData;
import com.lngtop.network.data_model.LTUserTaskData;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;
import java.util.UUID;

import retrofit.RetrofitError;
import retrofit.mime.TypedByteArray;

/**
 * 工具类
 */
public class LTUtils {
    private static Context context;
    private static View view;

    /**
     * 弹出
     */
    public static LTProgressHUD showProcessDialog(Context context, OnCancelListener cancelListener) {

        if (context == null)
            return null;
        LTProgressHUD customDialog = new LTProgressHUD(context);
        customDialog.setView(context, context.getText(R.string.hud_connect),
                true, false, cancelListener);


        return customDialog;
    }

    public static void errorPerformance(Context context, RetrofitError arg0) {
        if (arg0.getResponse() != null && arg0.getResponse().getBody() != null) {
            String result = new String(((TypedByteArray) arg0.getResponse()
                    .getBody()).getBytes());

            Gson gson = new Gson();

            try {
                LTErrorData error = gson.fromJson(result, LTErrorData.class);
                if (error.message != null) {

                    if (error.message.contains("token")) {
                        context.startActivity(new Intent(context, LSLoginAct.class));
                        return;
                    }
                    showToast(context, error.message);
                } else {
                    showToast(context, "未知错误");
                }

            } catch (Exception e) {
                LTLog.e(e.getMessage());
                e.printStackTrace();
            }

        } else {
            if (context instanceof LSBaseFragmentActivity) {
                ((LSBaseFragmentActivity) context).showNetworkError();
            }

        }
    }

    public static void showToast(Context context, String str) {
        try {
            LSSimpleToast.info(context, str, "{fa-check-square}");
        } catch (Exception e) {
            LTLog.e(e.getMessage());
            e.printStackTrace();
        }

    }

    public static void showToast(Context context, int reid) {
        LSSimpleToast.info(context, context.getResources().getString(reid), "{fa-check-square}");
    }

    public interface CallPhoneShowDialogListener {
        void showDialog(String phoneNum);

    }

    public static void setCommonDetailInfo(final Activity act, final LTUserTaskData.SalerTask data, final CallPhoneShowDialogListener showDialog) {
        if (null == data) {
            return;
        }
        ((TextView) act.findViewById(R.id.tv_status_value)).setText(data
                .getStatus() + "");
        ((TextView) act.findViewById(R.id.tv_deliveryDate_value)).setText(data
                .getDateString());
        ((TextView) act.findViewById(R.id.tv_type_value)).setText(data
                .getType_str());
        ((TextView) act.findViewById(R.id.tv_purchaserName_value))
                .setText(data.purchaserName);
        ((TextView) act.findViewById(R.id.tv_purchaserContact_value))
                .setText(data.purchaserContact + " " + data.purchaserContactTel);

        act.findViewById(R.id.tv_purchaserContact_value).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + data.purchaserContactTel));
//                act.startActivity(intent);
                showDialog.showDialog(data.purchaserContactTel);
            }
        });

        ((TextView) act.findViewById(R.id.tv_purchaserAddress_value))
                .setText(data.purchaserAddress);
        ((TextView) act.findViewById(R.id.tv_productName_value))
                .setText(data.productName);
        ((TextView) act.findViewById(R.id.tv_commodityName_value))
                .setText(data.commodityName);
        ((TextView) act.findViewById(R.id.tv_quantity_value))
                .setText(data.quantity + data.unitSymbol);
        ((TextView) act.findViewById(R.id.tv_workerName_value))
                .setText(data.workerName);
        ((TextView) act.findViewById(R.id.tv_driverName_value))
                .setText(data.driverName);
        ((TextView) act.findViewById(R.id.tv_carName_value))
                .setText(data.carName);
        ((TextView) act.findViewById(R.id.tv_remark_value))
                .setText(data.remark);
    }

    public static void setCommonDetailInfo2(Activity act, LTOrderData data) {
        if (null == data) {
            return;
        }
        ((TextView) act.findViewById(R.id.tv_0)).setText(data
                .productName + "");
        ((TextView) act.findViewById(R.id.tv_1)).setText(data
                .carName);
        ((TextView) act.findViewById(R.id.tv_2)).setText(data
                .quantity + data
                .unitSymbol);
        ((TextView) act.findViewById(R.id.tv_3)).setText(data
                .realQuantity + data
                .unitSymbol);
        ((TextView) act.findViewById(R.id.tv_4)).setText(data
                .discount);
        ((TextView) act.findViewById(R.id.tv_5)).setText(data
                .price + act.getString(R.string.market_yuan));
        ((TextView) act.findViewById(R.id.tv_6)).setText(data
                .realTotalAmount + act.getString(R.string.market_yuan));
//		if ( act.findViewById(R.id.ll_paytime).getVisibility() == View.VISIBLE) {
//			((TextView) act.findViewById(R.id.tv_7)).setText(data
//					.getDateString());
//		}

        ((TextView) act.findViewById(R.id.tv_8)).setText(data
                .remark);
    }

    public static void setCustomerCommonDetailInfo(Activity act, LTOrdersData.Order data) {
        if (null == data) {
            return;
        }
        ((TextView) act.findViewById(R.id.tv_0)).setText(data
                .productName + "");
        ((TextView) act.findViewById(R.id.tv_1)).setText(data
                .carName);
        ((TextView) act.findViewById(R.id.tv_2)).setText(data
                .quantity + data
                .unitSymbol);
        ((TextView) act.findViewById(R.id.tv_3)).setText(data
                .realQuantity + data
                .unitSymbol);
        ((TextView) act.findViewById(R.id.tv_4)).setText(data
                .discount);
        ((TextView) act.findViewById(R.id.tv_5)).setText(data
                .price + act.getString(R.string.market_yuan));
        ((TextView) act.findViewById(R.id.tv_6)).setText(data
                .realTotalAmount + act.getString(R.string.market_yuan));
        if (act.findViewById(R.id.ll_paytime).getVisibility() == View.VISIBLE) {
            ((TextView) act.findViewById(R.id.tv_7)).setText(data
                    .getDateString());
        }

        ((TextView) act.findViewById(R.id.tv_8)).setText(data
                .remark);
    }

    @SuppressWarnings("unused")
    static public void changeBitmapColor(Bitmap sourceBitmap, ImageView image, int color) {

        Bitmap resultBitmap = Bitmap.createBitmap(sourceBitmap, 0, 0,
                sourceBitmap.getWidth() - 1, sourceBitmap.getHeight() - 1);
        Paint p = new Paint();
        ColorFilter filter = new LightingColorFilter(color, 1);
        p.setColorFilter(filter);
        image.setImageBitmap(resultBitmap);

        Canvas canvas = new Canvas(resultBitmap);
        canvas.drawBitmap(resultBitmap, 0, 0, p);
    }

//    static public void initUserConfig() {
//        LSApp.mCategoryType = LTUtils.getCategory();
//        LSApp.mStatusType = LTUtils.getStatus();
//    }
//
//    static public String getStatus() {
//        List<LTFliterData.Filter> mFilterarray = LTUtils.findAll(LTFliterData.Filter.class);
//        if (null == mFilterarray)
//            return "";
//        int result = 0;
//        for (LTFliterData.Filter filter : mFilterarray) {
//            LTUtils.update(filter);
//            if (filter.isChecked) {
//                result = filter.value + result;
//            }
//        }
//        if (result == 0)
//        {
//            return "";
//        }
//        return result + "";
//    }
//
//    static public String getCategory() {
//        List<LTFliterData.Types> mTypeCarray = LTUtils.findAll(LTFliterData.Types.class);
//        if (null == mTypeCarray)
//            return "";
//        String result = "";
//        for (LTFliterData.Types filter : mTypeCarray) {
//            LTUtils.update(filter);
//            if (filter.isChecked) {
//                result = result + ",";
//            }
//        }
//        return result;
//    }

    static public void initDbUtils(Context context) {
        LSApp.mDbUtils = DbUtils.create(context);
    }

    static public void initBitMapUtils(Context context) {
        String cachePath = Environment.getExternalStorageDirectory() + "/cacheFileDir";
        LSApp.mBitmapUtils = new BitmapUtils(context, cachePath);
    }

    @SuppressWarnings("unused")
    static public void dropAll() {

        if (LSApp.mDbUtils == null)
            return;
        try {
            LSApp.mDbUtils.deleteAll(LTFliterData.Filter.class);
            LSApp.mDbUtils.deleteAll(LTFliterData.Types.class);
            LSApp.mDbUtils.dropDb();
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    static public void deleteAll(java.lang.Class<?> entityType) {

        if (LSApp.mDbUtils == null)
            return;
        try {
            LSApp.mDbUtils.deleteAll(entityType);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    static public void update(java.lang.Object entity) {
        if (LSApp.mDbUtils == null)
            return;
        try {
            LSApp.mDbUtils.saveOrUpdate(entity);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    static public void saveOrUpdate(java.lang.Object entity) {
        if (LSApp.mDbUtils == null)
            return;
        try {
            LSApp.mDbUtils.save(entity);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    static public <T> java.util.List<T> findAll(java.lang.Class<T> entityType) {
        if (LSApp.mDbUtils == null)
            return null;
        try {
            return LSApp.mDbUtils.findAll(entityType);
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getJson(String name) {
        try {
            InputStream is = LSApp.getApplication().getAssets().open(name);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return new String(buffer);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getFormat(long time, String format) {
        return new SimpleDateFormat(format).format(time);
    }

    public static boolean isToday(long time) {
        if (time >= getStartTime() && time <= getEndTime()) {
            return true;
        } else {
            return false;
        }
    }

    private static Long getStartTime() {
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        return todayStart.getTime().getTime();
    }

    private static Long getEndTime() {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        return todayEnd.getTime().getTime();
    }

    /**
     * 获取时间差
     */
    public static String getTimeSpan(long time, long endtime) {
        long between = endtime - time;
        return formatLongToTimeStr(between);
    }

    public static String getTimeSpan(long betweenTime) {
        return formatLongToTimeStr(betweenTime);
    }

    private static String formatLongToTimeStr(Long l) {
        int hour = 0;
        int minute = 0;
        int second = 0;

        second = l.intValue() / 1000;

        if (second > 60) {
            minute = second / 60;
            second = second % 60;
        }
        if (minute > 60) {
            hour = minute / 60;
            minute = minute % 60;
        }
        return (getTwoLength(hour) + ":" + getTwoLength(minute) + ":" + getTwoLength(second));
    }

    private static String getTwoLength(final int data) {
        if (data < 10) {
            return "0" + data;
        } else {
            return "" + data;
        }
    }

    public static void addFragment(FragmentActivity activity, int layoutId,
                                   Fragment fragment, String tag) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        if (activity.isFinishing()) {
            return;
        }
        fragmentTransaction.add(layoutId, fragment, tag);
        fragmentTransaction.commit(); // 提交事务
    }

    // 解压缩
    public static boolean unZip(String archive, String decompressDir) {//,boolean isDownloadData,LSOfflineData data) {
        try {
            BufferedInputStream bi;
            ZipFile zf = new ZipFile(archive, "GBK");
            long size = getOriginalSize(zf);
            Enumeration e = zf.getEntries();
            while (e.hasMoreElements()) {
                ZipEntry ze2 = (ZipEntry) e.nextElement();

                String entryName = ze2.getName();
                String path = decompressDir + "/" + entryName;
                if (ze2.isDirectory()) {
                    File decompressDirFile = new File(path);
                    if (!decompressDirFile.exists()) {
                        decompressDirFile.mkdirs();
                    }
                } else {
                    String fileDir = path.substring(0, path.lastIndexOf("/"));
                    File fileDirFile = new File(fileDir);
                    if (!fileDirFile.exists()) {
                        fileDirFile.mkdirs();
                    }
                    BufferedOutputStream bos = new BufferedOutputStream(
                            new FileOutputStream(decompressDir + "/"
                                    + entryName));
                    bi = new BufferedInputStream(zf.getInputStream(ze2));
                    byte[] readContent = new byte[1024];
                    int readCount = bi.read(readContent);
                    while (readCount != -1) {
                        bos.write(readContent, 0, readCount);
                        readCount = bi.read(readContent);
                    }
                    bos.flush();
                    bos.close();
                }
            }
            zf.close();
        } catch (Exception e2) {
            return false;
        }
        return true;
    }

    //获取压缩文件的大小
    public static long getOriginalSize(ZipFile file) {
        Enumeration<ZipEntry> entries = (Enumeration<ZipEntry>) file.getEntries();
        long originalSize = 0l;
        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            if (entry.getSize() >= 0) {
                originalSize += entry.getSize();
            }
        }
        return originalSize;
    }

    // 复制并解压
    public static boolean copy(Context myContext, String assets_name,
                               String savePath, String zipFileName) {
        File targetFile = new File(savePath + "/" + zipFileName + ".zip");
        if (targetFile.exists()) {
            targetFile.delete();
        }
        File root = new File(savePath);
        if (!root.exists()) {
            root.mkdirs();
        }
        File dir = new File(savePath + "/" + zipFileName + ".zip");
        try {
            InputStream is = myContext.getResources().getAssets()
                    .open(assets_name);
            FileOutputStream fos = new FileOutputStream(dir);
            byte[] buffer = new byte[1024];
            int count = 0;
            while ((count = is.read(buffer)) > 0) {
                fos.write(buffer, 0, count);
            }
            fos.close();
            is.close();
        } catch (Exception e) {
            return false;
        }
        boolean flag = unZip(savePath + "/" + zipFileName + ".zip", savePath + "/");
        targetFile.delete();
        return flag;
    }

    public static int copy(InputStream input, OutputStream output) {
        byte[] buffer = new byte[1024 * 8];
        BufferedInputStream in = new BufferedInputStream(input, 1024 * 8);
        BufferedOutputStream out = new BufferedOutputStream(output, 1024 * 8);
        int count = 0, n = 0;
        try {
            while ((n = in.read(buffer, 0, 1024 * 8)) != -1) {
                out.write(buffer, 0, n);
                count += n;
            }
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return count;
    }


    public static boolean isWifiAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected() && networkInfo
                .getType() == ConnectivityManager.TYPE_WIFI);
    }


    public static int getResourceByReflect(String name) {
        Class stringClass = R.string.class;
        Field field;
        int r_id;
        try {
            field = stringClass.getField(name);
            r_id = field.getInt(field.getName());
        } catch (Exception e) {
            r_id = R.string.role_admin;
            Log.e("ERROR", String.valueOf(e.getMessage()));
        }
        return r_id;
    }

    public static void deleteFile(File file) {
        if (file.isFile()) {
            file.delete();
            return;
        }
        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            if (childFiles == null || childFiles.length == 0) {
                file.delete();
                return;
            }
            for (int i = 0; i < childFiles.length; i++) {
                deleteFile(childFiles[i]);
            }
            file.delete();
        }
    }

    //获取消息详情显示时间的字符串
    public static String getMessageDate(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return getDateStr(date);
    }

    public static String getMessageTime(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return getTimeStr(date);
    }

    public static String getTimeStr(Date date) {
        String format = "HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String s = sdf.format(date.getTime());
        return s;
    }

    public static String getDateStr(Date date) {
        String format = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String s = sdf.format(date.getTime());
        return s;
    }

    public static String getFormatedDateTime(String pattern, long dateTime) {
        SimpleDateFormat sDateFormat = new SimpleDateFormat(pattern);
        sDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        return sDateFormat.format(new Date(dateTime + 0));
    }

    //上移动画
    public static ObjectAnimator moveupAnimtion(Context context, View view) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        ObjectAnimator titleAnimatoe = ObjectAnimator.ofFloat(view, "translationY", height, 0).setDuration(C.Duration);
        titleAnimatoe.start();
        return titleAnimatoe;
    }

    //下移动画
    public static ObjectAnimator movedownAnimtion(View view) {
        ObjectAnimator titleAnimatoe = ObjectAnimator.ofFloat(view, "translationY", 0, view.getHeight()).setDuration(C.Duration);
        titleAnimatoe.start();
        return titleAnimatoe;
    }

    /*判断应用是否在前台*/
    public static boolean isForeground(Context context)
    {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }
    public static void showMsg(final Context context, final String msgId) {
//        Bundle bundle=new Bundle();
//        bundle.putString("msgId", msgId);
//        bundle.putBoolean("message", true);
//        bundle.putBoolean("pushmessage",true);
//       ((LSBaseFragmentActivity)context).startActivity(LSChartAct.class,bundle);
        Intent intent=new Intent(context,LSChartAct.class);
        intent.putExtra("msgId", msgId);
        intent.putExtra("message", true);
        intent.putExtra("pushmessage", true);
        context.startActivity(intent);

//        LTBaseDialogFragment f = new LTBaseDialogFragment(new LTBaseDialogFragment.LTDialogFragmentUIDataSource() {
//            @Override
//            public int dialogGetLayoutId() {
//
//                return R.layout.dialog_normal_new;
//
//            }
//
//            @Override
//            public void dialogSetView(View view) {
//                //自定义样式
//                ((TextView) view.findViewById(R.id.dialog_content)).setText(msg);
//                ((TextView) view.findViewById(R.id.dialog_title)).setText(context.getString(R.string.monitor_message_detail));
//            }
//
//        }, "test");
//
//        f.setDialogFragmentClickListern(new LTBaseDialogFragment.LTDialogFragmentClickListen() {
//
//            @Override
//            public void dialogBtnClick(View view, LTBaseDialogFragment fragment) {
//                if (view.getId() == R.id.btn_gray_black) {
//                    fragment.dismiss();
//                }
//            }
//        });
//        try{
//            if(LSBaseFragmentActivity.mCurAct != null){
//                f.show(LSBaseFragmentActivity.mCurAct.getSupportFragmentManager(), "test");
//            }
//        }catch (Exception e){
//
//        }


    }
        //按规则参数照片的名字

    public static String produceFile() {
        Random ran = new Random();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String fileName = sdf.format(new Date()) + ran.nextInt(1000);
        return fileName + ".jpg";
    }

    /**
     * 照相功能
     */
    public static void cameraMethod(LSBaseFragmentActivity activity, String saveImageDir, String filename) {
        File file = new File(saveImageDir);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                return;
            }
        }
        Intent imageCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File imageFile = new File(filename);
        Uri u = Uri.fromFile(imageFile);
        imageCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT, u);
        imageCaptureIntent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 1600 * 1200);
        activity.startActivityForResult(imageCaptureIntent, C.CAMERA_REQUEST_CODE);
    }

    public static Uri getRotationPic(Uri uri) throws IOException {
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(uri.getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        int rotation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        int rotationInDegrees = exifToDegrees(rotation);
        Matrix matrix = new Matrix();
        if (rotation != 0f) {
            matrix.preRotate(rotationInDegrees);
        }

        Bitmap bitmap = MediaStore.Images.Media.getBitmap(LSApp.curApp().getContentResolver(), uri);

        float w_r = 1000.0f / (float) bitmap.getWidth();
        float h_r = 1000.0f / (float) bitmap.getHeight();
        float ratio = Math.min(w_r, h_r);
        if (ratio < 1.0f) {

            matrix.postScale(ratio, ratio);

        }
        Bitmap adjustedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

//        bitmap.recycle();

        //create a file to write bitmap data
        File f = new File(LSApp.curApp().getCacheDir(), "adjustedBitmap" + UUID.randomUUID());
        f.createNewFile();

//Convert bitmap to byte array
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        adjustedBitmap.compress(Bitmap.CompressFormat.JPEG, 50, bos);
        byte[] bitmapdata = bos.toByteArray();

//write the bytes in file
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(bitmapdata);
        fos.flush();
        fos.close();
        return Uri.fromFile(f);
    }

    private static int exifToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }

    /**
     * 数值统一保留两位小数
     * @return
     */
    public static String getFormatValue(double value){
        DecimalFormat df = new DecimalFormat("0.00");
        return String.valueOf(df.format(value));
    }

}
