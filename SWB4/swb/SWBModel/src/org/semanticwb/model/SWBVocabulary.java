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
    public final SemanticClass SystemPortlet;
    public final SemanticClass Calendar;
    public final SemanticClass Localeable;
    public final SemanticClass Community;
    public final SemanticClass Camp;
    public final SemanticClass Dns;
    public final SemanticClass UserRepository;
    public final SemanticClass ContentPortlet;
    public final SemanticClass Portletable;
    public final SemanticClass Templateable;
    public final SemanticClass TemplateRef;
    public final SemanticClass Template;
    public final SemanticClass Deleteable;
    public final SemanticClass Role;
    public final SemanticClass Priorityable;
    public final SemanticClass Reference;
    public final SemanticClass Roleable;
    public final SemanticClass VersionInfo;
    public final SemanticClass Portlet;
    public final SemanticClass RoleRefable;
    public final SemanticClass Descriptiveable;
    public final SemanticClass Ruleable;
    public final SemanticClass Versionable;
    public final SemanticClass RoleRef;
    public final SemanticClass PFlow;
    public final SemanticClass IPFilter;
    public final SemanticClass Rule;
    public final SemanticClass ApplicationPortlet;
    public final SemanticClass Statusable;
    public final SemanticClass RuleRefable;
    public final SemanticClass Valueable;
    public final SemanticClass WebPageable;
    public final SemanticClass WebPage;
    public final SemanticClass Calendarable;
    public final SemanticClass WebSite;
    public final SemanticClass ObjectGroup;
    public final SemanticClass Language;
    public final SemanticClass PortletRefable;
    public final SemanticClass Permission;
    public final SemanticClass PortletRef;
    public final SemanticClass TemplateRefable;
    public final SemanticClass PortletType;
    public final SemanticClass RuleRef;
    public final SemanticClass StrategyPortlet;
    public final SemanticClass Referensable;
    public final SemanticClass Device;
    public final SemanticClass Groupable;



    //Properties
    public final SemanticProperty hasRole;
    public final SemanticProperty hasGroup;
    public final SemanticProperty status;
    public final SemanticProperty hasReference;
    public final SemanticProperty hasRoleRef;
    public final SemanticProperty deleted;
    public final SemanticProperty created;
    public final SemanticProperty title;
    public final SemanticProperty userCreated;
    public final SemanticProperty language;
    public final SemanticProperty updated;
    public final SemanticProperty userModified;
    public final SemanticProperty hasRuleRef;
    public final SemanticProperty hasCalendar;
    public final SemanticProperty description;
    public final SemanticProperty priority;
    public final SemanticProperty hasRule;
    public final SemanticProperty value;
    public final SemanticProperty hasWebPage;
    public final SemanticProperty actualVersion;
    public final SemanticProperty lastVersion;
    public final SemanticProperty hasPortlet;
    public final SemanticProperty hasTemplate;
    public final SemanticProperty template;
    public final SemanticProperty nextVersion;
    public final SemanticProperty previousVersion;
    public final SemanticProperty versionComment;
    public final SemanticProperty versionCreated;
    public final SemanticProperty role;
    public final SemanticProperty hasPortletRef;
    public final SemanticProperty hasTemplateRef;
    public final SemanticProperty isChildOf;
    public final SemanticProperty homePage;
    public final SemanticProperty portlet;
    public final SemanticProperty rule;


    public SWBVocabulary()
    {

         SemanticVocabulary vocabulary=SWBInstance.getSemanticMgr().getVocabulary();
        // Classes
        User=vocabulary.getSemanticClass(URI+"User");
        SystemPortlet=vocabulary.getSemanticClass(URI+"SystemPortlet");
        Calendar=vocabulary.getSemanticClass(URI+"Calendar");
        Localeable=vocabulary.getSemanticClass(URI+"Localeable");
        Community=vocabulary.getSemanticClass(URI+"Community");
        Camp=vocabulary.getSemanticClass(URI+"Camp");
        Dns=vocabulary.getSemanticClass(URI+"Dns");
        UserRepository=vocabulary.getSemanticClass(URI+"UserRepository");
        ContentPortlet=vocabulary.getSemanticClass(URI+"ContentPortlet");
        Portletable=vocabulary.getSemanticClass(URI+"Portletable");
        Templateable=vocabulary.getSemanticClass(URI+"Templateable");
        TemplateRef=vocabulary.getSemanticClass(URI+"TemplateRef");
        Template=vocabulary.getSemanticClass(URI+"Template");
        Deleteable=vocabulary.getSemanticClass(URI+"Deleteable");
        Role=vocabulary.getSemanticClass(URI+"Role");
        Priorityable=vocabulary.getSemanticClass(URI+"Priorityable");
        Reference=vocabulary.getSemanticClass(URI+"Reference");
        Roleable=vocabulary.getSemanticClass(URI+"Roleable");
        VersionInfo=vocabulary.getSemanticClass(URI+"VersionInfo");
        Portlet=vocabulary.getSemanticClass(URI+"Portlet");
        RoleRefable=vocabulary.getSemanticClass(URI+"RoleRefable");
        Descriptiveable=vocabulary.getSemanticClass(URI+"Descriptiveable");
        Ruleable=vocabulary.getSemanticClass(URI+"Ruleable");
        Versionable=vocabulary.getSemanticClass(URI+"Versionable");
        RoleRef=vocabulary.getSemanticClass(URI+"RoleRef");
        PFlow=vocabulary.getSemanticClass(URI+"PFlow");
        IPFilter=vocabulary.getSemanticClass(URI+"IPFilter");
        Rule=vocabulary.getSemanticClass(URI+"Rule");
        ApplicationPortlet=vocabulary.getSemanticClass(URI+"ApplicationPortlet");
        Statusable=vocabulary.getSemanticClass(URI+"Statusable");
        RuleRefable=vocabulary.getSemanticClass(URI+"RuleRefable");
        Valueable=vocabulary.getSemanticClass(URI+"Valueable");
        WebPageable=vocabulary.getSemanticClass(URI+"WebPageable");
        WebPage=vocabulary.getSemanticClass(URI+"WebPage");
        Calendarable=vocabulary.getSemanticClass(URI+"Calendarable");
        WebSite=vocabulary.getSemanticClass(URI+"WebSite");
        ObjectGroup=vocabulary.getSemanticClass(URI+"ObjectGroup");
        Language=vocabulary.getSemanticClass(URI+"Language");
        PortletRefable=vocabulary.getSemanticClass(URI+"PortletRefable");
        Permission=vocabulary.getSemanticClass(URI+"Permission");
        PortletRef=vocabulary.getSemanticClass(URI+"PortletRef");
        TemplateRefable=vocabulary.getSemanticClass(URI+"TemplateRefable");
        PortletType=vocabulary.getSemanticClass(URI+"PortletType");
        RuleRef=vocabulary.getSemanticClass(URI+"RuleRef");
        StrategyPortlet=vocabulary.getSemanticClass(URI+"StrategyPortlet");
        Referensable=vocabulary.getSemanticClass(URI+"Referensable");
        Device=vocabulary.getSemanticClass(URI+"Device");
        Groupable=vocabulary.getSemanticClass(URI+"Groupable");



        //Properties
        hasRole=vocabulary.getSemanticProperty(URI+"hasRole");
        hasGroup=vocabulary.getSemanticProperty(URI+"hasGroup");
        status=vocabulary.getSemanticProperty(URI+"status");
        hasReference=vocabulary.getSemanticProperty(URI+"hasReference");
        hasRoleRef=vocabulary.getSemanticProperty(URI+"hasRoleRef");
        deleted=vocabulary.getSemanticProperty(URI+"deleted");
        created=vocabulary.getSemanticProperty(URI+"created");
        title=vocabulary.getSemanticProperty(URI+"title");
        userCreated=vocabulary.getSemanticProperty(URI+"userCreated");
        language=vocabulary.getSemanticProperty(URI+"language");
        updated=vocabulary.getSemanticProperty(URI+"updated");
        userModified=vocabulary.getSemanticProperty(URI+"userModified");
        hasRuleRef=vocabulary.getSemanticProperty(URI+"hasRuleRef");
        hasCalendar=vocabulary.getSemanticProperty(URI+"hasCalendar");
        description=vocabulary.getSemanticProperty(URI+"description");
        priority=vocabulary.getSemanticProperty(URI+"priority");
        hasRule=vocabulary.getSemanticProperty(URI+"hasRule");
        value=vocabulary.getSemanticProperty(URI+"value");
        hasWebPage=vocabulary.getSemanticProperty(URI+"hasWebPage");
        actualVersion=vocabulary.getSemanticProperty(URI+"actualVersion");
        lastVersion=vocabulary.getSemanticProperty(URI+"lastVersion");
        hasPortlet=vocabulary.getSemanticProperty(URI+"hasPortlet");
        hasTemplate=vocabulary.getSemanticProperty(URI+"hasTemplate");
        template=vocabulary.getSemanticProperty(URI+"template");
        nextVersion=vocabulary.getSemanticProperty(URI+"nextVersion");
        previousVersion=vocabulary.getSemanticProperty(URI+"previousVersion");
        versionComment=vocabulary.getSemanticProperty(URI+"versionComment");
        versionCreated=vocabulary.getSemanticProperty(URI+"versionCreated");
        role=vocabulary.getSemanticProperty(URI+"role");
        hasPortletRef=vocabulary.getSemanticProperty(URI+"hasPortletRef");
        hasTemplateRef=vocabulary.getSemanticProperty(URI+"hasTemplateRef");
        isChildOf=vocabulary.getSemanticProperty(URI+"isChildOf");
        homePage=vocabulary.getSemanticProperty(URI+"homePage");
        portlet=vocabulary.getSemanticProperty(URI+"portlet");
        rule=vocabulary.getSemanticProperty(URI+"rule");
    }
}
