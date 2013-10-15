/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.rdf.sparql;

import com.hp.hpl.jena.rdf.model.Model;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.runtime.tree.Tree;
import org.semanticwb.SWBUtils;


/**
 *
 * @author javier.solis.g
 */
public class SparqlProcessor
{
    private boolean showTree=false;    
    
    private String querytype = null;
    private HashMap<String, String> prefixs = new HashMap();      //prefijos
    private ArrayList<QueryNode> sparams = new ArrayList();          //parametros select
    private HashMap<String, QueryNode> vars = new HashMap();      //variables
    private ArrayList<QueryNode> ovars = new ArrayList();      //variables
    private ArrayList<QueryTriple> triples = new ArrayList();     //query triples
    private ArrayList<ParamNode> sqlparams =new ArrayList();        //Parametros a reemplazar en prepareStatement
    private Long limit=null;                                        //limite
    private Long offset=null;                                       //offset
    private ArrayList<OrderVar> orderbyvars = new ArrayList();      //ordenamientos
    
    private QueryNode temp_subj;                        //Valiables temporales
    private QueryNode temp_prop;
    private QueryNode temp_obj;    
    private boolean temp_typed=false;    
    private boolean temp_orderbydesc;    
    private boolean isCount=false;
    
    public static void main(String args[])
    {
        SparqlProcessor qp=new SparqlProcessor();
        qp.setShowTree(true);
        
        String query=
                    "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                    "PREFIX prop: <http://prop/>\n" +
                    "PREFIX onto: <http://ontology/>\n" +
                    "\n" +
//                    "select ?nombre ?telefono ?estado ?nempresa ?telempresa\n" +
//                    "select (count(?s) as ?c)\n" +
                "select count(*)\n" +
                    "where {\n" +
                    "  ?s a onto:Persona.\n" +
                    "  ?s prop:nombre ?nombre.\n" +
                    "  ?s prop:telefono ?telefono.\n" +
                    "  ?s prop:direccion ?direccion.\n" +
                    "  ?direccion prop:estado \"estado 0\".\n" +
                    "  ?s prop:empresa ?empresa .\n" +
                    "  ?empresa prop:nombre ?nempresa .\n" +
                    "  OPTIONAL { ?empresa prop:telefono2 ?telempresa2. }.\n" +
                    "  ?empresa prop:telefono ?telempresa.\n" +
//                    "  ?empresa prop:telefono 12.45 .\n" +                
                    "} \n" +
                    "ORDER BY DESC (?telefono) ?nombre \n" +
                    "OFFSET 10 \n" +
                    "LIMIT 100";
        
        qp.process(null, query);
          
        String iquery=qp.getIternalQuery("3");
        
        //Replace ?
        iquery=iquery.replace('?', '&');        
        Iterator<ParamNode> it=qp.sqlparams.iterator();
        while (it.hasNext())
        {
            QueryNode node = it.next().getNode();
            if(node.isIRI())
            {
                iquery=iquery.replaceFirst("&", "'uri|"+node.getValue().substring(1,node.getValue().length()-1)+"'");
            }
            else if(node.isString())
            {
                iquery=iquery.replaceFirst("&", "'lit|_|_|"+node.getValue()+"'");
            }
            else
            {
                iquery=iquery.replaceFirst("&", "'lit|"+node.getNodeType().replace("xsd:", "http://www.w3.org/2001/XMLSchema#")+"|_|"+node.getValue()+"'");
            }
        }
        
        System.out.println("prefixs:"+qp.prefixs);
        System.out.println("querytype:"+qp.querytype);
        System.out.println("sparams:"+qp.sparams);
        System.out.println("vars:"+qp.vars);
        System.out.println("triples:"+qp.triples);
        System.out.println("limit:"+qp.limit);
        System.out.println("offset:"+qp.offset);
        System.out.println("orderby:"+qp.orderbyvars);        
        
        System.out.println("iquery:"+iquery);
    }    

    public SparqlProcessor()
    {
        //prefixs.put("xsd", "<http://www.w3.org/2001/XMLSchema#>");
    }
    
    public void setShowTree(boolean showTree)
    {
        this.showTree = showTree;
    }    

