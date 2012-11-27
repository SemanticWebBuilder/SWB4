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
import java.util.List;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.query.QueryResult;
import javax.jcr.query.RowIterator;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBContext;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.repository.BaseNode;

/**
 *
 * @author victor.lorenzana
 */
public class QueryResultImp implements QueryResult
{
    static Logger log=SWBUtils.getLogger(QueryResultImp.class);
    private final List<SemanticObject> nodes;
    private final String workspaceName;
    private final SessionImp session;
    QueryResultImp(SessionImp session, SemanticObject[] nodes, String workspaceName)
    {
        if ( session == null || workspaceName == null )
        {
            throw new IllegalArgumentException("the session or workspaceName was null");
        }
        if ( SWBContext.getWorkspace(workspaceName) == null )
        {
            throw new IllegalArgumentException("The workspace " + workspaceName + " was not found");
        }
        this.workspaceName = workspaceName;
        this.session = session;
        this.nodes=new ArrayList<SemanticObject>();
        for(SemanticObject id : nodes)
        {
            this.nodes.add(id);
        }
    }
    QueryResultImp(SessionImp session, List<SemanticObject> nodes, String workspaceName)
    {
        if ( session == null || workspaceName == null )
        {
            throw new IllegalArgumentException("the session or workspaceName was null");
        }
        if ( SWBContext.getWorkspace(workspaceName) == null )
        {
            throw new IllegalArgumentException("The workspace " + workspaceName + " was not found");
        }
        this.nodes = nodes;
        this.workspaceName = workspaceName;
        this.session = session;        
        
    }
    public String[] getColumnNames() throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public RowIterator getRows() throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private static String getUri(String uri)
    {
        int pos = uri.indexOf("#");
        if ( pos != -1 )
        {
            uri = uri.substring(0, pos);
        }
        return uri;
    }

    public NodeIterator getNodes() throws RepositoryException
    {
        ArrayList<SimpleNode> simpleNodes = new ArrayList<SimpleNode>();        
        for ( SemanticObject object : nodes )
        { 
            BaseNode node=new BaseNode(object);
            if(session.existSimpleNodeByID(node.getId()))
            {
                simpleNodes.add(session.getSimpleNodeByID(node.getId()));
            }
            else
            {
                SimpleNode simpleNode=new SimpleNode(node, session);
                session.addSimpleNode(simpleNode);
                simpleNodes.add(simpleNode);
            }
        }
        return new NodeIteratorImp(session, simpleNodes);
    }
}
