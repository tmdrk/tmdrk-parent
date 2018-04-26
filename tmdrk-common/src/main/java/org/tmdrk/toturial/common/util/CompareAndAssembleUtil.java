package org.tmdrk.toturial.common.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.CollectionUtils;
import org.tmdrk.toturial.common.util.log.LogUtil;
import org.tmdrk.toturial.common.util.log.SettlementVo;

import com.alibaba.fastjson.JSON;

public class CompareAndAssembleUtil {
	public static void main(String[] args) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("name", "会会");
		map.put("sex", null);
		map.put("id", "");
		SettlementVo oldSV = new SettlementVo();
		oldSV.setId(1);
		oldSV.setName("张三");
//		oldSV.setNickName("皮鞋");
		oldSV.setCurrent("喂马");
		oldSV.setStartTime(new Date());
		String str = JSON.toJSONString(oldSV);
		System.out.println(str);
//		System.out.println(JSON.toJSONString(map));
		System.out.println(JSON.parseObject(str, SettlementVo.class));
	}
	public static <E> List<Map<String,Object>> compareAndAssembleInclude(List<E> oList,List<E> nList,List<String> inList){
		if(CollectionUtils.isEmpty(oList)||CollectionUtils.isEmpty(nList)){
			throw new RuntimeException("比较对象不能为空!");
		}
		if(oList.size()!=nList.size()){
			throw new RuntimeException("比较对象大小不一致，无法比较!");
		}
		List<Map<String,Object>> retList = new ArrayList<Map<String,Object>>();
		for(int i=0;i<oList.size();i++){
			Map<String,Object> result = compareAndAssemble(oList.get(i),nList.get(i),inList);
			retList.add(result);
		}
		return retList;
	}
//	public static <E,F> List<Map<String,Object>> compareAndAssembleExclude(E o,F n,List<String> excludeList){
//		return compareAndAssemble(o,n,null,list);
//	}
	public static <E> Map<String,Object> compareAndAssemble(E o,E n,List<String> compList ){
		if(!o.getClass().equals(n.getClass())){
			throw new RuntimeException("传入比较对象的类型不匹配 !");
		}
		Field[] fields = (Field[]) LogUtil.fieldCache.get(o.getClass().toString());
		Map<String,Object> retMap = new HashMap<String,Object>();
		String oldValue,newValue;
		
		for(Field fieldName : fields){
			fieldName.setAccessible(true);
			if(compList==null||(compList!=null&&compList.contains(fieldName.toString()))){
				try {
					oldValue = fieldName.get(o)==null?null:fieldName.get(o).toString();
					newValue = fieldName.get(n)==null?null:fieldName.get(n).toString();
					if(!compareValue(oldValue,newValue)){
						retMap.put(fieldName.getName(),newValue);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return retMap;
	}
	
	/**
	 * 比较两个字符串
	 * @Title: compareValue 
	 * @Description: TODO 
	 * @param ov
	 * @param nv
	 * @return boolean
	 * @author zhoujie
	 * @date 2018年4月11日下午7:10:54
	 */
	public static boolean compareValue(String ov,String nv){
		if(ov==nv){
			return true;
		}
		if((ov!=null&&nv!=null)&&(ov.equals(nv))){
			return true;
		}
		return false;
	}
}
