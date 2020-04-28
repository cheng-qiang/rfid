<template>
    <div>
        <el-tabs type="card" v-model="activeName">
            <el-tab-pane label="权限组" name="permissmana"><PermissMana></PermissMana></el-tab-pane>
        </el-tabs>
    </div>
</template>

<script>
    import PermissMana from "../../components/pos/Basic/PermissMana";
    export default {
        name: "SysBasic",
        data(){
            return{
                activeName:'permissmana'
            }
        },
        components:{
            PermissMana
        }
    }
</script>

<style scoped>

</style>
