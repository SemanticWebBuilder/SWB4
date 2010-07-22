package org.semanticwb.model.base;


public abstract class SecurityQuestionBase extends org.semanticwb.model.base.FormElementBase 
{
    public static final org.semanticwb.platform.SemanticClass swbxf_SecurityQuestion=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#SecurityQuestion");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#SecurityQuestion");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.model.SecurityQuestion> listSecurityQuestions(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.SecurityQuestion>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.model.SecurityQuestion> listSecurityQuestions()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.SecurityQuestion>(it, true);
        }

        public static org.semanticwb.model.SecurityQuestion getSecurityQuestion(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.SecurityQuestion)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.model.SecurityQuestion createSecurityQuestion(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.SecurityQuestion)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static void removeSecurityQuestion(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasSecurityQuestion(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSecurityQuestion(id, model)!=null);
        }
    }

    public SecurityQuestionBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
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
