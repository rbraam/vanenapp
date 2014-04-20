/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.roybraam.vanenapp.stripes;

import com.roybraam.vanenapp.entity.CompetitionType;
import com.roybraam.vanenapp.entity.Kyu;
import com.roybraam.vanenapp.entity.Participant;
import com.roybraam.vanenapp.entity.Poule;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import javax.persistence.Query;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SimpleMessage;
import net.sourceforge.stripes.action.StrictBinding;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import org.stripesstuff.stripersist.Stripersist;

/**
 *
 * @author Roy Braam
 */
@StrictBinding
@UrlBinding("/action/admin/print/{$event}")
public class PrintActionBean extends OrganizeVanencompetitionActionBean {

    private static final String JSP = "/WEB-INF/jsp/admin/print.jsp";
    private static final String PRINT_JSP = "/WEB-INF/jsp/admin/printPoule.jsp";
    private ActionBeanContext context;
    @Validate
    private List<Poule> poules = new ArrayList<Poule>();
    @Validate
    private List<Participant> participantsNotInPoule = new ArrayList<Participant>();
    @Validate
    private List<Poule> invalidKataPoules = new ArrayList<Poule>();
    @Validate 
    private List<Poule> invalidKumitePoules = new ArrayList<Poule>();
    @Validate
    private LinkedHashMap<Kyu,Integer> validKataPoulesWithKyu = new LinkedHashMap<Kyu,Integer>();
    @Validate
    private String belt = null;
    @Validate
    private String competitionType = null;

    @DefaultHandler
    public Resolution view() {
        if (this.getVanencompetition() == null) {
            setNoVanencompetitionMessage();
            return this.getChooseVanencompetitionResolution();
        }
        participantsNotInPoule = Stripersist.getEntityManager()
                .createQuery("FROM Participant where vanencompetition = :v and poule is null")
                .setParameter("v", this.getVanencompetition()).getResultList();
        if (!participantsNotInPoule.isEmpty()) {
            getContext().getMessages().add(new SimpleMessage("Er zijn nog " + participantsNotInPoule.size() + " karateka's die niet zijn ingedeeld!"));
        }

        List<Poule> poules = Stripersist.getEntityManager().createQuery("FROM Poule where vanencompetition = :v order by type,startKyu,startAge")
                .setParameter("v", getVanencompetition())
                .getResultList();

        for (Kyu k : Kyu.values()){
            validKataPoulesWithKyu.put(k,new Integer("0"));
        }
        for (Poule p : poules) {
            if (p.getType().equals(CompetitionType.KATA)){
                if (p.getParticipants().size() < 3 || p.getParticipants().size() > 6) {
                    invalidKataPoules.add(p);
                }else{
                    validKataPoulesWithKyu.put(p.getStartKyu(),validKataPoulesWithKyu.get(p.getStartKyu())+1);
                }
            }else{
                if (p.getParticipants().size() < 3 || p.getParticipants().size() > 6) {
                    invalidKumitePoules.add(p);
                }
            }
        }
        return new ForwardResolution(JSP);
    }

    public Resolution printPoules() {
        if (this.poules.isEmpty()) {
            Kyu kyu = null;
            CompetitionType type =null;
            String q = "FROM Poule where vanencompetition = :v";
            if (this.belt != null) {
                kyu = Kyu.valueOf(this.belt);
                q += " and startKyu = :b";
            }
            if (this.competitionType !=null){
                type = CompetitionType.valueOf(this.competitionType);
                q+= " and type = :t";
            }
            Query query = Stripersist.getEntityManager().createQuery(q);
            query.setParameter("v", getVanencompetition());
            if (kyu != null) {
                query.setParameter("b", kyu);
            }if (type!=null){
                query.setParameter("t",type);
            }
            List<Poule> poules = query.getResultList();

            for (Poule p : poules) {
                if (p.getParticipants().size() >= 3 && p.getParticipants().size() <= 6) {
                    this.poules.add(p);
                }
            }
        }
        return new ForwardResolution(PRINT_JSP);
    }

    //<editor-fold defaultstate="collapsed" desc="Getters/Setters">
    public List<Participant> getParticipantsNotInPoule() {
        return participantsNotInPoule;
    }
    
    public void setParticipantsNotInPoule(List<Participant> participantsNotInPoule) {
        this.participantsNotInPoule = participantsNotInPoule;
    }
    
    public List<Poule> getInvalidKataPoules() {
        return invalidKataPoules;
    }
    
    public void setInvalidKataPoules(List<Poule> invalidPoules) {
        this.invalidKataPoules = invalidPoules;
    }
    
    public List<Poule> getPoules() {
        return poules;
    }
    
    public void setPoules(List<Poule> poules) {
        this.poules = poules;
    }
    
    public String getBelt() {
        return belt;
    }
    
    public void setBelt(String belt) {
        this.belt = belt;
    }
    
    public LinkedHashMap<Kyu,Integer> getValidKataPoulesWithKyu() {
        return validKataPoulesWithKyu;
    }
    
    public void setValidKataPoulesWithKyu(LinkedHashMap<Kyu,Integer> validKataPoulesWithKyu) {
        this.validKataPoulesWithKyu = validKataPoulesWithKyu;
    }
    
    public List<Poule> getInvalidKumitePoules() {
        return invalidKumitePoules;
    }
    
    public void setInvalidKumitePoules(List<Poule> invalidKumitePoules) {
        this.invalidKumitePoules = invalidKumitePoules;
    }
    
    public String getCompetitionType() {
        return competitionType;
    }

    public void setCompetitionType(String competitionType) {
        this.competitionType = competitionType;
    }
}
    //</editor-fold>
