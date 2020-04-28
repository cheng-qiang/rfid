<template>
    <div class="login">
        <el-form
                :rules="rules"
                ref="loginForm"
                :model="loginForm"
                v-loading="loading"
                element-loading-text="正在登录...😀"
                element-loading-spinner="el-icon-loading"
                element-loading-background="rgba(0, 0, 0, 0.8)"
                class="loginContainer">
            <h3 class="loginTitle">机房人员定位管理系统</h3>
            <el-form-item prop="username">
                <el-input type="text" @keydown.enter.native="submitLogin" v-model="loginForm.username" auto-complete="off" placeholder="请输入用户名" ></el-input>
            </el-form-item>
            <el-form-item prop="password">
                <el-input type="password" @keydown.enter.native="submitLogin" v-model="loginForm.password" auto-complete="off" placeholder="请输入密码"></el-input>
            </el-form-item>
            <el-form-item prop="code" class="verifyCode">
                <el-input type="code" @keydown.enter.native="submitLogin" v-model="loginForm.code" auto-complete="off" placeholder="验证码"></el-input>
            </el-form-item>
            <img src="/vercode" class="verifyCodeImg" title="刷新" onclick="javascript:this.src='/vercode?time='+new Date().getTime()"/>
                <el-checkbox  class="loginRemember" v-model="checked">记住密码</el-checkbox>
            <el-button type="primary" style="width: 100%;" @click="submitLogin">登录</el-button>
        </el-form>
    </div>
</template>

<script>
    /*导入postKeyValueRequest*/
    import {postKeyValueRequest} from "../utils/api";

    export default {
        name: "Login",
        data(){
            return{
                loginForm:{
                    username:'',
                    password:'',
                    code:''
                },
                loading:false,
                checked:true,
                rules:{
                    username:[{required:true,message:'请输入用户名',trigger:'blur'}],
                    password:[{required:true,message:'请输入密码',trigger:'blur'}],
                    code:[{required:true,message:'请输入验证码',trigger:'blur'}]
                }
            }
        },mounted() {
          this.getCookie();
        },
        methods:{
            submitLogin(){
                this.$refs.loginForm.validate((valid) => {
                    if (this.checked == true){
                        this.setCookie(this.loginForm.username,this.loginForm.password,7);
                    }else {
                        this.clearCookie();
                    }
                    if (valid) {
                        this.loading = true;
                        postKeyValueRequest('/doLogin',this.loginForm).then(resp=>{
                            this.loading = false;
                            if (resp){
                                this.$store.commit('INIT_CURRENTHR',resp.object);
                                window.sessionStorage.setItem("user",JSON.stringify(resp.object));
                                let path = this.$route.query.redirect;
                                this.$router.replace((path=='/' || path==undefined)?'/home':path);
                            }
                        })
                    } else {
                        this.$message.error('请输入所有字段');
                        return false;
                    }
                });
            },
            setCookie(c_name,c_pwd,exdays){
                let ex_date = new Date();//获取时间
                ex_date.setTime(ex_date.getTime() + 24 * 60 * 60 * 1000 * exdays);//保存的天数
                window.document.cookie = "userName" + "=" + c_name +";path=/;expires=" + ex_date.toGMTString();
                window.document.cookie = "userPwd" + "=" + c_pwd + ";path=/;expires=" + ex_date.toGMTString();
            },
            getCookie: function() {
                if (document.cookie.length > 0) {
                    let arr = document.cookie.split('; '); //这里显示的格式需要切割一下自己可输出看下
                    for (let i = 0; i < arr.length; i++) {
                        let arr2 = arr[i].split('='); //再次切割
                        //判断查找相对应的值
                        if (arr2[0] == 'userName') {
                            this.loginForm.username = arr2[1]; //保存到保存数据的地方
                        } else if (arr2[0] == 'userPwd') {
                            this.loginForm.password = arr2[1];
                        }
                    }
                }
            },
            //清除cookie
            clearCookie: function() {
                this.setCookie("", "", -1); //修改2值都为空，天数为负1天就好了
            }
        }
    }
</script>

<style lang="less">
    .loginContainer{
        /*设置元素圆角*/
        border-radius: 15px;
        /*设置元素的背景延伸至内边距外沿,不会绘制到边框处*/
        background-clip: padding-box;
        /*设置元素上下边距180,该元素水平是居中的*/
        margin: 180px auto;
        /*设置元素宽度,此时元素垂直居中*/
        width: 350px;
        /*设置元素内边距上 右 下 左*/
        padding: 35px 35px 15px 35px;
        /*设置元素背景*/
        background: #ffffff;
        /*设置元素边框宽度 样式 颜色*/
        border: 1px solid #eaeaea;
        /*设置元素阴影的效果x偏移 y偏移 阴影模糊(扩散)半径 阴影颜色*/
        box-shadow: 0 0 25px #cacac6;
    }
    .loginTitle{
        /*设置元素外边距上 右 下 左*/
        margin: 0px auto 40px auto;
        /*设置元素内容居中*/
        text-align: center;
        /*设置元素颜色*/
        color: #505458;
    }
    .loginRemember{
        position: relative;
        right: 100px;
        bottom: 5px;
    }
    .login {
        display: flex;
        flex-direction: column;
        height: 100vh;
        overflow: auto;
        background: #f0f2f5 url('https://gw.alipayobjects.com/zos/rmsportal/TVYTbAXWheQpRcWDaDMu.svg') no-repeat center 110px;
        background-size: 100%;
    }
    .verifyCode{
        width:240px;
        float: left;
    }
    .verifyCodeImg{
        cursor: pointer;
        float: right;
    }

</style>
