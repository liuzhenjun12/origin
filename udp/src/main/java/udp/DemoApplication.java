package udp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;
import reactor.netty.tcp.TcpServer;
import reactor.netty.udp.UdpServer;
import udp.handler.TcpDecoderHanlder;
import udp.handler.UdpDecoderHanlder;

import java.time.Duration;

@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
    @Bean
    CommandLineRunner serverRunner(UdpDecoderHanlder udpDecoderHanlder, TcpDecoderHanlder tcpDecoderHanlder) {
        return strings -> {
            createUdpServer(udpDecoderHanlder);
            createTcpServer(tcpDecoderHanlder);
        };
    }

    /**
     * 创建UDP Server
     * @param udpDecoderHanlder： 解析UDP Client上报数据handler
     */
    private void createUdpServer(UdpDecoderHanlder udpDecoderHanlder) {
        UdpServer.create()
                .handle((in,out) -> {
                    in.receive()
                            .asByteArray()
                            .subscribe();
                    return Flux.never();

                })
                .port(10604)
                .doOnBound(conn -> conn.addHandlerLast("decoder",udpDecoderHanlder)) //可以添加多个handler
                .bindNow(Duration.ofSeconds(30));

    }

    /**
     * 创建TCP Server
     * @param tcpDecoderHanlder： 解析TCP Client上报数据的handler
     */
    private void createTcpServer(TcpDecoderHanlder tcpDecoderHanlder) {
        TcpServer.create()
                .handle((in,out) -> {
                    in.receive()
                            .asByteArray()
                            .subscribe();
                    return Flux.never();

                })
                .doOnConnection(conn ->
                        conn.addHandler(tcpDecoderHanlder)) //实例只写了如何添加handler,可添加delimiter，tcp生命周期，decoder，encoder等handler
                .port(8020)
                .bindNow();
    }
}
