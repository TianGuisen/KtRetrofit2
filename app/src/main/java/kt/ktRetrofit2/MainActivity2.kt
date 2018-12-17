package kt.ktRetrofit2

import android.os.Bundle
import android.view.View
import com.trello.rxlifecycle2.components.RxActivity
import kotlinx.android.synthetic.main.activity_main2.*
import kt.ktRetrofit2.bean.ProductInfo
import kt.ktRetrofit2.core.RVObserver
import kt.ktRetrofit2.core.ioMain
import kt.ktRetrofit2.servier.ProductService
import tgs.adapter.BaseAdapter
import tgs.adapter.SimpleAdapter

class MainActivity2 : RxActivity() {
    val listData = mutableListOf<ProductInfo>()
    val testAdapter: SimpleAdapter<ProductInfo> = SimpleAdapter(listData, R.layout.item_test)
    var pageNum = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        srl.setOnRefreshListener {
            pageNum = 1
            loadData(pageNum)
        }
        srl.setOnLoadMoreListener {
            loadData(++pageNum)
        }
        lv.setOnRetryClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                lv.showLoading()
                loadData(1)
            }
        })
        testAdapter.setOnItemClickLisener(object : BaseAdapter.OnItemClickLisener<ProductInfo> {
            override fun itemClick(bean: ProductInfo, view: View, position: Int) {

            }
        })
        loadData(1)
    }


    fun loadData(pageNum: Int) {
        ProductService.getProductList(pageNum, 10).compose(ioMain(this))
                .subscribe(object : RVObserver<MutableList<ProductInfo>>(lv, pageNum) {
                    override fun onSuccess(data: MutableList<ProductInfo>, code: Int, msg: String?, tag: Any?) {
                        if (pageNum == 1) {
                            listData.clear()
                        }
                        listData.addAll(data!!)
                        notifyAdapter()
                    }

                    override fun onFailure(data: MutableList<ProductInfo>?, code: Int, msg: String?, tag: Any?) {
                        notifyAdapter()
                    }
                })
    }

    fun notifyAdapter() {
        srl.finishRefresh()
        srl.finishLoadMore()
        testAdapter.notifyDataSetChanged()
    }
}