package huffman;

public class HuffmanTree implements Comparable<HuffmanTree> {
    private HuffmanTreeNode _root;
    private long _frequency;

    protected HuffmanTree(short givenByteCode, long givenFrequency){
        this.setFrequency(givenFrequency);
        this.setRoot(new HuffmanTreeLeafNode(givenByteCode));
    }

    protected HuffmanTree(HuffmanTree givenLeftSubTree, HuffmanTree givenRightSubTree){                                 //서브트리 두개로 주어지면 두개의 부모 노드를 새로만들어 root로
        this.setFrequency(givenLeftSubTree.frequency() + givenRightSubTree.frequency());
        this.setRoot(new HuffmanTreeNode(givenLeftSubTree.root(), givenRightSubTree.root()));
    }
    //getter/setter
    private void setFrequency(long newFrequency){
        this._frequency = newFrequency;
    }

    public long frequency(){
        return this._frequency;
    }

    protected HuffmanTreeNode root(){
        return this._root;
    }

    private void setRoot(HuffmanTreeNode newRoot){
        this._root = newRoot;
    }
    //end of getter/setter

    @Override
    public int compareTo(HuffmanTree otherTree){
        if(this.frequency() < otherTree.frequency()){
            return -1;
        }
        else if(this.frequency() > otherTree.frequency()){
            return +1;
        }
        else{
            return 0;
        }
    }
}
