package cn.windors.file.dto.malody;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.configurationprocessor.json.JSONException;

import java.io.File;
import java.io.IOException;

/**
 * @author Windor
 */
@Data
@NoArgsConstructor
public class MalodyChart {
    private McMeta meta;

//    private String time;
//
//    private Object note;

    public static void main(String[] args) throws IOException, JSONException {
        File file = new File("D:\\temp\\malody\\1650264969.mc");
        // 需要将file解压
        ObjectMapper mapper = new ObjectMapper();
        // 忽略目标对象没有的属性
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 拿一个mc文件做测试，尝试读取内容
        MalodyChart malodyChart = mapper.readValue(file, MalodyChart.class);
        System.out.println(malodyChart);
    }


}
