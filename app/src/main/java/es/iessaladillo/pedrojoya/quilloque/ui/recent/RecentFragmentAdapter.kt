package es.iessaladillo.pedrojoya.quilloque.ui.contacts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.iessaladillo.pedrojoya.quilloque.R
import es.iessaladillo.pedrojoya.quilloque.data.Contact
import es.iessaladillo.pedrojoya.quilloque.data.RecentCall
import es.iessaladillo.pedrojoya.quilloque.data.getCallTypeIcon
import es.iessaladillo.pedrojoya.quilloque.utils.createAvatarDrawable
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.contacts_fragment_item.view.*
import kotlinx.android.synthetic.main.contacts_fragment_item.view.btnCall
import kotlinx.android.synthetic.main.contacts_fragment_item.view.btnDelete
import kotlinx.android.synthetic.main.contacts_fragment_item.view.btnVideoCall
import kotlinx.android.synthetic.main.contacts_fragment_item.view.imgAvatar
import kotlinx.android.synthetic.main.contacts_fragment_item.view.lblName
import kotlinx.android.synthetic.main.contacts_fragment_item.view.lblPhoneNumber
import kotlinx.android.synthetic.main.recent_fragment_item.*
import kotlinx.android.synthetic.main.recent_fragment_item.view.*

class RecentFragmentAdapter: RecyclerView.Adapter<RecentFragmentAdapter.ViewHolder>() {

    private var data: List<RecentCall> = emptyList()


    private var onItemClickListener: ((Int,View) -> Unit)? = null
    private var onDeleteClickListener: ((Int) -> Unit)? = null
    private var onCallClickListener: ((Int) -> Unit)? = null
    private var onVideoCallClickListener: ((Int) -> Unit)? = null



    fun setOnItemClickListener(listener: ((Int,View) -> Unit)?){
        onItemClickListener= listener
    }

    fun setOnDeleteClickListener(listener: ((Int) -> Unit)?){
        onDeleteClickListener= listener
    }

    fun setOnCallClickListener(listener: ((Int) -> Unit)?){
        onCallClickListener= listener
    }

    fun setOnVideoCallClickListener(listener: ((Int) -> Unit)?){
        onVideoCallClickListener= listener
    }

    fun getItem(position: Int):RecentCall{
        return data[position]
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.recent_fragment_item, parent, false)
        return ViewHolder(itemView,onItemClickListener,onDeleteClickListener,onCallClickListener,onVideoCallClickListener)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    fun submitList(calls: List<RecentCall>) {
        data=calls
        notifyDataSetChanged()
    }


    inner class ViewHolder(
        override val containerView: View,
        onItemClickListener: ((Int, View) -> Unit)?,
        onDeleteClickListener: ((Int) -> Unit)?,
        onCallClickListener: ((Int) -> Unit)?,
        onVideoCallClickListener: ((Int) -> Unit)?
    ) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        init {
            containerView.setOnClickListener {
                onItemClickListener?.invoke(adapterPosition,containerView)
            }
            containerView.btnDelete.setOnClickListener {
                onDeleteClickListener?.invoke(adapterPosition)
            }
            containerView.btnCall.setOnClickListener {
                onCallClickListener?.invoke(adapterPosition)
            }
            containerView.btnVideoCall.setOnClickListener {
                onVideoCallClickListener?.invoke(adapterPosition)
            }
        }

        fun bind(recentCall: RecentCall) {
            recentCall.run {
                if (this.contactName.isNullOrBlank()){
                    containerView.lblName.text= this.phoneNumber
                    containerView.lblPhoneNumber.text = ""
                    containerView.lblCreateContact.visibility = View.VISIBLE
                    containerView.imgAvatar.setImageDrawable(createAvatarDrawable("?"))
                }else{
                    containerView.imgAvatar.setImageDrawable(createAvatarDrawable(this.contactName!!))
                    containerView.lblName.text= this.contactName
                    containerView.lblPhoneNumber.text = this.phoneNumber
                    containerView.lblCreateContact.visibility = View.INVISIBLE
                }
                containerView.lblDate.text= this.date
                containerView.lblTime.text= this.time
                containerView.imgCallType.run {
                    this.setImageDrawable(resources.getDrawable(getCallTypeIcon(recentCall.type)))
                }
            }
        }
    }


}