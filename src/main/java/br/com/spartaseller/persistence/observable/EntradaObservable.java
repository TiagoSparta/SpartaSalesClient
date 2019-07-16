package br.com.spartaseller.persistence.observable;

import br.com.spartaseller.persistence.model.Entrada;

public class EntradaObservable {
    private long id;
    private String usuario;
    private String tipo;
    private String dataInicio;
    private String dataFim;

    public EntradaObservable() {
    }

    public EntradaObservable(Entrada entrada) {
        id = entrada.getId();
        usuario = entrada.getApplicationUser().getUsername();
        tipo = entrada.getTipo();
        if (entrada.getDataHoraInicio() != null) {
                dataInicio = entrada.getDataHoraInicio().toString();
            } else {
                dataInicio = "Não registrado";
            }
            if (entrada.getDataHoraFim() != null) {
                dataFim = entrada.getDataHoraFim().toString();
            } else {
                dataFim = "Não registrado";
            }

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getDataFim() {
        return dataFim;
    }

    public void setDataFim(String dataFim) {
        this.dataFim = dataFim;
    }
}
