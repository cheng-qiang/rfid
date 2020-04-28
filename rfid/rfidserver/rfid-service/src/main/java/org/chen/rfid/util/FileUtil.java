package org.chen.rfid.util;

import com.sun.org.apache.regexp.internal.RE;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 程强
 * @date 2020年03月23日 10:05
 * @Description:
 */
public class FileUtil {

    /**
     * 返回导出文件名称
     * @param ExportFileName
     * @return
     */
    public static String getExportFileName(String ExportFileName){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.EXPORT_PATTERN_DATE);
        String format = simpleDateFormat.format(new Date());
        String name = UserUtil.getCurrentUser().getName();
        return ExportFileName+"("+format+")_"+name+".xls";
    }

    public static void writeExcelExport(HttpServletResponse response, HSSFWorkbook wb,String ExportFileName){
        OutputStream out = null;
        try {
            response.setContentType("octets/stream");
            response.setContentType("APPLICATION/OCTET-STREAM");
            response.setHeader("Content-Disposition", getHeadString(ExportFileName));
            out = response.getOutputStream();
            wb.write(out);
            out.flush();
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 防止文件中文乱码
     * @param ExportFileName
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String getHeadString(String ExportFileName) throws UnsupportedEncodingException {
        return  "attachment; filename=\"" + new String(ExportFileName.getBytes("gb2312"),"ISO8859-1")+"\"";
    }

    public static void main(String[] args) {
        System.out.println(getExportFileName("基站信息表"));
    }
}
