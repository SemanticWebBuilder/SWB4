<%-- 
    Document   : 
    Created on : 08-ago-2013, 11:51:58
    Author     : 
--%>
<%@page import="java.lang.reflect.Array"%>
<%@page import="java.text.ParseException"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
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
<%@page import="java.util.*"%> 
<%@page import="java.util.Calendar"%> 



<%!
    JSONArray getObject(SemanticObject semObj, String lang, HttpServletRequest request) throws Exception {
        //WebSite wsite=WebSite.ClassMgr.getWebSite(stream.getSemanticObject().getModel().getName());

        Iterator<PostIn> itObjPostIns = null;
        if (semObj.getGenericInstance() instanceof Stream) {
            Stream stream = (Stream) semObj.getGenericInstance();
            itObjPostIns = stream.listPostInStreamInvs();
        } else if (semObj.getGenericInstance() instanceof SocialTopic) {
            SocialTopic socialTopic = (SocialTopic) semObj.getGenericInstance();
            itObjPostIns = PostIn.ClassMgr.listPostInBySocialTopic(socialTopic, socialTopic.getSocialSite());
        }

        if (itObjPostIns == null) {

            JSONArray node = new JSONArray();
            return node;
        }
        java.util.Date date = null;
        Calendar calendario = Calendar.getInstance();


        String anio = request.getParameter("selectedAnio");
        if (anio.equals("")) {
            anio = String.valueOf(calendario.get(Calendar.YEAR));
        }

        String selectedAnio = anio;
        String selectAnio = request.getParameter("selectAnio"); //== "2013" ? "" : request.getParameter("selectAnio");
        String selectMonth = request.getParameter("selectMes") == null ? "" : request.getParameter("selectMes");


        JSONArray node = new JSONArray();
        ArrayList listPostIn = new ArrayList();
        Iterator iListPostIn;


        if (!selectedAnio.equals("") && selectMonth.equals("")) {
            int neutrals = 0, positives = 0, negatives = 0;

            int selectedA = Integer.parseInt(selectedAnio);
            int nPostIn = 0;
            int[][] months = new int[12][5];

            while (itObjPostIns.hasNext()) {
                PostIn postIn = itObjPostIns.next();

                if (postIn != null) {
                    if (postIn.getPostSentimentalType() == 0) {
                        neutrals++;
                    } else if (postIn.getPostSentimentalType() == 1) {
                        positives++;
                    } else if (postIn.getPostSentimentalType() == 2) {
                        negatives++;
                    }
                    if (postIn.getPi_createdInSocialNet() != null) {
                        date = postIn.getPi_createdInSocialNet();
                    }
                    nPostIn++;

                }
                calendario.setTime(date);
                int year = calendario.get(Calendar.YEAR);

                if (year == selectedA) {

                    months[calendario.get(Calendar.MONTH)][0] += 1;
                    months[calendario.get(Calendar.MONTH)][1] += neutrals;
                    months[calendario.get(Calendar.MONTH)][2] += negatives;
                    months[calendario.get(Calendar.MONTH)][3] += positives;
                    months[calendario.get(Calendar.MONTH)][4] = year;
                }



                neutrals = 0;
                negatives = 0;
                positives = 0;
                listPostIn.add(date);



            }

            // iListPostIn = listPostIn.iterator();

            int meses = 1;
            for (int idx = 0; idx < months.length; idx++) {
                if (meses < 13) {
                    Object elem = months[idx][0];
                    int neutrals_sYear = months[idx][1];
                    int negatives_sYear = months[idx][2];
                    int positives_sYear = months[idx][3];
                    int year = months[idx][4];
                    JSONObject node1 = new JSONObject();
                    node1.put("day", "");
                    node1.put("month", meses);
                    node1.put("month2", meses);
                    node1.put("year", year);
                    node1.put("post", elem);
                    node1.put("neutrals", neutrals_sYear);
                    node1.put("positives", positives_sYear);
                    node1.put("negatives", negatives_sYear);
                    node1.put("chartclass", "possClass");
                    node1.put("x", months.length);
                    double totalPost = (.50) * (nPostIn);
                    node1.put("totalPost", nPostIn + totalPost);
                    node1.put("typeX", "Meses");
                    node.put(node1);
                    meses++;
                }
            }





        } else if (!selectAnio.equals("") && !selectMonth.equals("")) {


            int selectedYear = Integer.parseInt(selectAnio);
            int selectMonthint = Integer.parseInt(selectMonth);


            int nPostIn = 0;
            Calendar cal = new GregorianCalendar(selectedYear, selectMonthint - 1, 1);
            // Get the number of days in that month 
            int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH); // 28


            int neutrals_ = 0, positives_ = 0, negatives_ = 0;
            int[][] dias = new int[days][7];


            while (itObjPostIns.hasNext()) {
                PostIn postIn = itObjPostIns.next();
                if (postIn != null) {
                    if (postIn.getPostSentimentalType() == 0) {
                        neutrals_++;
                    } else if (postIn.getPostSentimentalType() == 1) {
                        positives_++;
                    } else if (postIn.getPostSentimentalType() == 2) {
                        negatives_++;
                    }
                    if (postIn.getPi_createdInSocialNet() != null) {
                        date = postIn.getPi_createdInSocialNet();
                    }
                    nPostIn++;
                }

                calendario.setTime(date);

                int year = calendario.get(Calendar.YEAR);

                int comMonth = calendario.get(Calendar.MONTH);

                if (selectMonthint == comMonth + 1 && selectedYear == year) {
                    dias[calendario.get(Calendar.DAY_OF_MONTH) - 1][0] += 1;
                    dias[calendario.get(Calendar.DAY_OF_MONTH) - 1][1] += neutrals_;
                    dias[calendario.get(Calendar.DAY_OF_MONTH) - 1][2] += negatives_;
                    dias[calendario.get(Calendar.DAY_OF_MONTH) - 1][3] += positives_;
                    dias[calendario.get(Calendar.DAY_OF_MONTH) - 1][4] = comMonth;
                    dias[calendario.get(Calendar.DAY_OF_MONTH) - 1][5] = year;
                    dias[calendario.get(Calendar.DAY_OF_MONTH) - 1][6] = calendario.get(Calendar.DAY_OF_MONTH);

                }
                neutrals_ = 0;
                negatives_ = 0;
                positives_ = 0;
                listPostIn.add(date);
                //System.out.println("es el mismo año");


            }


            int d = 1;
            for (int idx = 0; idx < dias.length; idx++) {
                if (d <= days) {
                    Object elem = dias[idx][0];
                    int neutrals_s = dias[idx][1];
                    int negatives_s = dias[idx][2];
                    int positives_s = dias[idx][3];
                    int mes = dias[idx][4];
                    int year = dias[idx][5];
                    int dia = dias[idx][6];

                    JSONObject node1 = new JSONObject();
                    node1.put("day", dia);
                    node1.put("month", (idx + 1));
                    node1.put("month2", mes);
                    node1.put("year", year);
                    node1.put("neutrals", neutrals_s);
                    node1.put("positives", positives_s);
                    node1.put("negatives", negatives_s);
                    node1.put("post", elem);
                    node1.put("chartclass", "possClass");
                    node1.put("x", days);
                    double totalPost = (.50) * (nPostIn);
                    node1.put("totalPost", nPostIn + totalPost);
                    node1.put("typeX", "Dias");
                    node.put(node1);
                    d++;
                }
            }

            iListPostIn = listPostIn.iterator();


        }


        return node;

    }


%>
<%

    if (request.getParameter("selectedAnio") != null) {
        SemanticObject semObj = SemanticObject.getSemanticObject(request.getParameter("objUri"));
        String lang = request.getParameter("lang");
        out.println(getObject(semObj, lang, request));
    }
%>