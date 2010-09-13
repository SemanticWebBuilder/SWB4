/**  
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
 **/
package org.semanticwb.model.base;

   // TODO: Auto-generated Javadoc
/**
    * Interfaz que define propiedades para los elementos que pueden regirse por una o más reglas.
    */
public interface RuleableBase extends org.semanticwb.model.GenericObject
{
   /**
   * Objeto que define una regla de negocio, utilizando los atributos del usuario para filtrar componente, seccion, plantillas, etc. 
   */
    public static final org.semanticwb.platform.SemanticClass swb_Rule=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Rule");
    
    /** The Constant swb_hasRule. */
    public static final org.semanticwb.platform.SemanticProperty swb_hasRule=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasRule");
   
   /** Interfaz que define propiedades para los elementos que pueden regirse por una o más reglas. */
    public static final org.semanticwb.platform.SemanticClass swb_Ruleable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Ruleable");

    /**
     * List rules.
     * 
     * @return the org.semanticwb.model. generic iterator
     */
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.Rule> listRules();
    
    /**
     * Checks for rule.
     * 
     * @param value the value
     * @return true, if successful
     */
    public boolean hasRule(org.semanticwb.model.Rule value);

   /**
    * Adds the Rule.
    * 
    * @param value An instance of org.semanticwb.model.Rule
    */
    public void addRule(org.semanticwb.model.Rule value);

   /**
    * Remove all the values for the property Rule.
    */
    public void removeAllRule();

   /**
    * Remove a value from the property Rule.
    * 
    * @param value An instance of org.semanticwb.model.Rule
    */
    public void removeRule(org.semanticwb.model.Rule value);

/**
 * Gets the Rule.
 * 
 * @return a instance of org.semanticwb.model.Rule
 */
    public org.semanticwb.model.Rule getRule();
}
