/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.roybraam.vanenapp.stripes;

import com.roybraam.vanenapp.entity.CompetitionType;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
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
    @Validate
    private String competitionType = null;
    @Validate
    private String orderBy=null;
    
    @DefaultHandler
    public Resolution view() {
        if (this.getVanencompetition() == null) {
            setNoVanencompetitionMessage();
            return this.getChooseVanencompetitionResolution();
        }
        return new ForwardResolution(JSP);
    }

    public Resolution listParticipantsWithoutPoule() {
        return list("from Participant where vanencompetition = :v and poule is null","type,karateka.surname,karateka.name");
    }

    public Resolution listParticipants() {
        return list("from Participant where vanencompetition= :v","type,karateka.surname,karateka.name");
    }

    public Resolution listParticipantsSortByBelt() {
        return list("from Participant where vanencompetition= :v","type,karateka.belt desc,karateka.surname,karateka.name");
    }

    public Resolution listParticipantsSortByAge() {
        return list("from Participant where vanencompetition= :v","type,karateka.birthdate");
    }

    public Resolution listParticipantsSortByBeltAge() {
        return list("from Participant where vanencompetition= :v","type,karateka.belt desc,karateka.birthdate");
    }

    public Resolution listParticipantsSortByWeight() {
        return list("from Participant where vanencompetition= :v","type,karateka.weight");
    }

    public Resolution listParticipantsSortByAgeWeight() {
        return list("from Participant where vanencompetition= :v","type,karateka.birthdate,karateka.weight");
    }

    public Resolution listPoules() {
        return list("from Poule where vanencompetition = :v","type,startKyu");
    }
    /**
     * Execute query and return in the result list of the resolution
     * @param query the from - where statement
     * @param orderBy comma seperated list of columns for order by part
     * @return Resolution, the result list is filled.
     */
    private Resolution list(String query,String orderBy) {
        if (this.getVanencompetition() == null) {
            setNoVanencompetitionMessage();
            return this.getChooseVanencompetitionResolution();
        }
        CompetitionType ct = null;
        if(this.competitionType!=null){
            ct=CompetitionType.valueOf(this.competitionType);
        }
        if(CompetitionType.KATA.equals(ct) ||
                CompetitionType.KUMITE.equals(ct)){
            query+=" and type = :t";
        }
        EntityManager em = Stripersist.getEntityManager();
        if (this.orderBy!=null){
            if (orderBy ==null){
                orderBy=this.orderBy;
            }else{
                orderBy=this.orderBy+","+orderBy;
            }
        }
        if (orderBy!=null){
            query+=" order by "+orderBy;
        }
        Query q = em.createQuery(query);
        if(CompetitionType.KATA.equals(ct) ||
                CompetitionType.KUMITE.equals(ct)){
            q.setParameter("t", ct);
        }
        q.setParameter("v", this.getVanencompetition()).getResultList();
        this.resultList = q.getResultList();
        return new ForwardResolution(LIST_JSP);
    }

    public List getResultList() {
        return resultList;
    }

    public void setResultList(List resultList) {
        this.resultList = resultList;
    }

    public String getCompetitionType() {
        return competitionType;
    }

    public void setCompetitionType(String competitionType) {
        if (competitionType!=null){
            this.competitionType = competitionType.toUpperCase();
        }else{
            this.competitionType = competitionType;
        }
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
}
