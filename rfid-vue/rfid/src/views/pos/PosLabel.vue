<template>
    <div>
        <div class="addTab">
            <div style="float: left;">
                <el-input
                        @clear="selectTab"
                        clearable
                        size="mini"
                        prefix-icon="el-icon-search"
                        placeholder="请输入标签编号查询..."
                        class="selectTab"
                        v-model="tab_name"
                        @keydown.enter.native="selectTab"></el-input>
                <el-select v-model="status_name" size="mini" placeholder="请选择">
                    <el-option
                            v-for="item in status_array"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value">
                    </el-option>
                </el-select>
                <el-button style="margin-left: 8px;" icon="el-icon-search" size="mini" type="info" @click="selectTab">查询</el-button>
            </div>
            <div style="display: flex;justify-content: flex-end">

                <el-button
                        size="mini"
                        type="primary"
                        @click="addTab"><i class="el-icon-add-location"></i><span>添加标签</span></el-button>
            </div>
        </div>

        <div>
            <el-table
                    :data="tableData"
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
                        label="编号"
                        width="180">
                    <template slot-scope="scope">
                        <span>{{ scope.row.code }}</span>
                    </template>
                </el-table-column>
                <el-table-column
                        label="类别型号"
                        width="180">
                    <template slot-scope="scope">
                        <span>{{ static_status[scope.row.type] }}</span>
                    </template>
                </el-table-column>
                <el-table-column
                        label="是否正常" width="180">
                    <template slot-scope="scope">
                        <el-tag type="success" v-if="scope.row.status">正常</el-tag>
                        <el-tag type="danger" v-else>故障</el-tag>
                    </template>
                </el-table-column>
                <el-table-column
                        label="购买日期"
                        width="180">
                    <template slot-scope="scope">
                        <i class="el-icon-time"></i>
                        <span style="margin-left: 10px">{{ scope.row.date }}</span>
                    </template>
                </el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button
                                size="mini"
                                @click="tabEdit(scope.row)">编辑</el-button>
                        <el-button
                                size="mini"
                                type="danger"
                                @click="tabDelete(scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <el-button @click="deleteTabs" type="danger" size="mini" style="margin-top: 8px;float: left;" :disabled="this.multipleSelection.length==0">批量删除</el-button>
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
                    <el-form :model="tab" :rules="rules" ref="tabForm">
                        <el-row>
                            <el-col :span="6">
                                <el-form-item label="编号:" prop="code">
                                    <el-input size="mini" style="width: 150px" prefix-icon="el-icon-edit" v-model="tab.code"
                                              placeholder="请输入标签编号"></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="7">
                                <el-form-item label="类别型号:" prop="type">
                                    <el-select v-model="tab.type" placeholder="请选择类别型号" size="mini" style="width: 150px">
                                        <el-option
                                                v-for="item in options"
                                                :key="item.value"
                                                :label="item.label"
                                                :value="item.value">
                                        </el-option>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="6">
                                <el-form-item label="标签状态:" prop="status">
                                    <el-switch
                                            style="display: block"
                                            v-model="tab.status"
                                            active-color="#13ce66"
                                            inactive-color="#ff4949"
                                            active-text="正常"
                                            inactive-text="故障">
                                    </el-switch>
                                </el-form-item>
                            </el-col>
                            <el-col :span="7">
                                <el-form-item label="创建日期:" prop="date">
                                    <el-date-picker
                                            v-model="tab.date"
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
                    <el-button type="primary" @click="doaddTab">确 定</el-button>
                </span>
            </el-dialog>
        </div>
    </div>
</template>

