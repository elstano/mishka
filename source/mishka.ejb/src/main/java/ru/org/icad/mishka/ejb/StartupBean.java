package ru.org.icad.mishka.ejb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.org.icad.mishka.app.process.casting.CastingProcessDev;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Singleton
@Startup
public class StartupBean {
    private final static Logger LOGGER = LoggerFactory.getLogger(StartupBean.class);
    private final static ScheduledExecutorService SCHEDULED_EXECUTOR_SERVICE
            = Executors.newSingleThreadScheduledExecutor();

    @PostConstruct
    private void startup() {
        LOGGER.info("Startup!");

        SCHEDULED_EXECUTOR_SERVICE.schedule(
                new Runnable() {
                    @Override
                    public void run() {
                        CastingProcessDev.start();
                    }
                },
                5,
                TimeUnit.SECONDS
        );
    }

    @PreDestroy
    private void shutdown() {
        LOGGER.info("Shutdown!");
    }

}