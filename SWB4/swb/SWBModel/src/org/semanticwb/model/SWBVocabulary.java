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
package org.semanticwb.model;

import org.semanticwb.SWBPlatform;
import org.semanticwb.platform.SemanticVocabulary;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticProperty;
import java.util.Hashtable;
// TODO: Auto-generated Javadoc

/**
 * The Class SWBVocabulary.
 */
public class SWBVocabulary
{


    //Classes
    /** The swb_ interface. */
    public final SemanticClass swb_Interface;
    
    /** The swb_ user. */
    public final SemanticClass swb_User;
    
    /** The swb_ calendar. */
    public final SemanticClass swb_Calendar;
    
    /** The swb_ community. */
    public final SemanticClass swb_Community;
    
    /** The nt_file. */
    public final SemanticClass nt_file;
    
    /** The swb_ iconable. */
    public final SemanticClass swb_Iconable;
    
    /** The swbxf_ form view ref. */
    public final SemanticClass swbxf_FormViewRef;
    
    /** The swb_ deleteable. */
    public final SemanticClass swb_Deleteable;
    
    /** The swb_ reference. */
    public final SemanticClass swb_Reference;
    
    /** The swbrep_ nameable. */
    public final SemanticClass swbrep_Nameable;
    
    /** The swb_ roleable. */
    public final SemanticClass swb_Roleable;
    
    /** The swb_ p flow refable. */
    public final SemanticClass swb_PFlowRefable;
    
    /** The blog_ category. */
    public final SemanticClass blog_Category;
    
    /** The nt_ frozen node. */
    public final SemanticClass nt_FrozenNode;
    
    /** The blog_ blog. */
    public final SemanticClass blog_Blog;
    
    /** The swb_ ruleable. */
    public final SemanticClass swb_Ruleable;
    
    /** The nt_ unstructured. */
    public final SemanticClass nt_Unstructured;
    
    /** The swb_ swb model. */
    public final SemanticClass swb_SWBModel;
    
    /** The nt_ base node. */
    public final SemanticClass nt_BaseNode;
    
    /** The swb_ valueable. */
    public final SemanticClass swb_Valueable;
    
    /** The swbrep_ swb node. */
    public final SemanticClass swbrep_SWBNode;
    
    /** The swb_ calendarable. */
    public final SemanticClass swb_Calendarable;
    
    /** The swbrep_ traceable. */
    public final SemanticClass swbrep_Traceable;
    
    /** The swb_ permission. */
    public final SemanticClass swb_Permission;
    
    /** The swbxf_ object behavior. */
    public final SemanticClass swbxf_ObjectBehavior;
    
    /** The swbxf_ password update. */
    public final SemanticClass swbxf_PasswordUpdate;
    
    /** The swbrep_ class definition. */
    public final SemanticClass swbrep_ClassDefinition;
    
    /** The swbrep_ common propertiesfor definition. */
    public final SemanticClass swbrep_CommonPropertiesforDefinition;
    
    /** The blog_ post. */
    public final SemanticClass blog_Post;
    
    /** The swb_ template group. */
    public final SemanticClass swb_TemplateGroup;
    
    /** The swb_ portletable. */
    public final SemanticClass swb_Portletable;
    
    /** The cm_ office content. */
    public final SemanticClass cm_OfficeContent;
    
    /** The swb_ template. */
    public final SemanticClass swb_Template;
    
    /** The cm_ descriptiveable. */
    public final SemanticClass cm_Descriptiveable;
    
    /** The swb_ editable. */
    public final SemanticClass swb_Editable;
    
    /** The swb_ priorityable. */
    public final SemanticClass swb_Priorityable;
    
    /** The swb_ role. */
    public final SemanticClass swb_Role;
    
    /** The swb_ version info. */
    public final SemanticClass swb_VersionInfo;
    
    /** The swbxf_ property group. */
    public final SemanticClass swbxf_PropertyGroup;
    
    /** The mix_ lockable. */
    public final SemanticClass mix_Lockable;
    
    /** The swbxf_ resource parameter. */
    public final SemanticClass swbxf_ResourceParameter;
    
    /** The nt_hierarchy node. */
    public final SemanticClass nt_hierarchyNode;
    
    /** The swb_ swb class. */
    public final SemanticClass swb_SWBClass;
    
    /** The swb_ xm lable. */
    public final SemanticClass swb_XMLable;
    
    /** The nt_property definition. */
    public final SemanticClass nt_propertyDefinition;
    
    /** The swbxf_ display property. */
    public final SemanticClass swbxf_DisplayProperty;
    
    /** The swb_ rule. */
    public final SemanticClass swb_Rule;
    
    /** The swb_ web pageable. */
    public final SemanticClass swb_WebPageable;
    
    /** The swb_ web site. */
    public final SemanticClass swb_WebSite;
    
    /** The swb_ language. */
    public final SemanticClass swb_Language;
    
    /** The swb_ swb interface. */
    public final SemanticClass swb_SWBInterface;
    
    /** The swb_ portlet ref. */
    public final SemanticClass swb_PortletRef;
    
    /** The swbrep_ child node definition. */
    public final SemanticClass swbrep_ChildNodeDefinition;
    
    /** The swb_ portlet type. */
    public final SemanticClass swb_PortletType;
    
    /** The blog_ descriptiveable. */
    public final SemanticClass blog_Descriptiveable;
    
    /** The swb_ viewable. */
    public final SemanticClass swb_Viewable;
    
    /** The swbxf_ text. */
    public final SemanticClass swbxf_Text;
    
    /** The swb_ portlet sub type. */
    public final SemanticClass swb_PortletSubType;
    
