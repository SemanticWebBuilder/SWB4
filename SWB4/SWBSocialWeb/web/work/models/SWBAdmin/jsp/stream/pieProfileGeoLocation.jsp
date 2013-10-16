<%-- 
    Document   : profileGeoLocation
    Created on : 07-oct-2013, 19:51:58
    Author     : gabriela.rosales
--%>
<%@page import="com.sun.accessibility.internal.resources.accessibility"%>
<%@page contentType="text/json" pageEncoding="UTF-8"%> 
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.social.*"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.SWBPortal"%> 
<%@page import="org.semanticwb.platform.SemanticProperty"%>
<%@page import="org.semanticwb.portal.api.*"%>
<%@page import="org.json.*"%>
<%@page import="org.semanticwb.social.Country"%>
<%@page import="java.util.*"%>
<%@page import="java.util.Collection"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Arrays"%>



<%!
    JSONArray getObject(SemanticObject semObj, String lang, String idModel) throws Exception {

        int neutrals = 0, positives = 0, negatives = 0;
        Iterator<PostIn> itObjPostIns = null;


        if (semObj.getGenericInstance() instanceof Stream) {
            Stream stream = (Stream) semObj.getGenericInstance();
            itObjPostIns = stream.listPostInStreamInvs();
        } else if (semObj.getGenericInstance() instanceof SocialTopic) {
            SocialTopic socialTopic = (SocialTopic) semObj.getGenericInstance();
            itObjPostIns = PostIn.ClassMgr.listPostInBySocialTopic(socialTopic, socialTopic.getSocialSite());
        }

        SWBModel model = WebSite.ClassMgr.getWebSite(idModel);
        Iterator c = CountryState.ClassMgr.listCountryStates(model);
        Iterator coun = Country.ClassMgr.listCountries(model);


        HashMap mapCountry = new HashMap();
        while (coun.hasNext()) {
            Country cou = (Country) coun.next();
            mapCountry.put(cou.getTitle(), mapCountry.containsKey(cou.getTitle()) ? (Integer.parseInt(mapCountry.get(cou.getTitle()).toString()) + 1) : 0);
        }
        HashMap map = new HashMap();
        int size = 1;
        /*
         while (c.hasNext()) {
         CountryState css = (CountryState) c.next();            
         listOne.add(css.getTitle());            
         System.out.println("css" + css.getTitle());
         map.put(css.getTitle(), map.containsKey(css.getTitle())? (Integer.parseInt(map.get(css.getTitle()).toString()) + 1) : 0);
         }
        
         Iterator i = map.entrySet().iterator();
        
         while(i.hasNext()){
         Map.Entry e = (Map.Entry)i.next();
         System.out.println("KEYY"+e.getKey() + " \t VALUE  " + e.getValue());
        
         }*/



        JSONArray node = new JSONArray();

        ArrayList<String> geoLocation = new ArrayList<String>();
        while (itObjPostIns.hasNext()) {
            PostIn postIn = itObjPostIns.next();


            CountryState key = postIn.getGeoStateMap();
            map.put(key, map.containsKey(key) ? (Integer.parseInt(map.get(key).toString()) + 1) : 1);

            if (postIn.getPostSentimentalType() == 0) {
                neutrals++;
            } else if (postIn.getPostSentimentalType() == 1) {
                positives++;
            } else if (postIn.getPostSentimentalType() == 2) {
                negatives++;
            }
            size++;
        }
        float intTotalVotos = map.size() - 1;
        // System.out.println("TOTAL DE VOTOS: " + intTotalVotos);

        //Positivo
        //float intPorcentajePositive = ((float) positives * 100) / (float) intTotalVotos;
        Iterator i = map.entrySet().iterator();
        while (i.hasNext()) {
            Map.Entry e = (Map.Entry) i.next();
            if (e.getKey() != null) {
                CountryState csss = (CountryState) e.getKey();
                System.out.println("cs: " + csss.getDescription());
                System.out.println("KEYY" + e.getKey() + " \t VALUE  " + e.getValue());
                JSONObject node1 = new JSONObject();
                node1.put("label", csss.getTitle(lang));
                node1.put("value1", "" + e.getValue());

                float number = Float.parseFloat(e.getValue().toString());
                node1.put("value2", "" + round((float) number * 100) / (float) intTotalVotos);
                if (positives > negatives && positives > neutrals) {
                    System.out.println("entro a positivos");
                    node1.put("color", "#86c440");
                } else if (negatives > neutrals) {
                    System.out.println("entro a negatios");
                    node1.put("color", "#990000");
                } else {
                    System.out.println("entro a neutrales");
                    node1.put("color", "#eae8e3");
                }
                node1.put("label2", csss.getTitle() + " " + e.getValue());
                node1.put("chartclass", "possClass");
                node.put(node1);
            } else {

                JSONObject node3 = new JSONObject();
                node3.put("label", "Neutros");
                node3.put("value1", "0");
                node3.put("value2", "100");
                node3.put("color", "#eae8e3");
                node3.put("chartclass", "neuClass");
                node3.put("label2", "Sin datos para procesar");
                node.put(node3);
            }
        }
        
        if(map.isEmpty()){
            
            JSONObject node3 = new JSONObject();
                node3.put("label", "Neutros");
                node3.put("value1", "0");
                node3.put("value2", "100");
                node3.put("color", "#eae8e3");
                node3.put("chartclass", "neuClass");
                node3.put("label2", "Sin datos para procesar");
                node.put(node3);
        
        }

        return node;
    }

    public double round(float number) {
        return Math.rint(number * 100) / 100;
    }
%>
<%

    if (request.getParameter("objUri") != null) {
        SemanticObject semObj = SemanticObject.getSemanticObject(request.getParameter("objUri"));
        String lang = request.getParameter("lang");
        String idModel = request.getParameter("idModel");
        out.println(getObject(semObj, lang, idModel));
    }
%>