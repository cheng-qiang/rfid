package org.chen.rfid.util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;

public class POIExcelUtil {
    /**
     * 导出Excel
     * @param sheetName sheet名称
     * @param sheetTitle 表格标题
     * @param cellWidth 各列的宽度
     * @param title 列标题
     * @param values 内容
     * @param wb HSSFWorkbook对象
     * @return
     */
    public static HSSFWorkbook getHSSFWorkbook(String sheetName,String sheetTitle,int[] cellWidth,String []title,String [][]values, HSSFWorkbook wb){

        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        if(wb == null){
            wb = new HSSFWorkbook();
        }

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);

        //第三步，设备单元格高度
        if(cellWidth != null && cellWidth.length == title.length){
            for(int i=0;i<cellWidth.length;i++){
                sheet.setColumnWidth(i, cellWidth[i] * 256);//设置第i列的宽度是?个字符宽度
            }
        }
        //设置打印对象
        HSSFPrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setLandscape(true);//设置打印方向，横向就是true
        printSetup.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);//设置A4纸

        // 第四步，创建单元格，设置各单元格主题样式
        //表格主题样式
        HSSFCellStyle sheetTitleStyle = wb.createCellStyle();
        sheetTitleStyle.setAlignment(HorizontalAlignment.CENTER); // 创建一个居中格式
        HSSFFont font = wb.createFont();
        font.setFontName("微软雅黑");
        font.setColor(IndexedColors.BLACK.index);
        font.setBold(true);
        font.setFontHeightInPoints((short)18);
        sheetTitleStyle.setFont(font);

        //标题单元格样式
        HSSFCellStyle titleStyle = wb.createCellStyle();
        titleStyle.setAlignment(HorizontalAlignment.CENTER); // 创建一个居中格式
        font = wb.createFont();
        font.setFontName("微软雅黑");
        font.setColor(IndexedColors.BLACK.index);
        titleStyle.setFont(font);
        titleStyle.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
        //solid 填充  foreground  前景色
        titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        titleStyle.setBorderBottom(BorderStyle.THIN);
        titleStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        titleStyle.setBorderTop(BorderStyle.THIN);
        titleStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        titleStyle.setBorderRight(BorderStyle.THIN);
        titleStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        titleStyle.setBorderLeft(BorderStyle.THIN);
        titleStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());

        //内容单元格样式
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER); // 创建一个居中格式
        style.setWrapText(true);//设置自动换行
        font = wb.createFont();
        font.setFontName("微软雅黑");
        font.setColor(IndexedColors.BLACK.getIndex());
        style.setFont(font);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());

        //第五步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow sheetTitleRow = sheet.createRow(0);
        HSSFCell sheetTitlerowCell=sheetTitleRow.createCell(0);
        sheetTitlerowCell.setCellValue(sheetTitle);
        //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列  
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,(title.length-1)));
        sheetTitlerowCell.setCellStyle(sheetTitleStyle);

        //第六步，组装表标题及内容
        HSSFRow row = sheet.createRow(1);
        //声明列对象
        HSSFCell cell = null;

        //创建标题
        for(int i=0;i<title.length;i++){
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(titleStyle);
        }

        //创建内容
        for(int i=0;i<values.length;i++){
            row = sheet.createRow(i + 2);
            for(int j=0;j<values[i].length;j++){
                //将内容按顺序赋给对应的列对象
                cell = row.createCell(j);
                cell.setCellValue(values[i][j]);
                cell.setCellStyle(style);
            }
        }
        return wb;
    }


}
