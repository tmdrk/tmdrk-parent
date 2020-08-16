package org.tmdrk.toturial.spring.es;



import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.CollectionUtils;

import java.util.*;

public class BeanUtils extends org.springframework.beans.BeanUtils {

    public static <T> T createBeanByTarget(Object obj, Class<T> cls) {
        Object target = null;
        try {
            target = cls.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        copyProperties(obj, target);

        return (T) target;

    }

    public static <T> T createBeanByTarget(Object obj, Class<T> cls, String... ignoreProperties) {
        Object target = null;
        try {
            target = cls.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        copyProperties(obj, target, ignoreProperties);
        return (T) target;

    }

    public static <T> List<T> createBeanListByTarget(Collection objects, Class<T> cls) {
        List<T> results = new ArrayList<T>();
        if (CollectionUtils.isEmpty(objects)) {
            return results;
        }

        for (Object obj : objects) {
            T t = createBeanByTarget(obj, cls);
            results.add(t);
        }
        return results;
    }


    public static <T> IPage<T> createBeanPageByTarget(IPage page, Class<T> cls) {
        if (page != null && !CollectionUtils.isEmpty(page.getList())) {
            page.setList(createBeanListByTarget(page.getList(), cls));
        }
        return page;
    }

    public static <T> List<T> createBeanListByTarget(Collection objects, Class<T> cls, String... ignoreProperties) {
        List<T> results = new ArrayList<T>();
        if (CollectionUtils.isEmpty(objects)) {
            return results;
        }

        for (Object obj : objects) {
            T t = createBeanByTarget(obj, cls, ignoreProperties);
            results.add(t);
        }
        return results;
    }

    /**
     * @param source 要拷贝的对象
     * @return
     * @Description <p>获取到对象中属性为null的属性名  </P>
     */
    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

}
