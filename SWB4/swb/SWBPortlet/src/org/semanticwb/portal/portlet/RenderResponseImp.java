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
package org.semanticwb.portal.portlet;

import java.util.Collection;
import javax.portlet.*;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.portal.api.SWBParamRequest;

/**
 *
 * @author Javier Solis Gonzalez
 */
public class RenderResponseImp extends PortletResponseImp implements RenderResponse
{
    SWBParamRequest paramsRequest=null;
    boolean isContentTypeSet=false;
    private String title=null;
    
    /** Creates a new instance of RenderResponseImp */
    public RenderResponseImp(HttpServletResponse servletResponse, SWBParamRequest paramsRequest)
    {
        super(servletResponse);
        this.paramsRequest=paramsRequest;
    }
    
  /**
   * Returns the MIME type that can be used to contribute
   * markup to the render response.
   * <p>
   * If no content type was set previously using the {@link #setContentType} method
   * this method retuns <code>null</code>.
   *
   * @see #setContentType
   *
   * @return   the MIME type of the response, or <code>null</code>
   *           if no content type is set
   */
  
  public String getContentType ()
  {
      //TODO
        if(!isContentTypeSet)
            return null;
        else
            //return getContentType()InformationProviderAccess.getDynamicProvider(getHttpServletRequest()).getResponseContentType();      
            return "text/html";
  }
    
  /**
   * Returns a <code>OutputStream</code> suitable for writing binary
   * data in the response. The portlet container does not encode the
   * binary data.
   * <p>
   * Before calling this method the content type of the
   * render response must be set using the {@link #setContentType}
   * method.
   * <p>
   * Calling <code>flush()</code> on the OutputStream commits the response.
   * <p>
   * Either this method or {@link #getWriter} may be called to write the body, not both.
   *
   * @return	a <code>OutputStream</code> for writing binary data
   *
   * @exception java.lang.IllegalStateException   if the <code>getWriter</code> method
   * 					has been called on this response, or
   *                                    if no content type was set using the
   *                                    <code>setContentType</code> method.
   *
   * @exception java.io.IOException 	if an input or output exception occurred
   *
   * @see #setContentType
   * @see #getWriter
   */
  public java.io.OutputStream getPortletOutputStream() throws java.io.IOException
  {
        if(!isContentTypeSet)
            throw new IllegalStateException("No content type set.");
        else
            return getOutputStream();      
  }
  
  /**
   * Creates a portlet URL targeting the portlet. If no portlet mode,
   * window state or security modifier is set in the PortletURL the
   * current values are preserved. If a request is triggered by the
   * PortletURL, it results in a render request.
   * <p>
   * The returned URL can be further extended by adding
   * portlet-specific parameters and portlet modes and window states.
   * <p>
   * The created URL will per default not contain any parameters
   * of the current render request.
   *
   * @return a portlet render URL
   */
  public PortletURL createRenderURL()
  {
      return new PortletURLImp(paramsRequest.getRenderUrl());
  }  
  
  /**
   * The value returned by this method should be prefixed or appended to
   * elements, such as JavaScript variables or function names, to ensure
   * they are unique in the context of the portal page.
   *
   * @return   the namespace
   */
    @Override
  public String getNamespace()
  {
      return paramsRequest.getResourceBase().getSemanticObject().getPrefix()+"_"+paramsRequest.getResourceBase().getId();
  }  
  
  /**
   * This method sets the title of the portlet.
   * <p>
   * The value can be a text String
   *
   * @param  title    portlet title as text String or resource URI
   */
  public void setTitle(String title)
  {
      this.title=title;
  }
  
  /**
   * Getter for property title.
   * @return Value of property title.
   */
  public java.lang.String getTitle()
  {
      return title;
  }   
  
  /**
   * Creates a portlet URL targeting the portlet. If no portlet mode,
   * window state or security modifier is set in the PortletURL the
   * current values are preserved. If a request is triggered by the
   * PortletURL, it results in an action request.
   * <p>
   * The returned URL can be further extended by adding
   * portlet-specific parameters and portlet modes and window states.
   * <p>
   * The created URL will per default not contain any parameters
   * of the current render request.
   *
   * @return a portlet action URL
   */
  public PortletURL createActionURL()
  {
      return new PortletURLImp(paramsRequest.getActionUrl());
  }
  
  /**
   * Sets the MIME type for the render response. The portlet must
   * set the content type before calling {@link #getWriter} or
   * {@link #getPortletOutputStream}.
   * <p>
   * Calling <code>setContentType</code> after <code>getWriter</code>
   * or <code>getOutputStream</code> does not change the content type.
   *
   * @param   type  the content MIME type
   *
   * @throws  java.lang.IllegalArgumentException
   *              if the given type is not in the list returned
   *              by <code>PortletRequest.getResponseContentTypes</code>
   *
   * @see  RenderRequest#getResponseContentTypes
   * @see  #getContentType
   */
    @Override
  public void setContentType(String type)
  {
        super.setContentType(type);
        isContentTypeSet = true;
  }
  
  /**
   * Returns a PrintWriter object that can send character
   * text to the portal.
   * <p>
   * Before calling this method the content type of the
   * render response must be set using the {@link #setContentType}
   * method.
   * <p>
   * Either this method or {@link #getPortletOutputStream} may be
   * called to write the body, not both.
   *
   * @return    a <code>PrintWriter</code> object that
   * 		can return character data to the portal
   *
   * @exception  java.io.IOException
   *                 if an input or output exception occurred
   * @exception  java.lang.IllegalStateException
   *                 if the <code>getPortletOutputStream</code> method
   * 		     has been called on this response,
   *                 or if no content type was set using the
   *                 <code>setContentType</code> method.
   *
   * @see #setContentType
   * @see #getPortletOutputStream
   */
    @Override
  public java.io.PrintWriter getWriter() throws java.io.IOException
  {
        if(!isContentTypeSet)
            throw new IllegalStateException("No content type set.");
        else
            return super.getWriter();
  }

    public void setNextPossiblePortletModes(Collection<PortletMode> arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ResourceURL createResourceURL() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public CacheControl getCacheControl() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
  
}
