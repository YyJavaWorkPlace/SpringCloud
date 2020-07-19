<template>
    <div>
        <div class="row" style="margin-bottom: 10px;">
            <div class="col-xs-12 label label-lg label-info arrowed-in arrowed-right">
                <b>{{course.name}}---{{chapter.name}}</b>
            </div>
        </div>
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
        <table id="simple-table" class="table  table-bordered table-hover">
            <thead>
            <tr>
                <th>ID</th>
                <th>标题</th>
                <th>视频地址</th>
                <th>时长</th>
                <th>收费</th>
                <th>顺序</th>
                <th>创建时间</th>
                <th>操作</th>
            </tr>
            </thead>

            <tbody>
            <tr v-for="section in sections" :key="section.id">
                <td> {{section.id}}</td>
                <td> {{section.title}}</td>
                <td> {{section.video}}</td>
                <td> {{section.time|formatSecond}}</td>
                <td> {{SECTION_CHARGE | optionArray(section.charge)}}</td>
                <td> {{section.sort}}</td>
                <td> {{section.createdAt}}</td>

                <td>
                    <div class="hidden-sm hidden-xs btn-group">
                        <button v-on:click="edit(section)" class="btn btn-xs btn-info">
                            <i class="ace-icon fa fa-pencil bigger-120"></i>
                        </button>
                        <button v-on:click="del(section.id)" class="btn btn-xs btn-danger">
                            <i class="ace-icon fa fa-trash-o bigger-120"></i>
                        </button>
                    </div>
                </td>
            </tr>
            </tbody>
        </table>
        <div id="form-model" class="modal fade" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">新增小节</h4>
                    </div>
                    <div class="modal-body">
                        <form>
                            <div class="form-group">
                                <label>标题</label>
                                <input v-model="section.title" type="text" class="form-control">
                            </div>
                            <div class="form-group">
                                <label>课程</label>
                                <p class="form-control-static"> {{course.name}}</p>
                            </div>
                            <div class="form-group">
                                <label>大章</label>
                                <p class="form-control-static">{{chapter.name}}</p>
                            </div>
                            <div class="form-group">
                                <label>视频地址</label>
                                <input v-model="section.video" type="text" class="form-control">
                            </div>
                            <div class="form-group">
                                <label>时长</label>
                                <input v-model="section.time" type="text" class="form-control">
                            </div>
                            <div class="form-group">
                                <label>收费</label>
                                <select v-model="section.charge" class="form-control">
                                    <option v-for="o in SECTION_CHARGE" :key="o.num" v-bind:value="o.key">{{o.value}}
                                    </option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>顺序</label>
                                <input v-model="section.sort" type="text" class="form-control">
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
    </div>
</template>
<script>
    import Pagination from "../../components/pagination";

    export default {
        name: "section",
        components: {Pagination},
        data: function () {
            // 不使用return包裹的数据会在项目的全局可见，会造成变量污染；使用return包裹后数据中变量只在当前组件中生效，不会影响其他组件.
            return {
                section: {},
                sections: [],
                SECTION_CHARGE: SECTION_CHARGE,
                chapter: {},
                course: {},
            }
        },
        mounted: function () {
            let _this = this;
            let chapter = SessionStorage.get(SESSION_KEY_CHAPTER) || {};
            let course = SessionStorage.get(SESSION_KEY_COURSE) || {};
            if (Tool.isEmpty(chapter) || Tool.isEmpty(course)) {
                _this.$router.push("/welcome")
            }
            _this.chapter = chapter;
            _this.course = course;
            _this.list(1);
            this.$parent.activeSidebar("business-course-sidebar")
        },
        methods: {
            list(page) {
                //等待框
                Loading.show();
                let _this = this;
                _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/section/list', {
                    page: page,
                    size: _this.$refs.pagination.size,
                    chapterId: _this.chapter.id,
                    courseId: _this.course.id,
                }).then((response) => {
                    Loading.hide();
                    let resp = response.data;
                    _this.sections = resp.content.list;
                    _this.$refs.pagination.render(page, resp.content.total);
                })
            },
            add() {
                let _this = this;
                _this.section = {};
                $("#form-model").modal("show");
            },
            edit(section) {
                let _this = this;
                //修改的内容不会影响到原section 所以点击取消后数据不会发生更改
                _this.section = $.extend({}, section);
                $("#form-model").modal("show");
            },
            save() {
                let _this = this;
                if (1 != 1
                    || !Validator.require(_this.section.title, "标题")
                    || !Validator.length(_this.section.title, "标题", 1, "50")
                    || !Validator.length(_this.section.video, "视频地址", 1, "200")
                ) {
                    return;
                }
                _this.section.courseId = _this.course.id;
                _this.section.chapterId = _this.chapter.id;
                Loading.show();
                _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/section/save', _this.section
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
                })
            },
            del(id) {
                let _this = this;
                Confirm.show("删除后不可回复,确认删除?", function () {
                    Loading.show();
                    _this.$ajax.delete(process.env.VUE_APP_SERVER + '/business/admin/section/delete/' + id).then((response) => {
                        Loading.hide();
                        let resp = response.data;
                        if (resp.success) {
                            _this.list(1);
                            Toast.error("删除成功");
                        }
                    });
                });
            }
        }
    }
</script>