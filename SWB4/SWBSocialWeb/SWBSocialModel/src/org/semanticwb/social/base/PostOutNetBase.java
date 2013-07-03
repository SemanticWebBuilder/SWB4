package org.semanticwb.social.base;


   /**
   * En este objeto se guardara el identificador que es asignado para cada post en cada una de las redes sociales, es decir, si un mismo post se envía hacia mas de una red social, cada una de esas redes sociales daran un identificador unico para ese post en esa red social, este lo tenemos que guardar nosotros en este objeto para fines de monitoreo de estatus del post en esa red social (En Proceso, Revisado, Publicado, etc), como nosotros para un post, independientemente de a cuantas redes sociales se envíe, solo creamos un objeto PostOut (Message, Photo, Video), tuvimos que crear esta clase para guardar el identificador de ese postOut para c/red social. 
   */
public abstract class PostOutNetBase extends org.semanticwb.model.SWBClass 
{
   /**
   * Identificador del mensaje en la red social
   */
    public static final org.semanticwb.platform.SemanticProperty social_socialNetMsgID=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#socialNetMsgID");
   /**
   * Clase que comprende todos los tipos de Post de Salida que pueden ir siendo creados en la herramienta y que seran publicados a partir de esto en las diferentes redes sociales. Esta clase no se relaciona con una red social (con la clase SocialNetwork) porque un post de salida (desde la herramienta) podría ser enviado a diferentes redes sociales, sin embargo, es el mismo post de salida. Donde esta a que red social se envía esta en las instancias de la clase PostContainer.
   */
    public static final org.semanticwb.platform.SemanticClass social_PostOut=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostOut");
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
   * En este objeto se guardara el identificador que es asignado para cada post en cada una de las redes sociales, es decir, si un mismo post se envía hacia mas de una red social, cada una de esas redes sociales daran un identificador unico para ese post en esa red social, este lo tenemos que guardar nosotros en este objeto para fines de monitoreo de estatus del post en esa red social (En Proceso, Revisado, Publicado, etc), como nosotros para un post, independientemente de a cuantas redes sociales se envíe, solo creamos un objeto PostOut (Message, Photo, Video), tuvimos que crear esta clase para guardar el identificador de ese postOut para c/red social.
   */
    public static final org.semanticwb.platform.SemanticClass social_PostOutNet=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostOutNet");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostOutNet");

    public static class ClassMgr
    {
       /**
       * Returns a list of PostOutNet for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.PostOutNet
       */

        public static java.util.Iterator<org.semanticwb.social.PostOutNet> listPostOutNets(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutNet>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.PostOutNet for all models
       * @return Iterator of org.semanticwb.social.PostOutNet
       */

        public static java.util.Iterator<org.semanticwb.social.PostOutNet> listPostOutNets()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutNet>(it, true);
        }

        public static org.semanticwb.social.PostOutNet createPostOutNet(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.social.PostOutNet.ClassMgr.createPostOutNet(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.social.PostOutNet
       * @param id Identifier for org.semanticwb.social.PostOutNet
       * @param model Model of the org.semanticwb.social.PostOutNet
       * @return A org.semanticwb.social.PostOutNet
       */
        public static org.semanticwb.social.PostOutNet getPostOutNet(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.PostOutNet)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.PostOutNet
       * @param id Identifier for org.semanticwb.social.PostOutNet
       * @param model Model of the org.semanticwb.social.PostOutNet
       * @return A org.semanticwb.social.PostOutNet
       */
        public static org.semanticwb.social.PostOutNet createPostOutNet(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.PostOutNet)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.PostOutNet
       * @param id Identifier for org.semanticwb.social.PostOutNet
       * @param model Model of the org.semanticwb.social.PostOutNet
       */
        public static void removePostOutNet(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.PostOutNet
       * @param id Identifier for org.semanticwb.social.PostOutNet
       * @param model Model of the org.semanticwb.social.PostOutNet
       * @return true if the org.semanticwb.social.PostOutNet exists, false otherwise
       */

        public static boolean hasPostOutNet(String id, org.semanticwb.model.SWBModel model)
        {
            return (getPostOutNet(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.social.PostOutNet with a determined SocialPost
       * @param value SocialPost of the type org.semanticwb.social.PostOut
       * @param model Model of the org.semanticwb.social.PostOutNet
       * @return Iterator with all the org.semanticwb.social.PostOutNet
       */

        public static java.util.Iterator<org.semanticwb.social.PostOutNet> listPostOutNetBySocialPost(org.semanticwb.social.PostOut value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutNet> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_socialPost, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostOutNet with a determined SocialPost
       * @param value SocialPost of the type org.semanticwb.social.PostOut
       * @return Iterator with all the org.semanticwb.social.PostOutNet
       */

        public static java.util.Iterator<org.semanticwb.social.PostOutNet> listPostOutNetBySocialPost(org.semanticwb.social.PostOut value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutNet> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_socialPost,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostOutNet with a determined SocialNetwork
       * @param value SocialNetwork of the type org.semanticwb.social.SocialNetwork
       * @param model Model of the org.semanticwb.social.PostOutNet
       * @return Iterator with all the org.semanticwb.social.PostOutNet
       */

        public static java.util.Iterator<org.semanticwb.social.PostOutNet> listPostOutNetBySocialNetwork(org.semanticwb.social.SocialNetwork value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutNet> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_socialNetwork, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.PostOutNet with a determined SocialNetwork
       * @param value SocialNetwork of the type org.semanticwb.social.SocialNetwork
       * @return Iterator with all the org.semanticwb.social.PostOutNet
       */

        public static java.util.Iterator<org.semanticwb.social.PostOutNet> listPostOutNetBySocialNetwork(org.semanticwb.social.SocialNetwork value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOutNet> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_socialNetwork,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static PostOutNetBase.ClassMgr getPostOutNetClassMgr()
    {
        return new PostOutNetBase.ClassMgr();
    }

   /**
   * Constructs a PostOutNetBase with a SemanticObject
   * @param base The SemanticObject with the properties for the PostOutNet
   */
    public PostOutNetBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the SocialNetMsgID property
* @return String with the SocialNetMsgID
*/
    public String getSocialNetMsgID()
    {
        return getSemanticObject().getProperty(social_socialNetMsgID);
    }

/**
* Sets the SocialNetMsgID property
* @param value long with the SocialNetMsgID
*/
    public void setSocialNetMsgID(String value)
    {
        getSemanticObject().setProperty(social_socialNetMsgID, value);
    }
   /**
   * Sets the value for the property SocialPost
   * @param value SocialPost to set
   */

    public void setSocialPost(org.semanticwb.social.PostOut value)
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
   * @return a org.semanticwb.social.PostOut
   */
    public org.semanticwb.social.PostOut getSocialPost()
    {
         org.semanticwb.social.PostOut ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_socialPost);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.PostOut)obj.createGenericInstance();
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
