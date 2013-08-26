/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.roybraam.vanenapp.stripes;

import com.roybraam.vanenapp.entity.Kyu;
import com.roybraam.vanenapp.entity.Participant;
import com.roybraam.vanenapp.entity.Poule;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletResponse;
import net.sourceforge.stripes.action.Before;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SimpleMessage;
import net.sourceforge.stripes.action.StreamingResolution;
import net.sourceforge.stripes.action.StrictBinding;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
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
    @Validate
    private Integer startAge=null;
    @Validate
    private Integer endAge=null;
    @Validate
    private List<Participant> participants = new ArrayList<Participant>();
    
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
            this.participants = this.poule.getParticipants();
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
        EntityManager em = Stripersist.getEntityManager();
        
        this.poule.setVanencompetition(this.getVanencompetition());
        if (this.poule.getName()==null){
            this.poule.setName(this.poule.toString());
        }
        //set correct participants for poule
        for (Participant p : this.poule.getParticipants()){
            p.setPoule(null);
            em.persist(p);
        }
        for (Participant p : this.participants){
           p.setPoule(this.poule);
            em.persist(p);
        }
        em.persist(this.poule);
        em.getTransaction().commit();
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
        if (this.poule !=null && this.poule.getVanencompetition()!=null && this.getVanencompetition()==null){
            this.setVanencompetition(this.poule.getVanencompetition());
        }
        this.poules = Stripersist.getEntityManager().createQuery("FROM Poule where vanencompetition = :v").setParameter("v",this.getVanencompetition()).getResultList();
    }
    
    public Resolution participantList(){
        final List<Participant> kandidates;
        if (this.getVanencompetition()!=null){
            Date vanenDate = this.getVanencompetition().getDate();
            Calendar startCal = Calendar.getInstance();
            startCal.setTime(vanenDate);
            Calendar endCal = Calendar.getInstance();
            endCal.setTime(vanenDate);
            
            startCal.add(Calendar.YEAR, -this.startAge);
            endCal.add(Calendar.YEAR, -this.endAge);
            
            /*Date startDate= new GregorianCalendar(vanenDate.get);
            Date endDate = this.getVanencompetition().getDate();*/
            Query q = Stripersist.getEntityManager().createQuery("FROM Participant p where p.vanencompetition = :v"
                    + " and (p.poule = null or p.poule = :p)"
                    + " and p.karateka.belt BETWEEN :ek and :sk"
              //      + " and p.karateka.birthdate >= :sd and p.karateka.birthdate < :ed"
                    );
            q.setParameter("v", this.getVanencompetition());
            q.setParameter("sk", Kyu.valueOf(this.startKyu));
            q.setParameter("ek", Kyu.valueOf(this.endKyu));
            q.setParameter("p", this.poule);
           // q.setParameter("sd",startCal.getTime());
            //q.setParameter("ed",endCal.getTime());
            kandidates = q.getResultList();            
        }else{
            kandidates = new ArrayList<Participant>();
        }
        return new StreamingResolution("application/json") {
            @Override
            public void stream(HttpServletResponse response) throws Exception {
                ObjectMapper mapper = new ObjectMapper();
                StringWriter s = new StringWriter();
                mapper.writeValue(s, kandidates);
                response.getWriter().print(s.toString());
            }
        };
    }

    //<editor-fold defaultstate="collapsed" desc="Getters/setters">
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
    //</editor-fold>

    public Integer getStartAge() {
        return startAge;
    }

    public void setStartAge(Integer startAge) {
        this.startAge = startAge;
    }

    public Integer getEndAge() {
        return endAge;
    }

    public void setEndAge(Integer endAge) {
        this.endAge = endAge;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }
}
