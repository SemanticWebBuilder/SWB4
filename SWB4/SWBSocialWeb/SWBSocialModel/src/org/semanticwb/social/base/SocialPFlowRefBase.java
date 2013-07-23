package org.semanticwb.social.base;


   /**
   * Referencia a un objeto de tipo PFlow 
   */
public abstract class SocialPFlowRefBase extends org.semanticwb.model.Reference implements org.semanticwb.model.Activeable,org.semanticwb.social.SocialPFlowable
{
   /**
   * Un Flujo de Publicación es una serie de autorizaciones por las que pasa un contenido antes de publicarse en un Sitio Web
   */
    public static final org.semanticwb.platform.SemanticClass social_SocialPFlow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialPFlow");
    public static final org.semanticwb.platform.SemanticProperty social_pflow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#pflow");
   /**
   * Referencia a un objeto de tipo PFlow
   */
    public static final org.semanticwb.platform.SemanticClass social_SocialPFlowRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialPFlowRef");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialPFlowRef");

    public static class ClassMgr
    {
       /**
       * Returns a list of SocialPFlowRef for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.SocialPFlowRef
       */

        public static java.util.Iterator<org.semanticwb.social.SocialPFlowRef> listSocialPFlowRefs(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialPFlowRef>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.SocialPFlowRef for all models
       * @return Iterator of org.semanticwb.social.SocialPFlowRef
       */

        public static java.util.Iterator<org.semanticwb.social.SocialPFlowRef> listSocialPFlowRefs()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialPFlowRef>(it, true);
        }

        public static org.semanticwb.social.SocialPFlowRef createSocialPFlowRef(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.social.SocialPFlowRef.ClassMgr.createSocialPFlowRef(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.social.SocialPFlowRef
       * @param id Identifier for org.semanticwb.social.SocialPFlowRef
       * @param model Model of the org.semanticwb.social.SocialPFlowRef
       * @return A org.semanticwb.social.SocialPFlowRef
       */
        public static org.semanticwb.social.SocialPFlowRef getSocialPFlowRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.SocialPFlowRef)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.SocialPFlowRef
       * @param id Identifier for org.semanticwb.social.SocialPFlowRef
       * @param model Model of the org.semanticwb.social.SocialPFlowRef
       * @return A org.semanticwb.social.SocialPFlowRef
       */
        public static org.semanticwb.social.SocialPFlowRef createSocialPFlowRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.SocialPFlowRef)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.SocialPFlowRef
       * @param id Identifier for org.semanticwb.social.SocialPFlowRef
       * @param model Model of the org.semanticwb.social.SocialPFlowRef
       */
        public static void removeSocialPFlowRef(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.SocialPFlowRef
       * @param id Identifier for org.semanticwb.social.SocialPFlowRef
       * @param model Model of the org.semanticwb.social.SocialPFlowRef
       * @return true if the org.semanticwb.social.SocialPFlowRef exists, false otherwise
       */

        public static boolean hasSocialPFlowRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSocialPFlowRef(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.social.SocialPFlowRef with a determined Pflow
       * @param value Pflow of the type org.semanticwb.social.SocialPFlow
       * @param model Model of the org.semanticwb.social.SocialPFlowRef
       * @return Iterator with all the org.semanticwb.social.SocialPFlowRef
       */

        public static java.util.Iterator<org.semanticwb.social.SocialPFlowRef> listSocialPFlowRefByPflow(org.semanticwb.social.SocialPFlow value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialPFlowRef> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_pflow, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialPFlowRef with a determined Pflow
       * @param value Pflow of the type org.semanticwb.social.SocialPFlow
       * @return Iterator with all the org.semanticwb.social.SocialPFlowRef
       */

        public static java.util.Iterator<org.semanticwb.social.SocialPFlowRef> listSocialPFlowRefByPflow(org.semanticwb.social.SocialPFlow value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialPFlowRef> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_pflow,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static SocialPFlowRefBase.ClassMgr getSocialPFlowRefClassMgr()
    {
        return new SocialPFlowRefBase.ClassMgr();
    }

   /**
   * Constructs a SocialPFlowRefBase with a SemanticObject
   * @param base The SemanticObject with the properties for the SocialPFlowRef
   */
    public SocialPFlowRefBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Sets the value for the property Pflow
   * @param value Pflow to set
   */

    public void setPflow(org.semanticwb.social.SocialPFlow value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(social_pflow, value.getSemanticObject());
        }else
        {
            removePflow();
        }
    }
   /**
   * Remove the value for Pflow property
   */

    public void removePflow()
    {
        getSemanticObject().removeProperty(social_pflow);
    }

   /**
   * Gets the Pflow
   * @return a org.semanticwb.social.SocialPFlow
   */
    public org.semanticwb.social.SocialPFlow getPflow()
    {
         org.semanticwb.social.SocialPFlow ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_pflow);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.SocialPFlow)obj.createGenericInstance();
         }
         return ret;
    }

   /**
   * Gets the SocialSite
   * @return a instance of org.semanticwb.social.SocialSite
   */
    public org.semanticwb.social.SocialSite getSocialSite()
    {
        return (org.semanticwb.social.SocialSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
