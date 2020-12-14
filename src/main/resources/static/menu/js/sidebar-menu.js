/**
 * Created by OZY on 2017/11/8.
 */
(function ($) {

    var Menu = function (element, options) {
        this.$element = element;
        this.options = $.extend({}, $.fn.sidebarMenu.defaults, options)
    };


    Menu.prototype = {
        //初始化菜单
        init : function () {

            var menus = this.options.menus;

            if (menus == null) {
                console.log("ERROR: 请先设置资源属性menus...");
                return;
            }
            //创建菜单
            this.createSidebar(this.$element, menus);
            //绑定事件
            this.bindEvents();
        },

        //创建菜单
        createSidebar : function(element, menus) {
            //console.log(menus);
            for (var i = 0; i < menus.length ; i++) {

                var menu = menus[i];
                var li_ = $('<li id="li_' + menu.id + '"></li>');
                var icon_ = $('<i></i>').addClass("menu-icon").addClass(menu.icon);
                var text_ = $('<span></span>').addClass('menu-text').text(menu.resourceName);
                var a_ = $('<a></a>').append(icon_).append(text_);

                if (menu.children && menu.children.length > 0) {
                    a_.attr('href', '#');
                    a_.addClass('dropdown-toggle');

                    var arrow_ = $('<b></b>').addClass('arrow').addClass('fa fa-angle-down');
                    a_.append(arrow_);
                    li_.append(a_);

                    var menuUl_ = $('<ul></ul>').addClass('submenu');
                    this.createSidebar(menuUl_, menu.children);

                    li_.append(menuUl_);

                } else {
                    a_.attr("href", "javascript:;");
                    a_.attr("id", menu.id);
                    a_.attr("name", menu.resourceName);
                    a_.attr("url", menu.url);
                    a_.attr("close", true);
                    a_.attr("addtabs", "");
                    li_.append(a_);

                }

                element.append(li_);
            }

        },

        //绑定各种事件
        bindEvents : function() {
            var obj = this;
            //点击添加
            $("[addtabs]").click(function () {
                obj.addTabs(
                    $(this).attr("id"), $(this).attr('name'),
                    $(this).attr('url'), $(this).attr('close')
                );
            });

            //绑定关闭事件
            $(".nav-tabs").on("click", "[tabclose]", function (e) {
                var id = $(this).attr("tabclose");
                obj.closeTab(id);
            });

            //绑定自适应
            window.onresize = function () {
                var target = $(".tab-content .active iframe");
                obj.changeFrameHeight(target);
            };

            //tab页 左向右移动事件
            $('.nav-tab .left-backward').click(obj.leftMove);

            $('.nav-tab .right-forward').click(obj.rightMove);
        },

        //添加tabs
        addTabs : function(id, name, url, close) {
            var tabId = "tab_" + id;
            $(".active").removeClass("active");

            //如果TAB不存在，创建一个新的TAB
            if (!$("#" + tabId)[0]) {
                //固定TAB中IFRAME高度
                var mainHeight = document.documentElement.clientHeight - 115;
                //创建新TAB的title
                var title = '<li role="presentation" id="' + tabId + '" tabUrl="' + url + '"><a href="#tabpanel_' + id + '" aria-controls="tabpanel_' + id + '" role="tab" data-toggle="tab">' + name;
                //是否允许关闭
                if (close) {
                    title += ' <i class="glyphicon glyphicon-remove-sign" tabclose="' + id + '"></i>';
                }
                title += '</a></li>';


                //是否指定TAB内容
                var content = '<div role="tab" class="tab-pane" style="height: ' + mainHeight + 'px;" id="tabpanel_' + id + '"><iframe id="iframe_' + tabId +'" src="' + url +
                    '" width="100%" height="' + mainHeight + 'px" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="auto" allowtransparency="true"></iframe></div>';

                //加入TABS
                $(".nav-tabs").append(title);
                $(".tab-content").append(content);

                //右移
                this.rightMove();
            }

            //激活TAB
            $("#" + tabId).addClass('active');
            $("#tabpanel_" + id).addClass('active');

            //激活左边菜单
            $("#menu li").removeClass('active');
            $('#li_' + id).addClass('active');
            $('#li_' + id).parent().parent().addClass('active');
        },

        //关闭tab
        closeTab : function(id) {
            //如果关闭的是当前激活的TAB，激活他的前一个TAB
            if ($(".nav-tabs .active").attr('id') == "tab_" + id) {
                $("#tab_" + id).prev().addClass('active');
                $("#tabpanel_" + id).prev().addClass('active');
            }

            //关闭TAB
            $("#tabpanel_" + id).remove();
            $("#tab_" + id).remove();
        },

        //左移动
        leftMove : function() {

            if($(".nav-tab .middle-tab .nav-tabs").is(":animated")){
                return;
            }

            var iLeft = $('.nav-tab .middle-tab .nav-tabs').position().left;
            if(iLeft < 0) {
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
                $(".nav-tab .middle-tab .nav-tabs").animate({left : iLeft + 'px'});
            }
        },

        //右移动
        rightMove : function() {

            if($(".nav-tab .middle-tab .nav-tabs").is(":animated")){
                return;
            }

            var iLeft = $(".nav-tab .middle-tab .nav-tabs").position().left;
            var totalWidth = 0;
            $.each($(".nav-tabs li"), function(key, item){
                totalWidth+= $(item).width();
            });

            var tabsWidth = $(".nav-tab .middle-tab").width();

            if(totalWidth > tabsWidth) {
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

                $(".nav-tab .middle-tab .nav-tabs").animate({left : iLeft + 'px'});
            }
        },

        //自适应iframe
        changeFrameHeight : function(iframe) {
            $(iframe).height(document.documentElement.clientHeight - 115);
            $(iframe).parent(".tab-pane").height(document.documentElement.clientHeight - 130);
        },

        //获取menu实例
        getMenu : function() {
            return $(this.$element).data("menu");
        },

        tools : {
            //返回16位guid
            guid : function () {
                return 'xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
                    var r = Math.random()*16|0, v = c == 'x' ? r : (r&0x3|0x8);
                    return v.toString(16);
                });
            }
        }

    };


    $.fn.sidebarMenu = function (options) {
        var element = this;

        var menu = $(element).data("menu");

        if(!menu){
            $(element).data("menu", (menu = new Menu(element, options)));
            menu.init();
        }

        return menu;
    };


    $.fn.sidebarMenu.defaults = {
        menus : null //菜单资源
    };

})(jQuery);







