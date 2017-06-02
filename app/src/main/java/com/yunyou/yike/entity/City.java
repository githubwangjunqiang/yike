package com.yunyou.yike.entity;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/6/1.
 */

public class City {

    /**
     * retcode : 2000
     * msg : 获取成功
     * data : [{"id":"36","name":"安庆","pingyin":"A"},{"id":"112","name":"安顺","pingyin":"A"},{"id":"152","name":"安阳","pingyin":"A"},{"id":"246","name":"鞍山","pingyin":"A"},{"id":"259","name":"阿拉善盟","pingyin":"A"},{"id":"312","name":"安康","pingyin":"A"},{"id":"324","name":"阿坝","pingyin":"A"},{"id":"345","name":"阿里","pingyin":"A"},{"id":"352","name":"阿克苏","pingyin":"A"},{"id":"353","name":"阿拉尔","pingyin":"A"},{"id":"396","name":"澳门","pingyin":"A"},{"id":"37","name":"蚌埠","pingyin":"B"},{"id":"52","name":"北京","pingyin":"B"},{"id":"63","name":"白银","pingyin":"B"},{"id":"99","name":"百色","pingyin":"B"},{"id":"100","name":"北海","pingyin":"B"},{"id":"113","name":"毕节","pingyin":"B"},{"id":"122","name":"白沙","pingyin":"B"},{"id":"123","name":"保亭","pingyin":"B"},{"id":"139","name":"保定","pingyin":"B"},{"id":"213","name":"白城","pingyin":"B"},{"id":"214","name":"白山","pingyin":"B"},{"id":"247","name":"本溪","pingyin":"B"},{"id":"260","name":"巴彦淖尔盟","pingyin":"B"},{"id":"261","name":"包头","pingyin":"B"},{"id":"285","name":"滨州","pingyin":"B"},{"id":"313","name":"宝鸡","pingyin":"B"},{"id":"325","name":"巴中","pingyin":"B"},{"id":"354","name":"巴音郭楞","pingyin":"B"},{"id":"355","name":"博尔塔拉","pingyin":"B"},{"id":"371","name":"保山","pingyin":"B"},{"id":"3432","name":"北平","pingyin":"B"},{"id":"38","name":"巢湖","pingyin":"C"},{"id":"39","name":"池州","pingyin":"C"},{"id":"40","name":"滁州","pingyin":"C"},{"id":"78","name":"潮州","pingyin":"C"},{"id":"101","name":"崇左","pingyin":"C"},{"id":"124","name":"昌江","pingyin":"C"},{"id":"125","name":"澄迈县","pingyin":"C"},{"id":"140","name":"沧州","pingyin":"C"},{"id":"141","name":"承德","pingyin":"C"},{"id":"197","name":"长沙","pingyin":"C"},{"id":"199","name":"常德","pingyin":"C"},{"id":"200","name":"郴州","pingyin":"C"},{"id":"211","name":"长春","pingyin":"C"},{"id":"223","name":"常州","pingyin":"C"},{"id":"248","name":"朝阳","pingyin":"C"},{"id":"262","name":"赤峰","pingyin":"C"},{"id":"301","name":"长治","pingyin":"C"},{"id":"322","name":"成都","pingyin":"C"},{"id":"346","name":"昌都","pingyin":"C"},{"id":"356","name":"昌吉","pingyin":"C"},{"id":"372","name":"楚雄","pingyin":"C"},{"id":"64","name":"定西","pingyin":"D"},{"id":"79","name":"东莞","pingyin":"D"},{"id":"126","name":"定安县","pingyin":"D"},{"id":"127","name":"东方","pingyin":"D"},{"id":"168","name":"大庆","pingyin":"D"},{"id":"169","name":"大兴安岭","pingyin":"D"},{"id":"245","name":"大连","pingyin":"D"},{"id":"249","name":"丹东","pingyin":"D"},{"id":"286","name":"德州","pingyin":"D"},{"id":"287","name":"东营","pingyin":"D"},{"id":"302","name":"大同","pingyin":"D"},{"id":"326","name":"达州","pingyin":"D"},{"id":"327","name":"德阳","pingyin":"D"},{"id":"373","name":"大理","pingyin":"D"},{"id":"374","name":"德宏","pingyin":"D"},{"id":"375","name":"迪庆","pingyin":"D"},{"id":"3427","name":"第什营乡","pingyin":"D"},{"id":"3430","name":"东盖村","pingyin":"D"},{"id":"182","name":"鄂州","pingyin":"E"},{"id":"196","name":"恩施","pingyin":"E"},{"id":"263","name":"鄂尔多斯","pingyin":"E"},{"id":"41","name":"阜阳","pingyin":"F"},{"id":"53","name":"福州","pingyin":"F"},{"id":"80","name":"佛山","pingyin":"F"},{"id":"102","name":"防城港","pingyin":"F"},{"id":"234","name":"抚州","pingyin":"F"},{"id":"250","name":"抚顺","pingyin":"F"},{"id":"251","name":"阜新","pingyin":"F"},{"id":"65","name":"甘南","pingyin":"G"},{"id":"76","name":"广州","pingyin":"G"},{"id":"98","name":"桂林","pingyin":"G"},{"id":"103","name":"贵港","pingyin":"G"},{"id":"111","name":"贵阳","pingyin":"G"},{"id":"235","name":"赣州","pingyin":"G"},{"id":"271","name":"固原","pingyin":"G"},{"id":"276","name":"果洛","pingyin":"G"},{"id":"328","name":"甘孜","pingyin":"G"},{"id":"329","name":"广安","pingyin":"G"},{"id":"330","name":"广元","pingyin":"G"},{"id":"42","name":"淮北","pingyin":"H"},{"id":"43","name":"淮南","pingyin":"H"},{"id":"44","name":"黄山","pingyin":"H"},{"id":"81","name":"河源","pingyin":"H"},{"id":"82","name":"惠州","pingyin":"H"},{"id":"104","name":"河池","pingyin":"H"},{"id":"105","name":"贺州","pingyin":"H"},{"id":"120","name":"海口","pingyin":"H"},{"id":"142","name":"邯郸","pingyin":"H"},{"id":"143","name":"衡水","pingyin":"H"},{"id":"153","name":"鹤壁","pingyin":"H"},{"id":"167","name":"哈尔滨","pingyin":"H"},{"id":"170","name":"鹤岗","pingyin":"H"},{"id":"171","name":"黑河","pingyin":"H"},{"id":"183","name":"黄冈","pingyin":"H"},{"id":"184","name":"黄石","pingyin":"H"},{"id":"201","name":"衡阳","pingyin":"H"},{"id":"202","name":"怀化","pingyin":"H"},{"id":"224","name":"淮安","pingyin":"H"},{"id":"252","name":"葫芦岛","pingyin":"H"},{"id":"258","name":"呼和浩特","pingyin":"H"},{"id":"264","name":"呼伦贝尔","pingyin":"H"},{"id":"277","name":"海北","pingyin":"H"},{"id":"278","name":"海东","pingyin":"H"},{"id":"279","name":"海南","pingyin":"H"},{"id":"280","name":"海西","pingyin":"H"},{"id":"281","name":"黄南","pingyin":"H"},{"id":"288","name":"菏泽","pingyin":"H"},{"id":"314","name":"汉中","pingyin":"H"},{"id":"357","name":"哈密","pingyin":"H"},{"id":"358","name":"和田","pingyin":"H"},{"id":"376","name":"红河","pingyin":"H"},{"id":"383","name":"杭州","pingyin":"H"},{"id":"384","name":"湖州","pingyin":"H"},{"id":"3401","name":"合肥","pingyin":"H"},{"id":"66","name":"嘉峪关","pingyin":"J"},{"id":"67","name":"金昌","pingyin":"J"},{"id":"68","name":"酒泉","pingyin":"J"},{"id":"83","name":"江门","pingyin":"J"},{"id":"84","name":"揭阳","pingyin":"J"},{"id":"154","name":"济源","pingyin":"J"},{"id":"155","name":"焦作","pingyin":"J"},{"id":"172","name":"鸡西","pingyin":"J"},{"id":"173","name":"佳木斯","pingyin":"J"},{"id":"185","name":"荆门","pingyin":"J"},{"id":"186","name":"荆州","pingyin":"J"},{"id":"212","name":"吉林","pingyin":"J"},{"id":"236","name":"吉安","pingyin":"J"},{"id":"237","name":"景德镇","pingyin":"J"},{"id":"238","name":"九江","pingyin":"J"},{"id":"253","name":"锦州","pingyin":"J"},{"id":"283","name":"济南","pingyin":"J"},{"id":"289","name":"济宁","pingyin":"J"},{"id":"303","name":"晋城","pingyin":"J"},{"id":"304","name":"晋中","pingyin":"J"},{"id":"385","name":"嘉兴","pingyin":"J"},{"id":"386","name":"金华","pingyin":"J"},{"id":"151","name":"开封","pingyin":"K"},{"id":"359","name":"喀什","pingyin":"K"},{"id":"360","name":"克拉玛依","pingyin":"K"},{"id":"3428","name":"康家洼村","pingyin":"K"},{"id":"45","name":"六安","pingyin":"L"},{"id":"54","name":"龙岩","pingyin":"L"},{"id":"62","name":"兰州","pingyin":"L"},{"id":"69","name":"临夏","pingyin":"L"},{"id":"70","name":"陇南","pingyin":"L"},{"id":"106","name":"来宾","pingyin":"L"},{"id":"107","name":"柳州","pingyin":"L"},{"id":"114","name":"六盘水","pingyin":"L"},{"id":"128","name":"乐东","pingyin":"L"},{"id":"129","name":"临高县","pingyin":"L"},{"id":"130","name":"陵水","pingyin":"L"},{"id":"144","name":"廊坊","pingyin":"L"},{"id":"150","name":"洛阳","pingyin":"L"},{"id":"203","name":"娄底","pingyin":"L"},{"id":"215","name":"辽源","pingyin":"L"},{"id":"225","name":"连云港","pingyin":"L"},{"id":"254","name":"辽阳","pingyin":"L"},{"id":"290","name":"莱芜","pingyin":"L"},{"id":"291","name":"聊城","pingyin":"L"},{"id":"292","name":"临沂","pingyin":"L"},{"id":"305","name":"临汾","pingyin":"L"},{"id":"306","name":"吕梁","pingyin":"L"},{"id":"331","name":"乐山","pingyin":"L"},{"id":"332","name":"凉山","pingyin":"L"},{"id":"344","name":"拉萨","pingyin":"L"},{"id":"347","name":"林芝","pingyin":"L"},{"id":"370","name":"丽江","pingyin":"L"},{"id":"377","name":"临沧","pingyin":"L"},{"id":"387","name":"丽水","pingyin":"L"},{"id":"46","name":"马鞍山","pingyin":"M"},{"id":"85","name":"茂名","pingyin":"M"},{"id":"86","name":"梅州","pingyin":"M"},{"id":"174","name":"牡丹江","pingyin":"M"},{"id":"323","name":"绵阳","pingyin":"M"},{"id":"333","name":"眉山","pingyin":"M"},{"id":"55","name":"南平","pingyin":"N"},{"id":"56","name":"宁德","pingyin":"N"},{"id":"97","name":"南宁","pingyin":"N"},{"id":"156","name":"南阳","pingyin":"N"},{"id":"220","name":"南京","pingyin":"N"},{"id":"226","name":"南通","pingyin":"N"},{"id":"233","name":"南昌","pingyin":"N"},{"id":"334","name":"南充","pingyin":"N"},{"id":"335","name":"内江","pingyin":"N"},{"id":"348","name":"那曲","pingyin":"N"},{"id":"388","name":"宁波","pingyin":"N"},{"id":"57","name":"莆田","pingyin":"P"},{"id":"71","name":"平凉","pingyin":"P"},{"id":"157","name":"平顶山","pingyin":"P"},{"id":"239","name":"萍乡","pingyin":"P"},{"id":"255","name":"盘锦","pingyin":"P"},{"id":"336","name":"攀枝花","pingyin":"P"},{"id":"58","name":"泉州","pingyin":"Q"},{"id":"72","name":"庆阳","pingyin":"Q"},{"id":"87","name":"清远","pingyin":"Q"},{"id":"108","name":"钦州","pingyin":"Q"},{"id":"115","name":"黔东南","pingyin":"Q"},{"id":"116","name":"黔南","pingyin":"Q"},{"id":"117","name":"黔西南","pingyin":"Q"},{"id":"131","name":"琼海","pingyin":"Q"},{"id":"132","name":"琼中","pingyin":"Q"},{"id":"145","name":"秦皇岛","pingyin":"Q"},{"id":"175","name":"七台河","pingyin":"Q"},{"id":"176","name":"齐齐哈尔","pingyin":"Q"},{"id":"187","name":"潜江","pingyin":"Q"},{"id":"284","name":"青岛","pingyin":"Q"},{"id":"378","name":"曲靖","pingyin":"Q"},{"id":"293","name":"日照","pingyin":"R"},{"id":"349","name":"日喀则","pingyin":"R"},{"id":"47","name":"宿州","pingyin":"S"},{"id":"59","name":"三明","pingyin":"S"},{"id":"77","name":"深圳","pingyin":"S"},{"id":"88","name":"汕头","pingyin":"S"},{"id":"89","name":"汕尾","pingyin":"S"},{"id":"90","name":"韶关","pingyin":"S"},{"id":"121","name":"三亚","pingyin":"S"},{"id":"138","name":"石家庄","pingyin":"S"},{"id":"158","name":"三门峡","pingyin":"S"},{"id":"159","name":"商丘","pingyin":"S"},{"id":"177","name":"双鸭山","pingyin":"S"},{"id":"178","name":"绥化","pingyin":"S"},{"id":"188","name":"神农架林区","pingyin":"S"},{"id":"189","name":"十堰","pingyin":"S"},{"id":"190","name":"随州","pingyin":"S"},{"id":"204","name":"邵阳","pingyin":"S"},{"id":"216","name":"四平","pingyin":"S"},{"id":"217","name":"松原","pingyin":"S"},{"id":"221","name":"苏州","pingyin":"S"},{"id":"227","name":"宿迁","pingyin":"S"},{"id":"240","name":"上饶","pingyin":"S"},{"id":"244","name":"沈阳","pingyin":"S"},{"id":"272","name":"石嘴山","pingyin":"S"},{"id":"307","name":"朔州","pingyin":"S"},{"id":"315","name":"商洛","pingyin":"S"},{"id":"321","name":"上海","pingyin":"S"},{"id":"337","name":"遂宁","pingyin":"S"},{"id":"350","name":"山南","pingyin":"S"},{"id":"389","name":"绍兴","pingyin":"S"},{"id":"3431","name":"圣佛堂村","pingyin":"S"},{"id":"48","name":"铜陵","pingyin":"T"},{"id":"73","name":"天水","pingyin":"T"},{"id":"118","name":"铜仁","pingyin":"T"},{"id":"133","name":"屯昌县","pingyin":"T"},{"id":"146","name":"唐山","pingyin":"T"},{"id":"191","name":"天门","pingyin":"T"},{"id":"218","name":"通化","pingyin":"T"},{"id":"228","name":"泰州","pingyin":"T"},{"id":"256","name":"铁岭","pingyin":"T"},{"id":"265","name":"通辽","pingyin":"T"},{"id":"294","name":"泰安","pingyin":"T"},{"id":"300","name":"太原","pingyin":"T"},{"id":"316","name":"铜川","pingyin":"T"},{"id":"343","name":"天津","pingyin":"T"},{"id":"390","name":"台州","pingyin":"T"},{"id":"397","name":"台湾","pingyin":"T"},{"id":"49","name":"芜湖","pingyin":"W"},{"id":"74","name":"武威","pingyin":"W"},{"id":"109","name":"梧州","pingyin":"W"},{"id":"134","name":"万宁","pingyin":"W"},{"id":"135","name":"文昌","pingyin":"W"},{"id":"136","name":"五指山","pingyin":"W"},{"id":"180","name":"武汉","pingyin":"W"},{"id":"222","name":"无锡","pingyin":"W"},{"id":"266","name":"乌海","pingyin":"W"},{"id":"267","name":"乌兰察布市","pingyin":"W"},{"id":"273","name":"吴忠","pingyin":"W"},{"id":"295","name":"威海","pingyin":"W"},{"id":"296","name":"潍坊","pingyin":"W"},{"id":"317","name":"渭南","pingyin":"W"},{"id":"351","name":"乌鲁木齐","pingyin":"W"},{"id":"379","name":"文山","pingyin":"W"},{"id":"391","name":"温州","pingyin":"W"},{"id":"50","name":"宣城","pingyin":"X"},{"id":"60","name":"厦门","pingyin":"X"},{"id":"147","name":"邢台","pingyin":"X"},{"id":"160","name":"新乡","pingyin":"X"},{"id":"161","name":"信阳","pingyin":"X"},{"id":"162","name":"许昌","pingyin":"X"},{"id":"181","name":"仙桃","pingyin":"X"},{"id":"192","name":"咸宁","pingyin":"X"},{"id":"193","name":"襄樊","pingyin":"X"},{"id":"194","name":"孝感","pingyin":"X"},{"id":"205","name":"湘潭","pingyin":"X"},{"id":"206","name":"湘西","pingyin":"X"},{"id":"229","name":"徐州","pingyin":"X"},{"id":"241","name":"新余","pingyin":"X"},{"id":"268","name":"锡林郭勒盟","pingyin":"X"},{"id":"269","name":"兴安盟","pingyin":"X"},{"id":"275","name":"西宁","pingyin":"X"},{"id":"308","name":"忻州","pingyin":"X"},{"id":"311","name":"西安","pingyin":"X"},{"id":"318","name":"咸阳","pingyin":"X"},{"id":"380","name":"西双版纳","pingyin":"X"},{"id":"395","name":"香港","pingyin":"X"},{"id":"3429","name":"西盖村","pingyin":"X"},{"id":"91","name":"阳江","pingyin":"Y"},{"id":"92","name":"云浮","pingyin":"Y"},{"id":"110","name":"玉林","pingyin":"Y"},{"id":"179","name":"伊春","pingyin":"Y"},{"id":"195","name":"宜昌","pingyin":"Y"},{"id":"207","name":"益阳","pingyin":"Y"},{"id":"208","name":"永州","pingyin":"Y"},{"id":"209","name":"岳阳","pingyin":"Y"},{"id":"219","name":"延边","pingyin":"Y"},{"id":"230","name":"盐城","pingyin":"Y"},{"id":"231","name":"扬州","pingyin":"Y"},{"id":"242","name":"宜春","pingyin":"Y"},{"id":"243","name":"鹰潭","pingyin":"Y"},{"id":"257","name":"营口","pingyin":"Y"},{"id":"270","name":"银川","pingyin":"Y"},{"id":"282","name":"玉树","pingyin":"Y"},{"id":"297","name":"烟台","pingyin":"Y"},{"id":"309","name":"阳泉","pingyin":"Y"},{"id":"310","name":"运城","pingyin":"Y"},{"id":"319","name":"延安","pingyin":"Y"},{"id":"320","name":"榆林","pingyin":"Y"},{"id":"338","name":"雅安","pingyin":"Y"},{"id":"339","name":"宜宾","pingyin":"Y"},{"id":"51","name":"亳州","pingyin":"Z"},{"id":"61","name":"漳州","pingyin":"Z"},{"id":"75","name":"张掖","pingyin":"Z"},{"id":"93","name":"湛江","pingyin":"Z"},{"id":"94","name":"肇庆","pingyin":"Z"},{"id":"95","name":"中山","pingyin":"Z"},{"id":"96","name":"珠海","pingyin":"Z"},{"id":"119","name":"遵义","pingyin":"Z"},{"id":"137","name":"儋州","pingyin":"Z"},{"id":"148","name":"张家口","pingyin":"Z"},{"id":"149","name":"郑州","pingyin":"Z"},{"id":"163","name":"周口","pingyin":"Z"},{"id":"164","name":"驻马店","pingyin":"Z"},{"id":"165","name":"漯河","pingyin":"Z"},{"id":"166","name":"濮阳","pingyin":"Z"},{"id":"198","name":"张家界","pingyin":"Z"},{"id":"210","name":"株洲","pingyin":"Z"},{"id":"232","name":"镇江","pingyin":"Z"},{"id":"274","name":"中卫","pingyin":"Z"},{"id":"298","name":"枣庄","pingyin":"Z"},{"id":"299","name":"淄博","pingyin":"Z"},{"id":"340","name":"资阳","pingyin":"Z"},{"id":"341","name":"自贡","pingyin":"Z"},{"id":"342","name":"泸州","pingyin":"Z"},{"id":"382","name":"昭通","pingyin":"Z"},{"id":"392","name":"舟山","pingyin":"Z"},{"id":"393","name":"衢州","pingyin":"Z"},{"id":"394","name":"重庆","pingyin":"Z"}]
     */

    private int retcode;
    private String msg;
    private List<DataBean> data;

    public int getRetcode() {
        return retcode;
    }

    public void setRetcode(int retcode) {
        this.retcode = retcode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 36
         * name : 安庆
         * pingyin : A
         */

        private String id;
        private String name;
        private String pingyin;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPingyin() {
            return pingyin;
        }

        public void setPingyin(String pingyin) {
            this.pingyin = pingyin;
        }
    }
}
