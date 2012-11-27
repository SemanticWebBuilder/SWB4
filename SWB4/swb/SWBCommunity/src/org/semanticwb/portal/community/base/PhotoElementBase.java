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


public abstract class PhotoElementBase extends org.semanticwb.portal.community.MicroSiteElement implements org.semanticwb.model.Searchable,org.semanticwb.model.Traceable,org.semanticwb.model.Tagable,org.semanticwb.model.Viewable,org.semanticwb.portal.community.Interactiveable,org.semanticwb.model.Rankable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticProperty swbcomm_width=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#width");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_photoThumbnail=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#photoThumbnail");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_imageURL=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#imageURL");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_thumbHeight=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#thumbHeight");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_height=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#height");
    public static final org.semanticwb.platform.SemanticClass swb_WebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebPage");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_photoWebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#photoWebPage");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_thumbWidth=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#thumbWidth");
    public static final org.semanticwb.platform.SemanticClass swbcomm_PhotoElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#PhotoElement");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#PhotoElement");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.portal.community.PhotoElement> listPhotoElements(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.PhotoElement>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.portal.community.PhotoElement> listPhotoElements()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.PhotoElement>(it, true);
        }

        public static org.semanticwb.portal.community.PhotoElement createPhotoElement(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.portal.community.PhotoElement.ClassMgr.createPhotoElement(String.valueOf(id), model);
        }

        public static org.semanticwb.portal.community.PhotoElement getPhotoElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.community.PhotoElement)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.portal.community.PhotoElement createPhotoElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.community.PhotoElement)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removePhotoElement(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasPhotoElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (getPhotoElement(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.portal.community.PhotoElement> listPhotoElementByModifiedBy(org.semanticwb.model.User modifiedby,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.PhotoElement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_modifiedBy, modifiedby.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.PhotoElement> listPhotoElementByModifiedBy(org.semanticwb.model.User modifiedby)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.PhotoElement> it=new org.semanticwb.model.GenericIterator(modifiedby.getSemanticObject().getModel().listSubjects(swb_modifiedBy,modifiedby.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.PhotoElement> listPhotoElementByPhotoWebPage(org.semanticwb.model.WebPage photowebpage,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.PhotoElement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbcomm_photoWebPage, photowebpage.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.PhotoElement> listPhotoElementByPhotoWebPage(org.semanticwb.model.WebPage photowebpage)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.PhotoElement> it=new org.semanticwb.model.GenericIterator(photowebpage.getSemanticObject().getModel().listSubjects(swbcomm_photoWebPage,photowebpage.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.PhotoElement> listPhotoElementByCreator(org.semanticwb.model.User creator,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.PhotoElement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_creator, creator.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.PhotoElement> listPhotoElementByCreator(org.semanticwb.model.User creator)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.PhotoElement> it=new org.semanticwb.model.GenericIterator(creator.getSemanticObject().getModel().listSubjects(swb_creator,creator.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.PhotoElement> listPhotoElementByComment(org.semanticwb.portal.community.Comment hascomment,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.PhotoElement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbcomm_hasComment, hascomment.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.PhotoElement> listPhotoElementByComment(org.semanticwb.portal.community.Comment hascomment)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.PhotoElement> it=new org.semanticwb.model.GenericIterator(hascomment.getSemanticObject().getModel().listSubjects(swbcomm_hasComment,hascomment.getSemanticObject()));
            return it;
        }
    }

    public PhotoElementBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public int getWidth()
    {
        return getSemanticObject().getIntProperty(swbcomm_width);
    }

    public void setWidth(int value)
    {
        getSemanticObject().setIntProperty(swbcomm_width, value);
    }

    public String getPhotoThumbnail()
    {
        return getSemanticObject().getProperty(swbcomm_photoThumbnail);
    }

    public void setPhotoThumbnail(String value)
    {
        getSemanticObject().setProperty(swbcomm_photoThumbnail, value);
    }

    public String getImageURL()
    {
        return getSemanticObject().getProperty(swbcomm_imageURL);
    }

    public void setImageURL(String value)
    {
        getSemanticObject().setProperty(swbcomm_imageURL, value);
    }

    public int getThumbHeight()
    {
        return getSemanticObject().getIntProperty(swbcomm_thumbHeight);
    }

    public void setThumbHeight(int value)
    {
        getSemanticObject().setIntProperty(swbcomm_thumbHeight, value);
    }

    public int getHeight()
    {
        return getSemanticObject().getIntProperty(swbcomm_height);
    }

    public void setHeight(int value)
    {
        getSemanticObject().setIntProperty(swbcomm_height, value);
    }

    public void setPhotoWebPage(org.semanticwb.model.WebPage value)
    {
        getSemanticObject().setObjectProperty(swbcomm_photoWebPage, value.getSemanticObject());
    }

    public void removePhotoWebPage()
    {
        getSemanticObject().removeProperty(swbcomm_photoWebPage);
    }

    public org.semanticwb.model.WebPage getPhotoWebPage()
    {
         org.semanticwb.model.WebPage ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbcomm_photoWebPage);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.WebPage)obj.createGenericInstance();
         }
         return ret;
    }

    public int getThumbWidth()
    {
        return getSemanticObject().getIntProperty(swbcomm_thumbWidth);
    }

    public void setThumbWidth(int value)
    {
        getSemanticObject().setIntProperty(swbcomm_thumbWidth, value);
    }
}
