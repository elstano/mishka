package ru.org.icad.mishka.app.tx;

import ru.org.icad.mishka.app.JNDIFacade;

import javax.transaction.*;

public class TxUtil {
    public static <T> T executeInTransaction(Callable<T> c){
        UserTransaction t = JNDIFacade.getUserTransaction();
        try{
            t.begin();
            T result = c.run();
            t.commit();
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T executeInSeparateTransaction(Callable<T> c){
        TransactionManager txManager = JNDIFacade.getTXManager();
        try {
            Transaction toSuspend = txManager.suspend();
            try {
                txManager.begin();
                T result = c.run();
                txManager.commit();
                return result;
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    if(toSuspend != null){
                        txManager.resume(toSuspend);
                    }
                } catch (InvalidTransactionException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
