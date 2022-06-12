package cn.windors.file.controller;

import cn.windors.file.dto.HttpDataResponse;
import cn.windors.file.dto.HttpResponse;
import cn.windors.file.dto.malody.MalodyChart;
import cn.windors.file.enums.UploadModeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @author Windor
 */
@ConditionalOnMissingBean(BaseFileController.class)
@Api(value = "基本文件接口")
//@Controller
public abstract class BaseFileController {

    /**
     * 传过来一个文件名，返回一个id，将来会根据id来上传/下载文件
     */
    @PostMapping("/ask")
    @ResponseBody
    @ApiOperation(value = "获取上传路径")
    public abstract HttpDataResponse<Long> ask();

    /**
     * 下载文件
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "下载文件")
    public abstract StreamingResponseBody download(@PathVariable("id") Long id,
                                                   HttpServletResponse response,
                                                   @RequestParam(required = false) Map<String, String> map) throws UnsupportedEncodingException, FileNotFoundException;

    /**
     * 上传文件
     */
    @PostMapping("/upload")
    @ResponseBody
    @ApiOperation(value = "上传文件")
    public abstract HttpResponse upload(@RequestParam MultipartFile file,
                                        @RequestParam Long id,
                                        @RequestParam UploadModeEnum mode);


    /**
     * 解析文件
     */
    @PostMapping("/solve")
    @ResponseBody
    @ApiOperation(value = "解析文件")
    public abstract MalodyChart solve(@RequestParam Long id);


    /**
     * 解析歌曲
     */
    @PostMapping("/solve/song")
    @ResponseBody
    @ApiOperation(value = "解析歌曲")
    public abstract Integer solveSong(@RequestParam Long id);


    /**
     * 检查文件
     */
    @GetMapping("/check")
    @ResponseBody
    @ApiOperation(value = "检查文件是否有误")
    public abstract boolean check(@RequestParam Long id, String md5);
}
