<template>
    <div>
        <div id="main" style="width: 800px;height:560px;"></div>
    </div>
</template>

<script>
    import {init} from "../../utils/echarts.js";
    export default {
        name: "Statistics",
        data(){
            return{
            }
        },
        mounted() {
            this.initData();
        },
        methods:{
            initData(){
                this.getRequest('/position/visitors/base/Statistics/').then(resp=>{
                    if (resp){
                        let myChart = init(document.getElementById("main"));
                        // 指定图表的配置项和数据
                        let option = {
                            title: {
                                text: ''
                            },
                            tooltip: {},
                            // legend: {
                            //     data:['访客']
                            // },
                            xAxis: {
                                data: [resp[0].days,resp[1].days,resp[2].days,resp[3].days,resp[4].days,resp[5].days,resp[6].days]
                            },
                            yAxis: {},
                            series: [{
                                name: '访客量',
                                type: 'line',
                                data: [resp[0].count,resp[1].count,resp[2].count,resp[3].count,resp[4].count,resp[5].count,resp[6].count]
                            }]
                        };
                        // 使用刚指定的配置项和数据显示图表。
                        myChart.setOption(option);
                    }
                })
            }
        }

    }
</script>

<style scoped>

</style>