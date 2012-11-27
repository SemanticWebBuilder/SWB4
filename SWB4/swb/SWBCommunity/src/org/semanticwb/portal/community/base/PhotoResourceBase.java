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


public abstract class PhotoResourceBase extends org.semanticwb.portal.community.CommunityResource 
{
    public static final org.semanticwb.platform.SemanticClass swbcomm_PhotoElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#PhotoElement");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_hasPhoto=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#hasPhoto");
    public static final org.semanticwb.platform.SemanticClass swbcomm_PhotoResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#PhotoResource");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#PhotoResource");

    public PhotoResourceBase()
    {
    }

    public PhotoResourceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.PhotoElement> listPhotos()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.PhotoElement>(getSemanticObject().listObjectProperties(swbcomm_hasPhoto));
    }

    public boolean hasPhoto(org.semanticwb.portal.community.PhotoElement photoelement)
    {
        boolean ret=false;
        if(photoelement!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swbcomm_hasPhoto,photoelement.getSemanticObject());
        }
        return ret;
    }

    public void addPhoto(org.semanticwb.portal.community.PhotoElement value)
    {
        getSemanticObject().addObjectProperty(swbcomm_hasPhoto, value.getSemanticObject());
    }

    public void removeAllPhoto()
    {
        getSemanticObject().removeProperty(swbcomm_hasPhoto);
    }

    public void removePhoto(org.semanticwb.portal.community.PhotoElement photoelement)
    {
        getSemanticObject().removeObjectProperty(swbcomm_hasPhoto,photoelement.getSemanticObject());
    }

    public org.semanticwb.portal.community.PhotoElement getPhoto()
    {
         org.semanticwb.portal.community.PhotoElement ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbcomm_hasPhoto);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.community.PhotoElement)obj.createGenericInstance();
         }
         return ret;
    }
}
