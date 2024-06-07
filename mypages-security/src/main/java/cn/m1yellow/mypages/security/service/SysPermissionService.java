package cn.m1yellow.mypages.security.service;

import cn.m1yellow.mypages.security.entity.SysPermission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 权限表 服务类
 * </p>
 *
 * @author M1Yellow
 * @since 2021-06-02
 */
public interface SysPermissionService extends IService<SysPermission> {

    List<SysPermission> queryAllPermission();

}
