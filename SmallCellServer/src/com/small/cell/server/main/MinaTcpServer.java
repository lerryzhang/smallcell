package com.small.cell.server.main;


import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.mina.core.service.IoAcceptor;

import org.apache.mina.core.session.IdleStatus;

import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.small.cell.server.util.TestFactory;
import com.smallcell.server.handler.ServerHandler;

/**
 * @说明 Mina TCP 服务端
 * @author Lerry
 * @version 1.0
 * @since
 */
public class MinaTcpServer  {
	public static final int PORT = 5000;
	public MinaTcpServer() throws IOException {
		IoAcceptor acceptor = new NioSocketAcceptor();
        // 2. 加入日志记录过滤器，用SL4J库记录信息
        acceptor.getFilterChain().addLast("logger", new LoggingFilter());
        
        
        acceptor.getFilterChain().addLast("coderc", new ProtocolCodecFilter(
                new TestFactory() ));
        
      
        // 4. 设置ServerHandler, 自定义的Handler,TimeServerHandler
        acceptor.setHandler(new ServerHandler());
      
        // 6. 设置空闲时间， 这里的BOTH_IDLE指EADER_IDLE和WRITER_IDLE都为10秒
        acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 30);
        // 7. 绑定监听端口9123.MD
        
//      acceptor.bind(new InetSocketAddress("localhost",PORT));0
        acceptor.bind(new InetSocketAddress(PORT)); 
	}
	public static void main(String[] args) throws IOException {
		new MinaTcpServer();
	}
	
	
}