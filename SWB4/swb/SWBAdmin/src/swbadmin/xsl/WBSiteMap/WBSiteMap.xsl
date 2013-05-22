<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="xml" omit-xml-declaration="yes" indent="yes" encoding="ISO-8859-1"/>

    <xsl:template name="child">
        <li>
            <xsl:if test="@leaf = '0'">
                <a class="icomap">
                    <xsl:attribute name="href"><xsl:value-of select="@href"/></xsl:attribute>
                    <span>
                        <xsl:value-of select="@key"/>
                    </span>
                </a>
            </xsl:if>
            <xsl:if test="@leaf = '1'">
                <a class="icomap-trans">
                    <xsl:attribute name="href"><xsl:value-of select="href"/></xsl:attribute>
                </a>
            </xsl:if>
            <a>
                <xsl:attribute name="href"><xsl:value-of select="@url"/></xsl:attribute>
                <xsl:value-of select="@title"/>
            </a>
            <xsl:for-each select="branch">
                <xsl:call-template name="subchild" />
            </xsl:for-each>
        </li>
    </xsl:template>

    <xsl:template name="subchild">
        <xsl:for-each select="node">
            <xsl:if test="position()=1">
                <xsl:text  disable-output-escaping="yes">
                    &lt;ul>
                </xsl:text>
            </xsl:if>
            <xsl:call-template name="child" />
            <xsl:if test="position()=last()">
                <xsl:text  disable-output-escaping="yes">
                    &lt;/ul>
                </xsl:text>
            </xsl:if>
        </xsl:for-each>
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