package cn.m1yellow.mypages.service.impl;

import cn.m1yellow.mypages.common.constant.GlobalConstant;
import cn.m1yellow.mypages.entity.UserOpinion;
import cn.m1yellow.mypages.mapper.UserOpinionMapper;
import cn.m1yellow.mypages.service.UserOpinionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 用户观点看法表 服务实现类
 * </p>
 *
 * @author M1Yellow
 * @since 2021-04-13
 */
@Service
public class UserOpinionServiceImpl extends ServiceImpl<UserOpinionMapper, UserOpinion> implements UserOpinionService {

    @Override
    public long getOpinionCount(Long userId, Long platformId, Long typeId) {
        QueryWrapper<UserOpinion> opinionQueryWrapper = new QueryWrapper<>();
        opinionQueryWrapper.eq("user_id", userId);
        opinionQueryWrapper.eq("platform_id", platformId);
        opinionQueryWrapper.eq("opinion_type", typeId);

        return count(opinionQueryWrapper);
    }

    @Override
    public Page<UserOpinion> getPagingList(Long userId, Long platformId, Long typeId, Integer pageNo, Integer pageSize,
                                           Map<String, Object> params) {

        QueryWrapper<UserOpinion> opinionQueryWrapper = new QueryWrapper<>();
        opinionQueryWrapper.eq("user_id", userId);
        opinionQueryWrapper.eq("platform_id", platformId);
        opinionQueryWrapper.eq("opinion_type", typeId);
        //opinionQueryWrapper.eq("is_deleted", 0); // MyBatis-Plus 已配置全局逻辑删除
        opinionQueryWrapper.orderByDesc("sort_no"); // 跟首页默认数据统一排序
        opinionQueryWrapper.orderByAsc("id"); // 额外排序，产生 file sort 可能会影响效率

        if (null == pageNo) {
            pageNo = GlobalConstant.PAGE_NO_DEFAULT;
        }
        if (null == pageSize) {
            pageSize = GlobalConstant.PAGE_SIZE_DEFAULT;
        }

        Page<UserOpinion> opinionPage = new Page<>(pageNo, pageSize);
        opinionPage = page(opinionPage, opinionQueryWrapper);

        return opinionPage;
    }
}
