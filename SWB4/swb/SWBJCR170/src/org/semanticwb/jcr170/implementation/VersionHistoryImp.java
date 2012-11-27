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
import java.util.HashSet;
import javax.jcr.AccessDeniedException;
import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;
import javax.jcr.ReferentialIntegrityException;
import javax.jcr.RepositoryException;
import javax.jcr.UnsupportedRepositoryOperationException;
import javax.jcr.version.Version;
import javax.jcr.version.VersionException;
import javax.jcr.version.VersionHistory;
import javax.jcr.version.VersionIterator;
import org.semanticwb.Logger;
import org.semanticwb.SWBException;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.platform.SemanticLiteral;
import org.semanticwb.repository.BaseNode;

/**
 *
 * @author victor.lorenzana
 */
public class VersionHistoryImp extends SimpleNode implements VersionHistory
{

    private static Logger log = SWBUtils.getLogger(VersionHistoryImp.class);

    VersionHistoryImp(BaseNode versionHistory, SessionImp session, SimpleNode parent) throws RepositoryException
    {
        super(versionHistory, session);
        if (!versionHistory.isVersionHistoryNode())
        {
            throw new IllegalArgumentException("The node is not a versionhistory node");
        }
        this.parent = parent;
    }

    public BaseNode getVersionHistoryBase()
    {
        return node;
    }

    public BaseNode[] getVersions() throws SWBException
    {
        ArrayList<BaseNode> versions = new ArrayList<BaseNode>();
        for (BaseNode version : node.getVersions())
        {
            boolean isRemoved = false;
            for (SimpleNode removed : this.removedchilds)
            {
                if (removed.node != null && removed.node.equals(version))
                {
                    isRemoved = true;
                    break;
                }
            }
            if (!isRemoved)
            {
                versions.add(version);
            }
        }
        return versions.toArray(new BaseNode[versions.size()]);
    }

    @Override
    public String getVersionableUUID() throws RepositoryException
    {
        try
        {
            return node.getUUID();
        }
        catch (SWBException swbe)
        {
            throw new RepositoryException(swbe);
        }
    }

    @Override
    public Version getRootVersion() throws RepositoryException
    {
        GenericIterator<BaseNode> nodes = this.node.listNodes();
        while (nodes.hasNext())
        {
            BaseNode child = nodes.next();
            if (child.getName().equals("jcr:rootVersion"))
            {
                return new VersionImp(child, this, session);
            }
        }
        throw new RepositoryException("The root version was not found");
    }

    @Override
    public VersionIterator getAllVersions() throws RepositoryException
    {
        try
        {
            return new VersionIteratorImp(this, session, parent);
        }
        catch (SWBException swbe)
        {
            throw new RepositoryException(swbe);
        }
    }

    @Override
    public Version getVersion(String name) throws VersionException, RepositoryException
    {
        GenericIterator<BaseNode> nodes = this.node.listNodes();
        while (nodes.hasNext())
        {
            BaseNode child = nodes.next();
            if (child != null && name != null && child.getName() != null && child.getSemanticObject() != null && child.getSemanticObject().getSemanticClass() != null && child.getName().equals(name) && child.getSemanticObject().getSemanticClass().equals(org.semanticwb.repository.Version.nt_Version))
            {
                return new VersionImp(child, this, session);
            }
        }
        throw new RepositoryException("The version " + name + " was not found");
    }

    @Override
    public Version getVersionByLabel(String label) throws RepositoryException
    {
        Node versionLabels = null;
        try
        {
            versionLabels = this.getNode("jcr:versionLabels");
            Property prop = versionLabels.getProperty(label);
            Node nodeversion = prop.getNode();
            if (nodeversion instanceof Version)
            {
                return (Version) nodeversion;
            }
            if (nodeversion instanceof SimpleNode)
            {
                SimpleNode simple=(SimpleNode)nodeversion;
                if(simple.node.getSemanticObject().getSemanticClass().equals(org.semanticwb.repository.Version.nt_Version))
                {
                    VersionImp version=new VersionImp(simple.node, this, session);
                    session.nodes.put(version.getUUID(), version);
                    return version;
                }
                else
                {
                    return null;
                }
            }
            else
            {
                return null;
            }
        }
        catch (PathNotFoundException pnfe)
        {
            log.trace(pnfe);
            return null;
        }
    }

    @Override
    public void addVersionLabel(String versionName, String label, boolean moveLabel) throws VersionException, RepositoryException
    {
        Version currentVersion = null;
        VersionIterator it = this.getAllVersions();
        while (it.hasNext())
        {
            Version version = it.nextVersion();
            if (version.getName().equals(versionName))
            {
                currentVersion = version;
                break;
            }
        }
        if (currentVersion == null)
        {
            throw new VersionException("The version " + versionName + " was not found");
        }

        Version currentversionLabel = this.getVersionByLabel(label);
        if (currentversionLabel != null)
        {
            if (!currentversionLabel.getUUID().equals(currentVersion.getUUID()))
            {
                if (!moveLabel)
                {
                    throw new VersionException("The label " + label + " exists in another version");
                }
            }
        }
        Node versionLabels = null;
        try
        {
            versionLabels = this.getNode("jcr:versionLabels");
        }
        catch (PathNotFoundException pnfe)
        {
            log.trace(pnfe);
            versionLabels = this.addNode("jcr:versionLabels", "nt:versionLabels");
            this.save();
        }
        versionLabels.setProperty(label, currentVersion);
        versionLabels.save();
        this.save();
    }

