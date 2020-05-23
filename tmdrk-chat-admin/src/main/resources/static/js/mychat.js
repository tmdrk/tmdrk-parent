function user_login() {
    $.ajax({
        url:"/login",
        type: 'POST',
        dataType: 'json',
        data: $("#loginForm ").serialize(),
        async : false,
        success: function(data){
            console.log(data)
            if(data.code==1){
                $('#login').hide();
                $('#main').show();
                $('#selfImg').attr('src',data.data.img);
                $('#nickName').html(data.data.name);
                myUserId = data.data.id;
                localStorage.setItem(data.data.id, JSON.stringify(data.data));
                var html = create_navigation(data.data.friends);
                $('#navigation').empty();
                $('#navigation').append(html);
                register(myUserId);
            }else{
                alert(data.msg);
            }
        }
    });
}
var myUserId;
function create_navigation(list) {
    var html = '';
    if (list.length>0) {
        for (var i = 0; i < list.length; i++) {
            localStorage.setItem(list[i].id, JSON.stringify(list[i]));
            html += '<div style="height:90px;border-bottom: solid black 1px;" class="ng-scope" onclick="showTable('+(i+1)+','+list[i].id+',\''+list[i].name+'\',\''+list[i].img+'\')">\n' +
                '                              <div class="chat_item slide-left ng-scope active">\n' +
                '                                  <div class="ng-scope1">\n' +
                '                                      <div class="avatar">\n' +
                '                                          <img class="img" src="'+list[i].img+'" alt="">\n' +
                '                                      </div>\n' +
                '                                      <div class="ng-scope2">\n' +
                '                                          <h3 class="nickname">\n' +
                '                                              <span class="nickname_text ng-binding">'+list[i].name+'</span>\n' +
                '                                          </h3>\n' +
                '                                      </div>\n' +
                '                                  </div>\n' +
                '                              </div>\n' +
                '                          </div>';
            if(list[i].messages&&list[i].messages.length>0){
                for (var j = 0; j< list[i].messages.length; j++) {
                    appendMessage(list[i].messages[j],0);
                }
            }
        }
    }
    return html;
}
function showTable(index,userId,name,img) {
    $('.msg_tab').hide();
    if($('#msg_tab_'+userId)[0]){
        $('#msg_tab_'+userId).show();
    }else{
        console.log("构建新的标签页")
        var html = create_tab(userId,name,img);
        $('#msg_navigation').append(html);
    }
}
function create_tab(userId,name,img) {
    html='<div class="box chat ng-scope no-choose msg_tab" id="msg_tab_'+userId+'">\n' +
        '                  <div class="box_hd">\n' +
        '                      <div class="title_wrap">\n' +
        '                          <div class="title poi">\n' +
        '                              <a class="title_name ng-binding">'+name+'</a>\n' +
        '                          </div>\n' +
        '                      </div>\n' +
        '                  </div>\n' +
        '                  <div id="" class="scroll-wrapper box_bd chat_bd scrollbar-dynamic" style="position: absolute;overflow-y:auto; min-height:200px;">\n' +
        '                  <div id="message_line"  style="overflow-y:auto; height: 100%;">\n' +
        '                  </div>\n' +
        '                  </div>\n' +
/*        '                  <div id="operate_line" class="operate_line" style="position:absolute;border:solid red 1px;">\n' +
        '                  </div>\n' +*/
        '                  <div class="box_ft ng-scope">\n' +
        '                      <div class="content ng-isolate-scope">\n' +
        '                          <textarea id="messageInfo"  placeholder="请输入文字" class="input_content"></textarea>\n' +
        '                      </div>\n' +
        '                      <div class="action">\n' +
        '                          <span class="desc ng-scope">按下Ctrl+Enter换行</span>\n' +
        '                          <a id="sendButton" class="btn btn_send" href="javascript:;" onclick="sendMessage('+myUserId+','+userId+')">发送</a>\n' +
        '                      </div>\n' +
        '                  </div>\n' +
        '              </div>';
    return html;

}

$(document).keyup(function(event){
    if(event.keyCode ==13){
        $("#doLogin").trigger("click");
    }
});
