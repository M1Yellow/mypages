package cn.m1yellow.mypages.security.service;

import cn.m1yellow.mypages.security.entity.SysRole;
import cn.m1yellow.mypages.security.entity.SysUserRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户角色关联表 服务类
 * </p>
 *
 * @author M1Yellow
 * @since 2021-06-02
 */
public interface SysUserRoleService extends IService<SysUserRole> {

    List<SysUserRole> queryUserRoleListByUserId(Long userId);

    List<SysRole> queryRoleListByUserId(Long userId);

}
