/**
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
**/


/*
 * AFMailData.java
 *
 * Created on 20 de marzo de 2003, 13:25
 */

package com.infotec.appfw.lib.mail;

/**
 * Objeto que contiene los datos a enviar de un correo electronico.
 * @author Javier Solis Gonzalez
 */
public class AFMailData
{

    private String fromEmail = null;
    private String toEmail = null;
    private String ccEmail = null;
    private String bccEmail = null;
    private String subject = null;
    private String contentType = null;
    private int priority = 0;
    private String data = null;

    /** Creates a new instance of AFMailData */
    public AFMailData()
    {
    }

    /** Creates a new instance of AFMailData */
    public AFMailData(String email, String subject, String description)
    {
        this.fromEmail=com.infotec.appfw.util.AFUtils.getInstance().getEnv("af/adminEmail");
        this.toEmail = email;
        this.subject = subject;
        this.data = description;
    }

    public AFMailData(String fromEmail, String toEmail, String ccEmail, String bccEmail,
                      String subject, String contentType, int priority, String data)
    {
        this.fromEmail = fromEmail;
        this.toEmail = toEmail;
        this.ccEmail = ccEmail;
        this.bccEmail = bccEmail;
        this.subject = subject;
        this.contentType = contentType;
        this.priority = priority;
        this.data = data;
    }


    /** Getter for property fromEmail.
     * @return Value of property fromEmail.
     *
     */
    public java.lang.String getFromEmail()
    {
        return fromEmail;
    }

    /** Setter for property fromEmail.
     * @param fromEmail New value of property fromEmail.
     *
     */
    public void setFromEmail(java.lang.String fromEmail)
    {
        this.fromEmail = fromEmail;
    }

    /** Getter for property toEmail.
     * @return Value of property toEmail.
     *
     */
    public java.lang.String getToEmail()
    {
        return toEmail;
    }

    /** Setter for property toEmail.
     * @param toEmail New value of property toEmail.
     *
     */
    public void setToEmail(java.lang.String toEmail)
    {
        this.toEmail = toEmail;
    }

    /** Getter for property ccEmail.
     * @return Value of property ccEmail.
     *
     */
    public java.lang.String getCcEmail()
    {
        return ccEmail;
    }

    /** Setter for property ccEmail.
     * @param ccEmail New value of property ccEmail.
     *
     */
    public void setCcEmail(java.lang.String ccEmail)
    {
        this.ccEmail = ccEmail;
    }

    /** Getter for property bccEmail.
     * @return Value of property bccEmail.
     *
     */
    public java.lang.String getBccEmail()
    {
        return bccEmail;
    }

    /** Setter for property bccEmail.
     * @param bccEmail New value of property bccEmail.
     *
     */
    public void setBccEmail(java.lang.String bccEmail)
    {
        this.bccEmail = bccEmail;
    }

    /** Getter for property contentType.
     * @return Value of property contentType.
     *
     */
    public java.lang.String getContentType()
    {
        return contentType;
    }

    /** Setter for property contentType.
     * @param contentType New value of property contentType.
     *
     */
    public void setContentType(java.lang.String contentType)
    {
        this.contentType = contentType;
    }

    /** Getter for property priority.
     * @return Value of property priority.
     *
     */
    public int getPriority()
    {
        return priority;
    }

    /** Setter for property priority.
     * @param priority New value of property priority.
     *
     */
    public void setPriority(int priority)
    {
        this.priority = priority;
    }

    /** Getter for property data.
     * @return Value of property data.
     *
     */
    public java.lang.String getData()
    {
        return data;
    }

    /** Setter for property data.
     * @param data New value of property data.
     *
     */
    public void setData(java.lang.String data)
    {
        this.data = data;
    }

    /** Getter for property subject.
     * @return Value of property subject.
     *
     */
    public java.lang.String getSubject()
    {
        return subject;
    }

    /** Setter for property subject.
     * @param subject New value of property subject.
     *
     */
    public void setSubject(java.lang.String subject)
    {
        this.subject = subject;
    }

}
