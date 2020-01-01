package huffman;

import priorityQ.MinPriorityQ;
import priorityQ.MinPriorityQByHeap;

public class HuffmanEncoder {
    private long[] _frequencyTable = new long[Huffman_CONST.MAX_NUMBER_OF_CODES];                                       //코드의 개수만큼 테이블을 갖는다. 256가지의 코드의 중복회수 카운트
    private HuffmanCode[] _huffmanCodeTable = new HuffmanCode[Huffman_CONST.MAX_NUMBER_OF_CODES];                       //허프만코드의 배열 256가지
    private MinPriorityQ<HuffmanTree> _huffmanForest;

    private HuffmanTree _huffmanTree;
    private short[][] _serializedHuffmanTree;
    private int _numberOfByteCodesActuallyUsed;
    private long _numberOfBitsOfCompressedData;

    //getter/setter
    private long[] frequencyTable(){
        return this._frequencyTable;
    }

    private long frequencyOf(int byteCode){
        return this.frequencyTable()[byteCode];
    }

    private HuffmanCode[] huffmanCodeTable(){
        return this._huffmanCodeTable;
    }

    private MinPriorityQ<HuffmanTree> huffmanForest(){
        return this._huffmanForest;
    }

    private void setHuffmanForest(MinPriorityQ<HuffmanTree> newHuffmanForest){
        this._huffmanForest = newHuffmanForest;
    }

    private HuffmanTree huffmanTree(){
        return this._huffmanTree;
    }

    private void setHuffmanTree(HuffmanTree newHuffmanTree){
        this._huffmanTree = newHuffmanTree;
    }

    public short[][] serializedHuffmanTree(){
        return this._serializedHuffmanTree;
    }

    private void setSerializedHuffmanTree(short[][] newSerializedHuffmanTree){
        this._serializedHuffmanTree = newSerializedHuffmanTree;
    }

    private int numberOfByteCodesActuallyUsed(){
        return this._numberOfByteCodesActuallyUsed;
    }

    private void setNumberOfByteCodesActuallyUsed(int newNumberOfByteCodesActuallyUsed){
        this._numberOfByteCodesActuallyUsed = newNumberOfByteCodesActuallyUsed;
    }

    public long numberOfBitsOfCompressedData(){
        return this._numberOfBitsOfCompressedData;
    }

    private void setNumberOfBitsOfCompressedData(long newNumberOfBitsOfCompressedData){
        this._numberOfBitsOfCompressedData = newNumberOfBitsOfCompressedData;
    }
    //end of getter/setter

    public void incrementFrequencyOf(int byteCode){                                                                     //byteCode번째 huffmanCode의 등장주기 ++
        this.frequencyTable()[byteCode]++;
    }

    public HuffmanCode huffmanCodeOfByteCode(int byteCode){                                                             //byteCode번째 huffmanCode를 반환
        return this.huffmanCodeTable()[byteCode];
    }

    public int numberOfNodesOfSerializedHuffmanTree(){
        return (this.numberOfByteCodesActuallyUsed() * 2 - 1);
    }

    public void buildHuffmanCode(){                                                                                     //허프만 코드를 만들어냄
        this.setHuffmanTree(this.buildHuffmanTree());                                                                   //허프만트리를 만들고
        this.extractHuffmanCodeFromHuffmanTree();                                                                       //그로부터 허프만 코드를 추출
        this.setNumberOfBitsOfCompressedData(this.calcNumberOfBitsOfCompressedData());                                  //압축될 데이터 비트수 계산
        this.serializeHuffmanTree();                                                                                    //허프만 트리를 직렬화
    }

    public void initHuffmanForest(){
        for(short byteCode = 0; byteCode < Huffman_CONST.MAX_NUMBER_OF_CODES; byteCode++){                              //전체 바이트코드들의 frequency를 확인
            long frequencyOfByteCode = this.frequencyOf(byteCode);
            if(frequencyOfByteCode > 0){                                                                                //frequency가 0인 노드들은 사용하지 않는다.
                HuffmanTree currentTree = new HuffmanTree(byteCode, frequencyOfByteCode);                               //싱글노드 트리를 만들어 모두 숲에 추가해준다.
                this.huffmanForest().add(currentTree);
            }
        }
        this.setNumberOfByteCodesActuallyUsed(this.huffmanForest().size());
        return;
    }

