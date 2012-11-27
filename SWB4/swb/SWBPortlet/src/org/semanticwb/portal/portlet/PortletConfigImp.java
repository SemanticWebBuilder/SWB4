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

import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Vector;
import javax.portlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletConfig;
import javax.xml.namespace.QName;

/**
 * The <CODE>PortletConfig</CODE> interface provides the portlet with
 * its configuration. The configuration holds information about the
 * portlet that is valid for all users. The configuration is retrieved
 * from the portlet definition in the deployment descriptor.
 * The portlet can only read the configuration data.
 * <p>
 * The configuration information contains the portlet name, the portlet 
 * initialization parameters, the portlet resource bundle and the portlet 
 * application context.
 * 
 * @see Portlet
 * @author Javier Solis Gonzalez
 */
public class PortletConfigImp implements PortletConfigInt
{
    
    //Resource base=null;
    PortletContext portletContext=null;
    WBPortletDefinition portletDefinition=null;
    
    public PortletConfigImp(PortletContext portletContext, WBPortletDefinition portletDefinition)
    {
        //this.base=base;
        this.portletContext=portletContext;
        this.portletDefinition=portletDefinition;
    }
    
//    public PortletConfigImp(Resource base)
//    {
//        this.base=base;
//    }    
    
  /**
   * Returns the name of the portlet.
   * <P>
   * The name may be provided via server administration, assigned in the
   * portlet application deployment descriptor with the <code>portlet-name</code>
   * tag.
   *
   * @return   the portlet name
   */

  public String getPortletName ()
  {
      return portletDefinition.getName();
  }
  
  public WBPortletDefinition getPortletDefinition()
  {
      return portletDefinition;
  }


  /**
   * Returns the <code>PortletContext</code> of the portlet application 
   * the portlet is in.
   *
   * @return   a <code>PortletContext</code> object, used by the 
   *           caller to interact with its portlet container
   *
   * @see PortletContext
   */

  public PortletContext getPortletContext ()
  {
      return portletContext;
  }


  /**
   * Gets the resource bundle for the given locale based on the
   * resource bundle defined in the deployment descriptor
   * with <code>resource-bundle</code> tag or the inlined resources
   * defined in the deployment descriptor.
   *
   * @param    locale    the locale for which to retrieve the resource bundle
   * 
   * @return   the resource bundle for the given locale
   *
   */

  public java.util.ResourceBundle getResourceBundle(Locale locale)
  {
      //TODO
      //String bundle=base.getResourceType().getBundle();
      
      return portletDefinition.getResourceBundle(locale);
      
//      LanguageSet languageSet = portletDefinition.getLanguageSet();
//      Language lang = languageSet.get(locale);
//      
//      if (lang == null)
//      {
//          Locale defaultLocale = languageSet.getDefaultLocale();
//          lang = languageSet.get(defaultLocale);
//      }
//      
//      return lang.getResourceBundle();
      
  }


  /**
   * Returns a String containing the value of the named initialization parameter, 
   * or null if the parameter does not exist.
   *
   * @param name	a <code>String</code> specifying the name
   *			of the initialization parameter
   *
   * @return		a <code>String</code> containing the value 
   *			of the initialization parameter
   *
   * @exception	java.lang.IllegalArgumentException	
   *                      if name is <code>null</code>.
   */

  public String getInitParameter(java.lang.String name)
  {
        if (name == null)
        {
            throw new IllegalArgumentException("Parameter name == null");
        }
        return (String)portletDefinition.getInitParams().get(name);
  }


  /**
   * Returns the names of the portlet initialization parameters as an 
   * <code>Enumeration</code> of String objects, or an empty <code>Enumeration</code> if the 
   * portlet has no initialization parameters.    
   *
   * @return		an <code>Enumeration</code> of <code>String</code> 
   *			objects containing the names of the portlet 
   *			initialization parameters, or an empty <code>Enumeration</code> if the 
   *                    portlet has no initialization parameters. 
   */

  public java.util.Enumeration getInitParameterNames()
  {
      return portletDefinition.getInitParams().keys();
  }

    public Enumeration<String> getPublicRenderParameterNames() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getDefaultNamespace() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Enumeration<QName> getPublishingEventQNames() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Enumeration<QName> getProcessingEventQNames() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Enumeration<Locale> getSupportedLocales() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Map<String, String[]> getContainerRuntimeOptions() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
  
}
