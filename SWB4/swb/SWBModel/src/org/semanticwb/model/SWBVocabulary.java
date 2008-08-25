package org.semanticwb.model;

import org.semanticwb.SWBPlatform;
import org.semanticwb.platform.SemanticVocabulary;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticProperty;
import static org.semanticwb.platform.SemanticVocabulary.URI;

public class SWBVocabulary
{


    //Classes
    public final SemanticClass User;
    public final SemanticClass Calendar;
    public final SemanticClass Localeable;
    public final SemanticClass Community;
    public final SemanticClass Camp;
    public final SemanticClass Dns;
    public final SemanticClass UserRepository;
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
    public final SemanticClass Traceable;
    public final SemanticClass Portlet;
    public final SemanticClass RoleRefable;
    public final SemanticClass Descriptiveable;
    public final SemanticClass Ruleable;
    public final SemanticClass Versionable;
    public final SemanticClass RoleRef;
    public final SemanticClass IPFilter;
    public final SemanticClass PFlow;
    public final SemanticClass Rule;
    public final SemanticClass Statusable;
    public final SemanticClass RuleRefable;
    public final SemanticClass Valueable;
    public final SemanticClass WebPageable;
    public final SemanticClass WebPage;
    public final SemanticClass Calendarable;
    public final SemanticClass WebSite;
    public final SemanticClass ObjectGroup;
    public final SemanticClass PortletClass;
    public final SemanticClass Language;
    public final SemanticClass PortletRefable;
    public final SemanticClass Permission;
    public final SemanticClass PortletRef;
    public final SemanticClass TemplateRefable;
    public final SemanticClass PFlowRef;
    public final SemanticClass RuleRef;
    public final SemanticClass Referensable;
    public final SemanticClass Device;
    public final SemanticClass Groupable;



    //Properties
    public final SemanticProperty created;
    public final SemanticProperty modifiedBy;
    public final SemanticProperty usrSecondLastName;
    public final SemanticProperty usrEmail;
    public final SemanticProperty status;
    public final SemanticProperty usrFirstName;
    public final SemanticProperty language;
    public final SemanticProperty updated;
    public final SemanticProperty usrLastLogin;
    public final SemanticProperty hasReference;
    public final SemanticProperty creator;
    public final SemanticProperty usrLogin;
    public final SemanticProperty usrPassword;
    public final SemanticProperty usrLastName;
    public final SemanticProperty usrPasswordChanged;
    public final SemanticProperty hasRole;
    public final SemanticProperty usrSecurityQuestion;
    public final SemanticProperty hasGroup;
    public final SemanticProperty usrSecurityAnswer;
    public final SemanticProperty title;
    public final SemanticProperty description;
    public final SemanticProperty deleted;
    public final SemanticProperty hasCalendar;
    public final SemanticProperty hasRule;
    public final SemanticProperty value;
    public final SemanticProperty hasWebPage;
    public final SemanticProperty hasPortlet;
    public final SemanticProperty hasTemplate;
    public final SemanticProperty template;
    public final SemanticProperty priority;
    public final SemanticProperty hasRoleRef;
    public final SemanticProperty actualVersion;
    public final SemanticProperty lastVersion;
    public final SemanticProperty hasRuleRef;
    public final SemanticProperty nextVersion;
    public final SemanticProperty previousVersion;
    public final SemanticProperty versionComment;
    public final SemanticProperty versionCreated;
    public final SemanticProperty camp;
    public final SemanticProperty portletClass;
    public final SemanticProperty portletType;
    public final SemanticProperty role;
    public final SemanticProperty hasPortletRef;
    public final SemanticProperty hasTemplateRef;
    public final SemanticProperty hasPFlowRef;
    public final SemanticProperty isChildOf;
    public final SemanticProperty homePage;
    public final SemanticProperty userRepository;
    public final SemanticProperty portlet;
    public final SemanticProperty pflow;
    public final SemanticProperty rule;