    private HuffmanTree buildHuffmanTree(){                                                                             //싱글노드의 허프만트리로 이뤄진 huffmanForest
        this.setHuffmanForest(new MinPriorityQByHeap<HuffmanTree>(Huffman_CONST.MAX_NUMBER_OF_CODES));                        //최대 크기의 숲을 만듬
        this.initHuffmanForest();                                                                                       //숲에서 유효한 frequency를 가진 단일노드로 이뤄진것만 골라냄
        while(this.huffmanForest().size() > 1){                                                                         //숲이 하나의 트리로 합쳐질 때 까지 반복
            HuffmanTree firstMinTree = this.huffmanForest().removeMin();                                                //가장 작은 두 노드를 제거,하나의 트리로 합쳐서 다시 숲에 넣음
            HuffmanTree secondMinTree = this.huffmanForest().removeMin();
            this.huffmanForest().add(new HuffmanTree(firstMinTree, secondMinTree));                                     //이렇게 반복하면서 최종적으론 하나의 트리만 남게됨
        }
        return this.huffmanForest().removeMin();                                                                        //마지막 숲에는 트리 하나만 남는다. 이걸 리턴해주면 됨.
    }

    private void extractRecursively(HuffmanTreeNode node, HuffmanCode huffmanCode){                                     //시작 노드와 시작 코드를 주면 그걸로 재귀적으로 실행한다.
        if(node.isLeaf()){                                                                                              //리프가 발견되면 리프에대해서 처리
            HuffmanTreeLeafNode leafNode = (HuffmanTreeLeafNode) node;
            this.huffmanCodeTable()[leafNode.byteCode()] = huffmanCode.clone();                                         //노드로부터 인덱스와 코드를 가져와 테이블의 해당 인덱스에 코드를 저장
        }
        else{
            huffmanCode.appendBit(false);                                                                               //왼쪽으로 내려가면 0
            this.extractRecursively(node.left(), huffmanCode);                                                          //0붙은 허프만코드로 재귀
            huffmanCode.removeLastBit();                                                                                    //다시올라가므로 끝 비트 제거

            huffmanCode.appendBit(true);                                                                                //오른쪽으로 내려가면 1
            this.extractRecursively(node.right(), huffmanCode);                                                         //1붙은 허프만 코드로 제귀
            huffmanCode.removeLastBit();                                                                                    //다시올라가므로 끝 비트 제거
        }
    }

    private void extractHuffmanCodeFromHuffmanTree(){
        HuffmanCode huffmanCode = new HuffmanCode();
        this.extractRecursively(this.huffmanTree().root(), huffmanCode);                                                //허프만트리의 루트와 빈 허프만 코드로 재귀 시작
    }

    private long calcNumberOfBitsOfCompressedData(){
        long numberOfBits = 0;
        for(int i = 0; i < Huffman_CONST.MAX_NUMBER_OF_CODES; i++){
            if(this.huffmanCodeTable()[i] != null){
                HuffmanCode code = this.huffmanCodeTable()[i];
                numberOfBits += code.length() * this.frequencyTable()[i];                                               //해당 코드의 길이에 등장하는 빈도수를 곱한값의 합을 모두 구하면 압축된 데이터 비트의 길이
            }
        }
        return numberOfBits;
    }

    private int serializeHuffmanTreeRecursively(HuffmanTreeNode currentNode, int currentNodeIndex){
        if(currentNode.isLeaf()){
            short byteCode = ((HuffmanTreeLeafNode)currentNode).byteCode();                                             //leaf node는 left를 -1로 right를 bytecode로 한다.
            this.serializedHuffmanTree()[currentNodeIndex][Huffman_CONST.LEFT_OF_NODE] = -1;
            this.serializedHuffmanTree()[currentNodeIndex][Huffman_CONST.RIGHT_OF_NODE] = byteCode;
            return 1;
        }
        else{
            int leftNodeIndex = currentNodeIndex + 1;                                                                   //좌측노드는 current + 1
            int sizeOfLeftSubTree = this.serializeHuffmanTreeRecursively(currentNode.left(), leftNodeIndex);
            this.serializedHuffmanTree()[currentNodeIndex][Huffman_CONST.LEFT_OF_NODE] = (short) leftNodeIndex;

            int rightNodeIndex = leftNodeIndex + sizeOfLeftSubTree;                                                  //우측 노드는 current + left subtree size
            int sizeOfRightSubTree = this.serializeHuffmanTreeRecursively(currentNode.right(), rightNodeIndex);
            this.serializedHuffmanTree()[currentNodeIndex][Huffman_CONST.RIGHT_OF_NODE] = (short) rightNodeIndex;
            return (sizeOfLeftSubTree + sizeOfRightSubTree + 1);
        }
    }

    private void serializeHuffmanTree(){
        this.setSerializedHuffmanTree(new short[this.numberOfNodesOfSerializedHuffmanTree()][2]);                       //직렬화된 허프만 트리 배열을 허프만 트리의 노드개수 * 2 사이즈의 2차원 배열로
        HuffmanTreeNode root = this.huffmanTree().root();
        this.serializeHuffmanTreeRecursively(root, Huffman_CONST.ROOT_NODE_INDEX);
    }
}
