package org.semanticwb.model.base;


public abstract class LoginElementBase extends org.semanticwb.model.Text 
{
    public static final org.semanticwb.platform.SemanticClass swbxf_LoginElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#LoginElement");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#LoginElement");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.model.LoginElement> listLoginElements(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.LoginElement>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.model.LoginElement> listLoginElements()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.LoginElement>(it, true);
        }

        public static org.semanticwb.model.LoginElement getLoginElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.LoginElement)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.model.LoginElement createLoginElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.LoginElement)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static void removeLoginElement(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasLoginElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (getLoginElement(id, model)!=null);
        }
    }

    public LoginElementBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
