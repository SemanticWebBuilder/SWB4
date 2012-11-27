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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.portlet.Portlet;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.PreferencesValidator;
import javax.servlet.ServletConfig;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.portlet.factory.WBFactoryMgr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Javier Solis Gonzalez
 */
public class WBPortletDefinitionImp implements WBPortletDefinition
{
    private String id;
    private String site;
    private String portletgui;
    private String context;
    private String portletName;
    private String className;
    private String resourceBundle;
    private Portlet portlet=null;
    private ClassLoader portletClassLoader;
    private ServletConfig servletConfig;
    private PortletConfig portletConfig;
    private List supportedLocales=new ArrayList();
    private Hashtable initParams=new Hashtable();
    private ResourceBundle defaultBundle;;
    private Hashtable resourceBundles=new Hashtable();
    private PortletPreferencesImp defaultPreferences;
    private String preferencesValidatorClass;
    private PreferencesValidator preferencesValidator;
    private Hashtable roles=new Hashtable();
    private PortletModesSupport support=new PortletModesSupport();
    
    /** Creates a new instance of PortletDefinition */
    public WBPortletDefinitionImp(String id, String site, String contextName, String portletName)
    {
        //NOTA: cambiar id, site por objeto para almacenar datos del portlet a DB
        // y cambiar contex y portletname por portletGUI (context.name)
        this.portletgui=portletgui;
        this.id=id;
        this.site=site;
        this.context="/"+contextName;//"/"+portletgui.substring(0,portletgui.indexOf('.'));
        this.portletName=portletName;//portletgui.substring(portletgui.indexOf('.')+1);
        System.out.println("context:"+context);
        System.out.println("portletName:"+portletName);
        this.defaultPreferences=new PortletPreferencesImp();
    }
    
    
    /**
     * Getter for property id.
     * @return Value of property id.
     */
    public String getId()
    {
        return id;
    }
    
    /**
     * Setter for property id.
     * @param id New value of property id.
     */
    public void setId(String id)
    {
        this.id = id;
    }
    
    /**
     * Getter for property site.
     * @return Value of property site.
     */
    public java.lang.String getSite()
    {
        return site;
    }
    
    /**
     * Setter for property site.
     * @param site New value of property site.
     */
    public void setSite(java.lang.String site)
    {
        this.site = site;
    }
    
    /**
     * Getter for property context.
     * @return Value of property context.
     */
    public java.lang.String getContext()
    {
        return context;
    }
    
    /**
     * Setter for property context.
     * @param context New value of property context.
     */
    public void setContext(java.lang.String context)
    {
        this.context = context;
    }
    
    /**
     * Getter for property portletName.
     * @return Value of property portletName.
     */
    public java.lang.String getName()
    {
        return portletName;
    }
    
    /**
     * Setter for property portletName.
     * @param portletName New value of property portletName.
     */
    public void setName(java.lang.String portletName)
    {
        this.portletName = portletName;
    }
    
    /**
     * Getter for property portlet.
     * @return Value of property portlet.
     */
    public javax.portlet.Portlet getPortlet()
    {
        return portlet;
    }
    
    /**
     * Setter for property portlet.
     * @param portlet New value of property portlet.
     */
    public void setPortlet(javax.portlet.Portlet portlet)
    {
        this.portlet = portlet;
    }    
    
    public java.util.ResourceBundle getResourceBundle(Locale locale)    
    {
        ResourceBundle res=(ResourceBundle)resourceBundles.get(locale);
        if(res==null)
        {
            ResourceBundle aux=null;
            if(resourceBundle!=null)
            {
                try
                {
                    aux=ResourceBundle.getBundle(resourceBundle, locale, getPortletClassLoader());
                }catch(MissingResourceException e)
                {
                    try
                    {
                        if(supportedLocales.size()>0)
                        {
                            aux=ResourceBundle.getBundle(resourceBundle, (Locale)supportedLocales.get(0),getPortletClassLoader());          
                        }                        
                    }catch(MissingResourceException e2)
                    {
                        WBFactoryMgr.getPortletContainer().log(e2);
                    }
                }
            }
            res=new WBResourceBundle(aux, defaultBundle);
            resourceBundles.put(locale,res);
        }
        //System.out.println("getResourceBundle:"+locale+" "+res);
        return res;
        
//      if(bundle==null)bundle=portletDefinition.getClassName();
//      try
//      {
//            return java.util.ResourceBundle.getBundle(bundle, new Locale(locale.getLanguage()),portletDefinition.getPortletClassLoader());
//      }catch(MissingResourceException e)
//      {
//          List locales=portletDefinition.getSupportedLocales();
//          if(locales.size()>0)
//          {
//            return java.util.ResourceBundle.getBundle(bundle, (Locale)locales.get(0),portletDefinition.getPortletClassLoader());          
//          }
//      }
    }
    
