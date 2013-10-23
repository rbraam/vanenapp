/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.roybraam.vanenapp.stripes;

import com.roybraam.vanenapp.entity.Organisation;
import com.roybraam.vanenapp.entity.Participant;
import com.roybraam.vanenapp.entity.Poule;
import com.roybraam.vanenapp.entity.Role;
import com.roybraam.vanenapp.entity.User;
import com.roybraam.vanenapp.entity.Vanencompetition;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SimpleMessage;
import net.sourceforge.stripes.action.StrictBinding;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.stripesstuff.stripersist.Stripersist;

/**
 *
 * @author Roy Braam
 */
@StrictBinding
@UrlBinding("/action/admin/points/{$event}")
public class PointsActionBean implements ActionBean {

    private static final Log log = LogFactory.getLog(PointsActionBean.class);
    private String JSP = "/WEB-INF/jsp/admin/points.jsp";
    private String VANEN_JSP = "/WEB-INF/jsp/admin/selectVanencompetition.jsp";
    private ActionBeanContext context;
    @Validate
    private List<Vanencompetition> vanencompetitions;
    @Validate
    private List<Poule> poules;
    @Validate
    private Vanencompetition vanencompetition;
    @Validate
    private HashMap<Long,Integer> points = new HashMap<Long,Integer>();
    
    @DefaultHandler
    public Resolution unspecified() {
        User user = (User) context.getRequest().getUserPrincipal();
        Organisation o = user.getOrganisation();
        String query = "FROM Vanencompetition where date <= :now";
        Date now = new Date();
        if (user.checkRole(Role.SUPERADMIN.name())) {
            setVanencompetitions((List<Vanencompetition>) Stripersist.getEntityManager().createQuery(query)
                    .setParameter("now", now).getResultList());
        } else if (o != null) {
            query += " and organisation = :o";
            setVanencompetitions((List<Vanencompetition>) Stripersist.getEntityManager().createQuery(query)
                    .setParameter("now", now).setParameter("o", o).getResultList());
        }
        return new ForwardResolution(VANEN_JSP);
    }

    public Resolution showPoints() {
        Date now = new Date();
        if (this.vanencompetition != null && this.vanencompetition.getDate().getTime() <= now.getTime()) {
            this.poules = Stripersist.getEntityManager().createQuery("FROM Poule where vanencompetition = :v")
                    .setParameter("v", this.vanencompetition)
                    .getResultList();
            
            this.points.clear();
            for (Participant p : this.getVanencompetition().getParticipants()){
                this.points.put(p.getId(), p.getPoints());
            }
            
            return new ForwardResolution(JSP);
        }
        return new ForwardResolution(VANEN_JSP);
    }
    
    public Resolution cancel(){
        return unspecified();
    }

    
    public Resolution save(){
        for (Entry<Long,Integer> entry: this.points.entrySet()){
            Participant p = Stripersist.getEntityManager().find(Participant.class, entry.getKey());
            p.setPoints(entry.getValue());
            Stripersist.getEntityManager().persist(p);
        }
        Stripersist.getEntityManager().getTransaction().commit();        
        getContext().getMessages().add(new SimpleMessage("Punten met succes opgeslagen"));
        return this.showPoints();
    }

    @Override
    public void setContext(ActionBeanContext context) {
        this.context = context;
    }

    @Override
    public ActionBeanContext getContext() {
        return this.context;
    }

    public List<Vanencompetition> getVanencompetitions() {
        return vanencompetitions;
    }

    public void setVanencompetitions(List<Vanencompetition> vanencompetitions) {
        this.vanencompetitions = vanencompetitions;
    }

    public Vanencompetition getVanencompetition() {
        return vanencompetition;
    }

    public void setVanencompetition(Vanencompetition vanencompetition) {
        this.vanencompetition = vanencompetition;
    }

    public List<Poule> getPoules() {
        return poules;
    }

    public void setPoules(List<Poule> poules) {
        this.poules = poules;
    }

    public HashMap<Long,Integer> getPoints() {
        return points;
    }

    public void setPoints(HashMap<Long,Integer> points) {
        this.points = points;
    }
}
