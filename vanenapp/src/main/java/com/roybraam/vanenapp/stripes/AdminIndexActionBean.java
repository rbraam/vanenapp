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
@UrlBinding("/action/admin/{$event}")
public class AdminIndexActionBean implements ActionBean{
    private ActionBeanContext context;
    private static final String JSP = "/WEB-INF/jsp/admin/index.jsp";
    

    @DefaultHandler
    public Resolution view() {
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
}
