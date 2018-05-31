package ru.tenet;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import ru.tenet.hendlers.PilotHandler;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * project for emulated slave ModbusTCP listener.
 * socket wait connect and after connect start save up bytes
 **/

public class Main {
    private int port;

    public Main(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws Exception {
        new Main(8080).start();
    }
    public void start() throws Exception {
        final PilotHandler pilotHandler = new PilotHandler();
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(group)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>(){
                        @Override
                        public void initChannel(SocketChannel ch)
                                throws Exception {
                            ch.pipeline().addLast(pilotHandler);
                        }
                    });
            ChannelFuture f = b.bind().sync();
            System.out.println("BootStrap binding\n wait connect");
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }
    }

    private static void sSoc() {
        ServerSocket sSocket;
        Socket socket;
        int port = 8080;

        try {
            boolean interapted = false;
            sSocket = new ServerSocket(port);

            while (true) {
                System.out.println("wait...");
                socket = sSocket.accept();
                System.out.println("Client connected");

                InputStream sin = socket.getInputStream();
                OutputStream sout = socket.getOutputStream();
                DataInputStream in = new DataInputStream(sin);
                byte buf[] = new byte[300];
                int count = 0;
                while (!interapted) {
                    sin.read();

                }
                for (int i = 0; i < 10; i++) {
                    System.out.print(buf[i] + " ");

                }
                System.out.println("");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
