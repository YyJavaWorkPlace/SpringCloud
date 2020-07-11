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
                <#-- 取出field中文名称表头-->
                <#list fieldList as field>
                    <#if  field.nameHump!="updatedAt">
                        <th>${field.nameCn}</th>
                    </#if>
                </#list>
                <th>操作</th>
            </tr>
            </thead>

            <tbody>
            <#--            取出对象中的值 例如Course.fileName 过滤更新时间-->
            <tr v-for="${domain} in ${domain}s" :key="${domain}.id">
                <#list fieldList as field>
                    <#if  field.nameHump!="updatedAt">
                        <#if field.enums>
                            <td>{{${field.enumsConst}|optionArray(${domain}.${field.nameHump})}}</td>
                        <#else >
                            <td> {{${domain}.${field.nameHump}}}</td>
                        </#if>
                    </#if>
                </#list>
                <td>
                    <div class="hidden-sm hidden-xs btn-group">
                        <button v-on:click="edit(${domain})" class="btn btn-xs btn-info">
                            <i class="ace-icon fa fa-pencil bigger-120"></i>
                        </button>
                        <button v-on:click="del(${domain}.id)" class="btn btn-xs btn-danger">
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
                        <h4 class="modal-title">新增${tableNameCn}</h4>
                    </div>
                    <div class="modal-body">
                        <form>
                            <#--这里需要判断 如果是枚举类型 需要显示出下拉框-->
                            <#list fieldList as field>
                                <#if  field.nameHump!="id"&&field.nameHump!="createdAt"&&field.nameHump!="updatedAt">
                                    <#if field.enums>
                                        <div class="form-group">
                                            <label>${field.nameCn}</label>
                                            <select v-model="${domain}.${field.nameHump}" class="form-control">
                                                <option v-for="o in ${field.enumsConst}" :key="o.num"
                                                        v-bind:value="o.key">{{o.value}}
                                                </option>
                                            </select>
                                        </div>
                                    <#else >
                                        <div class="form-group">
                                            <label>${field.nameCn}</label>
                                            <input v-model="${domain}.${field.nameHump}" type="text"
                                                   class="form-control">
                                        </div>
                                    </#if>
                                </#if>
                            </#list>
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
        name: "${module}-${domain}",
        components: {Pagination},
        data: function () {
            // 不使用return包裹的数据会在项目的全局可见，会造成变量污染；使用return包裹后数据中变量只在当前组件中生效，不会影响其他组件.
            return {
            ${domain}:{},
            ${domain}s:[],
            <#list fieldList as field>
            <#if field.enums>
            ${field.enumsConst}: ${field.enumsConst},
            </#if>
            </#list>
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
                _this.$ajax.post(process.env.VUE_APP_SERVER + '/${module}/admin/${domain}/list', {
                    page: page,
                    size: _this.$refs.pagination.size,
                }).then((response) => {
                    Loading.hide();
                    let resp = response.data;
                    _this.${domain}s = resp.content.list;
                    _this.$refs.pagination.render(page, resp.content.total);
                })
            },
            add() {
                let _this = this;
                _this.${domain} = {};
                $("#form-model").modal("show");
            },
            edit(${domain}) {
                let _this = this;
                //修改的内容不会影响到原${domain} 所以点击取消后数据不会发生更改
                _this.${domain} = $.extend({}, ${domain});
                $("#form-model").modal("show");
            },
            save() {
                let _this = this;
                if (1 != 1
                    <#list fieldList as field>
                    <#if  field.nameHump!="id"&&field.nameHump!="createdAt"&&field.nameHump!="updateAt"&&field.nameHump!="sort">
                    <#if !field.nullAble>
                    || !Validator.require(_this.${domain}.${field.nameHump}, "${field.nameCn}")
                    </#if>
                    <#if (field.length>0)>
                    || !Validator.length(_this.${domain}.${field.nameHump}, "${field.nameCn}", 1, "${field.length?c}")
                    </#if>
                    </#if>
                    </#list>
                ) {
                    return;
                }
                Loading.show();
                _this.$ajax.post(process.env.VUE_APP_SERVER + '/${module}/admin/${domain}/save', _this.${domain}
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
                    _this.$ajax.delete(process.env.VUE_APP_SERVER + '/${module}/admin/${domain}/delete/' + id).then((response) => {
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