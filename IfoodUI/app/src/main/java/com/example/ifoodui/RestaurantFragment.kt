package com.example.ifoodui

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import co.tiagoaguiar.atway.ui.adapter.ATAdapter
import com.example.ifoodui.databinding.FragmentRestaurantBinding

class RestaurantFragment : Fragment(R.layout.fragment_restaurant) {

    private val categoryAdapter = CategoryAdapter(
        arrayListOf(
            Category(
                1,
                "https://wwww.ifood.com.br/static/images/categories/market.png",
                "Mercado",
                0XFFB6D048
            ),
            Category(
                2,
                "https://wwww.ifood.com.br/static/images/categories/restaurant.png",
                "Restaurante",
                0xFFE91D2D
            ),
            Category(
                3,
                "https://wwww.ifood.com.br/static/images/categories/drinks.png",
                "Bebidas",
                0XFFF6D553
            ),
            Category(
                4,
                "https://static-images.ifood.com.br/image/upload/f_auto/webapp/landingV2/express.png",
                "Express",
                0xFFFF0000
            ),
            Category(
                5,
                "https://parceiros.ifood.com.br/static/media/salad.9db040c0.png",
                "Saudável",
                0xFFE91D2D
            ),

            Category(
                6,
                "https://wwww.ifood.com.br/static/images/categories/drinks.png",
                "Salgados",
                0xFF8C60C5
            ),

            )
    )
    private val bannerAdapter = BannerAdapter(
        arrayListOf(
            Banner(
                1,
                "https://static-images.ifood.com.br/image/upload/t_high/discoveries/itensBasicosNOV21Principal_zE1X.png"
            ),
            Banner(
                2,
                "https://static-images.ifood.com.br/image/upload/t_high/discoveries/MerceariaeMatinaisPrincipal_mfDO.png"
            ),
            Banner(
                3,
                "https://static-images.ifood.com.br/image/upload/t_high/discoveries/Bebidas40offPrincipal_cljA.png"
            )
        )
    )


    val moreShopAdapter = MoreShopAdapter(
        arrayListOf(
            MoreShop(
                1,
                "https://static-images.ifood.com.br/image/upload/t_high/logosgde/46ebd05c-116e-41cd-b3de-7a05c5bc730a/201811071958_30656.jpg",
                "Pizza Crek",
                4.4,
                "Pizza",
                11.2,
                "60-70",
                26.00
            ),
            MoreShop(
                2,
                "https://static-images.ifood.com.br/image/upload/t_high/logosgde/bb3ad636-7c36-4ae2-a1db-14cd35695350/202001271029_rK15_i.png",
                "Fábrica de Esfiha",
                4.3,
                "Esfiha",
                12.2,
                "60-70",
                9.00
            ),
            MoreShop(
                3,
                "https://static-images.ifood.com.br/image/upload/t_high/logosgde/2fd863ac-4cc2-476c-8896-99aedfdaeb5f/201911150948_Z9QG_i.jpg",
                "Pecorino",
                4.9,
                "Grill",
                17.2,
                "60-70",
                10.00
            ),
            MoreShop(
                4,
                "https://static-images.ifood.com.br/image/upload/t_high/logosgde/86b58685-a7dc-4596-be26-2c4037b4d591/202006051304_JuRt_i.jpg",
                "Barbacoa Grill",
                4.9,
                "Grill",
                12.2,
                "70-90",
                40.00
            ),
            MoreShop(
                5,
                "https://static-images.ifood.com.br/image/upload/t_high/logosgde/e2f3424a-06fb-46dd-89c3-f7b039e2b1f0_BOLOD_PPIN02.jpeg",
                "Bolo de Madre",
                4.7,
                "Bolo",
                11.0,
                "80-90",
                30.00
            ),
            MoreShop(
                6,
                "https://static-images.ifood.com.br/image/upload/t_high/logosgde/201901021647_8066dc64-9383-46d1-aa2d-56b9492e27ed.png",
                "Uau Esfiha",
                4.4,
                "Esfiha",
                11.2,
                "60-70",
                8.00
            ),
            MoreShop(
                7,
                "https://static-images.ifood.com.br/image/upload/t_high/logosgde/201705131248_0ca51a98-ee95-48ac-b193-48066c8f20cc.png",
                "Bar do Juarez",
                4.9,
                "Bar",
                17.2,
                "40-50",
                13.00
            ),
        )
    )

