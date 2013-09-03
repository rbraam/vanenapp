/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.roybraam.vanenapp.stripes;

import com.roybraam.vanenapp.entity.Participant;
import com.roybraam.vanenapp.entity.Poule;
import java.util.ArrayList;
import java.util.List;
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
public class PrintActionBean extends OrganizeVanencompetitionActionBean{
    
    private static final String JSP = "/WEB-INF/jsp/admin/print.jsp";
    private static final String PRINT_JSP= "/WEB-INF/jsp/admin/printPoule.jsp";
    private ActionBeanContext context;
    
    @Validate
    private List<Poule> poules = new ArrayList<Poule>();
    
    @Validate
    private List<Participant> participantsNotInPoule = new ArrayList<Participant>();
    
    @Validate
    private List<Poule> invalidPoules = new ArrayList<Poule>();
    
    @DefaultHandler
    public Resolution view() {
        if (this.getVanencompetition() == null) {
            setNoVanencompetitionMessage();
            return this.getChooseVanencompetitionResolution();
        }
        participantsNotInPoule = Stripersist.getEntityManager()
                .createQuery("FROM Participant where vanencompetition = :v and poule is null")
                .setParameter("v",this.getVanencompetition()).getResultList();
        if (!participantsNotInPoule.isEmpty()){
            getContext().getMessages().add(new SimpleMessage("Er zijn nog "+participantsNotInPoule.size()+" karateka's die niet zijn ingedeeld!"));
        }
        
        List<Poule> poules = Stripersist.getEntityManager().createQuery("FROM Poule where vanencompetition = :v")
                .setParameter("v", getVanencompetition())
                .getResultList();
        
        for (Poule p : poules){
            if(p.getParticipants().size() < 3 || p.getParticipants().size() > 6){
                invalidPoules.add(p);
            }
        }
        
        return new ForwardResolution(JSP);
    }
    
    public Resolution printPoules(){
        List<Poule> poules = Stripersist.getEntityManager().createQuery("FROM Poule where vanencompetition = :v")
                .setParameter("v", getVanencompetition())
                .getResultList();
        
        for (Poule p : poules){
            if(p.getParticipants().size() >= 3 && p.getParticipants().size() <= 6){
                this.poules.add(p);
            }
        }
        return new ForwardResolution(PRINT_JSP);
    }

    public List<Participant> getParticipantsNotInPoule() {
        return participantsNotInPoule;
    }

    public void setParticipantsNotInPoule(List<Participant> participantsNotInPoule) {
        this.participantsNotInPoule = participantsNotInPoule;
    }

    public List<Poule> getInvalidPoules() {
        return invalidPoules;
    }

    public void setInvalidPoules(List<Poule> invalidPoules) {
        this.invalidPoules = invalidPoules;
    }

    public List<Poule> getPoules() {
        return poules;
    }

    public void setPoules(List<Poule> poules) {
        this.poules = poules;
    }
}