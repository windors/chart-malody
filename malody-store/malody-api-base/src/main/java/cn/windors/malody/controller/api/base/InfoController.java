package cn.windors.malody.controller.api.base;


import cn.windors.malody.properties.MalodyCoreProperties;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Windor
 */
@RestController
@RequestMapping("api/store")
@Api(tags = {"Malody Info API"})
public final class InfoController {

    @GetMapping("info")
    @ApiOperation(value = "获取服务器信息", notes = "为《Malody V》客户端提供服务器信息")
    public String info() {
        return "{\n" +
                "  \"code\": 0,\n" +
                "  \"api\": " + MalodyCoreProperties.nowVersion + ",\n" +
                "  \"min\": " + MalodyCoreProperties.minVersion + ",\n" +
                "  \"welcome\": " + "\"欢迎访问Windors的私服!" + "\"\n" +
                "}";
    }
}
