/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Nemesis
 */
public class mesaj {
        
    private String kime,icerik,saat;

    @Override
    public String toString() {
        return kime + "     " + icerik + "      " + saat;
    }

    public mesaj(String kime, String icerik, String saat) {
        this.kime = kime;
        this.icerik = icerik;
        this.saat = saat;
    }
    public String getKime() {
        return kime;
    }

    public void setKime(String kime) {
        this.kime = kime;
    }

    public String getIcerik() {
        return icerik;
    }

    public void setIcerik(String icerik) {
        this.icerik = icerik;
    }

    public String getSaat() {
        return saat;
    }

    public void setSaat(String saat) {
        this.saat = saat;
    }
  
}
