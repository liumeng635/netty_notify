package com.lty.controller;


import com.lty.service.INoticeService;
import com.lty.show.ResultShow;
import com.lty.show.request.RequestNoticeShow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 通知接口
 * @author zhouyongbo
 */
@RestController
public class NoticeContorller {


    @Autowired
    INoticeService iNoticeService;

    @RequestMapping(value = "/driver/notice",method = RequestMethod.POST)
    public ResultShow notice(@RequestBody RequestNoticeShow requestNoticeShow){
        try{
            return iNoticeService.noticeApp(requestNoticeShow);
        }catch (Exception e){
            return new ResultShow(102,"系统异常");
        }
    }

    @RequestMapping(value = "/notice",method = RequestMethod.POST)
    public String get(@RequestBody String s){
        System.out.println(s);
        return "success:"+s+"";
    }
}
