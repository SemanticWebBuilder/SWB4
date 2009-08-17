<%@page contentType="text/html"%>
<%@page import="org.semanticwb.portal.api.*,org.semanticwb.portal.community.*,org.semanticwb.*,org.semanticwb.model.*,java.util.*"%>
<%@page import="org.semanticwb.model.Resource" %>
<%
            SWBParamRequest paramRequest = (SWBParamRequest) request.getAttribute("paramRequest");
            Resource base = paramRequest.getResourceBase();
            User user = paramRequest.getUser();
            WebPage wpage = paramRequest.getWebPage();
            MicroSiteWebPageUtil wputil = MicroSiteWebPageUtil.getMicroSiteWebPageUtil(wpage);
            Member member = Member.getMember(user, wpage);

            String path = SWBPlatform.getWebWorkPath() + base.getWorkPath() + "/";
%>

<script type="text/javascript">
    function thumbnailizeImage(idImg) {
        alert('thumbnailizeImage');
        var image = document.getElementById(idImg);
        if(image.width > image.height) {
            var nw = 130;
            var nh = 95;
        }else {
            var nw = 95;
            var nh = 130;
        }
        image.width = nw;
        image.height = nh;
    }

    function createImageContainer(containerId) {
        var container = document.createElement('div');
        container.style.styleFloat = 'left';
        container.style.cssFloat = 'left'
        container.style.marginTop = '30px';
        container.style.marginLeft = '30px';
        container.style.marginRight = '30px';
        container.style.textAlign = 'center';
        return container;
    }

    function createThumbnail(imgId, imgUrl) {
        var img = document.createElement('img');
        img.id = imgId;
        img.src = imgUrl;
        /*var w = img.width;
        var h = img.height;*/

        if(img.width > img.height) {
            img.width = 130;
            img.height = 95;
        }else {
            img.width = 95;
            img.height = 130;
        }
        return img;
    }

    function createTextInfo(c, id, title, author, date, desc) {
        var p = document.createElement('p');
        p.innerText = title;
        p.textContent = title;
        p.style.lineHeight = '2px';
        c.appendChild(p);

        p = document.createElement('p');
        p.innerText = author;
        p.textContent = author;
        p.style.lineHeight = '2px';
        c.appendChild(p);

        p = document.createElement('p');
        p.innerText = date;
        p.textContent = date;
        p.style.lineHeight = '2px';
        c.appendChild(p);

        p = document.createElement('p');
        p.innerText = desc;
        p.textContent = desc;
        p.style.lineHeight = '2px';
        c.appendChild(p);
    }
</script>

<%
            Iterator<PhotoElement> it = PhotoElement.listPhotoElementByPhotoWebPage(wpage, wpage.getWebSite());
%>

<table border="0" width="90%">
    <tr><td>
            <div id="photoc_<%= base.getId()%>">
                <script type="text/javascript">
                    var photoContainer;
                    var img;
                    var a;
                    var mc = document.getElementById('photoc_<%= base.getId()%>');
                    <%
                            int i = 0;
                            while (it.hasNext()) {
                                PhotoElement photo = it.next();
                                SWBResourceURL viewurl = paramRequest.getRenderUrl();
                                viewurl.setParameter("act", "detail");
                                viewurl.setParameter("uri", photo.getURI());
                    %>
                        photoContainer = createImageContainer('ic_'+ '<%= i%>' +'<%= base.getId()%>');
                        img = createThumbnail('img_'+'<%= i%>'+'<%= base.getId()%>', '<%= path+photo.getImageURL()%>');
                        a = document.createElement('a');
                        a.href = '<%= viewurl %>';
                        a.appendChild(img);
                        photoContainer.appendChild(a);
                        createTextInfo(photoContainer, 'p_'+'<%= i%>'+'<%= base.getId()%>', '<%= photo.getTitle()%>', '<%= photo.getCreator().getFirstName()%>', '<%= photo.getCreated()%>', '<%= photo.getDescription()%>');
                        mc.appendChild(photoContainer);
                    <%
                                i++;
                            }
                    %>
                </script>
            </div>
        </td></tr>
</table>
<%
            System.out.println(member);
            if (member.canAdd()) {
%>
<center>
    <a href="<%=paramRequest.getRenderUrl().setParameter("act", "add").toString()%>">Agregar foto</a>
    <%
            if (wputil != null && member.canView()) {
                if (!wputil.isSubscribed(member)) {
    %>
    <a href="<%=paramRequest.getActionUrl().setParameter("act", "subscribe").toString()%>">Suscribirse a este elemento</a>
    <%
            } else {
    %>
    <a href="<%=paramRequest.getActionUrl().setParameter("act", "unsubscribe").toString()%>">Cancelar suscripción</a>
    <%
                }

            }
    %>
</center>
<%  } else {%>
<p>no puedes ver el menu</p>
<%    }%>