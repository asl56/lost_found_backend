package com.Tzj.lost_found_system.controller;

import com.Tzj.lost_found_system.pojo.Lost;
import com.Tzj.lost_found_system.pojo.PageBean;
import com.Tzj.lost_found_system.pojo.Result;
import com.Tzj.lost_found_system.service.LostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class LostController {
    @Autowired
    private LostService lostService;

    @GetMapping("/getLostAll")
    public Result getLostAll(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "5") Integer count) {
        PageBean losts = lostService.getLostAll(page, count);
        return Result.success(losts);
    }

    @GetMapping("/getLost")
    // 注意：userID 在数据库为 int 类型，此处为兼容前端传参使用 String，MyBatis 会自动转换
    public Result getLost(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "5") Integer count, Integer id,
                          String title, String description, String phone, String userID, String releaseDate, Integer statusID) {
        PageBean losts = lostService.getLost(page, count, id, title, description, phone, userID, releaseDate, statusID);
        return Result.success(losts);
    }

    @PostMapping("/editLost")
    public int editLost(@RequestBody Lost lost) {
        return lostService.editLost(lost);
    }
    @PostMapping("/addLost")
    public int addLost(@RequestBody Lost lost){
        return lostService.addLost(lost);
    }
    @GetMapping("/deleteLost")
    public int deleteLost(Integer id){
        return lostService.deleteLost(id);
    }

    @GetMapping("getLostData")
    public Result  getLostData(){
        return Result.success(lostService.getLostData());
    }
}
