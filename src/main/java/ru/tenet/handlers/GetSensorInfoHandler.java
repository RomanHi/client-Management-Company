package ru.tenet.handlers;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.log4j.Logger;
import ru.tenet.model.BaseSensorInfo;
import ru.tenet.model.SensorType;
import ru.tenet.services.SensorInfoService;

import java.util.Arrays;

public class GetSensorInfoHandler extends SimpleChannelInboundHandler<ByteBuf> {
    final static Logger logger = Logger.getLogger(GetSensorInfoHandler.class);
    private SensorInfoService sensorInfoService;

    public GetSensorInfoHandler(SensorType sensorType) {
        sensorInfoService = new SensorInfoService(sensorType);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        byte[] request = sensorInfoService.getRequest();
        logger.info("send req");
        System.out.println(Arrays.toString(request));
        ctx.writeAndFlush(Unpooled.copiedBuffer(request));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
        ctx.close();
        logger.info("context closed");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        logger.info("start read");
        short[] response = new short[byteBuf.readableBytes()];
        int i = 0;
        while (byteBuf.isReadable()) {
            response[i] = byteBuf.readUnsignedByte();
            i++;
        }
        logger.info("end read");
        logger.info("start parse");
        BaseSensorInfo sensorInfo = sensorInfoService.parseResponse(response);
        logger.info("finish parse: "+sensorInfo);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error(cause.getMessage());
        cause.printStackTrace();
        ctx.close();
    }
}
