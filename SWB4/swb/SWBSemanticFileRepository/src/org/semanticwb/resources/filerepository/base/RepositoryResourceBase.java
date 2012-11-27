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


public class RepositoryResourceBase extends org.semanticwb.repository.Resource implements org.semanticwb.repository.Referenceable
{
    public static final org.semanticwb.platform.SemanticProperty swbfilerep_comment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/filerepository#comment");
    public static final org.semanticwb.platform.SemanticProperty swbfilerep_userid=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/filerepository#userid");
    public static final org.semanticwb.platform.SemanticProperty swbfilerep_filesize=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/filerepository#filesize");
    public static final org.semanticwb.platform.SemanticClass swbfilerep_RepositoryResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/filerepository#RepositoryResource");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/filerepository#RepositoryResource");

    public RepositoryResourceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static java.util.Iterator<org.semanticwb.resources.filerepository.RepositoryResource> listRepositoryResources(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.resources.filerepository.RepositoryResource>(it, true);
    }

    public static java.util.Iterator<org.semanticwb.resources.filerepository.RepositoryResource> listRepositoryResources()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.resources.filerepository.RepositoryResource>(it, true);
    }

    public static org.semanticwb.resources.filerepository.RepositoryResource getRepositoryResource(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.resources.filerepository.RepositoryResource)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static org.semanticwb.resources.filerepository.RepositoryResource createRepositoryResource(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.resources.filerepository.RepositoryResource)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeRepositoryResource(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasRepositoryResource(String id, org.semanticwb.model.SWBModel model)
    {
        return (getRepositoryResource(id, model)!=null);
    }

    public String getComment()
    {
        return getSemanticObject().getProperty(swbfilerep_comment);
    }

    public void setComment(String comment)
    {
        getSemanticObject().setProperty(swbfilerep_comment, comment);
    }

    public String getUserid()
    {
        return getSemanticObject().getProperty(swbfilerep_userid);
    }

    public void setUserid(String userid)
    {
        getSemanticObject().setProperty(swbfilerep_userid, userid);
    }

    public long getFilesize()
    {
        return getSemanticObject().getLongProperty(swbfilerep_filesize);
    }

    public void setFilesize(long filesize)
    {
        getSemanticObject().setLongProperty(swbfilerep_filesize, filesize);
    }
}
