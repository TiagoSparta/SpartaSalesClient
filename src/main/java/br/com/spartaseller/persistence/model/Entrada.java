package br.com.spartaseller.persistence.model;

import java.time.LocalDateTime;

public class Entrada extends AbstractEntity{
    private ApplicationUser applicationUser;
    private String tipo;
    private LocalDateTime dataHoraInicio;
    private LocalDateTime dataHoraFim;

    public Entrada() {
    }

    public Entrada(long id) {
        this.id = id;
    }

    public Entrada(ApplicationUser applicationUser, String tipo, LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim) {
        this.applicationUser = applicationUser;
        this.tipo = tipo;
        this.dataHoraInicio = dataHoraInicio;
        this.dataHoraFim = dataHoraFim;
    }

    public ApplicationUser getApplicationUser() {
        return applicationUser;
    }

    public void setApplicationUser(ApplicationUser applicationUser) {
        this.applicationUser = applicationUser;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDateTime getDataHoraInicio() {
        return dataHoraInicio;
    }

    public void setDataHoraInicio(LocalDateTime dataHoraInicio) {
        this.dataHoraInicio = dataHoraInicio;
    }

    public LocalDateTime getDataHoraFim() {
        return dataHoraFim;
    }

    public void setDataHoraFim(LocalDateTime dataHoraFim) {
        this.dataHoraFim = dataHoraFim;
    }
}
