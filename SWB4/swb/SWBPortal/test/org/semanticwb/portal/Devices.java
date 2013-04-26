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
package org.semanticwb.portal;


import java.util.ArrayList;
import org.junit.Test;
import org.semanticwb.model.Device;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.WebSite;
import java.util.Iterator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
/**
 *
 * @author Administrador
 */
public class Devices {
    //static PrintStream out;

    @BeforeClass
    public static void setUpClass() throws Exception {
        String base=SWBUtils.getApplicationPath();
        SWBPlatform.createInstance();
        SWBPlatform.getSemanticMgr().initializeDB();
        SWBPlatform.getSemanticMgr().addBaseOntology(base+"../../../web/WEB-INF/owl/swb.owl");
        SWBPlatform.getSemanticMgr().addBaseOntology(base+"../../../web/WEB-INF/owl/swb_rep.owl");
        SWBPlatform.getSemanticMgr().addBaseOntology(base+"../../../web/WEB-INF/owl/office.owl");
        SWBPlatform.getSemanticMgr().loadBaseVocabulary();
        SWBPlatform.getSemanticMgr().loadDBModels();
        SWBPlatform.getSemanticMgr().rebind();
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
    public void display() {
        StringBuilder ret = new StringBuilder();
        String space="";

        WebSite webSite = SWBContext.getWebSite("x");

        ArrayList x=new ArrayList();
        Iterator<Device> itDevices = webSite.listDevices();
        while (itDevices.hasNext()) {
            x.add(itDevices.next());
        }
        x.trimToSize();
        
        ret.append("<select id=\"wb_device\" name=\"wb_device\" size=\"10\" multiple=\"multiple\">\n");
        //Iterator<Device> itDevices = webSite.listDevices();
        while(!x.isEmpty()) {
            Device device = (Device)x.get(0);
            ret.append("<option value=\""+device.getId()+"\">"+space+device.getTitle()+"</option>\n");
            x.remove(0);
            if(device.listChilds().hasNext()) {
                display2(x, device, ret, space+"&nbsp;&nbsp;&nbsp;");
            }            
        }
        ret.append("</select>\n");
        System.out.println(ret);
    }

    public void display2(ArrayList x, Device node, StringBuilder ret, String space) {
        ArrayList xx=new ArrayList();
        Iterator<Device> itDevices = node.listChilds();
        while (itDevices.hasNext()) {
            xx.add(itDevices.next());
        }
        xx.trimToSize();

        while(!xx.isEmpty()) {
            Device device = (Device)xx.get(0);
            ret.append("<option value=\""+device.getId()+"\">"+space+device.getTitle()+"</option>\n");
            x.remove(device);
            xx.remove(0);
            if(device.listChilds().hasNext()) {
                display2(x, device, ret, space+"&nbsp;&nbsp;&nbsp;");
            }            
        }
    }

}
