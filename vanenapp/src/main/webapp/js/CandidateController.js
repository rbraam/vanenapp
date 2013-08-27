/**
 * 
 */
Ext.define("CandidateController",{
    extend: "Ext.util.Observable",
    list: null,
    listContainer: null,
    config: {
        url: null,
        checkedCandidates: null,
        poule: null,
        renderTo: null
    },
    constructor: function (conf){
        CandidateController.superclass.constructor.call(this,conf);
        this.initConfig(conf);
        if(this.checkedCandidates==null){
            this.checkedCandidates=[];
        }
        this.listContainer = new Ext.Element(document.createElement("div"));
        this.listContainer.addCls('candidateList');
        Ext.get(this.renderTo).appendChild(this.listContainer);
    },
    
    refresh: function(){
        var startAge = document.getElementById("startAge").value;
        var endAge= document.getElementById("endAge").value;
        var startKyu= document.getElementById("startKyu").value; 
        var endKyu= document.getElementById("endKyu").value;

        var poule = null;
        if (document.getElementById("pouleId")){
            poule=document.getElementById("pouleId").value;
        }
        var me = this;
        Ext.Ajax.request({
            url: this.url,
            params: {
                startAge: startAge,
                endAge: endAge,
                startKyu: startKyu,
                endKyu: endKyu,
                poule: me.poule
            },
            success: function(response){
                var candidates = Ext.JSON.decode(response.responseText);
                me.setList(candidates);
                me.fireEvent("loaded",me,{});
            }
        });
    },
    
    setList: function(candidates){
        this.listContainer.update("");
        for (var i=0; i < candidates.length; i++){
            var candidate = candidates[i];
            var el = this.createElement(candidate);
            this.listContainer.appendChild(el);
        }
    },
            
    createElement: function(candidate){
        var div = new Ext.Element(document.createElement('div'));
        div.addCls("candidate-class");
        
        var candidateString =candidate.karateka.name + " "+candidate.karateka.belt+ " " + candidate.karateka.birthdate;
        
        var checked = this.isCheckedCandidate(candidate);
        
        /*var checkBox = Ext.create('Ext.form.field.Checkbox', {
            boxLabel: candidateString,
            name: 'participants',
            checked: checked,
            inputValue:  true,
            labelWidth: 275,
            width: 500,
            renderTo: div
        });*/
        var inputString = "<input type='checkbox' name='participants' value='"+candidate.id+"'";
        if (checked){
            inputString+="checked";
        }
        inputString+= ">"+candidateString+"<br/>";
        
        div.update(inputString);
        return div;
1    },
    
    isCheckedCandidate: function(candidate){
        for (var i=0; i < this.checkedCandidates.length; i++){
            if (this.checkedCandidates[i] === candidate.id){
                return true;
            }
        }
        return false;
    }
       
});