package cn.windors.malody.util;


import cn.windors.malody.dto.MalodyChart;
import cn.windors.malody.properties.MalodyCoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author Wind_Yuan
 */
@Component
public class RemoteFileSystemUtils {
    @Resource
    private RestTemplate restTemplate;

    public String getNewUploadUri() {
        String url = MalodyCoreProperties.fileGetUriAddress;
        return getResponseData(restTemplate.postForObject(url, null, String.class));
    }

    public MalodyChart getMalodyChartInfo(String uri) {
        try {
            String url = MalodyCoreProperties.fileSolveAddress;
            return restTemplate.postForObject(url + "?id=" + uri, null, MalodyChart.class);
        }catch (Exception e) {
            return null;
        }
    }

    public int getSongInfo(String uri) {
        String url = MalodyCoreProperties.fileSolveSongAddress;
        return restTemplate.postForObject(url + "?id=" + uri, null, Integer.class);
    }

    public String upload(MultipartFile file, String id) throws IOException {
        String url = MalodyCoreProperties.fileUploadAddress;
        MultiValueMap<String, Object> bodyParams = new LinkedMultiValueMap<>();
        org.springframework.core.io.Resource resource = new ByteArrayResource(file.getBytes()){
            @Override
            public String getFilename() {
                return file.getOriginalFilename();
            }
        };
        bodyParams.add("file", resource);
        bodyParams.add("id", id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(bodyParams, headers);
        return restTemplate.postForObject(url, requestEntity, String.class);
    }

    public boolean checkFile(String id, String md5) {
        // TODO 校验文件
        String url = MalodyCoreProperties.fileCheckAddress;
        return Boolean.TRUE.equals(restTemplate.getForObject(url + "?id=" + id + "md5=" + md5, Boolean.class));
    }

    public void releaseRemoteFile(String id) {

    }

    private static String getResponseData(String response) {
        try {
            return new JSONObject(response).getString("data");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("获取json值失败!");
    }
}
