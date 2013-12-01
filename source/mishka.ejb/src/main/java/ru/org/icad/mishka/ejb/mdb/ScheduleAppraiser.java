package ru.org.icad.mishka.ejb.mdb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * User: Boss
 * Date: 9/22/13
 * Time: 2:02 AM
 */
@MessageDriven(mappedName = "jms/ScheduleAppraiserQueue", activationConfig =  {
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class ScheduleAppraiser implements MessageListener {
    @Override
    public void onMessage(Message message) {

    }
}
