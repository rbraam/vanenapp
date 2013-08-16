/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.roybraam.vanenapp.stripes;

import com.roybraam.vanenapp.entity.Kyu;
import com.roybraam.vanenapp.entity.Poule;
import java.util.ArrayList;
import java.util.List;
import net.sourceforge.stripes.action.Before;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SimpleMessage;
import net.sourceforge.stripes.action.StrictBinding;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.stripesstuff.stripersist.Stripersist;

/**
 *
 * @author Roy Braam
 */
@StrictBinding
@UrlBinding("/action/admin/poule/{$event}")
public class PouleActionBean extends OrganizeVanencompetitionActionBean{
    private static final Log log = LogFactory.getLog(PouleActionBean.class);
    private static String JSP = "/WEB-INF/jsp/admin/poule.jsp";

    private List<Poule> poules = new ArrayList<Poule>();
    
    @Validate    
    @ValidateNestedProperties({
        @Validate(on = {"save"}, field = "name", maxlength = 255, label = "Naam"),
        @Validate(on = {"save"}, field = "startAge", required = true, label = "Vanaf leeftijd"),
        @Validate(on = {"save"}, field = "endAge", required = true, label = "Tot leeftijd")
    })
    private Poule poule;
    
    @Validate(on={"save"},required = true)
    private String startKyu = null;
    @Validate(on={"save"},required = true)
    private String endKyu = null;
    
    @DefaultHandler
    public Resolution view() {
        if (this.getVanencompetition() == null) {
            setNoVanencompetitionMessage();
            return this.getChooseVanencompetitionResolution();
        }
        return new ForwardResolution(JSP);
    }
    
    public Resolution edit(){
        if (this.getVanencompetition() == null) {
            setNoVanencompetitionMessage();
            return this.getChooseVanencompetitionResolution();
        }
        if(this.poule!=null){
            this.startKyu = poule.getStartKyu().name();
            this.endKyu = poule.getEndKyu().name();
        }
        return new ForwardResolution(JSP);
    }
    
    public Resolution save(){
        if (this.getVanencompetition() == null) {
            setNoVanencompetitionMessage();
            return this.getChooseVanencompetitionResolution();
        }
        this.poule.setStartKyu(Kyu.valueOf(this.startKyu));
        this.poule.setEndKyu(Kyu.valueOf(this.endKyu));
        if (this.poule.getStartKyu().getId() > this.poule.getEndKyu().getId()){
            Kyu temp = this.poule.getStartKyu();
            this.poule.setStartKyu(this.poule.getEndKyu());
            this.poule.setEndKyu(temp);
        }
        if (this.poule.getStartAge() > this.poule.getEndAge()){
            Integer tmp = this.poule.getStartAge();
            this.poule.setStartAge(this.poule.getEndAge());
            this.poule.setEndAge(tmp);
        }
        this.poule.setVanencompetition(this.getVanencompetition());
        if (this.poule.getName()==null){
            this.poule.setName(this.poule.toString());
        }
        Stripersist.getEntityManager().persist(this.poule);
        Stripersist.getEntityManager().getTransaction().commit();
        getContext().getMessages().add(new SimpleMessage("Poule is opgeslagen"));
        this.list();
        return new ForwardResolution(JSP);
    }
    
    public Resolution delete() {
        if (this.getVanencompetition() == null) {
            setNoVanencompetitionMessage();
            return this.getChooseVanencompetitionResolution();
        }
        if (this.poule != null) {
            Stripersist.getEntityManager().remove(this.poule);
            Stripersist.getEntityManager().getTransaction().commit();
            getContext().getMessages().add(new SimpleMessage("Poule is verwijderd"));
            this.list();
        }
        return new ForwardResolution(JSP);
    }
    
    public Resolution cancel(){
        return new ForwardResolution(JSP);
    }
    
    @Before(stages = LifecycleStage.EventHandling)
    public void list(){
        this.poules = Stripersist.getEntityManager().createQuery("FROM Poule where vanencompetition = :v").setParameter("v",this.getVanencompetition()).getResultList();
    }

    public List<Poule> getPoules() {
        return poules;
    }

    public void setPoules(List<Poule> poules) {
        this.poules = poules;
    }

    public Poule getPoule() {
        return poule;
    }

    public void setPoule(Poule poule) {
        this.poule = poule;
    }

    public String getStartKyu() {
        return startKyu;
    }

    public void setStartKyu(String startKyu) {
        this.startKyu = startKyu;
    }

    public String getEndKyu() {
        return endKyu;
    }

    public void setEndKyu(String endKyu) {
        this.endKyu = endKyu;
    }
}
