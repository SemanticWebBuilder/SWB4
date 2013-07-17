package com.infotec.pic.swb.base;


public abstract class SocialNetworkBase extends com.infotec.pic.swb.Catalog implements org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass pic_SocialNetwork=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#SocialNetwork");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#SocialNetwork");

    public static class ClassMgr
    {
       /**
       * Returns a list of SocialNetwork for a model
       * @param model Model to find
       * @return Iterator of com.infotec.pic.swb.SocialNetwork
       */

        public static java.util.Iterator<com.infotec.pic.swb.SocialNetwork> listSocialNetworks(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.SocialNetwork>(it, true);
        }
       /**
       * Returns a list of com.infotec.pic.swb.SocialNetwork for all models
       * @return Iterator of com.infotec.pic.swb.SocialNetwork
       */

        public static java.util.Iterator<com.infotec.pic.swb.SocialNetwork> listSocialNetworks()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.SocialNetwork>(it, true);
        }

        public static com.infotec.pic.swb.SocialNetwork createSocialNetwork(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return com.infotec.pic.swb.SocialNetwork.ClassMgr.createSocialNetwork(String.valueOf(id), model);
        }
       /**
       * Gets a com.infotec.pic.swb.SocialNetwork
       * @param id Identifier for com.infotec.pic.swb.SocialNetwork
       * @param model Model of the com.infotec.pic.swb.SocialNetwork
       * @return A com.infotec.pic.swb.SocialNetwork
       */
        public static com.infotec.pic.swb.SocialNetwork getSocialNetwork(String id, org.semanticwb.model.SWBModel model)
        {
            return (com.infotec.pic.swb.SocialNetwork)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a com.infotec.pic.swb.SocialNetwork
       * @param id Identifier for com.infotec.pic.swb.SocialNetwork
       * @param model Model of the com.infotec.pic.swb.SocialNetwork
       * @return A com.infotec.pic.swb.SocialNetwork
       */
        public static com.infotec.pic.swb.SocialNetwork createSocialNetwork(String id, org.semanticwb.model.SWBModel model)
        {
            return (com.infotec.pic.swb.SocialNetwork)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a com.infotec.pic.swb.SocialNetwork
       * @param id Identifier for com.infotec.pic.swb.SocialNetwork
       * @param model Model of the com.infotec.pic.swb.SocialNetwork
       */
        public static void removeSocialNetwork(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a com.infotec.pic.swb.SocialNetwork
       * @param id Identifier for com.infotec.pic.swb.SocialNetwork
       * @param model Model of the com.infotec.pic.swb.SocialNetwork
       * @return true if the com.infotec.pic.swb.SocialNetwork exists, false otherwise
       */

        public static boolean hasSocialNetwork(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSocialNetwork(id, model)!=null);
        }
       /**
       * Gets all com.infotec.pic.swb.SocialNetwork with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the com.infotec.pic.swb.SocialNetwork
       * @return Iterator with all the com.infotec.pic.swb.SocialNetwork
       */

        public static java.util.Iterator<com.infotec.pic.swb.SocialNetwork> listSocialNetworkByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.SocialNetwork> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.SocialNetwork with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the com.infotec.pic.swb.SocialNetwork
       */

        public static java.util.Iterator<com.infotec.pic.swb.SocialNetwork> listSocialNetworkByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.SocialNetwork> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.SocialNetwork with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the com.infotec.pic.swb.SocialNetwork
       * @return Iterator with all the com.infotec.pic.swb.SocialNetwork
       */

        public static java.util.Iterator<com.infotec.pic.swb.SocialNetwork> listSocialNetworkByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.SocialNetwork> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.SocialNetwork with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the com.infotec.pic.swb.SocialNetwork
       */

        public static java.util.Iterator<com.infotec.pic.swb.SocialNetwork> listSocialNetworkByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.SocialNetwork> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static SocialNetworkBase.ClassMgr getSocialNetworkClassMgr()
    {
        return new SocialNetworkBase.ClassMgr();
    }

   /**
   * Constructs a SocialNetworkBase with a SemanticObject
   * @param base The SemanticObject with the properties for the SocialNetwork
   */
    public SocialNetworkBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
