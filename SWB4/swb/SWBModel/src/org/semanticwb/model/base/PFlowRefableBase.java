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
    * Interfaz que define propiedades para elementos que pueden referencia a flujos de publicacion.
    */
public interface PFlowRefableBase extends org.semanticwb.model.Referensable
{
   
   /** Referencia a un objeto de tipo PFlow. */
    public static final org.semanticwb.platform.SemanticClass swb_PFlowRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#PFlowRef");
    
    /** The Constant swb_hasPFlowRef. */
    public static final org.semanticwb.platform.SemanticProperty swb_hasPFlowRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasPFlowRef");
   
   /** Interfaz que define propiedades para elementos que pueden referencia a flujos de publicacion. */
    public static final org.semanticwb.platform.SemanticClass swb_PFlowRefable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#PFlowRefable");

    /**
     * List p flow refs.
     * 
     * @return the org.semanticwb.model. generic iterator
     */
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.PFlowRef> listPFlowRefs();
    
    /**
     * Checks for p flow ref.
     * 
     * @param value the value
     * @return true, if successful
     */
    public boolean hasPFlowRef(org.semanticwb.model.PFlowRef value);
    
    /**
     * List inherit p flow refs.
     * 
     * @return the org.semanticwb.model. generic iterator
     */
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.PFlowRef> listInheritPFlowRefs();

   /**
    * Adds the PFlowRef.
    * 
    * @param value An instance of org.semanticwb.model.PFlowRef
    */
    public void addPFlowRef(org.semanticwb.model.PFlowRef value);

   /**
    * Remove all the values for the property PFlowRef.
    */
    public void removeAllPFlowRef();

   /**
    * Remove a value from the property PFlowRef.
    * 
    * @param value An instance of org.semanticwb.model.PFlowRef
    */
    public void removePFlowRef(org.semanticwb.model.PFlowRef value);

/**
 * Gets the PFlowRef.
 * 
 * @return a instance of org.semanticwb.model.PFlowRef
 */
    public org.semanticwb.model.PFlowRef getPFlowRef();
}
