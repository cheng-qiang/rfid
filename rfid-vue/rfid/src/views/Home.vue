<template>
    <div>
        <el-container>
            <el-header class="homeHeader">
                <div class="title">机房人员定位管理系统</div>
                <div>
                    <el-button type="text" icon="el-icon-bell" style="margin-right: 8px;color: #000000;" @click="goChat"></el-button>
                    <el-dropdown class="userInfo" @command="commandHandler">
                    <span class="el-dropdown-link">
                    {{user.name}}<i><img :src="user.userFace" alt=""></i>
                    </span>
                        <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item command="userInfo">个人中心</el-dropdown-item>
                            <el-dropdown-item command="setting">设置</el-dropdown-item>
                            <el-dropdown-item command="logout" divided>注销登录</el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
                </div>
            </el-header>
            <el-container>
                <el-aside width="200px">
                    <el-menu router unique-opened>
                        <el-submenu :index="index+''" v-for="(item,index) in routes" v-if="!item.hidden" :key="index">
                            <template slot="title">
                                <i style="color: #898cff;margin-right: 5px" :class="item.iconCls"></i>
                                <span>{{item.name}}</span>
                            </template>
                            <el-menu-item :index="child.path" v-for="(child,index_j) in item.children" :key="index_j">
                                <i style="color: #898cff;margin-right: 5px" :class="child.iconCls"></i>
                                <span>{{child.name}}</span>
                            </el-menu-item>
                        </el-submenu>
                    </el-menu>
                </el-aside>
                <el-main>
                    <el-breadcrumb separator-class="el-icon-arrow-right" v-show="this.$router.currentRoute.path!='/home'">
                        <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
                        <el-breadcrumb-item>{{this.$router.currentRoute.name}}</el-breadcrumb-item>
                    </el-breadcrumb>
                    <div class="homeWelcome" v-show="this.$router.currentRoute.path=='/home'">
                        欢迎来到机房人员定位管理系统！
                    </div>
                    <router-view class="homeRouterView"/>
                </el-main>
            </el-container>
        </el-container>
    </div>
</template>

<script>
    export default {
        name: "Home",
        data(){
            return{
                //user:JSON.parse(window.sessionStorage.getItem('user'))
            }
        },
        computed:{
            routes(){
                return this.$store.state.routes;
            },
            user() {
                return this.$store.state.currentHr;
            }
        },
        methods:{
            goChat(){
                this.$router.push("/chat");
            },
            commandHandler(cmd){
                if (cmd == 'logout'){
                    this.$confirm('此操作将注销登录, 是否继续?', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(() => {
                        this.getRequest('/logout');
                        window.sessionStorage.removeItem('user');
                        this.$router.replace('/');
                        this.$store.commit('initRoutes',[]);
                    }).catch(() => {
                        this.$message({
                            type: 'info',
                            message: '已取消操作'
                        });
                    });
                }else if (cmd == "userInfo"){
                    this.$router.push('/UserInfo');
                }
            }
        }
    }
</script>

<style>
    .homeHeader{
        /*设置元素背景蓝色*/
        background: #409eff;
        /*设置元素布局方式*/
        display: flex;
        /*设置元素块轴上居中*/
        align-items: center;
        /* 均匀排列每个元素:首个元素放置于起点，末尾元素放置于终点 */
        justify-content: space-between;
        /*设置元素内边距上 右*/
        padding: 0px 15px;
        /*设置元素放在盒子中
            Total width: 160px
            Total height: 80px
            Content box width: 160px - (2 * 20px) - (2 * 8px) = 104px
            Content box height: 80px - (2 * 20px) - (2 * 8px) = 24px
        */
        box-sizing: border-box;
    }

    .homeHeader .title{
        /*设置元素字体大小*/
        font-size: 30px;
        /*设置元素字体*/
        font-family: 华文行楷;
        /*设置元素字体颜色*/
        color: #ffffff;
    }

    .homeHeader .userInfo{
        cursor: pointer;
    }

    .el-dropdown-link{
        display: flex;
        align-items: center;
    }

    .el-dropdown-link img{
        width: 48px;
        height: 48px;
        border-radius: 24px;
        margin-left: 8px;
    }

    .homeWelcome{
        text-align: center;
        font-size: 30px;
        font-family: 华文行楷;
        color: #409eff;
        padding-top: 50px;
    }

    .homeRouterView{
        margin-top:10px;
    }
</style>
