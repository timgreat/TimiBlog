package com.timi.utils;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class BeanCopyUtils {
    public static <T> T copyBean(Object o, Class<T> c){
        T result=null;
        try {
            result=c.newInstance();
            BeanUtils.copyProperties(o,result);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public static <O,T> List<T> copyBeanList(List<O> list,Class<T> cla){

        return list.stream()
                .map(o -> copyBean(o,cla))
                .collect(Collectors.toList());
    }
}
