package cn.m1yellow.mypages.service.impl;

import cn.m1yellow.mypages.dto.UserPlatformDto;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.m1yellow.mypages.entity.UserPlatform;
import cn.m1yellow.mypages.mapper.UserPlatformMapper;
import cn.m1yellow.mypages.service.UserPlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 社交媒体平台表 服务实现类
 * </p>
 *
 * @author M1Yellow
 * @since 2021-04-13
 */
@Service
public class UserPlatformServiceImpl extends ServiceImpl<UserPlatformMapper, UserPlatform> implements UserPlatformService {

    @Autowired
    private UserPlatformMapper userPlatformMapper;

    @Override
    public boolean deleteById(UserPlatform platform) {
        return updateById(platform);
    }

    @Override
    public UserPlatformDto getUserPlatform(Map<String, Object> params) {
        return userPlatformMapper.getUserPlatform(params);
    }

    @Override
    public List<UserPlatformDto> queryUserPlatformList(Map<String, Object> params) {
        return userPlatformMapper.queryUserPlatformList(params);
    }
}
