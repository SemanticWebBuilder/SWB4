package org.semanticwb.social.base;


public abstract class PostInBase extends org.semanticwb.social.Post implements org.semanticwb.model.Tagable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable
{
   /**
   * Clase que va ha contener los Post que han sido tomados como base (es decir, que llegan por el listener y que se guardan en la clase PostListenerContainer) para crear un nuevo Post desde la herramienta y que se envía hacia las redes sociales.
   */
    public static final org.semanticwb.platform.SemanticClass social_PostListenerContainerBase=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostListenerContainerBase");
   /**
   * En esta propiedad se guarda el post que llegó por el listener y que sirvió de base para que se creara un post nuevo desde SSMCC.
   */
    public static final org.semanticwb.platform.SemanticProperty social_postListenerBase=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#postListenerBase");
    public static final org.semanticwb.platform.SemanticClass social_PostIn=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostIn");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostIn");

    public static class ClassMgr
    {
       /**
       * Returns a list of PostIn for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.PostIn
       */

        public static java.util.Iterator<org.semanticwb.social.PostIn> listPostIns(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.PostIn>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.PostIn for all models
       * @return Iterator of org.semanticwb.social.PostIn
       */

        public static java.util.Iterator<org.semanticwb.social.PostIn> listPostIns()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.PostIn>(it, true);
        }
       /**
       * Gets a org.semanticwb.social.PostIn
       * @param id Identifier for org.semanticwb.social.PostIn
       * @param model Model of the org.semanticwb.social.PostIn
       * @return A org.semanticwb.social.PostIn
       */
        public static org.semanticwb.social.PostIn getPostIn(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.PostIn)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.PostIn
       * @param id Identifier for org.semanticwb.social.PostIn
       * @param model Model of the org.semanticwb.social.PostIn
       * @return A org.semanticwb.social.PostIn
       */
        public static org.semanticwb.social.PostIn createPostIn(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.PostIn)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.PostIn
       * @param id Identifier for org.semanticwb.social.PostIn
       * @param model Model of the org.semanticwb.social.PostIn
       */
        public static void removePostIn(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.PostIn
       * @param id Identifier for org.semanticwb.social.PostIn
       * @param model Model of the org.semanticwb.social.PostIn
       * @return true if the org.semanticwb.social.PostIn exists, false otherwise
       */

        public static boolean hasPostIn(String id, org.semanticwb.model.SWBModel model)
        {
            return (getPostIn(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.social.PostIn with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.PostIn
       * @return Iterator with all the org.semanticwb.social.PostIn
       */

        public static java.util.Iterator<org.semanticwb.social.PostIn> listPostInByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostIn> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostIn with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.PostIn
       */

        public static java.util.Iterator<org.semanticwb.social.PostIn> listPostInByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostIn> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostIn with a determined PostListenerBase
       * @param value PostListenerBase of the type org.semanticwb.social.PostListenerContainerBase
       * @param model Model of the org.semanticwb.social.PostIn
       * @return Iterator with all the org.semanticwb.social.PostIn
       */

        public static java.util.Iterator<org.semanticwb.social.PostIn> listPostInByPostListenerBase(org.semanticwb.social.PostListenerContainerBase value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostIn> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_postListenerBase, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostIn with a determined PostListenerBase
       * @param value PostListenerBase of the type org.semanticwb.social.PostListenerContainerBase
       * @return Iterator with all the org.semanticwb.social.PostIn
       */

        public static java.util.Iterator<org.semanticwb.social.PostIn> listPostInByPostListenerBase(org.semanticwb.social.PostListenerContainerBase value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostIn> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_postListenerBase,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostIn with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.PostIn
       * @return Iterator with all the org.semanticwb.social.PostIn
       */

        public static java.util.Iterator<org.semanticwb.social.PostIn> listPostInByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostIn> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostIn with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.PostIn
       */

        public static java.util.Iterator<org.semanticwb.social.PostIn> listPostInByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostIn> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

   /**
   * Constructs a PostInBase with a SemanticObject
   * @param base The SemanticObject with the properties for the PostIn
   */
    public PostInBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Sets the value for the property PostListenerBase
   * @param value PostListenerBase to set
   */

    public void setPostListenerBase(org.semanticwb.social.PostListenerContainerBase value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(social_postListenerBase, value.getSemanticObject());
        }else
        {
            removePostListenerBase();
        }
    }
   /**
   * Remove the value for PostListenerBase property
   */

    public void removePostListenerBase()
    {
        getSemanticObject().removeProperty(social_postListenerBase);
    }

   /**
   * Gets the PostListenerBase
   * @return a org.semanticwb.social.PostListenerContainerBase
   */
    public org.semanticwb.social.PostListenerContainerBase getPostListenerBase()
    {
         org.semanticwb.social.PostListenerContainerBase ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_postListenerBase);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.PostListenerContainerBase)obj.createGenericInstance();
         }
         return ret;
    }
}
