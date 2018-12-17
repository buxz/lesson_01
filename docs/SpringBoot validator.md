### SpringBoot Validator
```text
内置验证：

@AssertTrue // 用于boolean字段，该字段只能为true
@AssertFalse // 用于boolean字段，该字段只能为false
@CreditCardNumber // 对卡号大致验证
@DecimalMax // 小于等于该值
@DecimalMin // 大于等于该值
@Digits (integet =2 , fraction =20)// 检查是否是一种数字的整数/分数/小数位数的数字
@Email // 
@Future // 是否是将来时间
@Past // 是否是过去时间
@Length(min= ， max= )
@Max
@Min
@NotNull // 
@NotBlank // 不能为空，可以为空字符串
@NotEmpty // 不能为空字符串
@Null // 
@Size(min = ,max =) // 可以校验字符串/数组/集合/Map
@URL(protocol =, host =) // 
@Valid()
```
## 自定义验证
自定义验证需要提供两个文件，一个是注解，另一个是对应注解
继承ConstrainValidator的实现类
1. 注解
```java
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Constraint(validatedBy = FlagValidatorClass.class )
public @interface FlagValidator {
    // flag的有效值，多个使用 “，”隔开
    String values();

    // 提示内容
    String message() default "flag不存在";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}

```

2. 对应注解继承ConstraintValidator的实现类

```java
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

```

3. 在对应字段上添加注解即可
```java
    // 需要验证 flag字段内容仅为 1，2，3
    @FlagValidator(values = "1,2,3")
    private String flag;

```





