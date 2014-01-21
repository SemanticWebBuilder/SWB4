<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml" omit-xml-declaration="yes" indent="yes" encoding="ISO-8859-1"/>
    <!--script src="/swbadmin/jsp/process/modeler/toolkit.js" ></script>
    <script src="/swbadmin/jsp/process/modeler/modeler.js" ></script-->
    <xsl:template match="/">
        <xsl:text disable-output-escaping="yes">
<![CDATA[ 
        <link href="/swbadmin/css/bootstrap/bootstrap.css" rel="stylesheet">
        <link href="/swbadmin/css/fontawesome/font-awesome.css" rel="stylesheet">
        <link href="/swbadmin/jsp/process/taskInbox/css/swbp.css" rel="stylesheet">
        <script type="text/javascript" src="/swbadmin/js/jquery/jquery.js"></script>
        <script type="text/javascript" src="/swbadmin/jsp/process/modeler/toolkit.js"></script>
        <script type="text/javascript" src="/swbadmin/jsp/process/modeler/modeler.js"></script>
        <script src="/swbadmin/js/bootstrap/bootstrap.js"></script>
        <link href="/swbadmin/jsp/process/documentation/css/style.css" rel="stylesheet">
        <link href="/swbadmin/jsp/process/modeler/images/modelerFrame.css" rel="stylesheet">
        
        <script type='text/javascript'> //Activate tooltips
            $(document).ready(function() {
                if ($("[data-toggle=tooltip]").length) {
                    $("[data-toggle=tooltip]").tooltip();
                }
                $('body').off('.data-api');
            });
        </script>
]]>
        </xsl:text>
        <div class="swbp-content-wrapper">
            <xsl:text disable-output-escaping="yes">
                <![CDATA[ 
                    <div class="row swbp-header hidden-xs">
                        <a class="" href="http://www.semanticwebbuilder.org.mx/SWBProcess" target="_blank">
                            <div class="swbp-brand"></div>
                        </a>
                    </div>
                ]]>
            </xsl:text>
            <!-- Begin header -->
            <nav class="swbp-toolbar" role="navigation">
                <div class="text-center">
                    <ul class="swbp-nav">
                        <li>
                            <h2>
                                <xsl:text disable-output-escaping="yes">
                                <![CDATA[ <i class="fa fa-gears">
                                </i>]]>
                                </xsl:text>
                                <span> 
                                    <xsl:value-of select="elements/@title"/>
                                </span>
                            </h2>
                        </li>
                    </ul>
                </div>
            </nav>
            <!-- End header -->
            <div class="row">
                <div class="col-lg-11 col-md-10 col-sm-10">
                    <ul class="breadcrumb hidden-print">
                        <li>
                            <a href="{elements/process/@url}">
                                <xsl:value-of select="elements/process/@process"/>
                            </a>
                        </li>
                        <xsl:for-each select="elements/father">
                            <xsl:choose>
                                <xsl:when test ="@type='urls'">
                                    <xsl:call-template name="display_menu_map" />
                                </xsl:when>
                            </xsl:choose>
                        </xsl:for-each>
                        <!--xsl:value-of select="elements/process/@path"/-->
                    </ul>
                </div>
                <div class="col-lg-1 col-md-2 col-sm-"> 
                    <a href="{elements/process/@urlExport}">
                                <xsl:value-of select="elements/process/@process"/>
                            </a>
                    <xsl:text disable-output-escaping="yes">
                        <![CDATA[ 
                            <a href="#" class="btn btn-default fa fa-download pull-right" data-placement="bottom" data-toggle="tooltip" data-original-title="Download" style="text-decoration: none;"></a>
                        ]]>
                    </xsl:text>
                </div>
            </div>
            <!-- Begin content -->
            <div class="row">
                
                <!-- Begin menu-->
                <div class="col-lg-2 col-md-2 col-sm-4 hidden-xs">
                    <div class="swbp-left-menu swbp-left-menu-doc" style="height: 72%;">
                        <ul class="nav nav-pills nav-stacked">
                            <xsl:for-each select="elements/father">
                                <xsl:choose>
                                    <xsl:when test ="@type='lanes'">
                                        <xsl:call-template name="display_menu" />
                                    </xsl:when>
                                    <xsl:when test ="@type='activities'">
                                        <xsl:call-template name="display_menu" />
                                    </xsl:when>
                                    <xsl:when test ="@type='gateways'">
                                        <xsl:call-template name="display_menu" />
                                    </xsl:when>
                                    <xsl:when test ="@type='events'">
                                        <xsl:call-template name="display_menu" />
                                    </xsl:when>
                                </xsl:choose>
                            </xsl:for-each>
                        </ul>
                    </div>
                </div>
                <!-- End menu -->
                
                <!-- Begin documentation -->
                <div class="col-lg-10 col-md-10 col-sm-8 col-xs" role="main">
                    <div class="contenido" style="height: 72%;">
                        <div class="panel panel-default" id="onhome">
                            <div class="panel-body">
                                <xsl:value-of select="elements/process/model" disable-output-escaping="yes"/>
                                <xsl:value-of select="elements/process/script" disable-output-escaping="yes"/>
                            </div>
                        </div>
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <div class="panel-title">
                                    <strong>
                                        Documentación de: <xsl:value-of select="elements/@title"/>
                                        <xsl:text disable-output-escaping="yes">
                                            <![CDATA[ 
                                                <a href="#onhome" class="fa fa-level-up pull-right" data-placement="bottom" data-toggle="tooltip" data-original-title="Up" style="text-decoration: none;"></a>
                                            ]]>
                                        </xsl:text>
                                    </strong>
                                </div>
                            </div>
                            <div class="panel-body">
                                <xsl:value-of select="elements/process/documentation" disable-output-escaping="yes"/>
                            </div>
                        </div>
                        
                        <xsl:for-each select="elements/father">
                            <xsl:choose>
                                <xsl:when test ="@type='lanes'">
                                    <xsl:call-template name="display_documentation" />
                                </xsl:when>
                                <xsl:when test ="@type='activities'">
                                    <xsl:call-template name="display_documentation" />
                                </xsl:when>
                                <xsl:when test ="@type='gateways'">
                                    <xsl:call-template name="display_documentation" />
                                </xsl:when>
                                <xsl:when test ="@type='events'">
                                    <xsl:call-template name="display_documentation" />
                                </xsl:when>
                            </xsl:choose>
                        </xsl:for-each>
                    </div>
                </div>
                <!-- End documentation -->
                
            </div>
            <!-- End content -->
            
        </div>
    </xsl:template>
    
    <!--Template for display menu -->
    <xsl:template name="display_menu">
        <li  class="active">
            <a href="{@url}">
                <xsl:value-of select="@title"/>
                <span class="badge pull-right">
                    <xsl:value-of select="@size"/>
                </span>
            </a>
        </li>
        <xsl:for-each select="son">
            <li>
                <a href="{@url}">
                    <xsl:value-of select="@title"/>
                </a>
            </li>
        </xsl:for-each>
    </xsl:template>
    <!--Template for display documentation -->
    <xsl:template name="display_documentation">
        <div class="panel panel-default" id="{@id}">
            <div class="panel-heading">
                <div class="panel-title">
                    <strong>
                        <xsl:value-of select="@title"/>
                        <xsl:text disable-output-escaping="yes">
                                <![CDATA[ 
                                    <a href="#onhome" class="fa fa-level-up pull-right" data-placement="bottom" data-toggle="tooltip" data-original-title="Up" style="text-decoration: none;"></a>
                                ]]>
                        </xsl:text>
                    </strong>
                </div>
            </div>
            <div class="panel-body">
                <xsl:for-each select="son">
                    <h4 id="{@id}">
                        <xsl:value-of select="@title"/>
                    </h4>
                    <xsl:value-of select="documentation" disable-output-escaping="yes"/>
                </xsl:for-each>
            </div>
        </div>
    </xsl:template>
    
    <!--Template for menu map -->
    <xsl:template name="display_menu_map">
        <xsl:for-each select="son">
            <li>
                <a href="{@url}">
                    <xsl:value-of select="@title"/>
                </a>
            </li>
        </xsl:for-each>
    </xsl:template>
    
</xsl:stylesheet>