// 本地缓存 浏览器关闭后 下次再打开还能读到
LocalStoreage = {
    get: function (key) {
        let v = localStoreage.getItem(key);
        if (v && typeof (v) !== "undefined" && v !== "undefined") {
            return JSON.parse(v);
        }
    },
    set: function (key, data) {
        localStoreage.setItem(key, JSON.stringify(data));
    },
    remove: function (key) {
        localStoreage.removeItem(key);
    },
    clearAll: function () {
        localStoreage.clear();
    }
};