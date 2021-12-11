import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultPromise;

import java.util.concurrent.ExecutionException;

public class PromiseFuture {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();
        EventLoop eventLoop = nioEventLoopGroup.next();
        DefaultPromise<Integer> promise = new DefaultPromise<>(eventLoop);
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("执行计算....");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                promise.setSuccess(1);
            }
        }).start();
        while (true){
            System.out.println(promise.getNow());
        }
//        System.out.println(promise.get());

    }

}
