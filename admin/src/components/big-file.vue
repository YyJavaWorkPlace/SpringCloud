<template>
    <div>
        <button type="button" v-on:click="selectFile()" class="btn btn-white btn-default btn-round">
            <i class="ace-icon fa fa-upload"></i>
            {{text}}
        </button>
        <input class="hidden" type="file" ref="file" v-on:change="uploadFile()" v-bind:id="inputId+'-input'">
    </div>
</template>
<script>
    export default {
        name: 'big-file',
        // 定义父组件向子组件传递的参数 可以是一个函数或者数据，本组件中暴露了两个参数list和itemCount给外部
        props: {
            text: {
                default: "上传文件"
            },
            inputId: {
                default: "file-upload"
            },
            suffixs: {
                default: []
            },
            afterUpload: {
                type: Function,
                default: null
            },
            use: {
                default: ""
            }
        },
        data: function () {
            //组件内部变量定义
            return {}
        },
        methods: {
            /**
             * 文件上传
             */
            uploadFile() {
                let _this = this;
                // let formData = new window.FormData();
                // key :"file"必须和后端controller参数名一致
                let file = _this.$refs.file.files[0];

                let key = hex_md5(file.name + file.size + file.type); //md5
                let key10 = parseInt(key, 16); // 转十进制
                let key62 = Tool._10to62(key10); //转62 26小字母+26大字母+10阿拉伯

                //判断文件上传格式
                let suffixs = _this.suffixs;
                let fileName = file.name;
                //获取上传文件的文件名后缀
                let suffix = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length).toLocaleLowerCase();
                let validateSuffix = false;
                for (let i = 0; i < suffixs.length; i++) {
                    console.log(suffix);
                    if (suffixs[i].toLocaleLowerCase() == suffix) {
                        validateSuffix = true;
                        break;
                    }
                }
                //校验未通过
                if (!validateSuffix) {
                    Toast.warning("文件格式不正确，仅支持上传" + suffixs.join(","));
                    $("#" + _this.inputId + "-input").val("");
                    return;
                }

                //文件分片
                let shardSize = 10 * 1024 * 1024; //以20MB为一个分片
                let shardIndex = 1;//分片索引
                let start = (shardIndex - 1) * shardSize; //当前分片的起始位置
                let end = Math.min(file.size, start + shardSize);//当前分片结束位置 1-20 20-40
                let fileShard = file.slice(start, end);//对文件进行截取
                let size = file.size; //文件大小
                let shardTotal = Math.ceil(size / shardSize);//总分片数
                //key: "shard" 必须和后缀controller参数名保持一致
                // formData.append("shard", fileShard);
                // formData.append("shardIndex", shardIndex);
                // formData.append("shardSize", shardSize);
                // formData.append("shardTotal", shardTotal);
                // formData.append("use", _this.use);
                // formData.append("name", file.name);
                // formData.append("suffix", suffix);
                // formData.append("size", size);
                // formData.append("key", key62);
                //将图片转成base64进行传输 先写数据监听
                let fileReader = new FileReader();
                fileReader.onload = function (e) {
                    let base64 = e.target.result;
                    console.log(base64);
                    let param = {
                        "shard":base64,
                        "shardIndex": shardIndex,
                        "shardSize":shardSize,
                        "shardTotal":shardTotal,
                        "use":_this.use,
                        "name":file.name,
                        "suffix":suffix,
                        "size":size,
                        "key": key62
                    };
                    Loading.show();
                    _this.$ajax.post(process.env.VUE_APP_SERVER + '/file/admin/upload', param ).then((response) => {
                        Loading.hide();
                        let resp = response.data;
                        console.log("上传文件地址：", resp);
                        _this.afterUpload(resp);
                        $("#" + _this.inputId + "-input").val("");
                    });
                };
                fileReader.readAsDataURL(fileShard);
            },
            selectFile() {
                let _this = this;
                $("#" + _this.inputId + "-input").trigger("click");
            }
        }
    }
</script>
