import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class Producer {
	
	public static void main(String[] args) throws Exception {
        String user ="admin";
        String password ="admin";
        Context context = new InitialContext();
        ActiveMQConnectionFactory activeMQConnectionFactory = (ActiveMQConnectionFactory) context.lookup("ConnectionFactory");
        Connection connection = activeMQConnectionFactory.createConnection(user,password);
        //ActiveMQInitialContextFactory activeMQInitialContextFactory = (ActiveMQInitialContextFactory) context.lookup("java.naming.factory.initial");
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination queue = session.createQueue("BranchCSRspQ");
        connection.start();
        MessageProducer producer = session.createProducer(queue);
        String text = "HelloAMQ";
        Message message = session.createTextMessage(text);
        for(int i=0;i<10000;i++) {
            producer.send(message);
        }
        System.out.println("Done Producing!!");
        session.close();
        connection.close();
    }

}
