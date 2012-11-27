/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
 */
package org.semanticwb.base.util;

/**
 *
 * @author jorge.jimenez
 */
import java.util.*;
import org.apache.commons.mail.EmailAttachment;
import javax.mail.internet.InternetAddress;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class SWBMail.
 */
public class SWBMail 
{
    
    /** The log. */
    private static Logger log=SWBUtils.getLogger(SWBMail.class);

    /** The from email. */
    private String fromEmail = null;
    
    /** The from name. */
    private String fromName = null;
    
    /** The to email. */
    private Collection toEmail = null;
    
    /** The cc email. */
    private Collection ccEmail = null;
    
    /** The bcc email. */
    private Collection bccEmail = null;
    
    /** The attachments. */
    private ArrayList<EmailAttachment> attachments = new ArrayList();
    
    /** The addresses. */
    private ArrayList<InternetAddress> addresses = new ArrayList();
    
    /** The login. */
    private String login;
    
    /** The password. */
    private String password;
    
    /** The subject. */
    private String subject = null;
    
    /** The data. */
    private String data = null;
    
    /** The content type. */
    private String contentType = null;
    
    /** The smtpserver. */
    private String smtpserver = null;

    /**
     * Creates a new instance of AFMailData.
     */
    public SWBMail() {
    }

    /**
     * Creates a new instance of AFMailData.
     * 
     * @param toEmail the to email
     * @param subject the subject
     * @param description the description
     */
    public SWBMail(Collection toEmail, String subject, String description) {
        this.fromEmail = "webmail.infotec.com.mx";
        this.toEmail = toEmail;
        this.subject = subject;
        this.data = description;
    }

    /**
     * Instantiates a new sWB mail.
     * 
     * @param fromEmail the from email
     * @param toEmail the to email
     * @param ccEmail the cc email
     * @param bccEmail the bcc email
     * @param subject the subject
     * @param data the data
     */
    public SWBMail(String fromEmail, Collection toEmail, Collection ccEmail, Collection bccEmail,
            String subject, String data) {
        this.fromEmail = fromEmail;
        this.toEmail = toEmail;
        this.ccEmail = ccEmail;
        this.bccEmail = bccEmail;
        this.subject = subject;
        this.data = data;
    }

    /**
     * Setter for property addresses.
     * 
     * @param addresses the new address
     */
    public void setAddress(ArrayList<InternetAddress> addresses) {
        this.addresses = addresses;
    }

    /** Getter for property addresses.
     * @return Value of property addresses.
     *
     */
    public ArrayList<InternetAddress> getAddresses() {
        return addresses;
    }

    /**
     * Adds the address.
     * 
     * @param address the address
     */
    public void addAddress(InternetAddress address) {
        addresses.add(address);
    }

    /**
     * Adds the address.
     * 
     * @param address the address
     */
    public void addAddress(String address) {
        InternetAddress inetAddress = new InternetAddress();
        inetAddress.setAddress(address);
        addresses.add(inetAddress);
    }

    /**
     * Setter for property attachments.
     * 
     * @param attachments the new attachments
     */
    public void setAttachments(ArrayList<EmailAttachment> attachments) {
        this.attachments = attachments;
    }

    /** Getter for property attachments.
     * @return Value of property attachments.
     *
     */
    public ArrayList<EmailAttachment> getAttachments() {
        return attachments;
    }

    /**
     * Adds the attachment.
     * 
     * @param attachment the attachment
     */
    public void addAttachment(EmailAttachment attachment) {
        attachments.add(attachment);
    }

    /** Getter for property fromEmail.
     * @return Value of property fromEmail.
     *
     */
    public java.lang.String getFromEmail() {
        return fromEmail;
    }

    /** Setter for property fromEmail.
     * @param fromEmail New value of property fromEmail.
     *
     */
    public void setFromEmail(java.lang.String fromEmail) {
        this.fromEmail = fromEmail;
    }
    
    /** Getter for property fromName.
     * @return Value of property fromName.
     *
     */
    public java.lang.String getFromName() {
        return fromName;
    }

