/*
* 菜单封装
* @version 1.0.0
* by Cerulean.
*/

(function ($) {

    var Menu = function (element, options) {
        this.$element = element;
        this.options = $.extend({}, $.fn.sidebarMenu.defaults, options)
    };


    Menu.prototype = {
        // 初始化菜单
        init : function () {

            var menus = this.options.menus;

            if (menus === undefined || menus.length === 0) {
                throw new Error("请先设置菜单menus属性");
            }
            // 创建菜单
            this.createSidebar();
            // 绑定事件
            this.bindEvents();
        },

        // 创建菜单
        createSidebar : function() {
            var element = this.$element;
            var menus = this.options.menus;
            var customizeName = this.options.customizeName;

            var ul_ = $('<ul class="navbar-nav pt-lg-3"></ul>');
            for (var i = 0; i < menus.length; i++) {
                // 资源菜单对象
                var menu = menus[i];

                var li_ = $('<li class="nav-item"></li>');
                li_.attr("id", this.options.idPrefix + menu[customizeName.id]);
                var div_ = $('<div class="nav-link"></div>');
                // 图标
                var icon_ = $('<span class="nav-link-icon d-md-none d-lg-inline-block"></span>');
                icon_.append(menu[customizeName.icon]);
                // 标题
                var name = $('<span class="nav-link-title"></span>');
                name.append(menu[customizeName.name]);
                // 添加进div容器
                div_.append(icon_);
                div_.append(name);
                li_.append(div_);
                // 判断是否存在孩子节点
                if (menu[customizeName.children] && menu[customizeName.children].length > 0) {
                    li_.addClass("dropdown");
                    div_.addClass("dropdown-toggle");
                    div_.attr("nav-dropdown-click", true);
                    div_.attr("aria-expanded", false);

                    var childrenDiv_ = this.createSidebarChildren(menu[customizeName.children], customizeName);
                    li_.append(childrenDiv_);
                } else {
                    // 不存在孩子节点，则添加点击绑定属性
                    li_.attr("nav-click", true);
                }
                // 赋值对象信息
                li_.data(menu);
                ul_.append(li_);
            }
            element.append(ul_);
        },
        // 创建孩子菜单(二级)
        createSidebarChildren(menus) {
            var customizeName = this.options.customizeName;

            var div_ = $('<div class="dropdown-menu"></div>');
            for (var i = 0; i < menus.length; i++) {
                var menu = menus[i];
                var div_item_ = $('<div class="dropdown-item"></div>');
                div_item_.attr("id", this.options.idPrefix + menu[customizeName.id]);
                div_item_.append(menu[customizeName.name]);
                div_item_.attr("nav-click", true);
                // 赋值对象信息
                div_item_.data(menu);
                div_.append(div_item_);
            }
            return div_;
        },
        // 绑定各种事件
        bindEvents : function() {
            var customizeName = this.options.customizeName;
            var idPrefix = this.options.idPrefix;
            var breadcrumbId = this.options.breadcrumbId;
            // 绑定无子菜单的点击事件
            $("[nav-click=true]").click(function () {
                var id = $(this).attr("id");
                var click_e_ =  $("#" + id);
                // 点击已点击菜单则不执行任何操作
                if (click_e_.hasClass("active")) {
                    return;
                }
                // 移除激活状态
                $("[id*=" + idPrefix + "]").removeClass("active");

                // 激活点击菜单
                click_e_.addClass('active');
                click_e_.parent().prev().attr("aria-expanded", true);
                click_e_.parent().parent().addClass('active');

                // 面包屑设置
                var ol_ = $('<ol class="breadcrumb"></ol>');
                ol_.attr("aria-label", "breadcrumbs");
                if (click_e_.hasClass("dropdown-item")) {
                    // 点击是二级
                    ol_.append('<li class="breadcrumb-item">' + click_e_.parent().parent().data(customizeName.name) + '</li>');
                }
                ol_.append('<li class="breadcrumb-item active" aria-current="page">' + click_e_.data(customizeName.name) + '</li>');

                var breadcrumb_ = $("#" + breadcrumbId);
                breadcrumb_.animate({left: '50px', opacity: 'toggle'}, "fast", function () {
                    breadcrumb_.empty();
                    breadcrumb_.append(ol_);
                    breadcrumb_.animate({left: '0', opacity: 'toggle'}, "fast");
                });
            });

            // 绑定存在子菜单的点击事件
            $("[nav-dropdown-click=true]").click(function () {
                $(this).next().slideToggle(200);
            })
        },
        // 菜单点击事件
        pageLoad: function() {

        },
        // 获取menu实例
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


    // 默认属性
    $.fn.sidebarMenu.defaults = {
        // 菜单列表
        menus : undefined,
        // 自定义属性别名
        customizeName: {
            id: "id",
            name: "name",
            url: "url",
            icon: "icon",
            children: "children"
        },
        // id扩展前缀
        idPrefix: "nav_",
        // 面包屑id
        breadcrumbId: "breadcrumb-menu"
    };

})(jQuery);







