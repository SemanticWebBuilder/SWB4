package org.semanticwb.portal.community;

import java.util.ArrayList;
import java.util.Iterator;


public class CURPModule extends org.semanticwb.portal.community.base.CURPModuleBase
{
    public CURPModule(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static Iterator<String> listStates () {
        //TODO: cambiar código por código para obtener la lista del catálogo
        ArrayList<String> states = new ArrayList<String>();

        String [] st = {"AGUASCALIENTES", "BAJA CALIFORNIA", "BAJA CALIFORNIA SUR",
                        "CAMPECHE", "COAHUILA", "COLIMA", "CHIAPAS", "CHIHUAHUA",
                        "DISTRITO FEDERAL", "DURANGO", "ESTADO DE MEXICO",
                        "GUANAJUATO", "GUERRERO", "HIDALGO", "JALISCO", "MICHOACAN",
                        "MORELOS", "NAYARIT", "NUEVO LEON", "OAXACA", "PUEBLA",
                        "QUERETARO", "QUINTANA ROO", "SAN LUIS POTOSI",
                        "SINALOA", "SONORA", "TABASCO", "TAMAULIPAS", "TLAXCALA",
                        "VERACRUZ", "YUCATAN", "ZACATECAS"};

        for (int i = 0; i < st.length; i++) {
            String string = st[i];
            states.add(string);
        }

        return states.iterator();
    }

    public static Iterator<String> listCouncils (String state) {
        //TODO: cambiar código por código para obtener la lista del catálogo
        ArrayList<String> councils = new ArrayList<String>();

        if (state.equalsIgnoreCase("distrito federal")) {
            String [] c = {"ALVARO OBREGON", "AZCAPOTZALCO", "BENITO JUAREZ",
                           "COYOACAN", "CUAJIMALPA", "CUAUHTEMOC",
                           "GUSTAVO A. MADERO", "IZTACALCO", "IZTAPALAPA",
                           "MAGDALENA CONTRERAS", "MIGUEL HIDALGO", "MILPA ALTA",
                           "TLAHUAC", "TLALPAN", "VENUSTIANO CARRANZA", "XOCHIMILCO"};

            for (int i = 0; i < c.length; i++) {
                String string = c[i];
                councils.add(string);
            }
        } else if (state.equalsIgnoreCase("morelos")) {
            String [] c = {"AMACUZAC", "ATLATLAHUCAN", "AXOCHIAPAN", "AYALA",
                           "COATLAN DEL RIO", "CUAUTLA", "CUERNAVACA",
                           "EMILIANO ZAPATA", "HIUITZILAC", "JANTETELCO",
                           "JIUTEPEC", "JOJUTLA", "JONACATEPEC", "MAZATEPEC",
                           "MIACATLAN", "OCUITUCO", "PUENTE DE IXTLA", "TEMIXCO",
                           "TEMOAC", "TEPALCINGO", "TEPOZTLAN", "TETECALA",
                           "TETELA DEL VOLCAN", "TLALNEPANTLA", "TLALTIZAPAN",
                           "TLALQUILTENANGO", "TLAYACAPAN", "TOTOLAPAN", 
                           "XOCHITEPEC", "YAUTEPEC", "YECAPIXTLA",
                           "ZACATEPEC DE HIDALGO", "ZACUALPAN DE AMILPAS"};
            for (int i = 0; i < c.length; i++) {
                String string = c[i];
                councils.add(string);
            }
        } else {
            return null;
        }
        return councils.iterator();
    }

    public String getAddressString() {
        String streetName = getStreetName();
        if (streetName == null || streetName.equals("null")) {
            streetName = "";
        } else {
            streetName = streetName + " ";
        }
        String intNumber = getIntNumber();
        if (intNumber == null || intNumber.equals("null")) intNumber = "";
        String extNumber = getExtNumber();
        if (extNumber == null || extNumber.equals("null")) {
            extNumber = "";
        } else {
            extNumber = " exterior " + extNumber;
        }
        String council = getCityCouncil();
        if (council == null || council.equals("null")) {
            council = "";
        } else {
            council = ", " + council;
        }
        String city = getCity();
        if (city == null || city.equals("null")) {
            city = "";
        } else {
            city = ", " + city + " ";
        }
        String state = getState();
        if (state == null || state.equals("null")) state = "";

        return streetName + intNumber + extNumber + council + city + state;
    }
}
