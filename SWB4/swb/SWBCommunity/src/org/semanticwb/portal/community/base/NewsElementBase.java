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


public abstract class NewsElementBase extends org.semanticwb.portal.community.MicroSiteElement implements org.semanticwb.model.Searchable,org.semanticwb.model.Traceable,org.semanticwb.model.Tagable,org.semanticwb.model.Viewable,org.semanticwb.portal.community.Interactiveable,org.semanticwb.model.Rankable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticProperty swbcomm_newsImage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#newsImage");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_citation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#citation");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_author=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#author");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_newsThumbnail=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#newsThumbnail");
    public static final org.semanticwb.platform.SemanticClass swb_WebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebPage");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_newsWebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#newsWebPage");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_newsplace=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#newsplace");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_infoLink=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#infoLink");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_abstr=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#abstr");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_fullText=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#fullText");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_subtitle=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#subtitle");
    public static final org.semanticwb.platform.SemanticClass swbcomm_NewsElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#NewsElement");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#NewsElement");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.portal.community.NewsElement> listNewsElements(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.NewsElement>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.portal.community.NewsElement> listNewsElements()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.NewsElement>(it, true);
        }

        public static org.semanticwb.portal.community.NewsElement createNewsElement(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.portal.community.NewsElement.ClassMgr.createNewsElement(String.valueOf(id), model);
        }

        public static org.semanticwb.portal.community.NewsElement getNewsElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.community.NewsElement)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.portal.community.NewsElement createNewsElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.community.NewsElement)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeNewsElement(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasNewsElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (getNewsElement(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.portal.community.NewsElement> listNewsElementByModifiedBy(org.semanticwb.model.User modifiedby,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.NewsElement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_modifiedBy, modifiedby.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.NewsElement> listNewsElementByModifiedBy(org.semanticwb.model.User modifiedby)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.NewsElement> it=new org.semanticwb.model.GenericIterator(modifiedby.getSemanticObject().getModel().listSubjects(swb_modifiedBy,modifiedby.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.NewsElement> listNewsElementByNewsWebPage(org.semanticwb.model.WebPage newswebpage,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.NewsElement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbcomm_newsWebPage, newswebpage.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.NewsElement> listNewsElementByNewsWebPage(org.semanticwb.model.WebPage newswebpage)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.NewsElement> it=new org.semanticwb.model.GenericIterator(newswebpage.getSemanticObject().getModel().listSubjects(swbcomm_newsWebPage,newswebpage.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.NewsElement> listNewsElementByCreator(org.semanticwb.model.User creator,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.NewsElement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_creator, creator.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.NewsElement> listNewsElementByCreator(org.semanticwb.model.User creator)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.NewsElement> it=new org.semanticwb.model.GenericIterator(creator.getSemanticObject().getModel().listSubjects(swb_creator,creator.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.NewsElement> listNewsElementByComment(org.semanticwb.portal.community.Comment hascomment,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.NewsElement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbcomm_hasComment, hascomment.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.NewsElement> listNewsElementByComment(org.semanticwb.portal.community.Comment hascomment)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.NewsElement> it=new org.semanticwb.model.GenericIterator(hascomment.getSemanticObject().getModel().listSubjects(swbcomm_hasComment,hascomment.getSemanticObject()));
            return it;
        }
    }

    public NewsElementBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public String getNewsImage()
    {
        return getSemanticObject().getProperty(swbcomm_newsImage);
    }

    public void setNewsImage(String value)
    {
        getSemanticObject().setProperty(swbcomm_newsImage, value);
    }

    public String getCitation()
    {
        return getSemanticObject().getProperty(swbcomm_citation);
    }

    public void setCitation(String value)
    {
        getSemanticObject().setProperty(swbcomm_citation, value);
    }

    public String getAuthor()
    {
        return getSemanticObject().getProperty(swbcomm_author);
    }

    public void setAuthor(String value)
    {
        getSemanticObject().setProperty(swbcomm_author, value);
    }

    public String getNewsThumbnail()
    {
        return getSemanticObject().getProperty(swbcomm_newsThumbnail);
    }

    public void setNewsThumbnail(String value)
    {
        getSemanticObject().setProperty(swbcomm_newsThumbnail, value);
    }

    public void setNewsWebPage(org.semanticwb.model.WebPage value)
    {
        getSemanticObject().setObjectProperty(swbcomm_newsWebPage, value.getSemanticObject());
    }

    public void removeNewsWebPage()
    {
        getSemanticObject().removeProperty(swbcomm_newsWebPage);
    }

    public org.semanticwb.model.WebPage getNewsWebPage()
    {
         org.semanticwb.model.WebPage ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbcomm_newsWebPage);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.WebPage)obj.createGenericInstance();
         }
         return ret;
    }

    public String getNewsplace()
    {
        return getSemanticObject().getProperty(swbcomm_newsplace);
    }

    public void setNewsplace(String value)
    {
        getSemanticObject().setProperty(swbcomm_newsplace, value);
    }

    public String getInfoLink()
    {
        return getSemanticObject().getProperty(swbcomm_infoLink);
    }

    public void setInfoLink(String value)
    {
        getSemanticObject().setProperty(swbcomm_infoLink, value);
    }

    public String getAbstr()
    {
        return getSemanticObject().getProperty(swbcomm_abstr);
    }

    public void setAbstr(String value)
    {
        getSemanticObject().setProperty(swbcomm_abstr, value);
    }

    public String getFullText()
    {
        return getSemanticObject().getProperty(swbcomm_fullText);
    }

    public void setFullText(String value)
    {
        getSemanticObject().setProperty(swbcomm_fullText, value);
    }

    public String getSubtitle()
    {
        return getSemanticObject().getProperty(swbcomm_subtitle);
    }

    public void setSubtitle(String value)
    {
        getSemanticObject().setProperty(swbcomm_subtitle, value);
    }
}
