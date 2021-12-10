import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class HelloServer {
    public static void main(String[] args) {
        EventLoopGroup eventExecutors = new NioEventLoopGroup();
    }
}
