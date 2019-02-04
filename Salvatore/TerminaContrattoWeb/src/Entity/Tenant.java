/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Tenant {
    private int IDLocatario;
    private String nickname;
    private int SollecitiPagamento;

    public Tenant(int IDLocatario, String nickname, int SollecitiPagamento){
        this.IDLocatario = IDLocatario;
        this.nickname = nickname;
        this.SollecitiPagamento = SollecitiPagamento;
    }
    
    public String getNickname(){
        return this.nickname;
    }

}
