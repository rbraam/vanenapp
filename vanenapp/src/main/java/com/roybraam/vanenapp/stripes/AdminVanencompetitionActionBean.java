package com.roybraam.vanenapp.stripes;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StrictBinding;
import net.sourceforge.stripes.action.UrlBinding;

/**
 *
 * @author Roy Braam
 */
@StrictBinding
@UrlBinding("/action/admin/vanencompetition/{$event}")
public class AdminVanencompetitionActionBean implements ActionBean{
    private ActionBeanContext context;
    private static final String JSP = "/WEB-INF/jsp/admin/vanencompetition.jsp";

    @DefaultHandler
    public Resolution view() {
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
    }
    //</editor-fold>
}
