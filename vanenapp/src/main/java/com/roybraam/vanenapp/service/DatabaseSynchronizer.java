package com.roybraam.vanenapp.service;

import com.roybraam.vanenapp.entity.Metadata;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import javax.persistence.EntityManager;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.jdbc.Work;
import org.stripesstuff.stripersist.Stripersist;

/**
 *
 * @author Roy Braam
 */
public class DatabaseSynchronizer implements Servlet {

    private static final Log log = LogFactory.getLog(DatabaseSynchronizer.class);
    private static final LinkedHashMap<String, List<File>> updates = new LinkedHashMap<String, List<File>>();
    private static final String SCRIPT_PATH="";

    private ServletConfig sc;
    static {
        try{
            updates.put("init", new ArrayList<File>());
            updates.put("0", new ArrayList<File>());
            updates.get("0").add(new File(DatabaseSynchronizer.class.getResource("/scripts/vanenapp_schema.sql").getFile()));
            updates.get("0").add(new File(DatabaseSynchronizer.class.getResource("/scripts/01_init-data.sql").getFile()));

            updates.put("0.1", new ArrayList<File>());
            updates.get("0.1").add(new File(DatabaseSynchronizer.class.getResource("/scripts/02_rename-karateka-birthdate.sql").getFile()));

            updates.put("0.2", new ArrayList<File>());
            updates.get("0.2").add(new File(DatabaseSynchronizer.class.getResource("/scripts/03_add_to_poule.sql").getFile()));

            updates.put("0.3", new ArrayList<File>());
            updates.get("0.3").add(new File(DatabaseSynchronizer.class.getResource("/scripts/04_add_weight_to_poule.sql").getFile()));

            updates.put("0.5", new ArrayList<File>());
            updates.get("0.5").add(new File(DatabaseSynchronizer.class.getResource("/scripts/05_add_base_points.sql").getFile()));

            updates.put("0.6", new ArrayList<File>());
            updates.get("0.6").add(new File(DatabaseSynchronizer.class.getResource("/scripts/06_add_base_points_kumite.sql").getFile()));

            updates.put("0.7", new ArrayList<File>());
            updates.get("0.7").add(new File(DatabaseSynchronizer.class.getResource("/scripts/07_add_member_number.sql").getFile()));

            updates.put("0.8", new ArrayList<File>());
            updates.get("0.8").add(new File(DatabaseSynchronizer.class.getResource("/scripts/08_add_poule_gender.sql").getFile()));

            updates.put("0.9", new ArrayList<File>());
            updates.get("0.9").add(new File(DatabaseSynchronizer.class.getResource("/scripts/09_add_contactinfo_org.sql").getFile()));

            updates.put("0.10", new ArrayList<File>());
            updates.get("0.10").add(new File(DatabaseSynchronizer.class.getResource("/scripts/10_add_email_karateka.sql").getFile()));
        }catch(Exception e){
            log.error("Error initializing DatabaseSynchronizer",e);
        }
    }

    public void doInit(){
        try {
            //System.out.println(getClass().getClassLoader().getResource(".").getPath());
            checkScriptDir();
            Stripersist.requestInit();
            EntityManager em = Stripersist.getEntityManager();
            if (em!=null){
                Session session = em.unwrap(Session.class);
                Transaction trans=session.beginTransaction();
                LinkedHashMap<String, List<File>> scripts = new LinkedHashMap<String, List<File>>();
                Metadata mdVersion = null;
                //check if any db exists
                try {
                    List<Metadata> metadata = em.createQuery("From Metadata where configKey = :v").setParameter("v", Metadata.VERSION_KEY).getResultList();
                    String version = "init";
                    if (!metadata.isEmpty()) {
                        mdVersion = metadata.get(0);
                        version = mdVersion.getConfigValue();
                    }else{
                        log.info("Database already initialized but not valid. Try to execute scripts again");
                    }
                    scripts = getUpdates(version);
                } catch (Exception e) {
                    log.info("No correct database, run init scripts");
                    scripts.put("0",updates.get("0"));
                }
                if (scripts.isEmpty()){
                    log.info("Database is up to date. No need for running update scripts");
                }else{
                    ScriptWorker w = new ScriptWorker(scripts);
                    session.doWork(w);
                    if (w.isErrored()){
                        log.info("Database updates returned a error.");
                    }
                    String updatedVersion = w.getLatestSuccesVersion();
                    if (updatedVersion!=null){
                        if (mdVersion==null){
                            mdVersion = new Metadata();
                            mdVersion.setConfigKey(Metadata.VERSION_KEY);
                        }
                        if (updatedVersion.equals("0")){
                            //if version ==0 the database is created with a schema, so its on the latest version
                            updatedVersion=(String)updates.keySet().toArray()[updates.size()-1];
                        }
                        mdVersion.setConfigValue(updatedVersion);
                        em.persist(mdVersion);
                        trans.commit();
                        log.info("Database updated to version: "+updatedVersion);
                        //em.getTransaction().commit();
                                               
                    }else{
                        log.info("No updates done on database, maybe a error occured");
                    }
                    String neededVersion=(String) updates.keySet().toArray()[updates.size()-1];
                    String version ="-1";
                    if (mdVersion!=null){
                        version =mdVersion.getConfigValue();
                    }
                    if (!neededVersion.equalsIgnoreCase(version)){
                        log.warn("Version of database is: "+version+" while the version must be: "+neededVersion+" Try to do the updates manualy");
                    }else{
                        log.info("Database is up to date");
                    }
                }
            }
            //Connection conn = ((Session)em.getDelegate()).getSession(EntityMode.MAP);
        } catch(Exception e){
            log.error("Unable to execute scripts for updating database",e);
        }finally {
            Stripersist.requestComplete();
        }
    }
    
