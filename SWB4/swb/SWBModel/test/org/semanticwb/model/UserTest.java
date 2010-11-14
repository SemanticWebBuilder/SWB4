/**  
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
 **/
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.model;

import org.semanticwb.SWBException;
import org.semanticwb.SWBPlatform;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticProperty;

import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFNode;
import java.util.ArrayList;
import java.util.Iterator;
import org.junit.*;
import org.semanticwb.SWBUtils;
import org.semanticwb.platform.SemanticVocabulary;

/**
 *
 * @author serch
 */
public class UserTest {

    static public final String NL = System.getProperty("line.separator");

    public UserTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        String base = SWBUtils.getApplicationPath();
        SWBPlatform.createInstance();
        SWBPlatform.getSemanticMgr().initializeDB();
        SWBPlatform.getSemanticMgr().addBaseOntology(base + "../../../web/WEB-INF/owl/swb.owl");
        SWBPlatform.getSemanticMgr().addBaseOntology(base + "../../../web/WEB-INF/owl/swb_rep.owl");
        SWBPlatform.getSemanticMgr().addBaseOntology(base + "../../../web/WEB-INF/owl/office.owl");
        SWBPlatform.getSemanticMgr().loadBaseVocabulary();
        SWBPlatform.getSemanticMgr().loadDBModels();
        SWBPlatform.getSemanticMgr().getOntology().rebind();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getName method, of class User.
     */
    //@Test
    public void testGetName() {
        UserRepository repository = null;
        repository = SWBContext.getUserRepository("urswb");
        System.out.println("Repository:" + repository);
        System.out.println("Model:" + repository.getSemanticObject().getModel().getRDFModel());
        SemanticClass cls = repository.getUserType("estudiante");
//        repository.createStringExtendedAttribute("escuela", "estudiante");
//        repository.createIntExtendedAttribute("edad");
        User instance = repository.getUserByLogin("admin");
        instance.addUserType("estudiante");
        //instance.getSemanticObject().addSemanticClass(cls);
        try {

            instance.setUserTypeAttribute("estudiante", "escuela", "ESIME-CULHUACAN");
        } catch (SWBException ex) {
            ex.printStackTrace();
        }

        Iterator<SemanticProperty> it = repository.listAttributes();
        while (it.hasNext()) {
            SemanticProperty sp = it.next();

            System.out.println("getName: " + sp.getName() + " getRange: " + sp.getRange());
        }
        System.out.println("Extended");
        it = repository.listExtendedAttributes();
        while (it.hasNext()) {
            SemanticProperty sp = it.next();

            System.out.println("getName: " + sp.getName() + " getRange: " + sp.getRange());
        }
        System.out.println("UserType estudiante");
        it = repository.listAttributesofUserType("estudiante");
        while (it.hasNext()) {
            SemanticProperty sp = it.next();

            System.out.println("getName: " + sp.getName() + " getRange: " + sp.getRange());
        }
        System.out.println("Básicos");
        it = repository.listBasicAttributes();
        while (it.hasNext()) {
            SemanticProperty sp = it.next();

            System.out.println("getName: " + sp.getName() + " getRange: " + sp.getRange());
        }
        // repository.createStringExtendedAttribute("escuela", "estudiante");

        //repository.createUser();
            /*
        SemanticClass cls=SWBContext.getVocabulary().swb_Dns;
        Dns dns=(Dns)SWBPlatform.getSemanticMgr().getOntology().getGenericObject("localhost", cls);
        System.out.println("dns:"+dns);
        System.out.println("dns_model:"+dns.getSemanticObject().getModel().getRDFModel());
         */
//        if (null==repository)
//        {
//            repository = SWBContext.createUserRepository("swb_users", "http://www.infotec.com.mx");
//        }
//        System.out.println("Repository2;"+repository);
//        System.out.println("Rep_SemObj:"+repository.getSemanticObject());
//        System.out.println("Rep_SemObj_Mod:"+repository.getSemanticObject().getModel());
//        System.out.println("Rep_SemObj_Mod_NS:"+repository.getSemanticObject().getModel().getNameSpace());
//
//
//        User instance = repository.getUser("serch");
//        //User instance = repository.createUser("serch");
//        System.out.println("User;"+instance+" "+instance.getSemanticObject());
//
//        instance.setCreated(new Date());
//        instance.setStatus(1);
//        instance.setUsrEmail("serch@infotec.com.mx");
//        instance.setUsrFirstName("Sergio");
//        instance.setUsrLastName("Martínez");
//        String expResult = "serch";
//        String result = instance.getName();
//        instance.setUsrPassword("serch08");
//
//        instance = repository.getUser("serch");
//        System.out.println("UsrEmail:"+instance.getUsrEmail());
//        System.out.println("UsrLastName:"+instance.getUsrLastName());
//        System.out.println("Language:"+instance.getLanguage());
//
//        // TODO review the generated test code and remove the default call to fail.
//        //fail("The test case is a prototype.");




        // repository.createStringExtendedAttribute("escuela", "estudiante");


        //repository.createUser();
        /*
        SemanticClass cls=SWBContext.getVocabulary().swb_Dns;
        Dns dns=(Dns)SWBPlatform.getSemanticMgr().getOntology().getGenericObject("localhost", cls);
        System.out.println("dns:"+dns);
        System.out.println("dns_model:"+dns.getSemanticObject().getModel().getRDFModel());
         */
//        if (null==repository) 
//        {
//            repository = SWBContext.createUserRepository("swb_users", "http://www.infotec.com.mx");
//        }
//        System.out.println("Repository2;"+repository);
//        System.out.println("Rep_SemObj:"+repository.getSemanticObject());
//        System.out.println("Rep_SemObj_Mod:"+repository.getSemanticObject().getModel());
//        System.out.println("Rep_SemObj_Mod_NS:"+repository.getSemanticObject().getModel().getNameSpace());
//        
//        
//        User instance = repository.getUser("serch");
//        //User instance = repository.createUser("serch");
//        System.out.println("User;"+instance+" "+instance.getSemanticObject());
//        
//        instance.setCreated(new Date());
//        instance.setStatus(1);
//        instance.setUsrEmail("serch@infotec.com.mx");
//        instance.setUsrFirstName("Sergio");
//        instance.setUsrLastName("Martínez");
//        String expResult = "serch";
//        String result = instance.getName();
//        instance.setUsrPassword("serch08");
//        
//        instance = repository.getUser("serch");
//        System.out.println("UsrEmail:"+instance.getUsrEmail());
//        System.out.println("UsrLastName:"+instance.getUsrLastName());
//        System.out.println("Language:"+instance.getLanguage());
//        
//        // TODO review the generated test code and remove the default call to fail.
//        //fail("The test case is a prototype.");
    }

