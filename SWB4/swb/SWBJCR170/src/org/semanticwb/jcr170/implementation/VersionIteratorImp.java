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
import java.util.Iterator;
import javax.jcr.RepositoryException;
import javax.jcr.version.Version;
import javax.jcr.version.VersionIterator;
import org.semanticwb.SWBException;
import org.semanticwb.repository.BaseNode;

/**
 *
 * @author victor.lorenzana
 */
public class VersionIteratorImp implements VersionIterator
{
    private final Iterator<BaseNode> versions;
    private final int size;
    private long position=0;
    private final VersionHistoryImp versionHistory;
    private final SessionImp session;
    private final SimpleNode parent;
    VersionIteratorImp(VersionHistoryImp versionHistory,SessionImp session,SimpleNode parent) throws SWBException
    {
        ArrayList<BaseNode> temp=new ArrayList<BaseNode>();
        for(BaseNode version : versionHistory.getVersions())
        {
            temp.add(version);
        }
        size=versionHistory.getVersions().length;
        versions=temp.iterator();
        this.versionHistory=versionHistory;
        this.session=session;
        this.parent=parent;
    }
    public Version nextVersion()
    {
        position++;
        BaseNode node=versions.next();
        if(node!=null)
        {            
            try
            {
                return new VersionImp(node, versionHistory, session);
            }
            catch(RepositoryException e)
            {
                return null;
            }
        }
        else
        {
            return null;
        }
    }

    public void skip(long arg0)
    {
        for(int i=1;i<=arg0;i++)
        {
            nextVersion();
        }
    }

    public long getSize()
    {
        return size;
    }

    public long getPosition()
    {
        return position;
    }

    public boolean hasNext()
    {
        return versions.hasNext();
    }

    public Object next()
    {
        return nextVersion();
    }

    public void remove()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
