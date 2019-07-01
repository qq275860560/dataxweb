define(['jquery','jquery.easypiechart.min','Vue'],function($,easyPieChart,Vue){
    function initindex(){
		$(".fh5co-tab-menu li").click(function(){
			var tab=$(this);
			$(".fh5co-tab-menu li").removeClass("active");
			tab.addClass("active");
			var tabnum=tab.find("a").eq(0).attr("data-tab");
			$(".fh5co-tab-content").each(function(){
				var tabcontnet=$(this);
				var datacontentnum=tabcontnet.attr("data-content");
				if(tabnum==datacontentnum){
					$(".fh5co-tab-content").removeClass("active");
					tabcontnet.addClass("active");
				}
			});
		});
      //$(".fh5co-tab-content").html("my says:it works");
    }
	//图表的插件函数
	var pieChart = function() {
		$('.chart').easyPieChart({
			scaleColor: false,
			lineWidth: 10,
			lineCap: 'butt',
			barColor: '#17e7a4',
			trackColor:	"#000000",
			size: 160,
			animate: 1000
		});
	};
	function initprofile(){
		var profileData = {
            name: 'Hello World!',
			profile_img: 'http://img2.imgtn.bdimg.com/it/u=2168108248,1477057420&fm=11&gp=0.jpg',
			profile:'介绍介绍',
			workyear:'8',
			job:'Years of experience in Web Design',
			jobremark:'job Far far away, behind the word mountains, far from the countries.far from the countries.',
			joblist:[
				{time:"2001-2004",remark:"1Far far away, behind the word mountains, far from the countries."},
				{time:"2004-2008",remark:"2Far far away, behind the word mountains, far from the countries."},
				{time:"2008-2012",remark:"3Far far away, behind the word mountains, far from the countries."}
			],
			companyremark:'I have the privilege to work with these cool companies.',
			companylist:[
				{time:"2008 - PRESENT",company:"XYZ Inc.",remark:"1Far far away, behind the word mountains, far from the countries."},
				{time:"2004-2008",company:"Previous Ltd Co.",remark:"1Far far away, behind the word mountains, far from the countries."},
				{time:"1999-2004",company:"Previous Ltd Co.",remark:"1Far far away, behind the word mountains, far from the countries."}
			],
			skills:'Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts.',
			skilllist:[
				{name:"HTML5",num:"95"},
				{name:"CSS3",num:"50"},
				{name:"jQuery",num:"90"},
				{name:"PHP",num:"89"},
				{name:"MySQL",num:"85"},
				{name:"Vue",num:"50"}
			]
        }
		// 创建一个 Vue 实例或 "ViewModel"
        // 它连接 View 与 Model
        new Vue({
            el: '#vue-profile',
            data: profileData
        });
		new Vue({
            el: '#vue-job',
            data: profileData
        });
		new Vue({
            el: '#vue-company',
            data: profileData
        });
		new Vue({
            el: '#vue-skills',
            data: profileData
        });
		setTimeout(function(){
			pieChart();
		}, 800);
	}
    initindex();
	initprofile();
})