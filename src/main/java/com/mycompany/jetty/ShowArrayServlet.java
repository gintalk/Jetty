/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jetty;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 *
 * @author duytu
 */
public class ShowArrayServlet extends HttpServlet{
    private int[] array;
    
    @Override
    public void init(){
        this.array = (int[]) getServletContext().getAttribute("my.array");
    }
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException{
        PrintWriter out = response.getWriter();
        
        for(int i=0; i<this.array.length; i++){
            out.println(this.array[i]);
        }
    }
}
