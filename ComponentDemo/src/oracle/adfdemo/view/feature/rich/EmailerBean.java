package oracle.adfdemo.view.feature.rich;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.io.InputStream;

import java.net.MalformedURLException;
import java.net.URL;

import java.net.URLConnection;


import java.text.SimpleDateFormat;

import java.util.Locale;
import java.util.Properties;
import java.util.Date;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.mail.Transport;
import javax.mail.event.TransportEvent;
import javax.mail.event.TransportListener;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * A very stupid emailer indeed, intended to allow for *simple* testing
 * of email-content sorts of things.
 */
public class EmailerBean
{
  public EmailerBean()
  {
  }
  
  public void setMailServer(String mailServer)
  {
    _mailServer = mailServer;
  }

  public String getMailServer()
  {
    return _mailServer;
  }

  public void setFrom(String from)
  {
    _from = from;
  }

  public String getFrom()
  {
    return _from;
  }

  public String getSubject()
  {
    return _subject;
  }

  public void setTo(String to)
  {
    _to = to;
  }

  public String getTo()
  {
    return _to;
  }
  
  public void setSubject(String subject)
  {
    _subject = subject;
  }

  public void sendMimeMultipartMail() 
    throws MessagingException, MalformedURLException, IOException
  {    
    // gather all the parts to the emailable page's complete URL
    FacesContext context = FacesContext.getCurrentInstance();
    String viewId = context.getViewRoot().getViewId();
    ExternalContext ec = context.getExternalContext();
    String requestContextPath = ec.getRequestContextPath();    
    String requestServletPath = ec.getRequestServletPath();
    Object opaqueRequest = ec.getRequest();

    String emailPageUrl;

    // we need a real ServletRequest to get access to the scheme, serverNme, and port,
    // so I guess Portlets are out of luck
    if (opaqueRequest instanceof ServletRequest)
    {
      ServletRequest request = (ServletRequest)opaqueRequest;

      emailPageUrl = request.getScheme() + "://" +
                  request.getServerName() + ":" +
                  request.getServerPort() + requestContextPath + requestServletPath + viewId 
                  + _EMAIL_MIME_MODE_QUERY_STRING;      
    }
    else
    {
      System.out.println("Request must be a ServletRequest to test email mime mode");
      return;
    }


    String emailCookies = null;

    if (opaqueRequest instanceof HttpServletRequest)
    {
      emailCookies = ((HttpServletRequest) opaqueRequest).getHeader("Cookie");
    }

    ////////////////////////////////////////////////////////////
    
    URL mailGenerator = new URL(emailPageUrl);
    URLConnection mailConnection = mailGenerator.openConnection();
    if (null != emailCookies)
    {
      mailConnection.setRequestProperty("Cookie", emailCookies);
    }
    
    ByteArrayOutputStream mailSink = new ByteArrayOutputStream();
    
    SimpleDateFormat smtpDateFormat = 
          new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.US);
    
    StringBuilder mailHeaders = new StringBuilder();
    mailHeaders.append("Date: ").append(smtpDateFormat.format(new Date())).append("\r\n");
    mailHeaders.append("Subject: ").append(getSubject()).append("\r\n");
    mailHeaders.append("To: ").append(getTo()).append("\r\n");
    mailHeaders.append("From: ").append(getFrom()).append("\r\n");
    mailHeaders.append("MIME-version: 1.0").append("\r\n");
    
    String sourceContentType = mailConnection.getContentType();
    mailHeaders.append("Content-Type: ").append(sourceContentType).append("\r\n");
    
    mailHeaders.append("\r\n");
    mailHeaders.append("This is a multi-part message in MIME format.\r\n");
    
    mailSink.write(mailHeaders.toString().getBytes());
    
    InputStream mailBodyStream = mailConnection.getInputStream();
    
    int readMax = 65536; // 64 KB, picked at random...
    byte[] buffer = new byte[readMax]; 
    int readLength = mailBodyStream.read(buffer, 0, readMax);
    
    while(0 <= readLength)
    {
      mailSink.write(buffer, 0, readLength); 
      readLength = mailBodyStream.read(buffer, 0, readMax);
    }
    
    byte[] allMessageBytes = mailSink.toByteArray();
    
    // System.out.println(getClass().getName() + " mailing the following messsage:");
    // System.out.println(new String(allMessageBytes));
    
    final ByteArrayInputStream mailMessageStream = 
      new ByteArrayInputStream(allMessageBytes);
    
    Properties props = new Properties();
    props.put("mail.smtp.host", _mailServer);

    Session session = Session.getInstance(props, null);
    MimeMessage msg = new MimeMessage(session, mailMessageStream);
  
    Transport t = session.getTransport("smtp");
    try
    {
      t.addTransportListener(new TransportListener()
      {
        @Override
        public void messageDelivered(TransportEvent transportEvent)
        {
          System.out.println("Email sent");
        }

        @Override
        public void messageNotDelivered(TransportEvent transportEvent)
        {
          System.err.println("Email failed.");
        }

        @Override
        public void messagePartiallyDelivered(TransportEvent transportEvent)
        {
          System.err.println("Email partially sent");
        }
      });

      t.connect();
      t.sendMessage(msg, msg.getAllRecipients());
    }
    finally
    {
      t.close();
    }
    
  }//sendMimeMultipartMail

  private String _mailServer;
  private String _from;
  private String _subject;
  private String _to;
  private static final String _EMAIL_MIME_MODE_QUERY_STRING = 
    "?org.apache.myfaces.trinidad.agent.email=true&" +
    "oracle.adf.view.rich.render.emailContentType=multipart/related";
}
