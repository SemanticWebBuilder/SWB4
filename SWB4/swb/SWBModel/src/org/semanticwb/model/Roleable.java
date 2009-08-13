package org.semanticwb.model;

public interface Roleable extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass swb_Role=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Role");
    public static final org.semanticwb.platform.SemanticProperty swb_hasRole=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasRole");
    public static final org.semanticwb.platform.SemanticClass swb_Roleable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Roleable");

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.Role> listRoles();
    public boolean hasRole(org.semanticwb.model.Role role);

    public void addRole(org.semanticwb.model.Role role);

    public void removeAllRole();

    public void removeRole(org.semanticwb.model.Role role);

    public Role getRole();
}
