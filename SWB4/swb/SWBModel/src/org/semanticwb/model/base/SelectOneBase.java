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
    * Elemento que muestra un componente grafico para seleccionar una opcion.
    */
public abstract class SelectOneBase extends org.semanticwb.model.base.FormElementBase 
{
    
    /** The Constant swbxf_so_globalScope. */
    public static final org.semanticwb.platform.SemanticProperty swbxf_so_globalScope=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#so_globalScope");
    
    /** The Constant swbxf_so_userRepository. */
    public static final org.semanticwb.platform.SemanticProperty swbxf_so_userRepository=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#so_userRepository");
    
    /** The Constant swbxf_so_nullSuport. */
    public static final org.semanticwb.platform.SemanticProperty swbxf_so_nullSuport=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#so_nullSuport");
   
   /** Elemento que muestra un componente grafico para seleccionar una opcion. */
    public static final org.semanticwb.platform.SemanticClass swbxf_SelectOne=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#SelectOne");
   
   /** The semantic class that represents the currentObject. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#SelectOne");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {
       
       /**
        * Returns a list of SelectOne for a model.
        * 
        * @param model Model to find
        * @return Iterator of org.semanticwb.model.SelectOne
        */

        public static java.util.Iterator<org.semanticwb.model.SelectOne> listSelectOnes(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.SelectOne>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.SelectOne for all models
       * @return Iterator of org.semanticwb.model.SelectOne
       */

        public static java.util.Iterator<org.semanticwb.model.SelectOne> listSelectOnes()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.SelectOne>(it, true);
        }
       /**
       * Gets a org.semanticwb.model.SelectOne
       * @param id Identifier for org.semanticwb.model.SelectOne
       * @param model Model of the org.semanticwb.model.SelectOne
       * @return A org.semanticwb.model.SelectOne
       */
        public static org.semanticwb.model.SelectOne getSelectOne(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.SelectOne)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.SelectOne
       * @param id Identifier for org.semanticwb.model.SelectOne
       * @param model Model of the org.semanticwb.model.SelectOne
       * @return A org.semanticwb.model.SelectOne
       */
        public static org.semanticwb.model.SelectOne createSelectOne(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.SelectOne)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.SelectOne
       * @param id Identifier for org.semanticwb.model.SelectOne
       * @param model Model of the org.semanticwb.model.SelectOne
       */
        public static void removeSelectOne(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.SelectOne
       * @param id Identifier for org.semanticwb.model.SelectOne
       * @param model Model of the org.semanticwb.model.SelectOne
       * @return true if the org.semanticwb.model.SelectOne exists, false otherwise
       */

        public static boolean hasSelectOne(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSelectOne(id, model)!=null);
        }
    }

   /**
    * Constructs a SelectOneBase with a SemanticObject.
    * 
    * @param base The SemanticObject with the properties for the SelectOne
    */
    public SelectOneBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
 * Gets the GlobalScope property.
 * 
 * @return boolean with the GlobalScope
 */
    public boolean isGlobalScope()
    {
        return getSemanticObject().getBooleanProperty(swbxf_so_globalScope);
    }

/**
 * Sets the GlobalScope property.
 * 
 * @param value long with the GlobalScope
 */
    public void setGlobalScope(boolean value)
    {
        getSemanticObject().setBooleanProperty(swbxf_so_globalScope, value);
    }

/**
 * Gets the UserRepository property.
 * 
 * @return boolean with the UserRepository
 */
    public boolean isUserRepository()
    {
        return getSemanticObject().getBooleanProperty(swbxf_so_userRepository);
    }

/**
 * Sets the UserRepository property.
 * 
 * @param value long with the UserRepository
 */
    public void setUserRepository(boolean value)
    {
        getSemanticObject().setBooleanProperty(swbxf_so_userRepository, value);
    }

/**
 * Gets the BlankSuport property.
 * 
 * @return boolean with the BlankSuport
 */
    public boolean isBlankSuport()
    {
        return getSemanticObject().getBooleanProperty(swbxf_so_nullSuport);
    }

/**
 * Sets the BlankSuport property.
 * 
 * @param value long with the BlankSuport
 */
    public void setBlankSuport(boolean value)
    {
        getSemanticObject().setBooleanProperty(swbxf_so_nullSuport, value);
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
