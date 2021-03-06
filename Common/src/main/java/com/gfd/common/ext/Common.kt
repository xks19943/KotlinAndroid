@file:Suppress("DEPRECATION")

package com.gfd.common.ext

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.support.v4.view.ViewPager
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.gfd.common.R
import com.gfd.common.utils.FixedSpeedScroller
import com.gfd.common.widgets.SpacesItemDecoration
import com.github.jdsjlzx.recyclerview.LRecyclerView
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter
import com.tencent.smtt.sdk.WebSettings
import com.tencent.smtt.sdk.WebView
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import java.lang.reflect.Field

/**
 * @Author : 郭富东
 * @Date ：2018/8/10 - 11:08
 * @Email：878749089@qq.com
 * @description：
 */
fun ViewPager.noScroll() {
    val mScroller: Field? = ViewPager::class.java.getDeclaredField("mScroller")
    mScroller!!.isAccessible = true
    val scroller = FixedSpeedScroller(this.context)
    mScroller.set(this, scroller)
}

fun LRecyclerView.gridInit(context: Context, span: Int = 3, adapter: LRecyclerViewAdapter) {
    val layoutManager = GridLayoutManager(context, span)
    this.layoutManager = layoutManager
    this.adapter = adapter
    this.setLoadMoreEnabled(false)
    this.setPullRefreshEnabled(false)
    val spacing = resources.getDimensionPixelSize(R.dimen.dp_4)
    this.addItemDecoration(SpacesItemDecoration.newInstance(
            spacing, spacing, span, resources.getColor(R.color.colorItemDecoration)))
}

fun LRecyclerView.listInit(context: Context, adapter: LRecyclerViewAdapter) {
    val layoutManager = LinearLayoutManager(context)
    this.layoutManager = layoutManager
    this.adapter = adapter
    this.setLoadMoreEnabled(false)
    this.setPullRefreshEnabled(false)
    val spacing = resources.getDimensionPixelSize(R.dimen.dp_4)
    this.addItemDecoration(SpacesItemDecoration.newInstance(
            spacing, spacing, 1, resources.getColor(R.color.colorItemDecoration)))
}

fun RecyclerView.gridInit(context: Context, span: Int = 3) {
    val layoutManager = GridLayoutManager(context, span)
    this.layoutManager = layoutManager
    val spacing = resources.getDimensionPixelSize(R.dimen.dp_4)
    this.addItemDecoration(SpacesItemDecoration.newInstance(
            spacing, spacing, span, resources.getColor(R.color.colorItemDecoration)))
}

var BANNER_TIME = 3 * 1000

fun Banner.player(titles: List<String>?, bannerImages: List<String>?) {
    val isNotEmptyImages = bannerImages?.isNotEmpty() ?: false
    if (isNotEmptyImages) {
        if (null != titles) {
            this.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE)
            this.setBannerTitles(titles)
        } else {
            this.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
        }
        this.setImageLoader(com.gfd.common.utils.GlideImageLoader())
        this.setImages(bannerImages)
        this.setDelayTime(BANNER_TIME)
        this.isAutoPlay(true)
        this.setIndicatorGravity(BannerConfig.CENTER)
        this.setBannerAnimation(Transformer.Default)
        this.start()
    }
}

@SuppressLint("SetJavaScriptEnabled")
fun WebSettings.config() {
    this.javaScriptEnabled = true
    this.useWideViewPort = true // 关键点
    this.allowFileAccess = true
    this.setSupportZoom(true)
    this.loadWithOverviewMode = true
    this.cacheMode = WebSettings.LOAD_NO_CACHE// 不加载缓存内容
    this.mixedContentMode = android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
    //自动播放视频
    this.mediaPlaybackRequiresUserGesture = false
}

fun WebView.onDestroy() {
    val parent = this.parent
    if (parent != null) {
        (parent as ViewGroup).removeView(this)
    }
    this.stopLoading()
    this.onPause()
    this.settings.javaScriptEnabled = false
    this.clearHistory()
    this.clearView()
    this.removeAllViews()
    this.destroy()
}