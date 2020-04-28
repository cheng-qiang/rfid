package org.chen.rfid.controller.station;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.chen.rfid.model.RespBean;
import org.chen.rfid.model.station.Visitors;
import org.chen.rfid.service.station.impl.VisitorsServiceImpl;
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
 * @date 2020年03月25日 10:39
 * @Description:
 */
@RestController
@RequestMapping("/position/visitors/base")
@Api(tags = "访客管理相关接口")
public class VisitorsController {
    @Autowired
    private VisitorsServiceImpl visitorsService;

    @GetMapping("/")
    @ApiOperation(value = "访客信息接口",notes = "分页条件查询访客信息接口")
    public Object getAllVisitors(@RequestParam(defaultValue = "1")Integer page,
                                 @RequestParam(defaultValue = "10")Integer size,
                                 @RequestParam(defaultValue = "") String name){
        return visitorsService.getAllVisitors(page,size,name);
    }

    @PostMapping("/")
    @ApiOperation(value = "添加访客接口")
    public RespBean addVisitors(@RequestBody @Valid Visitors visitors){
        return visitorsService.addVisitors(visitors);
    }

    @PutMapping("/")
    @ApiOperation(value = "更新访客接口")
    public RespBean updateVisitors(@RequestBody @Valid Visitors visitors){
        return visitorsService.updateVisitors(visitors);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除访客接口")
    public RespBean deleteVisitor(@PathVariable("id")Long id){
        return visitorsService.deleteVisitor(id);
    }

    @DeleteMapping("/")
    @ApiOperation(value = "批量删除访客接口")
    public RespBean deleteVisitors(Long[] ids){
        return visitorsService.deleteVisitors(ids);
    }
}
