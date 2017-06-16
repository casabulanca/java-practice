package ruiliu2.practice.nio;

import java.nio.ByteBuffer;

/**
 * Created by casa on 2017/6/6.
 */
public class DirectBuffer {

    public void DirectBufferPerform() {
        long start = System.currentTimeMillis();
        ByteBuffer bb = ByteBuffer.allocateDirect(500);//分配 DirectBuffer
        for (int i = 0; i < 100000; i++) {
            for (int j = 0; j < 99; j++) {
                bb.putInt(j);
            }
            bb.flip();
            for (int j = 0; j < 99; j++) {
                bb.getInt(j);
            }
        }
        bb.clear();
        long end = System.currentTimeMillis();
        System.out.println(String.format("direct buffer write and read: %s", end - start));
        start = System.currentTimeMillis();
        for (int i = 0; i < 20000; i++) {
            ByteBuffer b = ByteBuffer.allocateDirect(10000);//创建 DirectBuffer
        }
        end = System.currentTimeMillis();
        System.out.println(String.format("direct create write: %s", end - start));
    }

    public void ByteBufferPerform() {
        long start = System.currentTimeMillis();
        ByteBuffer bb = ByteBuffer.allocate(500);//分配 DirectBuffer
        for (int i = 0; i < 100000; i++) {
            for (int j = 0; j < 99; j++) {
                bb.putInt(j);
            }
            bb.flip();
            for (int j = 0; j < 99; j++) {
                bb.getInt(j);
            }
        }
        bb.clear();
        long end = System.currentTimeMillis();
        System.out.println(String.format("byte buffer write and read: %s", end - start));
        start = System.currentTimeMillis();
        for (int i = 0; i < 20000; i++) {
            ByteBuffer b = ByteBuffer.allocate(10000);//创建 ByteBuffer
        }
        end = System.currentTimeMillis();
        System.out.println(String.format("byte buffer create: %s", end - start));
    }

    public static void main(String[] args) {
        DirectBuffer db = new DirectBuffer();
        db.ByteBufferPerform();
        db.DirectBufferPerform();
    }
}

