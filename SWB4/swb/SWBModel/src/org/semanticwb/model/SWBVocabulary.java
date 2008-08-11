package org.semanticwb.model;

import org.semanticwb.SWBInstance;
import org.semanticwb.platform.SemanticVocabulary;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticProperty;
import static org.semanticwb.platform.SemanticVocabulary.URI;

public class SWBVocabulary
{


    //Classes
    public final SemanticClass User;
    public final SemanticClass Calendar;
    public final SemanticClass Community;
    public final SemanticClass ContentPortlet;
    public final SemanticClass TemplateRef;
    public final SemanticClass Templateable;
    public final SemanticClass Deleteable;
    public final SemanticClass Reference;
    public final SemanticClass Roleable;
    public final SemanticClass HomePage;
    public final SemanticClass RoleRefable;
    public final SemanticClass Ruleable;
    public final SemanticClass IPFilter;
    public final SemanticClass PFlow;
    public final SemanticClass ApplicationPortlet;
    public final SemanticClass RuleRefable;
    public final SemanticClass Valueable;
    public final SemanticClass Calendarable;
    public final SemanticClass PortletRefable;
    public final SemanticClass Permission;
    public final SemanticClass TemplateRefable;
    public final SemanticClass WebSiteable;
    public final SemanticClass RuleRef;
    public final SemanticClass StrategyPortlet;
    public final SemanticClass Referensable;
    public final SemanticClass Groupable;
    public final SemanticClass Device;
    public final SemanticClass SystemPortlet;
    public final SemanticClass Localeable;
    public final SemanticClass Camp;
    public final SemanticClass Dns;
    public final SemanticClass Portletable;
    public final SemanticClass UserRepository;
    public final SemanticClass Template;
    public final SemanticClass Priorityable;
    public final SemanticClass Role;
    public final SemanticClass VersionInfo;
    public final SemanticClass Portlet;
    public final SemanticClass Descriptiveable;
    public final SemanticClass Versionable;
    public final SemanticClass RoleRef;
    public final SemanticClass Rule;
    public final SemanticClass Statusable;
    public final SemanticClass WebPage;
    public final SemanticClass WebPageable;
    public final SemanticClass WebSite;
    public final SemanticClass ObjectGroup;
    public final SemanticClass Language;
    public final SemanticClass PortletRef;
    public final SemanticClass PortletType;



    //Properties
    public final SemanticProperty userReposotory;
    public final SemanticProperty hasRole;
    public final SemanticProperty hasGroup;
    public final SemanticProperty status;
    public final SemanticProperty hasReference;
    public final SemanticProperty created;
    public final SemanticProperty userModified;
    public final SemanticProperty title;
    public final SemanticProperty webSite;
    public final SemanticProperty description;
    public final SemanticProperty userCreated;
    public final SemanticProperty updated;
    public final SemanticProperty hasRoleRef;
    public final SemanticProperty deleted;
    public final SemanticProperty actualVersion;
    public final SemanticProperty language;
    public final SemanticProperty lastVersion;
    public final SemanticProperty hasRuleRef;
    public final SemanticProperty hasCalendar;
    public final SemanticProperty priority;
    public final SemanticProperty template;
    public final SemanticProperty hasTemplate;
    public final SemanticProperty hasPortletRef;
    public final SemanticProperty hasTemplateRef;
    public final SemanticProperty isChildOf;
    public final SemanticProperty hasRule;
    public final SemanticProperty value;
    public final SemanticProperty rule;
    public final SemanticProperty hasWebPage;
    public final SemanticProperty hasPortlet;
    public final SemanticProperty nextVersion;
    public final SemanticProperty previousVersion;
    public final SemanticProperty versionComment;
    public final SemanticProperty versionCreated;
    public final SemanticProperty role;
    public final SemanticProperty hasHome;
    public final SemanticProperty portlet;


