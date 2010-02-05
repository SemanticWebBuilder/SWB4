/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.jcr283.implementation;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;
import java.util.HashSet;
import java.util.List;
import javax.jcr.RepositoryException;
import javax.jcr.query.InvalidQueryException;
import javax.jcr.query.QueryResult;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.jcr283.repository.model.Base;
import org.semanticwb.model.SWBContext;
import org.semanticwb.platform.SemanticObject;

/**
 *
 * @author victor.lorenzana
 */
public class SPARQLQuery extends QueryImp
{

    private static Logger log = SWBUtils.getLogger(SPARQLQuery.class);
    public static final String SPARQL = "SPARQL";
    private static final String NL = "\r\n";
    private StringBuilder prefixStatement = new StringBuilder("");

    public SPARQLQuery(SessionImp session, String statement) throws RepositoryException
    {
        super(session, statement, SPARQL);

        for (String prefix : session.getNamespacePrefixes())
        {
            String uri = session.getNamespaceURI(prefix);
            if (!uri.endsWith("#"))
            {
                uri += "#";
            }
            prefixStatement.append("PREFIX " + prefix + ": <" + uri + ">" + NL);
        }
    }

    public QueryResult execute() throws InvalidQueryException, RepositoryException
    {
        HashSet<SemanticObject> semanticObjects = new HashSet<SemanticObject>();
        Model model = SWBContext.getWorkspace(session.getWorkspaceImp().getName()).getSemanticObject().getModel().getRDFModel();
        String sparql = prefixStatement.toString() + statement;
        com.hp.hpl.jena.query.Query query = com.hp.hpl.jena.query.QueryFactory.create(sparql);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        try
        {
            ResultSet rs = qexec.execSelect();
            while (rs.hasNext())
            {
                QuerySolution rb = rs.nextSolution();
                List resultVars = rs.getResultVars();
                for (Object name : resultVars)
                {
                    if (rb.get(name.toString()).isResource())
                    {
                        Resource res = rb.getResource(name.toString());
                        SemanticObject obj = SemanticObject.createSemanticObject(res);
                        if (obj != null && (obj.getSemanticClass().isSubClass(Base.sclass) || obj.getSemanticClass().equals(Base.sclass)))
                        {
                            semanticObjects.add(obj);
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
        HashSet<NodeImp> nodes = new HashSet<NodeImp>();
        for(SemanticObject obj : semanticObjects)
        {
            Base base=new Base(obj);
            NodeImp node=session.getWorkspaceImp().getNodeManager().getNodeByIdentifier(base.getId(),session,NodeTypeManagerImp.loadNodeType(obj.getSemanticClass()));
            if(node!=null)
            {
                nodes.add(node);
            }
        }
        return new QueryResultImp(nodes);
    }
}
