package com.lty.init;


import com.lty.config.SocketServerConfig;
import com.lty.socket.netty.Global;
import com.lty.socket.netty.NettyServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 *
 * 项目初始化 启动
 * @author zhouyongbo
 */
@Component
public class InitMain  implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(InitMain.class);
    @Autowired
    SocketServerConfig socketServerConfig;

    @Override
    public void run(String... strings) throws Exception {
        //初始化webSocket
        NettyServer nettyServer = new NettyServer(socketServerConfig.getServer().getPort());
        nettyServer.start();
        logger.info("==============webSocket 启动===============");
        //定期清理链接程序开启
        Global.timedTask();
        logger.info("==============定时清理程序启动===============");


    }
}
