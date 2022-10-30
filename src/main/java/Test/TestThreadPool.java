package Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestThreadPool {
    static ExecutorService es1= Executors.newFixedThreadPool(2);
    static ExecutorService es2=Executors.newSingleThreadExecutor();

    static Lock lock1=new ReentrantLock(true);
    static Lock lock2=new ReentrantLock();

    public static void main(String[] args) {
        String lock = "null";

        Runnable manager=()->{
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        };
        
        Runnable r1=new Runnable() {
            @Override
            public void run() {
                int cnt = 3;
//                synchronized (lock) {
//                while (true) {
                    lock1.lock();
                    System.out.println("1: " + Thread.currentThread().getId());
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    lock1.unlock();
//                }
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Runnable r2= () -> {
            int cnt=4;
            while (cnt-- > 0) {
                lock1.lock();
                System.out.println("2: " + Thread.currentThread().getId());
                try {
                    Thread.sleep(1300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lock1.unlock();
            }
        };

        Runnable slp=()->{
            try {
                System.out.println("errSLP");
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

//        es1.execute(r1);
//        es1.execute(r2);
        int tcnt=2;
        while(tcnt-- > 0) {
            es1.execute(r2);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        es1.execute(slp);

//        es2.execute(r1);
//        es2.execute(r2);
    }
}

class LockFairTest implements Runnable{
    //创建公平锁
    private static ReentrantLock lock=new ReentrantLock(true);
    public void run() {
        while(true){
            lock.lock();
            try{
                System.out.println(Thread.currentThread().getName()+"获得锁");
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }finally{
                lock.unlock();
            }
        }
    }
    public static void main(String[] args) {
        LockFairTest lft=new LockFairTest();
        Thread th1=new Thread(lft);
        Thread th2=new Thread(lft);
        Thread th3=new Thread(lft);
        th1.start();
        th2.start();
        th3.start();
    }
}
