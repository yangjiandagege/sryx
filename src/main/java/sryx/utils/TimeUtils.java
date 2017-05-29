package sryx.utils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class TimeUtils {
	
	private static Logger logger = Logger.getLogger(TimeUtils.class);
	
	private static Random r = new Random();
	private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	private static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");
	private static SimpleDateFormat sdf2 = new SimpleDateFormat("yyMMddHHmmssSSS");

	/**
	 * 当前时间年月日时分秒
	 * @return  yyyy-MM-dd HH:mm:ss
	 */
	public static String getCurrTime() {
		return df.format(new Date());
	}
	
	public static String getTimeString(String time) {
		try {
			return df.format(sdf.parse(time));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 当前时间年月日时分秒毫秒
	 * @return  yyyyMMddHHmmssSSS
	 */
	public static String getCurrTimeMillions() {
		return sdf.format(new Date());
	}
	
	public static String getCurrTimeSecondsStr() {
		return sdf1.format(new Date());
	}
	
	public static String getTimeSN() {
		String[] ops = new String[]{"SSS", "mm", "ss", "dd", "HH"};
		String pattn = "yy";
		Set<String> opSet = new HashSet<String>();
		while(true) {
			if(opSet.size() > 4) {
				break;
			}
			int c = r.nextInt(5);
			String oo = ops[c];
			if(!opSet.contains(oo)) {
				opSet.add(oo);
			}
		}
		for(String o:opSet) {
			pattn += o;
		}
		long _time = 0;
		DateFormat df = new SimpleDateFormat(pattn);
		String time = df.format(new Date());
		_time = Long.valueOf(time);
		_time = _time - r.nextInt(10);
		return String.valueOf(_time);
	}
	
	public static Date getDate(String dateString) {
		Date dt = null;
		try {
			dt = df.parse(dateString);
		} catch (ParseException e) {
			logger.error("parse date error." + e);
		}
		return dt;
	}
	
	
	
	/**
	 * 时间差
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static String timeDifs(long d1, long d2){
		long mss = d2 - d1;
		long days = mss / (1000 * 60 * 60 * 24);  
	    long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);  
	    long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);  
	    long seconds = (mss % (1000 * 60)) / 1000;  
	    return days + "-" + hours + "-" + minutes + "-"  
		            + seconds;
	}
	
	/**
	 * 增加减少时间
	 * @param time 时间
	 * @param oper 操作 j加，s减
	 * @param timeType 年月日时分秒
	 * @param num 数
	 * @return
	 * @throws ParseException 
	 */
	public static String timeChange(String time, String oper, String timeType, int num) throws ParseException{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = df.parse(time);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		if("j".equals(oper)){
			if("Y".equals(timeType)){
				c.add(Calendar.YEAR, num);
			}
			if("M".equals(timeType)){
				c.add(Calendar.MONTH, num);
			}
			if("D".equals(timeType)){
				c.add(Calendar.DAY_OF_MONTH, num);
			}
			if("h".equals(timeType)){
				c.add(Calendar.HOUR_OF_DAY, num);
			}
			if("m".equals(timeType)){
				c.add(Calendar.MINUTE, num);
			}
			if("s".equals(timeType)){
				c.add(Calendar.SECOND, num);
			}
		}else if("s".equals(oper)){
			if("Y".equals(timeType)){
				c.add(Calendar.YEAR, -num);
			}
			if("M".equals(timeType)){
				c.add(Calendar.MONTH, -num);
			}
			if("D".equals(timeType)){
				c.add(Calendar.DAY_OF_MONTH, -num);
			}
			if("h".equals(timeType)){
				c.add(Calendar.HOUR_OF_DAY,-num);
			}
			if("m".equals(timeType)){
				c.add(Calendar.MINUTE, -num);
			}
			if("s".equals(timeType)){
				c.add(Calendar.SECOND, -num);
			}
		}
		return df.format(c.getTime());
	}
	
	public static List<Long> daysBetween(Date smdate,Date bdate)
    {    
		List<Long> list = new ArrayList<Long>();
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        
        long days = (time2-time1)/(1000 * 60 * 60 * 24);
        long hours = (time2-time1)/(1000 * 60 * 60) - 24*days;
        long minutes = (time2-time1)/(1000 * 60)-24*60*days-60*hours;
        
        list.add(days);
        list.add(hours);
        list.add(minutes);
            
       return list;
    }
	
	public static List<Long> daysBetween(String smdate,String bdate) {
		List<Long> list = new ArrayList<Long>();
        Calendar cal = Calendar.getInstance();
        Date d1 = null, d2 = null;
        try {
        	d1 = df.parse(smdate);
        	d2 = df.parse(bdate);
	    } catch(Exception ex) {
	    	logger.error("parse date error.", ex);
	    }
        cal.setTime(d1);
        long time1 = cal.getTimeInMillis();
        cal.setTime(d2);
        long time2 = cal.getTimeInMillis();
        
        long days = (time2-time1)/(1000 * 60 * 60 * 24);
        long hours = (time2-time1)/(1000 * 60 * 60) - 24*days;
        long minutes = (time2-time1)/(1000 * 60)-24*60*days-60*hours;
        
        list.add(days);
        list.add(hours);
        list.add(minutes);
            
       return list;
    }
	
	public static String makeAtUserId(){
		SimpleDateFormat df = new SimpleDateFormat("yyssSSS");
		StringBuffer fm = new StringBuffer(); 
		fm.append("KL");
		fm.append(df.format(new Date()));
		Random r = new Random();
		for(int i = 0; i < 6; i++){
			fm.append(r.nextInt(9));
		}
		return fm.toString();
	}
	
	public static String makeProductId(){
		SimpleDateFormat df = new SimpleDateFormat("yyssSSS");
		StringBuffer fm = new StringBuffer(); 
		fm.append("PD");
		fm.append(df.format(new Date()));
		Random r = new Random();
		for(int i = 0; i < 6; i++){
			fm.append(r.nextInt(9));
		}
		return fm.toString();
	}
	
	/**
	 * ������˾��,����Ϊ:CP+��ݺ���λ+��+����+��λ�����,��ʮ��λ��
	 * @return
	 */
	public static String makeCompanyId(){
		SimpleDateFormat df = new SimpleDateFormat("yyssSSS");
		StringBuffer fm = new StringBuffer(); 
		fm.append("CP");
		fm.append(df.format(new Date()));
		Random r = new Random();
		for(int i = 0; i < 6; i++){
			fm.append(r.nextInt(9));
		}
		return fm.toString();
	}
	
	/**
	 * 生成部门ID
	 * @return
	 */
	public static String makeDepartmentId(){
		SimpleDateFormat df = new SimpleDateFormat("yyssSSS");
		StringBuffer fm = new StringBuffer(); 
		fm.append("DP");
		fm.append(df.format(new Date()));
		Random r = new Random();
		for(int i = 0; i < 6; i++){
			fm.append(r.nextInt(9));
		}
		return fm.toString();
	}
	
	/**
	 * ����������,����ΪPW+���+��+��λ�����,��ʮ��λ��
	 * @return
	 */
	public static String makePasswordId(){
		SimpleDateFormat df = new SimpleDateFormat("yyssSSS");
		StringBuffer fm = new StringBuffer(); 
		fm.append("PW");
		fm.append(df.format(new Date()));
		Random r = new Random();
		for(int i = 0; i < 6; i++){
			fm.append(r.nextInt(9));
		}
		return fm.toString();
	}
	
	public static String makeChannelId() {
		SimpleDateFormat df = new SimpleDateFormat("yyssSSS");
		StringBuffer fm = new StringBuffer(); 
		fm.append("CI");
		fm.append(df.format(new Date()));
		Random r = new Random();
		for(int i = 0; i < 6; i++){
			fm.append(r.nextInt(9));
		}
		return fm.toString();
	}
	
	public static String makeMarketStoreId(){
		SimpleDateFormat df = new SimpleDateFormat("yyssSSS");
		StringBuffer fm = new StringBuffer(); 
		fm.append("MS");
		fm.append(df.format(new Date()));
		Random r = new Random();
		for(int i = 0; i < 6; i++){
			fm.append(r.nextInt(9));
		}
		return fm.toString();
	}
	
	public static String makeLeasingContractId(){
		SimpleDateFormat df = new SimpleDateFormat("yyssSSS");
		StringBuffer fm = new StringBuffer(); 
		fm.append("LC");
		fm.append(df.format(new Date()));
		Random r = new Random();
		for(int i = 0; i < 6; i++){
			fm.append(r.nextInt(9));
		}
		return fm.toString();
	}
	
	public static String getRandomNum(int length){
		StringBuffer fm = new StringBuffer();
		Random r = new Random();
		for(int i = 0; i < length; i++){
			fm.append(r.nextInt(9));
		}
		return fm.toString();
	}
	
	public static String getUUID(){
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replaceAll("-", "");
	}
	
	public static String makeNotifyId(){
		return getUUID();
	}
	
	public static String makeOrderNum(){
		return getTimeSN();
	}
	
	public static String makeTradeNum(){
		return getTimeSN() + r.nextInt(1);
	}
	
	/**
	 * 获取IP地址
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public final static String getIpAddress(HttpServletRequest request) throws IOException {
		// ��ȡ��������IP��ַ,���ͨ�������������͸������ǽ��ȡ��ʵIP��ַ

		String ip = request.getHeader("X-Forwarded-For");
		if (logger.isInfoEnabled()) {
			logger.info("getIpAddress(HttpServletRequest) - X-Forwarded-For - String ip=" + ip);
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
				if (logger.isInfoEnabled()) {
					logger.info("getIpAddress(HttpServletRequest) - Proxy-Client-IP - String ip=" + ip);
				}
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
				if (logger.isInfoEnabled()) {
					logger.info("getIpAddress(HttpServletRequest) - WL-Proxy-Client-IP - String ip=" + ip);
				}
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_CLIENT_IP");
				if (logger.isInfoEnabled()) {
					logger.info("getIpAddress(HttpServletRequest) - HTTP_CLIENT_IP - String ip=" + ip);
				}
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_X_FORWARDED_FOR");
				if (logger.isInfoEnabled()) {
					logger.info("getIpAddress(HttpServletRequest) - HTTP_X_FORWARDED_FOR - String ip=" + ip);
				}
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
				if (logger.isInfoEnabled()) {
					logger.info("getIpAddress(HttpServletRequest) - getRemoteAddr - String ip=" + ip);
				}
			}
		} else if (ip.length() > 15) {
			String[] ips = ip.split(",");
			for (int index = 0; index < ips.length; index++) {
				String strIp = (String) ips[index];
				if (!("unknown".equalsIgnoreCase(strIp))) {
					ip = strIp;
					break;
				}
			}
		}
		return ip;
	}
	
	public static String serviceMapping(String type, String source){
		String target = null;
		if("u".equals(type.toLowerCase())){
			target = source.replace("A", "U");
		}
		return target;
	}
	
	/**
	 * ����������־Id,����ΪOP+���+��+��λ�����,��ʮ��λ��
	 * @return
	 */
	public static String makeOperId(){
		SimpleDateFormat df = new SimpleDateFormat("yyssSSS");
		StringBuffer fm = new StringBuffer(); 
		fm.append("OP");
		fm.append(df.format(new Date()));
		Random r = new Random();
		for(int i = 0; i < 6; i++){
			fm.append(r.nextInt(9));
		}
		return fm.toString();
	}
	
	/**
	 * ������¼��־Id,����ΪLG+���+��+��λ�����,��ʮ��λ��
	 * @return
	 */
	public static String makeLoginId(){
		SimpleDateFormat df = new SimpleDateFormat("yyssSSS");
		StringBuffer fm = new StringBuffer(); 
		fm.append("LG");
		fm.append(df.format(new Date()));
		Random r = new Random();
		for(int i = 0; i < 6; i++){
			fm.append(r.nextInt(9));
		}
		return fm.toString();
	}
	
	public static String makePageNum(){
		StringBuffer fm = new StringBuffer(); 
		fm.append(sdf2.format(new Date()));
		return fm.toString();
	}
	
	/**  
     * @param date1 开始月份  
     * @param date2 结束月份  
     * @param stype 返回值类型   0为天，1为月，2为年  
     * @return 返回结果中包含开始月份,不包含结束月份
     */ 
    public static List<String> getConcMonth(String date1,String date2,int stype){  
        int n = 0;  
           
        String[] u = {"天","月","年"};  
        String formatStyle = stype==1?"yyyy-MM":"yyyy-MM-dd";  
           
//        date2 = date2==null?DateTest.getCurrentDate():date2;  
           
        List<String> months = new ArrayList<String>();
        DateFormat df = new SimpleDateFormat(formatStyle);  
        Calendar c1 = Calendar.getInstance();  
        Calendar c2 = Calendar.getInstance();  
        try {  
            c1.setTime(df.parse(date1));  
            c2.setTime(df.parse(date2));  
        } catch (Exception e3) {  
            System.out.println("wrong occured");  
        }  
        //List list = new ArrayList();  
        while (!c1.after(c2)) {                     // 循环对比，直到相等，n 就是所要的结果  
            //list.add(df.format(c1.getTime()));    // 这里可以把间隔的日期存到数组中 打印出来  
//            n++;  
            if(stype==1){  
            	String y = String.valueOf(c1.get(Calendar.YEAR));
            	int t = c1.get(Calendar.MONTH) + 1;
            	String m = t < 10 ? "0" + t : String.valueOf(t);
            	String ym = y + "-" + m;
            	months.add(ym);
                c1.add(Calendar.MONTH, 1);          // 比较月份，月份+1
            }  
            else{  
                c1.add(Calendar.DATE, 1);           // 比较天数，日期+1  
            }  
        }  
//           
//        n = n-1;  
//           
//        if(stype==2){  
//            n = (int)n/365;  
//        }     
//           
//        System.out.println(date1+" -- "+date2+" 相差多少"+u[stype]+":"+n);        
        return months;  
    }  
	
	public static void main(String[] args) throws Exception{
		for(int i=0; i<20; i++) {
			System.out.println(TimeUtils.getTimeSN());
			
		}
	}
}
