<template>
    <div>
        <div class="row" style="margin-bottom: 10px;">
        <div class="col-xs-4 label label-lg label-info arrowed-in arrowed-right">
            <b>{{course.name}}</b>
        </div>
        </div>
        <p>
            <router-link to="/business/course" class="btn btn-warning btn-xs">
                <i class="ace-icon fa fa-arrow-left  bigger-110 icon-only"></i>
                返回课程
            </router-link>
            &nbsp;
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
                <th>序号</th>
                <th>课程编号</th>
                <th>课程ID</th>
                <th>操作</th>
            </tr>
            </thead>

            <tbody>
            <tr v-for="chapData in chapters" :key="chapData.id">
                <td>
                    {{chapData.id}}
                </td>
                <td>{{chapData.courseId}}</td>
                <td>{{chapData.name}}</td>
                <td>
                    <div class="hidden-sm hidden-xs btn-group">
                        <button v-on:click="edit(chapData)" class="btn btn-xs btn-info">
                            <i class="ace-icon fa fa-pencil bigger-120"></i>
                        </button>
                        <button v-on:click="del(chapData.id)" class="btn btn-xs btn-danger">
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
                        <h4 class="modal-title">新增课程章节</h4>
                    </div>
                    <div class="modal-body">
                        <form>
                            <div class="form-group">
                                <label>名称</label>
                                <input v-model="chapter.name" type="text" class="form-control" placeholder="名称">
                            </div>
                            <div class="form-group">
                                <label>课程编号</label>
                                <input v-model="chapter.courseId" type="text" class="form-control" placeholder="课程编号">
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
        name: "chapter",
        components: {Pagination},
        data: function () {
            // 不使用return包裹的数据会在项目的全局可见，会造成变量污染；使用return包裹后数据中变量只在当前组件中生效，不会影响其他组件.
            return {
                chapter: {},
                chapters: [],
                course:{}
            }
        },
        mounted: function () {
            let _this = this;
            let course = SessionStorage.get("course")||{};
            if (Tool.isEmpty(course)){
                _this.$route.push("/welcome");
            }
            _this.course=course;
            _this.list(1);

        },
        methods: {
            list(page) {
                //等待框
                Loading.show();
                let _this = this;
                _this.$ajax.post(process.env.VUE_APP_SERVER+'/business/admin/chapter/list', {
                    page: page,
                    size: _this.$refs.pagination.size,
                }).then((response) => {
                    Loading.hide();
                    let resp = response.data;
                    _this.chapters = resp.content.list;
                    _this.$refs.pagination.render(page, resp.content.total);
                })
            },
            add() {
                let _this = this;
                _this.chapter = {};
                $("#form-model").modal("show");
            },
            edit(chapter) {
                let _this = this;
                //修改的内容不会影响到原chapter 所以点击取消后数据不会发生更改
                _this.chapter = $.extend({}, chapter);
                $("#form-model").modal("show");
            },
            save() {
                let _this = this;
                //保存校验
                if (!Validator.require(_this.chapter.name,"名称")||
                    !Validator.require(_this.chapter.courseId,"课程ID")||
                    !Validator.length(_this.chapter.courseId,"课程ID",1,8)){
                    return;
                }
                Loading.show();
                _this.$ajax.post(process.env.VUE_APP_SERVER+'/business/admin/chapter/save', _this.chapter
                ).then((response) => {
                    Loading.hide();
                    let resp = response.data;
                    if (resp.success) {
                        $("#form-model").modal("hide");
                        _this.list(1);
                        Toast.success("保存成功");
                    }else{
                        Toast.warning(resp.message);
                    }
                })
            },
            del(id){
                let _this=this;
                Confirm.show("删除后不可回复,确认删除?",function(){
                    Loading.show();
                    _this.$ajax.delete(process.env.VUE_APP_SERVER +'/business/admin/chapter/delete/'+id).then((response)=>{
                        Loading.hide();
                        let resp=response.data;
                        if (resp.success){
                            _this.list(1);
                            Toast.error("删除成功");
                        }
                    });
                });
            }
        }
    }
</script>