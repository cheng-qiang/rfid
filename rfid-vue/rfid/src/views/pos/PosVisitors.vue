<template>
    <div>
        <div style="margin-bottom: 10px;">
            <div style="float: left;">
                <el-input
                    @clear="initVisitors"
                    clearable
                    size="mini"
                    class="initVisitors"
                    prefix-icon="el-icon-search"
                    plachholder="请输入访客姓名查询..."
                    v-model="visitors_name"
                    @keydown.enter.native="initVisitors"></el-input>
                    <el-button icon="el-icon-search" size="mini" type="info" @click="initVisitors">查询</el-button>
            </div>
            <div style="display: flex;justify-content: flex-end;">
                <el-button
                        size="mini"
                        type="primary"
                        @click="addVisitor"><i class="el-icon-user"></i><span>添加访客</span></el-button>
            </div>
        </div>
        <div>
            <el-table
                    :data="visitors"
                    size="small"
                    stripe
                    border
                    style="width: 100%"
                    v-loading="loading"
                    element-loading-text="正在加载..."
                    element-loading-spinner="el-icon-loading"
                    element-loading-background="rgba(0, 0, 0, 0.8)"
                    @selection-change="handleSelectionChange">
                <el-table-column
                        type="selection"
                        width="55">
                </el-table-column>
                <el-table-column
                        label="姓名"
                        width="80">
                    <template slot-scope="scope">
                        <span>{{ scope.row.name }}</span>
                    </template>
                </el-table-column>
                <el-table-column
                        label="手机号码"
                        width="100">
                    <template slot-scope="scope">
                        <span>{{ scope.row.phone }}</span>
                    </template>
                </el-table-column>
                <el-table-column
                        label="身份证号"
                        width="180">
                    <template slot-scope="scope">
                        <span>{{ scope.row.identityNumber }}</span>
                    </template>
                </el-table-column>
                <el-table-column
                        label="所属公司"
                        width="180">
                    <template slot-scope="scope">
                        <span>{{ scope.row.company }}</span>
                    </template>
                </el-table-column>
                <el-table-column
                        label="所属部门"
                        width="100">
                    <template slot-scope="scope">
                        <span>{{ scope.row.department }}</span>
                    </template>
                </el-table-column>
                <el-table-column
                        label="邮箱地址"
                        width="180">
                    <template slot-scope="scope">
                        <span>{{ scope.row.email }}</span>
                    </template>
                </el-table-column>
                <el-table-column
                        label="创建日期"
                        width="180">
                    <template slot-scope="scope">
                        <span>{{ scope.row.date }}</span>
                    </template>
                </el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button
                                size="mini"
                                @click="showVisitorsEdit(scope.row)">编辑</el-button>
                        <el-button
                                size="mini"
                                type="danger"
                                @click="visitorsDelete(scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <el-button @click="deleteVisitors" type="danger" size="mini" style="margin-top: 8px;float: left;" :disabled="this.multipleSelection.length==0">批量删除</el-button>
            <div style="display: flex;justify-content: flex-end;margin-top: 8px;">
                <el-pagination
                        background
                        @current-change="currentChange"
                        @size-change="sizeChange"
                        layout="sizes, prev, pager, next, jumper, ->, total, slot"
                        :total="total">
                </el-pagination>
            </div>
        </div>
        <div>
            <el-dialog
                    :title="title"
                    :visible.sync="dialogVisible"
                    width="60%">
                <div>
                    <el-form :model="visitor" :rules="rules" ref="visitorForm">
                        <el-row>
                            <el-col :span="6">
                                <el-form-item label="姓名:" prop="name">
                                    <el-input size="mini" style="width: 150px" prefix-icon="el-icon-edit" v-model="visitor.name"
                                              placeholder="请输入访客姓名"></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="7">
                                <el-form-item label="手机号码:" prop="phone">
                                    <el-input size="mini" style="width: 150px" prefix-icon="el-icon-edit" v-model="visitor.phone"
                                              placeholder="请输入手机号码"></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="9">
                                <el-form-item label="身份证号码:" prop="identityNumber">
                                    <el-input size="mini" style="width: 200px" prefix-icon="el-icon-edit" v-model="visitor.identityNumber"
                                              placeholder="请输入身份证号码"></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="7">
                                <el-form-item label="所属公司:" prop="company">
                                    <el-input size="mini" style="width: 150px" prefix-icon="el-icon-edit" v-model="visitor.company"
                                              placeholder="请输入所属公司"></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="7">
                                <el-form-item label="所属部门:" prop="department">
                                    <el-input size="mini" style="width: 150px" prefix-icon="el-icon-edit" v-model="visitor.department"
                                              placeholder="请输入所属部门"></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="9">
                                <el-form-item label="邮箱地址:" prop="email">
                                    <el-input size="mini" style="width: 200px" prefix-icon="el-icon-edit" v-model="visitor.email"
                                              placeholder="请输入邮箱地址"></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="7">
                                <el-form-item label="创建日期:" prop="date">
                                    <el-date-picker
                                            v-model="visitor.date"
                                            size="mini"
                                            type="datetime"
                                            value-format="yyyy-MM-dd HH:mm:ss"
                                            style="width: 150px;"
                                            placeholder="创建日期">
                                    </el-date-picker>
                                </el-form-item>
                            </el-col>
                        </el-row>
                    </el-form>
                </div>
                <span slot="footer" class="dialog-footer">
                    <el-button @click="dialogVisible = false">取 消</el-button>
                    <el-button type="primary" @click="doAddVisitor">确 定</el-button>
                </span>
            </el-dialog>
        </div>
    </div>
