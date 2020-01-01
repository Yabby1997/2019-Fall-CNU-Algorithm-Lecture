package huffman;

public abstract class Huffman_CONST {                                                                                   //huffman에서 사용할 상수들을 지정해둘 추상클래스
    protected static final int MAX_NUMBER_OF_CODES = 256;
    protected static final int MAX_CODE_LENGTH = MAX_NUMBER_OF_CODES - 1;
    protected static final int ROOT_NODE_INDEX = 0;
    public static final int LEFT_OF_NODE = 0;
    public static final int RIGHT_OF_NODE = 1;
}
