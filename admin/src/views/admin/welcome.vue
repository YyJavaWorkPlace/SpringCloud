<template>
    <div>
        <h1>您好,欢迎进入控台</h1>
    </div>
</template>
<script>
    export default{
        name:"welcome",
        // mounted:html加载完成后执行。执行顺序：子组件-父组件
        mounted:function(){
            // sidebar激活样式方法一 此方法需要在每一个vue文件中声明
            // this.$parent.activeSidebar("welcome-sidebar");
        },
        methods:{

        }
    }
</script>
