SESSION_KEY_COURSE = "SESSION_KEY_COURSE"; //课程管理页面点击章管理时 保存课程信息 显示大章
SESSION_KEY_CHAPTER = "SESSION_KEY_CHAPTER";//章节管理页面点击章节保存章的信息  显示小节
// 会话缓存,浏览器窗口关闭 缓存消失
SessionStorage = {
    get: function (key) {
        let v = sessionStorage.getItem(key);
        if (v && typeof (v) !== "undefined" && v !== "undefined") {
            return JSON.parse(v);
        }
    },
    set: function (key, data) {
        sessionStorage.setItem(key, JSON.stringify(data));
    },
    remove: function (key) {
        sessionStorage.removeItem(key);
    },
    clearAll: function () {
        sessionStorage.clear();
    }
};