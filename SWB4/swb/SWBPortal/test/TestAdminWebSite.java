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
 
import java.io.File;
import java.io.FileOutputStream;
import org.junit.*;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Calendarable;
import org.semanticwb.model.Language;
import org.semanticwb.model.ObjectBehavior;
import org.semanticwb.model.PFlowRefable;
import org.semanticwb.model.Resource;
import org.semanticwb.model.ResourceType;
import org.semanticwb.model.Resourceable;
import org.semanticwb.model.RoleRefable;
import org.semanticwb.model.Roleable;
import org.semanticwb.model.Rule;
import org.semanticwb.model.RuleRefable;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.Template;
import org.semanticwb.model.TemplateGroup;
import org.semanticwb.model.TemplateRef;
import org.semanticwb.model.TemplateRefable;
import org.semanticwb.model.UserRepository;
import org.semanticwb.model.VersionInfo;
import org.semanticwb.model.Versionable;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;



/**
 *
 * @author Jei
 */
public class TestAdminWebSite {
    

    public TestAdminWebSite()
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

    public ResourceType createResourceType(WebSite site, String name, String clsName, int type, String pkg)
    {
        ResourceType pt=site.getResourceType(name);
        if(pt==null)
        {
            pt=site.createResourceType(name);
            pt.setTitle(name);
            pt.setResourceMode(type);
            pt.setResourceClassName(pkg+"."+clsName);
            pt.setResourceBundle(pkg+"."+clsName);
            return pt;
        }
        return null;
    }

