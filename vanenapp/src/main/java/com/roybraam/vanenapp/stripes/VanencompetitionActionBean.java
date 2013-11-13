package com.roybraam.vanenapp.stripes;

import com.roybraam.vanenapp.entity.Organisation;
import com.roybraam.vanenapp.entity.Role;
import com.roybraam.vanenapp.entity.User;
import com.roybraam.vanenapp.entity.Vanencompetition;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.Before;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SimpleMessage;
import net.sourceforge.stripes.action.StrictBinding;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.validation.SimpleError;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import org.stripesstuff.stripersist.Stripersist;

/**
 *
 * @author Roy Braam
 */
@StrictBinding
@UrlBinding("/action/admin/vanencompetition/{$event}")
public class VanencompetitionActionBean implements ActionBean{
    private ActionBeanContext context;
    private static final String JSP = "/WEB-INF/jsp/admin/vanencompetition.jsp";
    protected boolean finishedVanencompetitions=false;
    
    @Validate
    private User user;
    @Validate (on = {"save"})
    @ValidateNestedProperties({
        @Validate(on = {"save"}, field = "location", required = true, maxlength = 255, label = "Locatie"),
        @Validate(on = {"save"}, field = "organisation", required = true, maxlength = 255, label = "Organisatie"),
        @Validate(on = {"save"}, field = "date", required = true, label = "Datum"),
        @Validate(on = {"save"}, field = "subscriptionEnd", label = "Inschrijf einde")
    })
    private Vanencompetition vanencompetition;
    @Validate
    private List<Vanencompetition> vanencompetitions = new ArrayList<Vanencompetition>(); 
    @Validate
    private List<Organisation> organisations = new ArrayList<Organisation>();
    
    @Before(stages = LifecycleStage.BindingAndValidation)
    public void load() {
        user=(User) context.getRequest().getUserPrincipal();
        Organisation o = user.getOrganisation();
        if (getUser().checkRole(Role.SUPERADMIN.name())){
            setVanencompetitions((List<Vanencompetition>) Stripersist.getEntityManager().createQuery("From Vanencompetition").getResultList());
        }else if (o!=null){
            setVanencompetitions((List<Vanencompetition>) Stripersist.getEntityManager().createQuery("from Vanencompetition where organisation = :o")
                    .setParameter("o", o).getResultList());
        } 
    }
    @DefaultHandler
    public Resolution view() {
        return new ForwardResolution(JSP);
    }
    
    public Resolution edit() {        
        if (getUser().checkRole(Role.SUPERADMIN.name())) {
            setOrganisations((List<Organisation>) Stripersist.getEntityManager().createQuery("FROM Organisation").getResultList());
        }else if (getUser().getOrganisation()!=null){
            getOrganisations().add(getUser().getOrganisation());
        }else{
            getContext().getMessages().add(new SimpleError("U heeft geen rechten, of bent geen lid van een organisatie. Hierdoor kan u geen vanencompetitie aanmaken."));
            this.setVanencompetitions(null);
            return new ForwardResolution(JSP); 
        }
        if (vanencompetition == null){
            vanencompetition= new Vanencompetition();
        }
        if (vanencompetition.getOrganisation()==null && getUser().getOrganisation()!=null){
            vanencompetition.setOrganisation(getUser().getOrganisation());
        }
        Stripersist.getEntityManager().getTransaction().commit();
        return new ForwardResolution(JSP);
    }
    
    public Resolution save() {
        if (!this.vanencompetition.isAllowed(this.user)){
            getContext().getMessages().add(new SimpleError("U heeft geen rechten om een vanencompetitie aan te maken voor deze organisatie"));
        }
        if (this.vanencompetition.getDate().compareTo(this.vanencompetition.getSubscriptionEnd()) < 0){
            getContext().getMessages().add(new SimpleError("Letop! De 'stop inschrijvingen' datum is later dan de datum waarop de vanencompetitie plaats vindt"));
        }
        Stripersist.getEntityManager().persist(vanencompetition);
        Stripersist.getEntityManager().getTransaction().commit();
        this.load();
        getContext().getMessages().add(new SimpleMessage("Vanencompetitie is opgeslagen"));
        return new ForwardResolution(JSP);
    }
    
    public Resolution delete() {
        if (!this.vanencompetition.isAllowed(this.user)){
            getContext().getMessages().add(new SimpleError("U heeft geen rechten om een vanencompetitie voor deze organisatie te verwijderen"));
        }
        Stripersist.getEntityManager().remove(vanencompetition);
        Stripersist.getEntityManager().getTransaction().commit();
        this.load();
        getContext().getMessages().add(new SimpleMessage("Vanencompetitie is verwijderd"));
        return new ForwardResolution(JSP);
    }
    
    //<editor-fold defaultstate="collapsed" desc="Getters and Setters">
    /**
     * @return the context
     */
    @Override
    public ActionBeanContext getContext() {
        return context;
    }
    
    /**
     * @param context the context to set
     */
    @Override
    public void setContext(ActionBeanContext context) {
        this.context = context;
    }public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Vanencompetition getVanencompetition() {
        return vanencompetition;
    }

    public void setVanencompetition(Vanencompetition vanencompetition) {
        this.vanencompetition = vanencompetition;
    }

    public List<Vanencompetition> getVanencompetitions() {
        return vanencompetitions;
    }

    public void setVanencompetitions(List<Vanencompetition> vanencompetitions) {
        this.vanencompetitions = vanencompetitions;
    }

    public List<Organisation> getOrganisations() {
        return organisations;
    }

    public void setOrganisations(List<Organisation> organisations) {
        this.organisations = organisations;
    }
    //</editor-fold>

    
}
