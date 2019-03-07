package com.lty.controller;

import com.alibaba.fastjson.JSON;
import com.lty.service.IDriverUserService;
import com.lty.show.LoginReturnShow;
import com.lty.show.LoginUerShow;
import com.lty.show.ResultShow;
import com.lty.show.request.OnLineDriverShow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 *  司机用户登录
 * @author zhouyongbo
 */
@RestController
@RequestMapping("/driver")
public class LoginDriverUserController {

    @Autowired
    IDriverUserService iDriverUserService;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ResultShow loginDriverUser(@RequestBody String loginUerShowStr,
                                      HttpServletResponse response){
        try{
            System.out.println("参数:"+loginUerShowStr);
            LoginUerShow loginUerShow = JSON.parseObject(loginUerShowStr,LoginUerShow.class);
            return iDriverUserService.loginDriverUser(loginUerShow.getAccount(),
                    loginUerShow.getPwd(),response);
        }catch (Exception e){
            e.printStackTrace();
            return new ResultShow(102,"系统异常，请联系管理员");
        }

    }


    @RequestMapping(value = "/onLine",method = RequestMethod.POST)
    public ResultShow onLineDriver(@RequestBody OnLineDriverShow onLineDriverShow){
        try{
        return iDriverUserService.onLineDriver(onLineDriverShow);
        }catch (Exception e){
            e.printStackTrace();
            return new ResultShow(102,"系统异常，请联系管理员");
        }
    }


}