    /** The swb_ inheritable. */
    public final SemanticClass swb_Inheritable;
    
    /** The swb_ template ref. */
    public final SemanticClass swb_TemplateRef;
    
    /** The swb_ templateable. */
    public final SemanticClass swb_Templateable;
    
    /** The swb_ sortable. */
    public final SemanticClass swb_Sortable;
    
    /** The swb_ traceable. */
    public final SemanticClass swb_Traceable;
    
    /** The nt_version labels. */
    public final SemanticClass nt_versionLabels;
    
    /** The swb_ role refable. */
    public final SemanticClass swb_RoleRefable;
    
    /** The swbxf_ text area. */
    public final SemanticClass swbxf_TextArea;
    
    /** The swb_ p flow. */
    public final SemanticClass swb_PFlow;
    
    /** The swb_ ip filter. */
    public final SemanticClass swb_IPFilter;
    
    /** The swb_ rule refable. */
    public final SemanticClass swb_RuleRefable;
    
    /** The nt_ version history. */
    public final SemanticClass nt_VersionHistory;
    
    /** The swb_ user group. */
    public final SemanticClass swb_UserGroup;
    
    /** The swb_ portlet refable. */
    public final SemanticClass swb_PortletRefable;
    
    /** The mix_ versionable. */
    public final SemanticClass mix_Versionable;
    
    /** The swb_ template refable. */
    public final SemanticClass swb_TemplateRefable;
    
    /** The swb_ p flow ref. */
    public final SemanticClass swb_PFlowRef;
    
    /** The swb_ rule ref. */
    public final SemanticClass swb_RuleRef;
    
    /** The nt_resource. */
    public final SemanticClass nt_resource;
    
    /** The cm_ office category. */
    public final SemanticClass cm_OfficeCategory;
    
    /** The swbxf_ form view. */
    public final SemanticClass swbxf_FormView;
    
    /** The swb_ referensable. */
    public final SemanticClass swb_Referensable;
    
    /** The swb_ hiddenable. */
    public final SemanticClass swb_Hiddenable;
    
    /** The swb_ device. */
    public final SemanticClass swb_Device;
    
    /** The swb_ localeable. */
    public final SemanticClass swb_Localeable;
    
    /** The swb_ camp. */
    public final SemanticClass swb_Camp;
    
    /** The swb_ dns. */
    public final SemanticClass swb_Dns;
    
    /** The swb_ user repository. */
    public final SemanticClass swb_UserRepository;
    
    /** The swbrep_ property definition. */
    public final SemanticClass swbrep_PropertyDefinition;
    
    /** The swbxf_ herarquical node. */
    public final SemanticClass swbxf_HerarquicalNode;
    
    /** The swb_ activeable. */
    public final SemanticClass swb_Activeable;
    
    /** The swb_ portlet. */
    public final SemanticClass swb_Portlet;
    
    /** The swb_ descriptiveable. */
    public final SemanticClass swb_Descriptiveable;
    
    /** The nt_child node definition. */
    public final SemanticClass nt_childNodeDefinition;
    
    /** The nt_folder. */
    public final SemanticClass nt_folder;
    
    /** The swb_ versionable. */
    public final SemanticClass swb_Versionable;
    
    /** The swb_ role ref. */
    public final SemanticClass swb_RoleRef;
    
    /** The swbxf_ object action. */
    public final SemanticClass swbxf_ObjectAction;
    
    /** The nt_ version. */
    public final SemanticClass nt_Version;
    
    /** The swb_ swb form element. */
    public final SemanticClass swb_SWBFormElement;
    
    /** The swb_ xml confable. */
    public final SemanticClass swb_XMLConfable;
    
    /** The mix_ referenceable. */
    public final SemanticClass mix_Referenceable;
    
    /** The swb_ class. */
    public final SemanticClass swb_Class;
    
    /** The swb_ web page. */
    public final SemanticClass swb_WebPage;
    
    /** The swb_ indexable. */
    public final SemanticClass swb_Indexable;
    
    /** The swbxf_ select one. */
    public final SemanticClass swbxf_SelectOne;
    
    /** The swbrep_ workspace. */
    public final SemanticClass swbrep_Workspace;



    //Properties
    /** The swb_has grouped user. */
    public final SemanticProperty swb_hasGroupedUser;
    
    /** The swb_has web page child. */
    public final SemanticProperty swb_hasWebPageChild;
    
    /** The swb_has pt sub type. */
    public final SemanticProperty swb_hasPTSubType;
    
    /** The swb_has pst portlets. */
    public final SemanticProperty swb_hasPSTPortlets;
    
    /** The swb_has web page virtual child. */
    public final SemanticProperty swb_hasWebPageVirtualChild;
    
    /** The swb_class name. */
    public final SemanticProperty swb_className;
    
    /** The swbrep_has nodes. */
    public final SemanticProperty swbrep_hasNodes;
    
    /** The swb_has pt portlet. */
    public final SemanticProperty swb_hasPTPortlet;
    
    /** The swbxf_frm text area rows. */
    public final SemanticProperty swbxf_frmTextAreaRows;
    
    /** The swb_has reference. */
    public final SemanticProperty swb_hasReference;
    
    /** The swb_has grouped template. */
    public final SemanticProperty swb_hasGroupedTemplate;
    
    /** The swb_created. */
    public final SemanticProperty swb_created;
    
    /** The swb_modified by. */
    public final SemanticProperty swb_modifiedBy;
    
    /** The swb_usr second last name. */
    public final SemanticProperty swb_usrSecondLastName;
    
    /** The swb_usr email. */
    public final SemanticProperty swb_usrEmail;
    
