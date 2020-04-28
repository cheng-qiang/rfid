package org.chen.rfid.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * @author 程强
 * @date 2020年03月23日 13:36
 * @Description:
 */
public class ListToArray {
    /**
     * 获取对象属性，返回一个字符串数组
     * @param o 对象
     * @return String[] 字符串数组
     */
    private String[] getFiledName(Object o) {
        try {
            Field[] fields = o.getClass().getDeclaredFields();
            String[] fieldNames = new String[fields.length];
            for (int i = 0; i < fields.length; i++) {
                fieldNames[i] = fields[i].getName();
            }
            return fieldNames;
        } catch (SecurityException e) {
            e.printStackTrace();
            System.out.println(e.toString());
        }
        return null;
    }

    /**
     * 使用反射根据属性名称获取t属性的get方法
     *
     * @param fieldNames 属性名称
     * @param o 操作对象
     * @return List<Method> get方法
     */
    private List<Method> getGetField(String[] fieldNames, Object o) {

        List<Method> methods=new ArrayList<Method>();
        for (String fieldName : fieldNames) {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = null;
            try {
                method = o.getClass().getMethod(getter, new Class[] {});
            } catch (NoSuchMethodException e) {
                System.out.println("属性不存在");
                continue;
            }
            //Object value = method.invoke(o, new Object[] {});
            methods.add(method);
        }
        return methods;
    }
    /**
     * 将list集合转换为二维string数组
     *
     * @param list 要转换的集合
     * @return String[][] 返回的sting数组
     */
    public String[][] listToArrayWay(List list) {
        Object o=list.get(0);
        String[] filedNames = getFiledName(o);
        int filedNum=filedNames.length;
        int listSize=list.size();
        List<Method> methods=getGetField(filedNames, o);

        String[][] arrs=new String[listSize][filedNum];
        int i=0;
        for (Object object : list) {
            int j=0;
            for (Method method : methods) {
                Object value=null;
                try {
                    value = method.invoke(object, new Object[] {});
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    System.out.println("属性不存在"+e);
                }
                if (value instanceof Date){
                    SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
                    arrs[i][j] = sdf.format(value);
                }else if (value instanceof Boolean){
                    arrs[i][j] = String.valueOf(value);
                }else if (value instanceof Long){
                    arrs[i][j] = String.valueOf(value);
                }else {
                    arrs[i][j]=(String) value;
                }
                j++;
            }
            i++;
        }
        return arrs;
    }

}

