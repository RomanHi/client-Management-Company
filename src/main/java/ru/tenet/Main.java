package ru.tenet;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.apache.log4j.Logger;
import ru.tenet.handlers.ClientHandler;
import ru.tenet.handlers.GetSensorInfoHandler;
import ru.tenet.model.SensorType;

import java.net.InetSocketAddress;

public class Main {
    final static Logger logger = Logger.getLogger(Main.class);
    private final String host;
    private final int port;

    public Main(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();

            b.group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(host, port))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch)
                                throws Exception {
                            ch.pipeline().addLast(
                                    new GetSensorInfoHandler(SensorType.TV7));
                        }
                    });
            ChannelFuture f = b.connect().sync();
            logger.info("service connected");
            f.channel().closeFuture().sync();
            logger.info("ending transmit");
        } finally {
            group.shutdownGracefully().sync();
            logger.info("service shutdown");
        }
    }
    public static void main(String[] args) throws Exception {
        String host ="127.0.0.1";
        int port = 8080;
        Main main = new Main(host, port);
        logger.info("service started");
        main.start();
        logger.info("service end");
    }


    static int crc16(final byte[] buffer) {
        int crc = 0xFFFF;

        for (int j = 0; j < buffer.length; j++) {
            crc = ((crc >>> 8) | (crc << 8)) & 0xffff;
            crc ^= (buffer[j] & 0xff);//byte to int, trunc sign
            crc ^= ((crc & 0xff) >> 4);
            crc ^= (crc << 12) & 0xffff;
            crc ^= ((crc & 0xFF) << 5) & 0xffff;
        }
        crc &= 0xffff;
        return crc;
    }

}
