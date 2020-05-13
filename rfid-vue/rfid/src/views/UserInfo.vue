<template>
    <div v-if="user">
        <el-card class="box-card" style="width: 400px">
            <div slot="header" class="clearfix">
                <span>{{user.name}}</span>
            </div>
            <div>
                <div style="display: flex;justify-content: center">
                    <el-upload
                            :show-file-list="false"
                            :on-success="onSuccess"
                            :data="user"
                            action="/system/user/user/face">
                        <img title="点击修改用户图像" :src="user.userFace"
                             style="width: 100px;height: 100px;border-radius: 50px;cursor: pointer;"
                             alt="">
                    </el-upload>
                </div>
                <div>电话号码：
                    <el-tag>{{user.telephone}}</el-tag>
                </div>
                <div>手机号码：
                    <el-tag>{{user.phone}}</el-tag>
                </div>
                <div>居住地址：
                    <el-tag>{{user.address}}</el-tag>
                </div>
                <div>用户标签：
                    <el-tag type="success" style="margin-right: 5px" v-for="(r,index) in user.roles" :key="index">
                        {{r.nameZh}}
                    </el-tag>
                </div>
                <div style="display: flex;justify-content: space-around;margin-top: 10px">
                    <el-button type="primary" @click="showUpdateuserInfoView">修改信息</el-button>
                    <el-button type="danger" @click="showUpdatePasswdView">修改密码</el-button>
                </div>
            </div>
        </el-card>
        <el-dialog
                title="修改用户信息"
                :visible.sync="dialogVisible"
                width="30%">
            <div>
                <table>
                    <tr>
                        <td>
                            <el-tag>用户昵称：</el-tag>
                        </td>
                        <td>
                            <el-input v-model="user2.name"></el-input>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <el-tag>电话号码：</el-tag>
                        </td>
                        <td>
                            <el-input v-model="user2.telephone"></el-input>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <el-tag>手机号码：</el-tag>
                        </td>
                        <td>
                            <el-input v-model="user2.phone"></el-input>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <el-tag>用户地址：</el-tag>
                        </td>
                        <td>
                            <el-input v-model="user2.address"></el-input>
                        </td>
                    </tr>
                </table>
            </div>
            <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="updateuserInfo">确 定</el-button>
  </span>
        </el-dialog>
        <el-dialog
                title="修改密码"
                :visible.sync="passwdDialogVisible"
                width="30%">
            <div>
                <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px"
                         class="demo-ruleForm">
                    <el-form-item label="请输入旧密码" prop="oldPass">
                        <el-input type="password" v-model="ruleForm.oldPass" autocomplete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="请输入新密码" prop="pass">
                        <el-input type="password" v-model="ruleForm.pass" autocomplete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="新确认密码" prop="checkPass">
                        <el-input type="password" v-model="ruleForm.checkPass" autocomplete="off"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
                        <el-button @click="resetForm('ruleForm')">重置</el-button>
                    </el-form-item>
                </el-form>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        name: "UserInfo",
        data() {
            var validatePass = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请输入密码'));
                } else {
                    if (this.ruleForm.checkPass !== '') {
                        this.$refs.ruleForm.validateField('checkPass');
                    }
                    callback();
                }
            };
            var validatePass2 = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请再次输入密码'));
                } else if (value !== this.ruleForm.pass) {
                    callback(new Error('两次输入密码不一致!'));
                } else {
                    callback();
                }
            };
            return {
                ruleForm: {
                    oldPass: '',
                    pass: '',
                    checkPass: ''
                },
                rules: {
                    oldPass: [
                        {validator: validatePass, trigger: 'blur'}
                    ],
                    pass: [
                        {validator: validatePass, trigger: 'blur'}
                    ],
                    checkPass: [
                        {validator: validatePass2, trigger: 'blur'}
                    ]
                },
                user: null,
                user2: null,
                dialogVisible: false,
                passwdDialogVisible: false
            }
        },
        mounted() {
            this.inituser();
        },
        methods: {
            onSuccess() {
                this.inituser();
            },
            updateuserInfo() {
                this.putRequest("/system/user/user/info", this.user2).then(resp => {
                    if (resp) {
                        this.dialogVisible = false;
                        this.inituser();
                    }
                })
            },
            showUpdateuserInfoView() {
                this.dialogVisible = true;
            },
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        this.ruleForm.userId = this.user.id;
                        this.putRequest("/system/user/user/pass", this.ruleForm).then(resp => {
                            if (resp) {
                                this.getRequest("/logout");
                                window.sessionStorage.removeItem("user")
                                this.$store.commit('initRoutes', []);
                                this.$router.replace("/");
                            }
                        })
                    } else {
                        return false;
                    }
                });
            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
            },
            showUpdatePasswdView() {
                this.passwdDialogVisible = true;
            },
            inituser() {
                this.getRequest('/system/user/user/info').then(resp => {
                    if (resp) {
                        this.user = resp;
                        this.user2 = Object.assign({}, this.user);
                        window.sessionStorage.setItem("user", JSON.stringify(resp));
                        this.$store.commit('INIT_CURRENTHR', resp);
                    }
                })
            }
        }
    }
</script>

<style>
    img:before {
        content: ' ';
        display: block;
        position: relative;
        height: 32px;
        width: 32px;
        background-image: url(../images/loding.gif);}
</style>