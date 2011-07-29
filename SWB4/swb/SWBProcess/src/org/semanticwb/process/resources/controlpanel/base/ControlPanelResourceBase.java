package org.semanticwb.process.resources.controlpanel.base;


public abstract class ControlPanelResourceBase extends org.semanticwb.portal.api.GenericSemResource 
{
    public static final org.semanticwb.platform.SemanticProperty cpanel_groupFilter=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/process/resources/ControlPanel#groupFilter");
    public static final org.semanticwb.platform.SemanticProperty cpanel_itemsPerPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/process/resources/ControlPanel#itemsPerPage");
    public static final org.semanticwb.platform.SemanticClass swb_Resource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Resource");
    public static final org.semanticwb.platform.SemanticProperty swb_semanticResourceInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#semanticResourceInv");
    public static final org.semanticwb.platform.SemanticProperty cpanel_sortType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/process/resources/ControlPanel#sortType");
    public static final org.semanticwb.platform.SemanticProperty cpanel_statusFilter=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/process/resources/ControlPanel#statusFilter");
    public static final org.semanticwb.platform.SemanticClass cpanel_ControlPanelResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/process/resources/ControlPanel#ControlPanelResource");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/process/resources/ControlPanel#ControlPanelResource");

    public ControlPanelResourceBase()
    {
    }

   /**
   * Constructs a ControlPanelResourceBase with a SemanticObject
   * @param base The SemanticObject with the properties for the ControlPanelResource
   */
    public ControlPanelResourceBase(org.semanticwb.platform.SemanticObject base)
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
* Gets the GroupFilter property
* @return String with the GroupFilter
*/
    public String getGroupFilter()
    {
        return getSemanticObject().getProperty(cpanel_groupFilter);
    }

/**
* Sets the GroupFilter property
* @param value long with the GroupFilter
*/
    public void setGroupFilter(String value)
    {
        getSemanticObject().setProperty(cpanel_groupFilter, value);
    }

/**
* Gets the ItemsPerPage property
* @return int with the ItemsPerPage
*/
    public int getItemsPerPage()
    {
        return getSemanticObject().getIntProperty(cpanel_itemsPerPage);
    }

/**
* Sets the ItemsPerPage property
* @param value long with the ItemsPerPage
*/
    public void setItemsPerPage(int value)
    {
        getSemanticObject().setIntProperty(cpanel_itemsPerPage, value);
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
* Gets the SortType property
* @return int with the SortType
*/
    public int getSortType()
    {
        return getSemanticObject().getIntProperty(cpanel_sortType);
    }

/**
* Sets the SortType property
* @param value long with the SortType
*/
    public void setSortType(int value)
    {
        getSemanticObject().setIntProperty(cpanel_sortType, value);
    }

/**
* Gets the StatusFilter property
* @return String with the StatusFilter
*/
    public String getStatusFilter()
    {
        return getSemanticObject().getProperty(cpanel_statusFilter);
    }

/**
* Sets the StatusFilter property
* @param value long with the StatusFilter
*/
    public void setStatusFilter(String value)
    {
        getSemanticObject().setProperty(cpanel_statusFilter, value);
    }
}
