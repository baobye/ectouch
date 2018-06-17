function loadPic(e){
    if(isWechatBrow()==="wechat"){
        /*如果是微信浏览器,就注入微信jssdk*/
       wechatJsApi();
       //拍照或从手机相册中选图接口
       wx.chooseImage({
           count:5,//设置一次能选择的图片的数量 
           sizeType:['original','compressed'],//指定是原图还是压缩,默认二者都有
           sourceType:['album','camera'],//可以指定来源是相册还是相机,默认二者都有
           success:function(res){   //微信返回了一个资源对象 
　　　　　　　　　//res.localIds 是一个数组　保存了用户一次性选择的所有图片的信息　 　　　　　　　　
               images.localId=res.localIds;//把图片的路径保存在images[localId]中--图片本地的id信息，用于上传图片到微信浏览器时使用
               her.upNum+=res.localIds.length; 
               ulLoadToWechat(0); //把这些图片上传到微信服务器  一张一张的上传
            }
       });
    }else{
        //把上传图片的按钮换成input type=file
    }
};