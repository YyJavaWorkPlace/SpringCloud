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
        <table id="simple-table" class="table  table-bordered table-hover">
            <thead>
            <tr>
                <th>id</th>
                <th>姓名</th>
                <th>昵称</th>
                <th>头像</th>
                <th>职位</th>
                <th>座右铭</th>
                <th>简介</th>
                <th>操作</th>
            </tr>
            </thead>

            <tbody>
            <tr v-for="teacher in teachers" :key="teacher.id">
                <td> {{teacher.id}}</td>
                <td> {{teacher.name}}</td>
                <td> {{teacher.nickname}}</td>
                <td> {{teacher.image}}</td>
                <td> {{teacher.position}}</td>
                <td> {{teacher.motto}}</td>
                <td> {{teacher.intro}}</td>
                <td>
                    <div class="hidden-sm hidden-xs btn-group">
                        <button v-on:click="edit(teacher)" class="btn btn-xs btn-info">
                            <i class="ace-icon fa fa-pencil bigger-120"></i>
                        </button>
                        <button v-on:click="del(teacher.id)" class="btn btn-xs btn-danger">
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
                        <h4 class="modal-title">新增讲师</h4>
                    </div>
                    <div class="modal-body">
                        <form>
                            <div class="form-group">
                                <label>姓名</label>
                                <input v-model="teacher.name" type="text"
                                       class="form-control">
                            </div>
                            <div class="form-group">
                                <label>昵称</label>
                                <input v-model="teacher.nickname" type="text"
                                       class="form-control">
                            </div>
                            <div class="form-group">
                                <label>头像</label>
                                <input v-model="teacher.image" type="text"
                                       class="form-control">
                            </div>
                            <div class="form-group">
                                <label>职位</label>
                                <input v-model="teacher.position" type="text"
                                       class="form-control">
                            </div>
                            <div class="form-group">
                                <label>座右铭</label>
                                <input v-model="teacher.motto" type="text"
                                       class="form-control">
                            </div>
                            <div class="form-group">
                                <label>简介</label>
                                <input v-model="teacher.intro" type="text"
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
    </div>
</template>
<script>
    import Pagination from "../../components/pagination";

    export default {
        name: "business-teacher",
        components: {Pagination},
        data: function () {
            // 不使用return包裹的数据会在项目的全局可见，会造成变量污染；使用return包裹后数据中变量只在当前组件中生效，不会影响其他组件.
            return {
                teacher: {},
                teachers: [],
            }
        },
        mounted: function () {
            let _this = this;
            _this.list(1);
        },
        methods: {
            list(page) {
                //等待框
                Loading.show();
                let _this = this;
                _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/teacher/list', {
                    page: page,
                    size: _this.$refs.pagination.size,
                }).then((response) => {
                    Loading.hide();
                    let resp = response.data;
                    _this.teachers = resp.content.list;
                    _this.$refs.pagination.render(page, resp.content.total);
                })
            },
            add() {
                let _this = this;
                _this.teacher = {};
                $("#form-model").modal("show");
            },
            edit(teacher) {
                let _this = this;
                //修改的内容不会影响到原teacher 所以点击取消后数据不会发生更改
                _this.teacher = $.extend({}, teacher);
                $("#form-model").modal("show");
            },
            save() {
                let _this = this;
                if (1 != 1
                    || !Validator.require(_this.teacher.name, "姓名")
                    || !Validator.length(_this.teacher.name, "姓名", 1, "50")
                    || !Validator.length(_this.teacher.nickname, "昵称", 1, "50")
                    || !Validator.length(_this.teacher.image, "头像", 1, "100")
                    || !Validator.length(_this.teacher.position, "职位", 1, "50")
                    || !Validator.length(_this.teacher.motto, "座右铭", 1, "50")
                    || !Validator.length(_this.teacher.intro, "简介", 1, "500")
                ) {
                    return;
                }
                Loading.show();
                _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/teacher/save', _this.teacher
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
                    _this.$ajax.delete(process.env.VUE_APP_SERVER + '/business/admin/teacher/delete/' + id).then((response) => {
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