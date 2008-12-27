package org.semanticwb.model;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.sparql.util.IndentedWriter;
import java.util.ArrayList;
import java.util.Iterator;
import org.semanticwb.SWBPlatform;
import org.semanticwb.model.base.*;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.platform.SemanticVocabulary;

public class UserRepository extends UserRepositoryBase
{

    public static final String SWBUR_AuthMethod = "SWBUR_AuthMethod";
    public static final String SWBUR_LoginContext = "SWBUR_LoginContext";
    public static final String SWBUR_CallBackHandlerClassName = "SWBUR_CallBackHandlerClassName";
    public static final String SWBUR_ClassHold = "extendedAttributes";
    public static final String SWBUR_ClassPost = "#clsExtendedAttibutes";
    public static final String SWBUR_ClassUserTypeHold = "userType";
    public static final String SWBUR_ClassUserTypePost = "/clsUserType";
    private static ArrayList<String> userTypes = new ArrayList<String>();
    private static final String NL = System.getProperty("line.separator");

    public UserRepository(SemanticObject base)
    {
        super(base);
    /*
    String uri = getProperty(SWBUR_ClassHold);
    if (uri != null)
    {
    uri = getId() + SWBUR_ClassPost;
    getSemanticObject().getModel().registerClass(uri);
    }
    StmtIterator ptopIt = getSemanticObject().getModel().getRDFModel().listStatements(getSemanticObject().getRDFResource(), null, (String) null);
    while (ptopIt.hasNext())
    {
    Statement sp = (Statement) ptopIt.next();
    if (sp.getPredicate().getLocalName().startsWith(SWBUR_ClassUserTypeHold))
    {
    uri = sp.getObject().toString();
    userTypes.add(uri.split("#")[1]);
    getSemanticObject().getModel().registerClass(uri);
    }
    }
     */
    }

    public Iterator<String> searchUsersBy(String usrFirstName, String usrLastName, String usrSecondLastName, String usrEmail, String Role, String Group)
    {
        System.out.println("Grp: "+Group);

        Iterator<String> ret = null;
        Model model = SWBPlatform.getSemanticMgr().getOntology().getRDFOntModel();

        // First part or the query string
        String prolog = "PREFIX swb: <" + SemanticVocabulary.URI + ">";
        prolog += "PREFIX rdf: <" + SemanticVocabulary.RDF_URI + ">";
        prolog += "PREFIX rdfs: <" + SemanticVocabulary.RDFS_URI + ">";


        String _usrFirstName = usrFirstName!=null?usrFirstName:"";
        String _usrLastName = usrLastName!=null?usrLastName:"";
        String _usrSecondLastName = usrSecondLastName!=null?usrSecondLastName:"";
        String _usrEmail = usrEmail!=null?usrEmail:"";
        String _Role = Role!=null?Role:null;
        String _Group = Group!=null?Group:null;

        if (null!=_Role){
            if (hasRole(_Role)){
                _Role = getRole(_Role).getURI();
            } else _Role = null;
        }

        if (null!=_Group){
            if (hasUserGroup(_Group)){
                _Group = getUserGroup(_Group).getURI();
            } else _Group = null;
        }


        // Query string.
        //"SELECT ?title ?class WHERE {?x swb:title ?title. ?x rdf:type swb:WebPage}"

        String queryString = prolog + NL +
                "SELECT ?x ?fname ?lname ?slname ?mail ?login WHERE {?x rdf:type swb:User. ";
        if (null!=_Role)     queryString +=   "?x swb:hasRole <"+_Role+">." ;
        if (null!=_Group)     queryString +=   "?x swb:userGroup <"+_Group+">." ;
        if (!"".equals(_usrFirstName))     queryString +=   "?x swb:usrFirstName ?gfn .   FILTER regex(?gfn, \""+_usrFirstName+"\", \"i\"). " ;
        if (!"".equals(_usrLastName))     queryString +=   "?x swb:usrLastName ?gln.   FILTER regex(?gln, \""+_usrLastName+"\", \"i\"). " ;
        if (!"".equals(_usrSecondLastName))     queryString +=   "?x swb:usrSecondLastName ?gsln.   FILTER regex(?gsln, \""+_usrSecondLastName+"\", \"i\"). " ;
        if (!"".equals(_usrEmail))     queryString +=   "?x swb:usrEmail ?gml.   FILTER regex(?gml, \""+_usrEmail+"\", \"i\"). " ;
             queryString +=   "?x swb:usrLastName ?lname. " +
                "?x swb:usrFirstName ?fname. " +
                "?x swb:usrSecondLastName ?slname. " +
                "?x swb:usrEmail ?mail. " +
                "?x swb:usrLogin ?login " +
                "}";


        Query query = QueryFactory.create(queryString);

        System.out.println(getId());
        // Print with line numbers
        query.serialize(new IndentedWriter(System.out,true)) ;
        System.out.println() ;

        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        try
        {
            ResultSet rs = qexec.execSelect();
            ArrayList<String> lista = new ArrayList<String>();
            for (; rs.hasNext();)
            {
                QuerySolution rb = rs.nextSolution();
                String current = rb.get("x").toString() + "||" +
                        rb.get("fname").toString() + "||" +
                        rb.get("lname").toString() + "||" +
                        rb.get("slname").toString() + "||" +
                        rb.get("mail").toString() + "||" +
                        rb.get("login").toString();
                lista.add(current);

            }
            ret = lista.iterator();
        } finally
        {
            // QueryExecution objects should be closed to free any system resources
            qexec.close();
        }

        return ret;
    }

