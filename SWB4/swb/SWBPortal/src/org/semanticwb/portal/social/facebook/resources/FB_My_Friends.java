/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.social.facebook.resources;

import org.semanticwb.portal.social.facebook.*;
import org.semanticwb.portal.resources.*;
import com.google.code.facebookapi.FacebookXmlRestClient;
import com.google.code.facebookapi.IFacebookRestClient;
import com.google.code.facebookapi.ProfileField;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 *
 * @author Jorge Jiménez
 */
public class FB_My_Friends extends GenericAdmResource {

    private static Logger log = SWBUtils.getLogger(FB_MyPhotos.class);
    private static final String FACEBOOK_USER_CLIENT = "facebook.user.client";
    private static String API_KEY = "";
    private static String SECRET_KEY = "";

    /**
     * @param base
     */
    @Override
    public void setResourceBase(Resource base) {
        try {
            super.setResourceBase(base);
            API_KEY = base.getAttribute("api_key");
            SECRET_KEY = base.getAttribute("secret_key");
        } catch (Exception e) {
            log.error("Error while setting resource base: " + base.getId() + "-" + base.getTitle(), e);
        }
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        try {
            PrintWriter out = response.getWriter();
            HttpSession session = request.getSession(true);
            IFacebookRestClient<Document> userClient = getUserClient(session);
            if (userClient != null) {
                ArrayList<FB_User> aUserData=new ArrayList();
                StringBuffer ret = new StringBuffer();
                if (userClient.friends_get() != null) {
                    NodeList nlistUsers = userClient.friends_get().getElementsByTagName("uid");
                    for (int i = 0; i < nlistUsers.getLength(); i++) {
                        FB_User userData=new FB_User();
                        Long luid = new Long(nlistUsers.item(i).getFirstChild().getNodeValue());
                        ArrayList<Long> uids = new ArrayList<Long>(1);
                        uids.add(luid);
                        List<ProfileField> fields = Arrays.asList(ProfileField.values());
                        userClient = new FacebookXmlRestClient(API_KEY, SECRET_KEY, userClient.getCacheSessionKey());
                        Document userInfoDoc = userClient.users_getInfo(uids, fields);
                        if(userInfoDoc.getElementsByTagName("user").getLength()>0)
                        {
                            NodeList nUsrNodes=userInfoDoc.getElementsByTagName("user").item(0).getChildNodes();
                            for(int y=0;y<nUsrNodes.getLength();y++){
                                if(nUsrNodes.item(y).getNodeName().equals("name")) {
                                    userData.setName(nUsrNodes.item(y).getFirstChild().getNodeValue());
                                }
                                else if (nUsrNodes.item(y).getNodeName().equals("profile_url") && nUsrNodes.item(y).getFirstChild() != null) {
                                    userData.setProfile_url( nUsrNodes.item(y).getFirstChild().getNodeValue());
                                }
                                else if (nUsrNodes.item(y).getNodeName().equals("pic_with_logo") && nUsrNodes.item(y).getFirstChild() != null) {
                                    userData.setPic_with_logo(nUsrNodes.item(y).getFirstChild().getNodeValue());
                                }else if (nUsrNodes.item(y).getNodeName().equals("birthday") && nUsrNodes.item(y).getFirstChild() != null) {
                                    userData.setBirthday(nUsrNodes.item(y).getFirstChild().getNodeValue());
                                }
                                else if(nUsrNodes.item(y).getNodeName().equals("hometown_location")) {
                                    NodeList nHomeTown=nUsrNodes.item(y).getChildNodes();
                                    for(int z=0;z<nHomeTown.getLength();z++){
                                        if(nHomeTown.item(z).getNodeName().equals("city") && nHomeTown.item(z).getFirstChild()!=null) userData.setCity(nHomeTown.item(z).getFirstChild().getNodeValue());
                                        else if(nHomeTown.item(z).getNodeName().equals("state") && nHomeTown.item(z).getFirstChild()!=null) userData.setState(nHomeTown.item(z).getFirstChild().getNodeValue());
                                        else if(nHomeTown.item(z).getNodeName().equals("country") && nHomeTown.item(z).getFirstChild()!=null) userData.setCountry(nHomeTown.item(z).getFirstChild().getNodeValue());
                                    }
                                }
                            }
                            aUserData.add(userData);
                        }
                    }
                    boolean flag=false;
                    int cont=0;
                    out.println("<table>");
                    out.println("<font size=\"10\">");
                    Iterator<FB_User> itusers=aUserData.iterator();
                    while(itusers.hasNext()){
                        FB_User user=itusers.next();
                        if(!flag && cont==0) out.println("<tr>");
                        cont++;
                        out.println("<td>");
                        if(user.getPic_with_logo()!=null)
                        {
                            out.println("<a href=\""+user.getProfile_url()+"\" target=\"_new\" text-decoration:none><img src=\""+user.getPic_with_logo()+"\"/></a><br>");
                            if(user.getName()!=null) out.println(user.getName()+"<br>");
                            if(user.getBirthday()!=null) out.println(user.getBirthday()+"<br>");
                            if(user.getCity()!=null) out.println(user.getCity()+"<br>");
                            //if(user.getState()!=null) out.println(user.getState()+"<br>");
                            //if(user.getCountry()!=null) out.println(user.getCountry()+"<br>");
                        }
                        out.println("</td>");
                        if(cont==4) {
                            out.println("</tr>");
                            cont=0;
                            flag=false;
                        }
                    }
                    if(flag) out.println("</tr>");
                    out.println("</font>");
                    out.println("</table>");
                }
            }
        } catch (Exception e) {
            log.error(e);
        }
    }

    static FacebookXmlRestClient getUserClient(HttpSession session) {
        return (FacebookXmlRestClient) session.getAttribute(FACEBOOK_USER_CLIENT);
    }
}


class FB_User{

    String name=null;
    String pic=null;
    String city=null;
    String state=null;
    String country=null;
    String profile_url=null;
    String pic_with_logo=null;
    String birthday=null;

    public void setName(String name){
        this.name=name;
    }
    public void setPic(String pic){
        this.pic=pic;
    }
    public void setPic_with_logo(String pic_with_logo){
        this.pic_with_logo=pic_with_logo;
    }
    public void setCity(String city){
        this.city=city;
    }
    public void setState(String state){
        this.state=state;
    }
    public void setCountry(String country){
        this.country=country;
    }
    public void setProfile_url(String profile_url){
        this.profile_url=profile_url;
    }
    public void setBirthday(String birthday){
        this.birthday=birthday;
    }

    //gets

    public String getName(){
        return name;
    }

    public String getPic(){
        return pic;
    }

    public String getPic_with_logo(){
        return pic_with_logo;
    }

    public String getCity(){
        return city;
    }

    public String getState(){
        return state;
    }

    public String getCountry(){
        return country;
    }

    public String getProfile_url(){
        return profile_url;
    }

    public String getBirthday(){
        return birthday;
    }


}
