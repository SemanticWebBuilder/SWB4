<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html" encoding="ISO-8859-1"/>
    
    <xsl:template match="/poll">
        <div class="swb-encuesta">
            <xsl:apply-templates select="title"/>
            <xsl:apply-templates select="imgTitle"/>
            <xsl:apply-templates select="question"/>
            <xsl:apply-templates select="options"/>
            <xsl:apply-templates select="vote"/>
            <xsl:apply-templates select="results"/>
            <xsl:apply-templates select="links"/>
        </div>
    </xsl:template>

    <xsl:template match="title">
        <h2 class="">
            <xsl:apply-templates/>
        </h2>
    </xsl:template>
    <xsl:template match="imgTitle">
        <img src="{@src}" alt="{@alt}"/>
    </xsl:template>
    <xsl:template match="question">
        <p class="">
            <xsl:apply-templates/>
        </p>
    </xsl:template>

    <xsl:template match="options">
        <ul>
        <xsl:for-each select="option">
            <li><xsl:call-template name="option" /></li>
        </xsl:for-each>
        </ul>
    </xsl:template>
    <xsl:template name="option">
        <label for="{@id}"><xsl:value-of select="option"/></label>
        <input type="radio" name="{@name}" id="{@id}" value="{@value}" />
        <xsl:apply-templates/>
    </xsl:template>

    <xsl:template match="vote">
        <p class="">
            <a href="#" onClick="{@action}">
                <img src="{@path}images/votar.png" alt="vota" />
            </a>
        </p>
    </xsl:template>
    <xsl:template match="results">
        <p class="">
            <a href="#" onclick="{@action}">
                <xsl:apply-templates/>
            </a>
        </p>
    </xsl:template>
    <xsl:template match="links">
        <xsl:for-each select="link">
            <a href="{@url}"><xsl:apply-templates/></a><br></br>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>