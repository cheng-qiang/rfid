package org.chen.rfid.controller.station;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.chen.rfid.model.RespBean;
import org.chen.rfid.model.annotation.SysLog;
import org.chen.rfid.model.station.TabStation;
import org.chen.rfid.service.station.impl.TabStationServiceImpl;
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

import javax.validation.Valid;

/**
 * @author 程强
 * @date 2020年03月28日 16:07
 * @Description:
 */
@RestController
@RequestMapping("/position/label/base")
@Api(tags = "标签管理接口设计")
public class TabStationController {

    @Autowired
    private TabStationServiceImpl tabStationService;

    @ApiOperation(value = "查询接口设计")
    @GetMapping("/")
    public Object getAllTabStations(@RequestParam(defaultValue = "1")Integer page,
                                    @RequestParam(defaultValue = "10")Integer size,
                                    @RequestParam(defaultValue = "")String code,
                                    @RequestParam(defaultValue = "")Boolean status){
        return tabStationService.getAllTabStations(page,size,code,status);
    }

    @GetMapping("/{tab_code}")
    @ApiOperation(value = "标签是否存在接口")
    public String isCodeOnly(@PathVariable("tab_code")String tab_code){
        return tabStationService.isCodeOnly(tab_code);
    }


    @SysLog(value = "添加标签")
    @PostMapping("/")
    @ApiOperation(value = "添加定位标签接口")
    public RespBean addTabStation(@RequestBody @Valid TabStation tabStation){
        return tabStationService.addTabStation(tabStation);
    }

    @SysLog(value = "更新标签")
    @PutMapping("/")
    @ApiOperation(value = "更新定位标签接口")
    public RespBean updateTabStation(@RequestBody @Valid TabStation tabStation){
        return tabStationService.updateTabStation(tabStation);
    }

    @SysLog(value = "删除标签")
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除标签接口")
    public RespBean deleteTabStation(@PathVariable(value = "id")Long id){
        return tabStationService.deleteTabStation(id);
    }

    @SysLog(value = "批量删除标签")
    @DeleteMapping("/")
    @ApiOperation(value = "批量删除标签接口")
    public RespBean deleteTabStations(Long[] ids){
        return tabStationService.deleteTabStations(ids);
    }


}
