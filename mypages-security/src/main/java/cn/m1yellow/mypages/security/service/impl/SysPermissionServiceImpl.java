package cn.m1yellow.mypages.security.service.impl;

import cn.m1yellow.mypages.common.constant.GlobalConstant;
import cn.m1yellow.mypages.common.util.JSONUtil;
import cn.m1yellow.mypages.common.util.ObjectUtil;
import cn.m1yellow.mypages.common.util.RedisUtil;
import cn.m1yellow.mypages.security.entity.SysPermission;
import cn.m1yellow.mypages.security.mapper.SysPermissionMapper;
import cn.m1yellow.mypages.security.service.SysPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author M1Yellow
 * @since 2021-06-02
 */
@Slf4j
@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements SysPermissionService {

    //@Autowired // 不是直接依赖的 jar 包，传递依赖的 common 模块，@Autowired 会报错
    @Resource
    private RedisUtil redisUtil;


    @Override
    public List<SysPermission> queryAllPermission() {
        // 先从 redis 缓存中获取
        String permissionListCacheStr = ObjectUtil.getString(redisUtil.get(GlobalConstant.SYS_PERMISSION_LIST_CACHE_KEY));
        if (StringUtils.isNotBlank(permissionListCacheStr)) {
            List<SysPermission> permissionList = JSONUtil.toList(permissionListCacheStr, SysPermission.class);
            if (permissionList != null && permissionList.size() > 0) {
                return permissionList;
            }
        }

        // 缓存不存在，则查询数据库
        List<SysPermission> permissionList = list();

        // 设置缓存
        if (permissionList != null && permissionList.size() > 0) {
            redisUtil.set(GlobalConstant.SYS_PERMISSION_LIST_CACHE_KEY, JSONUtil.toJSON(permissionList));
        }

        return permissionList;
    }

}