    /** The swb_user group. */
    public final SemanticProperty swb_userGroup;
    
    /** The swb_updated. */
    public final SemanticProperty swb_updated;
    
    /** The swb_usr first name. */
    public final SemanticProperty swb_usrFirstName;
    
    /** The swb_usr language. */
    public final SemanticProperty swb_usrLanguage;
    
    /** The swb_has calendar. */
    public final SemanticProperty swb_hasCalendar;
    
    /** The swb_usr password changed. */
    public final SemanticProperty swb_usrPasswordChanged;
    
    /** The swb_usr last name. */
    public final SemanticProperty swb_usrLastName;
    
    /** The swb_active. */
    public final SemanticProperty swb_active;
    
    /** The swb_usr last login. */
    public final SemanticProperty swb_usrLastLogin;
    
    /** The swb_creator. */
    public final SemanticProperty swb_creator;
    
    /** The swb_usr login. */
    public final SemanticProperty swb_usrLogin;
    
    /** The swb_usr password. */
    public final SemanticProperty swb_usrPassword;
    
    /** The swb_has role. */
    public final SemanticProperty swb_hasRole;
    
    /** The swb_usr security question. */
    public final SemanticProperty swb_usrSecurityQuestion;
    
    /** The swb_usr security answer. */
    public final SemanticProperty swb_usrSecurityAnswer;
    
    /** The swb_title. */
    public final SemanticProperty swb_title;
    
    /** The swb_xml. */
    public final SemanticProperty swb_xml;
    
    /** The swb_description. */
    public final SemanticProperty swb_description;
    
    /** The jcr_created. */
    public final SemanticProperty jcr_created;
    
    /** The swbrep_parent node. */
    public final SemanticProperty swbrep_parentNode;
    
    /** The jcr_primary type. */
    public final SemanticProperty jcr_primaryType;
    
    /** The jcr_mixin types. */
    public final SemanticProperty jcr_mixinTypes;
    
    /** The swbrep_path. */
    public final SemanticProperty swbrep_path;
    
    /** The swbrep_name. */
    public final SemanticProperty swbrep_name;
    
    /** The swb_icon class. */
    public final SemanticProperty swb_iconClass;
    
    /** The swb_form mode. */
    public final SemanticProperty swb_formMode;
    
    /** The swb_form view. */
    public final SemanticProperty swb_formView;
    
    /** The swb_deleted. */
    public final SemanticProperty swb_deleted;
    
    /** The jcr_name. */
    public final SemanticProperty jcr_name;
    
    /** The swb_has p flow ref. */
    public final SemanticProperty swb_hasPFlowRef;
    
    /** The jcr_frozen primary type. */
    public final SemanticProperty jcr_frozenPrimaryType;
    
    /** The jcr_frozen mixin types. */
    public final SemanticProperty jcr_frozenMixinTypes;
    
    /** The jcr_frozen uuid. */
    public final SemanticProperty jcr_frozenUuid;
    
    /** The blog_url. */
    public final SemanticProperty blog_url;
    
    /** The swb_has rule. */
    public final SemanticProperty swb_hasRule;
    
    /** The swb_value. */
    public final SemanticProperty swb_value;
    
    /** The swb_index. */
    public final SemanticProperty swb_index;
    
    /** The swb_hits. */
    public final SemanticProperty swb_hits;
    
    /** The swbxf_behavior refresh on show. */
    public final SemanticProperty swbxf_behaviorRefreshOnShow;
    
    /** The swb_hidden. */
    public final SemanticProperty swb_hidden;
    
    /** The swbxf_interface. */
    public final SemanticProperty swbxf_interface;
    
    /** The swb_indexable. */
    public final SemanticProperty swb_indexable;
    
    /** The swb_web page sort name. */
    public final SemanticProperty swb_webPageSortName;
    
    /** The swbxf_behavior url. */
    public final SemanticProperty swbxf_behaviorURL;
    
    /** The swb_web page url type. */
    public final SemanticProperty swb_webPageURLType;
    
    /** The swbxf_has resource param. */
    public final SemanticProperty swbxf_hasResourceParam;
    
    /** The swb_web page disk usage. */
    public final SemanticProperty swb_webPageDiskUsage;
    
    /** The swb_has role ref. */
    public final SemanticProperty swb_hasRoleRef;
    
    /** The swb_has web page virtual parent. */
    public final SemanticProperty swb_hasWebPageVirtualParent;
    
    /** The swb_views. */
    public final SemanticProperty swb_views;
    
    /** The swb_has portlet. */
    public final SemanticProperty swb_hasPortlet;
    
    /** The swb_has template ref. */
    public final SemanticProperty swb_hasTemplateRef;
    
    /** The swb_web page url. */
    public final SemanticProperty swb_webPageURL;
    
    /** The swb_has rule ref. */
    public final SemanticProperty swb_hasRuleRef;
    
    /** The swb_web page parent. */
    public final SemanticProperty swb_webPageParent;
    
    /** The swb_pass upd verify. */
    public final SemanticProperty swb_passUpdVerify;
    
    /** The mix_mixin. */
    public final SemanticProperty mix_mixin;
    
    /** The jcr_primary item name. */
    public final SemanticProperty jcr_primaryItemName;
    
    /** The jcr_child node definition. */
    public final SemanticProperty jcr_childNodeDefinition;
    
    /** The jcr_property definition. */
    public final SemanticProperty jcr_propertyDefinition;
    
    /** The jcr_supertypes. */
    public final SemanticProperty jcr_supertypes;
    
    /** The jcr_orderable child nodes. */
    public final SemanticProperty jcr_orderableChildNodes;
    
