package ru.org.icad.mishka.ejb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
public class StartupBean {
    private final static Logger LOGGER = LoggerFactory.getLogger(StartupBean.class);


    @PostConstruct
    private void startup() {
        LOGGER.info("Startup!");
    }

    @PreDestroy
    private void shutdown() {
        LOGGER.info("Shutdown!");
    }

}