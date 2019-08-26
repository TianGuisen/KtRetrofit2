package gdwl.tgs.bean

import java.io.Serializable

/**
 * Created by hcc on 16/8/20 12:38
 * 100332338@qq.com
 *
 *
 * 首页推荐界面数据
 */

class RecommendInfo {

    var code: Int = 0
    var result: MutableList<ResultBean>? = null

    class ResultBean {
        /**
         * type : recommend
         * head : {"param":"","goto":"","style":"gm_av","title":"热门焦点"}
         * body : [{"title":"【碧蓝航线MMD】 为什么到现在才开始爱上你 . . .『 Angelite 』","style":"gm_av","cover":"http://i0.hdslb.com/bfs/archive/5e82f63a7efd2118759f5a765e7d7b88092b85e1.jpg","param":"11669931","goto":"av","width":350,"height":219,"play":"9.0万","danmaku":"1067","up":"梦亦星逝"},{"title":"【第9回东方NICO童祭】暁Records / 秘封俱乐部【东方PV】","style":"gm_av","cover":"http://i0.hdslb.com/bfs/archive/6872cd625b8eca085084a396659b87531a4bc5f4.jpg","param":"11589549","goto":"av","width":350,"height":219,"play":"4.8万","danmaku":"458","up":"伊吹小秋"},{"title":"【乐正龙牙翻唱】喜剧之王【动点p】【原创PV付】","style":"gm_av","cover":"http://i0.hdslb.com/bfs/archive/4dc965faec20b058b6805d8713b6d26ac7d081c9.jpg","param":"11713479","goto":"av","width":350,"height":219,"play":"4.3万","danmaku":"1479","up":"动次打次的动点p"},{"title":"【MMD】纯情裙摆[tumi式初音]","style":"gm_av","cover":"http://i0.hdslb.com/bfs/archive/1c9285a6b4348fb00c5a3f0f7f6f982f010f168b.jpg","param":"11612879","goto":"av","width":350,"height":219,"play":"5.1万","danmaku":"613","up":"van狒"}]
         */

        var type: String? = null
        var head = HeadBean()
        var body = mutableListOf<BodyBean>()

        class HeadBean {
            /**
             * param :
             * goto :
             * style : gm_av
             * title : 热门焦点
             */
            var style: String? = null
            var title: String? = null
            var img: Int = 0
            override fun toString(): String {
                return "HeadBean(style=$style, title=$title, img=$img)"
            }

        }

        class BodyBean : Serializable {
            /**
             * title : 【碧蓝航线MMD】 为什么到现在才开始爱上你 . . .『 Angelite 』
             * style : gm_av
             * cover : http://i0.hdslb.com/bfs/archive/5e82f63a7efd2118759f5a765e7d7b88092b85e1.jpg
             * param : 11669931
             * goto : av
             * width : 350
             * height : 219
             * play : 9.0万
             * danmaku : 1067
             * up : 梦亦星逝
             */

            var title: String? = null
            var style: String? = null
            var cover: String? = null
            var height: Int = 0
            var play: String? = null
            var danmaku: String? = null
            var up: String? = null
            
            override fun toString(): String {
                return "BodyBean(title=$title, style=$style, cover=$cover, height=$height, play=$play, danmaku=$danmaku, up=$up)"
            }

        }

        override fun toString(): String {
            return "ResultBean(type=$type, head=$head, body=$body)"
        }

    }

    class BannerInfo {
        var title: String = ""
        var value: String = ""
        var image: String = ""
        var type: Int = 0
        override fun toString(): String {
            return "BannerInfo(title='$title', value='$value', image='$image', type=$type)"
        }

    }

    override fun toString(): String {
        return "RecommendInfo(code=$code, result=$result)"
    }

}
