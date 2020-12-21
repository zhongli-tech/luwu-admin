package net.zhongli.tech.luwu.admin.common.exception;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import net.zhongli.tech.luwu.admin.common.enums.ResultEnum;
import net.zhongli.tech.luwu.admin.common.utils.HttpUtils;
import net.zhongli.tech.luwu.admin.common.utils.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 统一异常处理
 * @author lk
 * @create 2020/12/10 3:25 下午
 **/
@ControllerAdvice
@Slf4j
public class CommonExceptionHandler {

    /**
     * 自定义异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public ResponseEntity<Result<Object>> handleServiceException(Exception e) {
        Result<Object> resp = new Result<>();
        ServiceException exception = (ServiceException) e;
        if (null == exception.getServiceErrorEnum()) {
            resp.setCode(exception.getCode());
            resp.setMessage(exception.getMessage());
        } else {
            resp.setCode(exception.getServiceErrorEnum().getCode());
            resp.setMessage(exception.getServiceErrorEnum().getMsg());
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    /**
     * 参数认证错误
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<Result<Object>> handlerNotValidException(MethodArgumentNotValidException e) {
        Result<Object> resp = new Result<>();
        resp.setCode(ResultEnum.FAIL.getCode());
        // 返回错误提示
        FieldError fieldError = e.getBindingResult().getFieldError();
        String msg = "系统错误";
        if (null != fieldError) {
            // 修改不显示字段提示，保护实体信息
            msg = fieldError.getDefaultMessage();
        }
        resp.setMessage(msg);
        log.error("\n【Luwu-ExceptionHandler】methodArgumentNotValidException,e={}", e.getMessage(), e);
        return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 权限报错
     * @param e
     * @return
     *//*
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Result<Object>> handleAccessDeniedException(AccessDeniedException e) {
        Result<Object> resp = new Result<>();
        resp.setCode(ResultEnum.FAIL.getCode());
        resp.setMsg("无权限访问");
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
    }*/
    /**
     * 处理系统异常，均抛出 500
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<Result<Object>> handleErrorException(Exception e) {
        Result<Object> resp = new Result<>();
        resp.setCode(ResultEnum.FAIL.getCode());
        resp.setMessage(ResultEnum.FAIL.getMsg());
        log.error("\n【Luwu-ExceptionHandler】exception,e={}", e.getMessage(), e);
        return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 处理 404
     * @param e
     * @return
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public void handlerNoFoundException(HttpServletRequest request, HttpServletResponse response, Exception e) throws IOException {
        // 判断是连接请求还是异步请求
        if (HttpUtils.isAjaxRequest(request)) {
            // 返回 404 错误 json
            Result<Object> resp = new Result<>();
            resp.setCode(ResultEnum.NOT_FOUND.getCode());
            resp.setMessage(ResultEnum.NOT_FOUND.getMsg());
            // 返回 json 流
            HttpUtils.writeJson(response, HttpStatus.NOT_FOUND.value(), JSONObject.toJSONString(resp));
        } else {
            // 跳转 404 错误页面
            response.sendRedirect("/error/404");
        }
    }
}
