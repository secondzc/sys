<template>
    <section>

    <div id="tabs" class="tabs-bottom">
        <ul>
            <li><a href="#tabs-1">Nunc tincidunt</a></li>
            <li><a href="#tabs-2">Proin dolor</a></li>
            <li><a href="#tabs-3">Aenean lacinia</a></li>
        </ul>
        <div class="tabs-spacer"></div>
        <div id="tabs-1">
            <p>Proin elit arcu, rutrum commodo, vehicula tempus, commodo a, risus. Curabitur nec arcu. Donec sollicitudin mi sit amet mauris. Nam elementum quam ullamcorper ante. Etiam aliquet massa et lorem. Mauris dapibus lacus auctor risus. Aenean tempor ullamcorper leo. Vivamus sed magna quis ligula eleifend adipiscing. Duis orci. Aliquam sodales tortor vitae ipsum. Aliquam nulla. Duis aliquam molestie erat. Ut et mauris vel pede varius sollicitudin. Sed ut dolor nec orci tincidunt interdum. Phasellus ipsum. Nunc tristique tempus lectus.</p>
        </div>
        <div id="tabs-2">
            <p>Morbi tincidunt, dui sit amet facilisis feugiat, odio metus gravida ante, ut pharetra massa metus id nunc. Duis scelerisque molestie turpis. Sed fringilla, massa eget luctus malesuada, metus eros molestie lectus, ut tempus eros massa ut dolor. Aenean aliquet fringilla sem. Suspendisse sed ligula in ligula suscipit aliquam. Praesent in eros vestibulum mi adipiscing adipiscing. Morbi facilisis. Curabitur ornare consequat nunc. Aenean vel metus. Ut posuere viverra nulla. Aliquam erat volutpat. Pellentesque convallis. Maecenas feugiat, tellus pellentesque pretium posuere, felis lorem euismod felis, eu ornare leo nisi vel felis. Mauris consectetur tortor et purus.</p>
        </div>
        <div id="tabs-3">
            <p>Mauris eleifend est et turpis. Duis id erat. Suspendisse potenti. Aliquam vulputate, pede vel vehicula accumsan, mi neque rutrum erat, eu congue orci lorem eget lorem. Vestibulum non ante. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Fusce sodales. Quisque eu urna vel enim commodo pellentesque. Praesent eu risus hendrerit ligula tempus pretium. Curabitur lorem enim, pretium nec, feugiat nec, luctus a, lacus.</p>
            <p>Duis cursus. Maecenas ligula eros, blandit nec, pharetra at, semper at, magna. Nullam ac lacus. Nulla facilisi. Praesent viverra justo vitae neque. Praesent blandit adipiscing velit. Suspendisse potenti. Donec mattis, pede vel pharetra blandit, magna ligula faucibus eros, id euismod lacus dolor eget odio. Nam scelerisque. Donec non libero sed nulla mattis commodo. Ut sagittis. Donec nisi lectus, feugiat porttitor, tempor ac, tempor vitae, pede. Aenean vehicula velit eu tellus interdum rutrum. Maecenas commodo. Pellentesque nec elit. Fusce in lacus. Vivamus a libero vitae lectus hendrerit hendrerit.</p>
        </div>
    </div>
    </section>
</template>
<script>
    import variabletree from './Variabletree.vue'
    import { mapState,mapGetters} from 'vuex'
    import PackageDiagramModel from "./packageDiagramModel";

    import 'jquery-ui/external/jquery-1.9.1/jquery'
    import 'jquery-ui/ui/widgets/tabs'
    import 'jquery-ui/themes/base/all.css'
    import 'jquery-ui/external/jquery/jquery'

    $(function() {
        $( "#tabs" ).tabs();
        // 修改 class
        $( ".tabs-bottom .ui-tabs-nav, .tabs-bottom .ui-tabs-nav > *" )
            .removeClass( "ui-corner-all ui-corner-top" )
            .addClass( "ui-corner-bottom" );

        // 移动导航到底部
        $( ".tabs-bottom .ui-tabs-nav" ).appendTo( ".tabs-bottom" );
    });
    export default {

        components: {
            PackageDiagramModel,
        },
        data() {
            return {
//                form: {
//                    name: '',
//                    region: '',
//                    date1: '',
//                    date2: '',
//                    delivery: false,
//                    type: [],
//                    resource: '',
//                    desc: ''
//                }
                form:[],
                tableData: [
                ],
                svgUrl:'',
                iconUrl:'',
                infoUrl:'',
                currentRow: null,
            }
        },
        computed: {
            ...mapState({
                modelId: state => state.modelId,
                a:state =>state.a
            }),
            ...mapGetters(['modelId','amsg']),
            getModelVar(){
                var _this = this;
                if(_this.modelId == null || _this.modelId == ''){
                    this.$router.push({path: '/model/index'});
                }else {
                    var url = '/api/model/modelVariable?modelId=' + _this.modelId;
                    _this.$http.post(url)
                        .then(function (response) {
                            _this.form = response.data.form;
                            if(response.data.form.diagramSvgPath == null){
                                _this.svgUrl = "http://gogs.modelica-china.com:8080/FileLibrarys/FileLibrary/zanwu.jpg"
                            }else{
                                _this.svgUrl = response.data.form.diagramSvgPath;
                            }
                            if(response.data.form.iconSvgPath == null){
                                _this.iconUrl = "http://gogs.modelica-china.com:8080/FileLibrarys/FileLibrary/zanwu.jpg"
                            }else{
                                _this.iconUrl = response.data.form.iconSvgPath;
                            }
                            if(response.data.form.infoTextPath == null){
                                _this.infoUrl = ""
                            }else{
                                _this.infoUrl = response.data.form.infoTextPath;
                            }

                        })
                        .catch(function (error) {
                            console.log(error)
                        })


                }
            }
        },
        methods: {
            setCurrent(row) {
                //   this.$refs.singleTable.setCurrentRow(row);
//                this.$store.dispatch('sendModelId',modelId.currentTarget.attributes[2].value);
                var _this=this;
                this.$store.dispatch('sendA',_this.form.directoryParentId);
                this.$router.push({path: '/model/index'});
            },
            handleCurrentChange(val) {
                this.currentRow = val;
            },
//            svgUrl : function () {
//                var svg = this.$refs.form.model.diagramSvgPath;
//                window.open(svg,"_blank");
//            },
//            iconUrl : function () {
//                var svg = this.$refs.form.model.iconSvgPath;
//                window.open(svg,"_blank");
//            },
//            infoUrl : function () {
//                var svg = this.$refs.form.model.infoTextPath;
//                window.open(svg,"_blank");
//            }
        }
    }

</script>
<style scoped>
    .image{
        display: block;
        width: 100%;
        /*需要对高度做限制*/
        max-height: 300px;
        border-top-left-radius: 5px;
        border-top-right-radius: 5px;
        transition: all 1s ease 0s;
    }

    .tabs-bottom .ui-tabs-nav { clear: left; padding: 0 .2em .2em .2em; }
    .tabs-bottom .ui-tabs-nav li { top: auto; bottom: 0; margin: 0 .2em 1px 0; border-bottom: aquamarine; border-top: 0; }
    .tabs-bottom .ui-tabs-nav li.ui-tabs-active { margin-top: -1px; padding-top: 1px; }
</style>
