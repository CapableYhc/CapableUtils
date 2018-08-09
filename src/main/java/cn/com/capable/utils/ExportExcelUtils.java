package cn.com.capable.utils;


import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.SimpleFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
导出excel工具类
 */
public class ExportExcelUtils<T> {

    public void exportExcelCom(HttpServletResponse response, String downName, String title, String []headers, String []Col, Collection<T> dataSet,String pattern,String Head) throws IOException {
        HSSFWorkbook workbook = this.exportExcel(title, headers, Col, dataSet, pattern, Head);
        downName=downName+".xls";
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename=" + new String(downName.getBytes("gb2312"), "iso8859-1")) ;
        OutputStream ouputStream = response.getOutputStream();
        workbook.write(ouputStream);
        ouputStream.flush();
        ouputStream.close();
    }

    public HSSFWorkbook exportExcel(String title, String[] headers, String[] Col,Collection<T> dataSet,String pattern,String Head){
        if(pattern ==null || pattern.equals("")) pattern = "yyyy-MM-dd";
        //声明一个工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();
        //生成一个表格
        HSSFSheet sheet = workbook.createSheet(title);
        //设置表格默认列宽度
        sheet.setDefaultColumnWidth(18);
        //生成样式
        HSSFCellStyle style = workbook.createCellStyle();
        //设置样式
        style.setFillForegroundColor(HSSFColor.WHITE.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //生成字体
        HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short) 12);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        //字体应用到当前样式
        style.setFont(font);

        //生成并设置第二个样式
        HSSFCellStyle style2 = workbook.createCellStyle();
        //设置样式
        style2.setFillForegroundColor(HSSFColor.WHITE.index);
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        //生成第二个字体
        HSSFFont font2 = workbook.createFont();
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        //把字体应用到当前样式
        style2.setFont(font2);
        //声明一个画图的顶级管理器
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
        //产生表格抬头
        HSSFRow row;
        if(Head!=null){
            //生成头部样式
            HSSFCellStyle headStyle = workbook.createCellStyle();
            //设置头部样式
            headStyle.setFillForegroundColor(HSSFColor.WHITE.index);
            headStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            headStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            headStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            headStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
            headStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
            headStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            // 生成一个字体
            HSSFFont headFont = workbook.createFont();
//	        font.setColor(HSSFColor.VIOLET.index);
            headFont.setFontHeightInPoints((short) 14);
            headFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            // 把字体应用到当前的样式
            headStyle.setFont(headFont);

            row = sheet.createRow(0);
            sheet.addMergedRegion(new Region(0,(short)0,0,(short)(headers.length-1)));//合并单元格
            HSSFCell c = row.createCell(0);
            c.setCellStyle(headStyle);
            c.setCellValue(new HSSFRichTextString(Head));
            row = sheet.createRow(1);
        }else {
            //产生表格标题行
            row = sheet.createRow(0);
        }
        int Cell = 0;
        for(short i=0;i < headers.length;i++){
            HSSFCell cell = row.createCell(Cell);
            cell.setCellStyle(style);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
            Cell ++;
        }
        //遍历集合数据，产生数据行
        Iterator<T> it = dataSet.iterator();
        int index;
        if(Head!=null){
            index = 1;
        }else {
            index = 0;
        }

        while (it.hasNext()){
            index++;
            row = sheet.createRow(index);
            T t = (T) it.next();
            String[] fields = Col;
            Cell = 0;
            for(short i = 0;i < fields.length; i++){
                String fieldName = fields[i];
                HSSFCell cell = row.createCell(Cell);
                cell.setCellStyle(style2);
                try{
                    Object value = "";
                    Class tCls = null;
                    Map map = null;
                    if(t instanceof Map){
                        map = (Map)t;
                        value = map.get(fieldName);
                    }else {
                        String getMethodName = "get"
                                +fieldName.substring(0,1).toUpperCase()
                                +fieldName.substring(1);
                        tCls = t.getClass();
                        Method getMethod = tCls.getMethod(getMethodName,new Class[]{});
                        value = getMethod.invoke(t,new Object[]{});
                    }
                    if(value == null) value="";
                    //判断值的类型后进行强制类型转换
                    String textValue = null;
                    if(value instanceof Date){
                        Date date = (Date) value;
                        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                        textValue = sdf.format(date);
                    } else if(value instanceof  Byte[]){
                        //有图片时，设置行高为60px
                        row.setHeightInPoints(60);
                        //设置图片所在列宽带为80px，需要注意单位换算
                        sheet.setColumnWidth(Cell,(short)35.7 * 80);
                        byte[] bsVaule = (byte[]) value;
                        HSSFClientAnchor anchor = new HSSFClientAnchor(0,0,
                                1023,255,(short) 6,index,(short) 6,index);
                        anchor.setAnchorType(2);
                        patriarch.createPicture(anchor,workbook.addPicture(bsVaule,HSSFWorkbook.PICTURE_TYPE_JPEG));
                    }else {
                        //其余数据类型当字符串处理
                        textValue = value.toString();
                    }

                    // 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
                    if (textValue != null) {
                        Pattern p = Pattern.compile("^//d+(//.//d+)?$");
                        Matcher matcher = p.matcher(textValue);
                        if (matcher.matches()) {
                            // 是数字当作double处理
                            cell.setCellValue(Double.parseDouble(textValue));
                        } else {
                            HSSFRichTextString richString = new HSSFRichTextString(
                                    textValue);
                            HSSFFont font3 = workbook.createFont();
                            //font3.setColor(HSSFColor.BLUE.index);
                            font3.setColor(HSSFColor.BLACK.index);
                            richString.applyFont(font3);
                            cell.setCellValue(richString);
                        }
                    }
                    Cell ++ ;
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

        return workbook;
    }


}
