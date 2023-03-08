package controller;

import modell.Kalender;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

@ManagedBean
@SessionScoped

public class LookUpBean {
    @Inject
    private KalenderBean kalenderCDI;

    public LookUpBean(){

    }
    public Kalender readKalender(){
        return kalenderCDI.getKalender();
    }
}
