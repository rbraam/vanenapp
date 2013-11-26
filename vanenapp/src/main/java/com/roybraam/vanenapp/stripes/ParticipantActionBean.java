/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.roybraam.vanenapp.stripes;

import com.roybraam.vanenapp.entity.CompetitionType;
import com.roybraam.vanenapp.entity.Karateka;
import com.roybraam.vanenapp.entity.Participant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StrictBinding;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.SimpleError;
import net.sourceforge.stripes.validation.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.stripesstuff.stripersist.Stripersist;

/**
 *
 * @author Roy Braam
 */
 @StrictBinding
@UrlBinding("/action/admin/participant/{$event}")
public class ParticipantActionBean extends OrganizeVanencompetitionActionBean {

    private static final Log log = LogFactory.getLog(ParticipantActionBean.class);
    private static final String JSP = "/WEB-INF/jsp/admin/participant.jsp";
    private static final Integer maxAge=18;
    @Validate
    private String participantsAdded = "";
    @Validate
    private String participantsRemoved = "";
    private String participantsJson = "";
    
    @Validate(required = true)
    private String competitionType = "";

    @DefaultHandler
    public Resolution view() {
        if (this.getVanencompetition() == null) {
            setNoVanencompetitionMessage();
            return this.getChooseVanencompetitionResolution();
        }
        this.createParticipantsJson();
        return new ForwardResolution(JSP);
    }
    public Resolution save(){
        if (this.getVanencompetition() == null) {
            setNoVanencompetitionMessage();            
            return this.getChooseVanencompetitionResolution();
        }
        EntityManager em = Stripersist.getEntityManager();
        List<Participant> notRemoved = new ArrayList<Participant>();
        
        int oldParticipantLength = this.getVanencompetition().getParticipants().size();
        
        Iterator<Participant> it =this.getVanencompetition().getParticipants().iterator();
        
        List<Integer> removedInt = this.getParticipantsList(this.participantsRemoved);
        List<Integer> addedInt = this.getParticipantsList(this.participantsAdded);
        if (!removedInt.isEmpty()){
            while(it.hasNext()){
                Participant p = it.next();
                if (CompetitionType.valueOf(this.competitionType).equals(p.getType())){
                    if (removedInt.contains(p.getKarateka().getId())){
                        if (p.getPoule()!=null){
                            notRemoved.add(p);
                        }else{
                            it.remove();
                        }
                        break;
                    }                
                }
            }
        }
        for (Integer kid : addedInt){
            Karateka k = em.find(Karateka.class, kid);
            Participant p = new Participant(this.getVanencompetition(),k,CompetitionType.valueOf(this.competitionType));
            if (!this.getVanencompetition().getParticipants().contains(p)){
                this.getVanencompetition().getParticipants().add(p);
            }
        }
        
        if (this.getVanencompetition().getParticipants().size() != (oldParticipantLength + addedInt.size() - removedInt.size())){
            this.getContext().getMessages().add(new SimpleError("Niet alle deelnemers konden worden verwijderd/toegevoegd. Controleer de lijst."));
        }
        if (!notRemoved.isEmpty()){
            String message = "De volgende karateka's kunnen niet verwijderd worden omdat deze al in een poule"
                    + "zijn ingedeeld, verwijder ze eerst uit de poule om ze daarna hier te kunnen verwijderen:<br/> ";
            for (Participant p : notRemoved){
                message+=p.toString()+"<br/>";
            }
            this.getContext().getMessages().add(new SimpleError(message));
        }        
        em.persist(this.getVanencompetition());
        em.getTransaction().commit();
        this.createParticipantsJson();
        return new ForwardResolution(JSP);
    }
    
    @Override
    public Resolution getChooseVanencompetitionResolution(){
        this.setForwardParam("competitionType="+this.getCompetitionType());
        return super.getChooseVanencompetitionResolution();
    }

    private void createParticipantsJson(){
        if (this.getVanencompetition()!=null){
            Calendar maxCal = Calendar.getInstance();
            maxCal.setTime(this.getVanencompetition().getDate());
            maxCal.add(Calendar.YEAR, -this.maxAge);
            
            JSONArray participantsArray = new JSONArray();
            List<Participant> participantList = Stripersist.getEntityManager()
                    .createQuery("FROM Participant where vanencompetition = :v"
                    + " and type = :t"
                    + " and karateka.birthdate > :mc"
                    + " order by karateka.surname,karateka,name")
                    .setParameter("v", this.getVanencompetition())
                    .setParameter("t", CompetitionType.valueOf(this.competitionType))
                    .setParameter("mc",maxCal.getTime())
                    .getResultList();
            for (Participant p : participantList) {
                if (p.getKarateka() != null) {
                    try {
                        participantsArray.put(p.getKarateka().toJSON());
                    } catch (Exception e) {
                        log.error("Failed to get Karateka", e);
                    }
                }
            }
            this.participantsJson = participantsArray.toString();
        }
    }
    public List<Integer> getParticipantsList(String participants) {
        List<Integer> result = new ArrayList<Integer>();
        if (participants != null) {
            String[] tokens = participants.split(",");
            for (int i = 0; i < tokens.length; i++) {
                result.add(Integer.parseInt(tokens[i].trim()));
            }
        }
        return result;
    }

    public String getParticipantsJson() {
        return participantsJson;
    }

    public void setParticipantsJson(String participantsJson) {
        this.participantsJson = participantsJson;
    }

    public String getCompetitionType() {
        return competitionType;
    }

    public void setCompetitionType(String competitionType) {
        this.competitionType = competitionType;
    }

    public String getParticipantsAdded() {
        return participantsAdded;
    }

    public void setParticipantsAdded(String participantsAdded) {
        this.participantsAdded = participantsAdded;
    }

    public String getParticipantsRemoved() {
        return participantsRemoved;
    }

    public void setParticipantsRemoved(String participantsRemoved) {
        this.participantsRemoved = participantsRemoved;
    }
}