    /**
     * Test of setUsrPassword method, of class User.
     */
    /*  @Test
    public void testSetUsrPassword() {
    System.out.println("setUsrPassword");
    String password = "";
    User instance = null;
    instance.setUsrPassword(password);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
    }*/
    // @Test
    public void listUserAttr() {
        UserRepository repository = null;
        repository = SWBContext.getUserRepository("urswb");
        Iterator<SemanticProperty> itsp = repository.listBasicAttributes();
        while (itsp.hasNext()) {
            System.out.println(itsp.next().getName());
        }
    }

//    //@Test
//    public void addRyG(){
//        UserRepository rep = SWBContext.createUserRepository("externalUsers", "ussrepext");
//        rep.setTitle("Usuarios Externos");
//        Role role = rep.createRole();
//        role.setTitle("Director");
//        role = rep.createRole();
//        role.setTitle("Gerente");
//        role = rep.createRole();
//        role.setTitle("Analista");
//        role = rep.createRole();
//        role.setTitle("Operador");
//        UserGroup group = rep.createUserGroup("OPER");
//        group = rep.createUserGroup("CAT");
//        group = rep.createUserGroup("SALES");
//        group = rep.createUserGroup("INVEST");
//    }
    //@Test
    public void FillUsers() {
        UserRepository repository = null;
        String[] nombres = {"Sergio", "Javier", "Jorge", "Carlos", "Edgar", "Nohemi", "Victor", "Melissa", "Nancy", "Rogelio", "Jose", "Aura"};
        String[] apellidos = {"Martínez", "Solís", "Jimenez", "Ramos", "Estrada", "Vargas", "Lorenzana", "Aduna", "Lopez", "Esperon", "Gonzalez", "Castro"};

        String nombre = null;
        String apellido1 = null;
        String apellido2 = null;
        String login = null;
        String mail = null;
        repository = SWBContext.getUserRepository("externalUsers");
        for (int i = 0; i < 100; i++) {
            nombre = nombres[(int) Math.floor(Math.random() * 12)];
            apellido1 = apellidos[(int) Math.floor(Math.random() * 12)];
            apellido2 = apellidos[(int) Math.floor(Math.random() * 12)];
            login = "" + apellido1.substring(0, 1) + nombre + "_" + i;
            mail = login + "@webbuilder.info";
            User current = repository.createUser();
            current.setActive(true);
            current.setFirstName(nombre);
            current.setLastName(apellido1);
            current.setSecondLastName(apellido2);
            current.setLogin(login);
            current.setEmail(mail);
            current.setPassword(apellido2 + "." + apellido1);
        }
    }

    //@Test
    public void putRoles() {
        Iterator<Role> itro = SWBContext.getDefaultRepository().listRoles();
        ArrayList<Role> arliro = new ArrayList<Role>();
        while (itro.hasNext()) {
            arliro.add(itro.next());
        }
        Iterator<User> itus = SWBContext.getDefaultRepository().listUsers();
        int i = 0;
        while (itus.hasNext()) {
            User usr = itus.next();
            i++;
            if (i == arliro.size()) {
                i = 0;
            }
            usr.addRole(arliro.get(i));
        }
    }

