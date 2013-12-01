package ru.org.icad.mishka.app;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.TransactionManager;
import javax.transaction.TransactionSynchronizationRegistry;
import javax.transaction.UserTransaction;

/**
 * User: Boss
 * Date: 10/4/13
 * Time: 12:49 AM
 */
public class JNDIFacade {

    public static <T> T lookup(String toLookup){
        try {
            InitialContext context = new InitialContext();
            return (T)context.lookup(toLookup);
        } catch (NamingException e){
            throw new RuntimeException(e);
        }
    }

    public static <T> T lookup(Class<T> toLookup){
        return lookup(toLookup.getName());
    }

    public static UserTransaction getUserTransaction(){
        return lookup("java:comp/UserTransaction");
    }

    public static TransactionSynchronizationRegistry getTXSRegistry(){
        return lookup("java:comp/TransactionSynchronizationRegistry");
    }

    public static TransactionManager getTXManager(){
        return lookup("java:appserver/TransactionManager");
    }
}
