package org.tmdrk.interview;

/**
 * MergeSortList
 * 合并K个升序链表
 * 示例 1：
 *
 * 输入：lists = [[1,4,5],[1,3,4],[2,6]]
 * 输出：[1,1,2,3,4,4,5,6]
 * 解释：链表数组如下：
 * [
 *   1->4->5,
 *   1->3->4,
 *   2->6
 * ]
 * 将它们合并到一个有序链表中得到。
 * 1->1->2->3->4->4->5->6
 *
 * @author Jie.Zhou
 * @date 2020/11/2 13:48
 */
public class MergeSortList {
    public static void main(String[] args) {
        ListNode node13 = new ListNode(5);
        ListNode node12 = new ListNode(4,node13);
        ListNode node11 = new ListNode(1,node12);

        ListNode node23 = new ListNode(4);
        ListNode node22 = new ListNode(3,node23);
        ListNode node21 = new ListNode(1,node22);

        ListNode node33 = new ListNode(6);
//        ListNode node32 = new ListNode(4,node33);
        ListNode node31 = new ListNode(2,node33);

        ListNode[] lists = new ListNode[3];
        lists[0] = node11;
//        lists[1] = node21;
//        lists[2] = node31;

        ListNode listNode = mergeKLists(lists);
        while (listNode!=null){
            System.out.print(listNode.val);
            listNode = listNode.next;
        }

    }
    public static ListNode mergeKLists(ListNode[] lists) {
        ListNode node = new ListNode();
        ListNode tmp;
        for(int i=0;i<lists.length;i++){
            tmp = node;
            ListNode listNode = lists[i];
            while (listNode != null){
                if(node.next==null || tmp.next == null){
                    tmp.next = listNode;
                    break;
                }else if(tmp.next.val<=listNode.val){
                    tmp = tmp.next;
                }else{
                    ListNode n = tmp.next;
                    tmp.next = new ListNode(listNode.val,n);
                    tmp = tmp.next;
                    listNode = listNode.next;
                }
            }
        }
        return node.next;
    }
}
