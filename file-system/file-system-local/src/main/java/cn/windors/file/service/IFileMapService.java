package cn.windors.file.service;

import cn.windors.file.dto.malody.MalodyChart;
import cn.windors.file.entity.FileMap;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.IOException;

/**
 * @author Windors
 */
public interface IFileMapService extends IService<FileMap> {

    MalodyChart solveMalodyChart(Long id) throws IOException;
}
