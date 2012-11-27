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
package org.semanticwb.process.model.base;

public interface StoreRepositoryNodeableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty swp_storeRepNodeId=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#storeRepNodeId");
    public static final org.semanticwb.platform.SemanticClass swp_RepositoryDirectory=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#RepositoryDirectory");
    public static final org.semanticwb.platform.SemanticProperty swp_storeRepNodeDirectory=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#storeRepNodeDirectory");
    public static final org.semanticwb.platform.SemanticProperty swp_storeRepNodeName=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#storeRepNodeName");
    public static final org.semanticwb.platform.SemanticClass swp_ItemAwareStatus=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ItemAwareStatus");
    public static final org.semanticwb.platform.SemanticProperty swp_storeRepNodeStatus=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#storeRepNodeStatus");
    public static final org.semanticwb.platform.SemanticProperty swp_storeRepNodeComment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#storeRepNodeComment");
    public static final org.semanticwb.platform.SemanticClass swp_StoreRepositoryNodeable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#StoreRepositoryNodeable");

    public String getNodeId();

    public void setNodeId(String value);

   /**
   * Sets a value from the property NodeDirectory
   * @param value An instance of org.semanticwb.process.model.RepositoryDirectory
   */
    public void setNodeDirectory(org.semanticwb.process.model.RepositoryDirectory value);

   /**
   * Remove the value from the property NodeDirectory
   */
    public void removeNodeDirectory();

    public org.semanticwb.process.model.RepositoryDirectory getNodeDirectory();

    public String getNodeName();

    public void setNodeName(String value);

   /**
   * Sets a value from the property NodeStatus
   * @param value An instance of org.semanticwb.process.model.ItemAwareStatus
   */
    public void setNodeStatus(org.semanticwb.process.model.ItemAwareStatus value);

   /**
   * Remove the value from the property NodeStatus
   */
    public void removeNodeStatus();

    public org.semanticwb.process.model.ItemAwareStatus getNodeStatus();

    public String getNodeComment();

    public void setNodeComment(String value);
}
