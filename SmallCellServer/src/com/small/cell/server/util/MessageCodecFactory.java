package com.small.cell.server.util;

import java.nio.charset.Charset;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

public class MessageCodecFactory implements ProtocolCodecFactory {    
   
private final ServerDecoder decoder;    
public MessageCodecFactory() {    
this(Charset.defaultCharset());    
}    
public MessageCodecFactory(Charset charSet) {    
   
this.decoder = new ServerDecoder();    
}    
@Override    
public ProtocolDecoder getDecoder(IoSession session) throws    
Exception {    
return decoder;    
}
@Override
public ProtocolEncoder getEncoder(IoSession arg0) throws Exception {
	// TODO Auto-generated method stub
	return null;
}    
    
}    