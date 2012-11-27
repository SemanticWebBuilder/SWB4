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
 * The Class VersionBase.
 */
public abstract class VersionBase extends org.semanticwb.repository.BaseNode implements org.semanticwb.repository.Referenceable,org.semanticwb.repository.Traceable
{
    
    /** The Constant nt_Version. */
    public static final org.semanticwb.platform.SemanticClass nt_Version=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#version");
    
    /** The Constant jcr_successors. */
    public static final org.semanticwb.platform.SemanticProperty jcr_successors=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#successors");
    
    /** The Constant jcr_predecessors. */
    public static final org.semanticwb.platform.SemanticProperty jcr_predecessors=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#predecessors");
    
    /** The Constant sclass. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#version");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {

        /**
         * List versions.
         * 
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.repository.Version> listVersions(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.repository.Version>(it, true);
        }

        /**
         * List versions.
         * 
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.repository.Version> listVersions()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.repository.Version>(it, true);
        }

        /**
         * Gets the version.
         * 
         * @param id the id
         * @param model the model
         * @return the version
         */
        public static org.semanticwb.repository.Version getVersion(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.repository.Version)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        /**
         * Creates the version.
         * 
         * @param id the id
         * @param model the model
         * @return the org.semanticwb.repository. version
         */
        public static org.semanticwb.repository.Version createVersion(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.repository.Version)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        /**
         * Removes the version.
         * 
         * @param id the id
         * @param model the model
         */
        public static void removeVersion(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        /**
         * Checks for version.
         * 
         * @param id the id
         * @param model the model
         * @return true, if successful
         */
        public static boolean hasVersion(String id, org.semanticwb.model.SWBModel model)
        {
            return (getVersion(id, model)!=null);
        }

        /**
         * List version by parent.
         * 
         * @param parentnode the parentnode
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.repository.Version> listVersionByParent(org.semanticwb.repository.BaseNode parentnode,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.repository.Version> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbrep_parentNode, parentnode.getSemanticObject()));
            return it;
        }

        /**
         * List version by parent.
         * 
         * @param parentnode the parentnode
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.repository.Version> listVersionByParent(org.semanticwb.repository.BaseNode parentnode)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.repository.Version> it=new org.semanticwb.model.GenericIterator(parentnode.getSemanticObject().getModel().listSubjects(swbrep_parentNode,parentnode.getSemanticObject()));
            return it;
        }

        /**
         * List version by successors.
         * 
         * @param successors the successors
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.repository.Version> listVersionBySuccessors(org.semanticwb.repository.Version successors,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.repository.Version> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(jcr_successors, successors.getSemanticObject()));
            return it;
        }

        /**
         * List version by successors.
         * 
         * @param successors the successors
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.repository.Version> listVersionBySuccessors(org.semanticwb.repository.Version successors)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.repository.Version> it=new org.semanticwb.model.GenericIterator(successors.getSemanticObject().getModel().listSubjects(jcr_successors,successors.getSemanticObject()));
            return it;
        }

        /**
         * List version by predecessors.
         * 
         * @param predecessors the predecessors
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.repository.Version> listVersionByPredecessors(org.semanticwb.repository.Version predecessors,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.repository.Version> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(jcr_predecessors, predecessors.getSemanticObject()));
            return it;
        }

        /**
         * List version by predecessors.
         * 
         * @param predecessors the predecessors
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.repository.Version> listVersionByPredecessors(org.semanticwb.repository.Version predecessors)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.repository.Version> it=new org.semanticwb.model.GenericIterator(predecessors.getSemanticObject().getModel().listSubjects(jcr_predecessors,predecessors.getSemanticObject()));
            return it;
        }

        /**
         * List version by node.
         * 
         * @param hasnodes the hasnodes
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.repository.Version> listVersionByNode(org.semanticwb.repository.BaseNode hasnodes,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.repository.Version> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbrep_hasNodes, hasnodes.getSemanticObject()));
            return it;
        }

        /**
         * List version by node.
         * 
         * @param hasnodes the hasnodes
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.repository.Version> listVersionByNode(org.semanticwb.repository.BaseNode hasnodes)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.repository.Version> it=new org.semanticwb.model.GenericIterator(hasnodes.getSemanticObject().getModel().listSubjects(swbrep_hasNodes,hasnodes.getSemanticObject()));
            return it;
        }
    }

    /**
     * Instantiates a new version base.
     * 
     * @param base the base
     */
    public VersionBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.repository.base.TraceableBase#getCreated()
     */
    public java.util.Date getCreated()
    {
        return getSemanticObject().getDateProperty(jcr_created);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.repository.base.TraceableBase#setCreated(java.util.Date)
     */
    public void setCreated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(jcr_created, value);
    }

    /**
     * Sets the successors.
     * 
     * @param value the new successors
     */
    public void setSuccessors(org.semanticwb.repository.Version value)
    {
        getSemanticObject().setObjectProperty(jcr_successors, value.getSemanticObject());
    }

    /**
     * Removes the successors.
     */
    public void removeSuccessors()
    {
        getSemanticObject().removeProperty(jcr_successors);
    }

    /**
     * Gets the successors.
     * 
     * @return the successors
     */
    public org.semanticwb.repository.Version getSuccessors()
    {
         org.semanticwb.repository.Version ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(jcr_successors);
         if(obj!=null)
         {
             ret=(org.semanticwb.repository.Version)obj.createGenericInstance();
         }
         return ret;
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

    /**
     * Sets the predecessors.
     * 
     * @param value the new predecessors
     */
    public void setPredecessors(org.semanticwb.repository.Version value)
    {
        getSemanticObject().setObjectProperty(jcr_predecessors, value.getSemanticObject());
    }

    /**
     * Removes the predecessors.
     */
    public void removePredecessors()
    {
        getSemanticObject().removeProperty(jcr_predecessors);
    }

    /**
     * Gets the predecessors.
     * 
     * @return the predecessors
     */
    public org.semanticwb.repository.Version getPredecessors()
    {
         org.semanticwb.repository.Version ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(jcr_predecessors);
         if(obj!=null)
         {
             ret=(org.semanticwb.repository.Version)obj.createGenericInstance();
         }
         return ret;
    }
}
