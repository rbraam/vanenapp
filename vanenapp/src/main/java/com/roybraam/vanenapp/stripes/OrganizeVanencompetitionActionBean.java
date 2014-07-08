/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.roybraam.vanenapp.stripes;

import com.roybraam.vanenapp.entity.Participant;
import com.roybraam.vanenapp.entity.Role;
import com.roybraam.vanenapp.entity.User;
import com.roybraam.vanenapp.entity.Vanencompetition;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.After;
import net.sourceforge.stripes.action.Before;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StrictBinding;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.util.UrlBuilder;
import net.sourceforge.stripes.validation.SimpleError;
import net.sourceforge.stripes.validation.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.stripesstuff.plugin.session.Session;
import org.stripesstuff.stripersist.Stripersist;

/**
 *
 * @author Roy Braam
 */
@StrictBinding
@UrlBinding("/action/admin/organizevanencompetition/{$event}")
public class OrganizeVanencompetitionActionBean implements ActionBean {
    private static final Log log = LogFactory.getLog(OrganizeVanencompetitionActionBean.class);
    
    protected static final String CHOOSE_VANENCOMPETITIE_JSP = "/WEB-INF/jsp/admin/choosevanencompetition.jsp";
    protected static final String PARTICIPANTPOINTS_BASEURL_PARAM = "participantpoints.baseUrl";
    private ActionBeanContext context;
    @Session
    private Long vanencompetitionId;
    @Validate
    private Vanencompetition vanencompetition;
    private List<Vanencompetition> vanencompetitions = new ArrayList<Vanencompetition>();
    private User user;
    
    private String forward ="";
    private String forwardEvent="";
    private String forwardParam="";

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
    public Resolution getChooseVanencompetitionResolution() {
        this.forward=this.getClass().getCanonicalName();
        if (user.checkRole(Role.SUPERADMIN.name())) {
            setVanencompetitions((List<Vanencompetition>) Stripersist.getEntityManager().createQuery("FROM Vanencompetition ORDER BY date").getResultList());
        } else if (user.getOrganisation() != null) {
            setVanencompetitions((List<Vanencompetition>) Stripersist.getEntityManager().createQuery("From Vanencompetition where organisation = :o ORDER BY date").setParameter("o", user.getOrganisation()).getResultList());
        }
        return new ForwardResolution(CHOOSE_VANENCOMPETITIE_JSP);

    }

    public void setNoVanencompetitionMessage() {
        getContext().getMessages().add(new SimpleError("Er moet eerst een vanencompetitie worden geselecteerd"));
    }
    
    /**
     * Get the base url of this application.
     * @return the url for this application. If not set as a contexst param, the url is resolved by using
     * the requested url.
     */
    protected String getBaseUrl(){
        StringBuilder sb = new StringBuilder();
        if (this.getContext().getServletContext().getInitParameter(PARTICIPANTPOINTS_BASEURL_PARAM)!=null){
            sb.append(this.getContext().getServletContext().getInitParameter(PARTICIPANTPOINTS_BASEURL_PARAM));
        }else{
            log.warn("Unable to get context param '"+PARTICIPANTPOINTS_BASEURL_PARAM+"' resolving the url from the request.");
            HttpServletRequest req = this.getContext().getRequest();
            sb.append(req.getScheme());
            sb.append("://");
            sb.append(req.getServerName());
            if (req.getServerPort()!=80){
                sb.append(":"+req.getServerPort());
            }
            sb.append(req.getContextPath());
        }
        return sb.toString();
    }
    /**
     * @see #createParticipantUrl(com.roybraam.vanenapp.entity.Participant, java.lang.String) 
     * @param p
     * @return 
     */
    protected String createParticipantUrl(Participant p){
        return this.createParticipantUrl(p,this.getBaseUrl());
    }
    
    /**
     * Create a participant url for the given participant
     * @param p the participant
     * @param baseUrl the base url used to build this url. 
     * @return 
     */
    protected String createParticipantUrl(Participant p, String baseUrl){
        StringBuffer sb = new StringBuffer();
        if (baseUrl!=null){
            sb.append(baseUrl);       
        }
        UrlBuilder builder = new UrlBuilder(Locale.ENGLISH, KaratekaPointsActionBean.class, false);
        builder.addParameter("p", p.getId());
        builder.addParameter("code",KaratekaPointsActionBean.generateCode(p, this.getContext()));
        sb.append(builder.toString());
        return sb.toString();
    }
    
    //<editor-fold defaultstate="collapsed" desc="Getters and setters">
    public ActionBeanContext getContext() {
        return context;
    }

    public void setContext(ActionBeanContext context) {
        this.context = context;
    }

    public Vanencompetition getVanencompetition() {
        if (this.vanencompetition==null){
            Object o = this.getContext().getRequest().getSession().getAttribute("vanencompetition");
            if (o==null){
                return null;
            }
            Long vanenId = (Long) o;
            this.vanencompetition=Stripersist.getEntityManager().find(Vanencompetition.class, vanenId);
        }
        return this.vanencompetition;
    }

    public void setVanencompetition(Vanencompetition vanencompetition) {
        this.vanencompetition=vanencompetition;
        Long id = null;
        if (this.vanencompetition!=null){
            id=new Long(vanencompetition.getId());
        }
        this.getContext().getRequest().getSession().setAttribute("vanencompetition", id);
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

    public String getForward() {
        return forward;
    }

    public void setForward(String forward) {
        this.forward = forward;
    }
    
    public String getForwardEvent() {
        return forwardEvent;
    }

    public void setForwardEvent(String forwardEvent) {
        this.forwardEvent = forwardEvent;
    }
    
    public String getForwardParam() {
        return forwardParam;
    }

    public void setForwardParam(String forwardParam) {
        this.forwardParam = forwardParam;
    }
    //</editor-fold>


}
