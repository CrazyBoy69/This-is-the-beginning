let app = new Vue({
    el:'#add',
    data:{
        text:'',
        aggressivity:'',
        bloodVolume:'',
        poison:'',
        holyShield:'',
        player1aggressivitys:[],
        player1bloodVolumes:[],
        player1poisons:[],
        player1holyShields:[],
        player2aggressivitys:[],
        player2bloodVolumes:[],
        player2poisons:[],
        player2holyShields:[],
        player1Entourages:[],
        player2Entourages:[],
        totalTouts:'',
        player1:[],
        player2:[],
        player1Win:0,
        player2Win:0,
        player1WinP:0,
        player2WinP:0,
    },
    methods:{

        add:function (num){
            if(this.bloodVolume <= 0){
                alert("血量值错误")
                return
            }
            let header =
                "<div class=\"col\">\n" +
                "   <div class=\"card container\" style=\"width: 12rem;height: 14rem\">\n" +
                "      <img src=\"image/7.jpeg\" style=\"width:100%;min-height:100%\" class=\"card-img\" alt=\"...\">\n" +
                "      <div class=\"card-img-overlay\">\n" +
                "         <h4 style=\"float:left;color: blue\"><i class=\"bi bi-lightning-charge-fill aggressivity\" style=\"font-size: 1.3rem;color: blue\">"+this.aggressivity+"</i></h4>\n"
            if (this.holyShield){
                 header+="<i class=\"bi bi-shield\" style=\"font-size: 1.3rem\"></i>\n"
            }
            if (this.poison){
                 header+="<i class=\"bi bi-droplet-half\" style=\"font-size: 1.3rem;color: green\"></i>\n"
            }
            let fort =
                "         <h4 style=\"float:right;color: red\"><i class=\"bi bi-heart-fill bloodVolume\" style=\"font-size: 1.3rem;color: red\">"+this.bloodVolume+"</i></h4>\n" +
                "      </div>\n" +
                "    </div>\n" +
                "</div>"
            if (this.holyShield === ""){
                this.holyShield = false
            }
            if (this.poison === ""){
                this.poison = false
            }
            if (num === 1){
                $(".player1").append(header+fort)
                this.player1aggressivitys.push(this.aggressivity)
                this.player1bloodVolumes.push(this.bloodVolume)
                this.player1holyShields.push(this.holyShield)
                this.player1poisons.push(this.poison)
            }else {
                $(".player2").append(header+fort)
                this.player2aggressivitys.push(this.aggressivity)
                this.player2bloodVolumes.push(this.bloodVolume)
                this.player2holyShields.push(this.holyShield)
                this.player2poisons.push(this.poison)
            }
            this.aggressivity = ""
            this.bloodVolume = ""
            this.holyShield = false
            this.poison = false
            this.text = "添加成功!!"
            setTimeout(function(){
                app.insert = ""
            },1000);
        },

        fight:function (){
            if (this.totalTouts === ""){
                return alert("请输入场数!")
            }
            this.player1Entourages = []
            this.player2Entourages = []
            this.player1Win = 0
            this.player2Win = 0
            this.player1WinP = 0
            this.player2WinP = 0
            let Entourage = {
                aggressivity:'',
                bloodVolume:'',
                poison:'',
                holyShield:''
            };
            for (let i = 0; i<this.player1aggressivitys.length; i++) {
                Entourage.aggressivity = Number(this.player1aggressivitys[i]);
                Entourage.bloodVolume = Number(this.player1bloodVolumes[i]);
                if (this.player1poisons[i]){
                    Entourage.poison = 1
                }else {
                    Entourage.poison = 0
                }
                if (this.player1holyShields[i]){
                    Entourage.holyShield = 1
                }else {
                    Entourage.holyShield = 0
                }
                this.player1Entourages.push(Entourage)
                Entourage = {}
            }
            for (let k = 0; k<this.player2aggressivitys.length; k++) {
                Entourage.aggressivity = Number(this.player2aggressivitys[k]);
                Entourage.bloodVolume = Number(this.player2bloodVolumes[k]);
                if (this.player2poisons[k]){
                    Entourage.poison = 1
                }else {
                    Entourage.poison = 0
                }
                if (this.player2holyShields[k]){
                    Entourage.holyShield = 1
                }else {
                    Entourage.holyShield = 0
                }
                this.player2Entourages.push(Entourage)
                Entourage = {}
            }
            $.ajax({
                url:'/entourage',
                type:'post',
                data: {
                    entourages : JSON.stringify({player1Entourage: this.player1Entourages, player2Entourage:this.player2Entourages}),
                    totalTout : this.totalTouts
                },
                success:function (data){
                    if (data === null){
                        return alert("数据错误!")
                    }
                    app.player1 = data["player1"]
                    app.player2 = data["player2"]
                    app.player1Win = app.player1["VictoryField"]
                    app.player1WinP = app.player1["WinningProbability"]
                    app.player2Win = app.player2["VictoryField"]
                    app.player2WinP = app.player2["WinningProbability"]
                },
                error:function (){
                    return alert("数据错误!")
                }
            });
        }
    }
})
