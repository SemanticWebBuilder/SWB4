package org.semanticwb.model.base;


public abstract class UserTypeDefBase extends org.semanticwb.model.SWBClass 
{
    public static final org.semanticwb.platform.SemanticClass swb_UserTypeDef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UserTypeDef");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UserTypeDef");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.model.UserTypeDef> listUserTypeDefs(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.UserTypeDef>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.model.UserTypeDef> listUserTypeDefs()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.UserTypeDef>(it, true);
        }

        public static org.semanticwb.model.UserTypeDef getUserTypeDef(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.UserTypeDef)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.model.UserTypeDef createUserTypeDef(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.UserTypeDef)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static void removeUserTypeDef(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasUserTypeDef(String id, org.semanticwb.model.SWBModel model)
        {
            return (getUserTypeDef(id, model)!=null);
        }
    }

    public UserTypeDefBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
