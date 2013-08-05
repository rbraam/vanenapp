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
        data: null,
        renderTo: null,
        clickHandler: null,
        crossClickHandler: null
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
        if (this.url){
            Ext.Ajax.request({
                url: me.url,
                success: function(response){
                    var karatekas = Ext.JSON.decode(response.responseText);
                    me.setList(karatekas);
                }
            });
        }else{
            this.setList(this.data);
        }
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
        var me = this;
        var element = new Ext.Element(document.createElement('div'));
        //add classesk
        element.addCls("karateka-item");
        element.addClsOnOver("karateka-item-over");
        element.addClsOnClick("karateka-item-click");
        //make the text
        var textDiv = new Ext.Element(document.createElement('div'));
        textDiv.addCls("karateka-item-text");
        var text =k.surname+", "+k.name;
        if (k.insert){
            text+=" "+k.insert;
        }
        textDiv.update(text);
        element.appendChild(textDiv);
        //add listener
        if (this.clickHandler!=null){
            textDiv.addListener("click",function(){
                if (me.clickHandler){
                    me.clickHandler.call(this,k.id);
                }
            });
        }
        if (this.crossClickHandler!=null){
            var crossDiv = new Ext.Element(document.createElement('div'));
            crossDiv.addCls("karateka-item-cross");
            element.appendChild(crossDiv);
            //add listener
            crossDiv.addListener("click",function(){
                if (me.crossClickHandler){
                    me.crossClickHandler.call(this,k.id);
                    return false;
                }
            });
        }
        return element;
    },
    /**
     * Get the index of the karateka with the given id. Or null if not found.
     */
    getKaratekaIndex: function(id){
        for (var i=0; i < this.list.length; i++){
            var k = this.list[i];
            if (k.id == id){
                return i;
            }
        }
        return null;
    },
    /**
     * Get a karateka by id. Return null if not found.
     */
    getKarateka : function(id){
        var index= this.getKaratekaIndex(id);
        if (index){
            return this.list[index];
        }
        return null;
    },
    /**
     * Remove the karateka with the given id. Return the removed karateka or null if not removed.
     */
    removeKarateka: function(id){
        var index= this.getKaratekaIndex(id);
        var returnVal = null;
        if (index!=null){
            returnVal=this.list.splice(index,1);
            if(returnVal.length ==1){
                returnVal=returnVal[0]
            }
        }
        return returnVal;
    },
    /**
     * Add a karateka to the list
     */
    addKarateka: function (k){
        var index=this.list.length;
        for (var i=0; i < this.list.length; i++){
            var compare = this.compareKarateka(k, this.list[i]);
            if (compare ==-1){
                this.list.splice(i,0,k);
                return;
            }
        }
        this.list.push(k);
    },
    /**
     * compare 2 karateka's
     * -1 if a is smaller then b
     * 0 if a equals b
     * 1 if a is bigger then b 
     */
    compareKarateka: function(karatekaA,karatekaB){
        if (karatekaA.surname.toLowerCase() < karatekaB.surname.toLowerCase()){
            return -1;
        }if (karatekaA.surname.toLowerCase() > karatekaB.surname.toLowerCase()){
            return 1;
        }
        //surname ==
        if (karatekaA.name.toLowerCase() < karatekaB.name.toLowerCase()){
            return -1;
        }if (karatekaA.name.toLowerCase() > karatekaB.name.toLowerCase()){
            return 1;
        }
        return 0;
    }
});
