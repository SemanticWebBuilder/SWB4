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
 * The Class AssociationBase.
 */
public abstract class AssociationBase extends org.semanticwb.model.SWBClass 
{
    
    /** The Constant swb_AssMember. */
    public static final org.semanticwb.platform.SemanticClass swb_AssMember=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#AssMember");
    
    /** The Constant swb_hasMember. */
    public static final org.semanticwb.platform.SemanticProperty swb_hasMember=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasMember");
    
    /** The Constant swb_Topic. */
    public static final org.semanticwb.platform.SemanticClass swb_Topic=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Topic");
    
    /** The Constant swb_assType. */
    public static final org.semanticwb.platform.SemanticProperty swb_assType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#assType");
    
    /** The Constant swb_Association. */
    public static final org.semanticwb.platform.SemanticClass swb_Association=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Association");
    
    /** The Constant sclass. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Association");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {

        /**
         * List associations.
         * 
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Association> listAssociations(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Association>(it, true);
        }

        /**
         * List associations.
         * 
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Association> listAssociations()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Association>(it, true);
        }

        /**
         * Creates the association.
         * 
         * @param model the model
         * @return the org.semanticwb.model. association
         */
        public static org.semanticwb.model.Association createAssociation(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.model.Association.ClassMgr.createAssociation(String.valueOf(id), model);
        }

        /**
         * Gets the association.
         * 
         * @param id the id
         * @param model the model
         * @return the association
         */
        public static org.semanticwb.model.Association getAssociation(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.Association)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        /**
         * Creates the association.
         * 
         * @param id the id
         * @param model the model
         * @return the org.semanticwb.model. association
         */
        public static org.semanticwb.model.Association createAssociation(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.Association)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        /**
         * Removes the association.
         * 
         * @param id the id
         * @param model the model
         */
        public static void removeAssociation(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        /**
         * Checks for association.
         * 
         * @param id the id
         * @param model the model
         * @return true, if successful
         */
        public static boolean hasAssociation(String id, org.semanticwb.model.SWBModel model)
        {
            return (getAssociation(id, model)!=null);
        }

        /**
         * List association by member.
         * 
         * @param hasmember the hasmember
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Association> listAssociationByMember(org.semanticwb.model.AssMember hasmember,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Association> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_hasMember, hasmember.getSemanticObject()));
            return it;
        }

        /**
         * List association by member.
         * 
         * @param hasmember the hasmember
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Association> listAssociationByMember(org.semanticwb.model.AssMember hasmember)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Association> it=new org.semanticwb.model.GenericIterator(hasmember.getSemanticObject().getModel().listSubjects(swb_hasMember,hasmember.getSemanticObject()));
            return it;
        }

        /**
         * List association by type.
         * 
         * @param asstype the asstype
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Association> listAssociationByType(org.semanticwb.model.Topic asstype,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Association> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_assType, asstype.getSemanticObject()));
            return it;
        }

        /**
         * List association by type.
         * 
         * @param asstype the asstype
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.Association> listAssociationByType(org.semanticwb.model.Topic asstype)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Association> it=new org.semanticwb.model.GenericIterator(asstype.getSemanticObject().getModel().listSubjects(swb_assType,asstype.getSemanticObject()));
            return it;
        }
    }

    /**
     * Instantiates a new association base.
     * 
     * @param base the base
     */
    public AssociationBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /**
     * List members.
     * 
     * @return the org.semanticwb.model. generic iterator
     */
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.AssMember> listMembers()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.AssMember>(getSemanticObject().listObjectProperties(swb_hasMember));
    }

    /**
     * Checks for member.
     * 
     * @param assmember the assmember
     * @return true, if successful
     */
    public boolean hasMember(org.semanticwb.model.AssMember assmember)
    {
        boolean ret=false;
        if(assmember!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasMember,assmember.getSemanticObject());
        }
        return ret;
    }

    /**
     * Adds the member.
     * 
     * @param value the value
     */
    public void addMember(org.semanticwb.model.AssMember value)
    {
        getSemanticObject().addObjectProperty(swb_hasMember, value.getSemanticObject());
    }

    /**
     * Removes the all member.
     */
    public void removeAllMember()
    {
        getSemanticObject().removeProperty(swb_hasMember);
    }

    /**
     * Removes the member.
     * 
     * @param assmember the assmember
     */
    public void removeMember(org.semanticwb.model.AssMember assmember)
    {
        getSemanticObject().removeObjectProperty(swb_hasMember,assmember.getSemanticObject());
    }

    /**
     * Gets the member.
     * 
     * @return the member
     */
    public org.semanticwb.model.AssMember getMember()
    {
         org.semanticwb.model.AssMember ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasMember);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.AssMember)obj.createGenericInstance();
         }
         return ret;
    }

    /**
     * Sets the type.
     * 
     * @param value the new type
     */
    public void setType(org.semanticwb.model.Topic value)
    {
        getSemanticObject().setObjectProperty(swb_assType, value.getSemanticObject());
    }

    /**
     * Removes the type.
     */
    public void removeType()
    {
        getSemanticObject().removeProperty(swb_assType);
    }

    /**
     * Gets the type.
     * 
     * @return the type
     */
    public org.semanticwb.model.Topic getType()
    {
         org.semanticwb.model.Topic ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_assType);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Topic)obj.createGenericInstance();
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
