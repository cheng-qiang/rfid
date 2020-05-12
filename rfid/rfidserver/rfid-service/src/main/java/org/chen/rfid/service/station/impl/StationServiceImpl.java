package org.chen.rfid.service.station.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.chen.rfid.mapper.station.StationMapper;
import org.chen.rfid.model.CodeMsg;
import org.chen.rfid.model.RespBean;
import org.chen.rfid.model.station.Station;
import org.chen.rfid.service.exception.GlobalException;
import org.chen.rfid.service.station.StationService;
import org.chen.rfid.util.Constants;
import org.chen.rfid.util.FileUtil;
import org.chen.rfid.util.ListToArray;
import org.chen.rfid.util.POIExcelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author 程强
 * @date 2020年03月21日 13:20
 * @Description:
 */
@Service
public class StationServiceImpl implements StationService {
    private static final Logger logger = LoggerFactory.getLogger(StationServiceImpl.class);
    @Autowired
    private StationMapper stationMapper;

    public Object getAllStations(Integer page, Integer size, String name) {
        Page<Station> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<Station> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Station::getName, name);
        IPage<Station> pageResult = stationMapper.selectPage(pageParam, queryWrapper);
        return pageResult;
    }

    public RespBean addStation(Station station) {
        if (null == station){
            throw new GlobalException(CodeMsg.ERROR_SERVER);
        }
        if (stationMapper.insert(station) > 0){
            return RespBean.ok(Constants.SUCCESS);
        }
        return RespBean.error(Constants.ERROR);
    }

    public String isCodeOnly(String station_code) {
        LambdaQueryWrapper<Station> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Station::getCode,station_code);
        Station station = stationMapper.selectOne(queryWrapper);
        if (station != null){
            return "FALSE";
        }else {
            return "TRUE";
        }

    }

    public RespBean updateStation(Station station) {
        if (stationMapper.updateById(station)>0){
            return RespBean.ok(Constants.SUCCESS);
        }
        return RespBean.error(Constants.ERROR);
    }

    public RespBean deleteStation(Long id) {
        if (stationMapper.deleteById(id)>0){
            return RespBean.ok(Constants.SUCCESS);
        }
        return RespBean.error(Constants.ERROR);
    }

    public RespBean deleteStations(Long[] ids) {
        ArrayList<Long> arrayList = new ArrayList<Long>(ids.length);
        Collections.addAll(arrayList, ids);
        if (stationMapper.deleteBatchIds(arrayList)==ids.length){
            return RespBean.ok(Constants.SUCCESS);
        }
        return RespBean.error(Constants.ERROR);
    }

    public void exportStations(HttpServletResponse response) {
        List<Station> stations = stationMapper.selectList(null);
        String[][] stationsArrays = new ListToArray().listToArrayWay(stations);
        HSSFWorkbook wb = null;
        wb = POIExcelUtil.getHSSFWorkbook(FileUtil.getExportFileName(Constants.STATION_FILE_NAME), Constants.STATION_FILE_NAME, Constants.STATION_EXPORT_EXCEL_CELL_WIDTH, Constants.STATION_EXPORT_EXCEL_HEADERS_EN, stationsArrays, wb);
        FileUtil.writeExcelExport(response, wb, FileUtil.getExportFileName(Constants.STATION_FILE_NAME));
    }

    public RespBean importStations(MultipartFile file) {
        List<Station> stations = new ArrayList<Station>();
        int count = 0;
        //解析EXCEL文件 --- > List<Station>
        HSSFWorkbook book = null;
        try {
            book = new HSSFWorkbook(file.getInputStream());
            HSSFSheet sheet = book.getSheetAt(0);
            for (int i = 2;i<sheet.getLastRowNum()+1;i++){
                HSSFRow row = sheet.getRow(i);
                String code = row.getCell(1).getStringCellValue();
                if (isCodeOnly(code).toLowerCase().equals("false")){
                    continue;
                }
                String name = row.getCell(2).getStringCellValue();
                String version = row.getCell(3).getStringCellValue();
                String ip = row.getCell(4).getStringCellValue();
                String statusString = row.getCell(5).getStringCellValue();
                Boolean status = statusString.toLowerCase().equals("true")?true:false;
                Date date = row.getCell(6).getDateCellValue();
                stations.add(new Station(code,name,version,ip,status,date));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //添加数据
        for (Station station : stations){
            if (stationMapper.insert(station)==1){
                count++;
            }
        }
        if (stations.size()==count){
            return RespBean.ok(Constants.SUCCESS);
        }
        if (stations.size() == 0){
            return RespBean.error("您重复导入数据啦!");
        }
        return RespBean.error(Constants.ERROR);
    }
}
