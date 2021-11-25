/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

/**
 *
 * @author vuduc
 */
public class SinhVien {
    String name;
    String id;
    String classes;

    public SinhVien(String name, String id, String classes) {
        this.name = name;
        this.id = id;
        this.classes = classes;
    }

    public SinhVien() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }
    
    
}
