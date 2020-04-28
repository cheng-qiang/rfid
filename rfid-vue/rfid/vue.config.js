/*配置请求转发代理*/
/*定义一个代理对象*/
let proxyObj = {};
/*拦截WebSocket请求,在线聊天模块*/
proxyObj['/ws'] = {
    ws: true,
    target: "ws://localhost:8082"
};
/*拦截http请求*/
proxyObj['/']={
    /*关闭websocket*/
    ws:false,
    /*代理转发目标地址*/
    target:'http://localhost:8082',
    changeOrigin: true,
    /*设置请求地址是否重写*/
    pathRewrite:{
        '^/':''
    }
};


/*导出*/
module.exports = {
    devServer:{
        host:'localhost',
        port:8080,
        proxy:proxyObj
    }
};
