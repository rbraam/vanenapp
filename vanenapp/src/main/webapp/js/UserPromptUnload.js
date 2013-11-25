/**
 * 
 */
Ext.define("UserPromptUnload",{
    promptUnload: false,
    config: {
        message:"Weet u zeker dat u deze pagina wilt verlaten, mogelijk gaan er gegevens verloren"
    },
    constructor: function(config){
        this.initConfig(config);
        Ext.EventManager.addListener(window,'beforeunload',this.onBeforeUnload,this,{normalized: false});
    },
    onBeforeUnload: function(e){
        if (this.promptUnload){
            if (e){
                e.returnValue=this.message;
            }if(window.event){
                window.event.returnValue = this.message;
            }
            return this.message;
        }
    },
    setPromptUnload: function(val){
        this.promptUnload=val;
    }
    
});