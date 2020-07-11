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
                        <th>名称</th>
                        <th>概述</th>
                        <th>时长</th>
                        <th>价格(元)</th>
                        <th>封面</th>
                        <th>级别</th>
                        <th>收费</th>
                        <th>状态</th>
                        <th>报名数量</th>
                        <th>顺序</th>
                        <th>创建时间</th>
                <th>操作</th>
            </tr>
            </thead>

            <tbody>
            <tr v-for="course in courses" :key="course.id">
                            <td> {{course.id}}</td>
                            <td> {{course.name}}</td>
                            <td> {{course.summary}}</td>
                            <td> {{course.time}}</td>
                            <td> {{course.price}}</td>
                            <td> {{course.image}}</td>
                            <td>{{COURSE_LEVEL|optionArray(course.level)}}</td>
                            <td>{{COURSE_CHARGE|optionArray(course.charge)}}</td>
                            <td>{{COURSE_STATUS|optionArray(course.status)}}</td>
                            <td> {{course.enroll}}</td>
                            <td> {{course.sort}}</td>
                            <td> {{course.createdAt}}</td>
                <td>
                    <div class="hidden-sm hidden-xs btn-group">
                        <button v-on:click="edit(course)" class="btn btn-xs btn-info">
                            <i class="ace-icon fa fa-pencil bigger-120"></i>
                        </button>
                        <button v-on:click="del(course.id)" class="btn btn-xs btn-danger">
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
                        <h4 class="modal-title">新增课程</h4>
                    </div>
                    <div class="modal-body">
                        <form>
                                        <div class="form-group">
                                            <label>名称</label>
                                            <input v-model="course.name" type="text"
                                                   class="form-control">
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
                                            <input v-model="course.sort" type="text"
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
        name: "business-course",
        components: {Pagination},
        data: function () {
            // 不使用return包裹的数据会在项目的全局可见，会造成变量污染；使用return包裹后数据中变量只在当前组件中生效，不会影响其他组件.
            return {
            course:{},
            courses:[],
            COURSE_LEVEL: COURSE_LEVEL,
            COURSE_CHARGE: COURSE_CHARGE,
            COURSE_STATUS: COURSE_STATUS,
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
                _this.course = {};
                $("#form-model").modal("show");
            },
            edit(course) {
                let _this = this;
                //修改的内容不会影响到原course 所以点击取消后数据不会发生更改
                _this.course = $.extend({}, course);
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
                })
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
            }
        }
    }
</script>