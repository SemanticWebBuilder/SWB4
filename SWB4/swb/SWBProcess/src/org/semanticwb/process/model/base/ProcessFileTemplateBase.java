package org.semanticwb.process.model.base;


public abstract class ProcessFileTemplateBase extends org.semanticwb.process.model.ProcessElement implements org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticProperty swp_fileName=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#fileName");
    public static final org.semanticwb.platform.SemanticClass swp_ProcessFileTemplate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessFileTemplate");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessFileTemplate");

    public static class ClassMgr
    {
       /**
       * Returns a list of ProcessFileTemplate for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.ProcessFileTemplate
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessFileTemplate> listProcessFileTemplates(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessFileTemplate>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.ProcessFileTemplate for all models
       * @return Iterator of org.semanticwb.process.model.ProcessFileTemplate
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessFileTemplate> listProcessFileTemplates()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessFileTemplate>(it, true);
        }

        public static org.semanticwb.process.model.ProcessFileTemplate createProcessFileTemplate(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.ProcessFileTemplate.ClassMgr.createProcessFileTemplate(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.ProcessFileTemplate
       * @param id Identifier for org.semanticwb.process.model.ProcessFileTemplate
       * @param model Model of the org.semanticwb.process.model.ProcessFileTemplate
       * @return A org.semanticwb.process.model.ProcessFileTemplate
       */
        public static org.semanticwb.process.model.ProcessFileTemplate getProcessFileTemplate(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ProcessFileTemplate)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.ProcessFileTemplate
       * @param id Identifier for org.semanticwb.process.model.ProcessFileTemplate
       * @param model Model of the org.semanticwb.process.model.ProcessFileTemplate
       * @return A org.semanticwb.process.model.ProcessFileTemplate
       */
        public static org.semanticwb.process.model.ProcessFileTemplate createProcessFileTemplate(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ProcessFileTemplate)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.ProcessFileTemplate
       * @param id Identifier for org.semanticwb.process.model.ProcessFileTemplate
       * @param model Model of the org.semanticwb.process.model.ProcessFileTemplate
       */
        public static void removeProcessFileTemplate(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.ProcessFileTemplate
       * @param id Identifier for org.semanticwb.process.model.ProcessFileTemplate
       * @param model Model of the org.semanticwb.process.model.ProcessFileTemplate
       * @return true if the org.semanticwb.process.model.ProcessFileTemplate exists, false otherwise
       */

        public static boolean hasProcessFileTemplate(String id, org.semanticwb.model.SWBModel model)
        {
            return (getProcessFileTemplate(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessFileTemplate with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.ProcessFileTemplate
       * @return Iterator with all the org.semanticwb.process.model.ProcessFileTemplate
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessFileTemplate> listProcessFileTemplateByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessFileTemplate> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessFileTemplate with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.ProcessFileTemplate
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessFileTemplate> listProcessFileTemplateByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessFileTemplate> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessFileTemplate with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @param model Model of the org.semanticwb.process.model.ProcessFileTemplate
       * @return Iterator with all the org.semanticwb.process.model.ProcessFileTemplate
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessFileTemplate> listProcessFileTemplateByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessFileTemplate> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessFileTemplate with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @return Iterator with all the org.semanticwb.process.model.ProcessFileTemplate
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessFileTemplate> listProcessFileTemplateByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessFileTemplate> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessFileTemplate with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.ProcessFileTemplate
       * @return Iterator with all the org.semanticwb.process.model.ProcessFileTemplate
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessFileTemplate> listProcessFileTemplateByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessFileTemplate> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessFileTemplate with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.ProcessFileTemplate
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessFileTemplate> listProcessFileTemplateByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessFileTemplate> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static ProcessFileTemplateBase.ClassMgr getProcessFileTemplateClassMgr()
    {
        return new ProcessFileTemplateBase.ClassMgr();
    }

   /**
   * Constructs a ProcessFileTemplateBase with a SemanticObject
   * @param base The SemanticObject with the properties for the ProcessFileTemplate
   */
    public ProcessFileTemplateBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the FileName property
* @return String with the FileName
*/
    public String getFileName()
    {
        return getSemanticObject().getProperty(swp_fileName);
    }

/**
* Sets the FileName property
* @param value long with the FileName
*/
    public void setFileName(String value)
    {
        getSemanticObject().setProperty(swp_fileName, value);
    }
}
