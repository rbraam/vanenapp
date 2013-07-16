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
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StrictBinding;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import org.stripesstuff.stripersist.Stripersist;

/**
 *
 * @author Roy Braam
 */
@StrictBinding
@UrlBinding("/action/admin/participant/{$event}")
public class ParticipantActionBean extends OrganizeVanencompetitionActionBean{
    private static final String JSP = "/WEB-INF/jsp/admin/participant.jsp";
    
    List<Karateka> participants = new ArrayList<Karateka>();
    List<Karateka> karatekas = new ArrayList<Karateka>();
    
    @DefaultHandler
    public Resolution view(){
        if (this.getVanencompetition()==null){
            setNoVanencompetitionMessage();
            return this.getChooseVanencompetitionResolution();
        }
        List<Integer> notInList = new ArrayList<Integer>();
        this.participants = new ArrayList<Karateka>();
        for (Participant p : this.vanencompetition.getParticipants()){
            if (p.getKarateka()!=null){
                this.participants.add(p.getKarateka());
                notInList.add(p.getKarateka().getId());
            }
        }
        if (notInList.isEmpty()){
            this.karatekas = Stripersist.getEntityManager().createQuery("FROM Karateka").getResultList();
        }else{
            this.karatekas = Stripersist.getEntityManager().createQuery("FROM Karateka where id not in(:l)").setParameter("l", notInList).getResultList();
        }
        return new ForwardResolution(JSP);
    }
}
