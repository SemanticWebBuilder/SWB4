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
    * Interfaz que define propiedades para los elementos que pueden ser versionados.
    */
public interface VersionableBase extends org.semanticwb.model.GenericObject
{
   
   /** Objeto utilizado para identificar una version de algun componente. */
    public static final org.semanticwb.platform.SemanticClass swb_VersionInfo=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#VersionInfo");
    
    /** The Constant swb_actualVersion. */
    public static final org.semanticwb.platform.SemanticProperty swb_actualVersion=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#actualVersion");
    
    /** The Constant swb_lastVersion. */
    public static final org.semanticwb.platform.SemanticProperty swb_lastVersion=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#lastVersion");
   
   /** Interfaz que define propiedades para los elementos que pueden ser versionados. */
    public static final org.semanticwb.platform.SemanticClass swb_Versionable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Versionable");

   /**
    * Sets a value from the property ActualVersion.
    * 
    * @param value An instance of org.semanticwb.model.VersionInfo
    */
    public void setActualVersion(org.semanticwb.model.VersionInfo value);

   /**
    * Remove the value from the property ActualVersion.
    */
    public void removeActualVersion();

    /**
     * Gets the actual version.
     * 
     * @return the actual version
     */
    public org.semanticwb.model.VersionInfo getActualVersion();

   /**
    * Sets a value from the property LastVersion.
    * 
    * @param value An instance of org.semanticwb.model.VersionInfo
    */
    public void setLastVersion(org.semanticwb.model.VersionInfo value);

   /**
    * Remove the value from the property LastVersion.
    */
    public void removeLastVersion();

    /**
     * Gets the last version.
     * 
     * @return the last version
     */
    public org.semanticwb.model.VersionInfo getLastVersion();
}
