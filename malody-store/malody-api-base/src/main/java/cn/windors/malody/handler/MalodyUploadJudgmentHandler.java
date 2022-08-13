package cn.windors.malody.handler;


import org.springframework.lang.NonNull;

/**
 * @author Windor
 * 用于判断用户是否具有上传权限
 */
public interface MalodyUploadJudgmentHandler {
    /**
     * @param uid 用户id
     * @param cid 谱面id
     * @return 拒绝用户上传的原因, 返回null表示用户可上传
     */
    String reject(@NonNull Long uid,@NonNull Long cid);
}
