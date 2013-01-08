package org.semanticwb.model.base;

   /**
   * Interfaz que define propiedades para los elementos que pueden tener asociado uno o más roles 
   */
public interface RoleableBase extends org.semanticwb.model.GenericObject
{
   /**
   * Objeto que define un Role dentro de un repositorio de usuarios aplicable a un Usuario para filtrar componente, seccion, plantillas, etc. 
   */
    public static final org.semanticwb.platform.SemanticClass swb_Role=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Role");
    public static final org.semanticwb.platform.SemanticProperty swb_hasRole=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasRole");
   /**
   * Interfaz que define propiedades para los elementos que pueden tener asociado uno o más roles 
   */
    public static final org.semanticwb.platform.SemanticClass swb_Roleable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Roleable");

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.Role> listRoles();
    public boolean hasRole(org.semanticwb.model.Role value);

   /**
   * Adds the Role
   * @param value An instance of org.semanticwb.model.Role
   */
    public void addRole(org.semanticwb.model.Role value);

   /**
   * Remove all the values for the property Role
   */
    public void removeAllRole();

   /**
   * Remove a value from the property Role
   * @param value An instance of org.semanticwb.model.Role
   */
    public void removeRole(org.semanticwb.model.Role value);

/**
* Gets the Role
* @return a instance of org.semanticwb.model.Role
*/
    public org.semanticwb.model.Role getRole();
}
