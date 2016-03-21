package org.lab.mars.onem2m.web.nework.protol;

import java.io.Serializable;

public class M2mNetworkMessage implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -9053314333322457203L;

    private int errorCode;

    private Object result;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

}