    public SWBVocabulary()
    {

         SemanticVocabulary vocabulary=SWBPlatform.getSemanticMgr().getVocabulary();
        // Classes
        User=vocabulary.getSemanticClass(URI+"User");
        Calendar=vocabulary.getSemanticClass(URI+"Calendar");
        Localeable=vocabulary.getSemanticClass(URI+"Localeable");
        Community=vocabulary.getSemanticClass(URI+"Community");
        Camp=vocabulary.getSemanticClass(URI+"Camp");
        Dns=vocabulary.getSemanticClass(URI+"Dns");
        UserRepository=vocabulary.getSemanticClass(URI+"UserRepository");
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
        Traceable=vocabulary.getSemanticClass(URI+"Traceable");
        Portlet=vocabulary.getSemanticClass(URI+"Portlet");
        RoleRefable=vocabulary.getSemanticClass(URI+"RoleRefable");
        Descriptiveable=vocabulary.getSemanticClass(URI+"Descriptiveable");
        Ruleable=vocabulary.getSemanticClass(URI+"Ruleable");
        Versionable=vocabulary.getSemanticClass(URI+"Versionable");
        RoleRef=vocabulary.getSemanticClass(URI+"RoleRef");
        IPFilter=vocabulary.getSemanticClass(URI+"IPFilter");
        PFlow=vocabulary.getSemanticClass(URI+"PFlow");
        Rule=vocabulary.getSemanticClass(URI+"Rule");
        Statusable=vocabulary.getSemanticClass(URI+"Statusable");
        RuleRefable=vocabulary.getSemanticClass(URI+"RuleRefable");
        Valueable=vocabulary.getSemanticClass(URI+"Valueable");
        WebPageable=vocabulary.getSemanticClass(URI+"WebPageable");
        WebPage=vocabulary.getSemanticClass(URI+"WebPage");
        Calendarable=vocabulary.getSemanticClass(URI+"Calendarable");
        WebSite=vocabulary.getSemanticClass(URI+"WebSite");
        ObjectGroup=vocabulary.getSemanticClass(URI+"ObjectGroup");
        PortletClass=vocabulary.getSemanticClass(URI+"PortletClass");
        Language=vocabulary.getSemanticClass(URI+"Language");
        PortletRefable=vocabulary.getSemanticClass(URI+"PortletRefable");
        Permission=vocabulary.getSemanticClass(URI+"Permission");
        PortletRef=vocabulary.getSemanticClass(URI+"PortletRef");
        TemplateRefable=vocabulary.getSemanticClass(URI+"TemplateRefable");
        PFlowRef=vocabulary.getSemanticClass(URI+"PFlowRef");
        RuleRef=vocabulary.getSemanticClass(URI+"RuleRef");
        Referensable=vocabulary.getSemanticClass(URI+"Referensable");
        Device=vocabulary.getSemanticClass(URI+"Device");
        Groupable=vocabulary.getSemanticClass(URI+"Groupable");



        //Properties
        created=vocabulary.getSemanticProperty(URI+"created");
        modifiedBy=vocabulary.getSemanticProperty(URI+"modifiedBy");
        usrSecondLastName=vocabulary.getSemanticProperty(URI+"usrSecondLastName");
        usrEmail=vocabulary.getSemanticProperty(URI+"usrEmail");
        status=vocabulary.getSemanticProperty(URI+"status");
        usrFirstName=vocabulary.getSemanticProperty(URI+"usrFirstName");
        language=vocabulary.getSemanticProperty(URI+"language");
        updated=vocabulary.getSemanticProperty(URI+"updated");
        usrLastLogin=vocabulary.getSemanticProperty(URI+"usrLastLogin");
        hasReference=vocabulary.getSemanticProperty(URI+"hasReference");
        creator=vocabulary.getSemanticProperty(URI+"creator");
        usrLogin=vocabulary.getSemanticProperty(URI+"usrLogin");
        usrPassword=vocabulary.getSemanticProperty(URI+"usrPassword");
        usrLastName=vocabulary.getSemanticProperty(URI+"usrLastName");
        usrPasswordChanged=vocabulary.getSemanticProperty(URI+"usrPasswordChanged");
        hasRole=vocabulary.getSemanticProperty(URI+"hasRole");
        usrSecurityQuestion=vocabulary.getSemanticProperty(URI+"usrSecurityQuestion");
        hasGroup=vocabulary.getSemanticProperty(URI+"hasGroup");
        usrSecurityAnswer=vocabulary.getSemanticProperty(URI+"usrSecurityAnswer");
        title=vocabulary.getSemanticProperty(URI+"title");
        description=vocabulary.getSemanticProperty(URI+"description");
        deleted=vocabulary.getSemanticProperty(URI+"deleted");
        hasCalendar=vocabulary.getSemanticProperty(URI+"hasCalendar");
        hasRule=vocabulary.getSemanticProperty(URI+"hasRule");
        value=vocabulary.getSemanticProperty(URI+"value");
        hasWebPage=vocabulary.getSemanticProperty(URI+"hasWebPage");
        hasPortlet=vocabulary.getSemanticProperty(URI+"hasPortlet");
        hasTemplate=vocabulary.getSemanticProperty(URI+"hasTemplate");
        template=vocabulary.getSemanticProperty(URI+"template");
        priority=vocabulary.getSemanticProperty(URI+"priority");
        hasRoleRef=vocabulary.getSemanticProperty(URI+"hasRoleRef");
        actualVersion=vocabulary.getSemanticProperty(URI+"actualVersion");
        lastVersion=vocabulary.getSemanticProperty(URI+"lastVersion");
        hasRuleRef=vocabulary.getSemanticProperty(URI+"hasRuleRef");
        nextVersion=vocabulary.getSemanticProperty(URI+"nextVersion");
        previousVersion=vocabulary.getSemanticProperty(URI+"previousVersion");
        versionComment=vocabulary.getSemanticProperty(URI+"versionComment");
        versionCreated=vocabulary.getSemanticProperty(URI+"versionCreated");
        camp=vocabulary.getSemanticProperty(URI+"camp");
        portletClass=vocabulary.getSemanticProperty(URI+"portletClass");
        portletType=vocabulary.getSemanticProperty(URI+"portletType");
        role=vocabulary.getSemanticProperty(URI+"role");
        hasPortletRef=vocabulary.getSemanticProperty(URI+"hasPortletRef");
        hasTemplateRef=vocabulary.getSemanticProperty(URI+"hasTemplateRef");
        hasPFlowRef=vocabulary.getSemanticProperty(URI+"hasPFlowRef");
        isChildOf=vocabulary.getSemanticProperty(URI+"isChildOf");
        homePage=vocabulary.getSemanticProperty(URI+"homePage");
        userRepository=vocabulary.getSemanticProperty(URI+"userRepository");
        portlet=vocabulary.getSemanticProperty(URI+"portlet");
        pflow=vocabulary.getSemanticProperty(URI+"pflow");
        rule=vocabulary.getSemanticProperty(URI+"rule");
    }
}
