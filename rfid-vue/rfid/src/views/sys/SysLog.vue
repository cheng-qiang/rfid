<template>
    <div>
            <div style="display: flex;margin-bottom: 10px">
                <el-input
                        style="width: 200px"
                        @clear="initSysLog"
                        clearable
                        size="mini"
                        prefix-icon="el-icon-search"
                        placeholder="请输入操作员名称查询..."
                        class="selectStation"
                        v-model="username"
                        @keydown.enter.native="initSysLog"></el-input>
                <el-button style="margin-left: 10px" icon="el-icon-search" size="mini" type="info" @click="initSysLog">查询</el-button>
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
                    >
                <el-table-column
                        label="操作员"
                        >
                    <template slot-scope="scope">
                        <span>{{ scope.row.username }}</span>
                    </template>
                </el-table-column>
                <el-table-column
                        label="操作日期"
                        >
                    <template slot-scope="scope">
                        <i class="el-icon-time"></i>
                        <span style="margin-left: 10px">{{ scope.row.addDate }}</span>
                    </template>
                </el-table-column>
                <el-table-column
                        label="操作内容"
                       >
                    <template slot-scope="scope">
                        <span>{{ scope.row.operate }}</span>
                    </template>
                </el-table-column>
            </el-table>
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
    </div>
</template>

<script>
    export default {
        name: "SysLog",
        data(){
            return{
                tableData:[],
                total: 0,
                page: 1,
                size: 10,
                username:''
            }
        },
        mounted() {
            this.initSysLog();
        },
        methods:{
            currentChange(currentPage) {
                this.page = currentPage;
                this.selectStation();
            },
            sizeChange(currentSize) {
                this.size = currentSize;
                this.selectStation();
            },
            initSysLog(){
                this.getRequest('/system/interface/?page=' + this.page + '&size=' + this.size+'&username='+this.username).then(resp=>{
                    if (resp){
                        this.tableData = resp.records;
                        this.total = resp.total;
                    }
                })
            }
        }
    }
</script>

<style scoped>

</style>
