package org.lab.onem2m.web.DO;

public class StatisticsDO {

    private String label;
    private Long y;

    public StatisticsDO(String label, Long y) {
        this.label = label;
        this.y = y;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Long getY() {
        return y;
    }

    public void setY(Long y) {
        this.y = y;
    }

}
