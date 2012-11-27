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


public abstract class DirectoryResourceBase extends org.semanticwb.portal.community.CommunityResource 
{
    public static final org.semanticwb.platform.SemanticProperty swbcomm_dirEditJsp=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#dirEditJsp");
    public static final org.semanticwb.platform.SemanticClass swbcomm_DirectoryClass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#DirectoryClass");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_directoryClass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#directoryClass");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_dirAcceptClaimMessage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#dirAcceptClaimMessage");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_dirListJsp=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#dirListJsp");
    public static final org.semanticwb.platform.SemanticClass swbcomm_DirectoryObject=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#DirectoryObject");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_hasDirectoryObjectInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#hasDirectoryObjectInv");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_dirAddJsp=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#dirAddJsp");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_dirClaimMessage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#dirClaimMessage");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_dirDetailJsp=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#dirDetailJsp");
    public static final org.semanticwb.platform.SemanticClass swbcomm_DirectoryResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#DirectoryResource");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#DirectoryResource");

    public DirectoryResourceBase()
    {
    }

    public DirectoryResourceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public String getEditJsp()
    {
        return getSemanticObject().getProperty(swbcomm_dirEditJsp);
    }

    public void setEditJsp(String value)
    {
        getSemanticObject().setProperty(swbcomm_dirEditJsp, value);
    }

    public void setDirectoryClass(org.semanticwb.platform.SemanticObject value)
    {
        getSemanticObject().setObjectProperty(swbcomm_directoryClass, value);
    }

    public void removeDirectoryClass()
    {
        getSemanticObject().removeProperty(swbcomm_directoryClass);
    }

    public org.semanticwb.platform.SemanticObject getDirectoryClass()
    {
         org.semanticwb.platform.SemanticObject ret=null;
         ret=getSemanticObject().getObjectProperty(swbcomm_directoryClass);
         return ret;
    }

    public String getDirAcceptClaimMessage()
    {
        return getSemanticObject().getProperty(swbcomm_dirAcceptClaimMessage);
    }

    public void setDirAcceptClaimMessage(String value)
    {
        getSemanticObject().setProperty(swbcomm_dirAcceptClaimMessage, value);
    }

    public String getListJsp()
    {
        return getSemanticObject().getProperty(swbcomm_dirListJsp);
    }

    public void setListJsp(String value)
    {
        getSemanticObject().setProperty(swbcomm_dirListJsp, value);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.DirectoryObject> listDirectoryObjects()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.DirectoryObject>(getSemanticObject().listObjectProperties(swbcomm_hasDirectoryObjectInv));
    }

    public boolean hasDirectoryObject(org.semanticwb.portal.community.DirectoryObject directoryobject)
    {
        boolean ret=false;
        if(directoryobject!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swbcomm_hasDirectoryObjectInv,directoryobject.getSemanticObject());
        }
        return ret;
    }

    public org.semanticwb.portal.community.DirectoryObject getDirectoryObject()
    {
         org.semanticwb.portal.community.DirectoryObject ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbcomm_hasDirectoryObjectInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.community.DirectoryObject)obj.createGenericInstance();
         }
         return ret;
    }

    public String getAddJsp()
    {
        return getSemanticObject().getProperty(swbcomm_dirAddJsp);
    }

    public void setAddJsp(String value)
    {
        getSemanticObject().setProperty(swbcomm_dirAddJsp, value);
    }

    public String getDirClaimMessage()
    {
        return getSemanticObject().getProperty(swbcomm_dirClaimMessage);
    }

    public void setDirClaimMessage(String value)
    {
        getSemanticObject().setProperty(swbcomm_dirClaimMessage, value);
    }

    public String getDetailJsp()
    {
        return getSemanticObject().getProperty(swbcomm_dirDetailJsp);
    }

    public void setDetailJsp(String value)
    {
        getSemanticObject().setProperty(swbcomm_dirDetailJsp, value);
    }
}
