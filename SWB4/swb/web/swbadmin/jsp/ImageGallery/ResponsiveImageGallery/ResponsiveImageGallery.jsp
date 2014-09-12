<%@page import="org.semanticwb.SWBPortal"%>
<%@page import="java.util.List"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<%
    List<String> imagenes = (List<String>) request.getAttribute("images");
    List<String> thumbnails = (List<String>) request.getAttribute("thumbnails");
    String pathJS = SWBPortal.getContextPath() + "/swbadmin/jsp/ImageGallery/ResponsiveImageGallery/js/";
    String pathIMG = SWBPortal.getContextPath() + "/swbadmin/jsp/ImageGallery/ResponsiveImageGallery/img/";
    String pathCSS = SWBPortal.getContextPath() + "/swbadmin/jsp/ImageGallery/ResponsiveImageGallery/css/";
    boolean autoplay = Boolean.valueOf(paramRequest.getResourceBase().getAttribute("autoplay", "false"));
    int autoPlayInterval = Integer.parseInt(paramRequest.getResourceBase().getAttribute("pause", "2500"));
    int slideDuration = Integer.parseInt(paramRequest.getResourceBase().getAttribute("fadetime", "500"));
    String title = paramRequest.getResourceBase().getTitle(paramRequest.getUser().getLanguage());
    if (title == null)
    {
        title = paramRequest.getResourceBase().getTitle();
    }
    if (title == null)
    {
        title = "";
    }
    boolean showTitle = Boolean.parseBoolean(paramRequest.getResourceBase().getAttribute("title", "false"));
    if (showTitle)
    {
%>
<h1><%=title%></h1>
<%
    }
%>
<link rel="stylesheet" type="text/css" href="<%=pathCSS%>demo.css" />
<link rel="stylesheet" type="text/css" href="<%=pathCSS%>style.css" />
<link rel="stylesheet" type="text/css" href="<%=pathCSS%>elastislide.css" />
<link href='http://fonts.googleapis.com/css?family=PT+Sans+Narrow&v1' rel='stylesheet' type='text/css' />
<link href='http://fonts.googleapis.com/css?family=Pacifico' rel='stylesheet' type='text/css' />
<noscript>
<style>
    .es-carousel ul{
        display:block;
    }
</style>
</noscript>
<script id="img-wrapper-tmpl" type="text/x-jquery-tmpl">	
    <div class="rg-image-wrapper">
    {{if itemsCount > 1}}
    <div class="rg-image-nav">
    <a href="#" class="rg-image-nav-prev">Previous Image</a>
    <a href="#" class="rg-image-nav-next">Next Image</a>
    </div>
    {{/if}}
    <div class="rg-image"></div>
    <div class="rg-loading"></div>
    <div class="rg-caption-wrapper">
    <div class="rg-caption" style="display:none;">
    <p></p>
    </div>
    </div>
    </div>
</script>


<div class="container">
    <div class="content">        
        <div id="rg-gallery" class="rg-gallery">
            <div class="rg-thumbs">
                <!-- Elastislide Carousel Thumbnail Viewer -->
                <div class="es-carousel-wrapper">
                    <div class="es-nav">
                        <span class="es-nav-prev">Previous</span>
                        <span class="es-nav-next">Next</span>
                    </div>
                    <div class="es-carousel">
                        <ul>
                            <%
                                int i = 0;
                                for (String image : imagenes)
                                {
                                    String thumb = thumbnails.get(i);
                                    i++;
                                    String description = "";
                            %>
                            <li><img src="<%=thumb%>" data-large="<%=image%>" alt="<%=image%>" data-description="<%=description%>" /></li>
                                <%
                                    }
                                %>                            
                        </ul>
                    </div>
                </div>
                <!-- End Elastislide Carousel Thumbnail Viewer -->
            </div><!-- rg-thumbs -->
        </div><!-- rg-gallery -->        
    </div><!-- content -->
</div><!-- container -->
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
<script type="text/javascript" src="<%=pathJS%>jquery.tmpl.min.js"></script>
<script type="text/javascript" src="<%=pathJS%>jquery.easing.1.3.js"></script>
<script type="text/javascript" src="<%=pathJS%>jquery.elastislide.js"></script>
<script type="text/javascript" src="<%=pathJS%>gallery.js"></script>


