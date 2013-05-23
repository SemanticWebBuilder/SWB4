package org.semanticwb.social.base;


   /**
   * Clase en la que se guardan datos que sirven para realizar una siguiente busqueda en una determinada red social y en un determinado stream. 
   */
public abstract class SocialNetStreamSearchBase extends org.semanticwb.model.SWBClass 
{
   /**
   * Clase que engloba a las diferentes clases que representan cada una de las redes sociales.
   */
    public static final org.semanticwb.platform.SemanticClass social_SocialNetwork=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialNetwork");
   /**
   * SocialNetwork al que se hace referencia en esta clase y en la cual se ira a buscar despues del dato que se encuentre en nextDatetoSearch
   */
    public static final org.semanticwb.platform.SemanticProperty social_snss_SocialNetwork=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#snss_SocialNetwork");
   /**
   * Fecha o id siguiente para busqueda en una red social.
   */
    public static final org.semanticwb.platform.SemanticProperty social_nextDatetoSearch=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#nextDatetoSearch");
   /**
   * Clase que contendra los streams que configurados para cada usuario
   */
    public static final org.semanticwb.platform.SemanticClass social_Stream=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#Stream");
   /**
   * Stream específico en el que se guarda la siguiente fecha a buscar en sus redes sociales
   */
    public static final org.semanticwb.platform.SemanticProperty social_snss_Stream=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#snss_Stream");
   /**
   * Clase en la que se guardan datos que sirven para realizar una siguiente busqueda en una determinada red social y en un determinado stream.
   */
    public static final org.semanticwb.platform.SemanticClass social_SocialNetStreamSearch=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialNetStreamSearch");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialNetStreamSearch");

    public static class ClassMgr
    {
       /**
       * Returns a list of SocialNetStreamSearch for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.SocialNetStreamSearch
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetStreamSearch> listSocialNetStreamSearches(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetStreamSearch>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.SocialNetStreamSearch for all models
       * @return Iterator of org.semanticwb.social.SocialNetStreamSearch
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetStreamSearch> listSocialNetStreamSearches()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetStreamSearch>(it, true);
        }

        public static org.semanticwb.social.SocialNetStreamSearch createSocialNetStreamSearch(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.social.SocialNetStreamSearch.ClassMgr.createSocialNetStreamSearch(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.social.SocialNetStreamSearch
       * @param id Identifier for org.semanticwb.social.SocialNetStreamSearch
       * @param model Model of the org.semanticwb.social.SocialNetStreamSearch
       * @return A org.semanticwb.social.SocialNetStreamSearch
       */
        public static org.semanticwb.social.SocialNetStreamSearch getSocialNetStreamSearch(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.SocialNetStreamSearch)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.SocialNetStreamSearch
       * @param id Identifier for org.semanticwb.social.SocialNetStreamSearch
       * @param model Model of the org.semanticwb.social.SocialNetStreamSearch
       * @return A org.semanticwb.social.SocialNetStreamSearch
       */
        public static org.semanticwb.social.SocialNetStreamSearch createSocialNetStreamSearch(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.SocialNetStreamSearch)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.SocialNetStreamSearch
       * @param id Identifier for org.semanticwb.social.SocialNetStreamSearch
       * @param model Model of the org.semanticwb.social.SocialNetStreamSearch
       */
        public static void removeSocialNetStreamSearch(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.SocialNetStreamSearch
       * @param id Identifier for org.semanticwb.social.SocialNetStreamSearch
       * @param model Model of the org.semanticwb.social.SocialNetStreamSearch
       * @return true if the org.semanticwb.social.SocialNetStreamSearch exists, false otherwise
       */

        public static boolean hasSocialNetStreamSearch(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSocialNetStreamSearch(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.social.SocialNetStreamSearch with a determined SocialNetwork
       * @param value SocialNetwork of the type org.semanticwb.social.SocialNetwork
       * @param model Model of the org.semanticwb.social.SocialNetStreamSearch
       * @return Iterator with all the org.semanticwb.social.SocialNetStreamSearch
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetStreamSearch> listSocialNetStreamSearchBySocialNetwork(org.semanticwb.social.SocialNetwork value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetStreamSearch> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_snss_SocialNetwork, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialNetStreamSearch with a determined SocialNetwork
       * @param value SocialNetwork of the type org.semanticwb.social.SocialNetwork
       * @return Iterator with all the org.semanticwb.social.SocialNetStreamSearch
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetStreamSearch> listSocialNetStreamSearchBySocialNetwork(org.semanticwb.social.SocialNetwork value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetStreamSearch> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_snss_SocialNetwork,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialNetStreamSearch with a determined Stream
       * @param value Stream of the type org.semanticwb.social.Stream
       * @param model Model of the org.semanticwb.social.SocialNetStreamSearch
       * @return Iterator with all the org.semanticwb.social.SocialNetStreamSearch
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetStreamSearch> listSocialNetStreamSearchByStream(org.semanticwb.social.Stream value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetStreamSearch> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_snss_Stream, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialNetStreamSearch with a determined Stream
       * @param value Stream of the type org.semanticwb.social.Stream
       * @return Iterator with all the org.semanticwb.social.SocialNetStreamSearch
       */

        public static java.util.Iterator<org.semanticwb.social.SocialNetStreamSearch> listSocialNetStreamSearchByStream(org.semanticwb.social.Stream value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialNetStreamSearch> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_snss_Stream,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static SocialNetStreamSearchBase.ClassMgr getSocialNetStreamSearchClassMgr()
    {
        return new SocialNetStreamSearchBase.ClassMgr();
    }

   /**
   * Constructs a SocialNetStreamSearchBase with a SemanticObject
   * @param base The SemanticObject with the properties for the SocialNetStreamSearch
   */
    public SocialNetStreamSearchBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Sets the value for the property SocialNetwork
   * @param value SocialNetwork to set
   */

    public void setSocialNetwork(org.semanticwb.social.SocialNetwork value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(social_snss_SocialNetwork, value.getSemanticObject());
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
        getSemanticObject().removeProperty(social_snss_SocialNetwork);
    }

   /**
   * Gets the SocialNetwork
   * @return a org.semanticwb.social.SocialNetwork
   */
    public org.semanticwb.social.SocialNetwork getSocialNetwork()
    {
         org.semanticwb.social.SocialNetwork ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_snss_SocialNetwork);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.SocialNetwork)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the NextDatetoSearch property
* @return String with the NextDatetoSearch
*/
    public String getNextDatetoSearch()
    {
        return getSemanticObject().getProperty(social_nextDatetoSearch);
    }

/**
* Sets the NextDatetoSearch property
* @param value long with the NextDatetoSearch
*/
    public void setNextDatetoSearch(String value)
    {
        getSemanticObject().setProperty(social_nextDatetoSearch, value);
    }
   /**
   * Sets the value for the property Stream
   * @param value Stream to set
   */

    public void setStream(org.semanticwb.social.Stream value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(social_snss_Stream, value.getSemanticObject());
        }else
        {
            removeStream();
        }
    }
   /**
   * Remove the value for Stream property
   */

    public void removeStream()
    {
        getSemanticObject().removeProperty(social_snss_Stream);
    }

   /**
   * Gets the Stream
   * @return a org.semanticwb.social.Stream
   */
    public org.semanticwb.social.Stream getStream()
    {
         org.semanticwb.social.Stream ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_snss_Stream);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.Stream)obj.createGenericInstance();
         }
         return ret;
    }
}
