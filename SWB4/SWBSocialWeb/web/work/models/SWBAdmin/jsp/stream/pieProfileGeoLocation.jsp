<%-- 
    Document   : profileGeoLocation
    Created on : 07-oct-2013, 19:51:58
    Author     : gabriela.rosales
--%>
<%@page import="java.lang.reflect.Array"%>
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
    JSONArray getObject(SemanticObject semObj, String lang, String idModel, String fi) throws Exception {

        String filter = reemplazar(fi);

        ArrayList list = new ArrayList();
        list.add("Aguscalientes");
        list.add("Baja California");
        list.add("Baja California Sur");
        list.add("Campeche");
        list.add("Coahuila");
        list.add("Chiapas");
        list.add("Colima");
        list.add("Chihuahua");
        list.add("Distrito Federal");
        list.add("Durango:");
        list.add("Guanajuato:");
        list.add("Guerrero");
        list.add("Hidalgo");
        list.add("Jalisco");
        list.add("Estado de México");
        list.add("Michoacán");
        list.add("Morelos");
        list.add("Nayarit");
        list.add("Nuevo León");
        list.add("Oaxaca");
        list.add("Puebla");
        list.add("Querétaro");
        list.add("Quintana Roo");
        list.add("San Luis Potosí");
        list.add("Sinaloa");
        list.add("Sonora");
        list.add("Tabasco");
        list.add("Tamaulipas");
        list.add("Tlaxcala");
        list.add("Veracruz");
        list.add("Yucatán");
        list.add("Zacatecas");
        list.add("No definido");

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

        ArrayList aguascalientes = new ArrayList();
        ArrayList bCalifornia = new ArrayList();
        ArrayList bCaliforniaS = new ArrayList();
        ArrayList campeche = new ArrayList();
        ArrayList colima = new ArrayList();
        ArrayList chiapas = new ArrayList();
        ArrayList coahuila = new ArrayList();
        ArrayList chihuahua = new ArrayList();
        ArrayList distritoFederal = new ArrayList();
        ArrayList durango = new ArrayList();
        ArrayList guanajuato = new ArrayList();
        ArrayList guerrero = new ArrayList();
        ArrayList hidalgo = new ArrayList();
        ArrayList jalisco = new ArrayList();
        ArrayList estadoMexico = new ArrayList();
        ArrayList michoacan = new ArrayList();
        ArrayList morelos = new ArrayList();
        ArrayList nayarit = new ArrayList();
        ArrayList nuevoLeon = new ArrayList();
        ArrayList oaxaca = new ArrayList();
        ArrayList puebla = new ArrayList();
        ArrayList queretaro = new ArrayList();
        ArrayList quintanaRoo = new ArrayList();
        ArrayList sanLuis = new ArrayList();
        ArrayList sinaloa = new ArrayList();
        ArrayList sonora = new ArrayList();
        ArrayList tabasco = new ArrayList();
        ArrayList tamaulipas = new ArrayList();
        ArrayList tlaxcala = new ArrayList();
        ArrayList veracruz = new ArrayList();
        ArrayList yucatan = new ArrayList();
        ArrayList zacatecas = new ArrayList();
        ArrayList nodefinido = new ArrayList();

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
            //System.out.println("title " + title);
            //map.put(title, map.containsKey(title) ? cad(map.get(title).toString(), postIn) : "1,1,0,0");

            if (title.contains("No definido")) {
                nodefinido.add(postIn);
            } else if (title.contains("aguascalientes")) {
                aguascalientes.add(postIn);
            } else if (title.contains("baja california")) {
                bCalifornia.add(postIn);
            } else if (title.contains("baja california sur")) {
                bCaliforniaS.add(postIn);
            } else if (title.contains("campeche")) {
                campeche.add(postIn);
            } else if (title.contains("coahuila")) {
                coahuila.add(postIn);
            } else if (title.contains("colima")) {
                colima.add(postIn);
            } else if (title.contains("chiapas")) {
                chiapas.add(postIn);
            } else if (title.contains("chihuahua")) {
                chihuahua.add(postIn);
            } else if (title.contains("distrito federal")) {
                distritoFederal.add(postIn);
            } else if (title.contains("durango")) {
                durango.add(postIn);
            } else if (title.contains("guanajuato")) {
                guanajuato.add(postIn);
            } else if (title.contains("guerrero")) {
                guerrero.add(postIn);
            } else if (title.contains("hidalgo")) {
                hidalgo.add(postIn);
            } else if (title.contains("jalisco")) {
                jalisco.add(postIn);
            } else if (title.contains("estado de mexico")) {
                estadoMexico.add(postIn);
            } else if (title.contains("michoacan")) {
                michoacan.add(postIn);
            } else if (title.contains("morelos")) {
                morelos.add(postIn);
            } else if (title.contains("nayarit")) {
                nayarit.add(postIn);
            } else if (title.contains("nuevo leon")) {
                nuevoLeon.add(postIn);
            } else if (title.contains("oaxaca")) {
                oaxaca.add(postIn);
            } else if (title.contains("puebla")) {
                puebla.add(postIn);
            } else if (title.contains("queretaro")) {
                queretaro.add(postIn);
            } else if (title.contains("quintana")) {
                quintanaRoo.add(postIn);
            } else if (title.contains("san luis")) {
                sanLuis.add(postIn);
            } else if (title.contains("sinaloa")) {
                sinaloa.add(postIn);
            } else if (title.contains("sonora")) {
                sonora.add(postIn);
            } else if (title.contains("tabasco")) {
                tabasco.add(postIn);
            } else if (title.contains("tamaulipas")) {
                tamaulipas.add(postIn);
            } else if (title.contains("tlaxcala")) {
                tlaxcala.add(postIn);
            } else if (title.contains("veracruz")) {
                veracruz.add(postIn);
            } else if (title.contains("yucatan")) {
                yucatan.add(postIn);
            } else if (title.contains("zacatecas")) {
                zacatecas.add(postIn);
            }


        }



        Iterator nodefinidoI = nodefinido.iterator();
        int neutralsnodefined = 0, positivesnoefined = 0, negativesnodefined = 0, totalnodefined = 0;
        while (nodefinidoI.hasNext()) {
            PostIn pi = (PostIn) nodefinidoI.next();
            totalnodefined++;
            if (pi.getPostSentimentalType() == 0) {
                neutralsnodefined++;
            } else if (pi.getPostSentimentalType() == 1) {
                positivesnoefined++;
            } else if (pi.getPostSentimentalType() == 2) {
                negativesnodefined++;
            }
        }
        float intPorcentajenodefinido = ((float) totalnodefined * 100) / (float) totalPost;
        float porcentajeNeutralsnodefined = 0;
        float porcentajePositivesnodefined = 0;
        float porcentajeNegativesnodefined = 0;

        if (totalnodefined != 0) {
            porcentajeNeutralsnodefined = ((float) neutralsnodefined * 100) / (float) totalnodefined;
            porcentajePositivesnodefined = ((float) positivesnoefined * 100) / (float) totalnodefined;
            porcentajeNegativesnodefined = ((float) negativesnodefined * 100) / (float) totalnodefined;
        }

        Iterator aguascalientesI = aguascalientes.iterator();
        int neutralsAguascalientes = 0, positivesAguascalientes = 0, negativesAguascalientes = 0, totalAguascalientes = 0;
        while (aguascalientesI.hasNext()) {
            PostIn pi = (PostIn) aguascalientesI.next();
            totalAguascalientes++;
            if (pi.getPostSentimentalType() == 0) {
                neutralsAguascalientes++;
            } else if (pi.getPostSentimentalType() == 1) {
                positivesAguascalientes++;
            } else if (pi.getPostSentimentalType() == 2) {
                negativesAguascalientes++;
            }
        }
        float intPorcentajeAguascalientes = ((float) totalAguascalientes * 100) / (float) totalPost;
        float porcentajeNeutralsAguascalientes = 0;
        float porcentajePositivesAguascalientes = 0;
        float porcentajeNegativesAguascalientes = 0;

        if (totalAguascalientes != 0) {
            porcentajeNeutralsAguascalientes = ((float) neutralsAguascalientes * 100) / (float) totalAguascalientes;
            porcentajePositivesAguascalientes = ((float) positivesAguascalientes * 100) / (float) totalAguascalientes;
            porcentajeNegativesAguascalientes = ((float) negativesAguascalientes * 100) / (float) totalAguascalientes;
        }



        Iterator bCaliforniaI = bCalifornia.iterator();
        int neutralsbCalifornia = 0, positivesbCalifornia = 0, negativesbCalifornia = 0, totalbCalifornia = 0;
        while (bCaliforniaI.hasNext()) {
            PostIn pi = (PostIn) bCaliforniaI.next();
            totalbCalifornia++;
            if (pi.getPostSentimentalType() == 0) {
                neutralsbCalifornia++;
            } else if (pi.getPostSentimentalType() == 1) {
                positivesbCalifornia++;
            } else if (pi.getPostSentimentalType() == 2) {
                negativesbCalifornia++;
            }
        }
        float intPorcentajeBajaCalifornia = ((float) totalbCalifornia * 100) / (float) totalPost;
        float porcentajeNeutralsBajaCalifornia = 0;
        float porcentajePositivesBajaCalifornia = 0;
        float porcentajeNegativesBajaCalifornia = 0;

        if (totalbCalifornia != 0) {
            porcentajeNeutralsBajaCalifornia = ((float) neutralsbCalifornia * 100) / (float) totalbCalifornia;
            porcentajePositivesBajaCalifornia = ((float) positivesbCalifornia * 100) / (float) totalbCalifornia;
            porcentajeNegativesBajaCalifornia = ((float) negativesbCalifornia * 100) / (float) totalbCalifornia;
        }


        Iterator bCaliforniaSI = bCaliforniaS.iterator();
        int neutralsbCaliforniaSI = 0, positivesbCaliforniaSI = 0, negativesbCaliforniaSI = 0, totalbCaliforniaSI = 0;
        while (bCaliforniaSI.hasNext()) {
            PostIn pi = (PostIn) bCaliforniaSI.next();
            totalbCaliforniaSI++;
            if (pi.getPostSentimentalType() == 0) {
                neutralsbCaliforniaSI++;
            } else if (pi.getPostSentimentalType() == 1) {
                positivesbCaliforniaSI++;
            } else if (pi.getPostSentimentalType() == 2) {
                negativesbCaliforniaSI++;
            }
        }
        float intPorcentajeBajaCaliforniaS = ((float) totalbCaliforniaSI * 100) / (float) totalPost;
        float porcentajeNeutralsBajaCaliforniaS = 0;
        float porcentajePositivesBajaCaliforniaS = 0;
        float porcentajeNegativesBajaCaliforniaS = 0;

        if (totalbCaliforniaSI != 0) {
            porcentajeNeutralsBajaCaliforniaS = ((float) neutralsbCaliforniaSI * 100) / (float) totalbCaliforniaSI;
            porcentajePositivesBajaCaliforniaS = ((float) positivesbCaliforniaSI * 100) / (float) totalbCaliforniaSI;
            porcentajeNegativesBajaCaliforniaS = ((float) negativesbCaliforniaSI * 100) / (float) totalbCaliforniaSI;
        }

        //total positivos nrgatuvos , neutros de campeche
        Iterator campecheI = campeche.iterator();
        int neutralsCampeche = 0, positivesCampeche = 0, negativesCampeche = 0, totalCampeche = 0;
        while (campecheI.hasNext()) {
            PostIn pi = (PostIn) campecheI.next();
            totalCampeche++;
            if (pi.getPostSentimentalType() == 0) {
                neutralsCampeche++;
            } else if (pi.getPostSentimentalType() == 1) {
                positivesCampeche++;
            } else if (pi.getPostSentimentalType() == 2) {
                negativesCampeche++;
            }
        }
        float intPorcentajeCampeche = ((float) totalCampeche * 100) / (float) totalPost;
        float porcentajeNeutralsCampeche = 0;
        float porcentajePositivesCampeche = 0;
        float porcentajeNegativesCampeche = 0;

        if (totalCampeche != 0) {
            porcentajeNeutralsCampeche = ((float) neutralsCampeche * 100) / (float) totalCampeche;
            porcentajePositivesCampeche = ((float) positivesCampeche * 100) / (float) totalCampeche;
            porcentajeNegativesCampeche = ((float) negativesCampeche * 100) / (float) totalCampeche;
        }



        //total positivos negativos, neutros de coahuila
        Iterator coahuilaI = coahuila.iterator();
        int neutralsCoahuila = 0, positivesCoahuia = 0, negativesCoahuila = 0, totalCoahuila = 0;
        while (coahuilaI.hasNext()) {
            PostIn pi = (PostIn) coahuilaI.next();
            totalCoahuila++;
            if (pi.getPostSentimentalType() == 0) {
                neutralsCoahuila++;
            } else if (pi.getPostSentimentalType() == 1) {
                positivesCoahuia++;
            } else if (pi.getPostSentimentalType() == 2) {
                negativesCoahuila++;
            }
        }
        float intPorcentajeCohauila = ((float) totalCoahuila * 100) / (float) totalPost;
        float porcentajeNeutralsCoahuila = 0;
        float porcentajePositivesCoahuila = 0;
        float porcentajeNegativesCohauila = 0;

        if (totalCoahuila != 0) {
            porcentajeNeutralsCoahuila = ((float) neutralsCoahuila * 100) / (float) totalCoahuila;
            porcentajePositivesCoahuila = ((float) positivesCoahuia * 100) / (float) totalCoahuila;
            porcentajeNegativesCohauila = ((float) negativesCoahuila * 100) / (float) totalCoahuila;
        }




        //total positivos negativos neutros colima
        Iterator colimaI = colima.iterator();
        int neutralsColima = 0, positivesColima = 0, negativesColima = 0, totalColima = 0;
        while (colimaI.hasNext()) {
            PostIn pi = (PostIn) colimaI.next();
            totalColima++;
            if (pi.getPostSentimentalType() == 0) {
                neutralsColima++;
            } else if (pi.getPostSentimentalType() == 1) {
                positivesColima++;
            } else if (pi.getPostSentimentalType() == 2) {
                negativesColima++;
            }
        }
        float intPorcentajeColima = ((float) totalColima * 100) / (float) totalPost;
        float porcentajeNeutralsColima = 0;
        float porcentajePositivesColima = 0;
        float porcentajeNegativesColima = 0;

        if (totalColima != 0) {
            porcentajeNeutralsColima = ((float) neutralsColima * 100) / (float) totalColima;
            porcentajePositivesColima = ((float) positivesColima * 100) / (float) totalColima;
            porcentajeNegativesColima = ((float) negativesColima * 100) / (float) totalColima;
        }


        //total positivos negativos neutros chiapas
        Iterator chipasI = chiapas.iterator();
        int neutralsChiapas = 0, positivesChiapas = 0, negativesChiapas = 0, totalChiapas = 0;
        while (chipasI.hasNext()) {
            PostIn pi = (PostIn) chipasI.next();
            totalChiapas++;
            if (pi.getPostSentimentalType() == 0) {
                neutralsChiapas++;
            } else if (pi.getPostSentimentalType() == 1) {
                positivesChiapas++;
            } else if (pi.getPostSentimentalType() == 2) {
                negativesChiapas++;
            }
        }

        float intPorcentajeChiapas = ((float) totalChiapas * 100) / (float) totalPost;
        float porcentajeNeutralsChiapas = 0;
        float porcentajePositivesChiapas = 0;
        float porcentajeNegativesChiapas = 0;

        if (totalChiapas != 0) {
            porcentajeNeutralsChiapas = ((float) neutralsChiapas * 100) / (float) totalChiapas;
            porcentajePositivesChiapas = ((float) positivesChiapas * 100) / (float) totalChiapas;
            porcentajeNegativesChiapas = ((float) negativesChiapas * 100) / (float) totalChiapas;
        }


        //total positivos negativos neutros chihuahua
        Iterator chihuahuaI = chihuahua.iterator();
        int neutralsChihuahua = 0, positivesChihuahua = 0, negativesChihuahua = 0, totalChihuahua = 0;
        while (chihuahuaI.hasNext()) {
            PostIn pi = (PostIn) chihuahuaI.next();
            totalChihuahua++;
            if (pi.getPostSentimentalType() == 0) {
                neutralsChihuahua++;
            } else if (pi.getPostSentimentalType() == 1) {
                positivesChihuahua++;
            } else if (pi.getPostSentimentalType() == 2) {
                negativesChihuahua++;
            }
        }

        float intPorcentajeChihuahua = ((float) totalChihuahua * 100) / (float) totalPost;
        float porcentajeNeutralsChihuahua = 0;
        float porcentajePositivesChihuahua = 0;
        float porcentajeNegativesChihuahua = 0;

        if (totalChihuahua != 0) {
            porcentajeNeutralsChihuahua = ((float) neutralsChihuahua * 100) / (float) totalChihuahua;
            porcentajePositivesChihuahua = ((float) positivesChihuahua * 100) / (float) totalChihuahua;
            porcentajeNegativesChihuahua = ((float) negativesChihuahua * 100) / (float) totalChihuahua;
        }

        //total positivos negativos neutros df
        Iterator distritoFederalI = distritoFederal.iterator();
        int neutralsdistritoFederal = 0, positivesdistritoFederal = 0, negativesdistritoFederal = 0, totaldistritoFederal = 0;
        while (distritoFederalI.hasNext()) {
            PostIn pi = (PostIn) distritoFederalI.next();
            totaldistritoFederal++;
            if (pi.getPostSentimentalType() == 0) {
                neutralsdistritoFederal++;
            } else if (pi.getPostSentimentalType() == 1) {
                positivesdistritoFederal++;
            } else if (pi.getPostSentimentalType() == 2) {
                negativesdistritoFederal++;
            }
        }

        float intPorcentajeDF = ((float) totaldistritoFederal * 100) / (float) totalPost;
        float porcentajeNeutralsDF = 0;
        float porcentajePositivesDF = 0;
        float porcentajeNegativesDF = 0;

        if (totaldistritoFederal != 0) {
            porcentajeNeutralsDF = ((float) neutralsdistritoFederal * 100) / (float) totaldistritoFederal;
            porcentajePositivesDF = ((float) positivesdistritoFederal * 100) / (float) totaldistritoFederal;
            porcentajeNegativesDF = ((float) negativesdistritoFederal * 100) / (float) totaldistritoFederal;
        }

        //total positivos negativos neutros durango
        Iterator durangoI = durango.iterator();
        int neutralsdurango = 0, positivesdurango = 0, negativesdurango = 0, totaldurango = 0;
        while (durangoI.hasNext()) {
            PostIn pi = (PostIn) durangoI.next();
            totaldurango++;
            if (pi.getPostSentimentalType() == 0) {
                neutralsdurango++;
            } else if (pi.getPostSentimentalType() == 1) {
                positivesdurango++;
            } else if (pi.getPostSentimentalType() == 2) {
                negativesdurango++;
            }
        }


        float intPorcentajeDurango = ((float) totaldurango * 100) / (float) totalPost;
        float porcentajeNeutralsDurango = 0;
        float porcentajePositivesDurango = 0;
        float porcentajeNegativesDurango = 0;

        if (totaldurango != 0) {
            porcentajeNeutralsDurango = ((float) neutralsdurango * 100) / (float) totaldurango;
            porcentajePositivesDurango = ((float) positivesdurango * 100) / (float) totaldurango;
            porcentajeNegativesDurango = ((float) negativesdurango * 100) / (float) totaldurango;
        }


        //total positivos negativos neutros guanajuato
        Iterator guanajuatoI = guanajuato.iterator();
        int neutralsguanajuato = 0, positivesguanajuato = 0, negativesguanajuato = 0, totalguanajuato = 0;
        while (guanajuatoI.hasNext()) {
            PostIn pi = (PostIn) guanajuatoI.next();
            totalguanajuato++;
            if (pi.getPostSentimentalType() == 0) {
                neutralsguanajuato++;
            } else if (pi.getPostSentimentalType() == 1) {
                positivesguanajuato++;
            } else if (pi.getPostSentimentalType() == 2) {
                negativesguanajuato++;
            }
        }


        float intPorcentajeGuanajuato = ((float) totalguanajuato * 100) / (float) totalPost;
        float porcentajeNeutralsGuanajuato = 0;
        float porcentajePositivesGuanajuato = 0;
        float porcentajeNegativesGuanajuato = 0;

        if (totalguanajuato != 0) {
            porcentajeNeutralsGuanajuato = ((float) neutralsguanajuato * 100) / (float) totalguanajuato;
            porcentajePositivesGuanajuato = ((float) positivesguanajuato * 100) / (float) totalguanajuato;
            porcentajeNegativesGuanajuato = ((float) negativesguanajuato * 100) / (float) totalguanajuato;
        }


        //total positivos negativos neutros guerrero
        Iterator guerreroI = guerrero.iterator();
        int neutralsguerrero = 0, positivesguerrero = 0, negativesguerrero = 0, totalguerrero = 0;
        while (guerreroI.hasNext()) {
            PostIn pi = (PostIn) guerreroI.next();
            totalguerrero++;
            if (pi.getPostSentimentalType() == 0) {
                neutralsguerrero++;
            } else if (pi.getPostSentimentalType() == 1) {
                positivesguerrero++;
            } else if (pi.getPostSentimentalType() == 2) {
                negativesguerrero++;
            }
        }


        float intPorcentajeGuerrero = ((float) totalguerrero * 100) / (float) totalPost;
        float porcentajeNeutralsGuerrero = 0;
        float porcentajePositivesGuerrero = 0;
        float porcentajeNegativesGuerrero = 0;

        if (totalguerrero != 0) {
            porcentajeNeutralsGuerrero = ((float) neutralsguerrero * 100) / (float) totalguanajuato;
            porcentajePositivesGuerrero = ((float) positivesguerrero * 100) / (float) totalguanajuato;
            porcentajeNegativesGuerrero = ((float) negativesguerrero * 100) / (float) totalguanajuato;
        }


        //total positivos negativos neutros hidalgo
        Iterator hidalgoI = hidalgo.iterator();
        int neutralshidalgo = 0, positiveshidalgo = 0, negativeshidalgo = 0, totalhidalgo = 0;
        while (hidalgoI.hasNext()) {
            PostIn pi = (PostIn) hidalgoI.next();
            totalhidalgo++;
            if (pi.getPostSentimentalType() == 0) {
                neutralshidalgo++;
            } else if (pi.getPostSentimentalType() == 1) {
                positiveshidalgo++;
            } else if (pi.getPostSentimentalType() == 2) {
                negativeshidalgo++;
            }
        }


        float intPorcentajehidalgo = ((float) totalhidalgo * 100) / (float) totalPost;
        float porcentajeNeutralshidalgo = 0;
        float porcentajePositiveshidalgo = 0;
        float porcentajeNegativeshidalgo = 0;

        if (totalhidalgo != 0) {
            porcentajeNeutralshidalgo = ((float) neutralshidalgo * 100) / (float) totalhidalgo;
            porcentajePositiveshidalgo = ((float) positiveshidalgo * 100) / (float) totalhidalgo;
            porcentajeNegativeshidalgo = ((float) negativeshidalgo * 100) / (float) totalhidalgo;
        }


        //total positivos negativos neutros hidalgo
        Iterator jaliscoI = jalisco.iterator();
        int neutralsjalisco = 0, positivesjalisco = 0, negativesjalisco = 0, totaljalisco = 0;
        while (jaliscoI.hasNext()) {
            PostIn pi = (PostIn) jaliscoI.next();
            totaljalisco++;
            if (pi.getPostSentimentalType() == 0) {
                neutralsjalisco++;
            } else if (pi.getPostSentimentalType() == 1) {
                positivesjalisco++;
            } else if (pi.getPostSentimentalType() == 2) {
                negativesjalisco++;
            }
        }


        float intPorcentajejalisco = ((float) totaljalisco * 100) / (float) totalPost;
        float porcentajeNeutralsjalisco = 0;
        float porcentajePositivesjalisco = 0;
        float porcentajeNegativesjalisco = 0;

        if (totaljalisco != 0) {
            porcentajeNeutralsjalisco = ((float) neutralsjalisco * 100) / (float) totaljalisco;
            porcentajePositivesjalisco = ((float) positivesjalisco * 100) / (float) totaljalisco;
            porcentajeNegativesjalisco = ((float) negativesjalisco * 100) / (float) totaljalisco;
        }

        //total positivos negativos neutros hidalgo
        Iterator estadoMexicoI = estadoMexico.iterator();
        int neutralsestadoMexico = 0, positivesestadoMexico = 0, negativesestadoMexico = 0, totalestadoMexico = 0;
        while (jaliscoI.hasNext()) {
            PostIn pi = (PostIn) estadoMexicoI.next();
            totalestadoMexico++;
            if (pi.getPostSentimentalType() == 0) {
                neutralsestadoMexico++;
            } else if (pi.getPostSentimentalType() == 1) {
                positivesestadoMexico++;
            } else if (pi.getPostSentimentalType() == 2) {
                negativesestadoMexico++;
            }
        }


        float intPorcentajeestadoMexico = ((float) totalestadoMexico * 100) / (float) totalPost;
        float porcentajeNeutralsestadoMexico = 0;
        float porcentajePositivesestadoMexico = 0;
        float porcentajeNegativesestadoMexico = 0;

        if (totalestadoMexico != 0) {
            porcentajeNeutralsestadoMexico = ((float) neutralsestadoMexico * 100) / (float) totalestadoMexico;
            porcentajePositivesestadoMexico = ((float) positivesestadoMexico * 100) / (float) totalestadoMexico;
            porcentajeNegativesestadoMexico = ((float) negativesestadoMexico * 100) / (float) totalestadoMexico;
        }



        //total positivos negativos neutros hidalgo
        Iterator michoacanI = michoacan.iterator();
        int neutralsmichoacan = 0, positivesmichoacan = 0, negativesmichoacan = 0, totalmichoacan = 0;
        while (michoacanI.hasNext()) {
            PostIn pi = (PostIn) michoacanI.next();
            totalmichoacan++;
            if (pi.getPostSentimentalType() == 0) {
                neutralsmichoacan++;
            } else if (pi.getPostSentimentalType() == 1) {
                positivesmichoacan++;
            } else if (pi.getPostSentimentalType() == 2) {
                negativesmichoacan++;
            }
        }


        float intPorcentajemichoacan = ((float) totalmichoacan * 100) / (float) totalPost;
        float porcentajeNeutralsmichoacan = 0;
        float porcentajePositivesmichoacan = 0;
        float porcentajeNegativesmichoacan = 0;

        if (totalmichoacan != 0) {
            porcentajeNeutralsmichoacan = ((float) neutralsmichoacan * 100) / (float) totalmichoacan;
            porcentajePositivesmichoacan = ((float) positivesmichoacan * 100) / (float) totalmichoacan;
            porcentajeNegativesmichoacan = ((float) negativesmichoacan * 100) / (float) totalmichoacan;
        }


        //total positivos negativos neutros morelos
        Iterator morelosI = morelos.iterator();
        int neutralsmorelos = 0, positivesmorelos = 0, negativesmorelos = 0, totalmorelos = 0;
        while (morelosI.hasNext()) {
            PostIn pi = (PostIn) morelosI.next();
            totalmorelos++;
            if (pi.getPostSentimentalType() == 0) {
                neutralsmorelos++;
            } else if (pi.getPostSentimentalType() == 1) {
                positivesmorelos++;
            } else if (pi.getPostSentimentalType() == 2) {
                negativesmorelos++;
            }
        }


        float intPorcentajemorelos = ((float) totalmorelos * 100) / (float) totalPost;
        float porcentajeNeutralsmorelos = 0;
        float porcentajePositivesmorelos = 0;
        float porcentajeNegativesmorelos = 0;

        if (totalmorelos != 0) {
            porcentajeNeutralsmorelos = ((float) neutralsmorelos * 100) / (float) totalmorelos;
            porcentajePositivesmorelos = ((float) positivesmorelos * 100) / (float) totalmorelos;
            porcentajeNegativesmorelos = ((float) negativesmorelos * 100) / (float) totalmorelos;
        }


        //total positivos negativos neutros nayarit
        Iterator nayaritI = nayarit.iterator();
        int neutralsnayarit = 0, positivesnayarit = 0, negativesnayarit = 0, totalnayarit = 0;
        while (nayaritI.hasNext()) {
            PostIn pi = (PostIn) nayaritI.next();
            totalnayarit++;
            if (pi.getPostSentimentalType() == 0) {
                neutralsnayarit++;
            } else if (pi.getPostSentimentalType() == 1) {
                positivesnayarit++;
            } else if (pi.getPostSentimentalType() == 2) {
                negativesnayarit++;
            }
        }


        float intPorcentajenayarit = ((float) totalnayarit * 100) / (float) totalPost;
        float porcentajeNeutralsnayarit = 0;
        float porcentajePositivesnayarit = 0;
        float porcentajeNegativesnayarit = 0;

        if (totalnayarit != 0) {
            porcentajeNeutralsnayarit = ((float) neutralsnayarit * 100) / (float) totalnayarit;
            porcentajePositivesnayarit = ((float) positivesnayarit * 100) / (float) totalnayarit;
            porcentajeNegativesnayarit = ((float) negativesnayarit * 100) / (float) totalnayarit;
        }

        //total positivos negativos neutros nuevoleon
        Iterator nuevoLeonI = nuevoLeon.iterator();
        int neutralsnuevoLeon = 0, positivesnuevoLeon = 0, negativesnuevoLeon = 0, totalnuevoLeon = 0;
        while (nuevoLeonI.hasNext()) {
            PostIn pi = (PostIn) nuevoLeonI.next();
            totalnuevoLeon++;
            if (pi.getPostSentimentalType() == 0) {
                neutralsnuevoLeon++;
            } else if (pi.getPostSentimentalType() == 1) {
                positivesnuevoLeon++;
            } else if (pi.getPostSentimentalType() == 2) {
                negativesnuevoLeon++;
            }
        }


        float intPorcentajenuevoLeon = ((float) totalnuevoLeon * 100) / (float) totalPost;
        float porcentajeNeutralsnuevoLeon = 0;
        float porcentajePositivesnuevoLeon = 0;
        float porcentajeNegativesnuevoLeon = 0;

        if (totalnuevoLeon != 0) {
            porcentajeNeutralsnuevoLeon = ((float) neutralsnuevoLeon * 100) / (float) totalnuevoLeon;
            porcentajePositivesnuevoLeon = ((float) positivesnuevoLeon * 100) / (float) totalnuevoLeon;
            porcentajeNegativesnuevoLeon = ((float) negativesnuevoLeon * 100) / (float) totalnuevoLeon;
        }


        //total positivos negativos neutros Oaxaca
        Iterator oaxacaI = oaxaca.iterator();
        int neutralsoaxaca = 0, positivesoaxaca = 0, negativesoaxaca = 0, totaloaxaca = 0;
        while (oaxacaI.hasNext()) {
            PostIn pi = (PostIn) oaxacaI.next();
            totaloaxaca++;
            if (pi.getPostSentimentalType() == 0) {
                neutralsoaxaca++;
            } else if (pi.getPostSentimentalType() == 1) {
                positivesoaxaca++;
            } else if (pi.getPostSentimentalType() == 2) {
                negativesoaxaca++;
            }
        }


        float intPorcentajeoaxaca = ((float) totaloaxaca * 100) / (float) totalPost;
        float porcentajeNeutralsoaxaca = 0;
        float porcentajePositivesoaxaca = 0;
        float porcentajeNegativesoaxaca = 0;

        if (totaloaxaca != 0) {
            porcentajeNeutralsoaxaca = ((float) neutralsoaxaca * 100) / (float) totaloaxaca;
            porcentajePositivesoaxaca = ((float) positivesoaxaca * 100) / (float) totaloaxaca;
            porcentajeNegativesoaxaca = ((float) negativesoaxaca * 100) / (float) totaloaxaca;
        }

        //total positivos negativos neutros puebla
        Iterator pueblaI = puebla.iterator();
        int neutralspuebla = 0, positivespuebla = 0, negativespuebla = 0, totalpuebla = 0;
        while (pueblaI.hasNext()) {
            PostIn pi = (PostIn) pueblaI.next();
            totalpuebla++;
            if (pi.getPostSentimentalType() == 0) {
                neutralspuebla++;
            } else if (pi.getPostSentimentalType() == 1) {
                positivespuebla++;
            } else if (pi.getPostSentimentalType() == 2) {
                negativespuebla++;
            }
        }


        float intPorcentajepuebla = ((float) totalpuebla * 100) / (float) totalPost;
        float porcentajeNeutralspuebla = 0;
        float porcentajePositivespuebla = 0;
        float porcentajeNegativespuebla = 0;

        if (totalpuebla != 0) {
            porcentajeNeutralspuebla = ((float) neutralspuebla * 100) / (float) totalpuebla;
            porcentajePositivespuebla = ((float) positivespuebla * 100) / (float) totalpuebla;
            porcentajeNegativespuebla = ((float) negativespuebla * 100) / (float) totalpuebla;
        }

        //total positivos negativos neutros queretaro
        Iterator queretaroI = queretaro.iterator();
        int neutralsqueretaro = 0, positivesqueretaro = 0, negativesqueretaro = 0, totalqueretaro = 0;
        while (queretaroI.hasNext()) {
            PostIn pi = (PostIn) queretaroI.next();
            totalqueretaro++;
            if (pi.getPostSentimentalType() == 0) {
                neutralsqueretaro++;
            } else if (pi.getPostSentimentalType() == 1) {
                positivesqueretaro++;
            } else if (pi.getPostSentimentalType() == 2) {
                negativesqueretaro++;
            }
        }


        float intPorcentajequeretaro = ((float) totalqueretaro * 100) / (float) totalPost;
        float porcentajeNeutralsqueretaro = 0;
        float porcentajePositivesqueretaro = 0;
        float porcentajeNegativesqueretaro = 0;

        if (totalqueretaro != 0) {
            porcentajeNeutralsqueretaro = ((float) neutralsqueretaro * 100) / (float) totalqueretaro;
            porcentajePositivesqueretaro = ((float) positivesqueretaro * 100) / (float) totalqueretaro;
            porcentajeNegativesqueretaro = ((float) negativesqueretaro * 100) / (float) totalqueretaro;
        }

        //total positivos negativos neutros quintanaRoo
        Iterator quintanaRooI = quintanaRoo.iterator();
        int neutralsquintanaRoo = 0, positivesquintanaRoo = 0, negativesquintanaRoo = 0, totalquintanaRoo = 0;
        while (quintanaRooI.hasNext()) {
            PostIn pi = (PostIn) quintanaRooI.next();
            totalquintanaRoo++;
            if (pi.getPostSentimentalType() == 0) {
                neutralsquintanaRoo++;
            } else if (pi.getPostSentimentalType() == 1) {
                positivesquintanaRoo++;
            } else if (pi.getPostSentimentalType() == 2) {
                negativesquintanaRoo++;
            }
        }


        float intPorcentajequintanaRoo = ((float) totalquintanaRoo * 100) / (float) totalPost;
        float porcentajeNeutralsquintanaRoo = 0;
        float porcentajePositivesquintanaRoo = 0;
        float porcentajeNegativesquintanaRoo = 0;

        if (totalquintanaRoo != 0) {
            porcentajeNeutralsquintanaRoo = ((float) neutralsquintanaRoo * 100) / (float) totalquintanaRoo;
            porcentajePositivesquintanaRoo = ((float) positivesquintanaRoo * 100) / (float) totalquintanaRoo;
            porcentajeNegativesquintanaRoo = ((float) negativesquintanaRoo * 100) / (float) totalquintanaRoo;
        }


        //total positivos negativos neutros sanluis
        Iterator sanLuisI = sanLuis.iterator();
        int neutralssanLuis = 0, positivessanLuis = 0, negativessanLuis = 0, totalsanLuis = 0;
        while (sanLuisI.hasNext()) {
            PostIn pi = (PostIn) sanLuisI.next();
            totalsanLuis++;
            if (pi.getPostSentimentalType() == 0) {
                neutralssanLuis++;
            } else if (pi.getPostSentimentalType() == 1) {
                positivessanLuis++;
            } else if (pi.getPostSentimentalType() == 2) {
                negativessanLuis++;
            }
        }


        float intPorcentajesanLuis = ((float) totalsanLuis * 100) / (float) totalPost;
        float porcentajeNeutralssanLuis = 0;
        float porcentajePositivessanLuis = 0;
        float porcentajeNegativessanLuis = 0;

        if (totalsanLuis != 0) {
            porcentajeNeutralssanLuis = ((float) neutralssanLuis * 100) / (float) totalsanLuis;
            porcentajePositivessanLuis = ((float) positivessanLuis * 100) / (float) totalsanLuis;
            porcentajeNegativessanLuis = ((float) negativessanLuis * 100) / (float) totalsanLuis;
        }

        //total positivos negativos neutros sinaloa
        Iterator sinaloaI = sinaloa.iterator();
        int neutralssinaloa = 0, positivessinaloa = 0, negativessinaloa = 0, totalsinaloa = 0;
        while (sinaloaI.hasNext()) {
            PostIn pi = (PostIn) sinaloaI.next();
            totalsinaloa++;
            if (pi.getPostSentimentalType() == 0) {
                neutralssinaloa++;
            } else if (pi.getPostSentimentalType() == 1) {
                positivessinaloa++;
            } else if (pi.getPostSentimentalType() == 2) {
                negativessinaloa++;
            }
        }


        float intPorcentajesinaloa = ((float) totalsinaloa * 100) / (float) totalPost;
        float porcentajeNeutralssinaloa = 0;
        float porcentajePositivessinaloa = 0;
        float porcentajeNegativessinaloa = 0;

        if (totalsinaloa != 0) {
            porcentajeNeutralssinaloa = ((float) neutralssinaloa * 100) / (float) totalsinaloa;
            porcentajePositivessinaloa = ((float) positivessinaloa * 100) / (float) totalsinaloa;
            porcentajeNegativessinaloa = ((float) negativessinaloa * 100) / (float) totalsinaloa;
        }

        //total positivos negativos neutros sonora
        Iterator sonoraI = sonora.iterator();
        int neutralssonora = 0, positivessonora = 0, negativessonora = 0, totalsonora = 0;
        while (sonoraI.hasNext()) {
            PostIn pi = (PostIn) sonoraI.next();
            totalsonora++;
            if (pi.getPostSentimentalType() == 0) {
                neutralssonora++;
            } else if (pi.getPostSentimentalType() == 1) {
                positivessonora++;
            } else if (pi.getPostSentimentalType() == 2) {
                negativessonora++;
            }
        }


        float intPorcentajesonora = ((float) totalsonora * 100) / (float) totalPost;
        float porcentajeNeutralssonora = 0;
        float porcentajePositivessonora = 0;
        float porcentajeNegativessonora = 0;

        if (totalsonora != 0) {
            porcentajeNeutralssonora = ((float) neutralssonora * 100) / (float) totalsonora;
            porcentajePositivessonora = ((float) positivessonora * 100) / (float) totalsonora;
            porcentajeNegativessonora = ((float) negativessonora * 100) / (float) totalsonora;
        }

        //total positivos negativos neutros tabasco
        Iterator tabascoI = tabasco.iterator();
        int neutralstabasco = 0, positivestabasco = 0, negativestabasco = 0, totaltabasco = 0;
        while (tabascoI.hasNext()) {
            PostIn pi = (PostIn) tabascoI.next();
            totaltabasco++;
            if (pi.getPostSentimentalType() == 0) {
                neutralstabasco++;
            } else if (pi.getPostSentimentalType() == 1) {
                positivestabasco++;
            } else if (pi.getPostSentimentalType() == 2) {
                negativestabasco++;
            }
        }


        float intPorcentajetabasco = ((float) totaltabasco * 100) / (float) totalPost;
        float porcentajeNeutralstabasco = 0;
        float porcentajePositivestabasco = 0;
        float porcentajeNegativestabasco = 0;

        if (totaltabasco != 0) {
            porcentajeNeutralstabasco = ((float) neutralstabasco * 100) / (float) totaltabasco;
            porcentajePositivestabasco = ((float) positivestabasco * 100) / (float) totaltabasco;
            porcentajeNegativestabasco = ((float) negativestabasco * 100) / (float) totaltabasco;
        }


        //total positivos negativos neutros tamaulipas
        Iterator tamaulipasI = tamaulipas.iterator();
        int neutralstamaulipas = 0, positivestamaulipas = 0, negativestamaulipas = 0, totaltamaulipas = 0;
        while (tamaulipasI.hasNext()) {
            PostIn pi = (PostIn) tamaulipasI.next();
            totaltamaulipas++;
            if (pi.getPostSentimentalType() == 0) {
                neutralstamaulipas++;
            } else if (pi.getPostSentimentalType() == 1) {
                positivestamaulipas++;
            } else if (pi.getPostSentimentalType() == 2) {
                negativestamaulipas++;
            }
        }


        float intPorcentajetamaulipas = ((float) totaltamaulipas * 100) / (float) totalPost;
        float porcentajeNeutralstamaulipas = 0;
        float porcentajePositivestamaulipas = 0;
        float porcentajeNegativestamaulipas = 0;

        if (totaltamaulipas != 0) {
            porcentajeNeutralstamaulipas = ((float) neutralstamaulipas * 100) / (float) totaltamaulipas;
            porcentajePositivestamaulipas = ((float) positivestamaulipas * 100) / (float) totaltamaulipas;
            porcentajeNegativestamaulipas = ((float) negativestamaulipas * 100) / (float) totaltamaulipas;
        }

        //total positivos negativos neutros tlaxcala
        Iterator tlaxcalaI = tlaxcala.iterator();
        int neutralstlaxcala = 0, positivestlaxcala = 0, negativestlaxcala = 0, totaltlaxcala = 0;
        while (tlaxcalaI.hasNext()) {
            PostIn pi = (PostIn) tlaxcalaI.next();
            totaltlaxcala++;
            if (pi.getPostSentimentalType() == 0) {
                neutralstlaxcala++;
            } else if (pi.getPostSentimentalType() == 1) {
                positivestlaxcala++;
            } else if (pi.getPostSentimentalType() == 2) {
                negativestlaxcala++;
            }
        }


        float intPorcentajetlaxcala = ((float) totaltlaxcala * 100) / (float) totalPost;
        float porcentajeNeutralstlaxcala = 0;
        float porcentajePositivestlaxcala = 0;
        float porcentajeNegativestlaxcala = 0;

        if (totaltlaxcala != 0) {
            porcentajeNeutralstlaxcala = ((float) neutralstlaxcala * 100) / (float) totaltlaxcala;
            porcentajePositivestlaxcala = ((float) positivestlaxcala * 100) / (float) totaltlaxcala;
            porcentajeNegativestlaxcala = ((float) negativestlaxcala * 100) / (float) totaltlaxcala;
        }



        //total positivos negativos neutros veracruz
        Iterator veracruzI = veracruz.iterator();
        int neutralsveracruz = 0, positivesveracruz = 0, negativesveracruz = 0, totalveracruz = 0;
        while (veracruzI.hasNext()) {
            PostIn pi = (PostIn) veracruzI.next();
            totalveracruz++;
            if (pi.getPostSentimentalType() == 0) {
                neutralsveracruz++;
            } else if (pi.getPostSentimentalType() == 1) {
                positivesveracruz++;
            } else if (pi.getPostSentimentalType() == 2) {
                negativesveracruz++;
            }
        }


        float intPorcentajeveracruz = ((float) totalveracruz * 100) / (float) totalPost;
        float porcentajeNeutralsveracruz = 0;
        float porcentajePositivesveracruz = 0;
        float porcentajeNegativesveracruz = 0;

        if (totalveracruz != 0) {
            porcentajeNeutralsveracruz = ((float) neutralsveracruz * 100) / (float) totalveracruz;
            porcentajePositivesveracruz = ((float) positivesveracruz * 100) / (float) totalveracruz;
            porcentajeNegativesveracruz = ((float) negativesveracruz * 100) / (float) totalveracruz;
        }




        //total positivos negativos neutros yucatan
        Iterator yucatanI = yucatan.iterator();
        int neutralsyucatan = 0, positivesyucatan = 0, negativesyucatan = 0, totalyucatan = 0;
        while (yucatanI.hasNext()) {
            PostIn pi = (PostIn) yucatanI.next();
            totalyucatan++;
            if (pi.getPostSentimentalType() == 0) {
                neutralsyucatan++;
            } else if (pi.getPostSentimentalType() == 1) {
                positivesyucatan++;
            } else if (pi.getPostSentimentalType() == 2) {
                negativesyucatan++;
            }
        }


        float intPorcentajeyucatan = ((float) totalyucatan * 100) / (float) totalPost;
        float porcentajeNeutralsyucatan = 0;
        float porcentajePositivesyucatan = 0;
        float porcentajeNegativesyucatan = 0;

        if (totalyucatan != 0) {
            porcentajeNeutralsyucatan = ((float) neutralsyucatan * 100) / (float) totalyucatan;
            porcentajePositivesyucatan = ((float) positivesyucatan * 100) / (float) totalyucatan;
            porcentajeNegativesyucatan = ((float) negativesyucatan * 100) / (float) totalyucatan;
        }

        //total positivos negativos neutros zacatcas
        Iterator zacatecasI = zacatecas.iterator();
        int neutralszacatecas = 0, positiveszacatecas = 0, negativeszacatecas = 0, totalzacatecas = 0;
        while (zacatecasI.hasNext()) {
            PostIn pi = (PostIn) zacatecasI.next();
            totalzacatecas++;
            if (pi.getPostSentimentalType() == 0) {
                neutralszacatecas++;
            } else if (pi.getPostSentimentalType() == 1) {
                positiveszacatecas++;
            } else if (pi.getPostSentimentalType() == 2) {
                negativeszacatecas++;
            }
        }


        float intPorcentajezacatecas = ((float) totalzacatecas * 100) / (float) totalPost;
        float porcentajeNeutralszacatecas = 0;
        float porcentajePositiveszacatecas = 0;
        float porcentajeNegativeszacatecas = 0;

        if (totalzacatecas != 0) {
            porcentajeNeutralszacatecas = ((float) neutralszacatecas * 100) / (float) totalzacatecas;
            porcentajePositiveszacatecas = ((float) positiveszacatecas * 100) / (float) totalzacatecas;
            porcentajeNegativeszacatecas = ((float) negativeszacatecas * 100) / (float) totalzacatecas;
        }






        if (filter.equals("all")) {


            getJson(node, "Aguascalientes", totalAguascalientes, intPorcentajeAguascalientes, positivesAguascalientes, negativesAguascalientes, neutralsAguascalientes, totalPost);
            getJson(node, "Baja California", totalbCalifornia, intPorcentajeBajaCalifornia, positivesbCalifornia, negativesbCalifornia, neutralsbCalifornia, totalPost);
            getJson(node, "Baja California Sur", totalbCaliforniaSI, intPorcentajeBajaCaliforniaS, positivesbCaliforniaSI, negativesbCaliforniaSI, neutralsbCaliforniaSI, totalPost);
            getJson(node, "Campeche", totalCampeche, intPorcentajeCampeche, positivesCampeche, negativesCampeche, neutralsCampeche, totalPost);
            getJson(node, "Coahuila de Zaragoza", totalCoahuila, intPorcentajeCohauila, positivesCoahuia, negativesCoahuila, neutralsCoahuila, totalPost);
            getJson(node, "Colima", totalColima, intPorcentajeCohauila, positivesCoahuia, negativesCoahuila, neutralsCoahuila, totalPost);
            getJson(node, "Chiapas", totalChiapas, intPorcentajeChiapas, positivesChiapas, negativesChiapas, neutralsChiapas, totalPost);
            getJson(node, "Chihuahua", totalChihuahua, intPorcentajeChihuahua, positivesChihuahua, negativesChihuahua, neutralsChihuahua, totalPost);
            getJson(node, "Distrito Federal", totaldistritoFederal, intPorcentajeDF, positivesdistritoFederal, negativesdistritoFederal, neutralsdistritoFederal, totalPost);
            getJson(node, "Durango", totaldurango, intPorcentajeDurango, positivesdurango, negativesdurango, neutralsdurango, totalPost);
            getJson(node, "Guanajuato", totalguanajuato, intPorcentajeGuanajuato, positivesguanajuato, negativesguanajuato, neutralsguanajuato, totalPost);
            getJson(node, "Guerrero", totalguerrero, intPorcentajeGuerrero, positivesguerrero, negativesguerrero, neutralsguerrero, totalPost);
            getJson(node, "Hidalgo", totalhidalgo, intPorcentajehidalgo, positiveshidalgo, negativeshidalgo, neutralshidalgo, totalPost);
            getJson(node, "Jalisco", totaljalisco, intPorcentajejalisco, positivesjalisco, negativesjalisco, neutralsjalisco, totalPost);
            getJson(node, "Estado de México", totalestadoMexico, intPorcentajeestadoMexico, positivesestadoMexico, negativesestadoMexico, neutralsestadoMexico, totalPost);
            getJson(node, "Michoacán", totalmichoacan, intPorcentajemichoacan, positivesmichoacan, negativesmichoacan, neutralsmichoacan, totalPost);
            getJson(node, "Morelos", totalmorelos, intPorcentajemorelos, positivesmorelos, negativesmorelos, neutralsmorelos, totalPost);
            getJson(node, "Nayarit", totalnayarit, intPorcentajenayarit, positivesnayarit, negativesnayarit, neutralsnayarit, totalPost);
            getJson(node, "Nuevo Leon", totalnuevoLeon, intPorcentajenuevoLeon, positivesnuevoLeon, negativesnuevoLeon, neutralsnuevoLeon, totalPost);
            getJson(node, "Oaxaca", totaloaxaca, intPorcentajeoaxaca, positivesoaxaca, negativesoaxaca, neutralsoaxaca, totalPost);
            getJson(node, "Puebla", totaloaxaca, intPorcentajepuebla, positivespuebla, negativespuebla, neutralspuebla, totalPost);
            getJson(node, "Querétaro", totalqueretaro, intPorcentajequeretaro, positivesqueretaro, negativesqueretaro, neutralsqueretaro, totalPost);
            getJson(node, "Quintana Roo", totalquintanaRoo, intPorcentajequintanaRoo, positivesquintanaRoo, negativesquintanaRoo, neutralsquintanaRoo, totalPost);
            getJson(node, "San Luis Potosi", totalsanLuis, intPorcentajesanLuis, positivessanLuis, negativessanLuis, neutralssanLuis, totalPost);
            getJson(node, "Sinaloa", totalsinaloa, intPorcentajesanLuis, positivessanLuis, negativessanLuis, neutralssanLuis, totalPost);
            getJson(node, "Sonora", totalsonora, intPorcentajesonora, positivessonora, negativessonora, neutralssonora, totalPost);
            getJson(node, "Tabasco", totaltabasco, intPorcentajetabasco, positivestabasco, negativestabasco, neutralstabasco, totalPost);
            getJson(node, "Tamaulipas", totaltamaulipas, intPorcentajetamaulipas, positivestamaulipas, negativestamaulipas, neutralstamaulipas, totalPost);
            getJson(node, "tlaxcala", totaltlaxcala, intPorcentajetlaxcala, positivestlaxcala, negativestlaxcala, neutralstlaxcala, totalPost);
            getJson(node, "Veracruz de Ignacio de la Llave", totalveracruz, intPorcentajeveracruz, positivesveracruz, negativesveracruz, neutralsveracruz, totalPost);
            getJson(node, "Yucatán", totalyucatan, intPorcentajeyucatan, positivesyucatan, negativesyucatan, neutralsyucatan, totalPost);
            getJson(node, "Zacatecas", totalzacatecas, intPorcentajezacatecas, positiveszacatecas, negativeszacatecas, neutralszacatecas, totalPost);
            getJson(node, "No definido", totalnodefined, intPorcentajenodefinido, positivesnoefined, negativesnodefined, neutralsnodefined, totalPost);

        } else if (filter.equals("Aguascalientes")) {

            getJsonPositivesNegativesNeutrals(filter, node, positivesAguascalientes, negativesAguascalientes, neutralsAguascalientes, porcentajePositivesAguascalientes, porcentajeNegativesAguascalientes, porcentajeNeutralsAguascalientes);

        } else if (filter.equals("Baja California")) {

            getJsonPositivesNegativesNeutrals(filter, node, positivesbCalifornia, negativesbCalifornia, neutralsbCalifornia, porcentajePositivesBajaCalifornia, porcentajeNegativesBajaCalifornia, porcentajeNeutralsBajaCalifornia);

        } else if (filter.equals("Baja California Sur")) {

            getJsonPositivesNegativesNeutrals(filter, node, positivesbCaliforniaSI, negativesbCaliforniaSI, neutralsbCaliforniaSI, porcentajePositivesBajaCaliforniaS, porcentajeNegativesBajaCaliforniaS, porcentajeNeutralsBajaCaliforniaS);

        } else if (filter.equals("Campeche")) {

            getJsonPositivesNegativesNeutrals(filter, node, positivesCampeche, negativesCampeche, neutralsCampeche, porcentajePositivesCampeche, porcentajeNegativesCampeche, porcentajeNeutralsCampeche);

        } else if (filter.equals("Coahuila de Zaragoza")) {

            getJsonPositivesNegativesNeutrals(filter, node, positivesCoahuia, negativesCoahuila, neutralsCoahuila, porcentajePositivesCoahuila, porcentajeNegativesCohauila, porcentajeNeutralsCoahuila);

        } else if (filter.equals("Colima")) {

            getJsonPositivesNegativesNeutrals(filter, node, positivesColima, negativesColima, neutralsColima, porcentajePositivesColima, porcentajeNegativesColima, porcentajeNeutralsColima);

        } else if (filter.equals("Chiapas")) {

            getJsonPositivesNegativesNeutrals(filter, node, positivesChiapas, negativesChiapas, neutralsChiapas, porcentajePositivesChiapas, porcentajeNegativesChiapas, porcentajeNeutralsChiapas);

        } else if (filter.equals("Chihuahua")) {

            getJsonPositivesNegativesNeutrals(filter, node, positivesChihuahua, negativesChihuahua, neutralsChihuahua, porcentajePositivesChihuahua, porcentajeNegativesChihuahua, porcentajeNeutralsChihuahua);

        } else if (filter.equals("Distrito Federal")) {

            getJsonPositivesNegativesNeutrals(filter, node, positivesdistritoFederal, negativesdistritoFederal, neutralsdistritoFederal, porcentajePositivesDF, porcentajeNegativesDF, porcentajeNeutralsDF);

        } else if (filter.equals("Durango")) {

            getJsonPositivesNegativesNeutrals(filter, node, positivesdurango, negativesdurango, neutralsdurango, porcentajePositivesDurango, porcentajeNegativesDurango, porcentajeNeutralsDurango);

        } else if (filter.equals("Guanajuato")) {

            getJsonPositivesNegativesNeutrals(filter, node, positivesguanajuato, negativesguanajuato, neutralsguanajuato, porcentajePositivesGuanajuato, porcentajeNegativesGuanajuato, porcentajeNeutralsGuanajuato);

        } else if (filter.equals("Guerrero")) {

            getJsonPositivesNegativesNeutrals(filter, node, positivesguerrero, negativesguerrero, neutralsguerrero, porcentajePositivesGuerrero, porcentajeNegativesGuerrero, porcentajeNeutralsGuerrero);

        } else if (filter.equals("Hidalgo")) {

            getJsonPositivesNegativesNeutrals(filter, node, positiveshidalgo, negativeshidalgo, neutralshidalgo, porcentajePositiveshidalgo, porcentajeNegativeshidalgo, porcentajeNeutralshidalgo);

        } else if (filter.equals("Jalisco")) {

            getJsonPositivesNegativesNeutrals(filter, node, positivesjalisco, negativesjalisco, neutralsjalisco, porcentajePositivesjalisco, porcentajeNegativesjalisco, porcentajeNeutralsjalisco);

        } else if (filter.equals("Estado de Mexico")) {
            getJsonPositivesNegativesNeutrals(filter, node, positivesestadoMexico, negativesestadoMexico, neutralsestadoMexico, porcentajePositivesestadoMexico, porcentajeNegativesestadoMexico, porcentajeNeutralsestadoMexico);

        } else if (filter.equals("Michoacan")) {

            getJsonPositivesNegativesNeutrals(filter, node, positivesmichoacan, negativesmichoacan, neutralsmichoacan, porcentajePositivesmichoacan, porcentajeNegativesmichoacan, porcentajeNeutralsmichoacan);

        } else if (filter.equals("Morelos")) {

            getJsonPositivesNegativesNeutrals(filter, node, positivesmorelos, negativesmorelos, neutralsmorelos, porcentajePositivesmorelos, porcentajeNegativesmorelos, porcentajeNeutralsmorelos);

        } else if (filter.equals("Nayarit")) {

            getJsonPositivesNegativesNeutrals(filter, node, positivesnayarit, negativesnayarit, neutralsnayarit, porcentajePositivesnayarit, porcentajeNegativesnayarit, porcentajeNeutralsnayarit);

        } else if (filter.equals("Nuevo Leon")) {

            getJsonPositivesNegativesNeutrals(filter,  node, positivesnuevoLeon, negativesnuevoLeon, neutralsnuevoLeon, porcentajePositivesnuevoLeon, porcentajeNegativesnuevoLeon, porcentajeNeutralsnuevoLeon);

        } else if (filter.equals("Oaxaca")) {

            getJsonPositivesNegativesNeutrals(filter, node, positivesoaxaca, negativesoaxaca, neutralsoaxaca, porcentajePositivesoaxaca, porcentajeNegativesoaxaca, porcentajeNeutralsoaxaca);

        } else if (filter.equals("Puebla")) {

            getJsonPositivesNegativesNeutrals(filter,  node, positivespuebla, negativespuebla, neutralspuebla, porcentajePositivespuebla, porcentajeNegativespuebla, porcentajeNeutralspuebla);

        } else if (filter.equals("Queretaro")) {

            getJsonPositivesNegativesNeutrals(filter,  node, positivesqueretaro, negativesqueretaro, neutralsqueretaro, porcentajePositivesqueretaro, porcentajeNegativesqueretaro, porcentajeNeutralsqueretaro);

        } else if (filter.equals("Quintana Roo")) {

            getJsonPositivesNegativesNeutrals(filter, node, positivesquintanaRoo, negativesquintanaRoo, neutralsquintanaRoo, porcentajePositivesquintanaRoo, porcentajeNegativesquintanaRoo, porcentajeNeutralsquintanaRoo);

        } else if (filter.equals("San Luis Potosi")) {

            getJsonPositivesNegativesNeutrals(filter, node, positivessanLuis, negativessanLuis, neutralssanLuis, porcentajePositivessanLuis, porcentajeNegativessanLuis, porcentajeNeutralssanLuis);

        } else if (filter.equals("Sinaloa")) {

            getJsonPositivesNegativesNeutrals(filter,  node, positivessinaloa, negativessinaloa, neutralssinaloa, porcentajePositivessinaloa, porcentajeNegativessinaloa, porcentajeNeutralssinaloa);

        } else if (filter.equals("Sonora")) {

            getJsonPositivesNegativesNeutrals(filter,  node, positivessonora, negativessonora, neutralssonora, porcentajePositivessonora, porcentajeNegativessonora, porcentajeNeutralssonora);

        } else if (filter.equals("Tabasco")) {

            getJsonPositivesNegativesNeutrals(filter,  node, positivestabasco, negativestabasco, neutralstabasco, porcentajePositivestabasco, porcentajeNegativestabasco, porcentajeNeutralstabasco);

        } else if (filter.equals("Tamaulipas")) {

            getJsonPositivesNegativesNeutrals(filter,  node, positivestamaulipas, negativestamaulipas, neutralstamaulipas, porcentajePositivestamaulipas, porcentajeNegativestamaulipas, porcentajeNeutralstamaulipas);

        } else if (filter.equals("Tlaxcala")) {

            getJsonPositivesNegativesNeutrals(filter,  node, positivestlaxcala, negativestlaxcala, neutralstlaxcala, porcentajePositivestlaxcala, porcentajeNegativestlaxcala, porcentajeNeutralstlaxcala);

        } else if (filter.equals("Veracruz")) {

            getJsonPositivesNegativesNeutrals(filter,  node, positivesveracruz, negativesveracruz, neutralsveracruz, porcentajePositivesveracruz, porcentajeNegativesveracruz, porcentajeNeutralsveracruz);

        } else if (filter.equals("Yucatan")) {

            getJsonPositivesNegativesNeutrals(filter,  node, positivesyucatan, negativesyucatan, neutralsyucatan, porcentajePositivesyucatan, porcentajeNegativesyucatan, porcentajeNeutralsyucatan);

        } else if (filter.equals("Zacatecas")) {

            getJsonPositivesNegativesNeutrals(filter,  node, positiveszacatecas, negativeszacatecas, neutralszacatecas, porcentajePositiveszacatecas, porcentajeNegativeszacatecas, porcentajeNeutralszacatecas);

        } else if (filter.equals("No definido")) {

            getJsonPositivesNegativesNeutrals(filter,  node, positivesnoefined, negativesnodefined, neutralsnodefined, porcentajePositivesnodefined, porcentajeNegativesnodefined, porcentajeNeutralsnodefined);

        }



        /*if (map.isEmpty()) {

         JSONObject node3 = new JSONObject();
         node3.put("label", "Neutros");
         node3.put("value1", "0");
         node3.put("value2", "100");
         node3.put("color", "#eae8e3");
         node3.put("chartclass", "neuClass");
         node3.put("label2", "Sin datos para procesar");
         node3.put("label3", "Total de Post: " + totalPost);

         node.put(node3);

         }*/

        return node;

    }

    public JSONArray getJson(JSONArray node, String label, int total, float porcentaje, int positives, int negatives, int neutrals, int totalPost) throws Exception {

        JSONObject node_ = new JSONObject();
        node_.put("label", "" + label);
        node_.put("value1", "" + total);
        node_.put("value2", "" + round(porcentaje));
        if (positives > negatives && positives > neutrals) {
            node_.put("color", "#008000");
        } else if (negatives > neutrals) {
            node_.put("color", "#FF0000");
        } else {
            node_.put("color", "#FFD700");
        }
        node_.put("label2", ""+ label+": "+total+ " -     Positivos : " + positives + "  Negativos: "  + negatives + "  Neutros : " + neutrals);
        node_.put("chartclass", "possClass");
        node_.put("label3", "Total de Post: " + totalPost);
        node.put(node_);
        return node;


    }

    public JSONArray getJsonPositivesNegativesNeutrals(String filter,JSONArray node, int positives, int negatives, int neutrals, float intPorcentajePositives, float intPorcentajeNegatives, float intPorcentajeNeutrals) throws Exception {

        if (neutrals > 0) {
            JSONObject node4 = new JSONObject();
            node4.put("label", "Neutros");
            node4.put("value1", "" + neutrals);
            node4.put("value2", "" + round(intPorcentajeNeutrals));
            node4.put("label2", ""+ filter+ "Positivos" + positives + " Negativos"  + negatives + " Neutros : " + neutrals);
            node4.put("color", "#FFD700");
            node4.put("chartclass", "possClass");
            //node4.put("label3", "Total de Post: " + totalPost);
            node.put(node4);
        }

        if (positives > 0) {
            JSONObject node5 = new JSONObject();
            node5.put("label", "Positivos");
            node5.put("value1", "" + positives);
            node5.put("value2", "" + round(intPorcentajePositives));
            node5.put("color", "#008000");
            node5.put("label2", "");
            node5.put("chartclass", "possClass");
            //node5.put("label3", "Total de Post: " + totalPost);
            node.put(node5);
        }

        if (negatives > 0) {

            JSONObject node6 = new JSONObject();
            node6.put("label", "Negativos");
            node6.put("value1", "" + negatives);
            node6.put("value2", "" + round(intPorcentajeNegatives));
            node6.put("color", "#FF0000");
            node6.put("label2", "");
            node6.put("chartclass", "possClass");
           // node6.put("label3", "Total de Post: " + totalPost);
            node.put(node6);
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

    public String reemplazar(String cadena) {

        String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ";
        // Cadena de caracteres ASCII que reemplazarán los originales.
        String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
        String output = cadena;
        for (int i = 0; i < original.length(); i++) {
            // Reemplazamos los caracteres especiales.
            output = output.replace(original.charAt(i), ascii.charAt(i));
        }//for i

        return output;
    }
%>
<%

    if (request.getParameter("objUri") != null) {
        SemanticObject semObj = SemanticObject.getSemanticObject(request.getParameter("objUri"));
        String lang = request.getParameter("lang");
        String idModel = request.getParameter("idModel");
        String filter = request.getParameter("filter");
        System.out.println("Filter: " + filter);
        out.println(getObject(semObj, lang, idModel, filter));
    }
%>