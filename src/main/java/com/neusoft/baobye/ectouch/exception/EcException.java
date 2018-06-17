package com.neusoft.baobye.ectouch.exception;

public class EcException extends RuntimeException {

    /**
     * 错误编码
     */
    private String buttonValue;
    /**
     * 消息是否为属性文件中的KEY
     */
    private boolean propertiesKey = true;

    private String contentUrl;

    public EcException(String message){
        super(message);
    }

    public EcException(String buttonValue,String message,String contentUrl){
        this(buttonValue,message,contentUrl,true);
    }

    public EcException(String buttonValue,String message,String contentUrl,boolean propertiesKey){
        super(message);
        this.setButtonValue(buttonValue);
        this.setPropertiesKey(propertiesKey);
        this.setContentUrl(contentUrl);
    }

    public EcException(String buttonValue,String message,Throwable cause,boolean propertiesKey){
        super(message,cause);
        this.setButtonValue(buttonValue);
        this.setPropertiesKey(propertiesKey);
    }
    public EcException(String message,Throwable cause){
        super(message,cause);
    }

    public String getButtonValue() {
        return buttonValue;
    }

    public void setButtonValue(String buttonValue) {
        this.buttonValue = buttonValue;
    }

    public boolean isPropertiesKey() {
        return propertiesKey;
    }

    public void setPropertiesKey(boolean propertiesKey) {
        this.propertiesKey = propertiesKey;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }
}
