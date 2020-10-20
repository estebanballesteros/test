package com.octagon.costooperacion.service.utills;

import com.octagon.costooperacion.service.dto.ResponseDTO;

public class OctagonBusinessException extends Exception {

    /**
     * status: true or false, when the service's response is success and have completed the functionality set true otherwise false
     */
    private Boolean status;
    /**
     * code: ' HTTP ResponseStatus status codes'
     * * 5xx: Server errors
     * * 6xx: Business errors see 'ResponseStatus Code Error'
     */
    private Number code;
    /**
     * ' HTTP ResponseStatus status message' ( OK,Created,No Content,Bad Request,Not Found), 'Error' or 'Specific error codes of the micro services'
     */
    private String message;
    /**
     * 'Details about the result', example: 'Entity create successfully', e.getMessage() 'from exceptions' .
     */
    private String description;

    public OctagonBusinessException(ResponseStatus reponseStatus, String description) {
        this.status = false;
        this.code = reponseStatus.getCode();
        this.message = reponseStatus.getMessage();
        this.description = reponseStatus.getDescription() + description;
    }

    public OctagonBusinessException(ResponseStatus reponseStatus) {
        this.status = false;
        this.code = reponseStatus.getCode();
        this.message = reponseStatus.getMessage();
        this.description = reponseStatus.getDescription();
    }


    public OctagonBusinessException(Number code, String message, String description) {
        this.status = false;
        this.code = code;
        this.message = message;
        this.description = description;
    }


    public OctagonBusinessException(Throwable throwable, ResponseStatus reponseStatus) {
        super(reponseStatus.getMessage(), throwable);
        this.status = false;
        this.code = reponseStatus.getCode();
        this.message = reponseStatus.getMessage();
        this.description = reponseStatus.getDescription();
    }

    public OctagonBusinessException(Throwable throwable, Number code, String message, String description) {
        super(message, throwable);
        this.status = false;
        this.code = code;
        this.message = message;
        this.description = description;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Number getCode() {
        return code;
    }

    public void setCode(Number code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ResponseDTO getResponseDTO(){
        return new ResponseDTO(status,code,message,description);
    }

}
