package com.Tzj.lost_found_system.controller;

import com.Tzj.lost_found_system.pojo.Notice;
import com.Tzj.lost_found_system.pojo.PageBean;
import com.Tzj.lost_found_system.pojo.Result;
import com.Tzj.lost_found_system.service.NoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class NoticeController {
    @Autowired
    private NoticeService noticeService;
    @GetMapping("/getNoticeAll")//获取所有公告
    public Result getAll(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "5") Integer count){
        PageBean notices = noticeService.getAll(page,count);
        return Result.success(notices);
    }
    @GetMapping("/getNotice") //按条件获取公告
    // 修复：添加 @RequestParam 默认值，防止 page/count 为 null 导致分页异常
    public Result getNotice(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "5") Integer count, Integer id, String title, String releaseDate, Integer userID){
        PageBean notices = noticeService.getNotice(page,count,id,title,releaseDate,userID);
        return Result.success(notices);
    }
    @PostMapping("/editNotice") //修改公告
    public int editNotice(Notice notice){
        return noticeService.editNotice(notice);
    }
    @PostMapping("/addNotice") //添加公告
    public int addNotice(Notice notice){
        log.info(notice+"");
        return noticeService.addNotice(notice);
    }
    @GetMapping("/deleteNotice") //删除公告
    public int deleteNotice(Integer id){
        return noticeService.deleteNotice(id);
    }
}
