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
                <th>相对路径</th>
                <th>文件名</th>
                <th>后缀</th>
                <th>大小</th>
                <th>用途</th>
                <th>创建时间</th>
                <th>操作</th>
            </tr>
            </thead>

            <tbody>
            <tr v-for="file in files" :key="file.id">
                <td> {{file.id}}</td>
                <td> {{file.path}}</td>
                <td> {{file.name}}</td>
                <td> {{file.suffix}}</td>
                <td> {{file.size}}</td>
                <td>{{FILE_USE|optionArray(file.use)}}</td>
                <td> {{file.createdAt}}</td>
                <td>
                    <div class="hidden-sm hidden-xs btn-group">
                        <button v-on:click="edit(file)" class="btn btn-xs btn-info">
                            <i class="ace-icon fa fa-pencil bigger-120"></i>
                        </button>
                        <button v-on:click="del(file.id)" class="btn btn-xs btn-danger">
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
                        <h4 class="modal-title">新增文件</h4>
                    </div>
                    <div class="modal-body">
                        <form>
                            <div class="form-group">
                                <label>相对路径</label>
                                <input v-model="file.path" type="text"
                                       class="form-control">
                            </div>
                            <div class="form-group">
                                <label>文件名</label>
                                <input v-model="file.name" type="text"
                                       class="form-control">
                            </div>
                            <div class="form-group">
                                <label>后缀</label>
                                <input v-model="file.suffix" type="text"
                                       class="form-control">
                            </div>
                            <div class="form-group">
                                <label>大小</label>
                                <input v-model="file.size" type="text"
                                       class="form-control">
                            </div>
                            <div class="form-group">
                                <label>用途</label>
                                <select v-model="file.use" class="form-control">
                                    <option v-for="o in FILE_USE" :key="o.num"
                                            v-bind:value="o.key">{{o.value}}
                                    </option>
                                </select>
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
        name: "file-file",
        components: {Pagination},
        data: function () {
            // 不使用return包裹的数据会在项目的全局可见，会造成变量污染；使用return包裹后数据中变量只在当前组件中生效，不会影响其他组件.
            return {
                file: {},
                files: [],
                FILE_USE: FILE_USE,
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
                _this.$ajax.post(process.env.VUE_APP_SERVER + '/file/admin/file/list', {
                    page: page,
                    size: _this.$refs.pagination.size,
                }).then((response) => {
                    Loading.hide();
                    let resp = response.data;
                    _this.files = resp.content.list;
                    _this.$refs.pagination.render(page, resp.content.total);
                })
            },
            add() {
                let _this = this;
                _this.file = {};
                $("#form-model").modal("show");
            },
            edit(file) {
                let _this = this;
                //修改的内容不会影响到原file 所以点击取消后数据不会发生更改
                _this.file = $.extend({}, file);
                $("#form-model").modal("show");
            },
            save() {
                let _this = this;
                if (1 != 1
                    || !Validator.require(_this.file.path, "相对路径")
                    || !Validator.length(_this.file.path, "相对路径", 1, "100")
                    || !Validator.length(_this.file.name, "文件名", 1, "100")
                    || !Validator.length(_this.file.suffix, "后缀", 1, "10")
                ) {
                    return;
                }
                Loading.show();
                _this.$ajax.post(process.env.VUE_APP_SERVER + '/file/admin/file/save', _this.file
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
                    _this.$ajax.delete(process.env.VUE_APP_SERVER + '/file/admin/file/delete/' + id).then((response) => {
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