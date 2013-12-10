/**
 * Calculates the age compared to a given date
 */
Ext.define("AgeCalculator",{
    /**
     * @param date a string that is used as date that is used to compare
     * format: yyyy-MM-dd
     */
    date: null,
    constructor: function (date){
        this.date= this.createDate(date);
    },
    createDate: function(dateString){
        var tokens = dateString.split("-");
        var s= tokens[1]+"/"+tokens[2]+"/"+tokens[0];
        return new Date(s);
    },
    getAge: function (birthDate){
        birthDate = this.createDate(birthDate);
        var years = this.date.getFullYear() - birthDate.getFullYear();

        birthDate.setFullYear(this.date.getFullYear());
        // If the user's birthday has not occurred yet this year, subtract 1.
        if (this.date < birthDate){
            years--;
        }
        return years;
    }
})