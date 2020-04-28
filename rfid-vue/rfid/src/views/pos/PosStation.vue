<template>
    <div>
        <div class="addStation">
                <div style="float: left;">
                    <el-input
                            @clear="selectStation"
                            clearable
                            size="mini"
                            prefix-icon="el-icon-search"
                            placeholder="请输入基站名称查询..."
                            class="selectStation"
                            v-model="station_name"
                            @keydown.enter.native="selectStation"></el-input>
                    <el-button icon="el-icon-search" size="mini" type="info" @click="selectStation">查询</el-button>
                </div>
                <div style="display: flex;justify-content: flex-end">
                    <el-upload
                            :show-file-list="false"
                            :before-upload="beforeUpload"
                            :on-success="onSuccess"
                            :on-error="onError"
                            :disabled="importDataDisabled"
                            style="display: inline-flex;margin-right: 8px"
                            action="/position/station/base/import">
                        <el-button size="mini" :disabled="importDataDisabled" type="success" :icon="importDataBtnIcon">
                            {{importDataBtnText}}
                        </el-button>
                    </el-upload>

                    <el-button size="mini" type="success" @click="exportData" icon="el-icon-download">
                        导出数据
                    </el-button>

                    <el-button
                            size="mini"
                            type="primary"
                            @click="addStation"><i class="el-icon-add-location"></i><span>添加基站</span></el-button>
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
                        label="名称"
                        width="180">
                    <template slot-scope="scope">
                        <el-popover trigger="hover" placement="top">
                            <p>编号: {{ scope.row.code }}</p>
                            <p>名称: {{ scope.row.name }}</p>
                            <p>版本号: {{ scope.row.version }}</p>
                            <p>IP地址: {{ scope.row.ip }}</p>
                            <p>创建日期: {{ scope.row.date }}</p>
                            <p>基站状态:
                                <el-tag type="success" v-if="scope.row.status">已启用</el-tag>
                                <el-tag type="danger" v-else>未启用</el-tag>
                            </p>
                            <div slot="reference" class="name-wrapper">
                                <el-tag size="medium">{{ scope.row.name }}</el-tag>
                            </div>
                        </el-popover>
                    </template>
                </el-table-column>
                <el-table-column
                        label="版本号"
                        width="180">
                    <template slot-scope="scope">
                        <span>{{ scope.row.version }}</span>
                    </template>
                </el-table-column>
                <el-table-column
                        label="IP地址"
                        width="180">
                    <template slot-scope="scope">
                        <span>{{ scope.row.ip }}</span>
                    </template>
                </el-table-column>
                <el-table-column
                        label="创建日期"
                        width="180">
                    <template slot-scope="scope">
                        <i class="el-icon-time"></i>
                        <span style="margin-left: 10px">{{ scope.row.date }}</span>
                    </template>
                </el-table-column>
                <el-table-column
                        label="是否启用" width="180">
                    <template slot-scope="scope">
                        <el-tag type="success" v-if="scope.row.status">已启用</el-tag>
                        <el-tag type="danger" v-else>未启用</el-tag>
                    </template>
                </el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button
                                size="mini"
                                @click="stationEdit(scope.row)">编辑</el-button>
                        <el-button
                                size="mini"
                                type="danger"
                                @click="stationDelete(scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <el-button @click="deleteStations" type="danger" size="mini" style="margin-top: 8px;float: left;" :disabled="this.multipleSelection.length==0">批量删除</el-button>
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
                    <el-form :model="station" :rules="rules" ref="stationForm">
                        <el-row>
                            <el-col :span="6">
                                <el-form-item label="编号:" prop="code">
                                    <el-input size="mini" style="width: 150px" prefix-icon="el-icon-edit" v-model="station.code"
                                              placeholder="请输入基站编号"></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="6">
                                <el-form-item label="名称:" prop="name">
                                    <el-input size="mini" style="width: 150px" prefix-icon="el-icon-edit" v-model="station.name"
                                              placeholder="请输入名称"></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="7">
                                <el-form-item label="版本号:" prop="version">
                                    <el-input size="mini" style="width: 150px" prefix-icon="el-icon-edit" v-model="station.version"
                                              placeholder="请输入版本号"></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="7">
                                <el-form-item label="IP地址:" prop="ip">
                                    <el-input size="mini" style="width: 150px" prefix-icon="el-icon-edit" v-model="station.ip"
                                              placeholder="请输入IP地址"></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="7">
                                <el-form-item label="创建日期:" prop="date">
                                    <el-date-picker
                                            v-model="station.date"
                                            size="mini"
                                            type="datetime"
                                            value-format="yyyy-MM-dd HH:mm:ss"
                                            style="width: 150px;"
                                            placeholder="创建日期">
                                    </el-date-picker>
                                </el-form-item>
                            </el-col>
                            <el-col :span="6">
                                <el-form-item label="基站状态:" prop="name">
                                    <el-switch
                                            style="display: block"
                                            v-model="station.status"
                                            active-color="#13ce66"
                                            inactive-color="#ff4949"
                                            active-text="启用"
                                            inactive-text="禁用">
                                    </el-switch>
                                </el-form-item>
                            </el-col>
                        </el-row>
                    </el-form>
                </div>
                <span slot="footer" class="dialog-footer">
                    <el-button @click="dialogVisible = false">取 消</el-button>
                    <el-button type="primary" @click="doAddStation">确 定</el-button>
                </span>
            </el-dialog>
        </div>
    </div>
