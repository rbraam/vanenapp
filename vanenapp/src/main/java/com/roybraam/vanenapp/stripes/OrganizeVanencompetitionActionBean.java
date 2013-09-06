/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.roybraam.vanenapp.stripes;

import com.roybraam.vanenapp.entity.Role;
import com.roybraam.vanenapp.entity.User;
import com.roybraam.vanenapp.entity.Vanencompetition;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.After;
import net.sourceforge.stripes.action.Before;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StrictBinding;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.validation.SimpleError;
import net.sourceforge.stripes.validation.Validate;

import org.stripesstuff.plugin.session.Session;
import org.stripesstuff.stripersist.Stripersist;

/**
 *
 * @author Roy Braam
 */
@StrictBinding
@UrlBinding("/action/admin/organizevanencompetition/{$event}")
public class OrganizeVanencompetitionActionBean implements ActionBean {
    protected static final String CHOOSE_VANENCOMPETITIE_JSP = "/WEB-INF/jsp/admin/choosevanencompetition.jsp";
    private ActionBeanContext context;
    @Session
    private Long vanencompetitionId;
    @Validate
    private Vanencompetition vanencompetition;
    private List<Vanencompetition> vanencompetitions = new ArrayList<Vanencompetition>();
    private User user;

    @Before(stages = LifecycleStage.BindingAndValidation)
    public void load() {
        user = (User) context.getRequest().getUserPrincipal();
    }

    @After(stages = {LifecycleStage.BindingAndValidation})
    public void initVanencompetition() {
        if (vanencompetitionId != null) {
            EntityManager em = Stripersist.getEntityManager();
            setVanencompetition(em.find(Vanencompetition.class, vanencompetitionId));
        }
    }
    /*@DefaultHandler
    public Resolution select(){
        if (vanencompetition!=null){
            this.setVanencompetition(vanencompetition);
        }
        return new ForwardResolution(ParticipantActionBean.class);
    }*/

    public Resolution getChooseVanencompetitionResolution() {
        if (user.checkRole(Role.SUPERADMIN.name())) {
            setVanencompetitions((List<Vanencompetition>) Stripersist.getEntityManager().createQuery("FROM Vanencompetition").getResultList());
        } else if (user.getOrganisation() != null) {
            setVanencompetitions((List<Vanencompetition>) Stripersist.getEntityManager().createQuery("From Vanencompetition where organisation = :o").setParameter("o", user.getOrganisation()).getResultList());
        }
        return new ForwardResolution(CHOOSE_VANENCOMPETITIE_JSP);

    }

    public void setNoVanencompetitionMessage() {
        getContext().getMessages().add(new SimpleError("Er moet eerst een vanencompetitie worden geselecteerd"));
    }
    
    public void reloadVanencompetition(){
        this.setVanencompetition(Stripersist.getEntityManager().find(Vanencompetition.class, this.getVanencompetition().getId()));
    }

    //<editor-fold defaultstate="collapsed" desc="Getters and setters">
    public ActionBeanContext getContext() {
        return context;
    }

    public void setContext(ActionBeanContext context) {
        this.context = context;
    }

    public Vanencompetition getVanencompetition() {
        Object o = this.getContext().getRequest().getSession().getAttribute("vanencompetition");
        if (o==null){
            return null;
        }
        return (Vanencompetition)o;
    }

    public void setVanencompetition(Vanencompetition vanencompetition) {
        this.getContext().getRequest().getSession().setAttribute("vanencompetition", vanencompetition);
    }
    
    public List<Vanencompetition> getVanencompetitions() {
        return vanencompetitions;
    }

    public void setVanencompetitions(List<Vanencompetition> vanencompetitions) {
        this.vanencompetitions = vanencompetitions;
    }
    public Long getVanencompetitionId() {
        return vanencompetitionId;
    }

    public void setVanencompetitionId(Long vanencompetitionId) {
        this.vanencompetitionId = vanencompetitionId;
    }
    //</editor-fold>

}
