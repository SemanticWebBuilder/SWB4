/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.semanticwb.Logger;
import org.semanticwb.SWBException;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticProperty;

/**
 *
 * @author serch
 */
public class TestListAtt
{
    private static Logger log = SWBUtils.getLogger(TestListAtt.class);


    @BeforeClass
    public static void setUpClass() throws Exception
    {
        SWBPlatform.createInstance(null);
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
    public void getListBasic(){
        UserRepository usrRepo = SWBContext.getDefaultRepository();
        Iterator<SemanticProperty> iteratt = usrRepo.listBasicAttributes();
    }

    //@Test
    public void getListExt(){
        UserRepository usrRepo = SWBContext.getDefaultRepository();
        Iterator<SemanticProperty> iteratt = usrRepo.listExtendedAttributes();
    }

    @Test
    public void getListUsrExt(){
        UserRepository usrRepo = SWBContext.getDefaultRepository();
        Iterator<String> iteSt = usrRepo.getUserTypes();
        while (iteSt.hasNext()){
            String curr = iteSt.next();
            System.out.println("Revisando ... "+ curr);
            Iterator<SemanticProperty> iteratt = usrRepo.listAttributesofUserType(curr);
        }
    }


    //@Test
    public void getList(){
        UserRepository usrRepo = SWBContext.getDefaultRepository();
        Iterator<SemanticProperty> iteratt = usrRepo.listAttributes();
    }

    //@Test
    public void doList()
    {
        UserRepository usrRepo = SWBContext.getDefaultRepository();
        Iterator<SemanticProperty> iteratt = usrRepo.listAttributes();
        HashMap hmAttr = new HashMap();
        HashMap hmOper = new HashMap();
        HashMap hmValues = new HashMap();
        HashMap comboAtt = new HashMap();
        Vector vecOrderAtt = new Vector(1, 1);
        int numero = 0;
        int attnum = 0;
        while (iteratt.hasNext())
        {
            String tipoControl = "TEXT";
            SemanticProperty usrAtt = iteratt.next();
            attnum++;
            log.debug("ListAttributes:" + usrAtt.getName() + ", " + attnum + ", objProp: " + usrAtt.isObjectProperty());
            hmAttr = new HashMap();
            hmOper = new HashMap();
            hmValues = new HashMap();
            hmAttr.put("Etiqueta", "EX: " + usrAtt.getDisplayName("en"));
            if (usrAtt.getDisplayProperty() != null)
            {
                log.debug("DisplayProperty");
                DisplayProperty dobj = new DisplayProperty(usrAtt.getDisplayProperty());
                if (!dobj.isHidden())
                {
                    String selectValues = dobj.getSelectValues("en");
                    ///////////////////////////
                    if (selectValues != null)
                    {
                        tipoControl = "select";
                        hmAttr.put("Tipo", tipoControl);
                        hmOper.put("=", "msgSameAs");
                        hmOper.put("!=", "msgNotEqual");
                        hmAttr.put("Operador", hmOper);
                        StringTokenizer st = new StringTokenizer(selectValues, "|");
                        while (st.hasMoreTokens())
                        {
                            String tok = st.nextToken();
                            int ind = tok.indexOf(':');
                            String idt = tok;
                            String val = tok;
                            if (ind > 0)
                            {
                                idt = tok.substring(0, ind);
                                val = tok.substring(ind + 1);
                            }
                            hmValues.put(idt, val);
                        }
                        hmAttr.put("Valor", hmValues);
                        comboAtt.put(usrAtt.getName(), hmAttr);
                        vecOrderAtt.add(numero++, usrAtt.getName());
                    } else
                    {
                        if (usrAtt.isDataTypeProperty())
                        {
                            log.debug("DP: DataTypeProperty");
                            if (usrAtt.isInt() || usrAtt.isFloat() || usrAtt.isLong())
                            {
                                tipoControl = "TEXT";
                                hmAttr.put("Tipo", tipoControl);
                                hmOper.put("&gt;", "msgGreaterThan");
                                hmOper.put("&lt;", "msgLessThan");
                                hmOper.put("=", "msgIs");
                                hmOper.put("!=", "msgNotIs");
                                hmAttr.put("Operador", hmOper);
                                comboAtt.put(usrAtt.getName(), hmAttr);
                                vecOrderAtt.add(numero++, usrAtt.getName());
                            } else if (usrAtt.isBoolean())
                            {
                                tipoControl = "select";
                                hmAttr.put("Tipo", tipoControl);
                                hmOper.put("=", "msgIs");
                                hmOper.put("!=", "msgNotIs");
                                hmAttr.put("Operador", hmOper);
                                hmValues.put("true", "msgTrue");
                                hmValues.put("false", "msgFalse");
                                hmAttr.put("Valor", hmValues);
                                comboAtt.put(usrAtt.getName(), hmAttr);
                                vecOrderAtt.add(numero++, usrAtt.getName());
                            } else if (usrAtt.isString())
                            {
                                tipoControl = "TEXT";
                                hmAttr.put("Tipo", tipoControl);
                                hmOper.put("=", "msgIs");
                                hmOper.put("!=", "msgNotIs");
                                hmAttr.put("Operador", hmOper);
                                comboAtt.put(usrAtt.getName(), hmAttr);
                                vecOrderAtt.add(numero++, usrAtt.getName());
                            }

                        // PARA ObjectType

                        //                            else {
                        //                                tipoControl = "TEXT";
                        //                                hmAttr.put("Tipo", tipoControl);
                        //                                hmOper.put("=", paramRequest.getLocaleString("msgIs"));
                        //                                hmOper.put("!=", paramRequest.getLocaleString("msgNotIs"));
                        //                                hmAttr.put("Operador", hmOper);
                        //                                comboAtt.put(usrAtt.getName(), hmAttr);
                        //                                vecOrderAtt.add(numero++, usrAtt.getName());
                        //                            }
                        }
                    //                        else if (usrAtt.isObjectProperty()) {
                    //                            log.debug("DP: ObjectProperty");
                    //                            tipoControl = "select";
                    //                            if (usrAtt == User.swb_hasRole) {
                    //                                Iterator<Role> itRol = usrRepo.listRoles();
                    //                                while (itRol.hasNext()) {
                    //                                    Role rol = itRol.next();
                    //                                    hmValues.put(rol.getId(), rol.getDisplayTitle(user.getLanguage()));
                    //                                }
                    //                                hmAttr.put("Valor", hmValues);
                    //                            }
                    //                        }
                    }
                    log.debug("DP:Tipo --- " + tipoControl);
                }
            }
        }

    }
}
