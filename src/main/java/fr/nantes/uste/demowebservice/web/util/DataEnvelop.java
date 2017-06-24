package fr.nantes.uste.demowebservice.web.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.util.Date;

/**
 * Created by ughostephan on 23/06/2017.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataEnvelop {

    private Object data;

    private HttpStatus status = HttpStatus.OK;

    private Date timestamp = new Date();

    private String error;

    private DataEnvelop(Object data) {
        this.data = data;
    }

    /**
     * Create envelop data envelop.
     *
     * @param data the data
     * @return the data envelop
     */
    public static DataEnvelop CreateEnvelop(Object data) {
        return new DataEnvelop(data);
    }

    private DataEnvelop(Object data, HttpStatus status) {
        this.data = data;
        this.status = status;
    }

    /**
     * Create envelop data envelop.
     *
     * @param data   the data
     * @param status the status
     * @return the data envelop
     */
    public static DataEnvelop CreateEnvelop(Object data, HttpStatus status) {
        return new DataEnvelop(data, status);
    }

    private DataEnvelop(HttpStatus status, String error) {
        this.error = error;
        this.status = status;
    }

    /**
     * Create envelop data envelop.
     *
     * @param status the status
     * @param error  the error
     * @return the data envelop
     */
    public static DataEnvelop CreateEnvelop(HttpStatus status, String error) {
        return new DataEnvelop(status, error);
    }

    /**
     * Gets data.
     *
     * @return the data
     */
    public Object getData() {
        return data;
    }

    /**
     * Sets data.
     *
     * @param data the data
     */
    public void setData(Object data) {
        this.data = data;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public String getStatus() {
        return status.toString();
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    /**
     * Gets error.
     *
     * @return the error
     */
    public String getError() {
        return error;
    }

    /**
     * Sets error.
     *
     * @param error the error
     */
    public void setError(String error) {
        this.error = error;
    }

    /**
     * Gets timestamp.
     *
     * @return the timestamp
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * Sets timestamp.
     *
     * @param timestamp the timestamp
     */
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
