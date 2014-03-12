package org.semanticwb.process.resources.controlpanel.base;


public abstract class ControlPanelResourceBase extends org.semanticwb.portal.api.GenericSemResource 
{
    public static final org.semanticwb.platform.SemanticProperty cpanel_showResponseGraph=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/process/resources/ControlPanel#showResponseGraph");
    public static final org.semanticwb.platform.SemanticProperty cpanel_filterByGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/process/resources/ControlPanel#filterByGroup");
    public static final org.semanticwb.platform.SemanticProperty cpanel_configJSP=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/process/resources/ControlPanel#configJSP");
    public static final org.semanticwb.platform.SemanticProperty cpanel_docsJSP=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/process/resources/ControlPanel#docsJSP");
    public static final org.semanticwb.platform.SemanticProperty cpanel_viewJSP=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/process/resources/ControlPanel#viewJSP");
    public static final org.semanticwb.platform.SemanticClass swb_Resource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Resource");
    public static final org.semanticwb.platform.SemanticProperty swb_semanticResourceInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#semanticResourceInv");
    public static final org.semanticwb.platform.SemanticClass swb_WebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebPage");
    public static final org.semanticwb.platform.SemanticProperty cpanel_trackWp=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/process/resources/ControlPanel#trackWp");
    public static final org.semanticwb.platform.SemanticProperty cpanel_showStatusGraph=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/process/resources/ControlPanel#showStatusGraph");
    public static final org.semanticwb.platform.SemanticProperty cpanel_showPartGraph=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/process/resources/ControlPanel#showPartGraph");
    public static final org.semanticwb.platform.SemanticProperty cpanel_itemsPerPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/process/resources/ControlPanel#itemsPerPage");
    public static final org.semanticwb.platform.SemanticProperty cpanel_showInstanceGraph=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/process/resources/ControlPanel#showInstanceGraph");
    public static final org.semanticwb.platform.SemanticProperty cpanel_displayCols=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/process/resources/ControlPanel#displayCols");
    public static final org.semanticwb.platform.SemanticProperty cpanel_displayMapWp=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/process/resources/ControlPanel#displayMapWp");
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
* Gets the ShowResponseGraph property
* @return boolean with the ShowResponseGraph
*/
    public boolean isShowResponseGraph()
    {
        return getSemanticObject().getBooleanProperty(cpanel_showResponseGraph);
    }

/**
* Sets the ShowResponseGraph property
* @param value long with the ShowResponseGraph
*/
    public void setShowResponseGraph(boolean value)
    {
        getSemanticObject().setBooleanProperty(cpanel_showResponseGraph, value);
    }

/**
* Gets the FilterByGroup property
* @return boolean with the FilterByGroup
*/
    public boolean isFilterByGroup()
    {
        return getSemanticObject().getBooleanProperty(cpanel_filterByGroup);
    }

/**
* Sets the FilterByGroup property
* @param value long with the FilterByGroup
*/
    public void setFilterByGroup(boolean value)
    {
        getSemanticObject().setBooleanProperty(cpanel_filterByGroup, value);
    }

/**
* Gets the ConfigJSP property
* @return String with the ConfigJSP
*/
    public String getConfigJSP()
    {
        return getSemanticObject().getProperty(cpanel_configJSP);
    }

/**
* Sets the ConfigJSP property
* @param value long with the ConfigJSP
*/
    public void setConfigJSP(String value)
    {
        getSemanticObject().setProperty(cpanel_configJSP, value);
    }

/**
* Gets the DocsJSP property
* @return String with the DocsJSP
*/
    public String getDocsJSP()
    {
        return getSemanticObject().getProperty(cpanel_docsJSP);
    }

/**
* Sets the DocsJSP property
* @param value long with the DocsJSP
*/
    public void setDocsJSP(String value)
    {
        getSemanticObject().setProperty(cpanel_docsJSP, value);
    }

/**
* Gets the ViewJSP property
* @return String with the ViewJSP
*/
    public String getViewJSP()
    {
        return getSemanticObject().getProperty(cpanel_viewJSP);
    }

/**
* Sets the ViewJSP property
* @param value long with the ViewJSP
*/
    public void setViewJSP(String value)
    {
        getSemanticObject().setProperty(cpanel_viewJSP, value);
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
   * Sets the value for the property TrackWp
   * @param value TrackWp to set
   */

    public void setTrackWp(org.semanticwb.model.WebPage value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(cpanel_trackWp, value.getSemanticObject());
        }else
        {
            removeTrackWp();
        }
    }
   /**
   * Remove the value for TrackWp property
   */

    public void removeTrackWp()
    {
        getSemanticObject().removeProperty(cpanel_trackWp);
    }

   /**
   * Gets the TrackWp
   * @return a org.semanticwb.model.WebPage
   */
    public org.semanticwb.model.WebPage getTrackWp()
    {
         org.semanticwb.model.WebPage ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(cpanel_trackWp);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.WebPage)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the ShowStatusGraph property
* @return boolean with the ShowStatusGraph
*/
    public boolean isShowStatusGraph()
    {
        return getSemanticObject().getBooleanProperty(cpanel_showStatusGraph);
    }

/**
* Sets the ShowStatusGraph property
* @param value long with the ShowStatusGraph
*/
    public void setShowStatusGraph(boolean value)
    {
        getSemanticObject().setBooleanProperty(cpanel_showStatusGraph, value);
    }

/**
* Gets the ShowPartGraph property
* @return boolean with the ShowPartGraph
*/
    public boolean isShowPartGraph()
    {
        return getSemanticObject().getBooleanProperty(cpanel_showPartGraph);
    }

/**
* Sets the ShowPartGraph property
* @param value long with the ShowPartGraph
*/
    public void setShowPartGraph(boolean value)
    {
        getSemanticObject().setBooleanProperty(cpanel_showPartGraph, value);
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
* Gets the ShowInstanceGraph property
* @return boolean with the ShowInstanceGraph
*/
    public boolean isShowInstanceGraph()
    {
        return getSemanticObject().getBooleanProperty(cpanel_showInstanceGraph);
    }

/**
* Sets the ShowInstanceGraph property
* @param value long with the ShowInstanceGraph
*/
    public void setShowInstanceGraph(boolean value)
    {
        getSemanticObject().setBooleanProperty(cpanel_showInstanceGraph, value);
    }

/**
* Gets the DisplayCols property
* @return String with the DisplayCols
*/
    public String getDisplayCols()
    {
        return getSemanticObject().getProperty(cpanel_displayCols);
    }

/**
* Sets the DisplayCols property
* @param value long with the DisplayCols
*/
    public void setDisplayCols(String value)
    {
        getSemanticObject().setProperty(cpanel_displayCols, value);
    }
   /**
   * Sets the value for the property DisplayMapWp
   * @param value DisplayMapWp to set
   */

    public void setDisplayMapWp(org.semanticwb.model.WebPage value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(cpanel_displayMapWp, value.getSemanticObject());
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
        getSemanticObject().removeProperty(cpanel_displayMapWp);
    }

   /**
   * Gets the DisplayMapWp
   * @return a org.semanticwb.model.WebPage
   */
    public org.semanticwb.model.WebPage getDisplayMapWp()
    {
         org.semanticwb.model.WebPage ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(cpanel_displayMapWp);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.WebPage)obj.createGenericInstance();
         }
         return ret;
    }
}
