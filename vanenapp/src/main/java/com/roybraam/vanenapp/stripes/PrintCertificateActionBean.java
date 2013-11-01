/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.roybraam.vanenapp.stripes;

import com.roybraam.vanenapp.entity.Participant;
import com.roybraam.vanenapp.entity.Poule;
import com.roybraam.vanenapp.entity.Vanencompetition;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StrictBinding;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import com.roybraam.vanenapp.entity.CompetitionType;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.EntityManager;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.StreamingResolution;
import net.sourceforge.stripes.validation.SimpleError;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.stripesstuff.stripersist.Stripersist;

/**
 *
 * @author Roy Braam
 */
@StrictBinding
@UrlBinding("/action/admin/printcertificate/{$event}")
public class PrintCertificateActionBean implements ActionBean {

    private static final Log log = LogFactory.getLog(PrintCertificateActionBean.class);
    private static final String ERRORJSP = "/WEB-INF/jsp/error.jsp";
    private ActionBeanContext context;
    @Validate
    private Vanencompetition vanencompetition;
    @Validate
    private List<Poule> poules = new ArrayList<Poule>();
    @Validate
    private List<Participant> participants = new ArrayList<Participant>();

    public Resolution print() {
        Exception e = null;
        if (!this.participants.isEmpty()) {
        } else if (!this.poules.isEmpty()) {
            for (Poule poule : this.poules) {
                this.participants.addAll(poule.getParticipants());
            }
        } else if (this.vanencompetition != null) {
            this.participants.addAll(this.vanencompetition.getParticipants());
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-ykyyy");
            //create itext doc
            final Document doc = new Document(PageSize.A4.rotate());

            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter writer = PdfWriter.getInstance(doc, baos);

            doc.open();
            if (!participants.isEmpty()) {
                this.participants = this.removeDuplicates(this.participants);
                EntityManager em = Stripersist.getEntityManager();
                for (Participant participant : participants) {
                    //A4		 595x842
                    //name
                    this.addText(participant.getKarateka().getFullName(),421,205,32,writer);
                    //points
                    Long points = (Long) em.createQuery("select sum(points) from Participant where karateka = :k").setParameter("k", participant.getKarateka()).getSingleResult();
                    if (points == null) {
                        points = 0l;
                    }
                    if (participant.getKarateka().getBasePoints() != null) {
                        points += participant.getKarateka().getBasePoints();
                    }
                    Long certificatePoints = calculateCertPoints(points);
                    this.addText(""+points,300,115,20,writer);
                    //category
                    String category = this.calculateCategory(points,participant);
                    this.addText(category,464,425,24,writer);
                    //date
                    Date date = participant.getVanencompetition().getDate();
                    String stringDate=sdf.format(date);
                    this.addText(stringDate,421,195,8,writer);
                    //start points
                    this.addText("10",475,115,20,writer);
                    doc.newPage();
                }
                doc.close();
            }

            //close doc
            return new StreamingResolution("application/pdf") {
                @Override
                public void stream(HttpServletResponse response) throws Exception {
                    OutputStream os = response.getOutputStream();
                    baos.writeTo(os);
                    baos.close();
                    os.flush();
                    os.close();
                }
            };
        } catch (Exception ex) {
            e = ex;
            log.error("Fout tijdens printen van Certificaten: ", e);
        }
        getContext().getMessages().add(new SimpleError("Het is wegens een fout niet mogelijk om de certificaten te printen. Neem contact op met de beheerder. Fout: "+e.getMessage()));
        return new ForwardResolution(ERRORJSP);
    }

    private void addText(String text, int x, int y, int fontSize,PdfWriter writer) throws DocumentException, IOException {
        PdfContentByte cb = writer.getDirectContent();
        BaseFont bf = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1250, BaseFont.NOT_EMBEDDED);
        cb.saveState();
        cb.beginText();
        cb.setFontAndSize(bf, fontSize);
        float n = 0;
        cb.showTextAligned(PdfContentByte.ALIGN_CENTER, text, x, y, n);
        cb.endText();
        cb.restoreState();
    }
    
    private String calculateCategory(Long points, Participant p){
        String categorie =  "Categorie Super Vaan";
        if(points <= 700){
            return "Categorie C";
        }else if (points <=1400){
            return "Categorie B";
        }else if (points <=2100){
            return "Categorie A";
        }
        if (CompetitionType.KATA.equals(p.getType())){
            categorie+=" Kata";
        }else{
            categorie+=" Kumite";
        }
        return categorie;
    }

    private Long calculateCertPoints(Long points){
        if (points >= 700){
            return calculateCertPoints(points -700);
        }
        return points;
    }
        
    private List<Participant> removeDuplicates(List<Participant> list) {
        List<Participant> newList = new ArrayList<Participant>();
        for (Participant p : list) {
            if (!newList.contains(p)) {
                newList.add(p);
            }
        }
        return newList;
    }
    
    public static void main (String[] args){
        
        float height=PageSize.A4.getHeight();
        float width= PageSize.A4.getWidth();
        System.out.println(height+"x"+width);
    }

    //<editor-fold defaultstate="collapsed" desc="Getters and setters">
    @Override
    public void setContext(ActionBeanContext context) {
        this.context = context;
    }

    @Override
    public ActionBeanContext getContext() {
        return context;
    }

    public Vanencompetition getVanencompetition() {
        return vanencompetition;
    }

    public void setVanencompetition(Vanencompetition vanencompetition) {
        this.vanencompetition = vanencompetition;
    }

    public List<Poule> getPoules() {
        return poules;
    }

    public void setPoules(List<Poule> poules) {
        this.poules = poules;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }

}
//</editor-fold>
