package cn.com.capable.utils;

import cn.com.gzheroli.core.util.IdWorker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtils {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    /**
     * 获取某年第一天日期
     *
     * @param year 年份
     * @return Date
     */
    public static Date getYearFirstDay(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR,year);
        Date firstDay = calendar.getTime();
        return firstDay;
    }

    /**
     * 获取某年最后一天日期
     *
     * @param year 年份
     * @return Date
     */
    public static Date getYearLastDay(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR,year);
        calendar.roll(Calendar.DAY_OF_YEAR,-1);
        Date lastDay = calendar.getTime();
        return lastDay;
    }

    /**
     * 获取当年的第一天
     *
     * @return Date
     */
    public static Date getNowYearFirstDay(){
        Calendar nowCal = Calendar.getInstance();
        int nowYear = nowCal.get(Calendar.YEAR);
        return getYearFirstDay(nowYear);
    }

    /**
     * 获取当月的最后一天
     *
     * @return Date
     */
    public static String getNowMonthLastDay(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return sdf.format(calendar.getTime());
    }

    /**
     * 获取当月的第一天
     *
     * @return Date
     */
     public static String getNowMonthFirstDay(){
         Calendar c = Calendar.getInstance();
         c.add(Calendar.MONTH,0);
         c.set(Calendar.DAY_OF_MONTH,1);
         return sdf.format(c.getTime());
     }

    /**
     * 获取最近一周的时间
     *
     * @return Date
     */
    public static Date getLastWeekDay(){
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE,-7);
        Date d = c.getTime();
        return d;
    }

    /**
     * 获取最近一月的时间
     * @param
     * @return
     */
    public static Date getLastMonthDay(){
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH,-1);
        Date d = c.getTime();
       return d;
    }

    /**
     * 获取最近N个月的时间
     * @param
     * @return
     */
    public static Date getLast3Month(int month){
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH,-month);
        Date d = c.getTime();
        return d;
    }

    /**
     * 获取指定年月的第一天或最后一天
     * @param
     * @return
     */
    public  static Date getDayOfMonth(int year,int month,boolean firstDay){
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,firstDay==true?c.getMinimum(Calendar.DATE):c.getMaximum(Calendar.DATE));
        Date d = c.getTime();
        return d;
    }

    public static void main(String[] args) {
       Date first  = TimeUtils.getNowYearFirstDay();
        System.out.println(sdf.format(first));
        for(int i=0;i<200;i++) {
            String id = IdWorker.getFlowIdWorkerInstance().nextIdStr();
            System.out.println(id);
        }

    }

}
