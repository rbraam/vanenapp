/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.roybraam.vanenapp.stripes;

import com.roybraam.vanenapp.entity.Participant;
import java.util.ArrayList;
import java.util.List;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StrictBinding;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;

/**
 *
 * @author Roy Braam
 */
@StrictBinding
@UrlBinding("/action/admin/participant/{$event}")
public class ParticipantActionBean extends OrganizeVanencompetitionActionBean{

    private static final Log log = LogFactory.getLog(ParticipantActionBean.class);
    private static final String JSP = "/WEB-INF/jsp/admin/participant.jsp";
    @Validate
    private List<Integer> participants = new ArrayList<Integer>();
    
    private String participantsJson = "";
    @DefaultHandler
    public Resolution view(){
        if (this.getVanencompetition()==null){
            setNoVanencompetitionMessage();
            return this.getChooseVanencompetitionResolution();
        }
        this.participants = new ArrayList<Integer>();
        JSONArray participantsArray = new JSONArray();
        for (Participant p : this.vanencompetition.getParticipants()){
            if (p.getKarateka()!=null){
                try{    
                    participantsArray.put(p.getKarateka().toJSON());
                }catch (Exception e){
                    log.error("Failed to get Karateka",e);
                }
            }
        }
        this.participantsJson = participantsArray.toString();
        return new ForwardResolution(JSP);
    }

    public List<Integer> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Integer> participants) {
        this.participants = participants;
    }

    public String getParticipantsJson() {
        return participantsJson;
    }

    public void setParticipantsJson(String participantsJson) {
        this.participantsJson = participantsJson;
    }
}
