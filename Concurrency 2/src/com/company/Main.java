package com.company;

import com.company.AnotherThread;
import com.company.MyRunnable;

import static com.company.ThreadColor.ANSI_GREEN;
import static com.company.ThreadColor.ANSI_PURPLE;
import static com.company.ThreadColor.ANSI_RED;

public class Main {

    public static void main(String[] args) {
        System.out.println(ANSI_PURPLE+"Hello from the main thread.");

        final Thread anotherThread = new AnotherThread();
        anotherThread.setName("== Another Thread ==");
        anotherThread.start();

        new Thread() {
            public void run() {
                System.out.println(ANSI_GREEN + "Hello from the anonymous class thread");
            }
        }.start();

        Thread myRunnableThread = new Thread(new MyRunnable() {
            @Override
            public void run() {
                System.out.println(ANSI_RED + "Hello from the anonymous class's implementation of run()");
                try {
                    //we can join the thread that's fetching the data
                    //and when we join a thread to a second thread
                    //the first thread will wait for the second
                    //threat to terminate and then it will continue
                    //to execute
                    anotherThread.join(); //Joining as the "second thread"
                    //We add a timeout value just in case
                    //the second thread never terminates
                    //The timeout value is an overloaded parameter
                    //in join( );
                    //In the real world, we wouldn't want to timeout
                    //too early before all of the data is sent over

                    System.out.println(ANSI_RED + "AnotherThread terminated, or timed out, so I'm running again");
                } catch(InterruptedException e) {
                    System.out.println(ANSI_RED + "I couldn't wait after all. I was interrupted");
                }
            }
        });

        myRunnableThread.start();




        System.out.println(ANSI_PURPLE+"Hello again from the main thread.");


    }
}

//On the set priority
//we can use that to try to influence the order in which
//threads are run
//but they're more like suggestions rather than commands
//It's still up to the JVM at the end of the day
//Some OS's don't even allow you to set priorities
//Never assume things