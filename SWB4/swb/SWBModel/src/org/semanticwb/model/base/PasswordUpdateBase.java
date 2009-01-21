package org.semanticwb.model.base;


public class PasswordUpdateBase extends org.semanticwb.model.base.FormElementBase 
{
    public static final org.semanticwb.platform.SemanticProperty swb_passUpdVerify=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#passUpdVerify");
    public static final org.semanticwb.platform.SemanticClass swbxf_PasswordUpdate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#PasswordUpdate");


    public static org.semanticwb.model.PasswordUpdate createPasswordUpdate(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.model.PasswordUpdate)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, swbxf_PasswordUpdate), swbxf_PasswordUpdate);
    }

    public PasswordUpdateBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public String getVerifyText()
    {
        return getSemanticObject().getProperty(swb_passUpdVerify);
    }

    public void setVerifyText(String passUpdVerify)
    {
        getSemanticObject().setProperty(swb_passUpdVerify, passUpdVerify);
    }

    public String getVerifyText(String lang)
    {
        return getSemanticObject().getProperty(swb_passUpdVerify, null, lang);
    }

    public String getDisplayVerifyText(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_passUpdVerify, lang);
    }

    public void setVerifyText(String passUpdVerify, String lang)
    {
        getSemanticObject().setProperty(swb_passUpdVerify, passUpdVerify, lang);
    }

    public void remove()
    {
        getSemanticObject().remove();
    }

    public java.util.Iterator<org.semanticwb.model.GenericObject> listRelatedObjects()
    {
        return new org.semanticwb.model.GenericIterator((org.semanticwb.platform.SemanticClass)null, getSemanticObject().listRelatedObjects(),true);
    }
}
