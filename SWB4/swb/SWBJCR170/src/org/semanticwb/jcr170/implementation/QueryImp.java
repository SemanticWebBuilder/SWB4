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

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;
import java.util.HashSet;
import java.util.List;
import javax.jcr.ItemExistsException;
import javax.jcr.ItemNotFoundException;
import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.UnsupportedRepositoryOperationException;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.query.Query;
import javax.jcr.query.QueryResult;
import javax.jcr.version.VersionException;
import org.jdom.Namespace;
import org.jdom.xpath.XPath;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBContext;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticVocabulary;
import org.semanticwb.repository.BaseNode;

/**
 *
 * @author victor.lorenzana
 */
public class QueryImp implements Query
{

    static Logger log = SWBUtils.getLogger(QueryImp.class);
    private static final String NL = System.getProperty("line.separator");
    public static final String SPARQL = "SPARQL";
    private final String workspaceName;
    private final String statement;
    private final String language;
    private final SessionImp session;
    private XPath xpath = null;
    private StringBuilder prefixStatement = new StringBuilder("");

    QueryImp(SessionImp session, String workspaceName, String statement, String language)
    {
        if (session == null || workspaceName == null)
        {
            throw new IllegalArgumentException("the session or workspaceName was null");
        }
        if (SWBContext.getWorkspace(workspaceName) == null)
        {
            throw new IllegalArgumentException("The workspace " + workspaceName + " was not found");
        }
        this.statement = statement;
        this.workspaceName = workspaceName;
        this.language = language;
        this.session = session;
        if (language.equals(SPARQL))
        {
            for (String prefix : BaseNode.listUris().keySet())
            {
                String uri=BaseNode.listUris().get(prefix);
                if(!uri.endsWith("#"))
                {
                    uri+="#";
                }
                prefixStatement.append("PREFIX " + prefix + ": <" + uri + ">" + NL);
            }
            prefixStatement.append(" PREFIX rdf: <" + SemanticVocabulary.RDF_URI + "> " + NL);
            prefixStatement.append(" PREFIX rdfs: <" + SemanticVocabulary.RDFS_URI + "> " + NL);
        }
        else if (language.equals(javax.jcr.query.Query.XPATH))
        {
            try
            {
                xpath = XPath.newInstance(statement);
                for (String prefix : BaseNode.listUris().keySet())
                {
                    String namespace = BaseNode.listUris().get(prefix);
                    if (namespace.endsWith("#"))
                    {
                        namespace = namespace.substring(0, namespace.length() - 1);
                    }
                    Namespace ns = Namespace.getNamespace(prefix, namespace);
                    xpath.addNamespace(ns);
                }
            }
            catch (Exception e)
            {
                throw new IllegalArgumentException(e);
            }
        }
        else
        {
            throw new IllegalArgumentException("The language " + language + " is not supported");
        }

    }

    public QueryResult execute() throws RepositoryException
    {
        if (xpath != null)
        {
            return null;
        /*try
        {
        List<Element> elements = xpath.selectNodes(session.getDocumentInternalView());
        ArrayList<String> nodes = new ArrayList<String>();
        for (Element e : elements)
        {
        if (e.getAttributeValue("path") != null || !e.getAttributeValue("path").equals(""))
        {
        nodes.add(e.getAttributeValue("path"));
        }
        }
        return new QueryResultImp(session, nodes, workspaceName);
        }
        catch (JDOMException jde)
        {
        throw new RepositoryException(jde);
        }
        catch (Exception jde)
        {
        throw new RepositoryException(jde);
        }
        catch (Throwable jde)
        {
        throw new RepositoryException(jde);
        }*/
        }
        else
        {
            HashSet<SemanticObject> nodes = new HashSet<SemanticObject>();
            Model model = SWBContext.getWorkspace(workspaceName).getSemanticObject().getModel().getRDFModel();
            String sparql = prefixStatement.toString()  + statement;
            com.hp.hpl.jena.query.Query query = com.hp.hpl.jena.query.QueryFactory.create(sparql);
            QueryExecution qexec = QueryExecutionFactory.create(query, model);
            try
            {
                ResultSet rs = qexec.execSelect();
                while (rs.hasNext())
                {
                    QuerySolution rb = rs.nextSolution();
                    List resultVars=rs.getResultVars();
                    for(Object name : resultVars)
                    {
                        if(rb.get(name.toString()).isResource())
                        {
                            Resource res=rb.getResource(name.toString());
                            SemanticObject obj=SemanticObject.createSemanticObject(res);
                            if(obj!=null)
                            {
                                nodes.add(obj);
                            }
                        }
                    }

                }
            }
            catch (Throwable e)
            {
                log.error(e);
            }
            finally
            {
                qexec.close();
            }
            return new QueryResultImp(session, nodes.toArray(new SemanticObject[nodes.size()]), workspaceName);

        }
    }

    public String getStatement()
    {
        return statement;
    }

    public String getLanguage()
    {
        return language;
    }

    public String getStoredQueryPath() throws ItemNotFoundException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Node storeAsNode(String arg0) throws ItemExistsException, PathNotFoundException, VersionException, ConstraintViolationException, LockException, UnsupportedRepositoryOperationException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
