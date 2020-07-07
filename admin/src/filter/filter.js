/**
 * 对象过滤器
 * @param list 例如{CHANGE:{key:"C",value:"收费"},FREE:{…………}}
 * @param key C
 * @returns {string} 收费
 * 首先判断参数是否为空
 * 传入数组 和 key 最后返回
 */
let optionKV=(object,key)=>{
    console.log(object);
  if (!object||!key){
        return "";
  } else{
      let result="";
      for (let enums in object){
          if (key === object[enums]["key"]){
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
let optionArray=(list,key)=>{
    if (!list||!key){
        return "";
    } else{
        let result="";
        for (let i = 0;i<list.length;i++){
            if (key === list[i]["key"]){
                result = list[i]["value"];
            }
        }
        return result;
    }
};
export default {
    optionKV,
    optionArray
}