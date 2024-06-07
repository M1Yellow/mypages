package cn.m1yellow.mypages.common.util;

import java.util.List;
import java.util.concurrent.*;

public class ThreadPoolUtil {

    /**
     * 系统可用计算资源，4核8线程，得到的结果是 8
     */
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();

    /**
     * 核心线程数，线程池创建时候初始化的线程数 4
     */
    private static final int CORE_POOL_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 4));

    /**
     * 最大线程数，线程池最大的线程数，只有在工作缓冲队列满了之后才会申请超过核心线程数的线程
     */
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;

    /**
     * 空闲线程存活时间，当超过了核心线程出之外的线程在空闲时间到达之后会被销毁，单位：秒
     */
    private static final int KEEP_ALIVE_SECONDS = 2 * 60;

    /**
     * 工作队列，用来缓冲执行任务的队列
     */
    private static final BlockingQueue<Runnable> POOL_WORK_QUEUE = new LinkedBlockingQueue<>(128);

    /**
     * 线程池对象
     */
    private static final ThreadPoolExecutor THREAD_POOL_EXECUTOR;


    static {
        /**
         * new ThreadPoolExecutor.AbortPolicy() // 银行满了，还有人进来，不处理这个人的，抛出异常
         * new ThreadPoolExecutor.CallerRunsPolicy() // 哪来的去哪里
         * new ThreadPoolExecutor.DiscardPolicy() // 队列满了，丢掉任务，不会抛出异常
         * new ThreadPoolExecutor.DiscardOldestPolicy() // 队列满了，尝试去和最早的竞争，也不会抛出异常
         */
        THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(
                // 核心线程数
                CORE_POOL_SIZE,
                // 最大线程数
                MAXIMUM_POOL_SIZE,
                // 空闲线程执行时间
                KEEP_ALIVE_SECONDS,
                // 空闲线程执行时间单位
                TimeUnit.SECONDS,
                // 工作队列（或阻塞队列）
                POOL_WORK_QUEUE,
                // 工厂模式
                Executors.defaultThreadFactory(),
                // 饱和拒绝策略
                // 当线程池没有处理能力的时候，该策略会直接在 execute 方法的调用线程中运行被拒绝的任务；如果执行程序已关闭，则会丢弃该任务
                new ThreadPoolExecutor.DiscardOldestPolicy()
        );

    }


    /**
     * 执行线程任务
     *
     * @param runnable 任务线程
     */
    public static void executor(Runnable runnable) {
        THREAD_POOL_EXECUTOR.execute(runnable);
    }


    /**
     * 获取线程池状态
     *
     * @return 返回线程池状态
     */
    public static boolean isShutDown() {
        return THREAD_POOL_EXECUTOR.isShutdown();
    }


    /**
     * 停止正在执行的线程任务
     *
     * @return 返回等待执行的任务列表
     */
    public static List<Runnable> shutDownNow() {
        return THREAD_POOL_EXECUTOR.shutdownNow();
    }


    /**
     * 关闭线程池
     */
    public static void shutdown() {
        THREAD_POOL_EXECUTOR.shutdown();
    }


    /**
     * 关闭线程池后判断所有任务是否都已完成
     *
     * @return
     */
    public static boolean isTerminated() {
        return THREAD_POOL_EXECUTOR.isTerminated();
    }


}
