import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.UUID;

public class MainTest {

    long l1;
    long[] l2;
    long[] l3;
    int i1;

    class Inner{
        public void a(){
            System.out.println(l1);
            System.out.println(MainTest.this.l1);
        }
    }

    public static void main(String[] args) throws Exception{
        Selector selector = Selector.open();
        ServerSocketChannel sc = ServerSocketChannel.open();
        sc.bind(new InetSocketAddress(2008));
        sc.configureBlocking(false);
        sc.register(selector, SelectionKey.OP_ACCEPT);
        sc = ServerSocketChannel.open();
        sc.bind(new InetSocketAddress(2009));
        sc.configureBlocking(false);
        sc.register(selector, SelectionKey.OP_ACCEPT);
        int data = 0;

        while(Integer.parseInt("1")==1){
            if (selector.select(3000L) == 0) {
                System.out.print(".");
                continue;
            }
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            while (it.hasNext()) {
                SelectionKey key = it.next();
                it.remove();
                if (key.isAcceptable()) {
                    ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                    SocketChannel accept = channel.accept();
                    accept.configureBlocking(false);
                    InetSocketAddress address = (InetSocketAddress)channel.getLocalAddress();
                    System.out.println("\n" + address.getPort() + " receive connect");
                    accept.register(key.selector(), SelectionKey.OP_WRITE|SelectionKey.OP_READ, ByteBuffer.allocate(10));
                }
                if (key.isReadable()) {
                    System.out.println("read data");
                    SocketChannel  channel = (SocketChannel)key.channel();
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    if (channel.read(buffer) == -1) {
                        channel.close();
                    }
                    String da = new String(buffer.array());
                    data = Integer.valueOf(da.trim()) + 1;
                    System.out.println("data> " + da);
                    channel.register(key.selector(), SelectionKey.OP_WRITE/*|SelectionKey.OP_READ*/, ByteBuffer.allocate(10));
                }
                if (key.isWritable()) {
                    System.out.println("write data");
                    SocketChannel  channel = (SocketChannel)key.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(10);
                    buffer.put(String.valueOf(data).getBytes());
                    buffer.flip();
                    channel.write(buffer);
                    channel.register(key.selector(), /*SelectionKey.OP_WRITE|*/SelectionKey.OP_READ, ByteBuffer.allocate(10));
//                    channel.close();
                }
            }
        }

        System.exit(0);

        System.out.println(unsafe.objectFieldOffset(clazz.getDeclaredField("i1")));
        System.out.println(unsafe.objectFieldOffset(clazz.getDeclaredField("l1")));
        System.out.println(unsafe.objectFieldOffset(clazz.getDeclaredField("l2")));
        System.out.println(unsafe.objectFieldOffset(clazz.getDeclaredField("l3")));

        System.out.println(UUID.fromString("a1a4a1b7-f06d-4e4c-8d55-5742b94528ed").toString());
    }

    private static Unsafe unsafe;
    static Class clazz = MainTest.class;
    static{
        Field theUnsafe = null;
        try {
            theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            unsafe = (Unsafe)theUnsafe.get(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
