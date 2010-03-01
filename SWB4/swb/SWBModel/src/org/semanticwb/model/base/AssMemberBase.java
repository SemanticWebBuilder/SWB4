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
 * The Class AssMemberBase.
 */
public abstract class AssMemberBase extends org.semanticwb.model.SWBClass 
{
    
    /** The Constant swb_Topic. */
    public static final org.semanticwb.platform.SemanticClass swb_Topic=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Topic");
    
    /** The Constant swb_assMember. */
    public static final org.semanticwb.platform.SemanticProperty swb_assMember=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#assMember");
    
    /** The Constant swb_assRole. */
    public static final org.semanticwb.platform.SemanticProperty swb_assRole=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#assRole");
    
    /** The Constant swb_Association. */
    public static final org.semanticwb.platform.SemanticClass swb_Association=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Association");
    
    /** The Constant swb_associationInv. */
    public static final org.semanticwb.platform.SemanticProperty swb_associationInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#associationInv");
    
    /** The Constant swb_AssMember. */
    public static final org.semanticwb.platform.SemanticClass swb_AssMember=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#AssMember");
    
    /** The Constant sclass. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#AssMember");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {

        /**
         * List ass members.
         * 
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.AssMember> listAssMembers(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.AssMember>(it, true);
        }

        /**
         * List ass members.
         * 
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.AssMember> listAssMembers()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.AssMember>(it, true);
        }

        /**
         * Creates the ass member.
         * 
         * @param model the model
         * @return the org.semanticwb.model. ass member
         */
        public static org.semanticwb.model.AssMember createAssMember(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.model.AssMember.ClassMgr.createAssMember(String.valueOf(id), model);
        }

        /**
         * Gets the ass member.
         * 
         * @param id the id
         * @param model the model
         * @return the ass member
         */
        public static org.semanticwb.model.AssMember getAssMember(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.AssMember)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        /**
         * Creates the ass member.
         * 
         * @param id the id
         * @param model the model
         * @return the org.semanticwb.model. ass member
         */
        public static org.semanticwb.model.AssMember createAssMember(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.AssMember)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        /**
         * Removes the ass member.
         * 
         * @param id the id
         * @param model the model
         */
        public static void removeAssMember(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        /**
         * Checks for ass member.
         * 
         * @param id the id
         * @param model the model
         * @return true, if successful
         */
        public static boolean hasAssMember(String id, org.semanticwb.model.SWBModel model)
        {
            return (getAssMember(id, model)!=null);
        }

        /**
         * List ass member by member.
         * 
         * @param assmember the assmember
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.AssMember> listAssMemberByMember(org.semanticwb.model.Topic assmember,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.AssMember> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_assMember, assmember.getSemanticObject()));
            return it;
        }

        /**
         * List ass member by member.
         * 
         * @param assmember the assmember
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.AssMember> listAssMemberByMember(org.semanticwb.model.Topic assmember)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.AssMember> it=new org.semanticwb.model.GenericIterator(assmember.getSemanticObject().getModel().listSubjects(swb_assMember,assmember.getSemanticObject()));
            return it;
        }

        /**
         * List ass member by role.
         * 
         * @param assrole the assrole
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.AssMember> listAssMemberByRole(org.semanticwb.model.Topic assrole,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.AssMember> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_assRole, assrole.getSemanticObject()));
            return it;
        }

        /**
         * List ass member by role.
         * 
         * @param assrole the assrole
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.AssMember> listAssMemberByRole(org.semanticwb.model.Topic assrole)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.AssMember> it=new org.semanticwb.model.GenericIterator(assrole.getSemanticObject().getModel().listSubjects(swb_assRole,assrole.getSemanticObject()));
            return it;
        }

        /**
         * List ass member by association.
         * 
         * @param associationinv the associationinv
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.AssMember> listAssMemberByAssociation(org.semanticwb.model.Association associationinv,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.AssMember> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_associationInv, associationinv.getSemanticObject()));
            return it;
        }

        /**
         * List ass member by association.
         * 
         * @param associationinv the associationinv
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.AssMember> listAssMemberByAssociation(org.semanticwb.model.Association associationinv)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.AssMember> it=new org.semanticwb.model.GenericIterator(associationinv.getSemanticObject().getModel().listSubjects(swb_associationInv,associationinv.getSemanticObject()));
            return it;
        }
    }

    /**
     * Instantiates a new ass member base.
     * 
     * @param base the base
     */
    public AssMemberBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /**
     * Sets the member.
     * 
     * @param value the new member
     */
    public void setMember(org.semanticwb.model.Topic value)
    {
        getSemanticObject().setObjectProperty(swb_assMember, value.getSemanticObject());
    }

    /**
     * Removes the member.
     */
    public void removeMember()
    {
        getSemanticObject().removeProperty(swb_assMember);
    }

    /**
     * Gets the member.
     * 
     * @return the member
     */
    public org.semanticwb.model.Topic getMember()
    {
         org.semanticwb.model.Topic ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_assMember);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Topic)obj.createGenericInstance();
         }
         return ret;
    }

    /**
     * Sets the role.
     * 
     * @param value the new role
     */
    public void setRole(org.semanticwb.model.Topic value)
    {
        getSemanticObject().setObjectProperty(swb_assRole, value.getSemanticObject());
    }

    /**
     * Removes the role.
     */
    public void removeRole()
    {
        getSemanticObject().removeProperty(swb_assRole);
    }

    /**
     * Gets the role.
     * 
     * @return the role
     */
    public org.semanticwb.model.Topic getRole()
    {
         org.semanticwb.model.Topic ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_assRole);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Topic)obj.createGenericInstance();
         }
         return ret;
    }

    /**
     * Sets the association.
     * 
     * @param value the new association
     */
    public void setAssociation(org.semanticwb.model.Association value)
    {
        getSemanticObject().setObjectProperty(swb_associationInv, value.getSemanticObject());
    }

    /**
     * Removes the association.
     */
    public void removeAssociation()
    {
        getSemanticObject().removeProperty(swb_associationInv);
    }

    /**
     * Gets the association.
     * 
     * @return the association
     */
    public org.semanticwb.model.Association getAssociation()
    {
         org.semanticwb.model.Association ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_associationInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Association)obj.createGenericInstance();
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
