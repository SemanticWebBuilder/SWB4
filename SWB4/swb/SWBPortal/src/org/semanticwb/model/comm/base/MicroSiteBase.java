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
 
package org.semanticwb.model.comm.base;


// TODO: Auto-generated Javadoc
/**
 * The Class MicroSiteBase.
 */
public class MicroSiteBase extends org.semanticwb.model.WebPage implements org.semanticwb.model.Referensable,org.semanticwb.model.UserGroupRefable,org.semanticwb.model.CalendarRefable,org.semanticwb.model.Hiddenable,org.semanticwb.model.Undeleteable,org.semanticwb.model.RoleRefable,org.semanticwb.model.RuleRefable,org.semanticwb.model.Rankable,org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Trashable,org.semanticwb.model.Activeable,org.semanticwb.model.Viewable,org.semanticwb.model.Resourceable,org.semanticwb.model.PFlowRefable,org.semanticwb.model.Filterable,org.semanticwb.model.TemplateRefable,org.semanticwb.model.Indexable
{
    
    /** The Constant swb_WebPage. */
    public static final org.semanticwb.platform.SemanticClass swb_WebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebPage");
    
    /** The Constant swbcomm_about. */
    public static final org.semanticwb.platform.SemanticProperty swbcomm_about=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#about");
    
    /** The Constant swbcomm_Member. */
    public static final org.semanticwb.platform.SemanticClass swbcomm_Member=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#Member");
    
    /** The Constant swbcomm_hasMSMembersInv. */
    public static final org.semanticwb.platform.SemanticProperty swbcomm_hasMSMembersInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#hasMSMembersInv");
    
    /** The Constant swbc_LocationEntity. */
    public static final org.semanticwb.platform.SemanticClass swbc_LocationEntity=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/swbcatalogs#LocationEntity");
    
    /** The Constant swbcomm_locatedIn. */
    public static final org.semanticwb.platform.SemanticProperty swbcomm_locatedIn=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#locatedIn");
    
    /** The Constant swbcomm_MicroSiteType. */
    public static final org.semanticwb.platform.SemanticClass swbcomm_MicroSiteType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#MicroSiteType");
    
    /** The Constant swbcomm_type. */
    public static final org.semanticwb.platform.SemanticProperty swbcomm_type=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#type");
    
    /** The Constant swbcomm_MicroSite. */
    public static final org.semanticwb.platform.SemanticClass swbcomm_MicroSite=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#MicroSite");
    
    /** The Constant sclass. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#MicroSite");

    /**
     * Instantiates a new micro site base.
     * 
     * @param base the base
     */
    public MicroSiteBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /**
     * List micro sites.
     * 
     * @param model the model
     * @return the java.util. iterator
     */
    public static java.util.Iterator<org.semanticwb.model.comm.MicroSite> listMicroSites(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.comm.MicroSite>(it, true);
    }

    /**
     * List micro sites.
     * 
     * @return the java.util. iterator
     */
    public static java.util.Iterator<org.semanticwb.model.comm.MicroSite> listMicroSites()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.comm.MicroSite>(it, true);
    }

    /**
     * Gets the micro site.
     * 
     * @param id the id
     * @param model the model
     * @return the micro site
     */
    public static org.semanticwb.model.comm.MicroSite getMicroSite(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.model.comm.MicroSite)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    /**
     * Creates the micro site.
     * 
     * @param id the id
     * @param model the model
     * @return the org.semanticwb.model.comm. micro site
     */
    public static org.semanticwb.model.comm.MicroSite createMicroSite(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.model.comm.MicroSite)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    /**
     * Removes the micro site.
     * 
     * @param id the id
     * @param model the model
     */
    public static void removeMicroSite(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    /**
     * Checks for micro site.
     * 
     * @param id the id
     * @param model the model
     * @return true, if successful
     */
    public static boolean hasMicroSite(String id, org.semanticwb.model.SWBModel model)
    {
        return (getMicroSite(id, model)!=null);
    }

    /**
     * Sets the about.
     * 
     * @param webpage the new about
     */
    public void setAbout(org.semanticwb.model.WebPage webpage)
    {
        getSemanticObject().setObjectProperty(swbcomm_about, webpage.getSemanticObject());
    }

    /**
     * Removes the about.
     */
    public void removeAbout()
    {
        getSemanticObject().removeProperty(swbcomm_about);
    }

    /**
     * Gets the about.
     * 
     * @return the about
     */
    public org.semanticwb.model.WebPage getAbout()
    {
         org.semanticwb.model.WebPage ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbcomm_about);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.WebPage)obj.createGenericInstance();
         }
         return ret;
    }

    /**
     * Sets the located in.
     * 
     * @param locationentity the new located in
     */
    public void setLocatedIn(org.semanticwb.model.catalogs.LocationEntity locationentity)
    {
        getSemanticObject().setObjectProperty(swbcomm_locatedIn, locationentity.getSemanticObject());
    }

    /**
     * Removes the located in.
     */
    public void removeLocatedIn()
    {
        getSemanticObject().removeProperty(swbcomm_locatedIn);
    }

    /**
     * Gets the located in.
     * 
     * @return the located in
     */
    public org.semanticwb.model.catalogs.LocationEntity getLocatedIn()
    {
         org.semanticwb.model.catalogs.LocationEntity ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbcomm_locatedIn);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.catalogs.LocationEntity)obj.createGenericInstance();
         }
         return ret;
    }

    /**
     * Sets the type.
     * 
     * @param micrositetype the new type
     */
    public void setType(org.semanticwb.model.comm.MicroSiteType micrositetype)
    {
        getSemanticObject().setObjectProperty(swbcomm_type, micrositetype.getSemanticObject());
    }

    /**
     * Removes the type.
     */
    public void removeType()
    {
        getSemanticObject().removeProperty(swbcomm_type);
    }

    /**
     * Gets the type.
     * 
     * @return the type
     */
    public org.semanticwb.model.comm.MicroSiteType getType()
    {
         org.semanticwb.model.comm.MicroSiteType ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbcomm_type);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.comm.MicroSiteType)obj.createGenericInstance();
         }
         return ret;
    }
}
