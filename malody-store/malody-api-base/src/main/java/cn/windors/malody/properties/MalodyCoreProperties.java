package cn.windors.malody.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author Windor
 */
@Configuration
@ConfigurationProperties(prefix = "malody")
public class MalodyCoreProperties {
    public static String minVersion;

    public static String nowVersion;

    public static Integer pageSize = 20;

    /**
     * 文件系统地址
     */
    public static String fileSystemAddress = "http://localhost:9870";

    /**
     * 上传地址
     */
    public static String fileUploadAddress = fileSystemAddress + "/upload";

    /**
     * 获取上传路径地址
     */
    public static String fileGetUriAddress = fileSystemAddress + "/ask";

    /**
     * 下载地址
     */
    public static String fileDownloadAddress = fileSystemAddress + "/";

    /**
     * 解析地址
     */
    public static String fileSolveAddress = fileSystemAddress + "/solve";

    /**
     * 歌曲解析地址
     */
    public static String fileSolveSongAddress = fileSystemAddress + "/solve/song";

    /**
     * 文件检查地址
     */
    public static String fileCheckAddress = fileSystemAddress + "/check";

    /**
     * 校验的公钥
     */
    public static String rsaKey = "sgQb7aIukw8OqyqveicRQe75C11EA0QLpMGXtS0QCbVaid1zICJeyIhiYBmCm05ygsFkfoh+qahey/8NtU51NvJByBGe3CpgSTiaH9uhAdsLI4LttVqhUYQDJpI0NbRZ4FpTMAd9rcPwV7p4N3K8oHaKaFLbffyd1i9Pl001RXk=";

    /**
     * 服务器能解析列表
     */
    public static List<String> canResolveList;

    public String getFileSolveSongAddress() {
        return fileSolveSongAddress;
    }

    public void setFileSolveSongAddress(String fileSolveSongAddress) {
        MalodyCoreProperties.fileSolveSongAddress = fileSolveSongAddress;
    }

    public String getFileCheckAddress() {
        return fileCheckAddress;
    }

    public void setFileCheckAddress(String fileCheckAddress) {
        MalodyCoreProperties.fileCheckAddress = fileCheckAddress;
    }

    public List<String> getCanResolveList() {
        return canResolveList;
    }

    public void setCanResolveList(List<String> resolveList) {
        MalodyCoreProperties.canResolveList = resolveList;
    }

    public String getMinVersion() {
        return minVersion;
    }

    public String getNowVersion() {
        return nowVersion;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public String getFileSystemAddress() {
        return fileSystemAddress;
    }

    public String getFileUploadAddress() {
        return fileUploadAddress;
    }

    public String getFileGetUriAddress() {
        return fileGetUriAddress;
    }

    public String getFileDownloadAddress() {
        return fileDownloadAddress;
    }

    public String getFileSolveAddress() {
        return fileSolveAddress;
    }

    public String getRsaKey() {
        return rsaKey;
    }

    public void setMinVersion(String minVersion) {
        MalodyCoreProperties.minVersion = minVersion;
    }

    public void setNowVersion(String nowVersion) {
        MalodyCoreProperties.nowVersion = nowVersion;
    }

    public void setPageSize(Integer pageSize) {
        MalodyCoreProperties.pageSize = pageSize;
    }

    public void setFileSystemAddress(String fileSystemAddress) {
        MalodyCoreProperties.fileSystemAddress = fileSystemAddress;
    }

    public void setFileUploadAddress(String fileUploadAddress) {
        MalodyCoreProperties.fileUploadAddress = fileUploadAddress;
    }

    public void setFileGetUriAddress(String fileGetUriAddress) {
        MalodyCoreProperties.fileGetUriAddress = fileGetUriAddress;
    }

    public void setFileDownloadAddress(String fileDownloadAddress) {
        MalodyCoreProperties.fileDownloadAddress = fileDownloadAddress;
    }

    public void setFileSolveAddress(String fileSolveAddress) {
        MalodyCoreProperties.fileSolveAddress = fileSolveAddress;
    }

    public void setRsaKey(String rsaKey) {
        MalodyCoreProperties.rsaKey = rsaKey;
    }
}
