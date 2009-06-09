/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.StringTokenizer;
import org.junit.*;
import org.semanticwb.SWBException;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBContext;
import org.semanticwb.platform.SemanticModel;
import org.semanticwb.model.catalogs.*;
import org.semanticwb.model.SWBModel;
import org.semanticwb.model.User;
import org.semanticwb.model.UserRepository;
import org.semanticwb.model.WebSite;

/**
 *
 * @author serch
 */
public class TestDemoEMex {

        @BeforeClass
    public static void setUpClass() throws Exception
    {
        //SWBPlatform.setUseDB(false);
        SWBPlatform.createInstance(null);
    //SWBPlatform.
    }

    @AfterClass
    public static void tearDownClass() throws Exception
    {
    }

    @Before
    public void setUp()
    {
    }

    @After
    public void tearDown()
    {
    }

    //@Test
    public void configUserRec() throws SQLException
    {

        UserRepository repository = null;
        repository = SWBContext.getUserRepository("EMexico_usr");
      //  repository.setUserRepSecurityQuestionList("1:Pasaporte num|2:Licencia num|3:nombre de tu mascota","es");
      //  repository.setUserRepSecurityQuestionList("1:Passport number|2:Drivers number|3:petsname","en");
System.out.println("inicio...");
        repository.createDateTimeExtendedAttribute("fechaNac");
System.out.println("creado fecha nac");
        String [] sex = new String[3];
        sex[0] = "hombre:mujer";
        sex[1] = "es|hombre:mujer";
        sex[2] = "en|man:woman";

        repository.createListExtendedAttribute("sexo", sex);
System.out.println("creado sexo");
      String [] al = new String[3];
        al[0] = "primaria:secundaria:preparatoria:universidad:maestria:doctorado";
        al[1] = "es|primaria:secundaria:preparatoria:universidad:maestria:doctorado";
        al[2] = "en|elementary:13-15grade:high:university:degree:doctordegre";
        repository.createListExtendedAttribute("grado", al);
System.out.println("Creado grado");

        repository.createStringExtendedAttribute("ciudad");
System.out.println("Creado ciudad");
        al = new String[3];
        al[0] = "empleado:estudiante:hogar:otro:por mi cuenta";
        al[1] = "es|empleado:estudiante:hogar:otro:por mi cuenta";
        al[2] = "en|employed:student:home:other:at my own";
        repository.createListExtendedAttribute("ocupacion", al);
System.out.println("Creado ocupacion");
        Connection con = SWBUtils.DB.getConnection("catalogs", "Conexion Carga CP's");
        PreparedStatement ps = con.prepareStatement("select entidad_federativa from entidad_relacion order by id_entidad_federativa2");
        ResultSet rs = ps.executeQuery();
        String cadena="";
        while (rs.next()){
            cadena = cadena + rs.getString(1) +":";
        }
        rs.close();
        ps.close();
        con.close();
        cadena = cadena.substring(1, cadena.length()-1);
System.out.println("cadena: "+cadena);
        al[0]=cadena;
        al[1]="es|"+cadena;
        al[2]="en|"+cadena;
        repository.createListExtendedAttribute("estado", al);
System.out.println("Creado estado");
    }

@Test
public void userdemo() throws SWBException{
    UserRepository repository = null;
        repository = SWBContext.getUserRepository("EMexico_usr");
        User user = repository.getUserByLogin("serch");
        System.out.println("Login: "+user.getLogin());
        user.setExtendedAttribute("estado", "Distrito Federal");
        user.setExtendedAttribute("sexo", "hombre");
        user.setExtendedAttribute("grado", "universidad");
        user.setExtendedAttribute("fechaNac", "08/05/1974");
        user.setExtendedAttribute("ciudad", "México");
}
}
