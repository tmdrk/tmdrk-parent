package org.tmdrk.toturial.common.jdktool;

/**
 * @ClassName EscapeAnalysisTest
 * @Description
 * 逃逸分析（虚拟机默认开启逃逸分析）
 *  关闭逃逸分析：-Xmx1G -Xms1G -XX:-DoEscapeAnalysis -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError
 *      C:\Users\zhoujie>jps
 *      12576 Jps
 *      3908 Test
 *      8328 EscapeAnalysisTest
 *      15564
 *      8732 Launcher
 *
 *      C:\Users\zhoujie>jmap -histo 8328
 *
 *          num     #instances         #bytes  class name
 *      ----------------------------------------------
 *          1:           567       20588328  [I
 *          2:          3640        2154112  [B
 *          3:         10692        1777904  [C
 *          4:        100000        1600000  org.tmdrk.toturial.common.jdktool.EscapeAnalysisTest$User
 *          5:          6211         149064  java.lang.String
 *          6:           609          69600  java.lang.Class
 *          7:           808          54072  [Ljava.lang.Object;
 *          8:           839          33560  java.util.TreeMap$Entry
 *
 *      GC详情
 *      Heap
 * 		 PSYoungGen      total 305664K, used 31457K [0x00000000eab00000, 0x0000000100000000, 0x0000000100000000)
 * 		  eden space 262144K, 12% used [0x00000000eab00000,0x00000000ec9b8778,0x00000000fab00000)
 * 		  from space 43520K, 0% used [0x00000000fd580000,0x00000000fd580000,0x0000000100000000)
 * 		  to   space 43520K, 0% used [0x00000000fab00000,0x00000000fab00000,0x00000000fd580000)
 * 		 ParOldGen       total 699392K, used 0K [0x00000000c0000000, 0x00000000eab00000, 0x00000000eab00000)
 * 		  object space 699392K, 0% used [0x00000000c0000000,0x00000000c0000000,0x00000000eab00000)
 * 		 Metaspace       used 3347K, capacity 4500K, committed 4864K, reserved 1056768K
 * 		  class space    used 362K, capacity 388K, committed 512K, reserved 1048576K
 *
 *  开启逃逸分析：-Xmx1G -Xms1G -XX:+DoEscapeAnalysis -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError
 *      C:\Users\zhoujie>jps
 * 		18656 Launcher
 * 		3908 Test
 * 		7720 Jps
 * 		15564
 * 		16124 EscapeAnalysisTest
 *
 * 		C:\Users\zhoujie>jmap -histo 16124
 *
 * 		 num     #instances         #bytes  class name
 * 		----------------------------------------------
 * 		   1:           566       16304888  [I
 * 		   2:          3640        2154112  [B
 * 		   3:         10692        1777904  [C
 * 		   4:         32162         514592  org.tmdrk.toturial.common.jdktool.EscapeAnalysisTest$User
 * 		   5:          6211         149064  java.lang.String
 * 		   6:           609          69600  java.lang.Class
 * 		   7:           808          54072  [Ljava.lang.Object;
 * 		   8:           839          33560  java.util.TreeMap$Entry
 *
 *      GC详情
 *      Heap
 * 		 PSYoungGen      total 305664K, used 26214K [0x00000000eab00000, 0x0000000100000000, 0x0000000100000000)
 * 		  eden space 262144K, 10% used [0x00000000eab00000,0x00000000ec499be8,0x00000000fab00000)
 * 		  from space 43520K, 0% used [0x00000000fd580000,0x00000000fd580000,0x0000000100000000)
 * 		  to   space 43520K, 0% used [0x00000000fab00000,0x00000000fab00000,0x00000000fd580000)
 * 		 ParOldGen       total 699392K, used 0K [0x00000000c0000000, 0x00000000eab00000, 0x00000000eab00000)
 * 		  object space 699392K, 0% used [0x00000000c0000000,0x00000000c0000000,0x00000000eab00000)
 * 		 Metaspace       used 3351K, capacity 4500K, committed 4864K, reserved 1056768K
 * 		  class space    used 362K, capacity 388K, committed 512K, reserved 1048576K
 *
 * @Author zhoujie
 * @Date 2020/6/21 0:51
 * @Version 1.0
 **/
public class EscapeAnalysisTest {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        for(int i=0;i<100000;i++){
            alloc();
        }
        long end = System.currentTimeMillis();
        System.out.println("duration:"+(end-start));
        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private static void alloc(){
        User user = new User();
    }
    static class User{

    }

}
