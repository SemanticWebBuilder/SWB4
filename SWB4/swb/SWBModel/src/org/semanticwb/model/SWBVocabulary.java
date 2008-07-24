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
    public final SemanticClass Resourceable;
    public final SemanticClass Community;
    public final SemanticClass TemplateRef;
    public final SemanticClass Templateable;
    public final SemanticClass Deleteable;
    public final SemanticClass Content;
    public final SemanticClass Reference;
    public final SemanticClass Roleable;
    public final SemanticClass HomePage;
    public final SemanticClass RoleRefable;
    public final SemanticClass Ruleable;
    public final SemanticClass PFlow;
    public final SemanticClass SWBModel;
    public final SemanticClass RuleRefable;
    public final SemanticClass Valueable;
    public final SemanticClass Calendarable;
    public final SemanticClass ipFilter;
    public final SemanticClass Resource;
    public final SemanticClass Permission;
    public final SemanticClass TemplateRefable;
    public final SemanticClass WebSiteable;
    public final SemanticClass RuleRef;
    public final SemanticClass Referensable;
    public final SemanticClass Groupable;
    public final SemanticClass Device;
    public final SemanticClass ResourceType;
    public final SemanticClass Localeable;
    public final SemanticClass Application;
    public final SemanticClass Camp;
    public final SemanticClass Dns;
    public final SemanticClass UserRepository;
    public final SemanticClass Template;
    public final SemanticClass Priorityable;
    public final SemanticClass Role;
    public final SemanticClass VersionInfo;
    public final SemanticClass ResourceRef;
    public final SemanticClass Descriptiveable;
    public final SemanticClass Versionable;
    public final SemanticClass RoleRef;
    public final SemanticClass SWBClass;
    public final SemanticClass System;
    public final SemanticClass Rule;
    public final SemanticClass Statusable;
    public final SemanticClass WebPage;
    public final SemanticClass WebPageable;
    public final SemanticClass WebSite;
    public final SemanticClass ObjectGroup;
    public final SemanticClass Language;
    public final SemanticClass SWBInterface;
    public final SemanticClass Strategy;
    public final SemanticClass ResourceRefable;



    //Properties
    public final SemanticProperty hasUserReposotory;
    public final SemanticProperty hasRole;
    public final SemanticProperty hasGroup;
    public final SemanticProperty status;
    public final SemanticProperty hasReference;
    public final SemanticProperty created;
    public final SemanticProperty userModified;
    public final SemanticProperty useWebSite;
    public final SemanticProperty title;
    public final SemanticProperty description;
    public final SemanticProperty userCreated;
    public final SemanticProperty updated;
    public final SemanticProperty hasResource;
    public final SemanticProperty hasTemplate;
    public final SemanticProperty priority;
    public final SemanticProperty deleted;
    public final SemanticProperty hasRoleRef;
    public final SemanticProperty useLanguage;
    public final SemanticProperty actualVersion;
    public final SemanticProperty lastVersion;
    public final SemanticProperty hasRuleRef;
    public final SemanticProperty hasCalendar;
    public final SemanticProperty hasTemplateRef;
    public final SemanticProperty hasResourceRef;
    public final SemanticProperty isChildOf;
    public final SemanticProperty hasRule;
    public final SemanticProperty value;
    public final SemanticProperty hasWebPage;
    public final SemanticProperty nextVersion;
    public final SemanticProperty versionComment;
    public final SemanticProperty versionCreated;
    public final SemanticProperty hasHome;


    public SWBVocabulary()
    {

         SemanticVocabulary vocabulary=SWBInstance.getSemanticMgr().getVocabulary();
        // Classes
        User=vocabulary.getSemanticClass(URI+"User");
        Calendar=vocabulary.getSemanticClass(URI+"Calendar");
        Resourceable=vocabulary.getSemanticClass(URI+"Resourceable");
        Community=vocabulary.getSemanticClass(URI+"Community");
        TemplateRef=vocabulary.getSemanticClass(URI+"TemplateRef");
        Templateable=vocabulary.getSemanticClass(URI+"Templateable");
        Deleteable=vocabulary.getSemanticClass(URI+"Deleteable");
        Content=vocabulary.getSemanticClass(URI+"Content");
        Reference=vocabulary.getSemanticClass(URI+"Reference");
        Roleable=vocabulary.getSemanticClass(URI+"Roleable");
        HomePage=vocabulary.getSemanticClass(URI+"HomePage");
        RoleRefable=vocabulary.getSemanticClass(URI+"RoleRefable");
        Ruleable=vocabulary.getSemanticClass(URI+"Ruleable");
        PFlow=vocabulary.getSemanticClass(URI+"PFlow");
        SWBModel=vocabulary.getSemanticClass(URI+"SWBModel");
        RuleRefable=vocabulary.getSemanticClass(URI+"RuleRefable");
        Valueable=vocabulary.getSemanticClass(URI+"Valueable");
        Calendarable=vocabulary.getSemanticClass(URI+"Calendarable");
        ipFilter=vocabulary.getSemanticClass(URI+"ipFilter");
        Resource=vocabulary.getSemanticClass(URI+"Resource");
        Permission=vocabulary.getSemanticClass(URI+"Permission");
        TemplateRefable=vocabulary.getSemanticClass(URI+"TemplateRefable");
        WebSiteable=vocabulary.getSemanticClass(URI+"WebSiteable");
        RuleRef=vocabulary.getSemanticClass(URI+"RuleRef");
        Referensable=vocabulary.getSemanticClass(URI+"Referensable");
        Groupable=vocabulary.getSemanticClass(URI+"Groupable");
        Device=vocabulary.getSemanticClass(URI+"Device");
        ResourceType=vocabulary.getSemanticClass(URI+"ResourceType");
        Localeable=vocabulary.getSemanticClass(URI+"Localeable");
        Application=vocabulary.getSemanticClass(URI+"Application");
        Camp=vocabulary.getSemanticClass(URI+"Camp");
        Dns=vocabulary.getSemanticClass(URI+"Dns");
        UserRepository=vocabulary.getSemanticClass(URI+"UserRepository");
        Template=vocabulary.getSemanticClass(URI+"Template");
        Priorityable=vocabulary.getSemanticClass(URI+"Priorityable");
        Role=vocabulary.getSemanticClass(URI+"Role");
        VersionInfo=vocabulary.getSemanticClass(URI+"VersionInfo");
        ResourceRef=vocabulary.getSemanticClass(URI+"ResourceRef");
        Descriptiveable=vocabulary.getSemanticClass(URI+"Descriptiveable");
        Versionable=vocabulary.getSemanticClass(URI+"Versionable");
        RoleRef=vocabulary.getSemanticClass(URI+"RoleRef");
        SWBClass=vocabulary.getSemanticClass(URI+"SWBClass");
        System=vocabulary.getSemanticClass(URI+"System");
        Rule=vocabulary.getSemanticClass(URI+"Rule");
        Statusable=vocabulary.getSemanticClass(URI+"Statusable");
        WebPage=vocabulary.getSemanticClass(URI+"WebPage");
        WebPageable=vocabulary.getSemanticClass(URI+"WebPageable");
        WebSite=vocabulary.getSemanticClass(URI+"WebSite");
        ObjectGroup=vocabulary.getSemanticClass(URI+"ObjectGroup");
        Language=vocabulary.getSemanticClass(URI+"Language");
        SWBInterface=vocabulary.getSemanticClass(URI+"SWBInterface");
        Strategy=vocabulary.getSemanticClass(URI+"Strategy");
        ResourceRefable=vocabulary.getSemanticClass(URI+"ResourceRefable");



        //Properties
        hasUserReposotory=vocabulary.getSemanticProperty(URI+"hasUserReposotory");
        hasRole=vocabulary.getSemanticProperty(URI+"hasRole");
        hasGroup=vocabulary.getSemanticProperty(URI+"hasGroup");
        status=vocabulary.getSemanticProperty(URI+"status");
        hasReference=vocabulary.getSemanticProperty(URI+"hasReference");
        created=vocabulary.getSemanticProperty(URI+"created");
        userModified=vocabulary.getSemanticProperty(URI+"userModified");
        useWebSite=vocabulary.getSemanticProperty(URI+"useWebSite");
        title=vocabulary.getSemanticProperty(URI+"title");
        description=vocabulary.getSemanticProperty(URI+"description");
        userCreated=vocabulary.getSemanticProperty(URI+"userCreated");
        updated=vocabulary.getSemanticProperty(URI+"updated");
        hasResource=vocabulary.getSemanticProperty(URI+"hasResource");
        hasTemplate=vocabulary.getSemanticProperty(URI+"hasTemplate");
        priority=vocabulary.getSemanticProperty(URI+"priority");
        deleted=vocabulary.getSemanticProperty(URI+"deleted");
        hasRoleRef=vocabulary.getSemanticProperty(URI+"hasRoleRef");
        useLanguage=vocabulary.getSemanticProperty(URI+"useLanguage");
        actualVersion=vocabulary.getSemanticProperty(URI+"actualVersion");
        lastVersion=vocabulary.getSemanticProperty(URI+"lastVersion");
        hasRuleRef=vocabulary.getSemanticProperty(URI+"hasRuleRef");
        hasCalendar=vocabulary.getSemanticProperty(URI+"hasCalendar");
        hasTemplateRef=vocabulary.getSemanticProperty(URI+"hasTemplateRef");
        hasResourceRef=vocabulary.getSemanticProperty(URI+"hasResourceRef");
        isChildOf=vocabulary.getSemanticProperty(URI+"isChildOf");
        hasRule=vocabulary.getSemanticProperty(URI+"hasRule");
        value=vocabulary.getSemanticProperty(URI+"value");
        hasWebPage=vocabulary.getSemanticProperty(URI+"hasWebPage");
        nextVersion=vocabulary.getSemanticProperty(URI+"nextVersion");
        versionComment=vocabulary.getSemanticProperty(URI+"versionComment");
        versionCreated=vocabulary.getSemanticProperty(URI+"versionCreated");
        hasHome=vocabulary.getSemanticProperty(URI+"hasHome");
    }
}
