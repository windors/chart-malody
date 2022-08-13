package cn.windors.malody.response;

import cn.windors.malody.dto.DownloadDto;
import cn.windors.malody.entity.core.Fileinfo;
import cn.windors.malody.type.MalodyResponseEnum;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Windor
 */
@Data
public class DownloadResponse extends MalodyResponse {

    private List<DownloadDto> items = new ArrayList<>();
    private Long sid;
    private Long cid;
    public DownloadResponse(MalodyResponseEnum code) {
        setCode(code);
    }

    public DownloadResponse(Long sid, Long cid) {
        this.sid = sid;
        this.cid = cid;
    }

    public static DownloadResponse empty() {
        return new DownloadResponse(MalodyResponseEnum.NOT_EXISTS);
    }

    public void add(Fileinfo fileinfo, String downloadAddress) {
        items.add(new DownloadDto(fileinfo.getName(), fileinfo.getMd5(),
                downloadAddress + fileinfo.getUri()));
    }
}
