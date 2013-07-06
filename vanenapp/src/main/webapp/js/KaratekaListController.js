/**
 * Creates a list of karateka's
 * @param {string} url the url that is used to get the karateka's
 * @param {string} renderTo the id of the element where this list is rendered.
 * @param {function} handler when clicked on element
 */
Ext.define("KaratekaListController",{
    list: null,
    filter: null,
    listContainer: null,
    config: {
        url: null,
        renderTo: null,
        clickHandler: null
    },
    constructor: function (conf){
        this.initConfig(conf);
        //this.renderTo= Ext.get(this.renderTo);
        var me=this;
        Ext.create('Ext.form.field.Text',{
            renderTo: this.renderTo,
            fieldLabel: 'Zoek',
            listeners: {
                change:{
                    fn: function(){
                        me.setFilter(this.value);
                    }
                }
            }
        });
        
        this.listContainer = new Ext.Element(document.createElement("div"));
        this.listContainer.addCls('karatekaList');
        Ext.get(this.renderTo).appendChild(this.listContainer);
    },
    /**
     * Overwrite the list and update it.
     */
    setList: function (list){
        this.list=list;
        this.update();
    },
    /**
     * Refreshes the list by getting the karateka's from the url
     */
    refresh: function(){
        var me=this;
        Ext.Ajax.request({
            url: listKaratekaUrl,
            success: function(response){
                var karatekas = Ext.JSON.decode(response.responseText);
                me.setList(karatekas);
            }
        });
    },
    /**
     * Update the dom list elements with the already recieved karateka's
     */
    update: function(){
        this.listContainer.update("");
        for (var i=0; i < this.list.length; i++){
            var k = this.list[i];
            if (this.checkFilter(k)){
                var el=this.createElement(k);
                this.listContainer.appendChild(el);
            }
        }
    },
            
    setFilter: function(filter){
        this.filter=filter;
        this.update();
    },
    /**
     * Check if the given karateka passes the filter that is set. It's checking the surname and name caseinsensitive
     * @param k the karateka
     * @return {boolean} true/false
     */
    checkFilter: function (k){
        if (this.filter===null){
            return true;
        }
        if (k.name.toLowerCase().indexOf(this.filter.toLowerCase())!==-1){
            return true;
        }
        if (k.surname.toLowerCase().indexOf(this.filter.toLowerCase())!==-1){
            return true;
        }
        return false;
    },
            
    createElement: function(k){
        var element = new Ext.Element(document.createElement('div'));
        //add classesk
        element.addCls("karateka-item");
        element.addClsOnOver("karateka-item-over");
        element.addClsOnClick("karateka-item-click");
        //make the text
        var textDiv = new Ext.Element(document.createElement('div'));
        var text =k.surname+", "+k.name;
        if (k.insert){
            text+=" "+k.insert;
        }
        textDiv.update(text);
        element.appendChild(textDiv);
        //add listener
        var me = this;
        element.addListener("click",function(){
            if (me.clickHandler){
                me.clickHandler.call(this,k.id);
            }
        });
        return element;
    }
    
});
