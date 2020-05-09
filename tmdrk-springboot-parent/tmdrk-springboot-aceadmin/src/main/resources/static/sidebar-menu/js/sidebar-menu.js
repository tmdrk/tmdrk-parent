(function ($) {
    $.fn.sidebarMenu = function (options) {
        options = $.extend({}, $.fn.sidebarMenu.defaults, options || {});
        var target = $(this);
        target.addClass('nav');
        target.addClass('nav-list');
        if (options.data) {
            init(target, options.data);
        }
        else {
            if (!options.url) return;
            $.getJSON(options.url, options.param, function (data) {
                init(target, data);
            });
        }
        var url = window.location.pathname;
        //menu = target.find("[href='" + url + "']");
        //menu.parent().addClass('active');
        //menu.parent().parentsUntil('.nav-list', 'li').addClass('active').addClass('open');
        function init(target, data) {
            $.each(data, function (i, item) {
                var li = $('<li></li>');
                var a = $('<a></a>');
                var icon = $('<i></i>');
                //icon.addClass('menu-icon fa fa-list');
                if(item.node.menuIcon.trim()==''){
                	icon.addClass('menu-icon fa fa-list');
                }else{
                	icon.addClass(item.node.menuIcon);
                }
                var text = $('<span></span>');
                text.addClass('menu-text').text(item.node.menuName);
                a.append(icon);
                a.append(text);
                if (item.childNodes&&item.childNodes.length>0) {
                    a.attr('href', '#');
                    a.addClass('dropdown-toggle');
                    var arrow = $('<b></b>');
                    arrow.addClass('arrow').addClass('fa fa-angle-down');
                    a.append(arrow);
                    li.append(a);
                    var menus = $('<ul></ul>');
                    menus.addClass('submenu');
                    init(menus, item.childNodes);
                    var b = $('<b></b>');
                    b.addClass('arrow')
                    li.append(b);
                    li.append(menus);
                }
                else {
                    var href = 'javascript:addTabs({id:\'' + item.node.menuId + '\',title: \'' + item.node.menuName + '\',close: true,url: \'' + item.node.menuUrl + '\'});';
                    a.attr('href', href);
                    //if (item.istab)
                    //    a.attr('href', href);
                    //else {
                    //    a.attr('href', item.url);
                    //    a.attr('title', item.text);
                    //    a.attr('target', '_blank')
                    //}
                    li.append(a);
                }
                target.append(li);
            });
        }
    }

    $.fn.sidebarMenu.defaults = {
        url: null,
        param: null,
        data: null
    };
})(jQuery);