package com.Tzj.lost_found_system.controller;

import com.Tzj.lost_found_system.pojo.Found;
import com.Tzj.lost_found_system.pojo.PageBean;
import com.Tzj.lost_found_system.pojo.Result; // 修复：移除多余空格
import com.Tzj.lost_found_system.service.FoundService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class FoundController {
    @Autowired
    private FoundService foundService;

    @GetMapping("/getFoundAll")
    public Result getFoundAll(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "5") Integer count) {
        PageBean founds = foundService.getFoundAll(page, count);
        return Result.success(founds);
    }

    @GetMapping("/getFound")
    // 注意：userID 在数据库为 int 类型，此处为兼容前端传参使用 String，MyBatis 会自动转换
    public Result getFound(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "5") Integer count, Integer id,
                          String title, String description, String phone, String userID, String releaseDate, Integer statusID) {
        PageBean founds = foundService.getFound(page, count, id, title, description, phone, userID, releaseDate, statusID);
        return Result.success(founds);
    }

    @PostMapping("/editFound")
    public int editFound(@RequestBody Found found) {
        return foundService.editFound(found);
    }
    @PostMapping("/addFound")
    public int addFound(@RequestBody Found found){
        return foundService.addFound(found);
    }
    @GetMapping("/deleteFound")
    public int deleteFound(Integer id){
        return foundService.deleteFound(id);
    }
    @GetMapping("/getFoundData")
    public Result getFoundData(){
        return Result.success(foundService.getFoundData());
    }
}
