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
 * The Class DisplayObjectBase.
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
    
    /** The Constant swbxf_DisplayObject. */
    public static final org.semanticwb.platform.SemanticClass swbxf_DisplayObject=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#DisplayObject");
    
    /** The Constant sclass. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#DisplayObject");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {

        /**
         * List display objects.
         * 
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.DisplayObject> listDisplayObjects(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.DisplayObject>(it, true);
        }

        /**
         * List display objects.
         * 
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.DisplayObject> listDisplayObjects()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.DisplayObject>(it, true);
        }

        /**
         * Gets the display object.
         * 
         * @param id the id
         * @param model the model
         * @return the display object
         */
        public static org.semanticwb.model.DisplayObject getDisplayObject(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.DisplayObject)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        /**
         * Creates the display object.
         * 
         * @param id the id
         * @param model the model
         * @return the org.semanticwb.model. display object
         */
        public static org.semanticwb.model.DisplayObject createDisplayObject(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.DisplayObject)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        /**
         * Removes the display object.
         * 
         * @param id the id
         * @param model the model
         */
        public static void removeDisplayObject(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        /**
         * Checks for display object.
         * 
         * @param id the id
         * @param model the model
         * @return true, if successful
         */
        public static boolean hasDisplayObject(String id, org.semanticwb.model.SWBModel model)
        {
            return (getDisplayObject(id, model)!=null);
        }
    }

    /**
     * Instantiates a new display object base.
     * 
     * @param base the base
     */
    public DisplayObjectBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.IconableBase#getIconClass()
     */
    public String getIconClass()
    {
        return getSemanticObject().getProperty(swb_iconClass);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.IconableBase#setIconClass(java.lang.String)
     */
    public void setIconClass(String value)
    {
        getSemanticObject().setProperty(swb_iconClass, value);
    }

    /**
     * Gets the do dispatcher.
     * 
     * @return the do dispatcher
     */
    public String getDoDispatcher()
    {
        return getSemanticObject().getProperty(swbxf_doDispatcher);
    }

    /**
     * Sets the do dispatcher.
     * 
     * @param value the new do dispatcher
     */
    public void setDoDispatcher(String value)
    {
        getSemanticObject().setProperty(swbxf_doDispatcher, value);
    }

    /**
     * Gets the tree controller.
     * 
     * @return the tree controller
     */
    public String getTreeController()
    {
        return getSemanticObject().getProperty(swbxf_doTreeController);
    }

    /**
     * Sets the tree controller.
     * 
     * @param value the new tree controller
     */
    public void setTreeController(String value)
    {
        getSemanticObject().setProperty(swbxf_doTreeController, value);
    }

    /**
     * Checks if is drag support.
     * 
     * @return true, if is drag support
     */
    public boolean isDragSupport()
    {
        return getSemanticObject().getBooleanProperty(swbxf_dragSupport);
    }

    /**
     * Sets the drag support.
     * 
     * @param value the new drag support
     */
    public void setDragSupport(boolean value)
    {
        getSemanticObject().setBooleanProperty(swbxf_dragSupport, value);
    }

    /**
     * Gets the drop match level.
     * 
     * @return the drop match level
     */
    public int getDropMatchLevel()
    {
        return getSemanticObject().getIntProperty(swbxf_dropMatchLevel);
    }

    /**
     * Sets the drop match level.
     * 
     * @param value the new drop match level
     */
    public void setDropMatchLevel(int value)
    {
        getSemanticObject().setIntProperty(swbxf_dropMatchLevel, value);
    }
}
