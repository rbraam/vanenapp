package com.roybraam.vanenapp.stripes;

import com.roybraam.vanenapp.entity.Karateka;
import com.roybraam.vanenapp.entity.Kyu;
import java.util.List;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;
import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SimpleMessage;
import net.sourceforge.stripes.action.StreamingResolution;
import net.sourceforge.stripes.action.StrictBinding;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.SimpleError;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.stripesstuff.stripersist.Stripersist;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Roy Braam
 */
@StrictBinding
@UrlBinding("/action/admin/karateka/{$event}")
public class KaratekaActionBean implements ActionBean {

    private static final Log log = LogFactory.getLog(KaratekaActionBean.class);
    private String JSP = "/WEB-INF/jsp/admin/karateka.jsp";
    private String EDITJSP = "/WEB-INF/jsp/admin/karatekaedit.jsp";
    private ActionBeanContext context;
    @Validate(on = {"save"})
    @ValidateNestedProperties({
        @Validate(on = {"save"}, field = "name", required = true, maxlength = 255, label = "Naam"),
        @Validate(on = {"save"}, field = "surname", required = true, maxlength = 255, label = "Achternaam"),
        @Validate(on = {"save"}, field = "insert", maxlength = 255, label = "Tussenvoegsel"),
        @Validate(on = {"save"}, field = "gender", required = true, maxlength = 255, label = "Geslacht"),
        @Validate(on = {"save"}, field = "birthdate", required = true, maxlength = 255, label = "Geboortedatum"),
        @Validate(on = {"save"}, field = "weight", label = "Gewicht"),
        @Validate(on = {"save"}, field = "basePointsKata", label = "Basis punten kata"),
        @Validate(on = {"save"}, field = "basePointsKumite", label = "Basis punten kumite"),
        @Validate(on = {"save"}, field = "memberNumber", label = "KBN lidmaatschap nummer"),
        @Validate(on = {"save"}, field = "emailAdress", label = "E-mail adres")
    })
    private Karateka karateka;
    @Validate(on = {"save"}, required = true, label = "Kyu-graad")
    private String belt;

    @DefaultHandler
    public Resolution view() {
        return new ForwardResolution(JSP);
    }

    public Resolution edit() {
        if (karateka != null) {
            this.belt = karateka.getBelt().name();
        }
        return new ForwardResolution(EDITJSP);
    }

    public Resolution save() {
        karateka.setBelt(Kyu.valueOf(this.belt));
        Stripersist.getEntityManager().persist(karateka);
        Stripersist.getEntityManager().getTransaction().commit();
        getContext().getMessages().add(new SimpleMessage("Karateka is opgeslagen"));
        return new ForwardResolution(EDITJSP);
    }

    public Resolution delete() {
        if (this.karateka != null) {
            EntityManager em = Stripersist.getEntityManager();
            Boolean remove=true;
            if ((this.karateka.getBasePointsKata()!=null && this.karateka.getBasePointsKata()> 0) ||
                    (this.karateka.getBasePointsKumite()!=null && this.karateka.getBasePointsKumite()>0)){
                remove = false;
            }
            if (remove){
                Long count = (Long) em.createQuery("select count(*) from Participant where karateka = :k and points > 0").setParameter("k", this.karateka).getSingleResult();
                if (count > 0){
                    remove = false;
                }
            }
            if (remove){
                em.remove(karateka);
                getContext().getMessages().add(new SimpleMessage("Karateka is verwijderd"));
            }else{
                getContext().getMessages().add(new SimpleError("Karateka kan (nog) niet verwijderd worden omdat deze al punten heeft behaald."));
            }
            em.getTransaction().commit();
        }
        return new ForwardResolution(EDITJSP);
    }

    public Resolution cancel() {
        return new ForwardResolution(EDITJSP);
    }

    public Resolution list() {
        List<Karateka> karatekas = Stripersist.getEntityManager().createQuery("FROM Karateka order by surname,name").getResultList();
        final JSONArray array = new JSONArray();
        for (Karateka k : karatekas) {
            try {
                JSONObject jsonKarateka = k.toJSON();
                array.put(jsonKarateka);
            } catch (Exception e) {
                log.warn("Failed to make JSONObject of karateka with id: " + k.getId());
            }
        }
        return new StreamingResolution("application/json") {
            @Override
            public void stream(HttpServletResponse response) throws Exception {
                response.getWriter().print(array.toString());
            }
        };
    }

    //<editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public ActionBeanContext getContext() {
        return context;
    }

    public void setContext(ActionBeanContext context) {
        this.context = context;
    }

    public Karateka getKarateka() {
        return karateka;
    }

    public void setKarateka(Karateka karateka) {
        this.karateka = karateka;
    }

    public String getBelt() {
        return belt;
    }

    public void setBelt(String belt) {
        this.belt = belt;
    }
    //</editor-fold>
}
