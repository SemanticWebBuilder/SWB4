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
 * The Class FrozenNodeBase.
 */
public abstract class FrozenNodeBase extends org.semanticwb.repository.BaseNode implements org.semanticwb.repository.Referenceable
{
    
    /** The Constant jcr_frozenPrimaryType. */
    public static final org.semanticwb.platform.SemanticProperty jcr_frozenPrimaryType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#frozenPrimaryType");
    
    /** The Constant jcr_frozenMixinTypes. */
    public static final org.semanticwb.platform.SemanticProperty jcr_frozenMixinTypes=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#frozenMixinTypes");
    
    /** The Constant jcr_frozenUuid. */
    public static final org.semanticwb.platform.SemanticProperty jcr_frozenUuid=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#frozenUuid");
    
    /** The Constant nt_FrozenNode. */
    public static final org.semanticwb.platform.SemanticClass nt_FrozenNode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#frozenNode");
    
    /** The Constant sclass. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#frozenNode");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {

        /**
         * List frozen nodes.
         * 
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.repository.FrozenNode> listFrozenNodes(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.repository.FrozenNode>(it, true);
        }

        /**
         * List frozen nodes.
         * 
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.repository.FrozenNode> listFrozenNodes()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.repository.FrozenNode>(it, true);
        }

        /**
         * Gets the frozen node.
         * 
         * @param id the id
         * @param model the model
         * @return the frozen node
         */
        public static org.semanticwb.repository.FrozenNode getFrozenNode(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.repository.FrozenNode)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        /**
         * Creates the frozen node.
         * 
         * @param id the id
         * @param model the model
         * @return the org.semanticwb.repository. frozen node
         */
        public static org.semanticwb.repository.FrozenNode createFrozenNode(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.repository.FrozenNode)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        /**
         * Removes the frozen node.
         * 
         * @param id the id
         * @param model the model
         */
        public static void removeFrozenNode(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        /**
         * Checks for frozen node.
         * 
         * @param id the id
         * @param model the model
         * @return true, if successful
         */
        public static boolean hasFrozenNode(String id, org.semanticwb.model.SWBModel model)
        {
            return (getFrozenNode(id, model)!=null);
        }

        /**
         * List frozen node by parent.
         * 
         * @param parentnode the parentnode
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.repository.FrozenNode> listFrozenNodeByParent(org.semanticwb.repository.BaseNode parentnode,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.repository.FrozenNode> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbrep_parentNode, parentnode.getSemanticObject()));
            return it;
        }

        /**
         * List frozen node by parent.
         * 
         * @param parentnode the parentnode
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.repository.FrozenNode> listFrozenNodeByParent(org.semanticwb.repository.BaseNode parentnode)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.repository.FrozenNode> it=new org.semanticwb.model.GenericIterator(parentnode.getSemanticObject().getModel().listSubjects(swbrep_parentNode,parentnode.getSemanticObject()));
            return it;
        }

        /**
         * List frozen node by node.
         * 
         * @param hasnodes the hasnodes
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.repository.FrozenNode> listFrozenNodeByNode(org.semanticwb.repository.BaseNode hasnodes,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.repository.FrozenNode> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbrep_hasNodes, hasnodes.getSemanticObject()));
            return it;
        }

        /**
         * List frozen node by node.
         * 
         * @param hasnodes the hasnodes
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.repository.FrozenNode> listFrozenNodeByNode(org.semanticwb.repository.BaseNode hasnodes)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.repository.FrozenNode> it=new org.semanticwb.model.GenericIterator(hasnodes.getSemanticObject().getModel().listSubjects(swbrep_hasNodes,hasnodes.getSemanticObject()));
            return it;
        }
    }

    /**
     * Instantiates a new frozen node base.
     * 
     * @param base the base
     */
    public FrozenNodeBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /**
     * Gets the frozen primary type.
     * 
     * @return the frozen primary type
     */
    public String getFrozenPrimaryType()
    {
        return getSemanticObject().getProperty(jcr_frozenPrimaryType);
    }

    /**
     * Sets the frozen primary type.
     * 
     * @param value the new frozen primary type
     */
    public void setFrozenPrimaryType(String value)
    {
        getSemanticObject().setProperty(jcr_frozenPrimaryType, value);
    }

    /**
     * Gets the frozen mixin types.
     * 
     * @return the frozen mixin types
     */
    public String getFrozenMixinTypes()
    {
        return getSemanticObject().getProperty(jcr_frozenMixinTypes);
    }

    /**
     * Sets the frozen mixin types.
     * 
     * @param value the new frozen mixin types
     */
    public void setFrozenMixinTypes(String value)
    {
        getSemanticObject().setProperty(jcr_frozenMixinTypes, value);
    }

    /**
     * Gets the frozen uuid.
     * 
     * @return the frozen uuid
     */
    public String getFrozenUuid()
    {
        return getSemanticObject().getProperty(jcr_frozenUuid);
    }

    /**
     * Sets the frozen uuid.
     * 
     * @param value the new frozen uuid
     */
    public void setFrozenUuid(String value)
    {
        getSemanticObject().setProperty(jcr_frozenUuid, value);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.repository.base.ReferenceableBase#getUuid()
     */
    public String getUuid()
    {
        return getSemanticObject().getProperty(jcr_uuid);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.repository.base.ReferenceableBase#setUuid(java.lang.String)
     */
    public void setUuid(String value)
    {
        getSemanticObject().setProperty(jcr_uuid, value);
    }
}
