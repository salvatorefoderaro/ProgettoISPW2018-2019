/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Bean.BeanNotifica;
import Bean.ContrattoBean;
import Bean.SegnalazionePagamentoBean;
import DAO.JDBCContratto;
import DAO.JDBCSegnalazionePagamento;
import Entity.Contratto;
import Entity.SegnalazionePagamento;
import java.sql.Connection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Observable;

public class Controller{
    
    // Creo il dizionario, "accetta" un Intero come chiave, e segnalazione Pagamento come valore (oggetto)
    private  Map<Integer, SegnalazionePagamento> dictionarySegnalazionePagamento  = new HashMap<Integer, SegnalazionePagamento>();
    private  Map<Integer, Contratto> dictionaryContratto  = new HashMap<Integer, Contratto>();
    private Connection connection = null;
    private JDBCSegnalazionePagamento jdbcSegnalazionePagamento;
    private JDBCContratto jdbcContratto;
    private static Controller controller_instance = null;
    
    private Controller(){}
    
    public static Controller getInstance(){
        if (controller_instance == null)
            controller_instance = new Controller();
        return controller_instance;
    }
    
    public List<SegnalazionePagamentoBean> getSegnalazioniPagamento(int ID, String type){
        
    jdbcSegnalazionePagamento = new JDBCSegnalazionePagamento();
    
    List<SegnalazionePagamento> Result = jdbcSegnalazionePagamento.getSegnalazioniPagamento(ID, type);
    System.out.println("\n\nLa dimensione è: " + Result.size());
    List<SegnalazionePagamentoBean> list = new LinkedList<SegnalazionePagamentoBean>();
    
    for (SegnalazionePagamento temp : Result) {
        if (dictionarySegnalazionePagamento.get(temp.getID()) == null){
            dictionarySegnalazionePagamento.put(temp.getID(), temp);    
        }
        list.add(temp.makeBean());
    }
    
    return list;    
}   
    
    public List<ContrattoBean> getContratti(int ID){
        
        JDBCContratto jdbcContratto = new JDBCContratto();
        List<Contratto> Result = jdbcContratto.getContratti(ID);
        List<ContrattoBean> list = new LinkedList<>();
        
        for (Contratto temp : Result) {
            ContrattoBean bean = new ContrattoBean();
            bean.setIDContratto(temp.getIDContratto());
            bean.setIDLocatario(temp.getIDLocatario());
            bean.setStatoContratto(temp.getStatoContratto());
            list.add(bean);
            dictionaryContratto.put(temp.getIDContratto(), temp);
        }        
        
        return list;
    }
  
    public void setContrattoArchiviato(SegnalazionePagamentoBean bean){
        dictionarySegnalazionePagamento.get(bean.getID()).getContratto().archiviaContratto();            
}
    
        public void setSegnalazioneNotificata(SegnalazionePagamentoBean bean){
        dictionarySegnalazionePagamento.get(bean.getID()).archiviaNotificaSegnalazione();
}
        
                public void setSegnalazionePagata(SegnalazionePagamentoBean bean){
        dictionarySegnalazionePagamento.get(bean.getID()).archiviaNotificaSegnalazione();
}
    
    public void testMakeBean(SegnalazionePagamentoBean bean){
       
        jdbcSegnalazionePagamento  = new JDBCSegnalazionePagamento();
        jdbcContratto = new JDBCContratto();
        
        jdbcSegnalazionePagamento.createSegnalazionePagamento(bean);
        jdbcContratto.setContrattoSegnalato(bean.getIDContratto());

        SegnalazionePagamento newSegnalazione = jdbcSegnalazionePagamento.getSegnalazionePagamento(bean.getIDContratto());
        dictionarySegnalazionePagamento.put(newSegnalazione.getID(), newSegnalazione);
    }
    
    public void incrementaSegnalazione(SegnalazionePagamentoBean bean){
        //if (bean.getNumeroReclamo() == 3){
          //  dictionarySegnalazionePagamento.get(bean.getID()).archiviaSegnalazionePagamento();
        
            dictionarySegnalazionePagamento.get(bean.getID()).incrementaSegnalazionePagamento();
        
    }
    
    
}
