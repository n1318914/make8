package com.yundaren.common.constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class RegexUtil {
	public static boolean isEmail(String str) {
		if (str == null || "".equals(str.trim()))
			return true;
		String regex = "^([\\w-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		return match(regex, str);
	}

	public static boolean isIP(String str) {
		String num = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";
		String regex = "^" + num + "\\." + num + "\\." + num + "\\." + num + "$";
		return match(regex, str);
	}

	public static boolean isUrl(String str) {
		String regex = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
		return match(regex, str);
	}

	public static boolean isTelephone(String str) {
		if (str == null || "".equals(str.trim()))
			return true;
		String result;
		String regex = "^[0-9]+?[0-9-()]*[0-9()]+?$|^[0-9]+?$|^$";
		String regex1 = "\\-{2,}";
		String regex2 = "\\({2,}";
		String regex3 = "\\){2,}";
		result = str.replaceAll(regex1, " ");
		result = result.replaceAll(regex2, " ");
		result = result.replaceAll(regex3, " ");
		// String regex = "^\\d{1,18}$";
		return match(regex, str) && str.length() == result.length();
	}

	public static boolean isPassword(String str) {
		String regex = "[A-Za-z]+[0-9]";
		return match(regex, str);
	}

	public static boolean isPasswLength(String str) {
		String regex = "^.{6,20}$";
		return match(regex, str);
	}

	public static boolean isPostalcode(String str) {
		String regex = "^\\d{6}$";
		return match(regex, str);
	}

	public static boolean isMobile(String str) {
		String regex = "^(0|86|17951)?(13[0-9]|15[012356789]|17[0678]|18[0-9]|14[57])[0-9]{8}$";
		// String regex = "^\\d{1,18}$";
		return match(regex, str);
	}

	public static boolean isIDcard(String idCard) {
		//String regex = "(^\\d{18}$)|(^\\d{15}$)";
		//return match(regex, str);
		
		//initialize the area code
		HashMap<String,String> areas = new HashMap();
		
		areas.put("11","北京");
		areas.put("12","天津");
		areas.put("13","河北");
		areas.put("14","山西");
		areas.put("15","内蒙古");
		areas.put("21","辽宁");
		areas.put("22","吉林");
		areas.put("23","黑龙江");
		areas.put("31","上海");
		areas.put("32","江苏");
		areas.put("33","浙江");
		areas.put("34","安徽");
		areas.put("35","福建");
		areas.put("36","江西");
		areas.put("37","山东");
		areas.put("41","河南");
		areas.put("42","湖北");
		areas.put("43","湖南");
		areas.put("44","广东");
		areas.put("45","广西");
		areas.put("46","海南");
		areas.put("50","重庆");
		areas.put("51","四川");
		areas.put("52","贵州");
		areas.put("53","云南");
		areas.put("54","西藏");
		areas.put("61","陕西");
		areas.put("62","甘肃");
		areas.put("63","青海");
		areas.put("64","宁夏");
		areas.put("65","新疆");
		areas.put("71","台湾");
		areas.put("81","香港");
		areas.put("82","澳门");
		areas.put("91","国外");
		
	   
	    String m, JYM;
	    int s, y;
	        
	    if(areas.get(idCard.substring(0, 2)) == null){
	    	return false; //身份证地区非法
	    }
	    
	    String regex;
	    
	    switch(idCard.length()){
	        case 15:
	            if((new Integer(idCard.substring(6,8)).intValue() + 1900) % 4 == 0 || 
	                 ((new Integer(idCard.substring(6,8)).intValue() + 1900) % 100 == 0 && 
	                  (new Integer(idCard.substring(6,8)).intValue() + 1900) % 4 == 0)){
	            	
	            	//测试出生日期的合法性
	            	regex = "^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$"; 
	            }else{
	            	//测试出生日期的合法性
	            	regex = "^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$"; 
	            }
	            
	            if(match(regex,idCard)){
	               return true;
	            }
	            else{
	            	return false;
	            }
	        case 18:
	            if (new Integer(idCard.substring(6,10)).intValue() % 4 == 0 ||
	                (new Integer(idCard.substring(6,10)).intValue() % 100 == 0 && 
	                 new Integer(idCard.substring(6,10)).intValue() % 4 == 0)){
	            	//闰年出生日期的合法性正则表达式
	            	regex = "^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$"; 
	            }else{
	            	//平年出生日期的合法性正则表达式
	            	regex = "^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$"; 
	            }
	            
	            if(match(regex,idCard)){
	            	s = (new Integer(idCard.substring(0,1)).intValue() + new Integer(idCard.substring(10,11)).intValue()) * 7 +
	            		(new Integer(idCard.substring(1,2)).intValue() + new Integer(idCard.substring(11,12)).intValue()) * 9 +
	            	    (new Integer(idCard.substring(2,3)).intValue() + new Integer(idCard.substring(12,13)).intValue()) * 10 +	
	            	    (new Integer(idCard.substring(3,4)).intValue() + new Integer(idCard.substring(13,14)).intValue()) * 5 +	
	            	    (new Integer(idCard.substring(4,5)).intValue() + new Integer(idCard.substring(14,15)).intValue()) * 8 +	
	            	    (new Integer(idCard.substring(5,6)).intValue() + new Integer(idCard.substring(15,16)).intValue()) * 4 +	
	            	    (new Integer(idCard.substring(6,7)).intValue() + new Integer(idCard.substring(16,17)).intValue()) * 2 +
	            	     new Integer(idCard.substring(7,8)).intValue() * 1 + 
	            	     new Integer(idCard.substring(8,9)).intValue() * 6 + 
	            	     new Integer(idCard.substring(9,10)).intValue() * 3;
	            	
	            	y = s % 11;
	            	m = "F";
	                JYM = "10X98765432";
	    
	                m = JYM.substring(y,y+1);
	                if(m.equals(idCard.substring(17))){
	                	return true;
	                }else{
	                	return false; //身份证号码校验错误
	                }
	            }else{
	            	return false; //身份证号码出生日期超出范围或含有非法字符
	            }
	        default:
	            return false; //身份证号码位数不对
	    }
	}

	public static boolean isDecimal(String str) {
		String regex = "^[\\-\\+]?[0123456789]+(.[0123456789]*)?$";
		if (match(regex, str)) {
			try {
				double a = Double.parseDouble(str);
			} catch (Exception e) {
				return false;
			}
		} else {
			return false;
		}
		return true;
	}

	public static boolean isSignlessDecimal(String str) {
		String regex = "^[\\+]?[0-9]+(.[0-9]*)?$";
		return match(regex, str);
	}

	public static boolean isDecimalLength(String str, int intlen, int digitallen) {
		str = str.replace("[\\-\\+]", "");
		String[] strs = str.split("\\.");
		if (strs.length > 1) {
			if (digitallen < 0) {
				return !(strs[0].length() > intlen);
			} else {
				return !(strs[0].length() > intlen || strs[1].length() > digitallen);
			}
		} else {
			return !(strs[0].length() > intlen);
		}
	}
	
	public static boolean isMonth(String str) {
		String regex = "^(0?[[1-9]|1[0-2])$";
		return match(regex, str);
	}

	public static boolean isBeforeToday(String str) {
		Date nowdate = new Date();
		Date d;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		try {
			d = sdf.parse(str);
			cal.setTime(d);
			cal.add(Calendar.DATE, 1);
			if (nowdate.before(cal.getTime())) {
				return true;
			}
		} catch (Exception e) {
		}
		return false;
	}

	public static boolean isDay(String str) {
		String regex = "^((0?[1-9])|((1|2)[0-9])|30|31)$";
		return match(regex, str);
	}

	public static boolean isDate(String str) {
		String regex = "^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-)) (20|21|22|23|[0-1]?\\d):[0-5]?\\d:[0-5]?\\d$";
		return match(regex, str);
	}

	public static boolean isNumber(String str) {
		String regex = "^[0-9]*$";
		return match(regex, str);
	}

	public static boolean isIntNumber(String str) {
		if ("0".equals(str))
			return true;
		String regex = "^[\\-\\+]?[1-9][0-9]*$";
		return match(regex, str);
	}

	public static boolean isUpChar(String str) {
		String regex = "^[A-Z]*$";
		return match(regex, str);
	}

	public static boolean isLowChar(String str) {
		String regex = "^[a-z]+$";
		return match(regex, str);
	}

	public static boolean isLetter(String str) {
		String regex = "^[A-Za-z]+$";
		return match(regex, str);
	}
	
	public static boolean isDigit(String str) {
		String regex = "^[0-9]*$";
		return match(regex, str);
	}

	public static boolean match(String regex, String str) {
		if (StringUtils.isEmpty(str)) {
			return false;
		}
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.lookingAt();
	}

	public static boolean isBlank(String str) {
		if (str == null || "".equals(str.trim())) {
			return true;
		}
		return false;
	}

	public static boolean isSelected(String[] select) {
		if (select == null || select.length == 0) {
			return false;
		}
		return true;
	}

	public static boolean isLength(String str, int minlen, int maxlen) {
		int len = 0;
		if (str != null) {
			len = str.length();
		}
		if (len >= minlen && len <= maxlen) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isRepeat(String[] strs) {
		ArrayList search = new ArrayList();
		for (int i = 0; i < strs.length; i++) {
			String str = strs[i];
			if ("".equals(str)) {
				continue;
			}
			int find = Arrays.binarySearch(search.toArray(), str);
			if (find >= 0) {
				return true;
			}
			search.add(str);
		}
		return false;
	}

	public static boolean isEmpty(String[] strs) {
		for (int i = 0; i < strs.length; i++) {
			if (strs[i] == null || "".equals(strs[i])) {
				return true;
			}
		}
		return false;
	}

	public static boolean hasFullText(String str) {
		if (str == null)
			return false;
		String hanzen = str.replaceAll("[\\x00-\\xff]", "**");
		return hanzen.length() == str.length() ? true : false;
	}
	
	public static void main(String[] args) {
//		System.out.println(RegexUtil.isUpChar("ASDZAXXX X"));
//		System.out.println(RegexUtil.isLowChar("asda1sqwqeqqr"));
//		System.out.println(RegexUtil.isLetter("asdaXXsqwqeqqr"));
//		
//		System.out.println(RegexUtil.isDecimal("1231.23"));
//		System.out.println(RegexUtil.isSignlessDecimal("A12312.3 asdasd123123123 aaaaaaaaaaaaa 1111111111111"));
		String regex = "^[\\w ]+$";
		System.out.println(RegexUtil.isDigit("12301"));
//		System.out.println(match(regex,"17727831879"));
	}
}