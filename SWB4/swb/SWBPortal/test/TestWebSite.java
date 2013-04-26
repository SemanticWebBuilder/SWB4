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
 

import java.io.FileOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import org.junit.*;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Dns;
import org.semanticwb.model.Language;
import org.semanticwb.model.ResourceType;
import org.semanticwb.model.Resource;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.Template;
import org.semanticwb.model.TemplateGroup;
import org.semanticwb.model.TemplateRef;
import org.semanticwb.model.UserRepository;
import org.semanticwb.model.User;
import org.semanticwb.model.VersionInfo;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;


/**
 *
 * @author Jei
 */
public class TestWebSite {
    

    public TestWebSite()
    {
    }

    @BeforeClass
    public static void setUpClass() throws Exception
    {
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

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    //@Test
    public void Test1()
    {
        if(SWBContext.getWebSite("sep")==null)
        {
            WebSite site=SWBContext.createWebSite("sep", "http://www.sep.gob.mx#");
            //site.setCreated(new java.util.Date(System.currentTimeMillis()));
            site.setTitle("Sep");
            site.setDescription("Sep WebSite");
            site.setActive(true);
            
            //Crear lenguajes por defecto
            Language lang=site.createLanguage("es");
            lang.setTitle("Español", "es");
            lang.setTitle("Spanish", "en");
            
            site.setLanguage(lang);
 
            Language lang2=site.createLanguage("en");
            lang2.setTitle("Ingles", "es");
            lang2.setTitle("English", "en");
            
            //Create HomePage
            WebPage home=site.createWebPage("home");
            site.setHomePage(home);
            home.setActive(true);
            home.setTitle("Pagina Principal");
            
            //Set User Repository
            UserRepository urep=SWBContext.getDefaultRepository();
            site.setUserRepository(urep);
            User user=urep.getUser("1");

            //VersionInfo para el WebSite
            VersionInfo versionws=site.createVersionInfo();
            versionws.setVersionNumber(1);
            versionws.setCreated(new java.util.Date(System.currentTimeMillis()));
            versionws.setCreator(user);
            versionws.setModifiedBy(user);
            versionws.setVersionComment("Versión inicial del sitio SEP para pruebas");
            versionws.setUpdated(new java.util.Date(System.currentTimeMillis()));
//            site.setActualVersion(versionws);
//            site.setLastVersion(versionws);
            site.setCreator(user);
            site.setModifiedBy(user);
            //Create DNS
            WebSite global=SWBContext.getGlobalWebSite();
            Dns dns=global.getDns("localhost");
            dns.setWebPage(home);

            TemplateGroup grp=site.createTemplateGroup();

            Template tpl=site.createTemplate();
            tpl.setActive(true);
            tpl.setTitle("Platilla1");
            tpl.setGroup(grp);
            
            VersionInfo version=site.createVersionInfo();
            version.setVersionNumber(1);
            version.setVersionFile("template.html");
            tpl.setActualVersion(version);
            tpl.setLastVersion(version);
            
            TemplateRef tplref=site.createTemplateRef();
            tplref.setTemplate(tpl);
            tplref.setActive(true);
            tplref.setPriority(3);
            
            home.addTemplateRef(tplref);
            site.setUpdated(new java.util.Date(System.currentTimeMillis()));
        }
/*
        if(SWBContext.getWebSite("sep2")==null)
        {
            WebSite site=SWBContext.createWebSite("sep2", "http://www.sep2.gob.mx");
            site.setTitle("Sep2");
            site.setDescription("Sep WebSite");
            site.setActive(true);
        }
        if(SWBContext.getUserRepository("u_sep2")==null)
        {
            UserRepository site=SWBContext.createUserRepository("u_sep2", "http://www.u_sep2.gob.mx");
            site.setTitle("u_Sep2");
            site.setDescription("Sep WebSite");
        }
 */
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    //@Test
    public void Test2()
    {
        WebSite site=SWBContext.getWebSite("sep");
        WebPage home=site.getHomePage();
        UserRepository urep=site.getUserRepository();
        Template tpl=site.getTemplate("1");

        if(site.getWebPage("page0")==null)
        {
            for(int x=0;x<10;x++)
            {
                WebPage page=site.createWebPage("page"+x);
                page.setTitle("Pagina "+x);
                page.setParent(home);
                page.setActive(true);
                for(int y=0;y<10;y++)
                {
                    WebPage child=site.createWebPage("page"+x+"_"+y);
                    child.setTitle("Pagina "+x+" "+y);
                    child.setParent(page);
                    child.setActive(true);
//                    for(int z=0;z<10;z++)
//                    {
//                        WebPage tchild=site.createWebPage("page"+x+"_"+y+"_"+z);
//                        tchild.setTitle("Pagina "+x+" "+y+" "+z);
//                        tchild.setParent(child);
//                        tchild.setActive(true);
//                    }
                }
            }
        }
    }

    
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    //@Test
    public void Test3()
    {
        WebSite site=SWBContext.getWebSite("sep");
        WebPage home=site.getHomePage();
        UserRepository urep=SWBContext.getDefaultRepository();
        User user=urep.getUser("1");
        Template tpl=site.getTemplate("1");
        
        if(site.getResourceType("Test")==null)
        {
            ResourceType ptype=site.createResourceType("Test");
            ptype.setResourceClassName("org.semanticwb.portal.resources.Test");
            ptype.setResourceBundle("org.semanticwb.portal.resources.Test");
            ptype.setResourceMode(1);
            ptype.setTitle("Recurso Test");
                    
            Resource resource=site.createResource();
            resource.setActive(true);
            resource.setCreator(user);
            resource.setResourceType(ptype);
            resource.setTitle("Test");
            
            home.addResource(resource);
        }
        
        if(site.getResourceType("Banner")==null)
        {
            ResourceType ptype=site.createResourceType("Banner");
            ptype.setResourceClassName("org.semanticwb.portal.resources.Banner");
            ptype.setResourceBundle("org.semanticwb.portal.resources.Banner");
            ptype.setResourceMode(1);
            ptype.setTitle("Recurso Banner");
                    
            Resource resource=site.createResource();
            resource.setActive(true);
            resource.setCreator(user);
            resource.setResourceType(ptype);
            resource.setTitle("Banner");
        }  
        
        if(site.getResourceType("Recommend")==null)
        {
            
            ResourceType ptype=site.createResourceType("Recommend");
            ptype.setResourceClassName("org.semanticwb.portal.resources.Recommend");
            ptype.setResourceBundle("org.semanticwb.portal.resources.Recommend");
            ptype.setResourceMode(1);
            ptype.setTitle("Recurso Recommend");
                    
            Resource resource=site.createResource();
            resource.setActive(true);
            resource.setCreator(user);
            resource.setResourceType(ptype);
            resource.setTitle("Recommend");
        }  
        
        if(site.getResourceType("RecommendSwf")==null)
        {
            
            ResourceType ptype=site.createResourceType("RecommendSwf");
            ptype.setResourceClassName("org.semanticwb.portal.resources.RecommendSwf");
            ptype.setResourceBundle("org.semanticwb.portal.resources.RecommendSwf");
            ptype.setResourceMode(1);
            ptype.setTitle("Recurso RecommendSwf");
                    
            Resource resource=site.createResource();
            resource.setActive(true);
            resource.setCreator(user);
            resource.setResourceType(ptype);
            resource.setTitle("RecommendSwf");
        }  
        
        if(site.getResourceType("Poll")==null)
        {
            
            ResourceType ptype=site.createResourceType("Poll");
            ptype.setResourceClassName("org.semanticwb.portal.resources.Poll");
            ptype.setResourceBundle("org.semanticwb.portal.resources.Poll");
            ptype.setResourceMode(1);
            ptype.setTitle("Recurso Poll");
                    
            Resource resource=site.createResource();
            resource.setActive(true);
            resource.setCreator(user);
            resource.setResourceType(ptype);
            resource.setTitle("Poll");
        }  
        
        if(site.getResourceType("WBMenuMap")==null)
        {
            
            ResourceType ptype=site.createResourceType("WBMenuMap");
            ptype.setResourceClassName("org.semanticwb.portal.resources.WBMenuMap");
            ptype.setResourceBundle("org.semanticwb.portal.resources.WBMenuMap");
            ptype.setResourceMode(1);
            ptype.setTitle("Recurso WBMenuMap");
                    
            Resource resource=site.createResource();
            resource.setActive(true);
            resource.setCreator(user);
            resource.setResourceType(ptype);
            resource.setTitle("WBMenuMap");
        }  
        
        if(site.getResourceType("WBMenuNivel")==null)
        {
            
            ResourceType ptype=site.createResourceType("WBMenuNivel");
            ptype.setResourceClassName("org.semanticwb.portal.resources.WBMenuNivel");
            ptype.setResourceBundle("org.semanticwb.portal.resources.WBMenuNivel");
            ptype.setResourceMode(1);
            ptype.setTitle("Recurso WBMenuNivel");
                    
            Resource resource=site.createResource();
            resource.setActive(true);
            resource.setCreator(user);
            resource.setResourceType(ptype);
            resource.setTitle("WBMenuNivel");
        }  
        
        
        if(site.getResourceType("Banner1")==null)
        {
            
            ResourceType ptype=site.createResourceType("Banner1");
            ptype.setResourceClassName("org.semanticwb.portal.resources.Banner1");
            ptype.setResourceBundle("org.semanticwb.portal.resources.Banner1");
            ptype.setResourceMode(1);
            ptype.setTitle("Recurso Banner1");
                    
            Resource resource=site.createResource();
            resource.setActive(true);
            resource.setCreator(user);
            resource.setResourceType(ptype);
            resource.setTitle("Banner");
            
            home.addResource(resource);
        } 
        
        if(site.getResourceType("Controls")==null)
        {
            
            ResourceType ptype=site.createResourceType("Controls");
            ptype.setResourceClassName("org.semanticwb.portal.resources.Controls");
            ptype.setResourceBundle("org.semanticwb.portal.resources.Controls");
            ptype.setResourceMode(1);
            ptype.setTitle("Recurso Controls");
                    
            Resource resource=site.createResource();
            resource.setActive(true);
            resource.setCreator(user);
            resource.setResourceType(ptype);
            resource.setTitle("Controls");
            
            home.addResource(resource);
        } 
        
        if(site.getResourceType("Menu")==null)
        {
            
            ResourceType ptype=site.createResourceType("Menu");
            ptype.setResourceClassName("org.semanticwb.portal.resources.Menu");
            ptype.setResourceBundle("org.semanticwb.portal.resources.Menu");
            ptype.setResourceMode(1);
            ptype.setTitle("Recurso Menu");
                    
            Resource resource=site.createResource();
            resource.setActive(true);
            resource.setCreator(user);
            resource.setResourceType(ptype);
            resource.setTitle("Menu");
        }          
        
        if(site.getResourceType("HtmlContent")==null)
        {
            
            ResourceType ptype=site.createResourceType("HtmlContent");
            ptype.setResourceClassName("org.semanticwb.portal.resources.HtmlContent");
            ptype.setResourceBundle("org.semanticwb.portal.resources.HtmlContent");
            ptype.setResourceMode(1);
            ptype.setTitle("Recurso HtmlContent");
                    
            Resource resource=site.createResource();
            resource.setActive(true);
            resource.setCreator(user);
            resource.setResourceType(ptype);
            resource.setTitle("HtmlContent");
            
            home.addResource(resource);
        }  
        
        if(site.getResourceType("JSPResource")==null)
        {
            
            ResourceType ptype=site.createResourceType("JSPResource");
            ptype.setResourceClassName("org.semanticwb.portal.resources.JSPResource");
            ptype.setResourceBundle("org.semanticwb.portal.resources.JSPResource");
            ptype.setResourceMode(1);
            ptype.setTitle("Recurso JSPResource");
                    
            Resource resource=site.createResource();
            resource.setActive(true);
            resource.setCreator(user);
            resource.setResourceType(ptype);
            resource.setTitle("JSPResource");
            
            home.addResource(resource);
        }  
        
        if(site.getResourceType("TematicIndexXSL")==null)
        {
            
            ResourceType ptype=site.createResourceType("TematicIndexXSL");
            ptype.setResourceClassName("org.semanticwb.portal.resources.TematicIndexXSL");
            ptype.setResourceBundle("org.semanticwb.portal.resources.TematicIndexXSL");
            ptype.setResourceMode(1);
            ptype.setTitle("Recurso TematicIndexXSL");
                    
            Resource resource=site.createResource();
            resource.setActive(true);
            resource.setCreator(user);
            resource.setResourceType(ptype);
            resource.setTitle("TematicIndexXSL");
            
            home.addResource(resource);
        }  
        
        
        if(site.getResourceType("FrameContent")==null)
        {
            
            ResourceType ptype=site.createResourceType("FrameContent");
            ptype.setResourceClassName("org.semanticwb.portal.resources.FrameContent");
            ptype.setResourceBundle("org.semanticwb.portal.resources.FrameContent");
            ptype.setResourceMode(1);
            ptype.setTitle("Recurso FrameContent");
                    
            Resource resource=site.createResource();
            resource.setActive(true);
            resource.setCreator(user);
            resource.setResourceType(ptype);
            resource.setTitle("FrameContent");
            
            home.addResource(resource);
        }  
        
        if(site.getResourceType("IFrameContent")==null)
        {
            
            ResourceType ptype=site.createResourceType("IFrameContent");
            ptype.setResourceClassName("org.semanticwb.portal.resources.IFrameContent");
            ptype.setResourceBundle("org.semanticwb.portal.resources.IFrameContent");
            ptype.setResourceMode(1);
            ptype.setTitle("Recurso IFrameContent");
                    
            Resource resource=site.createResource();
            resource.setActive(true);
            resource.setCreator(user);
            resource.setResourceType(ptype);
            resource.setTitle("IFrameContent");
            
            home.addResource(resource);
        }  
        
        
        if(site.getResourceType("HelloXforms")==null)
        {
            
            ResourceType ptype=site.createResourceType("HelloXforms");
            ptype.setResourceClassName("org.semanticwb.portal.resources.HelloXforms");
            ptype.setResourceBundle("org.semanticwb.portal.resources.HelloXforms");
            ptype.setResourceMode(1);
            ptype.setTitle("Recurso HelloXforms");
                    
            Resource resource=site.createResource();
            resource.setActive(true);
            resource.setCreator(user);
            resource.setResourceType(ptype);
            resource.setTitle("HelloXforms");
            
            home.addResource(resource);
        }   
        
        if(site.getResourceType("SWBASemObjectEditor")==null)
        {
            
            ResourceType ptype=site.createResourceType("SWBASemObjectEditor");
            ptype.setResourceClassName("org.semanticwb.portal.admin.resources.SWBASemObjectEditor");
            ptype.setResourceBundle("org.semanticwb.portal.admin.resources.SWBASemObjectEditor");
            ptype.setResourceMode(1);
            ptype.setTitle("SemanticObject Editor");
                    
            Resource resource=site.createResource();
            resource.setActive(true);
            resource.setCreator(user);
            resource.setResourceType(ptype);
            resource.setTitle("SWBASemObjectEditor");
            
            home.addResource(resource);
            
        }
        if(site.getResourceType("SWBASOPropRefEditor")==null)
        {
            ResourceType ptype=site.createResourceType("SWBASOPropRefEditor");
            ptype.setResourceClassName("org.semanticwb.portal.admin.resources.SWBASOPropRefEditor");
            ptype.setResourceBundle("org.semanticwb.portal.admin.resources.SWBASOPropRefEditor");
            ptype.setResourceMode(1);
            ptype.setTitle("SemanticObjectPropRef Editor");
                    
            Resource resource=site.createResource();
            resource.setActive(true);
            resource.setCreator(user);
            resource.setResourceType(ptype);
            resource.setTitle("SWBASOPropRefEditor");
            
            WebPage page0=site.getWebPage("page0");
            page0.addResource(resource);
            
        }
        
        if (site.getResourceType("CommentSwf") == null)
        {
            ResourceType ptype = site.createResourceType("CommentSwf");
            ptype.setResourceClassName("org.semanticwb.portal.resources.CommentSwf");
            ptype.setResourceBundle("org.semanticwb.portal.resources.CommentSwf");
            ptype.setResourceMode(1);
            ptype.setTitle("Recurso CommentSwf");
                    
            Resource resource = site.createResource();
            resource.setActive(true);
            resource.setCreator(user);
            resource.setResourceType(ptype);
            resource.setTitle("CommentSwf");
        }  

    }
    
    //@Test
    public void Test4()
    {
        WebSite site=SWBContext.getWebSite("sep");
//        site.setActive(true);
//        WebPage wp=site.getHomePage();
//        wp.setActive(true);
//        UserRepository urep=SWBContext.getDefaultRepository();
//        site.setUserRepository(urep);
//        User user=urep.getUser("1");
//        site.setModifiedBy(user);
        Template tpl = site.getTemplate("1");
        TemplateGroup grp=site.createTemplateGroup();
        tpl.setGroup(grp);

        
//        if(tpl==null)
//        {
//            tpl=site.createTemplate();
//            tpl.setTitle("Platilla1");
//            VersionInfo version=site.createVersionInfo();
//            version.setVersionNumber(1);
//            version.setVersionFile("template.html");
//            tpl.setActualVersion(version);
//            tpl.setLastVersion(version);
//        }
//
//        tpl.setActive(true);
//        TemplateRef tplref=site.createTemplateRef();
//        tplref.setTemplate(tpl);
//        tplref.setActive(true);
//        tplref.setPriority(3);
//        tplref.setActive(true);
//        wp.addTemplateRef(tplref);
//        site.setUpdated(new java.util.Date(System.currentTimeMillis()));
    }
    
    //@Test
    public void TestAdmin1()
    {    
        if(SWBContext.getWebSite("SWBAdmin")==null)
        {
            WebSite site=SWBContext.createWebSite("SWBAdmin", "http://www.semanticwb.org/SWBAdmin");
            site.setTitle("Admin");
            site.setDescription("Admin WebSite");
            site.setActive(true);
            
            //Crear lenguajes por defecto
            Language lang=site.createLanguage("es");
            lang.setTitle("Español", "es");
            lang.setTitle("Spanish", "en");
            lang=site.createLanguage("en");
            lang.setTitle("Ingles", "es");
            lang.setTitle("English", "en");
            
            //Create HomePage
            WebPage home=site.createWebPage("home");
            site.setHomePage(home);
            home.setActive(true);
            home.setTitle("Semantic WebBuilder");

            //Set User Repository
            UserRepository urep=SWBContext.getDefaultRepository();
            site.setUserRepository(urep);

            Template tpl=site.createTemplate();
            tpl.setActive(true);
            tpl.setTitle("container");
            
            VersionInfo version=site.createVersionInfo();
            version.setVersionNumber(1);
            version.setVersionFile("template.html");
            tpl.setActualVersion(version);
            tpl.setLastVersion(version);
            
            TemplateRef tplref=site.createTemplateRef();
            tplref.setTemplate(tpl);
            tplref.setActive(true);
            tplref.setPriority(3);
            
            home.addTemplateRef(tplref);
        }        
    }
    
    //@Test
    public void TestAdmin2()
    {    
        if(SWBContext.getWebSite("SWBAdmin")!=null)
        {
            WebSite site=SWBContext.getWebSite("SWBAdmin");
//            //Asignar platilla a system 
//            WebPage menus=site.getWebPage("WBAd_System");
//            TemplateRef tplref=site.createTemplateRef();
//            tplref.setTemplate(site.getTemplate("2"));
//            tplref.setActive(true);
//            tplref.setPriority(3);
//            menus.addTemplateRef(tplref);    
            
            ResourceType ptype=site.createResourceType("SWBASOPropRefEditor");
            ptype.setResourceClassName("org.semanticwb.portal.admin.resources.SWBASOPropRefEditor");
            ptype.setResourceBundle("org.semanticwb.portal.admin.resources.SWBASOPropRefEditor");
            ptype.setResourceMode(3);
            ptype.setTitle("SemanticObjectPropRef Editor");
                    
            Resource resource=site.createResource();
            resource.setActive(true);
            //resource.setCreator(user);
            resource.setResourceType(ptype);
            resource.setTitle("SWBASOPropRefEditor");
            
        }
    }
    
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    //@Test
    public void TestOntology()
    {
        WebSite site=SWBContext.getWebSite("sep");
        WebPage home=site.getHomePage();
        UserRepository urep=site.getUserRepository();
        Template tpl=site.getTemplate("1");

        User user=urep.getUserByLogin("admin");
        System.out.println("User:"+user);

        WebPage homeOnt=(WebPage)SWBPlatform.getSemanticMgr().getOntology().getGenericObject(home.getURI());  //, SWBContext.getVocabulary().WebPage

        System.out.println("home:"+home+" "+home.getTitle());
        System.out.println("homeOnt:"+homeOnt+" "+homeOnt.getTitle());

        home.setTitle("Prueba 1a");

        System.out.println("home:"+home+" "+home.getTitle());
        System.out.println("homeOnt:"+homeOnt+" "+homeOnt.getTitle());

        home.setTitle("Prueba 1b");

        System.out.println("home:"+home+" "+home.getTitle());
        System.out.println("homeOnt:"+homeOnt+" "+homeOnt.getTitle());

        homeOnt.setTitle("Prueba 2");

        System.out.println("home:"+home+" "+home.getTitle());
        System.out.println("homeOnt:"+homeOnt+" "+homeOnt.getTitle());

        home.setTitle("Home");

        System.out.println("home:"+home+" "+home.getTitle());
        System.out.println("homeOnt:"+homeOnt+" "+homeOnt.getTitle());
    }   
    
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    //@Test
    public void TestPerformance1()
    {
            WebSite site=SWBContext.getWebSite("sep");
            WebPage home=site.getHomePage();
            UserRepository urep=site.getUserRepository();
            Template tpl=site.getTemplate("1");
            
            for(int x=0;x<10;x++)
            {
                WebPage page=site.getWebPage("page"+x);
                //System.out.println(page.getTitle());
            }
    }       
    
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    //@Test
    public void TestPerformance2()
    {
            WebSite site=SWBContext.getWebSite("sep");
            WebPage home=site.getHomePage();
            UserRepository urep=site.getUserRepository();
            Template tpl=site.getTemplate("1");
            
            Iterator<WebPage> it=home.listChilds();
            while(it.hasNext())
            {
                WebPage page=it.next();
                //System.out.println(page.getTitle());
            }
            
            ArrayList arr=SWBPortal.getAppLanguages();
            System.out.println(arr);
    }     

    //@Test
    public void setXml()
    {

    }

    //@Test
    public void write()
    {
        WebSite site=SWBContext.getWebSite("sep");
        site.getSemanticObject().getModel().write(System.out);
    }
    
    //@Test
    public void SetAtributesDefaultUserRepository()
    {
        UserRepository urep=SWBContext.getDefaultRepository();
//        urep.setProperty("SWBUR_AuthMethod", "FORM2"); //BASIC
//        urep.setProperty("SWBUR_LoginContext", "swb4TripleStoreModule");
//        urep.setProperty("SWBUR_CallBackHandlerClassName", "org.semanticwb.security.auth.SWB4CallbackHandlerLoginPasswordImp");
        //MAPS74 - Cambiado a semantic prop
            urep.setAuthMethod("FORM");
            urep.setLoginContext("swb4TripleStoreModule");
            urep.setCallBackHandlerClassName("org.semanticwb.security.auth.SWB4CallbackHandlerLoginPasswordImp");
        urep.setDescription("3 Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei Jei");
    }

}
