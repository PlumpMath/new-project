// Compiled by ClojureScript 1.7.228 {}
goog.provide('new_project.core');
goog.require('cljs.core');
new_project.core.m_version = b4w.require("version");
console.log("Using Blend4Web Version",new_project.core.m_version.version());
new_project.core.start = (function new_project$core$start(json){
var m_main = b4w.require("main");
var m_data = b4w.require("data");
var canvas = document.getElementById("container");
var m_config = b4w.require("config");
var m_trans = b4w.require("transform");
var m_scenes = b4w.require("scenes");
var m_time = b4w.require("time");
var m_controls = b4w.require("controls");
new_project.core.rotate_cube = ((function (m_main,m_data,canvas,m_config,m_trans,m_scenes,m_time,m_controls){
return (function new_project$core$start_$_rotate_cube(val){
new_project.core.cube = m_scenes.get_object_by_name("Cube");

return m_trans.set_rotation_euler(new_project.core.cube,(0),(0),(val / (4)));
});})(m_main,m_data,canvas,m_config,m_trans,m_scenes,m_time,m_controls))
;

new_project.core.stageload_cb = ((function (m_main,m_data,canvas,m_config,m_trans,m_scenes,m_time,m_controls){
return (function new_project$core$start_$_stageload_cb(data_id,success){
if(cljs.core.truth_(m_data.is_primary_loaded())){
var timeline_sensor = m_controls.create_timeline_sensor();
var cube_manifold = m_controls.create_sensor_manifold(null,"m_main",m_controls.CT_CONTINUOUS,cljs.core.clj__GT_js.call(null,new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [timeline_sensor], null)),new_project.core.rotate_cube);
return null;
} else {
return null;
}
});})(m_main,m_data,canvas,m_config,m_trans,m_scenes,m_time,m_controls))
;

new_project.core.loaded_cb = ((function (m_main,m_data,canvas,m_config,m_trans,m_scenes,m_time,m_controls){
return (function new_project$core$start_$_loaded_cb(data_id,success){
return "Needed for basic scene loading.\n    Data-id seems to be the thread the data is loaded. Success is a boolean for\n    whether or not the scene loading was successful.";
});})(m_main,m_data,canvas,m_config,m_trans,m_scenes,m_time,m_controls))
;

m_config.set("console_verbose",true);

m_data.load([cljs.core.str(json)].join(''),new_project.core.loaded_cb,new_project.core.stageload_cb,true,false);

return m_main.init(canvas);
});
goog.exportSymbol('new_project.core.start', new_project.core.start);
