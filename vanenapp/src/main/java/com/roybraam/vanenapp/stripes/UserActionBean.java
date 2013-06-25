/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.roybraam.vanenapp.stripes;

import com.roybraam.vanenapp.entity.Organisation;
import com.roybraam.vanenapp.entity.Role;
import com.roybraam.vanenapp.entity.User;
import java.util.HashSet;
import java.util.Iterator;
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
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import org.stripesstuff.stripersist.Stripersist;

/**
 *
 * @author Roy Braam
 */
@StrictBinding
@UrlBinding("/action/admin/user/{$event}")
public class UserActionBean implements ActionBean {

    private static final String JSP = "/WEB-INF/jsp/admin/user.jsp";
    private ActionBeanContext context;
    private List<User> users;
    private List<Organisation> organisations;
    @Validate(on = {"save"})
    @ValidateNestedProperties({
        @Validate(on = {"save"}, field = "name", required = true, maxlength = 255, label = "Naam"),
        @Validate(on = {"save"}, field = "username", required = true, maxlength = 255, label = "Gebruikersnaam"),
        @Validate(on = {"save"}, field = "password", required = true, maxlength = 255, label = "Wachtwoord")
    })
    private User user;
    @Validate
    private Set<String> roles;

    @Before(stages = LifecycleStage.BindingAndValidation)
    public void load() {
        users = Stripersist.getEntityManager().createQuery("from User").getResultList();
    }

    @DefaultHandler
    public Resolution view() {
        return new ForwardResolution(JSP);
    }

    public Resolution save() {
        if (roles!=null) {
            Set<Role> newRoles = new HashSet<Role>();
            for (String role : this.roles){                
                newRoles.add(Role.valueOf(role));
            }
            user.setRoles(newRoles);
        }else{
            user.setRoles(null);
        }
        Stripersist.getEntityManager().persist(user);
        Stripersist.getEntityManager().getTransaction().commit();
        this.load();
        getContext().getMessages().add(new SimpleMessage("Gebruiker is opgeslagen"));
        return new ForwardResolution(JSP);
    }

    public Resolution edit() {
        setOrganisations((List<Organisation>) Stripersist.getEntityManager().createQuery("From Organisation").getResultList());
        Stripersist.getEntityManager().getTransaction().commit();
        return new ForwardResolution(JSP);
    }

    public Resolution cancel() {
        return new ForwardResolution(JSP);
    }

    public Resolution delete() {
        Stripersist.getEntityManager().remove(user);
        Stripersist.getEntityManager().getTransaction().commit();
        this.load();
        getContext().getMessages().add(new SimpleMessage("Gebruiker is verwijderd"));
        return new ForwardResolution(JSP);
    }

    //<editor-fold defaultstate="collapsed" desc="Getters and setters">
    /**
     * @return the context
     */
    public ActionBeanContext getContext() {
        return context;
    }

    /**
     * @param context the context to set
     */
    public void setContext(ActionBeanContext context) {
        this.context = context;
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return the users
     */
    public List<User> getUsers() {
        return users;
    }

    /**
     * @param users the users to set
     */
    public void setUsers(List<User> users) {
        this.users = users;
    }

    /**
     * @return the organisations
     */
    public List<Organisation> getOrganisations() {
        return organisations;
    }

    /**
     * @param organisations the organisations to set
     */
    public void setOrganisations(List<Organisation> organisations) {
        this.organisations = organisations;
    }
    //</editor-fold>

    /**
     * @return the roles
     */
    public Set<String> getRoles() {
        return roles;
    }

    /**
     * @param roles the roles to set
     */
    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