    private LinkedHashMap<String, List<File>> getUpdates(String version) {
        LinkedHashMap<String, List<File>> scripts = new LinkedHashMap<String, List<File>>();

        boolean versionFound = false;
        for (Entry<String, List<File>> entry : this.updates.entrySet()) {
            if (!versionFound) {
                String v = entry.getKey();
                if (v.equalsIgnoreCase(version)) {
                    versionFound = true;
                }
            }else if (versionFound) {
                scripts.put(entry.getKey(), entry.getValue());
            }
        }
        return scripts;
    }

    private void checkScriptDir() {
        File scriptDir = new File(DatabaseSynchronizer.class.getResource("/scripts").getFile());
        File[] scripts=scriptDir.listFiles();
        for (File script : scripts){
            boolean found=false;
            for (Entry<String, List<File>> entry : this.updates.entrySet()) {
                for (File registeredScript : entry.getValue()){
                    if (script.equals(registeredScript)){
                        found=true;
                        break;
                    }
                }
                if (found){
                    break;
                }
            }
            if (!found){
                log.warn("The sql script "+script.getAbsolutePath()+" is not registered in a update. "
                        + "The script is not used to updated the database. Is this correct? "
                        + "Otherwise add the script to the var DatabaseSynchronizer.updates.");
            }
        }
    }
    
    public class ScriptWorker implements Work{
        LinkedHashMap<String, List<File>> updateScripts;
        private String successVersion=null;
        private boolean errored=false;
        
        public ScriptWorker(LinkedHashMap<String, List<File>> scripts){
            this.updateScripts=scripts;
        }
        @Override
        public void execute(Connection cnctn) throws SQLException {                
            ScriptRunner runner = new ScriptRunner(cnctn, true, true);
            for (Entry<String, List<File>> entry : this.updateScripts.entrySet()) {
                List<File> scripts = entry.getValue();
                for (File script : scripts){
                    try {
                        log.info("Run database script: "+script.getPath());
                        runner.runScript(new FileReader(script));
                        if (!this.errored){
                            this.successVersion = entry.getKey();
                        }
                    } catch (Exception ex) {
                        log.error("Error while executing script: " + script.getAbsolutePath(), ex);
                        this.errored = true;
                        break;
                    }
                }
                if (this.isErrored()){
                    break;
                }
            }
        }
        public boolean isErrored(){
            return this.errored;
        }
        public String getLatestSuccesVersion(){
            return successVersion;
        }
    }

    @Override
    public void init(ServletConfig sc) throws ServletException {
        this.sc=sc;
        doInit();
    }

    //<editor-fold defaultstate="collapsed" desc="Interface methods">
    @Override
    public ServletConfig getServletConfig() {
        return sc;
    }
    
    @Override
    public void service(ServletRequest sr, ServletResponse sr1) throws ServletException, IOException {
        return;
    }
    
    @Override
    public String getServletInfo() {
        return"";
    }
    
    @Override
    public void destroy() {
        
    }
    //</editor-fold>
}
