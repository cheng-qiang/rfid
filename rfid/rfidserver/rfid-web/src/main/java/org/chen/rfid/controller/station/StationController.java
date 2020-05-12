package org.chen.rfid.controller.station;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.chen.rfid.model.RespBean;
import org.chen.rfid.model.annotation.SysLog;
import org.chen.rfid.model.station.Station;
import org.chen.rfid.service.station.impl.StationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

/**
 * @author 程强
 * @date 2020年03月21日 13:36
 * @Description:
 */
@RestController
@RequestMapping("/position/station/base")
@Api(tags = "基站管理相关接口")
public class StationController {

    @Autowired
    private StationServiceImpl stationService;

    @GetMapping("/")
    @ApiOperation(value = "获取基站信接口",notes = "分页条件查询获取基站信息接口")
    public Object getAllStations(@RequestParam(defaultValue = "1")Integer page,
                                        @RequestParam(defaultValue = "10")Integer size,
                                        @RequestParam(defaultValue = "") String name){
        return stationService.getAllStations(page,size,name);
    }

    @GetMapping("/{station_code}")
    @ApiOperation(value = "校验基站是否唯一接口")
    public String isCodeOnly(@PathVariable("station_code")String station_code){
        return stationService.isCodeOnly(station_code);
    }

    @SysLog(value = "添加基站")
    @PostMapping("/")
    @ApiOperation(value = "添加基站接口")
    public RespBean addStation(@RequestBody @Valid Station station){
        return stationService.addStation(station);
    }

    @SysLog(value = "更新基站")
    @PutMapping("/")
    @ApiOperation(value = "更新基站接口")
    public RespBean updateStation(@RequestBody @Valid Station station){
        return stationService.updateStation(station);
    }

    @SysLog(value = "删除基站")
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除基站接口")
    public RespBean deleteStation(@PathVariable("id")Long id){
        return stationService.deleteStation(id);
    }

    @SysLog(value = "批量删除基站")
    @DeleteMapping("/")
    @ApiOperation(value = "批量删除基站接口")
    public RespBean deleteStations(Long[] ids){
        return stationService.deleteStations(ids);
    }

    @SysLog(value = "导出基站")
    @GetMapping("/export")
    @ApiOperation(value = "导出基站信息接口")
    public void exportStations(HttpServletResponse response){
        stationService.exportStations(response);
    }

    @SysLog(value = "导入基站")
    @PostMapping("/import")
    @ApiOperation(value = "导入基站信息接口")
    public RespBean importStations(MultipartFile file)throws IOException{
        return stationService.importStations(file);
    }

}
