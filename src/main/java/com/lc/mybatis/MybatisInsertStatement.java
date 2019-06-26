package com.lc.mybatis;

import com.lc.model.User;

import java.lang.reflect.Field;

public class MybatisInsertStatement {
    public static void main(String[] args) {
        Class cl = User.class;
        Field[] fields = cl.getDeclaredFields();
        StringBuilder sb = new StringBuilder();
        for (Field item : fields) {
            sb.append("#{item.").append(item.getName()).append(",jdbcType=");
            //类型映射需要参考sql类型和java类型映射关系
            String temp = item.getType().getSimpleName();
            if ("String".equals(temp)) {
                sb.append("VARCHAR");
            } else if ("Double".equals(temp) || "Float".equals(temp)) {
                sb.append("DOUBLE");
            } else if ("Integer".equals(temp)) {
                sb.append("INTEGER");
            } else if ("BigDecimal".equals(temp)) {
                sb.append("DECIMAL");
            } else if ("DateTime".equals(temp)) {
                sb.append("TIMESTAMP,").append(" typeHandler=com.dmall.mybatis.generator.handler.TimestampTypeHandler");
            } else if ("Byte".equals(temp)) {
                sb.append("TINYINT");
            }
            sb.append("},").append('\n');
        }
        System.out.println(sb.toString());
    }
}
