/**
 * @author Dimitry Kudrayvtsev
 * @version 2.1
 * Licensed under the Apache License, Version 2.0
 */

d3.gantt = function(taskNames) {
    var selection = "#ganttChart";
    var container = d3.select(selection);
    var that = container;
    var longestTaskName = 0;
    var offsetX = 0;
    var FIT_TIME_DOMAIN_MODE = "fit";
    var FIXED_TIME_DOMAIN_MODE = "fixed";
    var margin = {
        top : 10,
        right : 0,
        bottom : 20,
        left : 20
    };
    var timeDomainStart = d3.time.day.offset(new Date(), -3);
    var timeDomainEnd = d3.time.hour.offset(new Date(), +3);
    var timeDomainMode = FIT_TIME_DOMAIN_MODE;// fixed or fit
    var taskTypes = [];
    if (arguments.length) {
        taskTypes = taskNames;
        for (var i = 0; i < taskTypes.length; i++) {
            if (taskTypes[i].length > longestTaskName) {
                longestTaskName = taskTypes[i].length;
            }
        }
        offsetX = (longestTaskName > 0 ? longestTaskName * 6 : 100) + margin.left
    }
    var taskStatus = [];
    var availableWidth = ((container.style && container.style('width') && (parseInt(container.style('width')) > 460) ? parseInt(container.style('width')) : false) || 460) - margin.right - offsetX; //
    var availableHeight = ((container.style && container.style('height') && (parseInt(container.style('height')) > 350) ? parseInt(container.style('height')) : false) || 350) - margin.top - margin.bottom; //
    var height = availableHeight - 5; //- margin.top - margin.bottom
    var width = availableWidth - 5;  //- margin.right - margin.left
    var tickFormat = "%H:%M"; //  %d/%m/%Y
    var keyFunction = function(d) {
        return d.startDate + d.taskName + d.endDate;
    };
    var rectTransform = function(d) {
        return "translate(" + (x(d.startDate) + gantt.offsetX()) + "," + (y(d.taskName) - margin.top - margin.bottom) + ")";
    };
    var x = d3.time.scale().domain([ timeDomainStart, timeDomainEnd ]).range([ 0, width ]).clamp(true);
    var y = d3.scale.ordinal().domain(taskTypes).rangeRoundBands([ 0, height ], .1);  // - margin.top - margin.bottom
    var xAxis = d3.svg.axis().scale(x).orient("bottom").tickFormat(d3.time.format(tickFormat)).tickSubdivide(true)
	    .tickSize(0).tickPadding(8);  //.tickSize(-availableHeight, 0)
    var yAxis = d3.svg.axis().scale(y).orient("left").tickSize(0);
    var initTimeDomain = function(tasks) {
        if (timeDomainMode === FIT_TIME_DOMAIN_MODE) {
            if (tasks === undefined || tasks.length < 1) {
                timeDomainStart = d3.time.day.offset(new Date(), -3);
                timeDomainEnd = d3.time.hour.offset(new Date(), +3);
            return;
            }
            tasks.sort(function(a, b) {
                return a.endDate - b.endDate;
            });
            timeDomainEnd = tasks[tasks.length - 1].endDate;
            tasks.sort(function(a, b) {
                return a.startDate - b.startDate;
            });
            timeDomainStart = tasks[0].startDate;
        }
        /*
        for (var i = 0; i < tasks.length; i++) {
            if (tasks[i].taskName.length > longestTaskName) {
                longestTaskName = tasks[i].taskName.length;
            }
        }*/
    };
    var initAxis = function() {
        x = d3.time.scale().domain([ timeDomainStart, timeDomainEnd ]).range([ 0, width ]).clamp(true);
        y = d3.scale.ordinal().domain(taskTypes).rangeRoundBands([ 0, height ], .1); //height - margin.top - margin.bottom
        xAxis = d3.svg.axis().scale(x).orient("bottom").tickFormat(d3.time.format(tickFormat)).tickSubdivide(true)
            .tickSize(0).tickPadding(8);  //.tickSize(-availableHeight, 0)

        yAxis = d3.svg.axis().scale(y).orient("left").tickSize(0);
    };
    
    //Agregadas para manejo de tooltips
    var tooltips = true;
    var tooltip = function(key, x, y, e, graph) {
        return '<h3>' + key + '</h3>' +
               '<p>Del ' +  new Date(x).toLocaleDateString() + ' al ' + new Date(y).toLocaleDateString() + '</p>';
      }
    var showTooltip = function(e, offsetElement) {
        var left = e.pos[0] + ( offsetElement.offsetLeft || 0 ),
            top = e.pos[1] + ( offsetElement.offsetTop || 0),
            x = xAxis.tickFormat()(gantt.x()(e.point, e.pointIndex)),
            y = yAxis.tickFormat()(gantt.y()(e.point, e.pointIndex)),
            content = tooltip(e.series.key, x, y, e, gantt);
        nv.tooltip.show([left, top], content, e.value < 0 ? 'n' : 's', null, offsetElement);
    };
    
    function gantt(tasks) {
	
        initTimeDomain(tasks);
        initAxis();
        gantt.offsetX((gantt.longestLabel() > 0 ? gantt.longestLabel() * 6 : margin.left) + margin.left); //calcula el ancho de las etiquetas
        
        var svg = d3.select(selection)
            .append("svg")
            .attr("class", "chart")
            .attr("width", width + margin.left + margin.right)
            .attr("height", height + margin.top + margin.bottom)
            .append("g")
                .attr("class", "gantt-chart")
            .attr("width", width)    //+ margin.left + margin.right
            .attr("height", height)  // + margin.top + margin.bottom
            .attr("transform", "translate(" + margin.left + ", " + (margin.top + margin.bottom) + ")");
	
        svg.selectAll(".chart")
            .data(tasks, keyFunction).enter()
            .append("rect")
            .attr("rx", 5)
            .attr("ry", 5)
            .attr("style", function(d){ 
                if (!d.status) { return "fill: #33b5e5;";}
                    return ("fill: " + d.status + ";");
                })
            .attr("y", 0)
            .attr("transform", rectTransform)
            .attr("height", function(d) { return y.rangeBand(); })
            .attr("width", function(d) {
                return (x(d.endDate) - x(d.startDate)); // - gantt.offsetX()
                })
            .attr("onmouseover", "gantt.tooltipShow(evt, this)")
            .attr("onmouseout", "gantt.tooltipHide()");
	/* ----- Para el despliegue de tooltips ----- */
    
        svg.append("g")
            .attr("class", "x axis")
            .attr("transform", "translate(" + gantt.offsetX() + ", " + (height - margin.top - margin.bottom) + ")")
            .transition()
            .call(xAxis);
         
        svg.append("g").attr("class", "y axis")
            .attr("transform", "translate(" + gantt.offsetX() + ", " + (0 - margin.top - margin.bottom) + ")")
            .transition().call(yAxis);
         outterSvg = svg;
        return gantt;

    };
    
    gantt.redraw = function(tasks) {

        initTimeDomain(tasks);
        initAxis();
        
        var svg = d3.select("svg");

        var ganttChartGroup = svg.select(".gantt-chart");
        var rect = ganttChartGroup.selectAll("rect").data(tasks, keyFunction);
            
        rect.enter()
            .insert("rect",":first-child")
            .attr("rx", 5)
            .attr("ry", 5)
            .attr("style", function(d){ 
                if (!d.status) { return "fill: #33b5e5;";}
                    return ("fill: " + d.status + ";");
                })
            .transition()
            .attr("y", 0)
            .attr("transform", rectTransform)
            .attr("height", function(d) { return y.rangeBand(); })
            .attr("width", function(d) { 
                return (x(d.endDate) - x(d.startDate)); // - gantt.offsetX()
             });
        rect.transition()
            .attr("transform", rectTransform)
            .attr("height", function(d) { return y.rangeBand(); })
            .attr("width", function(d) { 
                return (x(d.endDate) - x(d.startDate) - gantt.offsetX()); 
            });
        rect.exit().remove();

        svg.select(".x").transition().call(xAxis);
        svg.select(".y").transition().call(yAxis);
        
        return gantt;
    };

    gantt.margin = function(value) {
        if (!arguments.length)
            return margin;
        margin = value;
        return gantt;
    };

    gantt.timeDomain = function(value) {
        if (!arguments.length)
            return [ timeDomainStart, timeDomainEnd ];
        timeDomainStart = +value[0], timeDomainEnd = +value[1];
        return gantt;
    };

    /**
     * @param {string} value The value can be "fit" - the domain fits the data or
     *        "fixed" - fixed domain.
     */
    gantt.timeDomainMode = function(value) {
        if (!arguments.length)
            return timeDomainMode;
        timeDomainMode = value;
        return gantt;
    };

    gantt.taskTypes = function(value) {
        if (!arguments.length)
            return taskTypes;
        taskTypes = value;
        return gantt;
    };
    
    gantt.taskStatus = function(value) {
        if (!arguments.length)
            return taskStatus;
        taskStatus = value;
        return gantt;
    };

    gantt.width = function(value) {
        if (!arguments.length)
            return width;
        width = +value;
        return gantt;
    };

    gantt.height = function(value) {
        if (!arguments.length)
            return height;
        height = +value;
        return gantt;
    };

    gantt.tickFormat = function(value) {
        if (!arguments.length)
            return tickFormat;
        tickFormat = value;
        return gantt;
    };
    
    gantt.longestLabel = function(value) {
        if (!arguments.length)
            return longestTaskName;
        longestTaskName = value;
        return gantt;
    };
    
    gantt.offsetX = function(value) {
        if (!arguments.length)
            return offsetX;
        offsetX = value;
        return gantt;
    };
    
    gantt.tooltip = function(_) {
      if (!arguments.length) return tooltip;
      tooltip = _;
      return gantt;
    };

    gantt.tooltips = function(_) {
      if (!arguments.length) return tooltips;
      tooltips = _;
      return gantt;
    };

    gantt.tooltipContent = function(_) {
      if (!arguments.length) return tooltip;
      tooltip = _;
      return gantt;
    };
    gantt.tooltipShow = function(evt, element) {
        var left = evt.clientX + ( element.offsetLeft || 0 ),
            top = evt.clientY + ( element.offsetTop || 0),
            content = tooltip(element.__data__.taskName, element.__data__.startDate, element.__data__.endDate, evt, gantt);
        nv.tooltip.show([left, top], content, 'n', null, element.parentNode);
      return gantt;
    };
    gantt.tooltipHide = function() {
        if (tooltips) nv.tooltip.cleanup();
        return gantt;
    };
    
    gantt.x = function(value) {
        if (!arguments.length)
            return x;
        x = value;
        return gantt;
    };
    gantt.y = function(value) {
        if (!arguments.length)
            return y;
        y = value;
        return gantt;
    };
    
    return gantt;
};
