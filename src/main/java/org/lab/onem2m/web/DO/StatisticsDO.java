package org.lab.onem2m.web.DO;

import java.io.Serializable;

public class StatisticsDO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -744874904202620279L;
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
