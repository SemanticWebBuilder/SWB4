package org.semanticwb.portal.community;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

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

    public static Map<String, String> getCouncilsMap () {
        //TODO: cambiar código por código para obtener la lista del catálogo

        HashMap<String, String> councils = new HashMap<String, String>();

        String s_councils = "AGUASCALIENTES|ASIENTOS|CALVILLO|COSIO|EL LLANO|" +
                "JESUS MARIA|PABELLON DE ARTEAGA|RINCON DE ROMOS|" +
                "SAN FRANCISCO DE LOS ROMO|SAN JOSE DE GARCIA|TEPEZALA";
        councils.put("AGUASCALIENTES", s_councils);

        s_councils = "ENSENADA|MEXICALI|PLAYAS DE ROSARITO|TECATE|TIJUANA";
        councils.put("BAJA CALIFORNIA", s_councils);

        s_councils = "COMONDU|LA PAZ|LORETO|LOS CABOS|MULEGE";
        councils.put("BAJA CALIFORNIA SUR", s_councils);

        s_councils = "CALAKMUL|CALKINI|CAMPECHE|CANDELARIA|CARMEN|CHAMPOTON|" +
                "ESCARCEGA|HECELCHAKAN|HOPELCHEN|PALIZADA|TENABO";
        councils.put("CAMPECHE", s_councils);

        s_councils = "ABASOLO|ACU&Ntilde;A|ALLENDE|ARTEAGA|CANDELA|" +
                "CASTA&Ntilde;OS|CUATROCIENEGAS|ESCOBEDO|FRANCISCO I. MADERO|" +
                "FRONTERA|GENERAL CEPEDA|GUERRERO|HIDALGO|JIMENEZ|JUAREZ|" +
                "LA MADRID|MATAMOROS|MONCLOVA|MORELOS|MUZQUIZ|NADADORES|" +
                "NAVA|OCAMPO|PARRAS|PIEDRAS NEGRAS|PROGRESO|RAMOS ARIZPE|" +
                "SABINAS|SACRAMENTO|SALTILLO|SAN BUENAVENTURA|SAN JUAN DE SABINAS|" +
                "SAN PEDRO|SIERRA MOJADA|TORREON|VIESCA|VILLA UNION|ZARAGOZA";
        councils.put("COAHUILA", s_councils);
        
        s_councils = "ALVARO OBREGON|AZCAPOTZALCO|BENITO JUAREZ|COYOACAN|" +
                "CUAJIMALPA|CUAUHTEMOC|GUSTAVO A. MADERO|IZTACALCO|" +
                "IZTAPALAPA|MAGDALENA CONTRERAS|MIGUEL HIDALGO|" +
                "MILPA ALTA|TLAHUAC|TLALPAN|VENUSTIANO CARRANZA|" +
                "XOCHIMILCO";
        councils.put("DISTRITO FEDERAL", s_councils);

        s_councils = "AMACUZAC|ATLATLAHUCAN|AXOCHIAPAN|AYALA|COATLAN DEL RIO|" +
                "CUAUTLA|CUERNAVACA|EMILIANO ZAPATA|HIUITZILAC|JANTETELCO|" +
                "JIUTEPEC|JOJUTLA|JONACATEPEC|MAZATEPEC|MIACATLAN|OCUITUCO|" +
                "PUENTE DE IXTLA|TEMIXCO|TEMOAC|TEPALCINGO|TEPOZTLAN|TETECALA|" +
                "TETELA DEL VOLCAN|TLALNEPANTLA|TLALTIZAPAN|TLALQUILTENANGO|" +
                "TLAYACAPAN|TOTOLAPAN|XOCHITEPEC|YAUTEPEC|YECAPIXTLA|" +
                "ZACATEPEC DE HIDALGO|ZACUALPAN DE AMILPAS";
        councils.put("MORELOS", s_councils);

        return new TreeMap(councils);
    }

    public String getAddressString() {
        String streetName = getStreetName();
        if (streetName == null || streetName.equals("null")) {
            streetName = "";
        } else {
            streetName = streetName + " ";
        }
        String intNumber = getIntNumber();
        if (intNumber == null || intNumber.equals("null")) {
            intNumber = "";
        } else {
            intNumber = " interior " + intNumber + ", ";
        }

        String extNumber = getExtNumber();
        if (extNumber == null || extNumber.equals("null")) {
            extNumber = "";
        } else {
            extNumber = " exterior " + extNumber + ",<br>";
        }
        String council = getCityCouncil();
        if (council == null || council.equals("null")) council = "";
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