package robert.com.baselibrary;

import android.os.Environment;

import java.io.File;

/**
 * 路径管理中心
 */
public class LSResPathCenter {

    public static final String ROOT = "gaschat";
    public static final String DATA = "data";
    public static final String IMAGE = "image";
    public static final String SmallIMAGE = "samllimage";

    /**
     * SDCard路径
     */
    private static final String SD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();

    private static class ResPathCenterHolder {
        private static LSResPathCenter instance = new LSResPathCenter();
    }

    private LSResPathCenter() {
        init();
    }

    public static LSResPathCenter getInstance() {
        return ResPathCenterHolder.instance;
    }

    /**
     * 功能: 初始化本地数据存储目录<br/>
     * <br/>
     * 如果目录不存，则创建对应的目录。
     *
     * @author: yueshanshan
     * @date:2015-4-1下午4:14:01
     */
    private void init() {
        File rootFile = new File(SD_PATH + "/" + ROOT);
        if (!rootFile.exists()) {
            rootFile.mkdirs();
        }
        File dataFile = new File(SD_PATH + "/" + ROOT + "/" + DATA);
        if (!dataFile.exists()) {
            dataFile.mkdirs();
        }
        File projectDataFile = new File(getDataRootPath());
        if (!projectDataFile.exists()) {
            projectDataFile.mkdirs();
        }
    }

    /**
     * 功能:得到本项目中的图片等根目录 sdcard/gaschat/data
     *
     * @return
     * @author: yueshanshan
     * @date:2014-8-19下午5:41:56
     */
    public String getDataRootPath() {
        StringBuilder sb = new StringBuilder();
        sb.append(SD_PATH).append(File.separator);
        sb.append(ROOT).append(File.separator);
        sb.append(DATA).append(File.separator);
        return sb.toString();
    }

    /**
     * 功能: 存放图片文件的目录 sdcard/gaschat/data/image
     *
     * @return
     * @author: yueshanshan
     * @date:2014-8-19下午6:06:08
     */
    public String getImageFilePath() {
        StringBuilder sb = new StringBuilder();
        sb.append(getDataRootPath());
        sb.append(IMAGE);
        return sb.toString();
    }
    /**
     * 功能: 存放图片文件的目录 sdcard/gaschat/data/image
     *
     * @return
     * @author: yueshanshan
     * @date:2014-8-19下午6:06:08
     */
    public String getSmallImageFilePath() {
        StringBuilder sb = new StringBuilder();
        sb.append(getDataRootPath());
        sb.append(SmallIMAGE);
        return sb.toString();
    }
    public String getCatchPath() {
        StringBuilder sb = new StringBuilder();
        sb.append(getDataRootPath()).append(File.separator);
        sb.append("httpCache");
        return sb.toString();
    }
}
