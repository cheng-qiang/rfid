package org.chen.rfid.controller.station;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.chen.rfid.model.RespBean;
import org.chen.rfid.model.RespPageBean;
import org.chen.rfid.model.station.Personnel;
import org.chen.rfid.service.station.impl.CardServiceImpl;
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
import java.util.Date;

/**
 * @author 程强
 * @date 2020年04月10日 16:20
 * @Description:
 */
@Api(tags = "制卡管理接口")
@RestController
@RequestMapping("/position/card/base")
public class CardController {

    @Autowired
    private CardServiceImpl cardService;

    @GetMapping("/")
    @ApiOperation(value = "制卡列表接口")
    public RespPageBean getCardByPage(@RequestParam(defaultValue = "1")Integer page,
                                      @RequestParam(defaultValue = "10")Integer size,
                                      @RequestParam(defaultValue = "")String tabStationCode,
                                      @RequestParam(defaultValue = "")String visitorName,
                                      @RequestParam(defaultValue = "")Long userId,
                                      @RequestParam(defaultValue = "")Integer status,
                                      Date[] createDate){
        return cardService.getCardByPage(page,size,tabStationCode,visitorName,userId,status,createDate);
    }

    @ApiOperation(value = "添加绑定接口")
    @PostMapping("/")
    public RespBean addCard(@RequestBody @Valid Personnel personnel){
        return cardService.addCard(personnel);
    }

    @ApiOperation(value = "编辑绑定接口")
    @PutMapping("/")
    public RespBean editCard(@RequestBody @Valid Personnel personnel){
        return cardService.editCard(personnel);
    }

    @ApiOperation(value = "删除绑定接口")
    @DeleteMapping("/{id}")
    public RespBean deleteCard(@PathVariable Long id){
        return cardService.deleteCard(id);
    }

    @ApiOperation(value = "批量删除绑定接口")
    @DeleteMapping("/")
    public RespBean deleteCards(Long[] ids){
        return cardService.deleteCards(ids);
    }
}