    public void loadDefinition(InputStream in)
    {
        //System.out.println("loadDefinition..."+in);
        //TODO: completar
        if(in!=null)
        {
            Document doc=SWBUtils.XML.xmlToDom(in);
            //System.out.println("Document:"+AFUtils.getInstance().DomtoXml(doc));
            NodeList nl=doc.getElementsByTagName("portlet");
            //System.out.println("portletNodes:"+nl.getLength());
            for(int x=0;x<nl.getLength();x++)
            {
                Element root=(Element)nl.item(x);
                //System.out.println("portlet:"+root);
                String name=getTagValue(root,"portlet-name");
                //System.out.println("portlet-name:"+name);
                if(name!=null && name.equals(portletName))
                {
                    setClassName(getTagValue(root,"portlet-class"));
                    //System.out.println("portlet-class"+getClassName());
                    setResourceBundle(getTagValue(root,"resource-bundle"));
                    
                    Element porletInfo=getTag(root,"portlet-info");
                    if(porletInfo!=null)
                    {
			String title=getTagValue(porletInfo,"title");
                        String shortTitle=getTagValue(porletInfo,"short-title");
                        String keywords=getTagValue(porletInfo,"keywords");
                        defaultBundle=new WBResourceBundle(title,shortTitle,keywords);
                    }
                    
                    NodeList nl2=root.getElementsByTagName("supported-locale");
                    for(int i=0;i<nl2.getLength();i++)
                    {
                        supportedLocales.add(new Locale(nl2.item(i).getFirstChild().getNodeValue()));
                    }
                    
                    nl2=root.getElementsByTagName("init-param");
                    for(int i=0;i<nl2.getLength();i++)
                    {
                        Element ini=(Element)nl2.item(i);
                        String pname=getTagValue(ini, "name");
                        String pvalue=getTagValue(ini, "value");
                        if(pname!=null && pvalue!=null)
                        {
                            initParams.put(pname, pvalue);
                        }
                    }
                    
                    nl2=root.getElementsByTagName("supports");
                    for(int i=0;i<nl2.getLength();i++)
                    {
                        Element suport=(Element)nl2.item(i);
                        String mime=getTagValue(suport, "mime-type");
                        support.addMode(mime,"view");
                        String modes[]=getTagValues(suport, "portlet-mode");
                        //System.out.println("*********** mime:"+mime+" modes:"+modes);
                        support.addMode(mime,modes);
                    }                    
                    
                    Element portletPreferences=getTag(root, "portlet-preferences");
                    if(portletPreferences!=null)
                    {
                        nl2=portletPreferences.getElementsByTagName("preference");
                        for(int i=0;i<nl2.getLength();i++)
                        {
                            Element ini=(Element)nl2.item(i);
                            try
                            {
                                String pname=getTagValue(ini, "name");
                                String[] pvalues=getTagValues(ini, "value");
                                if(pname!=null && pvalues!=null)
                                {
                                    defaultPreferences.setValues(pname, pvalues);
                                }
                                String readOnly=getTagValue(ini, "read-only");
                                if(readOnly!=null && readOnly.equalsIgnoreCase("true"))
                                {
                                    defaultPreferences.setReadOnly(pname);
                                }
                            }catch(Exception e){WBFactoryMgr.getPortletContainer().log(e);}
                        }                    
                        preferencesValidatorClass=getTagValue(portletPreferences,"preferences-validator");
                    }
                    
                    nl2=root.getElementsByTagName("security-role-ref");
                    for(int i=0;i<nl2.getLength();i++)
                    {
                        Element ini=(Element)nl2.item(i);
                        try
                        {
                            String pname=getTagValue(ini, "role-name");
                            String pvalues=getTagValue(ini, "role-link");
                            if(pname!=null && pvalues!=null)
                            {
                                roles.put(pname, pvalues);
                            }
                        }catch(Exception e){WBFactoryMgr.getPortletContainer().log(e);}
                    }                    
                    
                    break;
                }
            }

            
            
        }
        //setClassName("HelloPortlet");
        //System.out.println("loadDefinition.exit");
    }
    
