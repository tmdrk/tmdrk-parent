package org.tmdrk.interview;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * ValidSudoku
 * 有效的数独
 * 判断一个 9x9 的数独是否有效。只需要根据以下规则，验证已经填入的数字是否有效即可。
 *
 * 数字 1-9 在每一行只能出现一次。
 * 数字 1-9 在每一列只能出现一次。
 * 数字 1-9 在每一个以粗实线分隔的 3x3 宫内只能出现一次。
 * 示例 1:
 *
 * 输入:
 * [
 *   ["5","3",".",".","7",".",".",".","."],
 *   ["6",".",".","1","9","5",".",".","."],
 *   [".","9","8",".",".",".",".","6","."],
 *   ["8",".",".",".","6",".",".",".","3"],
 *   ["4",".",".","8",".","3",".",".","1"],
 *   ["7",".",".",".","2",".",".",".","6"],
 *   [".","6",".",".",".",".","2","8","."],
 *   [".",".",".","4","1","9",".",".","5"],
 *   [".",".",".",".","8",".",".","7","9"]
 * ]
 * 输出: true
 * 示例 2:
 *
 * 输入:
 * [
 *   ["8","3",".",".","7",".",".",".","."],
 *   ["6",".",".","1","9","5",".",".","."],
 *   [".","9","8",".",".",".",".","6","."],
 *   ["8",".",".",".","6",".",".",".","3"],
 *   ["4",".",".","8",".","3",".",".","1"],
 *   ["7",".",".",".","2",".",".",".","6"],
 *   [".","6",".",".",".",".","2","8","."],
 *   [".",".",".","4","1","9",".",".","5"],
 *   [".",".",".",".","8",".",".","7","9"]
 * ]
 * 输出: false
 *
 * @author Jie.Zhou
 * @date 2020/11/6 14:34
 */
public class ValidSudoku {
//    static Map<String, Set<Character>> map = new HashMap<>();
    public static void main(String[] args) {
        char[][] board = new char[][]{
                {'.','8','7','6','5','4','3','2','1'},
                {'2','.','.','.','.','.','.','.','.'},
                {'3','.','.','.','.','.','.','.','.'},
                {'4','.','.','.','.','.','.','.','.'},
                {'5','.','.','.','.','.','.','.','.'},
                {'6','.','.','.','.','.','.','.','.'},
                {'7','.','.','.','.','.','.','.','.'},
                {'8','.','.','.','.','.','.','.','.'},
                {'9','.','.','.','.','.','.','.','.'}
        };
        boolean validSudoku = isValidSudoku(board);
        System.out.println(validSudoku);
    }
    public static boolean isValidSudoku(char[][] board) {
        return doValidSudoku(board);
    }

    public static boolean doValidSudoku(char[][] board) {
        Map<String, Set<Character>> map = new HashMap<>();
        for(int i=0;i<board.length;i++){
            if(!map.containsKey(i+"-")){
                map.put(i+"-",new HashSet<>());
            }
            if(!map.containsKey(i+"|")){
                map.put(i+"|",new HashSet<>());
            }
            String key = getX9(i,i*3%9);
            if(!map.containsKey(key)){
                map.put(key,new HashSet<>());
            }
        }
        for(int i=0;i<board.length;i++){
            for(int j=0;j<board.length;j++){
                boolean b = doValid(map,board,i,j);
                if(!b){
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean doValid(Map<String, Set<Character>> map,char[][] board, int row, int col) {
        String key = getX9(row,col);
        if(map.get(row+"-").contains(board[row][col])){
            return false;
        }else{
            if(board[row][col]!='.'){
                map.get(row+"-").add(board[row][col]);
            }
        }
        if(map.get(col+"|").contains(board[row][col])){
            return false;
        }else{
            if(board[row][col]!='.') {
                map.get(col + "|").add(board[row][col]);
            }
        }
        if(map.get(key).contains(board[row][col])){
            return false;
        }else{
            if(board[row][col]!='.') {
                map.get(key).add(board[row][col]);
            }
        }
        return true;
    }

    private static String getX9(int row, int idx) {
        return (row/3)*3+""+(idx/3)*3;
    }
}
