<template>
    <div>
        <div style="margin-top: 10px;display: flex;justify-content: center;">
            <el-input @keydown.enter.native="doSearch" style="width: 400px;margin-right: 10px;" v-model="keyWords" prefix-icon="el-icon-search" placeholder="请输入用户名搜索..."></el-input>
            <el-button type="primary" icon="el-icon-search" @click="doSearch">搜索</el-button>
        </div>
        <div class="user-container">
            <el-card class="user-card" v-for="(user,index) in users" :key="index">
                <div slot="header">
                    <span>{{user.name}}</span>
                    <el-button style="float: right; padding: 3px 0;color: #ff1517;cursor:pointer;" type="text" icon="el-icon-delete"  @click="deleteUser(user)"></el-button>
                </div>
                <div>
                   <div class="img-container">
                       <img :src="user.userFace" :alt="user.name" :title="user.name" class="userface-img">
                   </div>
                    <div class="userInfo-container">
                        <div>用户名：{{user.name}}</div>
                        <div>昵称：{{user.nickname}}</div>
                        <div>手机号码：{{user.phone}}</div>
                        <div>电话号码：{{user.telephone}}</div>
                        <div>地址：{{user.address}}</div>
                        <div style="display: flex">用户状态：<el-switch
                                style="display: flex;"
                                v-model="user.enabled"
                                @change="enabledChange(user)"
                                active-color="#13ce66"
                                inactive-color="#ff4949"
                                active-text="启用"
                                inactive-text="禁用">
                        </el-switch></div>
                        <div>用户角色：
                            <el-tag type="success" style="margin-right: 4px" v-for="(role,index_j) in user.roles"
                                    :key="index_j">{{role.nameZh}}</el-tag>
                            <el-popover
                                    placement="right"
                                    title="角色列表"
                                    @show="showPop(user)"
                                    @hide="hidePop(user)"
                                    width="200"
                                    trigger="click">
                                <el-select v-model="selectedRoles" multiple placeholder="请选择">
                                    <el-option
                                            v-for="(r,index_k) in allRoles"
                                            :key="index_k"
                                            :label="r.nameZh"
                                            :value="r.id">
                                    </el-option>
                                </el-select>
                                <el-button slot="reference" icon="el-icon-more" type="text"></el-button>
                            </el-popover>
                        </div>
                        <div>英雄事迹：{{user.remark}}</div>
                    </div>
                </div>
            </el-card>
        </div>
    </div>
</template>

<script>
    export default {
        name: "SysUser",
        data(){
            return{
                keyWords:'',
                users:[],
                selectedRoles: [],
                allRoles: []
            }
        },
        mounted() {
            this.initUsers();
        },
        methods:{
            doSearch() {
                this.initUsers();
            },
            initUsers(){
                this.getRequest('/system/user/?keyWords='+this.keyWords).then(resp=>{
                    if (resp){
                        console.log(resp)
                        this.users = resp;
                    }
                })
            },
            initAllRoles() {
                this.getRequest("/system/user/roles").then(resp => {
                    if (resp) {
                        this.allRoles = resp;
                    }
                })
            },
            showPop(user) {
                this.initAllRoles();
                let roles = user.roles;
                this.selectedRoles = [];
                roles.forEach(r => {
                    this.selectedRoles.push(r.id);
                })
            },
            hidePop(user) {
                let roles = [];
                Object.assign(roles,user.roles);
                let flag = false;
                if (roles.length != this.selectedRoles.length) {
                    flag = true;
                } else {
                    for (let i = 0; i < roles.length; i++) {
                        let role = roles[i];
                        for (let j = 0; j < this.selectedRoles.length; j++) {
                            let sr = this.selectedRoles[j];
                            if (role.id == sr) {
                                roles.splice(i, 1);
                                i--;
                                break;
                            }
                        }
                    }
                    if (roles.length != 0) {
                        flag = true;
                    }
                }
                if (flag) {
                    let url = '/system/user/role?u_rid=' + user.id;
                    this.selectedRoles.forEach(sr => {
                        url += '&rids=' + sr;
                    });
                    this.putRequest(url).then(resp => {
                        if (resp) {
                            this.initUsers();
                        }
                    });
                }
            },
            deleteUser(user){
                this.$confirm('此操作将永久删除【'+user.name+'】, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.deleteRequest("/system/user/"+user.id).then(resp=>{
                        if (resp) {
                            this.initUsers();
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            enabledChange(user){
                this.putRequest('/system/user/',user).then(resp=>{
                    if (resp){
                        this.initUsers();
                    }
                })
            }
        }
    }
</script>

<style>
    .user-container {
        margin-top: 20px;
        display: flex;
        /*自动换行*/
        flex-wrap: wrap;
        /*平均分配空隙*/
        justify-content: space-around;
    }

    .user-card {
        width: 400px;
        margin-bottom: 20px;
    }

    .img-container {
        width: 100%;
        display: flex;
        justify-content: center;
    }

    .userface-img {
        width: 72px;
        height: 72px;
        border-radius: 72px;
    }
    .userInfo-container{
        margin-top: 20px;
    }
    .userInfo-container div{
        font-size: 14px;
        color: #409EFF;
        margin-bottom: 8px;

    }
</style>
