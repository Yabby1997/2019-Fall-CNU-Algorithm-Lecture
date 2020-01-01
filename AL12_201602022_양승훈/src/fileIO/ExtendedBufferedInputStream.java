package fileIO;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class ExtendedBufferedInputStream extends BufferedInputStream {
    //short, int, long을 위한 bytebuffer
    private static ByteBuffer shortBuffer = ByteBuffer.allocate(Short.BYTES);
    private static ByteBuffer intBuffer = ByteBuffer.allocate(Integer.BYTES);
    private static ByteBuffer longBuffer = ByteBuffer.allocate(Long.BYTES);

    public ExtendedBufferedInputStream(InputStream in){
        super(in);
    }

    public short readShort() throws IOException{
        shortBuffer.clear();
        this.read(shortBuffer.array(), 0, Short.BYTES);                                                             //short.BYTES만큼 읽어서 shortBuffer에 저장
        return shortBuffer.getShort();
    }

    public int readInt() throws IOException{
        intBuffer.clear();
        this.read(intBuffer.array(), 0, Integer.BYTES);
        return intBuffer.getInt();
    }

    public long readLong() throws IOException{
        longBuffer.clear();
        this.read(longBuffer.array(), 0, Long.BYTES);
        return longBuffer.getLong();
    }
}
