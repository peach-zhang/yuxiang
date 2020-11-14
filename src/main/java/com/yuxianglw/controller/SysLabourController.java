package com.yuxianglw.controller;


import com.yuxianglw.common.Result;
import com.yuxianglw.entity.SysLabour;
import com.yuxianglw.service.SysLabourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 员工  前端控制器
 * </p>
 *
 * @author zhangtao
 * @since 2020-11-12
 */
@RestController
@RequestMapping("/yuxianglw/sysLabour")
public class SysLabourController {

    @Autowired
    private SysLabourService  sysLabourService;

    @PostMapping("/addSysLabour")
    public Result<?> addSysLabour(@Valid @RequestBody SysLabour sysLabour){
        return  sysLabourService.addSysLabour(sysLabour);
    }

    @GetMapping("/queryUser")
    public Result<?> queryUser( @RequestParam(value = "name",required = false) String name,
                                @RequestParam(value = "phone",required = false) String phone,
                                @RequestParam(value = "idcard",required = false) String idcard,
                                @RequestParam(value = "sex",required = false) String sex,
                                @RequestParam(value = "pagenum") int pagenum,
                                @RequestParam(value = "pagesize") int pagesize){
        return  sysLabourService.queryUser(name,phone,idcard,sex,pagenum,pagesize);
    }

    @PutMapping("/deleteUser")
    public Result<?> deleteUser(@RequestBody SysLabour sysLabour){
        return  sysLabourService.deleteUser(sysLabour);
    }
    @PutMapping("/batchDeleteUser")
    public Result<?> batchDeleteUser(@RequestBody List<SysLabour> sysLabours){
        return  sysLabourService.batchDeleteUser(sysLabours);
    }
    @PutMapping("/editUser")
    public Result<?> editUser(@RequestBody SysLabour sysLabour){
        return  sysLabourService.editUser(sysLabour);
    }

}

