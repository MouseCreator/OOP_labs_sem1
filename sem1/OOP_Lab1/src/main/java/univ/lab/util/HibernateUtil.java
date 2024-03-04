package univ.lab.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class HibernateUtil {

    private static SessionFactory sessionFactory = null;

    private static SessionFactory initSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");

        Properties properties = new Properties();
        try (InputStream input = HibernateUtil.class.getResourceAsStream("/db_config.txt")) {
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Unable to configure Hibernate");
        }

        configuration.setProperty("hibernate.connection.username", properties.getProperty("db.username"));
        configuration.setProperty("hibernate.connection.password", properties.getProperty("db.password"));
        return configuration.buildSessionFactory();
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null)
            sessionFactory = initSessionFactory();
        return sessionFactory;
    }
}
