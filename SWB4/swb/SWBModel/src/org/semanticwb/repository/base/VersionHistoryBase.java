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
 * The Class VersionHistoryBase.
 */
public abstract class VersionHistoryBase extends org.semanticwb.repository.BaseNode implements org.semanticwb.repository.Referenceable
{
    
    /** The Constant jcr_versionableUuid. */
    public static final org.semanticwb.platform.SemanticProperty jcr_versionableUuid=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#versionableUuid");
    
    /** The Constant nt_VersionHistory. */
    public static final org.semanticwb.platform.SemanticClass nt_VersionHistory=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#versionHistory");
    
    /** The Constant sclass. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#versionHistory");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {

        /**
         * List version histories.
         * 
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.repository.VersionHistory> listVersionHistories(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.repository.VersionHistory>(it, true);
        }

        /**
         * List version histories.
         * 
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.repository.VersionHistory> listVersionHistories()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.repository.VersionHistory>(it, true);
        }

        /**
         * Gets the version history.
         * 
         * @param id the id
         * @param model the model
         * @return the version history
         */
        public static org.semanticwb.repository.VersionHistory getVersionHistory(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.repository.VersionHistory)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        /**
         * Creates the version history.
         * 
         * @param id the id
         * @param model the model
         * @return the org.semanticwb.repository. version history
         */
        public static org.semanticwb.repository.VersionHistory createVersionHistory(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.repository.VersionHistory)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        /**
         * Removes the version history.
         * 
         * @param id the id
         * @param model the model
         */
        public static void removeVersionHistory(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        /**
         * Checks for version history.
         * 
         * @param id the id
         * @param model the model
         * @return true, if successful
         */
        public static boolean hasVersionHistory(String id, org.semanticwb.model.SWBModel model)
        {
            return (getVersionHistory(id, model)!=null);
        }

        /**
         * List version history by parent.
         * 
         * @param parentnode the parentnode
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.repository.VersionHistory> listVersionHistoryByParent(org.semanticwb.repository.BaseNode parentnode,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.repository.VersionHistory> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbrep_parentNode, parentnode.getSemanticObject()));
            return it;
        }

        /**
         * List version history by parent.
         * 
         * @param parentnode the parentnode
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.repository.VersionHistory> listVersionHistoryByParent(org.semanticwb.repository.BaseNode parentnode)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.repository.VersionHistory> it=new org.semanticwb.model.GenericIterator(parentnode.getSemanticObject().getModel().listSubjects(swbrep_parentNode,parentnode.getSemanticObject()));
            return it;
        }

        /**
         * List version history by node.
         * 
         * @param hasnodes the hasnodes
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.repository.VersionHistory> listVersionHistoryByNode(org.semanticwb.repository.BaseNode hasnodes,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.repository.VersionHistory> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbrep_hasNodes, hasnodes.getSemanticObject()));
            return it;
        }

        /**
         * List version history by node.
         * 
         * @param hasnodes the hasnodes
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.repository.VersionHistory> listVersionHistoryByNode(org.semanticwb.repository.BaseNode hasnodes)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.repository.VersionHistory> it=new org.semanticwb.model.GenericIterator(hasnodes.getSemanticObject().getModel().listSubjects(swbrep_hasNodes,hasnodes.getSemanticObject()));
            return it;
        }
    }

    /**
     * Instantiates a new version history base.
     * 
     * @param base the base
     */
    public VersionHistoryBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /**
     * Gets the versionable uuid.
     * 
     * @return the versionable uuid
     */
    public String getVersionableUuid()
    {
        return getSemanticObject().getProperty(jcr_versionableUuid);
    }

    /**
     * Sets the versionable uuid.
     * 
     * @param value the new versionable uuid
     */
    public void setVersionableUuid(String value)
    {
        getSemanticObject().setProperty(jcr_versionableUuid, value);
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
