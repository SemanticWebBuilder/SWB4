package org.semanticwb.social.base;


   /**
   * En este objeto se guardara el identificador que es asignado para cada post en cada una de las redes sociales, es decir, si un mismo post se envía hacia mas de una red social, cada una de esas redes sociales daran un identificador unico para ese post en esa red social, este lo tenemos que guardar nosotros en este objeto para fines de monitoreo de estatus del post en esa red social (En Proceso, Revisado, Publicado, etc), como nosotros para un post, independientemente de a cuantas redes sociales se envíe, solo creamos un objeto Post (Message, Photo, Video), tuvimos que crear esta clase para guardar el identificador de ese post para c/red social. En el ID de este objeto se colocara el id de ese post en esa red social. 
   */
public abstract class SocialPostBase extends org.semanticwb.model.SWBClass 
{
   /**
   * Clase a Cambiar despues por "Relacional", esta y todas sus hijas. Clase que comprende todos los tipos de Post que pueden ir siendo creados en la herramienta..
   */
    public static final org.semanticwb.platform.SemanticClass social_Post=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#Post");
   /**
   * Objeto Post al cual se hace referencia.
   */
    public static final org.semanticwb.platform.SemanticProperty social_socialPost=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#socialPost");
   /**
   * Clase que engloba a las diferentes clases que representan cada una de las redes sociales.
   */
    public static final org.semanticwb.platform.SemanticClass social_SocialNetwork=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialNetwork");
   /**
   * Objeto Red Social a la cual se hace referencia.
   */
    public static final org.semanticwb.platform.SemanticProperty social_socialNetwork=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#socialNetwork");
   /**
   * En este objeto se guardara el identificador que es asignado para cada post en cada una de las redes sociales, es decir, si un mismo post se envía hacia mas de una red social, cada una de esas redes sociales daran un identificador unico para ese post en esa red social, este lo tenemos que guardar nosotros en este objeto para fines de monitoreo de estatus del post en esa red social (En Proceso, Revisado, Publicado, etc), como nosotros para un post, independientemente de a cuantas redes sociales se envíe, solo creamos un objeto Post (Message, Photo, Video), tuvimos que crear esta clase para guardar el identificador de ese post para c/red social. En el ID de este objeto se colocara el id de ese post en esa red social.
   */
    public static final org.semanticwb.platform.SemanticClass social_SocialPost=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialPost");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialPost");

    public static class ClassMgr
    {
       /**
       * Returns a list of SocialPost for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.SocialPost
       */

        public static java.util.Iterator<org.semanticwb.social.SocialPost> listSocialPosts(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialPost>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.SocialPost for all models
       * @return Iterator of org.semanticwb.social.SocialPost
       */

        public static java.util.Iterator<org.semanticwb.social.SocialPost> listSocialPosts()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialPost>(it, true);
        }
       /**
       * Gets a org.semanticwb.social.SocialPost
       * @param id Identifier for org.semanticwb.social.SocialPost
       * @param model Model of the org.semanticwb.social.SocialPost
       * @return A org.semanticwb.social.SocialPost
       */
        public static org.semanticwb.social.SocialPost getSocialPost(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.SocialPost)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.SocialPost
       * @param id Identifier for org.semanticwb.social.SocialPost
       * @param model Model of the org.semanticwb.social.SocialPost
       * @return A org.semanticwb.social.SocialPost
       */
        public static org.semanticwb.social.SocialPost createSocialPost(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.SocialPost)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.SocialPost
       * @param id Identifier for org.semanticwb.social.SocialPost
       * @param model Model of the org.semanticwb.social.SocialPost
       */
        public static void removeSocialPost(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.SocialPost
       * @param id Identifier for org.semanticwb.social.SocialPost
       * @param model Model of the org.semanticwb.social.SocialPost
       * @return true if the org.semanticwb.social.SocialPost exists, false otherwise
       */

        public static boolean hasSocialPost(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSocialPost(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.social.SocialPost with a determined SocialPost
       * @param value SocialPost of the type org.semanticwb.social.Post
       * @param model Model of the org.semanticwb.social.SocialPost
       * @return Iterator with all the org.semanticwb.social.SocialPost
       */

        public static java.util.Iterator<org.semanticwb.social.SocialPost> listSocialPostBySocialPost(org.semanticwb.social.Post value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialPost> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_socialPost, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialPost with a determined SocialPost
       * @param value SocialPost of the type org.semanticwb.social.Post
       * @return Iterator with all the org.semanticwb.social.SocialPost
       */

        public static java.util.Iterator<org.semanticwb.social.SocialPost> listSocialPostBySocialPost(org.semanticwb.social.Post value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialPost> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_socialPost,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialPost with a determined SocialNetwork
       * @param value SocialNetwork of the type org.semanticwb.social.SocialNetwork
       * @param model Model of the org.semanticwb.social.SocialPost
       * @return Iterator with all the org.semanticwb.social.SocialPost
       */

        public static java.util.Iterator<org.semanticwb.social.SocialPost> listSocialPostBySocialNetwork(org.semanticwb.social.SocialNetwork value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialPost> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_socialNetwork, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialPost with a determined SocialNetwork
       * @param value SocialNetwork of the type org.semanticwb.social.SocialNetwork
       * @return Iterator with all the org.semanticwb.social.SocialPost
       */

        public static java.util.Iterator<org.semanticwb.social.SocialPost> listSocialPostBySocialNetwork(org.semanticwb.social.SocialNetwork value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialPost> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_socialNetwork,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static SocialPostBase.ClassMgr getSocialPostClassMgr()
    {
        return new SocialPostBase.ClassMgr();
    }

   /**
   * Constructs a SocialPostBase with a SemanticObject
   * @param base The SemanticObject with the properties for the SocialPost
   */
    public SocialPostBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Sets the value for the property SocialPost
   * @param value SocialPost to set
   */

    public void setSocialPost(org.semanticwb.social.Post value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(social_socialPost, value.getSemanticObject());
        }else
        {
            removeSocialPost();
        }
    }
   /**
   * Remove the value for SocialPost property
   */

    public void removeSocialPost()
    {
        getSemanticObject().removeProperty(social_socialPost);
    }

   /**
   * Gets the SocialPost
   * @return a org.semanticwb.social.Post
   */
    public org.semanticwb.social.Post getSocialPost()
    {
         org.semanticwb.social.Post ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_socialPost);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.Post)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property SocialNetwork
   * @param value SocialNetwork to set
   */

    public void setSocialNetwork(org.semanticwb.social.SocialNetwork value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(social_socialNetwork, value.getSemanticObject());
        }else
        {
            removeSocialNetwork();
        }
    }
   /**
   * Remove the value for SocialNetwork property
   */

    public void removeSocialNetwork()
    {
        getSemanticObject().removeProperty(social_socialNetwork);
    }

   /**
   * Gets the SocialNetwork
   * @return a org.semanticwb.social.SocialNetwork
   */
    public org.semanticwb.social.SocialNetwork getSocialNetwork()
    {
         org.semanticwb.social.SocialNetwork ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_socialNetwork);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.SocialNetwork)obj.createGenericInstance();
         }
         return ret;
    }
}
