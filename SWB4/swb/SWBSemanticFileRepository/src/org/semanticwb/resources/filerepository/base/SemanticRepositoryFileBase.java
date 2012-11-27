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
package org.semanticwb.resources.filerepository.base;


public class SemanticRepositoryFileBase extends org.semanticwb.portal.api.GenericSemResource 
{
    public static final org.semanticwb.platform.SemanticProperty swbfilerep_see=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/filerepository#see");
    public static final org.semanticwb.platform.SemanticProperty swbfilerep_msgupdated=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/filerepository#msgupdated");
    public static final org.semanticwb.platform.SemanticProperty swbfilerep_msgcrated=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/filerepository#msgcrated");
    public static final org.semanticwb.platform.SemanticProperty swbfilerep_msgdeleted=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/filerepository#msgdeleted");
    public static final org.semanticwb.platform.SemanticProperty swbfilerep_modify=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/filerepository#modify");
    public static final org.semanticwb.platform.SemanticProperty swbfilerep_useFolders=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/filerepository#useFolders");
    public static final org.semanticwb.platform.SemanticProperty swbfilerep_admin=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/filerepository#admin");
    public static final org.semanticwb.platform.SemanticClass swbfilerep_SemanticRepositoryFile=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/filerepository#SemanticRepositoryFile");

    public SemanticRepositoryFileBase()
    {
    }

    public SemanticRepositoryFileBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/filerepository#SemanticRepositoryFile");

    public String getSee()
    {
        return getSemanticObject().getProperty(swbfilerep_see);
    }

    public void setSee(String see)
    {
        getSemanticObject().setProperty(swbfilerep_see, see);
    }

    public String getMsgupdated()
    {
        return getSemanticObject().getProperty(swbfilerep_msgupdated);
    }

    public void setMsgupdated(String msgupdated)
    {
        getSemanticObject().setProperty(swbfilerep_msgupdated, msgupdated);
    }

    public String getMsgcrated()
    {
        return getSemanticObject().getProperty(swbfilerep_msgcrated);
    }

    public void setMsgcrated(String msgcrated)
    {
        getSemanticObject().setProperty(swbfilerep_msgcrated, msgcrated);
    }

    public String getMsgdeleted()
    {
        return getSemanticObject().getProperty(swbfilerep_msgdeleted);
    }

    public void setMsgdeleted(String msgdeleted)
    {
        getSemanticObject().setProperty(swbfilerep_msgdeleted, msgdeleted);
    }

    public String getModify()
    {
        return getSemanticObject().getProperty(swbfilerep_modify);
    }

    public void setModify(String modify)
    {
        getSemanticObject().setProperty(swbfilerep_modify, modify);
    }

    public boolean isUseFolders()
    {
        return getSemanticObject().getBooleanProperty(swbfilerep_useFolders);
    }

    public void setUseFolders(boolean useFolders)
    {
        getSemanticObject().setBooleanProperty(swbfilerep_useFolders, useFolders);
    }

    public String getAdmin()
    {
        return getSemanticObject().getProperty(swbfilerep_admin);
    }

    public void setAdmin(String admin)
    {
        getSemanticObject().setProperty(swbfilerep_admin, admin);
    }
}
