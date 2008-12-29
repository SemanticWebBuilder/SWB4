/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
                prefixStatement.append("PREFIX " + prefix + ": <" + BaseNode.listUris().get(prefix) + ">" + NL);
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
            String sparql = prefixStatement.toString() + NL + statement;
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
                            nodes.add(SemanticObject.getSemanticObject(res.getURI()));
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
