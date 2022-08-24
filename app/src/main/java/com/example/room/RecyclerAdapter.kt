package com.example.room

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.room.databinding.ItemRecyclerBinding
import java.text.SimpleDateFormat

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.Holder>() {
    var listData = mutableListOf<Memo>()
    var helper: SqliteHelper? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val memo = listData[position]
        holder.setMemo(memo)
    }

    override fun getItemCount(): Int {
        return listData.size
    }


    inner class Holder(private val binding : ItemRecyclerBinding): RecyclerView.ViewHolder(binding.root) {
        var mMemo : Memo? = null

        init {
            binding.deletebtn.setOnClickListener {
                helper?.deleteMemo(mMemo!!)
                listData.remove(mMemo)
                notifyDataSetChanged()
            }

            binding.editbtn.setOnClickListener {
                val m = Memo(mMemo!!.num,"수정됨", 34000000)

                helper?.updateMemo(m)
                listData.remove(mMemo)
                listData.add(m)
                notifyItemChanged(mMemo!!.num!!.toInt() - 1)
            }
        }


        fun setMemo(memo: Memo) {
            mMemo = memo
            binding.textNum.text = "${memo.num}"
            binding.textContent.text = memo.content
            val sdf = SimpleDateFormat("YYYY/MM/dd hh:mm")
            binding.textDatetime.text = sdf.format(memo.datetime)
        }
    }
}
