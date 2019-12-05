package ru.javawebinar.basejava;

import java.util.ArrayList;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class MainConcurrency2 {

    protected static final int THREADS_NUMBER = 1000;

    public static void main(String[] args) throws InterruptedException, ExecutionException {

//        new MCLatch().runThreads();
        new MCLock().runThreads();
        new MCAtomic().runThreads();
//        new MCExecutors().runThreads();
//        new MCExecutorsFuture().runThreads();
//        new MCCompletionService().runThreads();
    }

}


class MCLatch {
    private int counter;

    public void runThreads() throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(MainConcurrency2.THREADS_NUMBER);
        for (int i = 0; i < MainConcurrency2.THREADS_NUMBER; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    inc();
                }
                latch.countDown();
            });
            thread.start();
        }
        System.out.println(this.getClass().getName() + ": " + counter);
        latch.await();
        System.out.println(this.getClass().getName() + ": " + counter);
    }

    private synchronized void inc() {
        counter++;
    }
}

class MCLock {
    private int counter;
    ReentrantLock lock = new ReentrantLock();

    public void runThreads() throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(MainConcurrency2.THREADS_NUMBER);
        for (int i = 0; i < MainConcurrency2.THREADS_NUMBER; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    inc();
                }
                latch.countDown();
            });
            thread.start();
        }
        System.out.println(this.getClass().getName() + ": " + counter);
        latch.await();
        System.out.println(this.getClass().getName() + ": " + counter);
    }

    private void inc() {
        lock.lock();
        try {
            counter++;
        } finally {
            lock.unlock();
        }

    }
}

class MCAtomic {
    private AtomicInteger counter = new AtomicInteger(0);

    public void runThreads() throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(MainConcurrency2.THREADS_NUMBER);
        for (int i = 0; i < MainConcurrency2.THREADS_NUMBER; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    inc();
                }
                latch.countDown();
            });
            thread.start();
        }
        System.out.println(this.getClass().getName() + ": " + counter);
        latch.await();
        System.out.println(this.getClass().getName() + ": " + counter);
    }

    private void inc() {
        counter.incrementAndGet();
    }
}

class MCExecutors {
    private int counter;

    public void runThreads() throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(MainConcurrency2.THREADS_NUMBER);
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < MainConcurrency2.THREADS_NUMBER; i++) {
            executorService.submit(() -> {
                for (int j = 0; j < 1000; j++) {
                    inc();
                }
                latch.countDown();
            });
        }
        System.out.println(this.getClass().getName() + ": " + counter);
        latch.await();
        System.out.println(this.getClass().getName() + ": " + counter);
        executorService.shutdown();
    }

    private synchronized void inc() {
        counter++;
    }
}

class MCExecutorsFuture {
    private int counter;

    public void runThreads() throws InterruptedException, ExecutionException {

        CountDownLatch latch = new CountDownLatch(MainConcurrency2.THREADS_NUMBER);
        ExecutorService executorService = Executors.newCachedThreadPool();
        ArrayList<Future<Long>> futures = new ArrayList<>();
        for (int i = 0; i < MainConcurrency2.THREADS_NUMBER; i++) {
            Future<Long> future = executorService.submit(() -> {
                for (int j = 0; j < 1000; j++) {
                    inc();
                }
                latch.countDown();
                return latch.getCount();
            });
            switch (i) {
                case 0:
                case (MainConcurrency2.THREADS_NUMBER / 2):
                case MainConcurrency2.THREADS_NUMBER - 1:
                    futures.add(future);
            }
        }
        System.out.println(this.getClass().getName() + ": " + counter);
        System.out.println("     f1: " + futures.get(0).get());
        System.out.println("     f2: " + futures.get(1).get());
        System.out.println("     f3: " + futures.get(2).get());

        latch.await();
        System.out.println(this.getClass().getName() + ": " + counter);
        executorService.shutdown();
    }

    private synchronized void inc() {
        counter++;
    }
}

class MCCompletionService {
    private int counter;

    public void runThreads() throws InterruptedException, ExecutionException {

        CountDownLatch latch = new CountDownLatch(MainConcurrency2.THREADS_NUMBER);
        ExecutorService executorService = Executors.newCachedThreadPool();
        CompletionService completionService = new ExecutorCompletionService(executorService);
        for (int i = 0; i < MainConcurrency2.THREADS_NUMBER; i++) {
            completionService.submit(() -> {
                for (int j = 0; j < 1000; j++) {
                    inc();
                }
                latch.countDown();
                return latch.getCount();
            });
        }
        for (int i = 0; i < 3; i++) {
            Future future = completionService.take();
            System.out.println("     f" + i + ": " + future.get());
        }
        System.out.println(this.getClass().getName() + ": " + counter);

        latch.await();
        System.out.println(this.getClass().getName() + ": " + counter);
        executorService.shutdown();
    }

    private synchronized void inc() {
        counter++;
    }
}