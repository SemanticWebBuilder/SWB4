/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.opensocial.resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONObject;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;
import org.semanticwb.opensocial.model.Gadget;
import org.semanticwb.opensocial.model.PersonalizedGadged;
import org.semanticwb.opensocial.model.UserPref;
import org.semanticwb.opensocial.model.data.Group;
import org.semanticwb.opensocial.model.data.Name;
import org.semanticwb.opensocial.model.data.Person;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author victor.lorenzana
 */
public class SocialUser
{

    private static final Logger log = SWBUtils.getLogger(SocialUser.class);
    private final String user;
    private final UserPrefManager userprefsManager = new UserPrefManager();
    private String lang="ALL";
    private String country="ALL";
    public SocialUser(User user)
    {
        this.user = user == null ? null : user.getId();
        refresh(user);
    }
    public final void refresh(User user)
    {
        lang=user.getLanguage();
        if(user.getCountry()!=null)
        {
            country=user.getCountry().getId();
            if(country==null)
            {
                country="ALL";
            }
        }
        if(lang==null)
        {
            lang="ALL";
        }
    }
    public String getCountry()
    {
        return country;
    }
    public String getLanguage()
    {
        return lang;
    }
    public String getUserId()
    {
        return user;
    }
    public void clearUserPrefs(WebSite site)
    {
        if (user == null)
        {
            userprefsManager.clear();
        }
        else
        {
            User _user = site.getUserRepository().getUser(user);            
            Iterator<PersonalizedGadged> personalizedGadgeds = PersonalizedGadged.ClassMgr.listPersonalizedGadgedByUser(_user, site);
            ArrayList<PersonalizedGadged> toDelete = new ArrayList<PersonalizedGadged>();
            while (personalizedGadgeds.hasNext())
            {
                PersonalizedGadged pgadget = personalizedGadgeds.next();
                toDelete.add(pgadget);
            }
            for (PersonalizedGadged pgadget : toDelete)
            {
                System.out.println("deleting pgadget " + pgadget.getId());
                pgadget.remove();
            }            
        }
    }
    public UserPrefs[] getUserPrefs(WebSite site)
    {
        if (user == null)
        {
            return userprefsManager.toArray(new UserPrefs[userprefsManager.size()]);
        }
        else
        {
            User _user = site.getUserRepository().getUser(user);
            Set<UserPrefs> getUserPrefs = new HashSet<UserPrefs>();
            Iterator<PersonalizedGadged> personalizedGadgeds = PersonalizedGadged.ClassMgr.listPersonalizedGadgedByUser(_user, site);
            ArrayList<PersonalizedGadged> toDelete = new ArrayList<PersonalizedGadged>();
            while (personalizedGadgeds.hasNext())
            {
                PersonalizedGadged pgadget = personalizedGadgeds.next();
                if (pgadget.getGadget() != null && pgadget.getId() != null)
                {
                    UserPrefs pref = new UserPrefs(pgadget.getGadget(), pgadget.getId());
                    getUserPrefs.add(pref);
                }
                else
                {
                    toDelete.add(pgadget);
                }
            }
            for (PersonalizedGadged pgadget : toDelete)
            {
                System.out.println("deleting pgadget " + pgadget.getId());
                pgadget.remove();
            }
            return getUserPrefs.toArray(new UserPrefs[getUserPrefs.size()]);
        }
    }

    public void checkOsapiFeature(Gadget gadget, WebSite site)
    {
        checkOsapiFeature(gadget, site, false);
    }

    public void checkOsapiFeature(Gadget gadget, WebSite site, boolean forceupdate)
    {
        for (String feature : gadget.getAllFeatures())
        {
            if ("osapi".equals(feature)) // osapi is not valid for non register users
            {
                if (user != null)
                {
                    User _user = site.getUserRepository().getUser(user);
                    Person person = Person.ClassMgr.createPerson(user, site);
                    if (person == null)
                    {
                        person = Person.ClassMgr.createPerson(user, site);
                        Name name = Name.ClassMgr.createName(site);
                        name.setGivenName(_user.getFirstName());
                        name.setFamilyName(_user.getLastName());
                        name.setMiddleName(_user.getSecondLastName());
                        name.setFormatted(_user.getFullName());
                        name.setGivenName(_user.getFirstName());
                        person.setThumbnailUrl(SWBPortal.getDistributorPath() + SWBPortal.getWebWorkPath() + _user.getPhoto());
                        person.setName(name);
                        if (_user.getEmail() != null)
                        {
                            person.addEmail(_user.getEmail());
                        }
                        if (Group.getGroup(Group.FRIENDS, person, site) == null)
                        {
                            Group friends = Group.createGroup(Group.FRIENDS, person, site);
                            friends.setTitle("friends");
                            friends.setDescription("friends");
                            person.addGroup(friends);
                        }
                    }
                    else
                    {
                        if (Group.getGroup(Group.FRIENDS, person, site) == null)
                        {
                            Group friends = Group.createGroup(Group.FRIENDS, person, site);
                            friends.setTitle("friends");
                            friends.setDescription("friends");
                            person.addGroup(friends);
                        }
                        if (forceupdate)
                        {
                            Name name = person.getName();
                            if (name == null)
                            {
                                name = Name.ClassMgr.createName(site);
                                person.setName(name);
                            }
                            name.setGivenName(_user.getFirstName());
                            name.setFamilyName(_user.getLastName());
                            name.setMiddleName(_user.getSecondLastName());
                            name.setFormatted(_user.getFullName());
                            name.setGivenName(_user.getFirstName());
                            person.setThumbnailUrl(SWBPortal.getWebWorkPath() + _user.getPhoto());
                            if (Group.getGroup(Group.FRIENDS, person, site) == null)
                            {
                                Group friends = Group.createGroup(Group.FRIENDS, person, site);
                                friends.setTitle("friends");
                                friends.setDescription("friends");
                                person.addGroup(friends);
                            }
                        }
                    }

                }
            }
        }

    }

