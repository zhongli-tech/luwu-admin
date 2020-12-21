/*
* 公共引入js
* @version 1.0.0
* by OZY
*/
(function ($) {

    $.extend({

        index : {
            //子跳转页面方法
            loadPage: function (url) {
                var index = layer.load(2, {shade: 0.2});
                $.get(url, function (result) {
                    $("#page-content").html(result);
                    layer.close(index);
                });
            },

            //返回方法
            returnPage: function (url) {
                $("#" + $.index.getIframeId(), window.parent.document).attr("src", url);
            },

            //获取当前活动 iframeId
            getIframeId: function () {
                return $(".tab-content .active", window.parent.document).children().attr("id");
            },

            //通用添加页面跳转方法
            savePage: function (url) {
                $.index.loadPage(url);
            },

            //通用修改页面跳转方法
            updatePage: function (rows, url) {
                $.index.selectNumRow(rows, true , function () {
                    var suffix = "?";
                    if (url.indexOf("?") != -1) {
                        suffix = "&";
                    }

                    $.index.loadPage(url + suffix + "id=" + rows[0].id);
                });
            },

            //通用删除方法
            delPage: function (rows, url, callback, onlyOneFlag) {
                $.index.selectNumRow(rows, onlyOneFlag , function () {

                    //单行数据
                    if (onlyOneFlag) {
                        $.index.ajaxSend(url, {id : rows[0].id}, callback, "确认删除吗？");

                    } else {
                        //多行
                        var list = [];
                        $.each(rows, function (index, value) {
                            list.push(this.id);
                        });
                        $.index.ajaxSend(url, {ids : list}, callback, "确认删除吗？");
                    }

                });

            },

            //只能选中小于等于num行数据(第二个参数为是否只能选择一行数据)
            selectNumRow : function(rows, onlyOneFlag, callback) {
                if (onlyOneFlag) {
                    if (rows.length == 1) {
                        if (callback)
                            callback();

                    } else if (rows.length == 0) {
                        layer.msg("您没有选择行!");

                    } else {
                        layer.msg("最多只能选择一行数据进行操作!");
                    }

                } else {
                    if (rows.length >= 1) {
                        if (callback)
                            callback();

                    } else {
                        layer.msg("您没有选择行!");

                    }
                }


            },

            //通用ajax提交数据方法(必须有返回页面)
            ajaxCommit : function (data, url, returnUrl, callback) {

                $.index.ajaxSend(url, $.index.serializeNotNull(data), function (result) {
                    if (result.code == 200) {
                        if (callback != undefined) {
                            callback(result);

                        } else {
                            //延迟500毫秒再返回
                            setTimeout(function () {
                                $.index.returnPage(returnUrl);
                            }, 500);
                        }

                    } else {
                        layer.alert(result.msg, {icon : 5, shift : 6, time : 0});
                    }
                }, "确认提交吗");

            },

            //通用ajax请求方法
            ajaxSend : function (url, data, callback, message, type, dataType, headers) {

                if (message) {
                    //如需要提示
                    layer.confirm(message, {
                        icon: 3,
                        title: "操作提示"
                    }, function (index, layero) {
                        layer.close(index);
                        $.index.ajax(url, data, callback, type, dataType, headers);
                    });

                } else {
                    //不需要提示则直接发送ajax请求
                    $.index.ajax(url, data, callback, type, dataType, headers);
                }

            },

            //通用ajax封装
            ajax : function (url, data, callback, type, dataType, headers, cache) {

                $.ajax({
                    type : (type || "POST"),
                    url : url,
                    data : data,
                    dataType : (dataType || "JSON"),
                    headers : (headers || {}),
                    cache : (cache || false),
                    success: function (result) {
                        if (result.code == 200) {
                            //成功请求
                            layer.msg(result.msg);
                            if (callback) {
                                callback(result);
                            }

                        } else {
                            layer.alert(result.msg, {icon : 5, shift : 6, time : 0});
                        }
                    }
                });
            },

            //自定义验证
            validate: function (id, setting) {
                var options = $.extend({}, $.fn.validateDefaults, setting);

                $("#" + id).validate(options);
            },

            //序列化空字符的时候去掉
            serializeNotNull: function (param) {
                if (typeof(param) == "string") {
                    return param.split("&").filter(function (str) {
                        return !str.endsWith("=")
                    }).join("&");
                }

                return param;
                //return serStr.split("&").filter(str => !str.endsWith("=")).join("&");
            }
        }

    });


    //validate验证默认属性
    $.fn.validateDefaults = {
        errorElement : 'div',
        errorClass : 'help-block',
        focusInvalid : false,
        ignore : "",
        rules : {},
        messages : {},
        highlight : function(e) {
            $(e).closest(".form-group").removeClass("has-info").addClass("has-error");
        },
        success : function(e) {
            $(e).closest(".form-group").removeClass("has-error").addClass("has-success");
            $(e).remove();
        },
        errorPlacement : function(error, element) {
            if (element.is("input[type=checkbox]") || element.is("input[type=radio]")) {
                var controls = element.closest("div[class*='col-']");
                if (controls.find(':checkbox,:radio').length > 1)
                    controls.append(error);
                else
                    error.insertAfter(element.nextAll(".lbl:eq(0)").eq(0));
            } else if (element.is(".select2")) {
                error.insertAfter(element.siblings("[class*='select2-container']:eq(0)"));
            } else if (element.is(".chosen-select")) {
                error.insertAfter(element.siblings("[class*='chosen-container']:eq(0)"));
            } else
                error.insertAfter(element.parent());
        }

    };

    //全局ajax设置
    $.ajaxSetup({
        loadingBox : null,
        beforeSend: function () {
            this.loadingBox = layer.load(2, {shade: 0.2});
        },
        complete: function (jqXHR, textStatus) {
            //关闭等待框
            layer.close(this.loadingBox);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            if (jqXHR.responseJSON) {
                layer.alert(jqXHR.responseJSON.msg, {icon : 5, shift : 6, time : 0});
            }
        }
    });


})(jQuery);