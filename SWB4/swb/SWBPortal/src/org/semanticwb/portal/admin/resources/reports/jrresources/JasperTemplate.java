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
 

package org.semanticwb.portal.admin.resources.reports.jrresources;

// TODO: Auto-generated Javadoc
/**
 * The Enum JasperTemplate.
 */
public enum JasperTemplate {
    
    /** The RE s_ appeared. */
    RES_APPEARED("/org/semanticwb/portal/admin/resources/reports/jrresources/templates/AppearedResRep.jasper"),
    
    /** The RE s_ appeare d_ html. */
    RES_APPEARED_HTML("/org/semanticwb/portal/admin/resources/reports/jrresources/templates/AppearedResHtmlRep.jasper"),

    //###My code
    /** The GLOBA l_ daily. */
    GLOBAL_DAILY("/org/semanticwb/portal/admin/resources/reports/jrresources/templates/GlobalDailyRep.jasper"),

    GLOBAL_DAILY_EXCEL("/org/semanticwb/portal/admin/resources/reports/jrresources/templates/GlobalDailyRepExcel.jasper"),
    
    /** The GLOBA l_ dail y_ graph. */
    GLOBAL_DAILY_GRAPH("/org/semanticwb/portal/admin/resources/reports/jrresources/templates/GlobalDailyGraphRep.jasper"),
    
    /** The GLOBA l_ dail y_ html. */
    GLOBAL_DAILY_HTML("/org/semanticwb/portal/admin/resources/reports/jrresources/templates/GlobalDailyHtmlRep.jasper"),

    //###My Code
    /** The GLOBA l_ monthly. */
    GLOBAL_MONTHLY("/org/semanticwb/portal/admin/resources/reports/jrresources/templates/GlobalRep.jasper"),

    GLOBAL_MONTHLY_EXCEL("/org/semanticwb/portal/admin/resources/reports/jrresources/templates/GlobalRepExcel.jasper"),

    /** The GLOBA l_ monthl y_ graph. */
    GLOBAL_MONTHLY_GRAPH("/org/semanticwb/portal/admin/resources/reports/jrresources/templates/GlobalGraphRep.jasper"),
    
    /** The GLOBA l_ monthl y_ html. */
    GLOBAL_MONTHLY_HTML("/org/semanticwb/portal/admin/resources/reports/jrresources/templates/GlobalHtmlRep.jasper"),

    //###My Code
    /** The DEVIC e_ daily. */
    DEVICE_DAILY("/org/semanticwb/portal/admin/resources/reports/jrresources/templates/DevicesDailyRep.jasper"),
    DEVICE_DAILY_EXCEL("/org/semanticwb/portal/admin/resources/reports/jrresources/templates/DevicesDailyRepExcel.jasper"),
    DEVICE_DAILY_GRAPH("/org/semanticwb/portal/admin/resources/reports/jrresources/templates/DevicesDailyGraphRep.jasper"),
    
    /** The DEVIC e_ dail y_ html. */
    DEVICE_DAILY_HTML("/org/semanticwb/portal/admin/resources/reports/jrresources/templates/DevicesDailyHtmlRep.jasper"),
    DEVICE_MONTHLY("/org/semanticwb/portal/admin/resources/reports/jrresources/templates/DevicesRep.jasper"),
    DEVICE_MONTHLY_EXCEL("/org/semanticwb/portal/admin/resources/reports/jrresources/templates/DevicesRepExcel.jasper"),
    DEVICE_MONTHLY_GRAPH("/org/semanticwb/portal/admin/resources/reports/jrresources/templates/DevicesGraphRep.jasper"),
    
    /** The DEVIC e_ monthl y_ html. */
    DEVICE_MONTHLY_HTML("/org/semanticwb/portal/admin/resources/reports/jrresources/templates/DevicesHtmlRep.jasper"),

