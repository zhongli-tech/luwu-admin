/**
 * Created by OZY on 2017/11/8.
 */
(function ($) {
    $.fn.sidebarMenu = function (options) {

        options = $.extend({}, $.fn.sidebarMenu.defaults, options || {});
        var target = $(this);
        target.addClass('nav');
        target.addClass('nav-list');

        init(target, options);

        function init(target, data) {
            $.each(data, function (i, item) {
                var li = $('<li id="li_' + item.id + '"></li>');
                var a = $('<a></a>');
                var icon = $('<i></i>');
                //icon.addClass('glyphicon');
                icon.addClass("menu-icon").addClass(item.icon);
                var text = $('<span></span>');
                text.addClass('menu-text').text(item.name);
                a.append(icon);
                a.append(text);

                if (item.children && item.children.length > 0) {
                    a.attr('href', '#');
                    a.addClass('dropdown-toggle');
                    var arrow = $('<b></b>');
                    arrow.addClass('arrow').addClass('fa fa-angle-down');
                    a.append(arrow);
                    li.append(a);
                    var menus = $('<ul></ul>');
                    menus.addClass('submenu');
                    init(menus, item.children);
                    li.append(menus);

                } else {
                    var href = 'javascript:addTabs({id:\'' + item.id + '\', title: \'' + item.name + '\', close: true, url: \'' + item.url + '\'});';
                    a.attr('href', href);
                    li.append(a);
                }
                target.append(li);
            });
        }
    };

    $.fn.sidebarMenu.defaults = {};
})(jQuery);


$(function () {

    $("[addtabs]").click(function () {
        addTabs({ id: $(this).attr("id"), title: $(this).attr('title'), close: true });
    });

    $(".nav-tabs").on("click", "[tabclose]", function (e) {
        var id = $(this).attr("tabclose");
        closeTab(id);
    });

    window.onresize = function () {
        var target = $(".tab-content .active iframe");
        changeFrameHeight(target);
    };

    //tab页 左向右移动事件
    $('.nav-tab .left-backward').click(leftMove);

    $('.nav-tab .right-forward').click(rightMove);

    //固定左边菜单的高度
    //$('#sidebar').height($(window).height() - 80);
});


var addTabs = function (options) {
    //var rand = Math.random().toString();
    //var id = rand.substring(rand.indexOf('.') + 1);
    var id = "tab_" + options.id;
    $(".active").removeClass("active");
    //如果TAB不存在，创建一个新的TAB
    if (!$("#" + id)[0]) {
        //固定TAB中IFRAME高度
        var mainHeight = document.documentElement.clientHeight - 115;
        //创建新TAB的title
        var title = '<li role="presentation" id="tab_' + id + '"><a href="#' + id + '" aria-controls="' + id + '" role="tab" data-toggle="tab">' + options.title;
        //是否允许关闭
        if (options.close) {
            title += ' <i class="glyphicon glyphicon-remove-sign" tabclose="' + id + '"></i>';
        }
        title += '</a></li>';


        //是否指定TAB内容
        var content;
        if (options.content) {
            content = '<div role="tabpanel" class="tab-pane" id="' + id + '">' + options.content + '</div>';
        } else {//没有内容，使用IFRAME打开链接
            //url是否带参数
            var param =  options.url.indexOf("?") >= 0 ? "&" : "?";

            content = '<div role="tabpanel" class="tab-pane" id="' + id + '"><iframe id="iframe_' + id +'" src="' + options.url + param +
                '" width="100%" height="' + mainHeight + '" onload="changeFrameHeight(this)" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="auto" allowtransparency="true"></iframe></div>';
        }
        //加入TABS
        $(".nav-tabs").append(title);
        $(".tab-content").append(content);
        //右移
        rightMove();
    }
    //激活TAB
    $("#tab_" + id).addClass('active');
    $("#" + id).addClass("active");

    //激活左边菜单
    $("#menu li").removeClass('active');
    $('#li_' + options.id).addClass('active');
    $('#li_' + options.id).parent().parent().addClass('active');
};

var closeTab = function (id) {
    //如果关闭的是当前激活的TAB，激活他的前一个TAB
    if ($(".nav-tabs .active").attr('id') === "tab_" + id) {
        $("#tab_" + id).prev().addClass('active');
        $("#" + id).prev().addClass('active');
    }
    //关闭TAB
    $("#tab_" + id).remove();
    $("#" + id).remove();
};

var changeFrameHeight = function (that) {
    $(that).height(document.documentElement.clientHeight - 115);
    $(that).parent(".tab-pane").height(document.documentElement.clientHeight - 130);
};


function leftMove(){
    if($(".nav-tab .middle-tab .nav-tabs").is(":animated")){
        return;
    }

    var iLeft = $('.nav-tab .middle-tab .nav-tabs').position().left;
    if(iLeft < 0){
        var totalWidth = 0;
        var lis = $(".nav-tabs li");
        for(var i = 0; i < lis.length; i++){
            var item = lis[i];
            totalWidth-= $(item).width();
            if(iLeft > totalWidth){
                iLeft+= $(item).width();
                break;
            }
        }
        if(iLeft > 0){
            iLeft = 0;
        }
        $(".nav-tab .middle-tab .nav-tabs").animate({left: iLeft + 'px'});
    }
}


function rightMove(){
    if($(".nav-tab .middle-tab .nav-tabs").is(":animated")){
        return;
    }

    var iLeft = $(".nav-tab .middle-tab .nav-tabs").position().left;
    var totalWidth = 0;
    $.each($(".nav-tabs li"),function(key, item){
        totalWidth+= $(item).width();
    });
    var tabsWidth = $(".nav-tab .middle-tab").width();

    if(totalWidth > tabsWidth){
        if(totalWidth - tabsWidth <= Math.abs(iLeft)){
            return;
        }
        var lis = $(".nav-tabs li");
        totalWidth = 0;
        for(var i = lis.length - 1; i >= 0; i--){
            var item = lis[i];
            totalWidth-= $(item).width();
            if(iLeft > totalWidth){
                iLeft-= $(item).width();
                break;
            }
        }

        $(".nav-tab .middle-tab .nav-tabs").animate({left: iLeft + 'px'});
    }
}