    public ObjectBehavior createBehavior(WebSite site, String id, String title, String sort)
    {
        ObjectBehavior obj=ObjectBehavior.ClassMgr.getObjectBehavior(id, site);
        if(obj==null)
        {
            obj=ObjectBehavior.ClassMgr.createObjectBehavior(id, site);
            obj.setTitle(title);
            obj.setSortName(sort);
            obj.setActive(true);
            return obj;
        }
        return null;
    }


    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    //@Test
    public void Admin()
    {
        WebSite site=SWBContext.getAdminWebSite();
        
        if(site==null)
        {
            site=SWBContext.createWebSite("SWBAdmin", "http://www.semanticwb.org/SWBAdmin");
            site.setTitle("Admin");
            site.setDescription("Admin WebSite");
            site.setActive(true);
            
            //Crear lenguajes por defecto
            Language lang=site.createLanguage("es");
            lang.setTitle("Español", "es");
            lang.setTitle("Spanish", "en");
            lang.setTitle("Spanish", "pt");
            lang=site.createLanguage("en");
            lang.setTitle("Ingles", "es");
            lang.setTitle("English", "en");
            lang.setTitle("English", "pt");
            lang=site.createLanguage("pt");
            lang.setTitle("Ingles", "es");
            lang.setTitle("English", "en");
            lang.setTitle("English", "pt");
            
            //Set User Repository
            UserRepository urep=SWBContext.getDefaultRepository();
            site.setUserRepository(urep);
            
            TemplateGroup grp=site.createTemplateGroup();
            grp.setTitle("Default");
            Template tpl=site.createTemplate();
            tpl.setActive(true);
            tpl.setTitle("Main");
            tpl.setGroup(grp);
            
            VersionInfo version=site.createVersionInfo();
            version.setVersionNumber(1);
            version.setVersionFile("template.html");
            tpl.setActualVersion(version);
            tpl.setLastVersion(version);
            
            tpl=site.createTemplate();
            tpl.setActive(true);
            tpl.setTitle("Content");           
            tpl.setGroup(grp);

            version=site.createVersionInfo();
            version.setVersionNumber(1);
            version.setVersionFile("template.html");
            tpl.setActualVersion(version);
            tpl.setLastVersion(version);                       

            tpl=site.createTemplate();
            tpl.setActive(true);
            tpl.setTitle("Content Ajax");
            tpl.setGroup(grp);

            version=site.createVersionInfo();
            version.setVersionNumber(1);
            version.setVersionFile("template.html");
            tpl.setActualVersion(version);
            tpl.setLastVersion(version);
            
            WebPage home=site.createWebPage("home");
            home.setTitle("Home");
            site.setHomePage(home);
            home.setActive(true);

            TemplateRef tplref=site.createTemplateRef();
            tplref.setTemplate(site.getTemplate("1"));
            tplref.setActive(true);
            tplref.setPriority(3);
            site.getHomePage().addTemplateRef(tplref);
        }



        WebPage home=site.getHomePage();
        WebPage ob=site.getWebPage("ObjectBehavior");
        if(ob==null)
        {
            ob=site.createWebPage("ObjectBehavior");
            ob.setActive(true);
            ob.setTitle("Comportamientos");
            ob.setParent(home);

            TemplateRef tplref=site.createTemplateRef();
            tplref.setTemplate(site.getTemplate("3"));
            tplref.setActive(true);
            tplref.setPriority(3);
            ob.addTemplateRef(tplref);
        }

        ResourceType pt=null;
        Resource p=null;

        //Create Resource Types
        pt=createResourceType(site, "JSPResource", "JSPResource",1,"org.semanticwb.portal.resources");
        pt=createResourceType(site, "SemObjPropRefEditor", "SWBASOPropRefEditor",1,"org.semanticwb.portal.admin.resources");
        pt=createResourceType(site, "CreateUser", "SWBACreateUser",1,"org.semanticwb.portal.admin.resources");
        pt=createResourceType(site, "DBSparql", "SWBADBSparql",1,"org.semanticwb.portal.admin.resources");
        pt=createResourceType(site, "Editor", "SWBAEditor",1,"org.semanticwb.portal.admin.resources");
        if(pt!=null)
        {
            p=site.createResource();
            p.setResourceType(pt);
            p.setTitle("Editor");
            p.setActive(true);
        }
        //p=site.getResource("1");
        //p.setResourceType(site.getResourceType("Editor"));
        pt=createResourceType(site, "ExportWebSite", "SWBExportWebSite",1,"org.semanticwb.portal.admin.resources");
        pt=createResourceType(site, "ImportWebSite", "SWBImportWebSite",1,"org.semanticwb.portal.admin.resources");
        pt=createResourceType(site, "Logs", "SWBALogs",1,"org.semanticwb.portal.admin.resources");
        pt=createResourceType(site, "MMemory", "SWBAMMemory",1,"org.semanticwb.portal.admin.resources");
        pt=createResourceType(site, "Properties", "SWBAProperties",1,"org.semanticwb.portal.admin.resources");
        pt=createResourceType(site, "Rule", "SWBARule",1,"org.semanticwb.portal.admin.resources");
        pt=createResourceType(site, "Schedule", "SWBASchedule",1,"org.semanticwb.portal.admin.resources");
        pt=createResourceType(site, "SearchUsers", "SWBASearchUsers",1,"org.semanticwb.portal.admin.resources");
        pt=createResourceType(site, "VersionInfo", "SWBAVersionInfo",1,"org.semanticwb.portal.admin.resources");
        pt=createResourceType(site, "WebPageContents", "SWBAWebPageContents",1,"org.semanticwb.portal.admin.resources");
        pt=createResourceType(site, "GlobalReport", "WBAGlobalReport",1,"org.semanticwb.portal.admin.resources");
        //createResourceType(site, "XX", "XX",1,"org.semanticwb.portal.admin.resources");

        ObjectBehavior obj=createBehavior(site,"bh_Information","Información","10");
        if(obj!=null)
        {
            p=site.createResource();
            p.setResourceType(site.getResourceType("JSPResource"));
            p.setTitle("Information");
            p.setActive(true);
            p.setPriority(3);
            p.setAttribute("jsppath","/swbadmin/jsp/SemObjectEditor.jsp");
            try
            {
                p.updateAttributesToDB();
            }catch(Exception e){e.printStackTrace();}
            obj.addResource(p);
            obj.setParent(ob);
        }

        obj=createBehavior(site,"bh_Contents","Contenidos","20");
        if(obj!=null)
        {
            obj.setBehaviorParams("sprop=swb:hasResource");
            p=site.createResource();
            p.setResourceType(site.getResourceType("WebPageContents"));
            p.setTitle("Contents");
            p.setActive(true);
            p.setPriority(3);
            obj.addResource(p);
            obj.setParent(ob);
            obj.setInterface(Resourceable.swb_Resourceable.getSemanticObject());
        }

        obj=createBehavior(site,"bh_TemplateEdit","Editar Platilla","20");
        if(obj!=null)
        {
            p=site.createResource();
            p.setResourceType(site.getResourceType("JSPResource"));
            p.setTitle("Edit Template");
            p.setActive(true);
            p.setPriority(3);
            p.setAttribute("jsppath","/swbadmin/jsp/templateEdit.jsp");
            try
            {
                p.updateAttributesToDB();
            }catch(Exception e){e.printStackTrace();}
            obj.addResource(p);
            obj.setParent(ob);
            obj.setInterface(Template.sclass.getSemanticObject());
        }

        obj=createBehavior(site,"bh_RuleEditor","Editar Regla","20");
        if(obj!=null)
        {
            p=site.createResource();
            p.setResourceType(site.getResourceType("Rule"));
            p.setTitle("Edit Rule");
            p.setActive(true);
            p.setPriority(3);
            obj.addResource(p);
            obj.setParent(ob);
            obj.setInterface(Rule.sclass.getSemanticObject());
        }

        obj=createBehavior(site,"bh_Templates","Plantillas","20");
        if(obj!=null)
        {
            p=site.createResource();
            p.setResourceType(site.getResourceType("SemObjPropRefEditor"));
            p.setTitle("Templates");
            p.setActive(true);
            p.setPriority(3);
            obj.addResource(p);
            obj.setParent(ob);
            obj.setBehaviorParams("sprop=swb:hasTemplateRef&spropref=swb:template");
            obj.setInterface(TemplateRefable.swb_TemplateRefable.getSemanticObject());
        }

        obj=createBehavior(site,"bh_AdminPorltet","Administrar","30");
        if(obj!=null)
        {
            p=site.createResource();
            p.setResourceType(site.getResourceType("JSPResource"));
            p.setTitle("Admin Resource");
            p.setActive(true);
            p.setPriority(3);
            p.setAttribute("jsppath","/swbadmin/jsp/editResource.jsp");
            try
            {
                p.updateAttributesToDB();
            }catch(Exception e){e.printStackTrace();}
            obj.addResource(p);
            obj.setParent(ob);
            obj.setInterface(Resource.sclass.getSemanticObject());
        }

        obj=createBehavior(site,"bh_PFlows","Flujos de Publicación","40");
        if(obj!=null)
        {
            p=site.createResource();
            p.setResourceType(site.getResourceType("SemObjPropRefEditor"));
            p.setTitle("PFlows");
            p.setActive(true);
            p.setPriority(3);
            obj.addResource(p);
            obj.setParent(ob);
            obj.setBehaviorParams("sprop=swb:hasPFlowRef&spropref=swb:pflow");
            obj.setInterface(PFlowRefable.swb_PFlowRefable.getSemanticObject());
        }

        obj=createBehavior(site,"bh_Versions","Versiones","50");
        if(obj!=null)
        {
            p=site.createResource();
            p.setResourceType(site.getResourceType("VersionInfo"));
            p.setTitle("Version");
            p.setActive(true);
            p.setPriority(3);
            obj.addResource(p);
            obj.setParent(ob);
            obj.setInterface(Versionable.swb_Versionable.getSemanticObject());
        }

        obj=createBehavior(site,"bh_Roles","Roles","60");
        if(obj!=null)
        {
            p=site.createResource();
            p.setResourceType(site.getResourceType("SemObjPropRefEditor"));
            p.setTitle("Roles");
            p.setActive(true);
            p.setPriority(3);
            obj.addResource(p);
            obj.setParent(ob);
            obj.setBehaviorParams("sprop=swb:hasRoleRef&spropref=swb:role");
            obj.setInterface(RoleRefable.swb_RoleRefable.getSemanticObject());
        }

        obj=createBehavior(site,"bh_RolesDirect","Roles","65");
        if(obj!=null)
        {
            p=site.createResource();
            p.setResourceType(site.getResourceType("SemObjPropRefEditor"));
            p.setTitle("Roles Direct");
            p.setActive(true);
            p.setPriority(3);
            obj.addResource(p);
            obj.setParent(ob);
            obj.setBehaviorParams("sprop=swb:hasRole&spropref=swb:hasRole");
            obj.setInterface(Roleable.swb_Roleable.getSemanticObject());
        }

        obj=createBehavior(site,"bh_Rules","Reglas","70");
        if(obj!=null)
        {
            p=site.createResource();
            p.setResourceType(site.getResourceType("SemObjPropRefEditor"));
            p.setTitle("Rules");
            p.setActive(true);
            p.setPriority(3);
            obj.addResource(p);
            obj.setParent(ob);
            obj.setBehaviorParams("sprop=swb:hasRuleRef&spropref=swb:rule");
            obj.setInterface(RuleRefable.swb_RuleRefable.getSemanticObject());
        }

        obj=createBehavior(site,"bh_Schedule","Calendarización","80");
        if(obj!=null)
        {
            p=site.createResource();
            p.setResourceType(site.getResourceType("Schedule"));
            p.setTitle("Schedule");
            p.setActive(true);
            p.setPriority(3);
            obj.addResource(p);
            obj.setParent(ob);
            obj.setBehaviorParams("sprop=swb:hasCalendar");
            obj.setInterface(Calendarable.swb_Calendarable.getSemanticObject());
        }

        obj=createBehavior(site,"bh_Log","Bitácora","90");
        if(obj!=null)
        {
            p=site.createResource();
            p.setResourceType(site.getResourceType("Logs"));
            p.setTitle("Logs");
            p.setActive(true);
            p.setPriority(3);
            obj.addResource(p);
            obj.setParent(ob);
        }

    }

