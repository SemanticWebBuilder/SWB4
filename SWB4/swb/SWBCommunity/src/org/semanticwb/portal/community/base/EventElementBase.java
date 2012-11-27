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


public abstract class EventElementBase extends org.semanticwb.portal.community.MicroSiteElement implements org.semanticwb.model.Searchable,org.semanticwb.model.Traceable,org.semanticwb.model.Tagable,org.semanticwb.model.Viewable,org.semanticwb.portal.community.Interactiveable,org.semanticwb.model.Rankable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticProperty swbcomm_endTime=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#endTime");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_eventThumbnail=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#eventThumbnail");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_audienceType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#audienceType");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_endDate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#endDate");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_latitude=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#latitude");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_startDate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#startDate");
    public static final org.semanticwb.platform.SemanticClass swb_WebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebPage");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_eventWebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#eventWebPage");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_eventImage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#eventImage");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_longitude=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#longitude");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_startTime=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#startTime");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_place=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#place");
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_hasAttendant=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#hasAttendant");
    public static final org.semanticwb.platform.SemanticClass swbcomm_EventElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#EventElement");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#EventElement");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.portal.community.EventElement> listEventElements(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.EventElement>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.portal.community.EventElement> listEventElements()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.EventElement>(it, true);
        }

        public static org.semanticwb.portal.community.EventElement createEventElement(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.portal.community.EventElement.ClassMgr.createEventElement(String.valueOf(id), model);
        }

        public static org.semanticwb.portal.community.EventElement getEventElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.community.EventElement)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.portal.community.EventElement createEventElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.community.EventElement)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeEventElement(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasEventElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (getEventElement(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.portal.community.EventElement> listEventElementByModifiedBy(org.semanticwb.model.User modifiedby,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.EventElement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_modifiedBy, modifiedby.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.EventElement> listEventElementByModifiedBy(org.semanticwb.model.User modifiedby)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.EventElement> it=new org.semanticwb.model.GenericIterator(modifiedby.getSemanticObject().getModel().listSubjects(swb_modifiedBy,modifiedby.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.EventElement> listEventElementByEventWebPage(org.semanticwb.model.WebPage eventwebpage,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.EventElement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbcomm_eventWebPage, eventwebpage.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.EventElement> listEventElementByEventWebPage(org.semanticwb.model.WebPage eventwebpage)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.EventElement> it=new org.semanticwb.model.GenericIterator(eventwebpage.getSemanticObject().getModel().listSubjects(swbcomm_eventWebPage,eventwebpage.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.EventElement> listEventElementByAttendant(org.semanticwb.model.User hasattendant,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.EventElement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbcomm_hasAttendant, hasattendant.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.EventElement> listEventElementByAttendant(org.semanticwb.model.User hasattendant)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.EventElement> it=new org.semanticwb.model.GenericIterator(hasattendant.getSemanticObject().getModel().listSubjects(swbcomm_hasAttendant,hasattendant.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.EventElement> listEventElementByCreator(org.semanticwb.model.User creator,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.EventElement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_creator, creator.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.EventElement> listEventElementByCreator(org.semanticwb.model.User creator)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.EventElement> it=new org.semanticwb.model.GenericIterator(creator.getSemanticObject().getModel().listSubjects(swb_creator,creator.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.EventElement> listEventElementByComment(org.semanticwb.portal.community.Comment hascomment,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.EventElement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbcomm_hasComment, hascomment.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.EventElement> listEventElementByComment(org.semanticwb.portal.community.Comment hascomment)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.EventElement> it=new org.semanticwb.model.GenericIterator(hascomment.getSemanticObject().getModel().listSubjects(swbcomm_hasComment,hascomment.getSemanticObject()));
            return it;
        }
    }

    public EventElementBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public java.util.Date getEndTime()
    {
        return getSemanticObject().getDateProperty(swbcomm_endTime);
    }

    public void setEndTime(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swbcomm_endTime, value);
    }

    public String getEventThumbnail()
    {
        return getSemanticObject().getProperty(swbcomm_eventThumbnail);
    }

    public void setEventThumbnail(String value)
    {
        getSemanticObject().setProperty(swbcomm_eventThumbnail, value);
    }

    public String getAudienceType()
    {
        return getSemanticObject().getProperty(swbcomm_audienceType);
    }

    public void setAudienceType(String value)
    {
        getSemanticObject().setProperty(swbcomm_audienceType, value);
    }

    public java.util.Date getEndDate()
    {
        return getSemanticObject().getDateProperty(swbcomm_endDate);
    }

    public void setEndDate(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swbcomm_endDate, value);
    }

    public String getLatitude()
    {
        return getSemanticObject().getProperty(swbcomm_latitude);
    }

    public void setLatitude(String value)
    {
        getSemanticObject().setProperty(swbcomm_latitude, value);
    }

    public java.util.Date getStartDate()
    {
        return getSemanticObject().getDateProperty(swbcomm_startDate);
    }

    public void setStartDate(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swbcomm_startDate, value);
    }

    public void setEventWebPage(org.semanticwb.model.WebPage value)
    {
        getSemanticObject().setObjectProperty(swbcomm_eventWebPage, value.getSemanticObject());
    }

    public void removeEventWebPage()
    {
        getSemanticObject().removeProperty(swbcomm_eventWebPage);
    }

    public org.semanticwb.model.WebPage getEventWebPage()
    {
         org.semanticwb.model.WebPage ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbcomm_eventWebPage);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.WebPage)obj.createGenericInstance();
         }
         return ret;
    }

    public String getEventImage()
    {
        return getSemanticObject().getProperty(swbcomm_eventImage);
    }

    public void setEventImage(String value)
    {
        getSemanticObject().setProperty(swbcomm_eventImage, value);
    }

    public String getLongitude()
    {
        return getSemanticObject().getProperty(swbcomm_longitude);
    }

    public void setLongitude(String value)
    {
        getSemanticObject().setProperty(swbcomm_longitude, value);
    }

    public java.util.Date getStartTime()
    {
        return getSemanticObject().getDateProperty(swbcomm_startTime);
    }

    public void setStartTime(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swbcomm_startTime, value);
    }

    public String getPlace()
    {
        return getSemanticObject().getProperty(swbcomm_place);
    }

    public void setPlace(String value)
    {
        getSemanticObject().setProperty(swbcomm_place, value);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.User> listAttendants()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.User>(getSemanticObject().listObjectProperties(swbcomm_hasAttendant));
    }

    public boolean hasAttendant(org.semanticwb.model.User user)
    {
        boolean ret=false;
        if(user!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swbcomm_hasAttendant,user.getSemanticObject());
        }
        return ret;
    }

    public void addAttendant(org.semanticwb.model.User value)
    {
        getSemanticObject().addObjectProperty(swbcomm_hasAttendant, value.getSemanticObject());
    }

    public void removeAllAttendant()
    {
        getSemanticObject().removeProperty(swbcomm_hasAttendant);
    }

    public void removeAttendant(org.semanticwb.model.User user)
    {
        getSemanticObject().removeObjectProperty(swbcomm_hasAttendant,user.getSemanticObject());
    }

    public org.semanticwb.model.User getAttendant()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbcomm_hasAttendant);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }
}
