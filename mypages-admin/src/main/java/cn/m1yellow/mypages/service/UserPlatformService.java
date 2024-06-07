package cn.m1yellow.mypages.service;

import cn.m1yellow.mypages.dto.UserPlatformDto;
import cn.m1yellow.mypages.entity.UserPlatform;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 社交媒体平台表 服务类
 * </p>
 *
 * @author M1Yellow
 * @since 2021-04-13
 */
public interface UserPlatformService extends IService<UserPlatform> {

    /**
     * 逻辑删除平台
     *
     * @param platform UserPlatform 对象
     * @return boolean 是否操作成功
     */
    boolean deleteById(UserPlatform platform);

    /**
     * 自定义查询一个对象
     *
     * @param params 参数封装 map
     * @return UserPlatformDto
     */
    UserPlatformDto getUserPlatform(Map<String, Object> params);

    /**
     * 自定义查询列表
     *
     * @param params 参数封装
     * @return List<UserPlatformDto>
     */
    List<UserPlatformDto> queryUserPlatformList(Map<String, Object> params);

}
