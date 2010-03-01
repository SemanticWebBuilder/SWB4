package org.semanticwb.portal.community.base;

public interface InteractiveableBase extends org.semanticwb.model.Rankable
{
    public static final org.semanticwb.platform.SemanticProperty swbcomm_abused=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#abused");
    public static final org.semanticwb.platform.SemanticClass swbcomm_Comment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#Comment");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_hasComment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#hasComment");
    public static final org.semanticwb.platform.SemanticClass swbcomm_Interactiveable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#Interactiveable");

    public int getAbused();

    public void setAbused(int value);

    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Comment> listComments();
    public boolean hasComment(org.semanticwb.portal.community.Comment comment);

    public void addComment(org.semanticwb.portal.community.Comment comment);

    public void removeAllComment();

    public void removeComment(org.semanticwb.portal.community.Comment comment);

    public org.semanticwb.portal.community.Comment getComment();
}
