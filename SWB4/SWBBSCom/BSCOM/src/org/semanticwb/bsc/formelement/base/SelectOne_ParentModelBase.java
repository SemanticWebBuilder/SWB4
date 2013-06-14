package org.semanticwb.bsc.formelement.base;


   /**
   * Muestra un select con las instancias pertenecientes al BSC padre, de la clase definida en este elemento 
   */
public abstract class SelectOne_ParentModelBase extends org.semanticwb.model.SelectOne 
{
   /**
   * Define el nombre de la clase para la que se extraerán las instancias del BSC padre
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_classnameToUse=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#classnameToUse");
   /**
   * Muestra un select con las instancias pertenecientes al BSC padre, de la clase definida en este elemento
   */
    public static final org.semanticwb.platform.SemanticClass bsc_SelectOne_ParentModel=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#SelectOne_ParentModel");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#SelectOne_ParentModel");

    public static class ClassMgr
    {
       /**
       * Returns a list of SelectOne_ParentModel for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.formelement.SelectOne_ParentModel
       */

        public static java.util.Iterator<org.semanticwb.bsc.formelement.SelectOne_ParentModel> listSelectOne_ParentModels(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.formelement.SelectOne_ParentModel>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.formelement.SelectOne_ParentModel for all models
       * @return Iterator of org.semanticwb.bsc.formelement.SelectOne_ParentModel
       */

        public static java.util.Iterator<org.semanticwb.bsc.formelement.SelectOne_ParentModel> listSelectOne_ParentModels()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.formelement.SelectOne_ParentModel>(it, true);
        }
       /**
       * Gets a org.semanticwb.bsc.formelement.SelectOne_ParentModel
       * @param id Identifier for org.semanticwb.bsc.formelement.SelectOne_ParentModel
       * @param model Model of the org.semanticwb.bsc.formelement.SelectOne_ParentModel
       * @return A org.semanticwb.bsc.formelement.SelectOne_ParentModel
       */
        public static org.semanticwb.bsc.formelement.SelectOne_ParentModel getSelectOne_ParentModel(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.formelement.SelectOne_ParentModel)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.formelement.SelectOne_ParentModel
       * @param id Identifier for org.semanticwb.bsc.formelement.SelectOne_ParentModel
       * @param model Model of the org.semanticwb.bsc.formelement.SelectOne_ParentModel
       * @return A org.semanticwb.bsc.formelement.SelectOne_ParentModel
       */
        public static org.semanticwb.bsc.formelement.SelectOne_ParentModel createSelectOne_ParentModel(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.formelement.SelectOne_ParentModel)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.formelement.SelectOne_ParentModel
       * @param id Identifier for org.semanticwb.bsc.formelement.SelectOne_ParentModel
       * @param model Model of the org.semanticwb.bsc.formelement.SelectOne_ParentModel
       */
        public static void removeSelectOne_ParentModel(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.formelement.SelectOne_ParentModel
       * @param id Identifier for org.semanticwb.bsc.formelement.SelectOne_ParentModel
       * @param model Model of the org.semanticwb.bsc.formelement.SelectOne_ParentModel
       * @return true if the org.semanticwb.bsc.formelement.SelectOne_ParentModel exists, false otherwise
       */

        public static boolean hasSelectOne_ParentModel(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSelectOne_ParentModel(id, model)!=null);
        }
    }

    public static SelectOne_ParentModelBase.ClassMgr getSelectOne_ParentModelClassMgr()
    {
        return new SelectOne_ParentModelBase.ClassMgr();
    }

   /**
   * Constructs a SelectOne_ParentModelBase with a SemanticObject
   * @param base The SemanticObject with the properties for the SelectOne_ParentModel
   */
    public SelectOne_ParentModelBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the ClassnameToUse property
* @return String with the ClassnameToUse
*/
    public String getClassnameToUse()
    {
        return getSemanticObject().getProperty(bsc_classnameToUse);
    }

/**
* Sets the ClassnameToUse property
* @param value long with the ClassnameToUse
*/
    public void setClassnameToUse(String value)
    {
        getSemanticObject().setProperty(bsc_classnameToUse, value);
    }
}
