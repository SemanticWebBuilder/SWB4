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
 * The Class WorkspaceBase.
 */
public abstract class WorkspaceBase extends org.semanticwb.model.SWBModel implements org.semanticwb.model.Descriptiveable
{
    
    /** The Constant nt_BaseNode. */
    public static final org.semanticwb.platform.SemanticClass nt_BaseNode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#base");
    
    /** The Constant jcr_root. */
    public static final org.semanticwb.platform.SemanticProperty jcr_root=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#root");
    
    /** The Constant nt_Unstructured. */
    public static final org.semanticwb.platform.SemanticClass nt_Unstructured=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#unstructured");
    
    /** The Constant swbrep_Workspace. */
    public static final org.semanticwb.platform.SemanticClass swbrep_Workspace=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/repository#Workspace");
    
    /** The Constant sclass. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/repository#Workspace");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {

        /**
         * List workspaces.
         * 
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.repository.Workspace> listWorkspaces(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.repository.Workspace>(it, true);
        }

        /**
         * List workspaces.
         * 
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.repository.Workspace> listWorkspaces()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.repository.Workspace>(it, true);
        }

        /**
         * Gets the workspace.
         * 
         * @param id the id
         * @return the workspace
         */
        public static org.semanticwb.repository.Workspace getWorkspace(String id)
        {
            org.semanticwb.platform.SemanticMgr mgr=org.semanticwb.SWBPlatform.getSemanticMgr();
            org.semanticwb.repository.Workspace ret=null;
            org.semanticwb.platform.SemanticModel model=mgr.getModel(id);
            if(model!=null)
            {
                org.semanticwb.platform.SemanticObject obj=model.getSemanticObject(model.getObjectUri(id,sclass));
                if(obj!=null)
                {
                    ret=(org.semanticwb.repository.Workspace)obj.createGenericInstance();
                }
            }
            return ret;
        }

        /**
         * Creates the workspace.
         * 
         * @param id the id
         * @param namespace the namespace
         * @return the org.semanticwb.repository. workspace
         */
        public static org.semanticwb.repository.Workspace createWorkspace(String id, String namespace)
        {
            org.semanticwb.platform.SemanticMgr mgr=org.semanticwb.SWBPlatform.getSemanticMgr();
            org.semanticwb.platform.SemanticModel model=mgr.createModel(id, namespace);
            return (org.semanticwb.repository.Workspace)model.createGenericObject(model.getObjectUri(id, sclass), sclass);
        }

        /**
         * Removes the workspace.
         * 
         * @param id the id
         */
        public static void removeWorkspace(String id)
        {
            org.semanticwb.repository.Workspace obj=getWorkspace(id);
            if(obj!=null)
            {
                obj.remove();
            }
        }

        /**
         * Checks for workspace.
         * 
         * @param id the id
         * @return true, if successful
         */
        public static boolean hasWorkspace(String id)
        {
            return (getWorkspace(id)!=null);
        }

        /**
         * List workspace by parent web site.
         * 
         * @param parentwebsite the parentwebsite
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.repository.Workspace> listWorkspaceByParentWebSite(org.semanticwb.model.WebSite parentwebsite,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.repository.Workspace> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_parentWebSite, parentwebsite.getSemanticObject()));
            return it;
        }

        /**
         * List workspace by parent web site.
         * 
         * @param parentwebsite the parentwebsite
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.repository.Workspace> listWorkspaceByParentWebSite(org.semanticwb.model.WebSite parentwebsite)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.repository.Workspace> it=new org.semanticwb.model.GenericIterator(parentwebsite.getSemanticObject().getModel().listSubjects(swb_parentWebSite,parentwebsite.getSemanticObject()));
            return it;
        }

        /**
         * List workspace by root.
         * 
         * @param root the root
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.repository.Workspace> listWorkspaceByRoot(org.semanticwb.repository.BaseNode root,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.repository.Workspace> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(jcr_root, root.getSemanticObject()));
            return it;
        }

        /**
         * List workspace by root.
         * 
         * @param root the root
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.repository.Workspace> listWorkspaceByRoot(org.semanticwb.repository.BaseNode root)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.repository.Workspace> it=new org.semanticwb.model.GenericIterator(root.getSemanticObject().getModel().listSubjects(jcr_root,root.getSemanticObject()));
            return it;
        }
    }

    /**
     * Instantiates a new workspace base.
     * 
     * @param base the base
     */
    public WorkspaceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
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