    @Test
    public void setAlist() {
        String[] al = new String[3];
        al[0] = "primaria:secundaria:preparatoria:universidad:maestria:doctorado";
        al[1] = "es|primaria:secundaria:preparatoria:universidad:maestria:doctorado";
        al[2] = "en|elementary:13-15grade:high:university:degree:doctordegre";
//        SWBContext.getDefaultRepository().createListExtendedAttribute("escolaridad", al);
        try {
            SWBContext.getDefaultRepository().getUserByLogin("admin").setExtendedAttribute("escolaridad", "universidad");
            //setProperty("escolaridad", "universidad");
        } catch (SWBException ex) {
            ex.printStackTrace();
        }
        //setProperty("escolaridad", "universidad");
    }

    //@Test
    public void getAlist() {
        User usr = SWBContext.getDefaultRepository().getUserByLogin("admin");
        //System.out.println(usr.getProperty("escolaridad"));
        Object obj = usr.getExtendedAttribute("escolaridads");
        obj = usr.getExtendedAttribute("escolaridad");
        System.out.println(obj);
        System.out.println(obj.getClass().getName());
        Iterator<SemanticProperty> itsp = SWBContext.getDefaultRepository().listAttributes();
        while (itsp.hasNext()) {
            SemanticProperty sp = itsp.next();
            if (sp.getDisplayProperty() != null) {
                System.out.println(sp + "" + sp.getDisplayProperty().getRDFName());
            }
        }
    }

    @Test
    public void fixPasswords() {
        Iterator<UserRepository> iur = SWBContext.listUserRepositories();
        while (iur.hasNext()) {
            UserRepository ur = iur.next();
            Iterator<User> itus = ur.listUsers();
            while (itus.hasNext()) {
                User user = itus.next();
                if (null == user.getLogin()) {
                    user.remove();
                } else {
                    user.setPassword(user.getLogin());
                }
            }
        }
    }

    //@Test
    public void encode() throws Exception {
        System.out.println(SWBUtils.CryptoWrapper.comparablePassword("TOOMANYSECRETS", "MD5"));
    }

    //@Test
    public void putGroups() {
        Iterator<UserGroup> itro = SWBContext.getDefaultRepository().listUserGroups();
        ArrayList<UserGroup> arliro = new ArrayList<UserGroup>();
        while (itro.hasNext()) {
            arliro.add(itro.next());
        }
        Iterator<User> itus = SWBContext.getDefaultRepository().listUsers();
        int i = 0;
        while (itus.hasNext()) {
            User usr = itus.next();
            i++;
            if (i == arliro.size()) {
                i = 0;
            }
            usr.addUserGroup(arliro.get(i));
        }
    }

    //@Test
    public void test() {
        long time = System.currentTimeMillis();

        Model model = SWBPlatform.getSemanticMgr().getOntology().getRDFOntModel();

        // First part or the query string
        String prolog = "PREFIX swb: <" + SemanticVocabulary.URI + ">";
        prolog += "PREFIX rdf: <" + SemanticVocabulary.RDF_URI + ">";
        prolog += "PREFIX rdfs: <" + SemanticVocabulary.RDFS_URI + ">";

        // Query string.
        //"SELECT ?title ?class WHERE {?x swb:title ?title. ?x rdf:type swb:WebPage}"

        String queryString = prolog + NL
                + "SELECT ?x WHERE {?x rdf:type swb:User}";

        Query query = QueryFactory.create(queryString);
        // Print with line numbers
        //query.serialize(new IndentedWriter(System.out, true));
        query.serialize(System.out);
        System.out.println();

        // Create a single execution of this query, apply to a model
        // which is wrapped up as a Dataset

        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        // Or QueryExecutionFactory.create(queryString, model) ;

        try {
            // Assumption: it's a SELECT query.
            ResultSet rs = qexec.execSelect();

            Iterator<String> it = rs.getResultVars().iterator();
            while (it.hasNext()) {
                System.out.print(it.next() + "\t");
            }
            System.out.println();

            // The order of results is undefined.
            for (; rs.hasNext();) {
                QuerySolution rb = rs.nextSolution();


                it = rs.getResultVars().iterator();
                while (it.hasNext()) {
                    String name = it.next();
                    RDFNode x = rb.get(name);
                    System.out.print(x + "\t");
                }
                System.out.println();

                // Get title - variable names do not include the '?' (or '$')
//                RDFNode x = rb.get("x") ;
//                RDFNode title = rb.get("title") ;
//
//                System.out.println("x:"+x+" title:"+title) ;
//                // Check the type of the result value
//                if ( x.isLiteral() )
//                {
//                    Literal titleStr = (Literal)x  ;
//                    System.out.println("    "+titleStr) ;
//                }
//                else
//                    System.out.println("Strange - not a literal: "+x) ;

            }
        } finally {
            // QueryExecution objects should be closed to free any system resources
            qexec.close();
        }


        System.out.println("Time:" + (System.currentTimeMillis() - time));
    }
}
