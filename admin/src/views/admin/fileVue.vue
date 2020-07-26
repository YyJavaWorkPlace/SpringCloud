<template>
    <div>
        <p>
            <button @click="list()" class="btn btn-warning btn-xs">
                <i class="ace-icon fa fa-refresh  bigger-110 icon-only"></i>
                刷新
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
            </tr>
            </tbody>
        </table>
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
                }).then((response) => {S
                    Loading.hide();
                    let resp = response.data;
                    _this.files = resp.content.list;
                    _this.$refs.pagination.render(page, resp.content.total);
                })
            },
        }
    }
</script>