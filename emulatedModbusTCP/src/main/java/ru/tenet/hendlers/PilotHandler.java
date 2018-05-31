package ru.tenet.hendlers;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

@Sharable
public class PilotHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;

        while (buf.isReadable()) {
            System.out.println(buf.readByte());
        }
        System.out.println("");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        byte[] bytes = {0x00, 0x01, 0x00, 0x00, 0x00, 0x08, 0x0e,
                0x03, 0x0c, 0x17, 0x02, 0x01,  0x08,0x01,  0x08,(byte) 0x81,  0x08,0x05,0x05, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF};

        ctx.writeAndFlush(Unpooled.copiedBuffer(bytes)).addListener(ChannelFutureListener.CLOSE);
        System.out.println("send response");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