    /**
     * Sets the root.
     * 
     * @param value the new root
     */
    public void setRoot(org.semanticwb.repository.BaseNode value)
    {
        getSemanticObject().setObjectProperty(jcr_root, value.getSemanticObject());
    }

    /**
     * Removes the root.
     */
    public void removeRoot()
    {
        getSemanticObject().removeProperty(jcr_root);
    }

    /**
     * Gets the root.
     * 
     * @return the root
     */
    public org.semanticwb.repository.BaseNode getRoot()
    {
         org.semanticwb.repository.BaseNode ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(jcr_root);
         if(obj!=null)
         {
             ret=(org.semanticwb.repository.BaseNode)obj.createGenericInstance();
         }
         return ret;
    }

    /**
     * Gets the base node.
     * 
     * @param id the id
     * @return the base node
     */
    public org.semanticwb.repository.BaseNode getBaseNode(String id)
    {
        return org.semanticwb.repository.BaseNode.ClassMgr.getBaseNode(id, this);
    }

    /**
     * List base nodes.
     * 
     * @return the java.util. iterator
     */
    public java.util.Iterator<org.semanticwb.repository.BaseNode> listBaseNodes()
    {
        return org.semanticwb.repository.BaseNode.ClassMgr.listBaseNodes(this);
    }

    /**
     * Creates the base node.
     * 
     * @param id the id
     * @return the org.semanticwb.repository. base node
     */
    public org.semanticwb.repository.BaseNode createBaseNode(String id)
    {
        return org.semanticwb.repository.BaseNode.ClassMgr.createBaseNode(id,this);
    }

    /**
     * Removes the base node.
     * 
     * @param id the id
     */
    public void removeBaseNode(String id)
    {
        org.semanticwb.repository.BaseNode.ClassMgr.removeBaseNode(id, this);
    }
    
    /**
     * Checks for base node.
     * 
     * @param id the id
     * @return true, if successful
     */
    public boolean hasBaseNode(String id)
    {
        return org.semanticwb.repository.BaseNode.ClassMgr.hasBaseNode(id, this);
    }

    /**
     * Gets the unstructured.
     * 
     * @param id the id
     * @return the unstructured
     */
    public org.semanticwb.repository.Unstructured getUnstructured(String id)
    {
        return org.semanticwb.repository.Unstructured.ClassMgr.getUnstructured(id, this);
    }

    /**
     * List unstructureds.
     * 
     * @return the java.util. iterator
     */
    public java.util.Iterator<org.semanticwb.repository.Unstructured> listUnstructureds()
    {
        return org.semanticwb.repository.Unstructured.ClassMgr.listUnstructureds(this);
    }

    /**
     * Creates the unstructured.
     * 
     * @param id the id
     * @return the org.semanticwb.repository. unstructured
     */
    public org.semanticwb.repository.Unstructured createUnstructured(String id)
    {
        return org.semanticwb.repository.Unstructured.ClassMgr.createUnstructured(id,this);
    }

    /**
     * Creates the unstructured.
     * 
     * @return the org.semanticwb.repository. unstructured
     */
    public org.semanticwb.repository.Unstructured createUnstructured()
    {
        long id=getSemanticObject().getModel().getCounter(nt_Unstructured);
        return org.semanticwb.repository.Unstructured.ClassMgr.createUnstructured(String.valueOf(id),this);
    } 

    /**
     * Removes the unstructured.
     * 
     * @param id the id
     */
    public void removeUnstructured(String id)
    {
        org.semanticwb.repository.Unstructured.ClassMgr.removeUnstructured(id, this);
    }
    
    /**
     * Checks for unstructured.
     * 
     * @param id the id
     * @return true, if successful
     */
    public boolean hasUnstructured(String id)
    {
        return org.semanticwb.repository.Unstructured.ClassMgr.hasUnstructured(id, this);
    }
}
