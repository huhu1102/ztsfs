package com.zt.controller;

import com.zt.model.ResultObject;
import com.zt.po.Color;
import com.zt.service.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/color")
public class Colorcontroller {
    @Autowired
    ColorService colorService;
    /*
     * 分页模糊条件查询
     */
    @RequestMapping(value="/findAll",method= RequestMethod.GET)
    public ResultObject<Color> findALL() {
        return colorService.findAll();
    }
    /*
    新增
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public ResultObject<Color> add(Color color){
        return colorService.add(color);
    }
    /*
    删除
     */
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public ResultObject<Color> delete(long id){
        return colorService.delete(id);
    }

}
