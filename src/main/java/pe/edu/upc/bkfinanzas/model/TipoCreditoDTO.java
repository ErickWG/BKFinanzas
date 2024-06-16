package pe.edu.upc.bkfinanzas.model;

public class TipoCreditoDTO {
    private Double tasaNum;
    private String tasaText;
    private Integer cuotas;
    private Integer capitalizacion;

    public Double getTasaNum() {
        return tasaNum;
    }

    public void setTasaNum(Double tasaNum) {
        this.tasaNum = tasaNum;
    }

    public String getTasaText() {
        return tasaText;
    }

    public void setTasaText(String tasaText) {
        this.tasaText = tasaText;
    }

    public Integer getCuotas() {
        return cuotas;
    }

    public void setCuotas(Integer cuotas) {
        this.cuotas = cuotas;
    }

    public Integer getCapitalizacion() {
        return capitalizacion;
    }

    public void setCapitalizacion(Integer capitalizacion) {
        this.capitalizacion = capitalizacion;
    }
}
