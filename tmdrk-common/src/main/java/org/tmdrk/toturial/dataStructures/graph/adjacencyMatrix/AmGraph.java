package org.tmdrk.toturial.dataStructures.graph.adjacencyMatrix;

/**
 * 邻接矩阵实现图结构
 */
public class AmGraph {
    String[] vertexs; //顶点信息
    int[][] matrix;  //边信息，包含权重
    Byte graphType ; //图类型 0 无向图 1 有向图
    private int vertexNum = 0; //顶点数量
    private int EdgeNum = 0;   //边的数量
    private static final int MAX_WEIGHT=2147483647;//设置最大权重2147483647代表无穷大,常量  因为是静态私有的常量，只能在本类中有效

    public int getVertexNum() {
        return vertexNum;
    }

    public int getEdgeNum() {
        return EdgeNum;
    }

    public void setVertexNum(int vertexNum) {
        this.vertexNum = vertexNum;
    }

    public void setEdgeNum(int edgeNum) {
        EdgeNum = edgeNum;
    }

    public AmGraph(){
        this(4);
    }
    public AmGraph(int dimension){
        this(4,(byte)0);
    }

    public AmGraph(int dimension,byte graphType){
        vertexs = new String[dimension];
        matrix = new int[dimension][dimension];
        this.graphType = graphType;
//        for(int i=0;i<vertexs[i].length();i++){
//            for(int j=0;j<vertexs[i].length();j++){
//
//            }
//        }
    }

    public static void main(String[] args) {
        System.out.println("你好！");
        System.out.println("hi you are welcome");
    }

}
