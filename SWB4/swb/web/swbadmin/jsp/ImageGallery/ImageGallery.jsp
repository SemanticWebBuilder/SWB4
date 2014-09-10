<%@page import="java.util.List"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest"/>
<style>
    .cf:before,
    .cf:after {content:""; display:table;}
    .cf:after {clear:both;}
    .cf {zoom:1;}

    .toggle { margin: 2px 0 0 14px; float: left; border-radius: 6px; -moz-border-radius: 6px; -webkit-border-radius: 6px; }
    .toggle li { float: left; }
    .toggle li a {width: 50px; padding: 6px 0; text-align: center; display: block; text-shadow: 1px 1px 0 #fff; font-size: 12px; font-weight: 600; color: #666; -webkit-border-radius: 0 4px 4px 0; -moz-border-radius: 0 4px 4px 0; -o-border-radius: 0 4px 4px 0; border-radius: 0 4px 4px 0; 

                  background: #ffffff; /* Old browsers */
                  background: -moz-linear-gradient(top, #ffffff 0%, #ededed 100%); /* FF3.6+ */
                  background: -webkit-gradient(linear, left top, left bottom, color-stop(0%,#ffffff), color-stop(100%,#ededed)); /* Chrome,Safari4+ */
                  background: -webkit-linear-gradient(top, #ffffff 0%,#ededed 100%); /* Chrome10+,Safari5.1+ */
                  background: -o-linear-gradient(top, #ffffff 0%,#ededed 100%); /* Opera 11.10+ */
                  background: -ms-linear-gradient(top, #ffffff 0%,#ededed 100%); /* IE10+ */
                  background: linear-gradient(top, #ffffff 0%,#ededed 100%); /* W3C */

                  box-shadow: inset 1px 1px 0 #fff, inset -1px -1px 0 #fff;
                  -moz-box-shadow: inset 1px 1px 0 #fff, inset -1px -1px 0 #fff;
                  -webkit-box-shadow: inset 1px 1px 0 #fff, inset -1px -1px 0 #fff;

    }
    .toggle li:first-child a {-webkit-border-radius: 4px 0 0 4px; -moz-border-radius: 4px 0 0 4px; -o-border-radius: 4px 0 0 4px; border-radius: 4px 0 0 4px;}
    .toggle li a:hover { background: #ededed; color: #222; }
    .toggle li a.active { background: #c8e0f3; color: #325874; cursor: default; box-shadow: inset 0 0 3px rgba(0,0,0,0.4); -moz-box-shadow: inset 0 0 3px rgba(0,0,0,0.4); -webkit-box-shadow: inset 0 0 3px rgba(0,0,0,0.4); }

    .slider { margin: 50px 0 10px!important;}
    #carousel li {margin-right: 5px;}
    #carousel img {display: block; opacity: .5; cursor: pointer;}
    #carousel img:hover {opacity: 1;}
    #carousel .flex-active-slide img {opacity: 1; cursor: default;}




    /* Layout
    ***********************/
    #container {padding: 60px 40px 100px;}
    header {width: 420px; float: left;}
    #main {overflow: hidden;}
    aside h3 {float: left;}

    /* Header
    ***********************/    

    header .button { margin: 0 0 50px; padding: 10px 15px 10px 15px; position: relative; }
    h3.nav-header { width: 200px; margin: 0 0 10px; padding: 0 0 3px; font-size: 18px; text-shadow: 0 1px 1px #555; color: #fff; font-family: Geogrotesque-Regular, Helvetica, Arial, sans-serif; border-bottom: 1px solid #fff; border-bottom: 1px solid rgba(255,255,255,0.5); }
    nav li {margin: 0 0 7px; font-size: 15px; }
    nav li a:hover,
    nav li.active a  {border-bottom: 1px dotted #fff; border-bottom: 1px dotted rgba(255,255,255,0.3); background: none;}
    nav li.active a  { cursor: default; }

    /* Media Queries
    ***********************/
    @media screen and (max-width: 960px) {
        #container {padding: 35px;}
        header {width: 380px;}
        #main {margin-left: 380px;}
        aside h3  {
            float: none;
            font-size: 20px;
        }
        .toggle  {
            margin-left: 0;
            float: none;
        }
    }
    @media screen and (max-width: 768px) {
        #container {padding: 20px 30px;}
        header {width: 100%; float: none; text-align: center;}
        header img  {width:120px;}
        header h1  { margin: 0 auto 10px; font-size: 32px; }
        header h2  { font-size: 16px; }
        header .button  { margin-bottom: 28px; }

        #main {margin-left: 0;}
        h3.nav-header { margin: 0 auto 10px; font-size: 16px; }
        nav {
            position: relative;
            min-height: 46px;
            margin-bottom: 20px;
            width: 100%;
        }	
        nav ul {
            width: 260px;
            padding: 0;
            position: absolute;
            left: 50%;
            margin-left: -130px;
            top: 0;
            z-index: 9999;
            border: 1px solid #1e5486;
            background: #255a8c;
            -webkit-border-radius: 3px;
            -moz-border-radius: 3px;
            border-radius: 3px;
            -webkit-box-shadow: 0 0 5px 0 rgba(0,0,0,.2), inset 0 0 2px 0 rgba(255,255,255,0.2);
            -moz-box-shadow: 0 0 5px 0 rgba(0,0,0,.2), inset 0 0 2px 0 rgba(255,255,255,0.2);
            box-shadow: 0 0 5px 0 rgba(0,0,0,.2), inset 0 0 2px 0 rgba(255,255,255,0.2);
        }
        nav li {
            display: none; /* hide all <li> items */
            margin: 0;
        }
        nav li a  { border: none!important; text-shadow: 1px 1px 0 rgba(0,0,0,0.3); }
        nav li a:hover  { text-decoration: underline; }
        nav .active {
            display: block; /* show only current <li> item */
        }
        nav a {
            display: block;
            padding: 10px 20px;
            text-align: center;
            font-size: 12px;
        }
        nav .active a {
            background: none;
            color: #fff;
            position: relative;
        }
        nav .active a:after  {
            font-family: flexslider-icon;
            content: '\f005';
            font-size: 13px;
            position: absolute;
            right: 10px;
            top: 10px;
        }

        /* on nav hover */
        nav ul:hover {
            background: #32679a;
        }
        nav ul:hover li {
            display: block;
            margin: 0 0 5px;
        }
        nav ul:hover .active {
            border: 1px solid #1e5486!important;
            background: #255a8c;
            -webkit-border-radius: 1px;
            -moz-border-radius: 1px;
            border-radius: 1px;
        }
        nav ul:hover .active a:after  {
            display: none;
        }

    }
</style>
<!-- Demo CSS -->
<!--<link rel="stylesheet" href="/work/models/demo/css/demo.css" type="text/css" media="screen" />-->
<link rel="stylesheet" href="http://flexslider.woothemes.com/css/flexslider.css" type="text/css" media="screen" />

<%
    List<String> imagenes = (List<String>) request.getAttribute("images");
    List<String> thumbnails = (List<String>) request.getAttribute("thumbnails");
%>
<div id="slider" class="flexslider">
    <ul class="slides">
        <%                        for (String image : imagenes)
            {


        %>
        <li>
            <img src="<%=image%>" />
        </li>
        <%
            }
        %>
    </ul>
</div>
<div id="carousel" class="flexslider">
    <ul class="slides">
        <%
            for (String image : thumbnails)
            {
                
                int height = Integer.parseInt(paramRequest.getResourceBase().getAttribute("height", "180"));
                int width = Integer.parseInt(paramRequest.getResourceBase().getAttribute("width", "180"));
        %>
        <li>
            <img src="<%=image%>" width="<%=width%>" height="<%=height%>"/>
        </li>
        <%
            }
        %>


        <!-- items mirrored twice, total of 12 -->
    </ul

</div>





<!-- jQuery -->
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
<script>window.jQuery || document.write('<script src="/work/models/demo/js/libs/jquery-1.7.min.js">\x3C/script>')</script>

<!-- FlexSlider -->
<script defer src="/work/models/demo/js/jquery.flexslider.js"></script>

<script type="text/javascript">
    $(window).load(function() {
        // The slider being synced must be initialized first
        $('#carousel').flexslider({
            animation: "slide",
            controlNav: false,
            animationLoop: false,
            slideshow: false,
            itemWidth: 210,
            itemMargin: 5,
            asNavFor: '#slider'
        });

        $('#slider').flexslider({
            animation: "slide",
            controlNav: false,
            animationLoop: false,
            slideshow: false,
            sync: "#carousel"
        });
    });

</script>


<!-- Syntax Highlighter -->
<script type="text/javascript" src="/work/models/demo/js/shCore.js"></script>
<script type="text/javascript" src="/work/models/demo/js/shBrushXml.js"></script>
<script type="text/javascript" src="/work/models/demo/js/shBrushJScript.js"></script>

<!-- Optional FlexSlider Additions -->
<script src="/work/models/demo/js/jquery.easing.js"></script>
<script src="/work/models/demo/js/jquery.mousewheel.js"></script>
<script defer src="/work/models/demo/js/demo.js"></script>


