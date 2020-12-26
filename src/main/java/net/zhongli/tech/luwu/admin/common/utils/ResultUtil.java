package net.zhongli.tech.luwu.admin.common.utils;


import com.github.pagehelper.Page;
import lombok.extern.slf4j.Slf4j;
import net.zhongli.tech.luwu.admin.common.dto.Pager;
import net.zhongli.tech.luwu.admin.common.enums.ResultEnum;
import net.zhongli.tech.luwu.admin.common.enums.ServiceErrorEnum;
import net.zhongli.tech.luwu.admin.common.exception.ServiceException;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 统一结果返回
 * @author lk
 * @create 2020/12/10 17:58
 **/
@Slf4j
public class ResultUtil {

    public static <T> Result<T> success(T data, String msg, Integer code) {
        Result<T> r = new Result<>();
        r.setCode(code);
        r.setMessage(msg);
        r.setData(data);
        return r;
    }

    public static <T> Result<T> success(T data, ResultEnum resultEnum) {
        Result<T> r = new Result<>();
        r.setCode(resultEnum.getCode());
        r.setMessage(resultEnum.getMsg());
        r.setData(data);
        return r;
    }

    public static <T> Result<T> success() {
        return success(null, ResultEnum.SUCCESS);
    }

    /**
     * pageHelper 返回分页
     * @param page
     * @param data
     * @return
     */
    public static Pager page(Page<Object> page, Object data) {
        Pager pager = new Pager();
        pager.setPageNum(page.getPageNum());
        pager.setPageSize(page.getPageSize());
        pager.setPages(page.getPages());
        pager.setTotal(page.getTotal());
        pager.setStartRow(page.getStartRow());
        pager.setData(data);
        pager.setCode(ResultEnum.SUCCESS.getCode());
        pager.setMessage(ResultEnum.SUCCESS.getMsg());
        return pager;
    }

    /**
     * mybatis RowBound / 逻辑分页
     * 返回分页
     * @return
     */
    public static Pager page(Pager pager) {
        pager.setCode(ResultEnum.SUCCESS.getCode());
        pager.setMessage(ResultEnum.SUCCESS.getMsg());
        return pager;
    }

    public static <T> Result<T> success(T data) {
        return success(data, ResultEnum.SUCCESS);
    }

    public static <T> Result<T> success(String msg) {
        Result<T> r = new Result<>();
        r.setCode(ResultEnum.SUCCESS.getCode());
        r.setMessage(msg);
        r.setData(null);
        return r;
    }

    public static <T> Result<T> fail(Integer code, String msg) {
        Result<T> r = new Result<>();
        r.setCode(code);
        r.setMessage(msg);
        r.setData(null);
        return r;
    }

    public static <T> Result<T> fail(String msg) {
       return fail(ResultEnum.FAIL.getCode(),msg);
    }

    public static <T> Result<T> fail(ResultEnum resultEnum) {
        return fail(resultEnum.getCode(), resultEnum.getMsg());
    }

    public static <T> Result<T> fail() {
        return fail(ResultEnum.FAIL.getCode(), ResultEnum.FAIL.getMsg());
    }

    public static ResponseEntity<FileSystemResource> file(File file, String fileName) {
        String name = null == fileName ? file.getName() : fileName;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        try {
            headers.add("Content-Disposition", "attachment; filename=" + URLEncoder.encode(name,"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            log.error("【文件下载】下载异常：e={}", e.getMessage(), e);
            throw new ServiceException(ServiceErrorEnum.SYSTEM_DEFAULT_FAIL);
        }
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new FileSystemResource(file));
    }

}
