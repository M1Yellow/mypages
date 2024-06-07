package cn.m1yellow.mypages.user.service;


import cn.m1yellow.mypages.user.entity.UserBase;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author M1Yellow
 * @since 2021-04-13
 */
public interface UserBaseService extends IService<UserBase> {

    UserBase getByUserName(String userName);
}
