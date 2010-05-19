package org.semanticwb.model.base;

public interface RoleRefableBase extends org.semanticwb.model.Referensable
{
    public static final org.semanticwb.platform.SemanticClass swb_RoleRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#RoleRef");
    public static final org.semanticwb.platform.SemanticProperty swb_hasRoleRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasRoleRef");
    public static final org.semanticwb.platform.SemanticClass swb_RoleRefable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#RoleRefable");

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.RoleRef> listRoleRefs();
    public boolean hasRoleRef(org.semanticwb.model.RoleRef value);
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.RoleRef> listInheritRoleRefs();

    public void addRoleRef(org.semanticwb.model.RoleRef value);

    public void removeAllRoleRef();

    public void removeRoleRef(org.semanticwb.model.RoleRef value);

    public org.semanticwb.model.RoleRef getRoleRef();
}