    //My Code
    /** The LANGUAG e_ daily. */
    LANGUAGE_DAILY("/org/semanticwb/portal/admin/resources/reports/jrresources/templates/LanguageDailyRep.jasper"),

    LANGUAGE_DAILY_EXCEL("/org/semanticwb/portal/admin/resources/reports/jrresources/templates/LanguageDailyRepExcel.jasper"),

    /** The LANGUAG e_ dail y_ graph. */
    LANGUAGE_DAILY_GRAPH("/org/semanticwb/portal/admin/resources/reports/jrresources/templates/LanguageDailyGraphRep.jasper"),
    
    /** The LANGUAG e_ dail y_ html. */
    LANGUAGE_DAILY_HTML("/org/semanticwb/portal/admin/resources/reports/jrresources/templates/LanguageDailyHtmlRep.jasper"),

    //My Code
    /** The LANGUAG e_ monthly. */
    LANGUAGE_MONTHLY("/org/semanticwb/portal/admin/resources/reports/jrresources/templates/LanguagesRep.jasper"),

    LANGUAGE_MONTHLY_EXCEL("/org/semanticwb/portal/admin/resources/reports/jrresources/templates/LanguagesRepExcel.jasper"),
    
    /** The LANGUAG e_ monthl y_ graph. */
    LANGUAGE_MONTHLY_GRAPH("/org/semanticwb/portal/admin/resources/reports/jrresources/templates/LanguagesGraphRep.jasper"),
    
    /** The LANGUAG e_ monthl y_ html. */
    LANGUAGE_MONTHLY_HTML("/org/semanticwb/portal/admin/resources/reports/jrresources/templates/LanguagesHtmlRep.jasper"),

    //###My Code
    /** The LOGGE d_ uniqu e_ daily. */
    LOGGED_UNIQUE_DAILY("/org/semanticwb/portal/admin/resources/reports/jrresources/templates/UniqueUsersDailyRep.jasper"),

    LOGGED_UNIQUE_DAILY_EXCEL("/org/semanticwb/portal/admin/resources/reports/jrresources/templates/UniqueUsersDailyRepExcel.jasper"),
    
    /** The LOGGE d_ uniqu e_ dail y_ graph. */
    LOGGED_UNIQUE_DAILY_GRAPH("/org/semanticwb/portal/admin/resources/reports/jrresources/templates/UniqueUsersDailyGraphRep.jasper"),
    
    /** The LOGGE d_ uniqu e_ dail y_ html. */
    LOGGED_UNIQUE_DAILY_HTML("/org/semanticwb/portal/admin/resources/reports/jrresources/templates/UniqueUsersDailyHtmlRep.jasper"),

    //## My Code
    /** The LOGGE d_ uniqu e_ monthly. */
    LOGGED_UNIQUE_MONTHLY("/org/semanticwb/portal/admin/resources/reports/jrresources/templates/UniqueUsersRep.jasper"),

    LOGGED_UNIQUE_MONTHLY_EXCEL("/org/semanticwb/portal/admin/resources/reports/jrresources/templates/UniqueUsersRepExcel.jasper"),

    /** The LOGGE d_ uniqu e_ monthl y_ graph. */
    LOGGED_UNIQUE_MONTHLY_GRAPH("/org/semanticwb/portal/admin/resources/reports/jrresources/templates/UniqueUsersGraphRep.jasper"),
    
    /** The LOGGE d_ uniqu e_ monthl y_ html. */
    LOGGED_UNIQUE_MONTHLY_HTML("/org/semanticwb/portal/admin/resources/reports/jrresources/templates/UniqueUsersHtmlRep.jasper"),

    //##My code
    /** The USE r_ logge d_ daily. */
    USER_LOGGED_DAILY("/org/semanticwb/portal/admin/resources/reports/jrresources/templates/UsersDailyRep.jasper"),

    USER_LOGGED_DAILY_EXCEL("/org/semanticwb/portal/admin/resources/reports/jrresources/templates/UsersDailyRepExcel.jasper"),

