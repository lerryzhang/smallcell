package com.small.cell.server.main;

import java.net.InetSocketAddress;  
import org.apache.mina.core.buffer.IoBuffer;  
import org.apache.mina.core.future.ConnectFuture;  
import org.apache.mina.core.service.IoConnector;  
import org.apache.mina.core.service.IoHandlerAdapter;  
import org.apache.mina.core.session.IdleStatus;  
import org.apache.mina.core.session.IoSession;  
import org.apache.mina.transport.socket.nio.NioSocketConnector;  

import com.small.cell.server.util.ByteAndStr16;

/** 
 * @˵�� Mina TCP�ͻ��� 
 * @author ����ǿ 
 * @version 1.0 
 * @since 
 */  
public class MinaTcpClient extends IoHandlerAdapter {  
    private IoConnector connector;  
    private static IoSession session;  
    public static int[] value1={80,0,0,1,0,1,0,0,0,1,0,18,0,1,0,2,0,13};
	
		                    
    public MinaTcpClient() {  
        connector = new NioSocketConnector();  
        connector.setHandler(this);  
        ConnectFuture connFuture = connector.connect(new InetSocketAddress("localhost", MinaTcpServer.PORT));  
        connFuture.awaitUninterruptibly();  
        session = connFuture.getSession();  
        System.out.println("TCP �ͻ�������");  
    }  

	public static void main(String[] args) throws Exception {
		MinaTcpClient client = new MinaTcpClient();
		
			byte[] bts = new byte[value1.length];
			for (int i = 0; i < value1.length; i++) {
				bts[i] = (byte) value1[i];
				
			}
			System.out.println("==�ͻ��˷���������==="+ByteAndStr16.Bytes2HexString(bts));
			IoBuffer buffer = IoBuffer.allocate(value1.length);
			// �Զ�����
			buffer.setAutoExpand(true);
			// �Զ�����
			buffer.setAutoShrink(true);
			buffer.put(bts);
			buffer.flip();
			session.write(buffer);
			//Thread.sleep(2000);
		
		// �رջỰ���������̴߳��������
		client.connector.dispose(true);
	}  
    @Override  
    public void messageReceived(IoSession iosession, Object message)  
            throws Exception {  
        IoBuffer bbuf = (IoBuffer) message;  
        byte[] byten = new byte[bbuf.limit()];  
        bbuf.get(byten, bbuf.position(), bbuf.limit());  
        System.out.println("�ͻ����յ���Ϣ" + ByteAndStr16.Bytes2HexString(byten));  
    }  
    @Override  
    public void exceptionCaught(IoSession session, Throwable cause)  
            throws Exception {  
        System.out.println("�ͻ����쳣");  
        super.exceptionCaught(session, cause);  
    }  
    @Override  
    public void messageSent(IoSession iosession, Object obj) throws Exception {  
        System.out.println("�ͻ�����Ϣ����");  
        super.messageSent(iosession, obj);  
    }  
    @Override  
    public void sessionClosed(IoSession iosession) throws Exception {  
        System.out.println("�ͻ��˻Ự�ر�");  
        super.sessionClosed(iosession);  
    }  
    @Override  
    public void sessionCreated(IoSession iosession) throws Exception {  
        System.out.println("�ͻ��˻Ự����");  
        super.sessionCreated(iosession);  
    }  
    @Override  
    public void sessionIdle(IoSession iosession, IdleStatus idlestatus)  
            throws Exception {  
        System.out.println("�ͻ��˻Ự����");  
        super.sessionIdle(iosession, idlestatus);  
    }  
    @Override  
    public void sessionOpened(IoSession iosession) throws Exception {  
        System.out.println("�ͻ��˻Ự��");  
        super.sessionOpened(iosession);  
    }  
}  