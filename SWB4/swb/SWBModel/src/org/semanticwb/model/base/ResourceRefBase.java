package org.semanticwb.model.base;


   /**
   * Referencia a un objeto de tipo Resource 
   */
public abstract class ResourceRefBase extends org.semanticwb.model.Reference implements org.semanticwb.model.Activeable,org.semanticwb.model.Priorityable
{
   /**
   * Un recurso es un componente en una Página Web con el cual el usuario tiene interacción
   */
    public static final org.semanticwb.platform.SemanticClass swb_Resource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Resource");
    public static final org.semanticwb.platform.SemanticProperty swb_resource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#resource");
   /**
   * Referencia a un objeto de tipo Resource
   */
    public static final org.semanticwb.platform.SemanticClass swb_ResourceRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#ResourceRef");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#ResourceRef");

    public static class ClassMgr
    {
       /**
       * Returns a list of ResourceRef for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.model.ResourceRef
       */

        public static java.util.Iterator<org.semanticwb.model.ResourceRef> listResourceRefs(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceRef>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.ResourceRef for all models
       * @return Iterator of org.semanticwb.model.ResourceRef
       */

        public static java.util.Iterator<org.semanticwb.model.ResourceRef> listResourceRefs()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceRef>(it, true);
        }

        public static org.semanticwb.model.ResourceRef createResourceRef(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.model.ResourceRef.ClassMgr.createResourceRef(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.model.ResourceRef
       * @param id Identifier for org.semanticwb.model.ResourceRef
       * @param model Model of the org.semanticwb.model.ResourceRef
       * @return A org.semanticwb.model.ResourceRef
       */
        public static org.semanticwb.model.ResourceRef getResourceRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.ResourceRef)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.ResourceRef
       * @param id Identifier for org.semanticwb.model.ResourceRef
       * @param model Model of the org.semanticwb.model.ResourceRef
       * @return A org.semanticwb.model.ResourceRef
       */
        public static org.semanticwb.model.ResourceRef createResourceRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.ResourceRef)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.ResourceRef
       * @param id Identifier for org.semanticwb.model.ResourceRef
       * @param model Model of the org.semanticwb.model.ResourceRef
       */
        public static void removeResourceRef(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.ResourceRef
       * @param id Identifier for org.semanticwb.model.ResourceRef
       * @param model Model of the org.semanticwb.model.ResourceRef
       * @return true if the org.semanticwb.model.ResourceRef exists, false otherwise
       */

        public static boolean hasResourceRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (getResourceRef(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.model.ResourceRef with a determined Resource
       * @param value Resource of the type org.semanticwb.model.Resource
       * @param model Model of the org.semanticwb.model.ResourceRef
       * @return Iterator with all the org.semanticwb.model.ResourceRef
       */

        public static java.util.Iterator<org.semanticwb.model.ResourceRef> listResourceRefByResource(org.semanticwb.model.Resource value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceRef> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_resource, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.ResourceRef with a determined Resource
       * @param value Resource of the type org.semanticwb.model.Resource
       * @return Iterator with all the org.semanticwb.model.ResourceRef
       */

        public static java.util.Iterator<org.semanticwb.model.ResourceRef> listResourceRefByResource(org.semanticwb.model.Resource value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceRef> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_resource,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static ResourceRefBase.ClassMgr getResourceRefClassMgr()
    {
        return new ResourceRefBase.ClassMgr();
    }

   /**
   * Constructs a ResourceRefBase with a SemanticObject
   * @param base The SemanticObject with the properties for the ResourceRef
   */
    public ResourceRefBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the Priority property
* @return int with the Priority
*/
    public int getPriority()
    {
        return getSemanticObject().getIntProperty(swb_priority);
    }

/**
* Sets the Priority property
* @param value long with the Priority
*/
    public void setPriority(int value)
    {
        getSemanticObject().setIntProperty(swb_priority, value);
    }
   /**
   * Sets the value for the property Resource
   * @param value Resource to set
   */

    public void setResource(org.semanticwb.model.Resource value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_resource, value.getSemanticObject());
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
        getSemanticObject().removeProperty(swb_resource);
    }

   /**
   * Gets the Resource
   * @return a org.semanticwb.model.Resource
   */
    public org.semanticwb.model.Resource getResource()
    {
         org.semanticwb.model.Resource ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_resource);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Resource)obj.createGenericInstance();
         }
         return ret;
    }

   /**
   * Gets the WebSite
   * @return a instance of org.semanticwb.model.WebSite
   */
    public org.semanticwb.model.WebSite getWebSite()
    {
        return (org.semanticwb.model.WebSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
