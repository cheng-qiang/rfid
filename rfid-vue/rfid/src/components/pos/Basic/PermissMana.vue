<template>
    <div>
        <div class="permissManaTool">
            <el-input size="small" placeholder="请输入角色英文名" v-model="role.name">
                <template slot="prepend">ROLE_</template>
            </el-input>
            <el-input size="small" placeholder="请输入角色中文名" v-model="role.nameZh"></el-input>
            <el-button size="small" type="primary" icon="el-icon-plus" @click="insertRole">添加角色</el-button>
        </div>
        <div class="permissManaMain">
            <el-collapse accordion @change="change" v-model="activeName">
                <el-collapse-item  :title="r.nameZh" :name="r.id" v-for="(r,index) in roles" :key="index">
                    <el-card class="box-card">
                        <div slot="header" class="clearfix">
                            <span>可访问的资源</span>
                            <el-button style="float: right; padding: 3px 0;color: #ff1517" type="text" icon="el-icon-delete" @click="deleteRole(r.nameZh,r.id)"></el-button>
                        </div>
                        <div>
                            <el-tree
                                    ref="tree"
                                    node-key="id"
                                    :default-checked-keys="selectMenus"
                                    show-checkbox
                                    :data="allMenus" :props="defaultProps"></el-tree>
                            <div style="display: flex;justify-content: flex-end;">
                                <el-button @click="cancleUpdate">取消修改</el-button>
                                <el-button type="primary" @click="doUpdate(r.id,index)">确认修改</el-button>
                            </div>
                        </div>
                    </el-card>
                </el-collapse-item>
            </el-collapse>
        </div>
    </div>
</template>

<script>
    export default {
        name: "PermissMana",
        data(){
            return{
                activeName:-1,
                role:{
                    name:'',
                    nameZh:''
                },
                roles:[],
                allMenus:[],
                defaultProps: {
                    children: 'children',
                    label: 'name'
                },
                selectMenus:[]
            }
        },
        mounted() {
            this.initRoles();
        },
        methods:{
            deleteRole(role_name,role_id){
                this.$confirm('此操作将永久删除【'+role_name+'】这个角色, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.deleteRequest('/system/basic/permiss/'+role_id).then(resp=>{
                        if (resp){
                            this.initRoles();
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            insertRole(){
                if (this.role.name && this.role.nameZh){
                    this.role.name = "ROLE_" + this.role.name;
                    this.postRequest("/system/basic/permiss/",this.role).then(resp=>{
                        if (resp){
                            this.role.name = '';
                            this.role.nameZh = '';
                            this.initRoles();
                        }
                    })
                }else{
                    this.$message.error("请填写完所有字段哦 😀")
                }
            },
            cancleUpdate(){
                this.activeName = -1;
            },
            doUpdate(rid,index){
                let tree = this.$refs.tree[index];
                /*这里true表示只返回叶子节点*/
                let checkedKeys = tree.getCheckedKeys(true);
                let url = '/system/basic/permiss/?rid='+rid;
                checkedKeys.forEach(key=>{
                    url += '&mids=' + key;
                })
                this.putRequest(url).then(resp=>{
                    if (resp){
                        this.initRoles();
                        this.activeName = -1;
                    }
                })
            },
            change(name){
                if (name){
                    this.initMenus();
                    this.initSelectMenus(name);
                }
            },
            initSelectMenus(rid){
                this.getRequest("/system/basic/permiss/mids/" + rid).then(resp=>{
                    if (resp){
                        this.selectMenus = resp;
                    }
                })
            },
            initMenus(){
                this.getRequest("/system/basic/permiss/menus/").then(resp=>{
                    if (resp){
                        this.allMenus = resp;
                    }
                })
            },
            initRoles(){
                this.getRequest("/system/basic/permiss/").then(resp=>{
                    if (resp){
                        this.roles = resp;
                    }
                })
            }
        }
    }
</script>

<style>
    .permissManaTool{
        display: flex;
        justify-content: flex-start;
    }
    .permissManaTool .el-input{
        width: 300px;
        margin-right: 8px;
    }
    .permissManaMain{
        margin-top: 10px;
        width: 720px;
    }

</style>
