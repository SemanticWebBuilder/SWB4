<%-- 
    Document   : checkPostIns
    Created on : 02-jul-2013, 12:50:57
    Author     : jorge.jimenez
--%>
<%@page import="java.io.*"%>
<%@page import="org.semanticwb.social.*"%>
<%@page import="org.semanticwb.SWBUtils"%> 
<%@page import="java.util.*"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.*,org.semanticwb.platform.*,org.semanticwb.portal.*,org.semanticwb.model.*,java.util.*,org.semanticwb.base.util.*"%>

<%
    Iterator <SocialSite> itWebSites=SocialSite.ClassMgr.listSocialSites();
    while(itWebSites.hasNext())
    {
        SocialSite wsite=itWebSites.next();
        //System.out.println("wsite0:"+wsite);
        //if(wsite instanceof SocialSite) 
        {
            
            System.out.println("wsiteGe:"+wsite);
            int i=0;
            /*
            Iterator <PostIn> itPostIns=PostIn.ClassMgr.listPostIns(wsite);
            while(itPostIns.hasNext())
            {
                PostIn postIn=itPostIns.next();
                out.println("postIn:"+postIn);
                i++;
            }
            out.println("\n\n I Count:"+i);
            *  */
            /*
            i=0;
            Iterator <MessageIn> itMessageIns=MessageIn.ClassMgr.listMessageIns(wsite);
            while(itMessageIns.hasNext())
            {    
                MessageIn messageIn=itMessageIns.next();
                System.out.println("messageIn:"+messageIn);
                i++;
            }
            System.out.println("I Messages Count:"+i);
            
            i=0;
            Iterator <PhotoIn> itPhotoIns=PhotoIn.ClassMgr.listPhotoIns(wsite);
            while(itPhotoIns.hasNext())
            {    
                PhotoIn photoIn=itPhotoIns.next();
                System.out.println("messageIn:"+photoIn);
                i++;
            }
            System.out.println("I Photos Count:"+i);
            */
            /*
            i=0;
            Iterator<SocialNetworkUser> itSocialNetUsers=SocialNetworkUser.ClassMgr.listSocialNetworkUsers(wsite);
            while(itSocialNetUsers.hasNext())
            {
                SocialNetworkUser socialNetUser=itSocialNetUsers.next();
                //socialNetUser.remove(); 
                out.println("socialNetUser:"+socialNetUser.getSnu_name());
                //if(socialNetUser.getSnu_name().equals("@George24Mx"))
                {
                    System.out.println("PostIn de este cuate:"+socialNetUser.getSnu_name()+", postIns:");
                    Iterator<PostIn> itPostIns=PostIn.ClassMgr.listPostInByPostInSocialNetworkUser(socialNetUser);
                    while(itPostIns.hasNext())
                    {
                        PostIn postIn=itPostIns.next();
                        System.out.println("De este cuate:"+postIn);
                        //postIn.remove();
                    }
                }
                //socialNetUser.remove();
                i++;
            }
            */
            
            i=0;
            
            Iterator<PostOut> itPostOuts=PostOut.ClassMgr.listPostOuts(wsite);
            while(itPostOuts.hasNext())
            {
                i++;
                PostOut postOut=itPostOuts.next();
                out.println("postOut:"+postOut+"\n\r");
                //postOut.remove();
            }
            out.println("i:"+i);
            
            i=0;
            
            Iterator<PostOutNet> itPostOutsNets=PostOutNet.ClassMgr.listPostOutNets(wsite);
            while(itPostOutsNets.hasNext())
            {
                i++;
                PostOutNet postOutNet=itPostOutsNets.next();
                out.println("postOutNet"+postOutNet+"\n\r");
                //postOutNet.remove();
            }
            out.println("i:"+i);
        }
    }
%>