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
    * Interfaz que define propiedades para elementos que pueden referencia a recursos.
    */
public interface ResourceRefableBase extends org.semanticwb.model.Referensable
{
   
   /** Referencia a un objeto de tipo Resource. */
    public static final org.semanticwb.platform.SemanticClass swb_ResourceRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#ResourceRef");
    
    /** The Constant swb_hasResourceRef. */
    public static final org.semanticwb.platform.SemanticProperty swb_hasResourceRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasResourceRef");
   
   /** Interfaz que define propiedades para elementos que pueden referencia a recursos. */
    public static final org.semanticwb.platform.SemanticClass swb_ResourceRefable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#ResourceRefable");

    /**
     * List resource refs.
     * 
     * @return the org.semanticwb.model. generic iterator
     */
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceRef> listResourceRefs();
    
    /**
     * Checks for resource ref.
     * 
     * @param value the value
     * @return true, if successful
     */
    public boolean hasResourceRef(org.semanticwb.model.ResourceRef value);

   /**
    * Adds the ResourceRef.
    * 
    * @param value An instance of org.semanticwb.model.ResourceRef
    */
    public void addResourceRef(org.semanticwb.model.ResourceRef value);

   /**
    * Remove all the values for the property ResourceRef.
    */
    public void removeAllResourceRef();

   /**
    * Remove a value from the property ResourceRef.
    * 
    * @param value An instance of org.semanticwb.model.ResourceRef
    */
    public void removeResourceRef(org.semanticwb.model.ResourceRef value);

/**
 * Gets the ResourceRef.
 * 
 * @return a instance of org.semanticwb.model.ResourceRef
 */
    public org.semanticwb.model.ResourceRef getResourceRef();
}
