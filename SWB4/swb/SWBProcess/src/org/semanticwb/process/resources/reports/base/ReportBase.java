package org.semanticwb.process.resources.reports.base;


public abstract class ReportBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticClass swp_Process=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#Process");
    public static final org.semanticwb.platform.SemanticProperty rep_processName=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://org.semanticwb.process.resources/Reports#processName");
   /**
   * Almacena las columnas pertenecientes a determinado reporte
   */
    public static final org.semanticwb.platform.SemanticClass rep_ColumnReport=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://org.semanticwb.process.resources/Reports#ColumnReport");
    public static final org.semanticwb.platform.SemanticProperty rep_hasColumnReport=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://org.semanticwb.process.resources/Reports#hasColumnReport");
    public static final org.semanticwb.platform.SemanticProperty rep_pagingSize=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://org.semanticwb.process.resources/Reports#pagingSize");
    public static final org.semanticwb.platform.SemanticClass rep_FileReport=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://org.semanticwb.process.resources/Reports#FileReport");
    public static final org.semanticwb.platform.SemanticProperty rep_hasFilterReport=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://org.semanticwb.process.resources/Reports#hasFilterReport");
    public static final org.semanticwb.platform.SemanticProperty rep_hasFileReport=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://org.semanticwb.process.resources/Reports#hasFileReport");
    public static final org.semanticwb.platform.SemanticProperty rep_in_reportName=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://org.semanticwb.process.resources/Reports#in_reportName");
    public static final org.semanticwb.platform.SemanticClass swp_ItemAware=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ItemAware");
    public static final org.semanticwb.platform.SemanticProperty rep_hasReportItemAware=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://org.semanticwb.process.resources/Reports#hasReportItemAware");
    public static final org.semanticwb.platform.SemanticProperty rep_in_fileNameReport=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://org.semanticwb.process.resources/Reports#in_fileNameReport");
    public static final org.semanticwb.platform.SemanticClass rep_Report=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://org.semanticwb.process.resources/Reports#Report");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://org.semanticwb.process.resources/Reports#Report");

    public static class ClassMgr
    {
       /**
       * Returns a list of Report for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.resources.reports.Report
       */

        public static java.util.Iterator<org.semanticwb.process.resources.reports.Report> listReports(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.reports.Report>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.resources.reports.Report for all models
       * @return Iterator of org.semanticwb.process.resources.reports.Report
       */

        public static java.util.Iterator<org.semanticwb.process.resources.reports.Report> listReports()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.reports.Report>(it, true);
        }

        public static org.semanticwb.process.resources.reports.Report createReport(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.resources.reports.Report.ClassMgr.createReport(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.resources.reports.Report
       * @param id Identifier for org.semanticwb.process.resources.reports.Report
       * @param model Model of the org.semanticwb.process.resources.reports.Report
       * @return A org.semanticwb.process.resources.reports.Report
       */
        public static org.semanticwb.process.resources.reports.Report getReport(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.resources.reports.Report)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.resources.reports.Report
       * @param id Identifier for org.semanticwb.process.resources.reports.Report
       * @param model Model of the org.semanticwb.process.resources.reports.Report
       * @return A org.semanticwb.process.resources.reports.Report
       */
        public static org.semanticwb.process.resources.reports.Report createReport(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.resources.reports.Report)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.resources.reports.Report
       * @param id Identifier for org.semanticwb.process.resources.reports.Report
       * @param model Model of the org.semanticwb.process.resources.reports.Report
       */
        public static void removeReport(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.resources.reports.Report
       * @param id Identifier for org.semanticwb.process.resources.reports.Report
       * @param model Model of the org.semanticwb.process.resources.reports.Report
       * @return true if the org.semanticwb.process.resources.reports.Report exists, false otherwise
       */

        public static boolean hasReport(String id, org.semanticwb.model.SWBModel model)
        {
            return (getReport(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.resources.reports.Report with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.resources.reports.Report
       * @return Iterator with all the org.semanticwb.process.resources.reports.Report
       */

        public static java.util.Iterator<org.semanticwb.process.resources.reports.Report> listReportByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.reports.Report> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.resources.reports.Report with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.resources.reports.Report
       */

        public static java.util.Iterator<org.semanticwb.process.resources.reports.Report> listReportByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.reports.Report> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.resources.reports.Report with a determined ProcessName
       * @param value ProcessName of the type org.semanticwb.process.model.Process
       * @param model Model of the org.semanticwb.process.resources.reports.Report
       * @return Iterator with all the org.semanticwb.process.resources.reports.Report
       */

        public static java.util.Iterator<org.semanticwb.process.resources.reports.Report> listReportByProcessName(org.semanticwb.process.model.Process value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.reports.Report> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(rep_processName, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.resources.reports.Report with a determined ProcessName
       * @param value ProcessName of the type org.semanticwb.process.model.Process
       * @return Iterator with all the org.semanticwb.process.resources.reports.Report
       */

        public static java.util.Iterator<org.semanticwb.process.resources.reports.Report> listReportByProcessName(org.semanticwb.process.model.Process value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.reports.Report> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(rep_processName,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.resources.reports.Report with a determined ColumnReport
       * @param value ColumnReport of the type org.semanticwb.process.resources.reports.ColumnReport
       * @param model Model of the org.semanticwb.process.resources.reports.Report
       * @return Iterator with all the org.semanticwb.process.resources.reports.Report
       */

        public static java.util.Iterator<org.semanticwb.process.resources.reports.Report> listReportByColumnReport(org.semanticwb.process.resources.reports.ColumnReport value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.reports.Report> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(rep_hasColumnReport, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.resources.reports.Report with a determined ColumnReport
       * @param value ColumnReport of the type org.semanticwb.process.resources.reports.ColumnReport
       * @return Iterator with all the org.semanticwb.process.resources.reports.Report
       */

        public static java.util.Iterator<org.semanticwb.process.resources.reports.Report> listReportByColumnReport(org.semanticwb.process.resources.reports.ColumnReport value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.reports.Report> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(rep_hasColumnReport,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.resources.reports.Report with a determined FilterReport
       * @param value FilterReport of the type org.semanticwb.process.resources.reports.FileReport
       * @param model Model of the org.semanticwb.process.resources.reports.Report
       * @return Iterator with all the org.semanticwb.process.resources.reports.Report
       */

        public static java.util.Iterator<org.semanticwb.process.resources.reports.Report> listReportByFilterReport(org.semanticwb.process.resources.reports.FileReport value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.reports.Report> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(rep_hasFilterReport, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.resources.reports.Report with a determined FilterReport
       * @param value FilterReport of the type org.semanticwb.process.resources.reports.FileReport
       * @return Iterator with all the org.semanticwb.process.resources.reports.Report
       */

        public static java.util.Iterator<org.semanticwb.process.resources.reports.Report> listReportByFilterReport(org.semanticwb.process.resources.reports.FileReport value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.reports.Report> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(rep_hasFilterReport,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.resources.reports.Report with a determined FileReport
       * @param value FileReport of the type org.semanticwb.process.resources.reports.FileReport
       * @param model Model of the org.semanticwb.process.resources.reports.Report
       * @return Iterator with all the org.semanticwb.process.resources.reports.Report
       */

        public static java.util.Iterator<org.semanticwb.process.resources.reports.Report> listReportByFileReport(org.semanticwb.process.resources.reports.FileReport value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.reports.Report> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(rep_hasFileReport, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.resources.reports.Report with a determined FileReport
       * @param value FileReport of the type org.semanticwb.process.resources.reports.FileReport
       * @return Iterator with all the org.semanticwb.process.resources.reports.Report
       */

        public static java.util.Iterator<org.semanticwb.process.resources.reports.Report> listReportByFileReport(org.semanticwb.process.resources.reports.FileReport value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.reports.Report> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(rep_hasFileReport,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.resources.reports.Report with a determined In_reportName
       * @param value In_reportName of the type org.semanticwb.process.resources.reports.ColumnReport
       * @param model Model of the org.semanticwb.process.resources.reports.Report
       * @return Iterator with all the org.semanticwb.process.resources.reports.Report
       */

        public static java.util.Iterator<org.semanticwb.process.resources.reports.Report> listReportByIn_reportName(org.semanticwb.process.resources.reports.ColumnReport value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.reports.Report> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(rep_in_reportName, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.resources.reports.Report with a determined In_reportName
       * @param value In_reportName of the type org.semanticwb.process.resources.reports.ColumnReport
       * @return Iterator with all the org.semanticwb.process.resources.reports.Report
       */

        public static java.util.Iterator<org.semanticwb.process.resources.reports.Report> listReportByIn_reportName(org.semanticwb.process.resources.reports.ColumnReport value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.reports.Report> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(rep_in_reportName,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.resources.reports.Report with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.resources.reports.Report
       * @return Iterator with all the org.semanticwb.process.resources.reports.Report
       */

        public static java.util.Iterator<org.semanticwb.process.resources.reports.Report> listReportByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.reports.Report> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.resources.reports.Report with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.resources.reports.Report
       */

        public static java.util.Iterator<org.semanticwb.process.resources.reports.Report> listReportByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.reports.Report> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.resources.reports.Report with a determined ReportItemAware
       * @param value ReportItemAware of the type org.semanticwb.process.model.ItemAware
       * @param model Model of the org.semanticwb.process.resources.reports.Report
       * @return Iterator with all the org.semanticwb.process.resources.reports.Report
       */

        public static java.util.Iterator<org.semanticwb.process.resources.reports.Report> listReportByReportItemAware(org.semanticwb.process.model.ItemAware value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.reports.Report> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(rep_hasReportItemAware, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.resources.reports.Report with a determined ReportItemAware
       * @param value ReportItemAware of the type org.semanticwb.process.model.ItemAware
       * @return Iterator with all the org.semanticwb.process.resources.reports.Report
       */

        public static java.util.Iterator<org.semanticwb.process.resources.reports.Report> listReportByReportItemAware(org.semanticwb.process.model.ItemAware value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.reports.Report> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(rep_hasReportItemAware,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.resources.reports.Report with a determined In_fileNameReport
       * @param value In_fileNameReport of the type org.semanticwb.process.resources.reports.FileReport
       * @param model Model of the org.semanticwb.process.resources.reports.Report
       * @return Iterator with all the org.semanticwb.process.resources.reports.Report
       */

        public static java.util.Iterator<org.semanticwb.process.resources.reports.Report> listReportByIn_fileNameReport(org.semanticwb.process.resources.reports.FileReport value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.reports.Report> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(rep_in_fileNameReport, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.resources.reports.Report with a determined In_fileNameReport
       * @param value In_fileNameReport of the type org.semanticwb.process.resources.reports.FileReport
       * @return Iterator with all the org.semanticwb.process.resources.reports.Report
       */

        public static java.util.Iterator<org.semanticwb.process.resources.reports.Report> listReportByIn_fileNameReport(org.semanticwb.process.resources.reports.FileReport value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.reports.Report> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(rep_in_fileNameReport,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static ReportBase.ClassMgr getReportClassMgr()
    {
        return new ReportBase.ClassMgr();
    }

   /**
   * Constructs a ReportBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Report
   */
    public ReportBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Sets the value for the property ModifiedBy
   * @param value ModifiedBy to set
   */

    public void setModifiedBy(org.semanticwb.model.User value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_modifiedBy, value.getSemanticObject());
        }else
        {
            removeModifiedBy();
        }
    }
   /**
   * Remove the value for ModifiedBy property
   */

    public void removeModifiedBy()
    {
        getSemanticObject().removeProperty(swb_modifiedBy);
    }

   /**
   * Gets the ModifiedBy
   * @return a org.semanticwb.model.User
   */
    public org.semanticwb.model.User getModifiedBy()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_modifiedBy);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property ProcessName
   * @param value ProcessName to set
   */

    public void setProcessName(org.semanticwb.process.model.Process value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(rep_processName, value.getSemanticObject());
        }else
        {
            removeProcessName();
        }
    }
   /**
   * Remove the value for ProcessName property
   */

    public void removeProcessName()
    {
        getSemanticObject().removeProperty(rep_processName);
    }

   /**
   * Gets the ProcessName
   * @return a org.semanticwb.process.model.Process
   */
    public org.semanticwb.process.model.Process getProcessName()
    {
         org.semanticwb.process.model.Process ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(rep_processName);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Process)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Created property
* @return java.util.Date with the Created
*/
    public java.util.Date getCreated()
    {
        return getSemanticObject().getDateProperty(swb_created);
    }

/**
* Sets the Created property
* @param value long with the Created
*/
    public void setCreated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_created, value);
    }

/**
* Gets the Updated property
* @return java.util.Date with the Updated
*/
    public java.util.Date getUpdated()
    {
        return getSemanticObject().getDateProperty(swb_updated);
    }

/**
* Sets the Updated property
* @param value long with the Updated
*/
    public void setUpdated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_updated, value);
    }

/**
* Gets the Description property
* @return String with the Description
*/
    public String getDescription()
    {
        return getSemanticObject().getProperty(swb_description);
    }

/**
* Sets the Description property
* @param value long with the Description
*/
    public void setDescription(String value)
    {
        getSemanticObject().setProperty(swb_description, value);
    }

    public String getDescription(String lang)
    {
        return getSemanticObject().getProperty(swb_description, null, lang);
    }

    public String getDisplayDescription(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_description, lang);
    }

    public void setDescription(String description, String lang)
    {
        getSemanticObject().setProperty(swb_description, description, lang);
    }
   /**
   * Gets all the org.semanticwb.process.resources.reports.ColumnReport
   * @return A GenericIterator with all the org.semanticwb.process.resources.reports.ColumnReport
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.reports.ColumnReport> listColumnReports()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.reports.ColumnReport>(getSemanticObject().listObjectProperties(rep_hasColumnReport));
    }

   /**
   * Gets true if has a ColumnReport
   * @param value org.semanticwb.process.resources.reports.ColumnReport to verify
   * @return true if the org.semanticwb.process.resources.reports.ColumnReport exists, false otherwise
   */
    public boolean hasColumnReport(org.semanticwb.process.resources.reports.ColumnReport value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(rep_hasColumnReport,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a ColumnReport
   * @param value org.semanticwb.process.resources.reports.ColumnReport to add
   */

    public void addColumnReport(org.semanticwb.process.resources.reports.ColumnReport value)
    {
        getSemanticObject().addObjectProperty(rep_hasColumnReport, value.getSemanticObject());
    }
   /**
   * Removes all the ColumnReport
   */

    public void removeAllColumnReport()
    {
        getSemanticObject().removeProperty(rep_hasColumnReport);
    }
   /**
   * Removes a ColumnReport
   * @param value org.semanticwb.process.resources.reports.ColumnReport to remove
   */

    public void removeColumnReport(org.semanticwb.process.resources.reports.ColumnReport value)
    {
        getSemanticObject().removeObjectProperty(rep_hasColumnReport,value.getSemanticObject());
    }

   /**
   * Gets the ColumnReport
   * @return a org.semanticwb.process.resources.reports.ColumnReport
   */
    public org.semanticwb.process.resources.reports.ColumnReport getColumnReport()
    {
         org.semanticwb.process.resources.reports.ColumnReport ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(rep_hasColumnReport);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.resources.reports.ColumnReport)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the PagingSize property
* @return int with the PagingSize
*/
    public int getPagingSize()
    {
        return getSemanticObject().getIntProperty(rep_pagingSize);
    }

/**
* Sets the PagingSize property
* @param value long with the PagingSize
*/
    public void setPagingSize(int value)
    {
        getSemanticObject().setIntProperty(rep_pagingSize, value);
    }
   /**
   * Gets all the org.semanticwb.process.resources.reports.FileReport
   * @return A GenericIterator with all the org.semanticwb.process.resources.reports.FileReport
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.reports.FileReport> listFilterReports()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.reports.FileReport>(getSemanticObject().listObjectProperties(rep_hasFilterReport));
    }

   /**
   * Gets true if has a FilterReport
   * @param value org.semanticwb.process.resources.reports.FileReport to verify
   * @return true if the org.semanticwb.process.resources.reports.FileReport exists, false otherwise
   */
    public boolean hasFilterReport(org.semanticwb.process.resources.reports.FileReport value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(rep_hasFilterReport,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a FilterReport
   * @param value org.semanticwb.process.resources.reports.FileReport to add
   */

    public void addFilterReport(org.semanticwb.process.resources.reports.FileReport value)
    {
        getSemanticObject().addObjectProperty(rep_hasFilterReport, value.getSemanticObject());
    }
   /**
   * Removes all the FilterReport
   */

    public void removeAllFilterReport()
    {
        getSemanticObject().removeProperty(rep_hasFilterReport);
    }
   /**
   * Removes a FilterReport
   * @param value org.semanticwb.process.resources.reports.FileReport to remove
   */

    public void removeFilterReport(org.semanticwb.process.resources.reports.FileReport value)
    {
        getSemanticObject().removeObjectProperty(rep_hasFilterReport,value.getSemanticObject());
    }

   /**
   * Gets the FilterReport
   * @return a org.semanticwb.process.resources.reports.FileReport
   */
    public org.semanticwb.process.resources.reports.FileReport getFilterReport()
    {
         org.semanticwb.process.resources.reports.FileReport ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(rep_hasFilterReport);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.resources.reports.FileReport)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Gets all the org.semanticwb.process.resources.reports.FileReport
   * @return A GenericIterator with all the org.semanticwb.process.resources.reports.FileReport
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.reports.FileReport> listFileReports()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.reports.FileReport>(getSemanticObject().listObjectProperties(rep_hasFileReport));
    }

   /**
   * Gets true if has a FileReport
   * @param value org.semanticwb.process.resources.reports.FileReport to verify
   * @return true if the org.semanticwb.process.resources.reports.FileReport exists, false otherwise
   */
    public boolean hasFileReport(org.semanticwb.process.resources.reports.FileReport value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(rep_hasFileReport,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a FileReport
   * @param value org.semanticwb.process.resources.reports.FileReport to add
   */

    public void addFileReport(org.semanticwb.process.resources.reports.FileReport value)
    {
        getSemanticObject().addObjectProperty(rep_hasFileReport, value.getSemanticObject());
    }
   /**
   * Removes all the FileReport
   */

    public void removeAllFileReport()
    {
        getSemanticObject().removeProperty(rep_hasFileReport);
    }
   /**
   * Removes a FileReport
   * @param value org.semanticwb.process.resources.reports.FileReport to remove
   */

    public void removeFileReport(org.semanticwb.process.resources.reports.FileReport value)
    {
        getSemanticObject().removeObjectProperty(rep_hasFileReport,value.getSemanticObject());
    }

   /**
   * Gets the FileReport
   * @return a org.semanticwb.process.resources.reports.FileReport
   */
    public org.semanticwb.process.resources.reports.FileReport getFileReport()
    {
         org.semanticwb.process.resources.reports.FileReport ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(rep_hasFileReport);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.resources.reports.FileReport)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property In_reportName
   * @param value In_reportName to set
   */

    public void setIn_reportName(org.semanticwb.process.resources.reports.ColumnReport value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(rep_in_reportName, value.getSemanticObject());
        }else
        {
            removeIn_reportName();
        }
    }
   /**
   * Remove the value for In_reportName property
   */

    public void removeIn_reportName()
    {
        getSemanticObject().removeProperty(rep_in_reportName);
    }

   /**
   * Gets the In_reportName
   * @return a org.semanticwb.process.resources.reports.ColumnReport
   */
    public org.semanticwb.process.resources.reports.ColumnReport getIn_reportName()
    {
         org.semanticwb.process.resources.reports.ColumnReport ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(rep_in_reportName);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.resources.reports.ColumnReport)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property Creator
   * @param value Creator to set
   */

    public void setCreator(org.semanticwb.model.User value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_creator, value.getSemanticObject());
        }else
        {
            removeCreator();
        }
    }
   /**
   * Remove the value for Creator property
   */

    public void removeCreator()
    {
        getSemanticObject().removeProperty(swb_creator);
    }

   /**
   * Gets the Creator
   * @return a org.semanticwb.model.User
   */
    public org.semanticwb.model.User getCreator()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_creator);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Title property
* @return String with the Title
*/
    public String getTitle()
    {
        return getSemanticObject().getProperty(swb_title);
    }

/**
* Sets the Title property
* @param value long with the Title
*/
    public void setTitle(String value)
    {
        getSemanticObject().setProperty(swb_title, value);
    }

    public String getTitle(String lang)
    {
        return getSemanticObject().getProperty(swb_title, null, lang);
    }

    public String getDisplayTitle(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_title, lang);
    }

    public void setTitle(String title, String lang)
    {
        getSemanticObject().setProperty(swb_title, title, lang);
    }
   /**
   * Gets all the org.semanticwb.process.model.ItemAware
   * @return A GenericIterator with all the org.semanticwb.process.model.ItemAware
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ItemAware> listReportItemAwares()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ItemAware>(getSemanticObject().listObjectProperties(rep_hasReportItemAware));
    }

   /**
   * Gets true if has a ReportItemAware
   * @param value org.semanticwb.process.model.ItemAware to verify
   * @return true if the org.semanticwb.process.model.ItemAware exists, false otherwise
   */
    public boolean hasReportItemAware(org.semanticwb.process.model.ItemAware value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(rep_hasReportItemAware,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a ReportItemAware
   * @param value org.semanticwb.process.model.ItemAware to add
   */

    public void addReportItemAware(org.semanticwb.process.model.ItemAware value)
    {
        getSemanticObject().addObjectProperty(rep_hasReportItemAware, value.getSemanticObject());
    }
   /**
   * Removes all the ReportItemAware
   */

    public void removeAllReportItemAware()
    {
        getSemanticObject().removeProperty(rep_hasReportItemAware);
    }
   /**
   * Removes a ReportItemAware
   * @param value org.semanticwb.process.model.ItemAware to remove
   */

    public void removeReportItemAware(org.semanticwb.process.model.ItemAware value)
    {
        getSemanticObject().removeObjectProperty(rep_hasReportItemAware,value.getSemanticObject());
    }

   /**
   * Gets the ReportItemAware
   * @return a org.semanticwb.process.model.ItemAware
   */
    public org.semanticwb.process.model.ItemAware getReportItemAware()
    {
         org.semanticwb.process.model.ItemAware ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(rep_hasReportItemAware);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.ItemAware)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property In_fileNameReport
   * @param value In_fileNameReport to set
   */

    public void setIn_fileNameReport(org.semanticwb.process.resources.reports.FileReport value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(rep_in_fileNameReport, value.getSemanticObject());
        }else
        {
            removeIn_fileNameReport();
        }
    }
   /**
   * Remove the value for In_fileNameReport property
   */

    public void removeIn_fileNameReport()
    {
        getSemanticObject().removeProperty(rep_in_fileNameReport);
    }

   /**
   * Gets the In_fileNameReport
   * @return a org.semanticwb.process.resources.reports.FileReport
   */
    public org.semanticwb.process.resources.reports.FileReport getIn_fileNameReport()
    {
         org.semanticwb.process.resources.reports.FileReport ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(rep_in_fileNameReport);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.resources.reports.FileReport)obj.createGenericInstance();
         }
         return ret;
    }
}
