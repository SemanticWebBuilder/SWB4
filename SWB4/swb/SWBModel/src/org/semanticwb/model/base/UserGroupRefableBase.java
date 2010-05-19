package org.semanticwb.model.base;

public interface UserGroupRefableBase extends org.semanticwb.model.Referensable
{
    public static final org.semanticwb.platform.SemanticClass swb_UserGroupRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UserGroupRef");
    public static final org.semanticwb.platform.SemanticProperty swb_hasUserGroupRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasUserGroupRef");
    public static final org.semanticwb.platform.SemanticClass swb_UserGroupRefable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UserGroupRefable");

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroupRef> listUserGroupRefs();
    public boolean hasUserGroupRef(org.semanticwb.model.UserGroupRef value);
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroupRef> listInheritUserGroupRefs();

    public void addUserGroupRef(org.semanticwb.model.UserGroupRef value);

    public void removeAllUserGroupRef();

    public void removeUserGroupRef(org.semanticwb.model.UserGroupRef value);

    public org.semanticwb.model.UserGroupRef getUserGroupRef();
}
