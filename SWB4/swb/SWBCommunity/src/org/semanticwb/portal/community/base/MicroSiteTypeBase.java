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
package org.semanticwb.portal.community.base;


public abstract class MicroSiteTypeBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Traceable,org.semanticwb.model.Templateable,org.semanticwb.model.Activeable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass swbcomm_MicroSiteUtil=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#MicroSiteUtil");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_hasMicroSiteUtil=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#hasMicroSiteUtil");
    public static final org.semanticwb.platform.SemanticClass swbcomm_MicroSiteClass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#MicroSiteClass");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_microSiteClass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#microSiteClass");
    public static final org.semanticwb.platform.SemanticClass swbcomm_MicroSiteType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#MicroSiteType");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#MicroSiteType");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.portal.community.MicroSiteType> listMicroSiteTypes(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSiteType>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.portal.community.MicroSiteType> listMicroSiteTypes()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSiteType>(it, true);
        }

        public static org.semanticwb.portal.community.MicroSiteType getMicroSiteType(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.community.MicroSiteType)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.portal.community.MicroSiteType createMicroSiteType(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.community.MicroSiteType)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeMicroSiteType(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasMicroSiteType(String id, org.semanticwb.model.SWBModel model)
        {
            return (getMicroSiteType(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.portal.community.MicroSiteType> listMicroSiteTypeByTemplate(org.semanticwb.model.Template hastemplate,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSiteType> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_hasTemplate, hastemplate.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.MicroSiteType> listMicroSiteTypeByTemplate(org.semanticwb.model.Template hastemplate)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSiteType> it=new org.semanticwb.model.GenericIterator(hastemplate.getSemanticObject().getModel().listSubjects(swb_hasTemplate,hastemplate.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.MicroSiteType> listMicroSiteTypeByMicroSiteUtil(org.semanticwb.portal.community.MicroSiteUtil hasmicrositeutil,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSiteType> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbcomm_hasMicroSiteUtil, hasmicrositeutil.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.MicroSiteType> listMicroSiteTypeByMicroSiteUtil(org.semanticwb.portal.community.MicroSiteUtil hasmicrositeutil)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSiteType> it=new org.semanticwb.model.GenericIterator(hasmicrositeutil.getSemanticObject().getModel().listSubjects(swbcomm_hasMicroSiteUtil,hasmicrositeutil.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.MicroSiteType> listMicroSiteTypeByModifiedBy(org.semanticwb.model.User modifiedby,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSiteType> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_modifiedBy, modifiedby.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.MicroSiteType> listMicroSiteTypeByModifiedBy(org.semanticwb.model.User modifiedby)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSiteType> it=new org.semanticwb.model.GenericIterator(modifiedby.getSemanticObject().getModel().listSubjects(swb_modifiedBy,modifiedby.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.MicroSiteType> listMicroSiteTypeByMicroSiteClass(org.semanticwb.portal.community.MicroSiteClass micrositeclass,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSiteType> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbcomm_microSiteClass, micrositeclass.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.MicroSiteType> listMicroSiteTypeByMicroSiteClass(org.semanticwb.portal.community.MicroSiteClass micrositeclass)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSiteType> it=new org.semanticwb.model.GenericIterator(micrositeclass.getSemanticObject().getModel().listSubjects(swbcomm_microSiteClass,micrositeclass.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.MicroSiteType> listMicroSiteTypeByCreator(org.semanticwb.model.User creator,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSiteType> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_creator, creator.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.MicroSiteType> listMicroSiteTypeByCreator(org.semanticwb.model.User creator)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSiteType> it=new org.semanticwb.model.GenericIterator(creator.getSemanticObject().getModel().listSubjects(swb_creator,creator.getSemanticObject()));
            return it;
        }
    }

    public MicroSiteTypeBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.Template> listTemplates()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Template>(getSemanticObject().listObjectProperties(swb_hasTemplate));
    }

    public boolean hasTemplate(org.semanticwb.model.Template template)
    {
        boolean ret=false;
        if(template!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasTemplate,template.getSemanticObject());
        }
        return ret;
    }

    public void addTemplate(org.semanticwb.model.Template value)
    {
        getSemanticObject().addObjectProperty(swb_hasTemplate, value.getSemanticObject());
    }

    public void removeAllTemplate()
    {
        getSemanticObject().removeProperty(swb_hasTemplate);
    }

    public void removeTemplate(org.semanticwb.model.Template template)
    {
        getSemanticObject().removeObjectProperty(swb_hasTemplate,template.getSemanticObject());
    }

    public org.semanticwb.model.Template getTemplate()
    {
         org.semanticwb.model.Template ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasTemplate);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Template)obj.createGenericInstance();
         }
         return ret;
    }

    public boolean isActive()
    {
        return getSemanticObject().getBooleanProperty(swb_active);
    }

    public void setActive(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_active, value);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSiteUtil> listMicroSiteUtils()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSiteUtil>(getSemanticObject().listObjectProperties(swbcomm_hasMicroSiteUtil));
    }

    public boolean hasMicroSiteUtil(org.semanticwb.portal.community.MicroSiteUtil micrositeutil)
    {
        boolean ret=false;
        if(micrositeutil!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swbcomm_hasMicroSiteUtil,micrositeutil.getSemanticObject());
        }
        return ret;
    }

    public void addMicroSiteUtil(org.semanticwb.portal.community.MicroSiteUtil value)
    {
        getSemanticObject().addObjectProperty(swbcomm_hasMicroSiteUtil, value.getSemanticObject());
    }

    public void removeAllMicroSiteUtil()
    {
        getSemanticObject().removeProperty(swbcomm_hasMicroSiteUtil);
    }

    public void removeMicroSiteUtil(org.semanticwb.portal.community.MicroSiteUtil micrositeutil)
    {
        getSemanticObject().removeObjectProperty(swbcomm_hasMicroSiteUtil,micrositeutil.getSemanticObject());
    }

    public org.semanticwb.portal.community.MicroSiteUtil getMicroSiteUtil()
    {
         org.semanticwb.portal.community.MicroSiteUtil ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbcomm_hasMicroSiteUtil);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.community.MicroSiteUtil)obj.createGenericInstance();
         }
         return ret;
    }

    public java.util.Date getCreated()
    {
        return getSemanticObject().getDateProperty(swb_created);
    }

    public void setCreated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_created, value);
    }

    public void setModifiedBy(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(swb_modifiedBy, value.getSemanticObject());
    }

    public void removeModifiedBy()
    {
        getSemanticObject().removeProperty(swb_modifiedBy);
    }

    public org.semanticwb.model.User getModifiedBy()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_modifiedBy);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

    public String getTitle()
    {
        return getSemanticObject().getProperty(swb_title);
    }

    public void setTitle(String value)
    {
        getSemanticObject().setProperty(swb_title, value);
    }

    public String getTitle(String lang)
    {
        return getSemanticObject().getProperty(swb_title, null, lang);
    }

    public String getDisplayTitle(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_title, lang);
    }

    public void setTitle(String title, String lang)
    {
        getSemanticObject().setProperty(swb_title, title, lang);
    }

    public java.util.Date getUpdated()
    {
        return getSemanticObject().getDateProperty(swb_updated);
    }

    public void setUpdated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_updated, value);
    }

    public void setMicroSiteClass(org.semanticwb.portal.community.MicroSiteClass value)
    {
        getSemanticObject().setObjectProperty(swbcomm_microSiteClass, value.getSemanticObject());
    }

    public void removeMicroSiteClass()
    {
        getSemanticObject().removeProperty(swbcomm_microSiteClass);
    }

    public org.semanticwb.portal.community.MicroSiteClass getMicroSiteClass()
    {
         org.semanticwb.portal.community.MicroSiteClass ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbcomm_microSiteClass);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.community.MicroSiteClass)obj.createGenericInstance();
         }
         return ret;
    }

    public void setCreator(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(swb_creator, value.getSemanticObject());
    }

    public void removeCreator()
    {
        getSemanticObject().removeProperty(swb_creator);
    }

    public org.semanticwb.model.User getCreator()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_creator);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

    public String getDescription()
    {
        return getSemanticObject().getProperty(swb_description);
    }

    public void setDescription(String value)
    {
        getSemanticObject().setProperty(swb_description, value);
    }

    public String getDescription(String lang)
    {
        return getSemanticObject().getProperty(swb_description, null, lang);
    }

    public String getDisplayDescription(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_description, lang);
    }

    public void setDescription(String description, String lang)
    {
        getSemanticObject().setProperty(swb_description, description, lang);
    }

    public org.semanticwb.portal.community.MicrositeContainer getMicrositeContainer()
    {
        return (org.semanticwb.portal.community.MicrositeContainer)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
