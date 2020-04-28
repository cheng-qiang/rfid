<template>
    <div>
        <div class="card">
            <div style="float: left;display: flex;">
                <el-input
                        @clear="initCard"
                        style="margin-right: 8px;"
                        type="text"
                        v-model="tabStationCode"
                        prefix-icon="el-icon-search"
                        placeholder="请输入RFID标签编号"
                        @keydown.enter.native="initCard"
                        :disabled="showAdvanceSearchView"></el-input>
                <el-input
                        @clear="initCard"
                        type="text"
                        v-model="visitorName"
                        prefix-icon="el-icon-search"
                        placeholder="请输入访客人员名字"
                        @keydown.enter.native="initCard"
                        :disabled="showAdvanceSearchView"
                ></el-input>
                <el-button style="margin-left: 8px;" icon="el-icon-search" type="primary" @click="initCard" :disabled="showAdvanceSearchView">
                    搜索
                </el-button>
                <el-button type="primary" @click="showAdvanceSearchView = !showAdvanceSearchView">
                    <i :class="showAdvanceSearchView?'fa fa-angle-double-up':'fa fa-angle-double-down'"
                       aria-hidden="true"></i>
                    高级搜索
                </el-button>
            </div>
            <div style="display: flex;justify-content: flex-end">
                <el-button
                        type="primary"
                        @click="addCard"><i class="el-icon-add-location"></i><span>新增绑定</span></el-button>
            </div>
        </div>
        <div>
        <transition name="slide-fade">
            <div v-show="showAdvanceSearchView" style="border: 1px solid #409eff;border-radius: 5px;box-sizing: border-box;padding: 5px;margin: 10px 0px;">
                <el-row style="margin-bottom: 10px;margin-left: 10px;">
                    <el-col :span="7">
                        业务操作员：
                        <el-select v-model="searchValue.userId" placeholder="请选择..." size="mini"
                                   style="width: 130px;">
                            <el-option
                                    v-for="item in users"
                                    :key="item.id"
                                    :label="item.name"
                                    :value="item.id">
                            </el-option></el-select>
                    </el-col>
                    <el-col :span="5">
                        工作状态：
                        <el-select v-model="searchValue.status" placeholder="请选择..." size="mini"
                                   style="width: 130px;">
                            <el-option
                                    v-for="item in status"
                                    :key="item.value"
                                    :label="item.label"
                                    :value="item.value">
                            </el-option></el-select>
                    </el-col>
                </el-row>
              <el-row style="margin-left: 10px;">
                    <el-col :span="17">
                        录入时间：
                        <el-date-picker
                                size="mini"
                                type="daterange"
                                unlink-panels
                                v-model="searchValue.createDate"
                                value-format="yyyy-MM-dd"
                                range-separator="至"
                                start-placeholder="开始日期"
                                end-placeholder="结束日期">
                        </el-date-picker>
                    </el-col>
                    <!--<el-col :span="10">
                        工作日期：
                        <el-date-picker
                                v-model="searchValue.work_date"
                                type="daterange"
                                size="mini"
                                unlink-panels
                                value-format="yyyy-MM-dd"
                                range-separator="至"
                                start-placeholder="开始日期"
                                end-placeholder="结束日期">
                        </el-date-picker>
                    </el-col>-->
                    <el-col :span="3" :offset="4">
                        <el-button size="mini">取消</el-button>
                        <el-button size="mini" icon="el-icon-search" type="primary" @click="initCard('advanced')">搜索</el-button>
                    </el-col>
                </el-row>
            </div>
        </transition>
        </div>
        <!--表格-->
        <div>
            <el-table
                    :data="tableData"
                    size="small"
                    stripe
                    border
                    style="width: 100%"
                    max-height="250"
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
                        label="标签编号"
                        width="100">
                    <template slot-scope="scope">
                        <span>{{ scope.row.tabCode }}</span>
                    </template>
                </el-table-column>
                <el-table-column
                        fixed
                        label="访客姓名"
                        width="100">
                    <template slot-scope="scope">
                        <el-popover trigger="hover" placement="top">
                            <p>工作描述: {{ scope.row.work }}</p>
                            <div slot="reference" class="name-wrapper">
                                <el-tag size="medium">{{ scope.row.visitorsName }}</el-tag>
                            </div>
                        </el-popover>
                    </template>
                </el-table-column>
                <el-table-column
                        label="录入时间"
                        width="180">
                    <template slot-scope="scope">
                        <span style="margin-left: 10px">{{ scope.row.createDate }}</span>
                    </template>
                </el-table-column>
                <el-table-column
                        label="开始时间"
                        width="180">
                    <template slot-scope="scope">
                        <span style="margin-left: 10px">{{ scope.row.startDate }}</span>
                    </template>
                </el-table-column>
                <el-table-column
                        label="结束时间"
                        width="180">
                    <template slot-scope="scope">
                        <span style="margin-left: 10px">{{ scope.row.endDate }}</span>
                    </template>
                </el-table-column>
                <el-table-column
                        label="归还时间"
                        width="180">
                    <template slot-scope="scope">
                        <span style="margin-left: 10px">{{ scope.row.backDate }}</span>
                    </template>
                </el-table-column>
                <el-table-column
                        label="工作状态"
                        width="100">
                    <template slot-scope="scope">
                        <span>{{ work_status[scope.row.status] }}</span>
                    </template>
                </el-table-column>
                <el-table-column
                        label="业务操作员"
                        width="100">
                    <template slot-scope="scope">
                        <span style="margin-left: 10px">{{ scope.row.userName }}</span>
                    </template>
                </el-table-column>
                <el-table-column label="操作" fixed="right" width="200">
                    <template slot-scope="scope">
                        <el-button
                                size="mini"
                                type="success"
                                style="padding: 3px"
                                @click="endWork(scope.row)">结束工作</el-button>
                        <el-button
                                size="mini"
                                type="primary"
                                style="padding: 3px"
                                @click="cardEdit(scope.row)">编辑</el-button>
                        <el-button
                                size="mini"
                                style="padding: 3px"
                                type="danger"
                                @click="cardDelete(scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <el-button @click="deleteCards" type="danger" size="mini" style="margin-top: 8px;float: left;" :disabled="this.multipleSelection.length==0">批量删除</el-button>
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
<!--弹出框-->
        <div>
            <el-dialog
                    :title="title"
                    :visible.sync="dialogVisible"
                    width="60%">
                <div>
                    <el-form :model="card" :rules="rules" ref="cardForm">
                        <el-row style="display: flex;justify-content: space-around;">
                            <el-col :span="8">
                                <el-form-item label="业务操作员:" prop="userId">
                                    <el-select v-model="card.userId" placeholder="请输入关键字" size="mini"
                                               filterable
                                               remote
                                               reserve-keyword
                                               :remote-method="remoteMethodUser"
                                               style="width: 130px;"
                                               :loading="loading">
                                        <el-option
                                                v-for="item in users"
                                                :key="item.id"
                                                :label="item.label"
                                                :value="item.id">
                                        </el-option></el-select>
                                </el-form-item>
                            </el-col>
                            <el-col :span="7">
                                <el-form-item label="访客名称:" prop="visitorsId">
                                    <el-select v-model="card.visitorsId"
                                               filterable
                                               remote
                                               reserve-keyword
                                               placeholder="请输入关键字"
                                               :remote-method="remoteMethod"
                                               size="mini"
                                               style="width: 130px;"
                                               :loading="loading">
                                        <el-option
                                                v-for="item in options"
                                                :key="item.id"
                                                :label="item.label"
                                                :value="item.id">
                                        </el-option></el-select>
                                </el-form-item>
                            </el-col>
                            <el-col :span="7" >
                                <el-form-item label="标签编号:" prop="TabStationId">
                                    <el-select v-model="card.TabStationId"
                                               filterable
                                               remote
                                               reserve-keyword
                                               placeholder="请输入关键字"
                                               :remote-method="remoteMethodTabStationId"
                                               size="mini"
                                               style="width: 130px;"
                                               :loading="loading">
                                        <el-option
                                                v-for="item in optionsTabStationId"
                                                :key="item.id"
                                                :label="item.label"
                                                :value="item.id">
                                        </el-option></el-select>
                                </el-form-item>
                            </el-col>
                            <el-col :span="7" style="float: left;">
                                <el-form-item label="工作状态:" prop="status">
                                <el-select v-model="card.status" placeholder="请选择..." size="mini"
                                           style="width: 130px;">
                                    <el-option
                                            v-for="item in status"
                                            :key="item.value"
                                            :label="item.label"
                                            :value="item.value">
                                    </el-option></el-select>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="7">
                                <el-form-item label="绑定时间:" prop="createDate">
                                    <el-date-picker
                                            v-model="card.createDate"
                                            size="mini"
                                            type="datetime"
                                            value-format="yyyy-MM-dd HH:mm:ss"
                                            style="width: 175px;"
                                            placeholder="绑定时间">
                                    </el-date-picker>
                                </el-form-item>
                            </el-col>
                            <el-col :span="7">
                                <el-form-item label="开始时间:" prop="startDate">
                                    <el-date-picker
                                            v-model="card.startDate"
                                            size="mini"
                                            type="datetime"
                                            value-format="yyyy-MM-dd HH:mm:ss"
                                            style="width: 175px;"
                                            placeholder="开始时间">
                                    </el-date-picker>
                                </el-form-item>
                            </el-col>
                            <el-col :span="7">
                                <el-form-item label="结束时间:" prop="endDate">
                                    <el-date-picker
                                            v-model="card.endDate"
                                            size="mini"
                                            type="datetime"
                                            value-format="yyyy-MM-dd HH:mm:ss"
                                            style="width: 175px;"
                                            placeholder="结束时间">
                                    </el-date-picker>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="20">
                                <el-form-item label="工作描述:" prop="work">
                                    <el-input type="textarea"  :rows="5" v-model="card.work"
                                              placeholder="请输入内容"></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>
                    </el-form>
                </div>
                <span slot="footer" class="dialog-footer">
                    <el-button @click="dialogVisible = false">取 消</el-button>
                    <el-button type="primary" @click="doAddCard">确 定</el-button>
                </span>
            </el-dialog>
        </div>
    </div>
