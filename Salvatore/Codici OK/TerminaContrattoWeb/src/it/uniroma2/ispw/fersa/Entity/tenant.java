/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.uniroma2.ispw.fersa.Entity;


public class tenant {
    private int IDLocatario;
    private String nickname;
    private int SollecitiPagamento;

    public tenant(int IDLocatario, String nickname, int SollecitiPagamento){
        this.IDLocatario = IDLocatario;
        this.nickname = nickname;
        this.SollecitiPagamento = SollecitiPagamento;
    }
    
    public String getNickname(){
        return this.nickname;
    }

}
