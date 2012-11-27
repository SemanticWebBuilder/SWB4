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

import org.semanticwb.Logger;
import org.semanticwb.SWBException;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticProperty;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 *
 * @author serch
 */
public class TestListAtt {
    private static Logger log = SWBUtils.getLogger(TestListAtt.class);

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
    public void getListBasic() {
        UserRepository             usrRepo = SWBContext.getDefaultRepository();
        Iterator<SemanticProperty> iteratt = usrRepo.listBasicAttributes();
    }

    // @Test
    public void getListExt() {
        UserRepository             usrRepo = SWBContext.getDefaultRepository();
        Iterator<SemanticProperty> iteratt = usrRepo.listExtendedAttributes();
    }

    @Test
    public void getListUsrExt() {
        UserRepository   usrRepo = SWBContext.getDefaultRepository();
        Iterator<String> iteSt   = usrRepo.getUserTypes();

        while (iteSt.hasNext()) {
            String curr = iteSt.next();

            System.out.println("Revisando ... " + curr);

            Iterator<SemanticProperty> iteratt = usrRepo.listAttributesofUserType(curr);
        }
    }

    // @Test
    public void getList() {
        UserRepository             usrRepo = SWBContext.getDefaultRepository();
        Iterator<SemanticProperty> iteratt = usrRepo.listAttributes();
    }

    // @Test
    public void doList() {
        UserRepository             usrRepo     = SWBContext.getDefaultRepository();
        Iterator<SemanticProperty> iteratt     = usrRepo.listAttributes();
        HashMap                    hmAttr      = new HashMap();
        HashMap                    hmOper      = new HashMap();
        HashMap                    hmValues    = new HashMap();
        HashMap                    comboAtt    = new HashMap();
        Vector                     vecOrderAtt = new Vector(1, 1);
        int                        numero      = 0;
        int                        attnum      = 0;

        while (iteratt.hasNext()) {
            String           tipoControl = "TEXT";
            SemanticProperty usrAtt      = iteratt.next();

            attnum++;
            log.debug("ListAttributes:" + usrAtt.getName() + ", " + attnum + ", objProp: " + usrAtt.isObjectProperty());
            hmAttr   = new HashMap();
            hmOper   = new HashMap();
            hmValues = new HashMap();
            hmAttr.put("Etiqueta", "EX: " + usrAtt.getDisplayName("en"));

            if (usrAtt.getDisplayProperty() != null) {
                log.debug("DisplayProperty");

                DisplayProperty dobj = new DisplayProperty(usrAtt.getDisplayProperty());

                if (!dobj.isHidden()) {
                    String selectValues = dobj.getSelectValues("en");

                    // /////////////////////////
                    if (selectValues != null) {
                        tipoControl = "select";
                        hmAttr.put("Tipo", tipoControl);
                        hmOper.put("=", "msgSameAs");
                        hmOper.put("!=", "msgNotEqual");
                        hmAttr.put("Operador", hmOper);

                        StringTokenizer st = new StringTokenizer(selectValues, "|");

                        while (st.hasMoreTokens()) {
                            String tok = st.nextToken();
                            int    ind = tok.indexOf(':');
                            String idt = tok;
                            String val = tok;

                            if (ind > 0) {
                                idt = tok.substring(0, ind);
                                val = tok.substring(ind + 1);
                            }

                            hmValues.put(idt, val);
                        }

                        hmAttr.put("Valor", hmValues);
                        comboAtt.put(usrAtt.getName(), hmAttr);
                        vecOrderAtt.add(numero++, usrAtt.getName());
                    } else {
                        if (usrAtt.isDataTypeProperty()) {
                            log.debug("DP: DataTypeProperty");

                            if (usrAtt.isInt() || usrAtt.isFloat() || usrAtt.isLong()) {
                                tipoControl = "TEXT";
                                hmAttr.put("Tipo", tipoControl);
                                hmOper.put("&gt;", "msgGreaterThan");
                                hmOper.put("&lt;", "msgLessThan");
                                hmOper.put("=", "msgIs");
                                hmOper.put("!=", "msgNotIs");
                                hmAttr.put("Operador", hmOper);
                                comboAtt.put(usrAtt.getName(), hmAttr);
                                vecOrderAtt.add(numero++, usrAtt.getName());
                            } else if (usrAtt.isBoolean()) {
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
                            } else if (usrAtt.isString()) {
                                tipoControl = "TEXT";
                                hmAttr.put("Tipo", tipoControl);
                                hmOper.put("=", "msgIs");
                                hmOper.put("!=", "msgNotIs");
                                hmAttr.put("Operador", hmOper);
                                comboAtt.put(usrAtt.getName(), hmAttr);
                                vecOrderAtt.add(numero++, usrAtt.getName());
                            }

                            // PARA ObjectType
                            // else {
                            // tipoControl = "TEXT";
                            // hmAttr.put("Tipo", tipoControl);
                            // hmOper.put("=", paramRequest.getLocaleString("msgIs"));
                            // hmOper.put("!=", paramRequest.getLocaleString("msgNotIs"));
                            // hmAttr.put("Operador", hmOper);
                            // comboAtt.put(usrAtt.getName(), hmAttr);
                            // vecOrderAtt.add(numero++, usrAtt.getName());
                            // }
                        }

                        // else if (usrAtt.isObjectProperty()) {
                        // log.debug("DP: ObjectProperty");
                        // tipoControl = "select";
                        // if (usrAtt == User.swb_hasRole) {
                        // Iterator<Role> itRol = usrRepo.listRoles();
                        // while (itRol.hasNext()) {
                        // Role rol = itRol.next();
                        // hmValues.put(rol.getId(), rol.getDisplayTitle(user.getLanguage()));
                        // }
                        // hmAttr.put("Valor", hmValues);
                        // }
                        // }
                    }

                    log.debug("DP:Tipo --- " + tipoControl);
                }
            }
        }
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
