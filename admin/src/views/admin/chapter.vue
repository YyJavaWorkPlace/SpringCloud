<template>
    <div>
        <p>
            <button @click="list()"  class="btn btn-warning btn-xs">
                <i class="ace-icon fa fa-refresh  bigger-110 icon-only"></i>
                刷新
            </button>
            &nbsp;
            <button @click="add()"  class="btn btn-warning btn-xs">
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
                        <button class="btn btn-xs btn-success">
                            <i class="ace-icon fa fa-check bigger-120"></i>
                        </button>
                        <button class="btn btn-xs btn-info">
                            <i class="ace-icon fa fa-pencil bigger-120"></i>
                        </button>

                        <button class="btn btn-xs btn-danger">
                            <i class="ace-icon fa fa-trash-o bigger-120"></i>
                        </button>
                        <button class="btn btn-xs btn-warning">
                            <i class="ace-icon fa fa-flag bigger-120"></i>
                        </button>
                    </div>

                    <div class="hidden-md hidden-lg">
                        <div class="inline pos-rel">
                            <button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown"
                                    data-position="auto">
                                <i class="ace-icon fa fa-cog icon-only bigger-110"></i>
                            </button>

                            <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
                                <li>
                                    <a href="#" class="tooltip-info" data-rel="tooltip" title="View">
																			<span class="blue">
																				<i class="ace-icon fa fa-search-plus bigger-120"></i>
																			</span>
                                    </a>
                                </li>

                                <li>
                                    <a href="#" class="tooltip-success" data-rel="tooltip" title="Edit">
																			<span class="green">
																				<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
																			</span>
                                    </a>
                                </li>

                                <li>
                                    <a href="#" class="tooltip-error" data-rel="tooltip" title="Delete">
																			<span class="red">
																				<i class="ace-icon fa fa-trash-o bigger-120"></i>
																			</span>
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </td>
            </tr>
            </tbody>
        </table>
        <div id="form-model" class="modal fade" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">新增课程章节</h4>
                    </div>
                    <div  class="modal-body">
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
    export default{
        name:"chapter",
        components: {Pagination},
        data:function(){
            return {
                chapter:{},
                chapters:[]
            }
        },
        mounted:function() {
            let _this=this;
            _this.list(1);
        },
        methods:{
            list(page){
                let _this=this;
                _this.$ajax.post('http://127.0.0.1:9000/business/admin/chapter/list',{
                    page:page,
                    size:_this.$refs.pagination.size,
                }).then((response)=>{
                    let resp=response.data;
                    _this.chapters=resp.content.list;
                    _this.$refs.pagination.render(page,resp.content.total);
                })
            },
            add(){
                //let _this=this;
                $("#form-model").modal("show");
            },
            save(){
                let _this=this;
                _this.$ajax.post('http://127.0.0.1:9000/business/admin/chapter/save',_this.chapter
                ).then((response)=>{
                    let resp=response.data;
                    if (resp.success){
                        $("#form-model").modal("hide");
                        _this.list(1)
                    }
                })
            },
        }
    }
</script>