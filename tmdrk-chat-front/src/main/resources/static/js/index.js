//查询
function queryIndexList(){
    $.ajax({
        url:"/stat/search",
        type: 'GET',
        dataType: 'json',
        data: $("#indexForm ").serialize(),
        async : false,
        success: function(data){
            if(data.code==1){

            }else{
                if(data.code==1001){
                    alert("查询失败，第三方接口调用失败，请稍后重试");
                }else{
                    alert("查询失败，请重置搜索条件查询");
                }
            }
        }
    });
}

function convertIndexList(data,fullHalf){

}

function resetForm(){

}