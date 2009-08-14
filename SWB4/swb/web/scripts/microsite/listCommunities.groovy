import org.semanticwb.portal.api.SWBParamRequest
import org.semanticwb.model.User
import org.semanticwb.model.WebPage
import org.semanticwb.portal.community.Member
import org.semanticwb.portal.community.MicroSite



def paramRequest=request.getAttribute("paramRequest")
User user = paramRequest.getUser()
WebPage wpage=paramRequest.getWebPage()
Member member = Member.getMember(user,wpage)
def lista = Member.listMemberByUser(user,wpage.getWebSite())

println "Mis Comunidades:<ul>"
lista.each(){
    Member mem_curr = it
    MicroSite mem_mcs = mem_curr.getMicroSite()
    def titulo = mem_mcs.getDisplayName()
    def url = mem_mcs.getUrl()
    println "<li><a href=\"${url}\">$titulo</a></li>"
}
println "</ul>"