package cn.windors.file.controller;

import cn.windors.file.config.FileSystemProperties;
import cn.windors.file.dto.HttpDataResponse;
import cn.windors.file.dto.HttpResponse;
import cn.windors.file.dto.HttpResponseEnum;
import cn.windors.file.dto.malody.MalodyChart;
import cn.windors.file.entity.FileMap;
import cn.windors.file.enums.UploadModeEnum;
import cn.windors.file.service.IFileMapService;
import cn.windors.file.util.Md5Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Map;
import java.util.UUID;

/**
 * @author Windor
 */
@Controller
@Slf4j
public class LocalStorageController extends BaseFileController {
    @Resource
    private IFileMapService fileMapService;

    @Resource
    private FileSystemProperties properties;

    @Override
    public HttpDataResponse<Long> ask() {
        FileMap file = new FileMap();
        fileMapService.save(file);
        return HttpDataResponse.ok(file.getId());
    }

    @Override
    public StreamingResponseBody download(Long id,
                                          HttpServletResponse response,
                                          @RequestParam(required = false) Map<String, String> map) throws UnsupportedEncodingException, FileNotFoundException {
        FileMap file = fileMapService.getById(id);
        InputStream is = new FileInputStream(new java.io.File(properties.getFileDir(), file.getLocal()));

        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode(file.getOriginalName(), "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        log.info("download {}", file.getOriginalName());
        return outputStream -> {
            FileCopyUtils.copy(is, outputStream);
        };
    }

    @Override
    public HttpResponse upload(MultipartFile file, Long id, UploadModeEnum mode) {
        if(id == -1) {
            return HttpResponse.ok();
        }
        FileMap f = null;
        try {
            f = new FileMap();
            f.setId(id);
            f.setOriginalName(file.getOriginalFilename());
            f.setLocal("/" + System.currentTimeMillis() + UUID.randomUUID());
            log.info("uploading {}", f);
            FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(new java.io.File(properties.getFileDir(), f.getLocal())));
            log.info("uploaded {}", f);
            String md5 = Md5Utils.getMd5(new File(properties.getFileDir(), f.getLocal()));
            f.setMd5(md5);
            fileMapService.updateById(f);
            return HttpResponse.ok();
        }catch (Exception e) {
            // files 保存了已保存成功的文件，需要将这些转到本地文件中
            // f保存了已经保存到本地的文件，需要将这些文件重写
            e.printStackTrace();
            return HttpResponse.getInstance(HttpResponseEnum.ERROR);
        }
    }


    @Override
    public MalodyChart solve(Long id) {
        return null;
    }

    @Override
    public Integer solveSong(Long id) {
        return null;
    }

    @Override
    public boolean check(Long id, String md5, Map<String, String> map) {
        md5 = md5.trim();
        FileMap fileMap = fileMapService.getById(id);
        return fileMap.getMd5().equals(md5);
    }
}
