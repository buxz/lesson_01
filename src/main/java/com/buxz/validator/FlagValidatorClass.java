package com.buxz.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by SQ_BXZ on 2018-12-17.
 */
public class FlagValidatorClass implements ConstraintValidator<FlagValidator,Object> {

    // 临时变量保存的flag值列表
    private String values;

    // 初始化values的值
    @Override
    public void initialize(FlagValidator flagValidator) {
        this.values = flagValidator.values();
    }

    // 实现验证
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        // 分割定义的有效值
        String[] value_array = values.split(",");
        boolean isFlag = false;

        // 遍历对比有效值
        for (String s : value_array) {
            if (s.equals(value)){
                isFlag = true;
                break;
            }
        }
        // 返回是否存在boolean
        return isFlag;
    }
}
