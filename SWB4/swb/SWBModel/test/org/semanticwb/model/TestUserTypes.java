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
package org.semanticwb.model;

//~--- non-JDK imports --------------------------------------------------------

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.semanticwb.SWBException;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

/**
 *
 * @author serch
 */
public class TestUserTypes {
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
    public static void tearDownClass() throws Exception {}

    @Before
    public void setUp() {}

    @After
    public void tearDown() {}

    // @Test
    public void FillUsers() {
        UserRepository repository = null;
        String[]       nombres    = {
            "Sergio", "Javier", "Jorge", "Carlos", "Edgar", "Nohemi", "Victor", "Melissa", "Nancy", "Rogelio", "Jose",
            "Aura"
        };
        String[] apellidos = {
            "Martínez", "Solís", "Jimenez", "Ramos", "Estrada", "Vargas", "Lorenzana", "Aduna", "Lopez", "Esperon",
            "Gonzalez", "Castro"
        };
        String nombre    = null;
        String apellido1 = null;
        String apellido2 = null;
        String login     = null;
        String mail      = null;

        repository = SWBContext.getDefaultRepository();

        for (int i = 0; i < 20; i++) {
            nombre    = nombres[(int) Math.floor(Math.random() * 12)];
            apellido1 = apellidos[(int) Math.floor(Math.random() * 12)];
            apellido2 = apellidos[(int) Math.floor(Math.random() * 12)];
            login     = "" + apellido1.substring(0, 1) + nombre + "_" + i;
            mail      = login + "@webbuilder.info";

            User current = repository.createUser();

            current.setActive(true);
            current.setFirstName(nombre);
            current.setLastName(apellido1);
            current.setSecondLastName(apellido2);
            current.setLogin(login);
            current.setEmail(mail);
            current.setPassword(login);
        }
    }

    // @Test
    public void putRoles() {
        Iterator<Role>  itro   = SWBContext.getDefaultRepository().listRoles();
        ArrayList<Role> arliro = new ArrayList<Role>();

        while (itro.hasNext()) {
            arliro.add(itro.next());
        }

        Iterator<User> itus = SWBContext.getDefaultRepository().listUsers();
        int            i    = 0;

        while (itus.hasNext()) {
            User usr = itus.next();

            i++;

            if (i == arliro.size()) {
                i = 0;
            }

            usr.addRole(arliro.get(i));
        }
    }

    // @Test
    public void putGroups() {
        Iterator<UserGroup>  itro   = SWBContext.getDefaultRepository().listUserGroups();
        ArrayList<UserGroup> arliro = new ArrayList<UserGroup>();

        while (itro.hasNext()) {
            arliro.add(itro.next());
        }

        Iterator<User> itus = SWBContext.getDefaultRepository().listUsers();
        int            i    = 0;

        while (itus.hasNext()) {
            User usr = itus.next();

            i++;

            if (i == arliro.size()) {
                i = 0;
            }

            usr.addUserGroup(arliro.get(i));
        }
    }

    // @Test
    public void setValues() {
        UserRepository repository = null;

        repository = SWBContext.getDefaultRepository();
        repository.setUserRepSecurityQuestionList("1:Pasaporte num|2:Licencia num|3:nombre de tu mascota");
    }

    // @Test
//  public void createUserTypes(){
//      UserRepository repository = null;
//      repository = SWBContext.getDefaultRepository();
//      repository.createIntExtendedAttribute("edad");
//      SemanticClass cls = repository.getUserType("estudiante");
//      repository.createStringExtendedAttribute("escuela", "estudiante");
//      repository.createBooleanExtendedAttribute("inscrtito", "estudiante");
//      String [] al = new String[3];
//      al[0] = "primaria:secundaria:preparatoria:universidad:maestria:doctorado";
//      al[1] = "es|primaria:secundaria:preparatoria:universidad:maestria:doctorado";
//      al[2] = "en|elementary:13-15grade:high:university:degree:doctordegre";
//      repository.createListExtendedAttribute("grado", "estudiante", al);
//      cls = repository.getUserType("empleado");
//      repository.createStringExtendedAttribute("empresa", "empleado");
//      repository.createDateTimeExtendedAttribute("fingreso","empleado");
//
//  }
    // @Test
    public void setUserTypes() throws SWBException {
        String[]       grados     = {
            "primaria", "secundaria", "preparatoria", "universidad", "maestria", "doctorado"
        };
        String[]       escuela    = {
            "Tec", "UNAM", "IPN", "UVM", "UNITEC", "Valle"
        };
        UserRepository repository = null;

        repository = SWBContext.getDefaultRepository();

        Iterator<String>  usetypes = repository.getUserTypes();
        ArrayList<String> arliro   = new ArrayList<String>();

        while (usetypes.hasNext()) {
            arliro.add(usetypes.next());
        }

        Iterator<User> itus = SWBContext.getDefaultRepository().listUsers();
        int            i    = -1;

        while (itus.hasNext()) {
            User usr = itus.next();

            usr.setExtendedAttribute("edad", new Integer(((int) Math.floor(Math.random() * 30)) + 20));
            i++;

            if (i == arliro.size()) {
                i = -1;
            }

            if (i >= 0) {
                usr.addUserType(arliro.get(i));
            }
        }

        itus = SWBContext.getDefaultRepository().listUsers();

        while (itus.hasNext()) {
            User usr = itus.next();

            if (usr.hasUserType("empleado")) {
                usr.setExtendedAttribute("empresa", "Infotec");
                usr.setExtendedAttribute("fingreso", new Date());
            }

            if (usr.hasUserType("estudiante")) {
                usr.setExtendedAttribute("grado", grados[(int) Math.floor(Math.random() * 5)]);
                usr.setExtendedAttribute("escuela", escuela[(int) Math.floor(Math.random() * 5)]);
                usr.setExtendedAttribute("inscrtito", Boolean.valueOf(((int) Math.floor(Math.random() * 5) == 0)? false: true));
            }
        }
    }

    @Test
    public void userAtts() throws SWBException {
        System.out.println("****************************************************");

        Iterator<User> itus = SWBContext.getUserRepository("nuevo_usr").listUsers();
        User           usr  = null;

//      while (itus.hasNext()){
//          usr = itus.next();
//          System.out.println("usuario:"+usr.getLogin());
//          usr.addUserType("Empleado");
//          usr.setExtendedAttribute("age", new Integer(((int)Math.floor(Math.random()*30))+20));
//          usr.setExtendedAttribute("sex", 1);
//          usr.setExtendedAttribute("nombreEmpresa", "INFOTEC");
//      }
//      itus = SWBContext.getUserRepository("nuevo_usr").listUsers();
        while (itus.hasNext()) {
            usr = itus.next();
            System.out.println("Log:" + usr.getLogin());
            System.out.println("age:" + usr.getExtendedAttribute("age"));
            System.out.println("sex:" + usr.getExtendedAttribute("sex"));
            System.out.println("empresa:" + usr.getExtendedAttribute("nombreEmpresa"));
        }
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
