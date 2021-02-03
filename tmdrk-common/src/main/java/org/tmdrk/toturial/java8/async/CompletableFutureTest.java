package org.tmdrk.toturial.java8.async;

import org.tmdrk.toturial.arithmetic.encryption.MD5.MD5;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * CompletableFutureTest
 *
 * @author Jie.Zhou
 * @date 2021/2/2 14:26
 */
public class CompletableFutureTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        multAsync();

        multAsyncConcurrent();

        supplyAsync();

        compose();

        exception();

        allOf();
    }

    /**
     * 多异步任务串联
     * @return: void
     */
    private static void multAsync() {
        String loginName = "joy";
        try {
            String userInfo = CompletableFuture.supplyAsync(() -> login(loginName))
                    .thenApplyAsync(CompletableFutureTest::userInfo)
                    .get();
            System.out.println("userInfo:"+userInfo);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static String login(String loginName){
        System.out.println(Thread.currentThread());
        return MD5.encodeMD5Hex(loginName);
    }
    public static String userInfo(String token){
        System.out.println(Thread.currentThread());
        return MD5.encodeMD5Hex(token);
    }

    /**
     * 多异步任务并发
     * @return: void
     */
    private static void multAsyncConcurrent() {
        CompletableFuture<String> f = new CompletableFuture<>();

        new Thread(() -> {
            try {
                System.out.println(Thread.currentThread()+" thread1:" + f.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                System.out.println(Thread.currentThread()+" thread2:" + f.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }).start();

        f.complete("hello");
    }

    private static void supplyAsync() throws ExecutionException, InterruptedException {
        CompletableFuture<String> f = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
                System.out.println(Thread.currentThread()+" sleep finished");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello";
        });

        String result = f.get();

        System.out.println(result);

        CompletableFuture<Void> f1 = CompletableFuture
                .supplyAsync(() -> "hello")
                .thenApplyAsync(res -> res + " world!")
                .thenAcceptAsync(System.out::println);
        // wait for job done
        f1.get();

        CompletableFuture<String> f2 = CompletableFuture
                .supplyAsync(() -> "hello")
                .thenApplyAsync(res -> res + " world!")
                .whenComplete((res, err) -> {
                    if (err != null) {
                        err.printStackTrace();
                    } else {
                        System.out.println(res);
                    }
                });
        // wait for job done
        System.out.println("return="+f2.get());
    }

    /**
     * 组合
     * @return: void
     */
    private static void compose() throws ExecutionException, InterruptedException {
        CompletableFuture<String> f = CompletableFuture.supplyAsync(() -> "Hello")
                .thenCompose(res -> CompletableFuture.supplyAsync(() -> res + " World"))
                .thenCombine(CompletableFuture.supplyAsync(() -> "CompletableFuture!"), (a, b) -> a +","+ b);
        System.out.println("return="+f.get());
    }

    /**
     * 异常处理
     * @return: void
     */
    private static void exception() throws ExecutionException, InterruptedException {
        // 异常处理
        CompletableFuture<Object> f = CompletableFuture.supplyAsync(() -> "Hello")
                .thenApplyAsync(res -> res + "World")
                .thenApplyAsync(res -> {
                    throw new RuntimeException("error");
                })
                .exceptionally(e -> {
                    //handle exception here
                    e.printStackTrace();
                    return null;
                });
        System.out.println(f.get());

        // 执行结果处理
        CompletableFuture<Object> f2 = CompletableFuture.supplyAsync(() -> "Hello")
                .thenApplyAsync(res -> res + "World")
                .thenApplyAsync(res -> {
                    throw new RuntimeException("error");
                })
                .handleAsync((res, err) -> {
                    if (err != null) {
                        //handle exception here
                        return null;
                    } else {
                        return res;
                    }
                });
        System.out.println(f2.get());
    }
    /**
     * 组合
     * @return: void
     */
    private static void allOf() throws ExecutionException, InterruptedException {
        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() ->{
            System.out.println(Thread.currentThread()+" "+new Date());
            return "hello";
        });
        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread()+" "+new Date());
            return "world";
        });
        CompletableFuture<String> f3 = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread()+" "+new Date());
            return "!";
        });

        // 使用allOf方法
        CompletableFuture<Void> all = CompletableFuture.allOf(f1, f2, f3);
        all.get();

//        System.out.println(f1.get());
//        System.out.println(f2.get());
//        System.out.println(f3.get());

        // 结合StreamAPI
        List<String> result = Stream.of(f1, f2, f3)
                .map(CompletableFuture::join)
                .collect(Collectors.toList());

        System.out.println(result);
    }

}
