package cn.m1yellow.mypages.security.service.impl;

import cn.m1yellow.mypages.common.constant.GlobalConstant;
import cn.m1yellow.mypages.common.util.JSONUtil;
import cn.m1yellow.mypages.common.util.ObjectUtil;
import cn.m1yellow.mypages.common.util.RedisUtil;
import cn.m1yellow.mypages.security.entity.SysConfig;
import cn.m1yellow.mypages.security.mapper.SysConfigMapper;
import cn.m1yellow.mypages.security.service.SysConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统配置表 服务实现类
 * </p>
 *
 * @author M1Yellow
 * @since 2021-05-16
 */
@Slf4j
@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements SysConfigService {

    @Autowired
    private RedisUtil redisUtil;


    @Override
    public Map<String, Object> getSysConfigs() {

        // 先从缓存取
        String configCacheStr = ObjectUtil.getString(redisUtil.get(GlobalConstant.SYS_CONFIG_MAP_CACHE_KEY));
        if (StringUtils.isNotBlank(configCacheStr)) {
            Map<String, Object> sysConfigMap = JSONUtil.toMap(configCacheStr);
            if (!CollectionUtils.isEmpty(sysConfigMap)) {
                return sysConfigMap;
            }
        }

        List<SysConfig> sysConfigList = list();
        if (CollectionUtils.isEmpty(sysConfigList)) {
            return null;
        }
        Map<String, Object> sysConfigMap = sysConfigList.stream().collect(Collectors.toMap(SysConfig::getConfigKey, SysConfig::getConfigValue));
        // 添加缓存，不设置过期时间，有改动的地方，清理缓存之后，会自动重新加载
        if (!CollectionUtils.isEmpty(sysConfigMap)) {
            redisUtil.set(GlobalConstant.SYS_CONFIG_MAP_CACHE_KEY, JSONUtil.toJSON(sysConfigMap));
        }
        return sysConfigMap;
    }

}
