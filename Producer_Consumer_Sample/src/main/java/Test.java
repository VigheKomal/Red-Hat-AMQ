import java.awt.image.DirectColorModel;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.apache.activemq.artemis.jndi.ActiveMQInitialContextFactory;

public class Test {

	public static void main(String[] args) throws JMSException, InterruptedException, NamingException {
		// TODO Auto-generated method stub
		
		
		direct();
		
		//jndi();
		
	}
	
	public static void direct() throws JMSException, InterruptedException {
		
		final CountDownLatch countDownLatch= new CountDownLatch(1);
		
		String url="tcp://localhost:61616?user=admin";
		String user = "admin";
		String password ="admin";
		ActiveMQConnectionFactory activeMQConnectionFactory =new ActiveMQConnectionFactory(url, user, password);
		Connection connection=activeMQConnectionFactory.createConnection();
		connection.start();
		Session session=connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
		Destination destination=session.createQueue("TEST");
		MessageProducer messageProducer=session.createProducer(destination);
		Message message=session.createTextMessage("Hello");
		messageProducer.send(message);
		System.out.println("<-- Message Sent -->");
		MessageConsumer messageConsumer=session.createConsumer(destination);
		
		//Message recv=messageConsumer.receive();
		
	//Message recvTimeout=messageConsumer.receive(5000); // Timeout Error
	
		messageConsumer.setMessageListener(message1 -> {               //Async Consumer	
			
			System.out.println("<<-- Got a Message Async >> "+ message1);
			countDownLatch.countDown();
		});            
		countDownLatch.await();
		System.out.println("<-- Finish -->");
		
	
		
	//System.out.println("<-- Got a message -->" + recvTimeout);
		
	}
		
	public static void jndi() throws NamingException, JMSException, InterruptedException {
		
	//	String url="tcp://localhost:61616";
	//	String url="http://broker1-wconsj-0-svc-rte-amqtest22.apps.cnd23.psi.redhat.com";
		String url="http://ex-aao-wconsj-1-svc-rte-amq-1.apps.cnd23.psi.redhat.com";
		String user = "admin";
		String password ="admin";
		
		Properties properties= new Properties();
		properties.setProperty(Context.PROVIDER_URL ,url);
		properties.setProperty(Context.INITIAL_CONTEXT_FACTORY,ActiveMQInitialContextFactory.class.getName());
		properties.setProperty(Context.SECURITY_PRINCIPAL,user);
		properties.setProperty(Context.SECURITY_CREDENTIALS, password);
		
		InitialContext initialContext=new InitialContext(properties);
		NamingEnumeration<NameClassPair> list = initialContext.list("");
		
		/*
		 * while(list.hasMore()) { // to get name of factory
		 * System.out.println(list.next().getName()); }
		 */	
		
		final CountDownLatch countDownLatch= new CountDownLatch(1);
		ConnectionFactory connectionFactory=(ConnectionFactory)initialContext.lookup("ConnectionFactory");
		
		
		Connection connection=connectionFactory.createConnection();
		connection.start();
		Session session=connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
		
		Destination jndiQueue = (Destination) initialContext.lookup("dynamicQueues/TEST");
		System.out.println(""+ jndiQueue);;
		
		Destination destination=session.createQueue("TEST");
		MessageProducer messageProducer=session.createProducer(destination);
		Message message=session.createTextMessage("Hello");
		messageProducer.send(message);
		System.out.println("<-- Message Sent -->");
		MessageConsumer messageConsumer=session.createConsumer(destination);
		
		//Message recv=messageConsumer.receive();
		
	//Message recvTimeout=messageConsumer.receive(5000); // Timeout Error
	
		messageConsumer.setMessageListener(message1 -> {               //Async Consumer	
			
			System.out.println("<<-- Got a Message Async >> "+ message1);
			countDownLatch.countDown();
		});            
		countDownLatch.await();
		System.out.println("<-- Finish -->");
		
	
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
}
