package com.gfd.music.adapter

import android.content.Context
import android.text.Html
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.gfd.common.ui.adapter.BaseAdapter
import com.gfd.common.ui.adapter.BaseViewHolder
import com.gfd.common.utils.FormatUtil
import com.gfd.common.utils.FormatUtil.time
import com.gfd.music.R
import com.gfd.music.entity.CommentData
import de.hdodenhof.circleimageview.CircleImageView

/**
 * @Author : 郭富东
 * @Date ：2018/8/17 - 17:48
 * @Email：878749089@qq.com
 * @description：
 */
class MvCommentAdapter(val context: Context) : BaseAdapter<CommentData>(context) {
    override fun getItemLayoutId(): Int = R.layout.music_item_mv_comment

    override fun onBindView(holder: BaseViewHolder, position: Int) {
        val commentData = mData[position]
        holder.apply {
            setText(R.id.tv_item_mvcomment_author, commentData.userName)
            setText(R.id.tv_item_mvcomment_good, commentData.likedCount.toString())
            getView<TextView>(R.id.tv_item_mvcomment_content).setText(commentData.content, TextView.BufferType.SPANNABLE)
            setText(R.id.tv_item_mvcomment_time, FormatUtil.formatDate(commentData.time))
            Glide.with(context).load(commentData.userPic).into(getView(R.id.iv_item_mvcomment_pic))
            getView<CheckBox>(R.id.iv_mvdetail_good).isChecked = commentData.liked
        }
    }

}