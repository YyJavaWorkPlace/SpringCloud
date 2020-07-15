Loading = {
    // 显示等待框
    show: function () {
        $.blockUI({
            message: '<h1><img src="/static/image/loading.gif"/>',
            css: {
                padding: "5px",
                left: "50%",
                width: "90px",
                height: "90px",
                marginLeft: "-40px",
                borderRadius: "50%",
                opacity: "0.8"
            }
        });
    },
    // 关闭等待
    hide: function () {
        //本地查询速度太快 loading显示一瞬间,故意做一个延迟
        setTimeout(function () {
            $.unblockUI();
        }, 500)
    }
}