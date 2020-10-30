package org.tmdrk.interview;

/**
 * DeleteNthFromEnd
 *
 * @author Jie.Zhou
 * @date 2020/10/29 13:25
 */
public class DeleteNthFromEnd {
    public static void main(String[] args) {
        ListNode head = null;
        ListNode next = new ListNode(5);
        for(int i=4;i>0;i--){
            head = new ListNode(i,next);
            next = head;
        }
        ListNode listNode = removeNthFromEnd(next, 2);
        if(listNode!=null) {
            System.out.println(listNode.val);
        }
    }

    public static ListNode removeNthFromEnd(ListNode head, int n) {
//        if(head.next==null){
//            return null;
//        }
        int count=0;
        ListNode headTmp = head;
        ListNode tmp = null;
        while(head!=null){
            count++;
            head = head.next;
            if(count > n){
                tmp = tmp==null?headTmp:tmp.next;
            }
        }
        if(tmp == null){
            return headTmp.next;
        }else{
            ListNode del = tmp.next;
            if(del != null){
                tmp.next = del.next;
            }
        }
        return headTmp;
//        if(change){
//            ListNode del = tmp.next;
//            if(del != null){
//                tmp.next = del.next;
//            }
//            return headTmp;
//        }else{
//            return headTmp.next;
//        }
    }
}
class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}
