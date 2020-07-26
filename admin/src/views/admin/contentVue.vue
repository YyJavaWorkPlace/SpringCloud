<template>
    <div>
        <h4 class="lighter">
            <i class="ace-icon fa fa-hand-o-right icon-animated-hand-pointer blue"></i>
            <router-link to="/business/course" class="pink">{{course.name}}</router-link>
        </h4>
        <hr>
        <file v-bind:input-id="'content-file-upload'"
              v-bind:text="'上传文件'"
              v-bind:after-upload="afterUploadContentFile"
              v-bind:suffixs="['jpg','png','jpeg','mp4']"
              v-bind:use="FILE_USE[0].key"></file>
        <br>
        <table id="simple-table" class="table  table-bordered table-hover">
            <thead>
            <tr>
                <th>名称</th>
                <th>地址</th>
                <th>大小</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="(f,i) in files" v-bind:key="f.id">
                <td> {{f.name}}</td>
                <td> {{f.url}}</td>
                <td> {{f.size|formatFileSize}}</td>
                <td>
                    <button v-on:click="delFile(f)" class="btn btn-xs btn-danger">
                        <i class="ace-icon fa fa-trash-o bigger-120"></i>
                    </button>
                </td>
            </tr>
            </tbody>
        </table>

        <form class="form-horizontal">
            <div class="form-group">
                <div class="col-lg-12">
                    {{saveContentLable}}
                </div>
            </div>
            <div class="form-group">
                <div class="col-lg-12">
                    <div id="content"></div>
                </div>
            </div>
            <div class="form-group">
                <div class="col-lg-12">
                    {{saveContentLable}}
                </div>
            </div>
        </form>
        <router-link to="/business/course" type="button" class="btn btn-default" data-dismiss="modal">返回课程</router-link>
        <button type="button" class="btn btn-white btn-info btn-round" v-on:click="saveContent()">保存</button>
    </div>
</template>
<script>
    import File from "../../components/file";

    export default {
        name: "business-course-content",
        components: {File},
        data: function () {
            // 不使用return包裹的数据会在项目的全局可见，会造成变量污染；使用return包裹后数据中变量只在当前组件中生效，不会影响其他组件.
            return {
                course: {},
                FILE_USE: FILE_USE,
                saveContentLable: "",
                files: [],
                saveContentInterval: {},
            }
        },
        mounted: function () {
            let _this = this;
            let course = SessionStorage.get(SESSION_KEY_COURSE) || {};
            if (Tool.isEmpty(course)) {
                _this.$router.push("/welcome");
            }
            _this.course = course;

            _this.init();

            // sidebar激活样式方法一
            this.$parent.activeSidebar("business-course-sidebar");
        },
        destroyed: function () {
            let _this = this;
            console.log("组件销毁");
            clearInterval(_this.saveContentInterval);
        },
        methods: {
            init() {
                let _this = this;
                let course = _this.course;
                let id = course.id;
                $("#content").summernote({
                    focus: true,
                    height: 300
                });
                //先清空上一次富文本内容
                $("#content").summernote('code', '');
                //清空时间
                _this.saveContentLable = "";
                //加载内容文件列表
                _this.listContentFiles();
                //等待准备Ajax请求
                Loading.show();
                _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/course/find-content/' + id).then((response) => {
                    Loading.hide();
                    let resp = response.data;
                    if (resp.success) {
                        if (resp.content) {
                            $("#content").summernote('code', resp.content.content);
                        }
                        //定时自动保存  setInterval 重复的定时任务 setTimeout只执行一次的定时任务
                        _this.saveContentInterval = setInterval(function () {
                            _this.saveContent();
                        }, 10000);
                        // //关闭模态框后 清空自动保存任务清除上面的变量即可
                        // $("#course-content-modal").on('hidden.bs.modal', function (e) {
                        //     clearInterval(saveContentInterval);
                        // });
                    } else {
                        Toast.warning(resp.message);
                    }
                });
            },
            /**
             * 保存内容
             */
            saveContent() {
                let _this = this;
                let content = $("#content").summernote("code");
                _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/course/save-content', {
                    content: content,
                    id: _this.course.id,
                }).then((response) => {
                    Loading.hide();
                    let resp = response.data;
                    if (resp.success) {
                        // Toast.success("内容保存成功");
                        let now = Tool.dataFormat("yyyy-MM-dd hh:mm:ss");
                        _this.saveContentLable = "最后保存时间" + now;
                    } else {
                        Toast.error(resp.message);
                    }
                });
            },
            /**
             *加载内容文件列表
             * */
            listContentFiles() {
                let _this = this;
                _this.$ajax.get(process.env.VUE_APP_SERVER + '/business/admin/course-content-file/list/' + _this.course.id).then((response) => {
                    let resp = response.data;
                    if (resp.success) {
                        _this.files = resp.content;
                    }
                });
            },
            /**
             * 上传内容文件后,保存内容文件记录
             */
            afterUploadContentFile(response) {
                let _this = this;
                console.log("开始保存文件记录");
                let file = response.content;
                file.courseId = _this.course.id;
                file.url = file.path;
                _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/course-content-file/save', file).then((response) => {
                    let resp = response.data;
                    if (resp.success) {
                        Toast.success("上传文件成功");
                        _this.files.push(resp.content);
                    }
                })
            },
            /**
             * 删除内容文件
             * @param f
             */
            delFile(f) {
                let _this = this;
                Confirm.show("删除课程后不可回复,确认删除?", function () {
                    _this.$ajax.delete(process.env.VUE_APP_SERVER + '/business/admin/course-content-file/delete/' + f.id).then((response) => {
                        let resp = response.data;
                        if (resp.success) {
                            Toast.success("删除文件成功");
                            Tool.removeObj(_this.files, f);
                        }
                    });
                });
            },
        }
    }
</script>

