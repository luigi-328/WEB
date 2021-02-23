
package com.group23.fooddelivery.model.user;
import java.io.File;

import java.io.FileNotFoundException;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JOptionPane;

import com.sun.mail.smtp.SMTPTransport;


/**
 * Class that sends mail to a javax.mail library
 *
 */
public class EmailSender {
//	public static void main(String[] args) {
//		try {
//			if (sendMessage("mailsend", "fattura", "compa", null, false))
//				System.out.println("inviato");
//			else
//				System.out.println("non inviata");
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Metodo che invia una mail e riceve una email(destinatario), un oggetto, un messagio di 
	 * testo e il nome del pdf, che � l'allegato della mail.
	 * Controlla inoltre il corretto invio della mail e la mail del destinatario esiste.
	 * 
	 * @param send
	 * @param object
	 * @param messageArea
	 * @param pdf
	 * @return
	 * @throws FileNotFoundException
	 * @throws MessagingException
	 */
	public static boolean sendMessage(String send, String object, String messageArea, String pdf, boolean allegato)
			throws FileNotFoundException {

		boolean result = true;

		InternetAddress emailAddr;
		try {
			emailAddr = new InternetAddress(send);
		

		emailAddr.validate();

		if (result) {
			MimeMessage message;

			// Setta le propriet� della connessione
			Session session = Session.getDefaultInstance(setPropertiesStream());

			message = new MimeMessage(session);
			// mittente
			message.setFrom(new InternetAddress("fooddelivery256@gmail.com"));
 
			// chiamata al set To
			setTo(message, send);

			// chiamata al set Oggeto
			setObject(message, object);

			// chiamata al set Messaggio
			setText(message, messageArea);
			
			if(allegato) {
				// chiamata al set Allegato
				setAllegato(message,pdf);
			}
			// Send message
			sendEmail(message, session, "fooddelivery256@gmail.com");

			return true;
		}
		
		} catch (MessagingException e) {
			e.printStackTrace();
			//JOptionPane.showMessageDialog(null, "Si � verificato un errore, email non inviata!");
			return false;
		}

		return false;
	}

	/**
	 * Metodo che setta l'allegato(pdf) della mail.
	 * 
	 * @param message
	 * @param pdf
	 * @throws MessagingException
	 */
	private static void setAllegato(MimeMessage message, String pdf) throws MessagingException {
		BodyPart body = new MimeBodyPart();
		File f = new File(pdf);		
		DataSource source = new FileDataSource(f);
		body.setDataHandler(new DataHandler(source));
		body.setFileName(pdf);
		Multipart part = new MimeMultipart();
		part.addBodyPart(body);
		message.setContent(part);
	}

	/**
	 * Metodo che setta le properties della connessione smtp.
	 * 
	 * @return
	 */
	private static Properties setPropertiesStream() {
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.port", "25");
		properties.setProperty("mail.smtp.socketFactory.port", "25");

		properties.setProperty("smtp.gmail.com", "localhost");
		return properties;
	}

	/**
	 * Metodo che setta il destinatario della mail.
	 * 
	 * @param m
	 * @param jTx
	 * @throws MessagingException
	 * @throws AddressException
	 */
	private static void setTo(MimeMessage m, String jTx) throws AddressException, MessagingException {
		m.addRecipient(Message.RecipientType.TO, new InternetAddress(jTx));
	}

	/**
	 * Metodo che setta l'oggetto della mail.
	 * 
	 * @param m
	 * @param jTx
	 * @throws MessagingException
	 */
	private static void setObject(MimeMessage m, String jTx) throws MessagingException {
		m.setSubject(jTx);
	}

	/**
	 * Metodo che setta l'area di testo della mail.
	 * 
	 * @param m
	 * @param jTx
	 * @throws MessagingException
	 */
	private static void setText(MimeMessage m, String jTx) throws MessagingException {
		m.setText(jTx);
	}

	/**
	 * Metodo che attualmente invia la mail seguendo il settaggio della connessione alla 
	 * mailbox di gmail.
	 * 
	 * @param m
	 * @param s
	 * @param email
	 * @throws FileNotFoundException
	 * @throws MessagingException
	 */
	private static void sendEmail(MimeMessage m, Session s, String email)
			throws FileNotFoundException, MessagingException {

		 String user = "fooddelivery256@gmail.com";
		 String passwd = "Fooddelivery23.";

		SMTPTransport t = (SMTPTransport) s.getTransport("smtps");
		t.connect("smtp.gmail.com", user, passwd);
		//t.connect("smtp.gmail.com", email, "defaultpassword1!");
		t.sendMessage(m, m.getAllRecipients());
		t.close();
		System.out.println("EMAIL INVIATAAA");
	}
}