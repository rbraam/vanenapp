/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.roybraam.vanenapp.stripes;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
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
@UrlBinding("/action/admin/list/{$event}")
public class ListActionBean extends OrganizeVanencompetitionActionBean {

    private static final Log log = LogFactory.getLog(ListActionBean.class);
    private String JSP = "/WEB-INF/jsp/admin/showList.jsp";
    private String LIST_JSP = "/WEB-INF/jsp/admin/list.jsp";
    @Validate
    private List resultList = new ArrayList();

    @DefaultHandler
    public Resolution view() {
        if (this.getVanencompetition() == null) {
            setNoVanencompetitionMessage();
            return this.getChooseVanencompetitionResolution();
        }
        return new ForwardResolution(JSP);
    }

    public Resolution listParticipantsWithoutPoule() {
        return list("from Participant where vanencompetition = :v and poule is null");
    }

    public Resolution listParticipants() {
        return list("from Participant where vanencompetition= :v");
    }

    public Resolution listParticipantsSortByBelt() {
        return list("from Participant where vanencompetition= :v order by karateka.belt");
    }

    public Resolution listParticipantsSortByAge() {
        return list("from Participant where vanencompetition= :v order by karateka.birthdate");
    }

    public Resolution listParticipantsSortByBeltAge() {
        return list("from Participant where vanencompetition= :v order by karateka.belt,karateka.birthdate");
    }

    public Resolution listParticipantsSortByWeight() {
        return list("from Participant where vanencompetition= :v order by karateka.weight");
    }

    public Resolution listParticipantsSortByAgeWeight() {
        return list("from Participant where vanencompetition= :v order by karateka.birthdate,karateka.weight");
    }

    public Resolution listPoules() {
        return list("from Poule where vanencompetition = :v order by startKyu");
    }

    private Resolution list(String query) {
        if (this.getVanencompetition() == null) {
            setNoVanencompetitionMessage();
            return this.getChooseVanencompetitionResolution();
        }
        EntityManager em = Stripersist.getEntityManager();
        this.resultList = em.createQuery(query).setParameter("v", this.getVanencompetition()).getResultList();
        return new ForwardResolution(LIST_JSP);
    }

    public List getResultList() {
        return resultList;
    }

    public void setResultList(List resultList) {
        this.resultList = resultList;
    }
}
