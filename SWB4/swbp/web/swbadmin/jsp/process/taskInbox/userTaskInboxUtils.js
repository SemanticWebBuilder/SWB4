var color = d3.scale.category10();

function loadPageUrl(url, paramName, paramValue) {
    var dest = url;
    if (paramName !== null && paramValue !== null && paramValue !== "") {
        dest+="&"+paramName+"="+paramValue;
    }
    window.location = dest;
}

function click(d) {
    if (d.children) {
        d._children = d.children;
        d.children = null;
    } else {
        d.children = d._children;
        d._children = null;
    }
    updatePartChart("#participationGraph");
}

function updateChart(chartContainer, title, data) {
    var width, 
        height,
        radius,
        offset = 20,
        outerradius;

    var pie = d3.layout.pie()
        .sort(null)
        .value(function(d) {
            return d.value; 
        });
        
    var sum = d3.sum(data, function(d){
        return d.value;
    });

    height = width = $(chartContainer).parent().width();
    radius = (height-(height*.2)) / 2;
    outerradius = radius * 0.08;
    
    //replace container content
    $(chartContainer).html("<svg xmlns='http://www.w3.org/2000/svg' width='100%' height='100%'></svg>");
    
    //create arcs function
    var arc = d3.svg.arc()
        .outerRadius(radius - outerradius)
        .innerRadius(0);

    //create outter arcs function
    var arcOver = d3.svg.arc()
        .outerRadius(radius - outerradius)
        .innerRadius(radius);

    //Add graph contents
    var svg = d3.select(chartContainer+" svg")
        .attr("width", width)
        .attr("height", height)
        .append("g")
        .attr("transform", "translate(" + width / 2 + "," + (height / 2 + offset)+")");

    //Outter arcs selector
    var gl = svg.selectAll(".arcOver")
        .data(pie(data))
        .enter().append("g")
        .attr("class", "arcOver")
        .style("visibility","hidden");

    gl.append("path")
        .attr("d", arcOver)
        .style("fill-opacity", "0.6")
        .style("fill", function(d, i) {
            return color(i);
        });

    //Create tooltips
    var tooltips = svg.select("body")
        .data(pie(data))
        .enter().append("div")
        .attr("class","chartToolTip")
        .style("display", "none")
        .style("position", "absolute")
        .style("z-index", "10");

    tooltips.append("p")
        .append("span")
        .html(function(d) {
            var pc = ((d.data.value / sum) *100);
            if (pc % 1 > 0) {
                pc = pc.toFixed(3);
            }
            return "<strong>"+d.data.label+"</strong><br>"+d.data.value+" ("+pc+"%)";
        });

    //Arcs selector
    var g = svg.selectAll(".arc")
        .data(pie(data))
        .enter().append("g")
        .attr("class", "arc")
        .on("mouseover", function(d, i) {
            d3.select(gl[0][i]).style("visibility","visible"); 
            d3.select(tooltips[0][i])
                .style("display","block");
        })
        .on("mouseout", function(d, i) {
            d3.select(gl[0][i]).style("visibility","hidden"); 
            d3.select(tooltips[0][i])
                .style("display","none");
            d3.select(gl[0][i]).style("fill",function(d) {
                return color(d.data.label);
            });
        })
        .on("mousemove", function(d, i) {
            d3.select(tooltips[0][i])
                .style("top", d3.event.pageY-10+"px")
                .style("left", d3.event.pageX+10+"px")
        });

    //Create slices
    g.append("path")
        .attr("d", arc)
        .style("stroke", "white")
        .style("stroke-width", "2")
        .style("fill", function(d, i) {
            return color(i);
        });

    svg
        .append("text")
        .text(title)
        .style("text-anchor","middle")
        .style("fill","black")
        .style("font-size","10pt")
        .style("font-weight","bold")
        .attr("x","0")
        .attr("y",function(d) {
            return - width/2;
        });

    /*g.append("text")
        .attr("transform", function(d) {
            return "translate(" + arc.centroid(d) + ")";
        })
        .attr("dy", ".35em")
        .style("text-anchor", "middle")
        .style("fill", "white")
        .style("font-size", "12pt")
        .text(function(d) {
            return d.data.label;
        });*/
}

function showWaitDialog(title, loadmsg) {
    var msg = "Loading, please wait...";
    if (loadmsg && loadmsg !== "") msg = loadmsg;

    $("#modalDialog").html('<div class="modal-dialog"><div class="modal-content"><div class="modal-header">\n\
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4>' + title
        + '</h4></div><div class="modal-body"><div id="modal-content" class="text-center"><span class="fa fa-cog fa-lg fa-spin">\n\
        </span> '+ msg + '</div></div></div></div>');

    $("#modalDialog").modal('show');
}
    
function showModal(url, title, loadmsg, errormsg) {
    var emsg = "Sorry, there was an error processing the request...";
    if (errormsg && errormsg !== "") emsg = errormsg;

    showWaitDialog(title, loadmsg);

    var jqxhr = $.get(url, function(data) {
        $("#modalDialog").html(data);
    })
    .fail(function() {
        $("#modalDialog").find("#modal-content").html(" "+emsg);
    });
}