</template>

<script>
    export default {
        name: "PosStation",
        data(){
            let validate = (rule, value, callback) => {
                let station_code = value;
                this.validateCode('/position/station/base/'+station_code).then(resp=>{
                    if (resp && resp === "TRUE"){
                        callback();
                    }else if (resp && resp === "FALSE" && this.station.id === ''){
                        callback("编号已经存在！");
                    }else {
                        callback();
                    }
                })
            }
            return{
                importDataBtnText: '导入数据',
                importDataBtnIcon: 'el-icon-upload2',
                importDataDisabled: false,
                tableData:[],
                loading: false,
                station_name:'',
                total: 0,
                page: 1,
                size: 10,
                /*弹框初始化设置*/
                title: '',
                dialogVisible: false,
                station:{
                    id:'',
                    code:'',
                    name:'',
                    version:'',
                    ip:'',
                    status:''
                },
                multipleSelection:[],
                rules: {
                    code:[{required:true,message:'请输入编号',trigger:'blur'},
                        {validator:validate, trigger: 'blur'}],
                    name: [{required: true, message: '请输入名称', trigger: 'blur'}],
                    date: [{required: true, message: '请输入创建日期', trigger: 'blur'}],
                    version: [{required: true, message: '请输入版本号', trigger: 'blur'}],
                    ip: [{required: true, message: '请输入ip地址', trigger: 'blur'}, {
                        pattern: /((2(5[0-5]|[0-4]\d))|[0-1]?\d{1,2})(\.((2(5[0-5]|[0-4]\d))|[0-1]?\d{1,2})){3}/g,
                        message: 'ip地址格式不正确',
                        trigger: 'blur'
                    }]
                }
            }
        },
        mounted() {
            this.selectStation();
        },
        methods:{
            stationEdit(data){
                this.selectStation();
                this.title = '编辑基站信息';
                this.station = data;
                this.dialogVisible = true;
            },
            doAddStation(){
                if (this.station.id) {
                    this.$refs['stationForm'].validate(valid => {
                        if (valid) {
                            this.putRequest("/position/station/base/", this.station).then(resp => {
                                if (resp) {
                                    this.dialogVisible = false;
                                    this.selectStation();
                                    this.initStation();
                                }
                            })
                        }
                    });
                } else {
                    this.$refs['stationForm'].validate(valid => {
                        if (valid) {
                            this.postRequest("/position/station/base/", this.station).then(resp => {
                                if (resp) {
                                    this.dialogVisible = false;
                                    this.selectStation();
                                    this.initStation();
                                }
                            })
                        }
                    });
                }
            },
            /*初始化基站数组*/
            initStation(){
                this.station.status='';
                this.station.ip='';
                this.station.version='';
                this.station.name='';
                this.station.code='';
                this.station.id='';
                this.station.date='';
            },
            /*初始化基站列表*/
            selectStation(){
                this.loading = true;
                let url = '/position/station/base/?page=' + this.page + '&size=' + this.size + "&name=" + this.station_name;
                this.getRequest(url).then(resp => {
                    this.loading = false;
                    if (resp) {
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
                this.selectStation();
            },
            onError(err, file, fileList) {
                this.importDataBtnText = '导入数据';
                this.importDataBtnIcon = 'el-icon-upload2';
                this.importDataDisabled = false;
            },
            exportData() {
                window.open('/position/station/base/export', '_parent');
            },
            addStation(){
                this.title = "添加基站";
                this.dialogVisible = true;
            },
            handleSelectionChange(val){
                this.multipleSelection = val;
            },
            currentChange(currentPage) {
                this.page = currentPage;
                this.selectStation();
            },
            sizeChange(currentSize) {
                this.size = currentSize;
                this.selectStation();
            },
            /*删除基站*/
            stationDelete(data){
                this.$confirm('此操作将永久删除【'+data.name+'】, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.deleteRequest('/position/station/base/'+data.id).then(resp=>{
                        if (resp){
                            this.selectStation();
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            /*批量删除基站*/
            deleteStations(){
                this.$confirm('此操作将永久删除【'+this.multipleSelection.length+'】条记录, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    let ids = '?';
                    this.multipleSelection.forEach(item=>{
                        ids += 'ids='+item.id+'&';
                    })
                    this.deleteRequest("/position/station/base/"+ ids).then(resp=>{
                        if (resp){
                            this.selectStation();
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
    .selectStation{
        width: 300px;margin-right: 8px;
    }
    .addStation{
        margin-bottom: 10px;
    }
</style>
