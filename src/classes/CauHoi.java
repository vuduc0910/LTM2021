/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.util.ArrayList;

/**
 *
 * @author vuduc
 */
public class CauHoi {
    String question;
    ArrayList<String> listAnswer;
    String correctAnswer;
    String IdAnswer;

    public String getIdAnswer() {
        return IdAnswer;
    }

    public void setIdAnswer(String IdAnswer) {
        this.IdAnswer = IdAnswer;
    }

    public CauHoi() {
    }

    public CauHoi(String question, ArrayList<String> listAnswer, String correctAnswer) {
        this.question = question;
        this.listAnswer = listAnswer;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ArrayList<String> getListAnswer() {
        return listAnswer;
    }

    public void setListAnswer(ArrayList<String> listAnswer) {
        this.listAnswer = listAnswer;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
    
}
