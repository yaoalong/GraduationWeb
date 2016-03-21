/**
 * 
 */
package org.lab.onem2m.web.DO;

import java.io.Serializable;

/**
 * @author Administrator
 *
 */
public class MachineStatusDO implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = -3291229346829239514L;

    private Integer id;

    private String ip;

    private Integer status;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip
     *            the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @return the status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

}
