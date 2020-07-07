package udp.nio;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.net.DatagramSocket;

@Slf4j
@Component
public class UdpServerListener extends Thread implements ApplicationListener<ContextRefreshedEvent>,Runnable {
    private final int MAX_LENGTH = 1024;
    private final int PORT = 5060;
    private DatagramSocket datagramSocket;
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

    }
}
