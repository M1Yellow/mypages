package cn.m1yellow.mypages.user.service.impl;


import cn.m1yellow.mypages.user.entity.UserBase;
import cn.m1yellow.mypages.user.mapper.UserBaseMapper;
import cn.m1yellow.mypages.user.service.UserBaseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author M1Yellow
 * @since 2021-04-13
 */
@Slf4j
@Service
public class UserBaseServiceImpl extends ServiceImpl<UserBaseMapper, UserBase> implements UserBaseService {

    @Override
    public UserBase getByUserName(String userName) {
        if (StringUtils.isBlank(userName)) {
            return null;
        }
        QueryWrapper<UserBase> userBaseQueryWrapper = new QueryWrapper<>();
        userBaseQueryWrapper.eq("user_name", userName);
        UserBase userBase = getOne(userBaseQueryWrapper);
        return userBase;
    }
}
