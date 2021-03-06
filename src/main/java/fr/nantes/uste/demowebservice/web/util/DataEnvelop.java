package fr.nantes.uste.demowebservice.web.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ughostephan on 23/06/2017.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataEnvelop {

    private Object data;

    private HttpStatus status = HttpStatus.OK;

    private Date timestamp = new Date();

    private String error;

    private List<String> errorList;

    private static ResponseEntity makeResponseEntity(DataEnvelop envelop, HttpStatus status) {
        if (status == null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity(envelop, status);
    }

    private DataEnvelop(Object data) {
        this.data = data;
    }

    /**
     * Create envelop data envelop.
     *
     * @param data the data
     * @return the data envelop
     */
    public static ResponseEntity CreateEnvelop(Object data) {
        return makeResponseEntity(new DataEnvelop(data), HttpStatus.OK);
    }

    private DataEnvelop(Object data, HttpStatus status) {
        this.data = data;
        this.status = status;
    }

    /**
     * Create envelop response entity.
     *
     * @param status the status
     * @param data   the data
     * @return the response entity
     */
    public static ResponseEntity CreateEnvelop(HttpStatus status, Object data) {
        return makeResponseEntity(new DataEnvelop(data, status), status);
    }

    private DataEnvelop(HttpStatus status, String error) {
        this.error = error;
        this.status = status;
    }


    /**
     * Create envelop response entity.
     *
     * @param status the status
     * @param error  the error
     * @return the response entity
     */
    public static ResponseEntity CreateEnvelop(HttpStatus status, String error) {
        return makeResponseEntity(new DataEnvelop(status, error), status);
    }

    private DataEnvelop(HttpStatus status, String error, List<String> errorList) {
        this.error = error;
        this.status = status;
        this.errorList = errorList;
    }


    /**
     * Create envelop data envelop.
     *
     * @param status the status
     * @param error  the error
     * @param result the result
     * @return the data envelop
     */
    public static ResponseEntity CreateEnvelop(HttpStatus status, String error, BindingResult result) {
        final List<String> errorList = result.getFieldErrors().stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .collect(Collectors.toList());

        return makeResponseEntity(new DataEnvelop(status, error, errorList), status);
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

    /**
     * Gets error list.
     *
     * @return the error list
     */
    public List<String> getErrorList() {
        return errorList;
    }

    /**
     * Sets error list.
     *
     * @param errorList the error list
     */
    public void setErrorList(List<String> errorList) {
        this.errorList = errorList;
    }
}