    public boolean canAdd(Gadget gadget, WebSite site)
    {
        for (String feature : gadget.getAllFeatures())
        {
            if ("osapi".equals(feature)) // osapi is not valid for non register users
            {
                if (user == null)
                {
                    return false;
                }
                checkOsapiFeature(gadget, site);
            }
        }
        return true;
    }
    public int getNumberOfGadgets()
    {
        return userprefsManager.size();
    }
    public Map<String, String> getVariablesubstituion(Gadget gadget, String language, String country, String moduleID,WebSite site)
    {
        return getVariablesubstituion(gadget, language, country, moduleID, site, true);
    }
    public Map<String, String> getVariablesubstituion(Gadget gadget, String language, String country, String moduleID,WebSite site,boolean formated)
    {

        Map<String, String> getVariablesubstituion = new HashMap<String, String>();
        getVariablesubstituion.putAll(gadget.getMessagesFromGadget(language, country));

        getVariablesubstituion.put("__MODULE_ID__", moduleID);
        getVariablesubstituion.put("__MSG_LANG__", language);
        getVariablesubstituion.put("__MSG_COUNTRY__", country);
        if(user!=null && moduleID!=null && site!=null)
        {
            User _user=site.getUserRepository().getUser(user);
            Iterator<PersonalizedGadged> preferences = PersonalizedGadged.ClassMgr.listPersonalizedGadgedByUser(_user,site);
            while (preferences.hasNext())
            {
                PersonalizedGadged personalizedGadged = preferences.next();
                if (personalizedGadged.getGadget().getURI().equals(gadget.getURI()) && personalizedGadged.getId().equals(moduleID))
                {
                    GenericIterator<UserPref> list = personalizedGadged.listUserPrefses();
                    while (list.hasNext())
                    {
                        UserPref pref = list.next();
                        getVariablesubstituion.put("__UP_" + pref.getKey() + "__", pref.getValue());
                    }
                }
            }
        }
        else
        {
            UserPrefs prefs = userprefsManager.get(gadget, moduleID);
            if(prefs!=null)
            {
                for(String key : prefs.keySet())
                {
                    String value=prefs.get(key);
                    getVariablesubstituion.put("__UP_" + key + "__", value);
                }
            }
        }
        return getVariablesubstituion;
    }
    public void saveUserPref(Gadget gadget, String moduleId, String key, String value, WebSite site)
    {
        if (user == null)
        {
            if (!userprefsManager.contains(gadget, moduleId))
            {                
                userprefsManager.add(new UserPrefs(gadget, moduleId));
            }
            UserPrefs prefs = userprefsManager.get(gadget, moduleId);
            if (key != null && value != null)
            {
                prefs.put(key, value);
            }
        }
        else
        {
            PersonalizedGadged pgadget = null;
            User _user = site.getUserRepository().getUser(user);
            if (_user != null)
            {
                Iterator<PersonalizedGadged> preferences = PersonalizedGadged.ClassMgr.listPersonalizedGadgedByUser(_user, site);
                while (preferences.hasNext())
                {
                    PersonalizedGadged personalizedGadged = preferences.next();
                    if (personalizedGadged.getGadget() != null)
                    {
                        String urltest = personalizedGadged.getGadget().getUrl();
                        if (urltest.equals(gadget.getUrl()) && personalizedGadged.getId().equals(moduleId))
                        {
                            pgadget = personalizedGadged;
                            break;
                        }
                    }
                }
                if (pgadget != null && value != null)
                {
                    boolean exists = false;
                    Iterator<UserPref> userprefs = pgadget.listUserPrefses();
                    while (userprefs.hasNext())
                    {
                        UserPref pref = userprefs.next();
                        if (pref.getKey().equals(key))
                        {
                            pref.setValue(value);
                            exists = true;
                        }
                    }
                    if (!exists)
                    {
                        UserPref pref = UserPref.ClassMgr.createUserPref(site);
                        pref.setKey(key);
                        pref.setValue(value);
                        pgadget.addUserPrefs(pref);
                    }
                }
            }
        }
    }

