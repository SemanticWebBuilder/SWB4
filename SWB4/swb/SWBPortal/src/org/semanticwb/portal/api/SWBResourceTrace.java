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
package org.semanticwb.portal.api;

import java.util.Random;
import javax.servlet.http.*;
import org.semanticwb.model.Resource;

// TODO: Auto-generated Javadoc
/** Objeto: Se encarga de dar seguimiento a la invocaci�n de recursos.
 *
 * Object: It is in charge to follow the resource invocation.
 *
 * @author Javier Solis Gonzalez
 */
public class SWBResourceTrace
{
    
    /** The id. */
    long id=0;
    
    /** The time. */
    long time;
    
    /** The description. */
    String description = "nodesc";
    
    /** The wb resource. */
    SWBResource wbResource = null;
    
    /** The type. */
    String type = "";
    
    /** The thread. */
    Thread thread = null;
    
    /** The params request. */
    SWBParamRequest paramsRequest;
    
    /** The request. */
    HttpServletRequest request;
    
    /** The ran. */
    private static Random ran=new Random(10000);

    /**
     * Creates a new instance of WBResourceTrace.
     * 
     * @param wbres the wbres
     * @param request the request
     * @param paramsRequest the params request
     */
    public SWBResourceTrace(SWBResource wbres, HttpServletRequest request, SWBParamRequest paramsRequest)
    {
        this.paramsRequest=paramsRequest;
        this.request=request;
        wbResource = wbres;
        Resource resource=wbres.getResourceBase();
        
        time = System.currentTimeMillis();
        id=time+resource.hashCode()+ran.nextInt();
        description = resource.getResourceType().getTitle()+": "+resource.getTitle();
        type = resource.getResourceType().getURI();
        thread = Thread.currentThread();
    }
    
    /**
     * Gets the wB param request.
     * 
     * @return the wB param request
     */
    public SWBParamRequest getWBParamRequest()
    {
        return paramsRequest;
    }
    
    /**
     * Gets the request.
     * 
     * @return the request
     */
    public HttpServletRequest getRequest()
    {
        return request;
    }
    
    /**
     * Gets the id.
     * 
     * @return the id
     */
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
    public Resource getResource()
    {
        return wbResource.getResourceBase();
    }


    /**
     * Setter for property resource.
     * 
     * @param wbResource the new wB resource
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

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
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