    public void process(Model model, String sparql)
    {
        if(sparql==null)return;
        
        //Validacion de contador no estandar de sparql 
        int ci=sparql.toLowerCase().indexOf("count(*)");
        if(ci>-1)
        {
            sparql=sparql.substring(0,ci)+"*"+sparql.substring(ci+8);
            isCount=true;
        }
        System.out.println(sparql);
        
        SparqlLexer lex = null;
        try
        {
            lex = new SparqlLexer(new ANTLRInputStream(sparql));

        } catch (Exception ex)
        {
            Logger.getLogger(SparqlMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        CommonTokenStream tokens = new CommonTokenStream(lex);
        tokens.fill();

        SparqlParser parser = new SparqlParser(tokens);
        parser.setBuildParseTree(true);

        ParserRuleContext t = parser.query();

        String rnames[] = parser.getRuleNames();

        processTree(t, rnames);
        
        if(showTree)t.inspect(parser);
    }

    public ArrayList<ParamNode> getSQLParams()
    {
        return sqlparams;
    }
    
    private String getJoinCond(QueryTriple triple,String tblname, int tbl)
    {
        StringBuilder ret=new StringBuilder();
        if(triple.isOptional())
        {
            ret.append("\n   left join ");        
        }else
        {
            ret.append("\n   inner join ");        
        }
        ret.append(tblname+" as t"+(tbl));
        ret.append(" on (");
        String on="";
        if(triple.subj.isVar() && triple.subj.getLastInternalVar()!=null)
        {
            on+="t"+(tbl)+".subj="+triple.subj.getLastInternalVar();
        }
        if(triple.prop.isVar() && triple.prop.getLastInternalVar()!=null)
        {
            if(!on.isEmpty())on+=" and ";
            on+="t"+(tbl)+".prop="+triple.prop.getLastInternalVar();
        }
        if(triple.obj.isVar() && triple.obj.getLastInternalVar()!=null)
        {
            if(!on.isEmpty())on+=" and ";
            on+="t"+(tbl)+".obj="+triple.obj.getLastInternalVar();
        }
        if(on.isEmpty())
        {
            throw new UnsupportedOperationException("Not supported query");
        }
        on+=" and "+getTripleCond(triple,tbl);
        ret.append(on+")");
        return ret.toString();
    }    

    private void setTripleSQLParams(QueryTriple triple,int tbl)
    {
        if(!triple.getSubj().isVar())
        {
            sqlparams.add(new ParamNode(triple.subj,"subj"));
        }
        
        if(!triple.getProp().isVar())
        {
            sqlparams.add(new ParamNode(triple.prop,"prop"));
        }
        
        if(!triple.getObj().isVar())
        {
            sqlparams.add(new ParamNode(triple.obj,"obj"));
        }
    }
    
    
    private String getTripleCond(QueryTriple triple,int tbl)
    {
        String ret="";
        
        if(triple.getSubj().isVar())
        {
            triple.getSubj().addInternalVar("t"+tbl+".subj",triple.isOptional());
        }else
        {
            ret+="t"+tbl+".subj=?";
        }
        
        if(triple.getProp().isVar())
        {
            triple.getProp().addInternalVar("t"+tbl+".prop",triple.isOptional());
        }else
        {
            if(!ret.isEmpty())ret=ret+" and ";
            ret+="t"+tbl+".prop=?";
        }
        
        if(triple.getObj().isVar())
        {
            triple.getObj().addInternalVar("t"+tbl+".obj",triple.isOptional());
        }else
        {
            if(!ret.isEmpty())ret=ret+" and ";
            ret+="t"+tbl+".obj=?";
        }
        return ret;
    }
    
    public String getIternalQuery(String modelid)
    {
        StringBuilder select=new StringBuilder();
        StringBuilder from=new StringBuilder();
        StringBuilder where=new StringBuilder();
        StringBuilder ext=new StringBuilder();        

        String tblname="swb_graph_ts"+modelid;
        
        from.append(" from "+tblname+" as t0");
        
        if(!triples.isEmpty())
        {
            QueryTriple triple=triples.get(0);  
            
            where.append(" where ");
            where.append("\n   "+getTripleCond(triple,0));
                
            for(int x=1;x<triples.size();x++)
            {
                QueryTriple triple2=triples.get(x);                        
                
                from.append(getJoinCond(triple2, tblname, x));
                
                //where.append("\n   and "+getTripleCond(triple2,x));
                setTripleSQLParams(triple2,x);
            }
            
            setTripleSQLParams(triple,0);
        }
        
        if(querytype.equals("select"))
        {
            String dbtype=SWBUtils.DB.DBTYPE_HSQLDB;
            
            try
            {
                dbtype=SWBUtils.DB.getDatabaseType();
            }catch(Exception e)
            {
                e.printStackTrace();
            }
            
            if(dbtype==SWBUtils.DB.DBTYPE_MySQL)
            {
                select.append("select STRAIGHT_JOIN");  //Ordenamiento directo
            }else
            {
                select.append("select");  //Ordenamiento directo
            }
            if(isCount)
            {
                select.append(" count(*)"); 
            }else
            {
                Iterator<QueryNode> it=null;
                if(!sparams.isEmpty())
                {
                    it=sparams.iterator();
                }else
                {
                    it=ovars.iterator();               
                }
                boolean first=true;
                while (it.hasNext())
                {
                    QueryNode node = it.next();
                    if(first)
                    {
                        select.append(" ");
                        first=false;
                    }
                    else select.append(", ");
                    select.append(node.getLastInternalVar()+" as "+node.getValue().substring(1));
                    //select.append(", ");
                    //select.append(node.getLastInternalVar().substring(0,node.getLastInternalVar().indexOf("."))+".ext as __ext__"+node.getValue().substring(1));
                } 
            }
        }
        
        if(!isCount)
        {
            if(!orderbyvars.isEmpty())
            {
                ext.append("order by");
                Iterator<OrderVar> it=orderbyvars.iterator();
                boolean first=true;
                while (it.hasNext())
                {
                    OrderVar orderVar = it.next();
                    if(first)
                    {
                        ext.append(" ");
                        first=false;
                    }
                    else ext.append(", ");
                    ext.append(orderVar.getNode().getLastInternalVar());
                    if(orderVar.isDesc())ext.append(" desc");
                }  
                ext.append("\n");
            }    
            if(offset!=null && limit!=null)
            {
                ext.append("limit "+limit+" offset "+offset);
            }else if(limit!=null)
            {
                ext.append("limit "+limit);
            }
        }
        
        return select.toString()+"\n"+from.toString()+"\n"+where.toString()+"\n"+ext.toString();
    }
    
    /**
     * Valida si se encuentran los elementos de path en la ruta de padres del nodo
     * @param t
     * @param ruleNames
     * @param path
     * @return 
     */
    private boolean validatePath(Tree t, String ruleNames[], String path[])
    {
        return validatePath(t, ruleNames, path, null);
    }
    
    /**
     * Valida si se encuentran los elementos de path en la ruta de padres del nodo y si no se encuentran los elementos de npath en la misma ruta
     * @param t
     * @param ruleNames
     * @param path
     * @param npath
     * @return 
     */
    private boolean validatePath(Tree t, String ruleNames[], String path[], String npath[])
    {
        boolean valid=false;
        int l=path.length-1;
        while(t!=null && !valid)
        {
            String aux=path[l];
            String node=getTextNode(t, ruleNames);
            
            if(npath!=null)
            {
                for(int x=0;x<npath.length;x++)
                {
                    if(node.equals(npath[x]))
                    {
                        return false;
                    }
                }
            }
            
            if(aux.equals(node))
            {
                l--;
                if(l<0)valid=true;
            }
            t=t.getParent();
        }
        return valid;        
    }
    
    private String getTextNode(Tree t, String ruleNames[])
    {
        String node=null;
        if(t instanceof RuleNode)node="rn:"+ruleNames[((RuleNode) t).getRuleContext().getRuleIndex()];
        else if(t instanceof ErrorNode)node="en:"+t.toString();
        else if(t instanceof TerminalNode)node="tn:"+((TerminalNode) t).getSymbol().getText();            
        return node;
    }
    
    private QueryNode getQueryNode(Tree t, String ruleNames[])
    {
        String node=getTextNode(t, ruleNames).substring(3);        
        QueryNode ret=null;
        if(validatePath(t, ruleNames, new String[]{"rn:var"}))
        {       
            ret=vars.get(node);
            if(ret==null)
            {
                ret=new QueryNode(node,QueryNode.VAR);
                vars.put(ret.getValue(), ret);
                ovars.add(ret);
            }
        }else if(validatePath(t, ruleNames, new String[]{"rn:iri"}))
        {
            if(validatePath(t, ruleNames, new String[]{"rn:prefixedName"}))
            {
                int i=node.indexOf(":");
                String pre=node.substring(0,i+1);
                String pos=node.substring(i+1);

                String n=prefixs.get(pre);
                if(n!=null)
                {
                    if(n.endsWith(">"))
                    {
                        n=n.substring(0,n.length()-1)+pos+">";
                    }else
                    {
                        n=n+pos;
                    }                
                }else
                {
                    n=node;
                }
                
                //System.out.println("-->"+pre+" "+pos);
                //TODO:
                ret=new QueryNode(n,QueryNode.IRI);
            }else
            {
                ret=new QueryNode(node,QueryNode.IRI);
            }
        }else
        {
            if(node.equals("a"))
            {
                node="<http://www.w3.org/1999/02/22-rdf-syntax-ns#type>";
                ret=new QueryNode(node,QueryNode.IRI);
            }else
            {
                if(node.charAt(0)=='"')
                {
                    ret=new QueryNode(node.substring(1,node.length()-1),QueryNode.STRING);
                }else
                {                    
                    ret=new QueryNode(node,QueryNode.STRING);
                }
                
                if(validatePath(t, ruleNames, new String[]{"rn:numericLiteral"}))
                {
                    if(ret.getValue().indexOf(".")>-1)
                    {
                        ret.setNodeType("xsd:double");
                    }else
                    {
                        ret.setNodeType("xsd:long");
                    }                    
                }else if(validatePath(t, ruleNames, new String[]{"rn:booleanLiteral"}))
                {
                    ret.setNodeType("xsd:boolean");
                }
            }
        }
        return ret;
    }

    private boolean processNode(Tree t, String ruleNames[])
    {        
        boolean ret=false;
        String node=getTextNode(t, ruleNames);
        //System.out.println("payload:" + t.getPayload().toString());        
        //System.out.println("node:"+node);
        
        if("rn:prefixDecl".equals(node))
        {
            if(t.getChildCount()==3)
            {
                prefixs.put(((TerminalNode) t.getChild(1)).getSymbol().getText(), ((TerminalNode) t.getChild(2)).getSymbol().getText());
                ret=true;
            }            
        }else if("rn:offsetClause".equals(node))
        {
            if(t.getChildCount()==2)
            {
                offset=Long.parseLong(((TerminalNode) t.getChild(1)).getSymbol().getText());
                ret=true;
            }            
        }else if("rn:limitClause".equals(node))
        {
            if(t.getChildCount()==2)
            {
                limit=Long.parseLong(((TerminalNode) t.getChild(1)).getSymbol().getText());
                ret=true;
            }            
        }else if("rn:orderCondition".equals(node))
        {
            temp_orderbydesc=false; //por default es asc
        }
        
        if ((t instanceof RuleNode))
        {
            
        } else if (t instanceof ErrorNode)
        {
            System.out.println("Error:"+t);
        } else if (t instanceof TerminalNode)
        {
            if(validatePath(t, ruleNames, new String[]{"rn:selectClause"}))
            {
                if(node.equalsIgnoreCase("tn:select"))
                {
                    querytype="select";
                }else
                {
                    QueryNode qn=getQueryNode(t, ruleNames);
                    if(!(!qn.isVar() && qn.getValue().equals("*")))
                    {
                        sparams.add(qn);
                    }
                }
            }else if(validatePath(t, ruleNames, new String[]{"rn:triplesBlock"}))
            {
                if(validatePath(t, ruleNames, new String[]{"rn:triplesSameSubjectPath","rn:varOrTerm"},new String[]{"rn:propertyListPathNotEmpty"}))
                {
                    temp_subj=getQueryNode(t, ruleNames);
                }else if(validatePath(t, ruleNames, new String[]{"rn:triplesSameSubjectPath","rn:propertyListPathNotEmpty"}))
                {
                    if(validatePath(t, ruleNames, new String[]{"rn:verbPath"}) || validatePath(t, ruleNames, new String[]{"rn:verbSimple"}))
                    {
                        temp_prop=getQueryNode(t, ruleNames);
                    }else if(validatePath(t, ruleNames, new String[]{"rn:objectList"}) || validatePath(t, ruleNames, new String[]{"rn:objectListPath"}))
                    {
                        QueryNode n=getQueryNode(t, ruleNames);
                        if(n.getValue().equals("^^"))
                        {
                            temp_typed=true;
                        }else if(!temp_typed)
                        {
                            temp_obj=n;
                            QueryTriple qt=new QueryTriple(temp_subj, temp_prop, temp_obj);
                            triples.add(qt);
                            if(validatePath(t, ruleNames, new String[]{"rn:optionalGraphPattern"}))
                            {
                                qt.setOptional(true);
                            }
                        }else
                        {
                            temp_obj.setNodeType(n.getValue());
                            temp_typed=false;
                        }
                    }
                }
            }else if(validatePath(t, ruleNames, new String[]{"rn:orderCondition"}))
            {
                if(node.equalsIgnoreCase("tn:DESC"))
                {
                    temp_orderbydesc=true;
                }else
                {
                    if(validatePath(t, ruleNames, new String[]{"rn:var"}))
                    {
                        QueryNode qn=getQueryNode(t, ruleNames);
                        orderbyvars.add(new OrderVar(qn, temp_orderbydesc));
                    }
                }
            }
        }
        return ret;        
    }

    private void processTree(Tree t, String rnames[])
    {
        //System.out.println("payload:" + t.getPayload().toString());        
        if(!processNode(t, rnames))
        {
            for (int i = 0; i < t.getChildCount(); i++)
            {
                Tree tree = (Tree) t.getChild(i);
                processTree(tree, rnames);
            }
        }
    }
}

class OrderVar
{
    private QueryNode node;
    private boolean desc=false;

    public OrderVar(QueryNode node)
    {
        this.node = node;
    }
    
    public OrderVar(QueryNode node, boolean desc)
    {
        this.node = node;
        this.desc = desc;
    }

    @Override
    public String toString()
    {
        return "{desc:"+desc+", "+node+"}";
    }

    public QueryNode getNode()
    {
        return node;
    }

    public boolean isDesc()
    {
        return desc;
    }       
}

class ParamNode
{
    private QueryNode node;
    private String type=null;

    public ParamNode(QueryNode node, String type)
    {
        this.node = node;
        this.type = type;
    }
    
    @Override
    public String toString()
    {
        return "{node:"+node+", type:"+type+"}";
    }

    public QueryNode getNode()
    {
        return node;
    }

    public String getType()
    {
        return type;
    }        
     
}

class QueryNode
{
    public static final String IRI="iri";
    public static final String VAR="var";
    public static final String STRING="string";
    
    String value;
    ArrayList<String> internalVars=new ArrayList();
    String nodeType;

    public QueryNode(String value, String nodeType)
    {
        this.value = value;
        this.nodeType = nodeType;
    }
    
    public String getValue()
    {
        return value;
    }

    public String getNodeType()
    {
        return nodeType;
    }

    public void setNodeType(String nodeType)
    {
        this.nodeType = nodeType;
    }
        

    public void setValue(String value)
    {
        this.value = value;
    }

    @Override
    public String toString()
    {
        if(isVar())
        {
            return "{var:"+value+", intVars:"+internalVars+"}";
        }else
        {
            return "{node:"+value+", type:"+nodeType+"}";
        }        
    }
    
    public boolean isVar()
    {
        if(nodeType.equals(VAR))return true;
        return false;
    }
    
    public boolean isIRI()
    {
        if(nodeType.equals(IRI))return true;
        return false;
    }
    
    public boolean isString()
    {
        if(nodeType.equals(STRING))return true;
        return false;
    }
    
    public void addInternalVar(String var)
    {
        if(isVar())
        {
            internalVars.add(var);
        }
    }
    
    public void addInternalVar(String var, boolean optional)
    {
        if(isVar())
        {
            if(!(optional && !internalVars.isEmpty()))
            {
                internalVars.add(var);
            }
        }
    }    
    
    public String getLastInternalVar()
    {
        if(isVar())
        {
            if(!internalVars.isEmpty())
            {
                return internalVars.get(internalVars.size()-1);
            }        
        }
        return null;
    }   
}


class QueryTriple
{

    QueryNode subj;
    QueryNode prop;
    QueryNode obj;
    boolean optional=false;

    public QueryTriple()
    {
    }

    public QueryTriple(QueryNode subj, QueryNode prop, QueryNode obj)
    {
        this.subj = subj;
        this.prop = prop;
        this.obj = obj;
    }

    public QueryNode getObj()
    {
        return obj;
    }

    public QueryNode getProp()
    {
        return prop;
    }

    public QueryNode getSubj()
    {
        return subj;
    }

    public void setObj(QueryNode obj)
    {
        this.obj = obj;
    }

    public void setProp(QueryNode prop)
    {
        this.prop = prop;
    }

    public void setSubj(QueryNode subj)
    {
        this.subj = subj;
    }

    @Override
    public String toString()
    {
        return "{"+subj+", "+prop+", "+obj+", "+optional+"}";
    }    

    public void setOptional(boolean optional)
    {
        this.optional = optional;
    }

    public boolean isOptional()
    {
        return optional;
    }
    
}
