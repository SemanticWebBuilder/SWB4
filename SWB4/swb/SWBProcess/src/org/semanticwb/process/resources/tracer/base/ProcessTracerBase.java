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
package org.semanticwb.process.resources.tracer.base;


public abstract class ProcessTracerBase extends org.semanticwb.portal.api.GenericSemResource 
{
    public static final org.semanticwb.platform.SemanticProperty ptrace_filterByCreator=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://org.semanticwb.process.resources/ProcessTracer#filterByCreator");
    public static final org.semanticwb.platform.SemanticClass swb_WebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebPage");
    public static final org.semanticwb.platform.SemanticProperty ptrace_displayMapPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://org.semanticwb.process.resources/ProcessTracer#displayMapPage");
    public static final org.semanticwb.platform.SemanticProperty ptrace_itemsPerPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://org.semanticwb.process.resources/ProcessTracer#itemsPerPage");
    public static final org.semanticwb.platform.SemanticProperty ptrace_processID=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://org.semanticwb.process.resources/ProcessTracer#processID");
    public static final org.semanticwb.platform.SemanticProperty ptrace_viewJSP=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://org.semanticwb.process.resources/ProcessTracer#viewJSP");
    public static final org.semanticwb.platform.SemanticClass swb_Resource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Resource");
    public static final org.semanticwb.platform.SemanticProperty swb_semanticResourceInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#semanticResourceInv");
    public static final org.semanticwb.platform.SemanticProperty ptrace_userID=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://org.semanticwb.process.resources/ProcessTracer#userID");
    public static final org.semanticwb.platform.SemanticProperty ptrace_viewMode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://org.semanticwb.process.resources/ProcessTracer#viewMode");
    public static final org.semanticwb.platform.SemanticClass ptrace_ProcessTracer=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://org.semanticwb.process.resources/ProcessTracer#ProcessTracer");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://org.semanticwb.process.resources/ProcessTracer#ProcessTracer");

    public ProcessTracerBase()
    {
    }

   /**
   * Constructs a ProcessTracerBase with a SemanticObject
   * @param base The SemanticObject with the properties for the ProcessTracer
   */
    public ProcessTracerBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /*
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() 
    {
        return getSemanticObject().hashCode();
    }

    /*
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) 
    {
        if(obj==null)return false;
        return hashCode()==obj.hashCode();
    }

/**
* Gets the FilterByCreator property
* @return boolean with the FilterByCreator
*/
    public boolean isFilterByCreator()
    {
        return getSemanticObject().getBooleanProperty(ptrace_filterByCreator);
    }

/**
* Sets the FilterByCreator property
* @param value long with the FilterByCreator
*/
    public void setFilterByCreator(boolean value)
    {
        getSemanticObject().setBooleanProperty(ptrace_filterByCreator, value);
    }
   /**
   * Sets the value for the property DisplayMapPage
   * @param value DisplayMapPage to set
   */

    public void setDisplayMapPage(org.semanticwb.model.WebPage value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(ptrace_displayMapPage, value.getSemanticObject());
        }else
        {
            removeDisplayMapPage();
        }
    }
   /**
   * Remove the value for DisplayMapPage property
   */

    public void removeDisplayMapPage()
    {
        getSemanticObject().removeProperty(ptrace_displayMapPage);
    }

   /**
   * Gets the DisplayMapPage
   * @return a org.semanticwb.model.WebPage
   */
    public org.semanticwb.model.WebPage getDisplayMapPage()
    {
         org.semanticwb.model.WebPage ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ptrace_displayMapPage);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.WebPage)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the ItemsPerPage property
* @return int with the ItemsPerPage
*/
    public int getItemsPerPage()
    {
        return getSemanticObject().getIntProperty(ptrace_itemsPerPage);
    }

/**
* Sets the ItemsPerPage property
* @param value long with the ItemsPerPage
*/
    public void setItemsPerPage(int value)
    {
        getSemanticObject().setIntProperty(ptrace_itemsPerPage, value);
    }

/**
* Gets the ProcessID property
* @return String with the ProcessID
*/
    public String getProcessID()
    {
        return getSemanticObject().getProperty(ptrace_processID);
    }

/**
* Sets the ProcessID property
* @param value long with the ProcessID
*/
    public void setProcessID(String value)
    {
        getSemanticObject().setProperty(ptrace_processID, value);
    }

/**
* Gets the ViewJSP property
* @return String with the ViewJSP
*/
    public String getViewJSP()
    {
        return getSemanticObject().getProperty(ptrace_viewJSP);
    }

/**
* Sets the ViewJSP property
* @param value long with the ViewJSP
*/
    public void setViewJSP(String value)
    {
        getSemanticObject().setProperty(ptrace_viewJSP, value);
    }
   /**
   * Sets the value for the property Resource
   * @param value Resource to set
   */

    public void setResource(org.semanticwb.model.Resource value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_semanticResourceInv, value.getSemanticObject());
        }else
        {
            removeResource();
        }
    }
   /**
   * Remove the value for Resource property
   */

    public void removeResource()
    {
        getSemanticObject().removeProperty(swb_semanticResourceInv);
    }

   /**
   * Gets the Resource
   * @return a org.semanticwb.model.Resource
   */
    public org.semanticwb.model.Resource getResource()
    {
         org.semanticwb.model.Resource ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_semanticResourceInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Resource)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the UserID property
* @return String with the UserID
*/
    public String getUserID()
    {
        return getSemanticObject().getProperty(ptrace_userID);
    }

/**
* Sets the UserID property
* @param value long with the UserID
*/
    public void setUserID(String value)
    {
        getSemanticObject().setProperty(ptrace_userID, value);
    }

/**
* Gets the ViewMode property
* @return int with the ViewMode
*/
    public int getViewMode()
    {
        return getSemanticObject().getIntProperty(ptrace_viewMode);
    }

/**
* Sets the ViewMode property
* @param value long with the ViewMode
*/
    public void setViewMode(int value)
    {
        getSemanticObject().setIntProperty(ptrace_viewMode, value);
    }
}