    private val shopAdapter = ShopAdapter(
        arrayListOf(
            Shop(
                1,
                "https://static-images.ifood.com.br/image/upload/t_high/logosgde/46ebd05c-116e-41cd-b3de-7a05c5bc730a/201811071958_30656.jpg",
                "Pizza Crek"
            ),
            Shop(
                2,
                "https://static-images.ifood.com.br/image/upload/t_high/logosgde/bb3ad636-7c36-4ae2-a1db-14cd35695350/202001271029_rK15_i.png",
                "Fábrica de Esfiha"
            ),
            Shop(
                3,
                "https://static-images.ifood.com.br/image/upload/t_high/logosgde/2fd863ac-4cc2-476c-8896-99aedfdaeb5f/201911150948_Z9QG_i.jpg",
                "Pecorino"
            ),
            Shop(
                4,
                "https://static-images.ifood.com.br/image/upload/t_high/logosgde/86b58685-a7dc-4596-be26-2c4037b4d591/202006051304_JuRt_i.jpg",
                "Barbacoa Grill"
            ),
            Shop(
                5,
                "https://static-images.ifood.com.br/image/upload/t_high/logosgde/e2f3424a-06fb-46dd-89c3-f7b039e2b1f0_BOLOD_PPIN02.jpeg",
                "Bolo de Madre"
            ),
            Shop(
                6,
                "https://static-images.ifood.com.br/image/upload/t_high/logosgde/201901021647_8066dc64-9383-46d1-aa2d-56b9492e27ed.png",
                "Uau Esfiha"
            ),
            Shop(
                7,
                "https://static-images.ifood.com.br/image/upload/t_high/logosgde/201705131248_0ca51a98-ee95-48ac-b193-48066c8f20cc.png",
                "Bar do Juarez"
            ),
        )
    )

    private lateinit var binding: FragmentRestaurantBinding
    private var filters = arrayOf(
        FilterItem(1, "Ordenar", closeIcon = R.drawable.ic_keyboard_down),
        FilterItem(2, "Pra retirar", icon = R.drawable.ic_directions_walk),
        FilterItem(3, "entrega grátis"),
        FilterItem(4, "Vale-refeição", closeIcon = R.drawable.ic_keyboard_down),
        FilterItem(5, " Distância", closeIcon = R.drawable.ic_keyboard_down),
        FilterItem(6, "Entrega Parceria"),
        FilterItem(7, "Super Restaurante"),
        FilterItem(8, " Filtros", closeIcon = R.drawable.ic_filter_list),
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRestaurantBinding.bind(view)


        binding.let {
            filters.forEach { filterItem ->
                it.chipGroupFilter.addView(filterItem.toChip(requireContext()))
            }

            addDots(it.dots, bannerAdapter.itemCount, 0)

            it.rvCategory.layoutManager =
                setupLinearLayoutManager(LinearLayout.HORIZONTAL)
            it.rvCategory.adapter = categoryAdapter

            it.rvBanners.layoutManager =
                setupLinearLayoutManager(LinearLayout.HORIZONTAL)

            it.rvBanners.adapter = bannerAdapter
            it.rvBanners.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        notifyPositionChanged(recyclerView)
                    }
                }
            })

            it.rvShops.adapter = shopAdapter
            it.rvShops.layoutManager = setupLinearLayoutManager(LinearLayout.HORIZONTAL)

            it.rvMoreShops.adapter = moreShopAdapter
            it.rvMoreShops.layoutManager = setupLinearLayoutManager(LinearLayout.VERTICAL)
        }

    }


    fun addDots(container: LinearLayout, size: Int, position: Int) {
        container.removeAllViews()
        Array(size) {
            val textView = TextView(context).apply {
                text = getString(R.string.dotted)
                textSize = 20f
                setTextColor(
                    if (position == it) ContextCompat.getColor(context, R.color.black)
                    else ContextCompat.getColor(context, android.R.color.darker_gray)

                )
            }
            container.addView(textView)
        }
    }

    private val snapHelper = LinearSnapHelper()
    private var position: Int? = RecyclerView.NO_POSITION
    fun notifyPositionChanged(rv: RecyclerView) {
        val layoutManager = rv.layoutManager
        val view = snapHelper.findSnapView(layoutManager)
        val position =
            if (view == null) RecyclerView.NO_POSITION else layoutManager?.getPosition(view)

        val positionChanged = this.position != position

        if (positionChanged) {
            addDots(binding.dots, bannerAdapter.itemCount, position ?: 0)
        }
        this.position = position
    }

    fun setupLinearLayoutManager(type: Int): LinearLayoutManager {
        return LinearLayoutManager(requireContext(), type, false)
    }

}