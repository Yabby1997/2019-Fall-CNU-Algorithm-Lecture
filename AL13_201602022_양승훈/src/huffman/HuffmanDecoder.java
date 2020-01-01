package huffman;

import app.AppView;

public class HuffmanDecoder {
    private static final boolean DEBUG_MODE = false;                                                                    //디버그용 메시지를 정의해 사용한다.
    private static void showDebugMessage(String aMessage) {
        if(HuffmanDecoder.DEBUG_MODE) {
            AppView.outputLine("[debug] (HuffmanDecoder) " + aMessage);
        }
    }

    private short[][] _serializedHuffmanTree;                                                                           //압축파일에서 직렬화된 허프만트리를 가져와 사용하기 위해 2차원 short 배열 사용
    private int _currentNodeIndex;                                                                                      //직렬화된 허프만트리의 노드를 이동하며 확인할것이므로 인덱스를 기억해야한다.

    //constructor
    public HuffmanDecoder(short[][] givenSerializedHuffmanTree){                                                        //인자로 받아진 직렬화된 허프만트리로 생성. 인덱스는 0으로 초기화한다.
        this.setSerializedHuffmanTree(givenSerializedHuffmanTree);
        this.setCurrentNodeIndex(0);
    }

    //getter/setter
    private short[][] serializedHuffmanTree(){
        return this._serializedHuffmanTree;
    }

    private void setSerializedHuffmanTree(short[][] newSerializedHuffmanTree){
        this._serializedHuffmanTree = newSerializedHuffmanTree;
    }

    private int currentNodeIndex(){
        return this._currentNodeIndex;
    }

    private void setCurrentNodeIndex(int newCurrentNodeIndex){
        this._currentNodeIndex = newCurrentNodeIndex;
    }
    //end of getter/setter

    public int decodeBit(int bitValue){                                                                                     //압축파일로부터 읽어들인 각각의 비트를 받아서 디코드한다.
        int byteCode = -1;                                                                                                  //찾아진게 없으면, 즉 리프노드에 도달하지 못했으면 -1을 반환시킬것이다.
        if(bitValue == 0){                                                                                                  //읽어진 비트값이 0이면 왼쪽 경로를 타고 내려간다.
            this.setCurrentNodeIndex(this.serializedHuffmanTree()[this.currentNodeIndex()][Huffman_CONST.LEFT_OF_NODE]);    //왼쪽 노드가 갖는 값이 왼쪽 자식트리의 루트이다. 따라서 currentIndex로 지정해줌.
            HuffmanDecoder.showDebugMessage("? path finding ... currentNodeIndex : " + this.currentNodeIndex());
        }
        else{
            this.setCurrentNodeIndex(this.serializedHuffmanTree()[this.currentNodeIndex()][Huffman_CONST.RIGHT_OF_NODE]);   //마찬가지로 0이아니면 (1이면) 오른쪽경로로 내려가서 똑같이해준다.
            HuffmanDecoder.showDebugMessage("? path finding ... currentNodeIndex : " + this.currentNodeIndex());
        }

        if(this.serializedHuffmanTree()[this.currentNodeIndex()][Huffman_CONST.LEFT_OF_NODE] == -1){                        //그렇게 한비트 읽어 도달한 곳이 리프노드라면 (값이 -1이라면)
            HuffmanDecoder.showDebugMessage("!!LEAF FOUND!! : " + this.currentNodeIndex());                       //바이트코드가 찾아진것이므로 byteCode를 리프의 오른쪽자식(바이트코드)로 지정해주고
            byteCode = this.serializedHuffmanTree()[this.currentNodeIndex()][Huffman_CONST.RIGHT_OF_NODE];                  //다음 비트부터는 다시 전체 트리의 루트부터 탐색해야하므로 currentIndex를 0으로 다시 초기화해준다.
            this.setCurrentNodeIndex(0);
        }
        return byteCode;                                                                                                    //바이트코드가 찾아졌다면 그 값이, 아니라면 그냥 -1을 반환한다.
    }


