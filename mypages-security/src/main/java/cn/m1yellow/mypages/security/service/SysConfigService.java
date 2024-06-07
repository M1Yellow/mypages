package cn.m1yellow.mypages.security.service;


import cn.m1yellow.mypages.security.entity.SysConfig;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 系统配置表 服务类
 * </p>
 *
 * @author M1Yellow
 * @since 2021-05-16
 */
public interface SysConfigService extends IService<SysConfig> {

    Map<String, Object> getSysConfigs();

}
