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
package org.semanticwb.portal.services;

import org.junit.*;
import org.semanticwb.SWBUtils;


/**
 *
 * @author jorge.jimenez
 */
//@RunWith(Suite.class)
//@Suite.SuiteClasses({})
public class TestServices {

    @Test
    public void testcreateWebSite(){
        System.out.println("CreateSite");
        String uri="www.infotec.com.mx";
        
        //Model model = ModelFactory.createDefaultModel();
        //SemanticModel swbModel=new SemanticModel("GeorgeModel",model);
        //-NO-SWBModel swbModel1=swbModel.createSemanticObject(uri, SWBContext.getVocabulary().SWBModel);
        //SWBModel swbModel2=SWBContext.createSWBModel(swbModel, "www.infotec.com.mx");
        
        /*
        SemanticModel semModel=SWBInstance.getSemanticMgr().getSystemModel();
        
        System.out.println("CreateSite-1");
        
        HomePage hpage=SWBContext.createHomePage(semModel, uri);
        System.out.println("CreateSite-2");
         hpage.setTitle("home");
         System.out.println("CreateSite-3");
         WebSite website=SWBContext.createWebSite(semModel, uri);
         website.addHome(hpage);
         System.out.println("CreateSite-4");
         System.out.println("Entra a createWebSite-4");
         */
        /*
         SWBDBAdmLog swbAdmLog=new SWBDBAdmLog("user","create","www.jorge.com.mx","uri",SWBUtils.IO.getLocaleString(SWBUtils.LOCALE_SERVICES, "create_website"),null);
         System.out.println("Entra a createWebSite-5");
         try{
            swbAdmLog.create();
         }catch(Exception e){
             e.printStackTrace();
         }*/
        //org.semanticwb.SWBPortal.createInstance().getSWBServices().
         /*
        System.out.println("CreateSite-1");
        //SemanticModel model=SWBInstance.getSemanticMgr().getSystemModel();
        SemanticModel model=SWBInstance.getSemanticMgr().getModel("SWBSystem");
        try{
            System.out.println("CreateSite-2");
            SWBServices.WebSiteSrv.createWebSite(model, "www.jorge.com", "homeUri", "titulo", "home");
            System.out.println("CreateSite-3");
        }catch(Exception e){
            e.printStackTrace();
        }
         */
        
         System.out.println("Entra a createWebSite-6");
         //return website;
        
        
        //System.out.println("Model name:"+swbModel2.getName());
        
        
    }
    
}