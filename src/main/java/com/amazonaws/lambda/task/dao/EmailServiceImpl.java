package com.amazonaws.lambda.task.dao;


import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import com.amazonaws.lambda.task.domain.Task;

/**
 * Send an email through the Amazon SES SMTP interface.
 * 
 * TODO: modify to send to real email addresses from a task list.
 * 
 * @author khazanov
 */
public class EmailServiceImpl implements EmailService {

    private static final Logger log = Logger.getLogger(EmailServiceImpl.class);

    static final String FROM = "SENDER@EXAMPLE.COM";   // Replace with your "From" address. This address must be verified.
    static final String TO = "RECIPIENT@EXAMPLE.COM";  // Replace with a "To" address. If your account is still in the 
                                                       // sandbox, this address must be verified.
    
    static final String BODY = "This email was sent through the Amazon SES SMTP interface by using Java.";
    static final String SUBJECT = "Amazon SES test (SMTP interface accessed using Java)";
    
    // Supply your SMTP credentials below. Note that your SMTP credentials are different from your AWS credentials.
    static final String SMTP_USERNAME = "YOUR_SMTP_USERNAME";  // Replace with your SMTP username.
    static final String SMTP_PASSWORD = "YOUR_SMTP_PASSWORD";  // Replace with your SMTP password.
    
    // Amazon SES SMTP host name. This example uses the US West (Oregon) Region.
    static final String HOST = "email-smtp.us-west-2.amazonaws.com";    
    
    // The port you will connect to on the Amazon SES SMTP endpoint. We are choosing port 25 because we will use
    // STARTTLS to encrypt the connection.
    static final int PORT = 25;
    
    private static volatile EmailServiceImpl instance;

    private EmailServiceImpl() { }

    public static EmailServiceImpl instance() {

        if (instance == null) {
            synchronized(EmailServiceImpl.class) {
                if (instance == null)
                    instance = new EmailServiceImpl();
            }
        }
        return instance;
    }

    @Override
    public void sendEmails(List<Task> tasks) throws Exception {
    		
    		for(Task task: tasks) {
    	
    			String toEmail = task.getUser();
    			
		    	// Create a Properties object to contain connection configuration information.
		    	Properties props = System.getProperties();
		    	props.put("mail.transport.protocol", "smtps");
		    	props.put("mail.smtp.port", PORT); 
		    	
		    	// Set properties indicating that we want to use STARTTLS to encrypt the connection.
		    	// The SMTP session will begin on an unencrypted connection, and then the client
		    // will issue a STARTTLS command to upgrade to an encrypted connection.
		    	props.put("mail.smtp.auth", "true");
		    	props.put("mail.smtp.starttls.enable", "true");
		    	props.put("mail.smtp.starttls.required", "true");
		
		    // Create a Session object to represent a mail session with the specified properties. 
		    	Session session = Session.getDefaultInstance(props);
	
	        // Create a message with the specified information. 
	        MimeMessage msg = new MimeMessage(session);
	        msg.setFrom(new InternetAddress(FROM));
	        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(TO));
	        msg.setSubject(SUBJECT);
	        msg.setContent(BODY,"text/plain");
	            
	        // Create a transport.        
	        Transport transport = session.getTransport();
	                    
	        // Send the message.
	        try
	        {
	        		log.info("Attempting to send an email through the Amazon SES SMTP interface...");
	            
	            // Connect to Amazon SES using the SMTP username and password you specified above.
	            transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
	        	
	            // Send the email.
	            transport.sendMessage(msg, msg.getAllRecipients());
	            log.info("Email sent!");
	        }
	        catch (Exception ex) {
	        		log.error("The email was not sent.");
	        		log.error("Error message: " + ex.getMessage());
	        }
	        finally {
	            // Close and terminate the connection.
	            transport.close();        	
	        }
    		}
    }
}