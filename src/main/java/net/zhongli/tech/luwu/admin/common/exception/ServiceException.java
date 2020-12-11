package net.zhongli.tech.luwu.admin.common.exception;

import lombok.Getter;
import net.zhongli.tech.luwu.admin.common.enums.ServiceErrorEnum;

/**
 * 统一业务异常
 * @author lk
 * @create 2020/12/10 2:50 下午
 **/
@Getter
public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = 7124689980551360589L;

    private ServiceErrorEnum serviceErrorEnum;

    private int code;

    private String message;

    public ServiceException() {
        super();
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(ServiceErrorEnum serviceErrorEnum) {
        super(serviceErrorEnum.getMsg());
        this.serviceErrorEnum = serviceErrorEnum;
    }

    public ServiceException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public ServiceException(String message) {
        super(message);
        this.message = message;
        this.code = ServiceErrorEnum.SYSTEM_DEFAULT_FAIL.getCode();
    }
}
