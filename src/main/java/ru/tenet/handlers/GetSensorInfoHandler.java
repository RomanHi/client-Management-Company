package ru.tenet.handlers;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import ru.tenet.model.SensorType;
import ru.tenet.model.TV7SensorInfo;
import ru.tenet.services.SensorService;

public class GetSensorInfoHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private SensorService sensorService;

    public GetSensorInfoHandler(SensorType sensorType) {
        sensorService= new SensorService(sensorType);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        byte[] request = sensorService.getRequest();
        ctx.writeAndFlush(Unpooled.copiedBuffer(request));
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        short[] response = new short[byteBuf.readableBytes()];
        int i = 0;
        while (byteBuf.isReadable()) {
            response[i]=byteBuf.readUnsignedByte();
            i++;
        }
        TV7SensorInfo sensorInfo = (TV7SensorInfo) sensorService.parseResponse(response);
        System.out.println(sensorInfo);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