    /** The USE r_ logge d_ dail y_ graph. */
    USER_LOGGED_DAILY_GRAPH("/org/semanticwb/portal/admin/resources/reports/jrresources/templates/UsersDailyGraphRep.jasper"),
    
    /** The USE r_ logge d_ dail y_ html. */
    USER_LOGGED_DAILY_HTML("/org/semanticwb/portal/admin/resources/reports/jrresources/templates/UsersDailyHtmlRep.jasper"),
    
    //##My code
    /** The USE r_ logge d_ monthly. */
    USER_LOGGED_MONTHLY("/org/semanticwb/portal/admin/resources/reports/jrresources/templates/UsersRep.jasper"),

    USER_LOGGED_MONTHLY_EXCEL("/org/semanticwb/portal/admin/resources/reports/jrresources/templates/UsersRepExcel.jasper"),
    
    /** The USE r_ logge d_ monthl y_ graph. */
    USER_LOGGED_MONTHLY_GRAPH("/org/semanticwb/portal/admin/resources/reports/jrresources/templates/UsersGraphRep.jasper"),
    
    /** The USE r_ logge d_ monthl y_ html. */
    USER_LOGGED_MONTHLY_HTML("/org/semanticwb/portal/admin/resources/reports/jrresources/templates/UsersHtmlRep.jasper"),

    //##My code
    /** The SECTIO n_ daily. */
    SECTION_DAILY("/org/semanticwb/portal/admin/resources/reports/jrresources/templates/SectionsDailyRep.jasper"),

    SECTION_DAILY_EXCEL("/org/semanticwb/portal/admin/resources/reports/jrresources/templates/SectionsDailyRepExcel.jasper"),
    /** The SECTIO n_ dail y_ graph. */
    SECTION_DAILY_GRAPH("/org/semanticwb/portal/admin/resources/reports/jrresources/templates/SectionsDailyGraphRep.jasper"),
    
    /** The SECTIO n_ dail y_ html. */
    SECTION_DAILY_HTML("/org/semanticwb/portal/admin/resources/reports/jrresources/templates/SectionsDailyHtmlRep.jasper"),
    
    /** The SECTIO n_ monthly. */
    SECTION_MONTHLY("/org/semanticwb/portal/admin/resources/reports/jrresources/templates/SectionsRep.jasper"),
    
    /** The SECTIO n_ monthl y_ graph. */
    SECTION_MONTHLY_GRAPH("/org/semanticwb/portal/admin/resources/reports/jrresources/templates/SectionsGraphRep.jasper"),
    
    /** The SECTIO n_ monthl y_ html. */
    SECTION_MONTHLY_HTML("/org/semanticwb/portal/admin/resources/reports/jrresources/templates/SectionsHtmlRep.jasper"),    
    
    /** The RESOURC e_ daily. */
    RESOURCE_DAILY("/org/semanticwb/portal/admin/resources/reports/jrresources/templates/ResourcesDailyRep.jasper"),
    
    /** The RESOURC e_ dail y_ graph. */
    RESOURCE_DAILY_GRAPH("/org/semanticwb/portal/admin/resources/reports/jrresources/templates/ResourcesDailyGraphRep.jasper"),
    
    /** The RESOURC e_ dail y_ html. */
    RESOURCE_DAILY_HTML("/org/semanticwb/portal/admin/resources/reports/jrresources/templates/ResourcesDailyHtmlRep.jasper"),
    
    /** The RESOURC e_ monthly. */
    RESOURCE_MONTHLY("/org/semanticwb/portal/admin/resources/reports/jrresources/templates/ResourcesRep.jasper"),
    
    /** The RESOURC e_ monthl y_ graph. */
    RESOURCE_MONTHLY_GRAPH("/org/semanticwb/portal/admin/resources/reports/jrresources/templates/ResourcesGraphRep.jasper"),
    
