/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.roybraam.vanenapp.stripes;

import com.roybraam.vanenapp.entity.Participant;
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
    private ActionBeanContext context;
    
    @Validate
    private List<Participant> participantsNotInPoule = new ArrayList<Participant>();
    
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
        return new ForwardResolution(JSP);
    }

    public List<Participant> getParticipantsNotInPoule() {
        return participantsNotInPoule;
    }

    public void setParticipantsNotInPoule(List<Participant> participantsNotInPoule) {
        this.participantsNotInPoule = participantsNotInPoule;
    }
}