var promptUnload=false;
var promptUnloadMessage="Weet u zeker dat u deze pagina wilt verlaten, mogelijk gaan er gegevens verloren";
Ext.onReady(function(){
    Ext.EventManager.addListener(window,'beforeunload',onBeforeUnload,this,{normalized: false});
});
function onBeforeUnload(e){
    if (promptUnload){
        if (e){
            e.returnValue=promptUnloadMessage;
        }if(window.event){
            window.event.returnValue = promptUnloadMessage;
        }
        return promptUnloadMessage;
    }
}