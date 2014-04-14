<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml" omit-xml-declaration="yes" indent="yes" encoding="ISO-8859-1"/>
    <xsl:template match="/">
        <div class="panel panel-default swbp-panel">
            <div class="panel-heading swbp-panel-title"><!-- begin Panel heading -->
                <div class="panel-title">
                    <h1 class="panel-title">
                        <xsl:text disable-output-escaping="yes">
                            <![CDATA[<span class="fa fa-list-alt fa-fw"></span>]]>
                        </xsl:text>
                        <strong>
                            <xsl:value-of select="root/@title" disable-output-escaping="yes"/>
                        </strong>
                    </h1>     
                </div>
            </div><!-- end Panel heading -->
            <div class="panel-body"><!-- begin Panel body -->
            
                <button type="button" class="btn btn-default" data-toggle="collapse" data-target="#collapsemodel" id="collapseModelButton">Ocultar modelo</button>
                <div class="in" id="collapsemodel" style="height: auto;">
                    <div class="row text-center" id="modelerprocess">
                        <xsl:value-of select="root/model" disable-output-escaping="yes"/><!-- visulize Process Model -->
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-2">
                        <ul id="myTab0" class="nav nav-pills nav-stacked">
                            <xsl:for-each select="root/section">
                                <li>
                                    <a data-toggle="tab" href="#{@url}">
                                        <xsl:value-of select="@title"/>
                                    </a>
                                </li>
                            </xsl:for-each>
                        </ul>
                    </div>
                    
                    <div class="tab-content col-lg-10">
                        <xsl:for-each select="root/section">
                            <div id="{@url}" class="tab-pane">
                                <xsl:if test="@className != 'Activity' and @className != 'FreeText'">
                                    <table class="table table-hover swbp-table">
                                        <thead>
                                            <xsl:for-each select="instance">
                                                <xsl:if test="@count = 1">
                                                    <xsl:for-each select="property">
                                                        <th>
                                                            <xsl:value-of select="@title"/>
                                                        </th>
                                                    </xsl:for-each>
                                                </xsl:if>
                                            </xsl:for-each>
                                        </thead>
                                        <xsl:for-each select="instance">
                                            <tr>
                                                <xsl:for-each select="property">
                                                    <td>
                                                        <xsl:value-of select="." disable-output-escaping="yes"/>
                                                    </td>
                                                </xsl:for-each>
                                            </tr>
                                        </xsl:for-each>
                                    </table>
                                </xsl:if>
                                <xsl:if test="@className = 'Activity' or @className = 'FreeText'">
                                    <ul class="list-group">
                                        <xsl:for-each select="instance">
                                            <li class="list-group-item">
                                                <xsl:value-of select="property" disable-output-escaping="yes"/>
                                            </li>
                                        </xsl:for-each>
                                    </ul>
                                </xsl:if>
                            </div>
                        </xsl:for-each>
                    </div>
                </div>
            </div><!-- end Panel body -->
        </div>
    </xsl:template>
</xsl:stylesheet>