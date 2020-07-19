<template>
    <div>
        <p>
            <button @click="list()" class="btn btn-warning btn-xs">
                <i class="ace-icon fa fa-refresh  bigger-110 icon-only"></i>
                刷新
            </button>
            &nbsp;
            <button @click="add()" class="btn btn-warning btn-xs">
                <i class="ace-icon fa fa-edit bigger-110 icon-only"></i>
                新增
            </button>
        </p>
        <pagination ref="pagination" v-bind:list="list"></pagination>

        <div class="row">
            <div v-for="course in courses" :key="course.id" class="col-md-3">
                <div class="thumbnail search-thumbnail">
                    <img v-show="!course.image" class="media-object" src="/static/image/demo-course.jpg"/>
                    <img v-show="course.image" class="media-object" v-bind:src="course.image"/>
                    <div class="caption">
                        <div class="clearfix">
                               <span class="pull-right label label-warning info-label">
                                {{COURSE_CHARGE|optionArray(course.charge)}}
                            </span>
                            <span class="pull-right label label-danger info-label"
                                  style="float: left; position:absolute">
                                {{COURSE_LEVEL|optionArray(course.level)}}
                            </span>
                            <span class="pull-right label label-info info-label">
                               {{COURSE_STATUS|optionArray(course.status)}}
                            </span>
                        </div>

                        <h3 class="search-title">
                            <a href="#" class="blue">{{course.name}}</a>
                        </h3>
                        <p>
                            <span class="blue bloder bigger-150">{{course.price}} <i class="fa fa-rmb"></i> </span>
                        </p>
                        <p>{{course.summary}}</p>
                        <p class="labelGroup">
                            <span class="label label-yellow arrowed">{{course.id}}</span>
                            <span class="label label-yellow  arrowed">排序:{{course.sort}}</span>
                            <span class="label label-yellow  arrowed">时长:{{course.time}}</span>
                        </p>
                        <p>
                            <button v-on:click="toChapter(course)" type="button" class="btn btn-white btn-pink btn-sm">
                                大章
                            </button>&nbsp;
                            <button v-on:click="edit(course)" type="button" class="btn btn-white btn-pink btn-sm">编辑
                            </button>&nbsp;
                            <button v-on:click="editContent(course)" type="button"
                                    class="btn btn-white btn-pink btn-sm">内容
                            </button>&nbsp;
                            <button v-on:click="openSortModal(course)" type="button"
                                    class="btn btn-white btn-pink btn-sm">排序
                            </button>&nbsp;
                            <button v-on:click="del(course.id)" type="button" class="btn btn-white btn-pink btn-sm">删除
                            </button>&nbsp;
                        </p>
                    </div>
                </div>
            </div>
        </div>

        <!--        <table id="simple-table" class="table  table-bordered table-hover">-->
        <!--            <thead>-->
        <!--            <tr>-->
        <!--                <th>id</th>-->
        <!--                <th>名称</th>-->
        <!--                <th>概述</th>-->
        <!--                <th>时长</th>-->
        <!--                <th>价格(元)</th>-->
        <!--                <th>封面</th>-->
        <!--                <th>级别</th>-->
        <!--                <th>收费</th>-->
        <!--                <th>状态</th>-->
        <!--                <th>报名数量</th>-->
        <!--                <th>顺序</th>-->
        <!--                <th>创建时间</th>-->
        <!--                <th>操作</th>-->
        <!--            </tr>-->
        <!--            </thead>-->

        <!--            <tbody>-->
        <!--            <tr v-for="course in courses" :key="course.id">-->
        <!--                <td> {{course.id}}</td>-->
        <!--                <td> {{course.name}}</td>-->
        <!--                <td> {{course.summary}}</td>-->
        <!--                <td> {{course.time}}</td>-->
        <!--                <td> {{course.price}}</td>-->
        <!--                <td> {{course.image}}</td>-->
        <!--                <td>{{COURSE_LEVEL|optionArray(course.level)}}</td>-->
        <!--                <td>{{COURSE_CHARGE|optionArray(course.charge)}}</td>-->
        <!--                <td>{{COURSE_STATUS|optionArray(course.status)}}</td>-->
        <!--                <td> {{course.enroll}}</td>-->
        <!--                <td> {{course.sort}}</td>-->
        <!--                <td> {{course.createdAt}}</td>-->
        <!--                <td>-->
        <!--                    <div class="hidden-sm hidden-xs btn-group">-->
        <!--                        <button v-on:click="edit(course)" class="btn btn-xs btn-info">-->
        <!--                            <i class="ace-icon fa fa-pencil bigger-120"></i>-->
        <!--                        </button>-->
        <!--                        <button v-on:click="del(course.id)" class="btn btn-xs btn-danger">-->
        <!--                            <i class="ace-icon fa fa-trash-o bigger-120"></i>-->
        <!--                        </button>-->
        <!--                    </div>-->
        <!--                </td>-->
        <!--            </tr>-->
        <!--            </tbody>-->
        <!--        </table>-->
        <div id="form-model" class="modal fade" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">新增课程</h4>
                    </div>
                    <div class="modal-body">
                        <form>
                            <div class="form-group">
                                <label>分类</label>
                                <div class="col-ms-10">
                                    <ul id="treeDemo" class="ztree"></ul>
                                </div>
                            </div>
                            <div class="form-group">
                                <label>名称</label>
                                <input v-model="course.name" type="text"
                                       class="form-control">
                            </div>
                            <div class="form-group">
                                <label>讲师</label>
                                <select v-model="course.teacherId" class="form-control">
                                    <option v-for="o in teachers" :key="o.num"
                                            v-bind:value="o.id">{{o.name}}
                                    </option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>概述</label>
                                <input v-model="course.summary" type="text"
                                       class="form-control">
                            </div>
                            <div class="form-group">
                                <label>时长</label>
                                <input v-model="course.time" type="text"
                                       class="form-control">
                            </div>
                            <div class="form-group">
                                <label>价格(元)</label>
                                <input v-model="course.price" type="text"
                                       class="form-control">
                            </div>
                            <div class="form-group">
                                <label>封面</label>
                                <input v-model="course.image" type="text"
                                       class="form-control">
                            </div>
                            <div class="form-group">
                                <label>级别</label>
                                <select v-model="course.level" class="form-control">
                                    <option v-for="o in COURSE_LEVEL" :key="o.num"
                                            v-bind:value="o.key">{{o.value}}
                                    </option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>收费</label>
                                <select v-model="course.charge" class="form-control">
                                    <option v-for="o in COURSE_CHARGE" :key="o.num"
                                            v-bind:value="o.key">{{o.value}}
                                    </option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>状态</label>
                                <select v-model="course.status" class="form-control">
                                    <option v-for="o in COURSE_STATUS" :key="o.num"
                                            v-bind:value="o.key">{{o.value}}
                                    </option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>报名数量</label>
                                <input v-model="course.enroll" type="text"
                                       class="form-control">
                            </div>
                            <div class="form-group">
                                <label>顺序</label>
                                <input disabled v-model="course.sort" type="text"
                                       class="form-control">
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                        <button v-on:click="save()" type="button" class="btn btn-primary">保存</button>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->
        <div id="course-content-modal" class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog"
             aria-labelledby="myLargeModalLabel">
            <div class="modal-dialog modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="myModalLabel">课程内容编辑</h4>
                    </div>
                    <div class="modal-body">
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
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                        <button type="button" class="btn btn-primary" v-on:click="saveContent()">保存</button>
                    </div>
                </div>
            </div>
        </div>
        <div id="course-sort-modal" class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog"
             aria-labelledby="myLargeModalLabel">
            <div class="modal-dialog modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="myModalLabel">课程排序</h4>
                    </div>
                    <div class="modal-body">
                        <form class="form-horizontal">
                            <div class="form-group">
                                <label class="control-label col-lg-3">
                                    当前排序
                                </label>
                                <div class="col-lg-9">
                                    <input class="form-control" v-model="sort.oldSort" name="oldSort" disabled>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-lg-3">
                                    新排序
                                </label>
                                <div class="col-lg-9">
                                    <input class="form-control" v-model="sort.newSort" name="newSort">
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                        <button type="button" class="btn btn-primary" v-on:click="updateSort()">更新排序</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
    import Pagination from "../../components/pagination";

    export default {
        name: "business-course",
        components: {Pagination},
        data: function () {
            // 不使用return包裹的数据会在项目的全局可见，会造成变量污染；使用return包裹后数据中变量只在当前组件中生效，不会影响其他组件.
            return {
                course: {},
                courses: [],
                COURSE_LEVEL: COURSE_LEVEL,
                COURSE_CHARGE: COURSE_CHARGE,
                COURSE_STATUS: COURSE_STATUS,
                category: [],
                tree: {},
                saveContentLable: "",
                sort: {
                    id: "",
                    oldSort: 0,
                    newSort: 0
                },
                //页面打开加载所有的讲师
                teachers: [],
            }
        },
        mounted: function () {
            let _this = this;

            _this.allCategory();
            _this.allTeacher();
            _this.list(1);
        },
        methods: {
            list(page) {
                //等待框
                Loading.show();
                let _this = this;
                _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/course/list', {
                    page: page,
                    size: _this.$refs.pagination.size,
                }).then((response) => {
                    Loading.hide();
                    let resp = response.data;
                    _this.courses = resp.content.list;
                    _this.$refs.pagination.render(page, resp.content.total);
                })
            },
            add() {
                let _this = this;
                _this.course = {
                    sort: _this.$refs.pagination.total + 1,
                };
                _this.tree.checkAllNodes(false);
                $("#form-model").modal("show");
            },
            edit(course) {
                let _this = this;
                //修改的内容不会影响到原course 所以点击取消后数据不会发生更改
                _this.course = $.extend({}, course);
                //查找应该选中的节点
                _this.listCategory(course.id);
                $("#form-model").modal("show");
            },
            save() {
                let _this = this;
                if (1 != 1
                    || !Validator.require(_this.course.name, "名称")
                    || !Validator.length(_this.course.name, "名称", 1, "50")
                    || !Validator.length(_this.course.summary, "概述", 1, "2000")
                    || !Validator.length(_this.course.image, "封面", 1, "100")
                ) {
                    return;
                }

                let categorys = _this.tree.getCheckedNodes();
                if (Tool.isEmpty(categorys)) {
                    Toast.warning("请选择分类!");
                    return;
                }
                _this.course.categorys = categorys;
                Loading.show();
                _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/course/save', _this.course
                ).then((response) => {
                    Loading.hide();
                    let resp = response.data;
                    if (resp.success) {
                        $("#form-model").modal("hide");
                        _this.list(1);
                        Toast.success("保存成功");
                    } else {
                        Toast.warning(resp.message);
                    }
                });
            },
            del(id) {
                let _this = this;
                Confirm.show("删除后不可回复,确认删除?", function () {
                    Loading.show();
                    _this.$ajax.delete(process.env.VUE_APP_SERVER + '/business/admin/course/delete/' + id).then((response) => {
                        Loading.hide();
                        let resp = response.data;
                        if (resp.success) {
                            _this.list(1);
                            Toast.error("删除成功");
                        }
                    });
                });
            },
            /**
             * 点击大章
             * */
            // 组件(页面)间传输数据可以用h5原生localStorage sessionStorage 也可以用js全局变量 vuex store 最后两者刷新后会丢失
            toChapter(course) {
                let _this = this;
                SessionStorage.set(SESSION_KEY_COURSE, course);
                _this.$router.push("/business/chapter");
            },
            allCategory() {
                //等待框
                Loading.show();
                let _this = this;
                _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/category/all', {}).then((response) => {
                    Loading.hide();
                    let resp = response.data;
                    _this.category = resp.content;
                    _this.initTree();
                })
            },
            initTree() {
                let _this = this;
                let setting = {
                    check: {
                        enable: true
                    },
                    data: {
                        simpleData: {
                            idKey: "id",
                            pIdKey: "parent",
                            rootPId: "00000000",
                            enable: true
                        }
                    }
                };

                var zNodes = _this.category;

                var code;

                _this.tree = $.fn.zTree.init($("#treeDemo"), setting, zNodes);
                //展开所有节点
                // _this.tree.expandAll(true);
            },


            listCategory(courseId) {
                let _this = this;
                Loading.show();
                _this.$ajax.post(process.env.VUE_APP_SERVER + "/business/admin/course/list-category/" + courseId).then((res) => {
                    Loading.hide();
                    console.log("查找课程下所有分类结果", res);
                    let response = res.data;
                    let categorys = response.content;
                    //勾选查询到的分类
                    _this.tree.checkAllNodes(false);
                    for (let i = 0; i < categorys.length; i++) {
                        //根据参数获取节点对象
                        let node = _this.tree.getNodeByParam("id", categorys[i].categoryId);
                        //勾选以获取树节点
                        _this.tree.checkNode(node, true);
                    }
                });
            },
            /**
             * 打开内容编辑页
             * @param course
             */
            editContent(course) {
                let _this = this;
                let id = course.id;
                _this.course = course;
                $("#content").summernote({
                    focus: true,
                    height: 300
                });
                //先清空上一次富文本内容
                $("#content").summernote('code', '');
                //清空时间
                _this.saveContentLable = "";
                //等待准备Ajax请求
                Loading.show();
                _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/course/find-content/' + id).then((response) => {
                    Loading.hide();
                    let resp = response.data;
                    if (resp.success) {
                        //static 表示模态框不会点空白处被关闭
                        $("#course-content-modal").modal({backdrop: 'static', keyboard: false});
                        if (resp.content) {
                            $("#content").summernote('code', resp.content.content);
                        }
                        //定时自动保存  setInterval 重复的定时任务 setTimeout只执行一次的定时任务
                        let saveContentInterval = setInterval(function () {
                            _this.saveContent();
                        }, 10000);
                        //关闭模态框后 清空自动保存任务清除上面的变量即可
                        $("#course-content-modal").on('hidden.bs.modal', function (e) {
                            clearInterval(saveContentInterval);
                        });
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
                        let now = Tool.dataFormat("mm:ss");
                        _this.saveContentLable = "最后保存时间" + now;
                    } else {
                        Toast.error(resp.message);
                    }
                });
            },
            /**
             * 打开模态框 设置初始值
             * @param course
             */
            openSortModal(course) {
                let _this = this;
                _this.sort = {
                    id: course.id,
                    oldSort: course.sort,
                    newSort: course.sort
                };
                $("#course-sort-modal").modal("show");
            },
            /**
             * 更新排序
             */
            updateSort() {
                let _this = this;
                if (_this.sort.newSort === _this.sort.oldSort) {
                    Toast.warning("排序没有变化");
                    return;
                }
                Loading.show();
                _this.$ajax.post(process.env.VUE_APP_SERVER + "/business/admin/course/sort-course", _this.sort).then((res) => {
                    let response = res.data;
                    if (response.success) {
                        Toast.success("更新排序成功");
                        $("#course-sort-modal").modal("hide");
                        _this.list(1);
                    } else {
                        Toast.error("更新排序失败");
                    }
                });
            },
            allTeacher() {
                //等待框
                Loading.show();
                let _this = this;
                _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/teacher/all', {}).then((response) => {
                    Loading.hide();
                    let resp = response.data;
                    _this.teachers = resp.content;
                })
            },
        }
    }
</script>
<style scoped>
    .search-title a {
        font-size: 17px;
        font-weight: bold;
    }

    /*.labelGroup span{*/
    /*    margin-left: 10px;*/
    /*}*/
</style>