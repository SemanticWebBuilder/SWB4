package org.semanticwb.social.base;

   /**
   * Interface para las clases que apareceran en el árbol y que las instancias de esas clases podran tener a su vez instancias de las mismas 
   */
public interface ChildrenableBase extends org.semanticwb.model.GenericObject
{
   /**
   * Interface para las clases que apareceran en el árbol y que las instancias de esas clases podran tener a su vez instancias de las mismas 
   */
    public static final org.semanticwb.platform.SemanticClass social_Childrenable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#Childrenable");
    public static final org.semanticwb.platform.SemanticProperty social_parentObj=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#parentObj");
    public static final org.semanticwb.platform.SemanticProperty social_hasChildrenObjInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#hasChildrenObjInv");

   /**
   * Sets a value from the property ParentObj
   * @param value An instance of org.semanticwb.social.Childrenable
   */
    public void setParentObj(org.semanticwb.social.Childrenable value);

   /**
   * Remove the value from the property ParentObj
   */
    public void removeParentObj();

    public org.semanticwb.social.Childrenable getParentObj();

    public org.semanticwb.model.GenericIterator<org.semanticwb.social.Childrenable> listChildrenObjInvs();
    public boolean hasChildrenObjInv(org.semanticwb.social.Childrenable value);
}
