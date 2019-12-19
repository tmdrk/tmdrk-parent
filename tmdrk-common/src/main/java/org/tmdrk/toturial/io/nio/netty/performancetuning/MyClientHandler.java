package org.tmdrk.toturial.io.nio.netty.performancetuning;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @ClassName MyClientHandler
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/8/9 13:12
 * @Version 1.0
 **/
@ChannelHandler.Sharable
public class MyClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    public static final ChannelHandler INSTANCE = new MyClientHandler();
    private static AtomicLong beginTime = new AtomicLong();
    private static AtomicLong totalResponseTime = new AtomicLong();
    private static AtomicLong totalRequest = new AtomicLong();

    public static final Thread THREAD = new Thread(()->{
        try{
            while(true){
                long duration = System.currentTimeMillis() - beginTime.get();
                if(duration!=0){
                    System.out.println("qps:"+1000*totalRequest.get()/duration
                            +",  avg response time:"+ ((float)totalResponseTime.get())/totalRequest.get()
                            +",  totalRequest:"+totalRequest
                            +",  totalResponseTime:"+totalResponseTime);
                    Thread.sleep(2000);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    });

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        totalResponseTime.addAndGet(System.currentTimeMillis()-msg.readLong());
        totalRequest.incrementAndGet();
        if(beginTime.compareAndSet(0,System.currentTimeMillis())){
            THREAD.start();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.executor().scheduleAtFixedRate(()->{
            ByteBuf byteBuf = ctx.alloc().ioBuffer();
            byteBuf.writeLong(System.currentTimeMillis());
            ctx.channel().writeAndFlush(byteBuf);
        },0,1, TimeUnit.SECONDS);
    }
}
