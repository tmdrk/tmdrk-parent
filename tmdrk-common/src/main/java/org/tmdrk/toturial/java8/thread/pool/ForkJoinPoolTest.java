package org.tmdrk.toturial.java8.thread.pool;

import org.tmdrk.toturial.entity.User;

import java.util.concurrent.*;

/**
 * ForkJoinPoolTest
 *
 * @author Jie.Zhou
 * @date 2021/2/2 14:42
 */
public class ForkJoinPoolTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();
        testRecurisiveTask();
        System.out.println("cost:"+(System.currentTimeMillis()-start));

//        runnableTest();
//
//        callableTest();
//
//        futureTest();
//
//        invokeTest();

        executeTest();
    }

    private static void testRecurisiveTask() throws InterruptedException, ExecutionException {
        CountTask countTask = new CountTask(0, 10000, 10);
//        ForkJoinPool forkJoinPool = new ForkJoinPool(1);
        ForkJoinPool forkJoinPool = new ForkJoinPool(32);
        ForkJoinTask<Integer> task = forkJoinPool.submit(countTask);
        forkJoinPool.awaitTermination(1,TimeUnit.SECONDS);
        System.out.println(task.get());
        System.out.println(countTask==task);
    }

    public static void runnableTest() {
        try {
            ForkJoinPool pool = new ForkJoinPool();
            System.out.println("runnableTest begin " + System.currentTimeMillis());
            ForkJoinTask task = pool.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                        System.out.println("ThreadName=" + Thread.currentThread().getName());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            System.out.println(task.get());
            System.out.println("runnableTest   end " + System.currentTimeMillis());
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static void callableTest() {
        try {
            ForkJoinPool pool = new ForkJoinPool();
            System.out.println("callableTest begin " + System.currentTimeMillis());
            ForkJoinTask task = pool.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    try {
                        Thread.sleep(2000);
                        System.out.println("ThreadName=" + Thread.currentThread().getName());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return "hello";
                }
            });
            System.out.println(task.get());
            System.out.println("callableTest   end " + System.currentTimeMillis());
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static void futureTest() throws ExecutionException, InterruptedException {
        User user = new User();
        ForkJoinPool pool = new ForkJoinPool();
//        Future<User> future = pool.submit(new Runnable() {
        Future<User> future = pool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    user.setUserName("设置的值");
                    System.out.println("已经设置完结！");
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, user);
        System.out.println(future);
        // 建议使用此种方式future.get() 因为get()方法呈阻塞效果
        System.out.println("user username=" + future.get().getUserName());
    }

    public static void invokeTest() throws ExecutionException, InterruptedException {
        ForkJoinPool pool = new ForkJoinPool();
        String returnString = pool.invoke(new RecursiveTask<String>() {
            @Override
            protected String compute() {
                return "hi jimi";
            }
        });
        System.out.println(returnString);
    }

    public static void executeTest() throws ExecutionException, InterruptedException {
        ForkJoinPool pool = new ForkJoinPool();

        System.out.println("parallelism="+pool.getParallelism());
        RecursiveTask<String> task = new RecursiveTask<String>() {
            @Override
            protected String compute() {
                long start = System.currentTimeMillis();
                System.out.println(Thread.currentThread().getName()+" submit async start "+start);
//                try {
//                    Thread.sleep(900);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                System.out.println(Thread.currentThread().getName()+" submit async finished "+"cost:"+(System.currentTimeMillis()-start)+" time:"+start);
                return "do what you do "+start;
            }
        };
        RecursiveTask<String> task2 = new RecursiveTask<String>() {
            @Override
            protected String compute() {
                long start = System.currentTimeMillis();
                System.out.println(Thread.currentThread().getName()+" execute async start "+start);
//                try {
//                    Thread.sleep(900);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                System.out.println(Thread.currentThread().getName()+" execute async finished "+"cost:"+(System.currentTimeMillis()-start)+" time:"+start);
                return "do what you do "+start;
            }
        };
        RecursiveTask<String> task3 = new RecursiveTask<String>() {
            @Override
            protected String compute() {
                long start = System.currentTimeMillis();
                System.out.println(Thread.currentThread().getName()+" invoke async start "+start);
//                try {
//                    Thread.sleep(900);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                System.out.println(Thread.currentThread().getName()+" invoke async finished "+"cost:"+(System.currentTimeMillis()-start)+" time:"+start);
                return "do what you do "+start;
            }
        };

        ForkJoinTask<String> submit = pool.submit(task);
        pool.execute(task2);
        String invoke = pool.invoke(task3);
        pool.awaitTermination(1,TimeUnit.SECONDS);
        System.out.println(submit.get());
        System.out.println(Thread.currentThread().getName()+" executeTest finished "+task.get());
        System.out.println(Thread.currentThread().getName()+" executeTest finished "+task2.get());
        System.out.println(Thread.currentThread().getName()+" executeTest finished "+task3.get());
        pool.shutdown();
    }
}
