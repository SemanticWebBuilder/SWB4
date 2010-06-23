<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html" encoding="ISO-8859-1"/>
    
    <xsl:template match="/poll">
        <div class="swb-encuesta">
            <xsl:apply-templates select="title"/>
            <xsl:apply-templates select="imgTitle"/>
            <form name="mypoll" id="mypoll" action="{@url}" method="post">
                <xsl:apply-templates select="cuestion"/>
                <xsl:apply-templates select="options"/>
                <xsl:apply-templates select="vote"/>
                <xsl:apply-templates select="results"/>
                <xsl:apply-templates select="links"/>
            </form>
        </div>
    </xsl:template>

    <xsl:template match="title">
        <p class=""><xsl:apply-templates/></p>
    </xsl:template>
    <xsl:template match="imgTitle">
        <img src="{@src}" alt="{@alt}"/>
    </xsl:template>
    <xsl:template match="cuestion">
        <p class="">
            <xsl:value-of select="cuestion"/>
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
                <img src="{@path}images/vota.jpg" alt="vota" />
                <xsl:apply-templates/>
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
            <a href="{@url}"><xsl:apply-templates/></a><br />
        </xsl:for-each>
    </xsl:template>

    
</xsl:stylesheet>
