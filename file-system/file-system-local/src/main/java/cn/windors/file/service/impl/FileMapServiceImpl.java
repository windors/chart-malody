package cn.windors.file.service.impl;

import cn.windors.file.config.FileSystemProperties;
import cn.windors.file.dto.malody.MalodyChart;
import cn.windors.file.entity.FileMap;
import cn.windors.file.mapper.FileMapMapper;
import cn.windors.file.service.IFileMapService;
import cn.windors.file.util.MalodyFileUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author Windors
 */
@Service
public class FileMapServiceImpl extends ServiceImpl<FileMapMapper, FileMap> implements IFileMapService {

    @Resource
    private FileMapMapper fileMapMapper;

    @Resource
    private FileSystemProperties properties;

    @Override
    public MalodyChart solveMalodyChart(Long id) throws IOException {
        java.io.File file = new java.io.File(properties.getFileDir(), fileMapMapper.selectById(id).getLocal());
        // 需要将file解压
        ZipFile zip = new ZipFile(file, StandardCharsets.UTF_8);
        Enumeration<? extends ZipEntry> entries = zip.entries();
        InputStream inputStream = null;
        while(entries.hasMoreElements()) {
            ZipEntry zipEntry = entries.nextElement();
            if(MalodyFileUtil.canSolve(zipEntry.getName())) {
                inputStream = zip.getInputStream(zipEntry);
            }
        }
        if(inputStream == null) {
            throw new RuntimeException("未根据名称找到mc铺面");
        }
        ObjectMapper mapper = new ObjectMapper();
        // 忽略目标对象没有的属性
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.readValue(inputStream, MalodyChart.class);
    }
}
