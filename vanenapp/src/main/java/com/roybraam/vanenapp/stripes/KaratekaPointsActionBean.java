/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.roybraam.vanenapp.stripes;

import com.roybraam.vanenapp.entity.Category;
import com.roybraam.vanenapp.entity.Karateka;
import com.roybraam.vanenapp.entity.Participant;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StrictBinding;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Roy Braam
 */
@StrictBinding
@UrlBinding("/action/karatekapoints/{$event}")
public class KaratekaPointsActionBean implements ActionBean {

    private static final Log log = LogFactory.getLog(KaratekaPointsActionBean.class);
    private static final String JSP = "/WEB-INF/jsp/main/karatekaPoints.jsp";
    public static final String DEFAULT_SALT = "16f26f9d5e09ddac2a8c468d22a2c781";
    
    protected static final String PARTICIPANTPOINTS_SALT_PARAM = "participantpoints.salt";
    
    private ActionBeanContext context;
    @Validate(required = true)
    private Participant p;
    @Validate(required = true)
    private String code;
    @Validate
    private Karateka karateka;
    
    @Validate
    private Integer points;
    
    @Validate
    private Integer category;
    
    public Resolution view() throws UnsupportedEncodingException {
        String checkCode = generateCode(p, this.getContext());
        if (code==null || !code.equals(checkCode)){
            throw new SecurityException("Geen toegang. De code is onjuist");
        }
        
        Map.Entry<Integer,Category> points = PrintCertificateActionBean.calculateCertPoints(p);
        
        this.karateka = p.getKarateka();
        
        this.points = points.getKey();
        this.category = points.getValue().getId();
        
        return new ForwardResolution(JSP);
    }

    /**
     * Generate unique code
     */
    public static String generateCode(Participant p, ActionBeanContext context) {
        String salt = context.getServletContext().getInitParameter(PARTICIPANTPOINTS_SALT_PARAM);
        if (salt == null){
            log.warn("The '"+PARTICIPANTPOINTS_SALT_PARAM+ "' is not configured as context param. Using the insecure, default salt");
            salt = DEFAULT_SALT;
        }
        StringBuffer sb = new StringBuffer();
        sb.append(p.getKarateka().getName());
        sb.append(p.getKarateka().getSurname());
        sb.append(p.getVanencompetition().getDate());

        String hash = Md5Crypt.apr1Crypt(sb.toString(), salt);

        return hash;
    }

    //<editor-fold defaultstate="collapsed" desc="Getters/setters">
    public ActionBeanContext getContext() {
        return context;
    }

    public void setContext(ActionBeanContext context) {
        this.context = context;
    }

    public Participant getP() {
        return p;
    }

    public void setP(Participant p) {
        this.p = p;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Karateka getKarateka() {
        return karateka;
    }

    public void setKarateka(Karateka karateka) {
        this.karateka = karateka;
    }
}
    //</editor-fold>
