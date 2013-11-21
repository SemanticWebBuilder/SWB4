<%-- 
    Document   : profileGeoLocation
    Created on : 07-oct-2013, 19:51:58
    Author     : gabriela.rosales
--%>
<%@page import="java.util.Map.Entry"%>
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

        int neutrals = 0, positives = 0, negatives = 0, totalPost = 0;
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



        JSONArray node = new JSONArray();
        ArrayList<String> geoLocation = new ArrayList<String>();
        String cad = "1,0,0,0";
        while (itObjPostIns.hasNext()) {

            totalPost++;
            PostIn postIn = itObjPostIns.next();
            CountryState key = postIn.getGeoStateMap();
            //System.out.println("++++++++++++" + key);
            String title = "";
            if (key == null) {
                title = "No definido";
            } else {
                title = key.getTitle();

            }
            map.put(title, map.containsKey(title) ? cad(map.get(title).toString(), postIn) : "1,1,0,0");



            size++;
        }
        float intTotalVotos = map.size();

        Iterator i = map.entrySet().iterator();
        while (i.hasNext()) {
            Map.Entry e = (Map.Entry) i.next();
            //System.out.println("eeeeeeeee" + e.getKey());
            //if (e.getKey() != null) {
            // CountryState csss = (CountryState) e.getKey();
            //System.out.println("cs: " + e.getValue());
            String caden = (String) e.getValue();
            String[] phrasesStream = caden.split(",");
            neutrals = Integer.parseInt(phrasesStream[1]);
            positives = Integer.parseInt(phrasesStream[2]);
            negatives = Integer.parseInt(phrasesStream[3]);

            //System.out.println("KEYY" + e.getKey() + " \t VALUE  " + e.getValue());
            JSONObject node1 = new JSONObject();
            String label = e.getKey().toString();
            node1.put("label", label);
            node1.put("value1", "" + phrasesStream[0]);

            float number = Float.parseFloat(phrasesStream[0]);
            if (positives > negatives && positives > neutrals) {
                number = Float.parseFloat(phrasesStream[1]);
                node1.put("value2", "" + round((float) number * 100) / (float) totalPost);
                node1.put("color", "#86c440");
            } else if (negatives > neutrals) {
                number = Float.parseFloat(phrasesStream[2]);
                node1.put("value2", "" + round((float) number * 100) / (float) totalPost);
                node1.put("color", "#990000");
            } else {
                number = Float.parseFloat(phrasesStream[0]);
                node1.put("value2", "" + round((float) number * 100) / (float) totalPost);

                node1.put("color", "#eae8e3");
            }
            node1.put("label2", e.getKey() + " " + phrasesStream[0] + " Neutros: " + neutrals + " Positivos: " + positives + " Negativos: " + negatives);
            node1.put("chartclass", "possClass");
            node1.put("label3", "Total de Post: " + totalPost);

            node.put(node1);
            //  } else {

            // JSONObject node3 = new JSONObject();
            //node3.put("label", "Neutros");
            //node3.put("value1", "0");
            // node3.put("value2", "100");
            //  node3.put("color", "#eae8e3");
            // node3.put("chartclass", "neuClass");
            //  node3.put("label2", "Sin datos para procesar");
            //  node3.put("label3", "Total de Post: " + totalPost);

            //  node.put(node3);
            // }
        }



        if (map.isEmpty()) {

            JSONObject node3 = new JSONObject();
            node3.put("label", "Neutros");
            node3.put("value1", "0");
            node3.put("value2", "100");
            node3.put("color", "#eae8e3");
            node3.put("chartclass", "neuClass");
            node3.put("label2", "Sin datos para procesar");
            node3.put("label3", "Total de Post: " + totalPost);

            node.put(node3);

        }

        return node;
    }

    public double round(float number) {
        return Math.rint(number * 100) / 100;
    }

    public String cad(String cadena, PostIn pi) {
        //System.out.println("----->ENTRO" + cadena);
        Integer total;
        Integer positives = 0;
        Integer negatives = 0;
        Integer neutrals = 0;
        String[] phrasesStream = cadena.split(",");


        total = Integer.parseInt(phrasesStream[0]) + 1;
        //System.out.println("total" + total);

        if (pi.getPostSentimentalType() == 0) {
            //System.out.println("NEUTROS" + Integer.parseInt(phrasesStream[1]));
            neutrals = Integer.parseInt(phrasesStream[1]) + 1;
            //System.out.println("total NEUTROS" + neutrals);
        } else {
            //System.out.println("neutros" + Integer.parseInt(phrasesStream[1]));
            neutrals = Integer.parseInt(phrasesStream[1]);
        }
        if (pi.getPostSentimentalType() == 1) {
            //System.out.println("POSITIVOS" + Integer.parseInt(phrasesStream[2]));
            positives = Integer.parseInt(phrasesStream[2]) + 1;
        } else {
            //System.out.println("positivos" + Integer.parseInt(phrasesStream[2]));
            positives = Integer.parseInt(phrasesStream[2]);
        }
        if (pi.getPostSentimentalType() == 2) {
            //System.out.println("NEGATIVOS" + Integer.parseInt(phrasesStream[3]));
            negatives = Integer.parseInt(phrasesStream[3]) + 1;
        } else {
            //System.out.println("negativos" + Integer.parseInt(phrasesStream[3]));
            negatives = Integer.parseInt(phrasesStream[3]);
        }
        String cade = "";
        positives.toString();
        cade += total.toString() + ",";
        cade += neutrals.toString() + ",";
        cade += positives.toString() + ",";
        cade += negatives.toString();

        //System.out.println("cade" + cade);
        return cade;
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