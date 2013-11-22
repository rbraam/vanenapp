/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.roybraam.vanenapp.stripes;

import com.roybraam.vanenapp.entity.CompetitionType;
import com.roybraam.vanenapp.entity.KataPoule;
import com.roybraam.vanenapp.entity.KumitePoule;
import com.roybraam.vanenapp.entity.Kyu;
import com.roybraam.vanenapp.entity.Participant;
import com.roybraam.vanenapp.entity.Poule;
import com.roybraam.vanenapp.entity.Vanencompetition;
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
        @Validate(on = {"save"}, field = "endAge", required = true, label = "T/m leeftijd"),
        @Validate(on = {"save"}, field = "gender", label = "Geslacht")

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
    @Validate(on = {"save"},required = true)
    private String type=null;
    
    @Validate
    private Double startWeight=null;
    @Validate
    private Double endWeight=null;
    @Validate
    private String gender=null;
    @Validate
    private List<Participant> participants = new ArrayList<Participant>();
    @Validate
    private List<Participant> participantsWithoutPoule = new ArrayList<Participant>();
    
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
            if (this.poule.getType()!=null){
                this.type = this.poule.getType().name();
            }else{
                this.type = CompetitionType.KATA.name();
            }
            if (this.poule instanceof KumitePoule){
                KumitePoule kp = (KumitePoule)this.poule;
                this.startWeight = kp.getStartWeight();
                this.endWeight = kp.getEndWeight();
            }
                        
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
        if (!CompetitionType.valueOf(this.type).equals(this.poule.getType())){
            if (CompetitionType.valueOf(this.type).equals(CompetitionType.KUMITE)){
                this.poule = new KumitePoule(this.poule);
            }else{
                this.poule= new KataPoule(this.poule);
            }
        }            
        this.poule.setType(CompetitionType.valueOf(this.type));
            
        EntityManager em = Stripersist.getEntityManager();
        
        this.poule.setVanencompetition(this.getVanencompetition());
        
        clearPouleParticipants(em);
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
            EntityManager em= Stripersist.getEntityManager();
            clearPouleParticipants(em);
            em.remove(this.poule);
            em.getTransaction().commit();
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
        if (this.getVanencompetition()!=null){
            this.poules = Stripersist.getEntityManager().createQuery("FROM Poule where vanencompetition = :v").setParameter("v",this.getVanencompetition()).getResultList();
            if (this.type!=null || (this.poule!=null && this.poule.getType()!=null)){
                CompetitionType ct =null;
                if (this.type!=null){
                    ct= CompetitionType.valueOf(this.type);
                }else{
                    ct = this.poule.getType();
                }
                this.participantsWithoutPoule = Stripersist.getEntityManager()
                        .createQuery("FROM Participant where poule is null and vanencompetition = :v and type = :t order by karateka.belt,karateka.birthdate")
                        .setParameter("v", this.getVanencompetition()).setParameter("t", ct).getResultList();
            }else{
                this.participantsWithoutPoule = Stripersist.getEntityManager()
                        .createQuery("FROM Participant where poule is null and vanencompetition = :v order by karateka.belt,karateka.birthdate")
                        .setParameter("v", this.getVanencompetition()).getResultList();
            }
        }
    }
    
    public Resolution participantList(){
        final List<Participant> kandidates;
        if (this.getVanencompetition()!=null && this.startAge!=null && this.endAge!=null &&
                this.startKyu!=null && this.endKyu!=null){
            Date vanenDate = this.getVanencompetition().getDate();
            Calendar startCal = Calendar.getInstance();
            startCal.setTime(vanenDate);
            Calendar endCal = Calendar.getInstance();
            endCal.setTime(vanenDate);
            
            startCal.add(Calendar.YEAR, -this.startAge);
            endCal.add(Calendar.YEAR, -this.endAge);
            
            /*Date startDate= new GregorianCalendar(vanenDate.get);
            Date endDate = this.getVanencompetition().getDate();*/
            String queryString="FROM Participant p where p.vanencompetition = :v"
                    + " and (p.poule = :p or p.poule is null)"
                    + " and p.karateka.belt BETWEEN :ek and :sk"
                    + " and p.karateka.birthdate <= :sd and p.karateka.birthdate >= :ed"
                    + " and p.type = :t";
            if (CompetitionType.KUMITE.equals(CompetitionType.valueOf(this.type))){
                if (this.startWeight!=null){
                    queryString+= " and p.karateka.weight >= :sw";
                }if (this.endWeight!=null){
                    queryString+= " and p.karateka.weight <= :ew";
                }
            }
            if (gender!=null){
                queryString+=" and p.karateka.gender = :g";
            }
            Query q = Stripersist.getEntityManager().createQuery(queryString);
            q.setParameter("v", this.getVanencompetition());
            q.setParameter("sk", Kyu.valueOf(this.startKyu));
            q.setParameter("ek", Kyu.valueOf(this.endKyu));
            q.setParameter("p", this.poule);
            q.setParameter("sd",startCal.getTime());
            q.setParameter("ed",endCal.getTime());
            q.setParameter("t", CompetitionType.valueOf(this.type));
            if (CompetitionType.KUMITE.equals(CompetitionType.valueOf(this.type))){
                if(this.startWeight!=null){
                    q.setParameter("sw", this.startWeight);
                }if (this.endWeight!=null){
                    q.setParameter("ew", this.endWeight);
                }
            }if(gender!=null){
                q.setParameter("g", gender);
            }
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

    private void clearPouleParticipants(EntityManager em) {
        //set correct participants for poule
        for (Participant p : this.poule.getParticipants()){
            p.setPoule(null);
            em.persist(p);
        }
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
    
    public Vanencompetition getVanencompetition(){
        if (super.getVanencompetition()==null && this.poule !=null){
            return this.poule.getVanencompetition();
        }else{
            return super.getVanencompetition();
        }
    }

    public List<Participant> getParticipantsWithoutPoule() {
        return participantsWithoutPoule;
    }

    public void setParticipantsWithoutPoule(List<Participant> participantsWithoutPoule) {
        this.participantsWithoutPoule = participantsWithoutPoule;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getStartWeight() {
        return startWeight;
    }

    public void setStartWeight(Double startWeight) {
        this.startWeight = startWeight;
    }

    public Double getEndWeight() {
        return endWeight;
    }

    public void setEndWeight(Double endWeight) {
        this.endWeight = endWeight;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    //</editor-fold>
}