    /** The jcr_on parent version. */
    public final SemanticProperty jcr_onParentVersion;
    
    /** The jcr_auto created. */
    public final SemanticProperty jcr_autoCreated;
    
    /** The jcr_mandatory. */
    public final SemanticProperty jcr_mandatory;
    
    /** The jcr_protected. */
    public final SemanticProperty jcr_protected;
    
    /** The blog_category. */
    public final SemanticProperty blog_category;
    
    /** The cm_user. */
    public final SemanticProperty cm_user;
    
    /** The cm_title. */
    public final SemanticProperty cm_title;
    
    /** The cm_file. */
    public final SemanticProperty cm_file;
    
    /** The cm_officetype. */
    public final SemanticProperty cm_officetype;
    
    /** The cm_description. */
    public final SemanticProperty cm_description;
    
    /** The swb_template group. */
    public final SemanticProperty swb_templateGroup;
    
    /** The swb_actual version. */
    public final SemanticProperty swb_actualVersion;
    
    /** The swb_last version. */
    public final SemanticProperty swb_lastVersion;
    
    /** The swb_language. */
    public final SemanticProperty swb_language;
    
    /** The swb_priority. */
    public final SemanticProperty swb_priority;
    
    /** The swb_has permission. */
    public final SemanticProperty swb_hasPermission;
    
    /** The swb_previous version. */
    public final SemanticProperty swb_previousVersion;
    
    /** The swb_version locked by. */
    public final SemanticProperty swb_versionLockedBy;
    
    /** The swb_version file. */
    public final SemanticProperty swb_versionFile;
    
    /** The swb_version comment. */
    public final SemanticProperty swb_versionComment;
    
    /** The swb_next version. */
    public final SemanticProperty swb_nextVersion;
    
    /** The swb_version number. */
    public final SemanticProperty swb_versionNumber;
    
    /** The jcr_lock owner. */
    public final SemanticProperty jcr_lockOwner;
    
    /** The jcr_lock is deep. */
    public final SemanticProperty jcr_lockIsDeep;
    
    /** The swbxf_res param value. */
    public final SemanticProperty swbxf_resParamValue;
    
    /** The swbxf_res param name. */
    public final SemanticProperty swbxf_resParamName;
    
    /** The jcr_value constraints. */
    public final SemanticProperty jcr_valueConstraints;
    
    /** The jcr_required type. */
    public final SemanticProperty jcr_requiredType;
    
    /** The jcr_multiple. */
    public final SemanticProperty jcr_multiple;
    
    /** The jcr_default values. */
    public final SemanticProperty jcr_defaultValues;
    
    /** The swbrep_internal. */
    public final SemanticProperty swbrep_internal;
    
    /** The swbxf_prop select values. */
    public final SemanticProperty swbxf_propSelectValues;
    
    /** The swbxf_prop group. */
    public final SemanticProperty swbxf_propGroup;
    
    /** The swbxf_prop hidden. */
    public final SemanticProperty swbxf_propHidden;
    
    /** The swbxf_prop prompt message. */
    public final SemanticProperty swbxf_propPromptMessage;
    
    /** The swbxf_prop invalid message. */
    public final SemanticProperty swbxf_propInvalidMessage;
    
    /** The swbxf_form element. */
    public final SemanticProperty swbxf_formElement;
    
    /** The swbxf_prop editable. */
    public final SemanticProperty swbxf_propEditable;
    
    /** The swb_web page. */
    public final SemanticProperty swb_webPage;
    
    /** The swb_home page. */
    public final SemanticProperty swb_homePage;
    
    /** The swb_user repository. */
    public final SemanticProperty swb_userRepository;
    
    /** The swb_portlet. */
    public final SemanticProperty swb_portlet;
    
    /** The jcr_default primary type. */
    public final SemanticProperty jcr_defaultPrimaryType;
    
    /** The jcr_required primary types. */
    public final SemanticProperty jcr_requiredPrimaryTypes;
    
    /** The jcr_same name siblings. */
    public final SemanticProperty jcr_sameNameSiblings;
    
    /** The swb_portlet bundle. */
    public final SemanticProperty swb_portletBundle;
    
    /** The swb_portlet cache. */
    public final SemanticProperty swb_portletCache;
    
    /** The swb_portlet class name. */
    public final SemanticProperty swb_portletClassName;
    
    /** The swb_portlet mode. */
    public final SemanticProperty swb_portletMode;
    
    /** The blog_description. */
    public final SemanticProperty blog_description;
    
    /** The blog_name. */
    public final SemanticProperty blog_name;
    
    /** The swb_ pst type. */
    public final SemanticProperty swb_PSTType;
    
    /** The swb_inherita. */
    public final SemanticProperty swb_inherita;
    
    /** The swb_template. */
    public final SemanticProperty swb_template;
    
    /** The swb_has template. */
    public final SemanticProperty swb_hasTemplate;
    
    /** The swbxf_text area rows. */
    public final SemanticProperty swbxf_textAreaRows;
    
    /** The swbxf_text area cols. */
    public final SemanticProperty swbxf_textAreaCols;
    
    /** The swb_ip filter action. */
    public final SemanticProperty swb_ipFilterAction;
    
    /** The swb_ip filter number. */
    public final SemanticProperty swb_ipFilterNumber;
    
    /** The jcr_versionable uuid. */
    public final SemanticProperty jcr_versionableUuid;
    
    /** The swb_has portlet ref. */
    public final SemanticProperty swb_hasPortletRef;
    
    /** The jcr_version history. */
    public final SemanticProperty jcr_versionHistory;
    
    /** The jcr_is checked out. */
    public final SemanticProperty jcr_isCheckedOut;
    
