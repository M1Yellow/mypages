package cn.m1yellow.mypages.service;

import cn.m1yellow.mypages.entity.UserOpinion;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 用户观点看法表 服务类
 * </p>
 *
 * @author M1Yellow
 * @since 2021-04-13
 */
public interface UserOpinionService extends IService<UserOpinion> {

    /**
     * 查询用户在同一平台、同一类型下的观点数量
     *
     * @param userId     用户id
     * @param platformId 平台id
     * @param typeId     类型id
     * @return 观点条数
     */
    long getOpinionCount(Long userId, Long platformId, Long typeId);

    /**
     * 获取分页观点列表
     *
     * @param userId     用户id
     * @param platformId 平台id
     * @param typeId     类型id
     * @param pageNo     第几页，注意，页面传参从1开始，数据库记录下标从0开始
     * @param pageSize   每页数据，默认10
     * @param params     其他参数
     * @return 分页数据
     */
    Page<UserOpinion> getPagingList(Long userId, Long platformId, Long typeId, Integer pageNo, Integer pageSize, Map<String, Object> params);

}
