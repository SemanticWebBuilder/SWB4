package org.semanticwb.model.base;


   /**
   * Superclase de todos los tipos de Modelos de SemanticWebBuilder 
   */
public abstract class SWBModelBase extends org.semanticwb.model.base.GenericObjectBase 
{
   /**
   * Objeto que define un Sitio Web
   */
    public static final org.semanticwb.platform.SemanticClass swb_WebSite=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebSite");
    public static final org.semanticwb.platform.SemanticProperty swb_parentWebSite=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#parentWebSite");
   /**
   * Superclase de todos los tipos de Modelos de SemanticWebBuilder
   */
    public static final org.semanticwb.platform.SemanticClass swb_SWBModel=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#SWBModel");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#SWBModel");

    public static class ClassMgr
    {
       /**
       * Returns a list of SWBModel for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.model.SWBModel
       */

        public static java.util.Iterator<org.semanticwb.model.SWBModel> listSWBModels(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.SWBModel>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.SWBModel for all models
       * @return Iterator of org.semanticwb.model.SWBModel
       */

        public static java.util.Iterator<org.semanticwb.model.SWBModel> listSWBModels()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.SWBModel>(it, true);
        }
       /**
       * Gets a org.semanticwb.model.SWBModel
       * @param id Identifier for org.semanticwb.model.SWBModel
       * @param model Model of the org.semanticwb.model.SWBModel
       * @return A org.semanticwb.model.SWBModel
       */
        public static org.semanticwb.model.SWBModel getSWBModel(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.SWBModel)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.SWBModel
       * @param id Identifier for org.semanticwb.model.SWBModel
       * @param model Model of the org.semanticwb.model.SWBModel
       * @return A org.semanticwb.model.SWBModel
       */
        public static org.semanticwb.model.SWBModel createSWBModel(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.SWBModel)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.SWBModel
       * @param id Identifier for org.semanticwb.model.SWBModel
       * @param model Model of the org.semanticwb.model.SWBModel
       */
        public static void removeSWBModel(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.SWBModel
       * @param id Identifier for org.semanticwb.model.SWBModel
       * @param model Model of the org.semanticwb.model.SWBModel
       * @return true if the org.semanticwb.model.SWBModel exists, false otherwise
       */

        public static boolean hasSWBModel(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSWBModel(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.model.SWBModel with a determined ParentWebSite
       * @param value ParentWebSite of the type org.semanticwb.model.WebSite
       * @param model Model of the org.semanticwb.model.SWBModel
       * @return Iterator with all the org.semanticwb.model.SWBModel
       */

        public static java.util.Iterator<org.semanticwb.model.SWBModel> listSWBModelByParentWebSite(org.semanticwb.model.WebSite value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.SWBModel> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_parentWebSite, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.SWBModel with a determined ParentWebSite
       * @param value ParentWebSite of the type org.semanticwb.model.WebSite
       * @return Iterator with all the org.semanticwb.model.SWBModel
       */

        public static java.util.Iterator<org.semanticwb.model.SWBModel> listSWBModelByParentWebSite(org.semanticwb.model.WebSite value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.SWBModel> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_parentWebSite,value.getSemanticObject(),sclass));
            return it;
        }
    }

   /**
   * Constructs a SWBModelBase with a SemanticObject
   * @param base The SemanticObject with the properties for the SWBModel
   */
    public SWBModelBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Sets the value for the property ParentWebSite
   * @param value ParentWebSite to set
   */

    public void setParentWebSite(org.semanticwb.model.WebSite value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_parentWebSite, value.getSemanticObject());
        }else
        {
            removeParentWebSite();
        }
    }
   /**
   * Remove the value for ParentWebSite property
   */

    public void removeParentWebSite()
    {
        getSemanticObject().removeProperty(swb_parentWebSite);
    }

   /**
   * Gets the ParentWebSite
   * @return a org.semanticwb.model.WebSite
   */
    public org.semanticwb.model.WebSite getParentWebSite()
    {
         org.semanticwb.model.WebSite ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_parentWebSite);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.WebSite)obj.createGenericInstance();
         }
         return ret;
    }

    public void remove()
    {
        getSemanticObject().remove();
    }

    public java.util.Iterator<org.semanticwb.model.GenericObject> listRelatedObjects()
    {
        return new org.semanticwb.model.GenericIterator(getSemanticObject().listRelatedObjects(),true);
    }
}