    @Override
    public void removeVersionLabel(String label) throws VersionException, RepositoryException
    {
        Node versionLabels = null;
        try
        {
            versionLabels = this.getNode("jcr:versionLabels");
            Property prop = versionLabels.getProperty(label);
            prop.remove();
        }
        catch (PathNotFoundException pnfe)
        {
            log.trace(pnfe);
        }
    }

    @Override
    public boolean hasVersionLabel(String label) throws RepositoryException
    {
        String[] labels = getVersionLabels();
        for (String _label : labels)
        {
            if (_label.equals(label))
            {
                return true;
            }
        }
        return false;

    }

    @Override
    public boolean hasVersionLabel(Version version, String label) throws VersionException, RepositoryException
    {
        String[] labels = getVersionLabels(version);
        for (String _label : labels)
        {
            if (_label.equals(label))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public String[] getVersionLabels() throws RepositoryException
    {
        HashSet<String> getVersionLabels = new HashSet<String>();
        Node versionLabels = null;
        try
        {
            versionLabels = this.getNode("jcr:versionLabels");
            PropertyIterator props = versionLabels.getProperties();
            while (props.hasNext())
            {
                Property prop = props.nextProperty();
                getVersionLabels.add(prop.getString());
            }
        }
        catch (PathNotFoundException pnfe)
        {
            log.trace(pnfe);
        }
        return getVersionLabels.toArray(new String[getVersionLabels.size()]);

    }

    @Override
    public String[] getVersionLabels(Version version) throws VersionException, RepositoryException
    {
        HashSet<String> getVersionLabels = new HashSet<String>();
        Node versionLabels = null;
        try
        {
            versionLabels = this.getNode("jcr:versionLabels");
            PropertyIterator props = versionLabels.getProperties();
            while (props.hasNext())
            {
                Property prop = props.nextProperty();
                Node _node = prop.getNode();
                if (_node.getUUID().equals(version.getUUID()))
                {
                    getVersionLabels.add(prop.getString());
                }
            }
        }
        catch (PathNotFoundException pnfe)
        {
            log.trace(pnfe);
        }

        return getVersionLabels.toArray(new String[getVersionLabels.size()]);
    }

    @Override
    public void removeVersion(String versionName) throws ReferentialIntegrityException, AccessDeniedException, UnsupportedRepositoryOperationException, VersionException, RepositoryException
    {
        Version currentVersion = null;
        VersionIterator it = this.getAllVersions();
        while (it.hasNext())
        {
            Version version = it.nextVersion();
            if (version.getName().equals(versionName))
            {
                currentVersion = version;
                break;
            }
        }
        if (currentVersion == null)
        {
            throw new VersionException("The version " + versionName + " was not found");
        }
        if (currentVersion.getUUID().equals(this.getBaseVersion().getUUID()))
        {
            throw new AccessDeniedException("The base versión can not be deleted");
        }
        else
        {
            {
                Version[] predecessors = currentVersion.getPredecessors();

                for (Version version : predecessors)
                {
                    HashSet<Version> successors = new HashSet<Version>();
                    boolean found = false;
                    for (Version suc : version.getSuccessors())
                    {
                        if (suc.getUUID().equals(currentVersion.getUUID()))
                        {
                            // borra la relación
                            found = true;
                            break;
                        }
                        else
                        {
                            successors.add(suc);
                        }
                    }
                    if (found)
                    {
                        VersionImp imp = (VersionImp) version;
                        imp.node.getSemanticObject().removeProperty(org.semanticwb.repository.Version.jcr_successors);
                        for (Version successor : successors)
                        {
                            imp.node.getSemanticObject().addObjectProperty(org.semanticwb.repository.Version.jcr_successors, ((VersionImp) successor).node.getSemanticObject());
                        }

                    }
                }
            }
            {
                Version[] successors = currentVersion.getSuccessors();

                for (Version version : successors)
                {
                    HashSet<Version> predecessors = new HashSet<Version>();
                    boolean found = false;
                    for (Version prec : version.getPredecessors())
                    {
                        if (prec.getUUID().equals(currentVersion.getUUID()))
                        {
                            // borra la relación
                            found = true;
                            break;
                        }
                        else
                        {
                            predecessors.add(prec);
                        }
                    }
                    if (found)
                    {
                        VersionImp imp = (VersionImp) version;
                        imp.node.getSemanticObject().removeProperty(org.semanticwb.repository.Version.jcr_predecessors);
                        for (Version successor : successors)
                        {
                            imp.node.getSemanticObject().addObjectProperty(org.semanticwb.repository.Version.jcr_predecessors, ((VersionImp) successor).node.getSemanticObject());
                        }

                    }
                }
            }

            currentVersion.remove();

            this.save();
        }
    }
}
