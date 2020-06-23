package udp.handler;

import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
public class UdpDecoderHanlder extends MessageToMessageDecoder<DatagramPacket> {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, DatagramPacket byteBuf, List<Object> list) throws Exception {
        log.info("----------------------udp-----------------------");
        ByteBuf byteBuf1 = byteBuf.content();
        int size = byteBuf1.readableBytes();
        byte[] data = new byte[size];
        byteBuf1.readBytes(data);
        String tt=new String(data);
        log.info("sss==========>",tt);
        JSONObject jsonObject = JSONObject.parseObject(tt);
        log.info(jsonObject.getString("msg"));
    }
}
