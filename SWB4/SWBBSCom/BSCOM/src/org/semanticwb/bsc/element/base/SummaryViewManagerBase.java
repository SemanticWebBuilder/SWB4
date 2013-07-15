package org.semanticwb.bsc.element.base;


public abstract class SummaryViewManagerBase extends org.semanticwb.portal.api.GenericSemResource 
{
    public static final org.semanticwb.platform.SemanticClass bsc_SummaryView=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#SummaryView");
    public static final org.semanticwb.platform.SemanticProperty bsc_activeView=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#activeView");
    public static final org.semanticwb.platform.SemanticClass bsc_BSCElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#BSCElement");
    public static final org.semanticwb.platform.SemanticProperty bsc_workClass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#workClass");
    public static final org.semanticwb.platform.SemanticClass swb_Resource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Resource");
    public static final org.semanticwb.platform.SemanticProperty swb_semanticResourceInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#semanticResourceInv");
    public static final org.semanticwb.platform.SemanticClass bsc_SummaryViewManager=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#SummaryViewManager");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#SummaryViewManager");

    public SummaryViewManagerBase()
    {
    }

   /**
   * Constructs a SummaryViewManagerBase with a SemanticObject
   * @param base The SemanticObject with the properties for the SummaryViewManager
   */
    public SummaryViewManagerBase(org.semanticwb.platform.SemanticObject base)
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
   * Sets the value for the property ActiveView
   * @param value ActiveView to set
   */

    public void setActiveView(org.semanticwb.bsc.element.SummaryView value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(bsc_activeView, value.getSemanticObject());
        }else
        {
            removeActiveView();
        }
    }
   /**
   * Remove the value for ActiveView property
   */

    public void removeActiveView()
    {
        getSemanticObject().removeProperty(bsc_activeView);
    }

   /**
   * Gets the ActiveView
   * @return a org.semanticwb.bsc.element.SummaryView
   */
    public org.semanticwb.bsc.element.SummaryView getActiveView()
    {
         org.semanticwb.bsc.element.SummaryView ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_activeView);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.element.SummaryView)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property WorkClass
   * @param value WorkClass to set
   */

    public void setWorkClass(org.semanticwb.bsc.element.BSCElement value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(bsc_workClass, value.getSemanticObject());
        }else
        {
            removeWorkClass();
        }
    }
   /**
   * Remove the value for WorkClass property
   */

    public void removeWorkClass()
    {
        getSemanticObject().removeProperty(bsc_workClass);
    }

   /**
   * Gets the WorkClass
   * @return a org.semanticwb.bsc.element.BSCElement
   */
    public org.semanticwb.bsc.element.BSCElement getWorkClass()
    {
         org.semanticwb.bsc.element.BSCElement ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_workClass);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.element.BSCElement)obj.createGenericInstance();
         }
         return ret;
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
}
