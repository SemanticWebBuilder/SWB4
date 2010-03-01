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
 * The Class RoleRefBase.
 */
public abstract class RoleRefBase extends org.semanticwb.model.Reference implements org.semanticwb.model.Activeable
{
    
    /** The Constant swb_Role. */
    public static final org.semanticwb.platform.SemanticClass swb_Role=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Role");
    
    /** The Constant swb_role. */
    public static final org.semanticwb.platform.SemanticProperty swb_role=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#role");
    
    /** The Constant swb_RoleRef. */
    public static final org.semanticwb.platform.SemanticClass swb_RoleRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#RoleRef");
    
    /** The Constant sclass. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#RoleRef");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {

        /**
         * List role refs.
         * 
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.RoleRef> listRoleRefs(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.RoleRef>(it, true);
        }

        /**
         * List role refs.
         * 
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.RoleRef> listRoleRefs()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.RoleRef>(it, true);
        }

        /**
         * Creates the role ref.
         * 
         * @param model the model
         * @return the org.semanticwb.model. role ref
         */
        public static org.semanticwb.model.RoleRef createRoleRef(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.model.RoleRef.ClassMgr.createRoleRef(String.valueOf(id), model);
        }

        /**
         * Gets the role ref.
         * 
         * @param id the id
         * @param model the model
         * @return the role ref
         */
        public static org.semanticwb.model.RoleRef getRoleRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.RoleRef)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        /**
         * Creates the role ref.
         * 
         * @param id the id
         * @param model the model
         * @return the org.semanticwb.model. role ref
         */
        public static org.semanticwb.model.RoleRef createRoleRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.RoleRef)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        /**
         * Removes the role ref.
         * 
         * @param id the id
         * @param model the model
         */
        public static void removeRoleRef(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        /**
         * Checks for role ref.
         * 
         * @param id the id
         * @param model the model
         * @return true, if successful
         */
        public static boolean hasRoleRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (getRoleRef(id, model)!=null);
        }

        /**
         * List role ref by role.
         * 
         * @param role the role
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.RoleRef> listRoleRefByRole(org.semanticwb.model.Role role,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.RoleRef> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_role, role.getSemanticObject()));
            return it;
        }

        /**
         * List role ref by role.
         * 
         * @param role the role
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.RoleRef> listRoleRefByRole(org.semanticwb.model.Role role)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.RoleRef> it=new org.semanticwb.model.GenericIterator(role.getSemanticObject().getModel().listSubjects(swb_role,role.getSemanticObject()));
            return it;
        }
    }

    /**
     * Instantiates a new role ref base.
     * 
     * @param base the base
     */
    public RoleRefBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /**
     * Sets the role.
     * 
     * @param value the new role
     */
    public void setRole(org.semanticwb.model.Role value)
    {
        getSemanticObject().setObjectProperty(swb_role, value.getSemanticObject());
    }

    /**
     * Removes the role.
     */
    public void removeRole()
    {
        getSemanticObject().removeProperty(swb_role);
    }

    /**
     * Gets the role.
     * 
     * @return the role
     */
    public org.semanticwb.model.Role getRole()
    {
         org.semanticwb.model.Role ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_role);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Role)obj.createGenericInstance();
         }
         return ret;
    }

    /**
     * Gets the web site.
     * 
     * @return the web site
     */
    public org.semanticwb.model.WebSite getWebSite()
    {
        return (org.semanticwb.model.WebSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
