/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.uniroma2.ispw.fersa.Exceptions;

/**
 *
 * @author root
 */
public class transactionError extends Exception {

    public transactionError(String message){
        super(message);
    }

}
