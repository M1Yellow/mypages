package cn.m1yellow.mypages.controller;


import cn.m1yellow.mypages.common.api.CommonResult;
import cn.m1yellow.mypages.common.aspect.DoCache;
import cn.m1yellow.mypages.common.aspect.WebLog;
import cn.m1yellow.mypages.common.constant.GlobalConstant;
import cn.m1yellow.mypages.common.util.CheckParamUtil;
import cn.m1yellow.mypages.common.util.ObjectUtil;
import cn.m1yellow.mypages.dto.UserOpinionDto;
import cn.m1yellow.mypages.entity.UserOpinion;
import cn.m1yellow.mypages.service.UserOpinionService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 用户观点看法表 前端控制器
 * </p>
 *
 * @author M1Yellow
 * @since 2021-04-13
 */
@Slf4j
@RestController
@RequestMapping("/opinion")
public class UserOpinionController {

    @Autowired
    private UserOpinionService userOpinionService;


    @ApiOperation("添加/更新观点")
    @RequestMapping(value = "add", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @WebLog
    @DoCache
    public CommonResult<UserOpinion> add(UserOpinionDto opinion) {

        CheckParamUtil.validate(opinion);

        // 新增的时候，判断一个平台、一个类型下的观点不能超过 10 条，可常量配置
        // 查询对应平台和类型下的观点数量
        long opinionCount = userOpinionService.getOpinionCount(opinion.getUserId(), opinion.getPlatformId(), opinion.getOpinionType());
        if (opinionCount >= GlobalConstant.PLATFORM_TYPE_OPINION_NUM) {
            log.info("同一平台、同一类型只能添加 " + GlobalConstant.PLATFORM_TYPE_OPINION_NUM + " 条观点！");
            return CommonResult.failed("同一平台、同一类型只能添加 " + GlobalConstant.PLATFORM_TYPE_OPINION_NUM + " 条观点！");
        }

        UserOpinion saveOpinion = new UserOpinion();
        BeanUtils.copyProperties(opinion, saveOpinion);

        // 去字符串字段两边空格
        ObjectUtil.stringFiledTrim(saveOpinion);

        if (!userOpinionService.saveOrUpdate(saveOpinion)) {
            log.error("添加/更新观点失败");
            return CommonResult.failed("操作失败");
        }

        // 重新加载对象
        UserOpinion reloadOpinion = null;
        if (null != opinion.getId()) { // 更新
            reloadOpinion = userOpinionService.getById(saveOpinion.getId());
        }

        return CommonResult.success(reloadOpinion);
    }


    @ApiOperation("观点列表")
    @RequestMapping(value = "list", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @WebLog
    public CommonResult<Page<UserOpinion>> list(@RequestParam Long userId, @RequestParam Long platformId, @RequestParam Long typeId,
                                                @RequestParam(required = false) Integer recordNo,
                                                @RequestParam(required = false) Integer pageNo,
                                                @RequestParam(required = false) Integer pageSize) {

        if (null == userId || null == platformId || null == typeId) {
            log.error("请求参数错误");
            return CommonResult.failed("请求参数错误");
        }

        // 先查询是否有记录
        long count = userOpinionService.getOpinionCount(userId, platformId, typeId);
        if (count < 1) {
            // TODO 返回成功信息，否则页面会弹出错误提示
            return CommonResult.success();
        }

        // 添加额外参数
        Map<String, Object> params = null;
        if (null != recordNo) {
            params = new HashMap<>(1);
            params.put("recordNo", recordNo); // 用于控制从第几条记录开始
        }

        Page<UserOpinion> opinionListPage = userOpinionService.getPagingList(userId, platformId, typeId, pageNo, pageSize, params);

        return CommonResult.success(opinionListPage);
    }


    @ApiOperation("移除观点")
    @RequestMapping(value = "remove", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @WebLog
    @DoCache
    public CommonResult<String> remove(@RequestParam Long userId, @RequestParam Long id) {

        if (null == userId || null == id) {
            log.error("请求参数错误");
            return CommonResult.failed("请求参数错误");
        }

        Map<String, Object> params = new HashMap<>(2);
        params.put("user_id", userId);
        params.put("id", id);
        if (!userOpinionService.removeByMap(params)) {
            log.error("移除失败，userId: {}, id: {}", userId, id);
            return CommonResult.failed("操作失败");
        }

        return CommonResult.success();
    }

}
