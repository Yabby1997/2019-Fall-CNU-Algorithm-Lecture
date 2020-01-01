package fileIO;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public class ExtendedBufferedOutputStream extends BufferedOutputStream {
    private static ByteBuffer shortBuffer = ByteBuffer.allocate(Short.BYTES);
    private static ByteBuffer intBuffer = ByteBuffer.allocate(Integer.BYTES);
    private static ByteBuffer longBuffer = ByteBuffer.allocate(Long.BYTES);

    public ExtendedBufferedOutputStream(OutputStream out){
        super(out);
    }

    public void writeShort(short shortValue) throws IOException{                                                        //버퍼를 비우고, 인자로 입력된걸 버퍼에 쓴다
        shortBuffer.clear();
        shortBuffer.putShort(shortValue);
        this.write(shortBuffer.array(), 0, Short.BYTES);
    }

    public void writeInt(int intValue) throws IOException{
        intBuffer.clear();
        intBuffer.putInt(intValue);
        this.write(intBuffer.array(), 0, Integer.BYTES);
    }

    public void writeLong(long longValue) throws IOException{
        longBuffer.clear();
        longBuffer.putLong(longValue);
        this.write(longBuffer.array(), 0, Long.BYTES);
    }
}
