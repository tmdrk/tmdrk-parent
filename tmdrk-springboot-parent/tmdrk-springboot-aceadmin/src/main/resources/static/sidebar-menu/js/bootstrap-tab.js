var addTabs = function (options) {
    //var rand = Math.random().toString();
    //var id = rand.substring(rand.indexOf('.') + 1);
    var url = window.location.protocol + '//' + window.location.host;
    options.url = url +'/'+ options.url;
    id = "tab_" + options.id;
	var active_flag = false;
	if($("#" + id)){
		active_flag = $("#" + id).hasClass('active');
	}
    //$(".tab-pane.active").removeClass("active");
    $('[role="main-tabpanel"]').removeClass("active");
    //如果TAB不存在，创建一个新的TAB
    if (!$("#" + id)[0]) {
        //固定TAB中IFRAME高度
        mainHeight = $(document.body).height() - 90;
        //创建新TAB的title
        title = '<li class="tab-pane" role="main-tabpanel" id="tab_' + id + '"><a href="#' + id + '" aria-controls="' + id + '" role="tab" data-toggle="tab">' + options.title;
        //是否允许关闭
        if (options.close) {
            title += ' <i class="glyphicon glyphicon-remove" tabclose="' + id + '"></i>';
        }
        title += '</a></li>';
        //是否指定TAB内容
        if (options.content) {
            content = '<div role="main-tabpanel" class="tab-pane" id="' + id + '" style="overflow:hidden;height:100%">' + options.content + '</div>';
        } else {//没有内容，使用IFRAME打开链接
            /*content = '<div role="tabpanel" class="tab-pane" id="' + id + '" style="height:100%"><iframe id="iframe_'+id+'" src="' + options.url + 
				'" width="100%" height="100%" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="yes" allowtransparency="yes"></iframe></div>';*/
        	//没有 ? 就 ?tabId=id,如果有 ? 就加 &tabId=id
        	content = '<div role="main-tabpanel" class="tab-pane" id="' + id + '" style="overflow:hidden;height:100%"><script>'+
            '$( "#'+id+'").load("'+options.url+((options.url.indexOf("?") > -1)?'&':'?')+'tabId='+id+'")'+
            '</script></div>';
        }
        //加入TABS
        $("#main_nav-tabs").append(title);
        $("#main_tab-content").append(content);
    }else{
		if(active_flag){
	        //加入TABS
			$("#" + id).empty();
			//没有 ? 就 ?tabId=id,如果有 ? 就加 &tabId=id
	        $("#" + id).append('<script>'+'$( "#'+id+'").load("' + options.url+((options.url.indexOf("?") > -1)?'&':'?') + 'tabId='+id+ '")'+'</script>');
		}
	}
    //激活TAB
    $("#tab_" + id).addClass('active');
    $("#" + id).addClass("active");
};
var closeTab = function (id) {
    //如果关闭的是当前激活的TAB，激活他的前一个TAB
    if ($("li.active").attr('id') == "tab_" + id) {
        $("#tab_" + id).prev().addClass('active');
        $("#" + id).prev().addClass('active');
    }
    //关闭TAB
    $("#tab_" + id).remove();
    $("#" + id).remove();
};
$(function () {
    mainHeight = $(document.body).height() - 45;
    $('.main-left,.main-right').height(mainHeight);
    $("[addtabs]").click(function () {
        addTabs({ id: $(this).attr("id"), title: $(this).attr('title'), close: true });
    });

    $(".nav-tabs").on("click", "[tabclose]", function (e) {
        id = $(this).attr("tabclose");
        closeTab(id);
    });
    $(".nav-tabs").on("mouseover","[tabclose]",function(e){
        $(this).css({color:"red",cursor:"pointer"});
    });
    $(".nav-tabs").on("mouseout","[tabclose]",function(e){
        $(this).css({color:"#4c8fbd"});
    });
   
});