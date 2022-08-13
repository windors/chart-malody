package cn.windors.malody.util;

import cn.windors.malody.properties.MalodyCoreProperties;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author Wind_Yuan
 */
public class MalodyUtils {

    private static final String PICTURE_REGEX = ".+\\.(png|jpg|gif)$";
    private static final String MUSIC_REGEX = ".+\\.(ogg|mp3)$";

    /**
     * 判断文件是否可被解析
     * @param chartFileName
     * @return
     */
    public static boolean isChartFile(String chartFileName) {
        for (String canResolve : MalodyCoreProperties.canResolveList) {
            if (chartFileName.endsWith(canResolve)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 根据文件名判断是否是图片文件
     * @param filename 文件名
     * @return 如果可解析则返回true，否则返回false
     */
    public static boolean isPictureFile(String filename) {
        return Pattern.matches(PICTURE_REGEX, filename);
    }

    /**
     * 根据文件名判读是否是音乐文件
     * @param filename 文件名
     * @return 如果可解析则返回true，否则返回false
     */
    public static boolean isMusicFile(String filename) {
        return Pattern.matches(MUSIC_REGEX, filename);
    }

    /**
     * 计算mode
     * @param songMode
     * @param chartMode
     * @return
     */
    public static int calcMode(int songMode, int chartMode) {
        return 0;
    }


    /**
     * 获取数组中的铺面文件名
     * @param names 文件名数组
     * @return 如果找到了铺面名则返回铺面名，否则返回null
     */
    public static String getChartName(String[] names) {
        for (String name : names) {
            if (isChartFile(name)) {
                // TODO 判断难度名是否可解析
                return name;
            }
        }
        return null;
    }


    /**
     * 将name和hash转换为Map
     * @param name
     * @param hash
     * @return 结果map，如果为null表示name、hash有错误
     */
    public static Map<String, String> getNameHashMap(String name, String hash) {
        String[] names = name.split(",");
        String[] hashes = hash.split(",");
        if (names.length != hashes.length) {
            return null;
        }
        Map<String, String> result = new HashMap<>(names.length, 1);
        for (int i = 0; i < names.length; i++) {
            result.put(names[i], hashes[i]);
        }
        return result;
    }
}
