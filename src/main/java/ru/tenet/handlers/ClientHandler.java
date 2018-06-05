package ru.tenet.handlers;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Arrays;

@Sharable
public class ClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        byte[] request = {0x01, 0x03, 0x00, (byte) 0x87};
        ctx.writeAndFlush(Unpooled.copiedBuffer(request));
        System.out.println("send request: ");
        System.out.println(Arrays.toString(request));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        short[] response = new short[byteBuf.readableBytes()];
        int i = 0;
        while (byteBuf.isReadable()) {
            response[i]=byteBuf.readUnsignedByte();
            i++;
        }

        System.out.println("get response:");
        System.out.println(Arrays.toString(response));
    }
}