</template>

<script>
    export default {
        name: "PosVisitors",
        data(){
            return{
                visitors_name:'',
                visitors:[],
                total: 0,
                page: 1,
                size: 10,
                multipleSelection:[],
                loading:false,
                title:'',
                dialogVisible: false,
                visitor:{
                    id:'',
                    name:'',
                    phone:'',
                    identityNumber:'',
                    company:'',
                    department:'',
                    email:'',
                    date:''
                },
                rules: {
                    name:[{required:true,message:'请输入姓名',trigger:'blur'}],
                    phone: [{required: true, message: '请输入手机号码', trigger: 'blur'},
                        {
                            pattern:/^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\d{8}$/g,
                            message:'手机号码格式不正确',
                            trigger: 'blur'
                        }],
                    identityNumber: [{required: true, message: '请输入身份证号码', trigger: 'blur'},
                        {
                            pattern:/(^[1-9]\d{5}(18|19|20)\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$)|(^[1-9]\d{5}\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}$)/g,
                            message: '身份证号码格式不正确',
                            trigger: 'blur'
                        }],
                    company: [{required: true, message: '请输入所属公司', trigger: 'blur'}],
                    department: [{required: true, message: '请输入所属部门', trigger: 'blur'}],
                    email:[{required: true, message: '请输入邮箱地址', trigger: 'blur'},
                        {
                            pattern:/^(\w)+(\.\w+)*@(\w)+((\.\w+)+)$/g,
                            message: '邮箱格式不正确',
                            trigger: 'blur'
                        }],
                    date:[{required:true,message:'请选择创建时间',trigger:'blur'}]
                }
            }
        },
        mounted() {
            this.initVisitors();
        },
        methods:{
            initVisitors(){
                this.loading = true;
                let url = '/position/visitors/base/?page=' + this.page + '&size=' + this.size + "&name=" + this.visitors_name;
                this.getRequest(url).then(resp => {
                    this.loading = false;
                    if (resp) {
                        this.visitors = resp.records;
                        this.total = resp.total;
                    }
                });
            },
            handleSelectionChange(val){
                this.multipleSelection = val;
            },
            currentChange(currentPage) {
                this.page = currentPage;
                this.initVisitors();
            },
            sizeChange(currentSize) {
                this.size = currentSize;
                this.initVisitors();
            },
            showVisitorsEdit(data){
                this.initVisitors();
                this.title = '编辑访客信息';
                this.visitor = data;
                this.dialogVisible = true;
            },
            addVisitor(){
                this.title = "添加访客";
                this.dialogVisible = true;
            },
            doAddVisitor(){
                if (this.visitor.id) {
                    this.$refs['visitorForm'].validate(valid => {
                        if (valid) {
                            this.putRequest("/position/visitors/base/", this.visitor).then(resp => {
                                if (resp) {
                                    this.initVisitor();
                                    this.dialogVisible = false;
                                    this.initVisitors();
                                }
                            })
                        }
                    });
                } else {
                    this.$refs['visitorForm'].validate(valid => {
                        if (valid) {
                            this.postRequest("/position/visitors/base/", this.visitor).then(resp => {
                                if (resp) {
                                    this.initVisitor();
                                    this.dialogVisible = false;
                                    this.initVisitors();

                                }
                            })
                        }
                    });
                }
            },
            initVisitor() {
                    this.visitor.id = '';
                    this.visitor.name = '';
                    this.visitor.phone = '',
                    this.visitor.identityNumber = '';
                    this.visitor.company = '';
                    this.visitor.department = '';
                    this.visitor.email = '';
                    this.visitor.date = '';
            },
            visitorsDelete(data){
                this.$confirm('此操作将永久删除【'+data.name+'】访客信息, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.deleteRequest("/position/visitors/base/"+ data.id).then(resp=>{
                        if (resp){
                            this.initVisitors();
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            deleteVisitors(){
                this.$confirm('此操作将永久删除【'+this.multipleSelection.length+'】条记录, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    let ids = '?';
                    this.multipleSelection.forEach(item=>{
                        ids += 'ids='+item.id+'&';
                    })
                    this.deleteRequest("/position/visitors/base/"+ ids).then(resp=>{
                        if (resp){
                            this.initVisitors();
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            }
        }

    }
</script>

<style>
    .initVisitors{
        width: 300px;margin-right: 8px;
    }
</style>
