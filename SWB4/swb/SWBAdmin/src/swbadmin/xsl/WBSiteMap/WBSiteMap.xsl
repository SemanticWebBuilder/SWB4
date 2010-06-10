<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : WBSiteMap.xsl
    Created on : 7 de junio de 2010, 09:59 PM
    Author     : carlos.ramos
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" encoding="ISO-8859-1"/>

    <xsl:template name="child">
        <li>
            <xsl:if test="@leaf = '0'">
                <a class="icomap">
                    <xsl:attribute name="onClick"><xsl:value-of select="@onclick"/></xsl:attribute>
                    <span>
                        <xsl:value-of select="@key"/>
                    </span>
                </a>
            </xsl:if>
            <xsl:if test="@leaf = '1'">
                <a class="icomap-trans">
                    <xsl:attribute name="onClick"><xsl:value-of select="@onclick"/></xsl:attribute>
                </a>
            </xsl:if>

            <a>
                <xsl:attribute name="onClick"><xsl:value-of select="@url"/></xsl:attribute>
                <xsl:value-of select="@title"/>
            </a>
            <xsl:for-each select="branch">
                <xsl:call-template name="subchild" />
            </xsl:for-each>
        </li>
    </xsl:template>

    <xsl:template name="subchild">
        <ul>
            <xsl:for-each select="node">
                <xsl:call-template name="child" />
            </xsl:for-each>
        </ul>
    </xsl:template>


    <xsl:template match="/sitemap">
        <div class="swb-mapa" >
            <xsl:attribute name="id"><xsl:value-of select="@id"/></xsl:attribute>
            <h1>
                <xsl:value-of select="@title"/>
            </h1>
            <ul>
            <xsl:for-each select="node">
                <xsl:call-template name="child" />
            </xsl:for-each>
            </ul>
        </div>
    </xsl:template>

</xsl:stylesheet>