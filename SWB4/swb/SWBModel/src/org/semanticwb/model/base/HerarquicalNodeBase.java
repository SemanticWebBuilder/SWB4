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
 * The Class HerarquicalNodeBase.
 */
public abstract class HerarquicalNodeBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Sortable,org.semanticwb.model.Filterable,org.semanticwb.model.FilterableNode,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Iconable
{
    
    /** The Constant swbxf_heTreeController. */
    public static final org.semanticwb.platform.SemanticProperty swbxf_heTreeController=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#heTreeController");
    
    /** The Constant swb_Class. */
    public static final org.semanticwb.platform.SemanticClass swb_Class=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Class");
    
    /** The Constant swbxf_heClass. */
    public static final org.semanticwb.platform.SemanticProperty swbxf_heClass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#heClass");
    
    /** The Constant swbxf_hePropertyFilter. */
    public static final org.semanticwb.platform.SemanticProperty swbxf_hePropertyFilter=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#hePropertyFilter");
    
    /** The Constant swb_SWBModel. */
    public static final org.semanticwb.platform.SemanticClass swb_SWBModel=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#SWBModel");
    
    /** The Constant swbxf_heModel. */
    public static final org.semanticwb.platform.SemanticProperty swbxf_heModel=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#heModel");
    
    /** The Constant swbxf_HerarquicalNode. */
    public static final org.semanticwb.platform.SemanticClass swbxf_HerarquicalNode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#HerarquicalNode");
    
    /** The Constant sclass. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#HerarquicalNode");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {

        /**
         * List herarquical nodes.
         * 
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.HerarquicalNode> listHerarquicalNodes(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.HerarquicalNode>(it, true);
        }

        /**
         * List herarquical nodes.
         * 
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.HerarquicalNode> listHerarquicalNodes()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.HerarquicalNode>(it, true);
        }

        /**
         * Gets the herarquical node.
         * 
         * @param id the id
         * @param model the model
         * @return the herarquical node
         */
        public static org.semanticwb.model.HerarquicalNode getHerarquicalNode(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.HerarquicalNode)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        /**
         * Creates the herarquical node.
         * 
         * @param id the id
         * @param model the model
         * @return the org.semanticwb.model. herarquical node
         */
        public static org.semanticwb.model.HerarquicalNode createHerarquicalNode(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.HerarquicalNode)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        /**
         * Removes the herarquical node.
         * 
         * @param id the id
         * @param model the model
         */
        public static void removeHerarquicalNode(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        /**
         * Checks for herarquical node.
         * 
         * @param id the id
         * @param model the model
         * @return true, if successful
         */
        public static boolean hasHerarquicalNode(String id, org.semanticwb.model.SWBModel model)
        {
            return (getHerarquicalNode(id, model)!=null);
        }

        /**
         * List herarquical node by model.
         * 
         * @param hemodel the hemodel
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.HerarquicalNode> listHerarquicalNodeByModel(org.semanticwb.model.SWBModel hemodel,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.HerarquicalNode> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbxf_heModel, hemodel.getSemanticObject()));
            return it;
        }

        /**
         * List herarquical node by model.
         * 
         * @param hemodel the hemodel
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.HerarquicalNode> listHerarquicalNodeByModel(org.semanticwb.model.SWBModel hemodel)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.HerarquicalNode> it=new org.semanticwb.model.GenericIterator(hemodel.getSemanticObject().getModel().listSubjects(swbxf_heModel,hemodel.getSemanticObject()));
            return it;
        }
    }

    /**
     * Instantiates a new herarquical node base.
     * 
     * @param base the base
     */
    public HerarquicalNodeBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.SortableBase#getIndex()
     */
    public int getIndex()
    {
        return getSemanticObject().getIntProperty(swb_index);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.SortableBase#setIndex(int)
     */
    public void setIndex(int value)
    {
        getSemanticObject().setIntProperty(swb_index, value);
    }

    /**
     * Gets the tree controller.
     * 
     * @return the tree controller
     */
    public String getTreeController()
    {
        return getSemanticObject().getProperty(swbxf_heTreeController);
    }

    /**
     * Sets the tree controller.
     * 
     * @param value the new tree controller
     */
    public void setTreeController(String value)
    {
        getSemanticObject().setProperty(swbxf_heTreeController, value);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#getTitle()
     */
    public String getTitle()
    {
        return getSemanticObject().getProperty(swb_title);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#setTitle(java.lang.String)
     */
    public void setTitle(String value)
    {
        getSemanticObject().setProperty(swb_title, value);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#getTitle(java.lang.String)
     */
    public String getTitle(String lang)
    {
        return getSemanticObject().getProperty(swb_title, null, lang);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#getDisplayTitle(java.lang.String)
     */
    public String getDisplayTitle(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_title, lang);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#setTitle(java.lang.String, java.lang.String)
     */
    public void setTitle(String title, String lang)
    {
        getSemanticObject().setProperty(swb_title, title, lang);
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
     * Sets the h class.
     * 
     * @param value the new h class
     */
    public void setHClass(org.semanticwb.platform.SemanticObject value)
    {
        getSemanticObject().setObjectProperty(swbxf_heClass, value);
    }

    /**
     * Removes the h class.
     */
    public void removeHClass()
    {
        getSemanticObject().removeProperty(swbxf_heClass);
    }

    /**
     * Gets the h class.
     * 
     * @return the h class
     */
    public org.semanticwb.platform.SemanticObject getHClass()
    {
         org.semanticwb.platform.SemanticObject ret=null;
         ret=getSemanticObject().getObjectProperty(swbxf_heClass);
         return ret;
    }

    /**
     * Gets the property filter.
     * 
     * @return the property filter
     */
    public String getPropertyFilter()
    {
        return getSemanticObject().getProperty(swbxf_hePropertyFilter);
    }

    /**
     * Sets the property filter.
     * 
     * @param value the new property filter
     */
    public void setPropertyFilter(String value)
    {
        getSemanticObject().setProperty(swbxf_hePropertyFilter, value);
    }

    /**
     * Sets the model.
     * 
     * @param value the new model
     */
    public void setModel(org.semanticwb.model.SWBModel value)
    {
        getSemanticObject().setObjectProperty(swbxf_heModel, value.getSemanticObject());
    }

    /**
     * Removes the model.
     */
    public void removeModel()
    {
        getSemanticObject().removeProperty(swbxf_heModel);
    }

    /**
     * Gets the model.
     * 
     * @return the model
     */
    public org.semanticwb.model.SWBModel getModel()
    {
         org.semanticwb.model.SWBModel ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbxf_heModel);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.SWBModel)obj.createGenericInstance();
         }
         return ret;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#getDescription()
     */
    public String getDescription()
    {
        return getSemanticObject().getProperty(swb_description);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#setDescription(java.lang.String)
     */
    public void setDescription(String value)
    {
        getSemanticObject().setProperty(swb_description, value);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#getDescription(java.lang.String)
     */
    public String getDescription(String lang)
    {
        return getSemanticObject().getProperty(swb_description, null, lang);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#getDisplayDescription(java.lang.String)
     */
    public String getDisplayDescription(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_description, lang);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.DescriptiveableBase#setDescription(java.lang.String, java.lang.String)
     */
    public void setDescription(String description, String lang)
    {
        getSemanticObject().setProperty(swb_description, description, lang);
    }
}
