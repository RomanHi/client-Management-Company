package ru.tenet.handlers;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import ru.tenet.model.BaseSensorInfo;
import ru.tenet.model.SensorType;
import ru.tenet.services.SensorInfoService;

import java.util.Arrays;

public class GetSensorInfoHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private SensorInfoService sensorInfoService;

    public GetSensorInfoHandler(SensorType sensorType) {
        sensorInfoService = new SensorInfoService(sensorType);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        byte[] request = sensorInfoService.getRequest();
        System.out.println("send request:");
        System.out.println(Arrays.toString(request));
        ctx.writeAndFlush(Unpooled.copiedBuffer(request));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        short[] response = new short[byteBuf.readableBytes()];
        int i = 0;
        while (byteBuf.isReadable()) {
            response[i] = byteBuf.readUnsignedByte();
            i++;
        }
        BaseSensorInfo sensorInfo = sensorInfoService.parseResponse(response);
        System.out.println("get response:");
        System.out.println(Arrays.toString(response));
        System.out.println(sensorInfo);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
