/**
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
 **/

import org.semanticwb.portal.api.SWBParamRequest
import org.semanticwb.model.User
import org.semanticwb.model.WebPage
import org.semanticwb.portal.community.Member
import org.semanticwb.portal.community.MicroSite
import org.semanticwb.portal.community.MicroSiteWebPageUtil
import org.semanticwb.model.SWBModel
import org.semanticwb.SWBPlatform
import org.semanticwb.platform.SemanticProperty



def paramRequest=request.getAttribute("paramRequest")
User user = paramRequest.getUser()
WebPage wpage=paramRequest.getWebPage()
Member member = Member.getMember(user,wpage)
MicroSite microsite = null
if (wpage instanceof MicroSiteWebPageUtil) 
microsite = ((MicroSiteWebPageUtil)wpage).getMicroSite()
else if (wpage instanceof MicroSite)
microsite = wpage

if (null!=microsite){


    String perfil = wpage.getWebSite().getWebPage("perfil").getRealUrl()

    Iterator<Member> lista = microsite.listMembers()
    if (paramRequest.getCallMethod()==paramRequest.Call_STRATEGY && (!paramRequest.getArgument("virtualcontent").equals("true"))){
        println """<div id="contactos" class="miembros">
<h2>Miembros de la comunidad</h2><div>
"""


        def i = 0;
        while (lista.hasNext() && i<18){
            Member mem_curr = lista.next()
            User mem_usr = mem_curr.getUser()
            if (null!=mem_usr)
            {
                def uri = mem_usr.getEncodedURI()
                def nombre = mem_usr.getFullName()
                def img = SWBPlatform.getWebWorkPath()+mem_usr.getPhoto()
                if (null==img) img = "/swbadmin/images/defaultPhoto.jpg"
                println """<div class="moreUser"><a href="${perfil}?user=$uri" alt="Ir al perfil de $nombre"><img src="$img" width="39" height="39"  /></a></div>"""
            }
        }

        def url_mas = wpage.getRealUrl()

        println """</div><div class="clear"></div>"""
        if(i==18)
        {
            println """<div><p class="vermas"><a href="${url_mas}_Members" >Ver todos</a></p></div>"""
        }
        println """</div>"""
    } else {
        def mapa = new HashMap()
Iterator<SemanticProperty> list = org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#_ExtendedAttributes").listProperties();
                list.each{
                    def sp = it
                    mapa.put(sp.getName(),sp)
                }
        println """<div id="entriesWrapper">
<div class="entry">"""
        lista.each(){
            Member mem_curr = it
            User mem_usr = mem_curr.getUser()
            if (null!=mem_usr)
            {
                def uri = mem_usr.getEncodedURI()
                def nombre = mem_usr.getFullName()
                def img = SWBPlatform.getWebWorkPath()+mem_usr.getPhoto()
                if (null==img) img = "/swbadmin/images/defaultPhoto.jpg"
                def usr_age = mem_usr.getExtendedAttribute(mapa.get("userAge"))
                if (null==usr_age) usr_age = ""
                def usr_sex = mem_usr.getExtendedAttribute(mapa.get("userSex"))
                if ("M".equals(usr_sex)) usr_sex = "Hombre"
                if ("F".equals(usr_sex)) usr_sex = "Mujer"
                def usr_status = mem_usr.getExtendedAttribute(mapa.get("userStatus"))
                if (null==usr_status) usr_status = ""
                println """
<img src="$img" width="150" height="150" alt="Foto de $nombre" />
<div class="entryInfo"><p class="tituloNaranja"><a href="${perfil}?user=$uri" alt="Ir al perfil de $nombre" >$nombre</a></p>
<p>Edad: $usr_age</p>
<p>Sexo: $usr_sex</p>
<p>Tipo: $usr_status</p>
</div></div>"""
            }
        }
        println """</div>"""
    }

}
