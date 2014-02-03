/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.roybraam.vanenapp.stripes;

import com.roybraam.vanenapp.entity.Participant;
import com.roybraam.vanenapp.entity.Poule;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StrictBinding;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import com.roybraam.vanenapp.entity.Category;
import com.roybraam.vanenapp.entity.CompetitionType;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.Date;
import java.util.Locale;
import java.util.Map.Entry;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.StreamingResolution;
import net.sourceforge.stripes.util.UrlBuilder;
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
public class PrintCertificateActionBean extends OrganizeVanencompetitionActionBean{

    private static final Log log = LogFactory.getLog(PrintCertificateActionBean.class);
    private static final String ERRORJSP = "/WEB-INF/jsp/error.jsp";
    private static final String JSP = "/WEB-INF/jsp/admin/printCertificate.jsp";
    @Validate
    private List<Poule> poules = new ArrayList<Poule>();
    @Validate
    private List<Participant> participants = new ArrayList<Participant>();
    @Validate
    private boolean qr = false;

    private static BaseFont LUCIDA_FONT;
    
    static{
        try {
            LUCIDA_FONT = BaseFont.createFont(PrintCertificateActionBean.class.getResource("/fonts/Lucida.ttf").getFile(), BaseFont.WINANSI , BaseFont.EMBEDDED);
        } catch (Exception ex) {
            LUCIDA_FONT = null;
            log.error("Error while creating fonts",ex);
        }
    }
    
    @DefaultHandler
    public Resolution list(){
        if (this.getVanencompetition() == null) {
            setNoVanencompetitionMessage();
            return this.getChooseVanencompetitionResolution();
        }
        
        EntityManager em = Stripersist.getEntityManager();
        this.poules = em.createQuery("FROM Poule where vanencompetition = :v").setParameter("v",this.getVanencompetition()).getResultList();
        this.participants = em.createQuery("FROM Participant where vanencompetition = :v and poule = null")
                .setParameter("v",this.getVanencompetition()).getResultList();
        
        return new ForwardResolution(JSP);
    }
    
    public Resolution print() {
        Exception e = null;
        if (!this.participants.isEmpty()) {
        } else if (!this.poules.isEmpty()) {
            for (Poule poule : this.poules) {
                this.participants.addAll(poule.getParticipants());
            }
        } else if (this.getVanencompetition()!=null) {
            this.participants.addAll(this.getVanencompetition().getParticipants());
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            //create itext doc
            final Document doc = new Document(PageSize.A4.rotate());

            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter writer = PdfWriter.getInstance(doc, baos);

            doc.open();
            if (!participants.isEmpty()) {
                this.participants = this.removeDuplicates(this.participants);
                EntityManager em = Stripersist.getEntityManager();
                String baseUrl = this.getBaseUrl();
                for (Participant participant : participants) {
                    //A4		 595x842
                    //name
                    this.addText(participant.getKarateka().getFullName(),400,190,LUCIDA_FONT,32,writer);
                    
                    Entry<Integer,Category> certPoints = calculateCertPoints(participant);
                    Integer points = certPoints.getKey();
                    String category = certPoints.getValue().getName();
                    if (CompetitionType.KATA.equals(participant.getType())){
                        category+=" Kata";
                    }else{
                        category+=" Kumite";
                    }
                    
                    this.addText(""+points,295,95,20,writer);
                    //category                    
                    this.addText(category,464,440,24,writer);
                    //date
                    Date date = participant.getVanencompetition().getDate();
                    String stringDate=sdf.format(date);
                    this.addText(stringDate,780,25,8,writer);
                    //start points
                    this.addText("10",470,95,20,writer);

                    if (participant.getPoints() != null && participant.getPoints() >= 10) {
                        int newPoints = points + participant.getPoints();
                        this.addText("" + (participant.getPoints() - 10), 345, 52, 20, writer);
                        this.addText("" + newPoints, 610, 52, 20, writer);
                    }
                    
                    if (this.getQr()) {
                        String url = createParticipantUrl(participant, baseUrl);
                        BarcodeQRCode qrcode = new BarcodeQRCode(url, 20, 20, null);
                        Image img = qrcode.getImage();
                        img.setAbsolutePosition(25, 25);
                        doc.add(img);
                    }
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
        getContext().getMessages().add(new SimpleError("Het is wegens een fout niet mogelijk om de certificaten te printen. Neem contact op met de beheerder. Fout: "+e));
        return new ForwardResolution(ERRORJSP);
    }

    private void addText(String text, int x, int y, BaseFont font,int fontSize,PdfWriter writer) throws DocumentException, IOException {
        PdfContentByte cb = writer.getDirectContent();
        cb.saveState();
        cb.beginText();
        cb.setFontAndSize(font, fontSize);
        float n = 0;
        cb.showTextAligned(PdfContentByte.ALIGN_CENTER, text, x, y, n);
        cb.endText();
        cb.restoreState();
    }
    private void addText(String text, int x, int y, int fontSize,PdfWriter writer) throws DocumentException, IOException {
        this.addText(text, x, y, BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1250, BaseFont.NOT_EMBEDDED),fontSize, writer);
    }
    
    /**
     * When points get to 700. The points are back to 0 and the points above 700 are lost.
     * The karateka starts with 0 in the new category
     * @param participant the participant
     * @param category String is filled with the category which corresponds with the points this
     * karateka has.
     * @return The points that can be printed on the certificate
     */
    public static Entry<Integer,Category> calculateCertPoints(Participant participant){
         //points
        List<Integer> pointsList= Stripersist.getEntityManager()
                .createQuery("select points from Participant where points is not null AND karateka = :k AND type = :t AND vanencompetition.date < :d order by vanencompetition.date")
                .setParameter("k",participant.getKarateka())
                .setParameter("t",participant.getType())
                .setParameter("d",participant.getVanencompetition().getDate())
                .getResultList();
        Integer points = 0;
        if (CompetitionType.KATA.equals(participant.getType()) &&
                participant.getKarateka().getBasePointsKata()!=null){
            points=participant.getKarateka().getBasePointsKata();
        }else if (CompetitionType.KUMITE.equals(participant.getType()) &&
                participant.getKarateka().getBasePointsKumite()!=null){
            points=participant.getKarateka().getBasePointsKumite();
        }
        
        Integer c =0;
        //if more then factor 700
        if (points >=700){
            c = new Double(Math.floor(points/700)).intValue();
            points = points%700;
        }
        for (Integer p : pointsList){
            if (p!=null){
                points+=p;
                if (points >=700){
                    points=0;
                    c++;
                }
            }
        }
        Category category = Category.values()[c];        
        return new AbstractMap.SimpleEntry<Integer, Category>(points,category);
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

    //<editor-fold defaultstate="collapsed" desc="Getters and setters">
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

    public boolean getQr() {
        return qr;
    }

    public void setQr(boolean qr) {
        this.qr = qr;
    }

}
//</editor-fold>