    public User getUserByLogin(String login)
    {
        User ret = null;
        if (null != login)
        {
            Iterator aux = getSemanticObject().getRDFResource().getModel().listStatements(null, User.swb_usrLogin.getRDFProperty(), getSemanticObject().getModel().getRDFModel().createLiteral(login));
            Iterator it = new GenericIterator(User.swb_User, aux, true);
            if (it.hasNext())
            {
                ret = (User) it.next();
            }
        }
        return ret;
    }

    public SemanticProperty getExtendedAttribute(String name)
    {
        return getExtendedAttributesClass().getProperty(name);
    }

    public SemanticProperty createIntExtendedAttribute(String name)
    {
        return createIntExtendedAttribute(name, null);
    }

    public SemanticProperty createIntExtendedAttribute(String name, String clsName)
    {
        SemanticClass cls = (null == clsName) ? getExtendedAttributesClass() : getUserType(clsName);
        SemanticProperty sp = cls.getProperty(name);
        if (null == sp)
        {
            sp = getSemanticObject().getModel().createSemanticProperty(getId() + "#" + name, cls, SemanticVocabulary.OWL_DATATYPEPROPERTY, SemanticVocabulary.XMLS_INT);
        }
        return sp;
    }

    public SemanticProperty createLongExtendedAttribute(String name)
    {
        return createLongExtendedAttribute(name, null);
    }

    public SemanticProperty createLongExtendedAttribute(String name, String clsName)
    {
        SemanticClass cls = (null == clsName) ? getExtendedAttributesClass() : getUserType(clsName);
        SemanticProperty sp = cls.getProperty(name);
        if (null == sp)
        {
            sp = getSemanticObject().getModel().createSemanticProperty(getId() + "#" + name, cls, SemanticVocabulary.OWL_DATATYPEPROPERTY, SemanticVocabulary.XMLS_LONG);
        }
        return sp;
    }

    public SemanticProperty createBooleanExtendedAttribute(String name)
    {
        return createBooleanExtendedAttribute(name, null);
    }

    public SemanticProperty createBooleanExtendedAttribute(String name, String clsName)
    {
        SemanticClass cls = (null == clsName) ? getExtendedAttributesClass() : getUserType(clsName);
        SemanticProperty sp = cls.getProperty(name);
        if (null == sp)
        {
            sp = getSemanticObject().getModel().createSemanticProperty(getId() + "#" + name, cls, SemanticVocabulary.OWL_DATATYPEPROPERTY, SemanticVocabulary.XMLS_BOOLEAN);
        }
        return sp;
    }

    public SemanticProperty createDateTimeExtendedAttribute(String name)
    {
        return createDateTimeExtendedAttribute(name, null);
    }

    public SemanticProperty createDateTimeExtendedAttribute(String name, String clsName)
    {
        SemanticClass cls = (null == clsName) ? getExtendedAttributesClass() : getUserType(clsName);
        SemanticProperty sp = cls.getProperty(name);
        if (null == sp)
        {
            sp = getSemanticObject().getModel().createSemanticProperty(getId() + "#" + name, cls, SemanticVocabulary.OWL_DATATYPEPROPERTY, SemanticVocabulary.XMLS_DATETIME);
        }
        return sp;
    }

    public SemanticProperty createDoubleExtendedAttribute(String name)
    {
        return createDoubleExtendedAttribute(name, null);
    }

    public SemanticProperty createDoubleExtendedAttribute(String name, String clsName)
    {
        SemanticClass cls = (null == clsName) ? getExtendedAttributesClass() : getUserType(clsName);
        SemanticProperty sp = cls.getProperty(name);
        if (null == sp)
        {
            sp = getSemanticObject().getModel().createSemanticProperty(getId() + "#" + name, cls, SemanticVocabulary.OWL_DATATYPEPROPERTY, SemanticVocabulary.XMLS_DOUBLE);
        }
        return sp;
    }

    public SemanticProperty createFloatExtendedAttribute(String name)
    {
        return createFloatExtendedAttribute(name, null);
    }

    public SemanticProperty createFloatExtendedAttribute(String name, String clsName)
    {
        SemanticClass cls = (null == clsName) ? getExtendedAttributesClass() : getUserType(clsName);
        SemanticProperty sp = cls.getProperty(name);
        if (null == sp)
        {
            sp = getSemanticObject().getModel().createSemanticProperty(getId() + "#" + name, cls, SemanticVocabulary.OWL_DATATYPEPROPERTY, SemanticVocabulary.XMLS_FLOAT);
        }
        return sp;
    }

