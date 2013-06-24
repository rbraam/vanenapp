/**
 * @author C. van Lith
 * @version 1.00 2006/03/03
 *
 * Purpose: a class checking basic authority for users.
 *
 * @copyright 2007 All rights reserved. B3Partners
 */

package com.roybraam.vanenapp.service;

import com.roybraam.vanenapp.entity.User;
import java.security.Principal;
import java.util.List;
import javax.persistence.EntityManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.securityfilter.realm.SecurityRealmInterface;
import org.stripesstuff.stripersist.Stripersist;

public class SecurityRealm implements SecurityRealmInterface {
    private final Log log = LogFactory.getLog(SecurityRealm.class);


    public SecurityRealm() {
    }
    /** Checks wether an user, given his username and password, is allowed to use the system.
     *
     * @param username String representing the username.
     * @param password String representing the password.
     *
     * @return a principal object containing the user if he has been found as a registered user. Otherwise this object wil be empty (null).
     */
    public Principal authenticate(String username, String password) {

        String encpw = null;
        try {
            encpw = Crypter.encryptText(password);
        } catch (Exception ex) {
            log.error("error encrypting password: ", ex);
        }
        try{
            Stripersist.requestInit();
            EntityManager em=Stripersist.getEntityManager();
            try {
                User user=null;
                List<User> foundUsers =em.createQuery(
                        "from User u where " +
                        "lower(u.username) = lower(:username) " +
                        "and u.password = :password")
                        .setParameter("username", username)
                        .setParameter("password", encpw).getResultList();
                if (!foundUsers.isEmpty()){
                    user = foundUsers.get(0);
                }
                if (user==null){
                    foundUsers= em.createQuery(
                        "from User u where " +
                        "lower(u.username) = lower(:username) " +
                        "AND u.password = :password")
                        .setParameter("username", username)
                        .setParameter("password", password)
                        .getResultList();
                    if (!foundUsers.isEmpty()){
                        user=foundUsers.get(0);
                    }
                    if (user!=null){
                        user.setPassword(encpw);
                        em.persist(user);
                    }
                }
                if (user!=null)
                    return user;
                else
                    log.debug("No results using encrypted password");
            } catch (Exception e) {
                log.debug("No results using encrypted password cause: ",e);
            } finally {
                em.getTransaction().commit();
            }
        }finally{
            Stripersist.requestComplete();
        }
        return null;
    }

    public Principal getAuthenticatedPrincipal(String username) {
        EntityManager em = Stripersist.getEntityManager();
        try {
            User user = (User)em.createQuery(
                    "from User u where " +
                    "lower(u.username) = lower(:username) ")
                    .setParameter("username", username)
                    .getSingleResult();
            return user;
        } catch (Exception e) {
            log.error("Error while getting Gebruiker principal",e);
            return null;
        } finally {
            em.getTransaction().commit();
        }

    }

    /** Checks if a user is in the given role. A role represents a level of priviliges.
     *
     * @param principal Principal object representing the user.
     * @param role String representing the role this user has to checked against.
     *
     * @return a boolean which is true if the user is in the defined role otherwise false is returned.
     */
    public boolean isUserInRole(Principal principal, String role) {
        if(!(principal instanceof User)) {
            return false;
        }
        User user = (User)principal;
        //log.info("Check user principal has role");
        return user.checkRole(role);
    }

    public Principal getAuthenticatedPrincipal(String username, String password) {
        return authenticate(username, password);
    }
}