    public JSONObject getJSONUserPrefs(Gadget gadget, String moduleId, WebSite site)
    {
        Set<String> keys = new HashSet<String>();
        //{"gadgets":[{"userPrefs":{"uid":{"default":"","orderedEnumValues":[],"enumValues":{},"type":"hidden","displayName":"uid"},"sign":{"default":"All Signs","orderedEnumValues":[],"enumValues":{},"type":"hidden","displayName":"sign"},"showSocialAccessMessage":{"default":"true","orderedEnumValues":[],"enumValues":{},"type":"hidden","displayName":"showSocialAccessMessage"},"selectedTab":{"default":"0","orderedEnumValues":[],"enumValues":{},"type":"hidden","displayName":"selectedTab"},"month":{"default":"","orderedEnumValues":[{"value":"1","displayValue":"January"},{"value":"2","displayValue":"February"},{"value":"3","displayValue":"March"},{"value":"4","displayValue":"April"},{"value":"5","displayValue":"May"},{"value":"6","displayValue":"June"},{"value":"7","displayValue":"July"},{"value":"8","displayValue":"August"},{"value":"9","displayValue":"September"},{"value":"10","displayValue":"October"},{"value":"11","displayValue":"November"},{"value":"12","displayValue":"December"}],"enumValues":{"11":"November","12":"December","3":"March","2":"February","10":"October","1":"January","7":"July","6":"June","5":"May","4":"April","9":"September","8":"August"},"type":"enum","displayName":"Birth month"},"lastEdited":{"default":"0","orderedEnumValues":[],"enumValues":{},"type":"hidden","displayName":"lastEdited"},"showSignInMessage":{"default":"true","orderedEnumValues":[],"enumValues":{},"type":"hidden","displayName":"showSignInMessage"},"year":{"default":"","orderedEnumValues":[],"enumValues":{},"type":"string","displayName":"Birth year"},"showPopup":{"default":"0","orderedEnumValues":[],"enumValues":{},"type":"hidden","displayName":"showPopup"},"day":{"default":"","orderedEnumValues":[],"enumValues":{},"type":"string","displayName":"Birth date"}},"iframeUrl":"//http://localhost:8080/gadgets/ifr?url=http%3A%2F%2Fwww.google.com%2Fig%2Fmodules%2Fhoroscope.xml&container=default&view=%25view%25&lang=%25lang%25&country=%25country%25&debug=%25debug%25&nocache=%25nocache%25&v=b4ea67fd7aa33422aa257ee3f534daf0&st=%25st%25#up_day=%25up_day%25&up_month=%25up_month%25&up_year=%25up_year%25&up_selectedTab=%25up_selectedTab%25&up_sign=%25up_sign%25&up_uid=%25up_uid%25&up_lastEdited=%25up_lastEdited%25&up_showSignInMessage=%25up_showSignInMessage%25&up_showSocialAccessMessage=%25up_showSocialAccessMessage%25&up_showPopup=%25up_showPopup%25","author":"csindia-gadgets","title":"Today's Horoscope","titleUrl":"","height":200,"featureDetails":{"views":{"parameters":{},"required":true},"dynamic-height":{"parameters":{},"required":true},"setprefs":{"parameters":{},"required":true},"core":{"parameters":{},"required":true},"settitle":{"parameters":{},"required":true},"minimessage":{"parameters":{},"required":true},"tabs":{"parameters":{},"required":true},"opensocial-0.8":{"parameters":{},"required":true}},"features":["dynamic-height","views","setprefs","core","settitle","minimessage","opensocial-0.8","tabs"],"showStats":false,"screenshot":"http://www.google.com/ig/modules/horoscope/horoscope.png","moduleId":1,"authorEmail":"googlemodules+gadgetfactory_horoscope+201010081@google.com","singleton":false,"width":0,"links":{},"authorLink":"","url":"http://www.google.com/ig/modules/horoscope.xml","scaling":false,"directoryTitle":"Daily Horoscopes","thumbnail":"http://www.google.com/ig/modules/horoscope/horoscope-thm.png","scrolling":false,"views":{"home":{"preferredHeight":0,"quirks":true,"type":"html","preferredWidth":0},"default":{"preferredHeight":0,"quirks":true,"type":"html","preferredWidth":0},"canvas":{"preferredHeight":0,"quirks":true,"type":"html","preferredWidth":0},"profile":{"preferredHeight":0,"quirks":true,"type":"html","preferredWidth":0}},"categories":["",""],"authorPhoto":"","showInDirectory":false}]}
        JSONObject getJSONUserPrefs = new JSONObject();
        if (user == null)
        {
            UserPrefs prefs = userprefsManager.get(gadget, moduleId);
            if (prefs != null)
            {
                for (String key : prefs.keySet())
                {
                    String value = prefs.get(key);
                    JSONObject JSONData = new JSONObject();
                    try
                    {
                        JSONData.put("value", value);
                        getJSONUserPrefs.put(key, JSONData);
                        keys.add(key);
                    }
                    catch (Exception e)
                    {
                        log.error(e);
                    }
                }
            }
        }
        else
        {
            User _user = site.getUserRepository().getUser(user);
            if (_user != null)
            {
                Iterator<PersonalizedGadged> preferences = PersonalizedGadged.ClassMgr.listPersonalizedGadgedByUser(_user, site);
                while (preferences.hasNext())
                {
                    PersonalizedGadged personalizedGadged = preferences.next();
                    if (personalizedGadged.getGadget().getURI().equals(gadget.getURI()) && personalizedGadged.getId().equals(moduleId))
                    {
                        GenericIterator<UserPref> list = personalizedGadged.listUserPrefses();
                        while (list.hasNext())
                        {
                            UserPref pref = list.next();
                            String key = pref.getKey();
                            String value = pref.getValue();
                            JSONObject JSONData = new JSONObject();
                            try
                            {
                                JSONData.put("value", value);
                                getJSONUserPrefs.put(key, JSONData);
                                keys.add(key);
                            }
                            catch (Exception e)
                            {
                                log.error(e);
                            }
                        }
                        break;
                    }
                }
            }
        }
        Document doc = gadget.getDocument();
        NodeList userPrefs = doc.getElementsByTagName("UserPref");
        for (int i = 0; i < userPrefs.getLength(); i++)
        {
            if (userPrefs.item(i) instanceof Element)
            {
                Element userPref = (Element) userPrefs.item(i);
                String name = userPref.getAttribute("name");
                if (!keys.contains(name))
                {
                    String default_value = userPref.getAttribute("default_value");
                    String displayName = userPref.getAttribute("display_name");
                    String dataType = "string";
                    if (userPref.getAttribute("datatype") != null && !userPref.getAttribute("datatype").equals(""))
                    {
                        dataType = userPref.getAttribute("datatype");
                    }
                    JSONObject JSONDefault = new JSONObject();
                    //{"default":"","orderedEnumValues":[{"value":"1","displayValue":"January"},{"value":"2","displayValue":"February"},{"value":"3","displayValue":"March"},{"value":"4","displayValue":"April"},{"value":"5","displayValue":"May"},{"value":"6","displayValue":"June"},{"value":"7","displayValue":"July"},{"value":"8","displayValue":"August"},{"value":"9","displayValue":"September"},{"value":"10","displayValue":"October"},{"value":"11","displayValue":"November"},{"value":"12","displayValue":"December"}],"enumValues":{"11":"November","12":"December","3":"March","2":"February","10":"October","1":"January","7":"July","6":"June","5":"May","4":"April","9":"September","8":"August"},"type":"enum","displayName":"Birth month"}
                    try
                    {


                        JSONDefault.put("default", default_value);
                        JSONArray orderedEnumValues = new JSONArray();
                        JSONObject enumValues = new JSONObject();
                        if ("enum".equals(dataType))
                        {
                            NodeList nodelist_enumValues = userPref.getElementsByTagName("EnumValue");
                            for (int j = 0; j < nodelist_enumValues.getLength(); j++)
                            {
                                Element enumValue = (Element) nodelist_enumValues.item(j);
                                String value = enumValue.getAttribute("value");
                                String dp = enumValue.getAttribute("display_value");
                                JSONObject ordenadedValue = new JSONObject();
                                ordenadedValue.put("value", value);
                                ordenadedValue.put("displayValue", dp);
                                orderedEnumValues.put(ordenadedValue);
                                enumValues.put(value, dp);
                            }
                        }

                        JSONDefault.put("orderedEnumValues", orderedEnumValues);


                        JSONDefault.put("enumValues", enumValues);
                        JSONDefault.put("type", dataType);
                        JSONDefault.put("displayName", displayName);
                        getJSONUserPrefs.put(name, JSONDefault);
                    }
                    catch (Exception e)
                    {
                        log.error(e);
                    }
                }
                /*String dataType = "string";
                if (userPref.getAttribute("datatype") != null && !userPref.getAttribute("datatype").equals(""))
                {
                dataType = userPref.getAttribute("datatype");
                }

                String default_value = userPref.getAttribute("default_value");
                String displayName = userPref.getAttribute("display_name");*/
            }
        }
        return getJSONUserPrefs;
    }
}
