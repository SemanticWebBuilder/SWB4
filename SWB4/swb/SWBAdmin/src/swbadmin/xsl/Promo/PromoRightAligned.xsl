<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="xml" omit-xml-declaration="yes" indent="yes" encoding="ISO-8859-1"/>

    <xsl:template match="/promo">
        <div class="swb-promo rightPromo">
            <xsl:apply-templates select="title"/>
            <xsl:apply-templates select="image"/>
            <div class="promoInfo_RightAlign">
                <xsl:apply-templates select="subtitle"/>
                <xsl:apply-templates select="content"/>
            </div>
            <xsl:apply-templates select="more"/>
        </div>
    </xsl:template>

    <xsl:template match="title">
        <h2><xsl:apply-templates/></h2>
    </xsl:template>

    <xsl:template match="image">
        <div class="promoImage">
            <img  alt="{@alt}" width="{@width}" height="{@height}" >
                <xsl:attribute name="src"><xsl:value-of select="@src"/></xsl:attribute>
            </img>
            <xsl:apply-templates select="imageFoot"/>
        </div>
    </xsl:template>

    <xsl:template match="imageFoot">
        <p class="promoFoot">
            <xsl:apply-templates/>
        </p>
    </xsl:template>

    <xsl:template match="subtitle">
        <h3><xsl:apply-templates/></h3>
    </xsl:template>

    <xsl:template match="content">
        <p><xsl:apply-templates/></p>
    </xsl:template>

    <xsl:template match="more">
        <div class="more">
            <p>
                <xsl:if test="@target='true'">
                    <a href="{@url}" title="Ver más" style="text-decoration: none;" target="_blank">
                        <xsl:apply-templates/>
                    </a>
                </xsl:if>
                <xsl:if test="@target='false'">
                    <a href="{@url}" title="Ver más" style="text-decoration: none;">
                        <xsl:apply-templates/>
                    </a>
                </xsl:if>
            </p>
        </div>
    </xsl:template>
</xsl:stylesheet>