    private String getTagValue(Element root, String tag)
    {
        NodeList nl=root.getElementsByTagName(tag);
        if(nl.getLength()>0)
        {
            Node nd=nl.item(0);
            if(nd.hasChildNodes())
            {
                return nd.getFirstChild().getNodeValue();
            }
        }
        return null;
    }
    
    private String[] getTagValues(Element root, String tag)
    {
        NodeList nl=root.getElementsByTagName(tag);
        String list[]=new String[nl.getLength()];
        if(nl.getLength()>0)
        {
            for(int x=0;x<nl.getLength();x++)
            {
                Node nd=nl.item(x);
                if(nd.hasChildNodes())
                {
                    list[x]=nd.getFirstChild().getNodeValue();
                }else
                {
                    list[x]=null;
                }
            }
        }
        return list;
    }    
    
    private Element getTag(Element root, String tag)
    {
        NodeList nl=root.getElementsByTagName(tag);
        if(nl.getLength()>0)
        {
            return (Element)nl.item(0);
        }
        else return null;
    }
    
    
    
    public String getClassName()
    {
        return className;
    }
    
    /**
     * Setter for property className.
     * @param className New value of property className.
     */
    public void setClassName(java.lang.String className)
    {
        this.className = className;
    }    
    
    public ClassLoader getPortletClassLoader()
    {
        return portletClassLoader;
    }
    
    /**
     * Setter for property portletClassLoader.
     * @param portletClassLoader New value of property portletClassLoader.
     */
    public void setPortletClassLoader(java.lang.ClassLoader portletClassLoader)
    {
        this.portletClassLoader = portletClassLoader;
    }    

    /**
     * Getter for property servletConfig.
     * @return Value of property servletConfig.
     */
    public javax.servlet.ServletConfig getServletConfig()
    {
        return servletConfig;
    }    
    
    /**
     * Setter for property servletConfig.
     * @param servletConfig New value of property servletConfig.
     */
    public void setServletConfig(javax.servlet.ServletConfig servletConfig)
    {
        this.servletConfig = servletConfig;
    }
    
    /**
     * Getter for property resourceBundle.
     * @return Value of property resourceBundle.
     */
    public java.lang.String getResourceBundleString()
    {
        return resourceBundle;
    }
    
    /**
     * Setter for property resourceBundle.
     * @param resourceBundle New value of property resourceBundle.
     */
    public void setResourceBundle(java.lang.String resourceBundle)
    {
        this.resourceBundle = resourceBundle;
    }
    
    public List getSupportedLocales()
    {
        return supportedLocales;
    }
    
    /**
     * Getter for property initParams.
     * @return Value of property initParams.
     */
    public Hashtable getInitParams()
    {
        return initParams;
    }
    
    /**
     * Getter for property portletConfig.
     * @return Value of property portletConfig.
     */
    public javax.portlet.PortletConfig getPortletConfig()
    {
        return portletConfig;
    }    
    
    /**
     * Setter for property portletConfig.
     * @param portletConfig New value of property portletConfig.
     */
    public void setPortletConfig(javax.portlet.PortletConfig portletConfig)
    {
        this.portletConfig = portletConfig;
    }
    
    /**
     * Getter for property defaultPreferences.
     * @return Value of property defaultPreferences.
     */
    public PortletPreferences getDefaultPreferences()
    {
        return defaultPreferences;
    }
    
    /**
     * Setter for property defaultPreferences.
     * @param defaultPreferences New value of property defaultPreferences.
     */
    public void setDefaultPreferences(org.semanticwb.portal.portlet.PortletPreferencesImp defaultPreferences)
    {
        this.defaultPreferences = defaultPreferences;
    }
    
    /**
     * Getter for property preferencesValidator.
     * @return Value of property preferencesValidator.
     */
    public javax.portlet.PreferencesValidator getPreferencesValidator()
    {
        if(preferencesValidator!=null)return preferencesValidator;
        if(preferencesValidatorClass!=null)
        {
            try
            {
                Class cls=getPortletClassLoader().loadClass(preferencesValidatorClass);
                preferencesValidator=(PreferencesValidator)cls.newInstance();
                return preferencesValidator;
            }catch(Exception e){WBFactoryMgr.getPortletContainer().log(e);}
        }
        return null;
    }
    
    public Hashtable getPortletRoles()
    {
        return roles;
    }
       
    /**
     * Getter for property suports.
     * @return Value of property suports.
     */
    public org.semanticwb.portal.portlet.PortletModesSupport getPortletModesSupport()
    {
        return support;
    }
    
}
