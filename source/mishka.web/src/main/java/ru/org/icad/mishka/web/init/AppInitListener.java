package ru.org.icad.mishka.web.init;

import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * User: Boss
 * Date: 10/3/13
 * Time: 10:08 PM
 */
public class AppInitListener implements ServletContextListener {
    public static final String LOGGING_PROPERTIES = "config/logging.properties";

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try{
            initLogging();
            LogFactory.getLog(AppInitListener.class).info("logging initialized");
        } catch (IOException e){
            System.err.println("Failed to initialize application");
            e.printStackTrace(System.err);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    private void initLogging() throws IOException {
        InputStream loggingProperties = null;
        try{
            loggingProperties = this.getClass().getClassLoader().getResourceAsStream(LOGGING_PROPERTIES);
            Properties props = new Properties();
            props.load(loggingProperties);
            PropertyConfigurator.configure(props);
        } finally {
            if(loggingProperties != null){
                loggingProperties.close();
            }
        }
    }
}
