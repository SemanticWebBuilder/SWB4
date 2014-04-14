<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml" omit-xml-declaration="yes" indent="yes" encoding="ISO-8859-1"/>
    <!--script src="/swbadmin/jsp/process/modeler/toolkit.js" ></script>
    <script src="/swbadmin/jsp/process/modeler/modeler.js" ></script-->
    <xsl:template match="/">
        
        <xsl:value-of select="elements/process/imports" disable-output-escaping="yes"/>
        
        <script type='text/javascript'>
            $(document).ready(function() {
            if ($("[data-toggle=tooltip]").length) {
            $("[data-toggle=tooltip]").tooltip();
            }
            $('body').off('.data-api');
            });
        </script>
        
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
                <div class="col-lg-11 col-md-11 col-sm-11 col-xs-11">
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
                    </ul>
                </div>
                <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1"> 
                    
                    <xsl:value-of select="elements/process/btnDownload" disable-output-escaping="yes"/>
                    
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
                                    <xsl:when test="@type='lanes'">
                                        <xsl:call-template name="display_menu" />
                                    </xsl:when>
                                    <xsl:when test="@type='activities'">
                                        <xsl:call-template name="display_menu" />
                                    </xsl:when>
                                    <xsl:when test="@type='gateways'">
                                        <xsl:call-template name="display_menu" />
                                    </xsl:when>
                                    <xsl:when test="@type='events'">
                                        <xsl:call-template name="display_menu" />
                                    </xsl:when>
                                </xsl:choose>
                            </xsl:for-each>
                        </ul>
                    </div>
                </div>
                <!-- End menu -->
                
                <!-- Begin documentation -->
                <div class="col-lg-10 col-md-10 col-sm-8" role="main">
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
                                        <a class="pull-right" href="#onhome" data-placement="bottom" data-toggle="tooltip" data-original-title="Up">
                                            <span class="fa fa-level-up "></span>
                                        </a>
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
                        <a class="pull-right" href="#onhome" data-placement="bottom" data-toggle="tooltip" data-original-title="Up">
                            <span class="fa fa-level-up "></span>
                        </a>
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