    /**
     * Setter for property fromName.
     * 
     * @param fromName the new from name
     */
    public void setFromName(java.lang.String fromName) {
        this.fromName = fromName;
    }
    

    /** Getter for property toEmail.
     * @return Value of property toEmail.
     *
     */
    public Collection getToEmail() {
        return toEmail;
    }

    /** Setter for property toEmail.
     * @param toEmail New value of property toEmail.
     *
     */
    public void setToEmail(Collection toEmail) {
        this.toEmail = toEmail;
    }

    /** Getter for property ccEmail.
     * @return Value of property ccEmail.
     *
     */
    public Collection getCcEmail() {
        return ccEmail;
    }

    /** Setter for property ccEmail.
     * @param ccEmail New value of property ccEmail.
     *
     */
    public void setCcEmail(Collection ccEmail) {
        this.ccEmail = ccEmail;
    }

    /** Getter for property bccEmail.
     * @return Value of property bccEmail.
     *
     */
    public Collection getBccEmail() {
        return bccEmail;
    }

    /** Setter for property bccEmail.
     * @param bccEmail New value of property bccEmail.
     *
     */
    public void setBccEmail(Collection bccEmail) {
        this.bccEmail = bccEmail;
    }

    /** Getter for property data.
     * @return Value of property data.
     *
     */
    public java.lang.String getData() {
        return data;
    }

    /** Setter for property data.
     * @param data New value of property data.
     *
     */
    public void setData(java.lang.String data) {
        this.data = data;
    }

    /** Getter for property login.
     * @return Value of property login.
     *
     */
    public java.lang.String getLogin() {
        return login;
    }

    /**
     * Setter for property login.
     * 
     * @param login the new login
     */
    public void setLogin(java.lang.String login) {
        this.login = login;
    }

    /** Getter for property password.
     * @return Value of property password.
     *
     */
    public java.lang.String getPassword() {
        return password;
    }

    /**
     * Setter for property password.
     * 
     * @param password the new password
     */
    public void setPassword(java.lang.String password) {
        this.password = password;
    }

    /** Getter for property contentType.
     * @return Value of property contentType.
     *
     */
    public java.lang.String getContentType() {
        return contentType;
    }

    /**
     * Setter for property data.
     * 
     * @param contentType the new content type
     */
    public void setContentType(java.lang.String contentType) {
        this.contentType = contentType;
    }

    /** Getter for property subject.
     * @return Value of property subject.
     *
     */
    public java.lang.String getSubject() {
        return subject;
    }

    /** Setter for property subject.
     * @param subject New value of property subject.
     *
     */
    public void setSubject(java.lang.String subject) {
        this.subject = subject;
    }
    
    /**
     * Setter for property smtpserver.
     * 
     * @param smtpserver the new host name
     */
    public void setHostName(java.lang.String smtpserver) {
        this.smtpserver = smtpserver;
    }
    
    /**
     * Setter for property smtpserver.
     * 
     * @return the host name
     */
    public String getHostName() {
        return smtpserver;
    }
    
    /*
    public String sendMail() {
        try {
            HtmlEmail email = new HtmlEmail();

            Iterator itAttaches = attachments.iterator();
            while (itAttaches.hasNext()) {
                EmailAttachment attchment = (EmailAttachment) itAttaches.next();
                email.attach(attchment);
            }

            email.setHostName(smtpserver);
            email.setFrom(fromEmail, fromName);
            email.setTo(addresses);
            if (ccEmail != null) {
                email.setCc(ccEmail);
            }
            if (bccEmail != null) {
                email.setBcc(bccEmail);
            }
            email.setSubject(subject);

            if (contentType.equalsIgnoreCase("HTML")) {
                email.setHtmlMsg(data); // set the html message
            } else {
                email.setMsg(data);
            }

            if (login != null && password != null) {
                email.setAuthentication(login, password);
            }
            return email.send();
        } catch (Exception e) {
            log.error(e);
        }
        return null;
    }
     * */
}
