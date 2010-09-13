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
    * Elemento que muestra un componente grafico (Google Maps) para geolocalizar un punto.
    */
public abstract class GMapBase extends org.semanticwb.model.base.FormElementBase 
{
    
    /** The Constant swbxf_initLatitude. */
    public static final org.semanticwb.platform.SemanticProperty swbxf_initLatitude=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#initLatitude");
    
    /** The Constant swb_initLongitude. */
    public static final org.semanticwb.platform.SemanticProperty swb_initLongitude=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#initLongitude");
    
    /** The Constant swb_initStep. */
    public static final org.semanticwb.platform.SemanticProperty swb_initStep=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#initStep");
   
   /** Elemento que muestra un componente grafico (Google Maps) para geolocalizar un punto. */
    public static final org.semanticwb.platform.SemanticClass swbxf_GMap=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#GMap");
   
   /** The semantic class that represents the currentObject. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#GMap");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {
       
       /**
        * Returns a list of GMap for a model.
        * 
        * @param model Model to find
        * @return Iterator of org.semanticwb.model.GMap
        */

        public static java.util.Iterator<org.semanticwb.model.GMap> listGMaps(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.GMap>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.GMap for all models
       * @return Iterator of org.semanticwb.model.GMap
       */

        public static java.util.Iterator<org.semanticwb.model.GMap> listGMaps()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.GMap>(it, true);
        }
       /**
       * Gets a org.semanticwb.model.GMap
       * @param id Identifier for org.semanticwb.model.GMap
       * @param model Model of the org.semanticwb.model.GMap
       * @return A org.semanticwb.model.GMap
       */
        public static org.semanticwb.model.GMap getGMap(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.GMap)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.GMap
       * @param id Identifier for org.semanticwb.model.GMap
       * @param model Model of the org.semanticwb.model.GMap
       * @return A org.semanticwb.model.GMap
       */
        public static org.semanticwb.model.GMap createGMap(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.GMap)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.GMap
       * @param id Identifier for org.semanticwb.model.GMap
       * @param model Model of the org.semanticwb.model.GMap
       */
        public static void removeGMap(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.GMap
       * @param id Identifier for org.semanticwb.model.GMap
       * @param model Model of the org.semanticwb.model.GMap
       * @return true if the org.semanticwb.model.GMap exists, false otherwise
       */

        public static boolean hasGMap(String id, org.semanticwb.model.SWBModel model)
        {
            return (getGMap(id, model)!=null);
        }
    }

   /**
    * Constructs a GMapBase with a SemanticObject.
    * 
    * @param base The SemanticObject with the properties for the GMap
    */
    public GMapBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
 * Gets the InitLatitude property.
 * 
 * @return double with the InitLatitude
 */
    public double getInitLatitude()
    {
        return getSemanticObject().getDoubleProperty(swbxf_initLatitude);
    }

/**
 * Sets the InitLatitude property.
 * 
 * @param value long with the InitLatitude
 */
    public void setInitLatitude(double value)
    {
        getSemanticObject().setDoubleProperty(swbxf_initLatitude, value);
    }

/**
 * Gets the InitLongitude property.
 * 
 * @return double with the InitLongitude
 */
    public double getInitLongitude()
    {
        return getSemanticObject().getDoubleProperty(swb_initLongitude);
    }

/**
 * Sets the InitLongitude property.
 * 
 * @param value long with the InitLongitude
 */
    public void setInitLongitude(double value)
    {
        getSemanticObject().setDoubleProperty(swb_initLongitude, value);
    }

/**
 * Gets the InitStep property.
 * 
 * @return int with the InitStep
 */
    public int getInitStep()
    {
        return getSemanticObject().getIntProperty(swb_initStep);
    }

/**
 * Sets the InitStep property.
 * 
 * @param value long with the InitStep
 */
    public void setInitStep(int value)
    {
        getSemanticObject().setIntProperty(swb_initStep, value);
    }

    /**
     * Removes the.
     */
    public void remove()
    {
        getSemanticObject().remove();
    }

    /**
     * List related objects.
     * 
     * @return the java.util. iterator
     */
    public java.util.Iterator<org.semanticwb.model.GenericObject> listRelatedObjects()
    {
        return new org.semanticwb.model.GenericIterator(getSemanticObject().listRelatedObjects(),true);
    }
}
