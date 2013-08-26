package org.semanticwb.process.resources.reports.base;


public abstract class ReportResourceBase extends org.semanticwb.portal.api.GenericSemResource 
{
    public static final org.semanticwb.platform.SemanticClass swb_Resource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Resource");
    public static final org.semanticwb.platform.SemanticProperty swb_semanticResourceInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#semanticResourceInv");
    public static final org.semanticwb.platform.SemanticProperty rep_saveOnSystem=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://org.semanticwb.process.resources/Reports#saveOnSystem");
    public static final org.semanticwb.platform.SemanticProperty rep_pageElements=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://org.semanticwb.process.resources/Reports#pageElements");
    public static final org.semanticwb.platform.SemanticClass rep_Report=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://org.semanticwb.process.resources/Reports#Report");
    public static final org.semanticwb.platform.SemanticProperty rep_hasReport=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://org.semanticwb.process.resources/Reports#hasReport");
    public static final org.semanticwb.platform.SemanticProperty rep_modeExport=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://org.semanticwb.process.resources/Reports#modeExport");
    public static final org.semanticwb.platform.SemanticClass rep_ReportResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://org.semanticwb.process.resources/Reports#ReportResource");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://org.semanticwb.process.resources/Reports#ReportResource");

    public ReportResourceBase()
    {
    }

   /**
   * Constructs a ReportResourceBase with a SemanticObject
   * @param base The SemanticObject with the properties for the ReportResource
   */
    public ReportResourceBase(org.semanticwb.platform.SemanticObject base)
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
* Gets the SaveOnSystem property
* @return boolean with the SaveOnSystem
*/
    public boolean isSaveOnSystem()
    {
        return getSemanticObject().getBooleanProperty(rep_saveOnSystem);
    }

/**
* Sets the SaveOnSystem property
* @param value long with the SaveOnSystem
*/
    public void setSaveOnSystem(boolean value)
    {
        getSemanticObject().setBooleanProperty(rep_saveOnSystem, value);
    }

/**
* Gets the PageElements property
* @return int with the PageElements
*/
    public int getPageElements()
    {
        return getSemanticObject().getIntProperty(rep_pageElements);
    }

/**
* Sets the PageElements property
* @param value long with the PageElements
*/
    public void setPageElements(int value)
    {
        getSemanticObject().setIntProperty(rep_pageElements, value);
    }
   /**
   * Gets all the org.semanticwb.process.resources.reports.Report
   * @return A GenericIterator with all the org.semanticwb.process.resources.reports.Report
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.reports.Report> listReports()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.reports.Report>(getSemanticObject().listObjectProperties(rep_hasReport));
    }

   /**
   * Gets true if has a Report
   * @param value org.semanticwb.process.resources.reports.Report to verify
   * @return true if the org.semanticwb.process.resources.reports.Report exists, false otherwise
   */
    public boolean hasReport(org.semanticwb.process.resources.reports.Report value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(rep_hasReport,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a Report
   * @param value org.semanticwb.process.resources.reports.Report to add
   */

    public void addReport(org.semanticwb.process.resources.reports.Report value)
    {
        getSemanticObject().addObjectProperty(rep_hasReport, value.getSemanticObject());
    }
   /**
   * Removes all the Report
   */

    public void removeAllReport()
    {
        getSemanticObject().removeProperty(rep_hasReport);
    }
   /**
   * Removes a Report
   * @param value org.semanticwb.process.resources.reports.Report to remove
   */

    public void removeReport(org.semanticwb.process.resources.reports.Report value)
    {
        getSemanticObject().removeObjectProperty(rep_hasReport,value.getSemanticObject());
    }

   /**
   * Gets the Report
   * @return a org.semanticwb.process.resources.reports.Report
   */
    public org.semanticwb.process.resources.reports.Report getReport()
    {
         org.semanticwb.process.resources.reports.Report ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(rep_hasReport);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.resources.reports.Report)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the ModeExport property
* @return int with the ModeExport
*/
    public int getModeExport()
    {
        return getSemanticObject().getIntProperty(rep_modeExport);
    }

/**
* Sets the ModeExport property
* @param value long with the ModeExport
*/
    public void setModeExport(int value)
    {
        getSemanticObject().setIntProperty(rep_modeExport, value);
    }
}
