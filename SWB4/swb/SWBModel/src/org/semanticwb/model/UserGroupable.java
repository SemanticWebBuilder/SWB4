package org.semanticwb.model;

public interface UserGroupable extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass swb_UserGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UserGroup");
    public static final org.semanticwb.platform.SemanticProperty swb_hasUserGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasUserGroup");
    public static final org.semanticwb.platform.SemanticClass swb_UserGroupable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UserGroupable");

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroup> listUserGroups();
    public boolean hasUserGroup(org.semanticwb.model.UserGroup usergroup);

    public void addUserGroup(org.semanticwb.model.UserGroup usergroup);

    public void removeAllUserGroup();

    public void removeUserGroup(org.semanticwb.model.UserGroup usergroup);

    public UserGroup getUserGroup();
}
