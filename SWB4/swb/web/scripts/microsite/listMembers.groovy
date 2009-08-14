import org.semanticwb.portal.api.SWBParamRequest
import org.semanticwb.model.User
import org.semanticwb.model.WebPage
import org.semanticwb.portal.community.Member
import org.semanticwb.portal.community.MicroSite
import org.semanticwb.portal.community.MicroSiteWebPageUtil
import org.semanticwb.model.SWBModel



def paramRequest=request.getAttribute("paramRequest")
User user = paramRequest.getUser()
WebPage wpage=paramRequest.getWebPage()
Member member = Member.getMember(user,wpage)
MicroSite microsite = ((MicroSiteWebPageUtil)wpage).getMicroSite()

String perfil = "/swb/sitio/perfilpage"

def lista = Member.listMemberByMicroSite(microsite, (SWBModel)wpage.getWebSite())

println "Miembros de la comunidad:<ul>"
lista.each(){
    Member mem_curr = it
    User mem_usr = mem_curr.getUser()
    def uri = mem_usr.getEncodedURI()
    def nombre = mem_usr.getFullName()
    println "<li><a href=\"${perfil}?uri=$uri\">$nombre</a></li>"
}
println "</ul>"

