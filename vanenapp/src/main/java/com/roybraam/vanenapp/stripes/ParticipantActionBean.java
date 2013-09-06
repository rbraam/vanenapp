/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.roybraam.vanenapp.stripes;

import com.roybraam.vanenapp.entity.Karateka;
import com.roybraam.vanenapp.entity.Participant;
import com.roybraam.vanenapp.entity.Vanencompetition;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StrictBinding;
import net.sourceforge.stripes.action.UrlBinding;
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
    @Validate
    private String participants = "";
    private String participantsJson = "";

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
        reloadVanencompetition();
        this.getVanencompetition().getParticipants().clear();
        for (Integer kid : this.getParticipantsList()){
            Karateka k = em.find(Karateka.class, kid);
            Participant p = new Participant(this.getVanencompetition(),k);
            this.getVanencompetition().getParticipants().add(p);
        }
        em.persist(this.getVanencompetition());
        em.getTransaction().commit();
        this.createParticipantsJson();
        return new ForwardResolution(JSP);
    }

    private void createParticipantsJson(){
        if (this.getVanencompetition()!=null){
            JSONArray participantsArray = new JSONArray();
            reloadVanencompetition();
            for (Participant p : this.getVanencompetition().getParticipants()) {
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
    public String getParticipants() {
        return participants;
    }

    public void setParticipants(String participants) {
        this.participants = participants;
    }

    public List<Integer> getParticipantsList() {
        List<Integer> result = new ArrayList<Integer>();
        if (this.participants != null) {
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
}
