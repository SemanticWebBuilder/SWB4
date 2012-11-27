/*
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
 */
package org.semanticwb.repository.base;


// TODO: Auto-generated Javadoc
/**
 * The Class BaseNodeBase.
 */
public abstract class BaseNodeBase extends org.semanticwb.model.base.GenericObjectBase 
{
    
    /** The Constant nt_BaseNode. */
    public static final org.semanticwb.platform.SemanticClass nt_BaseNode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#base");
    
    /** The Constant swbrep_parentNode. */
    public static final org.semanticwb.platform.SemanticProperty swbrep_parentNode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/repository#parentNode");
    
    /** The Constant jcr_primaryType. */
    public static final org.semanticwb.platform.SemanticProperty jcr_primaryType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#primaryType");
    
    /** The Constant swbrep_path. */
    public static final org.semanticwb.platform.SemanticProperty swbrep_path=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/repository#path");
    
    /** The Constant swbrep_name. */
    public static final org.semanticwb.platform.SemanticProperty swbrep_name=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/repository#name");
    
    /** The Constant swbrep_hasNodes. */
    public static final org.semanticwb.platform.SemanticProperty swbrep_hasNodes=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/repository#hasNodes");
    
    /** The Constant jcr_mixinTypes. */
    public static final org.semanticwb.platform.SemanticProperty jcr_mixinTypes=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#mixinTypes");
    
    /** The Constant sclass. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#base");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {

        /**
         * List base nodes.
         * 
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.repository.BaseNode> listBaseNodes(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.repository.BaseNode>(it, true);
        }

        /**
         * List base nodes.
         * 
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.repository.BaseNode> listBaseNodes()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.repository.BaseNode>(it, true);
        }

        /**
         * Gets the base node.
         * 
         * @param id the id
         * @param model the model
         * @return the base node
         */
        public static org.semanticwb.repository.BaseNode getBaseNode(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.repository.BaseNode)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        /**
         * Creates the base node.
         * 
         * @param id the id
         * @param model the model
         * @return the org.semanticwb.repository. base node
         */
        public static org.semanticwb.repository.BaseNode createBaseNode(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.repository.BaseNode)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        /**
         * Removes the base node.
         * 
         * @param id the id
         * @param model the model
         */
        public static void removeBaseNode(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        /**
         * Checks for base node.
         * 
         * @param id the id
         * @param model the model
         * @return true, if successful
         */
        public static boolean hasBaseNode(String id, org.semanticwb.model.SWBModel model)
        {
            return (getBaseNode(id, model)!=null);
        }

        /**
         * List base node by parent.
         * 
         * @param parentnode the parentnode
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.repository.BaseNode> listBaseNodeByParent(org.semanticwb.repository.BaseNode parentnode,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.repository.BaseNode> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbrep_parentNode, parentnode.getSemanticObject()));
            return it;
        }

        /**
         * List base node by parent.
         * 
         * @param parentnode the parentnode
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.repository.BaseNode> listBaseNodeByParent(org.semanticwb.repository.BaseNode parentnode)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.repository.BaseNode> it=new org.semanticwb.model.GenericIterator(parentnode.getSemanticObject().getModel().listSubjects(swbrep_parentNode,parentnode.getSemanticObject()));
            return it;
        }

        /**
         * List base node by node.
         * 
         * @param hasnodes the hasnodes
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.repository.BaseNode> listBaseNodeByNode(org.semanticwb.repository.BaseNode hasnodes,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.repository.BaseNode> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbrep_hasNodes, hasnodes.getSemanticObject()));
            return it;
        }

        /**
         * List base node by node.
         * 
         * @param hasnodes the hasnodes
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.repository.BaseNode> listBaseNodeByNode(org.semanticwb.repository.BaseNode hasnodes)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.repository.BaseNode> it=new org.semanticwb.model.GenericIterator(hasnodes.getSemanticObject().getModel().listSubjects(swbrep_hasNodes,hasnodes.getSemanticObject()));
            return it;
        }
    }

    /**
     * Instantiates a new base node base.
     * 
     * @param base the base
     */
    public BaseNodeBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /**
     * Sets the parent.
     * 
     * @param value the new parent
     */
    public void setParent(org.semanticwb.repository.BaseNode value)
    {
        getSemanticObject().setObjectProperty(swbrep_parentNode, value.getSemanticObject());
    }

    /**
     * Removes the parent.
     */
    public void removeParent()
    {
        getSemanticObject().removeProperty(swbrep_parentNode);
    }

    /**
     * Gets the parent.
     * 
     * @return the parent
     */
    public org.semanticwb.repository.BaseNode getParent()
    {
         org.semanticwb.repository.BaseNode ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbrep_parentNode);
         if(obj!=null)
         {
             ret=(org.semanticwb.repository.BaseNode)obj.createGenericInstance();
         }
         return ret;
    }

    /**
     * Gets the primary type.
     * 
     * @return the primary type
     */
    public String getPrimaryType()
    {
        return getSemanticObject().getProperty(jcr_primaryType);
    }

    /**
     * Sets the primary type.
     * 
     * @param value the new primary type
     */
    public void setPrimaryType(String value)
    {
        getSemanticObject().setProperty(jcr_primaryType, value);
    }

    /**
     * Gets the path.
     * 
     * @return the path
     */
    public String getPath()
    {
        return getSemanticObject().getProperty(swbrep_path);
    }

    /**
     * Sets the path.
     * 
     * @param value the new path
     */
    public void setPath(String value)
    {
        getSemanticObject().setProperty(swbrep_path, value);
    }

    /**
     * Gets the name.
     * 
     * @return the name
     */
    public String getName()
    {
        return getSemanticObject().getProperty(swbrep_name);
    }

    /**
     * Sets the name.
     * 
     * @param value the new name
     */
    public void setName(String value)
    {
        getSemanticObject().setProperty(swbrep_name, value);
    }

    /**
     * List nodes.
     * 
     * @return the org.semanticwb.model. generic iterator
     */
    public org.semanticwb.model.GenericIterator<org.semanticwb.repository.BaseNode> listNodes()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.repository.BaseNode>(getSemanticObject().listObjectProperties(swbrep_hasNodes));
    }

    /**
     * Checks for node.
     * 
     * @param basenode the basenode
     * @return true, if successful
     */
    public boolean hasNode(org.semanticwb.repository.BaseNode basenode)
    {
        boolean ret=false;
        if(basenode!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swbrep_hasNodes,basenode.getSemanticObject());
        }
        return ret;
    }

    /**
     * Gets the node.
     * 
     * @return the node
     */
    public org.semanticwb.repository.BaseNode getNode()
    {
         org.semanticwb.repository.BaseNode ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbrep_hasNodes);
         if(obj!=null)
         {
             ret=(org.semanticwb.repository.BaseNode)obj.createGenericInstance();
         }
         return ret;
    }

    /**
     * Gets the mixin types.
     * 
     * @return the mixin types
     */
    public String getMixinTypes()
    {
        return getSemanticObject().getProperty(jcr_mixinTypes);
    }

    /**
     * Sets the mixin types.
     * 
     * @param value the new mixin types
     */
    public void setMixinTypes(String value)
    {
        getSemanticObject().setProperty(jcr_mixinTypes, value);
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

    /**
     * Gets the workspace.
     * 
     * @return the workspace
     */
    public org.semanticwb.repository.Workspace getWorkspace()
    {
        return (org.semanticwb.repository.Workspace)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
