package cn.m1yellow.mypages.common.util;


import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 接口或方法通用参数校验工具类
 */
@Slf4j
public class CheckParamUtil {

    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * <b>基础参数校验</b><br>
     * bean 整体校验，有校验不通过的，抛出首个错误信息（不用一次性给出所有错误信息，太多错误，会把用户吓着，体验不好）
     */
    public static void validate(Object obj, Class<?>... groups) {
        Set<ConstraintViolation<Object>> resultSet = validator.validate(obj, groups);
        if (null != resultSet && !resultSet.isEmpty()) {
            // 如果存在错误结果，则将其解析并进行拼凑后异常抛出
            List<String> errorMessageList = resultSet.stream().map(o -> o.getMessage()).collect(Collectors.toList());
            StringBuilder errorMessage = new StringBuilder();
            errorMessageList.forEach(o -> errorMessage.append(o).append(";"));

            log.info(">>>> errorMessage: {}", errorMessage);

            String message = resultSet.iterator().next().getMessage();
            throw new IllegalArgumentException(message);
        }
    }

}
