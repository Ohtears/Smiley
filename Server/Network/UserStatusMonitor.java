package Server.Network;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import Server.Database.MYSQLHandler;

public class UserStatusMonitor implements Runnable {

    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private static final int CHECK_INTERVAL_SECONDS = 5;  
    private static final int TIMEOUT_SECONDS = 20;  

    @Override
    public void run() {
        scheduler.scheduleAtFixedRate(() -> {
            MYSQLHandler.updateUserStatusBasedOnActivity(TIMEOUT_SECONDS);
        }, 0, CHECK_INTERVAL_SECONDS, TimeUnit.SECONDS);
    }

    public void startMonitoring() {
        Thread monitorThread = new Thread(this);
        monitorThread.start();
    }


}
