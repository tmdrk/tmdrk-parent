package org.tmdrk.toturial.common.util;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.CollectionUtils;
import org.tmdrk.toturial.common.util.log.SettlementVo;

import com.alibaba.fastjson.JSON;

public class CompareAndAssembleUtil {
	public static final String suf = "Old";//后缀
	public static void main(String[] args) {
//		Map<String,Object> map = new HashMap<String,Object>();
//		map.put("id", 222222222);
//		map.put("name", "芜湖");
//		map.put("startTime", new Date().getTime());
//		SettlementVo oldSV = new SettlementVo();
//		oldSV.setId(1);
//		oldSV.setName("张三");
//		oldSV.setNickName("皮鞋");
//		oldSV.setCurrent("喂马");
//		oldSV.setStartTime(new Date());
//		String str = JSON.toJSONString(oldSV);
//		System.color.println(str);
//		System.color.println(JSON.parseObject(str, SettlementVo.class));
//		String strMap =JSON.toJSONString(map);
//		System.color.println(JSON.parseObject(strMap, SettlementVo.class));
		
		SettlementVo oldSV = new SettlementVo();
		oldSV.setId(1);
		oldSV.setName("张三");
		oldSV.setNickName("皮鞋");
		oldSV.setCurrent("喂马");
		oldSV.setStartTime(new Date());
		oldSV.setMoney(new BigDecimal(34));
		SettlementVo newSV = new SettlementVo();
		newSV.setId(1);
		newSV.setName("李四");
		newSV.setCurrent("");
		newSV.setStartTime(new Date(1000000));
		newSV.setMoney(new BigDecimal(34.000));
		List<String> inList = Arrays.asList("name","startTime","id");
		List<String> duList = Arrays.asList("name","id");
		Map<String,String> repNewMap = new HashMap<String,String>();
		repNewMap.put("startTime", "openTime");
		Map<String,Object> map = compareAndAssemble(oldSV,newSV,inList,duList,repNewMap);
		System.out.println(JSON.toJSONString(map));
	}
	public static <E> List<Map<String,Object>> compareAndAssembleInclude(List<E> oList,List<E> nList,
			List<String> inList,List<String> dubbleList,Map<String,String> repNewMap,Map<String,String> repOldMap){
		if(CollectionUtils.isEmpty(oList)||CollectionUtils.isEmpty(nList)){
			throw new RuntimeException("比较对象不能为空!");
		}
		if(oList.size()!=nList.size()){
			throw new RuntimeException("比较对象大小不一致，无法比较!");
		}
		List<Map<String,Object>> retList = new ArrayList<Map<String,Object>>();
		for(int i=0;i<oList.size();i++){
			Map<String,Object> result = compareAndAssemble(oList.get(i),nList.get(i),inList,dubbleList,repNewMap,repOldMap);
			retList.add(result);
		}
		return retList;
	}
//	public static <E,F> List<Map<String,Object>> compareAndAssembleExclude(E o,F n,List<String> excludeList){
//		return compareAndAssemble(o,n,null,list);
//	}
	public static <E> Map<String,Object> compareAndAssemble(E o,E n){
		return compareAndAssemble(o,n,null);
	}
	public static <E> Map<String,Object> compareAndAssemble(E o,E n,List<String> compList){
		return compareAndAssemble(o,n,compList,null);
	}
	public static <E> Map<String,Object> compareAndAssemble(E o,E n,List<String> compList,List<String> dubbleList){
		return compareAndAssemble(o,n,compList,dubbleList,null,null);
	}
	public static <E> Map<String,Object> compareAndAssemble(E o,E n,List<String> compList,
			List<String> dubbleList,Map<String,String> repNewMap){
		return compareAndAssemble(o,n,compList,dubbleList,repNewMap,null);
	}
	public static <E> Map<String,Object> compareAndAssemble(E o,E n,List<String> compList,
			List<String> dubbleList,Map<String,String> repNewMap,Map<String,String> repOldMap){
		if(!o.getClass().equals(n.getClass())){
			throw new RuntimeException("传入比较对象的类型不匹配 !");
		}
		Field[] fields = o.getClass().getDeclaredFields();
		Map<String,Object> retMap = new HashMap<String,Object>();
		Object ov,nv;
		String fieldName;
		for(Field field : fields){
			field.setAccessible(true);
			fieldName = field.getName();
			if(compList==null||(compList!=null&&compList.contains(fieldName.toString()))){
				try {
					ov = field.get(o);
					nv = field.get(n);
					checkType(retMap, ov, nv, fieldName,dubbleList,repNewMap,repOldMap);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return retMap;
	}
	private static void checkType(Map<String, Object> retMap, Object ov,Object nv,String fieldName
			,List<String> dubbleList,Map<String,String> repNewMap,Map<String,String> repOldMap) {
		if(ov instanceof String){
			doAssemble(retMap, (String)ov, (String)nv, fieldName, dubbleList, repNewMap,repOldMap);
		}else if(ov instanceof Integer){
			doAssemble(retMap, (Integer)ov, (Integer)nv, fieldName, dubbleList, repNewMap,repOldMap);
		}else if(ov instanceof Byte){
			doAssemble(retMap, (Byte)ov, (Byte)nv, fieldName, dubbleList, repNewMap,repOldMap);
		}else if(ov instanceof Long){
			doAssemble(retMap, (Long)ov, (Long)nv, fieldName, dubbleList, repNewMap,repOldMap);
		}else if(ov instanceof Short){
			doAssemble(retMap, (Short)ov, (Short)nv, fieldName, dubbleList, repNewMap,repOldMap);
		}else if(ov instanceof Double){
			doAssemble(retMap, (Double)ov, (Double)nv, fieldName, dubbleList, repNewMap,repOldMap);
		}else if(ov instanceof Float){
			doAssemble(retMap, (Float)ov, (Float)nv, fieldName, dubbleList, repNewMap,repOldMap);
		}else if(ov instanceof Boolean){
			doAssemble(retMap, (Boolean)ov, (Boolean)nv, fieldName, dubbleList, repNewMap,repOldMap);
		}else if(ov instanceof Character){
			doAssemble(retMap, (Character)ov, (Character)nv, fieldName, dubbleList, repNewMap,repOldMap);
		}else if(ov instanceof BigDecimal){
			doAssemble(retMap, (BigDecimal)ov, (BigDecimal)nv, fieldName, dubbleList, repNewMap,repOldMap);
		}else if(ov instanceof Date){
			doAssemble(retMap, (Date)ov, (Date)nv, fieldName, dubbleList, repNewMap,repOldMap);
		}else{
			doAssemble(retMap, ov, nv, fieldName, dubbleList, repNewMap,repOldMap);
		}
	}
	private static <T> void doAssemble(Map<String, Object> retMap, T ov,
			T nv, String fieldName, List<String> dubbleList,
			Map<String, String> repNewMap, Map<String, String> repOldMap) {
		if(!equ(ov,nv)){
			if(dubbleList!=null&&dubbleList.contains(fieldName)){
				retMap.put((repOldMap==null||!repOldMap.containsKey(fieldName))?
						fieldName+suf:repOldMap.get(fieldName),(T)ov);
			}
			retMap.put((repNewMap==null||!repNewMap.containsKey(fieldName))?
					fieldName:repNewMap.get(fieldName),(T)nv);
		}
	}
	
	/**
	 * 比较两个对象
	 * @Title: compareValue 
	 * @Description: TODO 
	 * @param ov
	 * @param nv
	 * @return boolean
	 * @author zhoujie
	 * @date 2018年4月11日下午7:10:54
	 */
	public static <T> boolean equ(T ov,T nv){
		if(ov==nv||(ov!=null)&&(ov.equals(nv))){
			return true;
		}
		return false;
	}
}