    /** The RESOURC e_ monthl y_ html. */
    RESOURCE_MONTHLY_HTML("/org/semanticwb/portal/admin/resources/reports/jrresources/templates/ResourcesHtmlRep.jasper"),    
    
    /** The SESSIO n_ daily. */
    //###My code
    //SESSION_DAILY("/org/semanticwb/portal/admin/resources/reports/jrresources/templates/SessionsDailyRepMod.jasper"),
    SESSION_DAILY("/org/semanticwb/portal/admin/resources/reports/jrresources/templates/SessionsDailyRep.jasper"),

    SESSION_DAILY_EXCEL("/org/semanticwb/portal/admin/resources/reports/jrresources/templates/SessionsDailyRepExcel.jasper"),
    /** The SESSIO n_ dail y_ graph. */
    SESSION_DAILY_GRAPH("/org/semanticwb/portal/admin/resources/reports/jrresources/templates/SessionsDailyGraphRep.jasper"),
    
    /** The SESSIO n_ dail y_ html. */
    //### My code
    //SESSION_DAILY_HTML("/org/semanticwb/portal/admin/resources/reports/jrresources/templates/SessionsDailyHtmlRepMod.jasper"),
    SESSION_DAILY_HTML("/org/semanticwb/portal/admin/resources/reports/jrresources/templates/SessionsDailyHtmlRep.jasper"),
    /** The SESSIO n_ monthly. */
    SESSION_MONTHLY("/org/semanticwb/portal/admin/resources/reports/jrresources/templates/SessionsRep.jasper"),

    SESSION_MONTHLY_EXCEL("/org/semanticwb/portal/admin/resources/reports/jrresources/templates/SessionsRepExcel.jasper"),
    
    /** The SESSIO n_ monthl y_ graph. */
    SESSION_MONTHLY_GRAPH("/org/semanticwb/portal/admin/resources/reports/jrresources/templates/SessionsGraphRep.jasper"),
    
    /** The SESSIO n_ monthl y_ html. */
    SESSION_MONTHLY_HTML("/org/semanticwb/portal/admin/resources/reports/jrresources/templates/SessionsHtmlRep.jasper"),
    
    /** The USE r_ type s_ daily. */
    USER_TYPES_DAILY("/org/semanticwb/portal/admin/resources/reports/jrresources/templates/UserTypesDailyRep.jasper"),
    
    /** The USE r_ type s_ dail y_ graph. */
    USER_TYPES_DAILY_GRAPH("/org/semanticwb/portal/admin/resources/reports/jrresources/templates/UserTypesDailyGraphRep.jasper"),
    
    /** The USE r_ type s_ dail y_ html. */
    USER_TYPES_DAILY_HTML("/org/semanticwb/portal/admin/resources/reports/jrresources/templates/UserTypesDailyHtmlRep.jasper"),
    
    /** The USE r_ type s_ monthly. */
    USER_TYPES_MONTHLY("/org/semanticwb/portal/admin/resources/reports/jrresources/templates/UserTypesRep.jasper"),
    
    /** The USE r_ type s_ monthl y_ graph. */
    USER_TYPES_MONTHLY_GRAPH("/org/semanticwb/portal/admin/resources/reports/jrresources/templates/UserTypesGraphRep.jasper"),
    
    /** The USE r_ type s_ monthl y_ html. */
    USER_TYPES_MONTHLY_HTML("/org/semanticwb/portal/admin/resources/reports/jrresources/templates/UserTypesHtmlRep.jasper");
    
    /** The jasper file name. */
    private final String jasperFileName;
    
    /**
     * Instantiates a new jasper template.
     * 
     * @param jasperFileName the jasper file name
     */
    JasperTemplate(String jasperFileName){
        this.jasperFileName = jasperFileName;
    }   
    
    /**
     * Gets the template path.
     * 
     * @return the template path
     */
    public String getTemplatePath() {
        return jasperFileName;
    }
}
