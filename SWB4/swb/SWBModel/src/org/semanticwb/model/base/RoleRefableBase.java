package org.semanticwb.model.base;

   /**
   * Interfaz que define propiedades para elementos que pueden referencia a roles 
   */
public interface RoleRefableBase extends org.semanticwb.model.Referensable
{
   /**
   * Referencia a un objeto de tipo Role 
   */
    public static final org.semanticwb.platform.SemanticClass swb_RoleRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#RoleRef");
    public static final org.semanticwb.platform.SemanticProperty swb_hasRoleRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasRoleRef");
   /**
   * Interfaz que define propiedades para elementos que pueden referencia a roles 
   */
    public static final org.semanticwb.platform.SemanticClass swb_RoleRefable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#RoleRefable");

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.RoleRef> listRoleRefs();
    public boolean hasRoleRef(org.semanticwb.model.RoleRef value);
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.RoleRef> listInheritRoleRefs();

   /**
   * Adds the RoleRef
   * @param value An instance of org.semanticwb.model.RoleRef
   */
    public void addRoleRef(org.semanticwb.model.RoleRef value);

   /**
   * Remove all the values for the property RoleRef
   */
    public void removeAllRoleRef();

   /**
   * Remove a value from the property RoleRef
   * @param value An instance of org.semanticwb.model.RoleRef
   */
    public void removeRoleRef(org.semanticwb.model.RoleRef value);

/**
* Gets the RoleRef
* @return a instance of org.semanticwb.model.RoleRef
*/
    public org.semanticwb.model.RoleRef getRoleRef();
}
