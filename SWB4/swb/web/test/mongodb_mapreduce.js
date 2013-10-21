/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


var map = function() {
    emit(this.subj, {prop: this.prop, obj: this.obj});
};

var reduce = function(key, values) {
    var ret = {vals: values};
    var add=false;
    values.forEach(function(doc) {
        if (doc.prop == "uri|http://www.semanticwebbuilder.org/swb4/ontology#views" && doc.obj == "lit|http://www.w3.org/2001/XMLSchema#long|_|100")
            add=true;
    });
    if(add)return ret;
    return {};
};

var fin = function(key, values) {
    if(values.vals)
    {
      return values.vals;
    }
    return {};
};

var ret = db.swb_graph_ts2.mapReduce(map, reduce, {out: "ts_results", finalize: fin});

db["ts_results"].find({value:{$ne:{}}});


db["ts_results"].remove({value:{}});
db["ts_results"].find();

//-----------------------------------------------------------------------------

var map = function() {
    emit(this.subj, {prop: this.prop, obj: this.obj});
};

var reduce = function(key, values) {
    var ret = {vals: values};
    values.forEach(function(doc) {
        if (!(doc.prop == "uri|http://www.semanticwebbuilder.org/swb4/ontology#views" && doc.obj == "lit|http://www.w3.org/2001/XMLSchema#long|_|100"))
            ret = {};
    });
    return ret;
};

var fin = function(key, values) {
    if(values.vals)
    {
      var ret = values.vals;
    }
    return null;
};

var ret = db.swb_graph_ts2.mapReduce(map, reduce, {out: "ts_results", finalize: fin});

db["ts_results"].find();

//-----------------------------------------------------------------------------

var map = function() {
    if (this.prop == "uri|http://www.semanticwebbuilder.org/swb4/ontology#views" && this.obj == "lit|http://www.w3.org/2001/XMLSchema#long|_|100") {
        emit(this.subj, {prop: this.prop, obj: this.obj});
    }
};

var reduce = function(key, values) {
    var ret = {vals: values};
    return ret;
};

var ret = db.swb_graph_ts2.mapReduce(map, reduce, {out: "ts_results"});

db[ret.result].find();



//-----------------------------------------------------------------------------

var map = function() {
    if (this.prop == "uri|http://www.semanticwebbuilder.org/swb4/ontology#views" && this.obj == "lit|http://www.w3.org/2001/XMLSchema#long|_|100") {
        emit(this.subj, {prop: this.prop, obj: this.obj});
    }
};

var reduce = function(key, values) {
    var ret = {trip: db['swb_graph_ts2'].find({subj:key})};
    return ret;
};

var ret = db.swb_graph_ts2.mapReduce(map, reduce, {out: "ts_results"});

db[ret.result].find();