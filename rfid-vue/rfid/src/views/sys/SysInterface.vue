<template>
    <div>
        <iframe ref="iframe" id="bdIframe" :src="bdTokenUrl" frameborder="0" scrolling="auto" ></iframe>
    </div>
</template>

<script>
    export default {
        name: "SysInterface",
        data() {
            return {
                bdTokenUrl: '/swagger-ui.html'
            }
        },
        mounted(){
            const oIframe = document.getElementById('bdIframe');
            const deviceWidth = document.documentElement.clientWidth;
            const deviceHeight = document.documentElement.clientHeight;
            oIframe.style.width = (Number(deviceWidth)-220) + 'px'; //数字是页面布局宽度差值
            oIframe.style.height = (Number(deviceHeight)-120) + 'px'; //数字是页面布局高度差
        }
    }
</script>

<style>

</style>