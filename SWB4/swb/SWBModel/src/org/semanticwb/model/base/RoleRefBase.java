package org.semanticwb.model.base;


public class RoleRefBase extends org.semanticwb.model.Reference implements org.semanticwb.model.Activeable
{
    public static final org.semanticwb.platform.SemanticClass swb_Role=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Role");
    public static final org.semanticwb.platform.SemanticProperty swb_role=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#role");
    public static final org.semanticwb.platform.SemanticClass swb_RoleRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#RoleRef");


    public static org.semanticwb.model.RoleRef createRoleRef(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.model.RoleRef)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, swb_RoleRef), swb_RoleRef);
    }

    public RoleRefBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void setRole(org.semanticwb.model.Role role)
    {
        getSemanticObject().setObjectProperty(swb_role, role.getSemanticObject());
    }

    public void removeRole()
    {
        getSemanticObject().removeProperty(swb_role);
    }

    public org.semanticwb.model.Role getRole()
    {
         org.semanticwb.model.Role ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_role);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Role)obj.getSemanticClass().newGenericInstance(obj);
         }
         return ret;
    }

    public org.semanticwb.model.WebSite getWebSite()
    {
        return new org.semanticwb.model.WebSite(getSemanticObject().getModel().getModelObject());
    }
}