    public SWBVocabulary()
    {

         SemanticVocabulary vocabulary=SWBInstance.getSemanticMgr().getVocabulary();
        // Classes
        User=vocabulary.getSemanticClass(URI+"User");
        Calendar=vocabulary.getSemanticClass(URI+"Calendar");
        Community=vocabulary.getSemanticClass(URI+"Community");
        ContentPortlet=vocabulary.getSemanticClass(URI+"ContentPortlet");
        TemplateRef=vocabulary.getSemanticClass(URI+"TemplateRef");
        Templateable=vocabulary.getSemanticClass(URI+"Templateable");
        Deleteable=vocabulary.getSemanticClass(URI+"Deleteable");
        Reference=vocabulary.getSemanticClass(URI+"Reference");
        Roleable=vocabulary.getSemanticClass(URI+"Roleable");
        HomePage=vocabulary.getSemanticClass(URI+"HomePage");
        RoleRefable=vocabulary.getSemanticClass(URI+"RoleRefable");
        Ruleable=vocabulary.getSemanticClass(URI+"Ruleable");
        IPFilter=vocabulary.getSemanticClass(URI+"IPFilter");
        PFlow=vocabulary.getSemanticClass(URI+"PFlow");
        ApplicationPortlet=vocabulary.getSemanticClass(URI+"ApplicationPortlet");
        RuleRefable=vocabulary.getSemanticClass(URI+"RuleRefable");
        Valueable=vocabulary.getSemanticClass(URI+"Valueable");
        Calendarable=vocabulary.getSemanticClass(URI+"Calendarable");
        PortletRefable=vocabulary.getSemanticClass(URI+"PortletRefable");
        Permission=vocabulary.getSemanticClass(URI+"Permission");
        TemplateRefable=vocabulary.getSemanticClass(URI+"TemplateRefable");
        WebSiteable=vocabulary.getSemanticClass(URI+"WebSiteable");
        RuleRef=vocabulary.getSemanticClass(URI+"RuleRef");
        StrategyPortlet=vocabulary.getSemanticClass(URI+"StrategyPortlet");
        Referensable=vocabulary.getSemanticClass(URI+"Referensable");
        Groupable=vocabulary.getSemanticClass(URI+"Groupable");
        Device=vocabulary.getSemanticClass(URI+"Device");
        SystemPortlet=vocabulary.getSemanticClass(URI+"SystemPortlet");
        Localeable=vocabulary.getSemanticClass(URI+"Localeable");
        Camp=vocabulary.getSemanticClass(URI+"Camp");
        Dns=vocabulary.getSemanticClass(URI+"Dns");
        Portletable=vocabulary.getSemanticClass(URI+"Portletable");
        UserRepository=vocabulary.getSemanticClass(URI+"UserRepository");
        Template=vocabulary.getSemanticClass(URI+"Template");
        Priorityable=vocabulary.getSemanticClass(URI+"Priorityable");
        Role=vocabulary.getSemanticClass(URI+"Role");
        VersionInfo=vocabulary.getSemanticClass(URI+"VersionInfo");
        Portlet=vocabulary.getSemanticClass(URI+"Portlet");
        Descriptiveable=vocabulary.getSemanticClass(URI+"Descriptiveable");
        Versionable=vocabulary.getSemanticClass(URI+"Versionable");
        RoleRef=vocabulary.getSemanticClass(URI+"RoleRef");
        Rule=vocabulary.getSemanticClass(URI+"Rule");
        Statusable=vocabulary.getSemanticClass(URI+"Statusable");
        WebPage=vocabulary.getSemanticClass(URI+"WebPage");
        WebPageable=vocabulary.getSemanticClass(URI+"WebPageable");
        WebSite=vocabulary.getSemanticClass(URI+"WebSite");
        ObjectGroup=vocabulary.getSemanticClass(URI+"ObjectGroup");
        Language=vocabulary.getSemanticClass(URI+"Language");
        PortletRef=vocabulary.getSemanticClass(URI+"PortletRef");
        PortletType=vocabulary.getSemanticClass(URI+"PortletType");



        //Properties
        userReposotory=vocabulary.getSemanticProperty(URI+"userReposotory");
        hasRole=vocabulary.getSemanticProperty(URI+"hasRole");
        hasGroup=vocabulary.getSemanticProperty(URI+"hasGroup");
        status=vocabulary.getSemanticProperty(URI+"status");
        hasReference=vocabulary.getSemanticProperty(URI+"hasReference");
        created=vocabulary.getSemanticProperty(URI+"created");
        userModified=vocabulary.getSemanticProperty(URI+"userModified");
        title=vocabulary.getSemanticProperty(URI+"title");
        webSite=vocabulary.getSemanticProperty(URI+"webSite");
        description=vocabulary.getSemanticProperty(URI+"description");
        userCreated=vocabulary.getSemanticProperty(URI+"userCreated");
        updated=vocabulary.getSemanticProperty(URI+"updated");
        hasRoleRef=vocabulary.getSemanticProperty(URI+"hasRoleRef");
        deleted=vocabulary.getSemanticProperty(URI+"deleted");
        actualVersion=vocabulary.getSemanticProperty(URI+"actualVersion");
        language=vocabulary.getSemanticProperty(URI+"language");
        lastVersion=vocabulary.getSemanticProperty(URI+"lastVersion");
        hasRuleRef=vocabulary.getSemanticProperty(URI+"hasRuleRef");
        hasCalendar=vocabulary.getSemanticProperty(URI+"hasCalendar");
        priority=vocabulary.getSemanticProperty(URI+"priority");
        template=vocabulary.getSemanticProperty(URI+"template");
        hasTemplate=vocabulary.getSemanticProperty(URI+"hasTemplate");
        hasPortletRef=vocabulary.getSemanticProperty(URI+"hasPortletRef");
        hasTemplateRef=vocabulary.getSemanticProperty(URI+"hasTemplateRef");
        isChildOf=vocabulary.getSemanticProperty(URI+"isChildOf");
        hasRule=vocabulary.getSemanticProperty(URI+"hasRule");
        value=vocabulary.getSemanticProperty(URI+"value");
        rule=vocabulary.getSemanticProperty(URI+"rule");
        hasWebPage=vocabulary.getSemanticProperty(URI+"hasWebPage");
        hasPortlet=vocabulary.getSemanticProperty(URI+"hasPortlet");
        nextVersion=vocabulary.getSemanticProperty(URI+"nextVersion");
        previousVersion=vocabulary.getSemanticProperty(URI+"previousVersion");
        versionComment=vocabulary.getSemanticProperty(URI+"versionComment");
        versionCreated=vocabulary.getSemanticProperty(URI+"versionCreated");
        role=vocabulary.getSemanticProperty(URI+"role");
        hasHome=vocabulary.getSemanticProperty(URI+"hasHome");
        portlet=vocabulary.getSemanticProperty(URI+"portlet");
    }
}
