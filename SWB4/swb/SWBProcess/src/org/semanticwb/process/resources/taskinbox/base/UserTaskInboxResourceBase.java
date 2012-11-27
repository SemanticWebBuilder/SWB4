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
package org.semanticwb.process.resources.taskinbox.base;


public abstract class UserTaskInboxResourceBase extends org.semanticwb.portal.api.GenericSemResource 
{
    public static final org.semanticwb.platform.SemanticProperty utinbox_filterByGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/process/resources/UserTaskInbox#filterByGroup");
    public static final org.semanticwb.platform.SemanticProperty utinbox_configJSP=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/process/resources/UserTaskInbox#configJSP");
    public static final org.semanticwb.platform.SemanticProperty utinbox_viewJSP=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/process/resources/UserTaskInbox#viewJSP");
    public static final org.semanticwb.platform.SemanticClass swb_Resource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Resource");
    public static final org.semanticwb.platform.SemanticProperty swb_semanticResourceInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#semanticResourceInv");
    public static final org.semanticwb.platform.SemanticProperty utinbox_showProcessWPLink=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/process/resources/UserTaskInbox#showProcessWPLink");
    public static final org.semanticwb.platform.SemanticProperty utinbox_itemsPerPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/process/resources/UserTaskInbox#itemsPerPage");
    public static final org.semanticwb.platform.SemanticProperty utinbox_displayCols=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/process/resources/UserTaskInbox#displayCols");
    public static final org.semanticwb.platform.SemanticClass swb_WebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebPage");
    public static final org.semanticwb.platform.SemanticProperty utinbox_displayMapWp=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/process/resources/UserTaskInbox#displayMapWp");
    public static final org.semanticwb.platform.SemanticClass utinbox_UserTaskInboxResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/process/resources/UserTaskInbox#UserTaskInboxResource");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/process/resources/UserTaskInbox#UserTaskInboxResource");

    public UserTaskInboxResourceBase()
    {
    }

   /**
   * Constructs a UserTaskInboxResourceBase with a SemanticObject
   * @param base The SemanticObject with the properties for the UserTaskInboxResource
   */
    public UserTaskInboxResourceBase(org.semanticwb.platform.SemanticObject base)
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
* Gets the FilterByGroup property
* @return boolean with the FilterByGroup
*/
    public boolean isFilterByGroup()
    {
        return getSemanticObject().getBooleanProperty(utinbox_filterByGroup);
    }

/**
* Sets the FilterByGroup property
* @param value long with the FilterByGroup
*/
    public void setFilterByGroup(boolean value)
    {
        getSemanticObject().setBooleanProperty(utinbox_filterByGroup, value);
    }

/**
* Gets the ConfigJSP property
* @return String with the ConfigJSP
*/
    public String getConfigJSP()
    {
        return getSemanticObject().getProperty(utinbox_configJSP);
    }

/**
* Sets the ConfigJSP property
* @param value long with the ConfigJSP
*/
    public void setConfigJSP(String value)
    {
        getSemanticObject().setProperty(utinbox_configJSP, value);
    }

/**
* Gets the ViewJSP property
* @return String with the ViewJSP
*/
    public String getViewJSP()
    {
        return getSemanticObject().getProperty(utinbox_viewJSP);
    }

/**
* Sets the ViewJSP property
* @param value long with the ViewJSP
*/
    public void setViewJSP(String value)
    {
        getSemanticObject().setProperty(utinbox_viewJSP, value);
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
* Gets the ShowProcessWPLink property
* @return boolean with the ShowProcessWPLink
*/
    public boolean isShowProcessWPLink()
    {
        return getSemanticObject().getBooleanProperty(utinbox_showProcessWPLink);
    }

/**
* Sets the ShowProcessWPLink property
* @param value long with the ShowProcessWPLink
*/
    public void setShowProcessWPLink(boolean value)
    {
        getSemanticObject().setBooleanProperty(utinbox_showProcessWPLink, value);
    }

/**
* Gets the ItemsPerPage property
* @return int with the ItemsPerPage
*/
    public int getItemsPerPage()
    {
        return getSemanticObject().getIntProperty(utinbox_itemsPerPage);
    }

/**
* Sets the ItemsPerPage property
* @param value long with the ItemsPerPage
*/
    public void setItemsPerPage(int value)
    {
        getSemanticObject().setIntProperty(utinbox_itemsPerPage, value);
    }

/**
* Gets the DisplayCols property
* @return String with the DisplayCols
*/
    public String getDisplayCols()
    {
        return getSemanticObject().getProperty(utinbox_displayCols);
    }

/**
* Sets the DisplayCols property
* @param value long with the DisplayCols
*/
    public void setDisplayCols(String value)
    {
        getSemanticObject().setProperty(utinbox_displayCols, value);
    }
   /**
   * Sets the value for the property DisplayMapWp
   * @param value DisplayMapWp to set
   */

    public void setDisplayMapWp(org.semanticwb.model.WebPage value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(utinbox_displayMapWp, value.getSemanticObject());
        }else
        {
            removeDisplayMapWp();
        }
    }
   /**
   * Remove the value for DisplayMapWp property
   */

    public void removeDisplayMapWp()
    {
        getSemanticObject().removeProperty(utinbox_displayMapWp);
    }

   /**
   * Gets the DisplayMapWp
   * @return a org.semanticwb.model.WebPage
   */
    public org.semanticwb.model.WebPage getDisplayMapWp()
    {
         org.semanticwb.model.WebPage ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(utinbox_displayMapWp);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.WebPage)obj.createGenericInstance();
         }
         return ret;
    }
}