    //@Test
    public void writeAdmin()
    {
        WebSite site=SWBContext.getAdminWebSite();
        System.out.println("********"+SWBUtils.getApplicationPath());
        File file=new File(SWBUtils.getApplicationPath()+"../web/swbadmin/rdf/SWBAdmin.rdf");
        try
        {
            System.out.println("file:"+file.getCanonicalPath());
            FileOutputStream out=new FileOutputStream(file);
            site.getSemanticObject().getModel().write(out);
        }catch(Exception e){e.printStackTrace();}

        site=SWBContext.getOntEditor();
        System.out.println("********"+SWBUtils.getApplicationPath());
        file=new File(SWBUtils.getApplicationPath()+"../web/swbadmin/rdf/SWBOntEdit.rdf");
        try
        {
            System.out.println("file:"+file.getCanonicalPath());
            FileOutputStream out=new FileOutputStream(file);
            site.getSemanticObject().getModel().write(out);
        }catch(Exception e){e.printStackTrace();}
    }

    //@Test
    public void writeAdminNT()
    {
        WebSite site=SWBContext.getAdminWebSite();
        System.out.println("********"+SWBUtils.getApplicationPath());
        //File file=new File(SWBUtils.getApplicationPath()+"../web/swbadmin/rdf/SWBAdmin.nt");
        File file=new File("D:\\programming\\proys\\SWB\\swb\\build\\web\\swbadmin\\rdf\\SWBAdmin.nt");
        try
        {
            //System.out.println("file:"+file.getCanonicalPath());
            FileOutputStream out=new FileOutputStream(file);
            site.getSemanticObject().getModel().write(out,"N-TRIPLE");
        }catch(Exception e){e.printStackTrace();}

        site=SWBContext.getOntEditor();
        System.out.println("********"+SWBUtils.getApplicationPath());
        //file=new File(SWBUtils.getApplicationPath()+"../web/swbadmin/rdf/SWBOntEdit.nt");
        file=new File("D:\\programming\\proys\\SWB\\swb\\build\\web\\swbadmin\\rdf\\SWBOntEdit.nt");
        try
        {
            //System.out.println("file:"+file.getCanonicalPath());
            FileOutputStream out=new FileOutputStream(file);
            site.getSemanticObject().getModel().write(out,"N-TRIPLE");
        }catch(Exception e){e.printStackTrace();}
    }

}