    public SemanticProperty createStringExtendedAttribute(String name)
    {
        return createStringExtendedAttribute(name, null);
    }

    public SemanticProperty createStringExtendedAttribute(String name, String clsName)
    {
        SemanticClass cls = (null == clsName) ? getExtendedAttributesClass() : getUserType(clsName);
        SemanticProperty sp = cls.getProperty(name);
        if (null == sp)
        {
            sp = getSemanticObject().getModel().createSemanticProperty(getId() + "#" + name, cls, SemanticVocabulary.OWL_DATATYPEPROPERTY, SemanticVocabulary.XMLS_STRING);
        }
        return sp;
    }

    public Iterator<SemanticProperty> listAttributesofUserType(String name)
    {
        ArrayList<SemanticProperty> alsp = new ArrayList<SemanticProperty>();
        Iterator<SemanticProperty> itsp = getUserType(name).listProperties();
        while (itsp.hasNext())
        {
            SemanticProperty sp = itsp.next();
            if (null == sp.getRange())
            {
                continue;
            }
            alsp.add(sp);
        }
        return alsp.iterator();
    }

    public Iterator<SemanticProperty> listExtendedAttributes()
    {
        ArrayList<SemanticProperty> alsp = new ArrayList<SemanticProperty>();
        Iterator<SemanticProperty> itsp = getExtendedAttributesClass().listProperties();
        while (itsp.hasNext())
        {
            SemanticProperty sp = itsp.next();
            if (null == sp.getRange())
            {
                continue;
            }
            alsp.add(sp);
        }
        return alsp.iterator();
    }

    public Iterator<SemanticProperty> listBasicAttributes()
    {
        ArrayList<SemanticProperty> alsp = new ArrayList<SemanticProperty>();
        SemanticClass sc = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(User.swb_User.getURI());
        if (null != sc)
        {
            Iterator<SemanticProperty> itsp = sc.listProperties();
            while (itsp.hasNext())
            {
                SemanticProperty sp = itsp.next();
                if (null == sp.getRange())
                {
                    continue;
                }
                alsp.add(sp);
            }
        }
        return alsp.iterator();
    }

    public Iterator<SemanticProperty> listAttributes()
    {
        ArrayList<SemanticProperty> alsp = new ArrayList<SemanticProperty>();
        String uri = null;
        SemanticClass sc = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(User.swb_User.getURI());
        if (null != sc)
        {
            Iterator<SemanticProperty> itsp = sc.listProperties();
            while (itsp.hasNext())
            {
                SemanticProperty sp = itsp.next();
                if (null == sp.getRange())
                {
                    continue;
                }
                alsp.add(sp);
            }
        }
        uri = getProperty(SWBUR_ClassHold);
        sc = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(uri);
        if (null != sc)
        {
            Iterator<SemanticProperty> itsp = sc.listProperties();
            while (itsp.hasNext())
            {
                SemanticProperty sp = itsp.next();
                if (null == sp.getRange())
                {
                    continue;
                }
                alsp.add(sp);
            }
        }
        StmtIterator ptopIt = getSemanticObject().getModel().getRDFModel().listStatements(getSemanticObject().getRDFResource(), null, (String) null);
        while (ptopIt.hasNext())
        {
            Statement st = (Statement) ptopIt.next();
            if (st.getPredicate().getLocalName().startsWith(SWBUR_ClassUserTypeHold))
            {
                uri = st.getObject().toString();
                sc = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(uri);
                if (null != sc)
                {
                    Iterator<SemanticProperty> itsp = sc.listProperties();
                    while (itsp.hasNext())
                    {
                        SemanticProperty sp = itsp.next();
                        if (null == sp.getRange())
                        {
                            continue;
                        }
                        alsp.add(sp);
                    }
                }
            }
        }
        return alsp.iterator();
    }

    public SemanticClass getExtendedAttributesClass()
    {
        SemanticClass cls = null;
        String uri = getProperty(SWBUR_ClassHold);
        if (uri == null)
        {
            uri = getId() + SWBUR_ClassPost;
            cls = getSemanticObject().getModel().createSemanticClass(uri);
            setProperty(SWBUR_ClassHold, uri);
        } else
        {
            cls = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(uri);
        }
        return cls;
    }

    public SemanticClass getUserType(String name)
    {
        SemanticClass cls = null;
        String uri = getProperty(SWBUR_ClassUserTypeHold + name);
        if (uri == null)
        {
            uri = getId() + SWBUR_ClassUserTypePost + "#" + name;
            cls = getSemanticObject().getModel().createSemanticClass(uri);
            setProperty(SWBUR_ClassUserTypeHold + name, uri);
            userTypes.add(uri.split("#")[1]);
        } else
        {
            cls = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(uri);
        }
        return cls;
    }

    public Iterator<String> getUserTypes()
    {
        return userTypes.iterator();
    }
}
