package org.semanticwb.process.reports.base;


public abstract class ResourceReportsBase extends org.semanticwb.portal.api.GenericSemResource 
{
    public static final org.semanticwb.platform.SemanticClass swb_Resource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Resource");
    public static final org.semanticwb.platform.SemanticProperty swb_semanticResourceInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#semanticResourceInv");
    public static final org.semanticwb.platform.SemanticProperty rep_pageElements=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://org.semanticwb.resources/Reports#pageElements");
    public static final org.semanticwb.platform.SemanticClass rep_Report=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://org.semanticwb.resources/Reports#Report");
    public static final org.semanticwb.platform.SemanticProperty rep_hasReport=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://org.semanticwb.resources/Reports#hasReport");
    public static final org.semanticwb.platform.SemanticClass rep_ResourceReports=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://org.semanticwb.resources/Reports#ResourceReports");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://org.semanticwb.resources/Reports#ResourceReports");

    public ResourceReportsBase()
    {
    }

   /**
   * Constructs a ResourceReportsBase with a SemanticObject
   * @param base The SemanticObject with the properties for the ResourceReports
   */
    public ResourceReportsBase(org.semanticwb.platform.SemanticObject base)
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
   * Gets all the org.semanticwb.process.reports.Report
   * @return A GenericIterator with all the org.semanticwb.process.reports.Report
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.reports.Report> listReports()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.reports.Report>(getSemanticObject().listObjectProperties(rep_hasReport));
    }

   /**
   * Gets true if has a Report
   * @param value org.semanticwb.process.reports.Report to verify
   * @return true if the org.semanticwb.process.reports.Report exists, false otherwise
   */
    public boolean hasReport(org.semanticwb.process.reports.Report value)
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
   * @param value org.semanticwb.process.reports.Report to add
   */

    public void addReport(org.semanticwb.process.reports.Report value)
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
   * @param value org.semanticwb.process.reports.Report to remove
   */

    public void removeReport(org.semanticwb.process.reports.Report value)
    {
        getSemanticObject().removeObjectProperty(rep_hasReport,value.getSemanticObject());
    }

   /**
   * Gets the Report
   * @return a org.semanticwb.process.reports.Report
   */
    public org.semanticwb.process.reports.Report getReport()
    {
         org.semanticwb.process.reports.Report ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(rep_hasReport);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.reports.Report)obj.createGenericInstance();
         }
         return ret;
    }
}
