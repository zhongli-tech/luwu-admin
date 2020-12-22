/*
* 公共引入js
* @version 1.0.0
* by OZY
*/
(function ($) {

    $.extend({

        index : {
            //子跳转页面方法
            loadPage: function (url, successCallback) {
                var index = layer.load(2, { shade: 0.2 });
                $.get(url, function (result) {
                    $("#main-container").html(result);
                    if (successCallback && typeof(successCallback) === "function") {
                        successCallback()
                    }
                    layer.close(index);
                });
            },

            //返回方法
            returnPage: function (url) {

            },
        }

    });

    //全局ajax设置
    /*$.ajaxSetup({
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
    });*/


})(jQuery);