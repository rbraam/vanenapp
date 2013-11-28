package com.roybraam.vanenapp.stripes;

import com.roybraam.vanenapp.entity.Vanencompetition;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StrictBinding;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import org.stripesstuff.stripersist.Stripersist;

/**
 *
 * @author Roy Braam
 */
@StrictBinding
@UrlBinding("/action/index/{$event}")
public class IndexActionBean implements ActionBean{
    private ActionBeanContext context;
    private static final String JSP = "/WEB-INF/jsp/index.jsp";
    
    @Validate
    private List<Vanencompetition> vanencompetitions = new ArrayList();

    @DefaultHandler
    public Resolution view() {
        Date now = new Date();
        this.vanencompetitions=Stripersist.getEntityManager().createQuery("FROM Vanencompetition where date >= :n").setParameter("n", now).getResultList();
        return new ForwardResolution(JSP);
    }
    
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
    }

    public List<Vanencompetition> getVanencompetitions() {
        return vanencompetitions;
    }

    public void setVanencompetitions(List<Vanencompetition> vanencompetitions) {
        this.vanencompetitions = vanencompetitions;
    }
}
