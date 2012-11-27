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
package org.semanticwb.jcr170.implementation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.jcr.RepositoryException;
import javax.jcr.version.Version;
import javax.jcr.version.VersionHistory;
import org.semanticwb.SWBException;
import org.semanticwb.repository.BaseNode;

/**
 *
 * @author victor.lorenzana
 */
public class VersionImp extends SimpleNode implements Version
{

    private final BaseNode history;
    private final VersionHistoryImp historyNode;

    VersionImp(BaseNode version, VersionHistoryImp historyNode, SessionImp session) throws RepositoryException
    {
        super(version, session);
        if (!version.isVersionNode())
        {
            throw new IllegalArgumentException("The node is not a version node");
        }
        this.history = historyNode.node;
        if (!history.isVersionHistoryNode())
        {
            throw new IllegalArgumentException("The node is not a version history");
        }
        this.historyNode = historyNode;
        this.parent = historyNode;

    }

    @Override
    public VersionHistory getContainingHistory() throws RepositoryException
    {
        return historyNode;
    }

    @Override
    public Calendar getCreated() throws RepositoryException
    {
        org.semanticwb.repository.Version version = new org.semanticwb.repository.Version(node.getSemanticObject());
        Date date = version.getCreated();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    @Override
    public Version[] getSuccessors() throws RepositoryException
    {
        org.semanticwb.repository.Version versionNode = new org.semanticwb.repository.Version(node.getSemanticObject());

        try
        {
            ArrayList<Version> versions = new ArrayList<Version>();
            for (BaseNode baseNode : versionNode.getBaseSuccessors())
            {
                VersionImp version = new VersionImp(baseNode, historyNode, session);
                versions.add(version);
            }
            return versions.toArray(new Version[versions.size()]);
        }
        catch (SWBException swbe)
        {
            throw new RepositoryException(swbe);
        }
    }

    @Override
    public Version[] getPredecessors() throws RepositoryException
    {
        org.semanticwb.repository.Version versionNode = new org.semanticwb.repository.Version(node.getSemanticObject());
        try
        {
            ArrayList<Version> versions = new ArrayList<Version>();
            for (BaseNode baseNode : versionNode.getBasePredecessors())
            {
                VersionImp version = new VersionImp(baseNode, historyNode, session);
                versions.add(version);
            }
            return versions.toArray(new Version[versions.size()]);
        }
        catch (SWBException swbe)
        {
            throw new RepositoryException(swbe);
        }
    }

    @Override
    public String toString()
    {
        return node.getName();
    }
}