    /** The jcr_base version. */
    public final SemanticProperty jcr_baseVersion;
    
    /** The jcr_merge failed. */
    public final SemanticProperty jcr_mergeFailed;
    
    /** The jcr_uuid. */
    public final SemanticProperty jcr_uuid;
    
    /** The swb_pflow. */
    public final SemanticProperty swb_pflow;
    
    /** The swb_rule. */
    public final SemanticProperty swb_rule;
    
    /** The jcr_mime type. */
    public final SemanticProperty jcr_mimeType;
    
    /** The jcr_encoding. */
    public final SemanticProperty jcr_encoding;
    
    /** The jcr_data. */
    public final SemanticProperty jcr_data;
    
    /** The jcr_last modified. */
    public final SemanticProperty jcr_lastModified;
    
    /** The swbxf_has create property. */
    public final SemanticProperty swbxf_hasCreateProperty;
    
    /** The swbxf_has view property. */
    public final SemanticProperty swbxf_hasViewProperty;
    
    /** The swbxf_has edit property. */
    public final SemanticProperty swbxf_hasEditProperty;
    
    /** The swb_dns default. */
    public final SemanticProperty swb_dnsDefault;
    
    /** The swbxf_he model. */
    public final SemanticProperty swbxf_heModel;
    
    /** The swbxf_he class. */
    public final SemanticProperty swbxf_heClass;
    
    /** The swb_camp. */
    public final SemanticProperty swb_camp;
    
    /** The swb_portlet window. */
    public final SemanticProperty swb_portletWindow;
    
    /** The swb_xml conf. */
    public final SemanticProperty swb_xmlConf;
    
    /** The swb_portlet sub type. */
    public final SemanticProperty swb_portletSubType;
    
    /** The swb_portlet type. */
    public final SemanticProperty swb_portletType;
    
    /** The swb_role. */
    public final SemanticProperty swb_role;
    
    /** The swbxf_act group. */
    public final SemanticProperty swbxf_actGroup;
    
    /** The swbxf_action url. */
    public final SemanticProperty swbxf_actionURL;
    
    /** The jcr_successors. */
    public final SemanticProperty jcr_successors;
    
    /** The jcr_predecessors. */
    public final SemanticProperty jcr_predecessors;
    
    /** The swb_autogen id. */
    public final SemanticProperty swb_autogenId;
    
    /** The swbxf_so_blank suport. */
    public final SemanticProperty swbxf_so_blankSuport;
    
    /** The swbxf_so_global scope. */
    public final SemanticProperty swbxf_so_globalScope;
    
    /** The jcr_root. */
    public final SemanticProperty jcr_root;


