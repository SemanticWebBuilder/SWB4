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
    * Objeto utilizado para definir caracteristicas visuales de una clase dentro de la administracion de SWB.
    */
public abstract class DisplayObjectBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Iconable
{
    
    /** The Constant swbxf_doDispatcher. */
    public static final org.semanticwb.platform.SemanticProperty swbxf_doDispatcher=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#doDispatcher");
    
    /** The Constant swbxf_doTreeController. */
    public static final org.semanticwb.platform.SemanticProperty swbxf_doTreeController=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#doTreeController");
    
    /** The Constant swbxf_dragSupport. */
    public static final org.semanticwb.platform.SemanticProperty swbxf_dragSupport=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#dragSupport");
    
    /** The Constant swbxf_dropMatchLevel. */
    public static final org.semanticwb.platform.SemanticProperty swbxf_dropMatchLevel=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#dropMatchLevel");
   
   /** Objeto utilizado para definir caracteristicas visuales de una clase dentro de la administracion de SWB. */
    public static final org.semanticwb.platform.SemanticClass swbxf_DisplayObject=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#DisplayObject");
   
   /** The semantic class that represents the currentObject. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#DisplayObject");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {
       
       /**
        * Returns a list of DisplayObject for a model.
        * 
        * @param model Model to find
        * @return Iterator of org.semanticwb.model.DisplayObject
        */

        public static java.util.Iterator<org.semanticwb.model.DisplayObject> listDisplayObjects(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.DisplayObject>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.DisplayObject for all models
       * @return Iterator of org.semanticwb.model.DisplayObject
       */

        public static java.util.Iterator<org.semanticwb.model.DisplayObject> listDisplayObjects()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.DisplayObject>(it, true);
        }
       /**
       * Gets a org.semanticwb.model.DisplayObject
       * @param id Identifier for org.semanticwb.model.DisplayObject
       * @param model Model of the org.semanticwb.model.DisplayObject
       * @return A org.semanticwb.model.DisplayObject
       */
        public static org.semanticwb.model.DisplayObject getDisplayObject(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.DisplayObject)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.DisplayObject
       * @param id Identifier for org.semanticwb.model.DisplayObject
       * @param model Model of the org.semanticwb.model.DisplayObject
       * @return A org.semanticwb.model.DisplayObject
       */
        public static org.semanticwb.model.DisplayObject createDisplayObject(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.DisplayObject)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.DisplayObject
       * @param id Identifier for org.semanticwb.model.DisplayObject
       * @param model Model of the org.semanticwb.model.DisplayObject
       */
        public static void removeDisplayObject(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.DisplayObject
       * @param id Identifier for org.semanticwb.model.DisplayObject
       * @param model Model of the org.semanticwb.model.DisplayObject
       * @return true if the org.semanticwb.model.DisplayObject exists, false otherwise
       */

        public static boolean hasDisplayObject(String id, org.semanticwb.model.SWBModel model)
        {
            return (getDisplayObject(id, model)!=null);
        }
    }

   /**
    * Constructs a DisplayObjectBase with a SemanticObject.
    * 
    * @param base The SemanticObject with the properties for the DisplayObject
    */
    public DisplayObjectBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
 * Gets the IconClass property.
 * 
 * @return String with the IconClass
 */
    public String getIconClass()
    {
        return getSemanticObject().getProperty(swb_iconClass);
    }

/**
 * Sets the IconClass property.
 * 
 * @param value long with the IconClass
 */
    public void setIconClass(String value)
    {
        getSemanticObject().setProperty(swb_iconClass, value);
    }

/**
 * Gets the DoDispatcher property.
 * 
 * @return String with the DoDispatcher
 */
    public String getDoDispatcher()
    {
        return getSemanticObject().getProperty(swbxf_doDispatcher);
    }

/**
 * Sets the DoDispatcher property.
 * 
 * @param value long with the DoDispatcher
 */
    public void setDoDispatcher(String value)
    {
        getSemanticObject().setProperty(swbxf_doDispatcher, value);
    }

/**
 * Gets the TreeController property.
 * 
 * @return String with the TreeController
 */
    public String getTreeController()
    {
        return getSemanticObject().getProperty(swbxf_doTreeController);
    }

/**
 * Sets the TreeController property.
 * 
 * @param value long with the TreeController
 */
    public void setTreeController(String value)
    {
        getSemanticObject().setProperty(swbxf_doTreeController, value);
    }

/**
 * Gets the DragSupport property.
 * 
 * @return boolean with the DragSupport
 */
    public boolean isDragSupport()
    {
        return getSemanticObject().getBooleanProperty(swbxf_dragSupport);
    }

/**
 * Sets the DragSupport property.
 * 
 * @param value long with the DragSupport
 */
    public void setDragSupport(boolean value)
    {
        getSemanticObject().setBooleanProperty(swbxf_dragSupport, value);
    }

/**
 * Gets the DropMatchLevel property.
 * 
 * @return int with the DropMatchLevel
 */
    public int getDropMatchLevel()
    {
        return getSemanticObject().getIntProperty(swbxf_dropMatchLevel);
    }

/**
 * Sets the DropMatchLevel property.
 * 
 * @param value long with the DropMatchLevel
 */
    public void setDropMatchLevel(int value)
    {
        getSemanticObject().setIntProperty(swbxf_dropMatchLevel, value);
    }
}
