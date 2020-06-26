<template>
    <div>
        <p>
            <button @click="list()"  class="btn btn-warning btn-xs">
                <i class="ace-icon fa fa-refresh  bigger-110 icon-only"></i>
                刷新
            </button>
        </p>
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
    </div>

</template>
<script>
    export default{
        name:"chapter",
        data:function(){
            return {
                chapters:[]
            }
        },
        mounted:function() {
            let _this=this;
            _this.list();
        },
        methods:{
            list(){
                let _this=this;
                _this.$ajax.post('http://127.0.0.1:9000/business/admin/chapter/list',{
                    page:1,
                    size:1
                }).then((response)=>{
                    _this.chapters=response.data.list;
                })
            }
        }
    }
</script>