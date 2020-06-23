package udp.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Slf4j
public class TcpDecoderHanlder extends MessageToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, Object o, List list) throws Exception {
    log.info("channelHandlerContext:{},Object:{},List:{}",channelHandlerContext.toString(),o.toString(),list.toArray().toString());
    }
}
