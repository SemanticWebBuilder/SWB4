
/*
 * WBResourceTrace.java
 *
 * Created on 18 de febrero de 2005, 07:00 PM
 */

package org.semanticwb.portal.resources;

import java.util.Random;
import javax.servlet.http.*;
import org.semanticwb.model.Portlet;

/** Objeto: Se encarga de dar seguimiento a la invocaci�n de recursos.
 *
 * Object: It is in charge to follow the resource invocation.
 *
 * @author Javier Solis Gonzalez
 */
public class SWBResourceTrace
{
    long id=0;
    long time;
    String description = "nodesc";
    SWBResource wbResource = null;
    String type = "";
    Thread thread = null;
    SWBParamRequest paramsRequest;
    HttpServletRequest request;
    private static Random ran=new Random(10000);

    /** Creates a new instance of WBResourceTrace */
    public SWBResourceTrace(SWBResource wbres, HttpServletRequest request, SWBParamRequest paramsRequest)
    {
        this.paramsRequest=paramsRequest;
        this.request=request;
        wbResource = wbres;
        Portlet resource=wbres.getResourceBase();
        
        time = System.currentTimeMillis();
        id=time+resource.hashCode()+ran.nextInt();
        description = resource.getPortletType().getTitle()+": "+resource.getTitle();
        type = resource.getPortletType().getURI();
        thread = Thread.currentThread();
    }
    
    public SWBParamRequest getWBParamRequest()
    {
        return paramsRequest;
    }
    
    public HttpServletRequest getRequest()
    {
        return request;
    }
    
    public long getId()
    {
        return id;
    }

    /** Getter for property time.
     * @return Value of property time.
     *
     */
    public long getTime()
    {
        return time;
    }

    /** Setter for property time.
     * @param time New value of property time.
     *
     */
    public void setTime(long time)
    {
        this.time = time;
    }

    /** Getter for property description.
     * @return Value of property description.
     *
     */
    public java.lang.String getDescription()
    {
        return description;
    }

    /** Setter for property description.
     * @param description New value of property description.
     *
     */
    public void setDescription(java.lang.String description)
    {
        this.description = description;
    }

    /** Getter for property resource.
     * @return Value of property resource.
     *
     */
    public SWBResource getWBResource()
    {
        return wbResource;
    }

     /** Getter for property resource.
     * @return Value of property resource.
     *
     */
    public Portlet getResource()
    {
        return wbResource.getResourceBase();
    }


    /** Setter for property resource.
     * @param resource New value of property resource.
     *
     */
    public void setWBResource(SWBResource wbResource)
    {
        this.wbResource = wbResource;
    }

    /** Getter for property type.
     * @return Value of property type.
     *
     */
    public java.lang.String getType()
    {
        return type;
    }

    /** Setter for property type.
     * @param type New value of property type.
     *
     */
    public void setType(java.lang.String type)
    {
        this.type = type;
    }

    public String toString()
    {
        return description + ", " + thread.toString();
    }

    /** Getter for property thread.
     * @return Value of property thread.
     *
     */
    public java.lang.Thread getThread()
    {
        return thread;
    }

    /** Setter for property thread.
     * @param thread New value of property thread.
     *
     */
    public void setThread(java.lang.Thread thread)
    {
        this.thread = thread;
    }

}
