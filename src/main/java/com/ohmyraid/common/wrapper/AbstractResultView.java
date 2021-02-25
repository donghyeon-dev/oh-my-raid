package com.ohmyraid.common.wrapper;

import com.ohmyraid.common.result.Result;
import com.ohmyraid.common.result.SuccessResult;
import com.ohmyraid.utils.MessageUtil;

import java.io.Serializable;
import java.sql.Timestamp;

public abstract class AbstractResultView<T> implements Serializable {

    private static final long serialVersionUID = -1229247078892250872L;

    /** 통신에 대한 결과 코드를 담는다. */
    private String code;

    /** 통신에 대한 서버의 결과 메시지를 담는다. */
    private String message;

    /** 결과 데이터 */
    private T data;

    public AbstractResultView() {
        // 기본 성공 코드로 설정
        Result result = SuccessResult.SUCCESS;
        this.code = result.getResultCode();
        this.message = result.getResultMsg();
    }

    public AbstractResultView(T data) {
        this();
        this.data = data;
    }

    /**
     * 화면으로 보내는 JSON 데이터에서 결과상태과 메세지를 설정하는 함수 입력된 Result 객체 값에 따라 resultCode와 resultMsg의 값이 변경됨
     *
     * @param result
     * @return 체인메소드를 위해 해당 Instance를 반환
     */
    public AbstractResultView<T> setResult(Result result) {
        this.code = result.getResultCode();
        this.message = MessageUtil.getMessage(result.getResultMsg(), result.getResultMsgArgs());
        return this;
    }

    /**
     * resultCode의 getter 메소드
     *
     * @return resultCode
     */
    public String getCode() {
        return code;
    }

    /**
     * .do 요청을 통해 전달받는 데이터를 얻는다.
     *
     * @return data
     */
    public T getData() {
        return data;
    }

    /**
     * .do 요청을 통해 전달받는 데이터를 지정한다.
     *
     * @param data
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * resultCode의 setter 메소드
     *
     * @param resultCode
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * resultMsg의 getter 메소드
     *
     * @return resultMsg
     */
    public String getMessage() {
        return message;
    }

    /**
     * resultMsg의 setter 메소드
     *
     * @param resultMsg
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * now의 getter 메소드
     *
     * @return now
     */
    public String getTimestamp() {
        Long timestamp = new Timestamp(System.currentTimeMillis()).getTime();
        return timestamp.toString();
    }
}