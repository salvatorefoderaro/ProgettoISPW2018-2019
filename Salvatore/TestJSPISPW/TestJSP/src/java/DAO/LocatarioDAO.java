/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

 
import Entity.Locatario;
import java.util.List;
 
import Entity.SegnalazionePagamento;
 
public interface LocatarioDAO {
    public void incrementaSollecitiPagamento(int ID);
    public Locatario getLocatario(int ID);
}