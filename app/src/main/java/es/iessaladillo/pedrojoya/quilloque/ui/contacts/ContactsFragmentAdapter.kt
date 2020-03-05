package es.iessaladillo.pedrojoya.quilloque.ui.contacts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.iessaladillo.pedrojoya.quilloque.R
import es.iessaladillo.pedrojoya.quilloque.data.Contact
import es.iessaladillo.pedrojoya.quilloque.utils.createAvatarDrawable
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.contacts_fragment_item.view.*

class ContactsFragmentAdapter: RecyclerView.Adapter<ContactsFragmentAdapter.ViewHolder>() {

    private var data: List<Contact> = emptyList()


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

    fun getItem(position: Int):Contact{
        return data[position]
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.contacts_fragment_item, parent, false)
        return ViewHolder(itemView,onItemClickListener,onDeleteClickListener,onCallClickListener,onVideoCallClickListener)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    fun submitList(contacts: List<Contact>) {
        data=contacts
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

        fun bind(contact: Contact) {
            contact.run {
                containerView.lblName.text= this.name
                containerView.lblPhoneNumber.text = this.phone
                containerView.imgAvatar.setImageDrawable(createAvatarDrawable(this.name))
            }
        }
    }


}