                /*
                final exam practice
                 */
                public int decodeBit(int bitValue){
                    int byteCode = -1;
                    if(bitValue == 0){
                        this.setCurrentNodeIndex(this.serializedHuffmanTree()[this.currentNodeIndex()][Huffman_CONST.LEFT_OF_NODE]);
                    }
                    else{
                        this.setCurrentNodeIndex(this.serializedHuffmanTree()[this.currentNodeIndex()][Huffman_CONST.RIGHT_OF_NODE]);
                    }
                    if(this.serializedHuffmanTree()[this.currentNodeIndex()][Huffman_CONST.LEFT_OF_NODE] == -1){
                        byteCode = this.serializedHuffmanTree()[this.currentNodeIndex()][Huffman_CONST.RIGHT_OF_NODE];
                        this.setCurrentNodeIndex(0);
                    }
                    return byteCode;
                }


                public int decodeBit(int bitValue){
                    int byteCode = -1;
                    if(bitValue == 0){
                        this.setCurrentNodeIndex(this.serializedHuffmanTree()[this.currentNodeIndex()][Huffman_CONST.LEFT_OF_NODE]);
                    }
                    else{
                        this.setCurrentNodeIndex(this.serializedHuffmanTree()[this.currentNodeIndex()][Huffman_CONST.RIGHT_OF_NODE]);
                    }
                    if(this.serializedHuffmanTree()[this.currentNodeIndex()][Huffman_CONST.LEFT_OF_NODE] == -1){
                        byteCode = this.serializedHuffmanTree()[this.currentNodeIndex()][Huffman_CONST.RIGHT_OF_NODE];
                        this.setCurrentNodeIndex(0);
                    }
                    return byteCode;
                }

                public int decodeBit(int bitValue){
                    int byteCode = -1;
                    if(bitValue == 0){
                        this.setCurrentNodeIndex(this.serializedHuffmanTree()[this.currentNodeIndex()][Huffman_CONST.LEFT_OF_NODE]);
                    }
                    else{
                        this.setCurrentNodeIndex(this.serializedHuffmanTree()[this.currentNodeIndex()][Huffman_CONST.RIGHT_OF_NODE]);
                    }
                    if(this.serializedHuffmanTree()[this.currentNodeIndex()][Huffman_CONST.LEFT_OF_NODE] == -1){
                        byteCode = this.serializedHuffmanTree()[this.currentNodeIndex()][Huffman_CONST.RIGHT_OF_NODE];
                        this.setCurrentNodeIndex(0);
                    }
                }

                public int decodeBit(int bitValue){
                    int byteCode = -1;
                    if(bitValue == -1){
                        this.setCurrentNodeIndex(this.serializedHuffmanTree()[this.currentNodeIndex()][0]);
                    }
                    else{
                        this.setCurrentNodeIndex(this.serializedHuffmanTree()[this.currentNodeIndex()][1]);
                    }
                    if(this.serializedHuffmanTree()[this.currentNodeIndex()][0] == -1) {
                        byteCode = this.serializedHuffmanTree()[this.currentNodeIndex()][1];
                        this.setCurrentNodeIndex(0);
                    }
                    return byteCode;
                }

                public int decodeBit(int bitValue){
                    int byteCode = -1;
                    if(bitValue == 0){
                        this.setCurrentNodeIndex(this.serializedHuffmanTree()[this.currentNodeIndex()][0]);
                    }
                    else{
                        this.setCurrentNodeIndex(this.serializedHuffmanTree()[this.currentNodeIndex()][1]);
                    }
                    if(this.serializedHuffmanTree()[this.currentNodeIndex()][0] == -1){
                        byteCode = this.serializedHuffmanTree()[this.currentNodeIndex()][1];
                        this.setCurrentNodeIndex(0);
                    }
                    return byteCode;
                }

                public int decodeBit(int bitValue){
                    int byteCode = -1;
                    if(bitValue == 0){
                        this.setCurrentNodeIndex(this.serializedHuffmanTree()[this.currentNodeIndex()][0]);
                    }
                    else{
                        this.setCurrentNodeIndex(this.serializedHuffmanTree()[this.currentNodeindex()][1]);
                    }
                    if(this.serializedHuffmanTree()[this.currentNodeIndex()][0] == -1){
                        byteCode = this.serializedHuffmanTree()[this.currentNodeIndex()][1];
                        this.setCurrentNodeIndex(0);
                    }
                    return byteCode;
                }
                /*
                final exam practice
                 */
}
