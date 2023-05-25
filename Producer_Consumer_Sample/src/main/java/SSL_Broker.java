import javax.jms.*;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class SSL_Broker {

	public static void main(String[] args) throws JMSException {
		// TODO Auto-generated method stub
		
		String user = "admin";
        String password ="admin";
    //    System.setProperty("javax.net.debug","ssl,handshake");
        //String url="tcp://ex-aao-acceptor1-0-svc-rte-amq-1.apps.cnd23.psi.redhat.com:443?sslEnabled=true;keyStorePath=/home/kvighe/Amq/Certificates/broker.jks;keyStorePassword=password;trustStorePath=/home/kvighe/Amq/Certificates/client.jks;trustStorePassword=password;verifyHost=false";
        //String url="tcp://ex-aao-wconsj-0-svc-rte-amq-1.apps.cnd23.psi.redhat.com:443?sslEnabled=true;keyStorePath=/home/kvighe/Amq/Certificates/broker.jks;keyStorePassword=password;trustStorePath=/home/kvighe/Amq/Certificates/client.jks;trustStorePassword=password;verifyHost=false";
       // String url="https://ex-aao-openwire-tls-0-svc-rte-amq-test1.apps.testcluster01main.lab.psi.pnq2.redhat.com?sslEnabled=true;keyStorePath=/home/kvighe/Amq/Certificates/broker.jks;keyStorePassword=password;trustStorePath=/home/kvighe/Amq/Certificates/client.jks;trustStorePassword=password;verifyHost=false";
       //String url="http://ex-aao-wconsj-0-svc-rte-amq.apps.ocpcluster01.lab.psi.pnq2.redhat.com";
        
        String url="tcp://localhost:61616";
        
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(url,user,password);
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue("BranchCSRspQ");
        MessageProducer messageProducer = session.createProducer(destination);
        messageProducer.send(session.createTextMessage("I am komal"));
        System.out.println("Done!!");

	}

}
