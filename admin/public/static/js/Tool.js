Tool = {
    /**
     * 空校验 null或""都返回true
     * parse用于从一个字符串中解析出json对象
     * stringify用于从一个对象解析出字符串，如
     */
    isEmpty: function (obj) {
        if ((typeof obj == 'string')) {
            return !obj || obj.replace(/\s+/g, "") == ""
        } else {
            return (!obj || JSON.stringify(obj) === "{}" || obj.length === 0);
        }
    },

    /**
     * 非空校验
     */
    isNotEmpty: function (obj) {
        return !this.isEmpty(obj);
    },

    /**
     * 长度校验
     */
    isLength: function (str, min, max) {
        return $.trim(str).length >= min && $.trim(str).length <= max;
    },
    /**
     * 时间格式化
     */
    dataFormat: function (format, date) {
        let result;
        if (!date) {
            date = new Date();
        }
        const option = {
            "y+": date.getFullYear().toString(),  //年
            "M+": (date.getMonth() + 1).toString(), //月
            "d+": (date.getDate()).toString(), //日
            "h+": date.getHours().toString(), //时
            "m+": date.getMinutes().toString(), //分
            "s+": date.getSeconds().toString() //秒
        };
        for (let i in option) {
            result = new RegExp("(" + i + ")").exec(format); //返回包含第一个匹配项信息的数组;或者在没有匹配项的情况下返回null.
            if (result) {
                format = format.replace(result[1], (result[1].length == 1) ? (option[i]) : (option[i].padStart(result[1].length, "0")));
            }
        }
        return format;
    },
    /**
     * 移除对象数组中的对象
     * @param array
     * @param obj
     */
    removeObj: function (array, obj) {
        let index = -1;
        for (let i = 0; i < array.length; i++) {
            if (arr[i] === obj) {
                //删除一个元素 该方法会改变原始数组
                array.splice(i, 1);
                index = i;
                break;
            }
        }
        return index;
    },


    /**
     * 10进制转62进制
     * @param number
     * @returns {string}
     * @private
     */
    _10to62: function (number) {
        let chars = '0123456789abcdefghigklmnopqrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ';
        let radix = chars.length;
        let arr = [];
        do {
            let mod = number % radix;
            number = (number - mod) / radix;
            arr.unshift(chars[mod]);
        } while (number);
        return arr.join('');
    },
};