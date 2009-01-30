/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.semanticwb.SWBException;
import org.semanticwb.SWBPlatform;
import org.semanticwb.platform.SemanticClass;

/**
 *
 * @author serch
 */
public class TestUserTypes {

    @BeforeClass
    public static void setUpClass() throws Exception {
        SWBPlatform.createInstance(null);
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


    @Test
    public void FillUsers(){
      UserRepository repository = null;
      String[] nombres = {"Sergio", "Javier", "Jorge", "Carlos", "Edgar", "Nohemi", "Victor", "Melissa", "Nancy", "Rogelio", "Jose", "Aura"};
      String[] apellidos = {"Martínez", "Solís", "Jimenez", "Ramos", "Estrada", "Vargas", "Lorenzana", "Aduna", "Lopez", "Esperon", "Gonzalez", "Castro"};

      String nombre = null;
      String apellido1 = null;
      String apellido2 = null;
      String login = null;
      String mail = null;
      repository = SWBContext.getDefaultRepository();
      for (int i=0; i<20; i++){
          nombre = nombres[(int)Math.floor(Math.random()*12)];
          apellido1 = apellidos[(int)Math.floor(Math.random()*12)];
          apellido2 = apellidos[(int)Math.floor(Math.random()*12)];
          login = ""+apellido1.substring(0, 1)+nombre+"_"+i;
          mail = login+"@webbuilder.info";
          User current = repository.createUser();
          current.setActive(true);
          current.setUsrFirstName(nombre);
          current.setUsrLastName(apellido1);
          current.setUsrSecondLastName(apellido2);
          current.setUsrLogin(login);
          current.setUsrEmail(mail);
          current.setUsrPassword(login);
      }
    }

    @Test
    public void putRoles(){
        Iterator<Role> itro = SWBContext.getDefaultRepository().listRoles();
        ArrayList<Role> arliro = new ArrayList<Role>();
        while (itro.hasNext()){
            arliro.add(itro.next());
        }
        Iterator<User>itus = SWBContext.getDefaultRepository().listUsers();
        int i = 0;
        while (itus.hasNext()){
            User usr = itus.next();
            i++;
            if (i == arliro.size()) i = 0;
            usr.addRole(arliro.get(i));
        }
    }

    @Test
    public void putGroups(){
        Iterator<UserGroup> itro = SWBContext.getDefaultRepository().listUserGroups();
        ArrayList<UserGroup> arliro = new ArrayList<UserGroup>();
        while (itro.hasNext()){
            arliro.add(itro.next());
        }
        Iterator<User>itus = SWBContext.getDefaultRepository().listUsers();
        int i = 0;
        while (itus.hasNext()){
            User usr = itus.next();
            i++;
            if (i == arliro.size()) i = 0;
            usr.setGroup(arliro.get(i));
        }
    }

    @Test
    public void createUserTypes(){
        UserRepository repository = null;
        repository = SWBContext.getDefaultRepository();
        repository.createIntExtendedAttribute("edad");
        SemanticClass cls = repository.getUserType("estudiante");
        repository.createStringExtendedAttribute("escuela", "estudiante");
        repository.createBooleanExtendedAttribute("inscrtito", "estudiante");
        String [] al = new String[3];
        al[0] = "primaria:secundaria:preparatoria:universidad:maestria:doctorado";
        al[1] = "es|primaria:secundaria:preparatoria:universidad:maestria:doctorado";
        al[2] = "en|elementary:13-15grade:high:university:degree:doctordegre";
        repository.createListExtendedAttribute("grado", "estudiante", al);
        cls = repository.getUserType("empleado");
        repository.createStringExtendedAttribute("empresa", "empleado");
        repository.createDateTimeExtendedAttribute("fingreso","empleado");

    }

    @Test
    public void setUserTypes() throws SWBException{
        String[] grados = {"primaria","secundaria","preparatoria","universidad","maestria","doctorado"};
        String[] escuela = {"Tec","UNAM","IPN","UVM","UNITEC","Valle"};
        UserRepository repository = null;
        repository = SWBContext.getDefaultRepository();
        Iterator<String>usetypes=repository.getUserTypes();

        ArrayList<String> arliro = new ArrayList<String>();
        while (usetypes.hasNext()){
            arliro.add(usetypes.next());
        }
        Iterator<User>itus = SWBContext.getDefaultRepository().listUsers();
        int i = -1;
        while (itus.hasNext()){
            User usr = itus.next();
            usr.setExtendedAttribute("edad", new Integer(((int)Math.floor(Math.random()*30))+20));
            i++;
            if (i == arliro.size()) i = -1;
            if (i >=0) usr.addUserType(arliro.get(i));
        }
        itus = SWBContext.getDefaultRepository().listUsers();
        while (itus.hasNext()){
            User usr = itus.next();
            if (usr.hasUserType("empleado")) {
                usr.setExtendedAttribute("empresa", "Infotec");
                usr.setExtendedAttribute("fingreso", new Date());
            }
            if (usr.hasUserType("estudiante")) {
                usr.setExtendedAttribute("grado", grados[(int)Math.floor(Math.random()*5)]);
                usr.setExtendedAttribute("escuela", escuela[(int)Math.floor(Math.random()*5)]);
                usr.setExtendedAttribute("inscrtito", new Boolean(((int)Math.floor(Math.random()*5)==0)?false:true));
            }
        }
    }

}
