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
    * Interfaz que define propiedades para los elementos que pueden tener uno o mas objetos HerarquicalNode.
    */
public interface HerarquicalNodeableBase extends org.semanticwb.model.GenericObject
{
   /**
   * Objeto utilizado para definir un node del arbol de navegacion dentro de la administración de SWB, que contiene elemento de una clase definida. 
   */
    public static final org.semanticwb.platform.SemanticClass swbxf_HerarquicalNode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#HerarquicalNode");
    
    /** The Constant swb_hasHerarquicalNode. */
    public static final org.semanticwb.platform.SemanticProperty swb_hasHerarquicalNode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasHerarquicalNode");
   
   /** Interfaz que define propiedades para los elementos que pueden tener uno o mas objetos HerarquicalNode. */
    public static final org.semanticwb.platform.SemanticClass swbxf_HerarquicalNodeable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#HerarquicalNodeable");

    /**
     * List herarquical nodes.
     * 
     * @return the org.semanticwb.model. generic iterator
     */
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.HerarquicalNode> listHerarquicalNodes();
    
    /**
     * Checks for herarquical node.
     * 
     * @param value the value
     * @return true, if successful
     */
    public boolean hasHerarquicalNode(org.semanticwb.model.HerarquicalNode value);

   /**
    * Adds the HerarquicalNode.
    * 
    * @param value An instance of org.semanticwb.model.HerarquicalNode
    */
    public void addHerarquicalNode(org.semanticwb.model.HerarquicalNode value);

   /**
    * Remove all the values for the property HerarquicalNode.
    */
    public void removeAllHerarquicalNode();

   /**
    * Remove a value from the property HerarquicalNode.
    * 
    * @param value An instance of org.semanticwb.model.HerarquicalNode
    */
    public void removeHerarquicalNode(org.semanticwb.model.HerarquicalNode value);

/**
 * Gets the HerarquicalNode.
 * 
 * @return a instance of org.semanticwb.model.HerarquicalNode
 */
    public org.semanticwb.model.HerarquicalNode getHerarquicalNode();
}
