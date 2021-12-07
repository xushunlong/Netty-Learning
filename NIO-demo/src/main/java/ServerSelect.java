import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

public class ServerSelect {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        Selector selector = Selector.open();
        ssc.register(selector, SelectionKey.OP_ACCEPT);
        ssc.bind(new InetSocketAddress(8888));
        while (true){
            selector.select();
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                if(key.isAcceptable()){
                    SocketChannel sc = ssc.accept();
                    sc.configureBlocking(false);
                    SelectionKey sckey = sc.register(selector,0,0);

                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i <30000000 ; i++) {
                        sb.append('a');
                    }
                    ByteBuffer byteBuffer = Charset.defaultCharset().encode(sb.toString());
                    // 写入数据
                    int write = sc.write(byteBuffer);
                    System.out.println(write);

                    // 判断数据是否写入完成
                    if(byteBuffer.hasRemaining()){
                        // 关注可写事件
                        sckey.interestOps(SelectionKey.OP_WRITE);
                        sckey.attach(byteBuffer);
                    }
                }else if(key.isWritable()){
                    ByteBuffer byteBuffer = (ByteBuffer) key.attachment();
                    SocketChannel sc = (SocketChannel) key.channel();
                    int write = sc.write(byteBuffer);
                    System.out.println(write);

                    // 如果数据发送完成做清理工作
                    if(!byteBuffer.hasRemaining()){
                        // 清除附带内容
                        key.attach(null);
                        // 注销关注写事件
                        key.interestOps(key.interestOps()- SelectionKey.OP_WRITE);
                    }
                }
            }
        }
    }
}