<script>
    export default {
        name: "PosLabel",
        data(){
            let validate = (rule, value, callback) => {
                let tab_code = value;
                this.validateCode('/position/label/base/'+tab_code).then(resp=>{
                    if (resp && resp === "TRUE"){
                        callback();
                    }else if (resp && resp === "FALSE" && this.tab.id === ''){
                        callback("编号已经存在！");
                    }else {
                        callback();
                    }
                })
            }
            return{
                tableData:[],
                loading: false,
                tab_name:'',
                total: 0,
                page: 1,
                size: 10,
                /*弹框初始化设置*/
                title: '',
                dialogVisible: false,
                tab:{
                    id:'',
                    code:'',
                    type:'',
                    status:'',
                    date:''
                },
                static_status:['类别型号1','类别型号2','类别型号3','类别型号4','类别型号5','类别型号6'],
                multipleSelection:[],
                status_array:[{
                    value:0,
                    label:'故障'
                },{
                    value:1,
                    label:'正常'
                }],
                status_name:'',
                options: [{
                    value: 0,
                    label: '类别型号1'
                }, {
                    value: 1,
                    label: '类别型号2'
                }, {
                    value: 2,
                    label: '类别型号3'
                }, {
                    value: 3,
                    label: '类别型号4'
                }, {
                    value: 4,
                    label: '类别型号5'
                }],
                rules: {
                    code:[{required:true,message:'请输入编号',trigger:'blur'},
                        {validator:validate, trigger: 'blur'}],
                    type: [{required: true, message: '请选择类别型号', trigger: 'blur'}],
                    date: [{required: true, message: '请输入购买日期', trigger: 'blur'}]
                }
            }
        },
        mounted() {
            this.selectTab();
        },
        methods:{
            tabEdit(data){
                this.selectTab();
                this.title = '编辑标签信息';
                this.tab = data;
                this.dialogVisible = true;
            },
            doaddTab(){
                if (this.tab.id) {
                    this.$refs['tabForm'].validate(valid => {
                        if (valid) {
                            this.putRequest("/position/label/base/", this.tab).then(resp => {
                                if (resp) {
                                    this.inittab();
                                    this.dialogVisible = false;
                                    this.selectTab();
                                }
                            })
                        }
                    });
                } else {
                    this.$refs['tabForm'].validate(valid => {
                        if (valid) {
                            this.postRequest("/position/label/base/", this.tab).then(resp => {
                                if (resp) {
                                    this.inittab();
                                    this.dialogVisible = false;
                                    this.selectTab();
                                }
                            })
                        }
                    });
                }
            },
            inittab(){
                this.tab.status='';
                this.tab.code='';
                this.tab.type='';
                this.tab.id='';
                this.tab.date='';
            },
            selectTab(){
                this.loading = true;
                let url = '/position/label/base/?page=' + this.page + '&size=' + this.size +'&code='+this.tab_name+'&status='+this.status_name;
                this.getRequest(url).then(resp => {
                    this.loading = false;
                    if (resp) {
                        this.status_name = '';
                        this.tab_name ='';
                        this.tableData = resp.records;
                        this.total = resp.total;
                    }
                });
            },
            beforeUpload() {
                this.importDataBtnText = '正在导入';
                this.importDataBtnIcon = 'el-icon-loading';
                this.importDataDisabled = true;
            },
            onSuccess(response, file, fileList) {
                this.importDataBtnText = '导入数据';
                this.importDataBtnIcon = 'el-icon-upload2';
                this.importDataDisabled = false;
                this.selectTab();
            },
            onError(err, file, fileList) {
                this.importDataBtnText = '导入数据';
                this.importDataBtnIcon = 'el-icon-upload2';
                this.importDataDisabled = false;
            },
            exportData() {
                window.open('/position/label/base/export', '_parent');
            },
            addTab(){
                this.title = "添加基站";
                this.dialogVisible = true;
            },
            handleSelectionChange(val){
                this.multipleSelection = val;
            },
            currentChange(currentPage) {
                this.page = currentPage;
                this.selectTab();
            },
            sizeChange(currentSize) {
                this.size = currentSize;
                this.selectTab();
            },
            /*删除基站*/
            tabDelete(data){
                this.$confirm('此操作将永久删除【'+data.code+'】, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.deleteRequest('/position/label/base/'+data.id).then(resp=>{
                        if (resp){
                            this.selectTab();
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            deleteTabs(){
                this.$confirm('此操作将永久删除【'+this.multipleSelection.length+'】条记录, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    let ids = '?';
                    this.multipleSelection.forEach(item=>{
                        ids += 'ids='+item.id+'&';
                    })
                    this.deleteRequest("/position/label/base/"+ ids).then(resp=>{
                        if (resp){
                            this.selectTab();
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
    .selectTab{
        width: 300px;margin-right: 8px;
    }
    .addTab{
        margin-bottom: 10px;
    }
</style>
