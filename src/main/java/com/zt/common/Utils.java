/**  
* 
*/  
 
package com.zt.common;

import java.io.*;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.expression.ParseException;

/**
 * @author whl
 * @date 2019年5月15日 
 */
public class Utils {

	public final static String FORMAT_STRING = "yyyy-MM-dd HH:mm:ss";

	public final static String FORMAT_STRING2 = "EEE MMM dd yyyy HH:mm:ss z";

	public final static String[] REPLACE_STRING = new String[]{"GMT+0800", "GMT+08:00"};

	public final static String SPLIT_STRING = "(中国标准时间)";

	public static String parseTimeZone(String dateString) {
		try {
			dateString = dateString.split(Pattern.quote(SPLIT_STRING))[0].replace(REPLACE_STRING[0], REPLACE_STRING[1]);
			//转换为date
			SimpleDateFormat sf1 = new SimpleDateFormat(FORMAT_STRING2, Locale.ENGLISH);
			Date date = sf1.parse(dateString);
			return new SimpleDateFormat(FORMAT_STRING).format(date);
		} catch (Exception e) {
			throw new RuntimeException("时间转化格式错误" + "[dateString=" + dateString + "]" + "[FORMAT_STRING=" + FORMAT_STRING + "]");
		}
	}

//	public static <T> T[] convertArray(Class<T> targetType, Object[] arrayObjects) {
//        if (targetType == null) {
//            return (T[]) arrayObjects;
//        }
//        if (arrayObjects == null) {
//            return null;
//        }
//        T[] targetArray = (T[]) Array.newInstance(targetType, arrayObjects.length);
//        try {
//            System.arraycopy(arrayObjects, 0, targetArray, 0, arrayObjects.length);
//        } catch (ArrayStoreException e) {
//        	e.printStackTrace();
//        }
//        return targetArray;
//
//    }
	public static List<Date> getTwoDaysDay(String dateStart, String dateEnd) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<String> dateList = new ArrayList<String>();
		if (dateStart.equals(dateEnd)) {
			dateList.add(dateStart);
		}else {
			try{
				Date dateOne = sdf.parse(dateStart);
				Date dateTwo = sdf.parse(dateEnd);
				
				Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
				calendar.setTime(dateTwo);
				
				dateList.add(dateEnd);
				while(calendar.getTime().after(dateOne)){ //倒序时间,顺序after改before其他相应的改动。
					calendar.add(Calendar.DAY_OF_MONTH, -1); 
					dateList.add(sdf.format(calendar.getTime()));
				}
			} catch(Exception e){
				e.printStackTrace();
			}
		}
		List<Date> datelist=new ArrayList<Date>();
		for (String string : dateList) {
			
				try {
					datelist.add(sdf.parse(string));
				} catch (java.text.ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
		}
		return datelist;
		}
	 /**
	 * @param mpNo 起始编号
	 * @param n  位数
	 * @param id  存在的最大编号
	 * @return
	 */
	public static String currentNo(String mpNo,int n,Long id) {
		
		if(id!=null) {
			id+=1;
			StringBuilder sb =  new StringBuilder();
			sb.append("%0");
			sb.append(n);
			sb.append("d");
			String string = sb.toString();
			mpNo = String.format(string,id);
		}
		
		return mpNo;
	}


	/**
	 *
	 * @param planNo 获取表中的最大编号
	 * @param ty 编号前的字母
	 * @return
	 */
	public static String newPlanNo(String planNo,String ty) {
		String newPlanNo ="";
		SimpleDateFormat s = new SimpleDateFormat("yyyyMMdd");
		StringBuilder sb = new StringBuilder();
		if (planNo == null||planNo.equals("")) {
			sb.append(ty)
					.append(Long.parseLong(s.format(new Date())))
					.append(String.format("%02d",UsersUtils.getCurrentHr().getEmpId()))
					.append(String.format("%04d",1));
			newPlanNo = String.format("%19s", sb.toString()).trim();

		} else {
			String regEx="[^0-9]";
			Pattern p = Pattern.compile(regEx);
			Matcher m = p.matcher(planNo);
			String str = m.replaceAll("").trim();
			//去除前八位数字
//			String substring = str.substring(10);
			int length = str.length();
			String substring = str.substring(length-4,length);
			System.out.println(substring);
			sb.append(ty)
					.append(Long.parseLong(s.format(new Date())))
					.append(String.format("%02d",UsersUtils.getCurrentHr().getEmpId()))
					.append(String.format("%04d",Integer.parseInt(substring)+1));
			newPlanNo = String.format("%19s", sb.toString()).trim();
		}
		return newPlanNo;
	}

	/*
	时间转换
	 */
	public static Map<String,String>  updateTime(String ...days){
		Map<String,String>map=new HashMap<>();
		for (int i = 0; i < days.length; i++) {
			 if(days[i]!=null&&!(days[i].equals(""))){
				 map.put(i+"",Utils.parseTimeZone(days[i]));
			 }
		}
		return map;
	}
    /**
     * 克隆方法
	 */
	public static <T extends Serializable> T clone(T obj){
		T cloneObj = null;
		try {
			//写入字节流
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ObjectOutputStream obs = new ObjectOutputStream(out);
			obs.writeObject(obj);
			obs.close();

			//分配内存，写入原始对象，生成新对象
			ByteArrayInputStream ios = new ByteArrayInputStream(out.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(ios);
			//返回生成的新对象
			cloneObj = (T) ois.readObject();
			ois.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cloneObj;
	}



}
