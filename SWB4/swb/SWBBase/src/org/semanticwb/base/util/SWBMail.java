/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.base.util;

/**
 *
 * @author jorge.jimenez
 */
import java.util.*;
import org.apache.commons.mail.EmailAttachment;
import javax.mail.internet.InternetAddress;
import org.apache.commons.mail.HtmlEmail;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;

public class SWBMail 
{
    private static Logger log=SWBUtils.getLogger(SWBMail.class);

    private String fromEmail = null;
    private String fromName = null;
    private Collection toEmail = null;
    private Collection ccEmail = null;
    private Collection bccEmail = null;
    private ArrayList<EmailAttachment> attachments = new ArrayList();
    private ArrayList<InternetAddress> addresses = new ArrayList();
    private String login;
    private String password;
    private String subject = null;
    private String data = null;
    private String contentType = null;
    private String smtpserver = null;

    /** Creates a new instance of AFMailData */
    public SWBMail() {
    }

    /** Creates a new instance of AFMailData */
    public SWBMail(Collection toEmail, String subject, String description) {
        this.fromEmail = "webmail.infotec.com.mx";
        this.toEmail = toEmail;
        this.subject = subject;
        this.data = description;
    }

    public SWBMail(String fromEmail, Collection toEmail, Collection ccEmail, Collection bccEmail,
            String subject, String data) {
        this.fromEmail = fromEmail;
        this.toEmail = toEmail;
        this.ccEmail = ccEmail;
        this.bccEmail = bccEmail;
        this.subject = subject;
        this.data = data;
    }

    /** Setter for property addresses.
     * 
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

    public void addAddress(InternetAddress address) {
        addresses.add(address);
    }

    public void addAddress(String address) {
        InternetAddress inetAddress = new InternetAddress();
        inetAddress.setAddress(address);
        addresses.add(inetAddress);
    }

    /** Setter for property attachments.
     * 
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

    /** Setter for property fromName.
     * @param fromEmail New value of property fromName.
     *
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

    /** Setter for property login.
     * @param data New value of property login.
     *
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

    /** Setter for property password.
     * @param data New value of property password.
     *
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

    /** Setter for property data.
     * @param data New value of property data.
     *
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
    
    /** Setter for property smtpserver.
     * @param subject New value of property smtpserver.
     *
     */
    public void setHostName(java.lang.String smtpserver) {
        this.smtpserver = smtpserver;
    }
    
    /** Setter for property smtpserver.
     * @param subject New value of property smtpserver.
     *
     */
    public String getHostName() {
        return smtpserver;
    }
    

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
}