    /**
     * Instantiates a new sWB vocabulary.
     */
    public SWBVocabulary()
    {

         SemanticVocabulary vocabulary=SWBPlatform.getSemanticMgr().getVocabulary();
        // Classes
        swb_Interface=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Interface");
        swb_User=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
        swb_Calendar=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Calendar");
        swb_Community=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Community");
        nt_file=vocabulary.getSemanticClass("http://www.jcp.org/jcr/nt/1.0#file");
        swb_Iconable=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Iconable");
        swbxf_FormViewRef=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#FormViewRef");
        swb_Deleteable=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Deleteable");
        swb_Reference=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Reference");
        swbrep_Nameable=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/repository#Nameable");
        swb_Roleable=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Roleable");
        swb_PFlowRefable=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#PFlowRefable");
        blog_Category=vocabulary.getSemanticClass("http://www.semanticwb.org.mx/swb4/blog#Category");
        nt_FrozenNode=vocabulary.getSemanticClass("http://www.jcp.org/jcr/nt/1.0#frozenNode");
        blog_Blog=vocabulary.getSemanticClass("http://www.semanticwb.org.mx/swb4/blog#Blog");
        swb_Ruleable=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Ruleable");
        nt_Unstructured=vocabulary.getSemanticClass("http://www.jcp.org/jcr/nt/1.0#unstructured");
        swb_SWBModel=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#SWBModel");
        nt_BaseNode=vocabulary.getSemanticClass("http://www.jcp.org/jcr/nt/1.0#base");
        swb_Valueable=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Valueable");
        swbrep_SWBNode=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/repository#SWBNode");
        swb_Calendarable=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Calendarable");
        swbrep_Traceable=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/repository#Traceable");
        swb_Permission=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Permission");
        swbxf_ObjectBehavior=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#ObjectBehavior");
        swbxf_PasswordUpdate=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#PasswordUpdate");
        swbrep_ClassDefinition=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/repository#ClassDefinition");
        swbrep_CommonPropertiesforDefinition=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/repository#CommonPropertiesforDefinition");
        blog_Post=vocabulary.getSemanticClass("http://www.semanticwb.org.mx/swb4/blog#Post");
        swb_TemplateGroup=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#TemplateGroup");
        swb_Portletable=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Portletable");
        cm_OfficeContent=vocabulary.getSemanticClass("http://www.semanticwb.org.mx/swb4/content#OfficeContent");
        swb_Template=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Template");
        cm_Descriptiveable=vocabulary.getSemanticClass("http://www.semanticwb.org.mx/swb4/content#Descriptiveable");
        swb_Editable=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Editable");
        swb_Priorityable=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Priorityable");
        swb_Role=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Role");
        swb_VersionInfo=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#VersionInfo");
        swbxf_PropertyGroup=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#PropertyGroup");
        mix_Lockable=vocabulary.getSemanticClass("http://www.jcp.org/jcr/mix/1.0#lockable");
        swbxf_ResourceParameter=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#ResourceParameter");
        nt_hierarchyNode=vocabulary.getSemanticClass("http://www.jcp.org/jcr/nt/1.0#hierarchyNode");
        swb_SWBClass=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#SWBClass");
        swb_XMLable=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#XMLable");
        nt_propertyDefinition=vocabulary.getSemanticClass("http://www.jcp.org/jcr/nt/1.0#propertyDefinition");
        swbxf_DisplayProperty=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#DisplayProperty");
        swb_Rule=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Rule");
        swb_WebPageable=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebPageable");
        swb_WebSite=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebSite");
        swb_Language=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Language");
        swb_SWBInterface=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#SWBInterface");
        swb_PortletRef=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#PortletRef");
        swbrep_ChildNodeDefinition=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/repository#ChildNodeDefinition");
        swb_PortletType=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#PortletType");
        blog_Descriptiveable=vocabulary.getSemanticClass("http://www.semanticwb.org.mx/swb4/blog#Descriptiveable");
        swb_Viewable=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Viewable");
        swbxf_Text=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#Text");
        swb_PortletSubType=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#PortletSubType");
        swb_Inheritable=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Inheritable");
        swb_TemplateRef=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#TemplateRef");
        swb_Templateable=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Templateable");
        swb_Sortable=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Sortable");
        swb_Traceable=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Traceable");
        nt_versionLabels=vocabulary.getSemanticClass("http://www.jcp.org/jcr/nt/1.0#versionLabels");
        swb_RoleRefable=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#RoleRefable");
        swbxf_TextArea=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#TextArea");
        swb_PFlow=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#PFlow");
        swb_IPFilter=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#IPFilter");
        swb_RuleRefable=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#RuleRefable");
        nt_VersionHistory=vocabulary.getSemanticClass("http://www.jcp.org/jcr/nt/1.0#versionHistory");
        swb_UserGroup=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UserGroup");
        swb_PortletRefable=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#PortletRefable");
        mix_Versionable=vocabulary.getSemanticClass("http://www.jcp.org/jcr/mix/1.0#versionable");
        swb_TemplateRefable=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#TemplateRefable");
        swb_PFlowRef=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#PFlowRef");
        swb_RuleRef=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#RuleRef");
        nt_resource=vocabulary.getSemanticClass("http://www.jcp.org/jcr/nt/1.0#resource");
        cm_OfficeCategory=vocabulary.getSemanticClass("http://www.semanticwb.org.mx/swb4/content#OfficeCategory");
        swbxf_FormView=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#FormView");
        swb_Referensable=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Referensable");
        swb_Hiddenable=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Hiddenable");
        swb_Device=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Device");
        swb_Localeable=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Localeable");
        swb_Camp=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Camp");
        swb_Dns=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Dns");
        swb_UserRepository=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UserRepository");
        swbrep_PropertyDefinition=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/repository#PropertyDefinition");
        swbxf_HerarquicalNode=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#HerarquicalNode");
        swb_Activeable=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Activeable");
        swb_Portlet=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Portlet");
        swb_Descriptiveable=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Descriptiveable");
        nt_childNodeDefinition=vocabulary.getSemanticClass("http://www.jcp.org/jcr/nt/1.0#childNodeDefinition");
        nt_folder=vocabulary.getSemanticClass("http://www.jcp.org/jcr/nt/1.0#folder");
        swb_Versionable=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Versionable");
        swb_RoleRef=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#RoleRef");
        swbxf_ObjectAction=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#ObjectAction");
        nt_Version=vocabulary.getSemanticClass("http://www.jcp.org/jcr/nt/1.0#version");
        swb_SWBFormElement=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#SWBFormElement");
        swb_XMLConfable=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#XMLConfable");
        mix_Referenceable=vocabulary.getSemanticClass("http://www.jcp.org/jcr/mix/1.0#referenceable");
        swb_Class=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Class");
        swb_WebPage=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebPage");
        swb_Indexable=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Indexable");
        swbxf_SelectOne=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#SelectOne");
        swbrep_Workspace=vocabulary.getSemanticClass("http://www.semanticwebbuilder.org/swb4/repository#Workspace");



        //Properties
        swb_hasGroupedUser=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasGroupedUser");
        swb_hasWebPageChild=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasWebPageChild");
        swb_hasPTSubType=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasPTSubType");
        swb_hasPSTPortlets=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasPSTPortlets");
        swb_hasWebPageVirtualChild=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasWebPageVirtualChild");
        swb_className=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#className");
        swbrep_hasNodes=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/repository#hasNodes");
        swb_hasPTPortlet=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasPTPortlet");
        swbxf_frmTextAreaRows=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#frmTextAreaRows");
        swb_hasReference=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasReference");
        swb_hasGroupedTemplate=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasGroupedTemplate");
        swb_created=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#created");
        swb_modifiedBy=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#modifiedBy");
        swb_usrSecondLastName=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#usrSecondLastName");
        swb_usrEmail=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#usrEmail");
        swb_userGroup=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#userGroup");
        swb_updated=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#updated");
        swb_usrFirstName=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#usrFirstName");
        swb_usrLanguage=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#usrLanguage");
        swb_hasCalendar=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasCalendar");
        swb_usrPasswordChanged=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#usrPasswordChanged");
        swb_usrLastName=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#usrLastName");
        swb_active=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#active");
        swb_usrLastLogin=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#usrLastLogin");
        swb_creator=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#creator");
        swb_usrLogin=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#usrLogin");
        swb_usrPassword=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#usrPassword");
        swb_hasRole=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasRole");
        swb_usrSecurityQuestion=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#usrSecurityQuestion");
        swb_usrSecurityAnswer=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#usrSecurityAnswer");
        swb_title=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#title");
        swb_xml=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#xml");
        swb_description=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#description");
        jcr_created=vocabulary.getSemanticProperty("http://www.jcp.org/jcr/1.0#created");
        swbrep_parentNode=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/repository#parentNode");
        jcr_primaryType=vocabulary.getSemanticProperty("http://www.jcp.org/jcr/1.0#primaryType");
        jcr_mixinTypes=vocabulary.getSemanticProperty("http://www.jcp.org/jcr/1.0#mixinTypes");
        swbrep_path=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/repository#path");
        swbrep_name=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/repository#name");
        swb_iconClass=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#iconClass");
        swb_formMode=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#formMode");
        swb_formView=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#formView");
        swb_deleted=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#deleted");
        jcr_name=vocabulary.getSemanticProperty("http://www.jcp.org/jcr/1.0#name");
        swb_hasPFlowRef=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasPFlowRef");
        jcr_frozenPrimaryType=vocabulary.getSemanticProperty("http://www.jcp.org/jcr/1.0#frozenPrimaryType");
        jcr_frozenMixinTypes=vocabulary.getSemanticProperty("http://www.jcp.org/jcr/1.0#frozenMixinTypes");
        jcr_frozenUuid=vocabulary.getSemanticProperty("http://www.jcp.org/jcr/1.0#frozenUuid");
        blog_url=vocabulary.getSemanticProperty("http://www.semanticwb.org.mx/swb4/blog#url");
        swb_hasRule=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasRule");
        swb_value=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#value");
        swb_index=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#index");
        swb_hits=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hits");
        swbxf_behaviorRefreshOnShow=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#behaviorRefreshOnShow");
        swb_hidden=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hidden");
        swbxf_interface=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#interface");
        swb_indexable=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#indexable");
        swb_webPageSortName=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#webPageSortName");
        swbxf_behaviorURL=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#behaviorURL");
        swb_webPageURLType=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#webPageURLType");
        swbxf_hasResourceParam=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#hasResourceParam");
        swb_webPageDiskUsage=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#webPageDiskUsage");
        swb_hasRoleRef=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasRoleRef");
        swb_hasWebPageVirtualParent=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasWebPageVirtualParent");
        swb_views=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#views");
        swb_hasPortlet=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasPortlet");
        swb_hasTemplateRef=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasTemplateRef");
        swb_webPageURL=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#webPageURL");
        swb_hasRuleRef=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasRuleRef");
        swb_webPageParent=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#webPageParent");
        swb_passUpdVerify=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#passUpdVerify");
        mix_mixin=vocabulary.getSemanticProperty("http://www.jcp.org/jcr/mix/1.0#mixin");
        jcr_primaryItemName=vocabulary.getSemanticProperty("http://www.jcp.org/jcr/1.0#primaryItemName");
        jcr_childNodeDefinition=vocabulary.getSemanticProperty("http://www.jcp.org/jcr/1.0#childNodeDefinition");
        jcr_propertyDefinition=vocabulary.getSemanticProperty("http://www.jcp.org/jcr/1.0#propertyDefinition");
        jcr_supertypes=vocabulary.getSemanticProperty("http://www.jcp.org/jcr/1.0#supertypes");
        jcr_orderableChildNodes=vocabulary.getSemanticProperty("http://www.jcp.org/jcr/1.0#orderableChildNodes");
        jcr_onParentVersion=vocabulary.getSemanticProperty("http://www.jcp.org/jcr/1.0#onParentVersion");
        jcr_autoCreated=vocabulary.getSemanticProperty("http://www.jcp.org/jcr/1.0#autoCreated");
        jcr_mandatory=vocabulary.getSemanticProperty("http://www.jcp.org/jcr/1.0#mandatory");
        jcr_protected=vocabulary.getSemanticProperty("http://www.jcp.org/jcr/1.0#protected");
        blog_category=vocabulary.getSemanticProperty("http://www.semanticwb.org.mx/swb4/blog#category");
        cm_user=vocabulary.getSemanticProperty("http://www.semanticwb.org.mx/swb4/content#user");
        cm_title=vocabulary.getSemanticProperty("http://www.semanticwb.org.mx/swb4/content#title");
        cm_file=vocabulary.getSemanticProperty("http://www.semanticwb.org.mx/swb4/content#file");
        cm_officetype=vocabulary.getSemanticProperty("http://www.semanticwb.org.mx/swb4/content#officetype");
        cm_description=vocabulary.getSemanticProperty("http://www.semanticwb.org.mx/swb4/content#description");
        swb_templateGroup=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#templateGroup");
        swb_actualVersion=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#actualVersion");
        swb_lastVersion=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#lastVersion");
        swb_language=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#language");
        swb_priority=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#priority");
        swb_hasPermission=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasPermission");
        swb_previousVersion=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#previousVersion");
        swb_versionLockedBy=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#versionLockedBy");
        swb_versionFile=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#versionFile");
        swb_versionComment=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#versionComment");
        swb_nextVersion=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#nextVersion");
        swb_versionNumber=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#versionNumber");
        jcr_lockOwner=vocabulary.getSemanticProperty("http://www.jcp.org/jcr/1.0#lockOwner");
        jcr_lockIsDeep=vocabulary.getSemanticProperty("http://www.jcp.org/jcr/1.0#lockIsDeep");
        swbxf_resParamValue=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#resParamValue");
        swbxf_resParamName=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#resParamName");
        jcr_valueConstraints=vocabulary.getSemanticProperty("http://www.jcp.org/jcr/1.0#valueConstraints");
        jcr_requiredType=vocabulary.getSemanticProperty("http://www.jcp.org/jcr/1.0#requiredType");
        jcr_multiple=vocabulary.getSemanticProperty("http://www.jcp.org/jcr/1.0#multiple");
        jcr_defaultValues=vocabulary.getSemanticProperty("http://www.jcp.org/jcr/1.0#defaultValues");
        swbrep_internal=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/repository#internal");
        swbxf_propSelectValues=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#propSelectValues");
        swbxf_propGroup=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#propGroup");
        swbxf_propHidden=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#propHidden");
        swbxf_propPromptMessage=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#propPromptMessage");
        swbxf_propInvalidMessage=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#propInvalidMessage");
        swbxf_formElement=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#formElement");
        swbxf_propEditable=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#propEditable");
        swb_webPage=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#webPage");
        swb_homePage=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#homePage");
        swb_userRepository=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#userRepository");
        swb_portlet=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#portlet");
        jcr_defaultPrimaryType=vocabulary.getSemanticProperty("http://www.jcp.org/jcr/1.0#defaultPrimaryType");
        jcr_requiredPrimaryTypes=vocabulary.getSemanticProperty("http://www.jcp.org/jcr/1.0#requiredPrimaryTypes");
        jcr_sameNameSiblings=vocabulary.getSemanticProperty("http://www.jcp.org/jcr/1.0#sameNameSiblings");
        swb_portletBundle=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#portletBundle");
        swb_portletCache=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#portletCache");
        swb_portletClassName=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#portletClassName");
        swb_portletMode=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#portletMode");
        blog_description=vocabulary.getSemanticProperty("http://www.semanticwb.org.mx/swb4/blog#description");
        blog_name=vocabulary.getSemanticProperty("http://www.semanticwb.org.mx/swb4/blog#name");
        swb_PSTType=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#PSTType");
        swb_inherita=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#inherita");
        swb_template=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#template");
        swb_hasTemplate=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasTemplate");
        swbxf_textAreaRows=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#textAreaRows");
        swbxf_textAreaCols=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#textAreaCols");
        swb_ipFilterAction=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#ipFilterAction");
        swb_ipFilterNumber=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#ipFilterNumber");
        jcr_versionableUuid=vocabulary.getSemanticProperty("http://www.jcp.org/jcr/1.0#versionableUuid");
        swb_hasPortletRef=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasPortletRef");
        jcr_versionHistory=vocabulary.getSemanticProperty("http://www.jcp.org/jcr/1.0#versionHistory");
        jcr_isCheckedOut=vocabulary.getSemanticProperty("http://www.jcp.org/jcr/1.0#isCheckedOut");
        jcr_baseVersion=vocabulary.getSemanticProperty("http://www.jcp.org/jcr/1.0#baseVersion");
        jcr_mergeFailed=vocabulary.getSemanticProperty("http://www.jcp.org/jcr/1.0#mergeFailed");
        jcr_uuid=vocabulary.getSemanticProperty("http://www.jcp.org/jcr/1.0#uuid");
        swb_pflow=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#pflow");
        swb_rule=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#rule");
        jcr_mimeType=vocabulary.getSemanticProperty("http://www.jcp.org/jcr/1.0#mimeType");
        jcr_encoding=vocabulary.getSemanticProperty("http://www.jcp.org/jcr/1.0#encoding");
        jcr_data=vocabulary.getSemanticProperty("http://www.jcp.org/jcr/1.0#data");
        jcr_lastModified=vocabulary.getSemanticProperty("http://www.jcp.org/jcr/1.0#lastModified");
        swbxf_hasCreateProperty=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#hasCreateProperty");
        swbxf_hasViewProperty=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#hasViewProperty");
        swbxf_hasEditProperty=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#hasEditProperty");
        swb_dnsDefault=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#dnsDefault");
        swbxf_heModel=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#heModel");
        swbxf_heClass=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#heClass");
        swb_camp=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#camp");
        swb_portletWindow=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#portletWindow");
        swb_xmlConf=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#xmlConf");
        swb_portletSubType=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#portletSubType");
        swb_portletType=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#portletType");
        swb_role=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#role");
        swbxf_actGroup=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#actGroup");
        swbxf_actionURL=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#actionURL");
        jcr_successors=vocabulary.getSemanticProperty("http://www.jcp.org/jcr/1.0#successors");
        jcr_predecessors=vocabulary.getSemanticProperty("http://www.jcp.org/jcr/1.0#predecessors");
        swb_autogenId=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#autogenId");
        swbxf_so_blankSuport=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#so_blankSuport");
        swbxf_so_globalScope=vocabulary.getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#so_globalScope");
        jcr_root=vocabulary.getSemanticProperty("http://www.jcp.org/jcr/1.0#root");
    }



        //ListUris
              /**
         * List uris.
         * 
         * @return the hashtable
         */
        public Hashtable<String,String> listUris()
              {
                     Hashtable<String,String> namespaces=new Hashtable<String, String>();
                 namespaces.put("jcr","http://www.jcp.org/jcr/1.0#");
                 namespaces.put("cm","http://www.semanticwb.org.mx/swb4/content#");
                 namespaces.put("swbxf","http://www.semanticwebbuilder.org/swb4/xforms/ontology#");
                 namespaces.put("nt","http://www.jcp.org/jcr/nt/1.0#");
                 namespaces.put("mix","http://www.jcp.org/jcr/mix/1.0#");
                 namespaces.put("swbrep","http://www.semanticwebbuilder.org/swb4/repository#");
                 namespaces.put("blog","http://www.semanticwb.org.mx/swb4/blog#");
                 namespaces.put("swb","http://www.semanticwebbuilder.org/swb4/ontology#");
                     return namespaces;
              }
}
