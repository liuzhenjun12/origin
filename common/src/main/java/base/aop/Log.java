package base.aop;

import java.lang.annotation.*;

/**
 * 记录日志
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
@Inherited
public @interface Log {
    public enum LOG_TYPE {ADD, UPDATE, DEL, SELECT, OPEN, LOGIN, REGIST, IMPORT, EXPORT,CONNECT}
    /**
     * 描述
     */
    String desc();

    /**
     * 类型 curd
     */
    LOG_TYPE type() default LOG_TYPE.ADD;
}
