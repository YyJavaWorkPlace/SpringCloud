/**
 * 对象过滤器
 * @param list 例如{CHANGE:{key:"C",value:"收费"},FREE:{…………}}
 * @param key C
 * @returns {string} 收费
 * 首先判断参数是否为空
 * 传入数组 和 key 最后返回
 */
let optionKV = (object, key) => {
    if (!object || !key) {
        return "";
    } else {
        let result = "";
        for (let enums in object) {
            if (key === object[enums]["key"]) {
                result = object[enums]["value"];
            }
        }
        return result;
    }
};

/**
 * 数组过滤器
 * @param list 例如[{key:"C",value:"收费"},{…………}]
 * @param key C
 * @returns {string} 收费
 * 首先判断参数是否为空
 * 传入数组 和 key 最后返回
 */
let optionArray = (list, key) => {
    if (!list || !key) {
        return "";
    } else {
        let result = "";
        for (let i = 0; i < list.length; i++) {
            if (key === list[i]["key"]) {
                result = list[i]["value"];
            }
        }
        return result;
    }
};
let formatSecond = (value) => {
    value = value || 0;
    let second = parseInt(value, 10); //十进制解析字符串 返回整数
    let minute = 0;//分
    let hour = 0;//小时
    if (second > 60) {
        minute = Math.floor(second / 60);
        second = Math.floor(second % 60);
        if (minute > 60) {
            hour = Math.floor(minute / 60);
            minute = Math.floor(minute % 60)
        }
    } else {
        //直接显示秒数
    }
    let result = "" + PrefixInteger(second, 2) + "";
    result = "" + PrefixInteger(minute, 2) + ":" + result;
    result = "" + PrefixInteger(hour, 2) + ":" + result;
    return result;
};
/**
 * 补0操作
 * @return {string}
 */
var PrefixInteger = function (num, length) {
    return (Array(length).join('0') + num).slice(-length);
};
/**
 * 格式化文件大小
 * @param value
 * @returns {string}
 */
let formatFileSize = (value) => {
    value = value || 0;
    let result;
    //大于100K 显示MB单位
    if (result > 100 * 1024) {
        result = Math.round((value / 1024 / 1024) * 100) / 100 + "MB";
    } else {
        result = Math.round((value / 1024) * 100) / 100 + "KB";
    }
    return result;
};

export default {
    optionKV,
    optionArray,
    formatSecond,
    formatFileSize
}