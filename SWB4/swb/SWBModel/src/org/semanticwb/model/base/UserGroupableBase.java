package org.semanticwb.model.base;

   /**
   * Interfaz que define propiedades para los elementos que pueden tener asociado uno grupo de usuarios 
   */
public interface UserGroupableBase extends org.semanticwb.model.GenericObject
{
   /**
   * Objeto que define un grupo de usuarios dentro de un repositorio de usuarios para filtrar componente, seccion, plantillas, etc. 
   */
    public static final org.semanticwb.platform.SemanticClass swb_UserGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UserGroup");
    public static final org.semanticwb.platform.SemanticProperty swb_hasUserGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasUserGroup");
   /**
   * Interfaz que define propiedades para los elementos que pueden tener asociado uno grupo de usuarios 
   */
    public static final org.semanticwb.platform.SemanticClass swb_UserGroupable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UserGroupable");

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroup> listUserGroups();
    public boolean hasUserGroup(org.semanticwb.model.UserGroup value);

   /**
   * Adds the UserGroup
   * @param value An instance of org.semanticwb.model.UserGroup
   */
    public void addUserGroup(org.semanticwb.model.UserGroup value);

   /**
   * Remove all the values for the property UserGroup
   */
    public void removeAllUserGroup();

   /**
   * Remove a value from the property UserGroup
   * @param value An instance of org.semanticwb.model.UserGroup
   */
    public void removeUserGroup(org.semanticwb.model.UserGroup value);

/**
* Gets the UserGroup
* @return a instance of org.semanticwb.model.UserGroup
*/
    public org.semanticwb.model.UserGroup getUserGroup();
}
