<template>
    <div>
        <div class="row">
            <div class="col-md-6">
                <p>
                    <button @click="all1()" class="btn btn-warning btn-xs">
                        <i class="ace-icon fa fa-refresh  bigger-110 icon-only"></i>
                        刷新
                    </button>
                    &nbsp;
                    <button @click="add1()" class="btn btn-warning btn-xs">
                        <i class="ace-icon fa fa-edit bigger-110 icon-only"></i>
                        新增一级栏目
                    </button>
                </p>

                <table id="level1-table" class="table  table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>id</th>
                        <th>名称</th>
                        <th>顺序</th>
                        <th>操作</th>
                    </tr>
                    </thead>

                    <tbody>
                    <tr v-for="category in level1" :key="category.id" v-on:click="onClickLevel1(category)"
                        v-bind:class="{'active':category.id===active.id}">
                        <td> {{category.id}}</td>
                        <td> {{category.name}}</td>
                        <td> {{category.sort}}</td>
                        <td>
                            <div class="hidden-sm hidden-xs btn-group">
                                <button v-on:click="edit(category)" class="btn btn-xs btn-info">
                                    <i class="ace-icon fa fa-pencil bigger-120"></i>
                                </button>
                                <button v-on:click="del(category.id)" class="btn btn-xs btn-danger">
                                    <i class="ace-icon fa fa-trash-o bigger-120"></i>
                                </button>
                            </div>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div class="col-md-6">
                <p>
                    &nbsp;
                    <button @click="add2()" class="btn btn-warning btn-xs">
                        <i class="ace-icon fa fa-edit bigger-110 icon-only"></i>
                        新增二级栏目
                    </button>
                </p>
                <table id="level2-table" class="table  table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>id</th>
                        <th>名称</th>
                        <th>顺序</th>
                        <th>操作</th>
                    </tr>
                    </thead>

                    <tbody>
                    <tr v-for="category in level2" :key="category.id">
                        <td> {{category.id}}</td>
                        <td> {{category.name}}</td>
                        <td> {{category.sort}}</td>
                        <td>
                            <div class="hidden-sm hidden-xs btn-group">
                                <button v-on:click="edit(category)" class="btn btn-xs btn-info">
                                    <i class="ace-icon fa fa-pencil bigger-120"></i>
                                </button>
                                <button v-on:click="del(category.id)" class="btn btn-xs btn-danger">
                                    <i class="ace-icon fa fa-trash-o bigger-120"></i>
                                </button>
                            </div>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>

        <div id="form-model" class="modal fade" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">新增分类</h4>
                    </div>
                    <div class="modal-body">
                        <form>
                            <div class="form-group">
                                <label>父分类</label>
                                <p class="form-control-static">{{active.name||"无"}}</p>
                            </div>
                            <div class="form-group">
                                <label>名称</label>
                                <input v-model="category.name" type="text"
                                       class="form-control">
                            </div>
                            <div class="form-group">
                                <label>顺序</label>
                                <input v-model="category.sort" type="text"
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
    export default {
        name: "business-category",
        data: function () {
            // 不使用return包裹的数据会在项目的全局可见，会造成变量污染；使用return包裹后数据中变量只在当前组件中生效，不会影响其他组件.
            return {
                category: {},
                categorys: [],
                level1: [],
                level2: [],
                active: {},
            }
        },
        mounted: function () {
            let _this = this;
            _this.all();
        },
        methods: {
            all() {
                //等待框
                Loading.show();
                let _this = this;
                _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/category/all', {}).then((response) => {
                    Loading.hide();
                    let resp = response.data;
                    _this.categorys = resp.content;
                    // 将所有记录格式化成树形结构
                    _this.level1 = []; //刷新BUG
                    for (let i = 0; i < _this.categorys.length; i++) {
                        let c = _this.categorys[i];
                        if (c.parent === '00000000') {
                            _this.level1.push(c);
                            for (let j = 0; j < _this.categorys.length; j++) {
                                let child = _this.categorys[j];
                                if (child.parent === c.id) {
                                    if (Tool.isEmpty(c.children)) {
                                        c.children = [];
                                    }
                                    c.children.push(child);
                                }
                            }
                        }
                    }
                    _this.level2=[];
                    //对当前一级分类中选中的表格触发一次点击事件，以刷新二级菜单列表
                    //注意：界面的渲染需要等vue绑定好变量后才做 所以延时加载100ms
                    setTimeout(function () {
                        $("tr.active").trigger("click");},100);
                })
            },
            /**
             * 一级新增
             */
            add1() {
                let _this = this;
                _this.active = {};
                _this.level2 = [];
                _this.category = {
                    parent: "00000000"
                };
                $("#form-model").modal("show");
            },
            /**
             * 二级新增
             */
            add2() {
                let _this = this;
                if (Tool.isEmpty(_this.active)){
                    Toast.warning("请先点击一级分类");
                    return;
                }
                _this.category = {
                    parent: _this.active.id
                };
                $("#form-model").modal("show");
            },
            edit(category) {
                let _this = this;
                //修改的内容不会影响到原category 所以点击取消后数据不会发生更改
                _this.category = $.extend({}, category);
                $("#form-model").modal("show");
            },
            save() {
                let _this = this;
                if (1 != 1
                    || !Validator.require(_this.category.parent, "父id")
                    || !Validator.require(_this.category.name, "名称")
                    || !Validator.length(_this.category.name, "名称", 1, "50")
                ) {
                    return;
                }
                Loading.show();
                _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/category/save', _this.category
                ).then((response) => {
                    Loading.hide();
                    let resp = response.data;
                    if (resp.success) {
                        $("#form-model").modal("hide");
                        _this.all();
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
                    _this.$ajax.delete(process.env.VUE_APP_SERVER + '/business/admin/category/delete/' + id).then((response) => {
                        Loading.hide();
                        let resp = response.data;
                        if (resp.success) {
                            _this.all();
                            Toast.error("删除成功");
                        }
                    });
                });
            },
            onClickLevel1(category) {
                let _this = this;
                _this.active = category;
                _this.level2 = category.children
            }
        },
    }
</script>
<style scoped>
    .active td {
        background-color: #d3a61a !important;
    }
</style>