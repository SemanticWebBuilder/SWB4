
package org.semanticwb.portal.admin.resources.reports.jrresources;

public enum JasperTemplate {
    RES_APPEARED("templates/AppearedResRep.jasper"),
    RES_APPEARED_HTML("templates/AppearedResHtmlRep.jasper"),
    GLOBAL_DAILY("templates/GlobalDailyRep.jasper"),
    GLOBAL_DAILY_GRAPH("templates/GlobalDailyGraphRep.jasper"),
    GLOBAL_DAILY_HTML("templates/GlobalDailyHtmlRep.jasper"),
    GLOBAL_MONTHLY("templates/GlobalRep.jasper"),
    GLOBAL_MONTHLY_GRAPH("templates/GlobalGraphRep.jasper"),
    GLOBAL_MONTHLY_HTML("templates/GlobalHtmlRep.jasper"),
    DEVICE_DAILY("templates/DevicesDailyRep.jasper"),
    DEVICE_DAILY_GRAPH("templates/DevicesDailyGraphRep.jasper"),
    DEVICE_DAILY_HTML("templates/DevicesDailyHtmlRep.jasper"),
    DEVICE_MONTHLY("templates/DevicesRep.jasper"),
    DEVICE_MONTHLY_GRAPH("templates/DevicesGraphRep.jasper"),
    DEVICE_MONTHLY_HTML("templates/DevicesHtmlRep.jasper"),
    LANGUAGE_DAILY("templates/LanguageDailyRep.jasper"),
    LANGUAGE_DAILY_GRAPH("templates/LanguageDailyGraphRep.jasper"),
    LANGUAGE_DAILY_HTML("templates/LanguageDailyHtmlRep.jasper"),
    LANGUAGE_MONTHLY("templates/LanguagesRep.jasper"),
    LANGUAGE_MONTHLY_GRAPH("templates/LanguagesGraphRep.jasper"),
    LANGUAGE_MONTHLY_HTML("templates/LanguagesHtmlRep.jasper"),
    LOGGED_UNIQUE_DAILY("templates/UniqueUsersDailyRep.jasper"),
    LOGGED_UNIQUE_DAILY_GRAPH("templates/UniqueUsersDailyGraphRep.jasper"),
    LOGGED_UNIQUE_DAILY_HTML("templates/UniqueUsersDailyHtmlRep.jasper"),
    LOGGED_UNIQUE_MONTHLY("templates/UniqueUsersRep.jasper"),
    LOGGED_UNIQUE_MONTHLY_GRAPH("templates/UniqueUsersGraphRep.jasper"),
    LOGGED_UNIQUE_MONTHLY_HTML("templates/UniqueUsersHtmlRep.jasper"),
    USER_LOGGED_DAILY("templates/UsersDailyRep.jasper"),
    USER_LOGGED_DAILY_GRAPH("templates/UsersDailyGraphRep.jasper"),
    USER_LOGGED_DAILY_HTML("templates/UsersDailyHtmlRep.jasper"),
    USER_LOGGED_MONTHLY("templates/UsersRep.jasper"),
    USER_LOGGED_MONTHLY_GRAPH("templates/UsersGraphRep.jasper"),
    USER_LOGGED_MONTHLY_HTML("templates/UsersHtmlRep.jasper"),
    SECTION_DAILY("templates/SectionsDailyRep.jasper"),
    SECTION_DAILY_GRAPH("templates/SectionsDailyGraphRep.jasper"),
    SECTION_DAILY_HTML("templates/SectionsDailyHtmlRep.jasper"),
    SECTION_MONTHLY("templates/SectionsRep.jasper"),
    SECTION_MONTHLY_GRAPH("templates/SectionsGraphRep.jasper"),
    SECTION_MONTHLY_HTML("templates/SectionsHtmlRep.jasper"),    
    RESOURCE_DAILY("templates/ResourcesDailyRep.jasper"),
    RESOURCE_DAILY_GRAPH("templates/ResourcesDailyGraphRep.jasper"),
    RESOURCE_DAILY_HTML("templates/ResourcesDailyHtmlRep.jasper"),
    RESOURCE_MONTHLY("templates/ResourcesRep.jasper"),
    RESOURCE_MONTHLY_GRAPH("templates/ResourcesGraphRep.jasper"),
    RESOURCE_MONTHLY_HTML("templates/ResourcesHtmlRep.jasper"),    
    SESSION_DAILY("templates/SessionsDailyRep.jasper"),
    SESSION_DAILY_GRAPH("templates/SessionsDailyGraphRep.jasper"),
    SESSION_DAILY_HTML("templates/SessionsDailyHtmlRep.jasper"),
    SESSION_MONTHLY("templates/SessionsRep.jasper"),
    SESSION_MONTHLY_GRAPH("templates/SessionsGraphRep.jasper"),
    SESSION_MONTHLY_HTML("templates/SessionsHtmlRep.jasper"),
    USER_TYPES_DAILY("templates/UserTypesDailyRep.jasper"),
    USER_TYPES_DAILY_GRAPH("templates/UserTypesDailyGraphRep.jasper"),
    USER_TYPES_DAILY_HTML("templates/UserTypesDailyHtmlRep.jasper"),
    USER_TYPES_MONTHLY("templates/UserTypesRep.jasper"),
    USER_TYPES_MONTHLY_GRAPH("templates/UserTypesGraphRep.jasper"),
    USER_TYPES_MONTHLY_HTML("templates/UserTypesHtmlRep.jasper");
    
    private final String jasperFileName;
    
    JasperTemplate(String jasperFileName){
        this.jasperFileName = jasperFileName;
    }   
    
    public String getTemplatePath() {
        return jasperFileName;
    }
}