</template>

<script>
    export default {
        name: "PosCard",
        data(){
            return{
                tabStationCode:"",
                visitorName:'',
                showAdvanceSearchView: false,
                searchValue:{
                    createDate:'',
                    /*work_date:'',*/
                    status:'',
                    userId:''
                },
                title: '',
                dialogVisible: false,
                loading: false,
                list: [],
                listTabStation:[],
                total: 0,
                page: 1,
                size: 10,
                work_status:['未工作','工作中','结束工作','暂停工作'],
                multipleSelection:[],
                tableData:[],
                card:{
                    id:'',
                    userId:'',
                    TabStationId:'',
                    visitorsId:'',
                    createDate:'',
                    startDate:'',
                    endDate:'',
                    backDate:'',
                    status:'',
                    work:''
                },
                visitors:[],
                tab:[],
                listUser:[],
                options:[],
                optionsTabStationId:[],
                users:[],
                status:[{
                    label:'未工作',
                    value:0
                },{
                    label:'工作中',
                    value:1
                },{
                    label:'结束工作',
                    value:2
                },{
                    label:'暂停工作',
                    value:3
                }],
                rules: {

                }
            }
        },
        mounted() {
            this.initCard();
        },
        methods:{
            initEdit(){
                this.card.status = "";
                this.card.work = "";
                this.card.backDate = "";
                this.card.endDate = "";
                this.card.startDate = "";
                this.card.createDate = "";
                this.card.visitorsId = "";
                this.card.TabStationId = "";
                this.card.userId = "";
                this.card.id = "";
                this.options = [];
                this.users = [];
                this.optionsTabStationId = [];
            },
            cardEdit(data){
                this.title = "编辑绑定";
                this.card = data;
                console.log(this.card)
                this.remoteMethod(data.visitorsName);
                this.remoteMethodTabStationId(data.tabCode);
                this.remoteMethodUser(data.userName);
                this.dialogVisible = true;
            },
            addCard(){
                this.title = "新增绑定";
                this.dialogVisible = true;
            },
            remoteMethod(query) {
                if (query !== '') {
                    this.loading = true;
                    setTimeout(() => {
                        this.loading = false;
                        this.options = this.list.filter(item => {
                            return item.label.indexOf(query) > -1;
                        });
                    }, 200);
                } else {
                    this.options = [];
                }
            },
            remoteMethodUser(query){
                if (query !== '') {
                    this.loading = true;
                    setTimeout(() => {
                        this.loading = false;
                        this.users = this.listUser.filter(item => {
                            console.log(item.value+"+++++++++++++++++++")
                            console.log(item.label+"*******************")
                            return item.label.indexOf(query) > -1;
                        });
                        console.log(this.users+"----------------")
                    }, 200);
                } else {
                    this.users = [];
                }
                console.log(this.users+">>>>>>>>>>>>>>")
            },
            remoteMethodTabStationId(query) {
                if (query !== '') {
                    this.loading = true;
                    setTimeout(() => {
                        this.loading = false;
                        this.optionsTabStationId = this.listTabStation.filter(item => {
                            return item.label.indexOf(query) > -1;
                        });
                    }, 200);
                } else {
                    this.optionsTabStationId = [];
                }
            },
            initSearchIf(){
                this.tabStationCode = "";
                this.visitorName = "";
                this.searchValue.userId = "";
                this.searchValue.status = "";
                this.searchValue.createDate = "";
            },
            initCard(type){
                this.initUser();
                this.initVisitors();
                this.initTabStation();
                let url = '/position/card/base/?page=' + this.page + '&size=' + this.size;
                if (type && type == 'advanced') {
                    if (this.searchValue.userId != ""){
                        url += "&userId=" + this.searchValue.userId;
                    }
                    if (this.searchValue.status != ""){
                        url += "&status=" + this.searchValue.status;
                    }
                    if (this.searchValue.createDate != ""){
                        url += "&createDate=" + this.searchValue.createDate;
                    }
                }else {
                    url += "&tabStationCode=" + this.tabStationCode+"&visitorName="+this.visitorName;
                }
                this.getRequest(url).then(resp=>{
                    if (resp){
                        this.tableData = resp.data;
                        this.total = resp.total;
                        this.initSearchIf();
                    }
                })
            },
            /*************************************************************************************/
            initUser(){
                this.getRequest('/system/user/?keyWords='+"").then(resp=>{
                    if (resp){
                        this.listUser = resp.map(item=>{
                            return { value: `${item.id}`, label: `${item.name}` };
                        });
                        console.log(this.listUser)
                    }
                })
            },
            initVisitors(){
                this.getRequest("/position/visitors/base/").then(resp => {
                    if (resp) {
                        this.visitors = resp.records;
                        this.list = this.visitors.map(item=>{
                            return { value: `${item.id}`, label: `${item.name}` };
                        });
                        console.log(this.list);
                    }
                });
            },
            initTabStation(){
                this.getRequest("/position/label/base/").then(resp => {
                    if (resp) {
                        this.tab = resp.records;
                        this.listTabStation = this.tab.map(item=>{
                            return { value: `${item.id}`, label: `${item.code}` };
                        });
                        console.log(this.listTabStation)
                    }
                });
            },
            /*************************************************************************************/
            currentChange(currentPage) {
                this.page = currentPage;
                this.initCard();
            },
            sizeChange(currentSize) {
                this.size = currentSize;
                this.initCard();
            },
            handleSelectionChange(val){
                this.multipleSelection = val;
            },
            doAddCard(){
                    if (this.card.id) {
                        console.log(this.card.userId)
                        console.log(this.card.status)
                        this.$refs['cardForm'].validate(valid => {
                            if (valid) {
                                this.putRequest("/position/card/base/", this.card).then(resp => {
                                    if (resp) {
                                        this.dialogVisible = false;
                                        this.initCard();
                                        // this.initEdit();
                                    }
                                })
                            }
                        });
                    } else {
                        this.$refs['cardForm'].validate(valid => {
                            if (valid) {
                                this.postRequest("/position/card/base/", this.card).then(resp => {
                                    if (resp) {
                                        this.dialogVisible = false;
                                        this.initCard();
                                        // this.initEdit();
                                    }
                                })
                            }
                        });
                    }
            },
            cardDelete(data){
                this.$confirm('此操作将永久删除此记录, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.deleteRequest("/position/card/base/"+ data.id).then(resp=>{
                        if (resp){
                            this.initCard();
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            deleteCards(){
                this.$confirm('此操作将永久删除【'+this.multipleSelection.length+'】条记录, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    let ids = '?';
                    this.multipleSelection.forEach(item=>{
                        ids += 'ids='+item.id+'&';
                    })
                    this.deleteRequest("/position/card/base/"+ ids).then(resp=>{
                        if (resp){
                            this.initCard();
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
    .card{
        margin-bottom: 10px;
    }
</style>
