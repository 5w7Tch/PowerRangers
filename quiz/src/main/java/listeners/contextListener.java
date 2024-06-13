package listeners;

import models.dbCredentials;
import models.mySqlDb;
import models.Dao;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class contextListener implements ServletContextListener  {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        BasicDataSource source = new BasicDataSource();
        source.setUrl(dbCredentials.url);
        source.setUsername(dbCredentials.userName);
        source.setPassword(dbCredentials.password);
        Dao db = new mySqlDb(source);
        servletContextEvent.getServletContext().setAttribute("db",db);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ((Dao) servletContextEvent.getServletContext().getAttribute("db")).closeDbConnection();
    }
}
