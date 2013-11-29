package com.roybraam.vanenapp.stripes;

import com.roybraam.vanenapp.entity.Organisation;
import java.util.List;
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
import net.sourceforge.stripes.validation.EmailTypeConverter;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import org.stripesstuff.stripersist.Stripersist;

/**
 *
 * @author Roy Braam
 */
@StrictBinding
@UrlBinding("/action/admin/organisation/{$event}")
public class OrganisationActionBean implements ActionBean {
    
    private static final String JSP = "/WEB-INF/jsp/admin/organisation.jsp";
    private ActionBeanContext context;
    private List<Organisation> organisations;
    
    @Validate(on = {"save"})   
    @ValidateNestedProperties({
        @Validate(on = {"save"}, field = "name", required = true, maxlength = 255, label = "Naam"),
        @Validate(on = {"save"}, field = "phoneNumber", maxlength = 20, label = "Telefoonnummer"),
        @Validate(on = {"save"}, field = "emailAdress", converter=EmailTypeConverter.class, maxlength = 255, label = "E-mail adres")
    })
    private Organisation organisation;
    
    @Before(stages = LifecycleStage.BindingAndValidation)
    public void load() {
        organisations = Stripersist.getEntityManager().createQuery("from Organisation").getResultList();
    }
    
    @DefaultHandler
    public Resolution view(){
        return new ForwardResolution(JSP);
    }
    
    public Resolution edit(){
        return new ForwardResolution(JSP);
    }

    public Resolution save() {
        Stripersist.getEntityManager().persist(getOrganisation());
        Stripersist.getEntityManager().getTransaction().commit();
        this.load();
        getContext().getMessages().add(new SimpleMessage("Organisatie is opgeslagen"));
        return new ForwardResolution(JSP);
    }
    public Resolution cancel() {
        return new ForwardResolution(JSP);
    }

    public Resolution delete() {
        Stripersist.getEntityManager().remove(this.getOrganisation());
        Stripersist.getEntityManager().getTransaction().commit();
        this.load();
        getContext().getMessages().add(new SimpleMessage("Organisatie is verwijderd"));
        return new ForwardResolution(JSP);
    }

    
    //<editor-fold defaultstate="collapsed" desc="Getters and Setters">
    @Override
    public ActionBeanContext getContext() {
        return context;
    }
    
    @Override
    public void setContext(ActionBeanContext context) {
        this.context = context;
    }
    public List<Organisation> getOrganisations() {
        return organisations;
    }

    public void setOrganisations(List<Organisation> organisations) {
        this.organisations = organisations;
    }
    
    public Organisation getOrganisation() {
        return organisation;
    }

    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }
    //</editor-fold>


}
